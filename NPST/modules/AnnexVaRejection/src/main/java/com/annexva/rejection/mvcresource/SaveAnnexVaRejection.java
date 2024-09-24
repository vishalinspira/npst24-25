package com.annexva.rejection.mvcresource;

import com.annexva.rejection.constants.annexvarejectionPortletKeys;
import com.daily.average.service.model.Annexvarejection;
import com.daily.average.service.service.AnnexvarejectionLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(property = { "javax.portlet.name=" + annexvarejectionPortletKeys.ANNEXVAREJECTION,
"mvc.command.name=/annexvarejection/save" }, service = MVCResourceCommand.class)

public class SaveAnnexVaRejection implements MVCResourceCommand{
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		String cra = "";
		if(reportMasterId == 4) {
			cra = "PCRA";
		} else if(reportMasterId == 19){
			cra = "KCRA";
		} else if(reportMasterId == 124){
			cra = "CCRA";
		}

		String fileName = uploadPortletRequest.getFileName("annexvarejectionFile");

		File file = uploadPortletRequest.getFile("annexvarejectionFile");

		String mimeType = uploadPortletRequest.getContentType("annexvarejectionFile");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		AnnexvarejectionLocalServiceUtil.deleteAnnexvarejectionByReportUploadLogId(reportUploadLogId);
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			JSONArray annexvarejectionJsonArray = JSONFactoryUtil.createJSONArray();
			List<Annexvarejection> annexvarejections = new ArrayList<Annexvarejection>();
			//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Payment Id");
			
			
			boolean isexcelhaserror = false;
			String sheetName = "Transaction Return MIS";
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("Transaction Return MIS");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						rowLoop:
						while (rows.hasNext()) {
							
							Annexvarejection annexvarejection = AnnexvarejectionLocalServiceUtil.createAnnexvarejection(CounterLocalServiceUtil.increment(Annexvarejection.class.getName()));
									
							annexvarejection.setCreatedby(userId);
							annexvarejection.setReportUploadLogId(reportUploadLogId);
							annexvarejection.setCra(cra);
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							for (int i = 0; i < 29; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
		
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
										if (i == 0) {
											if (val != null ) {
												try {
													Date date= cell.getDateCellValue();
													annexvarejection.setValue_date(date);
												} catch (Exception e) {
													//annexvarejection.setValue_date(cell.getDateCellValue());
													// _log.error("error parsing date "+val);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											
										} 
										else if (i == 1) {
											try {
												Date date= cell.getDateCellValue();
												annexvarejection.setTrans_date(date);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 2) {
											try {
												Long Payment_ID = CommonParser.parseLong(val);
												annexvarejection.setPayment_id(Payment_ID);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										}
										 else if (i == 3) {
											 annexvarejection.setReturn_ref(val);
										}
										else if (i == 4) {
											annexvarejection.setMessage_type(val);
										}
										else if (i == 5) {
											annexvarejection.setSender_name(val);
										} 
										else if (i == 6) {
											annexvarejection.setUtr_number(val);
										} 
										else if (i == 7) {
											annexvarejection.setSender_ifsc(val);
										} 
										else if (i == 8) {
											try {
												Long Sender_Acc_No = CommonParser.parseLong(val);
												annexvarejection.setSender_acc_number(Sender_Acc_No);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										}
										else if (i == 9) {
											annexvarejection.setBeneficiary_ifsc(val);
										}
										else if (i == 10) {
											try {
												Long Beneficiary_Acc_No = CommonParser.parseLong(val);
												annexvarejection.setBeneficiary_acc_number(Beneficiary_Acc_No);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 11) {
											try {
												Long Credit_Acc_No = CommonParser.parseLong(val);
												annexvarejection.setCredit_acc_number(Credit_Acc_No);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 12) {
											try {
												Integer Corporate_Code = Integer.parseInt(val);
												annexvarejection.setCorporate_code(Corporate_Code);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 13) {
											annexvarejection.setBeneficiary_acc_name(val);
										} 
										else if (i == 14) {
											annexvarejection.setSender_info(val);
										}
										else if (i == 15) {
											try {
												BigDecimal amount = CommonParser.parseBigDecimal(val);
												annexvarejection.setAmount(amount);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 16) {
											annexvarejection.setBeneficiary_acc_type(val);
										} 
										else if (i == 17) {
											annexvarejection.setPayment_status(val);
										} 
										else if (i == 18) {
											try {
												Date date= cell.getDateCellValue();
												annexvarejection.setReturn_date(date);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 19) {
											annexvarejection.setSender_receiver_info(val);
										}
										
										else if (i == 20) {
											try {
												Integer POP_REG_NO = Integer.parseInt(val);
												annexvarejection.setPop_reg_no(POP_REG_NO);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										}
										else if (i == 21) {
											try {
												Integer POP_SP_REG_NO = Integer.parseInt(val);
												annexvarejection.setPop_sp_reg_no(POP_SP_REG_NO);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 22) {
											annexvarejection.setFiller_ref1(val);
										} 
										else if (i == 23) {
											annexvarejection.setFiller_ref2(val);
										}
										else if (i == 24) {
											annexvarejection.setFiller_ref3(val);
										} 
										else if (i == 25) {
											annexvarejection.setFiller_ref4(val);	
										} 
										else if (i == 26) {
											annexvarejection.setComments(val);
										} 
										else if (i == 27) {
											try {
												Integer cra_id = Integer.parseInt(val);
												annexvarejection.setCra_id(cra_id);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 28) {
											annexvarejection.setReason_return(val);
										}
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
								annexvarejection.setCreateDate(new Date());
								
								if (isError && rowCount > 1) {
									errorArray.put(errorObj);
									errorRow = xx.createRow(errorRowCount);
									errorRowCount++;
									XSSFCell rowCountCel = errorRow.createCell(1);
									rowCountCel.setCellValue(rowCount);
									XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
									cellError.setCellValue(errorObj.getString("msg"));
									isexcelhaserror = true;
								} else if (rowCount > 1) {
									//JSONObject annexvarejectionJsonObject = JSONFactoryUtil.createJSONObject(annexvarejection.toString());
									//annexvarejectionJsonArray.put(annexvarejectionJsonObject);
									annexvarejections.add(annexvarejection);
								}
								rowCount++;
							}
						_log.info("CL BAL data count"+rowCount);
						}
					}
				
				}catch (Exception e) {
					 _log.error(e);
					 resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					 return resultJsonObject;
				}
				_log.info(annexvarejectionJsonArray.toString());
				
				if (!isexcelhaserror) {
					try {
						AnnexvarejectionLocalServiceUtil.addAnnexvarejection(annexvarejections);
					} catch (Exception e1) {
						_log.error(e1);
						resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
						 return resultJsonObject;
					}
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
					try {
						errorExcel.close();
						
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(annexvarejections, themeDisplay, resourceRequest));
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {
	
						OutputStream stream = null;
	
						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
	
						errorExcel.write(stream);
	
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}
			return resultJsonObject;
		}
	
		@SuppressWarnings("deprecation")
		public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
				ResourceRequest resourceRequest) {

			File file = new File(filepath);
			String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			String title = filename + ".xlsx";
			String description = "This file is added via programatically";
			long repositoryId = themeDisplay.getScopeGroupId();
			String downloadUrl = StringPool.BLANK;
			try {

				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
						resourceRequest);
				FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0, title, mimeType, title, description, "",
						file, serviceContext);
				FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
				downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
			} catch (Exception e) {
				_log.info(e.getMessage());
				 _log.error(e);
			}
			return downloadUrl;
		}
		
		@SuppressWarnings("deprecation")
		public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
			
			Date date =new Date();
			UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

			String fileName =date.getTime()+ uploadPortletRequest.getFileName("annexvarejectionFile");

			File file = uploadPortletRequest.getFile("annexvarejectionFile");

			String mimeType = uploadPortletRequest.getContentType("annexvarejectionFile");

			String title = fileName;

			String description = "Rejection & Return MIS (D-remit)";

			long repositoryId = themeDisplay.getScopeGroupId();
		

			try {

				Folder folder = getFolder(themeDisplay);

				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
						resourceRequest);

				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
						"", file, serviceContext);
				return fileEntry.getFileEntryId();

			} catch (Exception e) {

				_log.info(e.getMessage());

				 _log.error(e);
			}
			return 0;
		}
		private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		public Folder getFolder(ThemeDisplay themeDisplay) {

			Folder folder = null;
			try {
				folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Daily");
				_log.info(folder);

			} catch (Exception e) {

				_log.info(e.getMessage());
			}
			return folder;

		}
		
		public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
			Date createDate = new Date();
			ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
		}
		
		@Reference
		ExcelValidationAn10Api excelValidationAn10Api;
		
		Log _log = LogFactoryUtil.getLog(getClass());
	}


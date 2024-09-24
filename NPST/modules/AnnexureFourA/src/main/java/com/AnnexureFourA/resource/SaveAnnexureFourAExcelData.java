package com.AnnexureFourA.resource;

import com.AnnexureFourA.constants.AnnexureFourAPortletKeys;
import com.daily.average.service.model.AnnexureFourA;
import com.daily.average.service.service.AnnexureFourALocalServiceUtil;
import com.daily.average.service.service.DailyAverageLocalServiceUtil;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + AnnexureFourAPortletKeys.ANNEXUREFOURA,
				"mvc.command.name=/saveAnnexureFourA/data",
		},
		service = MVCResourceCommand.class
		)
public class SaveAnnexureFourAExcelData implements MVCResourceCommand{
	
	private static final Long ANNEXURE_FOUR_A_REPORT_ID = 6L;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("SaveAnnexureFourAExcelData.serveResource()");
		
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
		
		String fileName = uploadPortletRequest.getFileName("AnnexureFourAFile");

		File file = uploadPortletRequest.getFile("AnnexureFourAFile");

		String mimeType = uploadPortletRequest.getContentType("AnnexureFourAFile");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		AnnexureFourALocalServiceUtil.deleteAnnexureFourAByReportUploadLogId(reportUploadLogId);
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			JSONArray eleRejectsJsonArray = JSONFactoryUtil.createJSONArray();
			List<AnnexureFourA> eleRejectList = new ArrayList<AnnexureFourA>();
			//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern,symbols);
			decimalFormat.setParseBigDecimal(true);
			
			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell fileNamecell = xxx.createCell(2);
			fileNamecell.setCellValue("Sr. No.");
			
			
			boolean isexcelhaserror = false;
			boolean isError = false;
			String sheetName = "Report";
			
			//sheet validate
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook);
			
			if (errorlist.size() > 0) {
				resultJsonObject.put("status", false);
				resultJsonObject.put("sheeterror",true);
				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
				resultJsonObject.put("errorSheetNameList", arrayList);
				try {
					errorExcel.close();
				} catch (IOException e) {
					_log.error(e);
				}

				return resultJsonObject;
			}
			
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					Iterator<Sheet> sheets = workbook.sheetIterator();
					while (sheets.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets.next();
						if(sheet.getSheetName().equalsIgnoreCase("Report")) {
							XSSFSheet sheetNext = workbook.getSheet(sheetName);
							Iterator<Row> rows = sheetNext.rowIterator();
							int rowCount = 1;
							int errorRowCount = 2;
							
							while (rows.hasNext()) {
								
								AnnexureFourA afa = AnnexureFourALocalServiceUtil.createAnnexureFourA(CounterLocalServiceUtil.increment(AnnexureFourA.class.getName()));
								
								afa.setReportUploadLogId(reportUploadLogId);
								afa.setCreatedby(userId);
								
								JSONObject errorObj = JSONFactoryUtil.createJSONObject();
								errorObj.put("rownum", rowCount);
								XSSFRow row = (XSSFRow) rows.next();
								XSSFRow errorRow = null;
								
								for (int i = 0; i < 10; i++) {
									XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
									if (cell != null) {
										
										DataFormatter formatter = new DataFormatter();
										
										String val = formatter.formatCellValue(cell);
										if (val != null)
											val = val.trim();
										if(rowCount > 1) {
											if (i == 0) {
												
												if (val != null && Validator.isNotNull(val) && val.length() > 0) {
													try {
														afa.setNps_acc_number(Long.parseLong(val.trim()));
													} catch (Exception e) {
														_log.error(e);
														 _log.info("error parsing integer"+val);
															resultJsonObject.put("status", false);
															resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
															return resultJsonObject;
													}
												}
												 else {
														errorObj.put("cellno", 2);
														errorObj.put("msg", "Invalid input.");
														
														isError = true;
													}
											}
											else if (i == 1) {
												afa.setNps_acc_name(val);
											}
											else if (i == 2) {
												try {
													Date date= cell.getDateCellValue();
													afa.setDate1(date);
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 3) {
												try {
													Date date= cell.getDateCellValue();
													afa.setDate2(date);
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 4) {
												try {
													BigDecimal bg =  CommonParser.parseBigDecimal(val);
													afa.setFunds_received_upto_date1(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 5) {
												try {
													BigDecimal bg =  CommonParser.parseBigDecimal(val);
													afa.setFunds_received_upto_date2(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 6) {
												try {
													BigDecimal bg =  CommonParser.parseBigDecimal(val);
													afa.setFunds_transfer_on_date1(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 7) {
												try {
													BigDecimal bg =  CommonParser.parseBigDecimal(val);
													afa.setFunds_transfer_on_date2(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											}
											else if (i == 8) {
												try {
													String s = String.valueOf(cell.getNumericCellValue());
													BigDecimal bg =  CommonParser.parseBigDecimal(val);
													afa.setTrustee_bank_outstanding(bg.stripTrailingZeros());
												} catch (Exception e) {
													_log.error(e);
													_log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} 
											else if (i == 9) {
												afa.setTrustee_bank_comments(val);
											} 
											
										}
									} // cell end here
								}
								
								afa.setCreatedate(new Date());
									
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
										//JSONObject eleRejectsJsonObject = JSONFactoryUtil.createJSONObject(afa.toString());
										//eleRejectsJsonArray.put(eleRejectsJsonObject);
										eleRejectList.add(afa);
										//_log.info(afa);
									}
									rowCount++;
								}
							_log.info(sheetName +" rowcount"+rowCount);
							  }
							}
						}
				}catch (Exception e) {
					isError = true;
					_log.error(e);
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					 return resultJsonObject;
				}
			
				_log.info(eleRejectsJsonArray.toString());
				
				if (!isexcelhaserror) {
					try {
						_log.info(sheetName +" datacount"+eleRejectList.size());
						AnnexureFourALocalServiceUtil.addAnnexureFourA(eleRejectList);
						
					} catch (Exception e1) {
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
	//					resultJsonObject.put("pdfURL",  pdfTable(eleRejectList, themeDisplay, resourceRequest));
						resultJsonObject.put("pdfURL",  "javascript:void(0)");
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
		String fileName = date.getTime() + uploadPortletRequest.getFileName("AnnexureFourAFile");

		File file = uploadPortletRequest.getFile("AnnexureFourAFile");

		String mimeType = uploadPortletRequest.getContentType("AnnexureFourAFile");

		String title = fileName;

		String description = "Weekly outstanding with Trustee Bank";

		long repositoryId = themeDisplay.getScopeGroupId();
		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getPrimaryKey();

		} catch (Exception e) {

			_log.info(e.getMessage());

			_log.error(e);
		}
		return 0;
	}
	

//		try {
//
//			Folder folder = getFolder(themeDisplay);
//
//			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
//					resourceRequest);
//
//			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description, "", file, serviceContext);
//			
//			if(Validator.isNotNull(fileEntry)) {
//				updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext);
////			addReportLog(themeDisplay.getUserId(), fileEntry.getFileEntryId());
//			}
//			
//		} catch (Exception e) {
//
//			_log.info(e.getMessage());
//
//			_log.error(e);
//		}
//	}

	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Weekly");
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
	
//	private void addReportLog(long createdBy, long fileEntryId) {
//		ReportMaster reportMaster = ReportMasterLocalServiceUtil.fetchReportMaster(ANNEXURE_FOUR_A_REPORT_ID);
//		
//		if(Validator.isNotNull(reportMaster)) {
//			CronSequenceGenerator generator = new CronSequenceGenerator(reportMaster.getDueDate());
//			Date nextRunDate = generator.next(new Date());
//			
//			ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndReportDate(reportMaster.getReportMasterId(), nextRunDate);
//			
//			if(Validator.isNotNull(reportUploadLog)) {
//				reportUploadLog.setCreateDate(new Date());
//				reportUploadLog.setCreatedBy(createdBy);
//				reportUploadLog.setFileEntryId(fileEntryId);
//				reportUploadLog.setUploaded(true);
//				ReportUploadLogLocalServiceUtil.updateReportUploadLog(reportUploadLog);
//			} else {
//				ReportUploadLogLocalServiceUtil.addReportUploadLog(reportMaster.getReportMasterId(), nextRunDate, new Date(), createdBy, fileEntryId, true);
//			}
//		}
//	}
	
	
	private static Log _log = LogFactoryUtil.getLog(SaveAnnexureFourAExcelData.class);
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}

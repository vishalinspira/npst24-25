package com.annexure.xiv.mvcresource;


import com.annexure.xiv.constants.AnnexureXivPortletKeys;
import com.daily.average.service.model.AnnexureXiv;
import com.daily.average.service.service.AnnexureXivLocalServiceUtil;
import com.daily.average.service.service.DailyAverageLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalServiceUtil;
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
import java.text.DateFormat;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

import nps.common.service.util.CommonParser;

@Component(property = { "javax.portlet.name=" + AnnexureXivPortletKeys.ANNEXUREXIV,
"mvc.command.name=/annexurexiv/save" }, service = MVCResourceCommand.class)
public class SaveAnnexureXiv implements MVCResourceCommand{
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("In serveResource");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
				
		try {
			resultJsonObject = fileUpload(themeDisplay, resourceRequest);
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		_log.info("in fileUpload 92");
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
		String fileName = uploadPortletRequest.getFileName("annexure14File");
		File file = uploadPortletRequest.getFile("annexure14File");
		String mimeType = uploadPortletRequest.getContentType("annexure14File");
		String title = fileName;
		JSONArray annexurexivJsonArray = JSONFactoryUtil.createJSONArray();
		List<AnnexureXiv> annexurexivs = new ArrayList<AnnexureXiv>();
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;
		
		//Create error excel
		 
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		XSSFCell rowNumCel = xxx.createCell(1);
		rowNumCel.setCellValue("RowNum");
		XSSFCell customerIdcell = xxx.createCell(2);
		customerIdcell.setCellValue("Sr. No");
		
		_log.info("in fileUpload 132");
		boolean isexcelhaserror = false;
		String sheetName = "Branch_List";
		
		AnnexureXivLocalServiceUtil.deleteAnnexureXivByReportUploadLogId(reportUploadLogId);
		
		try {
			_log.info("in fileUpload");
			if (file != null) {
				_log.info("in fileUpload1");
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("Branch_List");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					AnnexureXiv annexurexiv = AnnexureXivLocalServiceUtil.createAnnexureXiv(CounterLocalServiceUtil.increment(AnnexureXiv.class.getName()));
					
					annexurexiv.setReportUploadLogId(reportUploadLogId);
					annexurexiv.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 16; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.info("in fileUpload"+val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										try {
											Integer SNo=Integer.parseInt(val);
											annexurexiv.setSno(SNo);
										} catch (Exception e) {
											_log.info("error parsing integer"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									
									try {
										Integer branch_code=Integer.parseInt(val);
										annexurexiv.setBranch_code(branch_code);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 2) {
									
									try {
										Date date= cell.getDateCellValue();
										annexurexiv.setDate_of_opening(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 3) {
									annexurexiv.setBranch_name(val);
								} 
								else if (i == 4) {
									annexurexiv.setAddress(val);
								} 
								else if (i == 5) {
									annexurexiv.setDistrict(val);
								}
								else if (i == 6) {
									annexurexiv.setState_ut(val);
								}
								else if (i == 7) {
									
									annexurexiv.setCentre_asper_census2011(val);
								}
								else if (i == 8) {
									annexurexiv.setWhether_relocated(val);
								} 
								else if (i == 9) {
									annexurexiv.setUpgraded_relocation(val);
								} 
								else if (i == 10) {
									
									try {
										Date date= cell.getDateCellValue();
										annexurexiv.setRelocation_date(date);
									} catch (Exception e) {
										_log.info("error parsing date"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								else if (i == 11) {
									try {
										Integer uniformcode_i=Integer.parseInt(val);
										annexurexiv.setUniformcode_i(uniformcode_i);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 12) {
									try {
										Integer uniformcode_ii=Integer.parseInt(val);
										annexurexiv.setUniformcode_ii(uniformcode_ii);
									} catch (Exception e) {
										_log.info("error parsing integer"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								}
								else if (i == 13) {
									annexurexiv.setBoard_line_no(val);
								} 
								else if (i == 14) {
									annexurexiv.setFax_no(val);
								} 
								else if (i == 15) {
									try {
										Long Mobile_No=CommonParser.parseLong(val);
										annexurexiv.setMobile_no(Mobile_No);
									} catch (Exception e) {
										_log.info("error parsing long"+val);
										resultJsonObject.put("status", false);
										resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
										return resultJsonObject;
									}
								} 
								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					annexurexiv.setCreatedate(new Date());
						
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
							//JSONObject annexurexivJsonObject = JSONFactoryUtil.createJSONObject(annexurexiv.toString());
							//annexurexivJsonArray.put(annexurexivJsonObject);
							annexurexivs.add(annexurexiv);
						}
						rowCount++;
					}
				_log.info(sheetName +" rowcount"+rowCount);
				}
					
				}
			}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;
			}
			_log.info(annexurexivJsonArray.toString());
			
			if (!isexcelhaserror) {
				AnnexureXivLocalServiceUtil.addAnnexurexivs(annexurexivs);
				_log.info(sheetName +" datacount"+annexurexivs.size());
				try {
					errorExcel.close();
					long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
					
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
					//resultJsonObject.put("pdfURL",  pdfTable(annexurexivs, themeDisplay, resourceRequest));
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

			String fileName =date.getTime()+ uploadPortletRequest.getFileName("annexure14File");

			File file = uploadPortletRequest.getFile("annexure14File");

			String mimeType = uploadPortletRequest.getContentType("annexure14File");

			String title = fileName;

			String description = "annexure14File";

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
				folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
				_log.info(folder);

			} catch (Exception e) {

				_log.info(e.getMessage());
			}
			return folder;

		}
		
		public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
			Date createDate = new Date();
			ReportUploadLogSupervisorLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
		}
		
		Log _log = LogFactoryUtil.getLog(getClass());
		
	}


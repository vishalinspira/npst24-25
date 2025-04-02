package com.annexvi.mvcsource;


import com.annexvi.constants.AnnexVIPortletKeys;
import com.daily.average.service.model.AnnexVI;
import com.daily.average.service.service.AnnexVILocalServiceUtil;
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

@Component(property = { "javax.portlet.name=" + AnnexVIPortletKeys.ANNEXVI,
"mvc.command.name=/annexvi/save" }, service = MVCResourceCommand.class)
public class SaveAnnexVI implements MVCResourceCommand{
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
		if(reportMasterId == 7) {
			cra = "PCRA";
		} else if(reportMasterId == 20){
			cra = "KCRA";
		} else if(reportMasterId == 125){
			cra = "CCRA";
		}
		
		String fileName = uploadPortletRequest.getFileName("annexviFile");

		File file = uploadPortletRequest.getFile("annexviFile");

		String mimeType = uploadPortletRequest.getContentType("annexviFile");

		String title = fileName;
		
	

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(ValidateFileName.isValidfile(fileName)) {
		AnnexVILocalServiceUtil.deleteAnnexVIByReportUploadLogId(reportUploadLogId);
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			JSONArray annexviJsonArray = JSONFactoryUtil.createJSONArray();
			List<AnnexVI> annexvis = new ArrayList<AnnexVI>();
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
			customerIdcell.setCellValue("Sr. No");
			ArrayList <String> list = new ArrayList<String>();
		      list.add("All Citizen_UOS");
				list.add("Andhra Pradesh");
				list.add("Arunachal Pradesh");
				list.add("Assam");
				list.add("Atal Pension Yojana_APY");
				list.add("Bihar");
				list.add("CGAB");
				list.add("Central Govt");
				list.add("Chandigarh");
				list.add("Chhatisgarh");
				list.add("Corporate");
				list.add("D-Remit");
				list.add("Damodar_kolkata");
				list.add("ENPS");
				list.add("Goa");
				list.add("Gujarat");
				list.add("Haryana Govt");
				list.add("Himachal Pradesh");
				list.add("Jammu_Kashmir");
				list.add("Jharkhand");
				list.add("Karnataka");
				list.add("Kerala");
				list.add("Madhya Pradesh");
				list.add("Maharashtra");
				list.add("Manipur");
				list.add("Meghalaya");
				list.add("Mizoram");
				list.add("NACH-NSDL");
				list.add("NPS-Lite");
				list.add("Nagaland");
				list.add("Odisha");
				list.add("Omni Bus");
				list.add("Puducherry");
				list.add("Punjab");
				list.add("Rajasthan");
				list.add("Rejection Parking");
				list.add("Sikkim");
				//list.add("Summary_consolidate");
				list.add("Tamil Nadu");
				list.add("Telangana");
				list.add("Tripura");
				list.add("Uttar Pradesh");
				list.add("Uttarakhand");
				list.add("West Bengal");
				
			boolean isexcelhaserror = false;
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					int sheetcount = 0;
					Iterator<Sheet> sheets1 = workbook.sheetIterator();
					
					while(sheets1.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets1.next();
						if(list.contains(sheet.getSheetName())) {
							list.remove(sheet.getSheetName());
						}
					}
					
					if (list.size()>0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray(list);
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
						
					} else {
						Iterator<Sheet> sheets = workbook.sheetIterator();
						while(sheets.hasNext())
						{
							XSSFSheet sheet = (XSSFSheet) sheets.next();
							//XSSFSheet sheet1 = workbook.getSheet("Summary_consolidate");
							String sheetName = sheet.getSheetName();
							
							if(sheetcount>0) {
								Iterator<Row> rows = sheet.rowIterator();
								int rowCount = 1;
								int errorRowCount = 2;
								rowLoop:
								while (rows.hasNext()) {
									
									AnnexVI annexvi = AnnexVILocalServiceUtil.createAnnexVI(CounterLocalServiceUtil.increment(AnnexVI.class.getName()));
									
									annexvi.setReportUploadLogId(reportUploadLogId);
									annexvi.setCreatedby(userId);
									annexvi.setCra(cra);
									
									JSONObject errorObj = JSONFactoryUtil.createJSONObject();
									errorObj.put("rownum", rowCount);
									boolean isError = false;
									XSSFRow row = (XSSFRow) rows.next();
									XSSFRow errorRow = null;
									
									for (int i = 0; i < 9; i++) {
										XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
										if (cell != null) {
				
											DataFormatter formatter = new DataFormatter();
											
											String val = formatter.formatCellValue(cell);
											if (val != null)
												val = val.trim();
											if(rowCount > 1) {
												if (i == 0) {
													if (val != null && Validator.isNotNull(val)) {
														try {
															Date date= cell.getDateCellValue();
															val = val.replace("//", "-");
															annexvi.setDate_(date);
														} catch (Exception e) {
															_log.info("error parsing date"+val);
															resultJsonObject.put("status", false);
															resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
															return resultJsonObject;
															
															
														}
													} else {
														errorObj.put("cellno", 2);
														errorObj.put("msg", "it is empty");
														
														isError = true;
													}
												}
												else if (i == 1 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);
														annexvi.setPao_funds_received(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												}
												else if (i == 2 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);
														annexvi.setWrong_credit_return(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 3 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);
														annexvi.setReceived_cumulative_funds(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 4 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);
														annexvi.setPfm_funds_remitted(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 5 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);
														annexvi.setPfm_cumulative_funds_remitted(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												}
												else if (i == 6 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);
														annexvi.setClosing_balance(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												}
												else if (i == 7 && Validator.isNotNull(val)) {
													try {
														BigDecimal parsedStringValue = CommonParser.parseBigDecimal(val);											
														annexvi.setUncleared_funds(parsedStringValue);
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												}
												else if (i == 8 && Validator.isNotNull(val)) {
													try {
														Long nps_acc_number =CommonParser.parseLong(val);											
														annexvi.setNps_acc_number(nps_acc_number);
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
									annexvi.setCreatedate(new Date());
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
										//JSONObject annexurexivJsonObject = JSONFactoryUtil.createJSONObject(annexvi.toString());
										//annexviJsonArray.put(annexurexivJsonObject);
										annexvis.add(annexvi);
									}
									rowCount++;
								}
								_log.info(sheetName +" rowcount"+rowCount);
							}
							sheetcount++;
						}
					}
				}
			}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				 return resultJsonObject;
			}
			//_log.info(annexviJsonArray.toString());
			
			if (!isexcelhaserror) {
				try {
					AnnexVILocalServiceUtil.addannexVIs(annexvis);
					_log.info("annexvis" +" datacount"+annexvis.size());
				} catch (Exception e1) {
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					 return resultJsonObject;
				}
				
				uploadFILETOFOLDER(themeDisplay, resourceRequest);
				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				
				try {
					errorExcel.close();
					
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
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
		}
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
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

		String fileName =date.getTime()+ uploadPortletRequest.getFileName("annexviFile");

		File file = uploadPortletRequest.getFile("annexviFile");

		String mimeType = uploadPortletRequest.getContentType("annexviFile");

		String title = fileName;

		String description = "Weekly Consolidated Collection Report";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry  fileEntry=DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getPrimaryKey();
			
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Weekly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;
	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);
	}
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}


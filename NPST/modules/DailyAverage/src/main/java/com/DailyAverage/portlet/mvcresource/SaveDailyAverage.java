package com.DailyAverage.portlet.mvcresource;

import com.DailyAverage.constants.DailyAveragePortletKeys;
import com.DailyAverage.model.DailyAverageXMLModel;
import com.daily.average.service.model.DailyAverage;
import com.daily.average.service.service.DailyAverageLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import nps.email.api.api.ExcelValidationApi;

@Component(property = { "javax.portlet.name=" + DailyAveragePortletKeys.DAILYAVERAGE,
		"mvc.command.name=/dailyaverage/save" }, service = MVCResourceCommand.class)
public class SaveDailyAverage extends BaseMVCResourceCommand{
	private final Log _log = LogFactoryUtil.getLog(SaveDailyAverage.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("in SaveDailyAverage *********************");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);

		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			_log.error("Exception: "+e.getMessage());
		}
		
	}
	
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error("Exception on getting servicecontext : "+e.getMessage(),e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);

		String fileName = uploadPortletRequest.getFileName("dailyAverageFile");

		File file = uploadPortletRequest.getFile("dailyAverageFile");

		String mimeType = uploadPortletRequest.getContentType("dailyAverageFile");

		String title = fileName;

		JSONArray dailyAveragesJsonArray = JSONFactoryUtil.createJSONArray();
		List<DailyAverage> dailyAverages2 = new ArrayList<DailyAverage>();
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;
		
		List<DailyAverageXMLModel> dailyAverageXMLModels = new ArrayList<DailyAverageXMLModel>();

		/**
		 * Create error excel
		 */
		/*XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		XSSFCell rowNumCel = xxx.createCell(1);
		rowNumCel.setCellValue("RowNum");
		XSSFCell customerIdcell = xxx.createCell(2);
		customerIdcell.setCellValue("customerId");
		XSSFCell NPSNumber = xxx.createCell(3);
		NPSNumber.setCellValue("NPSNumber");
		XSSFCell NPSAcName = xxx.createCell(4);
		NPSAcName.setCellValue("NPSAcName");*/
		DailyAverageLocalServiceUtil.deleteDailyAverageByReportUploadLogId(reportUploadLogId);
		
		resultJsonObject = excelValidationApi.validateExcelFile(resourceRequest, "dailyAverageFile");
		boolean isexcelhaserror = false;
		String sheetName = "All AC";
		
		
		if(resultJsonObject.getBoolean("status")) {
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					_log.info("report sheet"+sheet);
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("All AC");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {

					
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						rowLoop:
						while (rows.hasNext()) {
		
							DailyAverage dailyAverage = DailyAverageLocalServiceUtil
									.createDailyAverage(CounterLocalServiceUtil.increment(DailyAverage.class.getName()));
							
							dailyAverage.setReportUploadLogId(reportUploadLogId);
							dailyAverage.setCreatedby(userId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							
							for (int i = 0; i < 15; i++) {
								XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
		
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
									if (i == 0) {
		
										if (val != null && val.length() == 9) {
		
											try {
												Long CUST_ID = CommonParser.parseLong(val);//Long.parseLong(val);
												if (CUST_ID != null) {
													dailyAverage.setCust_id(CUST_ID);
												} else {
													errorObj.put("cellno", 2);
													errorObj.put("msg", "is not a number");
													isError = true;

												}
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "length error");
											
											isError = true;
										}
									} else if (i == 1) {
		
										if (val != null && val.length() == 15) {
		
											try {
													Long NPS_Acc_No = CommonParser.parseLong(val);
													dailyAverage.setNps_acc_number(NPS_Acc_No);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else {
											errorObj.put("cellno", 3);
											errorObj.put("msg", "length error");
											
											isError = true;
										}
									} else if (i == 2) {
		
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											dailyAverage.setNps_acc_name(val);
										} else {
											errorObj.put("cellno", 4);
											errorObj.put("msg", "it is empty");
											
											isError = true;
										}
		
									} else if (i == 3) {
										try {
											Date date= cell.getDateCellValue();
											dailyAverage.setTran_date(date);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} else if (i == 4) {
		
										dailyAverage.setTran_id(val);
										
									} else if (i == 5) {
										if (Validator.isNotNull(val) && Validator.isDigit(val)) {
											try {
												Integer part_tran_sno_psrl = Integer.parseInt(val);
												dailyAverage.setPart_tran_sno_psrl(part_tran_sno_psrl);
											} catch (Exception e) {
												_log.info("error parsing integer"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
											
										}
									} else if (i == 6) {
										if (Validator.isNotNull(val) && Validator.isDigit(val)) {
											try {
												Long Chq_No = CommonParser.parseLong(val);
												dailyAverage.setChq_no(Chq_No);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
									} else if (i == 7 && Validator.isNotNull(val)) {
										try {
											Date date= cell.getDateCellValue();
											dailyAverage.setChq_date(date);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} else if (i == 8) {
										try {
											Double Debit_Trans = Double.parseDouble(val);
											dailyAverage.setDebit_trans(Debit_Trans);
										} catch (Exception e) {
											_log.info("error parsing double"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} else if (i == 9) {
										try {
											Double Credit_Trans = Double.parseDouble(val);
											dailyAverage.setCredit_trans(Credit_Trans);
										} catch (Exception e) {
											_log.info("error parsing double"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} else if (i == 10) {
										try {
											Double Running_Bal = Double.parseDouble(val);
											dailyAverage.setRunning_bal(Running_Bal);
										} catch (Exception e) {
											_log.info("error parsing double"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									} else if (i == 11) {
										dailyAverage.setTrans_particulars(val);
										
									} else if (i == 12) {
										dailyAverage.setTrans_remarks(val);
										
									} else if (i == 13) {
										if (Validator.isNotNull(val) && Validator.isDigit(val)) {
											try {
												Integer sol_id = Integer.parseInt(val);
												dailyAverage.setSol_id(sol_id);
											} catch (Exception e) {
												_log.info("error parsing integer"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
											
											
										}
									} else if (i == 14) {
										if (Validator.isNotNull(val) && Validator.isDigit(val)) {
											try {
												Long CP_Acc_No = CommonParser.parseLong(val);
												dailyAverage.setCounter_party_acc_number(CP_Acc_No);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
									}
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							dailyAverage.setCreateDate(new Date());
							
							if (isError && rowCount > 1) {
								isexcelhaserror = true;
							} else if (rowCount > 1) {
								dailyAverages2.add(dailyAverage);
							}
							rowCount++;
						}
						_log.info("ALL AC data count"+rowCount);
						
						}
					}
				
				}catch (Exception e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;
				} finally {
					try {
						workbook.close();
					} catch (Exception e) {
						 _log.error(e);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
						 return resultJsonObject;
					}
				}
			
			if (!isexcelhaserror) {
				try {
					DailyAverageLocalServiceUtil.addDailyAverages(dailyAverages2);
					_log.info("ALL AC data count"+dailyAverages2.size());
				} catch (Exception e1) {
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					 return resultJsonObject;
				}
				long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				
				try {
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
				} catch (Exception e) {
					 _log.error(e);
					 resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					 return resultJsonObject;
				}
				resultJsonObject.put("status", true);
				
			} else {
				resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				resultJsonObject.put("status", false);
			}
		}
		return resultJsonObject;

	}
	
	
	/*@SuppressWarnings("deprecation")
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
	}*/
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+uploadPortletRequest.getFileName("dailyAverageFile");

		File file = uploadPortletRequest.getFile("dailyAverageFile");

		String mimeType = uploadPortletRequest.getContentType("dailyAverageFile");

		String title = fileName;

		String description = "Daily Account Statement";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();

		} catch (Exception e) {
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
	/*public void pojoToXML(DailyAverageList dailyAverageList) throws JAXBException, IOException {	
		JAXBContext contextObj = JAXBContext.newInstance(DailyAverageList.class);  
		Marshaller marshallerObj = contextObj.createMarshaller();  
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    OutputStream os =  new FileOutputStream("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\dailyAverageList.xml");
	    marshallerObj.marshal(dailyAverageList, os);
	    os.close();
	}*/
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, 
			String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, 
				reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
		ReportUploadLogLocalServiceUtil.updateReportUploadLog(1, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationApi excelValidationApi;
}

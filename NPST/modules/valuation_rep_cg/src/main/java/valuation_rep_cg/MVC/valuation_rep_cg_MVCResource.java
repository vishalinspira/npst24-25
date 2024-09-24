package valuation_rep_cg.MVC;

import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.valuationCg;
import compliance.service.service.valuationCgLocalServiceUtil;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;
import valuation_rep_cg.constants.Valuation_rep_cgPortletKeys;

@Component(property = { 
		"javax.portlet.name=" +  Valuation_rep_cgPortletKeys.VALUATION_REP_CG,
		"mvc.command.name=" +  Valuation_rep_cgPortletKeys.saveValuation_rap_cg, 
}, 
service = MVCResourceCommand.class)

public class valuation_rep_cg_MVCResource implements MVCResourceCommand {
	private static Log _log = LogFactoryUtil.getLog(valuation_rep_cg_MVCResource.class);
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("resources:::::::::::::::::::");
		_log.info("Serve Resource method:::::::::::::::::::::::");
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
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("valuation_rep_cg");

		File file = uploadPortletRequest.getFile("valuation_rep_cg");

		String mimeType = uploadPortletRequest.getContentType("valuation_rep_cg");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		valuationCgLocalServiceUtil.deletevaluationCgByReportUploadLogId(reportUploadLogId);
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
			
			JSONArray ValuationJsonArray = JSONFactoryUtil.createJSONArray();
			List<valuationCg> valuationCgdata= new ArrayList<valuationCg>();
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
			fileNamecell.setCellValue("Client Code");
			
			
			boolean isexcelhaserror = false;
			String sheetName = "valuation_cg";
			try {
				if (file != null) {
					
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("valuation_cg");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowloop:
					while (rows.hasNext()) {
						
						valuationCg valuation=valuationCgLocalServiceUtil.createvaluationCg(CounterLocalServiceUtil.increment(valuationCg.class.getName()));
								
						valuation.setReportUploadLogId(reportUploadLogId);
						valuation.setCreatedby(userId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
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
									//_log.info("Val: " + val);
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											//Long srno=Long.parseLong(val);
											valuation.setClient_code(val);
										}
										 else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "It is not a number");
												
												isError = true;
											}
									}
									else if (i == 1) {
										
										valuation.setIsin(val);
									}
									else if (i == 2) {
										valuation.setIsin_desc(val);
									} 
									else if (i == 3) {
										Date date_1 = null;
										try {
											date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
										valuation.setMaturity_date(date_1);
									} 
									else if (i == 4) {
										try {
											long lValue = CommonParser.parseLong(val);
											valuation.setFree_securities_rs(lValue);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										try {
											long lValue = CommonParser.parseLong(val);
											valuation.setPledge_value_held_rs(lValue);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 6) {
										try {
											long lValue = CommonParser.parseLong(val);
											valuation.setSecurities_in_transit_rs(lValue);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
										
									}
									else if (i == 7) {
										try {
											long lValue = CommonParser.parseLong(val);
											valuation.setTotal_face_value_held_rs(lValue);
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
	                                 else if (i == 8) {
	                                	try {
	                                	 BigDecimal bg = CommonParser.parseBigDecimal(val);
	                                	 valuation.setRate(bg.stripTrailingZeros());
	                                 	} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 9) {
										try {
		                                	 BigDecimal bg = CommonParser.parseBigDecimal(val);
		                                	 valuation.setValue_(bg.stripTrailingZeros());
										} catch (Exception e) {
											_log.info("error parsing long"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
		                                	 
									} else if (i == 10) {
										Date date_1 = null;
										try {
											date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
										valuation.setReportingdate(date_1);
									} 
								}
							} else if(i == 0 && rowCount > 1) {
								break rowloop;
							}
						}
						valuation.setCreatedon(new Date());
							
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
								//JSONObject  ValuationJsonArray1 = JSONFactoryUtil.createJSONObject(valuation.toString());
								//ValuationJsonArray.put(ValuationJsonArray1);
								valuationCgdata.add(valuation);
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
				_log.info(ValuationJsonArray.toString());
				
				if (!isexcelhaserror) {
					valuationCgLocalServiceUtil.addValuationCg(valuationCgdata);
					_log.info(sheetName +" datacount"+valuationCgdata.size());
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
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
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,ResourceRequest resourceRequest) {

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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("valuation_rep_cg");

		File file = uploadPortletRequest.getFile("valuation_rep_cg");

		String mimeType = uploadPortletRequest.getContentType("valuation_rep_cg");

		String title = fileName;

		String description = "valuation_rep_cg";

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
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}
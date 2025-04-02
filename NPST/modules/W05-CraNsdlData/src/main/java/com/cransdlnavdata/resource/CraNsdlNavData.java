package com.cransdlnavdata.resource;

import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalServiceUtil;
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

import W05.CraNsdlData.constants.W05CraNsdlDataPortletKeys;
import compliance.service.model.Nsdlnav;
import compliance.service.service.NsdlnavLocalServiceUtil;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + W05CraNsdlDataPortletKeys.W05CRANSDLDATA,
		"mvc.command.name=" + W05CraNsdlDataPortletKeys.craNavData, 
		}, 
service = MVCResourceCommand.class)

public class CraNsdlNavData implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(CraNsdlNavData.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Inside serve Resource method");
		
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

	private boolean validateSheetName(File file) {
		List<String> sheetNames=new ArrayList<String>();
		sheetNames.add("Weekly_NAV");
		try {
			if(Validator.isNotNull(file)) {
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while (sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					if(sheetNames.contains(sheet.getSheetName())) {
						return true;
					}
				}
			}
			}catch (Exception e) {
				_log.info(e.getMessage());
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
				String cra = IntermediaryListLocalServiceUtil.fetchIntermediaryByReportLogId(reportUploadLogId);
				String fileName = uploadPortletRequest.getFileName("craDataFile");
				File file = uploadPortletRequest.getFile("craDataFile");
				

				String mimeType = uploadPortletRequest.getContentType("craDataFile");

				String title = fileName;
				
				JSONArray nsdlJsonArray = JSONFactoryUtil.createJSONArray();
				List<Nsdlnav> nsdlList = new ArrayList<Nsdlnav>();
				JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
				JSONArray errorArray = JSONFactoryUtil.createJSONArray();
				XSSFWorkbook workbook = null;
				if(ValidateFileName.isValidfile(fileName)) {
				if(validateSheetName(file)) {
				NsdlnavLocalServiceUtil.deleteNsdlnavByReportUploadLogId(reportUploadLogId);
				
				resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
				if(resultJsonObject.getBoolean("status")) {
				
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
					try {
						if (file != null) {
							OPCPackage pkg = OPCPackage.open(file);
							workbook = new XSSFWorkbook(pkg);
							
							XSSFSheet sheet = workbook.getSheet("Weekly_NAV");
							String sheetName = sheet.getSheetName();
							if (sheet == null) {
								resultJsonObject.put("status", false);
								resultJsonObject.put("sheeterror",true);
								JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
								errorSheetNameList.put("Weekly_NAV");
								resultJsonObject.put("errorSheetNameList", errorSheetNameList);
								return resultJsonObject;
							} else {
							
								Iterator<Row> rows = sheet.rowIterator();
								int rowCount = 1;
								int errorRowCount = 2;
								
								rowloop:
								while (rows.hasNext()) {
									
									Nsdlnav nsdlNavData = NsdlnavLocalServiceUtil.createNsdlnav(CounterLocalServiceUtil.increment(Nsdlnav.class.getName()));
											
									nsdlNavData.setReportUploadLogId(reportUploadLogId);
									nsdlNavData.setCreatedby(userId);
									nsdlNavData.setCra(cra);
									
									JSONObject errorObj = JSONFactoryUtil.createJSONObject();
									errorObj.put("rownum", rowCount);
									boolean isError = false;
									XSSFRow row = (XSSFRow) rows.next();
									XSSFRow errorRow = null;
									
									for (int i = 0; i < 16; i++) {
										XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
										if (cell != null) {
		
											DataFormatter formatter = new DataFormatter();
											
											String val = formatter.formatCellValue(cell);
											if (val != null)
												val = val.trim();
											if(rowCount > 1) {
												_log.info("Val: " + val);
												
												boolean isValid = (val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("- 0"));
												
												if (i == 0) {
													if (val != null && Validator.isNotNull(val) && val.length() > 0) {
														try {
															int iValue = Integer.parseInt(val);
															nsdlNavData.setSr_no(iValue);
														} catch (Exception e) {
															 _log.error(e);
															 _log.info("error parsing long"+val);
																resultJsonObject.put("status", false);
																resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
																return resultJsonObject;
														}
													}
													 else {
															errorObj.put("cellno", 2);
															errorObj.put("msg", "It is not a number");
															
															isError = true;
														}
												}
												else if (i == 1) {
													try {
														Date date_1 = cell.getDateCellValue();
														nsdlNavData.setDate_of_latest_nav(date_1);
													} catch (Exception e) {
														_log.info("error parsing date"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
														return resultJsonObject;
													}
												}
												else if (i == 2) {
													nsdlNavData.setPension_fund_code(val);
												} 
												else if (i == 3) {
													nsdlNavData.setPension_fund_name(val);
												} 
												else if (i == 4) {
													nsdlNavData.setScheme_code(val);
												} 
												else if (i == 5) {
													nsdlNavData.setScheme_name(val);
												}
												else if (i == 6) {
													nsdlNavData.setSector(val);
												}
												else if (i == 7) {
													try {
														long value = CommonParser.parseBigDecimal(val).longValue();
														nsdlNavData.setNo_of_subscriber(value);
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
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														 nsdlNavData.setAum(bg.stripTrailingZeros());
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
														nsdlNavData.setLastest_nav(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 10) {
													try {
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														nsdlNavData.setNav_prior_1_year(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
													
												} 
												else if (i == 11) {
													try {
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														nsdlNavData.setNav_prior_2_year(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 12) {
													try {
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														nsdlNavData.setNav_prior_3_year(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 13) {
													try {
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														nsdlNavData.setNav_prior_5_year(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 14) {
													try {
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														nsdlNavData.setNav_prior_7_year(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												else if (i == 15) {
													try {
														BigDecimal bg = CommonParser.parseBigDecimal(val);
														nsdlNavData.setNav_prior_10_year(bg.stripTrailingZeros());
													} catch (Exception e) {
														_log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
													}
												} 
												
											}
										} else if(i == 0 && rowCount > 1) {
											break rowloop;
										}
									}
									nsdlNavData.setCreatedon(new Date());
										
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
											//JSONObject nsdlJsonObject = JSONFactoryUtil.createJSONObject(nsdlNavData.toString());
											//nsdlJsonArray.put(nsdlJsonObject);
											nsdlList.add(nsdlNavData);
										}
										rowCount++;
									}
								}
							}
						}catch (Exception e) {
							 _log.error(e);
						}
						_log.info(nsdlJsonArray.toString());
						
						if (!isexcelhaserror) {
							try {
								NsdlnavLocalServiceUtil.addNsdlnav(nsdlList);
							} catch (Exception e1) {
								_log.error(e1);
								resultJsonObject.put("status", false);
								resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
								return resultJsonObject;
							}
							
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
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg",  "Sheet name is incorrect.");
			return resultJsonObject;
		}
				}else {
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg","Please upload files with a valid filename.");
					return resultJsonObject;
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

				String fileName = date.getTime() + uploadPortletRequest.getFileName("craDataFile");

				File file = uploadPortletRequest.getFile("craDataFile");

				String mimeType = uploadPortletRequest.getContentType("craDataFile");

				String title = fileName;

				String description = "craDataFile";

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
				ReportUploadLogPFMCRALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
			}
			
			@Reference
			ExcelValidationAn10Api excelValidationAn10Api;
			
			
}

package com.weekly.repononeq.resource;

import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import Weekly.Repo.Non.Eq.constants.WeeklyRepoNonEqPortletKeys;
import compliance.service.model.NonEq;
import compliance.service.service.NonEqLocalServiceUtil;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + WeeklyRepoNonEqPortletKeys.WEEKLYREPONONEQ,
		"mvc.command.name=" + WeeklyRepoNonEqPortletKeys.nonEq, 
		}, 
service = MVCResourceCommand.class)

public class RepoNonEq implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(RepoNonEq.class);
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
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
		
		String fileName = uploadPortletRequest.getFileName("nonEqFile");

		File file = uploadPortletRequest.getFile("nonEqFile");
		
		String mimeType = uploadPortletRequest.getContentType("nonEqFile");

		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
			//ETier_1 et1 = new ETier_1();
			
			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell fileNamecell = xxx.createCell(2);
			fileNamecell.setCellValue("Pension Fund");
			
			
			boolean isexcelhaserror = false;
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet("NONEQ");
					//String sheetName = sheet.getSheetName();
					//Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					int sheetCount = 0;
					_log.info("sheet"+sheet);
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("NONEQ");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {
					
					//Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
					//_log.info(sheetIterator.hasNext());
					
					//while(sheetIterator.hasNext()) {
						NonEqLocalServiceUtil.deleteNonEqByReportUploadLogId(reportUploadLogId);
						_log.info(sheet.getSheetName());
						Iterator<Row> rows = sheet.iterator();
						//_log.info(rows.next().getCell(1));
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						//XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						if(sheetCount == 0 && sheet != null) {
							
							repoNonEq(themeDisplay, rows, resourceRequest, reportUploadLogId);
							//ETier_1 et1 = new ETier_1();
							//et1.schemeETeir_1(themeDisplay, rows, resourceRequest);
							//_log.info("et1" + et1.schemeETeir_1(themeDisplay, rows, resourceRequest));
							//et1.printInfo();
						} 
						
						if (isError && rowCount > 0) {
							errorArray.put(errorObj);
							errorRow = xx.createRow(errorRowCount);
							errorRowCount++;
							XSSFCell rowCountCel = errorRow.createCell(1);
							rowCountCel.setCellValue(rowCount);
							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
							cellError.setCellValue(errorObj.getString("msg"));
							isexcelhaserror = true;
						}
						sheetCount++;
					}
						
					}
				}catch (Exception e) {
					 _log.error(e);
				}
	
			
				if (!isexcelhaserror) {
					
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
	
	public JSONObject repoNonEq(ThemeDisplay themeDisplay, Iterator<Row> rows, ResourceRequest resourceRequest, long reportUploadLogId) throws InvalidFormatException, IOException, JSONException, ParseException {
		
		JSONObject nonEqJsonObject = null;
		JSONArray nonEqJsonArray = JSONFactoryUtil.createJSONArray();
		List<NonEq> nonEqList = new ArrayList<NonEq>();
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern,symbols);
		decimalFormat.setParseBigDecimal(true);
		
		int rowCount = 1;
		//int errorRowCount = 2;
		
			rowloop:
			while (rows.hasNext()) {
				
				NonEq repoNonEq = (NonEq) NonEqLocalServiceUtil.
						createNonEq(CounterLocalServiceUtil.increment(NonEq.class.getName()));
				
				repoNonEq.setReportUploadLogId(reportUploadLogId);
				repoNonEq.setCreatedby(themeDisplay.getUserId());
				
				XSSFRow row = (XSSFRow) rows.next();
				
				int lastColumn = Math.max(row.getLastCellNum(), 0);
				
				for (int i = 0; i < lastColumn; i++) {
					
					XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					
					if (cell != null) {

						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						
						if (val != null)
							val = val.trim();
						if(rowCount > 0) {
							//_log.info("rowCount " + rowCount);
							//_log.info("Val: " + val);
							boolean isVal = (val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase(""));
							//Date date = new SimpleDateFormat("dd-MMM-yy").parse(val);
							
							if (i == 0) {
								if (val != null && Validator.isNotNull(val) && val.length() > 0) {
									try {
										repoNonEq.setDate_(cell.getDateCellValue());
									} catch (Exception e) {
										_log.info("error parsing date--->"+val);
										nonEqJsonObject.put("status", false);
										nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
										return nonEqJsonObject;
									}
									
								}
							}
							else if (i == 1) {
								//_log.info("cell2 " + cell.getCellType());
								repoNonEq.setFin(val);
							}
							else if (i == 2) {
								repoNonEq.setScheme(val);
							} 
							else if (i == 3) {
								repoNonEq.setCompany_name(val);
							} 
							else if (i == 4) {
								repoNonEq.setSecurity_code(val);
							} 
							else if (i == 5) {
								repoNonEq.setIsin(val);
							}
							else if (i == 6) {
								repoNonEq.setSecdesc(val);
							}
							else if (i == 7) {
								repoNonEq.setSec(val);
							}
							else if (i == 8) {
								repoNonEq.setCoupon(val);
							} 
							else if (i == 9) {
								BigDecimal bg=null;
								try {
									bg = CommonParser.parseBigDecimal(val); 
									repoNonEq.setFaceval(bg.stripTrailingZeros());
								} catch (Exception e) {
									 _log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 10) {
								long lValue=0;
								try {
									lValue = CommonParser.parseLong(val);
									repoNonEq.setCusthold(lValue);
								} catch (NumberFormatException e) {
									 _log.info("error in parsing long--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 11) {
								long lValue =0l;
								try {
									lValue = CommonParser.parseLong(val);
									repoNonEq.setTransit(lValue);
								} catch (NumberFormatException e) {
									 _log.info("error in parsing long--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 12) {
								//_log.info("rowCount " + rowCount);
								try {
									long lValue = CommonParser.parseLong(val);
									repoNonEq.setLoghold(lValue);
								} catch (Exception e) {
									_log.info("error in parsing long--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 13) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setPrice(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 14) {
								repoNonEq.setSource_(val);
							} 
							else if (i == 15) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setValue_(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 16) {
								try {
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_2 = CommonParser.parseDate(val, cell);
										repoNonEq.setTradedate(date_2);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
								}
							} 
							else if (i == 17) {
								repoNonEq.setMethod_(val);
							} 
							else if (i == 18) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setGross_price(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 19) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setAccured_interest(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 20) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setModified_duration(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 21) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setYtm(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 22) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setNse_price(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 23) {
								try {
									BigDecimal bg = CommonParser.parseBigDecimal(val);
									repoNonEq.setTenor_in_years(bg.stripTrailingZeros());
								} catch (Exception e) {
									_log.info("error in parsing big decimal--->"+val);
									 nonEqJsonObject.put("status", false);
									 nonEqJsonObject.put("msg", CommonParser.numberExceptionMsg);
									 return nonEqJsonObject;
								}
							} 
							else if (i == 24) {
								repoNonEq.setValue_of_security(val);
							} 
							else if (i == 25) {
								repoNonEq.setIssuerclass(val);
							} 
							else if (i == 26) {
								repoNonEq.setCoupontype(val);
							} 
							else if (i == 27) {
								int iValue = Integer.parseInt(val);
								repoNonEq.setCouponpaymentfrequency(iValue);
							} 
							else if (i == 28) {
								try {
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_3 = CommonParser.parseDate(val, cell);
										repoNonEq.setAllotmentdate(date_3);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
								}
							} 
							else if (i == 29) {
								try {
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_4 = CommonParser.parseDate(val, cell);
										repoNonEq.setFirstipdate(date_4);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
								}
							} 
							else if (i == 30) {
								if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
									repoNonEq.setTradedate(null);
								}
								else {
									Date date_5=null;
									try {
										date_5 = CommonParser.parseDate(val, cell);
									} catch (Exception e) {
										_log.info("error parsing date--->"+val);
										nonEqJsonObject.put("status", false);
										nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									}
									repoNonEq.setLastipdate(date_5);
								}
							} 
							else if (i == 31) {
								try {
									if(val.equalsIgnoreCase("-") ||val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_6 = CommonParser.parseDate(val, cell);
										repoNonEq.setFinalredemptiondate(date_6);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									return nonEqJsonObject;
								}
							} 
							else if (i == 32) {
								repoNonEq.setRedemppremium(val);
							} 
							else if (i == 33) {
								repoNonEq.setRating(val);
							} 
							else if (i == 34) {
								repoNonEq.setIlliquiditydiscount(val);
							} 
							else if (i == 35) {
								repoNonEq.setHigherrating(val);
							} 
							else if (i == 36) {
								repoNonEq.setPtc_amort_method(val);
							} 
							else if (i == 37) {
								repoNonEq.setCoupon_form(val);
							} 
							else if (i == 38) {
								repoNonEq.setCompounding_frequency(val);
							} 
							else if (i == 39) {
								repoNonEq.setMatrix_type(val);
							} 
							else if (i == 40) {
								repoNonEq.setSector(val);
							} 
							else if (i == 41) {
								try {
									if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_7 = CommonParser.parseDate(val, cell);
										repoNonEq.setCall_date(date_7);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									return nonEqJsonObject;
								}
							} 
							else if (i == 42) {
								try {
									if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_8 = CommonParser.parseDate(val, cell);
										repoNonEq.setPut_date(date_8);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									return nonEqJsonObject;
								}
							} 
							else if (i == 43) {
								try {
									if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_9 = CommonParser.parseDate(val, cell);
										repoNonEq.setSettlement_date(date_9);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									return nonEqJsonObject;
								}
							} 
							else if (i == 44) {
								try {
									if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_10 = CommonParser.parseDate(val, cell);
										repoNonEq.setBenchmark_date(date_10);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									return nonEqJsonObject;
								}
							} 
							else if (i == 45) {
								try {
									if(val.equalsIgnoreCase("-") || val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("")) {
										repoNonEq.setTradedate(null);
									}
									else {
										Date date_11 = CommonParser.parseDate(val, cell);
										repoNonEq.setConversion_date(date_11);
									}
								} catch (Exception e) {
									_log.info("error parsing date--->"+val);
									nonEqJsonObject.put("status", false);
									nonEqJsonObject.put("msg", CommonParser.dateExceptionMsg);
									return nonEqJsonObject;
								}
							} 
							else if (i == 46) {
								repoNonEq.setGoi_serviced(val);
							} 
							else if (i == 47) {
								repoNonEq.setSecured_unsecured(val);
							} 
							
						}
					} else if(i == 0 && rowCount > 0) {
						//_log.info("outside if");
						break rowloop;
					}
				}
				repoNonEq.setCreatedon(new Date());
				
				if (rowCount > 0) {
					nonEqList.add(repoNonEq);
				}
				rowCount++;

			}
		_log.info("NONEQ" +" rowcount"+rowCount);
		
		try {
			NonEqLocalServiceUtil.addNonEq(nonEqList);
			_log.info("nonEq datacount"+nonEqList.size());
			//eTierIJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
		} catch(Exception e) {
			 _log.error(e);
		}
		
		//_log.info(nonEqJsonArray.toString());
		
		return nonEqJsonObject;
		
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("nonEqFile");

		File file = uploadPortletRequest.getFile("nonEqFile");

		String mimeType = uploadPortletRequest.getContentType("nonEqFile");

		String title = fileName;

		String description = "nonEqFile";

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
	
	public static String formatDate(Date dateIn, String format) throws ParseException {
        String strDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(dateIn);
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d1 = sdf3.parse(formattedDate);

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(d1);
        } catch (Exception e) {
             _log.error(e);
        }
        return strDate;
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
	
	/*
	 * @Reference NonEqLocalService nonEqLocalService;
	 */
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

}

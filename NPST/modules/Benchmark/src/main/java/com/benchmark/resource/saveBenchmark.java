package com.benchmark.resource;

import com.benchmark.constants.BenchmarkPortletKeys;
import com.daily.average.service.model.BenchSchemeReturns;
import com.daily.average.service.model.BenchSchemeReturnsSince;
import com.daily.average.service.service.AnnexureXivLocalServiceUtil;
import com.daily.average.service.service.BenchSchemeReturnsLocalServiceUtil;
import com.daily.average.service.service.BenchSchemeReturnsSinceLocalServiceUtil;
import com.daily.average.service.service.BenchSchemeReturnsSinceMonthlyLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogNPSTLocalServiceUtil;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
			"javax.portlet.name="+BenchmarkPortletKeys.BENCHMARK,
			"mvc.command.name=/saveBenchmark/data",
		}, 
		service = MVCResourceCommand.class
	)
public class saveBenchmark implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("hi ");
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
		
		String fileName = uploadPortletRequest.getFileName("BenchmarkFile");
		_log.info("file name--->"+fileName);
		
		File file = uploadPortletRequest.getFile("BenchmarkFile");

		String mimeType = uploadPortletRequest.getContentType("BenchmarkFile");
		_log.info("file type--->"+mimeType);

		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		_log.info("<---deleting report upload id--->");
		BenchSchemeReturnsLocalServiceUtil.deleteBenchSchemeReturnsByReportUploadLogId(reportUploadLogId);
		_log.info("<---deleted report upload id--->");
		_log.info("<---deleting report upload id since by scheme--->");
		BenchSchemeReturnsSinceLocalServiceUtil.deleteBenchSchemeReturnsSinceByReportUploadLogId(reportUploadLogId);
		_log.info("<---deleted report upload id since by scheme--->");
	
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		_log.info("status of excel file--->"+resultJsonObject.getBoolean("status"));
		if(resultJsonObject.getBoolean("status")) {
			
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			
			
			List<BenchSchemeReturns> dailyAverages2 = new ArrayList<BenchSchemeReturns>();
			List<BenchSchemeReturnsSince> dailyAverages3 = new ArrayList<BenchSchemeReturnsSince>();
			
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
	
			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			boolean isexcelhaserror = false;
			boolean isNoError = false;
			ArrayList <String> list = new ArrayList<String>();
		      list.add("Benchmark Return");
			  list.add("Benchmark Return Date");
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					_log.info("ocp package--->"+pkg);
					workbook = new XSSFWorkbook(pkg);
					_log.info("work book--->"+workbook);
					Iterator<Sheet> sheets1 = workbook.sheetIterator();
					while (sheets1.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets1.next();
						_log.info("excel sheet name---"+sheet);
						if (list.contains(sheet.getSheetName())) {
							list.remove(sheet.getSheetName());
						}
					}

					if (list.size() > 0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror", true);
						JSONArray errorSheetNameList = JSONFactoryUtil.createJSONArray(list);
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;

					}
					Iterator<Sheet> sheets = workbook.sheetIterator();
					while (sheets.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets.next();
						String sheetName = sheet.getSheetName();
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						int sheetCount = 0;
						if(sheetName.equalsIgnoreCase("Benchmark Return")) {
							sheetCount = 0;
						}else if(sheetName.equalsIgnoreCase("Benchmark Return Date")) {
							sheetCount = 1;
						}
						
						rowLoop:
						while (rows.hasNext()) {
							BenchSchemeReturns bsr = null;
							BenchSchemeReturnsSince bsrs= null;
							try {
								if(sheetCount == 0) {
									bsr = BenchSchemeReturnsLocalServiceUtil.createBenchSchemeReturns(CounterLocalServiceUtil.increment(BenchSchemeReturns.class.getName()));
									bsr.setCreatedby(userId);
									bsr.setReportUploadLogId(reportUploadLogId);
								}else if(sheetCount == 1) {
									bsrs = BenchSchemeReturnsSinceLocalServiceUtil.createBenchSchemeReturnsSince(CounterLocalServiceUtil.increment(BenchSchemeReturnsSince.class.getName()));
									bsrs.setCreatedby(userId);
									bsrs.setReportUploadLogId(reportUploadLogId);
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							for (int i = 0; i < 16; i++) {
								XSSFCell cell = row.getCell(i);
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
		
									String val = formatter.formatCellValue(cell);
									_log.info(val);
									if (val != null) {
										val = val.trim();
										if(rowCount > 1) {
											if(sheetCount == 0 && bsr != null) {
												//sheet 1
												if (i == 0) {
													if (val != null) {
														bsr.setReturns(val);
													} 
												}
												else if (i == 1) {
													
													try {
														Date date_1 = cell.getDateCellValue();
														bsr.setBase_date(date_1);
													} catch (Exception e) {
														 _log.error("error parsing date"+val);
														 errorObj.put("status",false);
														 errorObj.put("msg", CommonParser.dateExceptionMsg+sheetName);
													}
												}
												else if (i == 2) {
													bsr.setCg(val);
												} 
												else if (i == 3) {
													bsr.setSg(val);
												} 
												else if (i == 4) {
													bsr.setCcg(val);
												} 
												else if (i == 5) {
													bsr.setLite(val);
												}
												else if (i == 6) {
													bsr.setApy(val);
												}
												else if (i == 7) {
													
													bsr.setE(val);
												}
												else if (i == 8) {
													bsr.setC(val);
												} 
												else if (i == 9) {
													bsr.setG(val);
												} 
												else if (i == 10) {
													bsr.setE2(val);
												}
												else if (i == 11) {
													bsr.setC2(val);
												} 
												else if (i == 12) {
													bsr.setG2(val);
													
												}else if (i == 13) {
													bsr.setTax_saver(val);
													
												} else if (i == 14) {
													try {
														bsr.setReturn_as_on_date(cell.getDateCellValue());
													} catch (Exception e) {
														 _log.error("error parsing date"+val);
														 errorObj.put("status",false);
														 errorObj.put("msg", CommonParser.dateExceptionMsg+sheetName);
													}
												}
											}else if(sheetCount == 1 && bsrs != null) {
												//sheet 2
												if (i == 0) {
													if(val == null) {
														break rowLoop;
													}
													
													if (val != null) {
														bsrs.setReturns_since(val);
													} 
												}
												else if (i == 1) {
													try {
														Date date_2 = cell.getDateCellValue();
														bsrs.setReturn_as_on_date(date_2);
													} catch (Exception e) {
														 _log.error("error parsing date"+val);
														 errorObj.put("status", false);
														 errorObj.put("msg", CommonParser.dateExceptionMsg);
														 return errorObj;
													}
												}
												else if (i == 2) {
													bsrs.setCg(val);
												} 
												else if (i == 3) {
													bsrs.setSg(val);
												} 
												else if (i == 4) {
													bsrs.setCcg(val);
												} 
												else if (i == 5) {
													bsrs.setLite(val);
												}
												else if (i == 6) {
													bsrs.setApy(val);
												}
												else if (i == 7) {
													
													bsrs.setE(val);
												}
												else if (i == 8) {
													bsrs.setC(val);
												} 
												else if (i == 9) {
													bsrs.setG(val);
												} 
												else if (i == 10) {
													bsrs.setE2(val);
												}
												else if (i == 11) {
													bsrs.setC2(val);
												} 
												else if (i == 12) {
													bsrs.setG2(val);
													
												}else if (i == 13) {
													bsrs.setTax_saver(val);
													
												} 
											}
											
										}
									} else if(rowCount>1 && i==0) {
										break rowLoop;
									}
										
								} 
								
							}
							
							if(sheetCount == 0 && bsr != null) {
								bsr.setCreatedate(new Date());
							}else if(sheetCount == 1 && bsrs != null) {
								bsrs.setCreatedate(new Date());
							}
		
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
								if(sheetCount == 0 && bsr != null) {
									_log.info("bsr"+bsr);
									dailyAverages2.add(bsr);
								}else if(sheetCount == 1 && bsrs != null) {
									_log.info("bsrs"+bsr);
									dailyAverages3.add(bsrs);
								}
							
							}
							rowCount++;
						}//row end
						_log.info(sheetName +" rowcount"+rowCount);
					}
					//_log.info(errorArray);
					
					_log.info("isexcelhaserror" + isexcelhaserror);
				}
			} catch (Exception e) {
				isNoError = true;
				isexcelhaserror = true;
				 _log.error(e);
			} finally {
				try {
					workbook.close();
				} catch (Exception e) {
					 _log.error(e);
				}
			}
			
			
			if (!isexcelhaserror ) {
				try {
					BenchSchemeReturnsLocalServiceUtil.addBenchmarkSchemeReturns(dailyAverages2);
					_log.info("BenchSchemeReturns datacount"+dailyAverages2.size());
					
					BenchSchemeReturnsSinceLocalServiceUtil.addBenchmarkSchemeReturnsSince(dailyAverages3);
					_log.info("BenchSchemeReturnsSince datacount"+dailyAverages3.size());
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("BenchmarkFile");

		File file = uploadPortletRequest.getFile("BenchmarkFile");

		String mimeType = uploadPortletRequest.getContentType("BenchmarkFile");

		String title = fileName;

		String description = "BenchmarkFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "PFM");
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
	
	private static Log _log = LogFactoryUtil.getLog(saveBenchmark.class);
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}

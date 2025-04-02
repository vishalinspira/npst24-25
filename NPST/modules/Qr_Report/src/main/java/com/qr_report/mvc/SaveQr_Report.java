package com.qr_report.mvc;

import com.daily.average.service.model.Qr_Report;
import com.daily.average.service.service.Qr_ReportLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
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
import com.qr_report.constants.Qr_ReportPortletKeys;

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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { "javax.portlet.name=" + Qr_ReportPortletKeys.QR_REPORT,
"mvc.command.name=/qrreport/save" }, service = MVCResourceCommand.class)


public class SaveQr_Report implements MVCResourceCommand{
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
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		
		String fileName = uploadPortletRequest.getFileName("qrreportFile");

		File file = uploadPortletRequest.getFile("qrreportFile");

		String mimeType = uploadPortletRequest.getContentType("qrreportFile");

		String title = fileName;
		
		_log.info("QrReport:::::::::::::::::::::");
		String sheetName="Quarterly_report";
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		if(ValidateFileName.isValidfile(fileName)) {
		Qr_ReportLocalServiceUtil.deleteQr_ReportByReportUploadLogId(reportUploadLogId);
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			
			//JSONArray qr_ReportJsonArray = JSONFactoryUtil.createJSONArray();
			List<Qr_Report> qr_Reports = new ArrayList<Qr_Report>();
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
			customerIdcell.setCellValue("Sr.No");
			
			
			boolean isexcelhaserror = false;
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("Quarterly_report");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					}else {
						
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						rowLoop:
						while (rows.hasNext()) {
							
							Qr_Report qr_Report =Qr_ReportLocalServiceUtil.createQr_Report(CounterLocalServiceUtil.increment(Qr_Report.class.getName()));
									
							qr_Report.setCreatedby(userId);
							qr_Report.setReportUploadLogId(reportUploadLogId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							//for (int i = 0; i < 36; i++) {
								for (int i = 0; i < 36; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
	
									DataFormatter formatter = new DataFormatter();
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												
												qr_Report.setPension_fund_name(val);
											} else {
												errorObj.put("cellno", 2);
												errorObj.put("msg", "it is not a number");
												
												isError = true;
											}
										}
										else if (i == 1) {
	
											try {
												Date dateFormat= cell.getDateCellValue();
												qr_Report.setAs_on_date(dateFormat);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 2) {
											qr_Report.setAsset_class(val);
										} 
										else if (i == 3) {
											qr_Report.setAsset_sub_class(val);
										}
										else if (i == 4) {
											try {
												BigDecimal cg = CommonParser.parseBigDecimal(val);
												qr_Report.setCg(cg);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 5) {
											//BigDecimal percentage_cg = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_cg(val);
										}
										else if (i == 6) {
											try {
												BigDecimal sg = CommonParser.parseBigDecimal(val);
												qr_Report.setSg(sg);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 7) {
											//BigDecimal percentage_sg = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_sg(val);
										}
										else if (i == 8) {
											try {
												BigDecimal e_i = CommonParser.parseBigDecimal(val);
												qr_Report.setE_i(e_i);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 9) {
											//BigDecimal percentage_e_i = CommonParser.parseBigDecimal(val);	
											qr_Report.setPercentage_e_i(val);
											
										} 
										else if (i == 10) {
											
											try {
												BigDecimal c_i = CommonParser.parseBigDecimal(val);
												qr_Report.setC_i(c_i);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 11) {
											
											//BigDecimal percentage_c_i = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_c_i(val);
										}
										else if (i == 12) {
											
											try {
												BigDecimal g_i = CommonParser.parseBigDecimal(val);
												qr_Report.setG_i(g_i);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 13) {
											
											//BigDecimal percentage_g_i = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_g_i(val);
										}
										else if (i == 14) {
											
											try {
												BigDecimal e_ii = CommonParser.parseBigDecimal(val);
												qr_Report.setE_ii(e_ii);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 15) {
											
											//BigDecimal percentage_e_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_e_ii(val);
										}
										else if (i == 16) {
											
											try {
												BigDecimal c_ii = CommonParser.parseBigDecimal(val);
												qr_Report.setC_ii(c_ii);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 17) {
											//BigDecimal percentage_c_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_c_ii(val);
										}
										else if (i == 18) {
											BigDecimal g_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setG_ii(g_ii);
										}
										else if (i == 19) {
											//BigDecimal percentage_g_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_g_ii(val);
										}
										else if (i == 20) {
											try {
												BigDecimal nps_lite = CommonParser.parseBigDecimal(val);
												qr_Report.setNps_lite(nps_lite);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 21) {
											//BigDecimal percentage_nps_lite = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_nps_lite(val);
										}
										else if (i == 22) {
											try {
												BigDecimal cor_cg = CommonParser.parseBigDecimal(val);
												qr_Report.setCor_cg(cor_cg);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 23) {
											//BigDecimal percentage_cor_cg = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_cor_cg(val);
										}
//										else if (i == 24) {
//											try {
//												BigDecimal purchases_and_sales = CommonParser.parseBigDecimal(val);
//												qr_Report.setPurchases_and_sales(purchases_and_sales);
//											} catch (Exception e) {
//												_log.info("error parsing long"+val);
//												resultJsonObject.put("status", false);
//												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
//												return resultJsonObject;
//											}
//										}
//										else if (i == 25) {
//											try {
//												BigDecimal amount_received_or_accrued = CommonParser
//														.parseBigDecimal(val);
//												qr_Report
//														.setAmount_received_or_accrued(amount_received_or_accrued);
//											} catch (Exception e) {
//												_log.info("error parsing long"+val);
//												resultJsonObject.put("status", false);
//												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
//												return resultJsonObject;
//											}
//										}
//										else if (i == 26) {
//											qr_Report.setGroup_of_the_sponsor_or_pfm(val);
//											//qr_Report.setGroup_of_sponsor_associates(val);
//										}
//										else if (i == 27) {
//											
//											qr_Report.setDown_graded_investments(val);
//										}
										else if (i == 24) {
											try {
												BigDecimal apy = CommonParser.parseBigDecimal(val);
												qr_Report.setApy(apy);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 25) {
											//BigDecimal percentage_apy = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_apy(val);
										}
										else if (i == 26) {
											try {
												BigDecimal a_i = CommonParser.parseBigDecimal(val);
												qr_Report.setA_i(a_i);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 27) {
											//BigDecimal percentage_a_i = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_a_i(val);
										}
										else if (i == 28) {
											try {
												BigDecimal scheme_nps_tts_ii = CommonParser.parseBigDecimal(val);
												qr_Report.setScheme_nps_tts_ii(scheme_nps_tts_ii);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 29) {
											//BigDecimal percentage_scheme_nps_tts_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_scheme_nps_tts_ii(val);
										}
										
										
										
										else if (i == 30) {
											try {
												BigDecimal apy_funds_cheme = CommonParser.parseBigDecimal(val);
												qr_Report.setApy_fund_scheme(apy_funds_cheme);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 31) {
											//BigDecimal percentage_scheme_nps_tts_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_apy_fund_scheme(val);
										}
										else if (i == 32) {
											try {
												BigDecimal tier2_composite = CommonParser.parseBigDecimal(val);
												qr_Report.setTier2_composite(tier2_composite);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 33) {
											//BigDecimal percentage_scheme_nps_tts_ii = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage_tier2_composite(val);
										}
										else if (i == 34) {
											try {
												BigDecimal total_aum = CommonParser.parseBigDecimal(val);
												qr_Report.setTotal_aum(total_aum);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 35) {
											//BigDecimal percentage = CommonParser.parseBigDecimal(val);
											qr_Report.setPercentage(val);
										}
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							qr_Report.setCreatedate(new Date());
							
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
								//JSONObject qr_ReportJsonObject = JSONFactoryUtil.createJSONObject(qr_Report.toString());
								//qr_ReportJsonArray.put(qr_ReportJsonObject);
								qr_Reports.add(qr_Report);
							}
							rowCount++;
						}
						_log.info(sheetName +" rowcount"+rowCount);
						
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
			//_log.info(qr_ReportJsonArray.toString());
			
			if (!isexcelhaserror) {
				Qr_ReportLocalServiceUtil.addQr_Reports(qr_Reports);
				_log.info(sheetName +" datacount"+qr_Reports.size());

				
				Long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					
					String statusByUserName = themeDisplay.getUser().getFullName();
					ServiceContext serviceContext = null;
					try {
						serviceContext = ServiceContextFactory.getInstance(resourceRequest);
					} catch (PortalException e) {
						 _log.error(e);
					}
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
					
					//resultJsonObject.put("pdfURL",  pdfTable(qr_Reports, themeDisplay, resourceRequest));
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
	public String pdfUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest) {

		File file = new File(filepath);
		String mimeType = "application/pdf";
		String title = filename + ".pdf";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {
			Folder folder = getFolder(themeDisplay);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("qrreportFile");

		File file = uploadPortletRequest.getFile("qrreportFile");

		String mimeType = uploadPortletRequest.getContentType("qrreportFile");

		String title = fileName;

		String description = "qrreportFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "PFM");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogPFMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	Log log=LogFactoryUtil.getLog(SaveQr_Report.class);

	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}

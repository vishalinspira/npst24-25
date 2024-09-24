package com.report.debit.mvcresoure;

import com.daily.average.service.model.ReportDebit;
import com.daily.average.service.service.ReportDebitLocalServiceUtil;
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
import com.report.debit.constants.ReportDebitPortletKeys;

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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;


@Component(property = { "javax.portlet.name=" + ReportDebitPortletKeys.REPORTDEBIT,
"mvc.command.name=/reportdebit/save" }, service = MVCResourceCommand.class)
public class SaveReportDebit implements MVCResourceCommand{
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
		String fileName = uploadPortletRequest.getFileName("reportdebitFile");

		File file = uploadPortletRequest.getFile("reportdebitFile");

		String mimeType = uploadPortletRequest.getContentType("reportdebitFile");

		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
			
		if(resultJsonObject.getBoolean("status")) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONArray reportdebitJsonArray = JSONFactoryUtil.createJSONArray();
		List<ReportDebit> reportdebits = new ArrayList<ReportDebit>();
		
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
		
		
		ReportDebitLocalServiceUtil.deleteReportDebitByReportUploadLogId(reportUploadLogId );
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("repo_debt");
				String sheetName = sheet.getSheetName();
				
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					errorSheetNameList.put("repo_debt");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
				} else {
					
					//resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
					_log.info("Excel validation done ");
					//boolean result=resultJsonObject.getBoolean("status");
					//_log.info("API status "+ result);
					//if(resultJsonObject.getBoolean("status")) {
					//if(true) {
                        
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						//_log.info("Entered if block 1");
				/*		while (rows.hasNext()) {
							_log.info("Entered while loop 1");
							XSSFRow row = (XSSFRow) rows.next();

						}*/
						rowLoop:
						while (rows.hasNext()) {
							
							//_log.info("Entered while loop");
							ReportDebit reportdebit =ReportDebitLocalServiceUtil.createReportDebit(CounterLocalServiceUtil.increment(ReportDebit.class.getName()));
									
							reportdebit.setCreatedby(userId);
							reportdebit.setReportUploadLogId(reportUploadLogId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							for (int i = 0; i < 17; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
								//	_log.info("Cell is not NULL");
									DataFormatter formatter = new DataFormatter();
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
									/*	if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												
												Date date_1=null;
												try {
													_log.info("column 0 rowcount= "+rowCount);
													date_1 = cell.getDateCellValue();
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheet);
													return resultJsonObject;
												}
												reportdebit.setDate(date_1);
											} else {
												_log.info("column 0 else part");
												errorObj.put("cellno", 2);
												errorObj.put("msg", "it is not a number");
												
												isError = true;
											}
										}*/
										 if (i == 0) {
											_log.info("column 1");
											reportdebit.setFin(val);
										}
										else if (i == 1) {
											_log.info("column 2");
											reportdebit.setScheme(val);
										} 
										else if (i == 2) {
											
											_log.info("column 3");
											reportdebit.setCompany_name(val);
										}
										else if (i == 3) {
											_log.info("column 4");
											reportdebit.setSecurity_code(val);
										} 
										else if (i == 4) {
											_log.info("column 5");
											reportdebit.setIsin(val);
										}
										else if (i == 5) {
											_log.info("column 6");
											reportdebit.setSecdesc(val);
										} 
										else if (i == 6) {
											_log.info("column 7");
											reportdebit.setSec(val);
										}
										else if (i == 7) {
											_log.info("column 8");
											reportdebit.setCoupon(val);
										}
										else if (i == 8) {
											
											try {
												long faceval=0;
												try {
													_log.info("column 9");
													faceval = CommonParser.parseLong(val);
												} catch (Exception e) {
													_log.info("error parsing long---->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												}
												reportdebit.setFaceval(faceval);
											} catch (Exception e) {
												 _log.error(e);
											}
										} 
										else if (i == 9) {
											
											BigDecimal custhold=null;
											try {
												_log.info("column 10");
												custhold = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal---->"+CommonParser.numberExceptionMsg);
											}
											reportdebit.setCusthold(custhold);
										}
										else if (i == 10) {
											
											BigDecimal transit = null;
											try {
												_log.info("column 11");
												transit = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											}
											reportdebit.setTransit(transit);
										}
										else if (i == 11) {
											
											BigDecimal loghold=null;
											try {
												_log.info("column 12");
												loghold = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											}
											reportdebit.setLoghold(loghold);
										} 
										else if (i == 12) {
											
											BigDecimal bse_rate=null;
											try {
												_log.info("column 13");
												bse_rate = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											}
											reportdebit.setBse_rate(bse_rate);
										}
										else if (i == 13) {
											
											BigDecimal bse_value=null;
											try {
												_log.info("column 14");
												bse_value = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											}
											reportdebit.setBse_value(bse_value);
										} 
										else if (i == 14) {
											
											BigDecimal nse_rate=null;
											try {
												_log.info("column 15");
												nse_rate = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											}
											reportdebit.setNse_rate(nse_rate);
										}
										else if (i == 15) {
											
											BigDecimal nse_value=null;
											try {
												_log.info("column 16");
												nse_value = CommonParser.parseBigDecimal(val);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											}
											reportdebit.setNse_value(nse_value);
										} else if (i == 16) {
											Date date_1 = null;
											try {
												_log.info("column 17");
												date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
											reportdebit.setReportingdate(date_1);
										} 
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							reportdebit.setCreatedate(new Date());
							
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
								JSONObject reportdebitJsonObject = JSONFactoryUtil.createJSONObject(reportdebit.toString());
								reportdebitJsonArray.put(reportdebitJsonObject);
								reportdebits.add(reportdebit);
							}
							rowCount++;
						}
						_log.info(sheetName +" rowcount"+rowCount);
					}
					
				}
				
		/*	}
			else {
				_log.info("File is null");
			}
			*/
		}catch (Exception e) {
			 _log.error(e);
		}
		_log.info(reportdebitJsonArray.toString());
		
		if (!isexcelhaserror) {
			ReportDebitLocalServiceUtil.addReportDebits(reportdebits);
			_log.info("reportdebits datacount"+reportdebits.size());
			
			Long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
			try {
				String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
				_log.info("fileEntryId "+ fileEntryId);
				//String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
				//Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
				String statusByUserName = themeDisplay.getUser().getFullName();
				ServiceContext serviceContext = null;
				try {
					serviceContext = ServiceContextFactory.getInstance(resourceRequest);
				} catch (PortalException e) {
					 _log.error(e);
				}
				updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
				
				errorExcel.close();
				//resultJsonObject.put("pdfURL",  pdfTable(reportdebits, themeDisplay, resourceRequest));
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
	/*public String pdfTable(List<ReportDebit> reportdebits, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        
        PDRectangle rect = page.getMediaBox();
        
        document.addPage(page);

        PDPageContentStream cos = new PDPageContentStream(document, page);
        
        float margin = 10;
        _log.info("rect.getHeight()"+rect.getHeight());
        
        float yStartNewPage = rect.getUpperRightY() - (2 * margin);
        
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float topMargin = 0;
        float bottomMargin = 10;
        
        float yPosition = rect.getUpperRightY()-margin;

        BaseTable table3 = new BaseTable(yPosition, yStartNewPage,topMargin,
                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
        
        be.quodlibet.boxable.Row<PDPage> table3row = table3.createRow(20);
        Cell<PDPage> table3cell = table3row.createCell(6, "Date");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "FIN");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Scheme");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(11, "Company Name");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(5, "Security Code");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(5, "ISIN");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(11, "SecDesc");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Sec");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Coupon");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "FaceVal");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "CustHold");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "Transit");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "LogHold");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "BSE Rate");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "BSE Value");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "NSE Rate");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(6, "NSE Value");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
       

        for (Iterator iterator = reportdebits.iterator(); iterator.hasNext();) {
        	ReportDebit reportdebit = (ReportDebit) iterator.next();
			
			 	table3row = table3.createRow(20);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getDate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(reportdebit.getFin()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(reportdebit.getScheme()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(11,String.valueOf(reportdebit.getCompname()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(5,String.valueOf(reportdebit.getSecuritycode()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(5,String.valueOf(reportdebit.getIsin()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(11,String.valueOf(reportdebit.getSecdesc()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(reportdebit.getSec()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(reportdebit.getCoupon()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(reportdebit.getFaceval()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getCusthold()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getTransit()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getLoghold()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getBserate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getBsevalue()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getNserate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(reportdebit.getNsevalue()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		       
		        
        }
        
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File reportdebittempfile = File.createTempFile("Repo_debt121121", "pdf");
		
		outputStream = new FileOutputStream(reportdebittempfile);
        document.save(outputStream);
        String filepath = reportdebittempfile.getPath();
		String filename = reportdebittempfile.getName();
        document.close();
        outputStream.close();
        return pdfUpload(themeDisplay, filepath, filename, resourceRequest);
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
	}*/

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

		Date date = new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+uploadPortletRequest.getFileName("reportdebitFile");

		File file = uploadPortletRequest.getFile("reportdebitFile");

		String mimeType = uploadPortletRequest.getContentType("reportdebitFile");

		String title = fileName;

		String description = "reportdebitFile";

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
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}


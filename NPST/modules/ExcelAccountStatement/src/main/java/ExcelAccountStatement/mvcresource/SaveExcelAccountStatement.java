package ExcelAccountStatement.mvcresource;




import com.daily.average.service.model.Excelaccount;
import com.daily.average.service.service.ExcelaccountLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
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

/*import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;*/
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import ExcelAccountStatement.constants.ExcelAccountStatementPortletKeys;
import ExcelAccountStatement.portlet.ValidateFileName;
import nps.common.service.util.CommonParser;
/*import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;*/
import nps.email.api.api.ExcelValidationAn10Api;
import nps.email.api.api.ExcelValidationAn7Api;

@Component(property = { "javax.portlet.name=" + ExcelAccountStatementPortletKeys.EXCELACCOUNTSTATEMENT,
"mvc.command.name=/excelaccountstatement/save" }, service = MVCResourceCommand.class)
public class SaveExcelAccountStatement implements MVCResourceCommand{
	Log _log = LogFactoryUtil.getLog(SaveExcelAccountStatement.class);
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

		String fileName = uploadPortletRequest.getFileName("excelAccountFile");

		File file = uploadPortletRequest.getFile("excelAccountFile");

		String mimeType = uploadPortletRequest.getContentType("excelAccountFile");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		if(ValidateFileName.isValidfile(fileName)) {
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
	
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
	
			JSONArray excelaccountJsonArray = JSONFactoryUtil.createJSONArray();
			//List<String> excelaccount = new ArrayList<String>();
			List<Excelaccount> excelaccounts = new ArrayList<Excelaccount>();
			
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
			customerIdcell.setCellValue("customerId");
			XSSFCell NPSNumber = xxx.createCell(3);
			NPSNumber.setCellValue("FORACID");
			//XSSFCell NPSAcName = xxx.createCell(4);
			//NPSAcName.setCellValue("NPSAcName");
			boolean isexcelhaserror = false;
			String sheetName = "CL BAL";
			ExcelaccountLocalServiceUtil.deleteExcelaccountByReportUploadLogId(reportUploadLogId);
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("CL BAL");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						_log.info(resultJsonObject);
						return resultJsonObject;
					} else {
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						rowLoop:
						while (rows.hasNext()) {
							
							Excelaccount excelaccount = ExcelaccountLocalServiceUtil.createExcelaccount(CounterLocalServiceUtil.increment(Excelaccount.class.getName()));
									
							excelaccount.setCreatedby(userId);
							excelaccount.setReportUploadLogId(reportUploadLogId);
							/*BigDecimal  initdec =  new BigDecimal("0.0");
							excelaccount.setOpening_bal(initdec);
							excelaccount.setTotal_credit(initdec);
							excelaccount.setTotal_debit(initdec);
							excelaccount.setClosing_bal(initdec);*/
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							for (int i = 0; i < 8; i++) {
								XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
									DataFormatter formatter = new DataFormatter();
									String val = formatter.formatCellValue(cell);
									
									if (val != null )
										val = val.trim();
									
									if(rowCount > 1) {
										_log.info(val);
										if (i == 0) {
											if (val != null) {
												try {
													Long CUST_ID = CommonParser.parseLong(val);
													if (CUST_ID != null) {
														excelaccount.setCust_id(CUST_ID);
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
											if (val != null ) {
												try {
													Long NPS_Acc_No = CommonParser.parseLong(val);
													if (NPS_Acc_No != null) {
														excelaccount.setNps_acc_number(NPS_Acc_No);
													} else {
														errorObj.put("cellno", 3);
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
												errorObj.put("cellno", 3);
												errorObj.put("msg", "length error");
												
												isError = true;
											}
										} else if (i == 2) {
											excelaccount.setNps_acc_name(val);
											
										} else if (i == 3 && Validator.isNotNull(val)) {
											try {
												BigDecimal  Opening_Bal =  CommonParser.parseBigDecimal(val);
												excelaccount.setOpening_bal(Opening_Bal);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else if (i == 4 && Validator.isNotNull(val)) {
											
											try {
												BigDecimal Total_Credit = CommonParser.parseBigDecimal(val);
												excelaccount.setTotal_credit(Total_Credit);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else if (i == 5 && Validator.isNotNull(val)) {
											
											try {
												BigDecimal Total_Debit = CommonParser.parseBigDecimal(val);
													excelaccount.setTotal_debit(Total_Debit);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else if (i == 6 && Validator.isNotNull(val)) {
											
											try {
												BigDecimal Closing_Bal = CommonParser.parseBigDecimal(val);
												excelaccount.setClosing_bal(Closing_Bal);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}else if (i == 7) {
											try {
												Date date= cell.getDateCellValue();
												excelaccount.setTran_date(date);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
											
										}
									
									}
									
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
							excelaccount.setCreateDate(new Date());
							
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
								excelaccounts.add(excelaccount);
							}
							rowCount++;
						}
						_log.info("CL BAL data count"+rowCount);
					}
					
					_log.info("isexcelhaserror" + isexcelhaserror);
				}
			
				if (!isexcelhaserror) {
					try {
						ExcelaccountLocalServiceUtil.addExcelaccounts(excelaccounts);
						_log.info("CL BAL data count"+excelaccounts.size());
					} catch (Exception e1) {
						_log.error(e1);
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
						//resultJsonObject.put("pdfURL",  pdfTable(excelaccounts, themeDisplay, resourceRequest));
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					/*String downloadUrl = null;
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
					resultJsonObject.put("downloadUrl", downloadUrl);*/
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					 return resultJsonObject;
				}
			}catch (Exception e) {
				_log.error(e);
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;
			}
			_log.info(resultJsonObject);
		}
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
		}
		return resultJsonObject;
	}
	

	/*public String pdfTable(List<Excelaccount> excelaccounts, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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
        Cell<PDPage> table3cell = table3row.createCell(14, "CUST_ID");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "FORACID");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(16, "ACCOUNT NAME");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Opening Balance");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Total Credits");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Total Debit");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Closing Balance");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        
        for (Iterator iterator = excelaccounts.iterator(); iterator.hasNext();) {
        	Excelaccount excelaccount = (Excelaccount) iterator.next();
			
			 	table3row = table3.createRow(20);
		        table3cell = table3row.createCell(14,String.valueOf(excelaccount.getCUST_ID()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(excelaccount.getNPS_Acc_No()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(16,String.valueOf(excelaccount.getNPS_Acc_Name()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(excelaccount.getOpening_Bal()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(excelaccount.getTotal_Credit()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(excelaccount.getTotal_Debit()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(excelaccount.getClosing_Bal()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		       
        }
         
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File excelaccounttempfile = File.createTempFile("Annex3", "pdf");
		
		outputStream = new FileOutputStream(excelaccounttempfile);
        document.save(outputStream);
        String filepath = excelaccounttempfile.getPath();
		String filename = excelaccounttempfile.getName();
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
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+uploadPortletRequest.getFileName("excelAccountFile");

		File file = uploadPortletRequest.getFile("excelAccountFile");

		String mimeType = uploadPortletRequest.getContentType("excelAccountFile");

		String title = fileName;

		String description = "Daily Average Balance Report";

		long repositoryId = themeDisplay.getScopeGroupId();
	    
		long fileEntryId = 0l;

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			fileEntryId  = fileEntry.getFileEntryId();
		} catch (Exception e) {

			_log.info(e.getMessage());

			 _log.error(e);
		}
		return fileEntryId;
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
	

	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
		ReportUploadLogLocalServiceUtil.updateReportUploadLog(1, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}

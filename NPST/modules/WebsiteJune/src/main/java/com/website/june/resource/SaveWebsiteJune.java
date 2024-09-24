package com.website.june.resource;



import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.model.WebsiteJune;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.daily.average.service.service.WebsiteJuneLocalServiceUtil;
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
import com.website.june.constants.WebsiteJunePortletKeys;

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

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+WebsiteJunePortletKeys.WEBSITEJUNE,
			"mvc.command.name=/websitejune/save",
		}, 
		service = MVCResourceCommand.class
	)
public class SaveWebsiteJune implements MVCResourceCommand{
	Log _log = LogFactoryUtil.getLog(SaveWebsiteJune.class);
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		_log.info("in serveResource");
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
			_log.info(resultJsonObject);
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = uploadPortletRequest.getFileName("websitejuneFile");

		File file = uploadPortletRequest.getFile("websitejuneFile");

		String mimeType = uploadPortletRequest.getContentType("websitejuneFile");
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		String cra = IntermediaryListLocalServiceUtil.fetchIntermediaryByReportLogId(reportUploadLogId);
		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
			JSONArray websiteJuneJsonArray = JSONFactoryUtil.createJSONArray();
			List<WebsiteJune> websiteJunes = new ArrayList<WebsiteJune>();
			
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
					XSSFSheet sheet = workbook.getSheet("website_data");
					String sheetName = sheet.getSheetName();
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowLoop:
					while (rows.hasNext()) {
						
						WebsiteJune websiteJune =WebsiteJuneLocalServiceUtil.createWebsiteJune(CounterLocalServiceUtil.increment(WebsiteJune.class.getName()));
								
						websiteJune.setCreatedby(userId);
						websiteJune.setCra(cra);
						websiteJune.setReportUploadLogId(reportUploadLogId);
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						for (int i = 0; i < 6; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							if (cell != null) {
	
								DataFormatter formatter = new DataFormatter();
								String val = formatter.formatCellValue(cell);
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									if (i == 0) {
										if (val != null && Validator.isNotNull(val) && val.length() > 0) {
											
											try {
												websiteJune.setDate_(cell.getDateCellValue());
											} catch (Exception e) {
												_log.info("error parsing date---->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										
										websiteJune.setNps_sector(val);
									}
									else if (i == 2) {
										
										try {
											Long no_of_subscribers = CommonParser.parseBigDecimal(val).longValue();
											websiteJune.setNo_of_subscribers(no_of_subscribers);
										} catch (Exception e) {
											_log.info("error parsing number---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 3) {
										
										try {
											BigDecimal total_contribution_m_and_b = CommonParser.parseBigDecimal(val);
											websiteJune.setTotal_contribution_m_and_b(total_contribution_m_and_b);
										} catch (Exception e) {
											_log.info("error parsing number---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 4) {
										
										try {
											BigDecimal aum = CommonParser.parseBigDecimal(val);
											websiteJune.setAum(aum);
										} catch (Exception e) {
											_log.info("error parsing number---->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
											return resultJsonObject;
										}
									} 
									else if (i == 5) {
										
									}
									
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						websiteJune.setCreatedate(new Date());
						
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
							websiteJunes.add(websiteJune);
						}
						rowCount++;
					}
					_log.info(sheetName +" rowcount"+rowCount);
					
				}
			}catch (Exception e) {
				 _log.error(e);
			}
			_log.info(websiteJuneJsonArray.toString());
			
			if (!isexcelhaserror) {
				WebsiteJuneLocalServiceUtil.addWebsiteJune(websiteJunes);
				_log.info("websiteJunes" +" datacount"+websiteJunes.size());
				
				Long fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
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
					//resultJsonObject.put("pdfURL",  pdfTable(websiteJunes, themeDisplay, resourceRequest));
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
				}*/
				//resultJsonObject.put("downloadUrl", downloadUrl);
				resultJsonObject.put("status", false);
			}
		
		}
		return resultJsonObject;
	}
	/*public String pdfTable(List<WebsiteJune> websiteJunes, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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
       

        for (Iterator iterator = websiteJunes.iterator(); iterator.hasNext();) {
        	WebsiteJune websiteJune = (WebsiteJune) iterator.next();
			
			 	table3row = table3.createRow(20);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getDate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(websiteJune.getFin()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(websiteJune.getScheme()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(11,String.valueOf(websiteJune.getCompname()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(5,String.valueOf(websiteJune.getSecuritycode()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(5,String.valueOf(websiteJune.getIsin()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(11,String.valueOf(websiteJune.getSecdesc()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(websiteJune.getSec()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(websiteJune.getCoupon()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(websiteJune.getFaceval()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getCusthold()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getTransit()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getLoghold()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getBserate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getBsevalue()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getNserate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(6,String.valueOf(websiteJune.getNsevalue()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		       
		        
        }
        
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File websiteJunetempfile = File.createTempFile("Repo_debt121121", "pdf");
		
		outputStream = new FileOutputStream(websiteJunetempfile);
        document.save(outputStream);
        String filepath = websiteJunetempfile.getPath();
		String filename = websiteJunetempfile.getName();
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("websitejuneFile");

		File file = uploadPortletRequest.getFile("websitejuneFile");

		String mimeType = uploadPortletRequest.getContentType("websitejuneFile");

		String title = fileName;

		String description = "websitejuneFile";

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
		ReportUploadLogPFMCRALocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}

	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}

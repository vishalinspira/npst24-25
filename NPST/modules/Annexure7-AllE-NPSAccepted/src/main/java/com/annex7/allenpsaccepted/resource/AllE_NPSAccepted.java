package com.annex7.allenpsaccepted.resource;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.awt.Color;
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
import java.util.Random;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Annexure7.AllE.NPSAccepted.constants.Annexure7AllENPSAcceptedPortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.line.LineStyle;
import compliance.service.model.NPSAccepted;
import compliance.service.service.NPSAcceptedLocalService;

@Component(property = { 
		"javax.portlet.name=" + Annexure7AllENPSAcceptedPortletKeys.ANNEXURE7ALLENPSACCEPTED,
		"mvc.command.name=" + Annexure7AllENPSAcceptedPortletKeys.eNpsAccepted, 
		}, 
service = MVCResourceCommand.class)

public class AllE_NPSAccepted implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(AllE_NPSAccepted.class);

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

		String fileName = uploadPortletRequest.getFileName("eNPSAcceptedFile");

		File file = uploadPortletRequest.getFile("eNPSAcceptedFile");

		String mimeType = uploadPortletRequest.getContentType("eNPSAcceptedFile");

		String title = fileName;

		JSONArray npsAcceptedJsonArray = JSONFactoryUtil.createJSONArray();
		List<NPSAccepted> allNPS = new ArrayList<NPSAccepted>();
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat deciFormatter = new DecimalFormat(pattern,symbols);
		deciFormatter.setParseBigDecimal(true);
		
		/**
		 * Create error excel
		 */
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		XSSFCell rowNumCel = xxx.createCell(1);
		rowNumCel.setCellValue("RowNum");
		XSSFCell fileNamecell = xxx.createCell(2);
		fileNamecell.setCellValue("File Name");
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(3); 
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					
					NPSAccepted npsAccepted = (NPSAccepted) npsAcceptedLocalService.createNPSAccepted(CounterLocalServiceUtil.increment(NPSAccepted.class.getName()));
							
					npsAccepted.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 13; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 3) {
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//Long srno=Long.parseLong(val);
										npsAccepted.setFile_name(val);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									
									npsAccepted.setChannel(val);
								}
								else if (i == 2) {
									npsAccepted.setPao_fin(val);
								} 
								else if (i == 3) {
									_log.info("cell " + cell.getCellType());
									long reg = Long.parseLong(val);
									npsAccepted.setPao_reg_no(reg);
								} 
								else if (i == 4) {
									_log.info("cell " + cell.getCellType());
									long transId = Long.parseLong(val);
									npsAccepted.setTrans_id(transId);
								} 
								else if (i == 5) {
									_log.info("cell " + cell.getCellType());
									BigDecimal bg = (BigDecimal) deciFormatter.parse(val);
									npsAccepted.setAmt(bg.stripTrailingZeros());
								}
								else if (i == 6) {
									npsAccepted.setFile_ref_no(val);
								}
								else if (i == 7) {
									_log.info("cell " + cell.getCellType());
									int recordNo = Integer.parseInt(val);
									npsAccepted.setRecord_no(recordNo);
								}
								else if (i == 8) {
									_log.info("cell " + cell.getCellType());
									Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
									npsAccepted.setReceipt_date(date_1);
								} 
								else if (i == 9) {
									_log.info("cell " + cell.getCellType());
									Date date_2 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
									npsAccepted.setFund_realised_date(date_2);
								} 
								else if (i == 10) {
									_log.info("cell " + cell.getCellType());
									Date date_3 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
									npsAccepted.setFrc_upload_date(date_3);
								} 
								else if (i == 11) {
									npsAccepted.setTrustee_bank_tat(val);
								} 
								else if (i == 12) {
									npsAccepted.setMatching_type(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 3) {
							break rowloop;
						}
					}
					npsAccepted.setCreatedon(new Date());
						
						if (isError && rowCount > 3) {
							errorArray.put(errorObj);
							errorRow = xx.createRow(errorRowCount);
							errorRowCount++;
							XSSFCell rowCountCel = errorRow.createCell(1);
							rowCountCel.setCellValue(rowCount);
							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
							cellError.setCellValue(errorObj.getString("msg"));
							isexcelhaserror = true;
						} else if (rowCount > 3) {
							JSONObject npsAcceptedJsonObject = JSONFactoryUtil.createJSONObject(npsAccepted.toString());
							npsAcceptedJsonArray.put(npsAcceptedJsonObject);
							allNPS.add(npsAccepted);
						}
						rowCount++;
					}
					
				}
			}catch (Exception e) {
				_log.error(e);
			}
			_log.info(npsAcceptedJsonArray.toString());
			
			if (!isexcelhaserror) {
				npsAcceptedLocalService.addNPSAccepted(allNPS);
				
				uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
					//resultJsonObject.put("pdfURL",  pdfTable(allNPS, themeDisplay, resourceRequest));
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
			return resultJsonObject;
		}
	
//	public String pdfTable(List<NPSAccepted> npsAccepted, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        PDFont fontPlain = PDType1Font.HELVETICA;
//        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
//        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
//        PDFont fontMono = PDType1Font.COURIER;
//        LineStyle bottomBorder = new LineStyle(Color.white, 0);
//
//        // Create a document and add a page to it
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage(PDRectangle.A4);
//        
//        // PDRectangle.LETTER and others are also possible
//        PDRectangle rect = page.getMediaBox();
//        
//        // rect can be used to get the page width and height
//        document.addPage(page);
//
//        // Start a new content stream which will "hold" the to be created content
//        PDPageContentStream cos = new PDPageContentStream(document, page);
//        //cos.b
//	    //Dummy Table
//        float margin = 10;
//        _log.info("rect.getHeight()"+rect.getHeight());
//        
//        // starting y position is whole page height subtracted by top and bottom margin
//        float yStartNewPage = rect.getUpperRightY() - (2 * margin);
//        
//        // we want table across whole page width (subtracted by left and right margin ofcourse)
//        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
//
//        boolean drawContent = true;
//        float yStart = yStartNewPage;
//        float topMargin = 0;
//        float bottomMargin = 10;
//        
//        // y position is your coordinate of top left corner of the table
//        float yPosition = rect.getUpperRightY()-margin;
//        
//        BaseTable table = new BaseTable(yPosition, yStartNewPage,topMargin,
//                bottomMargin, tableWidth, margin, document, page, false, drawContent);
//        
//        // the parameter is the row height
//        be.quodlibet.boxable.Row<PDPage> headerRow = table.createRow(20);
//        
//        // the first parameter is the cell width        
//        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 7 - All E-NPS Accepted");
//        cell.setAlign(HorizontalAlignment.CENTER);
//        cell.setFontSize(20);
//        cell.setFont(fontBold);
//        table.addHeaderRow(headerRow);
//        
//        table.draw();
//        
//        float tableHeight = table.getHeaderAndDataHeight();
//        _log.info("tableHeight = "+tableHeight);
//        
//        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 10;
//        
//        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
//                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
//        
//        be.quodlibet.boxable.Row<PDPage> table2row = table2.createRow(20);
//        Cell<PDPage> table2cell = table2row.createCell(20, "File Name");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Channel");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "PAOFIN");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "PAO Reg. No.");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Transaction Id");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//	    
//	    for (Iterator iterator = npsAccepted.iterator(); iterator.hasNext();) {
//        	NPSAccepted npsAccept = (NPSAccepted) iterator.next();
//			
//			 	table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getFilename()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getChannel()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getPaofin()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getPaoregno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getTransactionid()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//
//        }
//	    
//	    table2row = table2.createRow(20);
//	  	table2cell = table2row.createCell(100, "");
//	    table2cell.setAlign(HorizontalAlignment.LEFT);
//	    table2cell.setLeftBorderStyle(bottomBorder);
//	    table2cell.setRightBorderStyle(bottomBorder);
//	    table2cell.setTopBorderStyle(bottomBorder);
//	    //table2cell.setBottomBorderStyle(bottomBorder);
//	    table2cell.setFontSize(10);
//	    
//	    table2row = table2.createRow(20);
//	  	table2cell = table2row.createCell(20, "Amount");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "File Reference No.");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Record No.");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Receipt Date");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Fund Realised Date");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        
//        for (Iterator iterator = npsAccepted.iterator(); iterator.hasNext();) {
//        	NPSAccepted npsAccept = (NPSAccepted) iterator.next();
//			    
//			    table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getAmount()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getFilerefno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getRecordno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getReceiptdate()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getFundrealiseddate()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//
//        }
//        
//        table2row = table2.createRow(20);
//	  	table2cell = table2row.createCell(100, "");
//	    table2cell.setAlign(HorizontalAlignment.LEFT);
//	    table2cell.setLeftBorderStyle(bottomBorder);
//	    table2cell.setRightBorderStyle(bottomBorder);
//	    table2cell.setTopBorderStyle(bottomBorder);
//	    //table2cell.setBottomBorderStyle(bottomBorder);
//	    table2cell.setFontSize(10);
//
//        table2row = table2.createRow(20);
//	  	table2cell = table2row.createCell(20, "FRC Upload Date");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Trustee Bank TAT");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Matching Type");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        
//        for (Iterator iterator = npsAccepted.iterator(); iterator.hasNext();) {
//        	NPSAccepted npsAccept = (NPSAccepted) iterator.next();
//			    
//			    table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getFrcuploaddate()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getTbtat()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(npsAccept.getMatchingtype()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20, "");
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,"");
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//
//        }
//         
//        table2.draw();
//        
//        cos.close();
//        
//        OutputStream outputStream = null;
//		File npsAcceptedtempfile = File.createTempFile("All E-NPS Accepted", "pdf"); /////////////
//		
//		outputStream = new FileOutputStream(npsAcceptedtempfile); ////////////
//		//document.save("C:\\Users\\deepak\\Documents\\pdf\\All E-NPS Accepted.pdf");
//        document.save(outputStream);
//        String filepath = npsAcceptedtempfile.getPath(); /////////////
//		String filename = npsAcceptedtempfile.getName(); /////////////
//        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
//        document.close();
//        outputStream.close();
//        return pdfUpload(themeDisplay, filepath, filename, resourceRequest);
//	}
	
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
	public void uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {

		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = uploadPortletRequest.getFileName("eNPSAcceptedFile") + "_" + getRandomNumber();

		File file = uploadPortletRequest.getFile("eNPSAcceptedFile");

		String mimeType = uploadPortletRequest.getContentType("eNPSAcceptedFile");

		String title = fileName;

		String description = "Trustee Bank Annexure 7 - All E-NPS Accepted";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);

		} catch (Exception e) {

			_log.info(e.getMessage());

			_log.error(e);
		}
	}
	
	private int getRandomNumber() {
		Random random = new Random();
		int x = random.nextInt(900) + 100;
		return x;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	@Reference
	NPSAcceptedLocalService npsAcceptedLocalService;

}

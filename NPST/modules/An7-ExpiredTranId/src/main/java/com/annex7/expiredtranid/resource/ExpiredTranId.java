package com.annex7.expiredtranid.resource;

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
import com.liferay.portal.kernel.search.ParseException;
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
import java.util.Locale;
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

import An7.ExpiredTranId.constants.An7ExpiredTranIdPortletKeys;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.line.LineStyle;
import compliance.service.model.ExpTranId;
import compliance.service.service.ExpTranIdLocalService;

@Component(property = { 
		"javax.portlet.name=" + An7ExpiredTranIdPortletKeys.AN7EXPIREDTRANID,
		"mvc.command.name=" + An7ExpiredTranIdPortletKeys.expiredTranId, 
		}, 
service = MVCResourceCommand.class)

public class ExpiredTranId implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(ExpiredTranId.class);

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

		String fileName = uploadPortletRequest.getFileName("expiredTranIdFile");

		File file = uploadPortletRequest.getFile("expiredTranIdFile");

		String mimeType = uploadPortletRequest.getContentType("expiredTranIdFile");

		String title = fileName;

		JSONArray expTranIdJsonArray = JSONFactoryUtil.createJSONArray();
		List<ExpTranId> expTranIdList = new ArrayList<ExpTranId>();
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
		fileNamecell.setCellValue("Sr. No.");
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(18); 
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					
					ExpTranId expTranId = (ExpTranId) expTranIdLocalService.
							createExpTranId(CounterLocalServiceUtil.increment(ExpTranId.class.getName()));
							
					expTranId.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 20; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 4) {
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										_log.info("cell " + cell.getCellType());
										long srno = Long.parseLong(val);
										expTranId.setSr_no(srno);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									expTranId.setPayment_id(val);
								}
								else if (i == 2) {
									expTranId.setRet_ref_no(val);
								} 
								else if (i == 3) {
									expTranId.setSource_acc_no_nodal(val);
								} 
								else if (i == 4) {
									expTranId.setIfsc_source_no(val);
								} 
								else if (i == 5) {
									_log.info("cell " + cell.getCellType());
									String str1 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
									Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(str1);
									expTranId.setPayment_receipt_date(date_1);
								}
								else if (i == 6) {
									expTranId.setPeriod_(val);
								}
								else if (i == 7) {
									expTranId.setOriginal_utr(val);
								}
								else if (i == 8) {
									expTranId.setMode_(val);
								} 
								else if (i == 9) {
									_log.info("cell " + cell.getCellType());
									BigDecimal bg = (BigDecimal) deciFormatter.parse(val);
									expTranId.setUtr_amount(bg.stripTrailingZeros());
								} 
								else if (i == 10) {
									expTranId.setBeneficiary_acc_no(val);
								} 
								else if (i == 11) {
									expTranId.setNps_virtual_acc_no(val);
								} 
								else if (i == 12) {
									expTranId.setNps_acc_name(val);
								} 
								else if (i == 13) {
									_log.info("cell " + cell.getCellType());
									String str2 = formatDate(cell.getDateCellValue(), "dd-MM-yyyy");
									Date date_2 = new SimpleDateFormat("dd-MM-yyyy").parse(str2);
									expTranId.setReturn_date(date_2);
								} 
								else if (i == 14) {
									expTranId.setReturned_utr(val);
								} 
								else if (i == 15) {
									expTranId.setTid(val);
								} 
								else if (i == 16) {
									expTranId.setReason_return(val);
								} 
								else if (i == 17) {
									expTranId.setAdditional_comments(val);
								} 
								else if (i == 18) {
									expTranId.setPao_name(val);
								} 
								else if (i == 19) {
									expTranId.setEmail_id(val);
								} 
								
							}
						} else if(i == 0 && rowCount > 4) {
							break rowloop;
						}
					}
					expTranId.setCreatedon(new Date());
						
						if (isError && rowCount > 4) {
							errorArray.put(errorObj);
							errorRow = xx.createRow(errorRowCount);
							errorRowCount++;
							XSSFCell rowCountCel = errorRow.createCell(1);
							rowCountCel.setCellValue(rowCount);
							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
							cellError.setCellValue(errorObj.getString("msg"));
							isexcelhaserror = true;
						} else if (rowCount > 4) {
							JSONObject expTranIdJsonObject = JSONFactoryUtil.createJSONObject(expTranId.toString());
							expTranIdJsonArray.put(expTranIdJsonObject);
							expTranIdList.add(expTranId);
						}
						rowCount++;
					}
					
				}
			}catch (Exception e) {
				_log.error(e);
			}
			_log.info(expTranIdJsonArray.toString());
			
			if (!isexcelhaserror) {
				expTranIdLocalService.addexpTranId(expTranIdList);
				
				uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
//					resultJsonObject.put("pdfURL",  pdfTable(expTranIdList, themeDisplay, resourceRequest));
					resultJsonObject.put("pdfURL",  "javascript:void(0)");
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
	
//	public String pdfTable(List<ExpTranId> expiredTranId, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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
//        Cell<PDPage> cell = headerRow.createCell(100, "Annexure 7 - Expired Tran Id");
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
//        Cell<PDPage> table2cell = table2row.createCell(20, "Sr. No.");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Payment Id");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Ret. Ref. No.");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Account No (Source a/cof nodal office)");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "IFSC of Source Account No");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        
//        for (Iterator iterator = expiredTranId.iterator(); iterator.hasNext();) {
//        	ExpTranId expTranId = (ExpTranId) iterator.next();
//			
//			 	table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getSrno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getPaymentid()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getRetrefno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getAccountno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getIfscno()));
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
//	    table2row = table2.createRow(20);
//	  	table2cell = table2row.createCell(20, "Payment Receipt Date");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Period");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Original UTR");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Mode");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "UTR Amount");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        
//	    for (Iterator iterator = expiredTranId.iterator(); iterator.hasNext();) {
//	    	ExpTranId expTranId = (ExpTranId) iterator.next();
//			
//			    table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getPaymentreceiptdate()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getPeriod_()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getOriginalutr()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getMode_()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getUtramount()));
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
//	  	table2cell = table2row.createCell(20, "Beneficiary A/c No ");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "NPS Virtual Account No.");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "NPS Sector specific a/c no.");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Return Date");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "ReturnedUTR");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        
//	    for (Iterator iterator = expiredTranId.iterator(); iterator.hasNext();) {
//	    	ExpTranId expTranId = (ExpTranId) iterator.next();
//
//			    table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getBeneficiaryaccno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getNpsvirtualaccno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getNpssectoraccno()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getReturndate()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getReturnedutr()));
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
//	  	table2cell = table2row.createCell(20, "TID (if available)");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Reason of Return");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Additional Comments of NPS Trustee Bank");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "PAO Name");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        table2cell = table2row.createCell(20, "Email Id");
//	  	table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setFontSize(10);
//        table2cell.setFont(fontBold);
//        
//	    for (Iterator iterator = expiredTranId.iterator(); iterator.hasNext();) {
//	    	ExpTranId expTranId = (ExpTranId) iterator.next();
//
//			    table2row = table2.createRow(20);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getTid_if_available()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getReasonofreturn()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getCommentsofnpstb()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getPaoname()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//		        table2cell = table2row.createCell(20,String.valueOf(expTranId.getEmailid()));
//		        table2cell.setAlign(HorizontalAlignment.CENTER);
//
//        }
//         
//        table2.draw();
//        
//        cos.close();
//        
//        OutputStream outputStream = null;
//		File tempfile = File.createTempFile("Expired Tran Id", "pdf"); /////////////
//		
//		outputStream = new FileOutputStream(tempfile); ////////////
//		//document.save("C:\\Users\\deepak\\Documents\\pdf\\Expired Tran Id.pdf");
//        document.save(outputStream);
//        String filepath = tempfile.getPath(); /////////////
//		String filename = tempfile.getName(); /////////////
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

		String fileName = uploadPortletRequest.getFileName("expiredTranIdFile") + "_" + getRandomNumber();

		File file = uploadPortletRequest.getFile("expiredTranIdFile");

		String mimeType = uploadPortletRequest.getContentType("expiredTranIdFile");

		String title = fileName;

		String description = "Trustee Bank Annexure 7 - Expired Tran Id";

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
	ExpTranIdLocalService expTranIdLocalService;
	

}

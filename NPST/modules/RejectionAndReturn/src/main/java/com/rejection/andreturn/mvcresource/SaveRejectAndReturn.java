package com.rejection.andreturn.mvcresource;




import com.daily.average.service.model.Rejectionandreturn;
import com.daily.average.service.service.RejectionandreturnLocalServiceUtil;
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
import com.rejection.andreturn.constants.RejectionAndReturnPortletKeys;

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

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

@Component(property = { "javax.portlet.name=" + RejectionAndReturnPortletKeys.REJECTIONANDRETURN,
"mvc.command.name=/rejectionreturn/save" }, service = MVCResourceCommand.class)
public class SaveRejectAndReturn implements MVCResourceCommand{
	 
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
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		String cra = "";
		if(reportMasterId == 3) {
			cra = "PCRA";
		} else if(reportMasterId == 17){
			cra = "KCRA";
		}else if(reportMasterId == 18){
			cra = "CCRA";
		}

		String fileName = uploadPortletRequest.getFileName("rejectionReturnFile");

		File file = uploadPortletRequest.getFile("rejectionReturnFile");

		String mimeType = uploadPortletRequest.getContentType("rejectionReturnFile");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		RejectionandreturnLocalServiceUtil.deleteRejectionandreturnByReportUploadLogId(reportUploadLogId);
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat deciFormatter = new DecimalFormat(pattern,symbols);
			deciFormatter.setParseBigDecimal(true);
	
			JSONArray rejectionReturnJsonArray = JSONFactoryUtil.createJSONArray();
			//List<String> rejectionReturns = new ArrayList<String>();
			List<Rejectionandreturn> rejectionReturns = new ArrayList<Rejectionandreturn>();
			//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
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
			customerIdcell.setCellValue("Payment Id");
			XSSFCell NPSNumber = xxx.createCell(3);
			NPSNumber.setCellValue("Ret. Ref. No.");
		
			boolean isexcelhaserror = false;
			String sheetName = "Sheet1";
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("Sheet1");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {
					Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						rowLoop:
						while (rows.hasNext()) {
							
							Rejectionandreturn rejectionandreturn = RejectionandreturnLocalServiceUtil.createRejectionandreturn(CounterLocalServiceUtil.increment(Rejectionandreturn.class.getName()));
									
							rejectionandreturn.setCreatedby(userId);
							rejectionandreturn.setReportUploadLogId(reportUploadLogId);
							rejectionandreturn.setCra(cra);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							for (int i = 0; i < 30; i++) {
								XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
		
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
										if (i == 0) {
											if (val != null ) {
												_log.info(val);
												Integer sr_no = 0;
												try {
													sr_no = Integer.parseInt(val);
												} catch (Exception e) {
													 _log.error(e);
													 _log.info("error parsing integer"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
												}
												if (sr_no != null) {
													rejectionandreturn.setSr_no(sr_no);
												}
											}
											
										} 
										else if (i == 1) {
			
											if (val != null) {
			
												Long payment_id = 0l;
												try {
													payment_id = CommonParser.parseLong(val);
												} catch (Exception e) {
													 _log.error(e);
													 _log.info("error parsing long"+val);
														resultJsonObject.put("status", false);
														resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
														return resultJsonObject;
												}
												if (payment_id != null) {
													rejectionandreturn.setPayment_id(payment_id);
												} else {
													errorObj.put("cellno", 3);
													errorObj.put("msg", "is not a number");
												
													isError = true;
												}
											} else {
												errorObj.put("cellno", 3);
												errorObj.put("msg", "it is empty");
												
												isError = true;
											}
										} else if (i == 2) {
			
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												rejectionandreturn.setRet_ref_no(val);
											} else {
												errorObj.put("cellno", 4);
												errorObj.put("msg", "it is empty");
												
												isError = true;
											}
			
										} 
										else if (i == 3) {
											
											rejectionandreturn.setSource_acc_no_nodal(val);
										}
										else if (i == 4) {
											rejectionandreturn.setSource_Acc_No_IFSC(val);
										} 
										else if (i == 5) {
											try {
												Date date= cell.getDateCellValue();
												rejectionandreturn.setPayment_receipt_date(date);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
											}	
										} 
										else if (i == 6) {
											rejectionandreturn.setPeriod(val);
										} 
										else if (i == 7) {
											rejectionandreturn.setUtr_no(val);
										} 
										else if (i == 8) {
											rejectionandreturn.setMode_(val);
										}
										else if (i == 9) {
											//Double UTR_Amt = Double.parseDouble(val);
											try {
												BigDecimal utr_amount =  CommonParser.parseBigDecimal(val);
												rejectionandreturn.setUtr_amount(utr_amount);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 10) {
											
											try {
												Long Beneficiary_Acc_No=CommonParser.parseLong(val);
												rejectionandreturn.setBeneficiary_Acc_No(Beneficiary_Acc_No);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 11) {
											try {
												Integer nps_virtual_acc_no = Integer.parseInt(val);
												rejectionandreturn.setNps_virtual_acc_no(nps_virtual_acc_no);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 12) {
											rejectionandreturn.setNps_acc_name(val);
										} 
										else if (i == 13) {
											try {
												Date date= cell.getDateCellValue();
												rejectionandreturn.setReturn_date(date);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
											}
										}
										else if (i == 14) {
											rejectionandreturn.setReturned_utr(val);
										} 
										else if (i == 15) {
											try {
												Long tid = CommonParser.parseLong(val);
												rejectionandreturn.setTid(tid);
											} catch (NumberFormatException e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} 
										else if (i == 16) {
											rejectionandreturn.setReason_return(val);
										} 
										else if (i == 17) {
											rejectionandreturn.setAdditional_comments(val);
										} 
										else if (i == 18) {
											rejectionandreturn.setPao_name(val);
										}
										else if (i == 19) {
											rejectionandreturn.setEmail_id(val);
										} 
										else if (i == 20) {
											rejectionandreturn.setAddress_line1(val);
										} 
										else if (i == 21) {
											rejectionandreturn.setAddress_line2(val);
										}
										else if (i == 22) {
											try {
												Integer pincode = Integer.parseInt(val);
												rejectionandreturn.setPincode(pincode);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 23) {
											try {
												Long phone_no1 = CommonParser.parseLong(val);
												rejectionandreturn.setPhone_no1(phone_no1);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 24) {
											try {
												Long phone_no2 = CommonParser.parseLong(val);
												rejectionandreturn.setPhone_no2(phone_no2);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing long"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										} 
										else if (i == 25) {
											rejectionandreturn.setSender_receiver_info(val);
										} 
										else if (i == 26) {
											try {
												Integer Return_TAT=Integer.parseInt(val);
												rejectionandreturn.setReturn_tat(Return_TAT);
											} catch (Exception e) {
												 _log.error(e);
												 _log.info("error parsing integer"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
													return resultJsonObject;
											}
										}
										else if (i == 27) {
											rejectionandreturn.setDelay_remakrs(val);
										} 
										else if (i == 28) {
											rejectionandreturn.setOutward_srno(val);
										}
										else if (i == 29) {
											rejectionandreturn.setCourier_consignment_no(val);
										} 
										
									}
								}else if(i==0 && rowCount > 1) {
									break rowLoop;
								}
							}
								
							rejectionandreturn.setCreateDate(new Date());
							
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
								rejectionReturns.add(rejectionandreturn);
							}
							rowCount++;
						}
					}
				}
			}catch (IOException e) {
				 _log.error(e);
				 resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;

			} catch (InvalidFormatException e) {
				_log.error(e);
				resultJsonObject.put("status", false);
				 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
				 return resultJsonObject;

			} 
			_log.info(rejectionReturnJsonArray.toString());
			//_log.info(errorArray);
			
			_log.info("isexcelhaserror" + isexcelhaserror);
			if (!isexcelhaserror) {
				try {
					RejectionandreturnLocalServiceUtil.addRejectionandreturns(rejectionReturns);
				} catch (Exception e1) {
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
					//resultJsonObject.put("pdfURL",  pdfTable(rejectionReturns, themeDisplay, resourceRequest));
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
	
	/*public String pdfTable(List<Rejectionandreturn> rejectionReturns, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
        
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(PDRectangle.LEGAL.getHeight(), PDRectangle.LEGAL.getWidth()));
        //page.setRotation(90);
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
        Cell<PDPage> table3cell = table3row.createCell(4, "Sr. No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Payment Id");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Ret. Ref. No.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Account No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "IFSC of Source Account No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Payment Receipt Date");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Period");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "UTR No.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "Mode");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(4, "UTR Amount");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Beneficiary A/c No ");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "NPS Virtual Account No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "NPS Sector specific a/c Name");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Return Date");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Returned UTR");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "TID ");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Reason of Return");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Additional Comments");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "PAO Name");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Email Id");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Address Line 1");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Address Line 2");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "PINCODE");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Phone : 1");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Phone : 2");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "SENDER TO RECEIVER INFORMATION");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Return TAT");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Delay if any_Remakrs");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Outward Sr.No.for Letter Intimation Courier");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(3, "Courier Consignment No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
       
        
        
        for (Iterator iterator = rejectionReturns.iterator(); iterator.hasNext();) {
        	Rejectionandreturn rejectionReturn = (Rejectionandreturn) iterator.next();
			
			 	table3row = table3.createRow(20);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getSrno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getPaymentid()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getRetrefno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getAccountno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getIfscno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,rejectionReturn.getPmtreciptdate());
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getPeriod()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getUtrno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getMode()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getUtramount()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(4,String.valueOf(rejectionReturn.getBeneficiaryacno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getNpsvirtualacno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getNpssectoracname()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getReturndate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getReturnutr()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getTid()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getReasonofreturn()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getCommentofnps()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getPaoname()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getEmailid()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getAddressi()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getAddressii()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getPincode()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getPhonei()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getPhoneii()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getSendertoreceverinfo()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getReturntat()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getRemarks()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getSrnoforcuriar()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(3,String.valueOf(rejectionReturn.getCuriorno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		       
        }
         
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File rejectionandreturntempfile = File.createTempFile("Annex5", "pdf");
		
		outputStream = new FileOutputStream(rejectionandreturntempfile);
        document.save(outputStream);
        String filepath = rejectionandreturntempfile.getPath();
		String filename = rejectionandreturntempfile.getName();
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

		String fileName =date.getTime()+ uploadPortletRequest.getFileName("rejectionReturnFile");

		File file = uploadPortletRequest.getFile("rejectionReturnFile");

		String mimeType = uploadPortletRequest.getContentType("rejectionReturnFile");

		String title = fileName;

		String description = "Rejection & Return MIS (Electronic)";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry  fileEntry= DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
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
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());
}


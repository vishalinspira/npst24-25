package valuation_rep_sg.mvc;


import com.daily.average.service.model.valuationRepSg;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.daily.average.service.service.WeeklyLocalServiceUtil;
import com.daily.average.service.service.valuationRepSgLocalService;
import com.daily.average.service.service.valuationRepSgLocalServiceUtil;
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
import valuation_rep_sg.constants.Valuation_rep_sgPortletKeys;

@Component(property = { "javax.portlet.name=" + Valuation_rep_sgPortletKeys.VALUATION_REP_SG,
"mvc.command.name=/valuationsg/save" }, service = MVCResourceCommand.class)
public class saveValuationRepSg implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(saveValuationRepSg.class);
	 
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
		_log.info("Inside fileUpload");
		
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
		
		String fileName = uploadPortletRequest.getFileName("valuationsgFile");

		File file = uploadPortletRequest.getFile("valuationsgFile");

		String mimeType = uploadPortletRequest.getContentType("valuationsgFile");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		valuationRepSgLocalServiceUtil.deletevaluationRepSgByReportUploadLogId(reportUploadLogId);
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			JSONArray valuationSgArray = JSONFactoryUtil.createJSONArray();
			List<valuationRepSg> valuationSgList = new ArrayList<valuationRepSg>();
			JSONArray errorArray = JSONFactoryUtil.createJSONArray();
			XSSFWorkbook workbook = null;
			
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
			XSSFCell customerIdcell = xxx.createCell(2);
			customerIdcell.setCellValue("Payment Id");
			XSSFCell NPSNumber = xxx.createCell(3);
			NPSNumber.setCellValue("Ret. Ref. No.");
		
			boolean isexcelhaserror = false;
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet("valuation_sg");
					String sheetName = sheet.getSheetName();
					
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("valuation_sg");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowLoop:
					while (rows.hasNext()) {
						
						valuationRepSg valuationRepSg = (valuationRepSg) valuationRepSgLocalService.
								createvaluationRepSg(CounterLocalServiceUtil.increment(valuationRepSg.class.getName()));
						
						//valuationRepSg valuationRepSg = valuationRepSgLocalServiceUtil.createvaluationRepSg(CounterLocalServiceUtil.increment(valuationRepSg.class.getName()));
						//_log.info("valuationRepSg " + valuationRepSg);
						valuationRepSg.setCreatedby(userId);
						valuationRepSg.setReportUploadLogId(reportUploadLogId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						for (int i = 0; i < 10; i++) {
							XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							if (cell != null) {
	
								DataFormatter formatter = new DataFormatter();
	
								String val = formatter.formatCellValue(cell);
								if (val != null)
									val = val.trim();
								if(rowCount > 1) {
									//_log.info("Val: " + val);
									if (i == 0) {
										valuationRepSg.setClient_code(val);
									} 
									else if (i == 1) {
										valuationRepSg.setIsin(val);
									} 
									else if (i == 2) {
										valuationRepSg.setIsin_desc(val);
									} 
									else if (i == 3) {
										Date date_1=null;
										try {
											date_1 = cell.getDateCellValue();
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheet);
											return resultJsonObject;
										}
										valuationRepSg.setMaturity_date(date_1);
									}
									else if (i == 4) {
										Long longVal=0l;
										try {
											longVal = CommonParser.parseLong(val);
										} catch (Exception e) {
											_log.info("error parsing long--->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											return resultJsonObject;
										}
										valuationRepSg.setFree_securities_rs(longVal);
									} 
									else if (i == 5) {
										Long longVal=0l;
										try {
											longVal = CommonParser.parseLong(val);
										} catch (Exception e) {
											_log.info("error parsing long--->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											return resultJsonObject;
										}
										valuationRepSg.setPledge_value_held_rs(longVal);
									} 
									else if (i == 6) {
										Long longVal=0l;
										try {
											longVal = CommonParser.parseLong(val);
										} catch (Exception e) {
											_log.info("error parsing long--->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											return resultJsonObject;
										}
										valuationRepSg.setSecurities_in_transit_rs(longVal);
									} 
									else if (i == 7) {
										Long longVal=0l;
										try {
											longVal = CommonParser.parseLong(val);
										} catch (Exception e) {
											_log.info("error parsing long--->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											return resultJsonObject;
										}
										valuationRepSg.setTotal_face_value_held_rs(longVal);
									} 
									else if (i == 8) {
										BigDecimal parsedStringValue=null;
										try {
											parsedStringValue = CommonParser.parseBigDecimal(val);
										} catch (Exception e) {
											_log.info("error parsing big decimal--->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
											return resultJsonObject;
										}
										valuationRepSg.setRate(parsedStringValue);
									}
									else if (i == 9) {
										BigDecimal parsedStringValue=null;
										try {
											parsedStringValue = CommonParser.parseBigDecimal(val);
										} catch (Exception e) {
											_log.info("error parsing big decimal--->"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
											return resultJsonObject;
										}
										valuationRepSg.setValue_(parsedStringValue);
									} 
									else if (i == 10) {
										Date date_1 = null;
										try {
											date_1 = cell.getDateCellValue();//new SimpleDateFormat("dd-MM-yyyy").parse(val);
										} catch (Exception e) {
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
										valuationRepSg.setReportingdate(date_1);
									} 
									 
								}
							}else if(i==0 && rowCount > 1) {
								break rowLoop;
							}
						}
						
						valuationRepSg.setCreatedate(new Date());
						
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
							JSONObject valuationRepSgJsonObject = JSONFactoryUtil.createJSONObject(valuationRepSg.toString());
							valuationSgArray.put(valuationRepSgJsonObject);
							valuationSgList.add(valuationRepSg);
						}
						rowCount++;
					}
					_log.info(sheetName +" rowcount"+rowCount);
					}
					
				}
			}catch (Exception e) {
				 _log.error(e);
			} 
			//_log.info(valuationsgArray.toString());
			//_log.info(errorArray);
			
			//_log.info("isexcelhaserror" + isexcelhaserror);
			if (!isexcelhaserror) {
				valuationRepSgLocalService.addValuationRepSg(valuationSgList);
				_log.info("valuationSg datacount"+valuationSgList.size());
				
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

		String fileName =date.getTime()+ uploadPortletRequest.getFileName("valuationsgFile");

		File file = uploadPortletRequest.getFile("valuationsgFile");

		String mimeType = uploadPortletRequest.getContentType("valuationsgFile");

		String title = fileName;

		String description = "Valuation Rep Sg";

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
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	valuationRepSgLocalService valuationRepSgLocalService;
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}


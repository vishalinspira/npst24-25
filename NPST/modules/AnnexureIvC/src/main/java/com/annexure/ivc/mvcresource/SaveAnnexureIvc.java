package com.annexure.ivc.mvcresource;


import com.annexure.ivc.constants.AnnexureIvCPortletKeys;
import com.daily.average.service.model.Annexureiva;
import com.daily.average.service.model.Annexureivb;
import com.daily.average.service.model.Annexureivc;
import com.daily.average.service.service.AnnexureivaLocalServiceUtil;
import com.daily.average.service.service.AnnexureivcLocalServiceUtil;
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

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;

@Component(property = { "javax.portlet.name=" + AnnexureIvCPortletKeys.ANNEXUREIVC,
"mvc.command.name=/annexureivc/save" }, service = MVCResourceCommand.class)
public class SaveAnnexureIvc implements MVCResourceCommand{
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

		String fileName = uploadPortletRequest.getFileName("annexureivcFile");

		File file = uploadPortletRequest.getFile("annexureivcFile");

		String mimeType = uploadPortletRequest.getContentType("annexureivcFile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		JSONArray annexureivcJsonArray = JSONFactoryUtil.createJSONArray();
		List<Annexureivc> annexureivcs = new ArrayList<Annexureivc>();
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
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
		customerIdcell.setCellValue("Sr. No");
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(6);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				while (rows.hasNext()) {
					
					Annexureivc annexureivc = AnnexureivcLocalServiceUtil.createAnnexureivc(CounterLocalServiceUtil.increment(Annexureivc.class.getName()));
							
					annexureivc.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 13; i++) {
						XSSFCell cell = row.getCell(i);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 3) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										Integer sno=Integer.parseInt(val);
										annexureivc.setSno(sno);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									
									Long std_pran=Long.parseLong(val);
									annexureivc.setStd_pran(std_pran);
								}
								else if (i == 2) {
									annexureivc.setType(val);
								} 
								else if (i == 3) {
									annexureivc.setSubscriber_name(val);
								} 
								else if (i == 4) {
									annexureivc.setBeneficiary_name(val);
								} 
								else if (i == 5) {
									BigDecimal  net_amt = (BigDecimal) decimalFormat.parse(val);
									annexureivc.setNet_amt(net_amt);
								}
								else if (i == 6) {
									
									DateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
									annexureivc.setPay_in_date(dateFormat.parse(val));
								}
								else if (i == 7) {
									
									DateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
									annexureivc.setExecution_date(dateFormat.parse(val));
								}
								else if (i == 8) {
									annexureivc.setUtr_no(val);
								} 
								else if (i == 9) {
									annexureivc.setInitial_status(val);
								} 
								else if (i == 10) {
									annexureivc.setReturn_reason(val);
								}
								else if (i == 11) {
									
									annexureivc.setRemarks(val);
								}
								else if (i == 12) {
									annexureivc.setDelay(val);
								} 
							}
						}
					}
					annexureivc.setCreatedate(new Date());
						
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
						JSONObject annexureivcJsonObject = JSONFactoryUtil.createJSONObject(annexureivc.toString());
						annexureivcJsonArray.put(annexureivcJsonObject);
						annexureivcs.add(annexureivc);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			_log.error(e);
		}
		_log.info(annexureivcJsonArray.toString());
		
		if (!isexcelhaserror) {
			AnnexureivcLocalServiceUtil.addAnnexureivcs(annexureivcs);
			
			uploadFILETOFOLDER(themeDisplay, resourceRequest);
			try {
				errorExcel.close();
				//resultJsonObject.put("pdfURL",  pdfTable(annexureivcs, themeDisplay, resourceRequest));
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
	
	/*public String pdfTable(List<Annexureivc> annexureivcs, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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
        Cell<PDPage> table3cell = table3row.createCell(7, "Sr. No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(7, "STD_PRAN");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(7, "Type");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(7, "PRAN_NAME");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Beneficiary Name");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "net-amount");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Pay-In Date");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Execution");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "UTR No.");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Initial Status");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Return Reason");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Additional Comments");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(8, "Delay");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        
        
        for (Iterator iterator = annexureivcs.iterator(); iterator.hasNext();) {
        	Annexureivc annexureivc = (Annexureivc) iterator.next();
			
			 	table3row = table3.createRow(20);
		        table3cell = table3row.createCell(7,String.valueOf(annexureivc.getSrno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(7,String.valueOf(annexureivc.getStdpran()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(7,String.valueOf(annexureivc.getType()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(7,String.valueOf(annexureivc.getPranname()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getBeneficiaryname()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getNetamount()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getPayindate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getExecutiondate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getUtrno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getInitialstatus()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getReturnreason()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getComment()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(8,String.valueOf(annexureivc.getDelay()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		       
        }
        
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File annexureivctempfile = File.createTempFile("Annexure-4C", "pdf");
		
		outputStream = new FileOutputStream(annexureivctempfile);
        document.save(outputStream);
        String filepath = annexureivctempfile.getPath();
		String filename = annexureivctempfile.getName();
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
	}
*/
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

		String fileName = uploadPortletRequest.getFileName("annexureivcFile");

		File file = uploadPortletRequest.getFile("annexureivcFile");

		String mimeType = uploadPortletRequest.getContentType("annexureivcFile");

		String title = fileName;

		String description = "annexureivcFile";

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
	
	Log _log = LogFactoryUtil.getLog(getClass());

}


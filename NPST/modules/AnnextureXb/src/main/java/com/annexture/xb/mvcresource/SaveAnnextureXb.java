package com.annexture.xb.mvcresource;

import com.annexture.xb.constants.AnnextureXbPortletKeys;
import com.daily.average.service.model.AnnextureXa;
import com.daily.average.service.service.AnnextureXaLocalServiceUtil;
import com.daily.average.service.service.DailyAverageLocalServiceUtil;
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
import java.text.DateFormat;
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

/*import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;*/
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { "javax.portlet.name=" + AnnextureXbPortletKeys.ANNEXTUREXA,
"mvc.command.name=/annexturexa/save" }, service = MVCResourceCommand.class)

public class SaveAnnextureXb implements MVCResourceCommand{
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
		_log.info("In SaveAnnextureXb serveResource ");
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
		
		String fileName = uploadPortletRequest.getFileName("annexturexaFile");

		File file = uploadPortletRequest.getFile("annexturexaFile");

		String mimeType = uploadPortletRequest.getContentType("annexturexaFile");

		String title = fileName;
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		AnnextureXaLocalServiceUtil.deleteAnnextureXaByReportUploadLogId(reportUploadLogId);
		
		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
			if(resultJsonObject.getBoolean("status")) {
			
			JSONArray annextureXaJsonArray = JSONFactoryUtil.createJSONArray();
			List<AnnextureXa> annextureXas = new ArrayList<AnnextureXa>();
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
			customerIdcell.setCellValue("Sr. No");
			
			
			boolean isexcelhaserror = false;
			String sheetName = "Email_Complaint_Tracker";
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("Email_Complaint_Tracker");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					} else {
					Iterator<Row> rows = sheet.rowIterator();
					int rowCount = 1;
					int errorRowCount = 2;
					
					rowLoop:
					while (rows.hasNext()) {
						
						AnnextureXa annextureXa = AnnextureXaLocalServiceUtil.createAnnextureXa(CounterLocalServiceUtil.increment(AnnextureXa.class.getName()));
						
						annextureXa.setReportUploadLogId(reportUploadLogId);
						annextureXa.setCreatedby(userId);
						
						JSONObject errorObj = JSONFactoryUtil.createJSONObject();
						errorObj.put("rownum", rowCount);
						boolean isError = false;
						XSSFRow row = (XSSFRow) rows.next();
						XSSFRow errorRow = null;
						
						for (int i = 0; i < 7; i++) {
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
												Long SNo=CommonParser.parseLong(val);
												annextureXa.setSno(SNo);
											} catch (Exception e) {
												_log.info("error parsing long"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
										} else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "it is not a number");
											
											isError = true;
										}
									}
									else if (i == 1) {
										
										try {
											_log.info(val);
											Date date= cell.getDateCellValue();
											annextureXa.setReceipt_date(date);
										} catch (Exception e) {
											_log.error(e);
											_log.info("error parsing date"+val);
											resultJsonObject.put("status", false);
											resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
											return resultJsonObject;
										}
									}
									else if (i == 2) {
										annextureXa.setFrom(val);
									} 
									else if (i == 3) {
										annextureXa.setSubject(val);
									} 
									else if (i == 4) {
										annextureXa.setGist_case(val);
									} 
									else if (i == 5) {
										annextureXa.setRevert_containt(val);
									}
									else if (i == 6) {
										
										try {
											_log.info(val);
											Date date= cell.getDateCellValue();
											annextureXa.setResolve_date(date);
										} catch (Exception e) {
											_log.error(e);
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
							annextureXa.setCreatedate(new Date());
							
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
								//JSONObject annextureXaJsonObject = JSONFactoryUtil.createJSONObject(annextureXa.toString());
								//annextureXaJsonArray.put(annextureXaJsonObject);
								annextureXas.add(annextureXa);
							}
							rowCount++;
						}
					_log.info(sheetName +" rowcount"+rowCount);
					}
						
					}
				}catch (Exception e) {
					_log.error(e);
					resultJsonObject.put("status", false);
					 resultJsonObject.put("msg", CommonParser.fileExceptionMsg+sheetName);
					 return resultJsonObject;
				}
				_log.info(annextureXaJsonArray.toString());
				
				if (!isexcelhaserror) {
					AnnextureXaLocalServiceUtil.addAnnexturexas(annextureXas);
					_log.info(sheetName +" datacount"+annextureXas.size());
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
					try {
						errorExcel.close();
						
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(annextureXas, themeDisplay, resourceRequest));
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
	/*public String pdfTable(List<AnnextureXa> annextureXas, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws IOException {
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
        Cell<PDPage> table3cell = table3row.createCell(14, "Sr. No");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Receipt date");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "From");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15, "Subject");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(15, "Gist of the case");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Revert Containt");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        table3cell = table3row.createCell(14, "Resolve date");
        table3cell.setAlign(HorizontalAlignment.CENTER);
        table3cell.setFont(fontBold);
        
        for (Iterator iterator = annextureXas.iterator(); iterator.hasNext();) {
        	AnnextureXa annextureXa= (AnnextureXa) iterator.next();
			
			 	table3row = table3.createRow(20);
		        table3cell = table3row.createCell(14,String.valueOf(annextureXa.getSrno()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(annextureXa.getReceiptdate()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(annextureXa.getFrom()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(15,String.valueOf(annextureXa.getSubject()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(15,String.valueOf(annextureXa.getGistofthecase()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(annextureXa.getRevertcontaint()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        table3cell = table3row.createCell(14,String.valueOf(annextureXa.getRevertcontaint()));
		        table3cell.setAlign(HorizontalAlignment.CENTER);
		        
        }
        
        table3.draw();
        
        cos.close();
        
        OutputStream outputStream = null;
		File annextureXatempfile = File.createTempFile("Annexure10b", "pdf");
		
		outputStream = new FileOutputStream(annextureXatempfile);
        document.save(outputStream);
        String filepath = annextureXatempfile.getPath();
		String filename = annextureXatempfile.getName();
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

			String fileName =date.getTime()+ uploadPortletRequest.getFileName("annexturexaFile");

			File file = uploadPortletRequest.getFile("annexturexaFile");

			String mimeType = uploadPortletRequest.getContentType("annexturexaFile");

			String title = fileName;

			String description = "Email Complaint Tracker";

			long repositoryId = themeDisplay.getScopeGroupId();
		

			try {

				Folder folder = getFolder(themeDisplay);

				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
						resourceRequest);

				FileEntry fileEntry =DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
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
				folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
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


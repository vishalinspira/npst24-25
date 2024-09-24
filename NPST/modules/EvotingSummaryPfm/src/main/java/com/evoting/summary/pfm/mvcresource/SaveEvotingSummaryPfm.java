package com.evoting.summary.pfm.mvcresource;

import com.daily.average.service.model.EVotingSummaryPfm;
import com.daily.average.service.service.EVotingSummaryPfmLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
import com.evoting.summary.pfm.constants.evotingsummarypfmPortletKeys;
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

@Component(property = { "javax.portlet.name=" + evotingsummarypfmPortletKeys.EVOTINGSUMMARYPFM,
"mvc.command.name=/evotingsummary/save" }, service = MVCResourceCommand.class)

public class SaveEvotingSummaryPfm implements MVCResourceCommand{
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
		
		
		String fileName = uploadPortletRequest.getFileName("evotingsummaryFile");

		File file = uploadPortletRequest.getFile("evotingsummaryFile");

		String mimeType = uploadPortletRequest.getContentType("evotingsummaryFile");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		//if(mimeType.equalsIgnoreCase("application/vnd.openxmlformats-") || mimeType.equalsIgnoreCase("officedocument.spreadsheetml.sheet")) {
			String title = fileName;
			String sheetName="Quarterly Evoting";
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			
			JSONArray eVotingSummaryPfmJsonArray = JSONFactoryUtil.createJSONArray();
			List<EVotingSummaryPfm> eVotingSummaryPfms = new ArrayList<EVotingSummaryPfm>();
			
			resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
			if(resultJsonObject.getBoolean("status")) {
			
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
			if(resultJsonObject.getBoolean("status")) {
				EVotingSummaryPfmLocalServiceUtil.deleteEVotingSummaryPfmByReportUploadLogId(reportUploadLogId);
			
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					
					if (sheet == null) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
						errorSheetNameList.put("Quarterly Evoting");
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
					}else {
					 
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						rowLoop:
						while (rows.hasNext()) {
							
							EVotingSummaryPfm eVotingSummaryPfm =EVotingSummaryPfmLocalServiceUtil.createEVotingSummaryPfm(CounterLocalServiceUtil.increment(EVotingSummaryPfm.class.getName()));
									
							eVotingSummaryPfm.setCreatedby(userId);
							eVotingSummaryPfm.setReportUploadLogId(reportUploadLogId);
							
							JSONObject errorObj = JSONFactoryUtil.createJSONObject();
							errorObj.put("rownum", rowCount);
							boolean isError = false;
							XSSFRow row = (XSSFRow) rows.next();
							XSSFRow errorRow = null;
							
							for (int i = 0; i <= 12; i++) {
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
													Date dateFormat= cell.getDateCellValue();
													eVotingSummaryPfm.setMeeting_date(dateFormat);
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
													return resultJsonObject;
												}
											} else {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg+sheetName);
												return resultJsonObject;
											}
										}
										else if (i == 1) {
											
											eVotingSummaryPfm.setIsin(val);
										}
										else if (i == 2) {
											
											eVotingSummaryPfm.setCompany_name(val);
										}
										else if (i == 3) {
											eVotingSummaryPfm.setType_of_meetings(val);
										} 
										else if (i == 4) {
											eVotingSummaryPfm.setResolution_by_shareholder(val);
										}
										else if (i == 5) {
											eVotingSummaryPfm.setResolution_description(val);
										} 
										else if (i == 6) {
											eVotingSummaryPfm.setInvestee_company_recomm(val);
										}
										else if (i == 7) {
											eVotingSummaryPfm.setPf_voting_recomm(val);
										} 
										else if(i == 8) {
											eVotingSummaryPfm.setPf_rationale_recomm(val);
										}
										else if (i == 9) {
											eVotingSummaryPfm.setFinal_vote(val);
										}
										else if (i == 10) {
											eVotingSummaryPfm.setPension_fund_name(val);
										}
										else if (i == 11) {
											try {
												BigDecimal cg =CommonParser.parseBigDecimal(val);
												eVotingSummaryPfm.setSes_resid(cg);
											}catch (Exception e) {
												_log.info("error parsing number"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg+sheetName);
												return resultJsonObject;
											}
											
										}
										else if (i == 12) {
											try {
												Date date= cell.getDateCellValue();
												eVotingSummaryPfm.setQuarter_ended_date(date);
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
							eVotingSummaryPfm.setCreatedate(new Date());
							
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
	//							JSONObject eVotingSummaryPfmJsonObject = JSONFactoryUtil.createJSONObject(eVotingSummaryPfm.toString());
	//							eVotingSummaryPfmJsonArray.put(eVotingSummaryPfmJsonObject);
								eVotingSummaryPfms.add(eVotingSummaryPfm);
							}
							rowCount++;
						}
						_log.info(sheetName +" rowcount"+rowCount);
					 	
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			}
			//_log.info(eVotingSummaryPfmJsonArray.toString());
			
			if (!isexcelhaserror) {
				try {
					EVotingSummaryPfmLocalServiceUtil.addEVotingSummaryPfms(eVotingSummaryPfms);
					_log.info(sheetName +" datacount"+eVotingSummaryPfms.size());
				} catch (Exception e1) {
					_log.error(e1);
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					return resultJsonObject;
				}
				
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
					
					//resultJsonObject.put("pdfURL",  pdfTable(eVotingSummaryPfms, themeDisplay, resourceRequest));
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
		/*}else {
			_log.info("mime type error "+mimeType);
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
			return resultJsonObject;
		}*/
			}
	}
		return resultJsonObject;
	}
	
	
	@SuppressWarnings("deprecation")
	public String pdfUpload1(ThemeDisplay themeDisplay, String filepath, String filename,
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("evotingsummaryFile");

		File file = uploadPortletRequest.getFile("evotingsummaryFile");

		String mimeType = uploadPortletRequest.getContentType("evotingsummaryFile");

		String title = fileName;

		String description = "evotingsummaryFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
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
	Log _log = LogFactoryUtil.getLog(getClass());
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}

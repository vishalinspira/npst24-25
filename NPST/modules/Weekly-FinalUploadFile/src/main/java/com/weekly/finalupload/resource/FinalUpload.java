package com.weekly.finalupload.resource;

import com.daily.average.service.service.ReportUploadLogNPSTLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Weekly.FinalUploadFile.constants.WeeklyFinalUploadFilePortletKeys;
import compliance.service.model.FinalUploadFile;
import compliance.service.service.FinalUploadFileLocalService;

@Component(property = { 
		"javax.portlet.name=" + WeeklyFinalUploadFilePortletKeys.WEEKLYFINALUPLOADFILE,
		"mvc.command.name=" + WeeklyFinalUploadFilePortletKeys.finalUpload, 
		}, 
service = MVCResourceCommand.class)

public class FinalUpload implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(FinalUpload.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload_1(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		
		return false;
	}
	
	public JSONObject fileUpload_1(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
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
		
		String fileName = uploadPortletRequest.getFileName("finalUploadFile");

		File file = uploadPortletRequest.getFile("finalUploadFile");

		String mimeType = uploadPortletRequest.getContentType("finalUploadFile");

		String title = fileName;

		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;
		
		//ETier_1 et1 = new ETier_1();
		
		/**
		 * Create error excel
		 */
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
		XSSFRow xxx = xx.createRow(1);
		XSSFCell rowNumCel = xxx.createCell(1);
		rowNumCel.setCellValue("RowNum");
		XSSFCell fileNamecell = xxx.createCell(2);
		fileNamecell.setCellValue("Pension Fund");
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				//XSSFSheet sheet = workbook.getSheetAt(0);
				
				//Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				int sheetCount = 0;
				
				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
				_log.info(sheetIterator.hasNext());
				
				while(sheetIterator.hasNext()) {
					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
					_log.info(sheets.getSheetName());
					Iterator<Row> rows = sheets.iterator();
					_log.info(rows.next().getCell(1));
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					//XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					if(sheetCount == 0 || sheetCount == 1 || sheetCount == 2 || 
							sheetCount == 3 || sheetCount == 4 || sheetCount == 5 || 
							sheetCount == 6 || sheetCount == 7 || sheetCount == 8 || 
							sheetCount == 9 || sheetCount == 10 || sheetCount == 11 || 
							sheetCount == 12  && sheets != null) {
						finalUploadFileInfo(themeDisplay, rows, resourceRequest);
					} 

					if (isError && rowCount > 2) {
						errorArray.put(errorObj);
						errorRow = xx.createRow(errorRowCount);
						errorRowCount++;
						XSSFCell rowCountCel = errorRow.createCell(1);
						rowCountCel.setCellValue(rowCount);
						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
						cellError.setCellValue(errorObj.getString("msg"));
						isexcelhaserror = true;
					}
					sheetCount++;
				}
					
				}
			}catch (Exception e) {
				 _log.error(e);
			}

		
			if (!isexcelhaserror) {
				
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
			return resultJsonObject;
		}

	public JSONObject finalUploadFileInfo(ThemeDisplay themeDisplay, Iterator<Row> rows, ResourceRequest resourceRequest) throws InvalidFormatException, IOException, JSONException {
		
		JSONObject finalUploadJsonObject = null;
		JSONArray finalUploadJsonArray = JSONFactoryUtil.createJSONArray();
		List<FinalUploadFile> finalUploadList = new ArrayList<FinalUploadFile>();
		
		int rowCount = 1;
		//int errorRowCount = 2;
		
			rowloop:
			while (rows.hasNext()) {
				
				FinalUploadFile finalUpload = (FinalUploadFile) finalUploadFileLocalService.
						createFinalUploadFile(CounterLocalServiceUtil.increment(FinalUploadFile.class.getName()));
				
				finalUpload.setCreatedby(themeDisplay.getUserId());
				
//				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
//				errorObj.put("rownum", rowCount);
//				boolean isError = false;
//				row = (XSSFRow) rows.next();
//				XSSFRow errorRow = null;
				
				XSSFRow row = (XSSFRow) rows.next();
				
				int lastColumn = Math.max(row.getLastCellNum(), 0);
				
				for (int i = 0; i < lastColumn; i++) {
					//XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					
					XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					
					if (cell != null) {

						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (val != null)
							val = val.trim();
						if(rowCount > 0) {
							_log.info("Val: " + val);
							if (i == 1) {
								if (val != null && Validator.isNotNull(val) && val.length() > 0) {
									//Long srno=Long.parseLong(val);
									finalUpload.setPensionfund(val);
								}
							}
							else if (i == 2) {
								_log.info("cell2 " + cell.getCellType());
								finalUpload.setInceptiondate(val);
							}
							else if (i == 3) {
								finalUpload.setAum(val);
							} 
							else if (i == 4) {
								finalUpload.setSubscribers(val);
							} 
							else if (i == 5) {
								finalUpload.setNav(val);
							} 
							else if (i == 6) {
								finalUpload.setReturn_1year(val);
							}
							else if (i == 7) {
								finalUpload.setReturn_3year(val);
							}
							else if (i == 8) {
								finalUpload.setReturn_5year(val);
							}
							else if (i == 9) {
								finalUpload.setReturn_7year(val);
							} 
							else if (i == 10) {
								finalUpload.setReturn_10year(val);
							} 
							else if (i == 11) {
								finalUpload.setReturninception(val);
							} 
							
						}
					} else if(i == 1 && rowCount > 0) {
						_log.info("outside if");
						break rowloop;
					}
				}
				finalUpload.setCreatedon(new Date());
				
				if (rowCount > 0) {
					finalUploadJsonObject = JSONFactoryUtil.createJSONObject(finalUpload.toString());
					finalUploadJsonArray.put(finalUploadJsonObject);
					finalUploadList.add(finalUpload);
				}
				rowCount++;

			}
		
		try {
			finalUploadFileLocalService.addFinalUploadFile(finalUploadList);
			//eTierIJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
		} catch(Exception e) {
			 _log.error(e);
		}
		
		_log.info(finalUploadJsonArray.toString());
		
		return finalUploadJsonObject;
		
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("finalUploadFile");

		File file = uploadPortletRequest.getFile("finalUploadFile");

		String mimeType = uploadPortletRequest.getContentType("finalUploadFile");

		String title = fileName;

		String description = "finalUploadFile";

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
		ReportUploadLogNPSTLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	FinalUploadFileLocalService finalUploadFileLocalService;
	
	

}

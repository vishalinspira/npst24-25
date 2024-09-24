package com.esc.grievances.npst.resource;


import com.daily.average.service.model.EscGrievancesNpst;
import com.daily.average.service.service.EscGrievancesNpstLocalServiceUtil;
import com.esc.grievances.npst.constants.EscGrievancesNpstPortletKeys;
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

import nps.email.api.api.ExcelValidationAn10Api;
import nps.email.api.api.ExcelValidationAn7Api;

@Component(property = { "javax.portlet.name=" + EscGrievancesNpstPortletKeys.ESCGRIEVANCESNPST,
"mvc.command.name=/escgrievancesnpst/save" }, service = MVCResourceCommand.class)
public class SaveEscGrievancesNpst implements MVCResourceCommand{
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

		String fileName = uploadPortletRequest.getFileName("escgrievancesnpstFile");

		File file = uploadPortletRequest.getFile("escgrievancesnpstFile");
		excelValidationAn10Api.validateExcelFile(file,resourceRequest);

		String mimeType = uploadPortletRequest.getContentType("escgrievancesnpstFile");

		String title = fileName;

		JSONArray escgrievancesnpstJsonArray = JSONFactoryUtil.createJSONArray();
		List<EscGrievancesNpst> escgrievancesnpsts = new ArrayList<EscGrievancesNpst>();
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
		customerIdcell.setCellValue("Sr.No");
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(1);
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowLoop:
				while (rows.hasNext()) {
					
					EscGrievancesNpst escgrievancesnpst = EscGrievancesNpstLocalServiceUtil.createEscGrievancesNpst(CounterLocalServiceUtil.increment(EscGrievancesNpst.class.getName()));
							
					escgrievancesnpst.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 19; i++) {
						XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							_log.info(val);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										escgrievancesnpst.setSr_no(val);
									} else {
										errorObj.put("cellno", 2);
										errorObj.put("msg", "it is not a number");
										
										isError = true;
									}
								}
								else if (i == 1) {
									escgrievancesnpst.setToken_number(val);
								}
								else if (i == 2) {
									escgrievancesnpst.setPran(val);
								} 
								else if (i == 3) {
									escgrievancesnpst.setSubscriber_name(val);
								}
								else if (i == 4) {
									escgrievancesnpst.setEntity_name(val);
								} 
								else if (i == 5) {
									escgrievancesnpst.setPao_reg_no(val);
								} 
								else if (i == 6) {
									escgrievancesnpst.setPr_aO_name(val);
								}
								else if (i == 7) {
									escgrievancesnpst.setMinistry_name(val);
								}
								else if (i == 8) {
									escgrievancesnpst.setPao_type(val);
								}
								else if (i == 9) {
									escgrievancesnpst.setSector(val);
								} 
								else if (i == 10) {
									escgrievancesnpst.setEarlier_state(val);
								}
								else if (i == 11) {
									escgrievancesnpst.setResolution_date(val);
								} 								
								else if (i == 12) {
									escgrievancesnpst.setAssign_date(val);
								} 
								else if (i == 13) {
									escgrievancesnpst.setStandard_date(val);
								}
								else if (i == 14) {
									escgrievancesnpst.setTat(val);
								} 
								else if (i == 15) {
									escgrievancesnpst.setBucket(val);
								}
								else if (i == 16) {
									escgrievancesnpst.setEscalation_date(val);
								} 
								else if (i == 17) {
									escgrievancesnpst.setFirst_follow(val);
								}
								else if (i == 18) {
									escgrievancesnpst.setEecond_follow(val);
								} 								
							}
						}else if(i==0 && rowCount > 1) {
							break rowLoop;
						}
					}
					escgrievancesnpst.setCreatedate(new Date());
					
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
						JSONObject escgrievancesnpstJsonObject = JSONFactoryUtil.createJSONObject(escgrievancesnpst.toString());
						escgrievancesnpstJsonArray.put(escgrievancesnpstJsonObject);
						escgrievancesnpsts.add(escgrievancesnpst);
					}
					rowCount++;
				}
				
			}
		}catch (Exception e) {
			 _log.error(e);
		}
		_log.info(escgrievancesnpstJsonArray.toString());
		
		if (!isexcelhaserror) {
			EscGrievancesNpstLocalServiceUtil.addEscGrievancesNpsts(escgrievancesnpsts);
			
			uploadFILETOFOLDER(themeDisplay, resourceRequest);
			try {
				errorExcel.close();
				//resultJsonObject.put("pdfURL",  pdfTable(escgrievancesnpsts, themeDisplay, resourceRequest));
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
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("escgrievancesnpstFile");

		File file = uploadPortletRequest.getFile("escgrievancesnpstFile");

		String mimeType = uploadPortletRequest.getContentType("escgrievancesnpstFile");

		String title = fileName;

		String description = "escgrievancesnpstFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Grievances");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
	Log _log = LogFactoryUtil.getLog(getClass());

}

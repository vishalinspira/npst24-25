package ANNEXURE5A.resource;

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
import java.util.Random;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

import ANNEXURE5A.constants.ANNEXURE5APortletKeys;
import compliance.service.model.AnnexureVA;
import compliance.service.service.AnnexureVALocalServiceUtil;

@Component(property = { 
		"javax.portlet.name=" + ANNEXURE5APortletKeys.ANNEXURE5A,
		"mvc.command.name=" + ANNEXURE5APortletKeys.saveANNEXURE5A, 
		}, 
service = MVCResourceCommand.class)

public class Annexure5a implements MVCResourceCommand {
	private static Log _log = LogFactoryUtil.getLog(Annexure5a.class);
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("welcome to serveresource::::::::::::");
		_log.info("Inside serve Resource method");
		
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

		String fileName = uploadPortletRequest.getFileName("annexurea");

		File file = uploadPortletRequest.getFile("annexurea");

		String mimeType = uploadPortletRequest.getContentType("annexurea");

		String title = fileName;

		JSONArray annexureArray = JSONFactoryUtil.createJSONArray();
		List<AnnexureVA> annexureList = new ArrayList<AnnexureVA>();
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
		XSSFCell fileNamecell = xxx.createCell(2);
		fileNamecell.setCellValue("name of bills");
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				int sheetcount = 0;
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while(sheets.hasNext())
				{
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					if(sheetcount>0) {
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					
					AnnexureVA annexuredate= AnnexureVALocalServiceUtil.createAnnexureVA(CounterLocalServiceUtil.increment(AnnexureVA.class.getName()));	
					
					annexuredate.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 17; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell != null) {
							DataFormatter formatter = new DataFormatter();
							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 5) {
								_log.info("Val: " + val);
								_log.info("rowCount:::::::::::"+rowCount);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										//Long srno=Long.parseLong(val);
										annexuredate.setNamefbill(val);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									
									annexuredate.setNoofbill(val);
								}
								else if (i == 2) {
									annexuredate.setPfs_schemesName(val);
								} 
								else if (i == 3) {
									annexuredate.setPfs_ascharges(val);
								} 
								else if (i == 4) {
									annexuredate.setPfs_custodycharges(val);
								} 
								else if (i == 5) {
									annexuredate.setPfs_ccilcharges(val);
								}
								else if (i == 6) {
									annexuredate.setPfs_cbricscharges(val);
								}
								else if (i == 7) {
									
									annexuredate.setPfs_nsdlcharges(val);
								}
                                 else if (i == 8) {
                                	 annexuredate.setPfs_sebicharges(val);
								}
								else if (i == 9) {
									annexuredate.setTotalcharges(val);
								}
								else if (i == 10) {
									annexuredate.setShcil_ascharges(val);
								}  
								else if (i == 11) {
									annexuredate.setShcil_custodycharges(val);
								}  
								else if (i == 12) {
									annexuredate.setShcil_ccilcharges(val);
								}  
								else if (i == 13) {
									annexuredate.setShcil_cbricscharges(val);
								}  
								else if (i == 14) {
									annexuredate.setShcil_nsdlcharges(val);
								}  
								else if (i == 15) {
									annexuredate.setShcil_sebicharges(val);
								}  
								else if (i == 16) {
									annexuredate.setGrosspaybleshcil((long) cell.getNumericCellValue());
								}  
							}
						} else if(i == 0 && rowCount > 5) {
							break rowloop;
						}
					}
					annexuredate.setCreatedate(new Date());
						
						if (isError && rowCount > 5) {
							errorArray.put(errorObj);
							errorRow = xx.createRow(errorRowCount);
							errorRowCount++;
							XSSFCell rowCountCel = errorRow.createCell(1);
							rowCountCel.setCellValue(rowCount);
							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
							cellError.setCellValue(errorObj.getString("msg"));
							isexcelhaserror = true;
						} else if (rowCount > 5) {
							JSONObject annexurevaarray = JSONFactoryUtil.createJSONObject(annexuredate.toString());
							annexureArray.put(annexurevaarray);
							annexureList.add(annexuredate);
						}
						rowCount++;
					}
					}
						sheetcount++;	
				}	
				}
			}catch (Exception e) {
				_log.error(e);
			}
			_log.info(annexureArray.toString());
			
			if (!isexcelhaserror) {
			   AnnexureVALocalServiceUtil.addAnnexureVA(annexureList);  
				uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
					//resultJsonObject.put("pdfURL",  pdfTable(allElectronics, themeDisplay, resourceRequest));
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
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,ResourceRequest resourceRequest) {

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

		String fileName = uploadPortletRequest.getFileName("annexurea") + "_" + getRandomNumber();

		File file = uploadPortletRequest.getFile("annexurea");

		String mimeType = uploadPortletRequest.getContentType("annexurea");

		String title = fileName;

		String description = "annexurea";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Weekly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
}

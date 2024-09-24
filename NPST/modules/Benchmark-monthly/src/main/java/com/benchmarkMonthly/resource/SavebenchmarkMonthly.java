package com.benchmarkMonthly.resource;

import com.benchmarkMonthly.constants.BenchmarkMonthlyPortletKeys;
import com.daily.average.service.model.BenchSchemeReturnsMonthly;
import com.daily.average.service.model.BenchSchemeReturnsSinceMonthly;
import com.daily.average.service.service.BenchSchemeReturnsMonthlyLocalServiceUtil;
import com.daily.average.service.service.BenchSchemeReturnsSinceMonthlyLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
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
import java.text.DateFormat;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

import nps.common.service.util.CommonParser;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+BenchmarkMonthlyPortletKeys.BENCHMARKMONTHLY,
			"mvc.command.name=/saveBenchmarkMonthly/data",
		}, 
		service = MVCResourceCommand.class
	)
public class SavebenchmarkMonthly implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		_log.info("hi ");
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
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("BenchmarkFileMonthly");

		File file = uploadPortletRequest.getFile("BenchmarkFileMonthly");

		String mimeType = uploadPortletRequest.getContentType("BenchmarkFileMonthly");

		String title = fileName;

		List<BenchSchemeReturnsMonthly> dailyAverages2 = new ArrayList<BenchSchemeReturnsMonthly>();
		List<BenchSchemeReturnsSinceMonthly> dailyAverages3 = new ArrayList<BenchSchemeReturnsSinceMonthly>();
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
		fileNamecell.setCellValue("Sr. No.");
		boolean isexcelhaserror = false;
		boolean isNoError = false;
		int sheetCount = 0;
		BenchSchemeReturnsMonthlyLocalServiceUtil.deleteBenchSchemeReturnsMonthlyByReportUploadLogId(reportUploadLogId);
		BenchSchemeReturnsSinceMonthlyLocalServiceUtil.deleteBenchSchemeReturnsSinceMonthlyByReportUploadLogId(reportUploadLogId);;
		
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheets = workbook.sheetIterator();
				
				//sheet validate
				List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook);
				
				if (errorlist.size() > 0) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
					resultJsonObject.put("errorSheetNameList", arrayList);
					try {
						errorExcel.close();
					} catch (IOException e) {
						 _log.error(e);
					}
					// return
					return resultJsonObject;
				}
				
				
				while(sheets.hasNext()) {
				XSSFSheet sheet = (XSSFSheet) sheets.next();
				String sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				
				rowLoop:
				while (rows.hasNext()) {
					BenchSchemeReturnsMonthly bsr = null;
					BenchSchemeReturnsSinceMonthly bsrs= null;
					
					
					try {
						if(sheetCount == 0) {
							bsr = BenchSchemeReturnsMonthlyLocalServiceUtil.createBenchSchemeReturnsMonthly(CounterLocalServiceUtil.increment(BenchSchemeReturnsMonthly.class.getName()));
							bsr.setCreatedby(userId);
							bsr.setReportUploadLogId(reportUploadLogId);
						}else if(sheetCount == 1) {
							bsrs = BenchSchemeReturnsSinceMonthlyLocalServiceUtil.createBenchSchemeReturnsSinceMonthly(CounterLocalServiceUtil.increment(BenchSchemeReturnsSinceMonthly.class.getName()));
							bsrs.setCreatedby(userId);
							bsrs.setReportUploadLogId(reportUploadLogId);
						}
					} catch (Exception e) {
						_log.error("exception in creating object : "+e.getMessage());
					}
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					for (int i = 0; i < 15; i++) {
						XSSFCell cell = row.getCell(i);
						if (cell != null) {

							DataFormatter formatter = new DataFormatter();

							String val = formatter.formatCellValue(cell);
							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								if(sheetCount == 0 && bsr != null) {
									//sheet 1
									if (i == 0) {
										if(val == null) {
											break rowLoop;
										}
										
										if (val != null) {
											bsr.setReturns(val);
										} 
									}else if (i == 1 ) {
										try {
											Date date_1 = CommonParser.parseDate(val,cell);
											bsr.setReturn_as_on_date(date_1);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											errorObj.put("status", false);
											errorObj.put("msg", CommonParser.dateExceptionMsg+sheet);
										}
										
									}else if (i == 2 ) {
										try {
											Date date_2 = cell.getDateCellValue();
											bsr.setBase_date(date_2);
										} catch (Exception e) {
											_log.info("error parsing date---->"+val);
											errorObj.put("status", false);
											errorObj.put("msg", CommonParser.dateExceptionMsg+sheetName);
										}
									}
								
									else if (i == 3) {
										bsr.setCg(val);
									}
									else if (i == 4) {
										bsr.setSg(val);
									} 
									else if (i == 5) {
										bsr.setCcg(val);
									} 
									else if (i == 6) {
										bsr.setLite(val);
									}
									else if (i == 7) {
										bsr.setApy(val);
									}
									else if (i == 8) {
										
										bsr.setE(val);
									}
									else if (i == 9) {
										bsr.setC(val);
									} 
									else if (i == 10) {
										bsr.setG(val);
									} 
									else if (i == 11) {
										bsr.setE2(val);
									}
									else if (i == 12) {
										bsr.setC2(val);
									} 
									else if (i == 13) {
										bsr.setG2(val);
									}
									else if (i == 14) {
										bsr.setTax_saver(val);
									} 
								}else if(sheetCount == 1 && bsrs != null) {
									//sheet 2
									if (i == 0) {
										if(val == null) {
											break rowLoop;
										}
										
										if (val != null) {
											Date date_3 = cell.getDateCellValue();
											bsrs.setReturns_since(date_3);
										} 
									}
									else if (i == 1) {
										Date date_4 = cell.getDateCellValue();
										bsrs.setReturn_as_on_date(date_4);
									} 
									else if (i == 2) {
										bsrs.setCg(val);
									} 
									else if (i == 3) {
										bsrs.setSg(val);
									} 
									else if (i == 4) {
										bsrs.setCcg(val);
									} 
									else if (i == 5) {
										bsrs.setLite(val);
									}
									else if (i == 6) {
										bsrs.setApy(val);
									}
									else if (i == 7) {
										
										bsrs.setE(val);
									}
									else if (i == 8) {
										bsrs.setC(val);
									} 
									else if (i == 9) {
										bsrs.setG(val);
									} 
									else if (i == 10) {
										bsrs.setE2(val);
									}
									else if (i == 11) {
										bsrs.setC2(val);
									} 
									else if (i == 12) {
										bsrs.setG2(val);
									}
									else if (i == 13) {
										bsrs.setTax_saver(val);
									} 
								}
								
							}
						} 
						
					}
					
					if(sheetCount == 0 && bsr != null) {
						bsr.setCreatedate(new Date());
					}else if(sheetCount == 1 && bsrs != null) {
						bsrs.setCreatedate(new Date());
					}

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
						if(sheetCount == 0 && bsr != null) {
							dailyAverages2.add(bsr);
						}else if(sheetCount == 1 && bsrs != null) {
							dailyAverages3.add(bsrs);
						}
					
					}
					rowCount++;
				}//row end
				_log.info(sheetName +" rowcount"+rowCount);
				sheetCount++;
			}
				_log.info(errorArray);
				
				_log.info("isexcelhaserror" + isexcelhaserror);
			}
		} catch (Exception e) {
			isNoError = true;
			 _log.error(e);
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {
				 _log.error(e);
			}
		}
		
		
		if (!isexcelhaserror && !isNoError) {
			BenchSchemeReturnsMonthlyLocalServiceUtil.addBenchSchemeReturnsMonthly(dailyAverages2);
			_log.info("BenchSchemeReturnsMonthly datacount"+dailyAverages2.size());
			
			BenchSchemeReturnsSinceMonthlyLocalServiceUtil.addBenchSchemeReturnsSinceMonthly(dailyAverages3);
			_log.info("BenchSchemeReturnsSinceMonthly datacount"+dailyAverages3.size());
			
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("BenchmarkFileMonthly");

		File file = uploadPortletRequest.getFile("BenchmarkFileMonthly");

		String mimeType = uploadPortletRequest.getContentType("BenchmarkFileMonthly");

		String title = fileName;

		String description = "Benchmark index returns for the NPS schemes";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getPrimaryKey();
		} catch (Exception e) {

			_log.info("Exception in upload folder"+e.getMessage());

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
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	private static Log _log = LogFactoryUtil.getLog(SavebenchmarkMonthly.class);
	

}

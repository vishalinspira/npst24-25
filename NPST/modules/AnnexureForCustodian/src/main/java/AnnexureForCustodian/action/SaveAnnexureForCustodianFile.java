package AnnexureForCustodian.action;

import com.daily.average.service.model.QrCustodianBills;
import com.daily.average.service.service.QrCustodianBillsLocalService;
import com.daily.average.service.service.QrCustodianBillsLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogCustodianLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import AnnexureForCustodian.constants.AnnexureForCustodianPortletKeys;
import AnnexureForCustodian.util.AnnexureForCustodianUtil;
import AnnexureForCustodian.util.DocumentUploadUtil;
import AnnexureForCustodian.util.ValidateSheetName;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + AnnexureForCustodianPortletKeys.ANNEXUREFORCUSTODIAN,
				"mvc.command.name=save/AnnexureFourCustodianFile/excel"
		},
		service = MVCResourceCommand.class
)
public class SaveAnnexureForCustodianFile implements MVCResourceCommand{

	private static final Log _log = LogFactoryUtil.getLog(SaveAnnexureForCustodianFile.class);
	//private static final String PDF_FILE_NAME="CustodianClaimletterPDFFile";
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
	throws PortletException {
		
		_log.info("Annexure for custodian started ::::");
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long userId = themeDisplay.getUserId();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		File file = uploadPortletRequest.getFile("AnnexureFourCustodianExcelFile");
	//	File pdfFile = uploadPortletRequest.getFile(PDF_FILE_NAME);
		
		String filename = uploadPortletRequest.getFileName("AnnexureFourCustodianExcelFile");
		_log.info("file name :: "+filename);
		
		String mimeType = MimeTypesUtil.getContentType(file);
		
        
		/*
		 * File pdfFile = uploadPortletRequest.getFile("CustodianClaimletterPDFFile");
		 * 
		 * String pdfFilename =
		 * uploadPortletRequest.getFileName("CustodianClaimletterPDFFile");
		 * _log.info("file name :: "+pdfFilename);
		 * 
		 * String pdfMimeType = MimeTypesUtil.getContentType(pdfFile);
		 */
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		
		ServiceContext serviceContext = null;
		
		try {
			
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error(e);
		}
		
		PrintWriter writer = null;
		
		
		
		try {
			
			long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
			
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			
			symbols.setGroupingSeparator(',');
			
			symbols.setDecimalSeparator('.');
			
			String pattern = "#,##0.0#";
			
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			
			decimalFormat.setParseBigDecimal(true);
			
			if(null != file) {
				
				resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
				if(resultJsonObject.getBoolean("status")) {
					
					boolean flag = true;
					
					OPCPackage pkg = OPCPackage.open(file);
					XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
					
					//sheet validation
					List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook);
					
					if (errorlist.size() > 0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						StringBuilder sb = new StringBuilder();
						for(String L : errorlist) {
							if(errorlist.size() == 1) {
								sb.append(L);
							}else {
								sb.append(L);
								sb.append(",");
							}
						}
						
						String errorString = sb.toString();
						resultJsonObject.put("errorSheetNameList", errorString);
						try {
							workbook.close();
						} catch (IOException e) {
							_log.error(e);
						}
						// return
						
					}else {
						
						QrCustodianBillsLocalServiceUtil.deleteChequeClearanceByReportUploadLogId(reportUploadLogId);
						
						if(Validator.isNotNull(workbook.getSheet("Custodian Charges"))) {
							int to = workbook.getSheet("Custodian Charges").getLastRowNum() + 1;
							
							resultJsonObject = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("Custodian Charges"), decimalFormat, userId, reportUploadLogId, resultJsonObject);
						}
						
						/*if(Validator.isNotNull(workbook.getSheet("ICICI"))) {
							int to = workbook.getSheet("ICICI").getLastRowNum() + 1;
							
							flag = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("ICICI"), decimalFormat, userId, reportUploadLogId);
						}
						
						if(Validator.isNotNull(workbook.getSheet("Kotak"))) {
							int to = workbook.getSheet("Kotak").getLastRowNum() + 1;
							
							flag = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("Kotak"), decimalFormat, userId, reportUploadLogId);
						}
						
						if(Validator.isNotNull(workbook.getSheet("SBI"))) {
							int to = workbook.getSheet("SBI").getLastRowNum() + 1;
							
							flag = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("SBI"), decimalFormat, userId, reportUploadLogId);
						}
						
						if(Validator.isNotNull(workbook.getSheet("UTI"))) {
							int to = workbook.getSheet("UTI").getLastRowNum() + 1;
							
							flag = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("UTI"), decimalFormat, userId, reportUploadLogId);
						}
						
						if(Validator.isNotNull(workbook.getSheet("LIC 1"))) {
							int to = workbook.getSheet("LIC 1").getLastRowNum() + 1;
							
							flag = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("LIC 1"), decimalFormat, userId, reportUploadLogId);
						}
						
						if(Validator.isNotNull(workbook.getSheet("Birla1"))) {
							int to = workbook.getSheet("Birla1").getLastRowNum() + 1;
							
							flag = uploadExcelDataForALLSheet(annexureForCustodianUtil.getRowsForALLSheet(to), workbook.getSheet("Birla1"), decimalFormat, userId, reportUploadLogId);
						}*/
						JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
						
						
						
						if(resultJsonObject.getBoolean("status")) {
							
							try {
								String remarks = ParamUtil.getString(resourceRequest, "remarks");
								
								long fileEntryId = DocumentUploadUtil.getFileentryId(resourceRequest, themeDisplay.getScopeGroupId(), file, filename, mimeType, filename);
								
								//long pdfFileEntryId = DocumentUploadUtil.getFileentryId(resourceRequest, themeDisplay.getScopeGroupId(), pdfFile, pdfFilename, pdfMimeType, pdfFilename);
								JSONObject excelFileEntryJO = JSONFactoryUtil.createJSONObject();
								excelFileEntryJO.put("Name", file.getName());
								excelFileEntryJO.put("fileEntryId", fileEntryId);
								jsonArray.put(excelFileEntryJO);
								
								
								
								//	long pdfFileId=uploadFILETOFOLDER(themeDisplay, resourceRequest, PDF_FILE_NAME);
									//if(pdfFileId>0 && fileEntryId>0) {
								if(fileEntryId>0) {
									//	_log.info("pdfFileId :  "+pdfFileId +"  ExcelfileEntryId : "+fileEntryId);
									//JSONObject pdfFileEntryJO = JSONFactoryUtil.createJSONObject();
									//pdfFileEntryJO.put("Name", "Claim Letter & Invoice");
									//pdfFileEntryJO.put("fileEntryId", pdfFileId);
									//jsonArray.put(pdfFileEntryJO);
									updateReportLog( userId, fileEntryId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks, jsonArray.toString());
									
									////////////////////
									//ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(reportUploadLogId, pdfFileId, userId);
									//////////////////////
									resultJsonObject.put("status", true);
									}else {
										resultJsonObject=resultJsonObject.put("msg", "Error while uploading PDF.");
										resultJsonObject=resultJsonObject.put("status", false);
									}
								
								
								//TODO
								//jsonArray.put(fileEntryId);
								//jsonArray.put(pdfFileEntryId);
								
								
								
							} catch (Exception e) {
								resultJsonObject=resultJsonObject.put("msg", "Error while uploading File.");
								resultJsonObject=resultJsonObject.put("status", false);

								_log.error(e);
							}
						}
						/*
						 * }else {
						 * 
						 * resultJsonObject.put("status", false); }
						 */
					}// sheet validate end here
					
				}
				
			}
		} catch (Exception e) {
			_log.error(e);
		}
		
		_log.info("Annexure for custodian ended ::::");
		try {
			writer = resourceResponse.getWriter();
			writer.print(resultJsonObject.toString());
		} catch (IOException e) {
			_log.error(e);
		}
		return false;
		
	}
	
	private JSONObject uploadExcelDataForALLSheet(List<Integer> rowsForALLSheet, XSSFSheet sheet, DecimalFormat decimalFormat, long userId, long reportUploadLogId,JSONObject resultJsonObject) {
		boolean error = false;
		resultJsonObject.put("status", true);
		emptyrowEndHere:
		for(Integer i : rowsForALLSheet) {
			Integer rownum = i - 1;
			
			try {
				XSSFRow row = null;
				
				try {
					row = sheet.getRow(rownum);
					
					String empty = row.getCell(0).toString();
					
					if(empty.trim().equals("") || empty == null) {
						break emptyrowEndHere;
					}
				} catch (Exception e) {
					break emptyrowEndHere;
				}
				
				long primaryPk = CounterLocalServiceUtil.increment(QrCustodianBills.class.getName());
				QrCustodianBills qafc = qrAnnexureFourCustodianLocalService.createQrCustodianBills(primaryPk);
				
				qafc.setReportUploadLogId(reportUploadLogId);
				qafc.setScheme_name(row.getCell(0).toString().trim());
				try {
					qafc.setTransaction_charges_dem_trades((BigDecimal) decimalFormat.parse(row.getCell(1).toString().trim() == "" ? "0" : row.getCell(1).toString().trim()));
					qafc.setCustody_charges_dema_holdings((BigDecimal) decimalFormat.parse(row.getCell(2).toString().trim() == "" ? "0" : row.getCell(2).toString().trim()));
					qafc.setNsccl_ccil_charges((BigDecimal) decimalFormat.parse(row.getCell(3).toString().trim() == "" ? "0" : row.getCell(3).toString().trim()));
					qafc.setSebi_charges((BigDecimal) decimalFormat.parse(row.getCell(4).toString().trim() == "" ? "0" : row.getCell(4).toString().trim()));
					//qafc.setGross_bill_as_per_pf((BigDecimal) decimalFormat.parse(String.valueOf(row.getCell(5).getNumericCellValue())));
					qafc.setBill_value_excluding_gst((BigDecimal) decimalFormat.parse(row.getCell(5).toString().trim() == "" ? "0" : row.getCell(5).toString().trim()));
					qafc.setCgst((BigDecimal) decimalFormat.parse(row.getCell(6).toString().trim() == "" ? "0" : row.getCell(6).toString().trim()));
					qafc.setSgst((BigDecimal) decimalFormat.parse(row.getCell(7).toString().trim() == "" ? "0" : row.getCell(7).toString().trim()));
					qafc.setBill_value_including_gst((BigDecimal) decimalFormat.parse(row.getCell(8).toString().trim() == "" ? "0" : row.getCell(8).toString().trim()));
				} catch (Exception e) {
					_log.info("error parsing long"+e);
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
					return resultJsonObject;
				}
				try {
					qafc.setMonth(row.getCell(9).getDateCellValue());
				} catch (Exception e) {
					_log.info("error parsing long"+e);
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
					return resultJsonObject;
				}
				try {
					qafc.setAccount_number((BigDecimal) decimalFormat.parse(row.getCell(10).toString().trim() == "" ? "0" : row.getCell(10).toString().trim()));
				} catch (Exception e) {
					_log.info("error parsing long"+e);
					resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
					return resultJsonObject;
				}
				qafc.setCreatedon(new Date());
				qafc.setCreatedby(userId);
				
				_log.info("qafc ::: "+qafc.toString());
				
				try {
					qrAnnexureFourCustodianLocalService.addQrCustodianBills(qafc);
				} catch (Exception e) {
					_log.error(e);
					 resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
					return resultJsonObject;
				}
				
			} catch (Exception e) {
				_log.error(e.getMessage(), e);
				 resultJsonObject.put("status", false);
					resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
				error = true;
				return resultJsonObject;
			}
		}
		return resultJsonObject; 
		
	}
	
	
	private void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks, String fileList) {
		Date createDate = new Date();
		_log.info("in updateReportLog ---------");
		ReportUploadLogCustodianLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks,fileList);	
	}
	
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String documentName) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName =date.getTime()+ uploadPortletRequest.getFileName(documentName);

		File file = uploadPortletRequest.getFile(documentName);

		String mimeType = uploadPortletRequest.getContentType(documentName);

		String title = fileName;

		String description = documentName;

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
	
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	@Reference
	AnnexureForCustodianUtil annexureForCustodianUtil;
	
	@Reference
	QrCustodianBillsLocalService qrAnnexureFourCustodianLocalService;
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

}


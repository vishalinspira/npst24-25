package com.An11.CAC.resource;

import com.annexure.eleven.util.AccList;
import com.annexure.eleven.util.AnnexToCar;
import com.annexure.eleven.util.AverageAmt;
import com.annexure.eleven.util.CloBalUploadDelay;
import com.annexure.eleven.util.DelayAfter930;
import com.annexure.eleven.util.DelayExceed25min;
import com.annexure.eleven.util.DelayToPFM;
import com.annexure.eleven.util.DelayWithdrwalRem;
import com.annexure.eleven.util.ENPS;
import com.annexure.eleven.util.FRCWithdrawal;
import com.annexure.eleven.util.ReexecutedWithdrawal;
import com.annexure.eleven.util.ReturnOfRejFunds;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalServiceUtil;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import An11.QuarterlyConcurrentAuditorCertificate.constants.An11QuarterlyConcurrentAuditorCertificatePortletKeys;
import compliance.service.model.AccountList;
import compliance.service.model.AnnexureToCar;
import compliance.service.model.AvgAmt;
import compliance.service.model.ClosingBalUploadDelay;
import compliance.service.model.DelayAfter_930am;
import compliance.service.model.DelayExceed_25min;
import compliance.service.model.DelayToPFMAcc;
import compliance.service.model.DelayWithdrawalRemittance;
import compliance.service.model.ENPSServChanges;
import compliance.service.model.FRCWithdrawalAcc;
import compliance.service.model.ReexecutedWithdrawalReturn;
import compliance.service.model.ReturnOfRejectedFunds;
import compliance.service.service.AccountListLocalServiceUtil;
import compliance.service.service.AnnexureToCarLocalServiceUtil;
import compliance.service.service.AvgAmtLocalServiceUtil;
import compliance.service.service.ClosingBalUploadDelayLocalServiceUtil;
import compliance.service.service.DelayAfter_930amLocalServiceUtil;
import compliance.service.service.DelayExceed_25minLocalServiceUtil;
import compliance.service.service.DelayToPFMAccLocalServiceUtil;
import compliance.service.service.DelayWithdrawalRemittanceLocalServiceUtil;
import compliance.service.service.ENPSServChangesLocalServiceUtil;
import compliance.service.service.FRCWithdrawalAccLocalServiceUtil;
import compliance.service.service.ReexecutedWithdrawalReturnLocalServiceUtil;
import compliance.service.service.ReturnOfRejectedFundsLocalServiceUtil;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + An11QuarterlyConcurrentAuditorCertificatePortletKeys.AN11QUARTERLYCONCURRENTAUDITORCERTIFICATE,
		"mvc.command.name=" + An11QuarterlyConcurrentAuditorCertificatePortletKeys.concurrentAuditorCertificate, 
		}, 
service = MVCResourceCommand.class)

public class ConcurrentAuditorCertificate implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(ConcurrentAuditorCertificate.class);

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
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error(e);
		}
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		String cra = "";
		if(reportMasterId == 31) {
			cra = "NSDL";
		} else if(reportMasterId == 32){
			cra = "Kfintech";
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("AnnexureElevenAuditorCertificate");

		File file = uploadPortletRequest.getFile("AnnexureElevenAuditorCertificate");

		String mimeType = uploadPortletRequest.getContentType("AnnexureElevenAuditorCertificate");

		String title = fileName;
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		//resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		resultJsonObject.put("status", true);
		if(resultJsonObject.getBoolean("status")) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			
			int dataRowNum = 5;
			
			//zero
			JSONArray accountJsonArray = JSONFactoryUtil.createJSONArray();
			List<AccountList> accountList = new ArrayList<AccountList>();
			
			//first 
			JSONArray annexToCarJsonArray = JSONFactoryUtil.createJSONArray();
			List<AnnexureToCar> annexToCarList = new ArrayList<AnnexureToCar>();
			
			//second
			JSONArray avgAmtJsonArray = JSONFactoryUtil.createJSONArray();
			List<AvgAmt> avgAmtList = new ArrayList<AvgAmt>();
			
			//third
			JSONArray closingBalUploadJsonArray = JSONFactoryUtil.createJSONArray();
			List<ClosingBalUploadDelay> closingBalUploadList = new ArrayList<ClosingBalUploadDelay>();
			
			//fourth
			JSONArray delayAfter930JsonArray = JSONFactoryUtil.createJSONArray();
			List<DelayAfter_930am> delayAfter930List = new ArrayList<DelayAfter_930am>();
			
			//fifth
			JSONArray delayExceed25minJsonArray = JSONFactoryUtil.createJSONArray();
			List<DelayExceed_25min> delayExceed25minList = new ArrayList<DelayExceed_25min>();
			
			//sixth
			JSONArray delayToPFMJsonArray = JSONFactoryUtil.createJSONArray();
			List<DelayToPFMAcc> delayToPFMList = new ArrayList<DelayToPFMAcc>();
			
			//seventh
			JSONArray delayWithdrawRemJsonArray= JSONFactoryUtil.createJSONArray();
			List<DelayWithdrawalRemittance> delayWithdrawRemList = new ArrayList<DelayWithdrawalRemittance>();
			
			//eight
			JSONArray enpsJsonArray = JSONFactoryUtil.createJSONArray();
			List<ENPSServChanges> enpsList = new ArrayList<ENPSServChanges>();
			
			//nine
			JSONArray frcWithdrawalJsonArray = JSONFactoryUtil.createJSONArray();
			List<FRCWithdrawalAcc> frcWithdrawalList = new ArrayList<FRCWithdrawalAcc>();
			
			//ten
			JSONArray reexecutedWithdrawalJsonArray = JSONFactoryUtil.createJSONArray();
			List<ReexecutedWithdrawalReturn> reexecutedWithdrawalList = new ArrayList<ReexecutedWithdrawalReturn>();
			
			//eleven
			JSONArray returnOfRejFundsJsonArray = JSONFactoryUtil.createJSONArray();
			List<ReturnOfRejectedFunds> returnOfRejFundsList = new ArrayList<ReturnOfRejectedFunds>();
			
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
			customerIdcell.setCellValue("Sr.No");
			
			ArrayList <String> errorList = new ArrayList<String>();
			errorList.add("NCRA_Acc_List");
			errorList.add("KCRA_Acc_List");
			errorList.add("Annexure XII (B)");
			errorList.add("NCRA_Avg_Amt");
			errorList.add("KCRA_Avg_Amt");
			errorList.add("CAMS_Avg_Amt");
			errorList.add("Closing_Bal_Upload_Delay");
			errorList.add("NCRA_FRC_Delay_After_0930AM");
			errorList.add("NCRA_PayIn_Delay_Exceed_25mins");
			errorList.add("KCRA_PayIn_Delay_Exceed_25mins");
			errorList.add("NCRA_Delay_To_PFM_Acc");
			errorList.add("KCRA_Delay_To_PFM_Acc");
			errorList.add("Withdrawl_Remittance_Delay");
			errorList.add("Withdrawal_Remittance_DOP");
			errorList.add("NCRA_ENPS_Serv_Charges");
			errorList.add("KCRA_ENPS_Serv_Charges");
			errorList.add("FRC_Withdrawal_Acc");
			errorList.add("ReExecuted_WithdrawalReturn");
			errorList.add("ReExecuted_WithdrawalReturn_DOP");
			errorList.add("Return_of_Rejected_Funds");
			
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					//int sheetcount = 0;
					Iterator<Sheet> sheets1 = workbook.sheetIterator();
					while (sheets1.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets1.next();
						if (errorList.contains(sheet.getSheetName())) {
							errorList.remove(sheet.getSheetName());
						}
					}
				
					if (errorList.size()>0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray(errorList);
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
						
					}
				}
			}catch (Exception e) {
			}
			
			boolean isexcelhaserror = false;
			
			AccList.saveAccountList(file, workbook, userId, errorArray, xx, isexcelhaserror, accountJsonArray, accountList, decimalFormat, cra, reportUploadLogId);
			AnnexToCar.saveAnnexXIIB(file, workbook, userId, errorArray, xx, isexcelhaserror, annexToCarJsonArray, annexToCarList, decimalFormat, cra, reportUploadLogId);
			AverageAmt.saveAvgAmt(file, workbook, userId, errorArray, xx, isexcelhaserror, avgAmtJsonArray, avgAmtList, decimalFormat, cra, reportUploadLogId);
			CloBalUploadDelay.saveCloBalUploadDelay(file, workbook, userId, errorArray, xx, isexcelhaserror, closingBalUploadJsonArray, closingBalUploadList, decimalFormat, reportUploadLogId);
			DelayAfter930.saveDelayAfter930(file, workbook, userId, errorArray, xx, isexcelhaserror, delayAfter930JsonArray, delayAfter930List, decimalFormat, cra, reportUploadLogId);
			DelayExceed25min.saveDelayExceed25min(file, workbook, userId, errorArray, xx, isexcelhaserror, delayExceed25minJsonArray, delayExceed25minList, decimalFormat, cra, reportUploadLogId);
			DelayToPFM.saveDelayToPFMAcc(file, workbook, userId, errorArray, xx, isexcelhaserror, delayToPFMJsonArray, delayToPFMList, decimalFormat, cra, reportUploadLogId);
			DelayWithdrwalRem.saveAccountList(file, workbook, userId, errorArray, xx, isexcelhaserror, delayWithdrawRemJsonArray, delayWithdrawRemList, decimalFormat, reportUploadLogId);
			ENPS.saveENPSServChanges(file, workbook, userId, errorArray, xx, isexcelhaserror, enpsJsonArray, enpsList, decimalFormat, cra, reportUploadLogId);
			FRCWithdrawal.saveFRCWithdrawalAcc(file, workbook, userId, errorArray, xx, isexcelhaserror, frcWithdrawalJsonArray, frcWithdrawalList, decimalFormat, reportUploadLogId);
			ReexecutedWithdrawal.saveReexecutedWithdrawal(file, workbook, userId, errorArray, xx, isexcelhaserror, reexecutedWithdrawalJsonArray, reexecutedWithdrawalList, decimalFormat, reportUploadLogId);
			ReturnOfRejFunds.saveReturnOfRejFunds(file, workbook, userId, errorArray, xx, isexcelhaserror, returnOfRejFundsJsonArray, returnOfRejFundsList, decimalFormat, cra, reportUploadLogId);
			
			_log.info("errorlist " + errorList.toString());
			
			if (!isexcelhaserror) {
				AccountListLocalServiceUtil.addAccountList(accountList);
				_log.info("account datacount"+accountList.size());
				
				AnnexureToCarLocalServiceUtil.addAnnexToCar(annexToCarList);
				_log.info("annexToCar datacount"+annexToCarList.size());
				
				AvgAmtLocalServiceUtil.addAvgAmt(avgAmtList);
				_log.info("avgAmt datacount"+avgAmtList.size());
				
				ClosingBalUploadDelayLocalServiceUtil.addClosingBalDelay(closingBalUploadList);
				_log.info("closingBalUploadList datacount"+closingBalUploadList.size());
				
				DelayAfter_930amLocalServiceUtil.addDelayAfter930am(delayAfter930List);
				_log.info("delayAfter930 datacount"+delayAfter930List.size());
				
				DelayExceed_25minLocalServiceUtil.addDelayExceed25min(delayExceed25minList);
				_log.info("delayExceed25min datacount"+delayExceed25minList.size());
				
				DelayToPFMAccLocalServiceUtil.addDelayToPFMAcc(delayToPFMList);
				_log.info("delayToPFM datacount"+delayToPFMList.size());
				
				DelayWithdrawalRemittanceLocalServiceUtil.addDelayWithdrawalRem(delayWithdrawRemList);
				_log.info("delayWithdrawRem datacount"+delayWithdrawRemList.size());
				
				ENPSServChangesLocalServiceUtil.addENPSServChanges(enpsList);
				_log.info("enps datacount"+enpsList.size());
				
				FRCWithdrawalAccLocalServiceUtil.addFRCWithdrawalAcc(frcWithdrawalList);
				_log.info("frcWithdrawal datacount"+frcWithdrawalList.size());
				
				ReexecutedWithdrawalReturnLocalServiceUtil.addReexecutedWithdrawalReturn(reexecutedWithdrawalList);
				_log.info("reexecutedWithdrawal datacount"+reexecutedWithdrawalList.size());
				
				ReturnOfRejectedFundsLocalServiceUtil.addReturnOfRejFunds(returnOfRejFundsList);
				_log.info("returnOfRejFund datacount"+returnOfRejFundsList.size());
				
				Long fileEntryId = 0l;
				fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
				try {
					errorExcel.close();
					
					String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
					updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext, remarks);
					//resultJsonObject.put("pdfURL",  pdfTable(statusDayss, themeDisplay, resourceRequest));
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
					_log.info("line 311");
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

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("AnnexureElevenAuditorCertificate");

		File file = uploadPortletRequest.getFile("AnnexureElevenAuditorCertificate");

		String mimeType = uploadPortletRequest.getContentType("AnnexureElevenAuditorCertificate");

		String title = fileName;

		String description = "Annexure 11 Concurrent Auditor Report";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry  fileEntry=DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Quarterly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogSupervisorLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}

package com.annexure.seven.resource;

import com.annexure.seven.util.*;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.annexure.seven.constants.AnnexureSevenPortletKeys;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import compliance.service.model.AmtMismatch;
import compliance.service.model.ChequeAccepted;
import compliance.service.model.ChequeClearance;
import compliance.service.model.ChequeReject;
import compliance.service.model.ChequesFailed;
import compliance.service.model.CollectionSummary;
import compliance.service.model.CollectionsTID;
import compliance.service.model.ConsoCollReport;
import compliance.service.model.D_REMITAccepted;
import compliance.service.model.ElectronicAccepted;
import compliance.service.model.ElectronicRejSummary;
import compliance.service.model.ElectronicRejects;
import compliance.service.model.ExpTranId;
import compliance.service.model.InvalidAcc;
import compliance.service.model.InvalidAccII;
import compliance.service.model.NPSAccepted;
import compliance.service.model.NoTID;
import compliance.service.model.PAOMistakes;
import compliance.service.model.PAOSubmittingCheques;
import compliance.service.service.AmtMismatchLocalServiceUtil;
import compliance.service.service.ChequeAcceptedLocalServiceUtil;
import compliance.service.service.ChequeClearanceLocalServiceUtil;
import compliance.service.service.ChequeRejectLocalServiceUtil;
import compliance.service.service.ChequesFailedLocalServiceUtil;
import compliance.service.service.CollectionSummaryLocalServiceUtil;
import compliance.service.service.CollectionsTIDLocalServiceUtil;
import compliance.service.service.ConsoCollReportLocalServiceUtil;
import compliance.service.service.D_REMITAcceptedLocalServiceUtil;
import compliance.service.service.ElectronicAcceptedLocalServiceUtil;
import compliance.service.service.ElectronicRejSummaryLocalServiceUtil;
import compliance.service.service.ElectronicRejectsLocalServiceUtil;
import compliance.service.service.InvalidAccIILocalService;
import compliance.service.service.InvalidAccIILocalServiceUtil;
import compliance.service.service.InvalidAccLocalServiceUtil;
import compliance.service.service.NPSAcceptedLocalServiceUtil;
import compliance.service.service.NoTIDLocalServiceUtil;
import compliance.service.service.PAOMistakesLocalServiceUtil;
import compliance.service.service.PAOSubmittingChequesLocalServiceUtil;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + AnnexureSevenPortletKeys.ANNEXURESEVEN,
				"mvc.command.name=/saveAnnexureSevenFileURl/data"
		},
		service = MVCResourceCommand.class
		)
public class SaveAnnexureSeven implements MVCResourceCommand{
	
	private static Log _log = LogFactoryUtil.getLog(SaveAnnexureSeven.class);

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
		
		Long reportMasterId = ParamUtil.getLong(resourceRequest, "reportMasterId");
		String cra = "";
		if(reportMasterId == 8) {
			cra = "PCRA";
		} else if(reportMasterId == 21){
			cra = "KCRA";
		} else if(reportMasterId == 126){
			cra = "CCRA";
		}
		_log.info("cra " + cra);
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("AnnexureSeven");

		File file = uploadPortletRequest.getFile("AnnexureSeven");

		String mimeType = uploadPortletRequest.getContentType("AnnexureSeven");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		int dataRowNum = 5;
		
		//zero
		JSONArray collectionSummaryJsonArray = JSONFactoryUtil.createJSONArray();
		List<CollectionSummary> collectionSummary = new ArrayList<CollectionSummary>();
		
		//first 
		
		JSONArray electonicJsonArray = JSONFactoryUtil.createJSONArray();
		List<ElectronicAccepted> electronicaccepted = new ArrayList<ElectronicAccepted>();
		
		//second
//		 JSONArray npsacceptJsonArray = JSONFactoryUtil.createJSONArray(); 
//		 List<NPSAccepted> npsaccept = new ArrayList<NPSAccepted>();
		
		//third
		JSONArray dremitaJsonArray = JSONFactoryUtil.createJSONArray();
		List<D_REMITAccepted> dremita = new ArrayList<D_REMITAccepted>();
		
		//fourth
		JSONArray erejectionJsonArray = JSONFactoryUtil.createJSONArray();
		List<ElectronicRejects> electronicrej = new ArrayList<ElectronicRejects>();
		
		//fifth
		JSONArray checkacceptedJsonArray = JSONFactoryUtil.createJSONArray();
		List<ChequeAccepted>  chequeaccepted = new ArrayList<ChequeAccepted>();
		
		//sixth
		JSONArray chequesFaildJsonArray = JSONFactoryUtil.createJSONArray();
		List<ChequesFailed> chequesFaild = new ArrayList<ChequesFailed>();
		
		//seventh
		JSONArray paosubmittingChequesJsonArray = JSONFactoryUtil.createJSONArray();
		List<PAOSubmittingCheques> paosubmittingCheques = new ArrayList<PAOSubmittingCheques>();
		
		//eight
		JSONArray chequeclearanceJsonArray = JSONFactoryUtil.createJSONArray();
		List<ChequeClearance> chequeclearance = new ArrayList<ChequeClearance>();
		
		//nine
		JSONArray collectionsTIDJsonArray = JSONFactoryUtil.createJSONArray();
		List<CollectionsTID> collectionsTIDList = new ArrayList<CollectionsTID>();
		
		//ten
		JSONArray collRepJsonArray= JSONFactoryUtil.createJSONArray();
		List<ConsoCollReport> collRepList = new ArrayList<ConsoCollReport>();
		
		//eleven for 1st and 2nd column missing
		JSONArray paoMistakesJsonArray = JSONFactoryUtil.createJSONArray();
		List<PAOMistakes> paoMistakesList = new ArrayList<PAOMistakes>();
		 
		//twelve
		JSONArray invalidAccountJsonArray = JSONFactoryUtil.createJSONArray();
		List<InvalidAcc> invalidAccountList = new ArrayList<InvalidAcc>();
		
		JSONArray invalidAccountIIJsonArray = JSONFactoryUtil.createJSONArray();
		List<InvalidAccII> invalidAccountIIList = new ArrayList<InvalidAccII>();
		
//		JSONArray objJsonArray = JSONFactoryUtil.createJSONArray();
//		List<Object> objList = new ArrayList<Object>();
		
		//thirteen
		JSONArray amtMismatchJsonArray = JSONFactoryUtil.createJSONArray();
		List<AmtMismatch> amtMismatchList = new ArrayList<AmtMismatch>();
		
		//fourteen
		JSONArray expTranIdJsonArray = JSONFactoryUtil.createJSONArray();
		List<ExpTranId> expTranIdList = new ArrayList<ExpTranId>();
		
		//fifteen
		JSONArray noTIDJsonArray = JSONFactoryUtil.createJSONArray();
		List<NoTID> noTIDList = new ArrayList<NoTID>();
		
		//sixteen
		JSONArray chequeRejectJsonArray = JSONFactoryUtil.createJSONArray();
		List<ChequeReject> chequeRejectList = new ArrayList<ChequeReject>();
		
		//seventeen
		JSONArray eleRejSumJsonArray = JSONFactoryUtil.createJSONArray();
		List<ElectronicRejSummary> eleRejSummaryList = new ArrayList<ElectronicRejSummary>();
		 
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
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
			
			ArrayList <String> list = new ArrayList<String>();
		      list.add("Collection_Summary");
		      list.add("All_Electronic_Accepted");
			  list.add("All_ENPS_Accepted");
			  list.add("All_DRemit_Accepted");
			  list.add("All_Chq_Accepted");
			  list.add("All_Electronic_Rejects");
		      list.add("All_Chq_Failed_TID_Expired");
		      list.add("PAO_Submitting_Chq");
		      list.add("Chq_Clearance_TAT");
		      list.add("Collections_TID_ToBe_Enabled");
		      list.add("Consolidated_Collection_Report");
		      list.add("PAO_Repeated_mistakes");
		      list.add("Invalid_Acc_No");
		      list.add("Amount_Mismatch");
		      list.add("Expired_Tran_ID");
		      list.add("No_TranID_inward_msg");
		      list.add("Chq_Rejected");
		      list.add("Electronic_Rejects_Summary");
		      
			
			boolean isexcelhaserror = false;
			
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					Iterator<Sheet> sheets1 = workbook.sheetIterator();
					
					while(sheets1.hasNext()) {
						XSSFSheet sheet = (XSSFSheet) sheets1.next();
						if(list.contains(sheet.getSheetName())) {
							list.remove(sheet.getSheetName());
						}
					}
					
					if (list.size()>0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray(list);
						resultJsonObject.put("errorSheetNameList", errorSheetNameList);
						return resultJsonObject;
						
					}
				}
			}catch (Exception e) {
			}
			
			resultJsonObject = AnSevenSheetZero.saveSheetZero(file, workbook, userId, errorArray, xx, isexcelhaserror, collectionSummaryJsonArray, decimalFormat, cra, collectionSummary, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnsevensheetOne.saveSheetOne(file, workbook, userId, errorArray, xx, isexcelhaserror, electonicJsonArray, electronicaccepted, decimalFormat, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnsevensheetTen.saveSheetTen(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, collRepJsonArray, collRepList, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnsevensheetFourth.saveSheetFourth(file, workbook, userId, errorArray, xx, isexcelhaserror, erejectionJsonArray, electronicrej, decimalFormat, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnnsevensheetSixth.saveSheetSixth(file, workbook, userId, errorArray, xx, isexcelhaserror, chequesFaildJsonArray, chequesFaild, decimalFormat, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnnsevensheetSeven.saveSheetSeven(file, workbook, userId, errorArray, xx, isexcelhaserror, paosubmittingChequesJsonArray, paosubmittingCheques, decimalFormat, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnnsevenEight.saveSheetEigth(file, workbook, userId, errorArray, xx, isexcelhaserror, chequeclearanceJsonArray, chequeclearance, decimalFormat, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnsevensheetNine.saveSheetNine(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, collectionsTIDJsonArray, collectionsTIDList, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnsevensheeEleven.saveSheetEleven(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, paoMistakesJsonArray, paoMistakesList, cra, resultJsonObject, reportUploadLogId);
			
			/*if(cra.equalsIgnoreCase("NSDL")) {
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = AnsevensheeTwelve.saveSheetTwelveB(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, invalidAccountIIJsonArray, invalidAccountIIList, cra, resultJsonObject, reportUploadLogId);
			} else {*/
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = AnsevensheeTwelve.saveSheetTwelve(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, invalidAccountJsonArray, invalidAccountList, cra, resultJsonObject, reportUploadLogId);
			//}
			
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnsevensheeSixteen.saveSheetSixteen(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, chequeRejectJsonArray, chequeRejectList, cra, resultJsonObject, reportUploadLogId);
			if(!resultJsonObject.getBoolean("status"))
				return resultJsonObject;
			resultJsonObject = AnnSevenSheetSeventeen.saveSheetSeventeen(file, workbook, userId, errorArray, xx, isexcelhaserror, eleRejSumJsonArray, eleRejSummaryList, decimalFormat, cra, resultJsonObject, reportUploadLogId);
			// ========== Added sheet 3,5 parsing to sheet one ==============
			//AnsevensheetThree.saveSheetThree(file, workbook, userId, errorArray, xx, isexcelhaserror, dremitaJsonArray, dremita, decimalFormat, cra);
			//AnnsevensheetFifth.saveSheetFifth(file, workbook, userId, errorArray, xx, isexcelhaserror, checkacceptedJsonArray, chequeaccepted, decimalFormat, cra);
			// ========== Added sheet below sheets parsing to sheet twelve ==============
			//AnsevensheeThirteen.saveSheetThirteen(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, amtMismatchJsonArray, amtMismatchList, cra);
			//AnsevensheeFourteen.saveSheetFourteen(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, expTranIdJsonArray, expTranIdList, cra);
			//AnsevensheeFifteen.saveSheetFifteen(file, workbook, userId, errorArray, xx, isexcelhaserror, decimalFormat, noTIDJsonArray, noTIDList, cra);
			// ==========================================================================
			
				if (!isexcelhaserror) {
					//_log.info("Inside if line 270");
					
					try {
						CollectionSummaryLocalServiceUtil.addCollectionSummary(collectionSummary);
						ElectronicAcceptedLocalServiceUtil.addElectronicsAccepted(electronicaccepted);
						ConsoCollReportLocalServiceUtil.addConsoCollReport(collRepList);
						ElectronicRejectsLocalServiceUtil.addElectronicRejects(electronicrej);
						ChequesFailedLocalServiceUtil.addChequesFailed(chequesFaild);
						PAOSubmittingChequesLocalServiceUtil.addPaoSubmittingCheques(paosubmittingCheques);
						ChequeClearanceLocalServiceUtil.addChequeClearance(chequeclearance);
						CollectionsTIDLocalServiceUtil.addCollectionsTID(collectionsTIDList);
						PAOMistakesLocalServiceUtil.addPaoMistakes(paoMistakesList);
						ChequeRejectLocalServiceUtil.addChequeRejects(chequeRejectList);
						ElectronicRejSummaryLocalServiceUtil.addElectronicRejSummary(eleRejSummaryList);
						if(cra.equalsIgnoreCase("NSDL")) {
							InvalidAccIILocalServiceUtil.addInvalidAccountII(invalidAccountIIList);
						} else {
							InvalidAccLocalServiceUtil.addInvalidAccount(invalidAccountList);
						}
					} catch (Exception e1) {
						resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
					
					/*if(cra.equalsIgnoreCase("NSDL")) {
						InvalidAccIILocalServiceUtil.addInvalidAccountII(invalidAccountIIList);
					} else {
						InvalidAccLocalServiceUtil.addInvalidAccount(invalidAccountList);
					}*/
					
					
		
					//NPSAcceptedLocalServiceUtil.addNPSAccepted(npsaccept);
					//D_REMITAcceptedLocalServiceUtil.addDREMITAccepted(dremita);
					//ChequeAcceptedLocalServiceUtil.addChequeAccepted(chequeaccepted);
					// ====== Adding data into invalid acc table =====
					//AmtMismatchLocalServiceUtil.addAmountMismatch(amtMismatchList);
					//NoTIDLocalServiceUtil.addNoTID(noTIDList);
					
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
	
	//@SuppressWarnings("deprecation")
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
	
	//@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		
		Date date =new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+ uploadPortletRequest.getFileName("AnnexureSeven");

		File file = uploadPortletRequest.getFile("AnnexureSeven");

		String mimeType = uploadPortletRequest.getContentType("AnnexureSeven");

		String title = fileName;

		String description = "Trustee Bank - Collection Rejection of Remittance monthly MIS";

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
}

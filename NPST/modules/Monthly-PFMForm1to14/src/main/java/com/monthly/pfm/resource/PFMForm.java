package com.monthly.pfm.resource;

import com.daily.average.service.service.ReportUploadLogPFMLocalServiceUtil;
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
import com.monthly.pfm.util.Form1;
import com.monthly.pfm.util.Form10;
import com.monthly.pfm.util.Form11;
import com.monthly.pfm.util.Form12;
import com.monthly.pfm.util.Form13;
import com.monthly.pfm.util.Form14;
import com.monthly.pfm.util.Form2;
import com.monthly.pfm.util.Form3;
import com.monthly.pfm.util.Form4;
import com.monthly.pfm.util.Form5;
import com.monthly.pfm.util.Form6;
import com.monthly.pfm.util.Form7;
import com.monthly.pfm.util.Form8;
import com.monthly.pfm.util.Form8_ii;
import com.monthly.pfm.util.Form9;
import com.monthly.pfm.util.ValidateFileName;
import com.monthly.pfm.util.ValidateSheetName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Monthly.PFMForm1to14.constants.MonthlyPFMForm1to14PortletKeys;
import compliance.service.model.MnForm1;
import compliance.service.model.MnForm10;
import compliance.service.model.MnForm11;
import compliance.service.model.MnForm11_NonSponsor;
import compliance.service.model.MnForm12;
import compliance.service.model.MnForm13;
import compliance.service.model.MnForm14;
import compliance.service.model.MnForm2;
import compliance.service.model.MnForm3;
import compliance.service.model.MnForm4_AtalPensionYojana;
import compliance.service.model.MnForm4_CG;
import compliance.service.model.MnForm4_CorpCG;
import compliance.service.model.MnForm4_NpsLite;
import compliance.service.model.MnForm4_SG;
import compliance.service.model.MnForm5_C_I;
import compliance.service.model.MnForm5_C_II;
import compliance.service.model.MnForm6_E_I;
import compliance.service.model.MnForm6_E_II;
import compliance.service.model.MnForm7_G_I;
import compliance.service.model.MnForm7_G_II;
import compliance.service.model.MnForm7a_A_T_I;
import compliance.service.model.MnForm7b_TTS;
import compliance.service.model.MnForm8;
import compliance.service.model.MnForm8_ii;
import compliance.service.model.MnForm9;
import compliance.service.service.MnForm10LocalServiceUtil;
import compliance.service.service.MnForm11LocalServiceUtil;
import compliance.service.service.MnForm11_NonSponsorLocalServiceUtil;
import compliance.service.service.MnForm12LocalServiceUtil;
import compliance.service.service.MnForm13LocalServiceUtil;
import compliance.service.service.MnForm14LocalServiceUtil;
import compliance.service.service.MnForm1LocalServiceUtil;
import compliance.service.service.MnForm2LocalServiceUtil;
import compliance.service.service.MnForm3LocalServiceUtil;
import compliance.service.service.MnForm4_AtalPensionYojanaLocalServiceUtil;
import compliance.service.service.MnForm4_CGLocalServiceUtil;
import compliance.service.service.MnForm4_CorpCGLocalServiceUtil;
import compliance.service.service.MnForm4_NpsLiteLocalServiceUtil;
import compliance.service.service.MnForm4_SGLocalServiceUtil;
import compliance.service.service.MnForm5_C_IILocalServiceUtil;
import compliance.service.service.MnForm5_C_ILocalServiceUtil;
import compliance.service.service.MnForm6_E_IILocalServiceUtil;
import compliance.service.service.MnForm6_E_ILocalServiceUtil;
import compliance.service.service.MnForm7_G_IILocalServiceUtil;
import compliance.service.service.MnForm7_G_ILocalServiceUtil;
import compliance.service.service.MnForm7a_A_T_ILocalServiceUtil;
import compliance.service.service.MnForm7b_TTSLocalServiceUtil;
import compliance.service.service.MnForm8LocalServiceUtil;
import compliance.service.service.MnForm8_iiLocalServiceUtil;
import compliance.service.service.MnForm9LocalServiceUtil;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + MonthlyPFMForm1to14PortletKeys.MONTHLYPFMFORM1TO14,
		"mvc.command.name=" + MonthlyPFMForm1to14PortletKeys.pfm, 
		}, 
service = MVCResourceCommand.class)

public class PFMForm implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(PFMForm.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			_log.info("resultJsonObject------------"+resultJsonObject);
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
		
		String fileName = uploadPortletRequest.getFileName("sbiFile");

		File file = uploadPortletRequest.getFile("sbiFile");

		String mimeType = uploadPortletRequest.getContentType("sbiFile");

		String title = fileName;
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//Form 1
		JSONArray form1JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm1> form1List = new ArrayList<MnForm1>();
		
		//Form 2
		JSONArray form2JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm2> form2List = new ArrayList<MnForm2>();
		
		//Form 3
		JSONArray form3JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm3> form3List = new ArrayList<MnForm3>();
		
		//Form 4
		JSONArray form4AtalJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm4_AtalPensionYojana> form4AtalList = new ArrayList<MnForm4_AtalPensionYojana>();
		
		JSONArray form4CorpCGJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm4_CorpCG> form4CorpCGList = new ArrayList<MnForm4_CorpCG>();
		
		JSONArray form4NpsLiteJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm4_NpsLite> form4NpsLiteList = new ArrayList<MnForm4_NpsLite>();
		
		JSONArray form4CGJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm4_CG> form4CGList = new ArrayList<MnForm4_CG>();
		
		JSONArray form4SGJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm4_SG> form4SGList = new ArrayList<MnForm4_SG>();
		
		//Form 5
		JSONArray form5C_1JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm5_C_I> form5C_1List = new ArrayList<MnForm5_C_I>();
		
		JSONArray form5C_2JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm5_C_II> form5C_2List = new ArrayList<MnForm5_C_II>();
		
		//Form 6
		JSONArray form6E_1JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm6_E_I> form6E_1List = new ArrayList<MnForm6_E_I>();
		
		JSONArray form6E_2JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm6_E_II> form6E_2List = new ArrayList<MnForm6_E_II>();
		
		// Form 7
		JSONArray form7aJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm7a_A_T_I> form7aList = new ArrayList<MnForm7a_A_T_I>();
		
		JSONArray form7bJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm7b_TTS> form7bList = new ArrayList<MnForm7b_TTS>();
		
		JSONArray form7G_1JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm7_G_I> form7G_1List = new ArrayList<MnForm7_G_I>();
		
		JSONArray form7G_2JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm7_G_II> form7G_2List = new ArrayList<MnForm7_G_II>();
		
		//Form 8
		JSONArray form8JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm8> form8List = new ArrayList<MnForm8>();
		
		JSONArray form8_iiJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm8_ii> form8_iiList = new ArrayList<MnForm8_ii>();
		
		//Form 9
		JSONArray form9JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm9> form9List = new ArrayList<MnForm9>();
		
		//Form 10
		JSONArray form10JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm10> form10List = new ArrayList<MnForm10>();
		
		//Form 11
		JSONArray form11JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm11> form11List = new ArrayList<MnForm11>();
		
		JSONArray form11_NonSponsorJsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm11_NonSponsor> form11_NonSponsorList = new ArrayList<MnForm11_NonSponsor>();
		
		//Form 12
		JSONArray form12JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm12> form12List = new ArrayList<MnForm12>();
		
		//Form 13
		JSONArray form13JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm13> form13List = new ArrayList<MnForm13>();
		
		//Form 14
		JSONArray form14JsonArray = JSONFactoryUtil.createJSONArray();
		List<MnForm14> form14List = new ArrayList<MnForm14>();
		
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
	
		if(ValidateFileName.isValidfile(fileName)){
			List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, null);
		if (errorlist.size() > 0) {
			resultJsonObject.put("status", false);
			resultJsonObject.put("sheeterror",true);
			JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
			resultJsonObject.put("errorSheetNameList", arrayList);

			return resultJsonObject;
		}else {
			
		}
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
			XSSFCell fileNamecell = xxx.createCell(2);
			fileNamecell.setCellValue("Pension Fund");
			
			boolean isexcelhaserror = false;
			
			//sheet validation
	//		List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook);
			
//			if (errorlist.size() > 0) {
//				resultJsonObject.put("status", false);
//				resultJsonObject.put("sheeterror",true);
//				JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
//				resultJsonObject.put("errorSheetNameList", arrayList);
//				try {
//					errorExcel.close();
//				} catch (IOException e) {
//					 _log.error(e);
//				}
//				// return
//				return resultJsonObject;
//			}else {
				
				if(true) {
				resultJsonObject = Form1.saveForm1(file, workbook, userId, errorArray, xx, isexcelhaserror, form1JsonArray, form1List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form2.saveForm2(file, workbook, userId, errorArray, xx, isexcelhaserror, form2JsonArray, form2List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form3.saveForm3(file, workbook, userId, errorArray, xx, isexcelhaserror, form3JsonArray, form3List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				//Form 4
				resultJsonObject = Form4.saveForm4Atal(file, workbook, userId, errorArray, xx, isexcelhaserror, form4AtalJsonArray, form4AtalList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form4.saveForm4CorpCG(file, workbook, userId, errorArray, xx, isexcelhaserror, form4CorpCGJsonArray, form4CorpCGList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				
				resultJsonObject = Form4.saveForm4NpsLite(file, workbook, userId, errorArray, xx, isexcelhaserror, form4NpsLiteJsonArray, form4NpsLiteList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form4.saveForm4CG(file, workbook, userId, errorArray, xx, isexcelhaserror, form4CGJsonArray, form4CGList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form4.saveForm4SG(file, workbook, userId, errorArray, xx, isexcelhaserror, form4SGJsonArray, form4SGList, decimalFormat,reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				//Form 5
				resultJsonObject = Form5.saveForm5C_1(file, workbook, userId, errorArray, xx, isexcelhaserror, form5C_1JsonArray, form5C_1List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject=Form5.saveForm5C_2(file, workbook, userId, errorArray, xx, isexcelhaserror, form5C_2JsonArray, form5C_2List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				//Form 6
				resultJsonObject = Form6.saveForm6E_1(file, workbook, userId, errorArray, xx, isexcelhaserror, form6E_1JsonArray, form6E_1List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form6.saveForm6E_2(file, workbook, userId, errorArray, xx, isexcelhaserror, form6E_2JsonArray, form6E_2List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				//Form 7
				resultJsonObject = Form7.saveForm7A(file, workbook, userId, errorArray, xx, isexcelhaserror, form7aJsonArray, form7aList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form7.saveForm7B(file, workbook, userId, errorArray, xx, isexcelhaserror, form7bJsonArray, form7bList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form7.saveForm7G_1(file, workbook, userId, errorArray, xx, isexcelhaserror, form7G_1JsonArray, form7G_1List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form7.saveForm7G_2(file, workbook, userId, errorArray, xx, isexcelhaserror, form7G_2JsonArray, form7G_2List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form8.saveForm8(file, workbook, userId, errorArray, xx, isexcelhaserror, form8JsonArray, form8List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form8_ii.saveform8_ii(file, workbook, userId, errorArray, xx, isexcelhaserror, form8_iiJsonArray, form8_iiList, decimalFormat, reportUploadLogId);
				//.saveForm8_ii(file, workbook, userId, errorArray, xx, isexcelhaserror, form8_iiJsonArray, form8_iiList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form9.saveForm9(file, workbook, userId, errorArray, xx, isexcelhaserror, form9JsonArray, form9List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form10.saveForm10(file, workbook, userId, errorArray, xx, isexcelhaserror, form10JsonArray, form10List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				//Form 11
				resultJsonObject = Form11.saveForm11(file, workbook, userId, errorArray, xx, isexcelhaserror, form11JsonArray, form11List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form11.saveForm11NonSponsor(file, workbook, userId, errorArray, xx, isexcelhaserror, form11_NonSponsorJsonArray, form11_NonSponsorList, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form12.saveForm12(file, workbook, userId, errorArray, xx, isexcelhaserror, form12JsonArray, form12List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form13.saveForm13(file, workbook, userId, errorArray, xx, isexcelhaserror, form13JsonArray, form13List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				resultJsonObject = Form14.saveForm14(file, workbook, userId, errorArray, xx, isexcelhaserror, form14JsonArray, form14List, decimalFormat, reportUploadLogId);
				if(!resultJsonObject.getBoolean("status"))
					return resultJsonObject;
				if (!isexcelhaserror) {
					
					try {
						MnForm1LocalServiceUtil.addMnForm1(form1List);
						_log.info("form 1 datacount"+form1List.size());
						
						MnForm2LocalServiceUtil.addMnForm2(form2List);
						_log.info("form 2 datacount"+form2List.size());
						
						MnForm3LocalServiceUtil.addMnForm3(form3List);
						_log.info("form 3 datacount"+form3List.size());
						
						MnForm4_AtalPensionYojanaLocalServiceUtil.addMnForm4_AtalPensionYojana(form4AtalList);
						_log.info("form 4_atal datacount"+form4AtalList.size());
						
						MnForm4_CorpCGLocalServiceUtil.addMnForm4_CorpCG(form4CorpCGList);
						_log.info("form 4_crop datacount"+form4CorpCGList.size());
						
						MnForm4_NpsLiteLocalServiceUtil.addMnForm4_NpsLite(form4NpsLiteList);
						_log.info("form 4_nps datacount"+form4NpsLiteList.size());
						
						MnForm4_CGLocalServiceUtil.addMnForm4_CG(form4CGList);
						_log.info("form 4_cg datacount"+form4CGList.size());
						
						MnForm4_SGLocalServiceUtil.addMnForm4_SG(form4SGList);
						_log.info("form 4_sg datacount"+form4SGList.size());
						
						MnForm5_C_ILocalServiceUtil.addMnForm5_C_I(form5C_1List);
						_log.info("form 5_c_i datacount"+form5C_1List.size());
						
						MnForm5_C_IILocalServiceUtil.addMnForm5_C_II(form5C_2List);
						_log.info("form 5_c_ii datacount"+form5C_2List.size());
						
						MnForm6_E_ILocalServiceUtil.addMnForm6_E_I(form6E_1List);
						_log.info("form 6_e_i datacount"+form6E_1List.size());
						
						MnForm6_E_IILocalServiceUtil.addMnForm6_E_II(form6E_2List);
						_log.info("form 6_e_ii datacount"+form6E_2List.size());
						
						MnForm7a_A_T_ILocalServiceUtil.addMnForm7a_A_T_I(form7aList);
						_log.info("form 7a datacount"+form7aList.size());
						
						MnForm7b_TTSLocalServiceUtil.addMnForm7b_TTS(form7bList);
						_log.info("form 7b datacount"+form7bList.size());
						
						MnForm7_G_ILocalServiceUtil.addMnForm7_G_I(form7G_1List);
						_log.info("form 7_g_i datacount"+form7G_1List.size());
						
						MnForm7_G_IILocalServiceUtil.addMnForm7_G_II(form7G_2List);
						_log.info("form 7_g_ii datacount"+form7G_2List.size());
						
						MnForm8LocalServiceUtil.addMnForm8(form8List);
						_log.info("form 8 datacount"+form8List.size());
						
						MnForm8_iiLocalServiceUtil.addMnForm8_ii(form8_iiList);
						_log.info("form 8_ii datacount"+form8_iiList.size());
						
						MnForm9LocalServiceUtil.addMnForm9(form9List);
						_log.info("form 9 datacount"+form9List.size());
						
						MnForm10LocalServiceUtil.addMnForm10(form10List);
						_log.info("form 10 datacount"+form10List.size());
						
						MnForm11LocalServiceUtil.addMnForm11(form11List);
						_log.info("form 11 datacount"+form11List.size());
						
						MnForm11_NonSponsorLocalServiceUtil.addMnForm11_NonSponsor(form11_NonSponsorList);
						_log.info("form 11_non datacount"+form11_NonSponsorList.size());
						
						MnForm12LocalServiceUtil.addMnForm12(form12List);
						_log.info("form 12 datacount"+form12List.size());
						
						MnForm13LocalServiceUtil.addMnForm13(form13List);
						_log.info("form 13 datacount"+form13List.size());
						
						MnForm14LocalServiceUtil.addMnForm14(form14List);
						_log.info("form 14 datacount"+form14List.size());
						
					} catch (Exception e1) {
						_log.error(e1);
						 resultJsonObject.put("status", false);
						 resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
						 return resultJsonObject;
					}
					
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
			}
			
		}
		}else {
			resultJsonObject.put("status", false);
			resultJsonObject.put("msg","Please upload files with a valid filename.");
			return resultJsonObject;
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

		String fileName = date.getTime() + uploadPortletRequest.getFileName("sbiFile");

		File file = uploadPortletRequest.getFile("sbiFile");

		String mimeType = uploadPortletRequest.getContentType("sbiFile");

		String title = fileName;

		String description = "sbiFile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
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
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
	
}

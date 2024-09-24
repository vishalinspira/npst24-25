//package com.npa.development.service;
//
//import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
//import com.liferay.portal.kernel.log.Log;
//import com.liferay.portal.kernel.log.LogFactoryUtil;
//import com.liferay.portal.kernel.upload.UploadPortletRequest;
//import com.liferay.portal.kernel.util.ParamUtil;
//import com.npa.development.resource.SaveNpaDevelopment;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.portlet.ResourceRequest;
//
//import compliance.service.model.MnNpaDevelopment;
//import compliance.service.service.MnNpaDevelopmentLocalService;
//
//public class NpaDevelopmentImpl implements INpaDevelopment {
//	
//	private static Log _log = LogFactoryUtil.getLog(NpaDevelopmentImpl.class);
//	
//	private MnNpaDevelopmentLocalService npaDevelopmentLocalService;
//	
//	public NpaDevelopmentImpl(SaveNpaDevelopment saveNpaDevelopment) {
//		this.npaDevelopmentLocalService = saveNpaDevelopment.getNpaDevelopmentLocalService();
//	}
//
//	private MnNpaDevelopment create(long userId) {
//		
//		long primaryKey = CounterLocalServiceUtil.increment(MnNpaDevelopment.class.getName());
//		MnNpaDevelopment development = npaDevelopmentLocalService.createMnNpaDevelopment(primaryKey);
//		development.setCreatedby(userId);
//		development.setCreatedon(new Date());
//		
//		return development;
//	}
//
//	@Override
//	public List<MnNpaDevelopment> createModule(UploadPortletRequest uploadPortletRequest, ResourceRequest resourceRequest,
//			long userId) {
//		
//		//long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
//		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
//		_log.info("rowcount " + rowCount);
//		
//		String companies = ParamUtil.getString(resourceRequest, "companies");
//		String strDate = ParamUtil.getString(resourceRequest, "date_1");
//		String[] nameOfSecurity = ParamUtil.getParameterValues(resourceRequest, "securityName[]");
//		String[] legalCaseDetails = ParamUtil.getParameterValues(resourceRequest, "caseDetails[]");
//		String[] currentStatus = ParamUtil.getParameterValues(resourceRequest, "currentStatus[]");
//		
//		_log.info("companies " + companies + " date " + strDate);
//		
//		//JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
//		List<MnNpaDevelopment> list = new ArrayList<MnNpaDevelopment>();
//		
//		for(int i=0; i<rowCount; i++) {
//			_log.info("inside for");
//			
//			_log.info("nameOfSecurity " + nameOfSecurity[i] + " legalCaseDetails " + legalCaseDetails[i] + " currentStatus " + currentStatus[i]);
//			
//			MnNpaDevelopment npaDevelopment = create(userId);
//			npaDevelopment.setCompanies(companies);
//			npaDevelopment.setDate_1(strDate);
//			npaDevelopment.setName_of_security(nameOfSecurity[i]);
//			npaDevelopment.setLegal_case_details(legalCaseDetails[i]);
//			npaDevelopment.setCurrent_status(currentStatus[i]);
//			list.add(npaDevelopment);
//
//			_log.info("npaDevelopment " + npaDevelopment.toString());
//			_log.info("list " + list);
//			//_log.info("jsonArray " + jsonArray);
//			
//		}
//
//		return list;
//	}
//
//	@Override
//	public MnNpaDevelopment persist(MnNpaDevelopment npaDevelopment) {
//		
//		return npaDevelopmentLocalService.addMnNpaDevelopment(npaDevelopment);
//	}
//
//}

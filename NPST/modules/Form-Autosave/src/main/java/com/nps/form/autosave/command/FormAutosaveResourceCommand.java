package com.nps.form.autosave.command;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.nps.form.autosave.constants.AutoSaveConstants;
import com.nps.form.autosave.constants.FormsPortletsKeyConstants;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import nps.common.service.constants.ReportsNameConstants;

@Component(property = { 
		"javax.portlet.name=" + FormsPortletsKeyConstants.ANNUALLY_FORM_PORTLET_KEY,
		"javax.portlet.name=" + FormsPortletsKeyConstants.HY_FORM_PORTLET_KEY,
		"javax.portlet.name=" + FormsPortletsKeyConstants.QUARTERLY_FORM_PORTLET_KEY,
		"javax.portlet.name=" + FormsPortletsKeyConstants.MONTHLY_FORM_PORTLET_KEY,
		"mvc.command.name=" + FormsPortletsKeyConstants.FORM_AUTOSAVE_COMMAND, 
		}, 
service = MVCResourceCommand.class)

public class FormAutosaveResourceCommand implements MVCResourceCommand{
	

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		PrintWriter pw=null;
		try {
		 pw=resourceResponse.getWriter();
		Object obj=null;
		log.info("resorce method called");

		
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		//UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		String fieldType=ParamUtil.getString(resourceRequest, AutoSaveConstants.FIELD_TYPE);
		String fieldName=ParamUtil.getString(resourceRequest, AutoSaveConstants.FIELD_NAME);
		long autoSaveId=ParamUtil.getLong(resourceRequest, AutoSaveConstants.REPORT_UPLOAD_LOG_ID);
		log.info("autoSaveId : "+autoSaveId );
		if(autoSaveId>0) {
		ReportUploadLog reportUploadLog=	ReportUploadLogLocalServiceUtil.getReportUploadLog(autoSaveId);
		ReportMaster reportMaster= ReportMasterLocalServiceUtil.getReportMaster(reportUploadLog.getReportMasterId());
		log.info("fieldType : "+fieldType+" fieldName :  "+fieldName+" autoSaveId  : "+autoSaveId+"  report name : "+reportMaster.getReportName());
		Class clazz=null;
		Class clazzUtil=null;
		//String updateMethodName="";
		if(reportMaster.getReportName().equalsIgnoreCase(ReportsNameConstants.QUARTERLY_COMPLIANCE_CERTIFICATE)) {
			clazz=InputQuarterlyInterval.class;
			InputQuarterlyInterval quarterlyInterval= InputQuarterlyIntervalLocalServiceUtil.fetchInputQuarterlyInterval(autoSaveId);
			//log.info("quarterlyInterval : " +quarterlyInterval);
			if(Validator.isNull(quarterlyInterval)) {
				quarterlyInterval=InputQuarterlyIntervalLocalServiceUtil.createInputQuarterlyInterval(autoSaveId);
				quarterlyInterval.setCreatedby(serviceContext.getUserId());
				
			}
			obj=quarterlyInterval;
			clazzUtil=InputQuarterlyIntervalLocalServiceUtil.class;
		}else if(reportMaster.getReportName().equalsIgnoreCase(ReportsNameConstants.MONTHLY_COMPLIANCE_CERTIFICATE)) {
			clazz=MnCompCertificate.class;
			MnCompCertificate compCertificate=MnCompCertificateLocalServiceUtil.fetchMnCompCertificate(autoSaveId);
			if(Validator.isNull(compCertificate)) {
				compCertificate=MnCompCertificateLocalServiceUtil.createMnCompCertificate(autoSaveId);
				compCertificate.setCreatedby(serviceContext.getUserId());
			}
			obj=compCertificate;
			clazzUtil=MnCompCertificateLocalServiceUtil.class;
		}else if(reportMaster.getReportName().equalsIgnoreCase(ReportsNameConstants.ANNUALLY_COMPLIANCE_CERTIFICATE)) {
			clazz=AnnualCompCertificate.class;
			AnnualCompCertificate anCompCert=AnnualCompCertificateLocalServiceUtil.fetchAnnualCompCertificate(autoSaveId);
			if(Validator.isNull(anCompCert)) {
				anCompCert= AnnualCompCertificateLocalServiceUtil.createAnnualCompCertificate(autoSaveId);
				anCompCert.setCreatedby(serviceContext.getUserId());
			}
			obj=anCompCert;
			clazzUtil=AnnualCompCertificateLocalServiceUtil.class;
		}
		
		Method method=null;
		
//		if(autoSaveId<=0) {
//			foo=FooLocalServiceUtil.createFoo(CounterLocalServiceUtil.increment());
//			foo.setGroupId(serviceContext.getScopeGroupId());
//			foo.setCompanyId(serviceContext.getCompanyId());
//			foo.setUserId(serviceContext.getUserId());
//		}else {
//			foo=FooLocalServiceUtil.getFoo(autoSaveId);
//		}
		
		log.info("fieldName: "+fieldName);
		String methodName="set"+fieldName.substring(0,1).toUpperCase()+""+fieldName.substring(1);
		
		if(fieldType.equalsIgnoreCase(AutoSaveConstants.FIELD_TYPE_STRING)) {
			String fieldValue=ParamUtil.getString(resourceRequest, AutoSaveConstants.FIELD_VALUE);
			method= clazz.getMethod(methodName,fieldValue.getClass());
			method.invoke(obj, fieldValue);
			log.info("fieldType : "+fieldType +"   fieldName  : "+fieldName+"  fieldValue :  "+fieldValue+"   autoSaveId  : "+autoSaveId);
		}else if (fieldType.equalsIgnoreCase(AutoSaveConstants.FIELD_TYPE_INT)) {
			Integer fieldValue=ParamUtil.getInteger(resourceRequest, AutoSaveConstants.FIELD_VALUE);
			method= clazz.getMethod(methodName,fieldValue.getClass());
			method.invoke(obj, fieldValue);
			log.info("fieldType : "+fieldType +"   fieldName  : "+fieldName+"  fieldValue :  "+fieldValue+"   autoSaveId  : "+autoSaveId);
		}else if (fieldType.equalsIgnoreCase(AutoSaveConstants.FIELD_TYPE_DATE)) {
			Date fieldValue=ParamUtil.getDate(resourceRequest, AutoSaveConstants.FIELD_VALUE,simpleDateFormat);
			method= clazz.getMethod(methodName,fieldValue.getClass());
			method.invoke(obj, fieldValue);
			log.info("fieldType : "+fieldType +"   fieldName  : "+fieldName+"  fieldValue :  "+fieldValue+"   autoSaveId  : "+autoSaveId);
		}else if (fieldType.equalsIgnoreCase(AutoSaveConstants.FIELD_TYPE_FILE)) {
			try {
			//File file=uploadRequest.getFile(AutoSaveFieldConstant.FIELD_VALUE);
			//log.info("fieldType : "+fieldType +"   fieldName  : "+fieldName+"  fieldValue :  "+file+"   autoSaveId  : "+autoSaveId);
//			Long fieldValue=NpstCommonUtil.fileUpload(uploadRequest, 0, "AutoSaveFieldConstant.FIELD_VALUE", serviceContext);
//			method= Foo.class.getMethod(methodName,fieldValue.getClass());
//			method.invoke(foo, fieldValue);
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		// Class<FooLocalServiceUtil> fooObj = FooLocalServiceUtil.class;	
		log.info("update"+clazz.getSimpleName());
		 Method updateFoo = clazzUtil.getDeclaredMethod("update"+clazz.getSimpleName(), clazz);
		  updateFoo.invoke(null, obj); 

		 log.info("success---------------------------");
		}
}catch (Exception e) {
	log.info(e);
}
	pw.write("saved successfully");
	return false;
	}


	private static final Log log=LogFactoryUtil.getLog(FormAutosaveResourceCommand.class.getName());
	
}

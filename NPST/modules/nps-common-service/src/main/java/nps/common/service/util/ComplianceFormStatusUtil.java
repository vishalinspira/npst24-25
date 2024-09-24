package nps.common.service.util;

import com.daily.average.service.model.InputQuarterlyInterval;
import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil;
import com.daily.average.service.service.MnCompCertificateLocalServiceUtil;
import com.daily.average.service.service.Pfm_Qr_Internal_Audit_ReportLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import nps.common.service.constants.ReportsNameConstants;

public class ComplianceFormStatusUtil {
	
	
	public static void updateReportUploadLogStatus(int status,Long reportUploadLogId) {
		try {
		ReportUploadLog  reportUploadLog = ReportUploadLogLocalServiceUtil.getReportUploadLog(reportUploadLogId);
		ReportMaster reportMaster=ReportMasterLocalServiceUtil.getReportMaster(reportUploadLog.getReportMasterId());
		_log.info("Report name: -  "+reportMaster.getReportName());
		
		if(reportMaster.getReportName().equalsIgnoreCase(ReportsNameConstants.QUARTERLY_COMPLIANCE_CERTIFICATE)) {
			_log.info("quarterly compliance certificate Report name : -  "+reportMaster.getReportName());
			
			InputQuarterlyInterval inputQuarterlyInterval=  InputQuarterlyIntervalLocalServiceUtil.getInputQuarterlyInterval(reportUploadLogId);
			inputQuarterlyInterval.setIsAmRejected(1);
			InputQuarterlyIntervalLocalServiceUtil.updateInputQuarterlyInterval(inputQuarterlyInterval);
		}else if(reportMaster.getReportName().equalsIgnoreCase(ReportsNameConstants.QUARTERLY_COMPLIANCE_CERTIFICATE)) {
			_log.info("quarterly compliance certificate Report name : -  "+reportMaster.getReportName());
			MnCompCertificate mnCompCertificate=MnCompCertificateLocalServiceUtil.getMnCompCertificate(reportUploadLogId);
			mnCompCertificate.setIsAmRejected(1);
			MnCompCertificateLocalServiceUtil.updateMnCompCertificate(mnCompCertificate);
		}else if(reportMaster.getReportName().equalsIgnoreCase(ReportsNameConstants.QUARTERLY_IAR)) {
			_log.info("Quarterly Internal audit report name : -  "+reportMaster.getReportName());
			Pfm_Qr_Internal_Audit_Report qr_iar= Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.fetchPfm_Qr_Internal_Audit_Report(reportUploadLogId);
			qr_iar.setIsAmRejected(1);
			Pfm_Qr_Internal_Audit_ReportLocalServiceUtil.updatePfm_Qr_Internal_Audit_Report(qr_iar);
		}
		
		}catch (Exception e) {
			_log.error(e);
		}

	}
	
	private static Log _log=LogFactoryUtil.getLog(ComplianceFormStatusUtil.class.getName());
}

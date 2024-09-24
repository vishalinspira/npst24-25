package ManpowerForm1.asset;

import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.ReportMasterLocalService;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalService;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalService;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

public class ManpowerForm1_AssetRender extends BaseJSPAssetRenderer<Manpowerform_i>{
private final Manpowerform_i manpowerform_i;
	
	@Reference
	UserLocalService userLocalService; 
	
	public ManpowerForm1_AssetRender(Manpowerform_i manpowerform_i) {
		this.manpowerform_i = manpowerform_i;
	}

	@Override
	public Manpowerform_i getAssetObject() {
		return manpowerform_i;
	}

	@Override
	public long getGroupId() {
		return userLocalService.fetchUser(manpowerform_i.getCreatedby()).getGroupId();
	}

	@Override
	public long getUserId() {
		return manpowerform_i.getCreatedby();
	}

	@Override
	public String getUserName() {
		return userLocalService.fetchUser(manpowerform_i.getCreatedby()).getFullName();		
	}

	@Override
	public String getUuid() {
		return manpowerform_i.getUuid();
	}

	@Override
	public String getClassName() {
		return Manpowerform_i.class.getName();
	}

	@Override
	public long getClassPK() {
		return manpowerform_i.getPrimaryKey();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(manpowerform_i.getReportMasterId());
		return  "Report Name : "+reportMaster.getReportName() +", Report due date"+manpowerform_i.getReportDate();
	}

	@Override
	public String getTitle(Locale locale) {
		ReportMaster reportMaster =  ReportMasterLocalServiceUtil.fetchReportMaster(manpowerform_i.getReportMasterId());
		return reportMaster.getReportName();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String reportName = "NA";
		String uploadDate = "dd/MM/yyyy hh:mm";
		String annex_comp_certificate = "";
		String board_a = "";
		String board_b = "";
		String investment_a = "";
		String investment_b = "";
		String risk_a = "";
		String risk_b = "";
		String annex_ii = "";
		String annex_iii = "";
		String annex_iv = "";
		boolean isNonNPSUser = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
		try {
			_log.info("manpowerform_i---"+manpowerform_i);
			if(manpowerform_i!=null) {
				Long reportUploadLogId = manpowerform_i.getPrimaryKey();
				httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
				_log.info("reportUploadLogId---"+reportUploadLogId);
				//ReportUploadLog reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
				if(reportUploadLogId!=null) {
					try {
						httpServletRequest.setAttribute("reportUploadLogId", reportUploadLogId);
						reportUploadFileLogs = ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
						/*annex_comp_certificate = CompCerUtil.getDocumentURL(manpowerform_i.getAnnex_comp_certificate());
						board_a =  CompCerUtil.getDocumentURL(manpowerform_i.getBoard_a());
						board_b = CompCerUtil.getDocumentURL(manpowerform_i.getBoard_b());
						investment_a = CompCerUtil.getDocumentURL(manpowerform_i.getInvestment_a());
						investment_b = CompCerUtil.getDocumentURL(manpowerform_i.getInvestment_b());
						risk_a = CompCerUtil.getDocumentURL(manpowerform_i.getRisk_a());
						risk_b = CompCerUtil.getDocumentURL(manpowerform_i.getRisk_b());
						annex_ii = CompCerUtil.getDocumentURL(manpowerform_i.getAnnex_ii());
						annex_iii = CompCerUtil.getDocumentURL(manpowerform_i.getAnnex_iii());
						annex_iv = CompCerUtil.getDocumentURL(manpowerform_i.getAnnex_iv());*/
					} catch (Exception e) {
						_log.error(e);
					}
				}
				try {
					ReportMaster reportMaster = ReportMasterLocalServiceUtil.getReportMaster(manpowerform_i.getReportMasterId());
					reportName = reportMaster.getReportName() + (Validator.isNotNull(reportMaster.getCra()) ? " - " + reportMaster.getCra() : "");
				} catch (Exception e) {
					_log.error("Exception on getting report file name : "+e.getMessage());
				}
				uploadDate = dateFormat.format(manpowerform_i.getCreatedon());
			}
			//ComplianceCertificateAtQuartelyIntervalUtil complianceCertificateAtQuartelyIntervalUtil = new ComplianceCertificateAtQuartelyIntervalUtil();
			//isNonNPSUser = complianceCertificateAtQuartelyIntervalUtil.isNonNpsUser(themeDisplay.getUserId());
			
		} catch(Exception e) {
			_log.error(e);
		}
		
		httpServletRequest.setAttribute("reportName", reportName);
		httpServletRequest.setAttribute("uploadDate", uploadDate);
		httpServletRequest.setAttribute("reportUploadFileLogs", reportUploadFileLogs);
		httpServletRequest.setAttribute("manpowerform_i", manpowerform_i);
		
		httpServletRequest.setAttribute("annex_comp_certificate", annex_comp_certificate);
		httpServletRequest.setAttribute("board_a", board_a);
		httpServletRequest.setAttribute("board_b", board_b);
		httpServletRequest.setAttribute("investment_a", investment_a);
		httpServletRequest.setAttribute("investment_b", investment_b);
		httpServletRequest.setAttribute("risk_a", risk_a);
		httpServletRequest.setAttribute("risk_b", risk_b);
		httpServletRequest.setAttribute("annex_ii", annex_ii);
		httpServletRequest.setAttribute("annex_iii", annex_iii);
		httpServletRequest.setAttribute("annex_iv", annex_iv);
		httpServletRequest.setAttribute("isNonNPSUser", isNonNPSUser);
		
		return "/asset/scrutiny.jsp";
	}
	
	@Reference
	ReportMasterLocalService reportMasterLocalService;
	
	@Reference
	ReportUploadLogLocalService reportUploadLogLocalService;
	
	@Reference	
	ReportUploadFileLogLocalService reportUploadFileLogLocalService;
	
	Log _log = LogFactoryUtil.getLog(ManpowerForm1_AssetRender.class);

}


package com.internal.audit.report.resource;

import com.daily.average.service.model.InternalAuditReport;
import com.daily.average.service.service.InternalAuditReportLocalServiceUtil;
import com.internal.audit.report.constants.InternalAuditReportPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { "javax.portlet.name=" + InternalAuditReportPortletKeys.INTERNALAUDITREPORT,
		"mvc.command.name=/internalauditreport/save" }, service = MVCResourceCommand.class)
public class SaveInternalAuditReport implements MVCResourceCommand {
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = auditreportupload(resourceRequest, resourceResponse, themeDisplay);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		return false;
	}

	public JSONObject auditreportupload(ResourceRequest resourceRequest, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) {
		JSONObject jsonObject= JSONFactoryUtil.createJSONObject();
		long createdby = themeDisplay.getUserId();
		
		String observations_i_a= ParamUtil.getString(resourceRequest, "observations_i_a");
		String management_i_a= ParamUtil.getString(resourceRequest, "management_i_a");
		String observations_i_b= ParamUtil.getString(resourceRequest, "observations_i_b");
		String management_i_b= ParamUtil.getString(resourceRequest, "management_i_b");
		String observations_i_c= ParamUtil.getString(resourceRequest, "observations_i_c");
		String management_i_c= ParamUtil.getString(resourceRequest, "management_i_c");
		String observations_i_d= ParamUtil.getString(resourceRequest, "observations_i_d");
		String management_i_d= ParamUtil.getString(resourceRequest, "management_i_d");
		String observations_ii_a= ParamUtil.getString(resourceRequest, "observations_ii_a");
		String management_ii_a= ParamUtil.getString(resourceRequest, "management_ii_a");
		String observations_ii_b= ParamUtil.getString(resourceRequest, "observations_ii_b");
		String management_ii_b= ParamUtil.getString(resourceRequest, "management_ii_b");
		String observations_ii_c= ParamUtil.getString(resourceRequest, "observations_ii_c");
		String management_ii_c= ParamUtil.getString(resourceRequest, "management_ii_c");
		String observations_ii_d= ParamUtil.getString(resourceRequest, "observations_ii_d");
		String management_ii_d= ParamUtil.getString(resourceRequest, "management_ii_d");
		String observations_iii_a= ParamUtil.getString(resourceRequest, "observations_iii_a");
		String management_iii_a= ParamUtil.getString(resourceRequest, "management_iii_a");
		String observations_iii_b= ParamUtil.getString(resourceRequest, "observations_iii_b");
		String management_iii_b= ParamUtil.getString(resourceRequest, "management_iii_b");
		String observations_iii_c= ParamUtil.getString(resourceRequest, "observations_iii_c");
		String management_iii_c= ParamUtil.getString(resourceRequest, "management_iii_c");
		String observations_iii_d= ParamUtil.getString(resourceRequest, "observations_iii_d");
		String management_iii_d= ParamUtil.getString(resourceRequest, "management_iii_d");
		String observations_iii_e= ParamUtil.getString(resourceRequest, "observations_iii_e");
		String management_iii_e= ParamUtil.getString(resourceRequest, "management_iii_e");
		String observations_iii_f= ParamUtil.getString(resourceRequest, "observations_iii_f");
		String management_iii_f= ParamUtil.getString(resourceRequest, "management_iii_f");
		String observations_iii_g= ParamUtil.getString(resourceRequest, "observations_iii_g");
		String management_iii_g= ParamUtil.getString(resourceRequest, "management_iii_g");
		String observations_iii_h= ParamUtil.getString(resourceRequest, "observations_iii_h");
		String management_iii_h= ParamUtil.getString(resourceRequest, "management_iii_h");
		String observations_iii_i= ParamUtil.getString(resourceRequest, "observations_iii_i");
		String management_iii_i= ParamUtil.getString(resourceRequest, "management_iii_i");
		String observations_iv_a= ParamUtil.getString(resourceRequest, "observations_iv_a");
		String management_iv_a= ParamUtil.getString(resourceRequest, "management_iv_a");
		String observations_iv_b= ParamUtil.getString(resourceRequest, "observations_iv_b");
		String management_iv_b= ParamUtil.getString(resourceRequest, "management_iv_b");
		String observations_iv_c= ParamUtil.getString(resourceRequest, "observations_iv_c");
		String management_iv_c= ParamUtil.getString(resourceRequest, "management_iv_c");
		String observations_iv_d= ParamUtil.getString(resourceRequest, "observations_iv_d");
		String management_iv_d= ParamUtil.getString(resourceRequest, "management_iv_d");
		String observations_v_a= ParamUtil.getString(resourceRequest, "observations_v_a");
		String management_v_a= ParamUtil.getString(resourceRequest, "management_v_a");
		String observations_v_b= ParamUtil.getString(resourceRequest, "observations_v_b");
		String management_v_b= ParamUtil.getString(resourceRequest, "management_v_b");
		String observations_v_c= ParamUtil.getString(resourceRequest, "observations_v_c");
		String management_v_c= ParamUtil.getString(resourceRequest, "management_v_c");
		String observations_v_d= ParamUtil.getString(resourceRequest, "observations_v_d");
		String management_v_d= ParamUtil.getString(resourceRequest, "management_v_d");
		String observations_vi_a= ParamUtil.getString(resourceRequest, "observations_vi_a");
		String management_vi_a= ParamUtil.getString(resourceRequest, "management_vi_a");
		String observations_vi_b= ParamUtil.getString(resourceRequest, "observations_vi_b");
		String management_vi_b= ParamUtil.getString(resourceRequest, "management_vi_b");
		String observations_vi_c= ParamUtil.getString(resourceRequest, "observations_vi_c");
		String management_vi_c= ParamUtil.getString(resourceRequest, "management_vi_c");
		String observations_vi_d= ParamUtil.getString(resourceRequest, "observations_vi_d");
		String management_vi_d= ParamUtil.getString(resourceRequest, "management_vi_d");
		String observations_vi_e= ParamUtil.getString(resourceRequest, "observations_vi_e");
		String management_vi_e= ParamUtil.getString(resourceRequest, "management_vi_e");
		String observations_vii_a= ParamUtil.getString(resourceRequest, "observations_vii_a");
		String management_vii_a= ParamUtil.getString(resourceRequest, "management_vii_a");
		String observations_vii_b= ParamUtil.getString(resourceRequest, "observations_vii_b");
		String management_vii_b= ParamUtil.getString(resourceRequest, "management_vii_b");
		String observations_vii_c= ParamUtil.getString(resourceRequest, "observations_vii_c");
		String management_vii_c= ParamUtil.getString(resourceRequest, "management_vii_c");
		String observations_vii_d= ParamUtil.getString(resourceRequest, "observations_vii_d");
		String management_vii_d= ParamUtil.getString(resourceRequest, "management_vii_d");
		String observations_vii_e= ParamUtil.getString(resourceRequest, "observations_vii_e");
		String management_vii_e= ParamUtil.getString(resourceRequest, "management_vii_e");
		String observations_vii_f= ParamUtil.getString(resourceRequest, "observations_vii_f");
		String management_vii_f= ParamUtil.getString(resourceRequest, "management_vii_f");
		String observations_vii_g= ParamUtil.getString(resourceRequest, "observations_vii_g");
		String management_vii_g= ParamUtil.getString(resourceRequest, "management_vii_g");
		String observations_viii_a= ParamUtil.getString(resourceRequest, "observations_viii_a");
		String management_viii_a= ParamUtil.getString(resourceRequest, "management_viii_a");
		String observations_ix_a= ParamUtil.getString(resourceRequest, "observations_ix_a");
		String management_ix_a= ParamUtil.getString(resourceRequest, "management_ix_a");
		String observations_ix_b= ParamUtil.getString(resourceRequest, "observations_ix_b");
		String management_ix_b= ParamUtil.getString(resourceRequest, "management_ix_b");
		String observations_ix_c= ParamUtil.getString(resourceRequest, "observations_ix_c");
		String management_ix_c= ParamUtil.getString(resourceRequest, "management_ix_c");
		String observations_ix_d= ParamUtil.getString(resourceRequest, "observations_ix_d");
		String management_ix_d= ParamUtil.getString(resourceRequest, "management_ix_d");
		String observations_x_a= ParamUtil.getString(resourceRequest, "observations_x_a");
		String management_x_a= ParamUtil.getString(resourceRequest, "management_x_a");
		String observations_x_b= ParamUtil.getString(resourceRequest, "observations_x_b");
		String management_x_b= ParamUtil.getString(resourceRequest, "management_x_b");
		String observations_x_c= ParamUtil.getString(resourceRequest, "observations_x_c");
		String management_x_c= ParamUtil.getString(resourceRequest, "management_x_c");
		String observations_x_d= ParamUtil.getString(resourceRequest, "observations_x_d");
		String management_x_d= ParamUtil.getString(resourceRequest, "management_x_d");
		String observations_x_e= ParamUtil.getString(resourceRequest, "observations_x_e");
		String management_x_e= ParamUtil.getString(resourceRequest, "management_x_e");
		String observations_x_f= ParamUtil.getString(resourceRequest, "observations_x_f");
		String management_x_f= ParamUtil.getString(resourceRequest, "management_x_f");
		String observations_x_g= ParamUtil.getString(resourceRequest, "observations_x_g");
		String management_x_g= ParamUtil.getString(resourceRequest, "management_x_g");
		String observations_xi_a= ParamUtil.getString(resourceRequest, "observations_xi_a");
		String management_xi_a= ParamUtil.getString(resourceRequest, "management_xi_a");
		String observations_xi_b= ParamUtil.getString(resourceRequest, "observations_xi_b");
		String management_xi_b= ParamUtil.getString(resourceRequest, "management_xi_b");
		String observations_xi_c= ParamUtil.getString(resourceRequest, "observations_xi_c");
		String management_xi_c= ParamUtil.getString(resourceRequest, "management_xi_c");
		String observations_xi_d= ParamUtil.getString(resourceRequest, "observations_xi_d");
		String management_xi_d= ParamUtil.getString(resourceRequest, "management_xi_d");
		String observations_xii_a= ParamUtil.getString(resourceRequest, "observations_xii_a");
		String management_xii_a= ParamUtil.getString(resourceRequest, "management_xii_a");
		String observations_xii_b= ParamUtil.getString(resourceRequest, "observations_xii_b");
		String management_xii_b= ParamUtil.getString(resourceRequest, "management_xii_b");
		String observations_xii_c= ParamUtil.getString(resourceRequest, "observations_xii_c");
		String management_xii_c= ParamUtil.getString(resourceRequest, "management_xii_c");
		String observations_xii_d= ParamUtil.getString(resourceRequest, "observations_xii_d");
		String management_xii_d= ParamUtil.getString(resourceRequest, "management_xii_d");
		String observations_xii_e= ParamUtil.getString(resourceRequest, "observations_xii_e");
		String management_xii_e= ParamUtil.getString(resourceRequest, "management_xii_e");
		String observations_xiii_a= ParamUtil.getString(resourceRequest, "observations_xiii_a");
		String management_xiii_a= ParamUtil.getString(resourceRequest, "management_xiii_a");
		String observations_xiii_b= ParamUtil.getString(resourceRequest, "observations_xiii_b");
		String management_xiii_b= ParamUtil.getString(resourceRequest, "management_xiii_b");
		String observations_xiii_c= ParamUtil.getString(resourceRequest, "observations_xiii_c");
		String management_xiii_c= ParamUtil.getString(resourceRequest, "management_xiii_c");
		String observations_xiii_d= ParamUtil.getString(resourceRequest, "observations_xiii_d");
		String management_xiii_d= ParamUtil.getString(resourceRequest, "management_xiii_d");
		String observation_xiv__a= ParamUtil.getString(resourceRequest, "observation_xiv__a");
		String management_xiv_a= ParamUtil.getString(resourceRequest, "management_xiv_a");
		String observations_xiv_b= ParamUtil.getString(resourceRequest, "observations_xiv_b");
		String management_xiv_b= ParamUtil.getString(resourceRequest, "management_xiv_b");
		String observations_xiv_c= ParamUtil.getString(resourceRequest, "observations_xiv_c");
		String management_xiv_c= ParamUtil.getString(resourceRequest, "management_xiv_c");
		String observations_xiv_d= ParamUtil.getString(resourceRequest, "observations_xiv_d");
		String management_xiv_d= ParamUtil.getString(resourceRequest, "management_xiv_d");
		String observations_xiv_e= ParamUtil.getString(resourceRequest, "observations_xiv_e");
		String management_xiv_e= ParamUtil.getString(resourceRequest, "management_xiv_e");
		String observations_xiv_f= ParamUtil.getString(resourceRequest, "observations_xiv_f");
		String management_xiv_f= ParamUtil.getString(resourceRequest, "management_xiv_f");
		String observations_xiv_g= ParamUtil.getString(resourceRequest, "observations_xiv_g");
		String management_xiv_g= ParamUtil.getString(resourceRequest, "management_xiv_g");
		String observations_xv_a= ParamUtil.getString(resourceRequest, "observations_xv_a");
		String management_xv_a= ParamUtil.getString(resourceRequest, "management_xv_a");
		String observations_xv_b= ParamUtil.getString(resourceRequest, "observations_xv_b");
		String management_xv_b= ParamUtil.getString(resourceRequest, "management_xv_b");
		String observations_xv_c= ParamUtil.getString(resourceRequest, "observations_xv_c");
		String management_xv_c= ParamUtil.getString(resourceRequest, "management_xv_c");
		String observations_xvi_a= ParamUtil.getString(resourceRequest, "observations_xvi_a");
		String management_xvi_a= ParamUtil.getString(resourceRequest, "management_xvi_a");
		String observations_xvi_b= ParamUtil.getString(resourceRequest, "observations_xvi_b");
		String management_xvi_b= ParamUtil.getString(resourceRequest, "management_xvi_b");
		String observations_xvii_a= ParamUtil.getString(resourceRequest, "observations_xvii_a");
		String management_xvii_a= ParamUtil.getString(resourceRequest, "management_xvii_a");
		String observations_xvii_b= ParamUtil.getString(resourceRequest, "observations_xvii_b");
		String management_xvii_b= ParamUtil.getString(resourceRequest, "management_xvii_b");
		String observations_xvii_c= ParamUtil.getString(resourceRequest, "observations_xvii_c");
		String management_xvii_c= ParamUtil.getString(resourceRequest, "management_xvii_c");
		String observations_xvii_d= ParamUtil.getString(resourceRequest, "observations_xvii_d");
		String management_xvii_d= ParamUtil.getString(resourceRequest, "management_xvii_d");
		String observations_xviii_a= ParamUtil.getString(resourceRequest, "observations_xviii_a");
		String management_xviii_a= ParamUtil.getString(resourceRequest, "management_xviii_a");
		String observations_xviii_b= ParamUtil.getString(resourceRequest, "observations_xviii_b");
		String management_xviii_b= ParamUtil.getString(resourceRequest, "management_xviii_b");
		String observations_xviii_c= ParamUtil.getString(resourceRequest, "observations_xviii_c");
		String management_xviii_c= ParamUtil.getString(resourceRequest, "management_xviii_c");
		String observations_xviii_d= ParamUtil.getString(resourceRequest, "observations_xviii_d");
		String management_xviii_d= ParamUtil.getString(resourceRequest, "management_xviii_d");
		String observations_xix_a= ParamUtil.getString(resourceRequest, "observations_xix_a");
		String management_xix_a= ParamUtil.getString(resourceRequest, "management_xix_a");
        
		InternalAuditReport internalAuditReport = InternalAuditReportLocalServiceUtil.addInternalAuditReport(observations_i_a, management_i_a, observations_i_b, management_i_b, observations_i_c, management_i_c, observations_i_d, observations_ii_a, management_ii_a, observations_ii_b, management_ii_b, observations_ii_c, management_ii_c, observations_ii_d, management_ii_d, observations_iii_a, management_iii_a, observations_iii_b, management_iii_b, observations_iii_c, management_iii_c, observations_iii_d, management_iii_d, observations_iii_e, management_iii_e, observations_iii_f, management_iii_f, observations_iii_g, management_iii_g, observations_iii_h, management_iii_h, observations_iii_i, management_iii_i, management_iv_a, observations_iv_b, observations_iv_a, management_iv_b, observations_iv_c, observations_iv_d, management_iv_d, management_iv_c, observations_v_a, management_v_a, observations_v_b, observations_v_c, management_v_b, management_v_c, observations_v_d, management_v_d, observations_vi_a, management_vi_a, observations_vi_b, management_vi_b, observations_vi_c, management_vi_c, observations_vi_d, management_vi_d, observations_vi_e, management_vi_e, observations_vii_a, management_vii_a, observations_vii_b, management_vii_b, observations_vii_c, management_vii_c, observations_vii_d, management_vii_d, observations_vii_e, management_vii_e, observations_vii_f, management_vii_f, observations_vii_g, management_vii_g, management_viii_a, observations_ix_a, management_ix_a, observations_viii_a, observations_ix_b, management_ix_b, observations_ix_c, management_ix_c, observations_ix_d, management_ix_d, observations_x_a, management_x_a, observations_x_b, management_x_b, observations_x_c, management_x_c, observations_x_d, management_x_d, observations_x_e, management_x_e, observations_x_f, management_x_f, observations_x_g, management_x_g, observations_xi_a, management_xi_a, observations_xi_b, management_xi_b, observations_xi_c, management_xi_c, observations_xi_d, management_xi_d, observations_xii_a, management_xii_a, observations_xii_b, management_xii_b, observations_xii_c, management_xii_c, observations_xii_d, management_xii_d, observations_xii_e, management_xii_e, observations_xiii_a, management_xiii_a, observations_xiii_b, management_xiii_b, observations_xiii_c, management_xiii_c, observations_xiii_d, management_xiii_d, observation_xiv__a, management_xiv_a, observations_xiv_b, management_xiv_b, observations_xiv_c, management_xiv_c, observations_xiv_d, management_xiv_d, observations_xiv_e, management_xiv_e, observations_xiv_f, management_xiv_f, observations_xiv_g, management_xiv_g, observations_xv_a, management_xv_a, observations_xv_b, management_xv_b, management_xv_c, observations_xv_c, observations_xvi_a, management_xvi_a, observations_xvi_b, management_xvi_b, observations_xvii_a, management_xvii_a, observations_xvii_b, management_xvii_b, observations_xvii_c, management_xvii_c, observations_xvii_d, management_xvii_d, observations_xviii_a, management_xviii_a, observations_xviii_b, management_xviii_b, observations_xviii_c, management_xviii_c, observations_xviii_d, management_xviii_d, observations_xix_a, management_xix_a, createdby,management_i_d);
		return jsonObject;
	}
	 Log _log = LogFactoryUtil.getLog(getClass());
}

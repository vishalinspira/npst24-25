package nps.common.service.util;

import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil;
import com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny;
import com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

public class PfmHyccUtil {
	private static Log _log = LogFactoryUtil.getLog(PfmHyccUtil.class);
	public static JSONObject getloopingContexOfDetailedAuditReport() {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("1", 1);
		jsonObject.put("2", 1);
		jsonObject.put("3", 1);
		jsonObject.put("4", 1);
	
		
		return jsonObject;
	}
	
	public static JSONObject getHyComp_JSON_PDFdata(JSONObject jo_hyComp_data) {
		
		JSONObject jsonObject = getloopingContexOfDetailedAuditReport();
		JSONObject jsonObjectf = JSONFactoryUtil.createJSONObject();
		try {	
	for(String key : jsonObject.keySet()) {		
		JSONObject jsonObject_hyComp = JSONFactoryUtil.createJSONObject();
		long size = jsonObject.getLong(key);	
		jsonObject_hyComp = jo_hyComp_data.getJSONObject(key);
		
		for(int i=1; i<=size; i++) {
			jsonObjectf.put(key+"_"+i, jsonObject_hyComp.get(key+"_"+i));
			jsonObjectf.put(key+"_rem_"+i, jsonObject_hyComp.get(key+"_rem_"+i));
			jsonObjectf.put(key+"_npsrem_"+i, "");
		}	
	}
		}catch (Exception e) {
			_log.error(e);
		}
		return jsonObjectf;
	}
	
	
	public static JSONObject getHyComp_JSON_data(long reportUploadlogId) {
		JSONObject jsonObject = getloopingContexOfDetailedAuditReport();
		JSONObject jsonObjectf = JSONFactoryUtil.createJSONObject();
		try {
		PFM_hy_comp_cert pfm_hy_comp_cert= PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportUploadlogId);

		PFM_hy_comp_cert_Scrutiny pfm_hy_comp_certScrutiny=null;
		JSONObject jo_hyCompscr_data =null;
		try {
			List<PFM_hy_comp_cert_Scrutiny> comp_cert_Scrutinies=PFM_hy_comp_cert_ScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadlogId);
			 pfm_hy_comp_certScrutiny=comp_cert_Scrutinies.get(comp_cert_Scrutinies.size()-1);
			 jo_hyCompscr_data =JSONFactoryUtil.createJSONObject(pfm_hy_comp_certScrutiny.getNps_comments());	
			 if(Validator.isNull(jo_hyCompscr_data)) {
				 jo_hyCompscr_data=JSONFactoryUtil.createJSONObject();
			 }
		}catch (Exception e) {
			jo_hyCompscr_data=JSONFactoryUtil.createJSONObject();
			_log.error(e);
		}
		JSONObject jo_hyComp_data=JSONFactoryUtil.createJSONObject();
		if(Validator.isNotNull(pfm_hy_comp_cert)) {
			jo_hyComp_data =JSONFactoryUtil.createJSONObject(pfm_hy_comp_cert.getHycc_details());
		}
			try {	
	for(String key : jsonObject.keySet()) {
		try {
		JSONObject jsonObject_hyComp = JSONFactoryUtil.createJSONObject();
		JSONObject jo_hyComp_scr = JSONFactoryUtil.createJSONObject();
		long size = jsonObject.getLong(key);	
		//jsonObject_three = getJsonData(key+"_", size);
		jsonObject_hyComp = jo_hyComp_data.getJSONObject(key);
		jo_hyComp_scr = jo_hyCompscr_data.getJSONObject(key);

		for(int i=1; i<=size; i++) {
			try {
			jsonObjectf.put(key+"_"+i, jsonObject_hyComp.get(key+"_"+i));
			jsonObjectf.put(key+"_rem_"+i, jsonObject_hyComp.get(key+"_rem_"+i));
			}catch (Exception e) {
				jsonObjectf.put(key+"_"+i, "");
				jsonObjectf.put(key+"_rem_"+i,"");
				_log.error(e.getCause()+"  :  "+e.getMessage());
			}
			try {
			jsonObjectf.put(key+"_npsrem_"+i, jo_hyComp_scr.get(key+"_npsrem_"+i));
			}catch (Exception e) {
				jsonObjectf.put(key+"_npsrem_"+i, "");
				_log.error(e.getCause()+"  :  "+e.getMessage());
			}
		}
		}catch (Exception e) {
			_log.error(e.getCause()+"  :  "+e.getMessage());
		}
	}
		}catch (Exception e) {
			_log.error(e.getCause()+"  :  "+e.getMessage());
		}
		}catch (Exception e) {
			_log.error(e.getCause()+"  :  "+e.getMessage());
		}
		return jsonObjectf;
	}
	
}

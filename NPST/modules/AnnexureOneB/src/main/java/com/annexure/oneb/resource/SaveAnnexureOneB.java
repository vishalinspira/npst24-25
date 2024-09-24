package com.annexure.oneb.resource;

import com.annexure.oneb.constants.AnnexureOneBPortletKeys;
import com.daily.average.service.service.AnnexureOneBTableOneLocalServiceUtil;
import com.daily.average.service.service.AnnexureOneBTableTwoLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { 
		"javax.portlet.name=" +  AnnexureOneBPortletKeys.ANNEXUREONEB,
		"mvc.command.name=/AnnexureOneBForm/save" 
	}, service = MVCResourceCommand.class
)
public class SaveAnnexureOneB implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		System.err.println("SaveAnnexureOneB.serveResource()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String rowcount_first_table = ParamUtil.getString(resourceRequest, "rowcount_first_table");
		String rowcount_secound_table = ParamUtil.getString(resourceRequest, "rowcount_secound_table");
		boolean isError = false;
		String Fromdate = ParamUtil.getString(resourceRequest, "Fromdate");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date as_on_date = null;
		try {
			as_on_date = date.parse(Fromdate);
		} catch (ParseException e1) {
			isError = true;
			_log.error("Exception in date  : "+e1.getMessage());
		}
		
		if(!rowcount_first_table.trim().equals("") || rowcount_first_table != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_first_table.trim()); i++) {
				
				String name_of_pension_fund = ParamUtil.getString(resourceRequest, "name_of_pension_fund"+String.valueOf(i));
				String auc = ParamUtil.getString(resourceRequest, "auc"+String.valueOf(i));
				String aum = ParamUtil.getString(resourceRequest, "aum"+String.valueOf(i));
				String aum_under_coustody = ParamUtil.getString(resourceRequest, "aum_under_coustody"+String.valueOf(i));
				String asset_not_under_coustody = ParamUtil.getString(resourceRequest, "asset_not_under_coustody"+String.valueOf(i));

				if(as_on_date != null) {
					try {
						AnnexureOneBTableOneLocalServiceUtil.addAnnexureOneBTableOne(name_of_pension_fund, auc, aum, aum_under_coustody, asset_not_under_coustody, new Date(), themeDisplay.getUserId(), as_on_date);
					} catch (Exception e) {
						isError = true;
						_log.error("Exception in Persisting the table data one  : "+e.getMessage());
					}
				}
			}
		}
		
		if(!rowcount_secound_table.trim().equals("") || rowcount_secound_table != null) {
			for(int i = 1; i <= Long.parseLong(rowcount_secound_table.trim()); i++) {
				
				String particulars = ParamUtil.getString(resourceRequest, "particulars"+String.valueOf(i));
				String amount_in_crores = ParamUtil.getString(resourceRequest, "amount_in_crores"+String.valueOf(i));
				
				if(as_on_date != null) {
					try {
						AnnexureOneBTableTwoLocalServiceUtil.addAnnexureOneBTableTwo(particulars, amount_in_crores, new Date(), themeDisplay.getUserId(), as_on_date);
					} catch (Exception e) {
						isError = true;
						_log.error("Exception in Persisting the table data two  : "+e.getMessage());
					}
				}
			}
		}
		
		PrintWriter out = null;
		if(!isError && as_on_date != null) {
			try {
				out = resourceResponse.getWriter();
				out.print("success");
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		}else {
			try {
				out = resourceResponse.getWriter();
				out.print("error");
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		}
		
		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(SaveAnnexureOneB.class.getName());
}

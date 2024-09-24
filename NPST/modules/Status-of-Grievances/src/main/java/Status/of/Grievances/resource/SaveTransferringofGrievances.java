package Status.of.Grievances.resource;

import com.daily.average.service.service.GrievanceshandlerLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import Status.of.Grievances.constants.StatusOfGrievancesPortletKeys;

@Component(property = { 
		"javax.portlet.name=" +  StatusOfGrievancesPortletKeys.STATUSOFGRIEVANCES,
		"mvc.command.name=/TransferringofGrievancesForm/save" 
	}, service = MVCResourceCommand.class
)
public class SaveTransferringofGrievances implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		System.err.println("SaveTransferringofGrievances.serveResource()");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isError = false;
		
		String file_number = GetterUtil.getString(ParamUtil.getString(resourceRequest, "file_number"), "");
		String name = GetterUtil.getString(ParamUtil.getString(resourceRequest, "name"), "");
		String address = GetterUtil.getString(ParamUtil.getString(resourceRequest, "address"), "");
		String nps_trust_month_of = GetterUtil.getString(ParamUtil.getString(resourceRequest, "nps_trust_month_of"), "");
		
		
		for(int i=1; i<4; i++) {
			String opening_balance = ParamUtil.getString(resourceRequest, "opening_balance"+String.valueOf(i));
			String escalated_to_npst = ParamUtil.getString(resourceRequest, "escalated_to_npst"+String.valueOf(i));
			String received_during_the_month = ParamUtil.getString(resourceRequest, "received_during_the_month"+String.valueOf(i));
			String reviewed = ParamUtil.getString(resourceRequest, "reviewed"+String.valueOf(i));
			String resolved_during_the_month = ParamUtil.getString(resourceRequest, "resolved_during_the_month"+String.valueOf(i));
			String outstanding_at_the_end_of_the_month = ParamUtil.getString(resourceRequest, "outstanding_at_the_end_of_the_month"+String.valueOf(i));
			
			try {
				if(i == 1) {
					GrievanceshandlerLocalServiceUtil.addGrievanceshandler(file_number, name, address, nps_trust_month_of, "Escalated to NPST", opening_balance, escalated_to_npst, received_during_the_month, reviewed, resolved_during_the_month, outstanding_at_the_end_of_the_month, new Date(), themeDisplay.getUserId());
				}else if(i == 2){
					GrievanceshandlerLocalServiceUtil.addGrievanceshandler("", "", "", "", "Raised against NPST", opening_balance, escalated_to_npst, received_during_the_month, reviewed, resolved_during_the_month, outstanding_at_the_end_of_the_month, new Date(), themeDisplay.getUserId());
				}else if(i == 3) {
					GrievanceshandlerLocalServiceUtil.addGrievanceshandler("", "", "", "", "Total", opening_balance, escalated_to_npst, received_during_the_month, reviewed, resolved_during_the_month, outstanding_at_the_end_of_the_month, new Date(), themeDisplay.getUserId());
				}
			} catch (Exception e) {
				isError = true;
				_log.error("Exception in data inserting : "+e.getMessage());
			}
		}
		
		
		PrintWriter out = null;
		if(!isError) {
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

	private static Log _log = LogFactoryUtil.getLog(SaveTransferringofGrievances.class.getName());
}

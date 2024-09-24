package com.monthly.compcertificate.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.monthly.compcertificate.constants.MonthlyCompCertificatePortletKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=false",
			"javax.portlet.display-name=MonthlyCompCertificate_Scrutiny",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/scrutiny.jsp",
			"javax.portlet.name=" + MonthlyCompCertificatePortletKeys.MONTHLYCOMPCERTIFICATESCRUTINY,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user",
			"com.liferay.portlet.requires-namespaced-parameters=false"
		},
		service = Portlet.class
	)
public class Comp_Certificate_scrutiny_Portlet extends MVCPortlet{
private static Log _log = LogFactoryUtil.getLog(Comp_Certificate_scrutiny_Portlet.class);
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		_log.info("welcome to ::::::::::::::::::::::::::scrutiny URL:::::::");
		
//		long MnCompCertificateId = ParamUtil.getLong(renderRequest, "MnCompCertificateId", 0L);
//		
//		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
//		long userid=themeDisplay.getUserId();
//		
//		String Annexure_a_iURL="";
//		String Annexure_a_iiURL="";
//		String Annexure_bURL="";
//		String Annexure_cURL="";
//		String Annexure_dURL="";
//		String Annexure_eURL="";
//		String Annexure_fURL="";
//		String Annexure_gURL="";
//		String Annexure_hURL="";
//		
//		MnCompCertificate mnCompCertificate=null;
//			try {
//			  mnCompCertificate = MnCompCertificateLocalServiceUtil.getMnCompCertificate(MnCompCertificateId);
//			  
//			  //document url:::::::::::::::
//			  Annexure_a_iURL=getDocumentURL(mnCompCertificate.getAnnexure_a_i());
//			  Annexure_a_iiURL=getDocumentURL(mnCompCertificate.getAnnexure_a_ii());
//			  Annexure_bURL=getDocumentURL(mnCompCertificate.getAnnexure_b());
//			  Annexure_cURL=getDocumentURL(mnCompCertificate.getAnnexure_c());
//			  Annexure_dURL=getDocumentURL(mnCompCertificate.getAnnexure_d());
//			  Annexure_eURL=getDocumentURL(mnCompCertificate.getAnnexure_e());
//			  Annexure_fURL=getDocumentURL(mnCompCertificate.getAnnexure_f());
//			  Annexure_gURL=getDocumentURL(mnCompCertificate.getAnnexure_g());
//			  Annexure_hURL=getDocumentURL(mnCompCertificate.getAnnexure_g());
//			  
//			  _log.info("mnCompCertificate::::::::::::::::::::"+mnCompCertificate.toString());
//			} catch (PortalException e) {
//				 _log.error(e);
//			}
//	    renderRequest.setAttribute("mnCompCertificate", mnCompCertificate);
//	    renderRequest.setAttribute("Annexure_a_iURL", Annexure_a_iURL);
//	    renderRequest.setAttribute("Annexure_a_iiURL", Annexure_a_iiURL);
//	    renderRequest.setAttribute("Annexure_bURL", Annexure_bURL);
//	    renderRequest.setAttribute("Annexure_cURL", Annexure_cURL);
//	    renderRequest.setAttribute("Annexure_dURL", Annexure_dURL);
//	    renderRequest.setAttribute("Annexure_eURL", Annexure_eURL);
//	    renderRequest.setAttribute("Annexure_fURL", Annexure_fURL);
//	    renderRequest.setAttribute("Annexure_gURL", Annexure_gURL);
//	    renderRequest.setAttribute("Annexure_hURL", Annexure_hURL);
	    
		super.render(renderRequest, renderResponse);
	}
	
//	public static String getDocumentURL(long documentId) {
//		 String documentURL = StringPool.BLANK;
//		    if(Validator.isNotNull(documentId)) {
//		    	long fileEntryId = documentId;
//		    	DLFileEntry dlFileEntry = null;
//		    	try {
//		    		dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId);
//				} catch (Exception e) {
//					_log.error("Exception in getting dlFileEntry::" + e.getMessage());
//				}
//		    	if(Validator.isNotNull(dlFileEntry)) {
//		    		documentURL = "/documents/"+dlFileEntry.getRepositoryId()+"/"+dlFileEntry.getFolderId()+"/"+dlFileEntry.getTitle();
//		    	}
//		    }
//	    return documentURL;
//	}
	
//	@Reference
//	MnCompCertificateLocalService mnCompCertificateLocalService;
}

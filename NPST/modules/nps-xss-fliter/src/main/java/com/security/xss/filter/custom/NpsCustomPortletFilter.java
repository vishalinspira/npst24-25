package com.security.xss.filter.custom;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.security.xss.filter.constants.NpsXssFliterPortletKeys;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;


@Component(
		immediate = true, 
		property = { 
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE16INTERNALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.DAILYAVERAGE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AN15ANNUALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AN18BIODATA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AN4WEEKLYAVGBALREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXTUREXA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE11CONCURRENTAUDITORSCERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE12STATUTORYAUDITORCERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE13COMPLIANCECERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE17CHANGEININTERESTOFDIRECTOR,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXUREFOURA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXUREVIIA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXUREXIV,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.EXCELACCOUNTSTATEMENT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AM_ANNEXURE_12,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AM_ANNEXURE_9,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AN11AMCONCUAUDITORCERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AN15AMANNUALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.AN16AMINTERNALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.WEEKLYREPONONEQ,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.MONTHLYPFMFORM1TO14,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.SESVOTEMATRIX,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.W05CRANSDLDATA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.WEEKLY_CRA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.VALUATION_REP_CG,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.VALUATION_REP_SG,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.QUARTERLYCOMPLIANCESFORMS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.PFM_SBIREQUESTFORDATAREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.IIASVOTING_REPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.BENCHMARK,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.REPORTDEBIT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.REPORTEQUITY,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.BENCHMARKMONTHLY,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FINALIAREPORTSBIPF_INTERNALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.NPSTRUSTFEEPFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.MONTHLYNONUNANIMOUSVOTING,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.INVESTMENTMANAGEMENTFEE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.GROWTHDATA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FORM1REPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.EVOTINGSUMMARYPFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.MAJORITYVOTESPFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.UNAUDITEDFINANCIAL,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNUALPROXYPFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.NPADEVELOPMENT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FORM3REPORTBYDIRECTORANDKP,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNUALEVOTING,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.COMPLIANCECERTIFICATEATHALFYEARLYINTERVAL,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.TDSREPORTFROMPFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.HALFYEARLYIAR,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.QR_REPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.VOTE_MATRIX,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.VOTE_REPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.QUARTERLYIAREVOTINGCERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.GRIEVANCETOPFIVEDATA_MONTHLY,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FINALINTIMATION_NPS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.QUARTERLYSTEWARDSHIPREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.COMPLIANCE_CERTIFICATE_AT_QUARTERLY_INTERVAL,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.MONTHLYCOMPCERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.EVOTINGCOUNT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CUT_ANNUAL_AUDIT_REPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNUALCOMPLAINCEREPORT_FORM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FINALINTIMATIONAPY,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.LETTERSOFAPY,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.LETTERS_NPS_GRIEVANCES,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.MONTHLYMISREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.STATUSDAYS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.STATUSOFGRIEVANCES,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.GRIEVPENDING,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CATEGARYWISEAGAING,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXUREI_AUC_DETAILS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXUREFORCUSTODIAN,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.COMPLIANCE_REPORT_CUSTODIAN,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.COMPLIANCECERTIFICATEATHALFYEARLYINTERVAL,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.HDFCINTERNALAUDITREPORTPFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE_IV_PFM,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNEXURE_ONE_PFM_AUC_DETAILS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.MONTHLY_CUSTODIAN_TDS_REPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.WEBSITEJUNE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FINAL_COUNT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.PFVOTINGRECOMMENDATION,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CRA_COMPLIANCE_REPORT_NPST_TRUST,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CUSTODIANINTERNALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CRA_QUATERLY_DATA,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CRA_NPS_TRUST,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FORMSIX,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.QUARTERLYNONUNANIMOUS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FORM3REPORT_BY_DIRECTOR_KP,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.SLAFORMTRUSTEEBANK,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ASSETNOTUNDERCUSTODYREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.QUARTERLYCOMPLIANCECERTIFICATE,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.CUSTODIANPDFINTERNALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNUALAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.DETAILEDAUDITREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.PROXYVOTINGREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.STEWARDSHIPREPORT,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.PFMINTERNALAUDITREPORTS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.EXCEPTIONREPORTING,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.YEARLYEXCEPTIONREPORTING,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.ANNUALPROXYVOTINGREPORTS,
				"javax.portlet.name=" + NpsXssFliterPortletKeys.FINALINTIMATION,
				"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
				"javax.portlet.name=" + PortletKeys.LOGIN,
				"javax.portlet.name=" + PortletKeys.MY_ACCOUNT,
				"javax.portlet.name=" + PortletKeys.MY_PAGES,
				"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_INSTANCE,
				"javax.portlet.name=" + PortletKeys.MY_SITES_DIRECTORY,
				"javax.portlet.name=" + PortletKeys.CALENDAR,
		}, 
		service = PortletFilter.class
)
public class NpsCustomPortletFilter implements ActionFilter, RenderFilter, ResourceFilter {

	private static final Log _log = LogFactoryUtil.getLog(NpsCustomPortletFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(RenderRequest request, RenderResponse response, FilterChain filterChain)
			throws IOException, PortletException {
		//_log.info("RenderFilter :::: doFilter ::::::: ");
		//String currentCompleteURL = PortalUtil.getCurrentURL(request);
		//_log.info("currentUrl:::::::" + currentCompleteURL);
		boolean foundXSS = false;
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			//_log.info("param :::"+param);
			String value = request.getParameter(param);
			//_log.info("value ::::::"+value);
			if(param.contains("redirect")) {
				if(value.contains("www.google.com") || value.contains("owasp.org")) {
					//_log.info("inside param and value" + value);
					foundXSS = true;
				}
			}
			if (foundXSS) {
				break;
			}
			foundXSS = containsJS(value);
		}
		
		/*if(Validator.isNotNull(currentCompleteURL)) {
			foundXSS = containsJS(currentCompleteURL);
			_log.info("isFoundXsss in current complete url::::::::::::::::" + foundXSS);
			_log.info("isFoundXsss in current complete url::::::::::::::::" + currentCompleteURL);
		}*/
		
		HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(response);
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
		/*RenderParameters renderParameters = request.getRenderParameters();
		Set<String> names = renderParameters.getNames();
		if(!names.isEmpty()) {
			Iterator<String> iterator  = names.iterator();
			while (iterator.hasNext()) {
				String param = iterator.next();
				String value = renderParameters.getValue(param); //ParamUtil.getString(request, param);
				if (foundXSS) {
					break;
				}
				foundXSS = containsJS(value);
			}
		}*/
		//_log.info("foundXSS ::::::"+foundXSS);
		if (!foundXSS) {
			filterChain.doFilter(request, response);
		} else {
			_log.info("Render Request is more prone to XSS attack");
			//SessionErrors.add(request, "xss.error");
			String accessDeniedReason = "New Invalid REFERER  request headers are both not same so we block the request !";
			//httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
			 try {
				PortalUtil.sendError(403, new PrincipalException("Access denied."), httpRequest, httpResponse);
			} catch (ServletException e) {
				_log.error("Error in principal exception in message::::::::::" + e.getMessage());
			}
			//return;
		}
	}
	
	@Override
	public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain filterChain)
			throws IOException, PortletException {
		//_log.info("ResourceFilter :::: doFilter ::::::: ");
		boolean foundXSS = false;
		Enumeration<String> params = request.getParameterNames();
		
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String value = request.getParameter(param);
			if (foundXSS) {
				break;
			}
			foundXSS = containsJS(value);
		}
		

		HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(response);
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
		
		/*ResourceParameters resourceParameters = request.getResourceParameters();
		Set<String> names = resourceParameters.getNames();
		if(!names.isEmpty()) {
			Iterator<String> iterator  = names.iterator();
			while (iterator.hasNext()) {
				String param = iterator.next();
				String value = ParamUtil.getString(request, param);
				if (foundXSS) {
					break;
				}
				foundXSS = containsJS(value);
			}
		}*/
		//_log.info("foundXSS ::::::"+foundXSS);
		if (!foundXSS) {
			filterChain.doFilter(request, response);
		} else {
			//_log.info("Resource Request is more prone to XSS attack");
			//SessionErrors.add(request, "xss.error");
			String accessDeniedReason = "New Invalid REFERER  request headers are both not same so we block the request !";
			//httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
			 try {
				PortalUtil.sendError(403, new PrincipalException("Access denied."), httpRequest, httpResponse);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void doFilter(ActionRequest request, ActionResponse response, FilterChain filterChain)
			throws IOException, PortletException {
		//_log.info("ActionFilter :::: doFilter ::::::: ");
		boolean foundXSS = false;
		
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String value = request.getParameter(param);
			if(param.equalsIgnoreCase("login") || param.equalsIgnoreCase("rememberMe")) {
				if(value.contains("home")) {
					foundXSS = true;
				}
			}
			if(param.equalsIgnoreCase("g-recaptcha-response")) {
				if(value.contains("login-verify")) {
					foundXSS = true;
				}
			}
			if (foundXSS) {
				break;
			}
			foundXSS = containsJS(value);
		}
		
		String contentType = request.getContentType();
		if(contentType != null && contentType.equalsIgnoreCase("multipart/form-data")) {
			UploadPortletRequest ur = PortalUtil.getUploadPortletRequest(request);
			Enumeration<String> params1 = ur.getParameterNames();
			while (params1.hasMoreElements()) {
				String param = params1.nextElement();
				String value = ur.getParameter(param);
				if(param.equalsIgnoreCase("login") || param.equalsIgnoreCase("rememberMe")) {
					if(value.contains("home")) {
						foundXSS = true;
					}
				}
				if(param.equalsIgnoreCase("g-recaptcha-response")) {
					if(value.contains("login-verify")) {
						foundXSS = true;
					}
				}
				if (foundXSS) {
					break;
				}
				foundXSS = containsJS(value);
			}
		}
		

		HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(response);
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
		
		//_log.info("foundXSS ::::::"+foundXSS);
		if (!foundXSS) {
			filterChain.doFilter(request, response);
		} else {
		//	_log.info("Action Request is more prone to XSS attack");
			//SessionErrors.add(request, "xss.error");
			String accessDeniedReason = "New Invalid REFERER  request headers are both not same so we block the request !";
			//httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
			 try {
				PortalUtil.sendError(403, new PrincipalException("Access denied."), httpRequest, httpResponse);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}

	}
	
	public boolean containsJS(String value) {
		boolean foundXSS = false;
		if (value == null || value.isEmpty()) {
			return false;
		}
		/*Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
		Matcher scriptMat = scriptPattern.matcher(value);
		if (scriptMat.find()) {
			foundXSS = true;
		}
		scriptPattern = Pattern.compile("<script>", Pattern.CASE_INSENSITIVE);
		scriptMat = scriptPattern.matcher(value);
		if (scriptMat.find()) {
			foundXSS = true;
		}
		scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
		scriptMat = scriptPattern.matcher(value);
		if (scriptMat.find()) {
			foundXSS = true;
		}
		scriptPattern = Pattern.compile("javascript", Pattern.CASE_INSENSITIVE);
		scriptMat = scriptPattern.matcher(value);
		if (scriptMat.find()) {
			foundXSS = true;
		}*/
		_log.debug("Validator.isHTML(value) "+ Validator.isHTML(value));
		_log.debug("Validator.isXml(value) "+ Validator.isXml(value));
		for (Pattern scriptPattern : patterns){
			Matcher patternMatcher = scriptPattern.matcher(value);
			if(patternMatcher.find()) {
				foundXSS = true;
				 break;
			}
           
        }
		return foundXSS;
	}
	
	 private static Pattern[] patterns = new Pattern[]{
			 	Pattern.compile("www.google.com", Pattern.CASE_INSENSITIVE),
			 	Pattern.compile("owasp.org", Pattern.CASE_INSENSITIVE),
			 	//Pattern.compile("alert", Pattern.CASE_INSENSITIVE),
		        // Script fragments
		        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		        // src='...'
		        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // lonely script tags
		        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // eval(...)
		        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // expression(...)
		        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // javascript:...
		        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
		        // vbscript:...
		        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
		        // onload(...)=...
		        Pattern.compile("onafterprint(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onbeforeprint(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onbeforeunload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onhashchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmessage(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onoffline(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ononline(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onpagehide(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onpageshow(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onpopstate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onresize(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onstorage(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onunload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onblur(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oncontextmenu(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oninput(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oninvalid(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onreset(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onsearch(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onselect(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onsubmit(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onkeydown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onkeypress(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onkeyup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondblclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmousemove(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmouseout(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmouseup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onmousewheel(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onwheel(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondrag(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondragend(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondragenter(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondragleave(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondragover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondragstart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondrop(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onscroll(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oncopy(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oncut(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onpaste(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onabort(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oncanplay(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oncanplaythrough(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("oncuechange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ondurationchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onemptied(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onended(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onloadeddata(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onloadedmetadata(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onloadstart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onpause(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onplay(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onplaying(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onprogress(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onratechange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onseeked(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onseeking(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onstalled(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onsuspend(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ontimeupdate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onvolumechange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("onwaiting(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("ontoggle(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
		    };


}

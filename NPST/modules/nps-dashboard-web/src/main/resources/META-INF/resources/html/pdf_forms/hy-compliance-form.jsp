
<%@page import="nps.common.service.util.CommonRoleService"%>
<%@page import="nps.common.service.util.PfmHyccUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.daily.average.service.service.PFM_hy_comp_certLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.pfm.service.service.PFM_hy_comp_cert_ScrutinyLocalServiceUtil"%>
<%@page import="com.daily.pfm.service.model.PFM_hy_comp_cert_Scrutiny"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.PFM_hy_comp_cert"%>
<%-- <%@ include file="/init.jsp"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<% 
long reportUploadLogId=ParamUtil.getLong(request, "reportUploadLogId");
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
PFM_hy_comp_cert hyCompCert =null;
JSONObject jsonObject=null;
try{
	jsonObject=PfmHyccUtil.getHyComp_JSON_data(reportUploadLogId);
	hyCompCert = PFM_hy_comp_certLocalServiceUtil.fetchPFM_hy_comp_cert(reportUploadLogId);
}catch(Exception e){
	hyCompCert=PFM_hy_comp_certLocalServiceUtil.createPFM_hy_comp_cert(0);
}

%>


<div class="nps-page-main nps-upload-report nps-statement-wrapper">
   <!-- <div class="row mb-3">
      <div class="col-12">
         <div class="text-right">
            <p  class="back_btn"><a class="backbtn" href="/web/guest/half-yearly"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
         </div>
      </div>
   </div> -->
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Half - yearly Compliance Certificates</h4>
           <!--  <form class="form" id="annualComplains" action="#" method="post"> -->
              
               <%-- <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" name="reportDate" value="${reportDate }" readonly="readonly">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label class="pl-3">For the half - year ended</label>
                        <input type="text" class="rounded border-0 p-1 ml-3" id="year" name='year'>
                    </div>
				  </div>
               </div>
               <div class="row pt-3">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Half Yearly Compliance Certificate</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="hy_comp_certificate_file" name="hy_comp_certificate_file" accept=".pdf"/>
                           </div>
                           
                           <br>
                        </div>
                        <label id="error-hy_comp_certificate_file" class="error-message text-danger"></label>
                     </div>
                  </div>
               </div> --%>
               
               <!-- <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-md-9">
                        <div>
                           <p class="font-weight-bold mb-0">To,</p>
                           <p class="font-weight-bold mb-0">The Chief Executive Officer</p>
                           <p class="font-weight-bold">NPS Trust</p>
                           <p class="font-weight-bold mb-0">First Floor,IFCI Tower,</p>
                           <p class="font-weight-bold">61, Nehru Place, N. Delhi - 110019</p>
                        </div>
                     </div>
                  </div>
               </div>
                <br>
               <p>Sir,</p>
               <br>
               <p>In my/our opinion and to the best of my/our information and according to the examinations carried out by
					me/us and explanations furnished to me/us, I/We certify the following in respect of the period mentioned
					above.
               </p>
               <br>
               <hr/> -->
                <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="companies">Company Name : </label>
                      <span><%=CommonRoleService.getCompanyName(hyCompCert.getCreatedby()) %></span>
                    <%--  <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>"> --%>
                  </div>
               </div>
               <br>
               
               <div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="">
												<label for="name" class="pl-2">Report Due Date : </label>
												<span><%=dateFormat.format(hyCompCert.getReportDate()) %></span>
												 <%-- <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>dueDate' value="<%=dateFormat.format(hyCompCert.getReportDate()) %>"> --%>
												<%-- <input class="reportDate" type="text" value="<%=dateFormat.format(inputQuarterlyInterval.getReportDate()) %>" name="reportDate" readonly="readonly"> --%>
										</div>
									</div>
								</div><br>
               <div class="row">
                   <div class="col-md-1">
                      <h5>S.NO</h5>
                   </div>
                   <div class="col-md-5">
                      <h5>Parameters </h5>
                   </div>
                   <div class="col-md-2">
                      <h5>Yes/No/NA</h5>
                   </div>
                   <div class="col-md-2">
                       <h5>PFM Remarks</h5>
                   </div>
                    <div class="col-md-2">
                       <h5>NPS Remarks</h5>
                   </div>
                </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>1</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                     <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1_1">
                        <p>Whether PFM has submitted copy of half-Yearly unaudited accounts
							of schemes within one month from the close of each half-year.</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                  <span><%=jsonObject.get("1_1")%></span>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea class="form-control rem1" id="1_rem_1" name="1_rem_1" <%=(jsonObject.get("1_rem_1")) %>></textarea>
                 		</div>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea class="form-control rem1" id="1_npsrem_1" name="1_npsrem_1" <%=(jsonObject.get("1_npsrem_1")) %> ></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>2</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                     <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2_1">
                        <p>Whether PFM has disclosed on its website a copy of half-Yearly
							unaudited accounts of schemes along with scheme portfolio within
							one month from the close of each half-year</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <span><%=jsonObject.get("2_1")%></span>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea class="form-control rem2" id="2_rem_1" name="2_rem_1" <%=(jsonObject.get("2_rem_1")) %>></textarea>
                 </div>
                  </div>
                   <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea class="form-control rem1" id="2_npsrem_1"  name="2_npsrem_1" <%=(jsonObject.get("2_npsrem_1")) %>></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>3</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                     <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3_1">
                        <p>Whether any change in interest of Directors has been submitted to
							NPS Trust every six months</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <span><%=jsonObject.get("3_1")%></span>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea class="form-control rem3" id="3_rem_1" name="3_rem_1" <%=(jsonObject.get("3_rem_1")) %>></textarea>
                 </div>
                  </div>
                   <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem1" id="3_npsrem_1"  name="3_npsrem_1" <%=(jsonObject.get("3_npsrem_1")) %>></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>4</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                     <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4_1">
                        <p>Whether PFM has submitted half-yearly certification by the Internal
							Auditor after it has been considered by the Board of PFM.</p>
                     </div>
                  </div>
                  <div class="col-md-2">
                     <span><%=jsonObject.get("4_1")%></span>
                  </div>
                  <div class="col-md-2">
                  		<div class="form-group">
                 	<textarea class="form-control rem4" id="4_rem_1"  name="4_rem_1" <%=(jsonObject.get("4_rem_1")) %>></textarea>
                 </div>
                  </div>
                   <div class="col-md-2">
                  		<div class="form-group">
                 			<textarea  class="form-control rem1" id="4_npsrem_1"  name="4_npsrem_1" <%=(jsonObject.get("4_npsrem_1")) %> ></textarea>
                 		</div>
                  </div>
               </div>
               <hr/>

              <div class="row" id="sub">
                  <div class="col-md-12">
                     <div class="text-center">
                         <button class="btn btn-primary" id="pdfButton" onClick="downloadPDF()">PDF  </button>
                     </div>
                  </div>
              </div>
           <!--  </form> -->
         </div>
      </div>
   </div>
</div>
<div class="animaion" style="display:none;">
   <div class="row">
      <div class="loading-animation"></div>
   </div>
</div>
<style>
	
	input.error {
		border-color: red;
	}
	
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
	}
	
	.nps-body-main .nps-page-main.nps-statement-wrapper .table-cont table {
    	width: auto !important;
	}
</style>

<script> 
        function downloadPDF(){
        	 var pdfCheckBoxArray = []; 
             $("input:checkbox[name=pdfbox]:checked").each(function() { 
             	pdfCheckBoxArray.push($(this).val()); 
             }); 
             console.log("pdfCheckBoxArray  : "+pdfCheckBoxArray);
             setTimeout(function () {
					var reportUploadLogId="<%=reportUploadLogId%>";
					var portalURL="<%=themeDisplay.getPortalURL()%>";
					console.log("baseURLTest : "+portalURL)
					var baseURl=portalURL+"/web/guest/dashboard?p_p_id=com_nps_dashboard_web_NPSDashboardWebPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=formsPDFCreationResourceCommand";
			var url=baseURl+"&<portlet:namespace/>pdfCheckBox="+pdfCheckBoxArray+"&<portlet:namespace/>reportUploadlogId="+reportUploadLogId; 	
			console.log(url);
					window.location.href=url;
             }, 300);
        }
        
    </script>


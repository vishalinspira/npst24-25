<%@page import="com.nps.form.autosave.constants.FormsPortletsKeyConstants"%>
<%@page import="com.nps.form.autosave.constants.AnnualyCompFormConstants"%>
<%@page import="com.nps.form.autosave.constants.AutoSaveConstants"%>
<%@page import="com.daily.average.service.model.AnnualCompCertificate"%>
<%@page import="AnnualComplaincereport_Form.util.AnnualComplianceReportUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil"%>
<%@page import="AnnualComplaincereport_Form.util.Pre_Populate_scrutinydata"%>
<%@page import="com.daily.average.service.service.AnnualCompCertificateScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.AnnualCompCertificateScrutiny"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.Validator"%>


<% 
AnnualComplianceReportUtil  annualComplianceReportUtil = new AnnualComplianceReportUtil();
boolean isNonNPSUser = annualComplianceReportUtil.isNonNpsUser(themeDisplay.getUserId());

Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
sd.pre_populate_scrutiny_data(request);

//AnnualCompCertificateScrutiny annualCompCertificateScrutiny = (AnnualCompCertificateScrutiny) request.getAttribute("scrutinyInputQuarterlyInterval"); 
AnnualCompCertificateScrutiny annualCompScrutinydetails = Validator.isNotNull(request.getAttribute("annualCompScrutiny")) ? (AnnualCompCertificateScrutiny) request.getAttribute("annualCompScrutiny") : AnnualCompCertificateScrutinyLocalServiceUtil.createAnnualCompCertificateScrutiny(0);
System.out.println("annualCompScrutinydetails:::::::::::::::::::::"+annualCompScrutinydetails.toString());
SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
SimpleDateFormat reportDateFormat = new SimpleDateFormat("MMMM/yyyy");
long reportuploadlogId=0;
String formDate1="";
try{
	String dDate[]=((String)request.getAttribute("reportDate")).split("-");
	int month=Integer.parseInt(dDate[1]);
	int year=Integer.parseInt(dDate[0]);
	if(month==1){
		year=year-1;
		month=12;
	}else{
		month=month-1;	
	}
	formDate1=month+"/"+year;
}catch(Exception e1){	}

AnnualCompCertificate annualCompCertificate=null;
try{
	reportuploadlogId=(Long)request.getAttribute("reportUploadLogId");
	annualCompCertificate = AnnualCompCertificateLocalServiceUtil.fetchAnnualCompCertificate(reportuploadlogId);
}catch(Exception e){
	annualCompCertificate=AnnualCompCertificateLocalServiceUtil.createAnnualCompCertificate(0);
}
if(Validator.isNull(annualCompCertificate)){
	annualCompCertificate=AnnualCompCertificateLocalServiceUtil.createAnnualCompCertificate(0);
}
if(Validator.isNull(annualCompScrutinydetails)){
	annualCompScrutinydetails=AnnualCompCertificateScrutinyLocalServiceUtil.createAnnualCompCertificateScrutiny(0);
}

%>

<style>
.modal {
    display: none;
}

.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}
</style>


<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
        Data sent for Review.
      </div>       
    </div>
  </div>
</div>

<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center text-danger">
      	<i class="fas fa-times-circle  fa-4x d-block mb-4"></i>      
        <span id="output"></span>
      </div>       
    </div>
  </div>
</div>

<div class="nps-page-main nps-upload-report nps-statement-wrapper">

   <div class="row mb-3">
         <div class="col-12">
            <div class="text-right">
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/annually-report"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
     </div>
     
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Annual Compliance Certificate</h4>
            <form class="form" id="annualComplains" action="#" method="post">
            <input type="hidden" value="${reportUploadLogId }" name="reportUploadLogId" class="reportUploadLogId" />
			<input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"/>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" name="reportDate" value="${reportDate }" readonly="readonly">
                     </div>
                  </div>
                   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
					 <label class="pl-3">For the Quarter Ended</label>
					 <input type="text" class="rounded border-0 p-1 ml-3 date_1" value="<%=formDate1 %>" id="date_1" name='date_1' readOnly="true">
					 </div>                 
                  </div>
               </div>
               <br>
               <%-- <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-md-4">
                        <div>
                           <p class="font-weight-bold mb-0">To,</p>
                           <p class="font-weight-bold mb-0">The Chief Executive Officer</p>
                           <p class="font-weight-bold">NPS Trust</p>
                           <input type="text" class="address" id="address" <%=isNonNPSUser ? "": "disabled" %> value='<%= (Validator.isNotNull(annualCompCertificate))? annualCompCertificate.getAddress():"" %>' name="address">
                           
                        </div>
                     </div>
                  </div>
               </div> --%>
               <div class="row">
                                 <div class="col-md-9">
                                    <p class="font-weight-bold mb-0">To,</p>
<p class=" font-weight-bold mb-0">National Pension System Trust,</p>
<p class=" font-weight-bold mb-0">Tower B, B-302, Third Floor,</p>
<p class="font-weight-bold mb-0">World Trade Center,</p>
<p class="font-weight-bold mb-0">Nauroji Nagar,</p>
<p class="font-weight-bold mb-0">New Delhi-110029</p>
                                 </div>
                              </div>
                              <br>
               <br>
               <p>Sir,</p>
               <br>
               <p>In my opinion and to the best of my information and according 
               to the examinations carried out by me and explanations furnished to me,
                I certify the following in respect of the year mentioned above.
               </p>
               <br>
                             <hr/>
<div class="row">
<div class="col-md-1">
<h5>SL.NO</h5>
</div>
<div class="col-md-7">
<h5>Parameters </h5>
</div>
<div class="col-md-2">
<h5>Yes/No/NA</h5>
</div>
<div class="col-md-2">
<h5>PFM Remarks</h5>
</div>
<!-- <div class="col-md-2">
<h5>NPST Remarks</h5>
</div> -->
</div>
<hr/>
               <div class="row">
                 <div class="col-md-1">
                   <p>1.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Eligibility requirement related</b></p> 
                 </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>a) Whether Sponsor(s) and PFM are complying with the eligibility requirements of PFRDA (PF) 
                         regulations 2015 and certificate of registration issued to it by PFRDA.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIa" id="conflict_1" name="eligibilityIa" value="Yes" <%=(annualCompCertificate.getEligibilityia()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIa" id="conflict_2" name="eligibilityIa" value="No" <%=(annualCompCertificate.getEligibilityia()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIa" id="conflict_3" name="eligibilityIa" value="NA" <%=(annualCompCertificate.getEligibilityia()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                   <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIa_rem"  placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %> name="eligibilityIa_rem"><%= annualCompCertificate.getEligibilityIa_rem() != null ? annualCompCertificate.getEligibilityIa_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>b) Whether PFM is conducting its activities in accordance with the PFRDA Act,
                      applicable regulations in force and any guidelines, notifications or circulars 
                      issued by the Authority along with the operational agreement executed between the NPS 
                      Trust and PFM.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIb" id="monitoring_1" name="eligibilityIb" value="Yes" <%=(annualCompCertificate.getEligibilityib()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIb" id="monitoring_2" name="eligibilityIb" value="No" <%=(annualCompCertificate.getEligibilityib()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIb" id="monitoring_3" name="eligibilityIb" value="NA" <%=(annualCompCertificate.getEligibilityib()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    
                    	<textarea class="form-control" id="eligibilityIb_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIb_rem"><%= annualCompCertificate.getEligibilityIb_rem() != null ? annualCompCertificate.getEligibilityIb_rem():"" %></textarea>
                 </div> 
                 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>c) Whether Sponsor(s) is maintaining minimum Tangible Net-worth as stipulated by PFRDA
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIc" id="resolutions_1" name="eligibilityIc" value="Yes" <%=(annualCompCertificate.getEligibilityic()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIc" id="resolutions_2" name="eligibilityIc" value="No" <%=(annualCompCertificate.getEligibilityic()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIc" id="resolutions_3" name="eligibilityIc" value="NA" <%=(annualCompCertificate.getEligibilityic()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIc_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIc_rem"><%= annualCompCertificate.getEligibilityIc_rem() != null ? annualCompCertificate.getEligibilityIc_rem():"" %></textarea>
                 </div> 
                 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>d) Whether Sponsor(s) has contributed minimum Tangible Net-worth of the PFM as stipulated by the PFRDA
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityId" id="resolutions_1" name="eligibilityId" value="Yes" <%=(annualCompCertificate.getEligibilityid()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityId" id="resolutions_2" name="eligibilityId" value="No" <%=(annualCompCertificate.getEligibilityid()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityId" id="resolutions_3" name="eligibilityId" value="NA" <%=(annualCompCertificate.getEligibilityid()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    
                    	<textarea class="form-control" id="eligibilityId_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityId_rem"><%= annualCompCertificate.getEligibilityId_rem() != null ? annualCompCertificate.getEligibilityId_rem():"" %></textarea>
                    
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>e) Whether direct or indirect holding by a foreign company in the PFM is in compliance to PFRDA Act,
                      regulations and other communications.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIe" id="resolutions_1" name="eligibilityIe" value="Yes" <%=(annualCompCertificate.getEligibilityie()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIe" id="resolutions_2" name="eligibilityIe" value="No" <%=(annualCompCertificate.getEligibilityie()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIe" id="resolutions_3" name="eligibilityIe" value="NA" <%=(annualCompCertificate.getEligibilityie()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                   
                    	<textarea class="form-control" id="eligibilityIe_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIe_rem"><%= annualCompCertificate.getEligibilityIe_rem() != null ? annualCompCertificate.getEligibilityIe_rem():"" %></textarea>
                    
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>f) Whether annual fee payable to Authority has been paid as specified by the Authority and within the timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIf" id="resolutions_1" name="eligibilityIf" value="Yes" <%=(annualCompCertificate.getEligibilityif()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIf" id="resolutions_2" name="eligibilityIf" value="No" <%=(annualCompCertificate.getEligibilityif()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIf" id="resolutions_3" name="eligibilityIf" value="NA" <%=(annualCompCertificate.getEligibilityif()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIf_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIf_rem"><%= annualCompCertificate.getEligibilityIf_rem() != null ? annualCompCertificate.getEligibilityIf_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>g) Whether there is any change in the regulatory license (s) issued to the Sponsor(s).
                        Statement showing current status of the sponsor(s) regulatory licenses is provided in Annexure1
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIg" id="resolutions_1" name="eligibilityIg" value="Yes" <%=(annualCompCertificate.getEligibilityig()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIg" id="resolutions_2" name="eligibilityIg" value="No" <%=(annualCompCertificate.getEligibilityig()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIg" id="resolutions_3" name="eligibilityIg" value="NA" <%=(annualCompCertificate.getEligibilityig()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIg_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIg_rem"><%= annualCompCertificate.getEligibilityIg_rem() != null ? annualCompCertificate.getEligibilityIg_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>h) Whether there is any change in the name of the PFM or Sponsor(s) 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIh" id="resolutions_1" name="eligibilityIh" value="Yes" <%=(annualCompCertificate.getEligibilityih()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIh" id="resolutions_2" name="eligibilityIh" value="No" <%=(annualCompCertificate.getEligibilityih()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIh" id="resolutions_3" name="eligibilityIh" value="NA" <%=(annualCompCertificate.getEligibilityih()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                     <textarea class="form-control" id="eligibilityIh_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIh_rem"><%= annualCompCertificate.getEligibilityIh_rem() != null ? annualCompCertificate.getEligibilityIh_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>i) Whether sponsor(s) periodically review the activities of the pension fund.(Incase of irregularities sponsor shall immediately report to the Authority.)
                     
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIi" id="resolutions_1" name="eligibilityIi" value="Yes" <%=(annualCompCertificate.getEligibilityii()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIi" id="resolutions_2" name="eligibilityIi" value="No" <%=(annualCompCertificate.getEligibilityii()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIi" id="resolutions_3" name="eligibilityIi" value="NA" <%=(annualCompCertificate.getEligibilityii()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIi_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIi_rem"><%= annualCompCertificate.getEligibilityIi_rem() != null ? annualCompCertificate.getEligibilityIi_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>j) Whether the sponsor of a pension fund or the pension fund itself hold any equity stake in any other pensionfund regulated by the Authority 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIj" id="resolutions_1" name="eligibilityIj" value="Yes" <%=(annualCompCertificate.getEligibilityij()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIj" id="resolutions_2" name="eligibilityIj" value="No" <%=(annualCompCertificate.getEligibilityij()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIj" id="resolutions_3" name="eligibilityIj" value="NA" <%=(annualCompCertificate.getEligibilityij()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIj_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIj_rem"><%= annualCompCertificate.getEligibilityIj_rem() != null ? annualCompCertificate.getEligibilityIj_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>k) Whether Sponsor(s) holds directly or indirectly more than permissible stake in CRA, Trustee Bank or Custodian.
                          Statement of sponsor(s) holdingin intermediaries to be provide details in Annexure 2.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIk" id="resolutions_1" name="eligibilityIk" value="Yes" <%=(annualCompCertificate.getEligibilityik()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIk" id="resolutions_2" name="eligibilityIk" value="No" <%=(annualCompCertificate.getEligibilityik()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIk" id="resolutions_3" name="eligibilityIk" value="NA" <%=(annualCompCertificate.getEligibilityik()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIk_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIk_rem"><%= annualCompCertificate.getEligibilityIk_rem() != null ? annualCompCertificate.getEligibilityIk_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>l) Whether PFM has executed such agreements as specified by the Authority in the interest of subscribers with the parties, 
                      including other intermediaries, like Investment Management agreement and NDA with NPS Trust, Service contracts such as for custody
                      arrangements and transfer agency of the securities etc., and copy of such agreements have been submitted to NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIl" id="resolutions_1" name="eligibilityIl" value="Yes" <%=(annualCompCertificate.getEligibilityil()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIl" id="resolutions_2" name="eligibilityIl" value="No" <%=(annualCompCertificate.getEligibilityil()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIl" id="resolutions_3" name="eligibilityIl" value="NA" <%=(annualCompCertificate.getEligibilityil()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIl_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIl_rem" ><%= annualCompCertificate.getEligibilityIl_rem() != null ? annualCompCertificate.getEligibilityIl_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>m) Whether PFM has failed to take prior approval 
                     from authority of any major change in management or ownership or shareholding pattern or any change 
                     in controlling interest of the Sponsor(s) of the pension fund.Statement regarding Net-worth of the Sponsor(s) 
                     and PFM is provided in Annexure 3.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIm" id="resolutions_1" name="eligibilityIm" value="Yes" <%=(annualCompCertificate.getEligibilityim()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIm" id="resolutions_2" name="eligibilityIm" value="No" <%=(annualCompCertificate.getEligibilityim()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIm" id="resolutions_3" name="eligibilityIm" value="NA" <%=(annualCompCertificate.getEligibilityim()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIm_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIm_rem" ><%= annualCompCertificate.getEligibilityIm_rem() != null ? annualCompCertificate.getEligibilityIm_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>n) Whether the sponsor or pension fund or its principal officer or key management 
                     personnel has been convicted by a court forany offence involving moral turpitude, economic offence, securities laws or fraud;
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIn" id="resolutions_1" name="eligibilityIn" value="Yes" <%=(annualCompCertificate.getEligibilityin()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIn" id="resolutions_2" name="eligibilityIn" value="No" <%=(annualCompCertificate.getEligibilityin()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIn" id="resolutions_3" name="eligibilityIn" value="NA" <%=(annualCompCertificate.getEligibilityin()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIn_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIn_rem" ><%= annualCompCertificate.getEligibilityIn_rem() != null ? annualCompCertificate.getEligibilityIn_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>o) Whether an order of winding up has been passed against the Sponsor(s) or pension fund
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIo" id="resolutions_1" name="eligibilityIo" value="Yes" <%=(annualCompCertificate.getEligibilityio()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIo" id="resolutions_2" name="eligibilityIo" value="No" <%=(annualCompCertificate.getEligibilityio()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIo" id="resolutions_3" name="eligibilityIo" value="NA" <%=(annualCompCertificate.getEligibilityio()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIo_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIo_rem" ><%= annualCompCertificate.getEligibilityIo_rem() != null ? annualCompCertificate.getEligibilityIo_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>p) Whether Sponsor(s) or PFM or Key promoter has been declared insolvent
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIp" id="resolutions_1" name="eligibilityIp" value="Yes" <%=(annualCompCertificate.getEligibilityip()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIp" id="resolutions_2" name="eligibilityIp" value="No" <%=(annualCompCertificate.getEligibilityip()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIp" id="resolutions_3" name="eligibilityIp" value="NA" <%=(annualCompCertificate.getEligibilityip()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIp_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIp_rem" ><%= annualCompCertificate.getEligibilityIp_rem() != null ? annualCompCertificate.getEligibilityIp_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>q) Whether any order restraining, prohibiting or debarring the Sponsor(s) or PFM or its principal officer or key management personnel 
                     from dealing in securities in the capital market or from accessing the capital market has been passed by any regulatory authority or court
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIq" id="resolutions_1" name="eligibilityIq" value="Yes" <%=(annualCompCertificate.getEligibilityiq()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIq" id="resolutions_2" name="eligibilityIq" value="No" <%=(annualCompCertificate.getEligibilityiq()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIq" id="resolutions_3" name="eligibilityIq" value="NA" <%=(annualCompCertificate.getEligibilityiq()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIq_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIq_rem"><%= annualCompCertificate.getEligibilityIq_rem() != null ? annualCompCertificate.getEligibilityIq_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>r) Whether any order withdrawing or refusing to grant any license or approval to the sponsor or pension fund or its 
                        whole timedirector or managing partner which has a bearing on the capital market, has been passed by the concerned 
                        financialsector regulator or any other regulatory authority
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIr" id="resolutions_1" name="eligibilityIr" value="Yes" <%=(annualCompCertificate.getEligibilityir()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIr" id="resolutions_2" name="eligibilityIr" value="No" <%=(annualCompCertificate.getEligibilityir()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIr" id="resolutions_3" name="eligibilityIr" value="NA" <%=(annualCompCertificate.getEligibilityir()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIr_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIr_rem"><%= annualCompCertificate.getEligibilityIr_rem() != null ? annualCompCertificate.getEligibilityIr_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p> s) Whether there is any notice served of any action or investigation or other proceedings
                      of any nature whatsoever, against the sponsor orpension fund, or its Chief Executive Officer, 
                      any of its directors or employees, or a related group concern, by anygovernmental or statutory authority which would restrain, 
                      prohibit or otherwise challenge or impede the performance of obligations as sponsor or pension fund of the pension schemes regulated by theAuthority, 
                      and that there isadverse proceedings against it from anyfinancial sector regulator including the RBI, IRDA or SEBI, 
                     of a nature that couldadversely affect the ability to provide the services as sponsor or pension fund for the pension schemesregulated bythe Authority;
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIs" id="resolutions_1" name="eligibilityIs" value="Yes" <%=(annualCompCertificate.getEligibilityis()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIs" id="resolutions_2" name="eligibilityIs" value="No" <%=(annualCompCertificate.getEligibilityis()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="eligibilityIs" id="resolutions_3" name="eligibilityIs" value="NA" <%=(annualCompCertificate.getEligibilityis()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIs_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="eligibilityIs_rem"><%= annualCompCertificate.getEligibilityIs_rem() != null ? annualCompCertificate.getEligibilityIs_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <hr>
               
                <div class="row">
                 <div class="col-md-1">
                   <p>2.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Books of Accounts, Financial statements, Annual and Periodic reports</b></p> 
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>a) Whether PFM has maintained books and records about the operation 
                     of pension schemes to ensure compliance with the provisions of the Income-tax Act, 
                     the companies Actor under any otherAct in force and in such manner as may be required or called for by the Authority
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIa" id="resolutions_1" name="bookIIa" value="Yes" <%=(annualCompCertificate.getBooka()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIa" id="resolutions_2" name="bookIIa" value="No" <%=(annualCompCertificate.getBooka()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIa" id="resolutions_3" name="bookIIa" value="NA" <%=(annualCompCertificate.getBooka()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIa_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %> name="bookIIa_rem" ><%= annualCompCertificate.getBookIIa_rem() != null ? annualCompCertificate.getBookIIa_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>b) Whether PFM has prepared financial statements, 
                     annual report in compliance to regulation 19 (1) and schedule VII of PFRDA (PF) Regulations 
                     2015 and PFRDA (Preparation of financial statements and auditors
                      report of schemes under national pension system) guidelines 2012 and subsequent amendments.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIb" id="resolutions_1" name="bookIIb" value="Yes" <%=(annualCompCertificate.getBookb()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIb" id="resolutions_2" name="bookIIb" value="No" <%=(annualCompCertificate.getBookb()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIb" id="resolutions_3" name="bookIIb" value="NA" <%=(annualCompCertificate.getBookb()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="bookIIb_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="bookIIb_rem" ><%= annualCompCertificate.getBookIIb_rem() != null ? annualCompCertificate.getBookIIb_rem():"" %></textarea>
                 </div> 
               </div>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>c) Whether PFM has furnished to NPS Trust periodic reports including unaudited provisional 
                     financial statements (Balance Sheet, Revenue Account, notes and schedules) of each scheme and annual 
                     report within the specified time.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIc" id="resolutions_1" name="bookIIc" value="Yes" <%=(annualCompCertificate.getBookc()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIc" id="resolutions_2" name="bookIIc" value="No" <%=(annualCompCertificate.getBookc()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="bookIIc" id="resolutions_3" name="bookIIc" value="NA" <%=(annualCompCertificate.getBookc()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="bookIIc_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="bookIIc_rem" ><%= annualCompCertificate.getBookIIc_rem() != null ? annualCompCertificate.getBookIIc_rem():"" %></textarea>
                 </div> 
               </div>
              
               <hr>
               
                <div class="row">
                 <div class="col-md-1">
                   <p>3.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Audit of Scheme Accounts</b></p> 
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>a) Whether PFM has got its scheme accounts audited by the auditor appointed by the NPS Trust within specified timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="audita" id="resolutions_1" name="audita" value="Yes" <%=(annualCompCertificate.getAudita()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="audita" id="resolutions_2" name="audita" value="No" <%=(annualCompCertificate.getAudita()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="audita" id="resolutions_3" name="audita" value="NA" <%=(annualCompCertificate.getAudita()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="audita_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="audita_rem" ><%= annualCompCertificate.getAudita_rem() != null ? annualCompCertificate.getAudita_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>b)  Whether Audited Annual report and other information have been submitted to NPS Trust after approval of 
                     the board of directors of the pension fund, within specified timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditb" id="resolutions_1" name="auditb" value="Yes" <%=(annualCompCertificate.getAuditb()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditb" id="resolutions_2" name="auditb" value="No" <%=(annualCompCertificate.getAuditb()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditb" id="resolutions_3" name="auditb" value="NA" <%=(annualCompCertificate.getAuditb()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="auditb_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>   name="auditb_rem"><%= annualCompCertificate.getAuditb_rem() != null ? annualCompCertificate.getAuditb_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>c) Whether latest audited annual report has been placed on PFMs website within specified timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditc" id="resolutions_1" name="auditc" value="Yes" <%=(annualCompCertificate.getAuditc()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditc" id="resolutions_2" name="auditc" value="No" <%=(annualCompCertificate.getAuditc()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="auditc" id="resolutions_3" name="auditc" value="NA" <%=(annualCompCertificate.getAuditc()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="auditc_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>   name="auditc_rem"><%= annualCompCertificate.getAuditc_rem() != null ? annualCompCertificate.getAuditc_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <hr>
               
               
             <div class="row">
                 <div class="col-md-1">
                   <p>4.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Stewardship</b></p> 
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>a) Whether PFM has complied with circular PFRDA/2018/01/PF/01 on Common Stewardship Code dated 04.05.2018.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipa" id="resolutions_1" name="stewardshipa" value="Yes" <%=(annualCompCertificate.getStewardshipa()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipa" id="resolutions_2" name="stewardshipa" value="No" <%=(annualCompCertificate.getStewardshipa()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipa" id="resolutions_3" name="stewardshipa" value="NA" <%=(annualCompCertificate.getStewardshipa()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipa_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>   name="stewardshipa_rem"><%= annualCompCertificate.getStewardshipa_rem() != null ? annualCompCertificate.getStewardshipa_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>b) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust;and 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipb" id="resolutions_1" name="stewardshipb" value="Yes" <%=(annualCompCertificate.getStewardshipb()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipb" id="resolutions_2" name="stewardshipb" value="No" <%=(annualCompCertificate.getStewardshipb()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipb" id="resolutions_3" name="stewardshipb" value="NA" <%=(annualCompCertificate.getStewardshipb()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipb_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="stewardshipb_rem" ><%= annualCompCertificate.getStewardshipb_rem() != null ? annualCompCertificate.getStewardshipb_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>c) Whether, annual voting report has been submitted to the NPS Trust.(Circular PFRDA/2017/17/PF/1 dated 20.04.2017)

                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipc" id="resolutions_1" name="stewardshipc" value="Yes" <%=(annualCompCertificate.getStewardshipc()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipc" id="resolutions_2" name="stewardshipc" value="No" <%=(annualCompCertificate.getStewardshipc()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="stewardshipc" id="resolutions_3" name="stewardshipc" value="NA" <%=(annualCompCertificate.getStewardshipc()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipc_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="stewardshipc_rem"><%= annualCompCertificate.getStewardshipc_rem() != null ? annualCompCertificate.getStewardshipc_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <hr>
                 <div class="row">
                 <div class="col-md-1">
                   <p>5.</p>
                 </div>
                 <div class="col-md-8">
                   <p><b>Others</b></p> 
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>a) Whether all interest, dividends or any such accrual income and proceeds of redemption/sale were collected on due dates 
                     and promptly credited to the scheme accounts.Statement showing amount of income accrued but not realized as on closing date of 
                     the financial year is provided in Annexure 4.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersa" id="resolutions_1" name="othersa" value="Yes" <%=(annualCompCertificate.getOthersa()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersa" id="resolutions_2" name="othersa" value="No" <%=(annualCompCertificate.getOthersa()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersa" id="resolutions_3" name="othersa" value="NA" <%=(annualCompCertificate.getOthersa()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersa_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="othersa_rem" ><%= annualCompCertificate.getOthersa_rem() != null ? annualCompCertificate.getOthersa_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>b) Whether any of the core/non-core activities of the PFM, as defined in circular 
                     no. PFRDA/2017/30/PF/4 dated 09th October 2017, has been outsourced to a third party service provider by
                      the PFM.Statement showing list of activities outsourced is provided in Annexure 5.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersb" id="resolutions_1" name="othersb" value="Yes" <%=(annualCompCertificate.getOthersb()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersb" id="resolutions_2" name="othersb" value="No" <%=(annualCompCertificate.getOthersb()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersb" id="resolutions_3" name="othersb" value="NA" <%=(annualCompCertificate.getOthersb()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersb_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="othersb_rem"><%= annualCompCertificate.getOthersb_rem() != null ? annualCompCertificate.getOthersb_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>c) Whether comprehensive service level agreements have been executed with outsourcing service providers covering terms of 
                     contracts in consonance with to the provisions of PFRDA Act, rules, 
                     regulations, guidelines and directions issued by the authority and copies of all such 
                     agreements have been submitted to NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersc" id="resolutions_1" name="othersc" value="Yes" <%=(annualCompCertificate.getOthersc()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersc" id="resolutions_2" name="othersc" value="No" <%=(annualCompCertificate.getOthersc()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersc" id="resolutions_3" name="othersc" value="NA" <%=(annualCompCertificate.getOthersc()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="monitoring_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="othersc_rem"><%= annualCompCertificate.getOthersc_rem() != null ? annualCompCertificate.getOthersc_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>d) Incase any award has been passed against the PFM under the Pension fund Regulatory 
                     and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015, whether PFM has complied with such award.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersd" id="resolutions_1" name="othersd" value="Yes" <%=(annualCompCertificate.getOthersd()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersd" id="resolutions_2" name="othersd" value="No" <%=(annualCompCertificate.getOthersd()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersd" id="resolutions_3" name="othersd" value="NA" <%=(annualCompCertificate.getOthersd()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersd_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="othersd_rem"><%= annualCompCertificate.getOthersd_rem() != null ? annualCompCertificate.getOthersd_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>e) Whether PFM has complied with Cyber Security policy for Intermediaries issued vide circular PFRDA/2019/2/REG dated 07.01.2019.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherse" id="resolutions_1" name="otherse" value="Yes" <%=(annualCompCertificate.getOtherse()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherse" id="resolutions_2" name="otherse" value="No" <%=(annualCompCertificate.getOtherse()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="otherse" id="resolutions_3" name="otherse" value="NA" <%=(annualCompCertificate.getOtherse()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="otherse_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>  name="otherse_rem"><%= annualCompCertificate.getOtherse_rem() != null ? annualCompCertificate.getOtherse_rem():"" %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-7">
                     <p>f) Whether PFM has ensured dissemination of adequate, accurate, explicit and timely information about the investment policies, 
                     investment objectives, financial position and general affairs of the scheme to the subscribers in a fairly simple language.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersf" id="resolutions_1" name="othersf" value="Yes" <%=(annualCompCertificate.getOthersf()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersf" id="resolutions_2" name="othersf" value="No" <%=(annualCompCertificate.getOthersf()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="othersf" id="resolutions_3" name="othersf" value="NA" <%=(annualCompCertificate.getOthersf()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersf_rem" placeholder="Remarks if any" <%=isNonNPSUser ? "": "disabled" %>   name="othersf_rem"><%= annualCompCertificate.getOthersf_rem() != null ? annualCompCertificate.getOthersf_rem():"" %></textarea>
                    </div>
                 </div> 
                 
               </div>
               <hr>
               
               <p><b>Note</b></p>
               <div class="row">
                  <div class="col">
                  <p>
                     1. Where ever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to
                   </p>
                  </div>
               </div>
               
               <div class="row">
                  <div class="col">
                 <!--  <p>
                    2. This Compliance Certificate(s) shall be put up to the Board at its meeting to be held on <input type="date" class="meetingdate" id="meetingdate" name="meetingdate"> 
                    <label id="error-meetingdate" class="error-message text-danger"></label>
                     and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.
                     </p> -->
                      <p>2.This Compliance Certificate(s)n shall be put up to the Board at its ensuing Board Meeting and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.</p>
                  </div>
               </div>
               <br>
               <div class="row">
		                 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
		                     <label for="companies">For</label><br>
		                    
		                     <input type="text" class="w-100" readonly="readonly" name='companies' value="<%=companies %>">
		                     <label id="error-comanies" class="error-message text-danger"></label>
		                  </div>
		               </div>
		               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Date: </label>
                        <input type="date" class="date_2" id="date_2" name="date_2">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Name: </label>
                        <input type="text" class="employeeName" id="employeeName" name="employeeName" value="<%=annualCompCertificate.getEmplolyee_name()%>">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Place: </label>
                        <input type="text" class="place" id="place" name="place1" value="<%=annualCompCertificate.getPlace()%>">
                     </div>
                  </div>
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="roles">Role:</label><br>
                     <input type="text" class="w-100" id="roles" name="roles" value="<%=annualCompCertificate.getRoles()%>">
                     <!-- <select class="w-100" name="roles" id="roles">
                        <option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                     </select> -->
                     <label id="error-roles" class="error-message text-danger"></label>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexures 1: Current status of the sponsor(s) regulatory licenses </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile1">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureI" name="annexureI" accept=".xlsx"/>
                           </div>
                           <label id="error1" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureI_rem" placeholder="Remarks if any" name="annexureI_rem"><%=annualCompScrutinydetails.getAnnexurei_rem() == null ? "" : annualCompScrutinydetails.getAnnexurei_rem() %></textarea>
                    </div>
                 </div> 
                 
                 </div>
                 <br>
                 <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexures 2: Sponsor(s) holding in Intermediary </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile2">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureII" name="annexureII" accept=".xlsx"/>
                           </div>
                           <label id="error2" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureII_rem" placeholder="Remarks if any" name="annexureII_rem"><%=annualCompScrutinydetails.getAnnexureii_rem() == null ? "" : annualCompScrutinydetails.getAnnexureii_rem() %></textarea>
                    </div>
                 </div> 
                 
                </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexures 3: Statement of Sponsor(s) and PFMs Net-worth</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile3">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureIII" name="annexureIII" accept=".xlsx"/>
                           </div>
                           <label id="error3" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                  <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureIII_rem" placeholder="Remarks if any" name="annexureIII_rem"><%=annualCompScrutinydetails.getAnnexureiii_rem() == null ? "" : annualCompScrutinydetails.getAnnexureiii_rem() %></textarea>
                    </div>
                 </div> 
                 </div>
                 <br>
                 <div class="row"> 
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexures 4: Statement showing amount of income accrued but not realized as on closing date of the financial year.</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile4">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureIV" name="annexureIV" accept=".xlsx"/>
                           </div>
                           <label id="error4" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                  <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureIV_rem" placeholder="Remarks if any" name="annexureIV_rem" ><%=annualCompScrutinydetails.getAnnexureiv_rem() == null ? "" : annualCompScrutinydetails.getAnnexureiv_rem() %></textarea>
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure 5: List of activities outsourced  </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile5">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureV" name="annexureV" accept=".xlsx"/>
                           </div>
                           <label id="error5" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="" id="annexureV_rem" placeholder="Remarks if any" name="annexureV_rem"><%=annualCompScrutinydetails.getAnnexurev_rem() == null ? "" : annualCompScrutinydetails.getAnnexurev_rem() %></textarea>
                    </div>
                 </div> 
               </div>
               <div class="row" id="sub">
	               <div class="row text-center">
	                  <div class="col-md-12">
	                     <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
	                  </div>
	               </div>
	            </div>
            </form>
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
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
}
</style>

<script type="text/javascript">

$(document).ready(function() {

	toastr.options = {
		  "debug": false,
		  "positionClass": "toast-bottom-right",
		  "onclick": null,
		  "fadeIn": 300,
		  "fadeOut": 1000,
		  //"timeOut": 6000,
		  "extendedTimeOut": 1000
		}
	
	
	 $('#annexureI').bind('change', function () {
		  var filename = $("#annexureI").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile1").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureII').bind('change', function () {
		  var filename = $("#annexureII").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile2").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile2").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureIII').bind('change', function () {
		  var filename = $("#annexureIII").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile3").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile3").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureIV').bind('change', function () {
		  var filename = $("#annexureIV").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile4").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile4").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureV').bind('change', function () {
		  var filename = $("#annexureV").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile5").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile5").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	

	$.validator.addMethod("lettersonly", function(value, element) {
		  //return this.optional(element) || /^[a-zA-Z]+$/i.test(value);
		   return this.optional(element) || /^[a-z ]+$/i.test(value);
		}, "Please enter characters only");
	
	$.validator.addMethod('annexureI_validateFileName', function(value, element) {
		let filename = 'Annexures 1 Current status of the sponsor(s) regulatory licenses.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annexures 1 Current status of the sponsor(s) regulatory licenses.xlsx");
	
	$.validator.addMethod('annexureII_validateFileName', function(value, element) {
		let filename = 'Annexures 2 Sponsor(s) holding in Intermediary.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annexures 2 Sponsor(s) holding in Intermediary.xlsx");
	
	$.validator.addMethod('annexureIII_validateFileName', function(value, element) {
		let filename = 'Annexures 3 Statement of Sponsor(s) and PFMs Net-worth.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annexures 3 Statement of Sponsor(s) and PFMs Net-worth.xlsx");
	
	$.validator.addMethod('annexureIV_validateFileName', function(value, element) {
		let filename = 'Annexures 4 Statement showing amount of income accrued but not realized as on closing date of the financial year.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annexures 4 Statement showing amount of income accrued but not realized as on closing date of the financial year.xlsx");
	
	$.validator.addMethod('annexureV_validateFileName', function(value, element) {
		let filename = 'Annexure 5 List of activities outsourced.xlsx';
		return this.optional(element) || (value === filename); 
	}, "Please upload Annexure 5 List of activities outsourced.xlsx");
	
	

	 $("#annualComplains").validate({
	  	rules: {
	  		<portlet:namespace/>date_1: {
		      	required: true
		    },
		    <portlet:namespace/>date_2: {
			    required: true
			},
			<portlet:namespace/>meetingdate:{
				required: true
			},
			<portlet:namespace/>address:{
				required: true,
				lettersonly: true
			},
			<portlet:namespace/>employeeName:{
				required: true,
				lettersonly: true
			},
			<portlet:namespace/>place1:{
				required: true,
				lettersonly: true
			},
			roles:{
				required: true
			},
			annexureI:{
				required: true,
				annexureI_validateFileName: true
			},
			annexureII:{
				required: true,
				annexureII_validateFileName: true
			},
			annexureIII:{
				required: true,
				annexureIII_validateFileName: true
			},
			annexureIV:{
				required: true,
				annexureIV_validateFileName: true
			},
			annexureV:{
				required: true,
				annexureV_validateFileName: true
			},
		
	  },
	  errorPlacement: function (error, element) {
         if (element.attr("name") == "annexureI") {
              error.appendTo("#error1");
          } else if (element.attr("name") == "annexureII") {
              error.appendTo("#error2");
          } else if (element.attr("name") == "annexureIII") {
              error.appendTo("#error3");
          } else if (element.attr("name") == "annexureIV") {
              error.appendTo("#error4");
          }else if (element.attr("name") == "annexureV") {
              error.appendTo("#error5");
          }else if (element.attr("name") == "roles") {
              error.appendTo("#error-roles");
          }else if (element.attr("name") == "meetingdate") {
              error.appendTo("#error-meetingdate");
          } else {
        	  error.appendTo(element.parent().parent().after());
          }
      },
	}); 
	
	//eligibility
	
  $('.eligibilityIa').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIb').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIc').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityId').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIe').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIf').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIg').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIh').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIi').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIj').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIk').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIl').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.eligibilityIm').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.eligibilityIn').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.eligibilityIo').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.eligibilityIp').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.eligibilityIq').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.eligibilityIr').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.eligibilityIs').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	// Book
	$('.bookIIa').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.bookIIb').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.bookIIc').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	//Audit 
	
	$('.audita').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.auditb').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.auditc').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	//Stewardship
	
		$('.stewardshipa').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
		$('.stewardshipb').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		
		$('.stewardshipc').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });

	//Others
	$('.othersa').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
	
	$('.othersb').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.othersc').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.othersd').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.otherse').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	
	$('.othersf').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
	
	
	
	
	
	$("#annualComplains").on('submit', function(e) {
		console.log("Inside ajax");
		
		e.preventDefault();
			
			if($( "#annualComplains" ).valid()){
					
				$("#sub").addClass("hide");
				
					var formData = new FormData($("form.form")[0]);
					
	   				
	   				var htmlWidth = $("#canvasD").width();
					var htmlHeight = $("#canvasD").height();
					var pdfWidth = htmlWidth + (15 * 2);
					var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
					var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

					var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;


					html2canvas($("#canvasD")[0], { allowTaint: true }).then(function(canvas) {
						canvas.getContext('2d');

						var image = canvas.toDataURL("image/jpeg", 1.0);
						doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);


						for (var i = 1; i <= pageCount; i++) {
							doc.addPage(pdfWidth, pdfHeight);
							doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
						}

						console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
		/* 				doc.save("split-to-multi-page.pdf");
					     window.open(doc.output('bloburl')); */
					     
						var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
					     
						console.log("file::: ", file);
						
						formData.append("annual_compliance_report_pdf_file", file);
					
					$(".nps-report").hide();
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: '${annualComplainstResourceURL}',
			            data: formData,
			            success: function(data) {
			            	$(".nps-report").show();
			            	$(".animaion").hide();
			            	if(data == "Success") {
			            		//toastr.success('Form has been submitted successfully!');
		   		            	$('#success_tic').modal('show');
		   		            	$("#annualComplains")[0].reset();
		   		            	$('#success_tic').on('hidden.bs.modal', function (e) {
			            			location.reload(); 
			            		})
			            	} else {
			            		//console.log("Error Occured in ajax call");
			            		//toastr.error('An error occured while submitting the form. Please try again');
			            		$('#output').html("An error occured while submitting the form. Please try again");
				            	$('#errorExcel').modal('show');
			            	}
			            },
			            error: function() {
			            	$(".animaion").hide();
			            	//toastr.error('An error occured while submitting the form. Please try again');
			            	console.log("Error Occured in ajax call");
			            	$('#output').html("An error occured while submitting the form. Please try again");
			            	$('#errorExcel').modal('show');
			            },
			            complete: function(){
			            	$(".animaion").hide();
							console.log("Complete");
			            	if($("#sub").hasClass("hide")){
				            	$("#sub").removeClass("hide");
			            	}
				        }
			        }); 
	
				});
		    	
	 		}
		});
	//});
});

</script>


<portlet:resourceURL var="autoSaveResourceURL" id="<%=FormsPortletsKeyConstants.FORM_AUTOSAVE_COMMAND %>"/>
<script type="text/javascript">
            var autoSaveId="";
            $(document).ready(function(){
            	autoSaveId="<%=reportuploadlogId%>";
            	
            	  $("input[name=eligibilityIa]").change(function(){
            	    var name=$("input[name=eligibilityIa]:checked").val();
            	    console.log("name"+name)
            	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IA%>",name);
            	  });
            	  $("input[name=eligibilityIb]").change(function(){
              	    var name=$("input[name=eligibilityIb]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IB%>",name);
              	  });
            	  $("input[name=eligibilityIc]").change(function(){
              	    var name=$("input[name=eligibilityIc]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IC%>",name);
              	  });
            	  $("input[name=eligibilityId]").change(function(){
              	    var name=$("input[name=eligibilityId]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_ID%>",name);
              	  });
            	  $("input[name=eligibilityIe]").change(function(){
              	    var name=$("input[name=eligibilityIe]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IE%>",name);
              	  });
            	  $("input[name=eligibilityIf]").change(function(){
              	    var name=$("input[name=eligibilityIf]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IF%>",name);
              	  });
            	  $("input[name=eligibilityIg]").change(function(){
              	    var name=$("input[name=eligibilityIg]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IG%>",name);
              	  });
            	  
            	  $("input[name=eligibilityIh]").change(function(){
              	    var name=$("input[name=eligibilityIh]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IH%>",name);
              	  });
            	  $("input[name=eligibilityIi]").change(function(){
              	    var name=$("input[name=eligibilityIi]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_II%>",name);
              	  });
            	  $("input[name=eligibilityIj]").change(function(){
              	    var name=$("input[name=eligibilityIj]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IJ%>",name);
              	  });
            	  $("input[name=eligibilityIk]").change(function(){
              	    var name=$("input[name=eligibilityIk]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IK%>",name);
              	  });
            	  
            	  $("input[name=eligibilityIl]").change(function(){
              	    var name=$("input[name=eligibilityIl]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IL%>",name);
              	  });
            	  $("input[name=eligibilityIm]").change(function(){
              	    var name=$("input[name=eligibilityIm]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IM%>",name);
              	  });
            	  $("input[name=eligibilityIn]").change(function(){
              	    var name=$("input[name=eligibilityIn]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IN%>",name);
              	  });
            	  $("input[name=eligibilityIo]").change(function(){
              	    var name=$("input[name=eligibilityIo]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IO%>",name);
              	  });
            	  $("input[name=eligibilityIp]").change(function(){
              	    var name=$("input[name=eligibilityIp]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IP%>",name);
              	  });
            	   $("input[name=eligibilityIq]").change(function(){
            	    var name=$("input[name=eligibilityIq]:checked").val();
            	    console.log("name"+name)
            	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IQ%>",name);
            	  });
            	   $("input[name=eligibilityIr]").change(function(){
               	    var name=$("input[name=eligibilityIr]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IR%>",name);
               	  });
            	   $("input[name=eligibilityIs]").change(function(){
               	    var name=$("input[name=eligibilityIs]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IS%>",name);
               	  });
            	   $("input[name=bookIIa]").change(function(){
               	    var name=$("input[name=bookIIa]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.BOOK_A%>",name);
               	  });
            	   $("input[name=bookIIb]").change(function(){
               	    var name=$("input[name=bookIIb]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.BOOK_B%>",name);
               	  });
            	   $("input[name=bookIIc]").change(function(){
               	    var name=$("input[name=bookIIc]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.BOOK_C%>",name);
               	  });
            	   $("input[name=audita]").change(function(){
               	    var name=$("input[name=audita]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.AUDIT_A%>",name);
               	  });
            	   $("input[name=auditb]").change(function(){
               	    var name=$("input[name=auditb]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.AUDIT_B%>",name);
               	  });
            	   $("input[name=auditc]").change(function(){
               	    var name=$("input[name=auditc]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.AUDIT_C%>",name);
               	  });
            	   $("input[name=stewardshipa]").change(function(){
               	    var name=$("input[name=stewardshipa]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.STEWARDSHIP_A%>",name);
               	  });
            	   $("input[name=stewardshipb]").change(function(){
               	    var name=$("input[name=stewardshipb]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.STEWARDSHIP_B%>",name);
               	  });
            	   $("input[name=stewardshipc]").change(function(){
               	    var name=$("input[name=stewardshipc]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.STEWARDSHIP_C%>",name);
               	  });
            	   $("input[name=othersa]").change(function(){
               	    var name=$("input[name=othersa]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_A%>",name);
               	  });
            	   $("input[name=othersb]").change(function(){
               	    var name=$("input[name=othersb]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_B%>",name);
               	  });
            	   $("input[name=othersc]").change(function(){
               	    var name=$("input[name=othersc]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_C%>",name);
               	  });
            	   $("input[name=othersd]").change(function(){
               	    var name=$("input[name=othersd]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_D%>",name);
               	  });
            	   $("input[name=otherse]").change(function(){
               	    var name=$("input[name=otherse]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_E%>",name);
               	  });
            	   $("input[name=othersf]").change(function(){
               	    var name=$("input[name=othersf]:checked").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_F%>",name);
               	  });
            	   
            	   
            	   $("textarea[name=eligibilityIa_rem]").change(function(){
                 	    var name=$("textarea[name=eligibilityIa_rem]").val();
                 	    console.log("name"+name)
                 	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IA_REM%>",name);
                 	  });
            	   $("textarea[name=eligibilityIb_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIb_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IB_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIc_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIc_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IC_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityId_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityId_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_ID_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIe_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIe_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IE_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIf_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIf_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IF_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIg_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIg_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IG_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIh_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIh_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IH_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIi_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIi_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_II_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIj_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIj_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IJ_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIk_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIk_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IK_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIl_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIl_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IL_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIm_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIm_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IM_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIn_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIn_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IN_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIo_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIo_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IO_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIp_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIp_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IP_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIq_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIq_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IQ_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIr_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIr_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IR_REM%>",name);
                	  });
            	   $("textarea[name=eligibilityIs_rem]").change(function(){
                	    var name=$("textarea[name=eligibilityIs_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.ELIGIBILITY_IS_REM%>",name);
                	  });
            	   $("textarea[name=bookIIa_rem]").change(function(){
                	    var name=$("textarea[name=bookIIa_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.BOOK_IIA_REM%>",name);
                	  });
            	   $("textarea[name=bookIIb_rem]").change(function(){
                	    var name=$("textarea[name=bookIIb_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.BOOK_IIB_REM%>",name);
                	  });
            	   $("textarea[name=bookIIc_rem]").change(function(){
                	    var name=$("textarea[name=bookIIc_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.BOOK_IIC_REM%>",name);
                	  });
            	   
            		   $("textarea[name=audita_rem]").change(function(){
                    	    var name=$("textarea[name=audita_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.AUDIT_A_REM%>",name);
                    	  });
            		   $("textarea[name=auditb_rem]").change(function(){
                    	    var name=$("textarea[name=auditb_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.AUDIT_B_REM%>",name);
                    	  });
            		   $("textarea[name=auditc_rem]").change(function(){
                    	    var name=$("textarea[name=auditc_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.AUDIT_C_REM%>",name);
                    	  });
            		   $("textarea[name=stewardshipa_rem]").change(function(){
                    	    var name=$("textarea[name=stewardshipa_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.STEWARDSHIP_A_REM%>",name);
                    	  });
            		   $("textarea[name=stewardshipb_rem]").change(function(){
                    	    var name=$("textarea[name=stewardshipb_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.STEWARDSHIP_B_REM%>",name);
                    	  });
            		   $("textarea[name=stewardshipc_rem]").change(function(){
                    	    var name=$("textarea[name=stewardshipc_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.STEWARDSHIP_C_REM%>",name);
                    	  });
            		   $("textarea[name=othersa_rem]").change(function(){
                    	    var name=$("textarea[name=othersa_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_A_REM%>",name);
                    	  });
            		   $("textarea[name=othersb_rem]").change(function(){
                    	    var name=$("textarea[name=othersb_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_B_REM%>",name);
                    	  });
            		   $("textarea[name=othersc_rem]").change(function(){
                    	    var name=$("textarea[name=othersc_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_C_REM%>",name);
                    	  });
            		   $("textarea[name=othersd_rem]").change(function(){
                    	    var name=$("textarea[name=othersd_rem]").val();
                    	    console.log("name"+name)
                    	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_D_REM%>",name);
                    	  });
            		   
            		   $("textarea[name=otherse_rem]").change(function(){
               	    var name=$("textarea[name=otherse_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_E_REM%>",name);
                	  });
            	   $("textarea[name=othersf_rem]").change(function(){
                	    var name=$("textarea[name=othersf_rem]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=AnnualyCompFormConstants.OTHERS_F_REM%>",name);
                	  });
           	  
            });

            function autoSave(fieldType,fieldName,fieldValue) {
console.log("autoSaveId : "+autoSaveId)
            	const formData = new FormData();
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.FIELD_TYPE%>', fieldType);
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.FIELD_NAME%>', fieldName);
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.FIELD_VALUE%>', fieldValue);
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.REPORT_UPLOAD_LOG_ID%>', autoSaveId); 

            	  const xhr = new XMLHttpRequest();
            	  xhr.open('POST', '<%=autoSaveResourceURL%>', true);

            	  xhr.onload = function () {
            	    if (xhr.status === 200) {
            	    	
            	    } else {
            	      //document.getElementById('uploadStatus').textContent = 'File upload failed.';
            	    }
            	  };

            	  xhr.send(formData);
            	}
            </script>


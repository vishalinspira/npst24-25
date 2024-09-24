<%@page import="nps.common.service.util.CommonRoleService"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.daily.average.service.model.AnnualCompCertificate"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.service.AnnualCompCertificateLocalServiceUtil"%>
<%@page import="com.daily.average.service.service.AnnualCompCertificateScrutinyLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.AnnualCompCertificateScrutiny"%>
<%-- <%@ include file="/init.jsp" %> --%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>


<% 
long reportUploadLogId=ParamUtil.getLong(request, "reportUploadLogId");
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
AnnualCompCertificateScrutiny annualCompScrutiny =null;
AnnualCompCertificate annualCompDetails =null;

try{
	annualCompDetails = AnnualCompCertificateLocalServiceUtil.fetchAnnualCompCertificate(reportUploadLogId);
}catch(Exception e){
	annualCompDetails=AnnualCompCertificateLocalServiceUtil.createAnnualCompCertificate(0);
}try{
 List<AnnualCompCertificateScrutiny> annualCompScrutinydetails = AnnualCompCertificateScrutinyLocalServiceUtil.findAnnualCompCertificateScrutinyByReportUplaodLogId(reportUploadLogId);
 annualCompScrutiny=annualCompScrutinydetails.get(annualCompScrutinydetails.size()-1);
}catch(Exception e){
	annualCompScrutiny=AnnualCompCertificateScrutinyLocalServiceUtil.createAnnualCompCertificateScrutiny(0);
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


<div class="nps-page-main nps-upload-report nps-statement-wrapper">
     
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Annual Compliance Certificate</h4>
<!--             <form class="form" id="annualComplains" action="#" method="post">
 -->
<%--                <div class="row">
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
               <br> --%>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="companies">Company Name : </label>
                      <span><%=CommonRoleService.getCompanyName(annualCompDetails.getCreatedby()) %></span>
                    <%--  <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>"> --%>
                  </div>
               </div>
               <br>
               
               <div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="">
												<label for="name" class="pl-2">Report Due Date : </label>
												<span><%=dateFormat.format(annualCompDetails.getReportDate()) %></span>
												 <%-- <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>dueDate' value="<%=dateFormat.format(annualCompDetails.getReportDate()) %>"> --%>
												<%-- <input class="reportDate" type="text" value="<%=dateFormat.format(inputQuarterlyInterval.getReportDate()) %>" name="reportDate" readonly="readonly"> --%>
										</div>
									</div>
								</div><br>
								
								<div class="row">
                           <div class="col-md-1">
                              <h5>SL.NO</h5>
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
                              <h5>NPST Remarks</h5>
                           </div>
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
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIa">
                     <p>a) Whether Sponsor(s) and PFM are complying with the eligibility requirements of PFRDA (PF) 
                         regulations 2015 and certificate of registration issued to it by PFRDA.
                     </p>
                  </div>
                  <div class="col-md-2">
					 <span><%=annualCompDetails.getEligibilityia()%></span>
                  </div>
                   <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIa_rem"    name="eligibilityIa_rem"><%= annualCompDetails.getEligibilityIa_rem() != null ? annualCompDetails.getEligibilityIa_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIa_npsrem"    name="eligibilityIa_npsrem"><%= annualCompScrutiny.getEligibilityia_rem() != null ? annualCompScrutiny.getEligibilityia_rem():"" %></textarea>
               </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIb">
                     <p>b) Whether PFM is conducting its activities in accordance with the PFRDA Act,
                      applicable regulations in force and any guidelines, notifications or circulars 
                      issued by the Authority along with the operational agreement executed between the NPS 
                      Trust and PFM.
                     </p>
                  </div>
                  <div class="col-md-2">
				<span><%=annualCompDetails.getEligibilityib()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIb_rem"    name="eligibilityIb_rem"><%= annualCompDetails.getEligibilityIb_rem() != null ? annualCompDetails.getEligibilityIb_rem():"" %></textarea>
                 		
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIb_npsrem"    name="eligibilityIb_npsrem"><%= annualCompScrutiny.getEligibilityia_rem() != null ? annualCompScrutiny.getEligibilityia_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIc">
                     <p>c) Whether Sponsor(s) is maintaining minimum Tangible Net-worth as stipulated by PFRDA
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityic()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIc_rem"    name="eligibilityIc_rem"><%= annualCompDetails.getEligibilityIc_rem() != null ? annualCompDetails.getEligibilityIc_rem():"" %></textarea>
                		
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIc_npsrem"    name="eligibilityIc_npsrem"><%= annualCompScrutiny.getEligibilityic_rem() != null ? annualCompScrutiny.getEligibilityic_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityId">
                     <p>d) Whether Sponsor(s) has contributed minimum Tangible Net-worth of the PFM as stipulated by the PFRDA
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityid()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    
                    	<textarea class="form-control" id="eligibilityId_rem"    name="eligibilityId_rem"><%= annualCompDetails.getEligibilityId_rem() != null ? annualCompDetails.getEligibilityId_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityId_npsrem"    name="eligibilityId_npsrem"><%= annualCompScrutiny.getEligibilityid_rem() != null ? annualCompScrutiny.getEligibilityid_rem():"" %></textarea>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIe">
                     <p>e) Whether direct or indirect holding by a foreign company in the PFM is in compliance to PFRDA Act,
                      regulations and other communications.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityie()%></span>
                  </div>
                  
                  <div class="col-md-2">
                   
                    	<textarea class="form-control" id="eligibilityIe_rem"    name="eligibilityIe_rem"><%= annualCompDetails.getEligibilityIe_rem() != null ? annualCompDetails.getEligibilityIe_rem():"" %></textarea>
                    
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIe_npsrem"    name="eligibilityIe_npsrem"><%= annualCompScrutiny.getEligibilityie_rem() != null ? annualCompScrutiny.getEligibilityie_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIf">
                     <p>f) Whether annual fee payable to Authority has been paid as specified by the Authority and within the timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                    <span><%=annualCompDetails.getEligibilityif()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIf_rem"    name="eligibilityIf_rem"><%= annualCompDetails.getEligibilityIf_rem() != null ? annualCompDetails.getEligibilityIf_rem():"" %></textarea>
                    	 </div>
                    </div>
                    <div class="col-md-2">
                    <textarea class="form-control" id="eligibilityIf_npsrem"    name="eligibilityIf_npsrem"><%= annualCompScrutiny.getEligibilityif_rem() != null ? annualCompScrutiny.getEligibilityif_rem():"" %></textarea>
                    </div>
                 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIg">
                     <p>g) Whether there is any change in the regulatory license (s) issued to the Sponsor(s).
                        Statement showing current status of the sponsor(s) regulatory licenses is provided in Annexure1
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityig()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIg_rem"    name="eligibilityIg_rem"><%= annualCompDetails.getEligibilityIg_rem() != null ? annualCompDetails.getEligibilityIg_rem():"" %></textarea>
                    </div>	
                    </div>
                    <div class="col-md-2">
                    <textarea class="form-control" id="eligibilityIg_npsrem"    name="eligibilityIg_npsrem"><%= annualCompScrutiny.getEligibilityig_rem() != null ? annualCompScrutiny.getEligibilityig_rem():"" %></textarea>
                   
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIh">
                     <p>h) Whether there is any change in the name of the PFM or Sponsor(s) 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityih()%></span>
                  </div>
                  
                  <div class="col-md-2">
                     <textarea class="form-control" id="eligibilityIh_rem"    name="eligibilityIh_rem"><%= annualCompDetails.getEligibilityIh_rem() != null ? annualCompDetails.getEligibilityIh_rem():"" %></textarea>
                     
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIh_npsrem"    name="eligibilityIh_npsrem"><%= annualCompScrutiny.getEligibilityih_rem() != null ? annualCompScrutiny.getEligibilityih_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIi">
                     <p>i) Whether sponsor(s) periodically review the activities of the pension fund.(Incase of irregularities sponsor shall immediately report to the Authority.)
                     
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityii()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIi_rem"    name="eligibilityIi_rem"><%= annualCompDetails.getEligibilityIi_rem() != null ? annualCompDetails.getEligibilityIi_rem():"" %></textarea>
                    	</div> 
                    </div>
                    <div class="col-md-2">
                    <textarea class="form-control" id="eligibilityIi_npsrem"    name="eligibilityIi_npsrem"><%= annualCompScrutiny.getEligibilityii_rem() != null ? annualCompScrutiny.getEligibilityii_rem():"" %></textarea>
                </div>
                 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIj">
                     <p>j) Whether the sponsor of a pension fund or the pension fund itself hold any equity stake in any other pensionfund regulated by the Authority 
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityij()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIj_rem"    name="eligibilityIj_rem"><%= annualCompDetails.getEligibilityIj_rem() != null ? annualCompDetails.getEligibilityIj_rem():"" %></textarea>
                    	</div> 
                    </div>
                    <div class="col-md-2">
                    <textarea class="form-control" id="eligibilityIj_npsrem"    name="eligibilityIj_npsrem"><%= annualCompScrutiny.getEligibilityij_rem() != null ? annualCompScrutiny.getEligibilityij_rem():"" %></textarea>
                    </div>
                 
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIk">
                     <p>k) Whether Sponsor(s) holds directly or indirectly more than permissible stake in CRA, Trustee Bank or Custodian.
                          Statement of sponsor(s) holdingin intermediaries to be provide details in Annexure 2.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityik()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIk_rem"    name="eligibilityIk_rem"><%= annualCompDetails.getEligibilityIk_rem() != null ? annualCompDetails.getEligibilityIk_rem():"" %></textarea>
                    	</div>
                    </div>
                    <div class="col-md-2">
                    <textarea class="form-control" id="eligibilityIk_npsrem"    name="eligibilityIk_npsrem"><%= annualCompScrutiny.getEligibilityik_rem() != null ? annualCompScrutiny.getEligibilityik_rem():"" %></textarea>
                    </div>
                  
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIl">
                     <p>l) Whether PFM has executed such agreements as specified by the Authority in the interest of subscribers with the parties, 
                      including other intermediaries, like Investment Management agreement and NDA with NPS Trust, Service contracts such as for custody
                      arrangements and transfer agency of the securities etc., and copy of such agreements have been submitted to NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityil()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIl_rem"    name="eligibilityIl_rem" ><%= annualCompDetails.getEligibilityIl_rem() != null ? annualCompDetails.getEligibilityIl_rem():"" %></textarea>
                    	
                 </div> 
                  <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIl_npsrem"    name="eligibilityIl_npsrem"><%= annualCompScrutiny.getEligibilityil_rem() != null ? annualCompScrutiny.getEligibilityil_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIm">
                     <p>m) Whether PFM has failed to take prior approval 
                     from authority of any major change in management or ownership or shareholding pattern or any change 
                     in controlling interest of the Sponsor(s) of the pension fund.Statement regarding Net-worth of the Sponsor(s) 
                     and PFM is provided in Annexure 3.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityim()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIm_rem"    name="eligibilityIm_rem" ><%= annualCompDetails.getEligibilityIm_rem() != null ? annualCompDetails.getEligibilityIm_rem():"" %></textarea>
                    	</div>
                    </div>
                    <div class="col-md-2">
                    <textarea class="form-control" id="eligibilityIm_npsrem"    name="eligibilityIm_npsrem"><%= annualCompScrutiny.getEligibilityim_rem() != null ? annualCompScrutiny.getEligibilityim_rem():"" %></textarea>
                    </div>
                  
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIn">
                     <p>n) Whether the sponsor or pension fund or its principal officer or key management 
                     personnel has been convicted by a court forany offence involving moral turpitude, economic offence, securities laws or fraud;
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityin()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIn_rem"    name="eligibilityIn_rem" ><%= annualCompDetails.getEligibilityIn_rem() != null ? annualCompDetails.getEligibilityIn_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIn_npsrem"    name="eligibilityIn_npsrem"><%= annualCompScrutiny.getEligibilityin_rem() != null ? annualCompScrutiny.getEligibilityin_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIo">
                     <p>o) Whether an order of winding up has been passed against the Sponsor(s) or pension fund
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityio()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIo_rem"    name="eligibilityIo_rem" ><%= annualCompDetails.getEligibilityIo_rem() != null ? annualCompDetails.getEligibilityIo_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIo_npsrem"    name="eligibilityIo_npsrem"><%= annualCompScrutiny.getEligibilityio_rem() != null ? annualCompScrutiny.getEligibilityio_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIp">
                     <p>p) Whether Sponsor(s) or PFM or Key promoter has been declared insolvent
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityip()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIp_rem"    name="eligibilityIp_rem" ><%= annualCompDetails.getEligibilityIp_rem() != null ? annualCompDetails.getEligibilityIp_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIp_npsrem"    name="eligibilityIp_npsrem"><%= annualCompScrutiny.getEligibilityip_rem() != null ? annualCompScrutiny.getEligibilityip_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIq">
                     <p>q) Whether any order restraining, prohibiting or debarring the Sponsor(s) or PFM or its principal officer or key management personnel 
                     from dealing in securities in the capital market or from accessing the capital market has been passed by any regulatory authority or court
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityiq()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIq_rem"    name="eligibilityIq_rem"><%= annualCompDetails.getEligibilityIq_rem() != null ? annualCompDetails.getEligibilityIq_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIq_npsrem"    name="eligibilityIq_npsrem"><%= annualCompScrutiny.getEligibilityiq_rem() != null ? annualCompScrutiny.getEligibilityiq_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIr">
                     <p>r) Whether any order withdrawing or refusing to grant any license or approval to the sponsor or pension fund or its 
                        whole timedirector or managing partner which has a bearing on the capital market, has been passed by the concerned 
                        financialsector regulator or any other regulatory authority
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityir()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="eligibilityIr_rem"    name="eligibilityIr_rem"><%= annualCompDetails.getEligibilityIr_rem() != null ? annualCompDetails.getEligibilityIr_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIr_npsrem"    name="eligibilityIr_npsrem"><%= annualCompScrutiny.getEligibilityir_rem() != null ? annualCompScrutiny.getEligibilityir_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="eligibilityIs">
                     <p> s) Whether there is any notice served of any action or investigation or other proceedings
                      of any nature whatsoever, against the sponsor orpension fund, or its Chief Executive Officer, 
                      any of its directors or employees, or a related group concern, by anygovernmental or statutory authority which would restrain, 
                      prohibit or otherwise challenge or impede the performance of obligations as sponsor or pension fund of the pension schemes regulated by theAuthority, 
                      and that there isadverse proceedings against it from anyfinancial sector regulator including the RBI, IRDA or SEBI, 
                     of a nature that couldadversely affect the ability to provide the services as sponsor or pension fund for the pension schemesregulated bythe Authority;
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getEligibilityis()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="eligibilityIs_rem"    name="eligibilityIs_rem"><%= annualCompDetails.getEligibilityIs_rem() != null ? annualCompDetails.getEligibilityIs_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="eligibilityIs_npsrem"    name="eligibilityIs_npsrem"><%= annualCompScrutiny.getEligibilityis_rem() != null ? annualCompScrutiny.getEligibilityis_rem():"" %></textarea>
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
                  <div class="col-md-5">
                   <input type="checkbox" class="pdfbox"  name="pdfbox"  value="bookIIa">
                     <p>a) Whether PFM has maintained books and records about the operation 
                     of pension schemes to ensure compliance with the provisions of the Income-tax Act, 
                     the companies Actor under any otherAct in force and in such manner as may be required or called for by the Authority
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getBooka()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="bookIIa_rem"   name="bookIIa_rem" ><%= annualCompDetails.getBookIIa_rem() != null ? annualCompDetails.getBookIIa_rem():"" %></textarea>
                    	
                    </div>
                 </div>
                 <div class="col-md-2">
                 <textarea class="form-control" id="bookIIa_npsrem"    name="bookIIa_npsrem"><%= annualCompScrutiny.getBooka_rem() != null ? annualCompScrutiny.getBooka_rem():"" %></textarea>
                  </div>
               </div>
               <br>
               
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="bookIIb">
                     <p>b) Whether PFM has prepared financial statements, 
                     annual report in compliance to regulation 19 (1) and schedule VII of PFRDA (PF) Regulations 
                     2015 and PFRDA (Preparation of financial statements and auditors
                      report of schemes under national pension system) guidelines 2012 and subsequent amendments.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getBookb()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="bookIIb_rem"    name="bookIIb_rem" ><%= annualCompDetails.getBookIIb_rem() != null ? annualCompDetails.getBookIIb_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="bookIIb_npsrem"    name="bookIIb_npsrem"><%= annualCompScrutiny.getBookb_rem() != null ? annualCompScrutiny.getBookb_rem():"" %></textarea>
                 </div>
               </div>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="bookIIc">
                     <p>c) Whether PFM has furnished to NPS Trust periodic reports including unaudited provisional 
                     financial statements (Balance Sheet, Revenue Account, notes and schedules) of each scheme and annual 
                     report within the specified time.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getBookc()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="bookIIc_rem"    name="bookIIc_rem" ><%= annualCompDetails.getBookIIc_rem() != null ? annualCompDetails.getBookIIc_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="bookIIc_npsrem"    name="bookIIc_npsrem"><%= annualCompScrutiny.getBookc_rem() != null ? annualCompScrutiny.getBookc_rem():"" %></textarea>
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
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="audita">
                     <p>a) Whether PFM has got its scheme accounts audited by the auditor appointed by the NPS Trust within specified timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                  <span><%=annualCompDetails.getAudita()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="audita_rem"    name="audita_rem" ><%= annualCompDetails.getAudita_rem() != null ? annualCompDetails.getAudita_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                  <div class="col-md-2">
                 <textarea class="form-control" id="audita_npsrem"    name="audita_npsrem"><%= annualCompScrutiny.getAudita_rem() != null ? annualCompScrutiny.getAudita_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="auditb">
                     <p>b)  Whether Audited Annual report and other information have been submitted to NPS Trust after approval of 
                     the board of directors of the pension fund, within specified timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getAuditb()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    	<textarea class="form-control" id="auditb_rem"     name="auditb_rem"><%= annualCompDetails.getAuditb_rem() != null ? annualCompDetails.getAuditb_rem():"" %></textarea>
                    	
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="auditb_npsrem"    name="auditb_npsrem"><%= annualCompScrutiny.getAuditb_rem() != null ? annualCompScrutiny.getAuditb_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="auditc">
                     <p>c) Whether latest audited annual report has been placed on PFMs website within specified timelines.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getAuditc()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="auditc_rem"     name="auditc_rem"><%= annualCompDetails.getAuditc_rem() != null ? annualCompDetails.getAuditc_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                  <div class="col-md-2">
                 <textarea class="form-control" id="auditc_npsrem"    name="auditc_npsrem"><%= annualCompScrutiny.getAuditc_rem() != null ? annualCompScrutiny.getAuditc_rem():"" %></textarea>
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
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="stewardshipa">
                     <p>a) Whether PFM has complied with circular PFRDA/2018/01/PF/01 on Common Stewardship Code dated 04.05.2018.
                     </p>
                  </div>
                  <div class="col-md-2">
                  <span><%=annualCompDetails.getStewardshipa()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipa_rem"     name="stewardshipa_rem"><%= annualCompDetails.getStewardshipa_rem() != null ? annualCompDetails.getStewardshipa_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="stewardshipa_npsrem"    name="stewardshipa_npsrem"><%= annualCompScrutiny.getStewardshipa_rem() != null ? annualCompScrutiny.getStewardshipa_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="stewardshipb">
                     <p>b) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust;and 
                     </p>
                  </div>
                  <div class="col-md-2">
     				<span><%=annualCompDetails.getStewardshipb()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipb_rem"    name="stewardshipb_rem" ><%= annualCompDetails.getStewardshipb_rem() != null ? annualCompDetails.getStewardshipb_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="stewardshipa_npsrem"    name="stewardshipa_npsrem"><%= annualCompScrutiny.getStewardshipb_rem() != null ? annualCompScrutiny.getStewardshipb_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="stewardshipc">
                     <p>c) Whether, annual voting report has been submitted to the NPS Trust.(Circular PFRDA/2017/17/PF/1 dated 20.04.2017)

                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getStewardshipc()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="stewardshipc_rem"    name="stewardshipc_rem"><%= annualCompDetails.getStewardshipc_rem() != null ? annualCompDetails.getStewardshipc_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="stewardshipc_npsrem"    name="stewardshipc_npsrem"><%= annualCompScrutiny.getStewardshipc_rem() != null ? annualCompScrutiny.getStewardshipc_rem():"" %></textarea>
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
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="othersa">
                     <p>a) Whether all interest, dividends or any such accrual income and proceeds of redemption/sale were collected on due dates 
                     and promptly credited to the scheme accounts.Statement showing amount of income accrued but not realized as on closing date of 
                     the financial year is provided in Annexure 4.
                     </p>
                  </div>
                  <div class="col-md-2">
                  <span><%=annualCompDetails.getOthersa()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersa_rem"    name="othersa_rem" ><%= annualCompDetails.getOthersa_rem() != null ? annualCompDetails.getOthersa_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="othersa_npsrem"    name="othersa_npsrem"><%= annualCompScrutiny.getOthersa_rem() != null ? annualCompScrutiny.getOthersa_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="othersb">
                     <p>b) Whether any of the core/non-core activities of the PFM, as defined in circular 
                     no. PFRDA/2017/30/PF/4 dated 09th October 2017, has been outsourced to a third party service provider by
                      the PFM.Statement showing list of activities outsourced is provided in Annexure 5.
                     </p>
                  </div>
                  <div class="col-md-2">
					<span><%=annualCompDetails.getOthersb()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersb_rem"    name="othersb_rem"><%= annualCompDetails.getOthersb_rem() != null ? annualCompDetails.getOthersb_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="othersb_npsrem"    name="othersb_npsrem"><%= annualCompScrutiny.getOthersb_rem() != null ? annualCompScrutiny.getOthersb_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               
                <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="othersc">
                     <p>c) Whether comprehensive service level agreements have been executed with outsourcing service providers covering terms of 
                     contracts in consonance with to the provisions of PFRDA Act, rules, 
                     regulations, guidelines and directions issued by the authority and copies of all such 
                     agreements have been submitted to NPS Trust.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getOthersc()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersc_rem"    name="othersc_rem"><%= annualCompDetails.getOthersc_rem() != null ? annualCompDetails.getOthersc_rem():"" %></textarea>
                    	
                    </div>
                 </div>
                 <div class="col-md-2">
                 <textarea class="form-control" id="othersc_npsrem"    name="othersc_npsrem"><%= annualCompScrutiny.getOthersc_rem() != null ? annualCompScrutiny.getOthersc_rem():"" %></textarea>
                  </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="othersd">
                     <p>d) Incase any award has been passed against the PFM under the Pension fund Regulatory 
                     and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015, whether PFM has complied with such award.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getOthersd()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersd_rem"    name="othersd_rem"><%= annualCompDetails.getOthersd_rem() != null ? annualCompDetails.getOthersd_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="othersd_npsrem"    name="othersd_npsrem"><%= annualCompScrutiny.getOthersd_rem() != null ? annualCompScrutiny.getOthersd_rem():"" %></textarea>
                 </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="otherse">
                     <p>e) Whether PFM has complied with Cyber Security policy for Intermediaries issued vide circular PFRDA/2019/2/REG dated 07.01.2019.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getOthersf()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="otherse_rem"    name="otherse_rem"><%= annualCompDetails.getOtherse_rem() != null ? annualCompDetails.getOtherse_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 <div class="col-md-2">
                 <textarea class="form-control" id="otherse_npsrem"    name="otherse_npsrem"><%= annualCompScrutiny.getOtherse_rem() != null ? annualCompScrutiny.getOtherse_rem():"" %></textarea>
              </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="othersf">
                     <p>f) Whether PFM has ensured dissemination of adequate, accurate, explicit and timely information about the investment policies, 
                     investment objectives, financial position and general affairs of the scheme to the subscribers in a fairly simple language.
                     </p>
                  </div>
                  <div class="col-md-2">
                     <span><%=annualCompDetails.getOthersf()%></span>
                  </div>
                  
                  <div class="col-md-2">
                    <div class="form-group">
                    	<textarea class="form-control" id="othersf_rem"     name="othersf_rem"><%= annualCompDetails.getOthersf_rem() != null ? annualCompDetails.getOthersf_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                  <div class="col-md-2">
                 <textarea class="form-control" id="othersf_npsrem"    name="othersf_npsrem"><%= annualCompScrutiny.getOthersf_rem() != null ? annualCompScrutiny.getOthersf_rem():"" %></textarea>
                 </div>
               </div>
               <hr>

               <div class="row">
               <div class="col-lg-6 col-md-6 col-sm-12 col-12">
               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="annexureI">
                <label>Annexures 1: Current status of the sponsor(s) regulatory licenses </label>  
                </div> 
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	<textarea class="form-control" id="annexureI_npsrem"    name="annexureI_npsrem"><%= annualCompScrutiny.getAnnexurei_rem() != null ? annualCompScrutiny.getAnnexurei_rem():"" %></textarea>
                   
                    </div>
                 </div> 
                 
                 </div>
                 <br>
                 <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="annexureII">
                  <label>Annexures 2: Sponsor(s) holding in Intermediary </label>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                  	  <textarea class="form-control" id="annexureII_npsrem"    name="annexureII_npsrem"><%= annualCompScrutiny.getAnnexureii_rem() != null ? annualCompScrutiny.getAnnexureii_rem():"" %></textarea>
                
                    </div>
                 </div> 
                 
                </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="annexureIII">
                   <label>Annexures 3: Statement of Sponsor(s) and PFMs Net-worth</label>
                  </div>
                  
                  <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                  	  <textarea class="form-control" id="annexureIII_npsrem"    name="annexureIII_npsrem"><%= annualCompScrutiny.getAnnexureiii_rem() != null ? annualCompScrutiny.getAnnexureiii_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
                 </div>
                 <br>
                 <div class="row"> 
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="annexureIV">
                  <label>Annexures 4: Statement showing amount of income accrued but not realized as on closing date of the financial year.</label>
                  </div>
                  
                  <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                  	  <textarea class="form-control" id="annexureIV_npsrem"    name="annexureIV_npsrem"><%= annualCompScrutiny.getAnnexureiv_rem() != null ? annualCompScrutiny.getAnnexureiv_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
               </div>
               <br>
               
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  <input type="checkbox" class="pdfbox"  name="pdfbox"  value="annexureV">
                  <label>Annexure 5: List of activities outsourced  </label>
                  </div>
                  
                   <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                  	  <textarea class="form-control" id="annexureV_npsrem"    name="annexureV_npsrem"><%= annualCompScrutiny.getAnnexurev_rem() != null ? annualCompScrutiny.getAnnexurev_rem():"" %></textarea>
                    	
                    </div>
                 </div> 
               </div>
               <div class="row" id="sub">
	               <div class="row text-center">
	                  <div class="col-md-12">
	                      <button class="btn btn-primary" id="pdfButton" onClick="downloadPDF()">PDF  </button>
	                  </div>
	               </div>
	            </div>
            <!-- </form> -->
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
		<%-- 	var url="<%=formsPDFCreationsResourceURL%>&<portlet:namespace/>pdfCheckBox="+pdfCheckBoxArray+"&<portlet:namespace/>reportUploadlogId="+reportUploadLogId; --%>	
			console.log(url);
					//alert(url);
					window.location.href=url;
             }, 300);
        }
        
    </script>


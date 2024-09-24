<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletURLUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@page import="com.daily.average.service.service.ScrutinyInputQuarterlyIntervalLocalServiceUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.daily.average.service.model.ScrutinyInputQuarterlyInterval"%>
<%@page import="com.daily.average.service.model.InputQuarterlyInterval"%>
<%@page import="com.daily.average.service.service.InputQuarterlyIntervalLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.model.InputQuarterlyInterval"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%-- <%@ include file="/init.jsp" %> --%>

<% 
//getting inputQuarterlyInterval from asset render. Type casting the object to inputQuarterlyInterval.
long reportUploadLogId=ParamUtil.getLong(request, "reportUploadLogId");
 //String reportLogId=(String)request.getAttribute("reportUploadLogId");
 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 ScrutinyInputQuarterlyInterval scrutinyInputQuarterlyInterval=null;
 //out.println("reportUploadLogId "+reportUploadLogId);
InputQuarterlyInterval inputQuarterlyInterval=null;
try{
 	inputQuarterlyInterval = InputQuarterlyIntervalLocalServiceUtil.fetchInputQuarterlyInterval(reportUploadLogId);
}catch(Exception e){
	inputQuarterlyInterval=InputQuarterlyIntervalLocalServiceUtil.createInputQuarterlyInterval(0);
}try{
	
	 List<ScrutinyInputQuarterlyInterval>inputQuarterlyIntervals = ScrutinyInputQuarterlyIntervalLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
	 scrutinyInputQuarterlyInterval=inputQuarterlyIntervals.get(inputQuarterlyIntervals.size()-1);
}catch(Exception e){
	scrutinyInputQuarterlyInterval=ScrutinyInputQuarterlyIntervalLocalServiceUtil.createScrutinyInputQuarterlyInterval(0);
}
//out.println("inputQuarterlyInterval.getReportDate() :   "+inputQuarterlyInterval.getReportDate());
/* String dDate[]=dateFormat.format(inputQuarterlyInterval.getReportDate()).split("-");
int month=Integer.parseInt(dDate[1]);
int year=Integer.parseInt(dDate[0]);

if(month==1){
	year=year-1;
	month=12;
}else{
	month=month-1;	
}
String formDate1=month+"/"+year; */

String formDate1="0";

%>

<%-- <h1><%=reportuploadlogId %></h1>
<h4>${reportUploadLogId }</h4>
<h1><%=inputQuarterlyInterval %></h1> --%>

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
   
    <div class="row mb-3">
        <div class="col-12">
           <div class="text-right">
                <p  class="back_btn"><a class="backbtn" href="/web/guest/dashboard?p_p_id=com_nps_dashboard_web_NPSDashboardWebPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath=%2Fhtml%2Fpfm_screens%2Fapproved_forms.jsp#sddPFM"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
     
   <div class="row"  id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <div class="">
               <div class="content">
                  <h4>Quaterly Compliance Certificate</h4>
                <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="companies">Company Name : </label>
                     <span><%=inputQuarterlyInterval.getCompanies() %></span>
                     <%-- <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>"> --%>
                  </div>
               </div>
               <br>
                     <div class="statement-wrapper">
                        <input type="hidden" class="rowcount_one" name="rowcount_one">
                        <input type="hidden" class="rowcount_two" name="rowcount_two">
                        <input type="hidden" class="rowcount_three" name="rowcount_three">
                        <input type="hidden" class="rowcount_four" name="rowcount_four">
                        <input type="hidden" class="rowcount_five" name="rowcount_five">
                        <input type="hidden" class="rowcount_six" name="rowcount_six">
                        <input type="hidden" class="rowcount_seven" name="rowcount_seven">
                        <div class="row">
                           <div class="col">
                              <!-- <div class="form-group"> -->
                              	<input type="hidden" name="dlfileid">
								<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }">
								<%-- <input type="hidden" value="${reportMasterId }" name="reportMasterId" class="reportMasterId"> --%>
		                        <div class="row">
									
									<div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="">
												<label for="name" class="pl-2">Report Due Date :</label>
												 <span><%=dateFormat.format(inputQuarterlyInterval.getReportDate()) %></span>
												<%--  <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>dueDate' value="<%=dateFormat.format(inputQuarterlyInterval.getReportDate()) %>"> --%>
												<%-- <input class="reportDate" type="text" value="<%=dateFormat.format(inputQuarterlyInterval.getReportDate()) %>" name="reportDate" readonly="readonly"> --%>
										</div>
									</div>
								</div>
								
								<%-- 	<div class="col-lg-6 col-md-6 col-sm-12 col-12">
					                    <div class="nps-input-box mt-0">
					                        <label class="pl-3">For the Quarter Ended</label>
					           
					                          <input type="text" class="rounded border-0 p-1 ml-3" value="<%=formDate1 %>" id="formDate1" name='formDate'>
					                    </div>
				                	</div> --%>
								</div>
								
	
                           
                              <!-- </div> -->
                           </div>
                        </div>
                        <hr/>
                        
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
                        <h5>1. Management ,Sponser, Net-worth</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1ia">
                                 <p>a) Whether composition of the Board, Investment committee and Risk Management committeeis as per PFRDA (Pension Fund) regulations?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getOneia() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneia_rem_intermediary"  name="oneia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneia_remark():"" %></textarea>
		                        </div>
		                    </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneia_rem"  name="oneia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1ib">
                                 <p>b) Whether Principal officer and key personnel have been appointed as per PFRDA (Pension Fund) regulations.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneib() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneib_rem_intermediary"  name="oneib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneib_rem"  name="oneib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iia">
                                 <p>a) Whether bio-data of all the directors along with their interest in other companies has been filed with the NPS Trust within 15 days of their appointment?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneiia() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiia_rem_intermediary"  name="oneiia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiia_rem"  name="oneiia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iib">
                                 <p>b) Whether subsequent change in the interest of the directors havebeen filed with the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneiib() %></span>
                           </div>
                            <div class="col-md-2">
	                        <div class="form-group">
	                        	<textarea class="form-control" id="oneiib_rem_intermediary" name="oneiib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiib_remark():"" %></textarea>
	                        </div>
	                       </div>
                           <div class="col-md-2">
	                        <div class="form-group">
	                        	<textarea class="form-control" id="oneiib_rem" name="oneiib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiib_rem():"" %></textarea>
	                        </div>
	                       </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iic">
                                 <p>c) Whether minimum 51% of the directors have adequate professional experience in finance and financial services related fields.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneiic() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiic_rem_intermediary"  name="oneiic_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiic_remark():"" %></textarea>
		                        </div>
		                    </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiic_rem"  name="oneiic_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiic_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iid">
                                 <p>d) Whether change in key personnel has been intimated to the PFRDA within 15days of the occurrence of such change.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneiid() %></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiid_rem_intermediary"  name="oneiid_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiid_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiid_rem"  name="oneiid_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiid_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iiia">
                                 <p>a) Whether the Sponsor and the Pension Fund(PFM) fulfill and comply with the eligibility requirements as specified under the PFRDA (Pension Fund) Regulations?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneiiia() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiia_rem_intermediary_intermediary"  name="oneiiia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiiia_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiia_rem"  name="oneiiia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiiia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iiib">
                                 <p>b) Whether there is any material change in the information or particulars previously furnished which have a bearing onPFMs certificate of registration?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-check form-check-inline">
                               <span><%=inputQuarterlyInterval.getOneiiib() %></span>
                           </div>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiib_rem_intermediary"  name="oneiiib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiiib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiiib_rem"  name="oneiiib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiiib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iv)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1iva">
                                 <p>a) Whether there is any major change in the management or ownership or shareholding pattern or any change in controlling interest of the Sponsor?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOneiva() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiva_rem_intermediary"  name="oneiva_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOneiva_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="oneiva_rem"  name="oneiva_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOneiva_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(v)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1va">
                                 <p> Whether PFM is maintaining minimum Tangible Net-worth as stipulated by PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getOnev() %></span>
                           </div>
                            <div class="col-md-2">
	                       		<div class="form-group">
	                       			<textarea class="form-control" id="onev_rem_intermediary" name="onev_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getOnev_remark():"" %></textarea>
	                       		</div>
	                  	 </div>
                           <div class="col-md-2">
	                       		<div class="form-group">
	                       			<textarea class="form-control" id="onev_rem" name="onev_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getOnev_rem():"" %></textarea>
	                       		</div>
	                  	 </div>
                        </div>
                        <hr/>
                        <h5>2. Investment Policy</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2ia">
                                 <p>a) Whether Investment Policyhasbeen drawn in accordance with the investment guidelines approved by the PFRDA and has been approved by the Board of Directors (BOD) of the PFM?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getTwoia() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoia_rem_intermediary" name="twoia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoia_rem" name="twoia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2ib">
                                 <p>b) Whether Investment Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getTwoib() %></span>
                           </div>
                           <div class="col-md-2">
                       			<div class="form-group">
                       				<textarea class="form-control" id="twoib_rem_intermediary" name="twoib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoib_remark():"" %></textarea>
                       			</div>
                  		 	</div>
                            <div class="col-md-2">
                       			<div class="form-group">
                       				<textarea class="form-control" id="twoib_rem" name="twoib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoib_rem():"" %></textarea>
                       			</div>
                  		 	</div>
                        </div>
                        <div class="row pb-3"></div>
                        <div class="row pt-3"></div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5 pt-3">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2ic">
                                 <p>c) Whether the PFM has submitted details of the Investment Policy reviewed by its board to the NPS Trust within 30days of such review.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getTwoic() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoic_rem_intermediary" name="twoic_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoic_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twoic_rem" name="twoic_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoic_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row pt-3"></div>
                        <div class="row">
                           <div class="col-md-2">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-7">
                              <div class="form-group">
                                 <p>Please provide the following:</p>
                                 <div class="row">
                                    <div class="col-sm-1"></div>
                                    <div class="col-sm-11">
                                       <p>i) Initial BOD approval date</p>
                                       <p>ii) Last BOD review date </p>
                                       <p>iii) Frequency of review in a year as decided by the BOD</p>
                                    </div>
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-2"></div>
                        </div>
                        <hr/>
                        <div class="row">
                        	<div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group"><p>(ii)</p>
	                              </div>
	                           </div>
	                           <div class="col-md-8 pt-3">
	                              <div class="form-group">
	                              
	                                 <p>Please mention (individually), whether Investment Policy covers the following:</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                           </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5">
	                              <div class="form-group">
	                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iia">
	                                 <p>i) Prudential norms, Income recognition, Asset classification and Provisioning</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <span><%=inputQuarterlyInterval.getTwoiione() %></span>
	                           </div>
	                           <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiione_rem_intermediary" name="twoiione_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiione_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiione_rem" name="twoiione_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiione_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5 pt-3">
	                              <div class="form-group">
	                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iib">
	                                 <p>ii) Sector limits as stipulated in the Investment guidelines </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                             <span><%=inputQuarterlyInterval.getTwoiitwo() %></span>
	                           </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiitwo_rem_intermediary" name="twoiitwo_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiitwo_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiitwo_rem" name="twoiitwo_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiitwo_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5 pt-3">
	                              <div class="form-group">
	                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iic">
	                                 <p>iii) Sponsor and Non-Sponsor Group limits as stipulated in the Investment guidelines</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <span><%=inputQuarterlyInterval.getTwoiithree() %></span>
	                           </div>
	                           <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiithree_rem_intermediary" name="twoiithree_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiithree_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiithree_rem" name="twoiithree_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiithree_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5 pt-3">
	                              <div class="form-group">
	                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iid">
	                                 <p>iv) Liquidity and Asset/liability management </p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <span><%=inputQuarterlyInterval.getTwoiifour() %></span>
	                           </div>
	                           <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifour_rem_intermediary" name="twoiifour_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiifour_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifour_rem" name="twoiifour_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiifour_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5 pt-3">
	                              <div class="form-group">
	                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iie">
	                                 <p>v) Stop Loss Limits</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <span><%=inputQuarterlyInterval.getTwoiifive() %></span>
	                           </div>
	                           <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifive_rem_intermediary" name="twoiifive_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiifive_remark():"" %></textarea>
			                        </div>
			                    </div>
			                    <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiifive_rem" name="twoiifive_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiifive_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5 pt-3">
	                              <div class="form-group">
	                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iif">
	                                 <p>vi)Broker limit</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <span><%=inputQuarterlyInterval.getTwoiisix() %></span>
	                           </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiisix_rem_intermediary" name="twoiisix_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiisix_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiisix_rem" name="twoiisix_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiisix_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
	                        <div class="row">
	                           <div class="col-md-1">
	                              <div class="form-group">
	                              </div>
	                           </div>
	                           <div class="col-md-5 pt-3">
	                              <div class="form-group">
	                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2iig">
	                                 <p>vii) Investment audits</p>
	                              </div>
	                           </div>
	                           <div class="col-md-2">
	                              <span><%=inputQuarterlyInterval.getTwoiiseven() %></span>
	                           </div>
	                           <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiiseven_rem_intermediary" name="twoiiseven_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwoiiseven_remark():"" %></textarea>
			                        </div>
			                    </div>
	                            <div class="col-md-2">
			                        <div class="form-group">
			                        	<textarea class="form-control" id="twoiiseven_rem" name="twoiiseven_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwoiiseven_rem():"" %></textarea>
			                        </div>
			                    </div>
	                        </div>
                           </div>
                        </div>
                        <hr/>
                        <h5>3. Risk Management Policy</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3ia">
                                 <p>a) Whether Risk Management Policy hasbeen drawn in accordance with the guidelines approved by the PFRDA and has been approved by the Board of Directors?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getThreea() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threea_rem_intermediary" name="threea_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreea_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threea_rem" name="threea_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreea_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3ib">
                                 <p>b) Whether Risk Management Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
                              </div>
                           </div>
                          <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getThreeb() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeb_rem_intermediary" name="threeb_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeb_rem" name="threeb_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row pb-3"></div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3ic">
                                 <p>c) Whether the PFM has submitted details of the Risk Management Policy reviewed by its board to the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getThreec() %></span>
                           </div>
                          <div class="col-md-2">
                        		<div class="form-group">
                        			<textarea class="form-control" id="threec_rem_intermediary" name="threec_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreec_remark():"" %></textarea>
                       	 		</div>
                    		</div>
                            <div class="col-md-2">
                        		<div class="form-group">
                        			<textarea class="form-control" id="threec_rem" name="threec_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreec_rem():"" %></textarea>
                       	 		</div>
                    		</div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-8">
                              <div class="form-group">
                              
                                 <p>Please mention (individually), whether Risk Management policy covers the following:</p>
                              </div>
                              <div class="col-md-2"></div>
                           </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iia">
                                 <p>1. Risk Management functions</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 1.&nbsp;
                                 <span><%=inputQuarterlyInterval.getThreeci() %></span>
                              </div>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeci_rem_intermediary" name="threeci_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeci_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeci_rem" name="threeci_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeci_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iib">
                                 <p>2. Disaster recovery and business continuity plans</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 2.&nbsp;
                                 <span><%=inputQuarterlyInterval.getThreecii() %></span>
                              </div>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threecii_rem_intermediary" name="threecii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreecii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threecii_rem" name="threecii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreecii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iic">
                                 <p>3. Insurance cover against risks</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 3.&nbsp;
                                 <span><%=inputQuarterlyInterval.getThreeciii() %></span>
                              </div>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciii_rem_intermediary" name="threeciii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeciii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciii_rem" name="threeciii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeciii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group"></div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3iid">
                                 <p>4. Ensuring risk adjusted returns to subscribers consistent with the protection, safety and liquidity of such funds.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="number-align">
                                 4.&nbsp;
                                 <span><%=inputQuarterlyInterval.getThreeciv() %></span>
                              </div>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciv_rem_intermediary" name="threeciv_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getThreeciv_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="threeciv_rem" name="threeciv_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getThreeciv_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>4. Reporting of Investment Deviations</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4a">
                                 <p>a) Whether the PFM has ensured that all investments are made as per the investment guidelines?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getFoura() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="foura_rem_intermediary" name="foura_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFoura_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="foura_rem" name="foura_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFoura_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4b">
                                 <p>b) In case of a deviation (downgrade to a rating not permitted under the regulations for corporate bonds or any other non-compliance in any scheme/asset class post investment), whether the PFM has recorded an internal note justifying its decision to hold such securities where deviation has occurred. </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getFourb() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourb_rem_intermediary" name="fourb_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFourb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourb_rem" name="fourb_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFourb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4c">
                                 <p>c) Whether all such investment deviations are being reported to the Investment Committee and Board of the PFM for their approval to continue to remain invested in these securities.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getFourc() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourc_rem_intermediary" name="fourc_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFourc_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fourc_rem" name="fourc_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFourc_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-9">
                              <div class="form-group">
                                 <p>(Please provide details of the deviations that occurred in the quarter in Annexure 2 along with details of justification note and Investment Committee & Board approval extracts)</p>
                              </div>
                           </div>
                           <div class="col-md-2"></div>
                        </div>
                        <hr/>
                        <h5>5. Code of Conduct</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5ia">
                                 <p>Whether the PFM is engaged in any other business activity except those relating to pension schemes or funds, regulated by the PFRDA.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getFivei() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivei_rem_intermediary" name="fivei_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivei_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivei_rem" name="fivei_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivei_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5ib">
                                 <p>Whether the PFM has ensured that adequate disclosures are made to the PFRDA, the NPS Trust or subscriber in a comprehensible and timely manner.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getFiveii() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveii_rem_intermediary" name="fiveii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveii_rem" name="fiveii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5ic">
                                 <p>Whether the PFM has ensured that there has not been any misrepresentation or submission of any misleading information to the PFRDA, NPS Trust or subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                             <span><%=inputQuarterlyInterval.getFiveiii() %></span>  
	
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiii_rem_intermediary" name="fiveiii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveiii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiii_rem" name="fiveiii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveiii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iv)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5id">
                                 <p>Whether the PFM has divulged to anybody, either orally or in writing, directly or indirectly any confidential information about the PFRDA, the NPS Trust or subscribers, which has come to its knowledge, without taking prior permission of the PFRDA,the NPS Trust except where such disclosures are required to be made in compliance with any law for the time being in force.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getFiveiv() %></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiv_rem_intermediary" name="fiveiv_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveiv_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveiv_rem" name="fiveiv_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveiv_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(v)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iia">
                                 <p>a) Whether the PFM has made adequate disclosures of potential areas of conflict of duties or interest to the PFRDA, the NPS Trust or subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getFiveva() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveva_rem_intermediary" name="fiveva_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFiveva_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fiveva_rem" name="fiveva_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFiveva_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iib">
                                 <p>b) Whether the PFM hasestablished a mechanism to resolve any conflict of interest situation in an equitable manner, which may arise in the conduct of business.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getFivevb() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevb_rem_intermediary" name="fivevb_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevb_rem" name="fivevb_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iic">
                                 <p>c) Whether the PFM is satisfied that there has been no instances of self-dealing or front running or insider trading by any of the directors and Key personnel through their accounts or through their family members, relatives or friends. </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getFivevc() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevc_rem_intermediary" name="fivevc_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevc_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevc_rem" name="fivevc_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevc_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-8">
                              <div class="form-group">
                                 <p>Kindly provide list of directors and key personnel with status of submissions of self-declarations in Annexure 3.</p>
                              </div>
                           </div>
                           <div class="col-md-2"></div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(vi)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iiia">
                                 <p>a) Whether PFM has promptly informed the PFRDA and the NPS Trust, if there is any change in the registration status or any penal action taken by any Authority or any material change in financials which may adversely affect the interest of the subscribers.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getFivevia() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevia_rem_intermediary" name="fivevia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevia_rem" name="fivevia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="5iiib">
                                 <p>b) Whether the PFM has promptly informed the PFRDA and the NPS Trust about any action, legal proceedings initiated against it in respect of any material breach or non-compliance by it of any law, rules, regulations and directions of the PFRDA or any other regulatory body.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getFivevib() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevib_rem_intermediary" name="fivevib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getFivevib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="fivevib_rem" name="fivevib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getFivevib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>6. Internal Auditors</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6ia">
                                 <p>Whether appointment of the Internal Auditor and scope of internal audit is as per the Regulations/Guidance note issued by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getSixi() %></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixi_rem_intermediary" name="sixi_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixi_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixi_rem" name="sixi_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixi_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6iia">
                                 <p>a) Whether the PFM has produced to the Auditors such books, accounts, records and other documents in its custody or control and furnish such statement and information relating to its activities entrusted to its by the PFRDA, as it or he may require, within such reasonable time may be specified.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getSixiia() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiia_rem_intermediary" name="sixiia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiia_rem" name="sixiia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixiia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6iib">
                                 <p>b) Whether the PFM has allowed Auditor's reasonable access to the premises occupied by it and also extend reasonable facility for examining any books, records, documents and computer data in the possession of the PFM.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getSixiib() %></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiib_rem_intermediary" name="sixiib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiib_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiib_rem" name="sixiib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixiib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="6iic">
                                 <p>c)Whether audit observations till previous quarter have been closed and suggestions of PFRDA/NPS Trust thereto have been complied with?</p>
                               </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getSixiic() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiic_rem_intermediary" name="sixiic_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSixiic_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sixiic_rem" name="sixiic_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSixiic_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>7. Related Party engagement / transaction </h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ia">
                                 <p>a) Whether any transactions or engagement have been carried out by the PFM with a related party except investments of National Pension SystemTrust funds?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getSevenia() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenia_rem_intermediary" name="sevenia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenia_rem" name="sevenia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ib">
                                 <p>b) Whether prior permission of the NPS Trust was taken before entering into such engagement/transaction?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getSevenib() %></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenib_rem_intermediary" name="sevenib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenib_rem" name="sevenib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ic">
                                 <p>c)  Whether such engagement/transactions have been disclosed to the NPS Trust in its periodic reports.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getSevenic() %></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenic_rem_intermediary" name="sevenic_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenic_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenic_rem" name="sevenic_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenic_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7id">
                                 <p>d) Whether such related party engagements / transactions aredone at fair market price?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getSevenid() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenid_rem_intermediary" name="sevenid_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenid_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenid_rem" name="sevenid_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenid_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1"></div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="7ea">
                                 <p>e) Whether such transaction is recurring in nature?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getSevenie() %></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenie_rem_intermediary" name="sevenie_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getSevenie_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="sevenie_rem" name="sevenie_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getSevenie_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>8. Operations / Data Security / Infrastructure</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(i)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8ia">
                                 <p>a)Whether the PFM has complied with circular no. PFRDA/2017/30/PF/4 dated09.10.2017 onguidelines on outsourcing of activities by the Pension Fund? </p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightia()%></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightia_rem_intermediary" name="eightia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightia_rem" name="eightia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8ib">
                                 <p>b) Whether the PFM has complied with the reporting requirements of the circular no. PFRDA/2017/30/PF/4 dated 09.10.2017.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightib()%></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightib_rem_intermediary" name="eightib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightib_rem" name="eightib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8iia">
                                 <p>Whether all investments are held in the name of NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightii()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightii_rem_intermediary" name="eightii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightii_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    
		                    <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightii_rem" name="eightii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8iiia">
                                 <p>Whether PFM has pledged or hypothecated or lent any scheme assets which would amount to leverage on schemeâs portfolio?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightiii()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiii_rem_intermediary" name="eightiii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightiii_remark():"" %></textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiii_rem" name="eightiii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightiii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(iv)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8iva">
                                 <p>Whether all charges/fees debited to the schemes aredeterminedas stipulated by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightiv()%></span>
                           </div>
                          <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiv_rem_intermediary" name="eightiv_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightiv_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightiv_rem" name="eightiv_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightiv_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(v)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8va">
                                 <p>Whether all interest,dividendsor any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightv()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightv_rem_intermediary" name="eightv_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightv_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightv_rem" name="eightv_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightv_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(vi)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8via">
                                 <p>a) Whether the PFM has taken adequate and necessary steps to ensure that the data and records pertaining to its activities are maintained and are intact.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightvia()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvia_rem_intermediary" name="eightvia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightvia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvia_rem" name="eightvia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightvia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8vib">
                                 <p>b) Whether the PFM has ensured that for electronic records and data, up-to-date backup is always available with it.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightvib()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvib_rem_intermediary" name="eightvib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightvib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightvib_rem" name="eightvib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightvib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(vii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8viia">
                                 <p>a) Whether the PFM has maintained adequate infrastructure facilities to be able to discharge its services to the satisfaction of the PFRDA, the NPS Trust.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightviia()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviia_rem_intermediary" name="eightviia_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviia_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviia_rem" name="eightviia_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightviia_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8viib">
                                 <p>b) Whether the operating procedures and systems of the PFM are well documented and backed by operation manuals.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightviib()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviib_rem_intermediary" name="eightviib_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviib_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviib_rem" name="eightviib_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightviib_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(viii)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8viiia">
                                 <p>Whether the PFM has informed the entities in which investment of NPS funds have been made that interest received on the said investment is not liable for deduction of tax at source under the Income Tax Act, 1951</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getEightviii()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem_intermediary" name="eightviii_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightviii_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem" name="eightviii_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightviii_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        
                        
                        
                        
                        
                          <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(ix)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8ix">
                                <p>In case any Income tax has been deducted on the investment of NPS funds made by PFM, whether PFM has collected refund of such tax deducted within the same financial year.</p>
                                <p>In case any Income Tax has been deducted on the investment of NPS funds and PFM has not obtained the refund of such tax within the same financial year at its own cost and expense, the PFM fund shall reimburse the NPS Trust, of the said amounts, being deducted as tax, upon being notified by the NPS Trust.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getEightix()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightix_rem_intermediary" name="eightix_rem_intermediary" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightix_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightviii_rem" name="eightix_rem" disabled ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightix_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                                 <p>(x)</p>
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="8x">
                                 <p>Whether proper amount of tax has been deducted and deposited on payment of investment management fees and the custodian fees by the PFM on behalf of NPS Trust and within the prescribed timelines.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                               <span><%=inputQuarterlyInterval.getEightx()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightx_rem_intermediary" name="eightx_rem_intermediary" ><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getEightx_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="eightx_rem" name="eightx_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getEightx_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        
                        <h5>9. Brokers empanelment:</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="9ia">
                                 <p>* Whether Brokers empanelment is done in accordance to the guidelines issued by the PFRDA?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getNinea()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ninea_rem_intermediary" name="ninea_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getNinea_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ninea_rem" name="ninea_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getNinea_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="9ib">
                                 <p>* Whether prescribed limit of percentage of transactions through any single broker is complied with?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getNineb()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="nineb_rem_intermediary" name="nineb_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getNineb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="nineb_rem" name="nineb_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getNineb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>10. Inter-Scheme Investment Parameter</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="10ia">
                                 <p>Whether all such Inter-Scheme transfers are in conformity with the investment objective of the scheme to which such transfer has been made?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getTen()%></span>
                           </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ten_rem_intermediary" name="ten_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTen_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="ten_rem" name="ten_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTen_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>11. Voting Obligation</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                               <input type="checkbox" class="pdfbox"  name="pdfbox"  value="11ia">
                                 <p>a) Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getElevena()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevena_rem_intermediary" name="elevena_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getElevena_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevena_rem" name="elevena_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getElevena_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">	
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="11ib">
                                 <p>b)Whether, quarterly voting report has been submitted to the NPS Trust in compliance to Circular PFRDA/2017/17/PF/1 dated 20.04.2017?</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getElevenb()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevenb_rem_intermediary" name="elevenb_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getElevenb_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="elevenb_rem" name="elevenb_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getElevenb_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <hr/>
                        <h5>12. Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations</h5>
                        <hr/>
                        <div class="row">
                           <div class="col-md-1">
                              <div class="form-group">
                              </div>
                           </div>
                           <div class="col-md-5">
                              <div class="form-group">
                              <input type="checkbox" class="pdfbox"  name="pdfbox"  value="12ia">
                                 <p>Whether quarterly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the quarter.</p>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <span><%=inputQuarterlyInterval.getTwelve()%></span>
                           </div>
                           <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twelve_rem_intermediary" name="twelve_rem_intermediary" disabled><%= (Validator.isNotNull(inputQuarterlyInterval))? inputQuarterlyInterval.getTwelve_remark():"" %></textarea>
		                        </div>
		                    </div>
                            <div class="col-md-2">
		                        <div class="form-group">
		                        	<textarea class="form-control" id="twelve_rem" name="twelve_rem" disabled><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getTwelve_rem():"" %></textarea>
		                        </div>
		                    </div>
                        </div>
                        <!-- <hr/> -->
								<%-- <div class="row pt-3">
				   
					                  <div class="col-lg-6 col-md-6 col-sm-12 col-12 pt-4">
					                  	<div class="form-group">
				                        	<textarea class="form-control" id="annex_comp_certificate_rem" placeholder="Remarks if any" name="annex_comp_certificate_rem" ><%= (Validator.isNotNull(scrutinyInputQuarterlyInterval))? scrutinyInputQuarterlyInterval.getAnnex_comp_certificate_rem():"" %></textarea>
				                        </div>
					                  </div>
				                 </div> --%>
                             
                              <br>	
    
                        <div class="row" id="sub">
                           <div class="col-md-12">
                              <div class="text-center">
                                 <button class="btn btn-primary" id="pdfButton" onClick="downloadPDF()">PDF  </button>
                              </div>
                           </div>
                        </div>
                                            
		               <br>
                     </div>

               </div>
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

<%-- <portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.FORMS_PDF_CREATION_RESOURCE_COMMAND%>" var="formsPDFCreationsResourceURL"></portlet:resourceURL> --%>
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

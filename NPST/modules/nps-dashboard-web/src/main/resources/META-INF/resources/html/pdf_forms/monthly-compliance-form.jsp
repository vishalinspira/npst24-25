<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletURLUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@page import="com.daily.average.service.service.MnCompCertificateScrutinyLocalServiceUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.daily.average.service.model.MnCompCertificateScrutiny"%>
<%@page import="com.daily.average.service.service.MnCompCertificateLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.daily.average.service.model.MnCompCertificate"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%-- <%@ include file="/init.jsp" %>
 --%>
<% 
//getting inputQuarterlyInterval from asset render. Type casting the object to inputQuarterlyInterval.
long reportUploadLogId=ParamUtil.getLong(request, "reportUploadLogId");
 //String reportLogId=(String)request.getAttribute("reportUploadLogId");
 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 MnCompCertificateScrutiny mnCompCertificateScrutiny=null;
 //out.println("reportUploadLogId "+reportUploadLogId);
MnCompCertificate mnCompCertificate=null;
try{
	mnCompCertificate = MnCompCertificateLocalServiceUtil.fetchMnCompCertificate(reportUploadLogId);
	
}catch(Exception e){
	mnCompCertificate=MnCompCertificateLocalServiceUtil.createMnCompCertificate(0);
}try{
 List<MnCompCertificateScrutiny> mnCompCertificateScrutinys = MnCompCertificateScrutinyLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
 mnCompCertificateScrutiny=mnCompCertificateScrutinys.get(mnCompCertificateScrutinys.size()-1);
}catch(Exception e){
	mnCompCertificateScrutiny=MnCompCertificateScrutinyLocalServiceUtil.createMnCompCertificateScrutiny(0);
}

String formDate1="0";

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
   
    <div class="row mb-3">
        <div class="col-12">
           <div class="text-right">
                <p  class="back_btn"><a class="backbtn" href="/web/guest/dashboard?p_p_id=com_nps_dashboard_web_NPSDashboardWebPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath=%2Fhtml%2Fpfm_screens%2Fapproved_forms.jsp#sddPFM"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
           </div>
       </div>
     </div>
     <h4 class="text-center">Monthly Compliance Certificate</h4>
      <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="companies">Company Name : </label>
                      <span><%=mnCompCertificate.getCompany_name() %></span>
                    <%--  <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>"> --%>
                  </div>
               </div>
               <br>
                <div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-12">
											<div class="">
												<label for="name" class="pl-2">Report Due Date : </label>
												<span><%=dateFormat.format(mnCompCertificate.getReportDate()) %></span>
												 <%-- <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>dueDate' value="<%=dateFormat.format(mnCompCertificate.getReportDate()) %>"> --%>
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
  <div class="form_block mx-4">
        	
        		<input type="hidden" name="reportUploadLogId" class="reportUploadLogId" value="<%=mnCompCertificate.getReportUploadLogId()%>"/>
				<%-- <input type="hidden"value="<%=mnCompCertificate.getReportMasterId()%>" name="reportMasterId" class="reportMasterId"/>--%>
				<hr style="margin-top:0px"/>
        	    <div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>1. Purchase of securities</strong></p>
        			</div>
        		</div>
        		<hr style="margin-top:0px"/>
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                        <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.1a">
                            <p><strong>1.1</strong>  Whether purchase of securities adhere to the Investment guidelines issued by PFRDA. 
							(prescribed securities/ percentage/ limit/ prudential & exposure norms) Details of deviations provided in annexure A (i).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
	                   <span><%=mnCompCertificate.getPurchase_of_securities()%></span>
                  </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                       		<textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_1_2"> <%=mnCompCertificate.getPurchase_of_sec_rem_1_1_2() ==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_1_2() %> </textarea>
                        	
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_1" ><%=mnCompCertificateScrutiny.getPurchase_of_securities_rem() == null ? "" :  mnCompCertificateScrutiny.getPurchase_of_securities_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		 <div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.2a">
                            <p><strong>1.2 (a)</strong> Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getDetailed_investment()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2a_2"   ><%=mnCompCertificate.getPurchase_of_sec_rem_1_2a_2() ==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_2a_2() %></textarea>
                        	
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2a" ><%=mnCompCertificateScrutiny.getDetailed_investment_rem() == null ? "" :  mnCompCertificateScrutiny.getDetailed_investment_rem()%></textarea>
                        </div>
                    </div>
        		 </div><!-- row one end -->
        		 <hr style="margin-top:0px"/>
        		
        		 <div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.2b">
                            <p><strong>(b)</strong> Whether investments are approved by the committee/competent authority as per Approval delegation matrix</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getInvestments_approved()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class=" form-check-inline form-group">
                       <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2b_2"   ><%=mnCompCertificate.getPurchase_of_sec_rem_1_2b_2() ==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_2b_2() %></textarea>
                        	
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2b" ><%=mnCompCertificateScrutiny.getInvestments_approved_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestments_approved_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.2c">
                            <p><strong>(c)</strong> Whether each decision of investment is properly documented and record is maintained at individual transaction level.
                            (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getDecision_of_investment()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                       <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2c_2"   ><%=mnCompCertificate.getPurchase_of_sec_rem_1_2c_2() ==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_2c_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2c" ><%=mnCompCertificateScrutiny.getDecision_of_investment_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_of_investment_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.2d">
                            <p><strong>(d)</strong> Whether investments for non-dematerialized securities are supported by physical certificates</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getInvestments_non_dematerialized()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class=" form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2d_2"   ><%=mnCompCertificate.getPurchase_of_sec_rem_1_2d_2()==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_2d_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2d" ><%=mnCompCertificateScrutiny.getInvest_non_dematerialized_rem() == null ? "" :  mnCompCertificateScrutiny.getInvest_non_dematerialized_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                        <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.2e">
                            <p><strong>(e)</strong> Whether all investments from funds received under NPS schemes are held in the name of NPS Trust.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getAll_investments_from_funds()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                       <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2e_2"   ><%=mnCompCertificate.getPurchase_of_sec_rem_1_2e_2() ==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_2e_2()  %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2e" ><%=mnCompCertificateScrutiny.getAll_investments_from_funds_rem() == null ? "" :  mnCompCertificateScrutiny.getAll_investments_from_funds_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		<!-- 1.1 over -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.3a">
                            <p><strong>1.3</strong> Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                       <span><%=mnCompCertificate.getDelivery_of_securities_purch()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_3_2"   ><%=mnCompCertificate.getPurchase_of_sec_rem_1_3_2() ==null ?"":mnCompCertificate.getPurchase_of_sec_rem_1_3_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_3" ><%=mnCompCertificateScrutiny.getDelivery_of_security_purch_rem() == null ? "" :  mnCompCertificateScrutiny.getDelivery_of_security_purch_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                        <input type="checkbox" class="pdfbox"  name="pdfbox"  value="1.4a">
                            <p><strong>1.4</strong> Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period? (As per PFRDA circular no. PFRDA/2021/32/REG-PF/4 dated 27.07.2021, such investments to be reported to NPS Trust within 30 days of making such investments)
							Details of Investments provided in Annexure B.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getInvestment_done_in_ipo()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_4_2"   ><%=mnCompCertificate.getPurchase_of_secu_rem_1_4_2() ==null ?"":mnCompCertificate.getPurchase_of_secu_rem_1_4_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_4" ><%=mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>2. Securities held</strong></p>
        			</div>
        		</div>
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.1a">
                            <p><strong>2.1</strong>  Whether scheme investments adhere to the Investment guidelines issued by PFRDA. 
							(prescribed securities/ percentage/ limit/ prudential & exposure norms)
							Details of deviations provided in annexure A (ii).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getScheme_investments()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_1_2"   ><%=mnCompCertificate.getSecurities_held_2_1_2() ==null ?"":mnCompCertificate.getSecurities_held_2_1_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_1" ><%=mnCompCertificateScrutiny.getScheme_investments_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_investments_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.2a"> 
                            <p><strong>2.2 (a)</strong> Whether stop loss trigger has occurred for any security during the month as per Investment policy of the PFM. </p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getStop_loss_trigger()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                         <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_2a_2"   ><%=mnCompCertificate.getSecurities_held_2_2a_2() ==null ?"":mnCompCertificate.getSecurities_held_2_2a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_2a" ><%=mnCompCertificateScrutiny.getStop_loss_trigger_rem() == null ? "" :  mnCompCertificateScrutiny.getStop_loss_trigger_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.2b"> 
                            <p><strong>(b)</strong> Whether decision in such scenario is approved by the committee/competent authority as per Approval delegation matrix.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getDecision_approved_by_committee()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_2b_2"   ><%=mnCompCertificate.getSecurities_held_2_2b_2() ==null ?"":mnCompCertificate.getSecurities_held_2_2b_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_2b" ><%=mnCompCertificateScrutiny.getDecision_appr_by_committee_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_appr_by_committee_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.2c"> 
                            <p><strong>(c)</strong> Whether each decision along with rationale is properly documented and record is maintained at individual scrip level.
							(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)
							Details of stop loss occurred during the month and its decision provided in Annexure C.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getDecision_properly_documented()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_2c_2"   ><%=mnCompCertificate.getSecurities_held_2_2c_2() ==null ?"":mnCompCertificate.getSecurities_held_2_2c_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_2c" ><%=mnCompCertificateScrutiny.getDecision_prop_documented_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_prop_documented_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<!-- 2.1 over abc -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.3a"> 
                            <p><strong>2.3</strong>  Whether inter-scheme transfer of securities complies with point 14 of the Investment Guideline circular number PFRDA/2021/29/REG-PF/3 dated 20.07.2021.
							Details of inter scheme transfer provided in Annexure D.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getInter_scheme_transfer()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                         <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_3_2"   ><%=mnCompCertificate.getSecurities_held_2_3_2() ==null ?"":mnCompCertificate.getSecurities_held_2_3_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_3" ><%=mnCompCertificateScrutiny.getInter_scheme_transfer_rem() == null ? "" :  mnCompCertificateScrutiny.getInter_scheme_transfer_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.4a"> 
                            <p><strong>2.4</strong>  Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing. 
							Details provided in Annexure E</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getInvestment_held_in_equity()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                       <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_4_2"   ><%=mnCompCertificate.getSecurities_held_2_4_2() ==null ?"":mnCompCertificate.getSecurities_held_2_4_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_4" ><%=mnCompCertificateScrutiny.getInvestment_held_in_equity_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestment_held_in_equity_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                          <input type="checkbox" class="pdfbox"  name="pdfbox"  value="2.5a"> 
                            <p><strong>2.5</strong>  Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing. 
							Details provided in Annexure F.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getInvestment_in_equity_shares()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class=" form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Securities_held_2_5_2"   ><%=mnCompCertificate.getSecurities_held_2_5_2() ==null ?"":mnCompCertificate.getSecurities_held_2_5_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Securities_held_2_5" ><%=mnCompCertificateScrutiny.getInvest_in_equity_shares_rem() == null ? "" :  mnCompCertificateScrutiny.getInvest_in_equity_shares_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>3. Sale of securities</strong></p>
        			</div>
        		</div>
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3.1a"> 
                            <p><strong>3.1 (a)</strong> Whether disinvestments made are approved by the committee/competent authority as per delegation matrix.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getDisinvestments_approved()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                         <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Sale_of_securities_3_1a_2"   ><%=mnCompCertificate.getSale_of_securities_3_1a_2() ==null ?"":mnCompCertificate.getSale_of_securities_3_1a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Sale_of_securities_3_1a" ><%=mnCompCertificateScrutiny.getDisinvestments_approved_rem() == null ? "" :  mnCompCertificateScrutiny.getDisinvestments_approved_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-check-inline form-group">
                        <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3.1b"> 
                            <p><strong>(b)</strong> Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.
							(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <span><%=mnCompCertificate.getDecision_of_disinvestment()%></span>
                    </div>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Sale_of_securities_3_1b_2"   ><%=mnCompCertificate.getSale_of_securities_3_1b_2() ==null ?"":mnCompCertificate.getSale_of_securities_3_1b_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Sale_of_securities_3_1b" ><%=mnCompCertificateScrutiny.getDecision_of_disinvestment_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_of_disinvestment_rem()%></textarea>
                        </div>
                    </div>
                    </div>
                    <hr style="margin-top:0px"/>
        		<!-- row one end -->
        		
        		<!--3.1 over abc  -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="3.2a"> 
                            <p><strong>3.2</strong>  Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getDelivery_of_securities_sale()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Sale_of_securities_3_2_2"   ><%=mnCompCertificate.getSale_of_securities_3_2_2() ==null ?"":mnCompCertificate.getSale_of_securities_3_2_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Sale_of_securities_3_2" ><%=mnCompCertificateScrutiny.getDelivery_of_security_sale_rem() == null ? "" :  mnCompCertificateScrutiny.getDelivery_of_security_sale_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>4. Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations</strong></p>
        			</div>
        		</div>
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.1a"> 
                            <p><strong>4.1 (a)</strong> Whether all investment charges (Investment management fees, Custodian charges, SEBI charges, NPS Trust fees etc.) are loaded onto the NAV on a daily basis.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getAll_investment_charges()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                       <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1a_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_1a_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_1a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1a" ><%=mnCompCertificateScrutiny.getAll_investment_charges_rem() == null ? "" :  mnCompCertificateScrutiny.getAll_investment_charges_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.1b"> 
                            <p><strong>(b)</strong> Whether PFM has adhered to instructions of PFRDA to get the NAV verified by the auditors on a daily basis.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <span><%=mnCompCertificate.getPfm_adhered()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                         <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1b_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_1b_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_1b_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1b" ><%=mnCompCertificateScrutiny.getPfm_adhered_rem() == null ? "" :  mnCompCertificateScrutiny.getPfm_adhered_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.1c"> 
                            <p><strong>(c)</strong> Whether the records of the audit of NAV have been maintained by the pension fund for future inspection. </p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getRecords_of_the_audit_of_nav()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1c_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_1c_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_1c_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1c" ><%=mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem() == null ? "" :  mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.1d"> 
                            <p><strong>(d)</strong> Whether scheme-wise NAV (latest & historical) is uploaded daily on the PFM's website within the prescribed time limit.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                          <span><%=mnCompCertificate.getScheme_wise_nav_uploaded()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1d_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_1d_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_1d_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1d" ><%=mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.1e"> 
                            <p><strong>(e)</strong> Whether scheme-wise NAV is submitted daily to all the CRA's within the prescribed time limit.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                         <span><%=mnCompCertificate.getScheme_wise_nav_submitted()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                        <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1e_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_1e_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_1e_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_1e" ><%=mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<!--4.1 over abc  -->
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.2a"> 
                            <p><strong>4.2 (a)</strong> Whether monthly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                       <span><%=mnCompCertificate.getMonthly_reports_submitted()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline form-group">
                         <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_2a_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_2a_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_2a_2() %></textarea>
                        
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="Reports_and_Disclosures_4_2a" ><%=mnCompCertificateScrutiny.getMonthly_reports_submitted_rem() == null ? "" :  mnCompCertificateScrutiny.getMonthly_reports_submitted_rem()%></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		<hr style="margin-top:0px"/>
        		
        		<div class="row">
        			<div class="col-md-5">
                        <div class="form-group">
                         <input type="checkbox" class="pdfbox"  name="pdfbox"  value="4.2b">
                            <p><strong>(b)</strong> Whether script wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                       <span><%=mnCompCertificate.getScrip_wise_details()%></span>
                    </div>

                    <div class="col-md-4">
                        <div class="form-check-inline  form-group">
                         <textarea class="mr-3 form-control"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_2b_2"   ><%=mnCompCertificate.getReports_and_Disclosure_4_2b_2() ==null ?"":mnCompCertificate.getReports_and_Disclosure_4_2b_2() %></textarea>
                        
                        	<textarea class="form-control" name='Reports_and_Disclosures_4_2b' id="remarks_1_1" placeholder="Remarks if any" ><%=mnCompCertificateScrutiny.getScrip_wise_details_rem() == null ? "" :  mnCompCertificateScrutiny.getScrip_wise_details_rem()%></textarea>
                        </div>
                    </div>
        		</div>                             
                              
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

<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daily.average.service.model.ReportUploadFileLog"%>
<%@page import="java.util.List"%>
<%@page import="nps.common.service.util.PreviewFileURLUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="nps.common.service.constants.NPSTRoleConstants"%>
<%@page import="com.liferay.portal.kernel.service.RoleServiceUtil"%>
<%@page import="com.daily.average.service.service.MnCompCertificateLocalServiceUtil"%>
<%@page import="com.daily.average.service.model.MnCompCertificate"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.Validator"%>

<% 
   MnCompCertificate mnCompCertificatedetails = Validator.isNotNull(request.getAttribute("mnCompCertificate")) ? (MnCompCertificate) request.getAttribute("mnCompCertificate") : MnCompCertificateLocalServiceUtil.createMnCompCertificate(0);
	
   List<ReportUploadFileLog> reportUploadFileLogs = new ArrayList<ReportUploadFileLog>();
	reportUploadFileLogs = (List<ReportUploadFileLog>) request.getAttribute("reportUploadFileLogs");
	
   boolean isPfmSupervisor = RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), NPSTRoleConstants.PFM_SUPERVISOR, false);
	boolean isDocSigned = false;
	
	JSONArray urlArray = PreviewFileURLUtil.getPreviewFileURL(themeDisplay, reportUploadFileLogs);
	String fileEntryId = "0";
%>

<div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Monthly Compliance Certificate 
                </div>
            </div>
        </div>
   <div>
	<span>File Version : </span>
	<% 
	for(int i=0; i<urlArray.length();i++){
		JSONObject object = urlArray.getJSONObject(i);
		isDocSigned = Validator.isNotNull(object.getString("signature")); 
		fileEntryId = object.getString("fileEntryId");
	%>
		<span class="file-preview-span" data-index='<%= i%>'><a href="<%=object.getString("downloadURL")%>"> <%=object.getString("version")%></a></span>
		<% if(i != (urlArray.length()-1)){ %>
		 ,
		<% } %>
	<% } %>
	</div>
	<% if(isPfmSupervisor){ %>
		<div class="panel-body">
			
			<% if(!isDocSigned){ %>
				<button id="signFile" class="btn  btn-primary">Sign File</button>
			<% }else{ %>
				<button id="signFile" class="btn  btn-primary" disabled="disabled">Sign File</button>
				<h6>The report has been Authenticated</h6>
			<% } %>
		</div>
	<% } %>
	<script type="text/javascript">
	var SDWebServerUrl = "https://dsc.npstrust.net";
	var SDWebServerAuthTokenByUserPortalUrl = "/o/dsc/getAuthToken";
	
	$(function(){
		console.log("doc load");
		$(document).on('click', '#signFile', function(event) {
			//e.preventDefault();
			console.log("in sigin");
			SignerDigital.getSelectedCertificate()
	         .then(function(data){
	        	 console.log(data);
				var CertInfo = JSON.parse(data);
				$("#signature").val(CertInfo.Cert);
				var fd = new FormData();
				fd.append("certificateJSON", encodeURIComponent(data));
				fd.append("fileEntryId", "<%=fileEntryId%>");
				fd.append("sDHubConnectionIdFromBrowser", sdHubConnectionId);
				fd.append("certificateFromBrowser", encodeURIComponent(CertInfo.Cert));
	
				 $.ajax({
			            url: '/o/dsc/signPDF',  
			            type: 'POST',
			            cache: false,
			            data: fd,
			            contentType: false,
			            processData: false,
			            success:function(data){
			            	console.log(data);
			            	location.reload(); 
			            },
			            error: function() {
			           		console.log("Error Occured in ajax call");
			           		
			            },
			            complete: function(){
				        }
			    });
				//$("#submit").click();
			})
		})
	})
	
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/signalr.js"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/SignerDigitalCore-1.0.1.min.js"/>
	        
        <div class="form_block mx-4">
        	<form class="form_one" id="form_comp" action="#" method="post">
        	
        	    <div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>1. Purchase of securities</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>1.1</strong>  Whether purchase of securities adhere to the Investment guidelines issued by PFRDA. 
							(prescribed securities/ percentage/ limit/ prudential & exposure norms) Details of deviations provided in annexure A (i).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
	                     <div class="form-check form-check-inline">
	                        <input type="radio" class="purchase1" id="purchase_1" name='<portlet:namespace/>purchase1' disabled
	                        <%=mnCompCertificatedetails.getPurchase_of_securities() != null && mnCompCertificatedetails.getPurchase_of_securities().equalsIgnoreCase("Yes") ? "checked" : "" %>>
	                        <label class="form-check-label">&nbsp; Yes</label>
	                     </div>
	                     <div class="form-check form-check-inline">
	                        <input type="radio" class="purchase1" id="purchase_2" name='<portlet:namespace/>purchase1' disabled
	                          <%=mnCompCertificatedetails.getPurchase_of_securities() != null && mnCompCertificatedetails.getPurchase_of_securities().equalsIgnoreCase("No") ? "checked" : "" %>>
	                        <label class="form-check-label">&nbsp; No</label>
	                     </div>
	                     <div class="form-check form-check-inline">
	                        <input type="radio" class="purchase1" id="purchase_3" name='<portlet:namespace/>purchase1' disabled
	                        <%=mnCompCertificatedetails.getPurchase_of_securities() != null && mnCompCertificatedetails.getPurchase_of_securities().equalsIgnoreCase("NA") ? "checked" : "" %>>
	                        <label class="form-check-label">&nbsp; NA</label>
	                     </div>
                  </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_1"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		 <div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>1.2 (a)</strong> Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchase1-2' id="section-1-1" value="Yes" disabled 
                               <%=mnCompCertificatedetails.getDetailed_investment() != null && mnCompCertificatedetails.getDetailed_investment().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchase1-2' id="section-1-2" value="No" disabled 
                               <%=mnCompCertificatedetails.getDetailed_investment() != null && mnCompCertificatedetails.getDetailed_investment().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchase1-2' id="section-1-3" value="NA" disabled
                               <%=mnCompCertificatedetails.getDetailed_investment() != null && mnCompCertificatedetails.getDetailed_investment().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_2a"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		 <div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether investments are approved by the committee/competent authority as per Approval delegation matrix</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchaseb' id="section-1-1" value="Yes" disabled
                               <%=mnCompCertificatedetails.getInvestments_approved() != null && mnCompCertificatedetails.getInvestments_approved().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchaseb' id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getInvestments_approved() != null && mnCompCertificatedetails.getInvestments_approved().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchaseb' id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getInvestments_approved() != null && mnCompCertificatedetails.getInvestments_approved().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_2b"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		 <div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(c)</strong> Whether each decision of investment is properly documented and record is maintained at individual transaction level.
                            (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name='<portlet:namespace/>purchasec' id="section-1-1" value="Yes" disabled 
                             <%=mnCompCertificatedetails.getDecision_of_investment() != null && mnCompCertificatedetails.getDecision_of_investment().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchasec' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getDecision_of_investment() != null && mnCompCertificatedetails.getDecision_of_investment().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchasec' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getDecision_of_investment() != null && mnCompCertificatedetails.getDecision_of_investment().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_2c"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		 <div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(d)</strong> Whether investments for non-dematerialized securities are supported by physical certificates</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchased' type="radio" id="section-1-1" value="Yes" disabled 
                            <%=mnCompCertificatedetails.getInvestments_non_dematerialized() != null && mnCompCertificatedetails.getInvestments_non_dematerialized().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchased' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getInvestments_non_dematerialized() != null && mnCompCertificatedetails.getInvestments_non_dematerialized().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchased' type="radio" id="section-1-3" value="NA" disabled 
                            <%=mnCompCertificatedetails.getInvestments_non_dematerialized() != null && mnCompCertificatedetails.getInvestments_non_dematerialized().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_2d"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		 <div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(e)</strong> Whether all investments from funds received under NPS schemes are held in the name of NPS Trust.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchasee' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getAll_investments_from_funds() != null && mnCompCertificatedetails.getAll_investments_from_funds().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchasee' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getAll_investments_from_funds() != null && mnCompCertificatedetails.getAll_investments_from_funds().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchasee' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getAll_investments_from_funds() != null && mnCompCertificatedetails.getAll_investments_from_funds().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_2e"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		<!-- 1.1 over -->
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>1.3</strong> Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase1-3'type="radio" id="section-1-1" value="Yes" disabled
                             <%=mnCompCertificatedetails.getDelivery_of_securities_purch() != null && mnCompCertificatedetails.getDelivery_of_securities_purch().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase1-3' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getDelivery_of_securities_purch() != null && mnCompCertificatedetails.getDelivery_of_securities_purch().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase1-3' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getDelivery_of_securities_purch() != null && mnCompCertificatedetails.getDelivery_of_securities_purch().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_3"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>1.4</strong> Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period? (As per PFRDA circular no. PFRDA/2021/32/REG-PF/4 dated 27.07.2021, such investments to be reported to NPS Trust within 30 days of making such investments)
							Details of Investments provided in Annexure B.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase1-4' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getInvestment_done_in_ipo() != null && mnCompCertificatedetails.getInvestment_done_in_ipo().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase1-4' type="radio" id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getInvestment_done_in_ipo() != null && mnCompCertificatedetails.getInvestment_done_in_ipo().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase1-4' type="radio" id="section-1-3" value="NA" disabled 
                            <%=mnCompCertificatedetails.getInvestment_done_in_ipo() != null && mnCompCertificatedetails.getInvestment_done_in_ipo().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Purchase_of_securitiesremarks_1_4"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>2. Securities held</strong></p>
        			</div>
        		</div>
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>2.1</strong>  Whether scheme investments adhere to the Investment guidelines issued by PFRDA. 
							(prescribed securities/ percentage/ limit/ prudential & exposure norms)
							Details of deviations provided in annexure A (ii).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-1' type="radio" id="section-1-1" value="Yes" disabled
                             <%=mnCompCertificatedetails.getScheme_investments() != null && mnCompCertificatedetails.getScheme_investments().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-1' type="radio" id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getScheme_investments() != null && mnCompCertificatedetails.getScheme_investments().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-1' type="radio" id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getScheme_investments() != null && mnCompCertificatedetails.getScheme_investments().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_1"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		   <div class="row">
        			<div class="col-md-6">
                        <div class="form-group"> 
                            <p><strong>2.2 (a)</strong> Whether stop loss trigger has occurred for any security during the month as per Investment policy of the PFM. </p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2a' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getStop_loss_trigger() != null && mnCompCertificatedetails.getStop_loss_trigger().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2a' type="radio" id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getStop_loss_trigger() != null && mnCompCertificatedetails.getStop_loss_trigger().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2a' type="radio" id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getStop_loss_trigger() != null && mnCompCertificatedetails.getStop_loss_trigger().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; NA</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_2a"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether decision in such scenario is approved by the committee/competent authority as per Approval delegation matrix.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2b' type="radio" id="section-1-1" value="Yes" disabled
                             <%=mnCompCertificatedetails.getDecision_approved_by_committee() != null && mnCompCertificatedetails.getDecision_approved_by_committee().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2b' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getDecision_approved_by_committee() != null && mnCompCertificatedetails.getDecision_approved_by_committee().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2b' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getDecision_approved_by_committee() != null && mnCompCertificatedetails.getDecision_approved_by_committee().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_2b"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		       		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(c)</strong> Whether each decision along with rationale is properly documented and record is maintained at individual scrip level.
							(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)
							Details of stop loss occurred during the month and its decision provided in Annexure C.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2c'type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getDecision_properly_documented() != null && mnCompCertificatedetails.getDecision_properly_documented().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2c' type="radio" id="section-1-2" value="No" disabled 
                             <%=mnCompCertificatedetails.getDecision_properly_documented() != null && mnCompCertificatedetails.getDecision_properly_documented().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-2c' type="radio" id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getDecision_properly_documented() != null && mnCompCertificatedetails.getDecision_properly_documented().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_2c"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!-- 2.1 over abc -->
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>2.3</strong>  Whether inter-scheme transfer of securities complies with point 14 of the Investment Guideline circular number PFRDA/2021/29/REG-PF/3 dated 20.07.2021.
							Details of inter scheme transfer provided in Annexure D.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-3' type="radio" id="section-1-1" value="Yes" disabled 
                            <%=mnCompCertificatedetails.getInter_scheme_transfer() != null && mnCompCertificatedetails.getInter_scheme_transfer().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-3' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getInter_scheme_transfer() != null && mnCompCertificatedetails.getInter_scheme_transfer().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-3' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getInter_scheme_transfer() != null && mnCompCertificatedetails.getInter_scheme_transfer().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_3"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>2.4</strong>  Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing. 
							Details provided in Annexure E</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-4' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getInvestment_held_in_equity() != null && mnCompCertificatedetails.getInvestment_held_in_equity().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-4' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getInvestment_held_in_equity() != null && mnCompCertificatedetails.getInvestment_held_in_equity().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-4' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getInvestment_held_in_equity() != null && mnCompCertificatedetails.getInvestment_held_in_equity().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_4"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>2.5</strong>  Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing. 
							Details provided in Annexure F.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-5' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getInvestment_in_equity_shares() != null && mnCompCertificatedetails.getInvestment_in_equity_shares().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-5' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getInvestment_in_equity_shares() != null && mnCompCertificatedetails.getInvestment_in_equity_shares().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase2-5' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getInvestment_in_equity_shares() != null && mnCompCertificatedetails.getInvestment_in_equity_shares().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Securities_held_2_5"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		 <div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>3. Sale of securities</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>3.1 (a)</strong> Whether disinvestments made are approved by the committee/competent authority as per delegation matrix.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-1a' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getDisinvestments_approved() != null && mnCompCertificatedetails.getDisinvestments_approved().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-1a' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getDisinvestments_approved() != null && mnCompCertificatedetails.getDisinvestments_approved().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-1a' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getDisinvestments_approved() != null && mnCompCertificatedetails.getDisinvestments_approved().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Sale_of_securities_3_1a"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.
							(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision).</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-1b' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getDecision_of_disinvestment() != null && mnCompCertificatedetails.getDecision_of_disinvestment().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-1b' type="radio" id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getDecision_of_disinvestment() != null && mnCompCertificatedetails.getDecision_of_disinvestment().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-1b' type="radio" id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getDecision_of_disinvestment() != null && mnCompCertificatedetails.getDecision_of_disinvestment().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Sale_of_securities_3_1b"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!--3.1 over abc  -->
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>3.2</strong>  Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-2' type="radio" id="section-1-1" value="Yes" disabled
                             <%=mnCompCertificatedetails.getDelivery_of_securities_sale() != null && mnCompCertificatedetails.getDelivery_of_securities_sale().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-2' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getDelivery_of_securities_sale() != null && mnCompCertificatedetails.getDelivery_of_securities_sale().equalsIgnoreCase("No") ? "checked" : "" %>>
                            
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase3-2' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getDelivery_of_securities_sale() != null && mnCompCertificatedetails.getDelivery_of_securities_sale().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Sale_of_securities_3_2"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>4. Reports and Disclosures as per Schedule V of PFRDA (PF) Regulations</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>4.1 (a)</strong> Whether all investment charges (Investment management fees, Custodian charges, SEBI charges, NPS Trust fees etc.) are loaded onto the NAV on a daily basis.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1a' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getAll_investment_charges() != null && mnCompCertificatedetails.getAll_investment_charges().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1a' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getAll_investment_charges() != null && mnCompCertificatedetails.getAll_investment_charges().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1a' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getAll_investment_charges() != null && mnCompCertificatedetails.getAll_investment_charges().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_1a"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		   <div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether PFM has adhered to instructions of PFRDA to get the NAV verified by the auditors on a daily basis.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1b' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getPfm_adhered() != null && mnCompCertificatedetails.getPfm_adhered().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1b' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getPfm_adhered() != null && mnCompCertificatedetails.getPfm_adhered().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1b' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getPfm_adhered() != null && mnCompCertificatedetails.getPfm_adhered().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_1b"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(c)</strong> Whether the records of the audit of NAV have been maintained by the pension fund for future inspection. </p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1c' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getRecords_of_the_audit_of_nav() != null && mnCompCertificatedetails.getRecords_of_the_audit_of_nav().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1c' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getRecords_of_the_audit_of_nav() != null && mnCompCertificatedetails.getRecords_of_the_audit_of_nav().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1c' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getRecords_of_the_audit_of_nav() != null && mnCompCertificatedetails.getRecords_of_the_audit_of_nav().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_1c"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(d)</strong> Whether scheme-wise NAV (latest & historical) is uploaded daily on the PFM’s website within the prescribed time limit.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1d' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getScheme_wise_nav_uploaded() != null && mnCompCertificatedetails.getScheme_wise_nav_uploaded().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1d' type="radio" id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getScheme_wise_nav_uploaded() != null && mnCompCertificatedetails.getScheme_wise_nav_uploaded().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1d' type="radio" id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getScheme_wise_nav_uploaded() != null && mnCompCertificatedetails.getScheme_wise_nav_uploaded().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_1d"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(e)</strong> Whether scheme-wise NAV is submitted daily to all the CRA’s within the prescribed time limit.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1e' type="radio" id="section-1-1" value="Yes" disabled
                             <%=mnCompCertificatedetails.getScheme_wise_nav_submitted() != null && mnCompCertificatedetails.getScheme_wise_nav_submitted().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1e' type="radio" id="section-1-2" value="No" disabled
                            <%=mnCompCertificatedetails.getScheme_wise_nav_submitted() != null && mnCompCertificatedetails.getScheme_wise_nav_submitted().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-1e' type="radio" id="section-1-3" value="NA" disabled
                            <%=mnCompCertificatedetails.getScheme_wise_nav_submitted() != null && mnCompCertificatedetails.getScheme_wise_nav_submitted().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_1e"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!--4.1 over abc  -->
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>4.2 (a)</strong> Whether monthly periodic reports as per schedule V are submitted to NPS Trust within 10 days from the end of the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-2a' type="radio" id="section-1-1" value="Yes" disabled
                            <%=mnCompCertificatedetails.getMonthly_reports_submitted() != null && mnCompCertificatedetails.getMonthly_reports_submitted().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-2a' type="radio" id="section-1-2" value="No" disabled
                             <%=mnCompCertificatedetails.getMonthly_reports_submitted() != null && mnCompCertificatedetails.getMonthly_reports_submitted().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-2a' type="radio" id="section-1-3" value="NA" disabled
                             <%=mnCompCertificatedetails.getMonthly_reports_submitted() != null && mnCompCertificatedetails.getMonthly_reports_submitted().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_2a"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
        			<div class="col-md-6">
                        <div class="form-group">
                            <p><strong>(b)</strong> Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-2b' type="radio" id="section-1-1" name="randd" value="Yes" disabled
                             <%=mnCompCertificatedetails.getScrip_wise_details() != null && mnCompCertificatedetails.getScrip_wise_details().equalsIgnoreCase("Yes") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-2b' type="radio" id="section-1-2" name="randd" value="No" disabled
                             <%=mnCompCertificatedetails.getScrip_wise_details() != null && mnCompCertificatedetails.getScrip_wise_details().equalsIgnoreCase("No") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name='<portlet:namespace/>purchase4-2b' type="radio" id="section-1-3" name="randd" value="NA" disabled
                             <%=mnCompCertificatedetails.getScrip_wise_details() != null && mnCompCertificatedetails.getScrip_wise_details().equalsIgnoreCase("NA") ? "checked" : "" %>>
                            <label class="form-check-label">&nbsp; Na</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" name='<portlet:namespace/>purchase4-2b' id="remarks_1_1" placeholder="Remarks if any" name="<portlet:namespace/>Reports_and_Disclosures_4_2b"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<!-- document row -->
        		
        		<div class="row">
        			<div class="col-md-12 form-group">
        				<p><strong>Documents Details</strong></p>
        			</div>
        		</div>
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure A (i): Breach in Investment guidelines - Securities Purchased.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_a_iURL ? "javascript:void(0);" : Annexure_a_iURL}" ${empty Annexure_a_iURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>Document_Ai"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure A (ii): Breach in Investment guidelines - Securities held in portfolio.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_a_iiURL ? "javascript:void(0);" : Annexure_a_iiURL}" ${empty Annexure_a_iiURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureAii"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		  <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure B: Details of application/investment in IPO/FPO/OFS.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            ${Annexure_bURL}
                            <a href="${empty Annexure_bURL ? "javascript:void(0);" : Annexure_bURL}" ${empty Annexure_bURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureB"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure C: Details of stop loss triggered during the month.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_cURL ? "javascript:void(0);" : Annexure_cURL}" ${empty Annexure_cURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureC"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		   <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure D: Details of inter-scheme transfer of securities.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_dURL ? "javascript:void(0);" : Annexure_dURL}" ${empty Annexure_dURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureD"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		  <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure E: Details of stocks held outside the list of top 200 stocks (pending for rebalancing/rebalanced during the month) .</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_eURL ? "javascript:void(0);" : Annexure_eURL}" ${empty Annexure_eURL ? "" : "download"}>Click here to download</i></a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureE"></textarea>
                        </div>
                    </div>
        		   </div><!-- row one end -->
        		
        		  <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure F: Details of sale of shares allotted in IPO/FPO/OFS where stock does not meet market capitalization condition post listing.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_fURL ? "javascript:void(0);" : Annexure_fURL}" ${empty Annexure_fURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureF"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure G: Instances of delays in uploading NAV on website and submission to CRA.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                           <!--  <a>Click here to download</a> -->
                           <a href="${empty Annexure_gURL ? "javascript:void(0);" : Annexure_gURL}" ${empty Annexure_gURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureG"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <p>Annexure H: Segregation of Active and Passive deviations/breaches.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-check-inline">
                            <!-- <a>Click here to download</a> -->
                            <a href="${empty Annexure_hURL ? "javascript:void(0);" : Annexure_hURL}" ${empty Annexure_hURL ? "" : "download"}>Click here to download</a>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="Document_Ai" placeholder="Remarks if any" name="<portlet:namespace/>AnnexureH"></textarea>
                        </div>
                    </div>
        		</div><!-- row one end -->
        		
        		<div class="row text-center">
			        <div class="col-md-12">
			        	<button type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit">Submit</button>
			        </div>
		    	</div>
        		
        	</form>
        </div>
</div>




<script>
$("#form_comp").on('submit', (function(e) {
	    $.ajax({
	            url: '${saveMonthlyComCertificateForm}',
	            processData: false,
	            contentType: false,
	            data: new FormData(this),
	            type: "post",
	            success: function(data) {
	            },
	            error: function(xhr, status, error) {
	                console.log("Error error:::", error);
	            }
	        });
			
	}));
</script>
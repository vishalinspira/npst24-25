<%@page import="Custodian.Annexure_4b.constants.CustodianAnnexure_4bPortletKeys"%>
<%@ include file="/init.jsp" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

<portlet:resourceURL id="<%=CustodianAnnexure_4bPortletKeys.annex_4b%>" var="annexFourB" />

<div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Custodian - Annexure - 4b
                </div>
            </div>
        </div>
	
	<div class="form_block mx-4">
		<!-- <form class="form" id="custodianAnnex_4b" action="#" method="post"> -->
		<form class="form_1" id="form_1" action="#" method="post">
		<!-- <aui:form id="sesNonUnanimous" method="post"> -->
			<div class="tab_0" id="page_1">
				<div class="row">
					<div class="col-md-4">
						<label>File No.</label>
						<input type="text" class="form-control fileNumber" id="fileNumber" name="<portlet:namespace />fileNumber"><br>
					</div>
					<div class="col-md-4">
						<label>Date</label>
						<input type="date" class="form-control date_1" id="date_1" name="<portlet:namespace />date_1"><br>
					</div>
				</div>
				<br>
				
				<p>To</p><br>
				
				<div class="row">
	                <div class="col">
	                    <div class="form-group">
	                    	<div class="row">
								<div class="col-md-4">
									<label>Custodian Name</label>
									<input type="text" class="form-control custodianName" id="custodianName" name="<portlet:namespace />custodianName"><br>
								</div>	
							</div><br>
	                    	
	                    	<div class="row">
								<div class="col-md-4">
									<label>Address</label>
									<textarea class="form-control address" id="address" name="<portlet:namespace />address"></textarea><br>
								</div>	
							</div><br>

						</div>
					</div>
				</div>
				
				<p>Dear Sir,</p>
				<br>
				
				<div class="form-inline row">
					<div class="col-md-8">
		    			<p><strong>Subject: NPS Trust observations on Annual Audit Report of Custodian for the FY</strong> 
		        			<input type="text" class="form-control date_2" id="date_2" name="<portlet:namespace />date_2">
		        		</p>
					</div>
				</div>
				<br>
				
				<div class="form-inline row">
					<div class="col-md-12">
						<p>This has reference to the Annual Audit Report of Custodian for the FY <span id="date_3">YYYY-YY</span>
						The audit report as submitted by the M/s <input type="text" class="form-control name" id="name" name="<portlet:namespace />name">
						appointed by NPS Trust for the FY <span id="date_4">YYYY-YY</span>
						has been analysed in light of the exceptions noted by auditor and response provided by the management. 
						The observations of NPS Trust on the Annual Audit Report are provided as under:
						</p>
					</div>
				</div>
				
				<div class="row text-center">
					<div class="col-md-12">
						<button type="button" class="nps-btn page0_Next" id="nextBtn">Next</button>
					</div>
				</div>
				
				
			</div>
			
			</form>
			
			<form class="form_2" id="form_2" action="#" method="post">
			
			<div class="tab_1 d-none" id="page_2">
                <h5>A . Regulatory parameters</h5>
                <hr>
                <h5>General obligations of Custodian of Securities</h5>
                <hr>
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The Custodian is ensuring that it is managed in 
                            accordance with the provisions of the PFRDA Act, 
                            Trust Deed and all relevant regulations, guidelines 
                            and directions issued by the Authority and NPS Trust.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-1" name='<portlet:namespace/>section_a_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-2" name='<portlet:namespace/>section_a_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_1"></textarea>
                        	<span id="reg_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-1" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_1"><br>
                        	<span id="reg_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_1"></textarea>
                        	<span id="reg_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                 <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The Custodian has entered into all such agreements, 
                            as specified in the PFRDA Regulations and guidelines issued from 
                            time to time, with either NPS Trust or any other intermediary of NPS.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-3" name='<portlet:namespace/>section_a_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-4" name='<portlet:namespace/>section_a_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_2"></textarea>
                        	<span id="reg_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-2" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_2"><br>
                        	<span id="reg_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_2"></textarea>
                        	<span id="reg_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Custodian has taken reasonable care, prudence, professional 
                            skill and diligence while undertaking Custodial activities. 
                            Any deviation has been timely reported to NPS Trust/ PFRDA.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-5" name='<portlet:namespace/>section_a_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-6" name='<portlet:namespace/>section_a_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_3"></textarea>
                        	<span id="reg_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-3" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_3"><br>
                        	<span id="reg_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_3"></textarea>
                        	<span id="reg_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Custodian is undertaking activities as Domestic 
                            Depository in terms of Depositories Act, 1996 or as permitted by SEBI</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-7" name='<portlet:namespace/>section_a_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-8" name='<portlet:namespace/>section_a_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_4"></textarea>
                        	<span id="reg_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-4" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_4"><br>
                        	<span id="reg_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_4"></textarea>
                        	<span id="reg_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>5.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the Custodian is maintaining records of the decisions 
                            taken by its Board and sub-committees at its meetings and the 
                            minutes of the meetings?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-9" name='<portlet:namespace/>section_a_5' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-10" name='<portlet:namespace/>section_a_5' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-5" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_5"></textarea>
                        	<span id="reg_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-5" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_5"><br>
                        	<span id="reg_sam_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-5" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_5"></textarea>
                        	<span id="reg_nps_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>6.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the Custodian has submitted on time the audited scheme financials, 
                            internal audit reports, inspection, compliance reports and any other reports, 
                            as specified by the Authority and / or NPS Trust, to the Authority / NPS Trust, 
                            as applicable?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-11" name='<portlet:namespace/>section_a_6' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-12" name='<portlet:namespace/>section_a_6' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-6" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_6"></textarea>
                        	<span id="reg_rem_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-6" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_6"><br>
                        	<span id="reg_sam_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-6" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_6"></textarea>
                        	<span id="reg_nps_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>7.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the observations and recommendations, if any, 
                            made by NPS Trust on the above referred reports were acted upon 
                            and / or responded to and on time?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-13" name='<portlet:namespace/>section_a_7' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-14" name='<portlet:namespace/>section_a_7' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-7" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_7"></textarea>
                        	<span id="reg_rem_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-7" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_7"><br>
                        	<span id="reg_sam_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-7" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_7"></textarea>
                        	<span id="reg_nps_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>8.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian takes all necessary precautions to ensure that 
                            continuity of the record keeping is not lost or destroyed and that 
                            sufficient back up of records are available.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-15" name='<portlet:namespace/>section_a_8' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-16" name='<portlet:namespace/>section_a_8' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-8" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_8"></textarea>
                        	<span id="reg_rem_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-8" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_8"><br>
                        	<span id="reg_sam_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-8" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_8"></textarea>
                        	<span id="reg_nps_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>9.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Regulatory fee payment made on time at the rate as decided by the Authority.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-17" name='<portlet:namespace/>section_a_9' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-a-18" name='<portlet:namespace/>section_a_9' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulaory-remarks-9" placeholder="Remarks if any" name="<portlet:namespace/>regulatory_remarks_9"></textarea>
                        	<span id="reg_rem_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="regulatory-sample-9" placeholder="Sample Size" name="<portlet:namespace />regulatory_sample_9"><br>
                        	<span id="reg_sam_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="regulatory-nps-9" placeholder="NPS Trust Observations" name="<portlet:namespace/>regulatory_nps_9"></textarea>
                        	<span id="reg_nps_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>

                <div class="row text-center">
			        <div class="col-md-12">
			            <button type="button" class="nps-btn page1_Prev" id="prevBtn">Previous</button>
			            <button type="button" class="nps-btn page1_Next" id="nextBtn">Next</button>
			        </div>
		    	</div>
		    		
            </div>
			
			</form>
			
			<form class="form_3" id="form_3" action="#" method="post">
			
			<div class="tab_2 d-none">
                <h5>B . Operational parameters</h5>
                <hr>
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Operational Manual/Procedure has been approved by Board and subsequent amendments too.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-1" name='<portlet:namespace/>section_b_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-2" name='<portlet:namespace/>section_b_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_1"></textarea>
                        	<span id="oper_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-1" placeholder="Sample Size" name="<portlet:namespace />operational_sample_1"><br>
                        	<span id="oper_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_1"></textarea>
                        	<span id="oper_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>What is the frequency of review of operational manual?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-3" name='<portlet:namespace/>section_b_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-4" name='<portlet:namespace/>section_b_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_2"></textarea>
                        	<span id="oper_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-2" placeholder="Sample Size" name="<portlet:namespace />operational_sample_2"><br>
                        	<span id="oper_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_2"></textarea>
                        	<span id="oper_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Compliance with procedure for security documents execution as laid down in Operational Manual.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-5" name='<portlet:namespace/>section_b_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-6" name='<portlet:namespace/>section_b_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_3"></textarea>
                        	<span id="oper_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-3" placeholder="Sample Size" name="<portlet:namespace />operational_sample_3"><br>
                        	<span id="oper_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_3"></textarea>
                        	<span id="oper_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the Custodian acts upon communication in writing 
                            received from NPS Trust informing it of the deficiencies 
                            and reports to NPS Trust on the rectification of 
                            such deficiencies?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-7" name='<portlet:namespace/>section_b_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-8" name='<portlet:namespace/>section_b_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_4"></textarea>
                        	<span id="oper_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-4" placeholder="Sample Size" name="<portlet:namespace />operational_sample_4"><br>
                        	<span id="oper_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_4"></textarea>
                        	<span id="oper_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>5.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The Custodian receives and hold all securities 
                            delivered to it in Safe Custody and deal as may 
                            deem fit for the purposes of providing for the 
                            safekeeping thereof.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-9" name='<portlet:namespace/>section_b_5' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-10" name='<portlet:namespace/>section_b_5' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-5" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_5"></textarea>
                        	<span id="oper_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-5" placeholder="Sample Size" name="<portlet:namespace />operational_sample_5"><br>
                        	<span id="oper_sam_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-5" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_5"></textarea>
                        	<span id="oper_nps_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>6.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Neither any assets have been acquired out of subscribers'
                            funds nor any liability assumed which results in encumbrance 
                            of the property of the National Pension System Trust 
                            or that of the subscribers in any way.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-11" name='<portlet:namespace/>section_b_6' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-12" name='<portlet:namespace/>section_b_6' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-6" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_6"></textarea>
                        	<span id="oper_rem_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-6" placeholder="Sample Size" name="<portlet:namespace />operational_sample_6"><br>
                        	<span id="oper_sam_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-6" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_6"></textarea>
                        	<span id="oper_nps_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>7.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The Custodian have not assigned, transferred, hypothecated, 
                            pledged, lended, use or otherwise dispose of any assets and 
                            securities/ investible Fund, except pursuant to 
                            instruction from the Pension Fund/ NPS Trust.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-13" name='<portlet:namespace/>section_b_7' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-14" name='<portlet:namespace/>section_b_7' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-7" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_7"></textarea>
                        	<span id="oper_rem_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-7" placeholder="Sample Size" name="<portlet:namespace />operational_sample_7"><br>
                        	<span id="oper_sam_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-7" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_7"></textarea>
                        	<span id="oper_nps_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>8.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>There is proper documented procedure for 
                            receipt of orders from Pension Funds at 
                            Custodian’s end?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-15" name='<portlet:namespace/>section_b_8' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-16" name='<portlet:namespace/>section_b_8' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-8" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_8"></textarea>
                        	<span id="oper_rem_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-8" placeholder="Sample Size" name="<portlet:namespace />operational_sample_8"><br>
                        	<span id="oper_sam_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-8" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_8"></textarea>
                        	<span id="oper_nps_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>9.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>How orders are matched with files received and 
                            confirmation of match orders provided? 
                            Is there a maker and checker concept?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-17" name='<portlet:namespace/>section_b_9' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-18" name='<portlet:namespace/>section_b_9' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-9" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_9"></textarea>
                        	<span id="oper_rem_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-9" placeholder="Sample Size" name="<portlet:namespace />operational_sample_9"><br>
                        	<span id="oper_sam_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-9" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_9"></textarea>
                        	<span id="oper_nps_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>10.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>There is proper documented procedure for settling funds in the system?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-19" name='<portlet:namespace/>section_b_10' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-20" name='<portlet:namespace/>section_b_10' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-10" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_10"></textarea>
                        	<span id="oper_rem_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-10" placeholder="Sample Size" name="<portlet:namespace />operational_sample_10"><br>
                        	<span id="oper_sam_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-10" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_10"></textarea>
                        	<span id="oper_nps_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>11.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>How does Custodian report confirmed and unconfirmed trades?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-21" name='<portlet:namespace/>section_b_11' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-22" name='<portlet:namespace/>section_b_11' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-11" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_11"></textarea>
                        	<span id="oper_rem_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-11" placeholder="Sample Size" name="<portlet:namespace />operational_sample_11"><br>
                        	<span id="oper_sam_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-11" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_11"></textarea>
                        	<span id="oper_nps_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>12.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>How securities settlement takes place? 
                            Whether all the process as mentioned above 
                            is duly prescribed in the operational manual?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-23" name='<portlet:namespace/>section_b_12' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-24" name='<portlet:namespace/>section_b_12' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-12" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_12"></textarea>
                        	<span id="oper_rem_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-12" placeholder="Sample Size" name="<portlet:namespace />operational_sample_12"><br>
                        	<span id="oper_sam_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-12" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_12"></textarea>
                        	<span id="oper_nps_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>13.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>How scheme-wise reconciliation of holdings for 
                            each Pension Funds happens? What is the frequency?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-25" name='<portlet:namespace/>section_b_13' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-26" name='<portlet:namespace/>section_b_13' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-13" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_13"></textarea>
                        	<span id="oper_rem_13_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-13" placeholder="Sample Size" name="<portlet:namespace />operational_sample_13"><br>
                        	<span id="oper_sam_13_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-13" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_13"></textarea>
                        	<span id="oper_nps_13_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>14.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>How reconciliation between Pension scheme A/c and CGSL happens?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-27" name='<portlet:namespace/>section_b_14' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-28" name='<portlet:namespace/>section_b_14' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-14" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_14"></textarea>
                        	<span id="oper_rem_14_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-14" placeholder="Sample Size" name="<portlet:namespace />operational_sample_14"><br>
                        	<span id="oper_sam_14_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-14" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_14"></textarea>
                        	<span id="oper_nps_14_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>15.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian ensure no sharing of password between officers?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-29" name='<portlet:namespace/>section_b_15' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-30" name='<portlet:namespace/>section_b_15' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-15" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_15"></textarea>
                        	<span id="oper_rem_15_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-15" placeholder="Sample Size" name="<portlet:namespace />operational_sample_15"><br>
                        	<span id="oper_sam_15_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-15" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_15"></textarea>
                        	<span id="oper_nps_15_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>16.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The custodian of securities ensures that the 
                            individual holdings of securities in the pension 
                            scheme accounts are reconciled with the depository 
                            holdings and Constituents' Subsidiary General 
                            Ledger (CSGL) account at the end of the day.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-31" name='<portlet:namespace/>section_b_16' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-32" name='<portlet:namespace/>section_b_16' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-16" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_16"></textarea>
                        	<span id="oper_rem_16_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-16" placeholder="Sample Size" name="<portlet:namespace />operational_sample_16"><br>
                        	<span id="oper_sam_16_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-16" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_16"></textarea>
                        	<span id="oper_nps_16_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>17.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The custodian of securities maintains complete 
                            audit trail of the movement of securities in and out 
                            of the pension scheme accounts and is in position to 
                            provide the same whenever called for by the Authority 
                            or the National Pension System Trust.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-33" name='<portlet:namespace/>section_b_17' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-34" name='<portlet:namespace/>section_b_17' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-17" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_17"></textarea>
                        	<span id="oper_rem_17_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-17" placeholder="Sample Size" name="<portlet:namespace />operational_sample_17"><br>
                        	<span id="oper_sam_17_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-17" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_17"></textarea>
                        	<span id="oper_nps_17_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>18.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The reports are generated with manual intervention or through system? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-35" name='<portlet:namespace/>section_b_18' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-36" name='<portlet:namespace/>section_b_18' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-18" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_18"></textarea>
                        	<span id="oper_rem_18_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-18" placeholder="Sample Size" name="<portlet:namespace />operational_sample_18"><br>
                        	<span id="oper_sam_18_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-18" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_18"></textarea>
                        	<span id="oper_nps_18_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>19.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Neither the Custodian nor the Custodian's nominee has voted on any of the Securities
								held by the Custodian under its role as Custodian to NPS, except in accordance with 
								Instructions of the Pension Funds/ NPS Trust. The Custodian have promptly delivered 
								all reports, notices, proxies or proxy soliciting material received by it with
								respect to the Securities, relevant for the purpose of exercise of voting rights 
								to the Pension Funds/ NPS Trust.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-37" name='<portlet:namespace/>section_b_19' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-b-38" name='<portlet:namespace/>section_b_19' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-remarks-19" placeholder="Remarks if any" name="<portlet:namespace/>operational_remarks_19"></textarea>
                        	<span id="oper_rem_19_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="operational-sample-19" placeholder="Sample Size" name="<portlet:namespace />operational_sample_19"><br>
                        	<span id="oper_sam_19_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="operational-nps-19" placeholder="NPS Trust Observations" name="<portlet:namespace/>operational_nps_19"></textarea>
                        	<span id="oper_nps_19_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row text-center">
			        <div class="col-md-12">
			            <button type="button" class="nps-btn page2_Prev" id="prevBtn">Previous</button>
			            <button type="button" class="nps-btn page2_Next" id="nextBtn">Next</button>
			        </div>
		    	</div>

			</div>
			
			</form>
			
			<form class="form_4" id="form_4" action="#" method="post">
			
			<div class="tab_3 d-none">
				<h5>C . Timely Reporting</h5>
                <hr>
                <h5>To NPS Trust</h5>
                <hr>
                
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether internal audit reports at regular intervals for 
                            audits conducted by auditors appointed by NPS Trust as well 
                            as by the Custodian itself and the deviations mentioned therein 
                            have been shared with the Board and reported to PFRDA and NPS Trust, 
                            as applicable?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-1" name='<portlet:namespace/>section_c_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-2" name='<portlet:namespace/>section_c_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_1"></textarea>
                        	<span id="time_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-1" placeholder="Sample Size" name="<portlet:namespace />timely_sample_1"><br>
                        	<span id="time_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_1"></textarea>
                        	<span id="time_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether compliance certificates at regular 
                            intervals have been submitted to NPS Trust?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-3" name='<portlet:namespace/>section_c_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-4" name='<portlet:namespace/>section_c_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_2"></textarea>
                        	<span id="time_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-2" placeholder="Sample Size" name="<portlet:namespace />timely_sample_2"><br>
                        	<span id="time_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_2"></textarea>
                        	<span id="time_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether NPS Trust’s observations on the reports of the 
                            auditor and compliance reports have been acted upon and / or 
                            responded to and on time?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-5" name='<portlet:namespace/>section_c_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-6" name='<portlet:namespace/>section_c_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_3"></textarea>
                        	<span id="time_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-3" placeholder="Sample Size" name="<portlet:namespace />timely_sample_3"><br>
                        	<span id="time_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_3"></textarea>
                        	<span id="time_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether Custodian timely informs NPS Trust and 
                            PFs about the scripts moved out of Top 200 
                            stocks issued by NPS Trust.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-7" name='<portlet:namespace/>section_c_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-8" name='<portlet:namespace/>section_c_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_4"></textarea>
                        	<span id="time_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-4" placeholder="Sample Size" name="<portlet:namespace />timely_sample_4"><br>
                        	<span id="time_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_4"></textarea>
                        	<span id="time_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>5.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian submits holding confirmation with 
                            Pension Funds to NPS Trust within the stipulated 
                            timelines as prescribed by PFRDA </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-9" name='<portlet:namespace/>section_c_5' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-10" name='<portlet:namespace/>section_c_5' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-5" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_5"></textarea>
                        	<span id="time_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-5" placeholder="Sample Size" name="<portlet:namespace />timely_sample_5"><br>
                        	<span id="time_sam_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-5" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_5"></textarea>
                        	<span id="time_nps_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <h5>To Pension Funds</h5>
                <hr>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>6.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Daily saleable Holding Report</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-11" name='<portlet:namespace/>section_c_6' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-12" name='<portlet:namespace/>section_c_6' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-6" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_6"></textarea>
                        	<span id="time_rem_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-6" placeholder="Sample Size" name="<portlet:namespace />timely_sample_6"><br>
                        	<span id="time_sam_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-6" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_6"></textarea>
                        	<span id="time_nps_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>7.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Daily transaction statement (on next day)</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-13" name='<portlet:namespace/>section_c_7' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-14" name='<portlet:namespace/>section_c_7' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-7" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_7"></textarea>
                        	<span id="time_rem_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-7" placeholder="Sample Size" name="<portlet:namespace />timely_sample_7"><br>
                        	<span id="time_sam_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-7" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_7"></textarea>
                        	<span id="time_nps_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>8.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Shortage report</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-15" name='<portlet:namespace/>section_c_8' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-16" name='<portlet:namespace/>section_c_8' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-8" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_8"></textarea>
                        	<span id="time_rem_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-8" placeholder="Sample Size" name="<portlet:namespace />timely_sample_8"><br>
                        	<span id="time_sam_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-8" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_8"></textarea>
                        	<span id="time_nps_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>9.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Report of Corporate action</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-17" name='<portlet:namespace/>section_c_9' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-18" name='<portlet:namespace/>section_c_9' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-9" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_9"></textarea>
                        	<span id="time_rem_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-9" placeholder="Sample Size" name="<portlet:namespace />timely_sample_9"><br>
                        	<span id="time_sam_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-9" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_9"></textarea>
                        	<span id="time_nps_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>10.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Corporate Action receipt reports </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-19" name='<portlet:namespace/>section_c_10' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-20" name='<portlet:namespace/>section_c_10' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-10" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_10"></textarea>
                        	<span id="time_rem_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-10" placeholder="Sample Size" name="<portlet:namespace />timely_sample_10"><br>
                        	<span id="time_sam_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-10" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_10"></textarea>
                        	<span id="time_nps_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>11.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Put & Call Intimation</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-21" name='<portlet:namespace/>section_c_11' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-22" name='<portlet:namespace/>section_c_11' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-11" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_11"></textarea>
                        	<span id="time_rem_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-11" placeholder="Sample Size" name="<portlet:namespace />timely_sample_11"><br>
                        	<span id="time_sam_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-11" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_11"></textarea>
                        	<span id="time_nps_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>12.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Outstanding Corporate Action</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-23" name='<portlet:namespace/>section_c_12' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-24" name='<portlet:namespace/>section_c_12' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-12" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_12"></textarea>
                        	<span id="time_rem_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-12" placeholder="Sample Size" name="<portlet:namespace />timely_sample_12"><br>
                        	<span id="time_sam_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-12" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_12"></textarea>
                        	<span id="time_nps_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>13.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Ex-Date Report</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-25" name='<portlet:namespace/>section_c_13' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-c-26" name='<portlet:namespace/>section_c_13' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-remarks-13" placeholder="Remarks if any" name="<portlet:namespace/>timely_remarks_13"></textarea>
                        	<span id="time_rem_13_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="timely-sample-13" placeholder="Sample Size" name="<portlet:namespace />timely_sample_13"><br>
                        	<span id="time_sam_13_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="timely-nps-13" placeholder="NPS Trust Observations" name="<portlet:namespace/>timely_nps_13"></textarea>
                        	<span id="time_nps_13_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row text-center">
			        <div class="col-md-12">
			            <button type="button" class="nps-btn page3_Prev" id="prevBtn">Previous</button>
			            <button type="button" class="nps-btn page3_Next" id="nextBtn">Next</button>
			        </div>
		    	</div>
                
			</div>
			
			</form>
			
			<form class="form_5" id="form_5" action="#" method="post">
			
			<div class="tab_4 d-none">
			
				<h5>D . Custodian Billing </h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether Custodian is calculating and charging 
                            Asset Servicing Charges as per the guidelines and 
                            the same matches with data shared by PFs. The methods 
                            of computation and daily verification with PFs and SHCIL 
                            have been verified and deviations/suggestions, if any, 
                            were reported.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-d-1" name='<portlet:namespace/>section_d_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-d-2" name='<portlet:namespace/>section_d_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="custodian-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>custodian_remarks_1"></textarea>
                        	<span id="cust_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="custodian-sample-1" placeholder="Sample Size" name="<portlet:namespace />custodian_sample_1"><br>
                        	<span id="cust_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="custodian-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>custodian_nps_1"></textarea>
                        	<span id="cust_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>To check whether SEBI, NSDL, CCIL, 
                            CBRICS and other charges as claimed by the 
                            Custodian have been computed accurately.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-d-3" name='<portlet:namespace/>section_d_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-d-4" name='<portlet:namespace/>section_d_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="custodian-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>custodian_remarks_2"></textarea>
                        	<span id="cust_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="custodian-sample-2" placeholder="Sample Size" name="<portlet:namespace />custodian_sample_2"><br>
                        	<span id="cust_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="custodian-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>custodian_nps_2"></textarea>
                        	<span id="cust_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <h5>E . Custodian Administration </h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>List of dedicated total no. of resources supporting the 
                            Custodian systems and operations (Managerial/ technical/ operations/ administrative etc)
                            with name, qualifications and total experience and the activity performed by them.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-1" name='<portlet:namespace/>section_e_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-2" name='<portlet:namespace/>section_e_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>admin_remarks_1"></textarea>
                        	<span id="admin_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="admin-sample-1" placeholder="Sample Size" name="<portlet:namespace />admin_sample_1"><br>
                        	<span id="admin_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>admin_nps_1"></textarea>
                        	<span id="admin_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>List of employees (with educational qualification and no. of years 
                            of experiences) engaged with Custodian handling NPS work.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-3" name='<portlet:namespace/>section_e_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-4" name='<portlet:namespace/>section_e_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>admin_remarks_2"></textarea>
                        	<span id="admin_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="admin-sample-2" placeholder="Sample Size" name="<portlet:namespace />admin_sample_2"><br>
                        	<span id="admin_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>admin_nps_2"></textarea>
                        	<span id="admin_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian have employee dealing guidelines in place?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-5" name='<portlet:namespace/>section_e_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-6" name='<portlet:namespace/>section_e_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>admin_remarks_3"></textarea>
                        	<span id="admin_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="admin-sample-3" placeholder="Sample Size" name="<portlet:namespace />admin_sample_3"><br>
                        	<span id="admin_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>admin_nps_3"></textarea>
                        	<span id="admin_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Outsourcing of the activities and its agreements and approvals.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-7" name='<portlet:namespace/>section_e_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-8" name='<portlet:namespace/>section_e_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>admin_remarks_4"></textarea>
                        	<span id="admin_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="admin-sample-4" placeholder="Sample Size" name="<portlet:namespace />admin_sample_4"><br>
                        	<span id="admin_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>admin_nps_4"></textarea>
                        	<span id="admin_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>5.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Training of resources and its frequency.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-9" name='<portlet:namespace/>section_e_5' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-e-10" name='<portlet:namespace/>section_e_5' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-remarks-5" placeholder="Remarks if any" name="<portlet:namespace/>admin_remarks_5"></textarea>
                        	<span id="admin_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="admin-sample-5" placeholder="Sample Size" name="<portlet:namespace />admin_sample_5"><br>
                        	<span id="admin_sam_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="admin-nps-5" placeholder="NPS Trust Observations" name="<portlet:namespace/>admin_nps_5"></textarea>
                        	<span id="admin_nps_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row text-center">
			        <div class="col-md-12">
			            <button type="button" class="nps-btn page4_Prev" id="prevBtn">Previous</button>
			            <button type="button" class="nps-btn page4_Next" id="nextBtn">Next</button>
			        </div>
		    	</div>
                
			</div>
			
			</form>
			
			<form class="form_6" id="form_6" action="#" method="post">
			
			<div class="tab_5 d-none">
			
				<h5>F . Infrastructure </h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Details of infrastructure of Custodian (Prime site and DR site).</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-1" name='<portlet:namespace/>section_f_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-2" name='<portlet:namespace/>section_f_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_1"></textarea>
                        	<span id="infra_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-1" placeholder="Sample Size" name="<portlet:namespace />infra_sample_1"><br>
                        	<span id="infra_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_1"></textarea>
                        	<span id="infra_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the infrastructure of the Custodian is capable 
                            enough to render its role as Custodian effectively and efficiently?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-3" name='<portlet:namespace/>section_f_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-4" name='<portlet:namespace/>section_f_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_2"></textarea>
                        	<span id="infra_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-2" placeholder="Sample Size" name="<portlet:namespace />infra_sample_2"><br>
                        	<span id="infra_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_2"></textarea>
                        	<span id="infra_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>When the last DR drill conducted and BCP 
                            was activated on both the above-mentioned sites.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-5" name='<portlet:namespace/>section_f_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-6" name='<portlet:namespace/>section_f_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_3"></textarea>
                        	<span id="infra_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-3" placeholder="Sample Size" name="<portlet:namespace />infra_sample_3"><br>
                        	<span id="infra_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_3"></textarea>
                        	<span id="infra_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Custodian has provided foolproof electronic interconnectivity 
                            with the Pension Funds and other relevant parties.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-7" name='<portlet:namespace/>section_f_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-8" name='<portlet:namespace/>section_f_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_4"></textarea>
                        	<span id="infra_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-4" placeholder="Sample Size" name="<portlet:namespace />infra_sample_4"><br>
                        	<span id="infra_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_4"></textarea>
                        	<span id="infra_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>5.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the frequency of incremental data shipping to DR is real time?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-9" name='<portlet:namespace/>section_f_5' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-10" name='<portlet:namespace/>section_f_5' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-5" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_5"></textarea>
                        	<span id="infra_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-5" placeholder="Sample Size" name="<portlet:namespace />infra_sample_5"><br>
                        	<span id="infra_sam_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-5" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_5"></textarea>
                        	<span id="infra_nps_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>6.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the frequency of syncing data at DR is real time?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-11" name='<portlet:namespace/>section_f_6' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-12" name='<portlet:namespace/>section_f_6' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-6" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_6"></textarea>
                        	<span id="infra_rem_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-6" placeholder="Sample Size" name="<portlet:namespace />infra_sample_6"><br>
                        	<span id="infra_sam_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-6" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_6"></textarea>
                        	<span id="infra_nps_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>7.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>How much time is required for activation of DR site ?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-13" name='<portlet:namespace/>section_f_7' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-14" name='<portlet:namespace/>section_f_7' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-7" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_7"></textarea>
                        	<span id="infra_rem_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-7" placeholder="Sample Size" name="<portlet:namespace />infra_sample_7"><br>
                        	<span id="infra_sam_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-7" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_7"></textarea>
                        	<span id="infra_nps_7_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>8.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the VPN facility is available with the 
                            concerned officials for primary and DR site?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-15" name='<portlet:namespace/>section_f_8' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-16" name='<portlet:namespace/>section_f_8' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-8" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_8"></textarea>
                        	<span id="infra_rem_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-8" placeholder="Sample Size" name="<portlet:namespace />infra_sample_8"><br>
                        	<span id="infra_sam_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-8" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_8"></textarea>
                        	<span id="infra_nps_8_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>9.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the transition between Primary and 
                            Secondary lease lines on real time basis?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-17" name='<portlet:namespace/>section_f_9' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-18" name='<portlet:namespace/>section_f_9' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-9" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_9"></textarea>
                        	<span id="infra_rem_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-9" placeholder="Sample Size" name="<portlet:namespace />infra_sample_9"><br>
                        	<span id="infra_sam_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-9" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_9"></textarea>
                        	<span id="infra_nps_9_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>10.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the Custodian has appointed full time CISOs and IT 
                            team to have proper check on cyber security related 
                            arrangements for both DR and BCP.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-19" name='<portlet:namespace/>section_f_10' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-20" name='<portlet:namespace/>section_f_10' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-10" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_10"></textarea>
                        	<span id="infra_rem_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-10" placeholder="Sample Size" name="<portlet:namespace />infra_sample_10"><br>
                        	<span id="infra_sam_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-10" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_10"></textarea>
                        	<span id="infra_nps_10_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>11.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian conduct IT audits annually or at 
                            less frequency and put up the same to the Risk and 
                            Audit Committee of the Board.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-21" name='<portlet:namespace/>section_f_11' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-22" name='<portlet:namespace/>section_f_11' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-11" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_11"></textarea>
                        	<span id="infra_rem_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-11" placeholder="Sample Size" name="<portlet:namespace />infra_sample_11"><br>
                        	<span id="infra_sam_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-11" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_11"></textarea>
                        	<span id="infra_nps_11_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>12.</p>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the Insurance policy taken by the 
                            Custodian is sufficient to fully cover the 
                            premises and data of NPS?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-23" name='<portlet:namespace/>section_f_12' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-f-24" name='<portlet:namespace/>section_f_12' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-remarks-12" placeholder="Remarks if any" name="<portlet:namespace/>infra_remarks_12"></textarea>
                        	<span id="infra_rem_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="infra-sample-12" placeholder="Sample Size" name="<portlet:namespace />infra_sample_12"><br>
                        	<span id="infra_sam_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="infra-nps-12" placeholder="NPS Trust Observations" name="<portlet:namespace/>infra_nps_12"></textarea>
                        	<span id="infra_nps_12_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row text-center">
			        <div class="col-md-12">
			            <button type="button" class="nps-btn page5_Prev" id="prevBtn">Previous</button>
			            <button type="button" class="nps-btn page5_Next" id="nextBtn">Next</button>
			        </div>
		    	</div>

			</div>
			
			</form>
			
			<form class="form_7" id="form_7" action="#" method="post">
			
			<div class="tab_6 d-none">
				
				<h5>G . Protection of Assets of  Beneficial Owners </h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether segregated bank accounts and other 
                            records pertaining to activities under 
                            National Pension System are being maintained?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-1" name='<portlet:namespace/>section_g_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-2" name='<portlet:namespace/>section_g_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>protection_remarks_1"></textarea>
                        	<span id="prot_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="protection-sample-1" placeholder="Sample Size" name="<portlet:namespace />protection_sample_1"><br>
                        	<span id="prot_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>protection_nps_1"></textarea>
                        	<span id="prot_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the Custodian is exercising all due diligence 
                            and vigilance in carrying out its duties and in protecting 
                            the rights and interests of the subscribers and is complying 
                            with the terms and conditions of Service Level Agreements (SLA) & 
                            guidelines issued by PFRDA and NPS Trust?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-3" name='<portlet:namespace/>section_g_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-4" name='<portlet:namespace/>section_g_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>protection_remarks_2"></textarea>
                        	<span id="prot_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="protection-sample-2" placeholder="Sample Size" name="<portlet:namespace />protection_sample_2"><br>
                        	<span id="prot_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>protection_nps_2"></textarea>
                        	<span id="prot_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether Custodian is ensuring that any NPS Trust property 
                            under its custody / control is properly protected, 
                            held and administered by the proper persons?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-5" name='<portlet:namespace/>section_g_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-6" name='<portlet:namespace/>section_g_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>protection_remarks_3"></textarea>
                        	<span id="prot_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="protection-sample-3" placeholder="Sample Size" name="<portlet:namespace />protection_sample_3"><br>
                        	<span id="prot_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>protection_nps_3"></textarea>
                        	<span id="prot_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Where the Custodian is supervising the collection 
                            of any income due on assets held in the name of the 
                            National Pension System Trust, claiming any repayment 
                            of tax and holding any income received in trust for the 
                            beneficiaries, the same are in accordance with the Trust 
                            Deed and the regulations, guidelines or directions issued 
                            by the Authority and NPS Trust.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-7" name='<portlet:namespace/>section_g_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-8" name='<portlet:namespace/>section_g_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>protection_remarks_4"></textarea>
                        	<span id="prot_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="protection-sample-4" placeholder="Sample Size" name="<portlet:namespace />protection_sample_4"><br>
                        	<span id="prot_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>protection_nps_4"></textarea>
                        	<span id="prot_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>5.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Where Custodian has custody or under its 
                            control any property of the Trust, whether 
                            it is being held in the interest of beneficiaries?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-9" name='<portlet:namespace/>section_g_5' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-10" name='<portlet:namespace/>section_g_5' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-remarks-5" placeholder="Remarks if any" name="<portlet:namespace/>protection_remarks_5"></textarea>
                        	<span id="prot_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="protection-sample-5" placeholder="Sample Size" name="<portlet:namespace />protection_sample_5"><br>
                        	<span id="prot_sam_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-nps-5" placeholder="NPS Trust Observations" name="<portlet:namespace/>protection_nps_5"></textarea>
                        	<span id="prot_nps_5_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>6.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether Custodian has adequate systems for internal 
                            controls to prevent any manipulation of records and 
                            documents including audits for securities and rights or 
                            entitlements arising from the securities held under this agreement. 
                            The custodian of securities shall have appropriate safekeeping measures 
                            to ensure that such securities (assets or documents) are 
                            protected from theft or natural hazard.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-11" name='<portlet:namespace/>section_g_6' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-g-12" name='<portlet:namespace/>section_g_6' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-remarks-6" placeholder="Remarks if any" name="<portlet:namespace/>protection_remarks_6"></textarea>
                        	<span id="prot_rem_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="protection-sample-6" placeholder="Sample Size" name="<portlet:namespace />protection_sample_6"><br>
                        	<span id="prot_sam_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="protection-nps-6" placeholder="NPS Trust Observations" name="<portlet:namespace/>protection_nps_6"></textarea>
                        	<span id="prot_nps_6_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<h5>H . Record maintenance and storage</h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the latest details and address of 
                            NPS Trust and its authorised officers are 
                            available with Custodian</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-1" name='<portlet:namespace/>section_h_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-2" name='<portlet:namespace/>section_h_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>record_remarks_1"></textarea>
                        	<span id="record_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="record-sample-1" placeholder="Sample Size" name="<portlet:namespace />record_sample_1"><br>
                        	<span id="record_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>record_nps_1"></textarea>
                        	<span id="record_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Whether the latest details and address of 
                            PFs and its authorised officers are available 
                            with Custodian</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-3" name='<portlet:namespace/>section_h_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-4" name='<portlet:namespace/>section_h_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>record_remarks_2"></textarea>
                        	<span id="record_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="record-sample-2" placeholder="Sample Size" name="<portlet:namespace />record_sample_2"><br>
                        	<span id="record_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>record_nps_2"></textarea>
                        	<span id="record_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>The custodian of securities create and maintain the 
                            records of securities held in its custody of NPS Trust 
                            in such manner that the tracing of securities or obtaining 
                            duplicate of the documents is facilitated, in the event of 
                            loss of original records for any reason.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-5" name='<portlet:namespace/>section_h_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-6" name='<portlet:namespace/>section_h_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>record_remarks_3"></textarea>
                        	<span id="record_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="record-sample-3" placeholder="Sample Size" name="<portlet:namespace />record_sample_3"><br>
                        	<span id="record_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>record_nps_3"></textarea>
                        	<span id="record_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian have any record retention policy?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-7" name='<portlet:namespace/>section_h_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-h-8" name='<portlet:namespace/>section_h_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>record_remarks_4"></textarea>
                        	<span id="record_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="record-sample-4" placeholder="Sample Size" name="<portlet:namespace />record_sample_4"><br>
                        	<span id="record_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="record-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>record_nps_4"></textarea>
                        	<span id="record_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row text-center">
			        <div class="col-md-12">
			            <button type="button" class="nps-btn page6_Prev" id="prevBtn">Previous</button>
			            <button type="button" class="nps-btn page6_Next" id="nextBtn">Next</button>
			        </div>
		    	</div>

			</div>
			
			</form>
			
			<form class="form_8" id="form_8" action="#" method="post">
			
			<div class="tab_7 d-none">
			
				<h5>I . Grievance Redressal</h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>All grievances raised/ registered against 
                            Custodian were resolved within TAT.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-i-1" name='<portlet:namespace/>section_i_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-i-2" name='<portlet:namespace/>section_i_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="grievance-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>grievance_remarks_1"></textarea>
                        	<span id="grie_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="grievance-sample-1" placeholder="Sample Size" name="<portlet:namespace />grievance_sample_1"><br>
                        	<span id="grie_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="grievance-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>grievance_nps_1"></textarea>
                        	<span id="grie_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Assess quality of responses against grievances raised.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-i-3" name='<portlet:namespace/>section_i_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-i-4" name='<portlet:namespace/>section_i_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="grievance-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>grievance_remarks_2"></textarea>
                        	<span id="grie_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="grievance-sample-2" placeholder="Sample Size" name="<portlet:namespace />grievance_sample_2"><br>
                        	<span id="grie_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="grievance-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>grievance_nps_2"></textarea>
                        	<span id="grie_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<h5>J . Other Parameters</h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Custodian to produce necessary approval documents for 
                            the consents of appropriate authorities, licenses, permits 
                            and approvals as are necessary for carrying out its functions 
                            and obligations under those agreements.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-j-1" name='<portlet:namespace/>section_j_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-j-2" name='<portlet:namespace/>section_j_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="other-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>other_remarks_1"></textarea>
                        	<span id="other_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="other-sample-1" placeholder="Sample Size" name="<portlet:namespace />other_sample_1"><br>
                        	<span id="other_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="other-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>other_nps_1"></textarea>
                        	<span id="other_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Does Custodian have any written down 
                            operational policy of Exit Management 
                            Plan in place?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-j-3" name='<portlet:namespace/>section_j_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-j-4" name='<portlet:namespace/>section_j_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="other-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>other_remarks_2"></textarea>
                        	<span id="other_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="other-sample-2" placeholder="Sample Size" name="<portlet:namespace />other_sample_2"><br>
                        	<span id="other_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="other-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>other_nps_2"></textarea>
                        	<span id="other_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
			
				<h5>K . Governance Structure of Custodian</h5>
                <hr>
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>1.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>System of approval within the organization.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-1" name='<portlet:namespace/>section_k_1' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-2" name='<portlet:namespace/>section_k_1' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-remarks-1" placeholder="Remarks if any" name="<portlet:namespace/>governance_remarks_1"></textarea>
                        	<span id="gov_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="governance-sample-1" placeholder="Sample Size" name="<portlet:namespace />governance_sample_1"><br>
                        	<span id="gov_sam_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-nps-1" placeholder="NPS Trust Observations" name="<portlet:namespace/>governance_nps_1"></textarea>
                        	<span id="gov_nps_1_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>2.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>System of disposal of advisory received from PFRDA.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-3" name='<portlet:namespace/>section_k_2' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-4" name='<portlet:namespace/>section_k_2' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-remarks-2" placeholder="Remarks if any" name="<portlet:namespace/>governance_remarks_2"></textarea>
                        	<span id="gov_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="governance-sample-2" placeholder="Sample Size" name="<portlet:namespace />governance_sample_2"><br>
                        	<span id="gov_sam_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-nps-2" placeholder="NPS Trust Observations" name="<portlet:namespace/>governance_nps_2"></textarea>
                        	<span id="gov_nps_2_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>3.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Monitoring of advisory from PFRDA but pending at end of Custodian.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-5" name='<portlet:namespace/>section_k_3' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-6" name='<portlet:namespace/>section_k_3' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-remarks-3" placeholder="Remarks if any" name="<portlet:namespace/>governance_remarks_3"></textarea>
                        	<span id="gov_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="governance-sample-3" placeholder="Sample Size" name="<portlet:namespace />governance_sample_3"><br>
                        	<span id="gov_sam_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-nps-3" placeholder="NPS Trust Observations" name="<portlet:namespace/>governance_nps_3"></textarea>
                        	<span id="gov_nps_3_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-1">
                        <div class="form-group">
                            <p>4.</p>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <p>Method of follow up of irregularities in workflow 
                            with outsourcing agencies or other linkages of Custodian 
                            and their existing arrangement.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-7" name='<portlet:namespace/>section_k_4' value="Yes">
                            <label class="form-check-label">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="section-k-8" name='<portlet:namespace/>section_k_4' value="No">
                            <label class="form-check-label">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-remarks-4" placeholder="Remarks if any" name="<portlet:namespace/>governance_remarks_4"></textarea>
                        	<span id="gov_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<input type="text" class="form-control" id="governance-sample-4" placeholder="Sample Size" name="<portlet:namespace />governance_sample_4"><br>
                        	<span id="gov_sam_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                        <div class="form-group">
                        	<textarea class="form-control" id="governance-nps-4" placeholder="NPS Trust Observations" name="<portlet:namespace/>governance_nps_4"></textarea>
                        	<span id="gov_nps_4_error" class="text-danger"></span>
                        </div>
                    </div>
                    
                </div>
				
				<div class="row">
                   <div class="col-md-12">
                        <div class="form-group">
                            <p>The observations of the Trust are herewith being communicated to you for taking necessary action.</p>
                        </div>
                    </div>
                </div>
				
				<div class="row">
	                <div class="col">
	                    <div class="form-group">
							<div class="row">
			                   	<div class="col-md-4">
			                         <label>Yours sincerely,</label>
			                         <input type="text" class="form-control" id="manager" placeholder="General Manager" name="<portlet:namespace />manager">
			                    </div>
			                </div>
				
						</div>
					</div>
				</div>
				
				<div class="row text-center">
			        <div class="col-md-12">
			        	<button type="button" class="nps-btn page7_Prev" id="prevBtn">Previous</button>
			            <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
			        </div>
		    	</div>

			</div>

			</form>
		
		<!-- </aui:form> -->
	
	</div>
	
	<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div> 
	
</div>

<script type="text/javascript">
var table = null;
$(document).ready(function() {
	table = $("#myTable").DataTable();
});
</script>

<style type="text/css">
#myTable_length, #myTable_filter, #myTable_info, #myTable_paginate {
	display: none;
}

label.error {
	display: block !important;
}

input.error {
	border-color: red;
}

textarea.error {
	border-color: red;
}

/* .page_title {
	font-size: 18px;
}  */

/* .tab {
  display: none;
}  */

#myTable label.error {
	display: block !important;
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
	  "timeOut": 6000,
	  "extendedTimeOut": 1000
	}
	
	$('#date_2').datepicker({
        multidate: 2,
        format: "yyyy",
        viewMode: "years", 
   		minViewMode: "years",
        language: 'en'
        
    });
	
	$( "#name" ).on( "click", function() {
	  	var setdate = $('#date_2').val();
	  	console.log(setdate);
	  	$( "#date_3" ).text('');
		$( "#date_4" ).text('');
		$( "#date_3" ).text(setdate);
		$( "#date_4" ).text(setdate);
	});
	
	
	
	
	$('.page0_Next').click(function() {
    	console.log("page0_Next clicked");
    	
    	$("#form_1").validate({
    	  	rules: {
    	  	<portlet:namespace />fileNumber: {
    	      	required: true
    	    },
    	    <portlet:namespace/>date_1: {
    		    required: true
    		},
    		<portlet:namespace/>custodianName: {
    	      	required: true
    	    },
    	    <portlet:namespace/>address: {
    		    required: true
    		},
    		<portlet:namespace/>date_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>name: {
    		    required: true
    		},
    		
    	  }

    	});
    	
    	if ($('#form_1').valid()) {
    		$('.tab_0').addClass('d-none');
            $('.tab_1').removeClass('d-none');
        }
        
    });
    
    $('.page1_Prev').click(function() {
        $('.tab_1').addClass('d-none');
        $('.tab_0').removeClass('d-none');
    });
    
    $('.page1_Next').click(function() {
    	console.log("page1_Next clicked");
    	
    	$("#form_2").validate({
    	  	rules: {
    	  	<portlet:namespace />section_a_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_1: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_2: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_2: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_3: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_3: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_4: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_4: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_5: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_5"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_5: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_6: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_6"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_6: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_7: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_7"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_7: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_8: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_8"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_8: {
    		    required: true
    		},
    		<portlet:namespace/>section_a_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_remarks_9: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_a_9"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>regulatory_sample_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>regulatory_nps_9: {
    		    required: true
    		}, 
    		
    	  }

    	});
    	
    	if ($('#form_2').valid()) {
    		$('.tab_0, .tab_1').addClass('d-none');
            $('.tab_2').removeClass('d-none');
        }
        
    });
    
    $('.page2_Prev').click(function() {
    	$('.tab_0, .tab_2').addClass('d-none');
        $('.tab_1').removeClass('d-none');
    });
    
    $('.page2_Next').click(function() {
    	console.log("page2_Next clicked");
    	
    	$("#form_3").validate({
    	  	rules: {
    	  	<portlet:namespace />section_b_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_1: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_2: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_2: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_3: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_3: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_4: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_4: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_5: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_5"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_5: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_6: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_6"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_6: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_7: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_7"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_7: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_8: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_8"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_8: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_9: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_9"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_9: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_10: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_10: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_10"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_10: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_10: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_11: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_11: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_11"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_11: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_11: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_12: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_12: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_12"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_12: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_12: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_13: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_13: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_13"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_13: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_13: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_14: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_14: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_14"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_14: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_14: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_15: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_15: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_15"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_15: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_15: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_16: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_16: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_16"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_16: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_16: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_17: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_17: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_17"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_17: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_17: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_18: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_18: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_18"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_18: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_18: {
    		    required: true
    		},
    		<portlet:namespace/>section_b_19: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_remarks_19: {
    	    	required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_b_19"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>operational_sample_19: {
    	      	required: true
    	    },
    	    <portlet:namespace/>operational_nps_19: {
    		    required: true
    		},
    		
    		
    	  }

    	}); 
    	
    	if ($('#form_3').valid()) {
    		//$('.initial_info, .monitoring_audit').addClass('d-none');
    		$('.tab_0, .tab_1, .tab_2').addClass('d-none');
            $('.tab_3').removeClass('d-none');
        }
        
    });
    
    $('.page3_Prev').click(function() {
   		$('.tab_0, .tab_1, .tab_3').addClass('d-none');
       	$('.tab_2').removeClass('d-none');
   	});
    
    $('.page3_Next').click(function() {
    	console.log("page3_Next clicked");
    	
    	$("#form_4").validate({
    	  	rules: {
    	  	<portlet:namespace />section_c_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_1: {
    		    required: true
    		},
    		<portlet:namespace />section_c_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_2: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_2: {
    		    required: true
    		},
    		<portlet:namespace />section_c_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_3: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_3: {
    		    required: true
    		},
    		<portlet:namespace />section_c_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_4: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_4: {
    		    required: true
    		},
    		<portlet:namespace />section_c_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_5: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_5"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_5: {
    		    required: true
    		},
    		<portlet:namespace />section_c_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_6: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_6"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_6: {
    		    required: true
    		},
    		<portlet:namespace />section_c_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_7: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_7"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_7: {
    		    required: true
    		},
    		<portlet:namespace />section_c_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_8: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_8"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_8: {
    		    required: true
    		},
    		<portlet:namespace />section_c_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_9: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_9"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_9: {
    		    required: true
    		},
    		<portlet:namespace />section_c_10: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_10: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_10"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_10: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_10: {
    		    required: true
    		},
    		<portlet:namespace />section_c_11: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_11: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_11"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_11: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_11: {
    		    required: true
    		},
    		<portlet:namespace />section_c_12: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_12: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_12"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_12: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_12: {
    		    required: true
    		},
    		<portlet:namespace />section_c_13: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_remarks_13: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_c_13"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>timely_sample_13: {
    	      	required: true
    	    },
    	    <portlet:namespace/>timely_nps_13: {
    		    required: true
    		},

    	  }

    	});

    	if ($('#form_4').valid()) {
    		
    		$('.tab_0, .tab_1, .tab_2, .tab_3').addClass('d-none');
            $('.tab_4').removeClass('d-none');
        }
        
    });
    
    $('.page4_Prev').click(function() {
   		$('.tab_0, .tab_1, .tab_2, .tab_4').addClass('d-none');
       	$('.tab_3').removeClass('d-none');
   	});
    
    $('.page4_Next').click(function() {
    	console.log("page4_Next clicked");
    	
    	$("#form_5").validate({
    	  	rules: {
    	  	<portlet:namespace />section_d_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>custodian_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_d_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>custodian_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>custodian_nps_1: {
    		    required: true
    		},
    		<portlet:namespace />section_d_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>custodian_remarks_2: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_d_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>custodian_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>custodian_nps_2: {
    		    required: true
    		},
    		<portlet:namespace />section_e_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_e_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>admin_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_nps_1: {
    		    required: true
    		},
    		<portlet:namespace />section_e_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_remarks_2: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_e_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>admin_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_nps_2: {
    		    required: true
    		},
    		<portlet:namespace />section_e_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_remarks_3: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_e_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>admin_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_nps_3: {
    		    required: true
    		},
    		<portlet:namespace />section_e_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_remarks_4: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_e_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>admin_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_nps_4: {
    		    required: true
    		},
    		<portlet:namespace />section_e_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_remarks_5: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_e_5"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>admin_sample_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>admin_nps_5: {
    		    required: true
    		},

    	  }
    	
    	});

    	if ($('#form_5').valid()) {
    		
    		$('.tab_0, .tab_1, .tab_2, .tab_3, .tab_4').addClass('d-none');
            $('.tab_5').removeClass('d-none');
        }
        
    });
    
    $('.page5_Prev').click(function() {
   		$('.tab_0, .tab_1, .tab_2, .tab_3, .tab_5').addClass('d-none');
       	$('.tab_4').removeClass('d-none');
   	});
    
    $('.page5_Next').click(function() {
    	console.log("page5_Next clicked");
    	
    	$("#form_6").validate({
    	  	rules: {
    	  	<portlet:namespace />section_f_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_1: {
    		    required: true
    		},
    		<portlet:namespace />section_f_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_2: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_2: {
    		    required: true
    		},
    		<portlet:namespace />section_f_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_3: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_3: {
    		    required: true
    		},
    		<portlet:namespace />section_f_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_4: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_4: {
    		    required: true
    		},
    		<portlet:namespace />section_f_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_5: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_5"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_5: {
    		    required: true
    		},
    		<portlet:namespace />section_f_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_6: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_6"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_6: {
    		    required: true
    		},
    		<portlet:namespace />section_f_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_7: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_7"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_7: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_7: {
    		    required: true
    		},
    		<portlet:namespace />section_f_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_8: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_8"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_8: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_8: {
    		    required: true
    		},
    		<portlet:namespace />section_f_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_9: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_9"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_9: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_9: {
    		    required: true
    		},
    		<portlet:namespace />section_f_10: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_10: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_10"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_10: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_10: {
    		    required: true
    		},
    		<portlet:namespace />section_f_11: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_11: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_11"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_11: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_11: {
    		    required: true
    		},
    		<portlet:namespace />section_f_12: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_remarks_12: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_f_12"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>infra_sample_12: {
    	      	required: true
    	    },
    	    <portlet:namespace/>infra_nps_12: {
    		    required: true
    		},
    		
    	  }

    	});

    	if ($('#form_6').valid()) {
			
    		$('.tab_0, .tab_1, .tab_2, .tab_3, .tab_4, .tab_5').addClass('d-none');
            $('.tab_6').removeClass('d-none');
        }
        
    });
    
    $('.page6_Prev').click(function() {
   		$('.tab_0, .tab_1, .tab_2, .tab_3, .tab_4, .tab_6').addClass('d-none');
       	$('.tab_5').removeClass('d-none');
   	});
    
    $('.page6_Next').click(function() {
    	console.log("page6_Next clicked");
    	
    	$("#form_7").validate({
    	  	rules: {
    	  	<portlet:namespace />section_g_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_g_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>protection_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_nps_1: {
    		    required: true
    		},
    		<portlet:namespace />section_g_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_remarks_2: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_g_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>protection_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_nps_2: {
    		    required: true
    		},
    		<portlet:namespace />section_g_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_remarks_3: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_g_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>protection_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_nps_3: {
    		    required: true
    		},
    		<portlet:namespace />section_g_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_remarks_4: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_g_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>protection_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_nps_4: {
    		    required: true
    		},
    		<portlet:namespace />section_g_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_remarks_5: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_g_5"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>protection_sample_5: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_nps_5: {
    		    required: true
    		},
    		<portlet:namespace />section_g_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_remarks_6: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_g_6"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>protection_sample_6: {
    	      	required: true
    	    },
    	    <portlet:namespace/>protection_nps_6: {
    		    required: true
    		},
    		<portlet:namespace />section_h_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_remarks_1: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_h_1"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>record_sample_1: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_nps_1: {
    		    required: true
    		},
    		<portlet:namespace />section_h_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_remarks_2: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_h_2"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>record_sample_2: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_nps_2: {
    		    required: true
    		},
    		<portlet:namespace />section_h_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_remarks_3: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_h_3"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>record_sample_3: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_nps_3: {
    		    required: true
    		},
    		<portlet:namespace />section_h_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_remarks_4: {
    		    required: function(element){
    		    	return $('input[name="<portlet:namespace/>section_h_4"]:checked').val() === 'No';
    		    },
    		    minlength: 5,
    		    maxlength: 1000
    		},
    		<portlet:namespace/>record_sample_4: {
    	      	required: true
    	    },
    	    <portlet:namespace/>record_nps_4: {
    		    required: true
    		},
    		
    	  }

    	});		  
    	
    	if ($('#form_7').valid()) {
    		
    		$('.tab_0, .tab_1, .tab_2, .tab_3, .tab_4, .tab_5, .tab_6').addClass('d-none');
            $('.tab_7').removeClass('d-none');
        }
        
    });
    
    $('.page7_Prev').click(function() {
   		$('.tab_0, .tab_1, .tab_2, .tab_3, .tab_4, .tab_5, .tab_7').addClass('d-none');
       	$('.tab_6').removeClass('d-none');
   	});
   	
	
	$('#success-message').hide();
	$('#error-message').hide();
	
    $("#form_8").on('submit', (function(e) {
    	//$("#btn-submit").click(function() {	
    		e.preventDefault();
    		
    		$("#form_8").validate({
        	  	rules: {
        	  	<portlet:namespace />section_i_1: {
        	      	required: true
        	    },
        	    <portlet:namespace/>grievance_remarks_1: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_i_1"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>grievance_sample_1: {
        	      	required: true
        	    },
        	    <portlet:namespace/>grievance_nps_1: {
        		    required: true
        		},
        		<portlet:namespace />section_i_2: {
        	      	required: true
        	    },
        	    <portlet:namespace/>grievance_remarks_2: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_i_2"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>grievance_sample_2: {
        	      	required: true
        	    },
        	    <portlet:namespace/>grievance_nps_2: {
        		    required: true
        		},
        		<portlet:namespace />section_j_1: {
        	      	required: true
        	    },
        	    <portlet:namespace/>other_remarks_1: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_j_1"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>other_sample_1: {
        	      	required: true
        	    },
        	    <portlet:namespace/>other_nps_1: {
        		    required: true
        		},
        		<portlet:namespace />section_j_2: {
        	      	required: true
        	    },
        	    <portlet:namespace/>other_remarks_2: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_j_2"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>other_sample_2: {
        	      	required: true
        	    },
        	    <portlet:namespace/>other_nps_2: {
        		    required: true
        		},
        		<portlet:namespace />section_k_1: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_remarks_1: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_k_1"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>governance_sample_1: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_nps_1: {
        		    required: true
        		},
        		<portlet:namespace />section_k_2: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_remarks_2: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_k_2"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>governance_sample_2: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_nps_2: {
        		    required: true
        		},
        		<portlet:namespace />section_k_3: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_remarks_3: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_k_3"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>governance_sample_3: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_nps_3: {
        		    required: true
        		},
        		<portlet:namespace />section_k_4: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_remarks_4: {
        		    required: function(element){
        		    	return $('input[name="<portlet:namespace/>section_k_4"]:checked').val() === 'No';
        		    },
        		    minlength: 5,
        		    maxlength: 1000
        		},
        		<portlet:namespace/>governance_sample_4: {
        	      	required: true
        	    },
        	    <portlet:namespace/>governance_nps_4: {
        		    required: true
        		},
        		<portlet:namespace />manager: {
        	      	required: true
        	    },

        	  }

        	});		  

    		if($( "#form_8" ).valid()){

    			//if($("form.form").valid()){
				    
				    var dataString = $("#form_1, #form_2, #form_3, #form_4, #form_5, #form_6, #form_7, #form_8").serializeArray();
				    
    				console.log(dataString);
    				var formData = new FormData();
    				for (var i=0; i<dataString.length; i++)
    				    formData.append(dataString[i].name, dataString[i].value);
    				//console.log(formData);
    				
    				$(".animaion").show();
    				$.ajax({
    		            type: "POST",
    		            enctype: 'multipart/form-data',
    		            processData: false,
    		            contentType: false,
    		            cache: false,
    		            url: '${annexFourB}',
    		            data: formData,
    		            success: function(data) {
    		            	console.log("Inside Success");
    		            	//$('#success-message').show();
    		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
    		            	$(".animaion").hide();
    		            	toastr.success('Form has been submitted successfully!');
    		            	//$("form.form")[0].reset();
    		            	$("#form_1")[0].reset();
    		            },
    		            error: function() {
    		            	//$('#error-message').show();
    		            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
    		            	$(".animaion").hide();
    		            	toastr.error('An error occured while submitting the form. Please try again');
    		            	console.log("Error Occured in ajax call");
    		            },
    		            complete: function(){
    		            	$(".animaion").hide();
    						setTimeout(function() { location.reload(true); }, 5000);
    			        }
    		
    		        });
    			//} 
    			
    			
    		}

    	}));
    
});
	
</script>
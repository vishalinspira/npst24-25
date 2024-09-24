<%@ include file="/init.jsp" %>
<%@page import="ComplianceForm.constants.ComplianceFormPortletKeys"%>

<portlet:resourceURL id="<%=ComplianceFormPortletKeys.complianceCertificate%>" var="complianceResourceURL" /> 

    <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
					<img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Compliance Certificate
                </div>
            </div>
        </div>
        
      <div class="form_block mx-4">
      	<form id="complianceForm" method="post">
        
        	<div class="initial_info">
					<div class="row">
	                    <div class="col">
	                        <div class="form-group">
	                            <div class="row">
									<div class="col-md-3">
										<label>No.</label>
       									<input type="text" class="form-control" id="number" name="<portlet:namespace />number" onkeypress="return onlyNumberKey(event)">
									</div>	
								</div><br>
	                                
	        					<div class="row">
									<div class="col-md-3">
										<label>Date</label>
       									<input type="date" class="form-control" id="compliance-date" name='<portlet:namespace/>complianceDate'>
									</div>	
								</div><br>		
	        							
	        					<div class="row">
									<div class="col-md-4">
										<p class="bold-text">To,</p>
					                    <p class="mb-0">Compliance Officer</p>
					                    <p class="mb-0">NPS Trust</p>
					                    <p class="mb-0">Chhatrapati Shivaji Bhavan,</p>
					                    <p class="mb-0">B-14/A, Qutab Institutional Area</p>
	        							<p class="mb-0">Katwaria Sarai, New Delhi- 110 016 </p>
									</div>	
								</div><br>		
								
								<div class="row">
				                   	<div class="col-md-12 text-center">
				                        <div class="form-group">
				                            <p class="font-weight-bold pb-3">Subject: National Pension System Trust (NPS Trust) Compliance Certificate for the FY ending  <input type="date" class="rounded border p-1" id="compFYEndingDate" name='<portlet:namespace/>compFYEndingDate'></p>
				                        </div>
				                   	</div>
				                   	
								</div>

	        					<div class="row">
									<div class="col-md-12">
										<p>I certify that the following are in accordance with the
											functions, duties and obligations of the Trustee Bank prescribed
											in the PFRDA Act, relevant regulations Trust Deed, Guidelines
											from the Authority and NPS Trust, issued from time to time, and
											are in accordance with the examination carried out by me/us and
											explanations furnished by Trustee Bank officials for FY ending  
											<input type="date" class="rounded border p-1" id="tbOfficialsFYEndingDate" name='<portlet:namespace/>tbOfficialsFYEndingDate'>
											</p>
									</div>	
								</div>		
	        							
								<div class="row text-center">
									<div class="col-md-12">
										<button type="button" class="nps-btn initialNext">Next</button>
									</div>
								</div>		

	        				</div>
	        			</div>
	        		</div>
        		</div>

            <div id="page1" class="trustee_bank d-none">
                <h5>A . Trustee Bank</h5>
                <hr>
                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(i)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is ensuring that it is managed in accordance with the provisions of the PFRDA Act, Trust Deed and all relevant regulations, guidelines and directions issued by the Authority and NPS Trust? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-1" name='<portlet:namespace/>trustee-selection-1' value="Yes">
                            <label class="form-check-label" for="trustee-selection-1">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-2" name='<portlet:namespace/>trustee-selection-1' value="No">
                            <label class="form-check-label" for="trustee-selection-2">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-1" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-1'></textarea>
                        	<span id="tr_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(ii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is reviewing the schemes’ performance and transactions carried out by it, and has submitted all required exception reports along with its recommendations to the Authority and NPS Trust, as applicable?
                            </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-3" name='<portlet:namespace/>trustee-selection-2' value="Yes">
                            <label class="form-check-label" for="trustee-selection-3">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-4" name='<portlet:namespace/>trustee-selection-2' value="No">
                            <label class="form-check-label" for="trustee-selection-4">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-2" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-2'></textarea>
                        	<span id="tr_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank has entered into an investment management agreement  with NPS Trust and all such other agreements as specified in the PFRDA guidelines issued from time to time, for the protection of subscribers’ interest
                                and information? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-5" name='<portlet:namespace/>trustee-selection-3' value="Yes">
                            <label class="form-check-label" for="trustee-selection-5">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-6" name='<portlet:namespace/>trustee-selection-3' value="No">
                            <label class="form-check-label" for="trustee-selection-6">&nbsp; No</label>
                        </div>

                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-3" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-3'></textarea>
                        	<span id="tr_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iv)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank acts upon communication in writing received from NPS Trust informing it of the deficiencies and reports to NPS Trust on the rectification of such deficiencies? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-7" name='<portlet:namespace/>trustee-selection-4' value="Yes">
                            <label class="form-check-label" for="trustee-selection-7">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-8" name='<portlet:namespace/>trustee-selection-4' value="No">
                            <label class="form-check-label" for="trustee-selection-8">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-4" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-4'></textarea>
                        	<span id="tr_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(v)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank has submitted and on time the audited scheme financials, internal audit reports, inspection, compliance reports and any other reports, as specified by the Authority and / or NPS Trust, to the Authority
                                / NPS Trust, as applicable? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-9" name='<portlet:namespace/>trustee-selection-5' value="Yes">
                            <label class="form-check-label" for="trustee-selection-9">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-10" name='<portlet:namespace/>trustee-selection-5' value="No">
                            <label class="form-check-label" for="trustee-selection-10">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-5" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-5'></textarea>
                       		<span id="tr_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(vi)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether with observations and recommendations, if any, made by NPS Trust on the above referred reports were acted upon and / or responded to and on time? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-11" name='<portlet:namespace/>trustee-selection-6' value="Yes">
                            <label class="form-check-label" for="trustee-selection-11">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-12" name='<portlet:namespace/>trustee-selection-6' value="No">
                            <label class="form-check-label" for="trustee-selection-12">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-6" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-6'></textarea>
                        	<span id="tr_rem_6_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(vii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Neither any assets have been acquired out of subscribers�� funds nor any liability assumed which results in encumbrance of the property of the National Pension System Trust or that of the subscribers in any way. </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-13" name='<portlet:namespace/>trustee-selection-7' value="Yes">
                            <label class="form-check-label" for="trustee-selection-13">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="trustee-selection-14" name='<portlet:namespace/>trustee-selection-7' value="No">
                            <label class="form-check-label" for="trustee-selection-14">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="trustee-remarks-7" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>trustee-remarks-7'></textarea>
                        	<span id="tr_rem_7_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row text-center">
                    <div class="col-md-12">
                    	<button class="nps-btn initialPrev">Previous</button>
                        <button class="nps-btn trusteeNext">Next</button>
                    </div>
                </div>
            </div>


            <div id="page2" class="monitoring_audit d-none">
                <h5>B . Monitoring of Audit and Compliance reports of Intermediaries</h5>
                <hr>
                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(i)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether internal audit reports at regular intervals for audits conducted by  auditors appointed by NPS Trust as well as  by the Trustee Bank itself and the deviations mentioned therein have been shared with the Board and reported
                                to PFRDA and NPS Trust, as applicable? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="audit-selection-1" name='<portlet:namespace/>audit-selection-1' value="Yes">
                            <label class="form-check-label" for="audit-selection-1">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="audit-selection-2" name='<portlet:namespace/>audit-selection-1' value="No">
                            <label class="form-check-label" for="audit-selection-2">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="audit-remarks-1" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>audit-remarks-1'></textarea>
                        	<span id="au_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(ii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether compliance certificates at regular intervals have been submitted to NPS Trust? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="audit-selection-3" name='<portlet:namespace/>audit-selection-2' value="Yes">
                            <label class="form-check-label" for="audit-selection-3">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="audit-selection-4" name='<portlet:namespace/>audit-selection-2' value="No">
                            <label class="form-check-label" for="audit-selection-4">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="audit-remarks-2" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>audit-remarks-2'></textarea>
                        	<span id="au_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether NPS Trust’s observations on the reports of the auditor and compliance reports have been acted upon and / or responded to and on time? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="audit-selection-5" name='<portlet:namespace/>audit-selection-3' value="Yes">
                            <label class="form-check-label" for="audit-selection-5">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="audit-selection-6" name='<portlet:namespace/>audit-selection-3' value="No">
                            <label class="form-check-label" for="audit-selection-6">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="audit-remarks-3" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>audit-remarks-3'></textarea>
                        	<span id="au_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row text-center">
                    <div class="col-md-12">
                        <button class="nps-btn trusteePrev">Previous</button>
                        <button class="nps-btn monitoringNext">Next</button>
                    </div>
                </div>
            </div>
			
            <div id="page3" class="monitoring_operations d-none">
                <h5>C . Monitoring of operations and service level activities of intermediaries</h5>
                <hr>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(i)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether all operational agreements under the National Pension System with NPS Trust and all other parties, as required, are executed and valid as on date? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-1" name='<portlet:namespace/>operations-selection-1' value="Yes">
                            <label class="form-check-label" for="operations-selection-1">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-2" name='<portlet:namespace/>operations-selection-1' value="No">
                            <label class="form-check-label" for="operations-selection-2">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="operations-remarks-1" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>operations-remarks-1'></textarea>
                        	<span id="op_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(ii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Where it has been observed/found by NPS Trust or the Trustee Bank itself that the conduct of its business is not in accordance with applicable regulations, guidelines, notifications, circular issued and operational agreements, whether
                                any remedial steps have been taken and whether NPS Trust and PFRDA, as required, have been informed regarding violation and subsequent action, if any. </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-3" name='<portlet:namespace/>operations-selection-2' value="Yes">
                            <label class="form-check-label" for="operations-selection-3">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-4" name='<portlet:namespace/>operations-selection-2' value="No">
                            <label class="form-check-label" for="operations-selection-4">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="operations-remarks-2" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>operations-remarks-2'></textarea>
                       		<span id="op_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether segregated bank accounts and other records pertaining to activities under National Pension System are being maintained? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-5" name='<portlet:namespace/>operations-selection-3' value="Yes">
                            <label class="form-check-label" for="operations-selection-5">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-6" name='<portlet:namespace/>operations-selection-3' value="No">
                            <label class="form-check-label" for="operations-selection-6">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="operations-remarks-3" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>operations-remarks-3'></textarea>
                        	<span id="op_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iv)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is exercising all due diligence and vigilance in carrying out its duties and in protecting the rights and interests of the subscribers and is complying with the terms and conditions of Service Level
                                Agreements (SLA) & guidelines issued by PFRDA and NPS Trust ? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-7" name='<portlet:namespace/>operations-selection-4' value="Yes">
                            <label class="form-check-label" for="operations-selection-7">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="operations-selection-8" name='<portlet:namespace/>operations-selection-4' value="No">
                            <label class="form-check-label" for="operations-selection-8">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="operations-remarks-4" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>operations-remarks-4'></textarea>
                        	<span id="op_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="col-md-12">
                        <button class="nps-btn monitoringPrev">Previous</button>
                        <button class="nps-btn monitoringOperNext">Next</button>
                    </div>
                </div>
            </div>


            <div id="page4" class="protection_assets d-none">
                <h5>D . Protection of Assets of Beneficial Owners</h5>
                <hr>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(i)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Where Trustee Bank has custody or under its control any property of the Trust, whether it is being held in the interest of beneficiaries? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-1" name='<portlet:namespace/>assets-selection-1' value="Yes">
                            <label class="form-check-label" for="assets-selection-1">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-2" name='<portlet:namespace/>assets-selection-1' value="No">
                            <label class="form-check-label" for="assets-selection-2">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="assets-remarks-1" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>assets-remarks-1'></textarea>
                        	<span id="a_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(ii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether Trustee Bank is ensuring that any NPS Trust property under its custody / control is properly protected, held and administered by the proper persons? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-3" name='<portlet:namespace/>assets-selection-2' value="Yes">
                            <label class="form-check-label" for="assets-selection-3">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-4" name='<portlet:namespace/>assets-selection-2' value="No">
                            <label class="form-check-label" for="assets-selection-4">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="assets-remarks-2" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>assets-remarks-2'></textarea>
                        	<span id="a_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Where the Trustee Bank is supervising the collection of any income due on assets held in the name of the National Pension System Trust, claiming any repayment of tax and holding any income received in trust for the beneficiaries,
                                the same are in accordance with the Trust Deed and the regulations, guidelines or directions issued by the Authority and NPS Trust. </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-5" name='<portlet:namespace/>assets-selection-3' value="Yes">
                            <label class="form-check-label" for="assets-selection-5">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-6" name='<portlet:namespace/>assets-selection-3' value="No">
                            <label class="form-check-label" for="assets-selection-6">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="assets-remarks-3" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>assets-remarks-3'></textarea>
                        	<span id="a_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iv)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is giving true and accurate accounts of all income due to it in the course of its role in the management of the National Pension System or in relation to carrying out the objectives and purpose of the
                                National Pension System ? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-7" name='<portlet:namespace/>assets-selection-4' value="Yes">
                            <label class="form-check-label" for="assets-selection-7">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="assets-selection-8" name='<portlet:namespace/>assets-selection-4' value="No">
                            <label class="form-check-label" for="assets-selection-8">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="assets-remarks-4" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>assets-remarks-4'></textarea>
                        	<span id="a_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row text-center">
                    <div class="col-md-12">
                        <button class="nps-btn monitoringOperPrev">Previous</button>
                        <button class="nps-btn trustObligNext">Next</button>
                    </div>
                </div>
            </div>

            <div id="page5" class="trust_obligations d-none">
                <h5>E . NPS Trust Obligations</h5>
                <hr>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(i)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is maintaining records of the decisions taken by its Board at its meetings and the minutes of the meetings? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-1" name='<portlet:namespace/>obligations-selection-1' value="Yes">
                            <label class="form-check-label" for="obligations-selection-1">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-2" name='<portlet:namespace/>obligations-selection-1' value="No">
                            <label class="form-check-label" for="obligations-selection-2">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="obligations-remarks-1" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>obligations-remarks-1'></textarea>
                        	<span id="ob_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(ii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Where applicable and required, the intermediary is taking adequate steps to spread awareness on various aspects of NPS to the subscribers. </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-3" name='<portlet:namespace/>obligations-selection-2' value="Yes">
                            <label class="form-check-label" for="obligations-selection-3">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-4" name='<portlet:namespace/>obligations-selection-2' value="No">
                            <label class="form-check-label" for="obligations-selection-4">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="obligations-remarks-2" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>obligations-remarks-2'></textarea>
                        	<span id="ob_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether Trustee Bank fulfills its duty to provide or cause to provide information to the beneficiaries, the Authority and NPS Trust, as may be required, from time to time? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-5" name='<portlet:namespace/>obligations-selection-3' value="Yes">
                            <label class="form-check-label" for="obligations-selection-5">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-6" name='<portlet:namespace/>obligations-selection-3' value="No">
                            <label class="form-check-label" for="obligations-selection-6">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="obligations-remarks-3" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>obligations-remarks-3'></textarea>
                        	<span id="ob_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iv)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether subscriber grievances have been resolved in accordance with the Trustee Bank Regulatory and Development Authority (Redressal of Subscriber Grievance) Regulations, 2015? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-7" name='<portlet:namespace/>obligations-selection-4' value="Yes">
                            <label class="form-check-label" for="obligations-selection-7">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-8" name='<portlet:namespace/>obligations-selection-4' value="No">
                            <label class="form-check-label" for="obligations-selection-8">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="obligations-remarks-4" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>obligations-remarks-4'></textarea>
                        	<span id="ob_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(v)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Where applicable, whether the Exit /Withdrawal of the subscriber from the National Pension System has been undertaken as per the provisions of regulations and guidelines,and deviations reported to PFRDA and NPS Trust? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-9" name='<portlet:namespace/>obligations-selection-5' value="Yes">
                            <label class="form-check-label" for="obligations-selection-9">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="obligations-selection-10" name='<portlet:namespace/>obligations-selection-5' value="No">
                            <label class="form-check-label" for="obligations-selection-10">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="obligations-remarks-5" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>obligations-remarks-5'></textarea>
                        	<span id="ob_rem_5_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="col-md-12">
                        <button class="nps-btn trustObligPrev">Previous</button>
                        <button class="nps-btn accAdmiNext">Next</button>
                    </div>
                </div>
            </div>

            <div id="page6" class="accounts_administration d-none">
                <h5>F . Accounts and Administration related obligations</h5>
                <hr>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(i)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank prepares once every year, an annual report of its activities under the National Pension System? Whether a copy of the same along with the copy of the audited accounts with observations at the end of
                                the financial year is being furnished to the Authority and NPS Trust before May 31st of the same calendar year? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-1" name='<portlet:namespace/>accounts-selection-1' value="Yes">
                            <label class="form-check-label" for="accounts-selection-1">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-2" name='<portlet:namespace/>accounts-selection-1' value="No">
                            <label class="form-check-label" for="accounts-selection-2">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="accounts-remarks-1" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>accounts-remarks-1'></textarea>
                        	<span id="ac_rem_1_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(ii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is maintaining proper books of accounts and records with respect to all sums of money received and expended by it under the National Pension System? </p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-3" name='<portlet:namespace/>accounts-selection-2' value="Yes">
                            <label class="form-check-label" for="accounts-selection-3">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-4" name='<portlet:namespace/>accounts-selection-2' value="No">
                            <label class="form-check-label" for="accounts-selection-4">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="accounts-remarks-2" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>accounts-remarks-2'></textarea>
                        	<span id="ac_rem_2_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iii)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>Whether the Trustee Bank is furnishing quarterly information to NPS Trust as mentioned below: </p>
                            <p>(i) report on the activities of the Trustee Bank; </p>
                            <p>(ii) a certificate stating that it has satisfied itself that there have been no instances of self-dealing or front running by any of the trustees, directors and key personnel of the Trustee Bank; </p>
                            <p>(iii) a certificate to the effect that the Trustee Bank has been managing its affairs under NPS independently of any other activities and that it has taken adequate steps to ensure that the interest of subscribers are protected;</p>
                            <p>(iv) exception reports; and</p>
                            <p>(v) recommendations on further course of action on deviations?</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-5" name='<portlet:namespace/>accounts-selection-3' value="Yes">
                            <label class="form-check-label" for="accounts-selection-5">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-6" name='<portlet:namespace/>accounts-selection-3' value="No">
                            <label class="form-check-label" for="accounts-selection-6">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea class="form-control" id="accounts-remarks-3" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>accounts-remarks-3'></textarea>
                        	<span id="ac_rem_3_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1">
                        <div class="form-group">
                            <p>(iv)</p>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <p>(i) Whether the Trustee Bank has and is following its administrative & financial rules? </p>
                            <p>(ii) All important communications/letters issued by GOI/ Authority, NPS Trust or other stakeholders have been attended to promptly and in a time-bound manner.</p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-7" name='<portlet:namespace/>accounts-selection-4' value="Yes">
                            <label class="form-check-label" for="accounts-selection-7">&nbsp; Yes</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="accounts-selection-8" name='<portlet:namespace/>accounts-selection-4' value="No">
                            <label class="form-check-label" for="accounts-selection-8">&nbsp; No</label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                        	<textarea rows="4" class="form-control" id="accounts-remarks-4" maxlength="80" placeholder="Remarks if any" name='<portlet:namespace/>accounts-remarks-4'></textarea>
                        	<span id="ac_rem_4_error" class="text-danger"></span>
                        </div>
                    </div>
                </div>
                
                <!-- <div class="row">
					<div class="col-md-4 signature-sec">
	                	<div id="signature"></div>
	                	<div class="text-center d-flex mt-2 sign-btn">
		                	<button class="nps-btn mr-3" type='button' id='verifySign'>Preview</button>
	                		<button class="nps-btn" type='button' id="clearSign">Clear</button>
		                </div>
	                </div>
	                <div class="col-md-8 text-right preview-sec">
	                	<input class="form-control d-none" id='base64Val'>
						<img src='' id='sign_prev' style='display: none;' />
	                </div>
                </div>  -->
				
				<div class="row text-center">
                    <div class="col-md-12">
                        <button class="nps-btn accAdmiPrev">Previous</button>
                        <button class="nps-btn lastDetailsNext">Next</button>
                    </div>
                </div>

            </div>
            
            <div class="last_details d-none">
	                <div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<div class="row">
									<div class="col-md-12">
									<p>Certified that the information given herein are correct
										and complete to the best of my knowledge and belief. All
										pending items/actions have been duly mentioned under remarks
										and their completion would be reported in the monthly progress
										report to be submitted by 10th of subsequent month till all the
										issues are resolved.</p>
									</div>	
								</div><br>
						
	                			<div class="row">
	                				<div class="col-md-6">
	                					<div class="col-md-6">
		                					<div class="form-group">
												<label>Signature</label>
												<input type="text" class="form-control" id="signature1" name='<portlet:namespace/>signature1'>
											</div>
										</div>
	                				
		                				<div class="col-md-6">
											<label>Compliance Officer, Trustee Bank</label>
										</div>	
										
										<div class="col-md-6">
											<div class="form-group">
												<label>Name of the Compliance Officer </label>
												<input type="text" class="form-control" id="compOfficerName1" name='<portlet:namespace/>compOfficerName1'>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
							                	<label>Date:</label>
							                    <input type="date" class="form-control" id="sign-date-1" name='<portlet:namespace/>signDate1'>
							                    <span id="sign-date-error-1" class="text-danger"></span>
							                </div>
						                </div>
						                
						                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Place:</label>
							                    <input type="text" class="form-control" id="place1" name='<portlet:namespace/>place1'>
							                    <span id="place-error-1" class="text-danger"></span>
							                </div>
										</div>
	                				
	                				</div>
	                				
	                				<div class="col-md-6">
		                				<div class="col-md-6">
		                					<div class="form-group">
												<label>Signature</label>
												<input type="text" class="form-control" id="signature2" name='<portlet:namespace/>signature2'>
											</div>
										</div>
										
										<div class="col-md-6">
											<label>Principal Officer, Trustee Bank</label>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label>Name</label>
												<input type="text" class="form-control" id="compOfficerName2" name='<portlet:namespace/>compOfficerName2'>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
							                	<label>Date:</label>
							                    <input type="date" class="form-control" id="sign-date-2" name='<portlet:namespace/>signDate2'>
							                    <span id="sign-date-error-2" class="text-danger"></span>
							                </div>
						                </div>
						                
						                <div class="col-md-6">
							                <div class="form-group">
							                	<label>Place:</label>
							                    <input type="text" class="form-control" id="place2" name='<portlet:namespace/>place2'>
							                    <span id="place-error-2" class="text-danger"></span>
							                </div>
						                </div>
										
	                				</div>
								</div>					
				
				                <div class="row text-center">
				                    <div class="col-md-12">
				                        <button class="nps-btn lastDetailsPrev">Previous</button>
				                        <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
				                    </div>
				                </div>
								
	                		</div>
	                	</div>
	                </div>
                </div>
        </form>
      	
	</div>
	
	<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div> 
        
</div>
    
<style>
.subject_class {
	font-weight: bold;
    text-decoration: underline;
}

.bold-text {
	font-weight: bold;
    margin-bottom: 0;
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
		  "timeOut": 9000,
		  "extendedTimeOut": 1000
		}

	    $("#complianceForm").on('submit', (function(e) {
	        e.preventDefault();
	    })); 
	    
	    //NEXT-PREVIOUS
	    
	    $('.initialNext').click(function() {
	    	console.log("initialNext clicked");
	    	/* var form = $("#complianceForm");
	        form.validate({
	          rules: {
	        	  <portlet:namespace />number: {
	              required: true,
	              minlength: 6
	            }
	          },
	          messages: {
	        	  <portlet:namespace />number: {
	              required: "This is required.",
	              minlength: "Too short."
	            }
	          }
	        });
	        if (form.valid() === true){
	        	console.log("form is valid");
	        	$('.initial_info').addClass('d-none');
		        $('.trustee_bank').removeClass('d-none');
	        }
	        console.log("after click"); */
	        
	    	$('.initial_info').addClass('d-none');
	        $('.trustee_bank').removeClass('d-none');
	        
	    });
	    
	    $('.initialPrev').click(function() {
	        $('.trustee_bank').addClass('d-none');
	        $('.initial_info').removeClass('d-none');
	    });
	    
	    /* $('.trusteeNext').click(function() {
	        $('.initial_info, .trustee_bank').addClass('d-none');
	        $('.monitoring_audit').removeClass('d-none');
	    }); */
	    
	    $('.trusteeNext').click(function() {
	    	var flag = false;
	    	
	    	// for 1st radio
	    	var radioValue = $('input[name="<portlet:namespace/>trustee-selection-1"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-1').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_1_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_1_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_1_error").html("");
	    	}
	    	
	    	// for 2nd radio
	    	radioValue = $('input[name="<portlet:namespace/>trustee-selection-2"]:checked').val();
	    	console.log("Val2>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-2').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_2_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_2_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_2_error").html("");
	    	}
	    	
	    	// for 3rd radio
	    	radioValue = $('input[name="<portlet:namespace/>trustee-selection-3"]:checked').val();
	    	console.log("Val3>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-3').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_3_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_3_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_3_error").html("");
	    	}
	    	
	    	// for 4th radio
	    	radioValue = $('input[name="<portlet:namespace/>trustee-selection-4"]:checked').val();
	    	console.log("Val4>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-4').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_4_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_4_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_4_error").html("");
	    	}
	    	
	    	// for 5th radio
	    	radioValue = $('input[name="<portlet:namespace/>trustee-selection-5"]:checked').val();
	    	console.log("Val5>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-5').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_5_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_5_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_5_error").html("");
	    	}
	    	
	    	// for 6th radio
	    	radioValue = $('input[name="<portlet:namespace/>trustee-selection-6"]:checked').val();
	    	console.log("Val6>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-6').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_6_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_6_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_6_error").html("");
	    	}
	    	
	    	// for 7th radio
	    	radioValue = $('input[name="<portlet:namespace/>trustee-selection-7"]:checked').val();
	    	console.log("Val7>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#trustee-remarks-7').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#tr_rem_7_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#tr_rem_7_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#tr_rem_7_error").html("");
	    	}

	    	//check if flag is true
	    	if(flag == true) {
	    		$('.initial_info, .trustee_bank').addClass('d-none');
	    		$('.monitoring_audit').removeClass('d-none');
	    	}
	    });
	    
	    $('.trusteePrev').click(function() {
	        $('.initial_info, .monitoring_audit').addClass('d-none');
	        $('.trustee_bank').removeClass('d-none');
	    });
	
	    /* $('.monitoringNext').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit').addClass('d-none');
	        $('.monitoring_operations').removeClass('d-none');
	    }); */
	    
	    $('.monitoringNext').click(function() {
	    	var flag = false;
	    	
	    	// for 1st radio
	    	var radioValue = $('input[name="<portlet:namespace/>audit-selection-1"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#audit-remarks-1').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#au_rem_1_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#au_rem_1_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#au_rem_1_error").html("");
	    	}
	    	
	    	// for 2nd radio
	    	radioValue = $('input[name="<portlet:namespace/>audit-selection-2"]:checked').val();
	    	console.log("Val2>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#audit-remarks-2').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#au_rem_2_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#au_rem_2_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#au_rem_2_error").html("");
	    	}
	    	
	    	// for 3rd radio
	    	radioValue = $('input[name="<portlet:namespace/>audit-selection-3"]:checked').val();
	    	console.log("Val3>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#audit-remarks-3').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#au_rem_3_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#au_rem_3_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#au_rem_3_error").html("");
	    	}
	    	
	    	//check if flag is true
	    	if(flag == true) {
	    		$('.initial_info, .trustee_bank, .monitoring_audit').addClass('d-none');
				$('.monitoring_operations').removeClass('d-none');
	    	}
	    });

	    $('.monitoringPrev').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_operations').addClass('d-none');
	        $('.monitoring_audit').removeClass('d-none');
	    });
	
	    /* $('.monitoringOperNext').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_operations, .monitoring_audit').addClass('d-none');
	        $('.protection_assets').removeClass('d-none');
	    }); */
	    
	    
	    $('.monitoringOperNext').click(function() {
	    	var flag = false;
	    	
	    	// for 1st radio
	    	var radioValue = $('input[name="<portlet:namespace/>operations-selection-1"]:checked').val();
	    	//console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#operations-remarks-1').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#op_rem_1_error").html("");
	    			flag = true;
	    			//console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#op_rem_1_error").html("This field is required");
	    			flag = false;
	    			//console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		//console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#op_rem_1_error").html("");
	    	}
	    	
	    	// for 2nd radio
	    	radioValue = $('input[name="<portlet:namespace/>operations-selection-2"]:checked').val();
	    	//console.log("Val2>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#operations-remarks-2').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#op_rem_2_error").html("");
	    			flag = true;
	    			//console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#op_rem_2_error").html("This field is required");
	    			flag = false;
	    			//console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		//console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#op_rem_2_error").html("");
	    	}
	    	
	    	// for 3rd radio
	    	radioValue = $('input[name="<portlet:namespace/>operations-selection-3"]:checked').val();
	    	//console.log("Val3>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#operations-remarks-3').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#op_rem_3_error").html("");
	    			flag = true;
	    			//console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#op_rem_3_error").html("This field is required");
	    			flag = false;
	    			//console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		//console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#op_rem_3_error").html("");
	    	}
			
			
			// for 4th radio
	    	var radioValue = $('input[name="<portlet:namespace/>operations-selection-4"]:checked').val();
	    	//console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#operations-remarks-4').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#op_rem_4_error").html("");
	    			flag = true;
	    			//console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#op_rem_4_error").html("This field is required");
	    			flag = false;
	    			//console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		//console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#op_rem_4_error").html("");
	    	}

	    	//check if flag is true
	    	if(flag == true) {
	    		$('.initial_info, .trustee_bank, .monitoring_operations, .monitoring_audit').addClass('d-none');
				$('.protection_assets').removeClass('d-none');
	    	}
	    });
	
	    $('.monitoringOperPrev').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets').addClass('d-none');
	        $('.monitoring_operations').removeClass('d-none');
	    });
	
	    /* $('.trustObligNext').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations').addClass('d-none');
	        $('.trust_obligations').removeClass('d-none');
	    }); */
	    
	    
	    $('.trustObligNext').click(function() {
	    	var flag = false;
	    	
	    	// for 1st radio
	    	var radioValue = $('input[name="<portlet:namespace/>assets-selection-1"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#assets-remarks-1').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#a_rem_1_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#a_rem_1_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#a_rem_1_error").html("");
	    	}
	    	
	    	// for 2nd radio
	    	radioValue = $('input[name="<portlet:namespace/>assets-selection-2"]:checked').val();
	    	console.log("Val2>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#assets-remarks-2').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#a_rem_2_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#a_rem_2_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#a_rem_2_error").html("");
	    	}
	    	
	    	// for 3rd radio
	    	radioValue = $('input[name="<portlet:namespace/>assets-selection-3"]:checked').val();
	    	console.log("Val3>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#assets-remarks-3').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#a_rem_3_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#a_rem_3_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#a_rem_3_error").html("");
	    	}
			
			
			// for 4th radio
	    	var radioValue = $('input[name="<portlet:namespace/>assets-selection-4"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#assets-remarks-4').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#a_rem_4_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#a_rem_4_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#a_rem_4_error").html("");
	    	}
	    	
	    	//check if flag is true
	    	if(flag == true) {
	    		$('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations').addClass('d-none');
				$('.trust_obligations').removeClass('d-none');
	    	}
	    });
	    
	
	    $('.trustObligPrev').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .monitoring_operations, .trust_obligations').addClass('d-none');
	        $('.protection_assets').removeClass('d-none');
	    });
	
	    /* $('.accAdmiNext').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations, .trust_obligations').addClass('d-none');
	        $('.accounts_administration').removeClass('d-none');
	    }); */
	    
	    
	    $('.accAdmiNext').click(function() {
	    	var flag = false;
	    	
	    	// for 1st radio
	    	var radioValue = $('input[name="<portlet:namespace/>obligations-selection-1"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#obligations-remarks-1').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ob_rem_1_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ob_rem_1_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ob_rem_1_error").html("");
	    	}
	    	
	    	// for 2nd radio
	    	radioValue = $('input[name="<portlet:namespace/>obligations-selection-2"]:checked').val();
	    	console.log("Val2>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#obligations-remarks-2').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ob_rem_2_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ob_rem_2_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ob_rem_2_error").html("");
	    	}
	    	
	    	// for 3rd radio
	    	radioValue = $('input[name="<portlet:namespace/>obligations-selection-3"]:checked').val();
	    	console.log("Val3>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#obligations-remarks-3').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ob_rem_3_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ob_rem_3_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ob_rem_3_error").html("");
	    	}
			
			
			// for 4th radio
	    	var radioValue = $('input[name="<portlet:namespace/>obligations-selection-4"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#obligations-remarks-4').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ob_rem_4_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ob_rem_4_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ob_rem_4_error").html("");
	    	}
			
			// for 5th radio
	    	var radioValue = $('input[name="<portlet:namespace/>obligations-selection-5"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#obligations-remarks-5').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ob_rem_5_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ob_rem_5_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ob_rem_5_error").html("");
	    	}

	    	//check if flag is true
	    	if(flag == true) {
	    		$('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations, .trust_obligations').addClass('d-none');
				$('.accounts_administration').removeClass('d-none');
	    	}
	    });

	    $('.accAdmiPrev').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations, .accounts_administration').addClass('d-none');
	        $('.trust_obligations').removeClass('d-none');
	    });
	    
	    /* $('.lastDetailsNext').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations, .trust_obligations, .accounts_administration').addClass('d-none');
	        $('.last_details').removeClass('d-none');
	    }); */
	    
	    
	    $('.lastDetailsNext').click(function() {
	    	var flag = false;
	    	
	    	// for 1st radio
	    	var radioValue = $('input[name="<portlet:namespace/>accounts-selection-1"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#accounts-remarks-1').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ac_rem_1_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ac_rem_1_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ac_rem_1_error").html("");
	    	}
	    	
	    	// for 2nd radio
	    	radioValue = $('input[name="<portlet:namespace/>accounts-selection-2"]:checked').val();
	    	console.log("Val2>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#accounts-remarks-2').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ac_rem_2_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ac_rem_2_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ac_rem_2_error").html("");
	    	}
	    	
	    	// for 3rd radio
	    	radioValue = $('input[name="<portlet:namespace/>accounts-selection-3"]:checked').val();
	    	console.log("Val3>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#accounts-remarks-3').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ac_rem_3_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ac_rem_3_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ac_rem_3_error").html("");
	    	}
			
			
			// for 4th radio
	    	var radioValue = $('input[name="<portlet:namespace/>accounts-selection-4"]:checked').val();
	    	console.log("Val1>> ",radioValue);
	    	if(radioValue === "No") {
	    		if ($.trim($('#accounts-remarks-4').val()).length > 0)
	    		{
	    			$(this).parent().parent().parent().find("#ac_rem_4_error").html("");
	    			flag = true;
	    			console.log("No, but text added. jumping to next radio");
	    		}
	    		else {
	    			$(this).parent().parent().parent().find("#ac_rem_4_error").html("This field is required");
	    			flag = false;
	    			console.log("No, and no text added. returning false");
	    			return false;
	    		}
	    		
	    	}else {
	    		console.log("Yes or Undefined value, jumping to next radio");
	    		flag = true;
	    		$(this).parent().parent().parent().find("#ac_rem_4_error").html("");
	    	}

	    	//check if flag is true
	    	if(flag == true) {
	    		$('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations, .trust_obligations, .accounts_administration').addClass('d-none');
				$('.last_details').removeClass('d-none');
	    	}
	    });

	    $('.lastDetailsPrev').click(function() {
	        $('.initial_info, .trustee_bank, .monitoring_audit, .protection_assets, .monitoring_operations, .trust_obligations, .last_details').addClass('d-none');
	        $('.accounts_administration').removeClass('d-none');
	    });
	    
	    /* $("#complianceForm").validate({
	  	rules: {
		<portlet:namespace />number: {
	      	required: true
	    },
	    <portlet:namespace/>complianceDate: {
		    required: true
		},
		<portlet:namespace/>compFYEndingDate:{
			required: true
		},
		<portlet:namespace/>tbOfficialsFYEndingDate:{
			required: true,
		},
		<portlet:namespace/>compOfficerName1:{
			required: true
		},
		<portlet:namespace/>signDate1:{
			required: true
		},
		<portlet:namespace/>place1:{
			required: true
		},
		<portlet:namespace/>compOfficerName2:{
			required: true
		},
		<portlet:namespace/>signDate2:{
			required: true
		},
		<portlet:namespace/>place2:{
			required: true
		},
	  }

	}); */

	});
	
	function onlyNumberKey(evt) {
    
	    // Only ASCII character in that range allowed
	    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
	    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 48 || ASCIICode > 57))
	        return false;
	    return true;
	}
	
	
	
	//$("#complianceForm").on('submit', (function(e) {
	$("#btn-submit").click(function() {
		
        console.log("Insert button click");
        
        //e.preventDefault();
        
        //if($( "#complianceForm" ).valid()) {
	        var number = $("#number").val();
	        var complianceDate = $("#complianceDate").val();
	        var compFYEndingDate = $("#compFYEndingDate").val();
	        var tbOfficialsFYEndingDate = $("#tbOfficialsFYEndingDate").val();
	        
	        var compOfficerName_1 = $("#compOfficerName1").val();
	        var signDate_1 = $("#signDate1").val();
	        var place_1 = $("#place1").val();
	        
	        var compOfficerName_2 = $("#compOfficerName2").val();
	        var signDate_2 = $("#signDate2").val();
	        var place_2 = $("#place2").val();
	        
	        var tr_1 = $('input[name="<portlet:namespace/>trustee-selection-1"]:checked').val();
	        
	        //console.log("tr_1: ", tr_1);
	        var trem_1 = $("#trustee-remarks-1").val();
	        //console.log("trem_1: ", trem_1);
	        var tr_2 = $('input[name="<portlet:namespace/>trustee-selection-2"]:checked').val();
	        var trem_2 = $("#trustee-remarks-2").val();
	        var tr_3 = $('input[name="<portlet:namespace/>trustee-selection-3"]:checked').val();
	        var trem_3 = $("#trustee-remarks-3").val();
	        var tr_4 = $('input[name="<portlet:namespace/>trustee-selection-4"]:checked').val();
	        var trem_4 = $("#trustee-remarks-4").val();
	        var tr_5 = $('input[name="<portlet:namespace/>trustee-selection-5"]:checked').val();
	        var trem_5 = $("#trustee-remarks-5").val();
	        var tr_6 = $('input[name="<portlet:namespace/>trustee-selection-6"]:checked').val();
	        var trem_6 = $("#trustee-remarks-6").val();
	        var tr_7 = $('input[name="<portlet:namespace/>trustee-selection-7"]:checked').val();
	        var trem_7 = $("#trustee-remarks-7").val();
	
	        var au_1 = $('input[name="<portlet:namespace/>audit-selection-1"]:checked').val();
	        var aurem_1 = $("#audit-remarks-1").val();
	        var au_2 = $('input[name="<portlet:namespace/>audit-selection-2"]:checked').val();
	        var aurem_2 = $("#audit-remarks-2").val();
	        var au_3 = $('input[name="<portlet:namespace/>audit-selection-3"]:checked').val();
	        var aurem_3 = $("#audit-remarks-3").val();
	
	        var mo_1 = $('input[name="<portlet:namespace/>operations-selection-1"]:checked').val();
	        var morem_1 = $("#operations-remarks-1").val();
	        var mo_2 = $('input[name="<portlet:namespace/>operations-selection-2"]:checked').val();
	        var morem_2 = $("#operations-remarks-2").val();
	        var mo_3 = $('input[name="<portlet:namespace/>operations-selection-3"]:checked').val();
	        var morem_3 = $("#operations-remarks-3").val();
	        var mo_4 = $('input[name="<portlet:namespace/>operations-selection-4"]:checked').val();
	        var morem_4 = $("#operations-remarks-4").val();
	
	        var pa_1 = $('input[name="<portlet:namespace/>assets-selection-1"]:checked').val();
	        var parem_1 = $("#assets-remarks-1").val();
	        var pa_2 = $('input[name="<portlet:namespace/>assets-selection-2"]:checked').val();
	        var parem_2 = $("#assets-remarks-2").val();
	        var pa_3 = $('input[name="<portlet:namespace/>assets-selection-3"]:checked').val();
	        var parem_3 = $("#assets-remarks-3").val();
	        var pa_4 = $('input[name="<portlet:namespace/>assets-selection-4"]:checked').val();
	        var parem_4 = $("#assets-remarks-4").val();
	
	        var nto_1 = $('input[name="<portlet:namespace/>obligations-selection-1"]:checked').val();
	        var ntorem_1 = $("#obligations-remarks-1").val();
	        var nto_2 = $('input[name="<portlet:namespace/>obligations-selection-2"]:checked').val();
	        var ntorem_2 = $("#obligations-remarks-2").val();
	        var nto_3 = $('input[name="<portlet:namespace/>obligations-selection-3"]:checked').val();
	        var ntorem_3 = $("#obligations-remarks-3").val();
	        var nto_4 = $('input[name="<portlet:namespace/>obligations-selection-4"]:checked').val();
	        var ntorem_4 = $("#obligations-remarks-4").val();
	        var nto_5 = $('input[name="<portlet:namespace/>obligations-selection-5"]:checked').val();
	        var ntorem_5 = $("#obligations-remarks-5").val();
	
	        var aa_1 = $('input[name="<portlet:namespace/>accounts-selection-1"]:checked').val();
	        var aarem_1 = $("#accounts-remarks-1").val();
	        var aa_2 = $('input[name="<portlet:namespace/>accounts-selection-2"]:checked').val();
	        var aarem_2 = $("#accounts-remarks-2").val();
	        var aa_3 = $('input[name="<portlet:namespace/>accounts-selection-3"]:checked').val();
	        var aarem_3 = $("#accounts-remarks-3").val();
	        var aa_4 = $('input[name="<portlet:namespace/>accounts-selection-4"]:checked').val();
	        var aarem_4 = $("#accounts-remarks-4").val();
	
	        var formData = new FormData();
	        formData.append('<portlet:namespace/>number', number);
	        formData.append('<portlet:namespace/>complianceDate', complianceDate);
	        formData.append('<portlet:namespace/>compFYEndingDate', compFYEndingDate);
	        formData.append('<portlet:namespace/>tbOfficialsFYEndingDate', tbOfficialsFYEndingDate);
	        formData.append('<portlet:namespace/>tr_1', tr_1);
	        formData.append('<portlet:namespace/>trem_1', trem_1);
	        formData.append('<portlet:namespace/>tr_2', tr_2);
	        formData.append('<portlet:namespace/>trem_2', trem_2);
	        formData.append('<portlet:namespace/>tr_3', tr_3);
	        formData.append('<portlet:namespace/>trem_3', trem_3);
	        formData.append('<portlet:namespace/>tr_4', tr_4);
	        formData.append('<portlet:namespace/>trem_4', trem_4);
	        formData.append('<portlet:namespace/>tr_5', tr_5);
	        formData.append('<portlet:namespace/>trem_5', trem_5);
	        formData.append('<portlet:namespace/>tr_6', tr_6);
	        formData.append('<portlet:namespace/>trem_6', trem_6);
	        formData.append('<portlet:namespace/>tr_7', tr_7);
	        formData.append('<portlet:namespace/>trem_7', trem_7);
	
	        formData.append('<portlet:namespace/>au_1', au_1);
	        formData.append('<portlet:namespace/>aurem_1', aurem_1);
	        formData.append('<portlet:namespace/>au_2', au_2);
	        formData.append('<portlet:namespace/>aurem_2', aurem_2);
	        formData.append('<portlet:namespace/>au_3', au_3);
	        formData.append('<portlet:namespace/>aurem_3', aurem_3);
	
	        formData.append('<portlet:namespace/>mo_1', mo_1);
	        formData.append('<portlet:namespace/>morem_1', morem_1);
	        formData.append('<portlet:namespace/>mo_2', mo_2);
	        formData.append('<portlet:namespace/>morem_2', morem_2);
	        formData.append('<portlet:namespace/>mo_3', mo_3);
	        formData.append('<portlet:namespace/>morem_3', morem_3);
	        formData.append('<portlet:namespace/>mo_4', mo_4);
	        formData.append('<portlet:namespace/>morem_4', morem_4);
	
	        formData.append('<portlet:namespace/>pa_1', pa_1);
	        formData.append('<portlet:namespace/>parem_1', parem_1);
	        formData.append('<portlet:namespace/>pa_2', pa_2);
	        formData.append('<portlet:namespace/>parem_2', parem_2);
	        formData.append('<portlet:namespace/>pa_3', pa_3);
	        formData.append('<portlet:namespace/>parem_3', parem_3);
	        formData.append('<portlet:namespace/>pa_4', pa_4);
	        formData.append('<portlet:namespace/>parem_4', parem_4);
	
	        formData.append('<portlet:namespace/>nto_1', nto_1);
	        formData.append('<portlet:namespace/>ntorem_1', ntorem_1);
	        formData.append('<portlet:namespace/>nto_2', nto_2);
	        formData.append('<portlet:namespace/>ntorem_2', ntorem_2);
	        formData.append('<portlet:namespace/>nto_3', nto_3);
	        formData.append('<portlet:namespace/>ntorem_3', ntorem_3);
	        formData.append('<portlet:namespace/>nto_4', nto_4);
	        formData.append('<portlet:namespace/>ntorem_4', ntorem_4);
	        formData.append('<portlet:namespace/>nto_5', nto_5);
	        formData.append('<portlet:namespace/>ntorem_5', ntorem_5);
	
	        formData.append('<portlet:namespace/>aa_1', aa_1);
	        formData.append('<portlet:namespace/>aarem_1', aarem_1);
	        formData.append('<portlet:namespace/>aa_2', aa_2);
	        formData.append('<portlet:namespace/>aarem_2', aarem_2);
	        formData.append('<portlet:namespace/>aa_3', aa_3);
	        formData.append('<portlet:namespace/>aarem_3', aarem_3);
	        formData.append('<portlet:namespace/>aa_4', aa_4);
	        formData.append('<portlet:namespace/>aarem_4', aarem_4);
	        
	        formData.append('<portlet:namespace/>compOfficerName_1', compOfficerName_1);
	        formData.append('<portlet:namespace/>signDate_1', signDate_1);
	        formData.append('<portlet:namespace/>place_1', place_1);
	        formData.append('<portlet:namespace/>compOfficerName_2', compOfficerName_2);
	        formData.append('<portlet:namespace/>signDate_2', signDate_2);
	        formData.append('<portlet:namespace/>place_2', place_2);
	        
	        for (var value of formData.values()) {
				   console.log(value);
				}
	        
	
	        //console.log("formdata: ", formData);
	
	        /* $.ajax({
	
	            type: "POST",
	            enctype: 'multipart/form-data',
	            processData: false,
	            contentType: false,
	            cache: false,
	            url: '${addCompCertResourceURL}',
	            data: formData,
	            success: function(taId) {
	            	//section classes
	           	 	$('.trustee_bank').removeClass('d-none');
	                $('.monitoring_audit').removeClass('d-none');
	                $('.monitoring_operations').removeClass('d-none');
	                $('.protection_assets').removeClass('d-none');
	                $('.trust_obligations').removeClass('d-none');
	                $('.accounts_administration').removeClass('d-none');
	                //button classes
	                $(".trustee_bank").find(".text-center").addClass("d-none");
	                $(".monitoring_audit").find(".text-center").addClass("d-none");
	                $(".monitoring_operations").find(".text-center").addClass("d-none");
	                $(".protection_assets").find(".text-center").addClass("d-none");
	                $(".trust_obligations").find(".text-center").addClass("d-none");
	                $(".accounts_administration").find(".text-center").addClass("d-none");
	                $(".signature-sec").addClass("invisible");
	            	$('#success-message').show();
	                if (taId > 0) {
	                    $("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
	                } else {
	                    $("#success-message").html("");
	                }
	                $("textarea").each(function() {
	                	if(!$(this).val().replace(/\s/g, '').length) {
	                		$(this).val("");
	                	}
	                    if(!$(this).val() == '') {
	                        $(this).replaceWith( "<p class='remarks-paragraph'>" + $(this).val() + "</p>" );
	                    }
	                    $(this).prop("disabled","true");
	                });
	                convertHTMLToPDF();
	            },
	            error: function(taId) {
	            	$('#error-message').show();
	                if (taId < 0) {
	                    $("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
	                } else {
	                    $("#error-message").html("");
	                }
	            }
	
	        }); */
	        $(".animaion").show();
	        $.ajax({
		        url: '<%=complianceResourceURL%>',
		        type: "POST",
		        enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
		        cache: false,
		        data:formData,
		        success: function(data) {
		        	console.log("Inside success");
		        	$(".animaion").hide();
		        	toastr.success('Form has been submitted successfully!');
		        	//$('#success-message').show();
	            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
	            	$("#complianceForm")[0].reset();
		        },
		        error: function() {
	            	console.log("Inside error");
	            	$(".animaion").hide();
	            	//$('#error-message').show();
	            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
	            	toastr.error('Something went wrong please try again!');
	            },
				complete: function(){
					$(".animaion").hide();
		        	console.log("Inside callback");
		        	setTimeout(function() { 
		        		window.location = "/dashboard";
		        	}, 5000);
		        }
	    });

	//}
	
});
//}));	    
    
</script>

<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/internalauditreport/save" var="internalauditreportURL"></portlet:resourceURL>	
	<%-- <div class="card_blocks">
	    <div class="row">
	        <div class="col">
	            <div class="page_title">
	                <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure 3a - Internal Audit Report
	            </div>
	        </div>
	    </div> --%>
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
       		<h4>Annexure 3a - Internal Audit Report submitted by custodian</h4>
				<form class="row form" id="myForm" >
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId"/>
				<input type="hidden" value="25" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
					<div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                    <div class="nps-input-box mt-0">
	                        <label for="name">Report Due Date</label>
	                        <input class="reportDate" type="date" readonly="readonly">
	                        <!-- <input type="text" id="name"  disabled> -->
	                    </div>
	                </div>
					<div class="row justify-content-center pt-3 pb-3">
						<div class="col-sm-2"></div>
						<div class="col-sm-8">
							<h6>Internal Audit Report:  This report is submitted by custodian on Quarterly basis.</h6>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<hr class="m-0"/>
					<div class="row justify-content-center pt-3">
						<div class="col-sm-2"></div>
						<div class="col-sm-8">
							<h6>AUDIT REPORT OF CUSTODIAL OPERATIONS FOR PENSION FUNDS FOR THE PERIOD</h6>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="row justify-content-center pb-3">
						<div class="col-sm-4"></div>
						<div class="col-sm-6">
							<h6>FROM 1ST JANUARY, 2021 TO 31ST MARCH, 2021</h6>
						</div>
						<div class="col-sm-2"></div>
					</div>
				<div class="statement-wrapper">
                	<div id="table" class="table-editable">
                		<div class="table-cont" >
							<table class="table" id="example1">
								<thead>
									<tr>
										<th width="5%">Sr.No</th>
										<th width="10%">Broad description</th>
										<th width="10%">Sampling as required Under Guidance Note</th>
										<th width="10%">Sampling Reported by Auditor</th>
										<th width="5%">Risk</th>
										<th width="20%">Sub- description</th>
										<th width="20%">Observations</th>
										<th width="20%">Management Response</th>
									</tr>
								</thead>
									<tr>
										<th rowspan="4">1.</th>
										<th rowspan="4">Board Meeting</th>
										<td>100%</td>
										<td>100%</td>
										<td rowspan="4">Low </td>
										<td>Composition of Board</td>
										<td><input type="text" class="observations_i_a form-control" name="<portlet:namespace />observations_i_a"></td>
										<td><input type="text" class="management_i_a form-control" name="<portlet:namespace />management_i_a"></td>
									</tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Frequency of meeting</td>
		                                <td><input type="text" class="observations_i_b form-control" name="<portlet:namespace />observations_i_b"></td>
										<td><input type="text" class="management_i_b form-control" name="<portlet:namespace />management_i_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Compliance with observations of the Board</td>
		                                <td><input type="text" class="observations_i_c form-control" name="<portlet:namespace />observations_i_c"></td>
										<td><input type="text" class="management_i_c form-control" name="<portlet:namespace />management_i_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Delegation of Authority</td>
		                                <td><input type="text" class="observations_i_d form-control" name="<portlet:namespace />observations_i_d"></td>
										<td><input type="text" class="management_i_d form-control" name="<portlet:namespace />management_i_d"></td>
	                                </tr>
	                                <tr>
		                                <th rowspan="4">2.</th>
		                                <th rowspan="4">Operational Manual/Procedure</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>To be approved by Board</td>
		                                <td><input type="text" class="observations_ii_a form-control" name="<portlet:namespace />observations_ii_a"></td>
										<td><input type="text" class="management_ii_a form-control" name="<portlet:namespace />management_ii_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Amendments, if any, to be approved by the board</td>
		                                <td><input type="text" class="observations_ii_b form-control" name="<portlet:namespace />observations_ii_b"></td>
										<td><input type="text" class="management_ii_b form-control" name="<portlet:namespace />management_ii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Frequency of review</td>
		                                <td><input type="text" class="observations_ii_c form-control" name="<portlet:namespace />observations_ii_c"></td>
										<td><input type="text" class="management_ii_c form-control" name="<portlet:namespace />management_ii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Compliance with Procedure for security documents execution as laid down Operational Manual.</td>
		                                <td><input type="text" class="observations_ii_d form-control" name="<portlet:namespace />observations_ii_d"></td>
										<td><input type="text" class="management_ii_d form-control" name="<portlet:namespace />management_ii_d"></td>
	                                </tr>
	                                <tr>
	                                	<th rowspan="9">3.</th>
										<th rowspan="9">Risk Management Policy</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="9">low</td>
		                                <td>Approved by Board of Director</td>
		                                <td><input type="text" class="observations_iii_a form-control" name="<portlet:namespace />observations_iii_a"></td>
										<td><input type="text" class="management_iii_a form-control" name="<portlet:namespace />management_iii_a"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Frequency of review </td>
		                                <td><input type="text" class="observations_iii_b form-control" name="<portlet:namespace />observations_iii_b"></td>
										<td><input type="text" class="management_iii_b form-control" name="<portlet:namespace />management_iii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Disaster recovery strategy</td>
		                                <td><input type="text" class="observations_iii_c form-control" name="<portlet:namespace />observations_iii_c"></td>
										<td><input type="text" class="management_iii_c form-control" name="<portlet:namespace />management_iii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Business Continuity Plan</td>
		                                <td><input type="text" class="observations_iii_d form-control" name="<portlet:namespace />observations_iii_d"></td>
										<td><input type="text" class="management_iii_d form-control" name="<portlet:namespace />management_iii_d"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>IT System Audit</td>
		                                <td><input type="text" class="observations_iii_e form-control" name="<portlet:namespace />observations_iii_e"></td>
										<td><input type="text" class="management_iii_e form-control" name="<portlet:namespace />management_iii_e"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Data Integrity</td>
		                                <td><input type="text" class="observations_iii_f form-control" name="<portlet:namespace />observations_iii_f"></td>
										<td><input type="text" class="management_iii_f form-control" name="<portlet:namespace />management_iii_f"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Operational risk management</td>
		                                <td><input type="text" class="observations_iii_g form-control" name="<portlet:namespace />observations_iii_g"></td>
										<td><input type="text" class="management_iii_g form-control" name="<portlet:namespace />management_iii_g"></td>
	                                </tr>
	                                 <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Employee dealing Guidelines</td>
		                                <td><input type="text" class="observations_iii_h form-control" name="<portlet:namespace />observations_iii_h"></td>
										<td><input type="text" class="management_iii_h form-control" name="<portlet:namespace />management_iii_h"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Comprehensive Insurance cover against risk</td>
		                                <td><input type="text" class="observations_iii_i form-control" name="<portlet:namespace />observations_iii_i"></td>
										<td><input type="text" class="management_iii_i form-control" name="<portlet:namespace />management_iii_i"></td>
	                               </tr>
	                               <tr>
		                                <th rowspan="4">4.</th>
		                                <th rowspan="4">Risk Management Committee</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>Composition of Risk Committee</td>
		                                <td><input type="text" class="observations_iv_a form-control" name="<portlet:namespace />observations_iv_a"></td>
										<td><input type="text" class="management_iv_a form-control" name="<portlet:namespace />management_iv_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Frequency of meeting</td>
		                                <td><input type="text" class="observations_iv_b form-control" name="<portlet:namespace />observations_iv_b"></td>
										<td><input type="text" class="management_iv_b form-control" name="<portlet:namespace />management_iv_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Minutes of meeting</td>
		                                <td><input type="text" class="observations_iv_c form-control" name="<portlet:namespace />observations_iv_c"></td>
										<td><input type="text" class="management_iv_c form-control" name="<portlet:namespace />management_iv_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Implementation of observations of the risk management committee</td>
		                                <td><input type="text" class="observations_iv_d form-control" name="<portlet:namespace />observations_iv_d"></td>
										<td><input type="text" class="management_iv_d form-control" name="<portlet:namespace />management_iv_d"></td>
	                               </tr>
	                               <tr>
		                                <th rowspan="4">5.</th>
		                                <th rowspan="4">Adequacy of Infrastructure</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>Adequacy of Physical Infrastructure </td>
		                                <td><input type="text" class="observations_v_a form-control" name="<portlet:namespace />observations_v_a"></td>
										<td><input type="text" class="management_v_a form-control" name="<portlet:namespace />management_v_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Adequacy of IT Infrastructure</td>
		                                <td><input type="text" class="observations_v_b form-control" name="<portlet:namespace />observations_v_b"></td>
										<td><input type="text" class="management_v_b form-control" name="<portlet:namespace />management_v_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Adequacy of information Security Infrastructure</td>
		                                <td><input type="text" class="observations_v_c form-control" name="<portlet:namespace />observations_v_c"></td>
										<td><input type="text" class="management_v_c form-control" name="<portlet:namespace />management_v_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Adequacy of HR Infrastructure</td>
		                                <td><input type="text" class="observations_v_d form-control" name="<portlet:namespace />observations_v_d"></td>
										<td><input type="text" class="management_v_d form-control" name="<portlet:namespace />management_v_d"></td>
	                                </tr>
	                                <tr>
		                                <th rowspan="5">6.</th>
		                                <th rowspan="5">Settlement Processing</th>
		                                <td>50% of each Scheme</td>
		                                <td>50% of each Scheme</td>
		                                <td rowspan="5">low</td>
		                                <td>Receipt of orders from Pension Funds</td>
		                                <td><input type="text" class="observations_vi_a form-control" name="<portlet:namespace />observations_vi_a"></td>
										<td><input type="text" class="management_vi_a form-control" name="<portlet:namespace />management_vi_a"></td>
	                               </tr>
	                               <tr>
		                                <td>50% of each Scheme</td>
		                                <td>50% of each Scheme</td>
		                                <td>Order matching with files received from SE’s and confirmation of match orders to SE’s</td>
		                                <td><input type="text" class="observations_vi_b form-control" name="<portlet:namespace />observations_vi_b"></td>
										<td><input type="text" class="management_vi_b form-control" name="<portlet:namespace />management_vi_b"></td>
	                                </tr>
	                                <tr>
		                                <td>50% of each Scheme</td>
		                                <td>50% of each Scheme</td>
		                                <td>Fund Settlement</td>
		                                <td><input type="text" class="observations_vi_c form-control" name="<portlet:namespace />observations_vi_c"></td>
										<td><input type="text" class="management_vi_c form-control" name="<portlet:namespace />management_vi_c"></td>
	                                </tr>
	                                <tr>
		                                <td>50% of each Scheme</td>
		                                <td>50% of each Scheme</td>
		                                <td>Confirmed and unconfirmed trades</td>
		                                <td><input type="text" class="observations_vi_d form-control" name="<portlet:namespace />observations_vi_d"></td>
										<td><input type="text" class="management_vi_d form-control" name="<portlet:namespace />management_vi_d"></td>
	                                </tr>
	                                <tr>
		                                <td>50% of each Scheme</td>
		                                <td>50% of each Scheme</td>
		                                <td>Securities settlement</td>
		                                <td><input type="text" class="observations_vi_e form-control" name="<portlet:namespace />observations_vi_e"></td>
										<td><input type="text" class="management_vi_e form-control" name="<portlet:namespace />management_vi_e"></td>
	                                </tr>
	                                <tr>
		                                <th rowspan="7">7.</th>
		                                <th rowspan="7">Scheme wise reconciliation of Holdings for each Pension Funds</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="7">low</td>
		                                <td>Scheme CG, SG, NPS LITE</td>
		                                <td><input type="text" class="observations_vii_a form-control" name="<portlet:namespace />observations_vii_a"></td>
										<td><input type="text" class="management_vii_a form-control" name="<portlet:namespace />management_vii_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Scheme E Tier I & II</td>
		                                <td><input type="text" class="observations_vii_b form-control" name="<portlet:namespace />observations_vii_b"></td>
										<td><input type="text" class="management_vii_b form-control" name="<portlet:namespace />management_vii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Scheme C Tier I & II</td>
		                                <td><input type="text" class="observations_vii_c form-control" name="<portlet:namespace />observations_vii_c"></td>
										<td><input type="text" class="management_vii_c form-control" name="<portlet:namespace />management_vii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Scheme G Tier I & II</td>
		                                <td><input type="text" class="observations_vii_d form-control" name="<portlet:namespace />observations_vii_d"></td>
										<td><input type="text" class="management_vii_d form-control" name="<portlet:namespace />management_vii_d"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Corporate CG scheme</td>
		                                <td><input type="text" class="observations_vii_e form-control" name="<portlet:namespace />observations_vii_e"></td>
										<td><input type="text" class="management_vii_e form-control" name="<portlet:namespace />management_vii_e"></td>
	                                </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>APY or Any other scheme regulated by PFRDA</td>
		                                <td><input type="text" class="observations_vii_f form-control" name="<portlet:namespace />observations_vii_f"></td>
										<td><input type="text" class="management_vii_f form-control" name="<portlet:namespace />management_vii_f"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Reconciliation between Pension Scheme a/c and CGSL</td>
		                                <td><input type="text" class="observations_vii_g form-control" name="<portlet:namespace />observations_vii_g"></td>
										<td><input type="text" class="management_vii_g form-control" name="<portlet:namespace />management_vii_g"></td>
	                               </tr>
	                               <tr>
		                                <td>8.</td>
		                                <td>Dealing Procedure (Front office) for deals executed by Pension funds</td>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>low</td>
		                                <td>Installation of voice Recording Machine System and procedure of dealing in Equity and Debt</td>
		                                <td><input type="text" class="observations_viii_a form-control" name="<portlet:namespace />observations_viii_a"></td>
										<td><input type="text" class="management_viii_a form-control" name="<portlet:namespace />management_viii_a"></td>
	                              </tr>
	                              <tr>
		                                <th rowspan="4">9.</th>
		                                <th rowspan="4">Back office Procedure</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>Deployment of adequate staff</td>
		                                <td><input type="text" class="observations_ix_a form-control" name="<portlet:namespace />observations_ix_a"></td>
										<td><input type="text" class="management_ix_a form-control" name="<portlet:namespace />management_ix_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>No password sharing between officers</td>
		                                <td><input type="text" class="observations_ix_b form-control" name="<portlet:namespace />observations_ix_b"></td>
										<td><input type="text" class="management_ix_b form-control" name="<portlet:namespace />management_ix_b"></td>
	                                </tr>
	                                <tr>
		                                <td>50% of each Scheme</td>
		                                <td>50% of each Scheme</td>
		                                <td>Accounting and settlement of deal</td>
		                                <td><input type="text" class="observations_ix_c form-control" name="<portlet:namespace />observations_ix_c"></td>
										<td><input type="text" class="management_ix_c form-control" name="<portlet:namespace />management_ix_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Deal execution through STP</td>
		                                <td><input type="text" class="observations_ix_d form-control" name="<portlet:namespace />observations_ix_d"></td>
										<td><input type="text" class="management_ix_d form-control" name="<portlet:namespace />management_ix_d"></td>
	                                </tr>
	                                <tr>
		                                <th rowspan="7">10.</th>
		                                <th rowspan="7">Safe Keeping</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="7">low</td>
		                                <td>Crediting securities to the designated demat account of specified Pension Funds</td>
		                                <td><input type="text" class="observations_x_a form-control" name="<portlet:namespace />observations_x_a"></td>
										<td><input type="text" class="management_x_a form-control" name="<portlet:namespace />management_x_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Intimating about free holdings in the securities a/c to the Pension Funds</td>
		                                <td><input type="text" class="observations_x_b form-control" name="<portlet:namespace />observations_x_b"></td>
										<td><input type="text" class="management_x_b form-control" name="<portlet:namespace />management_x_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Physical securities if any are properly coded and stored in High security vaults</td>
		                                <td><input type="text" class="observations_x_c form-control" name="<portlet:namespace />observations_x_c"></td>
										<td><input type="text" class="management_x_c form-control" name="<portlet:namespace />management_x_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Closed circuit TV with hidden camera</td>
		                                <td><input type="text" class="observations_x_d form-control" name="<portlet:namespace />observations_x_d"></td>
										<td><input type="text" class="management_x_d form-control" name="<portlet:namespace />management_x_d"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Access Control</td>
		                                <td><input type="text" class="observations_x_e form-control" name="<portlet:namespace />observations_x_e"></td>
										<td><input type="text" class="management_x_e form-control" name="<portlet:namespace />management_x_e"></td>
	                                </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Full time security staff </td>
		                                <td><input type="text" class="observations_x_f form-control" name="<portlet:namespace />observations_x_f"></td>
										<td><input type="text" class="management_x_f form-control" name="<portlet:namespace />management_x_f"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Adequate Insurance of securities</td>
		                                <td><input type="text" class="observations_x_g form-control" name="<portlet:namespace />observations_x_g"></td>
										<td><input type="text" class="management_x_g form-control" name="<portlet:namespace />management_x_g"></td>
	                               </tr>
	                                <tr>
		                                <th rowspan="4">11.</th>
		                                <th rowspan="4">Asset Servicing</th>
		                                <td>50%</td>
		                                <td>50%</td>
		                                <td rowspan="4">low</td>
		                                <td>Event tracking</td>
		                                <td><input type="text" class="observations_xi_a form-control" name="<portlet:namespace />observations_xi_a"></td>
										<td><input type="text" class="management_xi_a form-control" name="<portlet:namespace />management_xi_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Eligibility computation for all types of events both monetary and non-monetary</td>
		                                <td><input type="text" class="observations_xi_b form-control" name="<portlet:namespace />observations_xi_b"></td>
										<td><input type="text" class="management_xi_b form-control" name="<portlet:namespace />management_xi_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Timely receipt of all the dues from the Issuer and registrar of companies</td>
		                                <td><input type="text" class="observations_xi_c form-control" name="<portlet:namespace />observations_xi_c"></td>
										<td><input type="text" class="management_xi_c form-control" name="<portlet:namespace />management_xi_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Receipt and matching of Corporate Actions </td>
		                                <td><input type="text" class="observations_xi_d form-control" name="<portlet:namespace />observations_xi_d"></td>
										<td><input type="text" class="management_xi_d form-control" name="<portlet:namespace />management_xi_d"></td>
	                               </tr>
	                               <tr>
		                                <th rowspan="5">12.</th>
		                                <th rowspan="5">Reporting to Pension Funds/NPS Trust</th>
		                                <td>50%</td>
		                                <td>50%</td>
		                                <td rowspan="5">low</td>
		                                <td>Daily saleable Holding Report</td>
		                                <td><input type="text" class="observations_xii_a form-control" name="<portlet:namespace />observations_xii_a"></td>
										<td><input type="text" class="management_xii_a form-control" name="<portlet:namespace />management_xii_a"></td>
	                               </tr>
	                               <tr>
		                                <td>50%</td>
		                                <td>50%</td>
		                                <td>Daily transaction statement (on next day)</td>
		                                <td><input type="text" class="observations_xii_b form-control" name="<portlet:namespace />observations_xii_b"></td>
										<td><input type="text" class="management_xii_b form-control" name="<portlet:namespace />management_xii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Daily shortage report</td>
		                                <td><input type="text" class="observations_xii_c form-control" name="<portlet:namespace />observations_xii_c"></td>
										<td><input type="text" class="management_xii_c form-control" name="<portlet:namespace />management_xii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>50%</td>
		                                <td>50%</td>
		                                <td>Report of Corporate action</td>
		                                <td><input type="text" class="observations_xii_d form-control" name="<portlet:namespace />observations_xii_d"></td>
										<td><input type="text" class="management_xii_d form-control" name="<portlet:namespace />management_xii_d"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Corporate Action receipt reports on daily basis.</td>
		                                <td><input type="text" class="observations_xii_e form-control" name="<portlet:namespace />observations_xii_e"></td>
										<td><input type="text" class="management_xii_e form-control" name="<portlet:namespace />management_xii_e"></td>
	                               </tr>
	                               <tr>
		                                <th rowspan="4">13.</th>
		                                <th rowspan="4">Reporting Pension Funds / NPS Trust</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>Put & Call Intimation</td>
		                                <td><input type="text" class="observations_xiii_a form-control" name="<portlet:namespace />observations_xiii_a"></td>
										<td><input type="text" class="management_xiii_a form-control" name="<portlet:namespace />management_xiii_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Outstanding Corporate Action</td>
		                                <td><input type="text" class="observations_xiii_b form-control" name="<portlet:namespace />observations_xiii_b"></td>
										<td><input type="text" class="management_xiii_b form-control" name="<portlet:namespace />management_xiii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Ex-Date Report</td>
		                                <td><input type="text" class="observations_xiii_c form-control" name="<portlet:namespace />observations_xiii_c"></td>
										<td><input type="text" class="management_xiii_c form-control" name="<portlet:namespace />management_xiii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>MIS to NPS Trust and PFRDA</td>
		                                <td><input type="text" class="observations_xiii_d form-control" name="<portlet:namespace />observations_xiii_d"></td>
										<td><input type="text" class="management_xiii_d form-control" name="<portlet:namespace />management_xiii_d"></td>
	                               </tr>
	                               <tr>
		                                <th rowspan="7">14.</th>
		                                <th rowspan="7">Accounting</th>
		                                <td>25%</td>
		                                <td>25%</td>
		                                <td rowspan="7">low</td>
		                                <td>Deal tickets</td>
		                                <td><input type="text" class="observation_xiv__a form-control" name="<portlet:namespace />observations_xiv_a"></td>
										<td><input type="text" class="management_xiv_a form-control" name="<portlet:namespace />management_xiv_a"></td>
	                               </tr>
	                               <tr>
		                                <td>25%</td>
		                                <td>25%</td>
		                                <td>DIS/DIP statement & intimation to the custodian</td>
		                                <td><input type="text" class="observations_xiv_b form-control" name="<portlet:namespace />observations_xiv_b"></td>
										<td><input type="text" class="management_xiv_b form-control" name="<portlet:namespace />management_xiv_b"></td>
	                                </tr>
	                                <tr>
		                                <td>25%</td>
		                                <td>25%</td>
		                                <td>Verification of timely and accurate Settlement of trade</td>
		                                <td><input type="text" class="observations_xiv_c form-control" name="<portlet:namespace />observations_xiv_c"></td>
										<td><input type="text" class="management_xiv_c form-control" name="<portlet:namespace />management_xiv_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Compliance with accounting standards</td>
		                                <td><input type="text" class="observations_xiv_d form-control" name="<portlet:namespace />observations_xiv_d"></td>
										<td><input type="text" class="management_xiv_d form-control" name="<portlet:namespace />management_xiv_d"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Adherence to accounting policy</td>
		                                <td><input type="text" class="observations_xiv_e form-control" name="<portlet:namespace />observations_xiv_e"></td>
										<td><input type="text" class="management_xiv_e form-control" name="<portlet:namespace />management_xiv_e"></td>
	                                </tr>
	                               <tr>
		                                <td>25%</td>
		                                <td>25%</td>
		                                <td>Corporate action- bonus, rights, dividend, interest receivable</td>
		                                <td><input type="text" class="observations_xiv_f form-control" name="<portlet:namespace />observations_xiv_f"></td>
										<td><input type="text" class="management_xiv_f form-control" name="<portlet:namespace />management_xiv_f"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Outsourcing (100% of all items under the scope of accounting, if it is outsourced)</td>
		                                <td><input type="text" class="observations_xiv_g form-control" name="<portlet:namespace />observations_xiv_g"></td>
										<td><input type="text" class="management_xiv_g form-control" name="<portlet:namespace />management_xiv_g"></td>
	                               </tr>
	                               <tr>
		                                <th rowspan="3">15.</th>
		                                <th rowspan="3">Valuation of Asset Under Custody (AUC)</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="3">low</td>
		                                <td>Valuation of AUC – Equity as per guidelines</td>
		                                <td><input type="text" class="observations_xv_a form-control" name="<portlet:namespace />observations_xv_a"></td>
										<td><input type="text" class="management_xv_a form-control" name="<portlet:namespace />management_xv_a"></td>
	                               </tr>
	                               <tr>
		                                <td>50%</td>
		                                <td>50%</td>
		                                <td>Valuation of AUC – Debt as per guidelines</td>
		                                <td><input type="text" class="observations_xv_b form-control" name="<portlet:namespace />observations_xv_b"></td>
										<td><input type="text" class="management_xv_b form-control" name="<portlet:namespace />management_xv_b"></td>
	                                </tr>
	                                <tr>
		                                <td>10%</td>
		                                <td>10%</td>
		                                <td>Deviations, if any</td>
		                                <td><input type="text" class="observations_xv_c form-control" name="<portlet:namespace />observations_xv_c"></td>
										<td><input type="text" class="management_xv_c form-control" name="<portlet:namespace />management_xv_c"></td>
	                                </tr>
	                                <tr>
		                                <th rowspan="2">16.</th>
		                                <th rowspan="2">Disclosure</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="2">low</td>
		                                <td>Half yearly financial statement</td>
		                                <td><input type="text" class="observations_xvi_a form-control" name="<portlet:namespace />observations_xvi_a"></td>
										<td><input type="text" class="management_xvi_a form-control" name="<portlet:namespace />management_xvi_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Annual report of Custodian</td>
		                                <td><input type="text" class="observations_xvi_b form-control" name="<portlet:namespace />observations_xvi_b"></td>
										<td><input type="text" class="management_xvi_b form-control" name="<portlet:namespace />management_xvi_b"></td>
	                                </tr>
	                                <tr>
		                                <th rowspan="4">17.</th>
		                                <th rowspan="4">Periodical returns to Authority/ NPS Trust</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>In respect of reports submitted to PFRDA and NPST:-</td>
		                                <td><input type="text" class="observations_xvii_a form-control" name="<portlet:namespace />observations_xvii_a"></td>
										<td><input type="text" class="management_xvii_a form-control" name="<portlet:namespace />management_xvii_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Timely submission</td>
		                                <td><input type="text" class="observations_xvii_b form-control" name="<portlet:namespace />observations_xvii_b"></td>
										<td><input type="text" class="management_xvii_b form-control" name="<portlet:namespace />management_xvii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Accuracy of data</td>
		                                <td><input type="text" class="observations_xvii_c form-control" name="<portlet:namespace />observations_xvii_c"></td>
										<td><input type="text" class="management_xvii_c form-control" name="<portlet:namespace />management_xvii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Procedure of generation of data and report</td>
		                                <td><input type="text" class="observations_xvii_d form-control" name="<portlet:namespace />observations_xvii_d"></td>
										<td><input type="text" class="management_xvii_d form-control" name="<portlet:namespace />management_xvii_d"></td>
	                               </tr>
	                              <tr>
		                                <th rowspan="4">18.</th>
		                                <th rowspan="4">Compliances</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td rowspan="4">low</td>
		                                <td>Compliance to clauses of Tripartite Agreement</td>
		                                <td><input type="text" class="observations_xviii_a form-control" name="<portlet:namespace />observations_xviii_a"></td>
										<td><input type="text" class="management_xviii_a form-control" name="<portlet:namespace />management_xviii_a"></td>
	                               </tr>
	                               <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Compliance to Guidelines and Guidance note</td>
		                                <td><input type="text" class="observations_xviii_b form-control" name="<portlet:namespace />observations_xviii_b"></td>
										<td><input type="text" class="management_xviii_b form-control" name="<portlet:namespace />management_xviii_b"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Compliance to Internal Guidelines, Operational manual</td>
		                                <td><input type="text" class="observations_xviii_c form-control" name="<portlet:namespace />observations_xviii_c"></td>
										<td><input type="text" class="management_xviii_c form-control" name="<portlet:namespace />management_xviii_c"></td>
	                                </tr>
	                                <tr>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>Adequacy and efficacy of Internal Control system and procedures</td>
		                                <td><input type="text" class="observations_xviii_d form-control" name="<portlet:namespace />observations_xviii_d"></td>
										<td><input type="text" class="management_xviii_d form-control" name="<portlet:namespace />management_xviii_d"></td>
	                               </tr>
	                               <tr>
	                               		<th>19.</th>
		                                <th>Internal Audit/ Custodian/ Scheme Audit</th>
		                                <td>100%</td>
		                                <td>100%</td>
		                                <td>low</td>
		                                <td>To see the exceptions of previous internal audit & compliance there of</td>
		                                <td><input type="text" class="observations_xix_a form-control" name="<portlet:namespace />observations_xix_a"></td>
										<td><input type="text" class="management_xix_a form-control" name="<portlet:namespace />management_xix_a"></td>
	                              </tr>  
							</table>
								<br><br>
							</div>
						</div>
					</div>
					<div class="nps-input-box">
		                	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
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
		input.error {
			border:1px solid red !important;
		}
		
		/* label.error {
			display: none !important;
		} */
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		$("form.form").validate({
		  rules: {
				<portlet:namespace/>observations_i_a: {
			      required: true
			    },
			    <portlet:namespace/>management_i_a: {
				      required: true
				},
				<portlet:namespace/>observations_i_b: {
					required: true
				},
				<portlet:namespace/>management_i_b: {
					required: true
				},
				<portlet:namespace/>observations_i_c:{
					required: true
				},
				<portlet:namespace/>management_i_c:{
					required: true
				},
				<portlet:namespace/>observations_i_d:{
					required: true
				},
				<portlet:namespace/>management_i_d:{
					required: true
				},
				<portlet:namespace/>observations_ii_a:{
					required: true
				},
				<portlet:namespace/>management_ii_a: {
				      required: true
			    },
			    <portlet:namespace/>observations_ii_b: {
				      required: true
				},
				<portlet:namespace/>management_ii_b: {
					required: true
				},
				<portlet:namespace/>observations_ii_c: {
					required: true
				},
				<portlet:namespace/>management_ii_c:{
					required: true
				},
				<portlet:namespace/>observations_ii_d:{
					required: true
				},
				<portlet:namespace/>management_ii_d:{
					required: true
				},
				<portlet:namespace/>observations_iii_a:{
					required: true
				},
				<portlet:namespace/>management_iii_a:{
					required: true
				},
				<portlet:namespace/>observations_iii_b: {
				      required: true
			    },
			    <portlet:namespace/>management_iii_b: {
				      required: true
				},
				<portlet:namespace/>observations_iii_c: {
					required: true
				},
				<portlet:namespace/>management_iii_c: {
					required: true
				},
				<portlet:namespace/>observations_iii_d:{
					required: true
				},
				<portlet:namespace/>management_iii_d:{
					required: true
				},
				<portlet:namespace/>observations_iii_e:{
					required: true
				},
				<portlet:namespace/>management_iii_e:{
					required: true
				},
				<portlet:namespace/>observations_iii_f:{
					required: true
				},
				<portlet:namespace/>management_iii_f: {
				      required: true
			    },
			    <portlet:namespace/>observations_iii_g: {
				      required: true
				},
				<portlet:namespace/>management_iii_g: {
					required: true
				},
				<portlet:namespace/>observations_iii_h: {
					required: true
				},
				<portlet:namespace/>management_iii_h:{
					required: true
				},
				<portlet:namespace/>observations_iii_i:{
					required: true
				},
				<portlet:namespace/>management_iii_i:{
					required: true
				},
				<portlet:namespace/>observations_iv_a:{
					required: true
				},
				<portlet:namespace/>management_iv_a:{
					required: true
				},
				<portlet:namespace/>observations_iv_b: {
				      required: true
			    },
			    <portlet:namespace/>management_iv_b: {
				      required: true
				},
				<portlet:namespace/>observations_iv_c: {
					required: true
				},
				<portlet:namespace/>management_iv_c: {
					required: true
				},
				<portlet:namespace/>observations_iv_d:{
					required: true
				},
				<portlet:namespace/>management_iv_d:{
					required: true
				},
				<portlet:namespace/>observations_v_a:{
					required: true
				},
				<portlet:namespace/>management_v_a:{
					required: true
				},
				<portlet:namespace/>observations_v_b:{
					required: true
				},
				<portlet:namespace/>management_v_b: {
				      required: true
			    },
			    <portlet:namespace/>observations_v_c: {
				      required: true
				},
				<portlet:namespace/>management_v_c: {
					required: true
				},
				<portlet:namespace/>observations_v_d: {
					required: true
				},
				<portlet:namespace/>management_v_d:{
					required: true
				},
				<portlet:namespace/>observations_vi_a:{
					required: true
				},
				<portlet:namespace/>management_vi_a:{
					required: true
				},
				<portlet:namespace/>observations_vi_b:{
					required: true
				},
				<portlet:namespace/>management_vi_b:{
					required: true
				},
				<portlet:namespace/>observations_vi_c: {
					required: true
				},
				<portlet:namespace/>management_vi_c: {
					required: true
				},
				<portlet:namespace/>observations_vi_d:{
					required: true
				},
				<portlet:namespace/>management_vi_d:{
					required: true
				},
				<portlet:namespace/>observations_vi_e:{
					required: true
				},
				<portlet:namespace/>management_vi_e:{
					required: true
				},
				<portlet:namespace/>observations_vii_a:{
					required: true
				},
				<portlet:namespace/>management_vii_a: {
					required: true
				},
				<portlet:namespace/>observations_vii_b: {
					required: true
				},
				<portlet:namespace/>management_vii_b:{
					required: true
				},
				<portlet:namespace/>observations_vii_c:{
					required: true
				},
				<portlet:namespace/>management_vii_c:{
					required: true
				},
				<portlet:namespace/>observations_vii_d:{
					required: true
				},
				<portlet:namespace/>management_vii_d:{
					required: true
				},
				<portlet:namespace/>observations_vii_e: {
					required: true
				},
				<portlet:namespace/>management_vii_e: {
					required: true
				},
				<portlet:namespace/>observations_vii_f:{
					required: true
				},
				<portlet:namespace/>management_vii_f:{
					required: true
				},
				<portlet:namespace/>observations_vii_g:{
					required: true
				},
				<portlet:namespace/>management_vii_g:{
					required: true
				},
				<portlet:namespace/>observations_viii_a:{
					required: true
				},
				<portlet:namespace/>management_viii_a: {
					required: true
				},
				<portlet:namespace/>observations_ix_a: {
					required: true
				},
				<portlet:namespace/>management_ix_a:{
					required: true
				},
				<portlet:namespace/>observations_ix_b:{
					required: true
				},
				<portlet:namespace/>management_ix_b:{
					required: true
				},
				<portlet:namespace/>observations_ix_c:{
					required: true
				},
				<portlet:namespace/>management_ix_c:{
					required: true
				},
				<portlet:namespace/>observations_ix_d: {
					required: true
				},
				<portlet:namespace/>management_ix_d: {
					required: true
				},
				<portlet:namespace/>observations_x_a:{
					required: true
				},
				<portlet:namespace/>management_x_a:{
					required: true
				},
				<portlet:namespace/>observations_x_b:{
					required: true
				},
				<portlet:namespace/>management_x_b:{
					required: true
				},
				<portlet:namespace/>observations_x_c:{
					required: true
				},
				<portlet:namespace/>management_x_c: {
					required: true
				},
				<portlet:namespace/>observations_x_d: {
					required: true
				},
				<portlet:namespace/>management_x_d:{
					required: true
				},
				<portlet:namespace/>observations_x_e:{
					required: true
				},
				<portlet:namespace/>management_x_e:{
					required: true
				},
				<portlet:namespace/>observations_x_f:{
					required: true
				},
				<portlet:namespace/>management_x_f:{
					required: true
				},
				<portlet:namespace/>observations_x_g: {
					required: true
				},
				<portlet:namespace/>management_x_g: {
					required: true
				},
				<portlet:namespace/>observations_xi_a:{
					required: true
				},
				<portlet:namespace/>management_xi_a:{
					required: true
				},
				<portlet:namespace/>observations_xi_b:{
					required: true
				},
				<portlet:namespace/>management_xi_b:{
					required: true
				},
				<portlet:namespace/>observations_xi_c:{
					required: true
				},
				<portlet:namespace/>management_xi_c: {
					required: true
				},
				<portlet:namespace/>observations_xi_d: {
					required: true
				},
				<portlet:namespace/>management_xi_d:{
					required: true
				},
				<portlet:namespace/>observations_xii_a:{
					required: true
				},
				<portlet:namespace/>management_xii_a:{
					required: true
				},
				<portlet:namespace/>observations_xii_b:{
					required: true
				},
				<portlet:namespace/>management_xii_b:{
					required: true
				},
				<portlet:namespace/>observations_xii_c:{
					required: true
				},
				<portlet:namespace/>management_xii_c:{
					required: true
				},
				<portlet:namespace/>observations_xii_d:{
					required: true
				},
				<portlet:namespace/>management_xii_d:{
					required: true
				},
				<portlet:namespace/>observations_xii_e:{
					required: true
				},
				<portlet:namespace/>management_xii_e:{
					required: true
				},
				<portlet:namespace/>observations_xiii_a:{
					required: true
				},
				<portlet:namespace/>management_xiii_a:{
					required: true
				},
				<portlet:namespace/>observations_xiii_b:{
					required: true
				},
				<portlet:namespace/>management_xiii_b:{
					required: true
				},
				<portlet:namespace/>observations_xiii_c:{
					required: true
				},
				<portlet:namespace/>management_xiii_c:{
					required: true
				},
				<portlet:namespace/>observations_xiii_d:{
					required: true
				},
				<portlet:namespace/>management_xiii_d:{
					required: true
				},
				<portlet:namespace/>observation_xiv__a:{
					required: true
				},
				<portlet:namespace/>management_xiv_a:{
					required: true
				},
				<portlet:namespace/>observations_xiv_b:{
					required: true
				},
				<portlet:namespace/>management_xiv_b:{
					required: true
				},
				<portlet:namespace/>observations_xiv_c:{
					required: true
				},
				<portlet:namespace/>management_xiv_c:{
					required: true
				},
				<portlet:namespace/>observations_xiv_d:{
					required: true
				},
				<portlet:namespace/>management_xiv_d:{
					required: true
				},
				<portlet:namespace/>observations_xiv_e:{
					required: true
				},
				<portlet:namespace/>management_xiv_e:{
					required: true
				},
				<portlet:namespace/>observations_xiv_f:{
					required: true
				},
				<portlet:namespace/>management_xiv_f:{
					required: true
				},
				<portlet:namespace/>observations_xiv_g:{
					required: true
				},
				<portlet:namespace/>management_xiv_g:{
					required: true
				},
				<portlet:namespace/>observations_xv_a:{
					required: true
				},
				<portlet:namespace/>management_xv_a:{
					required: true
				},
				<portlet:namespace/>observations_xv_b:{
					required: true
				},
				<portlet:namespace/>management_xv_b:{
					required: true
				},
				<portlet:namespace/>observations_xv_c:{
					required: true
				},
				<portlet:namespace/>management_xv_c:{
					required: true
				},
				<portlet:namespace/>observations_xvi_a:{
					required: true
				},
				<portlet:namespace/>management_xvi_a:{
					required: true
				},
				<portlet:namespace/>observations_xvi_b:{
					required: true
				},
				<portlet:namespace/>management_xvi_b:{
					required: true
				},
				<portlet:namespace/>observations_xvii_a:{
					required: true
				},
				<portlet:namespace/>management_xvii_a:{
					required: true
				},
				<portlet:namespace/>observations_xvii_b:{
					required: true
				},
				<portlet:namespace/>management_xvii_b:{
					required: true
				},
				<portlet:namespace/>observations_xvii_c:{
					required: true
				},
				<portlet:namespace/>management_xvii_c:{
					required: true
				},
				<portlet:namespace/>observations_xvii_d:{
					required: true
				},
				<portlet:namespace/>management_xvii_d:{
					required: true
				},
				<portlet:namespace/>observations_xviii_a:{
					required: true
				},
				<portlet:namespace/>management_xviii_a:{
					required: true
				},
				<portlet:namespace/>observations_xviii_b:{
					required: true
				},
				<portlet:namespace/>management_xviii_b:{
					required: true
				},
				<portlet:namespace/>observations_xviii_c:{
					required: true
				},
				<portlet:namespace/>management_xviii_c:{
					required: true
				},
				<portlet:namespace/>observations_xviii_d:{
					required: true
				},
				<portlet:namespace/>management_xviii_d:{
					required: true
				},
				<portlet:namespace/>observations_xix_a:{
					required: true
				},
				<portlet:namespace/>management_xix_a:{
					required: true
				},
			}
		});
		$('#btn-submit').on('click', function(){ 
	    	if($("form.form").valid()){
		        var fd = new FormData($("form.form")[0]);
		        $(".content").hide();
		        $(".animaion").show();
		        $.ajax({
		            url: '<%=internalauditreportURL %>',  
		            type: 'POST',
		            data: fd,
		            cache: false,
		            contentType: false,
		            processData: false,
		            success:function(data){
		            	console.log("Inside Success");
		            	$(".content").show();
		                $(".animaion").hide();
		                toastr.success('Form has been submitted successfully!');
		                $("form.form")[0].reset();
		                //$('#output').html("Data submited successfuly.");
		            },
		            error: function() {
		            	$(".animaion").hide();
		           		console.log("Error Occured in ajax call");
		           		toastr.error('An error occured while submitting the form');
		            },
		            complete: function(){
		            	$(".animaion").hide();
						console.log("Complete");
			        }
		        });
	    	}else{
	    		toastr.error('Please fill the required field(s).');
	    		//$('#output').html("Please fill the required field.");
	    	}
	    });
	});

	</script>
	
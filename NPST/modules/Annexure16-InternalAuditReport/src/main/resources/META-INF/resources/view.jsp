<%@page import="Annexure16.InternalAuditReport.constants.Annexure16InternalAuditReportPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=Annexure16InternalAuditReportPortletKeys.auditReport%>" var="auditResource" /> 

<div class="card_blocks">
	<div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" />Internal Audit Report
                </div>
            </div>
      </div>
	
	<div class="form_block mx-4">
		<form id="auditForm" action="#" method="post">
		
		<div class="tab">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
						         <p class="mb-0">Ref. - AXB/2021-22/GEN/20 </p>
						         <p class="mb-0">Date - November 10, 2021</p>
						    </div>
						</div>
					</div>
				</div>
			</div>		
		
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
		                   <p class="font-weight-bold mb-0">Shri Rajiv Raj,</p>
		                   <p class="font-weight-bold mb-0">Deputy General Manager,</p>
		                   <p class="font-weight-bold">National Pension System Trust, </p>
		                   <p class="mb-0">14th Floor,</p>
		                   <p class="mb-0">IFCI Tower, 61,</p>
		                   <p>Nehru Place, New Delhi - 110 019</p>
					</div>
				</div>
			</div>
			
			<p>Dear Sir,</p>

			<p class="subject_class">Sub: Submission of internal audit report for period
					October 2020 to May 2021 (Registration No. -TB001)</p>

			<p>We refer to the requirement of submission of Internal audit
				reports of NPS Trustee Bank activities to NPS Trust. In this regard,
				we hereby submit last conducted Internal audit report for period
				October 2020 to May 2021 for your reference.</p>
		
			<p>Kindly acknowledge receipt of the same.</p>
			
			<div class="row">
		       	<div class="col">
		           	<div class="form-group">
						<div class="row">
		                    <div class="col">
		                    	<label>Yours faithfully, </label>
		                    </div>
						</div>
						<div class="row">
		                    <div class="col-md-3">
		                    	<input type="text" class="form-control" id="signature1" name='<portlet:namespace/>signature1'>
		                    </div>
						</div><br>
						<div class="row">
		                    <div class="col-md-5">
		                    	<p class="bold-text">Alok Srivastava</p>
		                    	<p class="bold-text">Vice President</p>
		                    	<p class="bold-text">Centralized Collection & Payment Hub</p>
		                    </div>
						</div>
					</div>
				</div>
			</div>
			
			<p>Encls. As above</p>

			<p>Copy to: Shri. Vikas Kumar Singh, General Manager, Pension
				Fund Regulatory and Development Authority, Third Floor, Chhatrapati
				Shivaji Bhawan, B-14/A, Qutab Institutional Area, New Delhi -
				110016. For information</p>
				
				
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	
				
		</div>	

		<div class="tab">
		
			<h5 class="text-center my-3 font-weight-bold">AXIS BANK</h5>
			
			<h5 class="text-center my-3 font-weight-bold">INTERNAL AUDIT DEPARTMENT </h5>
			
			<h5 class="text-center my-3 font-weight-bold">CORPORATE OFFICE, MUMBAI</h5>
			
			<h5 class="text-center my-3 font-weight-bold">INTERNAL AUDIT REPORT OF NPS - TRUSTEE BANK Dated: 03.08.2021 </h5>
			
			<p>Date: 03.08.2021</p>
			
			<div class="row">
				<div class="col-md-5">
					<div class="form-group">
		                   <p class="bold-text">Chief Audit Executive</p>
		                   <p class="mb-0">AXIS Bank Ltd., Corporate Office, </p>
		                   <p class="mb-0">Wadia International Centre,</p>
		                   <p class="mb-0">P.B. Marg,</p>
		                   <p class="mb-0">Worli, Mumbai - 400 025,</p>
					</div>
				</div>
			</div>
			
			<p>Dear Sir,</p>
			
			<p class="bold-text mb-2">Audit Report of NPS - Trustee Bank</p>

			<p>We have carried out the internal audit of NPS - Trustee Bank.
				A report of the review conducted is enclosed for your perusal.</p>

			<p>Copy of this letter has been sent along with the audit report to the Vice President -the NPS -Trustee Bank</p>

			<p>The audit observations are based on the files, documents,
				papers, information and . explanations furnished by the concerned
				officials to the audit team.</p>

			<p>The findings of the report have been discussed with the
				concerned officials. We undertake to keep the findings confidential
				and these would not be disclosed / shared with unauthorized persons.</p>
				
			<p>Yours faithfully,</p>	
			
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<div class="row">
		                    <div class="col-md-3">
		                    	<p class="font-weight-bold mb-0">Aditya Pawaskar</p>
		                   		<p class="mb-0">Senior Vice President</p>
		                   		<p>(Internal Audit)</p>
		                    </div>
		                    
		                    <div class="col-md-3">
		                    	<p class="font-weight-bold mb-0">Sujata Rao</p>
		                   		<p class="mb-0">Vice President</p>
		                   		<p>(Internal Audit)</p>
		                    </div>
		                    
		                    <div class="col-md-3">
		                    	<p class="font-weight-bold mb-0">Yogesh Oturkur</p>
		                   		<p class="mb-0">Deputy Vice President</p>
		                   		<p>(Internal Audit)</p>
		                    </div>
		                    
		                    <div class="col-md-3">
		                    	<p class="font-weight-bold mb-0">Shaishavi Dave</p>
		                   		<p class="mb-0">Assistant Vice President</p>
		                   		<p>(Internal Audit)</p>
		                    </div>
						</div>
		                   
					</div>
				</div>
			</div>
			
			<ul class="pl-3">
				<li>Copy forwarded, for information and necessary action to Mr.
					Rahul Choudhary (Executive Vice President - CCPH).</li>
				<li>A copy of the internal Audit report is enclosed. Please
					take the necessary action immediately and submit compliance report
					to the Internal Audit Department through Controllers (wherever
					applicable) within the requisite timeline.</li>
			</ul>

			<p>The time norms for receipt of compliance and closure of the
				audit issues are based on the risk classification of the audit issue
				mentioned in the report. Accordingly the times norms for closure of
				audit findings is as follows:-</p>

			<table id="audit_issue" class="table table-bordered table-striped">
				
				<thead>
					<tr>
						<th>Nature of the Audit Issue</th>
						<th>Time Norms for submission of the compliance by the Auditee Unit</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><p>Extremely High Risk / Very High Risk / High Risk</p></td>
						<td><p>30 Working Days</p></td>
						
					</tr>
					<tr>
						<td><p>Medium Risk</p></td>
						<td><p>45 Working Days</p></td>
					</tr>
					<tr>
						<td><p>Low Risk</p></td>
						<td><p>60 Working Days</p></td>
					</tr>
				</tbody>
				
			</table><br>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	

		</div>
		
		<div class="tab">

			<p class="text-center my-3 font-weight-bold mb-0">AXIS BANK</p>
			<p class="text-center my-3 font-weight-bold mb-0">Internal Audit Department Central Office</p>
			<p class="text-center my-3 font-weight-bold text-underline">Audit Report - NPS - Trustee Bank</p>
			
			<p><strong>1. Background</strong></p>

			<p>Axis Bank has been appointed by Pension Fund Regulatory and
				Development Authority (PFRDA) as the Trustee Bank for National
				Pension System (NPS) effective from lst July, 2015 for a period of 5
				years. Axis Bank was re-appointed by PFRDA as the Trustee Bank from
				October 2020 for period of 5 years</p>

			<p>Trustee Bank as an intermediary receives NPS funds from all
				Nodal Offices and transfers the same to the Pension Funds / Annuity
				Service Providers/Other Intermediaries as per instructions received
				from the Central Recordkeeping Agency (CRA). The funds with
				incomplete information are returned back to the respective Nodal
				Offices of accredited banks for credit to the source account.</p>

			<p>Trustee Bank ensures daily/weekly reporting of the amounts
				received from various nodal offices towards NPS, remittances to
				Pension Fund Managers (PFMs), processing of withdrawal request,
				redressal of grievances and other allied activities based on
				instructions received from CRA.</p>

			<p><strong>2. Scope of Audit</strong></p>

			<ul class="pl-3">
				<li>Adherence to the agreed terms of Service Level Agreement signed with NPS Trust.</li>
				<li>Review the process of collections and transaction matching in Electronic Payment Hub (EPH) system.</li>
				<li>Reconciliation, accounting and reporting of NPS funds received from Nodal offices</li>
				<li>Remittance of funds to Pension Fund Managers based on instructions received from CRA.</li>
				<li>Processing of funds to subscriber individual account based on withdrawal request.</li>
				<li>Regulatory reporting to PFRDA/NPS Trust/Other Regulators.</li>
				<li>Compliance testing of last year's audit report.</li><br>
				
				<p class="font-weight-bold mb-0">Scope exclusion:</p>
				<li>Opening of NPS accounts, NPS activities carried out at CRS Unit and NPS product features covered under NPS POP Audit.</li>
			</ul>
		
			<p><strong>3. Audit Period:</strong></p>

			<p class="pl-3">01.10.2020 to 31.05.2021</p>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>
			
			
		</div>	
		
		<div class="tab">

			<p><strong>4. Audit Rating:</strong></p>
			
			<p class="pl-3"><strong>I. Operating Effectiveness :</strong> Partially Effective</p>
			
			<p class="pl-3 text-underline"><strong>II.	Residual Risk Assessment </strong></p>
			
			<table id="residual_risk" class="table table-bordered table-striped">
				
				<thead>
					<tr>
						<th>Risk Type</th>
						<th>Risk Category</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><p>Inherent Risk Rating</p></td>
						<td><p>Very High Risk</p></td>
						
					</tr>
					<tr>
						<td><p>Control Score and Rating </p></td>
						<td><p>High Risk</p></td>
					</tr>
					<tr>
						<td><p>Residual Risk Rating</p></td>
						<td><p>High Risk</p></td>
					</tr>
				</tbody>
				
			</table><br>
			
			<p class="pl-3"><strong>III. Heat Map </strong></p>
			
			<table id="heat_map" class="table table-bordered table-striped">
				
				<!-- <thead>
					<tr>
						<th>Nature of the Audit Issue</th>
						<th>Time Norms for submission of the compliance by the Auditee Unit</th>
					</tr>
				</thead> -->
				
				<tbody>
					<tr>
						<td rowspan="7"><p class="text-center">Inherent Risk Rating</p></td>
						<td><p><strong>Extremely High</strong></p></td>
						<td><p>High</p></td>
						<td><p>High</p></td>
						<td><p>Very High</p></td>
						<td><p>Extremely High</p></td>
						<td><p>Extremely High</p></td>
						
					</tr>
					<tr>
						<td><p><strong>Very High</strong></p></td>
						<td><p>Medium</p></td>
						<td><p>High</p></td>
						<td><p>High</p></td>
						<td><p>Very High</p></td>
						<td><p>Extremely High</p></td>
					</tr>
					<tr>
						<td><p><strong>High</strong></p></td>
						<td><p>Medium</p></td>
						<td><p>Medium</p></td>
						<td><p>High</p></td>
						<td><p>High</p></td>
						<td><p>Very High</p></td>
					</tr>
					<tr>
						<td><p><strong>Medium</strong></p></td>
						<td><p>Low</p></td>
						<td><p>Medium</p></td>
						<td><p>Medium</p></td>
						<td><p>High</p></td>
						<td><p>High</p></td>
					</tr>
					<tr>
						<td><p><strong>Low</strong></p></td>
						<td><p>Low</p></td>
						<td><p>Low</p></td>
						<td><p>Low</p></td>
						<td><p>Medium</p></td>
						<td><p>Medium</p></td>
					</tr>
					<tr>
						<td></td>
						<td><p><strong>Low</strong></p></td>
						<td><p><strong>Medium</strong></p></td>
						<td><p><strong>High</strong></p></td>
						<td><p><strong>Very High</strong></p></td>
						<td><p><strong>Extremely High</strong></p></td>
					</tr>
					<tr>
						<td colspan="7"><p class="text-center">Control Risk Rating</p></td>
					</tr>

				</tbody>
				
			</table>
			<br>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>
			
		</div>	
		
		<div class="tab">
		
			<!-- No.1 -->
			<p><strong>5. Audit Observations, Recommendation and Remedial Action Plan</strong></p>
			
			<p><strong>5.1 Audit of NPS Trustee Bank </strong></p>

			<p class="pl-3 text-underline">
				<strong>1. Process to ensure timely intimation of change in
					officers to Central Record Agency (CRA) was not effective (since
					complied) </strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
			  <li>As per the Service Level Agreement (SLA) entered between NPS Trustee Bank and NPS Trust
			    <ul class="pl-5">
						<li>Trustee Bank is required to provide complete details of
							all the officers including compliance and alternate compliance
							officer and register them with the CRA.</li>
						<li>If there is any change in the information, which is
							provided at the time of registration with CRA, the Trustee Bank
							should intimate the same to CRA within 7 days.</li>
					</ul>
			  </li>
			</ul>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
			  <li>There was delay in submission of details to CRA of the changes in below officers:
			    <ul class="pl-5">
						<li>Change in Alternate Compliance Officer - delay of 22 days</li>
						<li>Separation of Officer Mr Rajat Varghese from the Bank - delay of 11 days</li>
					</ul>
			  </li>
			</ul>

			<p><strong>Category: </strong>Regulatory</p>
			<p><strong>Risk Rating: </strong>Very High</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Unit to periodically monitor that any change
					in officers is submitted to CRA within 7 days</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p>
				<strong>CCPH Response: </strong>Weekly tracker is now being submitted
				to Principal officer every Tuesday.
			</p>

			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			<!-- No.2 -->
			<p class="pl-3 text-underline">
				<strong>2. Controls to ensure timely and accurate submission
					of information related to Directors to NPS Trust were not in place
					(since complied) </strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
			  <li>PFRDA Rules 2015 stipulates that the Trustee Bank shall file the following with National Pension System Trust:
			    <ul class="pl-5">
						<li>Bio-data of all its directors along with their interest
							in other companies within fifteen days of their appointment and</li>
						<li>any change in the interests of directors at every six
							months</li>
					</ul>
			  </li>
			</ul>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
				<li>On verification of submission of director's details made on
					29th January 2021 to NPS Trust, noted that the Bio-data and
					interests of the following Director were not submitted</li>
			</ul>
			
			<table id="controls" class="table table-bordered table-striped">
				
				<thead>
					<tr>
						<th>Director</th>
						<th>Date of Appointment</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><p>Ms Vasantha Govindan</p></td>
						<td><p>27th January 2021</p></td>
					</tr>
				</tbody>
				
			</table><br>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	
			
		</div>
			
		<div class="tab">	

			<p><strong>Category: </strong>Regulatory</p>
			<p><strong>Risk Rating: </strong>Very High</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Unit to periodically obtain the information from the CS
					team and submit to the Trust on timely basis to ensure compliance
					with regulatory requirements</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p><strong>CCPH Response: </strong></p>
			
			<ul class="pl-3">
				<li>Weekly tracker is now submitted to Principal Officer every Tuesday.</li>
				<li>Also, weekly mail is now being sent to CS team by the Unit.</li>
				<li>We have already shared the details with NPS trust on 31st July 2021. Evidence already submitted</li>
			</ul>
			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			
			<!-- No.3 -->
			<p class="pl-3 text-underline">
				<strong>3. Delay in upload of Fund Receipt
					Confirmation(FRC) file due to technical issues (Repeat Observation)
				</strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
				<li>As per point 10 of Schedule IV of SLA with NPS Trust,
					Trustee Bank Business Activities of the agreement</li>
			</ul>

			<p>"Trustee Bank shall upload the Fund Receipt Confirmation (FRC)
				file on T+ 1 day on CRA portal (by 9:15 am) of realization of funds.
				The file has to be uploaded as per the format provided by CRA(s)".</p>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
				<li>On review of FRCs uploaded during the period lst October
					2020 till 31st May 2021, delays were noted in 3 cases where FRC
					were uploaded with delays ranging between 30 minutes to 1 hour
					(beyond 9:15 a.m. on T+1 day).</li>
				<li>Reasons for the above delays were as follows</li>	
			</ul>
			
			<table id="delay" class="table table-bordered table-striped">
				
				<thead>
					<tr>
						<th>No of Instances</th>
						<th>Reasons</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><p class="text-center">2</p></td>
						<td><p>Technical issue at Trustee Bank</p></td>
					</tr>
					<tr>
						<td><p class="text-center">1</p></td>
						<td><p>Huge Transaction volume</p></td>
					</tr>
				</tbody>
				
			</table><br>
			<p>(Refer Annexure : Delay in FRC</p>

			<p><strong>Category: </strong>Regulatory</p>
			<p><strong>Risk Rating: </strong>Very High</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Unit to take up with the IT team to identify the root cause
					and implement preventive controls</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p><strong>CCPH Response: </strong></p>
			
			<ul class="pl-3">
				<li>Technical issue at-Trustee Bank - There was technical issue
					and report from Proplus system (reporting utility) was delayed.
					This was rectified.</li>
				<li>Huge transaction volume - On huge volumes, the matching
					took time as it was manual activity. This has now been automated
					effective 4th June 2021.(implemented)</li>
			</ul>
			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	
			
		</div>
		
		<div class="tab">	

			<!--No.4  -->
			<p class="pl-3 text-underline">
				<strong>4. Comprehensive Digital Signature Certificate
					(DSC) details not submitted to CRA (Repeat Observation) (since
					complied) </strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
				<li>Unit provides details of separated employees to CRA
					agencies for de-activation of Digital Signature Certificate (DSC)
					of separated employees.</li>
			</ul>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
			  <li> On verification of DSC details shared with CRA, following gaps were noted:
			    <ul class="pl-5">
						<li>Relieving date of Mr Rajat Varghese (Officer) was 10th
							April 2021. There was delay in intimation of separation to NSDL,
							which was sent on 6 May 2021</li>
						<li>Confirmation of de-activation of DSC of Mr. Rajat was
							received from NSDL on 27th May 2021.</li>
					</ul>
			  </li>
			</ul>

			<p><strong>Category: </strong>Non-Regulatory </p>
			<p><strong>Risk Rating: </strong>Medium</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Control to be put in place to ensure timely intimation and
					de-activation of DSC of separated employees.</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p>Noted. We have already deactivated the token for Mr. Rajat
				Varghese. This is now included in the weekly tracker for monitoring
			</p>

			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>


			<!--No.5  -->
			<p class="pl-3 text-underline">
				<strong>5. Structured Grievance Redressal Mechanism was not in place:  </strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
				<li>As per Para 3 (o) of the agreement entered between National Pension Scheme (NPS) Trust and Axis Bank - Trustee Bank on 30 June 2015</li>
			</ul>

			<p>"The Trustee Bank shall redress the grievances of the
				subscribers in accordance with the PFRDA (Redressal of Subscriber
				Grievance) Regulations 2015, in force and shall provide
				data/information, to the NPS Trust, in relation to any transaction,
				which is subject matter of any grievances of the subscribers,
				against any other intermediary, to the extent held by the bank."</p>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
				<li>The subscriber complaints received from CRA portal and
					through emails were addressed and tracked by Trustee bank. However
					the complaints received on emails were not being reported to the
					NPS Trust.</li>
			</ul>

			<p><strong>Category: </strong>Regulatory </p>
			<p><strong>Risk Rating: </strong>Very High</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Process to be put in place to ensure that complaints from
					all sources are reported to NPS Trust in a comprehensive manner.</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>
			
			<p><strong>CCPH Response: </strong></p>

			<p>While grievance reported on the CRA portal is already reported
				to the NPS trust, going forward any grievance received on email of
				npstrusti,oxisbank.com and redirected to CRA for actionable or at
				Trustee Bank shall be reported to NPS Trust. It will be part of
				performance report (Timelines: 10th August 2021)</p>

			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	

		</div>	
			
		<div class="tab">
			
			<!--No.6  -->
			<p class="pl-3 text-underline">
				<strong>6. Operational Manual not adequately documented and approved  </strong>
			</p>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
			  <li> On review of the operational manual of NPS Trustee Bank, noted that it was not updated and in line with the current process
			    <ul class="pl-5">
					<li>New process of De-remit was not documented</li>
				</ul>
			  </li>
			  <li>The operational manual was not been approved by the Senior Management</li>
			</ul>

			<p><strong>Category: </strong>Non - Regulatory  </p>
			<p><strong>Risk Rating: </strong>Medium</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>The operational manual to be comprehensively documented and approved by Unit Head</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p>We have prepared the New SOP and same is in the process of
				approval. We shall submit the same once approved (Time lines: 10th
				August 2021)</p>
			
			<p><strong>IAD Comments: </strong>Noted</p>
			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			
			<!--No.7  -->
			<p class="pl-3 text-underline">
				<strong>7. Process of timely submission of periodic reports (MIS) to NPS Trust was not effective (since complied)  </strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
				<li>Various reports are required to be submitted to NPS Trust on periodic basis as per the prescribed PFRDA guidelines.</li>
			</ul>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
				<li>During the period under review, the Trustee Bank had
					submitted reports to various stakeholders as per the prescribed
					guidelines. There were delays of 1 to 2 days in submission of
					reports in 8 instances:</li>
			</ul>
			
			<table id="process" class="table table-bordered table-striped">
				
				<thead>
					<tr>
						<th>Name of Report</th>
						<th>Periodicity</th>
						<th>Due date</th>
						<th>Actually submitted on</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><p>Daily Average Balance Report</p></td>
						<td><p>Daily</p></td>
						<td><p>17.03.2021</p></td>
						<td><p>18.03.2021</p></td>
					</tr>
					<tr>
						<td rowspan="3"><p>Weekly Average Balance Report</p></td>
						<td rowspan="3"><p>Weekly every Tuesday</p></td>
						<td><p>16.02.2021</p></td>
						<td><p>17.02.2021</p></td>
					</tr>
					<tr>
						<td><p>26.01.2021</p></td>
						<td><p>28.01.2021</p></td>
					</tr>
					<tr>
						<td><p>03.11.2020</p></td>
						<td><p>04.11.2020</p></td>
					</tr>
					<tr>
						<td rowspan="3"><p>Consolidated collection report</p></td>
						<td rowspan="3"><p>Weekly every Tuesday</p></td>
						<td><p>16.02.21</p></td>
						<td><p>17.02.2021</p></td>
					</tr>
					<tr>
						<td><p>26.01.2021</p></td>
						<td><p>28.01.2021</p></td>
					</tr>
					<tr>
						<td><p>03.11.2020</p></td>
						<td><p>04.11.2020</p></td>
					</tr>
					<tr>
						<td><p>New Branch Opening</p></td>
						<td><p>Weekly every Tuesday</p></td>
						<td><p>06.10.2020</p></td>
						<td><p>7.10.2020</p></td>
					</tr>
				</tbody>
				
			</table><br>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	
			
		</div>
		
		<div class="tab">	

			<p><strong>Category: </strong>Regulatory  </p>
			<p><strong>Risk Rating: </strong>Very High</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Compliance tracker for regulatory submissions to be
					maintained and reviewed periodically by the Principal officer to
					ensure timely submission of reports.</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p>Weekly tracker is now being maintained and monitored with
				maker-checker and no delays have been noted after 01.04.2021
				(implemented)</p>
				
			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			
			<!--No.8  -->
			<p class="pl-3 text-underline">
				<strong>8. Instructions for concurrent audit report to be
					sig_ned by CISA qualified auditor not adhered to: (persisting,
					irregularity) (since complied) </strong>
			</p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
				<li>As per the PFRDA (Trustee Bank) Regulations 2015 and Para
					3(e) of agreement entered between National Pension Scheme (NPS)
					Trust and Axis Bank - Trustee Bank on 30 June 2015, : The Trustee
					Bank shall file the following periodic reports with the Authority
					(PFRDA) and National Pension System Trust (NPS Trust):
				<ul class="pl-5">
						<li>Extracts of internal audit report with respect to the
							National Pension System;</li>
						<li>Concurrent audit report to be submitted every quarter;</li>
						<li>External audit report of all the National Pension System
							accounts maintained with the Trustee Bank to be submitted
							annually</li>
						<li>Further, internal/concurrent report should be certified
							by CISA qualified auditors</li>
					</ul>
				</li>	
			</ul>

			<p><strong>Observation:</strong></p>
			
			<ul class="pl-3">
				<li>Concurrent audit reports are certified by DISA qualified
					auditor instead of CISA qualified auditor.</li>
			</ul>

			<p><strong>Category: </strong>Regulatory </p>
			<p><strong>Risk Rating: </strong>Very High</p>

			<p><strong>Recommendation: </strong></p>

			<ul class="pl-3">
				<li>Concurrent Audit report to be certified by CISA qualified auditor in line with NPS Trust guidelines.</li>
			</ul>

			<p><strong>Remedial Action Plan:</strong></p>

			<p>
				<strong>CCPH Response: </strong>Responsibility of appointing CISA
				qualified auditors were with IAD. CCPH has done the follow up with
				IAD. However, there was delay on appointment at IAD end due to Covid
				situation. Further, CISA qualified auditors have been appointed and
				they are commencing their operations.from 1st July 2021. All the
				future reports shall be certified by CISA qualified auditor. This
				point should be tagged to IAD.
			</p>

			<p><strong>Action By: </strong>Trustee bank (CCPH)</p>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	

		</div>
		
		<div class="tab">
			
			<h4>Statement of Facts</h4>
			
			<p class="text-underline"><strong>1. Delay in upload of FRC resulting into payment of penalty</strong></p>
			
			<ul class="pl-3">
				<li>Compensation of Rs 31384 was paid by NPS Trustee Bank in
					January 2021 for delay in upload of FRC, return of funds and delay
					in processing of withdrawals due to technical issues in 2019.
					<ul class="pl-5">
						<li>The delay happened as there were certain transactions
							that remained unreconciled when 24*7 NEFT went live for the first
							time. The transactions were received continuously from RBI and
							EOD could not happen on time.</li>
						<li>Treasury operations immediately identified the issue and
							changed the EOD timing to align it with the new process of NEFT
							transactions received 24*7. The control is put in place whereby
							now EPH system EOD is being processed before Finacle EOD to
							ensure that all the transactions that are getting captured in EPH
							are also captured in the same day in Finacle EOD. No such
							instances have been reported post this change.</li>
					</ul>
				</li>
			</ul>
			
			<p class="text-underline"><strong>2. Processing of Withdrawal of Funds</strong></p>
			
			<p><strong>Background:</strong></p>
			
			<ul class="pl-3">
				<li>SOP on NPS Trustee Bank process stipulates that CRA
					provides instructions to Trustee Bank to transfer the funds to NPS
					Trust- Withdrawal A/c (for withdrawal/ redemption request submitted
					by the subscriber). Based on the instruction received from CRA,
					funds are required to be transferred to withdrawal account as a
					part of Pay- in process. After transfer to withdrawal account,
					Trustee Bank is required to give confirmation for fund transfer of
					funds on CRA website. CRA further provides instruction to transfer
					of funds from withdrawal account to respective beneficiaries. Based
					on the instruction received from CRA, TrUstee Bank carries out
					transfer of funds to respective beneficiaries on the same day
					either through direct credit in case subscriber is maintaining
					account with Axis Bank (Trustee Bank) or through NEFT in case
					subscriber is maintaining account with other Banks. After
					successful transfer of withdrawal of Funds, a report is sent to
					CRA/PFRDA giving UTR detail.</li>
			</ul>
			
			<p><strong>Statement of Fact:</strong></p>
			
			<ul class="pl-3">
				<li>As on 24th June 2021, amount of Rs.5.71 crore (due to
					issues with beneficiary accounts) and Rs.3.38 crore (due to pending
					instructions from CRA) were lying in the withdrawal accounts and
					not remitted to beneficiary accounts as instructions were not
					received from the CRA. Ageing of the same is as under:</li>
			</ul>
			
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<div class="row">
		                    <div class="col-md-6">
								<table id="fact_1" class="table table-bordered table-striped">
				
									<thead>
										<tr>
											<th>Ageing (in days)</th>
											<th>Rs (in cr)</th>
										</tr>
									</thead>
									
									<tbody>
										<tr>
											<td><p>15-100</p></td>
											<td><p>3.09</p></td>
										</tr>
										<tr>
											<td><p>101-500</p></td>
											<td><p>1.52</p></td>
										</tr>
										<tr>
											<td><p>501-1000</p></td>
											<td><p>0.78</p></td>
										</tr>
										<tr>
											<td><p>1001-2000</p></td>
											<td><p>0.33</p></td>
										</tr>
										<tr>
											<td><p>greater than 2000</p></td>
											<td><p>0.00</p></td>
										</tr>
										<tr>
											<td><p><strong>Total</strong></p></td>
											<td><p><strong>5.72</strong></p></td>
										</tr>
									</tbody>
									
								</table><br>
							</div>
							
							<div class="col-md-6">
								<table id="fact_2" class="table table-bordered table-striped">
				
									<thead>
										<tr>
											<th>Ageing (in days)</th>
											<th>Rs (in cr)</th>
										</tr>
									</thead>
									
									<tbody>
										<tr>
											<td><p>15-100</p></td>
											<td><p>0.31</p></td>
										</tr>
										<tr>
											<td><p>101-500</p></td>
											<td><p>2.01</p></td>
										</tr>
										<tr>
											<td><p>501-1000</p></td>
											<td><p>0.37</p></td>
										</tr>
										<tr>
											<td><p>1001-2000</p></td>
											<td><p>0.68</p></td>
										</tr>
										<tr>
											<td><p><strong>Total</strong></p></td>
											<td><p><strong>3.38</strong></p></td>
										</tr>
									</tbody>
									
								</table><br>
							</div>

						</div>
					</div>
				</div>		
			</div>			
			
			<ul class="pl-3">
				<li>Details of the above un-remitted amounts are as follows:
					<ul class="pl-5">
						<li>In 3929 instances there was delay in payment of
							withdrawal remittances.</li>
						<li>In 5905 instances transfer to beneficiaries were returned
							and pending for re-execution</li>
					</ul>
				</li>
			</ul>
			
			<p class="text-underline"><strong>3. Delay in upload of Fund Receipt Confirmation(FRC) file due to technical issues at CRA</strong></p>
			
			<ul class="pl-3">
				<li>On review of FRCS uploaded during the period 1st October
					2020 till 31st May 2021 delays were noted in 52 instances due to
					technical issues at CRA portal</li>
			</ul>
			
			<div class="row text-center">
		        <div class="col-md-12">
		            <button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
      				<button type="button" class="nps-btn" id="nextBtn" onclick="nextPrev(1)">Next</button>
		        </div>
		    </div>	
			
		</div>
		
		<div class="tab">
			
			<p class="text-underline"><strong>4. Long Outstanding balance in Omni Bus Current Account:</strong></p>
			
			<ul class="pl-3">
				<li>The Omnibus current account was created when the funds
					pertaining to erstwhile Trustee Bank (B01) were transferred to Axis
					Bank —new trustee Bank appointed. The NPS fund cannot be used for
					any other purpose. The NPS Trustee Bank has utilised the fund as
					per the instructions of CRA. There are funds available in NPS Trust
					A/C-OMNI BUS amounting to Rs. 35.23 lakhs which is reported to the
					NPS Trust.</li>
				<li>Hence, the funds will be utilised as and when the details
					for the same will be received from Nodal Offices/CRA-NSDL/PFRDA/NPS
					Trust</li>
			</ul>
			
			<p class="text-underline"><strong>5. Manual transfer of files between Trustee Bank & CRA </strong></p>
			
			<ul class="pl-3">
				<li>Certain files like withdrawal files are received on emails
					from the CRA. Additionally, transaction ID files, FRC related files
					are first downloaded from CRA portal and then uploaded in EPH.
					Feasibility of SFTP I STP / automation of file transfers to be
					explored.</li>
			</ul>
		
			<div class="row text-center">
		        <div class="col-md-12">
		        	<button type="button" class="nps-btn" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
		            <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
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

<style type="text/css">
table#heat_map td[rowspan] {
	-webkit-transform: rotate(-90deg);
    transform: rotate(-90deg);
    width: 1%;
    font-weight: bold;
    text-align: center;
    white-space: nowrap;
    background: transparent !important;
}

.tab {
  display: none;
}

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
		  "timeOut": 5000,
		  "extendedTimeOut": 1000
		}
	});
  
  
  var currentTab = 0; // Current tab is set to be the first tab (0)
	showTab(currentTab); // Display the current tab

	function showTab(n) {
	  // This function will display the specified tab of the form...
	  var x = document.getElementsByClassName("tab");
	  x[n].style.display = "block";
	  //... and fix the Previous/Next buttons:
	  if (n == 0) {
	    document.getElementById("prevBtn").style.display = "none";
	  } else {
	    document.getElementById("prevBtn").style.display = "inline";
	  }
	  if (n == (x.length - 1)) {
	    document.getElementById("nextBtn").innerHTML = "Submit";
	  } else {
	    document.getElementById("nextBtn").innerHTML = "Next";
	  }
	  $("html, body").animate({ scrollTop: 0 }, "slow");
	  //... and run a function that will display the correct step indicator:
	  //fixStepIndicator(n)
	}

	function nextPrev(n) {
	  // This function will figure out which tab to display
	  var x = document.getElementsByClassName("tab");
	  // Exit the function if any field in the current tab is invalid:
	  //if (n == 1 && !validateForm()) return false;
	  // Hide the current tab:
	  x[currentTab].style.display = "none";
	  // Increase or decrease the current tab by 1:
	  currentTab = currentTab + n;
	  // if you have reached the end of the form...
	  //if (currentTab >= x.length) {
	    // ... the form gets submitted:
	    //document.getElementById("regForm").submit();
	    //return false;
	  //}
	  // Otherwise, display the correct tab:
	  showTab(currentTab);
	}

  $("#auditForm").on('submit', (function(e) {
		e.preventDefault();
		$(".animaion").show();
				$.ajax({
		            type: "POST",
		            enctype: 'multipart/form-data',
		            processData: false,
		            contentType: false,
		            cache: false,
		            url: '${auditResource}',
		            //data: formData,
		            success: function(data) {
		            	$(".animaion").hide();
		            	toastr.success('Form has been submitted successfully!');
		            	//$('#success-message').show();
		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
		            	$("#auditForm")[0].reset();
		            },
		            error: function() {
		            	$(".animaion").hide();
		            	toastr.error('Something went wrong please try again!');
		            	//$('#error-message').show();
		            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
		            },
		            complete: function(){
		            	$(".animaion").hide();
		            	console.log("set timeout");
		            	setTimeout(function() { location.reload(true); }, 6000);
			        }
		
		        });
	
	})); 

</script>

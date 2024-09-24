<%@ include file="/init.jsp" %>

<portlet:actionURL name="quaterendedUpload" var="quaterendedUploadURL">

</portlet:actionURL>
<portlet:resourceURL id="/quarterended/save" var="quaterendedUploadURL"></portlet:resourceURL>	
<div class="card_blocks">

   <div class="row mb-3">
         <div class="col-12">
            <div class="text-right">
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/quarterly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
     </div>
     
     <div class="row">
         <div class="col">
             <div class="page_title">
                 <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> INPUT - Compliance certificate at quarterly interval
             </div>
         </div>
     </div>
    <div class="form_block mx-4">
		<div class=" content">
		<aui:form id="myForm" method="post">
			<div class="row">
					<div class="col-md-1">
					<div class="form-group">
						<h6>SL.NO</h6>
					</div>
				</div>
	
				<div class="col-md-8">
					<div class="form-group">
						<h6>Parameters </h6>
					</div>
				</div>
				<div class="col-md-3">
					<h6>Complied[Yes or No]</h6>
				</div>
			</div>
		<hr class="m-0"/>
			<h6>1. Management ,Sponser, Net-worth</h6> 
			<hr class="m-0"/>	
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						<p>(i)</p>
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether composition of the Board, Investment committee and Risk Management committeeis as per PFRDA (Pension Fund) regulations?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIay" name='<portlet:namespace/>OneIa' value="Yes">
						<label class="form-check-label" for="OneIay">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIan" name='<portlet:namespace/>OneIa' value="No">
						<label class="form-check-label" for="OneIan">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIa"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>b) Whether Principal officer and key personnel have been appointed as per PFRDA (Pension Fund) regulations.</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIby" name='<portlet:namespace/>OneIb' value="Yes">
						<label class="form-check-label" for="OneIby">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIbn" name='<portlet:namespace/>OneIb' value="No">
						<label class="form-check-label" for="OneIbn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIb"></label>
				</div>
					
			</div>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						<p>(ii)</p>
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether bio-data of all the directors along with their interest in other companies has been filed with the NPS Trust within 15 days of their appointment?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIay" name='<portlet:namespace/>OneIIa' value="Yes">
						<label class="form-check-label" for="OneIIay">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIan" name='<portlet:namespace/>OneIIa' value="No">
						<label class="form-check-label" for="OneIIan">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIIa"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">	
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>b) Whether subsequent change in the interest of the directors havebeen filed with the NPS Trust?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIby" name='<portlet:namespace/>OneIIb' value="Yes">
						<label class="form-check-label" for="OneIIby">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIbn" name='<portlet:namespace/>OneIIb' value="No">
						<label class="form-check-label" for="OneIIbn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIIb"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>c) Whether minimum 51% of the directors have adequate professional experience in finance and financial services related fields.</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIcy" name='<portlet:namespace/>OneIIc' value="Yes">
						<label class="form-check-label" for="OneIIcy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIcn" name='<portlet:namespace/>OneIIc' value="No">
						<label class="form-check-label" for="OneIIcn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIIc"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>d) Whether change in key personnel has been intimated to the PFRDA within 15days of the occurrence of such change.</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIcy" name='<portlet:namespace/>OneIId' value="Yes">
						<label class="form-check-label" for="OneIIcy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIcn" name='<portlet:namespace/>OneIId' value="No">
						<label class="form-check-label" for="OneIIcn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIId"></label>
				</div>
			</div>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						<p>(iii)</p>
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether the Sponsor and the Pension Fund(PFM) fulfill and comply with the eligibility requirements as specified under the PFRDA (Pension Fund) Regulations?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIIay" name='<portlet:namespace/>OneIIIa' value="Yes">
						<label class="form-check-label" for="OneIIIay">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIIan" name='<portlet:namespace/>OneIIIa' value="No">
						<label class="form-check-label" for="OneIIIan">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIIIa"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>b) Whether there is any material change in the information or particulars previously furnished which have a bearing onPFMs certificate of registration?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIIby" name='<portlet:namespace/>OneIIIb' value="Yes">
						<label class="form-check-label" for="OneIIIby">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIIIbn" name='<portlet:namespace/>OneIIIb' value="No">
						<label class="form-check-label" for="OneIIIbn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneIIIb"></label>
				</div>
			</div>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						<p>(iv)</p>
					</div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether there is any major change in the management or ownership or shareholding pattern or any change in controlling interest of the Sponsor?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneIVan" name='<portlet:namespace/>OneIVa' value="No">
						<label class="form-check-label" for="OneIVan">&nbsp; NIL</label> 
					</div>
				</div>
			</div>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						<p>(v)</p>
					</div>
				</div>
		
				<div class="col-md-8">
					<div class="form-group">
						<p> Whether PFM is maintaining minimum Tangible Net-worth as stipulated by PFRDA?</p>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneVy" name='<portlet:namespace/>OneV' value="Yes">
						<label class="form-check-label" for="OneVy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="OneVn" name='<portlet:namespace/>OneV' value="No">
						<label class="form-check-label" for="OneVn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>OneV"></label>
				</div>
			</div>
			<hr class="m-0"/>
			<h6>2. Investment Policy</h6> 
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						<p>(i)</p>
					</div>
				</div>
		
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether Investment Policyhasbeen drawn in accordance with the investment guidelines approved by the PFRDA and has been approved by the Board of Directors (BOD) of the PFM?</p>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="TwoIay" name='<portlet:namespace/>TwoIa' value="Yes">
						<label class="form-check-label" for="TwoIay">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="TwoIan" name='<portlet:namespace/>TwoIa' value="No">
						<label class="form-check-label" for="TwoIan">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>TwoIa"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						
					</div>
				</div>
		
				<div class="col-md-8">
					<div class="form-group">
						<p>b) Whether Investment Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
					</div>
				</div>
				
				<div class="col-md-3">
					<textarea class="form-control" rows="10" id="TwoIbr" placeholder="Remarks" name="<portlet:namespace />TwoIb"></textarea>
				</div>
				<label class="error" for="<portlet:namespace/>TwoIb"></label>
			</div>
			<div class="row pb-3"></div>
			<div class="row pt-3"></div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
						
					</div>
				</div>
		
				<div class="col-md-8 pt-3">
					<div class="form-group">
						<p>c) Whether the PFM has submitted details of the Investment Policy reviewed by its board to the NPS Trust within 30days of such review.</p>
					</div>
				</div>
				<div class="col-md-3">
					<textarea class="form-control" rows="10" id="TwoIcr" placeholder="Remarks" name="<portlet:namespace />TwoIc"></textarea>
 				</div>
 				<label class="error" for="<portlet:namespace/>TwoIc"></label>
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
				<div class="col-md-3"></div>
			</div>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-2">
					<div class="form-group">
						<p>(ii)</p>
					</div>
				</div>
				<div class="col-md-7">
					<div class="form-group">
						<p>Please mention (individually), whether Investment Policy covers the following:</p>
						
						<div class="row">
							<div class="col-sm-1"></div>
							<div class="col-sm-11">
								<p>i) Prudential norms, Income recognition, Asset classification and Provisioning</p>
								<p>ii) Sector limits as stipulated in the Investment guidelines </p>
								<p>iii) Sponsor and Non-Sponsor Group limits as stipulated in the Investment guidelines</p>
								<p>iv) Liquidity and Asset/liability management </p>
								<p>v) Stop Loss Limits </p>
								<p>vi)Broker limit</p>
								<p>vii) Investment audits</p>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3 pt-5">
					<textarea class="form-control" rows="5" id="TwoIIr" placeholder="Remarks" name="<portlet:namespace />TwoII"></textarea>
 				</div>
 				<label class="error" for="<portlet:namespace/>TwoII"></label>
			</div>
			<hr class="m-0"/>
			<h6>3. Risk Management Policy</h6>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether Risk Management Policy hasbeen drawn in accordance with the guidelines approved by the PFRDA and has been approved by the Board of Directors?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="ThreeAy" name='<portlet:namespace/>ThreeA' value="Yes">
						<label class="form-check-label" for="ThreeAy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="ThreeAn" name='<portlet:namespace/>ThreeA' value="No">
						<label class="form-check-label" for="ThreeAn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>ThreeA"></label>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>b) Whether Risk Management Policy is being reviewed periodically (minimum half yearly basis) by the PFM?</p>
					</div>
				</div>
				<div class="col-md-3">
					<textarea class="form-control" rows="5" id="ThreeBr" placeholder="Remarks" name="<portlet:namespace />ThreeB"></textarea>
				</div>
				<label class="error" for="<portlet:namespace/>ThreeB"></label>
			</div>
			<div class="row pb-3"></div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>c) Whether the PFM has submitted details of the Risk Management Policy reviewed by its board to the NPS Trust?</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="ThreeCy" name='<portlet:namespace/>ThreeC' value="Yes">
						<label class="form-check-label" for="ThreeCy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="ThreeCn" name='<portlet:namespace/>ThreeC' value="No">
						<label class="form-check-label" for="ThreeCn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>ThreeC"></label>
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
					<div class="col-md-3"></div>
				</div>	
			</div>	
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>1. Risk Management functions</p>
					</div>
				</div>
					<div class="col-md-3">
						<div class="number-align">
							1.&nbsp;
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" id="ThreeCiy" name='<portlet:namespace/>ThreeCi' value="Yes">
								<label class="form-check-label" for="ThreeCiy">&nbsp; Yes</label> 
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" id="ThreeCin" name='<portlet:namespace/>ThreeCi' value="No">
								<label class="form-check-label" for="ThreeCin">&nbsp; No</label> 
							</div>
							<label class="error" for="<portlet:namespace/>ThreeCi"></label>
						</div>	
					</div>	
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>2. Disaster recovery and business continuity plans</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="number-align">
							2.&nbsp;
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="ThreeCiiy" name='<portlet:namespace/>ThreeCii' value="Yes">
							<label class="form-check-label" for="ThreeCiiy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="ThreeCiin" name='<portlet:namespace/>ThreeCii' value="No">
							<label class="form-check-label" for="ThreeCiin">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>ThreeCii"></label>
					</div>	
				</div>
			</div>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>3. Insurance cover against risks</p>
					</div>
				</div>
				<div class="col-md-3">
						<div class="number-align">
							3.&nbsp;
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="ThreeCiiiy" name='<portlet:namespace/>ThreeCiii' value="Yes">
							<label class="form-check-label" for="ThreeCiiiy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="ThreeCiiin" name='<portlet:namespace/>ThreeCiii' value="No">
							<label class="form-check-label" for="ThreeCiiin">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>ThreeCiii"></label>
					</div>	
				</div>	
			</div>				
			<div class="row">
				<div class="col-md-1">
					<div class="form-group"></div>
				</div>
				<div class="col-md-8">
					<div class="form-group">
						<p>4. Ensuring risk adjusted returns to subscribers consistent with the protection, safety and liquidity of such funds.</p>
					</div>
				</div>
					<div class="col-md-3">
						<div class="number-align">
							4.&nbsp;
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" id="ThreeCivy" name='<portlet:namespace/>ThreeCiv' value="Yes">
								<label class="form-check-label" for="ThreeCivy">&nbsp; Yes</label> 
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" id="ThreeCivn" name='<portlet:namespace/>ThreeCiv' value="No">
								<label class="form-check-label" for="ThreeCivn">&nbsp; No</label> 
							</div>
							<label class="error" for="<portlet:namespace/>ThreeCiv"></label>
						</div>	
					</div>
			</div>	
			<hr class="m-0"/>
			<h6>4. Reporting of Investment Deviations</h6>
			<hr class="m-0"/>
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
					</div>
				</div>
		
				<div class="col-md-8">
					<div class="form-group">
						<p>a) Whether the PFM has ensured that all investments are made as per the investment guidelines?</p>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="FourAy" name='<portlet:namespace/>FourA' value="Yes">
						<label class="form-check-label" for="FourAy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="FourAn" name='<portlet:namespace/>FourA' value="No">
						<label class="form-check-label" for="FourAn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>FourA"></label>
				</div>
		    </div>	
			<div class="row">
				<div class="col-md-1">
					<div class="form-group">
					</div>
				</div>
		
				<div class="col-md-8">
					<div class="form-group">
						<p>b) In case of a deviation (downgrade to a rating not permitted under the regulations for corporate bonds or any other non-compliance in any scheme/asset class post investment), whether the PFM has recorded an internal note justifying its decision to hold such securities where deviation has occurred. </p>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="FourBy" name='<portlet:namespace/>FourB' value="Yes">
						<label class="form-check-label" for="FourBy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" id="FourBn" name='<portlet:namespace/>FourB' value="No">
						<label class="form-check-label" for="FourBn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>FourB"></label>
				</div>
		    </div>	
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>c) Whether all such investment deviations are being reported to the Investment Committee and Board of the PFM for their approval to continue to remain invested in these securities.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FourCy" name='<portlet:namespace/>FourC' value="Yes">
							<label class="form-check-label" for="FourCy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FourCn" name='<portlet:namespace/>FourC' value="No">
							<label class="form-check-label" for="FourCn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FourC"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-9">
						<div class="form-group">
							<p>(Please provide details of the deviations that occurred in the quarter in Annexure 2 along with details of justification note and Investment Committee & Board approval extracts)</p>
						</div>
					</div>
					<div class="col-md-3"></div>
			    </div>	
				<hr class="m-0"/>
				<h6>5. Code of Conduct</h6>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(i)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether the PFM is engaged in any other business activity except those relating to pension schemes or funds, regulated by the PFRDA.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIy" name='<portlet:namespace/>FiveI' value="Yes">
							<label class="form-check-label" for="FiveIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIn" name='<portlet:namespace/>FiveI' value="No">
							<label class="form-check-label" for="FiveIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveI"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(ii)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether the PFM has ensured that adequate disclosures are made to the PFRDA, the NPS Trust or subscriber in a comprehensible and timely manner.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIIy" name='<portlet:namespace/>FiveII' value="Yes">
							<label class="form-check-label" for="FiveIIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIIn" name='<portlet:namespace/>FiveII' value="No">
							<label class="form-check-label" for="FiveIIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveII"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(iii)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether the PFM has ensured that there has not been any misrepresentation or submission of any misleading information to the PFRDA, NPS Trust or subscribers.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIIIy" name='<portlet:namespace/>FiveIII' value="Yes">
							<label class="form-check-label" for="FiveIIIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIIIn" name='<portlet:namespace/>FiveIII' value="No">
							<label class="form-check-label" for="FiveIIIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveIII"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(iv)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether the PFM has divulged to anybody, either orally or in writing, directly or indirectly any confidential information about the PFRDA, the NPS Trust or subscribers, which has come to its knowledge, without taking prior permission of the PFRDA,the NPS Trust except where such disclosures are required to be made in compliance with any law for the time being in force.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIVy" name='<portlet:namespace/>FiveIV' value="Yes">
							<label class="form-check-label" for="FiveIVy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveIVn" name='<portlet:namespace/>FiveIV' value="No">
							<label class="form-check-label" for="FiveIVn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveIV"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(v)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a) Whether the PFM has made adequate disclosures of potential areas of conflict of duties or interest to the PFRDA, the NPS Trust or subscribers.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVay" name='<portlet:namespace/>FiveVa' value="Yes">
							<label class="form-check-label" for="FiveVay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVan" name='<portlet:namespace/>FiveVa' value="No">
							<label class="form-check-label" for="FiveVan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveVa"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b) Whether the PFM hasestablished a mechanism to resolve any conflict of interest situation in an equitable manner, which may arise in the conduct of business.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVby" name='<portlet:namespace/>FiveVb' value="Yes">
							<label class="form-check-label" for="FiveVby">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVbn" name='<portlet:namespace/>FiveVb' value="No">
							<label class="form-check-label" for="FiveVbn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveVb"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
						
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>c) Whether the PFM is satisfied that there has been no instances of self-dealing or front running or insider trading by any of the directors and Key personnel through their accounts or through their family members, relatives or friends. </p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVcy" name='<portlet:namespace/>FiveVc' value="Yes">
							<label class="form-check-label" for="FiveVcy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVcn" name='<portlet:namespace/>FiveVc' value="No">
							<label class="form-check-label" for="FiveVcn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveVc"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
					
					<div class="col-md-8">
						<div class="form-group">
							<p>Kindly provide list of directors and key personnel with status of submissions of self-declarations in Annexure 3.</p>
						</div>
					</div>
					<div class="col-md-3"></div>
			    </div>	
				<hr class="m-0"/>
				
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(vi)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a) Whether PFM has promptly informed the PFRDA and the NPS Trust, if there is any change in the registration status or any penal action taken by any Authority or any material change in financials which may adversely affect the interest of the subscribers.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVIay" name='<portlet:namespace/>FiveVIa' value="Yes">
							<label class="form-check-label" for="FiveVIay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVIan" name='<portlet:namespace/>FiveVIa' value="No">
							<label class="form-check-label" for="FiveVIan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveVIa"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b) Whether the PFM has promptly informed the PFRDA and the NPS Trust about any action, legal proceedings initiated against it in respect of any material breach or non-compliance by it of any law, rules, regulations and directions of the PFRDA or any other regulatory body.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVIby" name='<portlet:namespace/>FiveVIb' value="Yes">
							<label class="form-check-label" for="FiveVIby">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="FiveVIbn" name='<portlet:namespace/>FiveVIb' value="No">
							<label class="form-check-label" for="FiveVIbn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>FiveVIb"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<h6>6. Internal Auditors</h6>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(i)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether appointment of the Internal Auditor and scope of internal audit is as per the Regulations/Guidance note issued by the PFRDA?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SixIy" name='<portlet:namespace/>SixI' value="Yes">
							<label class="form-check-label" for="SixIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SixIn" name='<portlet:namespace/>SixI' value="No">
							<label class="form-check-label" for="SixIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>SixI"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(ii)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a) Whether the PFM has produced to the Auditors such books, accounts, records and other documents in its custody or control and furnish such statement and information relating to its activities entrusted to its by the PFRDA, as it or he may require, within such reasonable time may be specified.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SixIIay" name='<portlet:namespace/>SixIIa' value="Yes">
							<label class="form-check-label" for="SixIIay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SixIIan" name='<portlet:namespace/>SixIIa' value="No">
							<label class="form-check-label" for="SixIIan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>SixIIa"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b) Whether the PFM has allowed Auditor's reasonable access to the premises occupied by it and also extend reasonable facility for examining any books, records, documents and computer data in the possession of the PFM.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SixIIby" name='<portlet:namespace/>SixIIb' value="Yes">
							<label class="form-check-label" for="SixIIby">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SixIIbn" name='<portlet:namespace/>SixIIb' value="No">
							<label class="form-check-label" for="SixIIbn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>SixIIb"></label>
					</div>
			    </div>	
				<hr class="m-0"/>
				<h6>7. Related Party engagement / transaction </h6>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(i)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a) Whether any transactions or engagement have been carried out by the PFM with a related party except investments of National Pension SystemTrust funds?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SevenIay" name='<portlet:namespace/>SevenIa' value="Yes">
							<label class="form-check-label" for="SevenIay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SevenIan" name='<portlet:namespace/>SevenIa' value="No">
							<label class="form-check-label" for="SevenIan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>SevenIa"></label>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b) Whether prior permission of the NPS Trust was taken before entering into such engagement/transaction?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SevenIbna" name='<portlet:namespace/>SevenIb' value="Yes">
							<label class="form-check-label" for="SevenIbna">&nbsp; NA</label> 
						</div>
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>c)  Whether such engagement/transactions have been disclosed to the NPS Trust in its periodic reports.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SevenIcna" name='<portlet:namespace/>SevenIc' value="Yes">
							<label class="form-check-label" for="SevenIcna">&nbsp; NA</label> 
						</div>
						
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>d) Whether such related party engagements / transactions aredone at fair market price?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SevenIdna" name='<portlet:namespace/>SevenId' value="Yes">
							<label class="form-check-label" for="SevenIdna">&nbsp; NA</label> 
						</div>
						
					</div>
			    </div>	
				<div class="row">
					<div class="col-md-1"></div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>e) Whether such transaction is recurring in nature?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" id="SevenIena" name='<portlet:namespace/>SevenIe' value="Yes">
							<label class="form-check-label" for="SevenIena">&nbsp; NA</label> 
						</div>
						
					</div>
			    </div>	
				<hr class="m-0"/>
				<h6>8. Operations / Data Security / Infrastructure</h6>
				<hr class="m-0"/>
								
				 <div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(i)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a)Whether the PFM has complied with circular no. PFRDA/2017/30/PF/4 dated09.10.2017 onguidelines on outsourcing of activities by the Pension Fund? </p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIay" name='<portlet:namespace/>EightIa' value="Yes">
						  	<label class="form-check-label" for="EightIay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIan" name='<portlet:namespace/>EightIa' value="No">
						  	<label class="form-check-label" for="EightIan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightIa"></label>
					</div>
				
				</div>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b. Whether the PFM has complied with the reporting requirements of the circular no. PFRDA/2017/30/PF/4 dated 09.10.2017.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIby" name='<portlet:namespace/>EightIb' value="Yes">
						  	<label class="form-check-label" for="EightIby">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIbn" name='<portlet:namespace/>EightIb' value="No">
						  	<label class="form-check-label" for="EightIbn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightIb"></label>
					</div>
				
				</div>
				<hr class="m-0"/>
				
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(ii)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether all investments are held in the name of NPS Trust?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIIy" name='<portlet:namespace/>EightII' value="Yes">
						  	<label class="form-check-label" for="EightIIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIIn" name='<portlet:namespace/>EightII' value="No">
						  	<label class="form-check-label" for="EightIIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightII"></label>
					</div>
				
				</div>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(iii)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether PFM has pledged or hypothecated or lent any scheme assets which would amount to leverage on scheme’s portfolio?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIIIy" name='<portlet:namespace/>EightIII' value="Yes">
						  	<label class="form-check-label" for="EightIIIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIIIn" name='<portlet:namespace/>EightIII' value="No">
						  	<label class="form-check-label" for="EightIIIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightIII"></label>
					</div>
				
				</div>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
							<p>(iv)</p>
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether all charges/fees debited to the schemes aredeterminedas stipulated by the PFRDA?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIV" name='<portlet:namespace/>EightIV' value="Yes">
						  	<label class="form-check-label" for="EightIV">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightIV" name='<portlet:namespace/>EightIV' value="No">
						  	<label class="form-check-label" for="EightIV">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightIV"></label>
					</div>
				</div>				
				<hr class="m-0"/>				
				<div class="row">
					<div class="col-md-1">
						<div class="form-group"><p>(v)</p>	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether all interest,dividendsor any such accrual income and proceeds of redemption/sale were collected on due dates and promptly credited to the scheme accounts?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVy" name='<portlet:namespace/>EightV' value="Yes">
						  	<label class="form-check-label" for="EightVy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVn" name='<portlet:namespace/>EightV' value="No">
						  	<label class="form-check-label" for="EightVn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightV"></label>
					</div>
				</div>				
				<hr class="m-0"/>				
				<div class="row">
					<div class="col-md-1">
						<div class="form-group"><p>(vi)</p>	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a.Whether the PFM has taken adequate and necessary steps to ensure that the data and records pertaining to its activities are maintained and are intact.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIay" name='<portlet:namespace/>EightVIa' value="Yes">
						  	<label class="form-check-label" for="EightVIay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIan" name='<portlet:namespace/>EightVIa' value="No">
						  	<label class="form-check-label" for="EightVIan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightVIa"></label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b.Whether the PFM has ensured that for electronic records and data, up-to-date backup is always available with it.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIby" name='<portlet:namespace/>EightVIb' value="Yes">
						  	<label class="form-check-label" for="EightVIby">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIbn" name='<portlet:namespace/>EightVIb' value="No">
						  	<label class="form-check-label" for="EightVIbn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightVIb"></label>
					</div>
				</div>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group"><p>(vii)</p>	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a.Whether the PFM has maintained adequate infrastructure facilities to be able to discharge its services to the satisfaction of the PFRDA, the NPS Trust.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIIay" name='<portlet:namespace/>EightVIIa' value="Yes">
						  	<label class="form-check-label" for="EightVIIay">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIIan" name='<portlet:namespace/>EightVIIa' value="No">
						  	<label class="form-check-label" for="EightVIIan">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightVIIa"></label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>b. Whether the operating procedures and systems of the PFM are well documented and backed by operation manuals.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIIby" name='<portlet:namespace/>EightVIIb' value="Yes">
						  	<label class="form-check-label" for="EightVIIby">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIIbn" name='<portlet:namespace/>EightVIIb' value="No">
						  	<label class="form-check-label" for="EightVIIbn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightVIIb"></label>
					</div>
				
				</div>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group"><p>(viii)</p>	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether the PFM has informed relevant institutions that no tax has to be deducted at source under the Income Tax Act.</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIIIy" name='<portlet:namespace/>EightVIII' value="Yes">
						  	<label class="form-check-label" for="EightVIIIy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="EightVIIIn" name='<portlet:namespace/>EightVIII' value="No">
						  	<label class="form-check-label" for="EightVIIIn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>EightVIII"></label>
					</div>
				</div>
				<hr class="m-0"/>
				<h6>9. Brokers empanelment:</h6>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>*Whether Brokers empanelment is done in accordance to the guidelines issued by the PFRDA?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="NineAy" name='<portlet:namespace/>NineA' value="Yes">
						  	<label class="form-check-label" for="NineAy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="NineAn" name='<portlet:namespace/>NineA' value="No">
						  	<label class="form-check-label" for="NineAn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>NineA"></label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>*Whether prescribed limit of percentage of transactions through any single broker is complied with?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="NineBy" name='<portlet:namespace/>NineB' value="Yes">
						  	<label class="form-check-label" for="NineBy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="NineBn" name='<portlet:namespace/>NineB' value="No">
						  	<label class="form-check-label" for="NineBn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>NineB"></label>
					</div>
				</div>
				<hr class="m-0"/>
				<h6>10. Inter-Scheme Investment Parameter</h6>
				<hr class="m-0"/>
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">	
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>Whether all such Inter-Scheme transfers are in conformity with the investment objective of the scheme to which such transfer has been made?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="Teny" name='<portlet:namespace/>Ten' value="Yes">
						  	<label class="form-check-label" for="Teny">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="Tenn" name='<portlet:namespace/>Ten' value="No">
						  	<label class="form-check-label" for="Tenn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>Ten"></label>
					</div>
				</div>
				<hr class="m-0"/>
				<h6>11. Voting Obligation</h6> 
				<hr class="m-0"/>	
				<div class="row">
					<div class="col-md-1">
						<div class="form-group">
						</div>
					</div>
			
					<div class="col-md-8">
						<div class="form-group">
							<p>a. Whether the PFM has complied with its obligation to exercise its voting rights on behalf of the NPS Trust?</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="ElevenAy" name='<portlet:namespace/>ElevenA' value="Yes">
						  	<label class="form-check-label" for="ElevenAy">&nbsp; Yes</label> 
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" id="ElevenAn" name='<portlet:namespace/>ElevenA' value="No">
						  	<label class="form-check-label" for="ElevenAn">&nbsp; No</label> 
						</div>
						<label class="error" for="<portlet:namespace/>ElevenA"></label>
					</div>
				
				</div>
				<div class="row">
				<div class="col-md-1">
					<div class="form-group">	
					</div>
				</div>
		
				<div class="col-md-8">
					<div class="form-group">
						<p>b. Whether, voting report has been submitted to the NPS Trust? </p>
					</div>
				</div>
				
				<div class="col-md-3">
					<div class="form-check form-check-inline">
					  	<input class="form-check-input" type="radio" id="ElevenBy" name='<portlet:namespace/>ElevenB' value="Yes">
					  	<label class="form-check-label" for="ElevenBy">&nbsp; Yes</label> 
					</div>
					<div class="form-check form-check-inline">
					  	<input class="form-check-input" type="radio" id="ElevenBn" name='<portlet:namespace/>ElevenB' value="No">
					  	<label class="form-check-label" for="ElevenBn">&nbsp; No</label> 
					</div>
					<label class="error" for="<portlet:namespace/>ElevenB"></label>
				</div>
				<div class="col-md-12">
							<div class="text-center">
								<input type="button" class="nps-btn" id="btn-submit" value="Submit">
							</div>	
				</div>
			</div>
			</aui:form>	
		</div>
	</div>
	<div class="">
		 <div class="row">
		  	<div  class="col">
		    	<div id="output"></div>
		    </div>
		 </div>
	</div>
	
	<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div>
	
</div>

<style>
hr {
	padding: 7px;
}
</style>

<script type="text/javascript">
$(function(){
	toastr.options = {
			  "debug": false,
			  "positionClass": "toast-bottom-right",
			  "onclick": null,
			  "fadeIn": 300,
			  "fadeOut": 1000,
			  "timeOut": 9000,
			  "extendedTimeOut": 1000
	}
	$("form.form").validate({
	  rules: {
		<portlet:namespace/>OneIa: {
	      required: true
	    },
	    <portlet:namespace/>OneIb: {
		      required: true
		},
		<portlet:namespace/>OneIIa: {
			required: true
		},
		<portlet:namespace/>OneIIb: {
			required: true
		},
		<portlet:namespace/>OneIIc:{
			required: true
		},
		<portlet:namespace/>OneIId:{
			required: true
		},
		<portlet:namespace/>OneIIIa:{
			required: true
		},
		<portlet:namespace/>OneIIIb:{
			required: true
		},
		<portlet:namespace/>OneV:{
			required: true
		},
		<portlet:namespace/>TwoIa:{
			required: true
		},
		<portlet:namespace/>TwoIb:{
			required: true
		},
		<portlet:namespace/>TwoIc:{
			required: true
		},
		<portlet:namespace/>TwoII:{
			required: true
		},
		<portlet:namespace/>ThreeA:{
			required: true
		},
		<portlet:namespace/>ThreeB:{
			required: true
		},
		<portlet:namespace/>ThreeC:{
			required: true
		},
		<portlet:namespace/>ThreeCi:{
			required: true
		},
		<portlet:namespace/>ThreeCii:{
			required: true
		},
		<portlet:namespace/>ThreeCiii:{
			required: true
		},
		<portlet:namespace/>ThreeCiv:{
			required: true
		},
		<portlet:namespace/>FourA:{
			required: true
		},
		<portlet:namespace/>FourB:{
			required: true
		},
		<portlet:namespace/>FourC:{
			required: true
		},
		<portlet:namespace/>FiveI:{
			required: true
		},
		<portlet:namespace/>FiveII:{
			required: true
		},
		<portlet:namespace/>FiveIII:{
			required: true
		},
		<portlet:namespace/>FiveIV:{
			required: true
		},
		<portlet:namespace/>FiveVa:{
			required: true
		},
		<portlet:namespace/>FiveVb:{
			required: true
		},
		<portlet:namespace/>FiveVc:{
			required: true
		},
		<portlet:namespace/>FiveVIa:{
			required: true
		},
		<portlet:namespace/>FiveVIb:{
			required: true
		},
		<portlet:namespace/>SixI:{
			required: true
		},
		<portlet:namespace/>SixIIa:{
			required: true
		},
		<portlet:namespace/>SixIIb:{
			required: true
		},
		<portlet:namespace/>SevenIa:{
			required: true
		},
		<portlet:namespace/>EightIa:{
			required: true
		},
		<portlet:namespace/>EightIb:{
			required: true
		},
		<portlet:namespace/>EightII:{
			required: true
		},
		<portlet:namespace/>EightIII:{
			required: true
		},
		<portlet:namespace/>EightIV:{
			required: true
		},
		<portlet:namespace/>EightV:{
			required: true
		},
		<portlet:namespace/>EightVIa:{
			required: true
		},
		<portlet:namespace/>EightVIb:{
			required: true
		},
		<portlet:namespace/>EightVIIa:{
			required: true
		},
		<portlet:namespace/>EightVIIb:{
			required: true
		},
		<portlet:namespace/>EightVIII:{
			required: true
		},
		<portlet:namespace/>NineA:{
			required: true
		},
		<portlet:namespace/>NineB:{
			required: true
		},
		<portlet:namespace/>Ten:{
			required: true
		},
		<portlet:namespace/>ElevenA:{
			required: true
		},
		<portlet:namespace/>ElevenB:{
			required: true
		},
	  }
	});
    $('#btn-submit').on('click', function(){ 
    	if($( "form.form" ).valid()){
	        var fd = new FormData($("form.form")[0]);
	        $(".content").hide();
	        $(".animaion").show();
	        $.ajax({
	            url: '<%=quaterendedUploadURL %>',  
	            type: 'POST',
	            data: fd,
	            cache: false,
	            contentType: false,
	            processData: false,
	            success:function(data){
	            	$(".content").show();
	                $(".animaion").hide();
	                $("form.form")[0].reset();
	                try {
                    	data = JSON.parse(data);
                    	toastr.success('Form has been submitted successfully!');
                    } catch (e) {
                    	toastr.error('An error occured while parsing the json data. Please try again');
                    	console.log("error while parsing the json data");
                    }
              		var pdfURL = data.pdfURL;
           		 	$('#output').html("<label>Please download the pdf here. </label> <a class='ml-2' href="+pdfURL+"><i class='fa fa-download'></i></a>");
	            },
	            error: function() {
	            	$(".animaion").hide();
	           		console.log("Error Occured in ajax call");
	           		toastr.error('An error occured while submitting the form. Please try again');
	            },
	            complete: function(){
	            	$(".animaion").hide();
					console.log("Complete");
		        }
	        });
    	}else{
    		toastr.error('Please fill the required field(s).');
    		
    	}
    });
});
</script>
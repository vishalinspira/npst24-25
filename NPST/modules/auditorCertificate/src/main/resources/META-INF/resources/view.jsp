<%@ include file="/init.jsp" %>

<portlet:actionURL name="auditorCertificateUpload" var="auditorCertificateUploadUrl">

</portlet:actionURL>
<portlet:resourceURL id="/auditorcertificate/save" var="auditorCertificateUploadUrl"></portlet:resourceURL>	
 <div class="card_blocks">
     <div class="row">
         <div class="col">
             <div class="page_title">
                 <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure - 12
             </div>
         </div>
     </div>
    <div class="form_block mx-4">
		<div class=" content">
			<aui:form id="myForm" method="post">
				<div class="row">
					<div class="col-md-12 pt-3 pb-3">
						<h5 class="text-center text-underline">Scope of audit and format of Auditor's certificate to be submitted annually</h5>
					</div>
				</div>
				<p>Trustee Bank shall be required to get NPS accounts audited in terms of the regulation/ existing
				 agreement and prescribed guidelines. The statutory Auditor's Report shall be submitted to the NPS
				  Trust on an annual basis. This shall be presented by NPS Trust to the Authority with qualifications 
				  and recommendations on the deviations found if any.</p>
				<div class="row">
					<div class="col-md-12 pt-3 pb-3">
						<h5 class="text-center text-underline">Format of Auditor's report</h5>
					</div>
				</div>	
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<b>To,</b>
							<p>The CEO-NPST<br>
								1st Floor, ICADR Building<br>
								Plot No. 6, Vasant Kunj Institutional Area<br>
								Phase-II, Vasant Kunj, New Delhi- 110070</p>
						</div>
						<div class="col-md-9"></div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 m-3">
						<ol class="parent-list">
						  	<li>Certified that:
								<ol class="first-step">
								    <li>We have examined the records relating to the NPS accounts maintained by 
										<input class="form-check-input form-control d-inline w-25" type="text" id="name"name='<portlet:namespace/>name'>
										 (name of the Trustee Bank)</li>
								    <li>We have obtained all information and explanations which, to the best of our knowledge and 
								    		belief, were necessary for the purpose of this audit,</li>
								    <li>Reports and audit- The Trustee Bank has filed the following periodic reports with the National Pension System Trust - 
								    	<ol class="second-step" type="a">
								    		<li>extracts of internal audit report from the independent auditors with respect to the 
								    			National Pension System Trust Accounts, compliance certificates and subscriber 
								    			complaints reports at regular intervals;</li>
								    		<li>quarterly concurrent audit report;</li>
								    		<li>external audit report of all the National Pension System accounts maintained with the Trustee Bank is being submitted</li>
								    	</ol>
								    </li>
								    <li>At the end of each financial year, the Trustee Bank has furnished a statement 
								    of the income and expenditure account and a balance sheet reflecting the position 
								    of the Trustee Bank as on the closing date of the financial year to the National 
								    Pension System Trust.</li>
								</ol>
						  	</li>
						  	
							<li>It is further certified that:
								<ol class="first-step">
								    <li>Funds have been maintained in accordance with the guidelines issued by the Authority/ NPS Trust;</li>
								    <li>The system, procedures and safeguards followed by the NPS Trustee Bank are adequate;</li>
								    <li>The provisions of registration certificate by Authority, SLA agreement signed with the Trust are being complied with by the Trustee Bank;</li>
								    <li>List of amount lying in various accounts of NPS maintained with Trustee Bank as on 
								    <input class="form-check-input form-control d-inline w-25" type="text" id="amount"name='<portlet:namespace/>amount'> is enclosed</li>
								</ol>
							</li>
						</ol>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="text" class="adress-1 form-control" id="adressi"placeholder="For ABC & Co Ltd." name="<portlet:namespace />compname">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="text" class="adress-2 form-control" id="adressii" placeholder="Chartered Accountants" name="<portlet:namespace />characc">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="text" class="adress-3 form-control" id="adressiii" placeholder="(Registration No. 000000W) " name="<portlet:namespace />regno">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						<br/><br/>
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="text" class="adress-4 form-control" id="adressiv" placeholder="ABC Partner" name="<portlet:namespace />compartner">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="text" class="adress-5 form-control" id="adressv" placeholder="(Membership No.: 000000) " name="<portlet:namespace />memberno">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="date" class="adress-6 form-control" id="adressvi" placeholder="Date:-" name="<portlet:namespace />date">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="text" class="adress-7 form-control" id="adressvii" placeholder="Place:-" name="<portlet:namespace />place">
								</div>
							</div>
							<div class="col-md-9"></div>
						</div>
						
						<div class="row">
							<div class="col-md-12 pt-3 text-center">
								<button type="button" id="btn-submit" class="nps-btn">Send</button>
							</div>
						</div>
						
					</div>
				</div>
				
			</aui:form>
		</div>
	</div>
	<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
	</div>
</div>

<style>
input.error {
	border-color: red;
}

label.error {
	display: none !important;
}

ol.first-step > li:before {
  content: "(" counter(list) ") ";
  counter-increment: list;
}
ol.first-step {
	counter-reset: list;
}
ol.first-step > li {
	list-style: none;
	margin-bottom: 15px;
}

ol.second-step > li:before {
  content: "(" counter(list, lower-alpha) ") ";
  counter-increment: list;
}
ol.second-step {
	counter-reset: list;
}
ol.second-step > li {
	list-style: none;
	margin-bottom: 15px;
}

ol.second-step {
	margin-left: 40px;
	margin-top: 12px;
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

	$(function(){
		$("form.form").validate({
		  rules: {
			<portlet:namespace/>name: {
		      required: true
		    },
		    <portlet:namespace/>amount: {
			      required: true
			},
			<portlet:namespace/>compname: {
				required: true
			},
			<portlet:namespace/>characc: {
				required: true
			},
			<portlet:namespace/>regno: {
				required: true
			},
			<portlet:namespace/>compartner:{
				required: true
			},
			<portlet:namespace/>memberno:{
				required: true
			},
			<portlet:namespace/>date:{
				required: true
			},
			<portlet:namespace/>place:{
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
		            url: '<%=auditorCertificateUploadUrl %>',  
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
});
</script>
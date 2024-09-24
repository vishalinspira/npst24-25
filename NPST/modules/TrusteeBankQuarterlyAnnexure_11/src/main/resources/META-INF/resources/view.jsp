<%@page import="TrusteeBankQuarterlyAnnexure_11.constants.TrusteeBankQuarterlyAnnexure_11PortletKeys"%>
<%@ include file="/init.jsp" %>

<!-- <portlet:resourceURL var="addQuarterlyAuditorResourceURL" id="/qauraudi"></portlet:resourceURL> -->
<portlet:resourceURL id="<%=TrusteeBankQuarterlyAnnexure_11PortletKeys.quarterlyAuditor%>" var="addQuarterlyAuditorResourceURL" /> 

<div class="card_blocks">
	 <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure 11 - Scope Of Audit And Format Of Quarterly Auditor's Certificate
                </div>
            </div>
      </div>

        <div class="form_block mx-4">

            <div class="row">
                <div class="col">
                    <p>TB shall be required to get NPS accounts audited in terms of the regulation/ existing agreement and prescribed guidelines. The Auditor's Report shall be submitted to NPS Trust on quarterly basis. This shall be presented by NPS Trust
                        to the Authority with qualifications and recommendations on the deviations found if any.</p>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <h5 class="text-center my-3 font-weight-bold">Format Of Auditor's Report</h5>
                </div>
            </div>
	
	<form id="quarterlyAuditorForm" action="#" method="post">
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label>Date</label> 
					<input type="date" class="form-control" id="performant-date" name='<portlet:namespace/>performantDate'>
					<span id="performant-date-error" class="text-danger"></span>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
                    <p class="mb-0"><b>To, </b><br/>The CEO-N PST</p>
                    <p class="mb-0">1st Floor, ICADR Building</p>
                    <p class="mb-0">Plot No. 6, Vasant Kunj Institutional Area</p>
                    <p>Phase-II, Vasant Kunj, New Delhi- 110070</p>
				</div>
			</div>
		</div>

		<div class="row">
            <div class="col-md-12">
	            <label class="fs-16">1. Certified that:</label>
	            <ol class="px-4">
	                <li>We have examined the records relating to the NPS accounts maintained by <input class="form-control d-inline w-25" type="text" id="name" name='<portlet:namespace/>name'>&nbsp;
	                    <span id="name-error" class="text-danger"></span> (name of the Trustee Bank)</li><br/>
	                <li>We have obtained all information and explanations which, to the best of our knowledge and belief, were necessary for the purpose of this audit,</li><br>
	                <li>The NPS Trustee Bank has maintained all books and records of the Trust Account and has complied with the disclosure requirements as specified by PFRDA/NPS Trust from time to time.</li><br>
	                <li>NPS Trustee Bank has maintained books and records about the fund flow and information flow between NPS Trustee Bank, CRA(s), subscribers, Pension Fund Managers, etc to ensure compliance with the guidelines and has
	                    submitted regular reports at such intervals and in such manner as was required or called for by PFRDA/NPS Trust.</li><br>
	                <li>No fund pertaining to NPS is parked in suspense account/ transitory account.</li><br>
	                <li>Trustee Bank is adhering to the Turn Around Time (TAT) as per the agreement/ prescribed guidelines on remittance to be returned and transfer of funds to various stakeholders for receipt of NPS contributions and
	                    payment of funds to ASPs and subscribers at the time of exit/ withdrawal. (Exceptions found, if any, and auditor's comments on that to be enclosed separately)</li>
	                <ol>
	                    <li>In <input class="form-control d-inline w-25" type="text" id="number" name='<portlet:namespace/>number' onkeypress="return onlyNumberKey(event)">&nbsp;
	                        <span id="number-error" class="text-danger"></span> no. of cases/instances the money was transferred on > T+1 basis.
	                    </li>
	                </ol><br>
	                <li>During the quarter under review the Trustee Bank has enjoyed an average daily float of Rs. <input class="form-control d-inline w-25" type="text" id="amount" name='<portlet:namespace/>amount' onkeypress="return onlyNumberKey(event)">&nbsp;
	                    <span id="amount-error" class="text-danger"></span></li><br>
	                <li>NPS funds are not being used for any other purpose.</li><br>
	                <li>Reconciliation is undertaken on a daily basis.</li><br>
	                <li>Timely reports are being submitted to various stakeholders as per the prescribed guidelines.</li><br>
	                <li>Registers/ ledgers/ records are being properly maintained</li><br>
	                <li>User Ids/ passwords are not being shared with any one by the officials.</li><br>
	                <li>The system of timely back-ups, offsite backups, contingency and disaster data recovery plan are being maintained properly.</li><br>
	                <li>Exception reports are being properly maintained and monitored.</li><br>
	                <li>Observations/ qualifications in previous audit reports of concurrent audit, internal audit, RBI inspection, etc. were studied and their compliance on time has been noted.</li><br>
	                <li>Examined the corrective steps taken by the Bank to minimize errors.</li><br>
	            </ol>
            </div>
        </div>

        <div class="row">
	        <div class="col-md-12">
	            <label class="fs-16">2. It is further certified that:-</label>
	            <ol class="px-4">
	                <li>Funds have been maintained in accordance with the guidelines issued by the Authority/ NPS Trust;</li><br>
	                <li>No window dressing in the accounts has taken place.</li><br>
	                <li>The system, procedures and safeguards followed by the NPS Trustee Bank are adequate;</li><br>
	                <li>The provisions of registration certificate by Authority, SLA agreement signed with the Trust are being complied with by the Trustee Bank;</li><br>
	                <li>Directions issued by the Authority/ NPS Trust from time to time or any other statutory requirements have been followed;</li><br>
	                <li>Affairs of the Trustee Bank are being conducted in a manner which is in the interest of the subscribers;</li><br>
	                <li>List of amount lying in various accounts of NPS maintained with Trustee Bank as on &nbsp; <input class="form-control d-inline w-25" type="date" id="amountListDate" name='<portlet:namespace/>amountListDate'>&nbsp;
	                    <span id="amountListDate-error" class="text-danger"></span>is enclosed</li><br>
	            </ol>
	        </div>   
        </div>
		
		<div class="row">
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" class="form-control" id="companyName" name='<portlet:namespace/>companyName' placeholder="Company Name"> 
                    <span id="companyName-error" class="text-danger"></span>
                </div>
           	</div>
		
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" class="form-control" id="designation" name='<portlet:namespace/>designation' placeholder="Designation">
                    <span id="designation-error" class="text-danger"></span>
                </div>
           	</div>
		
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" class="form-control" id="regNo" name='<portlet:namespace/>regNo' placeholder="(Registration No. 000000W)">
                    <span id="regNo-error" class="text-danger"></span>
                </div>
           	</div>
		
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" class="form-control" id="partnerCompanyName" name='<portlet:namespace/>partnerCompanyName' placeholder="Partner Company Name">
                	<span id="partnerCompanyName-error" class="text-danger"></span>
                </div>
           	</div>
		
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" class="form-control" id="partnerMemNo" name='<portlet:namespace/>partnerMemNo' placeholder="(Membership No.: 000000) ">
                	<span id="partnerMemNo-error" class="text-danger"></span>
                </div>
           	</div>
		</div>
		
		<div class="row">
           	<div class="col-md-2">
                <div class="form-group">
                	<label>Date:</label>
                    <input type="date" class="form-control" id="sign-date" name='<portlet:namespace/>signDate'>
                    <span id="sign-date-error" class="text-danger"></span>
                </div>
           	</div>

           	<div class="col-md-2">
                <div class="form-group">
                	<label>Place:</label>
                    <input type="text" class="form-control" id="place" name='<portlet:namespace/>place'>
                    <span id="place-error" class="text-danger"></span>
                </div>
           	</div>
		</div>
		
		<div class="row text-center">
	        <div class="col-md-12">
	            <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
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
label.error {
	display: none !important;
}

input.error {
	border-color: red;
}

.fs-16 {
	font-size: 16px;
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
	
	$('#success-message').hide();
	$('#error-message').hide();
	
	$("#quarterlyAuditorForm").on('submit', (function(e) {
	    e.preventDefault();
	}));
	
	$(function(){
	    var dtToday = new Date();

	    var month = dtToday.getMonth() + 1;
	    var day = dtToday.getDate();
	    var year = dtToday.getFullYear();

	    if(month < 10)
	        month = '0' + month.toString();
	    if(day < 10)
	        day = '0' + day.toString();

	    var maxDate = year + '-' + month + '-' + day;    
	    $('#performant-date').attr('max', maxDate);
	    $('#amountListDate').attr('max', maxDate);
	    $('#sign-date').attr('max', maxDate);
	});
	
	$("#quarterlyAuditorForm").validate({
	  	rules: {
		<portlet:namespace/>performantDate: {
	      	required: true
	    },
	    <portlet:namespace/>name: {
		    required: true
		},
		<portlet:namespace/>number:{
			required: true
		},
		<portlet:namespace/>amount:{
			required: true,
		},
		<portlet:namespace/>amountListDate:{
			required: true
		},
		<portlet:namespace/>companyName:{
			required: true
		},
		<portlet:namespace/>designation:{
			required: true
		},
		<portlet:namespace/>regNo:{
			required: true
		},
		<portlet:namespace/>partnerCompanyName:{
			required: true
		},
		<portlet:namespace/>partnerMemNo:{
			required: true
		},
		<portlet:namespace/>signDate:{
			required: true
		},
		<portlet:namespace/>place:{
			required: true
		},
	  }

	});
	
});

function onlyNumberKey(evt) {
    
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
}


$("#quarterlyAuditorForm").on('submit', (function(e) {
//$("#btn-submit").click(function() {
	
	e.preventDefault();
	
	if($( "#quarterlyAuditorForm" ).valid()){
	//if(registerQuarterlyAuditorForm()) {
		
		console.log("Inside button click");
		
		var performantDate = $("#performant-date").val();
		var name = $("#name").val();
		var number = $("#number").val();
		var amount = $("#amount").val();
		var amountListDate = $("#amountListDate").val();
		var companyName = $("#companyName").val();
		var designation = $("#designation").val();
		var regNo = $("#regNo").val();
		var partnerCompanyName = $("#partnerCompanyName").val();
		var partnerMemNo = $("#partnerMemNo").val();
		var signDate = $("#sign-date").val();
		var place = $("#place").val();
		
		var formData = new FormData();
		formData.append('<portlet:namespace/>performantDate', performantDate);
		formData.append('<portlet:namespace/>name', name);
		formData.append('<portlet:namespace/>number', number);
		formData.append('<portlet:namespace/>amount', amount);
		formData.append('<portlet:namespace/>amountListDate', amountListDate);
		formData.append('<portlet:namespace/>companyName', companyName);
		formData.append('<portlet:namespace/>designation', designation);
		formData.append('<portlet:namespace/>regNo', regNo);
		formData.append('<portlet:namespace/>partnerCompanyName', partnerCompanyName);
		formData.append('<portlet:namespace/>partnerMemNo', partnerMemNo);
		formData.append('<portlet:namespace/>signDate', signDate);
		formData.append('<portlet:namespace/>place', place);
        
		$(".animaion").show();
        $.ajax({
	        url: '<%=addQuarterlyAuditorResourceURL%>',
	        type: "POST",
	        enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
	        cache: false,
	        data:formData,
	        success: function() {
	        	$(".animaion").hide();
	        	//$('#success-message').show();
            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
            	toastr.success('Form has been submitted successfully!');
            	$("#quarterlyAuditorForm")[0].reset();
	        },
	        error: function() {
	        	$(".animaion").hide();
            	//$('#error-message').show();
            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
            	toastr.error('An error occured while submitting the form. Please try again');
            	console.log("Error Occured in ajax call");
            },
			complete: function(){
				$(".animaion").hide();
				setTimeout(function() { location.reload(true); }, 5000);
	        }
    });
        
		console.log("Exiting ajax");
		
	//}

	}else {
		toastr.error('Please fill the required field(s).');
	}

//});
}));

	function validateEmptyFields(value) {
		return value == "";
	}
	
	function showErrorMessage(elementId, errorMessage) {
		$(elementId).html(errorMessage);
	}
	
	function removeErrorMessage(elementId) {
		$(elementId).html("");
	}

	function registerQuarterlyAuditorForm() {
		
		var valid = false;
		
		var performmantDateInfo = $("#performant-date").val();
		var isperformmantDateValid = false; 
		console.log("Inside", validateEmptyFields(performmantDateInfo));
		if (validateEmptyFields(performmantDateInfo)) {
			showErrorMessage("#performant-date-error", "This field is required");
			isperformmantDateValid;
			//return;
		} else {
			removeErrorMessage("#performant-date-error");
			isperformmantDateValid = true;
		}
		
		var nameInfo = $("#name").val();
		var isnameValid = false;
		if (validateEmptyFields(nameInfo)) {
			showErrorMessage("#name-error", "This field is required");
			isnameValid;
			//return;
		} else {
			removeErrorMessage("#name-error");
			isnameValid = true;
		}
		
		var numberInfo = $("#number").val();
		var isnumberValid = false;
		if (validateEmptyFields(numberInfo)) {
			showErrorMessage("#number-error", "This field is required");
			isnumberValid;
			//return;
		} else {
			removeErrorMessage("#number-error");
			isnumberValid = true;
		}
		
		var amountInfo = $("#amount").val();
		var isamountValid = false;
		if (validateEmptyFields(amountInfo)) {
			showErrorMessage("#amount-error", "This field is required");
			isamountValid;
			//return;
		} else {
			removeErrorMessage("#amount-error");
			isamountValid = true;
		}
		
		var amountListInfo = $("#amountListDate").val();
		var isamountListDateValid = false;
		if (validateEmptyFields(amountListInfo)) {
			showErrorMessage("#amountListDate-error", "This field is required");
			isamountListDateValid;
			//return;
		} else {
			removeErrorMessage("#amountListDate-error");
			isamountListDateValid = true;
		}
		
		var companyNameInfo = $("#companyName").val();
		var iscompanyNameValid = false;
		if (validateEmptyFields(companyNameInfo)) {
			showErrorMessage("#companyName-error", "This field is required");
			iscompanyNameValid;
			//return;
		} else {
			removeErrorMessage("#companyName-error");
			iscompanyNameValid = true;
		}
		
		var designationInfo = $("#designation").val();
		var isdesignationValid = false;
		if (validateEmptyFields(designationInfo)) {
			showErrorMessage("#designation-error", "This field is required");
			isdesignationValid;
			//return;
		} else {
			removeErrorMessage("#designation-error");
			isdesignationValid = true;
		} 
		
		var regNoInfo = $("#regNo").val();
		var isregNoValid = false;
		if (validateEmptyFields(regNoInfo)) {
			showErrorMessage("#regNo-error", "This field is required");
			isregNoValid;
			//return;
		} else {
			removeErrorMessage("#regNo-error");
			isregNoValid = true;
		}
		
		var partnerCompanyNameInfo = $("#partnerCompanyName").val();
		var ispartnerCompanyNameValid = false;
		if (validateEmptyFields(partnerCompanyNameInfo)) {
			showErrorMessage("#partnerCompanyName-error", "This field is required");
			ispartnerCompanyNameValid;
			//return;
		} else {
			removeErrorMessage("#partnerCompanyName-error");
			ispartnerCompanyNameValid = true;
		}
		
		var partnerMemNoInfo = $("#partnerMemNo").val();
		var ispartnerMemNoValid = false;
		if (validateEmptyFields(partnerMemNoInfo)) {
			showErrorMessage("#partnerMemNo-error", "This field is required");
			ispartnerMemNoValid;
			//return;
		} else {
			removeErrorMessage("#partnerMemNo-error");
			ispartnerMemNoValid = true;
		}
		
		
		var signDateInfo = $("#sign-date").val();
		var issignDateValid = false;
		if (validateEmptyFields(signDateInfo)) {
			showErrorMessage("#sign-date-error", "This field is required");
			issignDateValid;
			//return;
		} else {
			removeErrorMessage("#sign-date-error");
			issignDateValid = true;
		}
		
		var placeInfo = $("#place").val();
		var isplaceValid = false;
		if (validateEmptyFields(placeInfo)) {
			showErrorMessage("#place-error", "This field is required");
			isplaceValid;
			//return;
		} else {
			removeErrorMessage("#place-error");
			isplaceValid = true;
		}
		
		
		if(isperformmantDateValid && isnameValid && isnumberValid && isamountValid && 
				isamountListDateValid && iscompanyNameValid && isdesignationValid && isregNoValid &&
				ispartnerCompanyNameValid && ispartnerMemNoValid && issignDateValid && isplaceValid) {
			
			valid = true;
		}
		
		if(isperformmantDateValid && isnameValid && isnumberValid && isamountValid && 
				isamountListDateValid && iscompanyNameValid && isdesignationValid && isregNoValid &&
				ispartnerCompanyNameValid && ispartnerMemNoValid && issignDateValid && isplaceValid) {
			
			$("#quarterlyAuditorForm")[0].submit();
		}
		
		return valid;
		
	}

</script>

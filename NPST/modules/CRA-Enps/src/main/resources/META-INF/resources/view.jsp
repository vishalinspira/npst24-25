<%@ include file="/init.jsp" %>

<portlet:resourceURL id="/craamenps/save" var="saveCraAMEnpsURL"></portlet:resourceURL>
<portlet:resourceURL id="/craamenps/getreportlogdetails" var="getreportlogdetailsURL"></portlet:resourceURL>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>

<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Annexure 8 - Closing Balance Confirmation For NPS Trust Collection Accounts
                </div>
            </div>
        </div> --%>
<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
        Data sent for Review.
      </div>       
    </div>
  </div>
</div>
<div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center text-danger">
      	<i class="fas fa-times-circle  fa-4x d-block mb-4"></i>      
        <span id="output"></span>
      </div>       
    </div>
  </div>
</div>
<div class="nps-page-main nps-upload-report nps-statement-wrapper">

 <div class="row mb-3">
         <div class="col-12">
            <div class="text-right">
                 <p  class="back_btn"><a class="backbtn" href="/web/guest/upload-cra-am"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
         </div>
  </div>
    
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
       		<h4>eNPS Death Claim Data</h4>
           	<form class="row form" id="closingBalForm">
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId"/>
				<input type="hidden" value="123" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
				<fieldset class="row mb-3">
	                  <label class="p-3">CRA</label>
	                  <div class="col-sm-12">
	                    <div class="form-check form-check-inline">
	                      <input class="intermediaryid" type="radio" name="<portlet:namespace/>intermediaryid" id="gridRadios1" value="1" >
	                      <label class="form-check-label ml-1" for="gridRadios1">
	                        Kfintech
	                      </label>
	                    </div>
	                    <div class="form-check form-check-inline">
	                      <input class="intermediaryid" type="radio" name="<portlet:namespace/>intermediaryid" id="gridRadios2" value="2">
	                      <label class="form-check-label ml-1" for="gridRadios2">
	                        NSDL
	                      </label>
	                    </div>
	                    <div class="form-check disabled form-check-inline">
	                      <input class="intermediaryid" type="radio" name="<portlet:namespace/>intermediaryid" id="gridRadios3" value="3" >
	                      <label class="form-check-label ml-1" for="gridRadios3">
	                        CAMS
	                      </label>
	                    </div>
	                  </div> 
	            </fieldset>
				
				<div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" readonly="readonly">
                    </div>
                </div>

		
			
				<div class="statement-wrapper">
                  <div id="table" class="table-editable">
                  	<div class="text-end">
                   		<button id="addRow" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
					</div>
					<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
                   <div class="table-cont" > 
                       <table class="table" id="myTable">
							<thead>
								<tr>
									<th>Sr. No</th>
									<th>PRAN</th>
									<th>Name of Subscriber</th>
									<th>Name of Claiment</th>
									<th>Date of Authorisation of claim </th>
									<th>Date of dispatch of claim form </th>
									<th>Tracking ID</th>
									<th data-orderable="false">Remove</th>
								</tr>
							</thead>
				
							<tbody>
								<tr>
									<td>
										<input type="text" class="form-control srno digits" id="<portlet:namespace />srno" name="<portlet:namespace />srno[]" maxlength="16" readonly="readonly" value="1"><br>
										<span id="srno-error" style="color:red; font-size:12px" for="<portlet:namespace />srno"></span>
									</td>
									<td>
										<input type="text" class="form-control pran digits" id="<portlet:namespace />pran" name="<portlet:namespace />pran[]" ><br>
										<span for="<portlet:namespace />pran" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="text" class="form-control subName " id="<portlet:namespace />subName" name="<portlet:namespace />subName[]"><br>
										<span for="<portlet:namespace />subName" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="text" class="form-control claimentName " id="<portlet:namespace />claiment_name" name="<portlet:namespace />claimentName[]"><br>
										<span for="<portlet:namespace />claimentName" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="date" class="form-control authClaimDate " id="authClaimDate" name="<portlet:namespace />authClaimDate[]"><br>
										<span for="<portlet:namespace />authClaimDate" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="date" class="form-control claimDispatchDate " id="claimDispatchDate" name="<portlet:namespace />claimDispatchDate[]"><br>
										<span for="<portlet:namespace />claimDispatchDate" style="color:red; font-size:12px"></span>
									</td>
									<td>
										<input type="text" class="form-control trackId" id="trackId" name="<portlet:namespace />trackId[]"><br>
										<span for="<portlet:namespace />trackId" style="color:red; font-size:12px"></span>
									</td>
									<td class="text-center">
									</td>
									
								</tr> 
							</tbody>
				
						</table>
						<br><br>
						</div>
			          </div>
			      </div>
					<div class="nps-input-box">
		                	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" disabled="disabled">Save</button>
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
		.modal {
    		display: none;
		}
		
		.modal-dialog {
			margin: 5% 40%;
		}
		
		.modal-content {
			width: 20vw;
		}
				/* label.error {
			display: none !important;
		}  */
	</style>

<script type="text/javascript">

function getreportUploadLogId(){
	var fd = new FormData($("form.form")[0]);
	$.ajax({
        url: '<%=getreportlogdetailsURL %>',  
        type: 'POST',
        data: fd,
        success:function(data){
        	console.log(data);
        	try {
            	data = JSON.parse(data);
            	if(data.status){
            		$("#btn-submit").prop('disabled', false);
            		$(".reportUploadLogId").val(data.reportUploadLogId);
                	
                	$(".reportDate").val(data.reportDate);
            	}else{
            		$("#btn-submit").prop('disabled', true);
            	}
            	
            } catch (e) {
            	console.log("error while parsing the json data",e);
            }
        },
        error: function() {
        	//$(".animaion").hide();
        	//toastr.error('An error occured while submitting the form');
       		console.log("Error Occured in ajax call");
        },
        complete: function(){
				console.log("Complete");
	     },
        cache: false,
        contentType: false,
        processData: false
    });
}
$(document).ready(function() {
	
	$(".intermediaryid").click(function(){ 
		getreportUploadLogId();
	});
	
	
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
	
	/* $(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();

    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var maxDate = year + '-' + month + '-' + day;    
    $('#closing-balance-date').attr('max', maxDate);
    $('#confirm-date').attr('max', maxDate);
}); */
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z ]+$/i.test(value);
		}, "Please enter characters only"); 
	
	$("#closingBalForm").validate({
	  	rules: {
	    "<portlet:namespace />pran[]": {
		    required: true,
		    number:true
		},
		"<portlet:namespace />subName[]":{
			required: true
		},
		"<portlet:namespace />claimentName[]":{
			required: true
		},
		"<portlet:namespace />authClaimDate[]":{
			required: true
		},
		"<portlet:namespace />claimDispatchDate[]":{
			required: true
		},
		"<portlet:namespace />trackId[]":{
			required: true,
		}
	  },
	   /* messages: {
		  <portlet:namespace/>accountnumber0:{
				minlength:"Account number must be 16 digits",
				maxlength:"Account number must be 16 digits",
			},
	  } */

	});
	
	var count = 2; 
    $("#addRow").click(function () {
       var htmlContent = '';
       htmlContent = '<tr>' + 
       
			        '<td><input type="text" class="form-control srno digits" id="srno-'+count+'" name="<portlet:namespace />srno[]" maxlength="16" value="'+count+'"> <span id="srno-error-'+count+'" style="color:red; font-size:12px"></span> </td>' +
					'<td><input type="text" class="form-control pran digits" id="pran-'+count+'" name="<portlet:namespace />pran[]" > <span id="pran-error-'+count+'" style="color:red; font-size:12px"></span> </td>' +
					'<td><input type="text" class="form-control subName " id="subName-'+count+'" name="<portlet:namespace />subName[]"> <span id="subName-error-'+count+'" style="color:red; font-size:12px"></span> </td>' +
					'<td><input type="text" class="form-control claimentName" id="claimentName-'+count+'" name="<portlet:namespace />claimentName[]"> <span id="claimentName-error-'+count+'" style="color:red; font-size:12px"></span> </td>' +
					'<td><input type="date" class="form-control authClaimDate " id="authClaimDate-'+count+'" name="<portlet:namespace />authClaimDate[]"> <span id="authClaimDate-error-'+count+'" style="color:red; font-size:12px"></span> </td>' + 
					'<td><input type="date" class="form-control claimDispatchDate " id="claimDispatchDate-'+count+'" name="<portlet:namespace />claimDispatchDate[]"> <span id="claimDispatchDate-error-'+count+'" style="color:red; font-size:12px"></span> </td>' + 
					'<td><input type="text" class="form-control trackId" id="trackId-'+count+'" name="<portlet:namespace />trackId[]"> <span id="trackId-error-'+count+'" style="color:red; font-size:12px"></span> </td>' + 
					'<td class="text-center"><button class="btn btn-danger btn-sm btn-small remove rmvbtn'+count+'" data-count="'+count+'" id="removeRow" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>' +
					'</tr>';
	
	   //$(".remove").attr("disabled",true);

       $('#myTable tbody').append(htmlContent);
		count++;
		
		/* $('.srno').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                minlength:16,
	  				maxlength:16,
	                messages: {
	                    required: "This field is required.",
	                    minlength:"Account number must be 16 digits",
						maxlength:"Account number must be 16 digits",
	                }
	            }
	        );
	    }); */
		
		$('.pran').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                number:true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.subName').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.claimentName').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.authClaimDate').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		$('.claimDispatchDate').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		$('.trackId').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
   });
    
    $("#myTable").on('click','.remove',function(){
        $(this).closest('tr').remove();
     });
  //$("#closingBalForm").on('submit', (function(e) {
    $("#btn-submit").click(function() {	
    	//e.preventDefault();
    	
    	if($( "#closingBalForm" ).valid()){
    		//if(registerSubmitFunction()) {
    		
    		console.log("Inside button click");
    		
    		
    		/*var accNo = $('.srno').map(function(){return $(this).val();}).get();
    		var accHolder = $('.pran').map(function(){return $(this).val();}).get();
    		var totBal = $('.subName').map(function(){return $(this).val();}).get();
    		var cleaBal = $('.claimentName').map(function(){return $(this).val();}).get();
    		var uncleaBal = $('.authClaimDate').map(function(){return $(this).val();}).get();
    		var claimDispatchDate = $('.claimDispatchDate').map(function(){return $(this).val();}).get();
    		var trackId = $('.trackId').map(function(){return $(this).val();}).get();
    		var reportUploadLogId = $(".reportUploadLogId").val();
    		var reportMasterId = $(".reportMasterId").val();
    		
    		var tableArray = [];
    		var length = accNo.length;
    		
    		for(var i=0;i<length;i++) {
    			var accNoDetail = accNo[i];
    			var accHolderDetail = accHolder[i];
    			var totBalDetail = totBal[i];
    			var cleaBalDetail = cleaBal[i];
    			var uncleaBalDetail = uncleaBal[i];
    			
    			var jsonObject = {
    				"accNoDetail" : accNoDetail,
    				"accHolderDetail" : accHolderDetail,
    				"totBalDetail" : totBalDetail,
    				"cleaBalDetail" : cleaBalDetail,
    				"uncleaBalDetail" : uncleaBalDetail,    				
    			};
    			
    			tableArray.push(jsonObject);
    		}
    		
    		console.log("Eq array ", tableArray);
    		*/
    		var formData = new FormData($("#closingBalForm")[0]);
    		/*formData.append('<portlet:namespace/>cloBal', closingBalDate);
    		formData.append('<portlet:namespace/>address1', ad1);
    		formData.append('<portlet:namespace/>address2', ad2);
    		formData.append('<portlet:namespace/>address3', ad3);
    		formData.append('<portlet:namespace/>date2', confirmDate);
    		formData.append('<portlet:namespace/>tableArray', JSON.stringify(tableArray));
    		formData.append('<portlet:namespace/>personName', name);
    		formData.append('<portlet:namespace/>desig', designation);
    		formData.append('<portlet:namespace/>reportUploadLogId', reportUploadLogId);
    		formData.append('<portlet:namespace/>reportMasterId', reportMasterId);*/
    		
    		console.log("formData: ", JSON.stringify(formData));
    		$(".animaion").show();
    		$.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                url: '<%=saveCraAMEnpsURL%>',
                data: formData,
                success: function(data) {
                	console.log("Inside Success");
                	//$('#success-message').show();
                	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
                	$(".animaion").hide();
                	//toastr.success('Form has been submitted successfully!');
                	//$("#closingBalForm")[0].reset();
                	$('#success_tic').modal('show');
                },
                error: function() {
                	//$('#error-message').show();
                	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
                	$(".animaion").hide();
                	//toastr.error('An error occured while submitting the form. Please try again');
                	console.log("Error Occured in ajax call");
                	$('#output').html("An error occured while submitting the form. Please try again");
            		$('#errorModal').modal('show');
                },
                complete: function(){
                	$(".animaion").hide();
    				setTimeout(function() { location.reload(true); }, 5000);
    	        }

            });
    	}

    });	
    $('#success_tic').on('hidden.bs.modal', function (e) {
		location.reload(); 
    });
    $('#errorExcel').on('hidden.bs.modal', function (e) {
		location.reload(); 
    });
});

$(document).ready(function() {
    $(document).on('keypress', ".alpha", function(e){
        var keyCode = e.keyCode || e.which;
   	 
        //Regex for Valid Characters i.e. Alphabets.
        var regex = /^[A-Za-z]+$/;
 
        //Validate TextBox value against the Regex.
        var isValid = regex.test(String.fromCharCode(keyCode));
 
        return isValid;
    	
    });
    
    
    $(document).on('keypress', ".digits", function(evt){
        // Only ASCII character in that range allowed
        var ASCIICode = (evt.which) ? evt.which : evt.keyCode
        if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 45 || ASCIICode > 57))
            return false;
        return true;
    	
    });
});

	


</script>



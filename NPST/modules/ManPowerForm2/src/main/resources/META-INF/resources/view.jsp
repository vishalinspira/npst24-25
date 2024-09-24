<%@ include file="/init.jsp"%>


<div class="modal fade" id="success_tic" tabindex="-1"
	aria-labelledby="success_ticLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body text-center">
				<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>
				Data sent for Review.
			</div>
		</div>
	</div>
</div>
 
<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body text-center text-danger">
				<i class="fas fa-times-circle  fa-4x d-block mb-4"></i> <span id="output"></span>
			</div>
		</div>
	</div>
</div>
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row mb-3">
		<div class="col-12">
			<div class="text-right">
		            <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
		        </div>
		</div>
	</div>
	<div class="row" id="canvasD">
		<div class="col-lg-12 col-md-12 col-sm-12 col-12">
		   
			<div class="nps-report">
				<h4>ManPower Form2</h4>
				<form class="form" id="manPowerForm2" action="#" method="post">
					<div class="statement-wrapper">
						<input type="hidden" value="${reportUploadLogId }" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" /> <input type="hidden"
							value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId" />
						<input type="hidden" id="rowCountFT" name="<portlet:namespace />rowCountFT">
						<%-- <div class="row">
							<div class="col-lg-6 col-md-6 col-sm-12 col-12">
								<div class="nps-input-box mt-0">
									<label for="name">Report Due Date</label> <input class="reportDate" type="date" name="reportDate" value="${reportDate }" readonly="readonly">
								</div>
							</div>
						</div> --%>
						<br>
						<div id="table" class="table-editable">

							<div class="table-cont">
								
								<table class="table table-bordered tables w-100 mb-3" id="myTable_1">
									<thead>

										<tr>
											<td colspan=3 class="text-center text-light"><strong>Any Change in the interests of Directors of the Pension FundForm 3</strong></td>
										</tr>
										<tr>
											<th>Name of director</th>
											<th>Date of submission of form MBP-1 by the director</th>
											<th>Form MBP-1 (PDF Upload)</th>
										</tr>
									</thead>
									
									<tbody>
										<!-- table 1 -->
										<tr>
											<td><input type="text" class="director w-100" id="director"name="director"></td>
											<td><input type="date" class="submission w-100" id="submission" name="submission"></td>
								  			<td> <input type="file" class="form" id="form" name="form" accept=".pdf"></td>
								  			<!-- <td> <div class="file-upload">
                       							 <div class="nps-input-box mt-0">
                           							<div class="file-select">
						                              <div class="file-select-name" id="noFile">File Name</div>
						                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
						                             <input type="file" class="form" id="form" name="form" accept=".pdf">
						                           </div>
						                        </div>
						                     </div></td> -->
									
										</tr>

									</tbody>
									</table>
									
									<!-- table 2 -->
									
									<table class="table table-bordered tables w-100 mb-3" id="myTable_2">
									<br>
									<thead>
										<br>
										<br>
										<tr>
											<td colspan=5 class="text-center text-light"><strong>Interest of the Director in other companies</strong></td>
										</tr>
										<tr>
											<th>Sr.No</th>
											<th>Names of the companies/bodies corporate/firms/association of individuals</th>
											<th>Nature of interest or concern / Change in interest or concern</th>
											<th>Shareholding</th>
											<th>Date on which interest or concern arose / changed</th>
										</tr>
									</thead>
									<tbody>
										

										<tr>
											<td class="text-center" >1</td>
											<td><input type="text" class="companies w-100" id="companies_1" name="companies_1"></td>
											<td><input type="text" class="nature w-100" id="nature_1" name="nature_1"></td>
											<td><input type="text" class="shareholding w-100" id="shareholding_1" name="shareholding_1"></td>
											<td><input type="date" class="date w-100" id="date_1" name="date_1"></td>
											
										</tr>
										<tr>
											<td class="text-center" >2</td>
											<td><input type="text" class="companies w-100" id="companies_2" name="companies_2"></td>
											<td><input type="text" class="nature w-100" id="nature_2"name="nature_2"></td>
											<td><input type="text" class="shareholding w-100" id="shareholding_2" name="shareholding_2"></td>
											<td><input type="date" class="date w-100" id="date_2" name="date_2"></td>
											
										</tr>
										<tr>
											<td class="text-center" >3</td>
											<td><input type="text" class="companies w-100" id="companies_3" name="companies_3"></td>
											<td><input type="text" class="nature w-100" id="nature_3"name="nature_3"></td>
											<td><input type="text" class="shareholding w-100" id="shareholding_3" name="shareholding_3"></td>
											<td><input type="date" class="date w-100" id="date_3" name="date_3"></td>
											
										</tr>
										
									</tbody>
								</table>
								<br>
								<br>
								<div class="row text-center">
									<div class="col-md-12">
										<input type="submit"class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
									</div>
								</div>
							</div>
						</div>
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
	#myTable_1_length, #myTable_1_filter, #myTable_1_info, #myTable_1_paginate {
		display: none;
	}
	
	input.error {
		border-color: red;
	}
	
	select {
	    background-color: #E9F3FC;
	    color: #111111;
	    border-radius: 5px;
	    padding: 5px 20px;
	}
	
	.css-serial {
	  counter-reset: serial-number;  /* Set the serial number counter to 0 */
	}
	
	.css-serial td:first-child:before {
	  counter-increment: serial-number;  /* Increment the serial number counter */
	  content: counter(serial-number);  /* Display the counter */
	}
	
	.modal {
    display: none;
	}

</style>	
		
<script type="text/javascript">
$(document).ready(function() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){
	        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    $("input[type='date']").attr('max', today);
		
			 $('#form').bind('change', function () {
		 var filename = $("#form").val();
		 if (/^\s*$/.test(filename)) {
		    $(".file-upload").removeClass('active');
		    $("#noFile").text("No file chosen...");
		 }
		 else {
		    $(".file-upload").addClass('active');
		    $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
		 }
		});


	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z\s]+$/i.test(value);
		}, "Please enter characters only");
	
	
	$("#manPowerForm2").validate({
	  	rules: {
	  		"director": {
	  			lettersonly: true,
	  			minlength:3,
		      	required: true
		    },
		    "submission": {
			    required: true
			},
			"form": {
			    required: true
			},
			"Sr": {
			    required: true
			},
			"companies_1": {
				lettersonly: true,
				minlength:3,
			    required: true
			},
			"nature_1": {
			/* 	lettersonly: true,
				minlength:3, */
			    required: true
			},"shareholding_1": {
				/* lettersonly: true,
				minlength:3, */
			    required: true
			},
			"date_1": {
			    required: true
			},
			"companies_2": {
				lettersonly: true,
				minlength:3,
			    required: true
			},
			"nature_2": {
				/* lettersonly: true,
				minlength:3, */
			    required: true
			},"shareholding_2": {
				/* lettersonly: true,
				minlength:3, */
			    required: true
			},
			"date_2": {
			    required: true
			},
			"companies_3": {
				lettersonly: true,
				minlength:3,
			    required: true
			},
			"nature_3": {
				/* lettersonly: true,
				minlength:3, */
			    required: true
			},"shareholding_3": {
				/* lettersonly: true,
				minlength:3, */
			    required: true
			},
			"date_3": {
			    required: true
			}

	  	}

	});
	   
   
	$("#manPowerForm2").on('submit', (function(e) {
		
    		e.preventDefault();

    		if($( "#manPowerForm2" ).valid()){

    			//console.log("Inside button click");
    			
    			
   				var formData = new FormData($("form.form")[0]);
   				
   				var htmlWidth = $("#canvasD").width();
				var htmlHeight = $("#canvasD").height();
				var pdfWidth = htmlWidth + (15 * 2);
				var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
				var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

				var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;

				html2canvas($("#canvasD")[0], {imageTimeout: 5000, allowTaint: true }).then(function(canvas) {
					canvas.getContext('2d');
	
					var image = canvas.toDataURL("image/jpeg", 1.0);
					doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);
	
					for (var i = 1; i <= pageCount; i++) {
						doc.addPage(pdfWidth, pdfHeight);
						doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
					}
	
					console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
	/* 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); */
				     
					var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					console.log("file::: ", file);
					
					formData.append("Manpower_Form_2_File", file);
   				
   				$(".animaion").show();
   				$.ajax({
   		            type: "POST",
   		            enctype: 'multipart/form-data',
   		            processData: false,
   		            contentType: false,
   		            cache: false,
   		            url: '${manPowerForm2ResourceURL}',
   		            data: formData,
   		            success: function(data) {
   		            	$(".animaion").hide();
   		            	if(data == "Success") {
   		            		$('#success_tic').modal('show');
   		            		$("#manPowerForm2")[0].reset();
   	   		             	$('#success_tic').on('hidden.bs.modal', function (e) {
   	   		             		location.reload(); 
   	   		           		})
		            	} else {
		            		console.log("Error Occured in ajax call");
		            		$('#output').html('An error occured while submitting the form. Please try again');
		            		$('#errorExcel').modal('show');
		            	}
   		            	
   		            },
   		            error: function() {
   		            	$(".animaion").hide();
	            		$('#output').html('An error occured while submitting the form. Please try again');
	            		$('#errorExcel').modal('show');
   		            	console.log("Error Occured in ajax call");
   		            },
   		            complete: function(){
   		            	$(".animaion").hide();
   						//setTimeout(function() { location.reload(true); }, 5000);
   			        }
   		
   		        });
				});
    		}

    	}));
    
});

</script>
     
    

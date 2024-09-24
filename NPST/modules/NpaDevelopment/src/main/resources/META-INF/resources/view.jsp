<%@ include file="/init.jsp" %>

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
<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
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
		            <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
		        </div>
		     </div>
		  </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Development in Default securities</h4>
            <form class="form" id="npaDevelopment" action="#" method="post">
               <div class="statement-wrapper">
              	  <input type="hidden" value="${reportUploadLogId }" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" />
		 		  <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                  <input type="hidden" id="rowCountFT" name="<portlet:namespace />rowCountFT">
                  <div class="row">
	                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
	                     <div class="nps-input-box mt-0">
	                        <label for="name">Report Due Date</label>
	                        <input class="reportDate" type="date" name="<portlet:namespace />reportDate" value="${reportDate }" readonly="readonly">
	                     </div>
	                  </div>
               	  </div><br>
                  <div id="table" class="table-editable">
                     <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                           <!--<select class="w-100" name='<portlet:namespace/>companies' id="companies">
                              <option value="">Select</option>
                              <option value="Aditya Birla Sun Life Pension Management Limited">Aditya Birla Sun Life Pension Management Limited </option>
                              <option value="HDFC Pension Management Company Limited">HDFC Pension Management Company Limited </option>
                              <option value="ICICI Prudential Pension Funds Management Company Limited">ICICI Prudential Pension Funds Management Company Limited </option>
                              <option value="Kotak Mahindra Pension Fund Limited">Kotak Mahindra Pension Fund Limited </option>
                              <option value="LIC Pension Fund Limited">LIC Pension Fund Limited </option>
                              <option value="SBI Pension Funds Private Limited">SBI Pension Funds Private Limited </option>
                              <option value="UTI Retirement Solutions Limited">UTI Retirement Solutions Limited </option>
                           </select>-->
                           <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>">
                        </div>
                     </div>
                     <br>
                     <div class="form-inline row">
                        <p>The script wise details of default securities and the measures taken by PF for recovery of dues are placed as under as on 
                           <input type="date" class="date_1 border-0" id="date_1" name="<portlet:namespace />date_1">
                        </p>
                     </div>
                     <br>
                    <!--   <div class="text-end">
                        <button type="button" class="common-btn d-inline-block text-capitalize border-0 mb-3" id="addRow">Add Row</button>
                     </div>
                     <div class="table-cont">
                        <table id="myTable_1" class="table css-serial w-100 table-responsive">
                           <thead>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Name of Security&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th>Legal Case details&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th>Current Status&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                                 <th data-orderable="false">Remove&nbsp;  	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp; 	&nbsp;</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td></td>
                                 <td>
                                    <textarea type="text" class="securityName" placeholder="Please do not use Comma(,)" id="securityName" name="<portlet:namespace />securityName[]"></textarea>
                                 </td>
                                 <td>
                                    <textarea type="text" class="caseDetails" id="caseDetails" placeholder="Please do not use Comma(,)" name="<portlet:namespace />caseDetails[]"></textarea>
                                 </td>
                                 <td>
                                    <textarea type="text" class="currentStatus" id="currentStatus" placeholder="Please do not use Comma(,)" name="<portlet:namespace />currentStatus[]"></textarea>
                                 </td>
                                 <td class="text-center">
                                 </td>
                              </tr>
                           </tbody>
                        </table> -->
                        
                        
                         <div class="row">
                       <div class="col-md-1">
<h5>Sr.No</h5>
</div>
<div class="col-md-3">
<h5>Name of Security </h5>
</div>
<div class="col-md-3">
<h5>Legal Case details</h5>
</div>
<div class="col-md-3">
<h5>Current Status </h5>
</div>
</div>
<hr/>

                     <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>1</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                        <textarea  class="securityName" id="securityName1"  name="<portlet:namespace />securityName1"></textarea>
                             </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails1" name="<portlet:namespace />caseDetails1"></textarea>
                    	       </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus1" name="<portlet:namespace />currentStatus1"></textarea>
                       
                   </div>
                  </div>
                      
                       <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>2</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  	
                        <textarea  class="securityName" id="securityName2"  name="<portlet:namespace />securityName2"></textarea>
                        </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails2" name="<portlet:namespace />caseDetails2"></textarea>
                    	</div>
                    	<div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus2" name="<portlet:namespace />currentStatus2"></textarea>
                          
                   </div>
                  </div>
                  
                   <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>3</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  
                        <textarea  class="securityName" id="securityName3"  name="<portlet:namespace />securityName3"></textarea>
                        </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails3" name="<portlet:namespace />caseDetails3"></textarea>
                    	 </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus3" name="<portlet:namespace />currentStatus3"></textarea>
                                 
                  
                   </div>
                  </div>
                  
                   <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>4</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  	  
                        <textarea  class="securityName" id="securityName4"  name="<portlet:namespace />securityName4"></textarea>
                         </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails4" name="<portlet:namespace />caseDetails4"></textarea>
                    	 </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus4" name="<portlet:namespace />currentStatus4"></textarea>
                                 
                  
                   </div>
                  </div>  
                  
                   <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>5</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  	  
                        <textarea  class="securityName" id="securityName5"  name="<portlet:namespace />securityName5"></textarea>
                         </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails5" name="<portlet:namespace />caseDetails5"></textarea>
                    	 </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus5" name="<portlet:namespace />currentStatus5"></textarea>
                                 
                  
                   </div>
                  </div>  
                  
                   <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>6</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  	  
                        <textarea  class="securityName" id="securityName6"  name="<portlet:namespace />securityName6"></textarea>
                         </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails6" name="<portlet:namespace />caseDetails6"></textarea>
                    	 </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus6" name="<portlet:namespace />currentStatus6"></textarea>
                                 
                  
                   </div>
                  </div>  
                  
                   <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>7</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  	  
                        <textarea  class="securityName" id="securityName7"  name="<portlet:namespace />securityName7"></textarea>
                         </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails7" name="<portlet:namespace />caseDetails7"></textarea>
                    	 </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus7" name="<portlet:namespace />currentStatus7"></textarea>
                                 
                  
                   </div>
                  </div>  
                  
                   <div class="row">
                     <div class="col-md-1">
                     <div class="form-group">
                        <p>8</p>
                     </div>
                  </div>
                  
                   <div class="col-md-3">
                  	  
                        <textarea  class="securityName" id="securityName8"  name="<portlet:namespace />securityName8"></textarea>
                         </div>
                         <div class="col-md-3">
                    	<textarea class="caseDetails"  id="caseDetails8" name="<portlet:namespace />caseDetails8"></textarea>
                    	 </div>
                         <div class="col-md-3">
                        <textarea  class="currentStatus" id="currentStatus8" name="<portlet:namespace />currentStatus8"></textarea>
                                 
                  
                   </div>
                  </div>  
                          
                        <br><br>
                        <div class="row text-center">
                           <div class="col-md-12">
                              <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
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

	/* label.error {
		display: none !important;
	} */
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
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z\s]+$/i.test(value);
		}, "Please enter characters only");
	
	$("#npaDevelopment").validate({
	  	rules: {
	  		"<portlet:namespace/>companies": {
		      	required: true
		    },
		    "<portlet:namespace/>date_1": {
			    required: true
			},
			"<portlet:namespace/>securityName[]": {
			    required: true
			},
			"<portlet:namespace/>caseDetails[]": {
			    required: true
			},
			"<portlet:namespace/>currentStatus[]": {
			    required: true
			},
	  	}

	});
	
	var count = 1;
	
	$("#addRow").click(function () {
       
       //var rowLen =  $('tr.tabRow').length;
       /* var rowLen = $('#myTable').DataTable().rows().count();
       alert("rowLen", rowLen); */
       //var rowsize = $(".rowcount").val(count);
       //console.log("rowsize", rowsize);
       
       var htmlContent = '';
       htmlContent = '<tr>' + 
       '<td></td>' +
       	'<td><textarea type="text" placeholder="Please do not use Comma(,)" class="securityName" id="securityName-'+count+'" name="<portlet:namespace />securityName[]"></textarea></td>' +
       	'<td><textarea type="text" placeholder="Please do not use Comma(,)" class="caseDetails" id="caseDetails-'+count+'" name="<portlet:namespace />caseDetails[]"></textarea></td>' +
       	'<td><textarea type="text" placeholder="Please do not use Comma(,)" class="currentStatus" id="currentStatus-'+count+'" name="<portlet:namespace />currentStatus[]"></textarea></td>' +
        '<td class="text-center"><button class="btn btn-primary btn-sm remove" id="removeRow" type="button" style="padding:5px 10px"><i class="fas fa-trash-alt"></i></button> </td>' +
       '</tr>';

       $('#myTable_1 tbody').append(htmlContent);
       count++;
       
       $('.securityName').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		$('.caseDetails').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.currentStatus').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
       
		$("textarea").each(function () {
	    	this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
	   	}).on("input", function () {
	   		this.style.height = 0;
	    	this.style.height = (this.scrollHeight) + "px";
	   	});
	 });
	    
    $("#myTable_1").on('click','.remove',function(){
        $(this).closest('tr').remove();
    });

	/* var t1 = $('#example1').DataTable({
        "paging":   false,
        "ordering": false,
        "info":     false,
        "searching": false
        
    }); */
    
    $("textarea").each(function () {
    	this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
   	}).on("input", function () {
   		this.style.height = 0;
    	this.style.height = (this.scrollHeight) + "px";
   	});
    
	$("#npaDevelopment").on('submit', (function(e) {
		
    		e.preventDefault();

    		if($( "#npaDevelopment" ).valid()){

    			//console.log("Inside button click");
    			//console.log("Valid ", $( "#custodianAnnex_2a" ).valid());
    			var rc = $("#myTable_1 tbody tr").length;
    			console.log("rowCountFT ", rc);
    			$("#rowCountFT").val(rc);
    			
    			console.log("rowCountFT ", rc);
    			
    			//var description = $('.description').map(function(){return $(this).val();}).get();
    			//var remarks = $('.remarks').map(function(){return $(this).val();}).get();

   				var formData = new FormData($("form.form")[0]);
				var htmlWidth = $("#canvasD").width();
				var htmlHeight = $("#canvasD").height();
				var pdfWidth = htmlWidth + (15 * 2);
				var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
				var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

				var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;
				html2canvas($("#canvasD")[0], { allowTaint: true }).then(function(canvas) {
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
					
					formData.append("npadevelopment_pdf_file", file);
	   				$(".animaion").show();
	   				$.ajax({
	   		            type: "POST",
	   		            enctype: 'multipart/form-data',
	   		            processData: false,
	   		            contentType: false,
	   		            cache: false,
	   		            url: '${npaResourceURL}',
	   		            data: formData,
	   		            success: function(data) {
	   		            	$(".animaion").hide();
	   		            	if(data == "Success") {
	   		            		$('#success_tic').modal('show');
	   		            		$("#npaDevelopment")[0].reset();
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
     
    
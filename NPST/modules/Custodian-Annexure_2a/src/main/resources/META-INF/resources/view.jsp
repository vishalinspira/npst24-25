<%@page import="Custodian.Annexure_2a.constants.CustodianAnnexure_2aPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CustodianAnnexure_2aPortletKeys.annex_2a%>" var="annexTwoA" />

<div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Custodian - Annexure - 2a
                </div>
            </div>
        </div>
	
	<div class="form_block mx-4">
		<form class="form" id="custodianAnnex_2a" action="#" method="post">
		<!-- <aui:form id="sesNonUnanimous" method="post"> -->

			<div class="row">
				<div class="col-md-12 text-right">
					<button type="button" class="nps-btn btn-addrow" id="addRow">Add Row</button>
				</div>
			</div>
			<br>
			
			<input type="hidden" class="rowCountFT" name="<portlet:namespace />rowCountFT">
			
			<p>The following report is submitted by custodian on quarterly basis :-</p>
			
			<table id="myTable" class="table table-bordered table-striped">
	
				<thead>
					<tr>
						<th>Descriptions</th>
						<th>Remarks</th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<textarea class="form-control description" id="description" name="<portlet:namespace />description[]"></textarea><br>
						</td>
						<td>
							<textarea class="form-control remarks" id="remarks" name="<portlet:namespace />remarks[]"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>

			<div class="row text-center">
	            <div class="col-md-12">
	                <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
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
	display: none !important;
}

input.error {
	border-color: red;
}

/* .page_title {
	font-size: 18px;
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
	
	$("#custodianAnnex_2a").validate({
	  	rules: {
	    /* <portlet:namespace/>description_0: {
	      	required: true
	    },
	    <portlet:namespace/>remarks_0: {
		    required: true
		}, */
		
	  }

	});		
	
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
	
	var count = 1; 
	//var count_2 = 1;
	//var count_3 = 2;
    $("#addRow").click(function () {
       $(".rowcount").val(count);
       var htmlContent = '';
       htmlContent = '<tr>' + 
       	
       	'<td><textarea class="form-control description" id="description-'+count+'" name="<portlet:namespace />description[]"></textarea></td>' +
       	'<td><textarea class="form-control remarks" id="remarks-'+count+'" name="<portlet:namespace />remarks[]"></textarea></td>' +
        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small remove" id="removeRow" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>' +
       '</tr>';

       $('#myTable tbody').append(htmlContent);
		count++;

		 /* $('.description').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		$('.remarks').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    }); */
		
   });
    
    $("#myTable").on('click','.remove',function(){
        $(this).closest('tr').remove();
     });

    $("#custodianAnnex_2a").on('submit', (function(e) {
    	//$("#btn-submit").click(function() {	
    		e.preventDefault();
    		
    		$('.description').each(function() {
    	        $(this).rules("add", 
    	            {
    	                required: true,
    	                messages: {
    	                    required: "This field is required.",
    	                }
    	            }
    	        );
    	    });
    		
    		$('.remarks').each(function() {
    	        $(this).rules("add", 
    	            {
    	                required: true,
    	                messages: {
    	                    required: "This field is required.",
    	                }
    	            });
    	    });

    		if($( "#custodianAnnex_2a" ).valid()){

    			console.log("Inside button click");
    			console.log("Valid ", $( "#custodianAnnex_2a" ).valid());
    			
    			var rowCountFT = $("#myTable tbody tr").length;
    			
    			$(".rowCountFT").val(rowCountFT);
    			
    			console.log("rowCountFT ", rowCountFT);
    			
    			//var table = $('#myTableVoting').DataTable();
    			//var countRow = table.data().count();
    			
    			//$(".countrows").val(countRow);
    			
    			//var ad1 = $("#address-line-1").val();
    			//console.log("ad1 ", ad1);
    			var description = $('.description').map(function(){return $(this).val();}).get();
    			var remarks = $('.remarks').map(function(){return $(this).val();}).get();

    			//console.log("Eq array ", tableArray);
    			//if($("form.form").valid()){
    				var formData = new FormData($("form.form")[0]);
    				//var formData = new FormData();
    				
    				$(".animaion").show();
    				$.ajax({
    		            type: "POST",
    		            enctype: 'multipart/form-data',
    		            processData: false,
    		            contentType: false,
    		            cache: false,
    		            url: '${annexTwoA}',
    		            data: formData,
    		            success: function(data) {
    		            	console.log("Inside Success");
    		            	//$('#success-message').show();
    		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
    		            	$(".animaion").hide();
    		            	toastr.success('Form has been submitted successfully!');
    		            	//$("form.form")[0].reset();
    		            	$("#custodianAnnex_2a")[0].reset();
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
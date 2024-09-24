<%@page import="Custodian.Annexure_1a.constants.CustodianAnnexure_1aPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CustodianAnnexure_1aPortletKeys.annex_1a%>" var="annexOneA" />

<div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> Custodian - Annexure - 1a
                </div>
            </div>
        </div>
	
	<div class="form_block mx-4">
		<form class="form" id="custodianAnnex_1a" action="#" method="post">
		<!-- <aui:form id="sesNonUnanimous" method="post"> -->

			<div class="row">
				<div class="col-md-12 text-right">
					<button type="button" class="nps-btn btn-addrow" id="addRow">Add Row</button>
				</div>
			</div>
			<br>
			
			<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
			
			<div class="form-inline row">
				<div class="col-md-8">
	    			<p><strong>Details Of Asset Under Custody As On</strong> 
	        			<input type="date" class="form-control custody" id="custody-date" name="<portlet:namespace />custodyDate"> 
	        		</p>
				</div>
			</div>
			<p><strong> (Rs. In Crores) </strong></p>
			<br>
			
			<table id="myTable" class="table table-bordered table-striped">
	
				<thead>
					<tr>
						<th>Name of Pension</th>
						<th>AUC as per face value for debt and purchase price for equity)</th>
						<th>AUC (as per market valuation)</th>
						<th>AUM</th>
						<th>% of AUM under custody</th>
						<th>Asset not under custody</th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control pension-name" id="pension-name" name="<portlet:namespace />pension_name_0"><br>
						</td>
						<td>
							<input type="text" class="form-control auc-face-value" id="auc-face-value" name="<portlet:namespace />auc_face_value_0" onkeypress="return onlyNumberKey(event)"><br>
						</td>
						<td>
							<input type="text" class="form-control auc-market-value" id="auc-market-value" name="<portlet:namespace />auc_market_value_0" onkeypress="return onlyNumberKey(event)"><br>
						</td>
						<td>
							<input type="text" class="form-control aum" id="aum" name="<portlet:namespace />aum_0" onkeypress="return onlyNumberKey(event)"><br>
						</td>
						<td>
							<input type="text" class="form-control percent-of-aum" id="percent-of-aum" name="<portlet:namespace />percent_of_aum_0" onkeypress="return onlyNumberKey(event)"><br>
						</td>
						<td>
							<input type="text" class="form-control asset-not-under-custody" id="asset-not-under-custody" name="<portlet:namespace />asset_not_under_custody_0" onkeypress="return onlyNumberKey(event)"><br>
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

function onlyNumberKey(evt) {
    
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 45 || ASCIICode > 57))
        return false;
    return true;
}

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
	
	$("#custodianAnnex_1a").validate({
	  	rules: {
		<portlet:namespace/>custodyDate: {
	      	required: true
	    },
	    <portlet:namespace/>pension_name_0: {
	      	required: true
	    },
	    <portlet:namespace/>auc_face_value_0: {
		    required: true
		},
		<portlet:namespace/>auc_market_value_0:{
			required: true
		},
		<portlet:namespace/>aum_0:{
			required: true,
		},
		<portlet:namespace/>percent_of_aum_0:{
			required: true
		},
		<portlet:namespace/>asset_not_under_custody_0:{
			required: true
		},
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
       	
       	'<td><input type="text" class="form-control pension-name" id="pension-name-'+count+'" name="<portlet:namespace />pension_name_'+count+'"> </td>' +
      	'<td><input type="text" class="form-control auc-face-value" id="auc-face-value-'+count+'" name="<portlet:namespace />auc_face_value_'+count+'" onkeypress="return onlyNumberKey(event)"> </td>' +
		'<td><input type="text" class="form-control auc-market-value" id="auc-market-value-'+count+'" name="<portlet:namespace />auc_market_value_'+count+'" onkeypress="return onlyNumberKey(event)"> </td>' +
		'<td><input type="text" class="form-control aum" id="aum-'+count+'" name="<portlet:namespace />aum_'+count+'" onkeypress="return onlyNumberKey(event)"> </td>' +
		'<td><input type="text" class="form-control percent-of-aum" id="percent-of-aum-'+count+'" name="<portlet:namespace />percent_of_aum_'+count+'" onkeypress="return onlyNumberKey(event)"> </td>' +
		'<td><input type="text" class="form-control asset-not-under-custody" id="asset-not-under-custody-'+count+'" name="<portlet:namespace />asset_not_under_custody_'+count+'" onkeypress="return onlyNumberKey(event)"> </td>' +
        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small remove" id="removeRow" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>' +
       '</tr>';

       $('#myTable tbody').append(htmlContent);
		count++;

		 $('.pension-name').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
		$('.auc-face-value').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.auc-market-value').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.aum').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.percent-of-aum').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
		$('.asset-not-under-custody').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
		
   });
    
    $("#myTable").on('click','.remove',function(){
        $(this).closest('tr').remove();
     });

    $("#custodianAnnex_1a").on('submit', (function(e) {
    	//$("#btn-submit").click(function() {	
    		e.preventDefault();
    		
    		if($( "#custodianAnnex_1a" ).valid()){

    			console.log("Inside button click");
    			console.log("Valid ", $( "#custodianAnnex_1a" ).valid());
    			
    			//var table = $('#myTableVoting').DataTable();
    			//var countRow = table.data().count();
    			
    			//$(".countrows").val(countRow);
    			
    			//var ad1 = $("#address-line-1").val();
    			//console.log("ad1 ", ad1);
    			var pemsionName = $('.pension-name').map(function(){return $(this).val();}).get();
    			var aucFaceValue = $('.auc-face-value').map(function(){return $(this).val();}).get();
    			var aucMarketValue = $('.auc-market-value').map(function(){return $(this).val();}).get();
    			var aum = $('.aum').map(function(){return $(this).val();}).get();
    			var percentOfAum = $('.percent-of-aum').map(function(){return $(this).val();}).get();
    			var assetNotUnderCustody = $('.asset-not-under-custody').map(function(){return $(this).val();}).get();

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
    		            url: '${annexOneA}',
    		            data: formData,
    		            success: function(data) {
    		            	console.log("Inside Success");
    		            	//$('#success-message').show();
    		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
    		            	$(".animaion").hide();
    		            	toastr.success('Form has been submitted successfully!');
    		            	//$("form.form")[0].reset();
    		            	$("#custodianAnnex_1a")[0].reset();
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




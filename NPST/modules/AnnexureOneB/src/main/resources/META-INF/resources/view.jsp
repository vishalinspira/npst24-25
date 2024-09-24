<%@ include file="/init.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>

<portlet:resourceURL id="/AnnexureOneBForm/save" var="annexureonebformtURL"></portlet:resourceURL>

<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> ANNEXURE 1b - AUM DATA SUMBITTED BY PFMs
                </div>
            </div>
        </div> --%>
	
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
       		<h4>Annexure 1b - AUM data submitted by PFMs.</h4>
				<form id="Annexure-oneB-Form" class="row form">
				<input type="hidden" name="dlfileid"/>
				<input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId"/>
				<input type="hidden" value="25" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
				 <input type="hidden" class="rowcount_first_table" name="<portlet:namespace />rowcount_first_table">
				  <input type="hidden" class="rowcount_secound_table" name="<portlet:namespace />rowcount_secound_table">
				  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="nps-input-box mt-0">
                        <label for="name">Report Due Date</label>
                        <input class="reportDate" type="date" readonly="readonly">
                        <!-- <input type="text" id="name"  disabled> -->
                    </div>
                </div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<p>
										<strong>Details submitted by 7 PFMs </strong>
									</p>
									<span class="text-danger" id="confirm-date-error"></span>
								</div>
							</div>
						</div>
					
						<br><br>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<p>
											DETAILS OF ASSET UNDER CUSTODY AS ON <input class="form-control d-inline w-10" type="date" name='<portlet:namespace/>Fromdate'><br>
											(i.e. last working day of the month)
										</p>
									</div>
								</div>
							</div>
	
							<div class="statement-wrapper">
		                		<div id="table" class="table-editable">
				                  	<div class="text-end">
				                   		<button id="addRowOne" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
									</div>
								<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
		                   		<div class="table-cont" > 
			                       <table class="table" id="myTableDetailsOfAsset">
											<thead>
												<tr>
													<th>Sr no. </th>
													<th>Name Of Pension Fund </th>
													<th>AUC (as per market valuation) </th>
													<th>AUM </th>
													<th>% of AUM Under Coustody </th>
													<th>Asset not Under Coustody</th>
													<th data-orderable="false">Remove</th>
												</tr>
											</thead>
										
											<tbody>
												<tr>
													<td>
														<input type="text" class="form-control sr_no" name="<portlet:namespace />sr_no" readonly="readonly" value="1"><br>
													</td>
													<td>
														<input type="text" class="form-control name_of_pension_fund" name="<portlet:namespace />name_of_pension_fund1"><br>
													</td>
													<td>
														<input type="text" class="form-control auc" name="<portlet:namespace />auc1" onkeypress="return onlyNumberKey(event)"><br>
													</td>
													<td>
														<input type="text" class="form-control aum" name="<portlet:namespace />aum1" onkeypress="return onlyNumberKey(event)"><br>
													</td>
													<td>
														<input type="text" class="form-control aum_under_coustody" name="<portlet:namespace />aum_under_coustody1" onkeypress="return onlyNumberKey(event)"><br>
													</td>
													<td>
														<input type="text" class="form-control asset_not_under_coustody" name="<portlet:namespace />asset_not_under_coustody1" onkeypress="return onlyNumberKey(event)"><br>
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
								
								<!-- 2nd data-table ------------------------------ -->
								
								<br><br>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<p>
												DETAILS OF ASSET NOT UNDER CUSTODY ( &#8377; In Crores)
											</p>
										</div>
									</div>
								</div>
							<div class="statement-wrapper">
				              <div id="table" class="table-editable">
				                	<div class="text-end">
				                 		<button id="addRow" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
									</div>
									<input type="hidden" class="rowcount" name="<portlet:namespace />rowcount">
				                 	<div class="table-cont" > 

								<table id="myTableParticulars" class="table">
						
									<thead>
										<tr>
											<th>Sr no. </th>
											<th>Particulars </th>
											<th>Amount in Crores (4 decimal places) </th>
											<th data-orderable="false">Remove</th>
										</tr>
									</thead>
									
									<tbody>
										<tr>
											<td>
												<input type="text" class="form-control sr_no_two" name="<portlet:namespace />sr_no_two" readonly="readonly" value="1"><br>
											</td>
											<td>
												<input type="text" class="form-control particulars" name="<portlet:namespace />particulars1"><br>
											</td>
											<td>
												<input type="text" class="form-control amount_in_crores" name="<portlet:namespace />amount_in_crores1" onkeypress="return onlyNumberKey(event)"><br>
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
                    	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
                        <!-- <a href="javascript:;" class="common-btn d-inline-block">save</a> -->
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
	
</div>

<style type="text/css">

.d-inline {
    display: inline !important;
    width: 15%;
}

.page_title {
	font-size: 18px;
}

</style>

<script type="text/javascript">

var tableOne = null;
var tableTwo = null;
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
	
	tableOne = $("#myTableDetailsOfAsset").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	
	tableTwo = $("#myTableParticulars").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
});

function onlyNumberKey(evt) {
    
    // Only ASCII character in that range allowed
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && ASCIICode!=46 && (ASCIICode < 45 || ASCIICode > 57))
        return false;
    return true;
}

$("#Annexure-oneB-Form").validate({
  	rules: {
	  	<portlet:namespace/>Fromdate: {
	  	      	required: true
	  	 },
	  	 <portlet:namespace/>name_of_pension_fund1: {
	        	required: true
	    },
	  	<portlet:namespace/>auc1: {
	      	required: true
	    },
	    <portlet:namespace/>aum1: {
		    required: true
		},
		<portlet:namespace/>aum_under_coustody1:{
			required: true
		},
		<portlet:namespace/>asset_not_under_coustody1:{
			required: true
		},
		<portlet:namespace/>particulars1:{
			required: true
		},
		<portlet:namespace/>amount_in_crores1:{
			required: true
		},
	
  },  

}); 


var counterFT = 2;

$('#addRowOne').on( 'click', function () {
	tableOne = '<tr>' + 
     
			        '<td><input type="text" class="sr_no form-control" name="<portlet:namespace/>sr_no " readonly="readonly" value="'+counterFT+'"></td>' +
			        '<td><input type="text" class="name_of_pension_fund form-control" name="<portlet:namespace/>name_of_pension_fund'+counterFT+'"></td>' +
			        '<td><input type="text" class="auc form-control" name="<portlet:namespace/>auc'+counterFT+'" onkeypress="return onlyNumberKey(event)"></td>' +
			        '<td><input type="text" class="aum form-control" name="<portlet:namespace/>aum'+counterFT+'" onkeypress="return onlyNumberKey(event)"></td>' +
			        '<td><input type="text" class="aum_under_coustody form-control" name="<portlet:namespace/>aum_under_coustody'+counterFT+'" onkeypress="return onlyNumberKey(event)"></td>' +
			        '<td><input type="text" class="asset_not_under_coustody form-control" name="<portlet:namespace/>asset_not_under_coustody'+counterFT+'" onkeypress="return onlyNumberKey(event)"></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeOne" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableDetailsOfAsset tbody').append(tableOne);

    counterFT++;
	
     $('.name_of_pension_fund').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.auc').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.aum').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.aum_under_coustody').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.asset_not_under_coustody').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });  
    
} );

 
$("#myTableDetailsOfAsset").on('click','.removeOne',function(){
    $(this).closest('tr').remove();
 });
 
 /* table one -------------------------------- end  */
 
 
 var counterST = 2;

$('#addRowTwo').on( 'click', function () {
	tableTwo = '<tr>' + 
     
			        '<td><input type="text" class="sr_no form-control" name="<portlet:namespace/>sr_no " readonly="readonly" value="'+counterST+'"></td>' +
			        '<td><input type="text" class="particulars form-control" name="<portlet:namespace/>particulars'+counterST+'"></td>' +
			        '<td><input type="text" class="amount_in_crores form-control" name="<portlet:namespace/>amount_in_crores'+counterST+'" onkeypress="return onlyNumberKey(event)"></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeTwo" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableParticulars tbody').append(tableTwo);

     counterST++;
	
     $('.particulars').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.amount_in_crores').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
     
    
} );

 
$("#myTableParticulars").on('click','.removeTwo',function(){
    $(this).closest('tr').remove();
 });
 
 
 /* Ajax call */
 
 $(".animaion").hide();
$('#Annexure-oneB-Form').on('submit', function(e){ 
	e.preventDefault();
	
	
	if($( "#Annexure-oneB-Form" ).valid()){
		
		var rowCountFT = $("#myTableDetailsOfAsset tr").length -1;
		
		var rowCountST = $("#myTableParticulars tr").length -1;
		
		$('.rowcount_first_table').val(rowCountFT);
		$('.rowcount_secound_table').val(rowCountST);
		
        var fd = new FormData(this);
        $(".animaion").show();
        $.ajax({
            url: '<%=annexureonebformtURL%>',  
            type: 'POST',
            cache: false,
            data:fd,
            contentType: false,
            processData: false,
            success:function(data){
            	console.log("result - ",data);        		
          		if(data == "success"){
          			 $(".animaion").hide();
               		toastr.success('Form has been submitted successfully!');
               		$("#Annexure-oneB-Form")[0].reset();
          		}else{
          			console.log("Error Occured in back-end call");
               		$(".animaion").hide();
               		toastr.error('An error occured while submitting the form - Contact Admin');
          		}
            },
            error: function() {
           		console.log("Error Occured in ajax call");
           		$(".animaion").hide();
           		toastr.error('An error occured while submitting the form');
            },
            complete: function(){
            	$(".animaion").hide();
	        }
        });
	}else{
		toastr.error('Please fill the required field(s).');
		}
});

 
</script>

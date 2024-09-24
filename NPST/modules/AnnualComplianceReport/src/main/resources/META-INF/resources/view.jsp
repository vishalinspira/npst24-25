<%@ include file="/init.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"/>

<portlet:resourceURL id="/AnnualCoplianceReport/save" var="annualcompliancereportURL"></portlet:resourceURL>

<%-- <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> ANNUAL COMPLIANCE REPORT
                </div>
            </div>
        </div>
	 --%>
	<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row">
	    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
	        <div class="nps-report">
		<form id="Annual-compliance-report-Form" action="#" method="post">
		    <input type="hidden" class="rowcount_one" name="<portlet:namespace />rowcount_one">
		    <input type="hidden" class="rowcount_two" name="<portlet:namespace />rowcount_two">
		    <input type="hidden" class="rowcount_three" name="<portlet:namespace />rowcount_three">
		    <input type="hidden" class="rowcount_four" name="<portlet:namespace />rowcount_four">
		    <input type="hidden" class="rowcount_five" name="<portlet:namespace />rowcount_five">
		    <input type="hidden" class="rowcount_six" name="<portlet:namespace />rowcount_six">
		    <input type="hidden" class="rowcount_seven" name="<portlet:namespace />rowcount_seven">
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<p>
							<strong>Summary Report on Annual Compliance Certificates submitted by Pension Funds for FY 2020-2021 </strong>
						</p>
						<span class="text-danger" id="confirm-date-error"></span>
					</div>
				</div>
			</div>
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> SBI Pension Funds Pvt. Ltd. </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualOne" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no_One" id="sr_no" name="<portlet:namespace />sr_no_One" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsOne" name="<portlet:namespace />detailsOne1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedOne" name="<portlet:namespace />information_submittedOne1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsOne" name="<portlet:namespace />commentsOne1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
			
			<!-- 2nd table ------------------------ start  -->
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> UTI Retirement Solutions Ltd. </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualTwo" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no_Two" name="<portlet:namespace />sr_no_Two" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsTwo" name="<portlet:namespace />detailsTwo1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedTwo" name="<portlet:namespace />information_submittedTwo1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsTwo" name="<portlet:namespace />commentsTwo1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
			
			<!-- 3nd table ----------------------------start -->
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> LIC Pension Fund </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualThree" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no" id="sr_no_Three" name="<portlet:namespace />sr_no_Three" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsThree" name="<portlet:namespace />detailsThree1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedThree" name="<portlet:namespace />information_submittedThree1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsThree" name="<portlet:namespace />commentsThree1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
			
			<!-- 4th table ---------------------------start -->
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> HDFC Pension Management Company Limited </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualFour" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no_Four" name="<portlet:namespace />sr_no_Four" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsFour" name="<portlet:namespace />detailsFour1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedFour" name="<portlet:namespace />information_submittedFour1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsFour" name="<portlet:namespace />commentsFour1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
			
			<!-- 5th table ------------------------start -->
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> ICICI Prudential Pension Funds Management Co. Ltd </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualFive" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no_Five" name="<portlet:namespace />sr_no_Five" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsFive" name="<portlet:namespace />detailsFive1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedFive" name="<portlet:namespace />information_submittedFive1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsFive" name="<portlet:namespace />commentsFive1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
			
			<!-- 6th table ---------------------------start -->
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> Aditya Birla Sun Life Pension Management Limited </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualSix" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no_Six"  name="<portlet:namespace />sr_no_Six" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsSix" name="<portlet:namespace />detailsSix1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedSix" name="<portlet:namespace />information_submittedSix1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsSix" name="<portlet:namespace />commentsSix1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
			
			<!-- 7th table -------------------------------start -->
			
			<br><br>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" align="center">
						<p>
							Annual Compliance certificate of &nbsp;&nbsp;<strong> Kotak Mahindra Pension Fund Ltd. </strong>
						</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="text-end">
				     <button id="addRow1" class="common-btn d-inline-block table-add glyphicon glyphicon-plus text-capitalize mb-3 mt-md-0 mt-sm-3 mt-3" type="button">Add Row</button>
				</div>
			</div>
			<br>

			<table id="myTableAnnualSeven" class="table table-bordered table-striped table-responsive">
	
				<thead>
					<tr>
						<th>Sr no. </th>
						<th>Details </th>
						<th>Complied with / Information submitted </th>
						<th>Comments / Recommendation of NPS Trust </th>
						<th data-orderable="false">Remove</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>
							<input type="text" class="form-control sr_no_Seven" name="<portlet:namespace />sr_no_Seven" readonly="readonly" value="1"><br>
						</td>
						<td>
							<textarea class="form-control detailsSeven" name="<portlet:namespace />detailsSeven1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control information_submittedSeven" name="<portlet:namespace />information_submittedSeven1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td>
							<textarea class="form-control commentsSeven" name="<portlet:namespace />commentsSeven1" rows="4" cols="50" maxlength="2000"></textarea><br>
						</td>
						<td class="text-center">
						</td>
						
					</tr> 
				</tbody>
	
			</table>
			<br><br>
		
					<div class="nps-input-box">
		                	<button id="btn-submit" class="common-btn d-inline-block" name="Submit" type="button" >Save</button>
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

.page_title {
	font-size: 18px;
}

</style>

<script type="text/javascript">

var tableOne = null;
var tabletwo = null;
var tableThree = null;
var tableFour = null;
var tableFive = null;
var tableSix = null;
var tableSeven = null;
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
	
	tableOne = $("#myTableAnnualOne").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	tabletwo = $("#myTableAnnualTwo").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	tableThree = $("#myTableAnnualThree").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	tableFour = $("#myTableAnnualFour").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	tableFive = $("#myTableAnnualFive").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	tableSix = $("#myTableAnnualSix").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
	tableSeven = $("#myTableAnnualSeven").DataTable({
	       "paging":   false,
	       "ordering": false,
	       "info":     false,
	       "searching": false
	       
	   	});
});

$("#Annual-compliance-report-Form").validate({
  	rules: {
  	 <portlet:namespace/>detailsOne1: {
  	      	required: true
  	    },
  	 <portlet:namespace/>information_submittedOne1: {
        	required: true
      },
  	<portlet:namespace/>commentsOne1: {
      	required: true
    },
    
 	 <portlet:namespace/>detailsTwo1: {
	      	required: true
	    },
	 <portlet:namespace/>information_submittedTwo1: {
     	required: true
   },
	<portlet:namespace/>commentsTwo1: {
   	required: true
 },
 
	 <portlet:namespace/>detailsThree1: {
	      	required: true
	    },
	 <portlet:namespace/>information_submittedThree1: {
     	required: true
   },
	<portlet:namespace/>commentsThree1: {
   	required: true
 },
 
	 <portlet:namespace/>detailsFour1: {
	      	required: true
	    },
	 <portlet:namespace/>information_submittedFour1: {
     	required: true
   },
	<portlet:namespace/>commentsFour1: {
   	required: true
 },
 
	 <portlet:namespace/>detailsFive1: {
	      	required: true
	    },
	 <portlet:namespace/>information_submittedFive1: {
     	required: true
   },
	<portlet:namespace/>commentsFive1: {
   	required: true
 },
 
	 <portlet:namespace/>detailsSix1: {
	      	required: true
	    },
	 <portlet:namespace/>information_submittedSix1: {
     	required: true
   },
	<portlet:namespace/>commentsSix1: {
   	required: true
 },
 
	 <portlet:namespace/>detailsSeven1: {
	      	required: true
	    },
	 <portlet:namespace/>information_submittedSeven1: {
     	required: true
   },
	<portlet:namespace/>commentsSeven1: {
   	required: true
 },
  },  

}); 

var counterFT = 2;

$('#addRowAnnualOne').on( 'click', function () {
	tableOne = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_One form-control" name="<portlet:namespace/>sr_no_One " readonly="readonly" value="'+counterFT+'"></td>' +
			        '<td><textarea class="form-control detailsOne" name="<portlet:namespace />detailsOne'+counterFT+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedOne" name="<portlet:namespace />information_submittedOne'+counterFT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsOne" name="<portlet:namespace />commentsOne'+counterFT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeOne" id="removeRowOne" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualOne tbody').append(tableOne);

    counterFT++;
	
     $('.detailsOne').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedOne').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsOne').each(function() {
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

$("#myTableAnnualOne").on('click','.removeOne',function(){
    $(this).closest('tr').remove();
 });
 
 /* table one ------------------------------ end */
 
 var counterST = 2;

$('#addRowAnnualTwo').on( 'click', function () {
	tableTwo = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_Two form-control" name="<portlet:namespace/>sr_no_Two " readonly="readonly" value="'+counterST+'"></td>' +
			        '<td><textarea class="form-control detailsTwo" name="<portlet:namespace />detailsTwo'+counterST+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedTwo" name="<portlet:namespace />information_submittedTwo'+counterST+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsTwo" name="<portlet:namespace />commentsTwo'+counterST+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeTwo" id="removeRowTwo" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualTwo tbody').append(tableTwo);

     counterST++;
	
     $('.detailsTwo').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedTwo').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsTwo').each(function() {
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

$("#myTableAnnualTwo").on('click','.removeTwo',function(){
    $(this).closest('tr').remove();
 });
 
 
 /* table two ---------------------- end */
 
 var counterTT = 2;

$('#addRowAnnualThree').on( 'click', function () {
	tableThree = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_Three form-control" name="<portlet:namespace/>sr_no_Three " readonly="readonly" value="'+counterTT+'"></td>' +
			        '<td><textarea class="form-control detailsThree" name="<portlet:namespace />detailsThree'+counterTT+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedThree" name="<portlet:namespace />information_submittedThree'+counterTT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsThree" name="<portlet:namespace />commentsThree'+counterTT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeThree" id="removeRowThree" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualThree tbody').append(tableThree);

     counterTT++;
	
     $('.detailsThree').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedThree').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsThree').each(function() {
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

$("#myTableAnnualThree").on('click','.removeThree',function(){
    $(this).closest('tr').remove();
 });
 
 /* table three -------------------- end */
 
 var counterFFT = 2;

$('#addRowAnnualFour').on( 'click', function () {
	tableFour = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_Four form-control" name="<portlet:namespace/>sr_no_Four " readonly="readonly" value="'+counterFFT+'"></td>' +
			        '<td><textarea class="form-control detailsFour" name="<portlet:namespace />detailsFour'+counterFFT+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedFour" name="<portlet:namespace />information_submittedFour'+counterFFT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsFour" name="<portlet:namespace />commentsFour'+counterFFT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeFour" id="removeRowFour" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualFour tbody').append(tableFour);

     counterFFT++;
	
     $('.detailsFour').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedFour').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsFour').each(function() {
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

$("#myTableAnnualFour").on('click','.removeFour',function(){
    $(this).closest('tr').remove();
 });
 
 
 /* table four ------------------------ end */
 
 var counterFivT = 2;

$('#addRowAnnualFive').on( 'click', function () {
	tableFive = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_Five form-control" name="<portlet:namespace/>sr_no_Five " readonly="readonly" value="'+counterFivT+'"></td>' +
			        '<td><textarea class="form-control detailsFive" name="<portlet:namespace />detailsFive'+counterFivT+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedFive" name="<portlet:namespace />information_submittedFive'+counterFivT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsFive" name="<portlet:namespace />commentsFive'+counterFivT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeFive" id="removeRowFive" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualFive tbody').append(tableFive);

     counterFivT++;
	
     $('.detailsFive').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedFive').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsFive').each(function() {
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

$("#myTableAnnualFive").on('click','.removeFive',function(){
    $(this).closest('tr').remove();
 });
 
 
 /* table five -------------------------------- end */

 
 var counterSixT = 2;

$('#addRowAnnualSix').on( 'click', function () {
	tableSix = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_Six form-control" name="<portlet:namespace/>sr_no_Six " readonly="readonly" value="'+counterSixT+'"></td>' +
			        '<td><textarea class="form-control detailsSix" name="<portlet:namespace />detailsSix'+counterSixT+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedSix" name="<portlet:namespace />information_submittedSix'+counterSixT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsSix" name="<portlet:namespace />commentsSix'+counterSixT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeSix" id="removeRowSix" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualSix tbody').append(tableSix);

     counterSixT++;
	
     $('.detailsSix').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedSix').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsSix').each(function() {
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

$("#myTableAnnualSix").on('click','.removeSix',function(){
    $(this).closest('tr').remove();
 });
 
 
 /* table six ----------------------------- end */
 
 
 var counterSevenT = 2;

$('#addRowAnnualSeven').on( 'click', function () {
	tableSeven = '<tr>' + 
     
			        '<td><input type="text" class="sr_no_Seven form-control" name="<portlet:namespace/>sr_no_Seven " readonly="readonly" value="'+counterSevenT+'"></td>' +
			        '<td><textarea class="form-control detailsSeven" name="<portlet:namespace />detailsSeven'+counterSevenT+'" rows="4" cols="50" maxlength="2000"></textarea></td>' +
			        '<td><textarea class="form-control information_submittedSeven" name="<portlet:namespace />information_submittedSeven'+counterSevenT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td><textarea class="form-control commentsSeven" name="<portlet:namespace />commentsSeven'+counterSevenT+'" rows="4" cols="50" maxlength="2000"></textarea><br></td>' +
			        '<td class="text-center"><button class="btn btn-danger btn-sm btn-small removeSeven" id="removeRowSeven" type="button" style="padding:5px 10px"><span class="fa fa-minus-circle"></span></button> </td>'+
					'</tr>'

     $('#myTableAnnualSeven tbody').append(tableSeven);

     counterSevenT++;
	
     $('.detailsSeven').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.information_submittedSeven').each(function() {
        $(this).rules("add", 
            {
	        	required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
    
    $('.commentsSeven').each(function() {
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

$("#myTableAnnualSeven").on('click','.removeSeven',function(){
    $(this).closest('tr').remove();
 });
 
 
 /* table seven --------------------------end */
$(".animaion").hide();
 
$('#Annual-compliance-report-Form').on('submit', function(e){ 
	e.preventDefault();
	
	
	if($( "#Annual-compliance-report-Form" ).valid()){
		
		var rowcountOne = $("#myTableAnnualOne tr").length -1;
		var rowcountTwo = $("#myTableAnnualTwo tr").length -1;
		var rowcountThree = $("#myTableAnnualThree tr").length -1;
		var rowcountFour = $("#myTableAnnualFour tr").length -1;
		var rowcountFive = $("#myTableAnnualFive tr").length -1;
		var rowcountSix = $("#myTableAnnualSix tr").length -1;
		var rowcountSeven = $("#myTableAnnualSeven tr").length -1;
		
		$('.rowcount_one').val(rowcountOne);
		$('.rowcount_two').val(rowcountTwo);
		$('.rowcount_three').val(rowcountThree);
		$('.rowcount_four').val(rowcountFour);
		$('.rowcount_five').val(rowcountFive);
		$('.rowcount_six').val(rowcountSix);
		$('.rowcount_seven').val(rowcountSeven);
		
        var fd = new FormData(this);
        $(".animaion").show();
        $.ajax({
            url: '<%=annualcompliancereportURL%>',  
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
               		$("#Annual-compliance-report-Form")[0].reset();
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
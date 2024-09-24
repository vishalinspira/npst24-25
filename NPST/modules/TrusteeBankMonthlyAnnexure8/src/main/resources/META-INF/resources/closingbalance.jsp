<%-- <%@ include file="/init.jsp" %>

<div class="closingbalance">

<div class="row">
	<div class="col-md-12">
         <h2 class="text-center my-3 font-weight-bold">Annexure 8 - Closing balance confirmation for NPS Trust Collection Accounts</h2>
    </div>
</div>
<br><br>

<form id="closingBalForm" action="#" method="post">

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label>Date</label>
					<input type="date" class="form-control" id="closing-balance-date" name='<portlet:namespace/>closing-balance-date'>
					<span id="date-error" style="color:red; font-size:12px"></span>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<p>The Chief Executive Officer</p>
					<p>National Pension System (NPS) Trust</p>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="row">
						<div class="col">
							<label>Address Line 1</label>
							<input type="text" class="form-control" id="address-line-1" name='<portlet:namespace/>address-line-1' required>
							<span id="ad1-error" style="color:red; font-size:12px"></span>
						</div>	
					</div><br>
					
					<div class="row">
						<div class="col">
							<label>Address Line 2</label>
							<input type="text" class="form-control" id="address-line-2" name='<portlet:namespace/>address-line-2'>
						</div>	
					</div><br>
					
					<div class="row">
						<div class="col">
							<label>Address Line 3</label>
							<input type="text" class="form-control" id="address-line-3" name='<portlet:namespace/>address-line-3'>
						</div>	
					</div><br>
	
				</div>
			</div>
		</div>
		<br>
		
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<p>Dear Sir, </p>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
		         <h4 class="text-center my-3 font-weight-bold">Sub: Bank Balance Confirmation as on</h4>
		    </div>
		</div>
		<br>
		
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<p>
						We hereby confirm availability of bank balance as per detail
						mentioned below in NPS Trust A/C - CENTRAL GOVERNMENT account
						maintained with our Corporate Banking Branch as on <input
						type="date" id="exit-date" name='<portlet:namespace/>exit-date'
						required>
					</p>
				</div>
			</div>
		</div>

		<table id="myTable" class="table table-bordered table-striped">

			<thead>
				<tr>
					<th>ACCOUNT NUMBER</th>
					<th>NAME OF ACCOUNT HOLDER </th>
					<th>TOTAL BALANCE (Amount in Rs.)</th>
					<th>CLEARED BALANCE (AMOUNT IN Rs.)</th>
					<th>UNCLEARED BALANCE (AMOUNT IN Rs.)</th>
				</tr>
			</thead>
			
			<tbody>
				<tr>
					<td>
						<input type="text" class="account-number" id="account-number" name="<portlet:namespace />account-number"><br>
						<span id="account-number-error" style="color:red; font-size:12px"></span>
					</td>
					<td>
						<input type="text" class="account-holder" id="account-holder" name="<portlet:namespace />account-holder"><br>
						<span id="account-holder-error" style="color:red; font-size:12px"></span>
					</td>
					<td>
						<input type="text" class="total-balance" id="total-balance" name="<portlet:namespace />total-balance"><br>
						<span id="total-balance-error" style="color:red; font-size:12px"></span>
					</td>
					<td>
						<input type="text" class="cleared-balance" id="cleared-balance" name="<portlet:namespace />cleared-balance"><br>
						<span id="cleared-balance-error" style="color:red; font-size:12px"></span>
					</td>
					<td>
						<input type="text" class="uncleared-balance" id="uncleared-balance" name="<portlet:namespace />uncleared-balance"><br>
						<span id="uncleared-balance-error" style="color:red; font-size:12px"></span>
					</td>
				</tr>
			</tbody>

		</table>
		<br><br>
		
		<div class="row">
           	<div class="col-md-1">
                <div class="form-group">
                    <label>Yours faithfully</label>
                </div>
           	</div>
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" id="name" name='<portlet:namespace/>name' required>
                </div>
           	</div>
		</div>
		
		<div class="row">
           	<div class="col-md-2">
                <div class="form-group">
                    <input type="text" id="designation" name='<portlet:namespace/>designation' required>
                </div>
           	</div>
           	<div class="col-md-1">
                <div class="form-group">
                    <label>Designation</label>
                </div>
           	</div>
		</div>
		
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="row">
						<div class="col">
							<label>Yours faithfully</label>
						</div>	
					</div>
					<div class="row">
						<div class="col">
							<input type="text" id="name" name='<portlet:namespace/>name' placeholder="Name" required>
						</div>	
					</div><br>
					
					<div class="row">
						<div class="col">
							<input type="text" id="designation" name='<portlet:namespace/>designation' placeholder="Designation" required>
						</div>	
					</div><br>
					<div class="row">
						<div class="col">
							<label>Designation</label>
						</div>	
					</div>
				
				</div>
			</div>
		</div>

		<div class="row text-center">
            <div class="col-md-12">
                <input type="submit" class="btn btn-primary" id="btn-submit" value="Submit">
            </div>
        </div>
		
		

	</form>

</div>

<!-- jquery table links -->
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<link
  rel="stylesheet"
  href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css"
/>
<!-- ------------------------ -->

<script type="text/javascript">
	var table = null;
  $(document).ready(function() {
   table = $("#myTable").DataTable();
});
</script>

<style type="text/css">
#exit-date, #name, #designation {
    outline: 0;
    border-width: 0 0 2px;
}
</style>

<script type="text/javascript">

$(document).ready(function() {
	$("#closingBalForm").on('submit', (function(e) {
	    e.preventDefault();
	}));
	
	$("#btn-submit").click(function() {
		
		var valid = false;
		
		var closingbaldate = $("#closing-balance-date").val();
		var isclosingdateValid = false; 
		console.log("Inside", validateEmptyFields(closingbaldate));
		if (validateEmptyFields(closingbaldate)) {
			showErrorMessage("#date-error", "This field is required");
			isclosingdateValid;
			//return;
		} else {
			removeErrorMessage("#date-error");
			isclosingdateValid = true;
		}
		
		var ad1 = $("#address-line-1").val();
		var isad1Valid = false;
		if (validateEmptyFields(ad1)) {
			showErrorMessage("#ad1-error", "This field is required");
			isad1Valid;
			//return;
		} else {
			removeErrorMessage("#ad1-error");
			isad1Valid = true;
		}
		
		var accountNumber = $("#account-number").val();
		var isaccountNumberValid = false;
		if (validateEmptyFields(accountNumber)) {
			showErrorMessage("#account-number-error", "This field is required");
			isaccountNumberValid;
			//return;
		} else {
			removeErrorMessage("#account-number-error");
			isaccountNumberValid = true;
		}
		
		var accountHolder = $("#account-holder").val();
		var isaccountHolderValid = false;
		if (validateEmptyFields(accountHolder)) {
			showErrorMessage("#account-holder-error", "This field is required");
			isaccountHolderValid;
			//return;
		} else {
			removeErrorMessage("#account-holder-error");
			isaccountHolderValid = true;
		}
		
		var totalBalance = $("#total-balance").val();
		var istotalBalanceValid = false;
		if (validateEmptyFields(totalBalance)) {
			showErrorMessage("#total-balance-error", "This field is required");
			istotalBalanceValid;
			//return;
		} else {
			removeErrorMessage("#total-balance-error");
			istotalBalanceValid = true;
		}
		
		var clearedBalance = $("#cleared-balance").val();
		var isclearedBalanceValid = false;
		if (validateEmptyFields(clearedBalance)) {
			showErrorMessage("#cleared-balance-error", "This field is required");
			isclearedBalanceValid;
			//return;
		} else {
			removeErrorMessage("#cleared-balance-error");
			isclearedBalanceValid = true;
		}
		
		var unclearedBalance = $("#uncleared-balance").val();
		var isunclearedBalanceValid = false;
		if (validateEmptyFields(unclearedBalance)) {
			showErrorMessage("#uncleared-balance-error", "This field is required");
			isunclearedBalanceValid;
			//return;
		} else {
			removeErrorMessage("#uncleared-balance-error");
			isunclearedBalanceValid = true;
		}
		
		//if()

	});
	

});

	function validateEmptyFields(value) {
		return value == "";
	}
	
	function showErrorMessage(elementId, errorMessage) {
		$(elementId).html(errorMessage);
	}
	
	function removeErrorMessage(elementId) {
		$(elementId).html("");
	}



</script>

<style>

	#myTable_length, #myTable_filter, #myTable_info, #myTable_paginate {
		display: none;
	}
</style>











 --%>
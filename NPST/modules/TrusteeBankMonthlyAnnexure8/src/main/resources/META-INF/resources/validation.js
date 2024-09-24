function submitForm() {
	
	var closingbaldate = $("closing-balance-date").val();
	if (validateEmptyFields(closingbaldate)) {
		showErrorMessage("#date-error", "This field is required");
		return;
	} else {
		removeErrorMessage("closing-balance-date");
	}
	
	var ad1 = $("address-line-1").val();
	if (validateEmptyFields(ad1)) {
		showErrorMessage("#ad1-error", "This field is required");
		return;
	} else {
		removeErrorMessage("address-line-1");
	}
	
	var accountNumber = $("account-number").val();
	if (validateEmptyFields(ad1)) {
		showErrorMessage("#account-number-error", "This field is required");
		return;
	} else {
		removeErrorMessage("account-number");
	}
	
	var accountHolder = $("account-holder").val();
	if (validateEmptyFields(ad1)) {
		showErrorMessage("#account-holder-error", "This field is required");
		return;
	} else {
		removeErrorMessage("account-holder");
	}
	
	var totalBalance = $("total-balance").val();
	if (validateEmptyFields(ad1)) {
		showErrorMessage("#total-balance-error", "This field is required");
		return;
	} else {
		removeErrorMessage("total-balance");
	}
	
	var clearedBalance = $("cleared-balance").val();
	if (validateEmptyFields(ad1)) {
		showErrorMessage("#cleared-balance-error", "This field is required");
		return;
	} else {
		removeErrorMessage("cleared-balance");
	}
	
	var unclearedBalance = $("uncleared-balance").val();
	if (validateEmptyFields(ad1)) {
		showErrorMessage("#uncleared-balance-error", "This field is required");
		return;
	} else {
		removeErrorMessage("uncleared-balance");
	}
	
	
}

function validateEmptyFields(value) {
	return value == "";
}

function showErrorMessage(elementId, errorMessage) {
	$(elementId).html(errorMessage);
}

function removeErrorMessage(elementId) {
	$(elementId).html("");
}

















<%@ include file="/init.jsp"%>

<nav aria-label="breadcrumb">
  <ol class="breadcrumb bg-transparent">
  	<li class="breadcrumb-item">Dashboard</li>
    <li class="breadcrumb-item active" aria-current="page">Submitted to NPS Trust</li>
  </ol>
</nav>

<div class="nps-page-main pending-table mt-md-0 mt-sm-3 mt-3">
   <div class="row">
        <div class="col-12">
            <div class="text-right">
                <p  class="back_btn"><a class="backbtn" href="/web/guest/dashboard"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
            </div>
        </div>
    </div>
    
   <div class="nps-pending-table">
       <div class="mydataTable">
            <ul class="nav nav-tabs border-0 mb-3 nps-report-main">
                <li class="nav-item" onclick='createReportsTable("all_reports_table_daily", "Daily");'>
                    <a data-bs-toggle="tab" class="nav-link active p-0" href="#pills-daily"> Daily </a>
                </li>
                <li class="nav-item" onclick='createReportsTable("all_reports_table_weekly", "Weekly");'>
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-weekly"> Weekly </a>
                </li>
                <li class="nav-item" onclick='createReportsTable("all_reports_table_monthly", "Monthly");'>
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-monthly"> Monthly</a>
                </li>
				<li class="nav-item" onclick='createReportsTable("all_reports_table_periodically", "Periodically");'>
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-periodically"> Periodically</a>
                </li>
                <li class="nav-item" onclick='createReportsTable("all_reports_table_quarterly", "Quarterly");'>
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-quarterly"> Quarterly</a>
                </li>
                <li class="nav-item" onclick='createReportsTable("all_reports_table_half_yearly", "Half-Yearly");'>
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-half-yearly"> Half-Yearly</a>
                </li>
				<li class="nav-item" onclick='createReportsTable("all_reports_table_annually", "Annually");'>
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-annually"> Annually</a>
                </li>
            </ul>
                            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane active py-3" id="pills-daily">
                	<table id="all_reports_table_daily" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-weekly">
                	<table id="all_reports_table_weekly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-monthly">
                	<table id="all_reports_table_monthly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-periodically">
                	<table id="all_reports_table_periodically" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-quarterly">
                	<table id="all_reports_table_quarterly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-half-yearly">
                	<table id="all_reports_table_half_yearly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-annually">
                	<table id="all_reports_table_annually" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

$(document).ready(function(){
	$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
	createReportsTable("all_reports_table_daily", "Daily");
	
	var hash = window.location.hash;
    let cururl = $("a.backbtn").attr("href");
	$("a.backbtn").attr("href",cururl+hash);
});

function createReportsTable(tableName, durationType) {
	let urlObject = new URL("${fetchReportsResourceCommandURL}");
	urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	$.ajax({
	  	type: "post",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>reportsType": "ALL",
			"<portlet:namespace/>durationType": durationType
		},
		success: function(jsonArray) {	
			$("#"+tableName).DataTable({
				searching : true,
				pagination : "bootstrap",
				filter : true,
				data : jsonArray,
				destroy : true,
				lengthMenu : [ 5, 10, 25 ],
				pageLength : 10,
				order: [[2, 'desc']],
				language: {
		            sLengthMenu: " _MENU_",
		            "info": "Showing  _START_  to  _END_  of  _TOTAL_  entries",
		            oPaginate: {
		                sNext: 'NEXT',
		                sPrevious: 'PREV'
		            }
		     	},
				"dom": '<"top"f>rt<"bottom"<"left"li>p><"clear">',
				"columnDefs": [
			        { "targets": [1,2], "searchable": false }
			    ],
				"columns" : [
					{
						data : "moduleName",
						title: "Report Name",
						"render" : function(data, type, meta) {
							
							if(meta.moduleName && meta.url) {
								return '<a href="'+ meta.url +'" download>'+ meta.moduleName +'</a>';
							} else if(meta.moduleName) {
								return meta.moduleName;
							} else {
								return "NA";
							}
							
						}
					},
					/* {
						data : "submittedBy",
						title: "Submitted By",
						name : "submittedBy"
					}, */
					{
						data : "dueDate",
						title: "Due Date",
						name : "dueDate"
					},
					{
						data : "createDate",
						title: "Submitted On",
						name : "createDate"
					}
				]					
			});
		}	 
	});
}

</script>
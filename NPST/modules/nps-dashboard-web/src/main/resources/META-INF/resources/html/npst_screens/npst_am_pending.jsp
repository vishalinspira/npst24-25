<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@ include file="/init.jsp"%>

<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND%>" var="checkReportsMVCResourceCommandURL">
	<portlet:param name="action" value="<%=NPSDashboardWebPortletKeys.PENDING_FOR_UPLOAD_BY_AM%>"/>
</portlet:resourceURL>

	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Pending Reports from AM</li>
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
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link active p-0" href="#pills-monthly"> Monthly</a>
                </li>
				<li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-periodically"> Periodically</a>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-quarterly"> Quarterly</a>
                </li>
				<li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-annually"> Annually</a>
                </li>
			</ul>
			 <div class="tab-content">
                <div class="tab-pane active py-3" id="pills-monthly">
                	<table id="pills_monthly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th>Report Name</th>
								<th>To be Uploaded by</th>
								<th>Due Date</th>
							</tr>
						</thead>
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-periodically">
                	<table id="pills_periodically_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th>Report Name</th>
								<th>To be Uploaded by</th>
								<th>Due Date</th>
							</tr>
						</thead>
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-quarterly">
                	<table id="pills_quarterly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th>Report Name</th>
								<th>To be Uploaded by</th>
								<th>Due Date</th>
							</tr>
						</thead>
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-annually">
                	<table id="pills_annually_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th>Report Name</th>
								<th>To be Uploaded by</th>
								<th>Due Date</th>
							</tr>
						</thead>
					</table>
                </div>
            </div>
		</div>
	</div>
</div>
        
<script>

$(document).ready(function(){
	let urlObject = new URL('${checkReportsMVCResourceCommandURL}');
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
    
    var hash = window.location.hash;
    let cururl = $("a.backbtn").attr("href");
	$("a.backbtn").attr("href",cururl+hash);
	
	$.ajax({
	  	type: "GET",	
		url: urlObject,
		dataType: 'json',
		success: function(jsonObject) {
			$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
			createReportsMasterTable(jsonObject["Monthly"], "pills_monthly_table");
			createReportsMasterTable(jsonObject["Periodically"], "pills_periodically_table");
			createReportsMasterTable(jsonObject["Quarterly"], "pills_quarterly_table");
			createReportsMasterTable(jsonObject["Annually"], "pills_annually_table");
		}
	});
});

function createReportsMasterTable(jsonArray, tableId) {
	$('#'+tableId).DataTable({
		"dom": '<"top"f>rt<"bottom"<"left"li>p><"clear">',
		lengthChange: true,
        buttons: ['excel'],
        bSortable: false,
        searching: true,
        bSort: true,
        responsive: true,
        data : jsonArray,
        language: {
            sLengthMenu: " _MENU_",
            "info": "Showing  _START_  to  _END_  of  _TOTAL_  entries",
            oPaginate: {
                sNext: 'NEXT',
                sPrevious: 'PREV'
            }
        },
		order: [[2, 'desc']],
		"columnDefs": [
	        { "targets": [1,2], "searchable": false }
	    ],
		"columns" : [
			{
				data : "reportName",
				name : "reportName"
			},
			{
				data : "toBeUploadedBy",
				name : "toBeUploadedBy"
			},
			{
				data : "dueDate",
				name : "dueDate"
			}
		]					
	});
	$('a[data-bs-toggle="tab"]').on('shown.bs.tab', function (e) {
        $($.fn.dataTable.tables(true)).DataTable().columns.adjust().responsive.recalc();
      });
}

</script>
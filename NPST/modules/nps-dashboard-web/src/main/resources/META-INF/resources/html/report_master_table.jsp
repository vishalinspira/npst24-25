<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND%>" var="checkReportsMVCResourceCommandURL"/>

<div class="card_blocks">
	<div class="row">
		<div class="col">
			<div class="page_title">
				<img src="<%=request.getContextPath()%>/images/report.png" alt="Reports" /> Admin Master Table
			</div>
		</div>
	</div>

	<div class="average_tabs mx-4">
		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
			<li class="nav-item" role="presentation">
				<a class="nav-link active text-dark" id="pills-daily-tab" data-toggle="pill" href="#pills-daily" role="tab" aria-controls="pills-daily" aria-selected="true">
					<img src="<%=request.getContextPath()%>/images/calendar.png" alt="Daily Average" /> Daily
				</a>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-weekly-tab" data-toggle="pill" href="#pills-weekly" role="tab" aria-controls="pills-weekly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/week.png" alt="Weekly Average" /> Weekly
				</a>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-monthly-tab" data-toggle="pill" href="#pills-monthly" role="tab" aria-controls="pills-monthly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/monthly.png" alt="Monthly Average" /> Monthly
				</a>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-annually-tab" data-toggle="pill" href="#pills-annually" role="tab" aria-controls="pills-annually" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/yearly.png" alt="Half Yearly Average" /> Annually
				</a>
			</li>
		</ul>
		<div class="tab-content" id="pills-tabContent">
			<div class="tab-pane fade show active" id="pills-daily" role="tabpanel" aria-labelledby="pills-daily-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="pills_daily_table" class="dataTable-table table table-bordered">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 34%;"><a href="javascript:void(0)" class="dataTable-sorter">To be Uploaded by</th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Due Date</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade show active" id="pills-weekly" role="tabpanel" aria-labelledby="pills-weekly-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="pills_weekly_table" class="dataTable-table table table-bordered">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 34%;"><a href="javascript:void(0)" class="dataTable-sorter">To be Uploaded by</th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Due Date</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade show active" id="pills-admin-table" role="tabpanel" aria-labelledby="pills-admin-table-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="pills_monthly_table" class="dataTable-table table table-bordered">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 34%;"><a href="javascript:void(0)" class="dataTable-sorter">To be Uploaded by</th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Due Date</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade show active" id="pills-annually" role="tabpanel" aria-labelledby="pills-annually-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="pills_annually_table" class="dataTable-table table table-bordered">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 34%;"><a href="javascript:void(0)" class="dataTable-sorter">To be Uploaded by</th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Due Date</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<style>
.dataTable-container table.dataTable-table {
	width: 100% !important;
}
table.dataTable thead th {
	width: 20% !important;
}
</style>

<script>

$(document).ready(function(){
	let urlObject = new URL('${checkReportsMVCResourceCommandURL}');
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
	$.ajax({
	  	type: "GET",	
		url: urlObject,
		dataType: 'json',
		success: function(jsonObject) {
			createReportsMasterTable(jsonObject["DAILY_JSON_ARRAY"], "pills_daily_table");
			createReportsMasterTable(jsonObject["WEEKLY_JSON_ARRAY"], "pills_weekly_table");
			createReportsMasterTable(jsonObject["MONTHLY_JSON_ARRAY"], "pills_monthly_table");
			createReportsMasterTable(jsonObject["ANNUALLY_JSON_ARRAY"], "pills_annually_table");
		}
	});
});

function createReportsMasterTable(jsonArray, tableId) {
	$('#' + tableId).DataTable({
		searching : true,
		info: false,
		pagination : "bootstrap",
		filter : true,
		data : jsonArray,
		destroy : true,
		lengthMenu : [ 5, 10, 25 ],
		pageLength : 10,
		"dom": '<"top"f>rt<"bottom"lp><"clear">',
		"columns" : [
			{
				data : "reportMasterId",
				name : "reportMasterId"
			},
			{
				data : "reportName",
				name : "reportName"
			},
			{
				data : "isUploaded",
				name : "isUploaded"
			}
		]					
	});
}

</script>
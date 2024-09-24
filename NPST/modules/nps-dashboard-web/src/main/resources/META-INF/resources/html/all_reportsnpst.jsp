<%@ include file="/init.jsp"%>

<div class="card_blocks">
	<div class="row">
		<div class="col">
			<div class="page_title">
				<img src="<%=request.getContextPath()%>/images/report.png" alt="Reports" /> All Reports
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
				<a class="nav-link text-dark" id="pills-periodically-tab" data-toggle="pill" href="#pills-periodically" role="tab" aria-controls="pills-periodically" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/schedule.png" alt="Periodically Average" /> Periodically
				</a>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-quarterly-tab" data-toggle="pill" href="#pills-quarterly" role="tab" aria-controls="pills-quarterly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/pie-chart.png" alt="Periodically Average" /> Quarterly
				</a>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-half-yearly-tab" data-toggle="pill" href="#pills-half-yearly" role="tab" aria-controls="pills-half-yearly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/calendar.png" alt="Half Yearly Average" /> Half Yearly
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
								<table id="all_reports_table_daily" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="pills-weekly" role="tabpanel" aria-labelledby="pills-weekly-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="all_reports_table_weekly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="pills-monthly" role="tabpanel" aria-labelledby="pills-monthly-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="all_reports_table_monthly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="pills-periodically" role="tabpanel" aria-labelledby="pills-periodically-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="all_reports_table_periodically" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="pills-quarterly" role="tabpanel" aria-labelledby="pills-quarterly-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="all_reports_table_quarterly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="pills-half-yearly" role="tabpanel" aria-labelledby="pills-half-yearly-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="all_reports_table_half_yearly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="pills-annually" role="tabpanel" aria-labelledby="pills-annually-tab">
				<div class="">
					<div class="card-body">
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="all_reports_table_annually" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th>
											<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
	createReportsTable("all_reports_table_daily", "Daily");
	createReportsTable("all_reports_table_weekly", "Weekly");
	createReportsTable("all_reports_table_monthly", "Monthly");
	createReportsTable("all_reports_table_periodically", "Periodically");
	createReportsTable("all_reports_table_quarterly", "Quarterly");
	createReportsTable("all_reports_table_half_yearly", "Half-Yearly");
	createReportsTable("all_reports_table_annually", "Annually");
});

function createReportsTable(tableName, durationType) {
	let urlObject = new URL("${fetchReportsResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>reportsType": "ALL",
			"<portlet:namespace/>durationType": durationType
		},
		success: function(jsonArray) {	
			$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
			$('#' + tableName).DataTable({
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
						data : "moduleName",
						"render" : function(data, type, meta) {
							
							if(meta.moduleName) {
								return meta.moduleName;
							} else {
								return "NA";
							}
							
						}
					},
					{
						data : "fileName",
						name : "fileName"
					},
					{
						data : "view",
						"render" : function(data, type, meta) {
							return '<a href="'+ meta.url +'">Click Here</a>';
						}
					},
					{
						data : "status",
						name : "status"
					},
					{
						data : "remarks",
						name : "remarks"
					}
				]					
			});
		}	 
	});
}

</script>
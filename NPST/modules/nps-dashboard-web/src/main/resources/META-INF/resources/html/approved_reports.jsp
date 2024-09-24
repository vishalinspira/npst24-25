<%@ include file="/init.jsp"%>

<div class="card_blocks">
	<div class="row">
		<div class="col">
			<div class="page_title">
				<img src="<%=request.getContextPath()%>/images/report.png" alt="Reports" /> Approved Reports
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
								<table id="approved_reports_table_daily" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Report Uploaded by</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
		<!-- 									<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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
								<table id="approved_reports_table_weekly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>											
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">User Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
											<!-- <th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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
								<table id="approved_reports_table_monthly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>											
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">User Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
										<!-- 	<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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
								<table id="approved_reports_table_periodically" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>											
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">User Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
										<!-- 	<th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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
								<table id="approved_reports_table_quarterly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>											
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">User Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
											<!-- <th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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
								<table id="approved_reports_table_half_yearly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>											
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">User Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
											<!-- <th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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
								<table id="approved_reports_table_annually" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">File Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Module Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Download</th>											
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">User Name</th>
											<th data-sortable="" style="width: 33%;"><a href="#" class="dataTable-sorter">Create Date</th>
											<!-- <th data-sortable="" style="width: 34%;"><a href="#" class="dataTable-sorter">Status</th> -->
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


<!-- 
<div class="modal" id="remarks">
  <input type="hidden" id="hiddenFileEntryId" value=""/>
  <div class="modal-dialog">
    <div class="modal-content">
    
      Modal Header
      <div class="modal-header">
        <h4 class="modal-title">Remarks</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      Modal body
      <div class="modal-body">
        <textarea class="form-control" cols="5" rows="5" placeholder="Enter your Remarks" id="remarks-textarea-id"></textarea>
      </div>
      
      Modal footer
      <div class="modal-footer">
        <button type="button" class="nps-btn" id="submit-remarks-button">Save</button>
        <button type="button" class="nps-btn" data-dismiss="modal" id="close-remarks-button">Close</button>
      </div>
      
    </div>
  </div>
</div>
 -->
 
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
	createReportsTable("approved_reports_table_daily", "Daily");
	createReportsTable("approved_reports_table_weekly", "Weekly");
	createReportsTable("approved_reports_table_monthly", "Monthly");
	createReportsTable("approved_reports_table_periodically", "Periodically");
	createReportsTable("approved_reports_table_quarterly", "Quarterly");
	createReportsTable("approved_reports_table_half_yearly", "Half-Yearly");
	createReportsTable("approved_reports_table_annually", "Annually");
	
	$("#submit-remarks-button").click(saveFileEntryRemarks);
});

function createReportsTable(tableName, durationType) {
	let urlObject = new URL("${fetchReportsResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>reportsType": "APPROVED",
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
						data : "fileName",
						name : "fileName"
					},
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
						data : "view",
						"render" : function(data, type, meta) {
							return '<a href="'+ meta.url +'">Click Here</a>';
						}
					},
					{
						data : "userName",
						name : "userName"
					},
					{
						data : "createDate",
						name : "createDate"
					},
					/* {
						data : "status",
						name : "status"
					}, */
					{
						data : "remarks",
						"render" : function(data, type, meta) {
							if(meta.remarks) {
								return meta.remarks;
							} else {
								return "NA";
							}
						}
					}
				]					
			});
		}	 
	});
}

function setFileEntryid(fileEntryId) {
	$("#hiddenFileEntryId").val(fileEntryId);
}

function saveFileEntryRemarks() {
	$(".animaion").show();
	
	let urlObject = new URL("${addRemarksResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
    
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		data: {
			'<portlet:namespace/>fileEntryId': $("#hiddenFileEntryId").val(),
			'<portlet:namespace/>remarks': $("#remarks-textarea-id").val()
		},
		success: function(data) {
			$(".animaion").hide();
			$("#remarks-textarea-id").val("");
			$("#close-remarks-button").click();
		},
		error: function() {
			$(".animaion").hide();
			$("#remarks-textarea-id").val("");
			$("#close-remarks-button").click();
        }
	});
}

</script>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp"%>
<%
String transitionName = ParamUtil.getString(request, "transitionName","");
%>

<div class="card_blocks">
	<div class="row">
		<div class="col">
			<div class="page_title">
				<img src="<%=request.getContextPath()%>/images/report.png" alt="Reports" /> Pending for Reupload
			</div>
		</div>
	</div>

	<div class="average_tabs mx-4">
		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
			<li class="nav-item" role="presentation">
				<a class="nav-link active text-dark" id="pills-daily-tab" data-toggle="pill" href="#pills-daily" role="tab" aria-controls="pills-daily" aria-selected="true">
					<img src="<%=request.getContextPath()%>/images/calendar.png" alt="Daily Average" /> Daily
				</a>
				<span class="rp_count Daily_count">0</span>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-weekly-tab" data-toggle="pill" href="#pills-weekly" role="tab" aria-controls="pills-weekly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/week.png" alt="Weekly Average" /> Weekly
				</a>
				<span class="rp_count Weekly_count">0</span>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-monthly-tab" data-toggle="pill" href="#pills-monthly" role="tab" aria-controls="pills-monthly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/monthly.png" alt="Monthly Average" /> Monthly
				</a>
				<span class="rp_count Monthly_count">0</span>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-periodically-tab" data-toggle="pill" href="#pills-periodically" role="tab" aria-controls="pills-periodically" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/schedule.png" alt="Periodically Average" /> Periodically
				</a>
				<span class="rp_count Periodically_count">0</span>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-quarterly-tab" data-toggle="pill" href="#pills-quarterly" role="tab" aria-controls="pills-quarterly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/pie-chart.png" alt="Periodically Average" /> Quarterly
				</a>
				<span class="rp_count Quarterly_count">0</span>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-half-yearly-tab" data-toggle="pill" href="#pills-half-yearly" role="tab" aria-controls="pills-half-yearly" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/calendar.png" alt="Half Yearly Average" /> Half Yearly
				</a>
				<span class="rp_count Half-Yearly_count">0</span>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link text-dark" id="pills-annually-tab" data-toggle="pill" href="#pills-annually" role="tab" aria-controls="pills-annually" aria-selected="false">
					<img src="<%=request.getContextPath()%>/images/yearly.png" alt="Half Yearly Average" /> Annually
				</a>
				<span class="rp_count Annually_count">0</span>
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
								<table id="pending_reports_table_daily" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
								<table id="pending_reports_table_weekly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
								<table id="pending_reports_table_monthly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
								<table id="pending_reports_table_periodically" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
								<table id="pending_reports_table_quarterly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
								<table id="pending_reports_table_half_yearly" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
								<table id="pending_reports_table_annually" class="dataTable-table table table-bordered table-responsive">
									<thead>
										<tr>
											<th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Report Name</th>
											<!-- <th data-sortable="" style="width: 20%;"><a href="#" class="dataTable-sorter">Uploaded by</th> -->
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Upload On</th>
											<th data-sortable="" style="width: 13%;"><a href="#" class="dataTable-sorter">Due Date</th>
											<th data-sortable="" style="width: 10%;"><a href="#" class="dataTable-sorter">Action</th>
											<th data-sortable="" style="width: 24%;"><a href="#" class="dataTable-sorter">Remarks</th>
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
	
	
	
		
		
		<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
		  <div class="modal-dialog modal-sm">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body text-center">
		      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
		        <span class="head">Data is Approved.</span>
		      </div>       
		    </div>
		  </div>
		</div>

<style>

	.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}
	
	.modal {
		display: none;
			}
.dataTable-container table.dataTable-table {
	width: 100% !important;
}
.rp_count {
    position: absolute;
    top: -10px;
    right: -5px;
    font-size: 10px;
    font-weight: 600;
    background: #ed9421;
    color: #fff;
    padding: 0px 6px;
    border-radius: 5px;
    z-index: 9; 
}
.average_tabs .nav-item {
	margin-right: 10px;
}

table.dataTable thead th {
	width: 20% !important;
}
</style>

<script>

$(document).ready(function(){
	createReportsTable("pending_reports_table_daily", "Daily");
	createReportsTable("pending_reports_table_weekly", "Weekly");
	createReportsTable("pending_reports_table_monthly", "Monthly");
	createReportsTable("pending_reports_table_periodically", "Periodically");
	createReportsTable("pending_reports_table_quarterly", "Quarterly");
	createReportsTable("pending_reports_table_half_yearly", "Half-Yearly");
	createReportsTable("pending_reports_table_annually", "Annually");
	//alert('<%=transitionName%>');
	if("Approve" === '<%=transitionName%>'){
		$(".head").html("<h6>Data is approved.</h6>");
		$('#success_tic').modal('show');
	}else if("Reject"=== '<%=transitionName%>'){
		$(".head").html("<h6>Data is rejected.</h6>");
		$('#success_tic').modal('show');
	}else if("Reviewed"==='<%=transitionName%>'){
		$(".head").html("<h6>Data sent for approval.</h6>");
		$('#success_tic').modal('show');
	} else if(""!='<%=transitionName%>') {
		$(".head").html("<h6>Data is <%=transitionName%>.</h6>");
		$('#success_tic').modal('show');
	}
});

function createReportsTable(tableName, durationType) {
	let urlObject = new URL("${fetchReportsResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>reportsType": "PENDING",
			"<portlet:namespace/>durationType": durationType
		},
		success: function(jsonArray) {	
			if(jsonArray.length){
				$("."+durationType+"_count").text("0"+jsonArray.length);
			}else{
				$("."+durationType+"_count").hide();
			}
			
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
								return '<a href="'+ meta.url +'">'+meta.moduleName+'</a>';
							} else {
								return "NA";
							}
							
						}
					},
					/*{
						data : "fileName",
						name : "fileName"
					},
					{
						data : "view",
						"render" : function(data, type, meta) {
							return '<a href="'+ meta.url +'">Click Here</a>';
						}
					},*/
					/* {
						data : "userName",
						"render" : function(data, type, meta) {
							return meta.userName;
						}
					}, */
					{
						data : "createDate",
						name : "createDate"
					},
					{
						data : "dueDate",
						name : "dueDate"
					},
					{
						data : "view",
						"render" : function(data, type, meta) {
							if(meta.actionURL){
								return '<a href="'+ meta.actionURL +'">Review / Approve</a>';
							} else {
								return 'Review / Approve';
							}
						}
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
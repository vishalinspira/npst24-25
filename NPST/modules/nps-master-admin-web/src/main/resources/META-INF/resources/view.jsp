<%@ include file="/init.jsp" %>
<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
        Data sent for Review.
      </div>       
    </div>
  </div>
</div>
<div class="card_blocks nps-page-main nps-upload-report">
	<%-- <div class="row">
		<div class="col">
			<div class="page_title">
				<img src="<%=request.getContextPath()%>/images/report.png" alt="Reports" /> Admin Master Table
			</div>
		</div>
	</div> --%>

	<div class="average_tabs mx-4">
		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
			<li class="nav-item" role="presentation">
				<a class="nav-link active text-dark" id="pills-admin-table-tab" data-toggle="pill" href="#pills-admin-table" role="tab" aria-controls="pills-admin-table" aria-selected="true">
					<img src="<%=request.getContextPath()%>/images/calendar.png" alt="Calendar Imager" /> Admin Master
				</a>
			</li>
		</ul>
		<div class="tab-content" id="pills-tabContent">
			<div class="tab-pane fade show active" id="pills-admin-table" role="tabpanel" aria-labelledby="pills-admin-table-tab">
				<div class="">
					<div class="card-body ">
						 <div class="row">
					      <div class="col-lg-7 col-md-10 col-sm-12 col-12">
					         <div class="nps-report">
					            <div class="form_block">
					               <div class=" content">
					                  <div class="row formrow">
					                  	 <h4></h4>
					                     <form id="fileinfo" method="POST" >
						                    <div class="nps-input-box file-upload">
					                           <label>Report Date</label>
					                           <input class="reportDate" name="<portlet:namespace/>reportDate" type="date" value="" >
					                        </div>
					                        <div class="nps-input-box">
					                           <button id="upload" class="common-btn d-inline-block" name="Submit" type="button" >Generate Date</button>
					                        </div>
					                     </form>
					                  </div>
					               </div>
					            </div>
					         </div>
					      </div>
					    </div>
						<div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
							<div class="dataTable-top d-none">
								<div class="dataTable-dropdown"><label><select class="dataTable-selector"><option value="5">5</option><option value="10" selected="">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option></select> entries per page</label></div>
								<div class="dataTable-search"><input class="dataTable-input" placeholder="Search..." type="text"></div>
							</div>
							<div class="dataTable-container">
								<table id="admin_master_table" class="dataTable-table table table-bordered">
									<thead>
										<tr>
											<th data-sortable="" style="width: 33%;">Sl. No</th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Name</a><input type="text" placeholder="Search" /></th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Due Date</a><input type="text" placeholder="Search" /></th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Report Type</a><input type="text" placeholder="Search" />></th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Department</a><input type="text" placeholder="Search" /></th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Intermediary Name</a><input type="text" placeholder="Search" /></th>
											<th data-sortable="" style="width: 33%;"><a href="javascript:void(0)" class="dataTable-sorter">Status</a><input type="text" placeholder="Search" /></th>
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
.modal {
    display: none;
}

.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}
.dataTable-container table.dataTable-table {
	width: 100% !important;
}

table.dataTable thead th {
	width: 33% !important;
}

table.dataTable thead th:last-child {
    width: 34% !important;
}
.nps-page-main table a {
  font-weight: 500;
  text-decoration: none;
  color: #fff;
}


</style>

<script>

$(document).ready(function(){
	createReportsTable();
	
	$.ajax({
	  	type: "GET",	
		url: '${checkReportsMVCResourceCommandURL}',
		dataType: 'json',
		success: function(jsonArray) {	
			createReportsTable(jsonArray);
		}	 
	});
	
	$('#upload').on('click', function(){
		//let fd = new FormData();
		//fd.append("<portlet:namespace/>reportDate")
		$.ajax({
		  	type: "POST",
			url: '${genReportDateMVCResourceCommandURL}',
			data: $("#fileinfo").serialize(),
			dataType: 'json',
			success: function(data) {	
				//createReportsTable(jsonArray);
			}	 
		});
	});
});



function createReportsTable(jsonArray) {
	$('#admin_master_table').DataTable({
		searching : false,
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
				data : "reportUploadLogId",
				name : "reportUploadLogId"
			},
			{
				data : "reportName",
				name : "reportName"
			},
			
			{
				data : "reportDueDate",
				name : "reportDueDate"
			},
			{
				data : "reportType",
				name : "reportType"
			},
			{
				data : "department",
				name : "department"
			},
			{
				data : "intermediaryName",
				name : "intermediaryName"
			},
			{
				data : "isUploaded",
				name : "isUploaded"
			}
		]					
	});
}

</script>
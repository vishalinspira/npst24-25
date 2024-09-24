<%@ include file="/init.jsp"%>
	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Approved Reports</li>
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
                    <a data-bs-toggle="tab" class="nav-link active p-0 tab_active_btn" href="#report-status"> 
                    	Approved Reports
                    </a>
                </li>
            </ul>
            
            <div class="tab-content">
                <div class="tab-pane active py-3" id="report-status">
                	<table id="report_status_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
						<thead>
							<tr>
								<th>Report Name</th>
								<!-- <th>Uploaded By</th> -->
								<th>Due Date</th>
								<th>Upload Date</th>
								<!-- <th>Status</th> -->
								<th>Remarks</th>
								<th>Report Summary</th>
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
	createReportsTable();
});

function createReportsTable(tableName, durationType) {
	let urlObject = new URL("${fetchReportsResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	
    var hash = window.location.hash;
    let cururl = $("a.backbtn").attr("href");
	$("a.backbtn").attr("href",cururl+hash);
    
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>reportsType": "APPROVED_REPORT_SUMMERY",
		},
		success: function(jsonArray) {	
			$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
			$('#report_status_table').DataTable({
				searching : false,
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
				"columns" : [
					{
						data : "fileName",
						"render" : function(data, type, meta) {
							
							if(meta.fileName && meta.url) {
								return '<a href="'+ meta.url +'" download>'+ meta.fileName +'</a>';
							} else if(meta.fileName) {
								return meta.fileName;
							} else {
								return "NA";
							}
							
						}
					},
					/* {
						data : "userName",
						name : "userName"
					}, */
					{
						data : "dueDate",
						name : "dueDate"
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
						name : "remarks"
					},
					{
						data : "workflowInstanceId",
						"render" : function(data, type, meta) {
							var URL = '${reportSummaryMVCRenderCommandURL}&<portlet:namespace/>workflowInstanceId='+ meta.workflowInstanceId + '&<portlet:namespace/>reportUploadLogId='+meta.reportUploadLogId;
							return '<a href="'+ URL +'">View</a>';
						}
					}
				]					
			});
		}	 
	});
}

</script>
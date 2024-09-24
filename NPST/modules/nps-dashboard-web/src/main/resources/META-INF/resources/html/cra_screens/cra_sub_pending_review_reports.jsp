<%@ include file="../../init.jsp"%>

<portlet:renderURL var="getAssigneeUsersURL" windowState="<%=LiferayWindowState.EXCLUSIVE.toString()%>">
	<portlet:param name="mvcRenderCommandName" value="<%=NPSDashboardWebPortletKeys.FETCH_ASSINGEE_RENDER_COMMAND%>"/>
</portlet:renderURL>

<%
String transitionName = ParamUtil.getString(request, "transitionName","");
String department = ParamUtil.getString(renderRequest, "department");
%>

	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Assign Reports</li>
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
                    <a data-bs-toggle="tab" class='nav-link active p-0' href="#pills-monthly"> Monthly</a>
                    <span class="rp_count Monthly_count">0</span>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-quarterly"> Quarterly</a>
                    <span class="rp_count Quarterly_count">0</span>
                </li>
            </ul>
                            <!-- Tab panes -->
            <div class="tab-content">
                <div class='tab-pane active py-3' id="pills-monthly">
                	<table id="pending_reports_table_monthly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                <div class="tab-pane fade py-3" id="pills-quarterly">
                	<table id="pending_reports_table_quarterly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
			</div>
		</div>
		<div class="animaion" style="display: none;">
			<div class="row">
				<div class="loading-animation"></div>
			</div>
		</div>
	</div>
</div>
<div id="assign-modal-ctn"></div>
<!-- Modal -->
<div class="success-modal modal fade" id="success_tic" tabindex="-1"
	aria-labelledby="success_ticLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body text-center">
				<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>
				<span class="head">Data is Approved.</span>
			</div>
		</div>
	</div>
</div>

<style>
.success-modal  .modal-dialog {
	margin: 5% 40%;
}

.success-modal .modal-content {
	width: 20vw;
}
	
.modal {
	display: none;
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

</style>

<script>

$(document).ready(function() {
	createReportsTable2();
	
	 var hash = window.location.hash;
	    let cururl = $("a.backbtn").attr("href");
		$("a.backbtn").attr("href",cururl+hash);
		
		
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


function createReportsTable2() {
	let urlObject = new URL("${fetchSubPendingForReviewResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
    let tabsArray = [];
    tabsArray.push({"tabid":"pending_reports_table_monthly", "dataKey":"Monthly"});
    tabsArray.push({"tabid":"pending_reports_table_quarterly", "dataKey":"Quarterly"});
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>department": "<%= department%>"
		},
		success: function(data) {	
			tabsArray.forEach(function(v, i){
				console.log(data);
				console.log(v);
				
				let jsonArray = data[v.dataKey];
				try {
					if(jsonArray.length){
						$("."+v.dataKey+"_count").text("0"+jsonArray.length);
					}else{
						$("."+v.dataKey+"_count").hide();
					}
				} catch(err) {
					$("."+v.dataKey+"_count").hide();
					jsonArray= [];
				}
				$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
				
				$('#' + v.tabid).DataTable({
					searching : true,
					pagination : "bootstrap",
					filter : true,
					data : jsonArray,
					destroy : true,
					lengthMenu : [ 5, 10, 25 ],
					pageLength : 10,
					order: [[3, 'desc']],
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
							data : "moduleName",
							title: "Report Name",
							"render" : function(data, type, meta) {
								
								if(meta.moduleName) {
									return '<a href="'+ meta.url +'">'+meta.moduleName+'</a>';
								} else {
									return "NA";
								}
								
							}
						},
						{
							data : "intermediaryName",
							name : "intermediaryName",
							title: "Intermediary Name"
						},
						{
							data : "userName",
							title: "Uploaded By",
							"render" : function(data, type, meta) {
								return meta.userName;
							}
						},
						{
							data : "createDate",
							title: "Upload On",
							name : "createDate"
						},
						{
							data : "dueDate",
							title: "Due Date",
							name : "dueDate"
						},
						{
							data : "view",
							title: "Action",
							"render" : function(data, type, meta) {
								var result = "<a href='javascript:void(0);' class='re-assign-task'  data-workflowTaskId='"+meta.workflowTaskId+"' data-department='"+meta.department+"' data-workflowContext='"+meta.workflowContext+"'>Assign</a>";
								return result;
							}
						},
						{
							data : "remarks",
							title: "Remarks",
							name : "remarks"
						}
						
					]					
				});
				
				$(".animaion").hide();
			});
				
		}
	});
}

$(document).on('click', ".re-assign-task", function(){
	const workflowTaskId = $(this).attr("data-workflowTaskId");
	const department = $(this).attr("data-department");
	const workflowContext = $(this).attr("data-workflowContext");
	
	$.ajax({
		url:"${getAssigneeUsersURL}",
		type:"post",
		data: {
			<portlet:namespace/>department: department,
			<portlet:namespace/>workflowTaskId: workflowTaskId,
			<portlet:namespace/>workflowContext: workflowContext
		},
		success: function(data){
			$("#assign-modal-ctn").html(data);
			$("#assignTaskModal").modal('show');
		}
	});
	
});
</script>
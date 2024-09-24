<%@page import="com.nps.dashboard.web.constants.NPSDashboardWebPortletKeys"%>
<%@ include file="/init.jsp"%>

<portlet:resourceURL id="<%=NPSDashboardWebPortletKeys.CHECK_REPORTS_MVC_RESOURCE_COMMAND%>" var="checkReportsMVCResourceCommandURL"/>

<nav aria-label="breadcrumb">
  <ol class="breadcrumb bg-transparent">
  	<li class="breadcrumb-item">Dashboard</li>
    <li class="breadcrumb-item active" aria-current="page">Pending for Upload</li>
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
                    <a data-bs-toggle="tab" class="nav-link active p-0" href="#pills-daily"> Daily </a>
                     <span class="rp_count Daily_count hide">0</span>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-weekly"> Weekly </a>
                    <span class="rp_count Weekly_count hide">0</span>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-monthly"> Monthly</a>
                    <span class="rp_count Monthly_count hide">0</span>
                </li>
                <% if(!isChecker && !isMaker){ %>
					<li class="nav-item">
	                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-periodically"> Periodically</a>
	                    <span class="rp_count Periodically_count hide">0</span>
	                </li>
	                <li class="nav-item">
	                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-quarterly"> Quarterly</a>
	                     <span class="rp_count Quarterly_count hide">0</span>
	                </li>
	                <li class="nav-item">
	                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-half-yearly"> Half-Yearly</a>
	                    <span class="rp_count Half-Yearly_count hide">0</span>
	                </li>
					<li class="nav-item">
	                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-annually"> Annually</a>
	                    <span class="rp_count Annually_count hide">0</span>
	                </li>
				<%} %>
            </ul>
                            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane active py-3" id="pills-daily">
                	<table id="pills_daily_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <div class="tab-pane fade py-3" id="pills-weekly">
                	<table id="pills_weekly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <div class="tab-pane fade py-3" id="pills-monthly">
                	<table id="pills_monthly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <% if(!isChecker && !isMaker){ %>
                <div class="tab-pane fade py-3" id="pills-periodically">
                	<table id="pills_periodically_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <div class="tab-pane fade py-3" id="pills-quarterly">
                	<table id="pills_quarterly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <div class="tab-pane fade py-3" id="pills-half-yearly">
                	<table id="pills_half-yearly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <div class="tab-pane fade py-3" id="pills-annually">
                	<table id="pills_annually_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
                </div>
                <%} %>
            </div>
        </div>
    </div>
</div>

<style>
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
</style>

<script>
	let urlObject = new URL('${checkReportsMVCResourceCommandURL}');
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
    
    var hash = window.location.hash;
    let cururl = $("a.backbtn").attr("href");
	$("a.backbtn").attr("href",cururl+hash);

	let tabsArray = [];
    tabsArray.push({"tabid":"pills_daily_table","dataKey":"Daily"});
    tabsArray.push({"tabid":"pills_weekly_table", "dataKey":"Weekly"});
    tabsArray.push({"tabid":"pills_monthly_table", "dataKey":"Monthly"});
    tabsArray.push({"tabid":"pills_periodically_table", "dataKey":"Periodically"});
    tabsArray.push({"tabid":"pills_quarterly_table", "dataKey":"Quarterly"});
    tabsArray.push({"tabid":"pills_half-yearly_table", "dataKey":"Half-Yearly"});
    tabsArray.push({"tabid":"pills_annually_table", "dataKey":"Annually"});
    
	$.ajax({
	  	type: "get",	
		url: urlObject,
		dataType: 'json',
		success: function(jsonObject) {
			tabsArray.forEach(function(v, i){
				console.log(jsonObject);
				console.log(v);
				
				let jsonArray = jsonObject[v.dataKey];
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
			});
			
			
			console.log(">>>> ",jsonObject);
			$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
			createReportsMasterTable(jsonObject["Daily"], "pills_daily_table");
			createReportsMasterTable(jsonObject["Weekly"], "pills_weekly_table");
			createReportsMasterTable(jsonObject["Monthly"], "pills_monthly_table");
			<% if(!isChecker && !isMaker){ %>
			createReportsMasterTable(jsonObject["Periodically"], "pills_periodically_table");
			createReportsMasterTable(jsonObject["Quarterly"], "pills_quarterly_table");
			createReportsMasterTable(jsonObject["Half-Yearly"], "pills_half-yearly_table");
			createReportsMasterTable(jsonObject["Annually"], "pills_annually_table");
			<% }%>
		}
	});


function createReportsMasterTable(jsonArray, tableId) {
	$("#"+tableId).DataTable({
		searching : true,
		pagination : "bootstrap",
		filter : true,
		data : jsonArray,
		destroy : true,
		lengthMenu : [ 5, 10, 25 ],
		pageLength : 10,
		order: [[2, 'asc']],
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
				data : "reportName",
				name : "reportName",
				title: "Report Name"
			},
			{
				data : "toBeUploadedBy",
				name : "toBeUploadedBy",
				title: "To be Uploaded by"
			},
			{
				data : "dueDate",
				name : "dueDate",
				title: "Due Date"
			}
		]					
	});
	
}
</script>
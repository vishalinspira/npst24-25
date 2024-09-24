<%@ include file="../../init.jsp"%>
<%
String department = ParamUtil.getString(renderRequest, "department");
%>

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
             <p  class="back_btn"><a class="backbtn" href="/web/guest/dashboard#sddCustodian"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
         </div>
      </div>
   </div>


   <div class="nps-pending-table">
       <div class="mydataTable">
            <ul class="nav nav-tabs border-0 mb-3 nps-report-main">
                <li class="nav-item" onclick='createReportsTable("all_reports_table_monthly", "Monthly");'>
                    <a data-bs-toggle="tab" class='nav-link p-0 active' href="#pills-monthly"> Monthly</a>
                </li>
            </ul>
                            <!-- Tab panes -->
            <div class="tab-content">
                <div class='tab-pane active py-3' id="pills-monthly">
                	<table id="all_reports_table_monthly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

$(document).ready(function(){
	$.fn.dataTable.moment('DD-MM-YYYY hh:mm A');
	createReportsTable("all_reports_table_monthly", "Monthly");
	
	 var hash = window.location.hash;
	    let cururl = $("a.backbtn").attr("href");
		$("a.backbtn").attr("href",cururl+hash);
});

function createReportsTable(tableName, durationType) {
	let urlObject = new URL("${fetchCompanyReportsResourceCommandURL}");
	urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
	$.ajax({
	  	type: "post",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>reportsType": "ALL",
			"<portlet:namespace/>durationType": durationType,
			"<portlet:namespace/>department": "<%=department%>"			
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
						data : "intermediaryName",
						name : "intermediaryName",
						title: "Intermediary Name"
					},
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
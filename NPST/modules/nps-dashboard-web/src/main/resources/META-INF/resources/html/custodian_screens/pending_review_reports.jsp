<%@ include file="../../init.jsp"%>
<%
String transitionName = ParamUtil.getString(request, "transitionName","");
String department = ParamUtil.getString(renderRequest, "department");
String url = "/web/guest/dashboard#sdd"+department;
System.out.println("Department ::::::::: In JSP ::::::::: "+department);
JSONArray intermediaryArray = NPSDashboardUtil.getIntermediaryList(user, department);
%>

	<nav aria-label="breadcrumb">
	  <ol class="breadcrumb bg-transparent">
	  	<li class="breadcrumb-item">Dashboard</li>
	    <li class="breadcrumb-item active" aria-current="page">Pending for Review</li>
	  </ol>
	</nav>


<div class="nps-page-main pending-table mt-md-0 mt-sm-3 mt-3">
  
  <div class="row">
         <div class="col-12">
            <div class="text-right">
                <p  class="back_btn"><a class="backbtn" href="<%=url%>"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
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
	            
	            <% if(!isPfmMaker && !isPfmChecker && !isPfmSupervisor){ %>
	            <li class="nav-item">
                    <a data-bs-toggle="tab" class="nav-link p-0" href="#pills-annually"> Annually</a>
                    <span class="rp_count Annually_count">0</span>
                </li>
	            <% } %>
            </ul>
			<!-- Tab panes -->
            <div class="tab-content">
                <div class='tab-pane active py-3' id="pills-monthly">
                	<table id="pending_reports_table_monthly" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline">
					</table>
                </div>
                
                <div class="tab-pane fade py-3" id="pills-quarterly">
	                	<table id="pills_quarterly_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
	            </div>
	            <% if(!isPfmMaker && !isPfmChecker && !isPfmSupervisor){ %>
	            <div class="tab-pane fade py-3" id="pills-annually">
	                	<table id="pills_annually_table" cellspacing="0" width="100%" class="display dataTable no-footer dtr-inline"></table>
	            </div>
	            <% } %>
			</div>
		</div>
		<div class="animaion" style="display: none;">
			<div class="row">
				<div class="loading-animation"></div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
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

.intermediaryList{
   display: none;
}

</style>

<script>
const intermediaryArray = <%=intermediaryArray%>;
$(document).ready(function() {
	
	  var hash = window.location.hash;
	    let cururl = $("a.backbtn").attr("href");
		//$("a.backbtn").attr("href",cururl+hash);
	
	createReportsTable2();
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

var dataTables = [];
function createReportsTable2() {
	let urlObject = new URL("${fetchPendingReviewReportsResourceCommandURL}");
    urlObject.searchParams.delete('_com_nps_dashboard_web_NPSDashboardWebPortlet_mvcPath');
    let tabsArray = [];
    tabsArray.push({"tabid":"pending_reports_table_monthly", "dataKey":"Monthly"});
    tabsArray.push({"tabid":"pills_quarterly_table", "dataKey":"Quarterly"});
    <% if(!isPfmMaker && !isPfmChecker && !isPfmSupervisor){ %>
    tabsArray.push({"tabid":"pills_annually_table", "dataKey":"Annually"});
    <% } %>
	$.ajax({
	  	type: "POST",	
		url: urlObject,
		dataType:'json',
		data: {
			"<portlet:namespace/>department": "<%= NPSCompany.PFM%>"
		},
		success: function(data) {	
			tabsArray.forEach(function(v, i){
				console.log(data);
				console.log(v);
				console.log("i :: ",i);
				var index = i;
				
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
				
				var table = $('#' + v.tabid).DataTable({
					searching : true,
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
						/* {
							data : "userName",
							title: "Uploaded By",
							"render" : function(data, type, meta) {
								return meta.userName;
							}
						}, */
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
								if(meta.actionURL){
									return '<a href="'+ meta.actionURL +'">Review / Approve</a>';
								} else {
									return 'Review / Approve';
								}
							}
						},
						{
							data : "remarks",
							title: "Remarks",
							name : "remarks"
						}
						
					],
					initComplete: function () {
						var divHtml = $('<div class="col-sm-3 text-left form-group"></div>');
						var select = $('<select class="intermediaryList form-control" data-index='+index+'><option value="All">All</option></select>').appendTo(divHtml);
						
						$("#"+v.tabid+"_filter").addClass("row");
						$("#"+v.tabid+"_filter").find("label").addClass("col-sm-9 text-right");
						divHtml.prependTo($("#"+v.tabid+"_filter"));

						for(var i=0; i<intermediaryArray.length; i++){
							select.append('<option value="' + intermediaryArray[i].name + '">' + intermediaryArray[i].name + '</option>');
						}
					}
				});
				dataTables.push(table);
				$(".animaion").hide();
			});
				
		}
	});
}
var intermediary = "All";
var srindex;
$(document).on('change',".intermediaryList",function(){
	intermediary = $(this).val();
	var index = $(this).attr('data-index');
	console.log("intermediary name : ",intermediary);
	console.log("index : ",index);
	var selectedTable = dataTables[index];
	$.fn.dataTable.ext.search.push(
	        function( settings, data, dataIndex ) {
	        	if(intermediary === "All"){
	        		return true;
	        	}
	            return data[1]===intermediary
	                ? true
	                : false
	        }     
	    );
	selectedTable.draw();
    $.fn.dataTable.ext.search.pop();
});

$(document).on('click',".sorting",function(){
	console.log(" sorting intermediary : "+intermediary);
	var selectedTable = dataTables[srindex];
	$.fn.dataTable.ext.search.push(
	        function( settings, data, dataIndex ) {
	        	if(intermediary === "All"){
	        		return true;
	        	}
	            return data[1]===intermediary
	                ? true
	                : false
	        }     
	    );
	selectedTable.draw();
    $.fn.dataTable.ext.search.pop();
});

$(document).on('keyup','input[type=search]',function(){
	console.log(" search keyword intermediary : "+intermediary);
	if(!(intermediary === "All")){
	//console.log("intermediary name : ",intermediary);
	//console.log("index : ",srindex);
	var selectedTable = dataTables[srindex];
	$.fn.dataTable.ext.search.push(
	        function( settings, data, dataIndex ) {
	        	if(intermediary === "All"){
	        		return true;
	        	}
	            return data[1]===intermediary
	                ? true
	                : false
	        }     
	    );
	selectedTable.draw();
    $.fn.dataTable.ext.search.pop();
    console.log("custom search method called");
   // alert("The text has been changed.");
	}
  });

$(".nav-link").click(function(){
	var hrefVal = $(this).attr("href");
	$(hrefVal).find(".intermediaryList").val(intermediary);
	$(hrefVal).find(".intermediaryList").trigger('change');
});
</script>
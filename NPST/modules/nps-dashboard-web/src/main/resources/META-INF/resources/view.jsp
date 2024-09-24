<%@page import="nps.common.service.constants.NPSCompany"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@ include file="init.jsp"%>
<portlet:renderURL var="renderDepartmentOptionURL" windowState="<%=LiferayWindowState.EXCLUSIVE.toString()%>">
	<portlet:param name="mvcRenderCommandName" value="/department/pages"/>
</portlet:renderURL>
<%
/* String analyticURL = "http://dev.dcms.npstrust.org.in:7980/SASVisualAnalyticsViewer/?reportSBIP=SBIP%3A%2F%2FMETASERVER%2FShared%20Data%2FSAS%20Visual%20Analytics%2FPublic%2FDashboards%2FTrustee%2FTrustee%20Bank(Report)&page=vi1996";
if(isNPSTDGM){
	analyticURL = "http://dev.dcms.npstrust.org.in:7980/SASVisualAnalyticsViewer/?reportSBIP=SBIP%3A%2F%2FMETASERVER%2FShared%20Data%2FSAS%20Visual%20Analytics%2FPublic%2FDashboards%2FTrustee%2FDGM%20Dashboard(Report)&page=vi15444&reportViewOnly=true&appSwitcherDisabled=true";
} else if(isNPSTGM) {
	analyticURL = "http://dev.dcms.npstrust.org.in:7980/SASVisualAnalyticsViewer/?reportSBIP=SBIP%3A%2F%2FMETASERVER%2FShared%20Data%2FSAS%20Visual%20Analytics%2FPublic%2FDashboards%2FTrustee%2FGM%20Dashboard(Report)&page=vi15444&reportViewOnly=true&appSwitcherDisabled=true";
} */

%>
<% if(!isNPSTCEO){ %>
<nav aria-label="breadcrumb">
  <ol class="breadcrumb bg-transparent">
    <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
  </ol>
</nav>
<% } %>
<div id="dashboardMainContent" style="display:none;">
<% if(isNPSTUser) {%>
	<div class="dashboard_block mx-4 mainDashboard">
     
        <div class="row">
        	<div class="col-sm-6 col-12">
			    <a href="/web/guest/dashboard#sdo" class="nps-box text-center position-relative nps-bg3 showDepartmentOptions">
			        <div class="nps-icon">
			            <span>
			                <img src="<%=request.getContextPath()%>/images/dashboard_icons/report.png" alt="Reports" />
			            </span>
			        </div>
			        <h4>Reports</h4>
			    </a>
			</div>
			<% if(analyticReport && !isPFRDA_CRA && !isPFRDA_GRIEVANCE && !isPFRDA_CUSTODIAN && !isPFRDA_PFM){ %>
			<div class="col-sm-6 col-12">
                <a href="${analyticURL}" class="nps-box text-center position-relative">
                    <div class="nps-icon">
                        <span><img src="<%=request.getContextPath()%>/images/dashboard_icons/analytics.png" alt="Analytics" /></span>
                    </div>
                    <h4>Analytics</h4>
                </a>
            </div> 
            <%} %>
		</div>
	</div>
	<div class="departmentOption" style="display: none;">
	 <div class="row">
         <div class="col-12">
            <div class="text-right">
                <a href="/web/guest/dashboard" class="back_btn"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a>
            </div>
         </div>
      </div>
		<div class="row">
			<c:forEach items="${userCompanies }" var="department" varStatus="departmentStatus">
				<div class="col-sm-6 col-12">
				    <a href="/web/guest/dashboard#sdd${department}" data-department="${departmentStatus.index }" data-departmentName="${department}" 
				    	class="nps-box text-center position-relative nps-bg3 showDepartmentDashboard">
				        <div class="nps-icon">
				            <span>
				                <img src="<%=request.getContextPath()%>/images/dashboard_icons/report.png" alt="Reports" />
				            </span>
				        </div>
				        <c:choose>  
    <c:when test="${department == 'CRA'}">  
       <h4>Exit & Withdrawal</h4>
    </c:when>  

    <c:otherwise>  
     <h4>${department }</h4>
    </c:otherwise>  
</c:choose>  
				    </a>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="dashboard_block mx-4  departmentContent" style="display:none;"></div>
<%} else if(isMaker) { %>
	<%@ include file="/html/dashboard_screens/makers.jspf" %>
<%} else if(isChecker){ %>
	<%@ include file="/html/dashboard_screens/checker.jspf" %>
<% }else if(isSupervisor){ %>
	<%@ include file="/html/dashboard_screens/supervisor.jspf" %>
<% }else if(isNPSTCEO){ %>
	<%@ include file="/html/dashboard_screens/ceo_dashboard.jspf" %>
<% } else if(isPFRDA_TB){ %>
	<%@include file="/html/pfrda_screens/dashboard.jsp" %>
<% } else if(isCompanyMaker){ %>
	<%@include file="/html/dashboard_screens/pfm/pfm_makers.jspf" %>
<% } else if(isCompanyChecker || isCompanySupervisor){ %>
	<%@include file="/html/dashboard_screens/pfm/pfm_checker.jspf" %>
<% } else if(isCraMaker){ %>
	<%-- <%@include file="/html/dashboard_screens/cra/cra_supervisor.jspf" %> --%>
	<%@include file="/html/dashboard_screens/cra/cra_makers.jspf" %>
<% } else if(isCustodianMaker){ %>
	<%@include file="/html/dashboard_screens/custodian/custodian_makers.jspf" %>
<% } else if(isCustodianChecker){ %>
	<%@include file="/html/dashboard_screens/custodian/custodian_checker.jspf" %>
<% } else if(isCustodianSupervisor){ %>
	<%@include file="/html/dashboard_screens/custodian/custodian_supervisor.jspf" %>
<%} else { %>
	<%@ include file="/html/dashboard_screens/no_user_dashboard.jspf" %>
<%} %>

<%-- <%if(!isNPSTCEO && !isCraSupervisor){ %> --%>
<%if(!isNPSTCEO){ %>
	<%@ include file="/html/dashboard_screens/chart-report.jspf" %>
<%} %>
</div>
<div class="animaion" style="display:none;">
	 	<div class="row">
			<div class="loading-animation"></div>
		</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var hash = window.location.hash;
	console.log('hashValue::',hash);
	if(hash.startsWith("#sdd")){
		$("#dashboardMainContent").hide();
		$(".animaion").show();
	}else{
		$("#dashboardMainContent").show();
		$(".animaion").hide();
	}
	if(hash==="#sdo"){
		$(".departmentOption").show();
		$(".mainDashboard").hide();	
	}else if(hash==="#am_user"){
		$(".departmentOption").hide();
		$(".mainDashboard").show();	
	}else if(hash.startsWith("#sdd")){
		console.log("hash::::::::::::",decodeURI(hash.slice(4)));
		
		var userDepartment = decodeURI(hash.slice(4));
		console.log('userDepartment chart change::',userDepartment);
		if(userDepartment != null && userDepartment != ''){
			if(userDepartment != $(".chart1Department").val() && userDepartment != $(".chart2Department").val()){
				$(".chart1Department").val(userDepartment);
				$(".chart2Department").val(userDepartment);
				let chartStatus = Chart.getChart("myChart"); 
				if (chartStatus != undefined) {
				  chartStatus.destroy();
				}
				let chartStatus1 = Chart.getChart("myChart1"); 
				if (chartStatus1 != undefined) {
					chartStatus1.destroy();
				}
				getChart1Details();
				getChart2Details();
			}
		}
		
		$.ajax({
			url: "${renderDepartmentOptionURL}",
			type:"POST",
			data: {
				"<portlet:namespace/>department": decodeURI(hash.slice(4))
			},
			success: function(data){
				$(".animaion").hide();
				$("#dashboardMainContent").show();
				$(".departmentContent").html(data);
				$(".mainDashboard").hide();	
				$(".departmentContent").show();
				$(".departmentOption").hide();
				$("a.npsurl").each(function(index, element){
					let cururl = $(element).attr("href");
					$(element).attr("href",cururl+hash);
				})
			}
		});
	}
	
	$(".showDepartmentOptions").click(function(e){
		$(".departmentOption").show();
		$(".mainDashboard").hide();
	});
	
	$(".back_btn").click(function(){
			$(".departmentOption").hide();
			$(".mainDashboard").show();	
			console.log('Main dashboard showing:::::');
	});
	
	$(".showDepartmentDashboard").click(function(){
		var index = $(this).attr("data-department");
		var departmentName = $(this).attr("data-departmentName");
		console.log("departmentName : ",departmentName);
		
		var userDepartment = departmentName;
		console.log('userDepartment chart change::',userDepartment);
		if(userDepartment != null && userDepartment != ''){
			if(userDepartment != $(".chart1Department").val() && userDepartment != $(".chart2Department").val()){
				$(".chart1Department").val(userDepartment);
				$(".chart2Department").val(userDepartment);
				let chartStatus = Chart.getChart("myChart"); 
				if (chartStatus != undefined) {
				  chartStatus.destroy();
				}
				let chartStatus1 = Chart.getChart("myChart1"); 
				if (chartStatus1 != undefined) {
					chartStatus1.destroy();
				}
				getChart1Details();
				getChart2Details();
			}
		}
		
		$.ajax({
			url: "${renderDepartmentOptionURL}",
			type:"POST",
			data: {
				"<portlet:namespace/>department": departmentName
			},
			success: function(data){
				$(".departmentContent").html(data);
				$(".departmentContent").show();
				$(".departmentOption").hide();
				let hash1 = window.location.hash;
				$("a.npsurl").each(function(index, element){
					let cururl = $(element).attr("href");
					console.log("cururl : ",cururl);
					console.log("hash : ",hash1);
					$(element).attr("href",cururl+hash1);
				});
			}
		});
	});
	
});

</script>

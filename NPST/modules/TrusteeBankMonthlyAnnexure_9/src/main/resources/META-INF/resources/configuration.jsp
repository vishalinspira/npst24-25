<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ include file="/init.jsp"%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>"
    var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="<%= true %>"
    var="configurationRenderURL" />
    
<div class="container content">
	<aui:form id="myForm"  action="<%= configurationActionURL %>" method="post">
	
	 <aui:input name="<%= Constants.CMD %>" type="hidden"
        value="<%= Constants.UPDATE %>" />

    <aui:input name="redirect" type="hidden"
        value="<%= configurationRenderURL %>" />
        
	<div class="row pt-3">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<h3>Select Notification Channel</h3>
			
		</div>
		<div class="col-md-4"></div>
	</div>
	<hr/>
	
		<div class="row pt-3">
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="checkbox" id="email" name='<portlet:namespace/>email' value="Mail" <%= emailSelected %>>
				<label class="form-check-label" for="email">&nbsp;Send  Email</label> 
			</div>
		</div>
		<div class="row pt-3">
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="checkbox" id="sms" name='<portlet:namespace/>sms' value="SMS"  <%= smsSelected%>>
				<label class="form-check-label" for="sms">&nbsp;Send Sms</label> 
			</div>						
		</div>
		<div class="row pt-3">
			<div class="text-center">
				<input type="submit" class="btn btn-primary" id="btn-submit" style="height:40px;width:80px" value="Submit">
			</div>
		</div>
	</aui:form>	
</div>


















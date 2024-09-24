<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@ include file="/init.jsp" %>
<%
JSONObject resultJsonObject=(JSONObject)request.getAttribute("resultJsonObject");

boolean status = resultJsonObject.getBoolean("status");
%>
<div class="container">
  <div class="row">
    <div class="col">
      <% if(status){ %>
      Data submited successfuly.
      <% }
      else{
      %>
      An error occourd. To download the error excel file click <a href="<%= resultJsonObject.getString("downloadUrl")%>">here</a>
      <% } %>
    </div>
  </div>
</div>
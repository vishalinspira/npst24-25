<%@page import="com.daily.average.service.model.AccountStatement"%>
<%@ include file="/init.jsp" %>

<%
AccountStatement accountStatement = (AccountStatement) request.getAttribute("accountStatement");
%>
<div class="mt-4">
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=accountStatement.getId()%></td>
			</tr>
			<tr>
				<th>Account Name.</th>
				<td><%=accountStatement.getAccountname()%></td>
			</tr>
			<tr>
				<th>Statement</th>
				<td><%=accountStatement.getStatement()%></td>
			</tr>
			<tr>
				<th>Customer Number</th>
				<td><%=accountStatement.getCustomerno()%></td>
			</tr>
		</tbody>
	</table>
</div>
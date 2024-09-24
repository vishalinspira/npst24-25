<%@page import="compliance.service.model.ClosingBal"%>

<%@ include file="/init.jsp" %>

<%
ClosingBal closingBal = (ClosingBal) request.getAttribute("closingBal");
%>
<div class="mt-4">
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=closingBal.getExitid()%></td>
			</tr>
			<tr>
				<th>Account Details.</th>
				<td><%=closingBal.getAccountdetails()%></td>
			</tr>
			<tr>
				<th>Address line 1.</th>
				<td><%=closingBal.getAddressline1()%></td>
			</tr>
			<tr>
				<th>Address line 2.</th>
				<td><%=closingBal.getAddressline2()%></td>
			</tr>
		</tbody>
	</table>
</div>
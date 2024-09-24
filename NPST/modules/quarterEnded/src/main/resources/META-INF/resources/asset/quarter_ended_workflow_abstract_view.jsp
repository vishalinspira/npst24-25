<%@page import="com.daily.average.service.model.QuarterEnded"%>
<%@ include file="/init.jsp" %>

<%
QuarterEnded quarterEnded = (QuarterEnded) request.getAttribute("quarterEnded");
%>
<div class="mt-4">
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=quarterEnded.getId()%></td>
			</tr>
			<tr>
				<th>One Ia.</th>
				<td><%=quarterEnded.getOneIa() %></td>
			</tr>
			<tr>
				<th>One Ib.</th>
				<td><%=quarterEnded.getOneIb() %></td>
			</tr>
			<tr>
				<th>One IIa.</th>
				<td><%=quarterEnded.getOneIIa()%></td>
			</tr>
		</tbody>
	</table>
</div>
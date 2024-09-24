<%@page import="com.daily.average.service.model.DailyAverage"%>
<%@ include file="/init.jsp" %>

<%
DailyAverage dailyAverage = (DailyAverage) request.getAttribute("dailyAverage");
%>
<div class="mt-4">
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<th>Serial No.</th>
				<td><%=dailyAverage.getId()%></td>
			</tr>
			<tr>
				<th>NPSA Name.</th>
				<td><%=dailyAverage.getNPSAcName()%></td>
			</tr>
			<tr>
				<th>NPS Acount Number</th>
				<td><%=dailyAverage.getNPSAcNumber()%></td>
			</tr>
			<tr>
				<th>Cheq Number</th>
				<td><%=dailyAverage.getChqNo()%></td>
			</tr>
		</tbody>
	</table>
</div>
<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">Report test</DIV>
					<%
						
						ReportMessage cleannessReport = (ReportMessage)request.getAttribute("cleannessReport");
						if(cleannessReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No cleanness report data</div>
							<%
						}
						ReportMessage extraReport = (ReportMessage)request.getAttribute("extraReport");
						if(extraReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No extra report data</div>
							<%
						}
						ReportMessage roomStatusReport = (ReportMessage)request.getAttribute("cleannessReport");
						if(roomStatusReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No Room Status report data</div>
							<%
						}
						ReportMessage roomAnalysisReport = (ReportMessage)request.getAttribute("roomAnalysisReport");
						if(roomAnalysisReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No room analysis report data</div>
							<%
						}
						
					%>
					<div id="graph" style="width:400px;height:200px"></div>
					<script id="source" language="javascript" type="text/javascript">
$(function () {

	var d2 = [[0, 3.4], [1, 8.2], [2, 5.0], [3, 13]];

	$.plot($("#graph"), [
		{
			data: d2,
			bars: { show: true }
		}
	],
		{
			xaxis: {
				ticks: [[0.5, "invalid"], [1.5, "single"], [2.5, "standard"], [3.5, "suite"]]
			}
		}
	);
});
</script>
				</div>
<%@ include file="../footer.jsp" %>

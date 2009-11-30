<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">Report test</DIV>
					<% 
						/*
						ReportMessage report = (ReportMessage)request.getAttribute("report");
						if(report==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No report data</div>
							<%
						}
						*/
					%>
					<div id="graph" style="width:600px;height:300px"></div> 
					<script id="source" language="javascript" type="text/javascript"> 
$(function () {
    var d1 = [];
 
    var d2 = [['invalid', 3], ['single', 8], ['standard', 5], ['suite', 13]];
    $.plot($("#graph"), [
        {
            data: d2,
            bars: { show: true }
        }
    ]);
});
</script> 
				</div>
<%@ include file="../footer.jsp" %>

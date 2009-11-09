<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Error</div>
					<%
						if(request.getAttribute("message")!=null) {
							%>
							<div class='error'><%=request.getAttribute("message") %></div>
							<%
						}
					%>
				</div>
<%@ include file="footer.jsp" %>
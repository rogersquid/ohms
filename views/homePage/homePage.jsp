<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<h2> WELCOME TO OHMS DEMONSTRATION WEB PAGE </h2>
					<h4> OHMS is an online hotel management system </h4>
					<h4> Here is our Test Hotel Layout </h4>
					<a href='home.html?floor=1'>Floor 1</a>
					<%
						if(Integer(request.getParameter("floor")).intValue<4) {
						%>
							<div class="hotel_image">
								<img src="images/uploads/Floor<%=Integer(request.getParameter("floor")).intValue %>.png" />
							</div>
						<% } %>
				</div>
<%@ include file="../footer.jsp" %>
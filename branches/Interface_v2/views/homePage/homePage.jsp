<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<h2> WELCOME TO OHMS DEMONSTRATION WEB PAGE </h2>
					<h4> OHMS is an online hotel management system </h4>
					<h4> Here is our Test Hotel Layout </h4>
					<a href='/ohms/?floor=1'>Floor 1</a>
					<%
						String floor = "";
						if(request.getParameter("floor")!=null) floor = request.getParameter("floor");
						else floor = "1";
						if(Integer.parseInt(floor)<4) {
						%>
							<div class="hotel_image">
								<img src="images/uploads/Floor<%=floor %>.png" />
							</div>
						<% } %>
				</div>
<%@ include file="../footer.jsp" %>

<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<h2> WELCOME TO OHMS DEMONSTRATION WEB PAGE </h2>
					<h4> OHMS is an online hotel management system </h4>
					<h4> Here is our Test Hotel Layout </h4>
					
					<table cellpadding=2 cellspacing=2>
						<tr>
    						<td>Outer View</td>
    						<td>NA: Picture is not yet taken</td>
    						<td></td>
  						</tr>
  						<tr>
    						<td><a href='/ohms/?floor=1'>Floor 1</a></td>
    						<td><a href='/ohms/?floor=2'>Floor 2</a></td>
    						<td><a href='/ohms/?floor=3'>Floor 3</a></td>
  						</tr>
  					</table>
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

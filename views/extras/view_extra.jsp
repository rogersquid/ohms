<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<% ExtraMessage extra = (ExtraMessage)request.getAttribute("extra"); %>
					<div id='title'>Extra &bull <a href='extra.html?action=edit&amp;id=<%=extra.extraID %>'><small>Edit</small></a></div>
					<%
						String status = "";
						if(request.getParameter("status")!=null) { status = request.getParameter("status"); }

						
						if(status.equals("edit_success")) {
							%>
							<div class='success'><strong>Extra updated.</strong></div>
							<%
						}
					%>
					<table class='extra'>
						<tr>
							<th>Name</th>
							<td><%=extra.name %></td>
						</tr>
						<tr>
							<th>Price</th>
							<td><%=extra.price %></td>
						</tr>
						<tr>
							<th>Date</th>
							<td><%=extra.date %></td>
						</tr>
						<tr>
							<th>Booking</th>
							<td><%=extra.bookingID%></td>
						</tr>
					</table>
				</div>
<%@ include file="footer.jsp" %>

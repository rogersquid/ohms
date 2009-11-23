<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Booking Details</div>
					<%
						if(request.getAttribute("status")=="booking_failed") {
							%>
							<div class='error'><strong>Booking failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}

						if(request.getAttribute("status")=="booking_success") {
							%>
							<div class='success'><%=request.getAttribute("message") %></div>
							<%
						}
						
						Message data = (Message)getAttribute("data");
					%>
					<table>
						<tr>
							<td>Customer: </td>
							<td><%=data.accounts[0].firstName %> <%=data.accounts[0].lastName %></td>
						</tr>
						<tr>
							<td>Room #: </td>
							<td><a href='rooms.html?action=view&amp;id=<%=data.rooms[0].roomID %>'><%=data.rooms[0].roomNumber %></a></td>
						</tr>
						<tr>
							<td>Start date: </td>
							<td><%=data.bookings[0].startDate %></td>
						</tr>
						<tr>
							<td>End date: </td>
							<td><%=data.bookings[0].endDate %></td>
						</tr>
					</table>
				</div>
<%@ include file="footer.jsp" %>
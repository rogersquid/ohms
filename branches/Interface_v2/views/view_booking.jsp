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
						
						BookingMessage booking = (BookingMessage)getAttribute("booking");
					%>
					<table>
						<tr>
							<td>Customer: </td>
							<td><%=booking.firstName %> <%=booking.lastName %></td>
						</tr>
						<tr>
							<td>Room #: </td>
							<td><a href='rooms.html?action=view&amp;id=<%=booking.roomID %>'><%=booking.roomNumber %></a></td>
						</tr>
						<tr>
							<td>Start date: </td>
							<td><%=booking.startDate %></td>
						</tr>
						<tr>
							<td>End date: </td>
							<td><%=booking.endDate %></td>
						</tr>
					</table>
				</div>
<%@ include file="footer.jsp" %>
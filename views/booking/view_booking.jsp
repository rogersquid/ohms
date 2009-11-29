<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>
						Booking Details
						<%
							Message data = (Message)request.getAttribute("data");

							int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
							int userid = ((Integer)request.getAttribute("userID")).intValue();
							if(authlevel >= 3) {
								if(data.bookings[0].status==0) {
									%>
									&bull; <a href='bookings.html?action=checkin&amp;id=<%=data.bookings[0].bookingID %>'>check in</a>
									<%
								} else {
									%>
									&bull; <a href='bookings.html?action=checkout&amp;id=<%=data.bookings[0].bookingID %>'>check out</a>
									<%
								}
							}

						%>
					</div>
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

						if(request.getParameter("status")=="checkin") {
							%>
							<div class='success'>You have checked in.</div>
							<%
						}
						if(request.getParameter("status")=="checkout") {
							%>
							<div class='success'>You have checked out.</div>
							<%
						}

					%>
					<table class='vertical'>
						<tr>
							<td>Customer: </td>
							<td><a href='account.html?action=view&amp;id=<%=data.bookings[0].ownerID %>'><%=data.accounts[0].firstName %> <%=data.accounts[0].lastName %></a></td>
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
						<tr>
							<td>Status: </td>
							<td><% if(data.bookings[0].status==1) { %>Checked in<% } else { %>Not checked in<% } %></td>
						</tr>
					</table>
				</div>
<%@ include file="../footer.jsp" %>
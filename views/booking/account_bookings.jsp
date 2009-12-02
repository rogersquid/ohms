<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Bookings for Account ID: <a href='account.html?action=view&amp;id=<%=request.getParameter("id") %>'><%=request.getParameter("id") %></a></div>
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
					%>

						<%
						Message data = (Message)request.getAttribute("data");
						if(data.bookings.length>0) {

							%>
							<table class='horizontal'>
								<tr>
									<th></th>
									<th>Booking</th>
									<th>Room #</th>
									<th>Start Date</th>
									<th>End Date</th>
									<th>Status</th>
									<th></th>
								</tr>

								<%
								// Print the values into the table
								for(int i=0; i < data.bookings.length; i++) {
								%>
								<tr>
									<td><span class='index'><%=i+1 %></span></td>
									<td><a href='bookings.html?action=view&amp;id=<%=data.bookings[i].bookingID %>'><%=data.bookings[i].bookingID %></a></td>
									<td><a href='room.html?action=view&amp;id=<%=data.bookings[i].roomID %>'><%=data.rooms[i].roomNumber %></a></td>
									<td><%=data.bookings[i].startDate %></td>
									<td><%=data.bookings[i].endDate %></td>
									<%
										String status;
										switch (data.bookings[i].status){
											case 0:	status = "Not checked in"; break;
											case 1: status = "Checked in"; break;
											case 2: status = "Checked out"; break;
											default: status = "ERROR: Status int has unsupported value!";
										}
									%>
									<td><%=status %></td>
									<td><a href='bookings.html?action=delete&amp;id=<%=data.bookings[i].bookingID %>' title='Delete booking' class='delete'>[ X ]</a></td>
								</tr>
								<%
							} %>
							</table>
							<%
						} else { %>
							<div class='info'>You have no bookings.</div>
							<%
						} %>
				</div>
<%@ include file="../footer.jsp" %>
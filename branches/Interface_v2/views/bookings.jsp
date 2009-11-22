<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Bookings</div>
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
					<form action='' method='post'>
						<table>
							<tr>
								<td>Owner ID (testing only): </td>
								<td><input type='text' name='ownerID' /></td>
							</tr>
							<tr>
								<td>Room ID: </td>
								<td><input type='text' name='roomID' /></td>
							</tr>
							<tr>
								<td>Start date: </td>
								<td><input type='text' name='startDate' class='date-pick' /></td>
							</tr>
							<tr>
								<td>End date: </td>
								<td><input type='text' name='endDate' class='date-pick' /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type='submit' name='Submit' /></td>
							</tr>
						</table>
					</form>

					<table class='bookings'>
						<tr>
							<th></th>
							<th>Customer</th>
							<th>Room #</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Status</th>
						</tr>

						<%
						data = (Message)request.getAttribute("data");

						// Print the values into the table
						for(int i=0; i < data.bookings.length; i++) {
							%>
							<tr>
								<td><span class='index'><%=i+1 %></span></td>
								<td><%=data.accounts[i].firstName %> <%=data.accounts[i].lastName %></td>
								<td><%=data.rooms[i].roomNumber %></td>
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
							</tr>
							<%
						}
						%>
					</table>
				</div>
<%@ include file="footer.jsp" %>
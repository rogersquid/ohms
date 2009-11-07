<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
<%@ page import="models.messages.BookingMessage;" %>
				<div id='content'>
					<div id='title'>Bookings</div>
					<%
						if(request.getAttribute("status")=="booking_failed") {
							%>
							<div class='error'>Booking failed: <%=request.getAttribute("message") %></div>
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
								<td>Duration: </td>
								<td><input type='text' name='duration' /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type='submit' name='Submit' /></td>
							</tr>
						</table>
					</form>

					<table>
						<tr>
							<th>Owner ID</th>
							<th>Room ID</th>
							<th>Start Date</th>
							<th>Duration</th>
							<th>Status</th>
						</tr>
						
						<%
						BookingMessage[] bookingsArray = request.getAttribute("bookingsArray");
						// These string arrays hold the data from the array of RoomMessages
						String owner	[bookingsArray.length];
						String room		[bookingsArray.length];
						String date		[bookingsArray.length];
						String duration	[bookingsArray.length];
						String status	[bookingsArray.length];
						
						// Populate the string arrays
						for (int i=0; i<bookingsArray.length; i++){
							owner[i] 	= (String)	reply[i].ownerID;
							room[i]		= (String)	reply[i].startDate;
							date[i] 	= (String)	reply[i].duration;
							duration[i] = (String)	reply[i].roomID;
							switch (reply[i].status){
								case 0:	status[i] = "Not checked in";
								case 1: status[i] = "Checked in";
								case 2: status[i] = "Checked out";
								default: status[i] = "ERROR: Status int has unsupported value!";
							}
						}
						
						// Print the values into the table
						for(int i=0; i < bookingsArray.length; i++) { 
							%>
							<tr>
								<td></td>
								<td><%=owner[i]%></td>
								<td><%=room[i]%></td>
								<td><%=date[i]%></td>
								<td><%=duration[i] %></td>
								<td><%=status[i] %></td>
							</tr>
							<%
						}
						%>
					</table>
				</div>
<%@ include file="footer.jsp" %>
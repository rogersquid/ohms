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
							<th>Owner ID</th>
							<th>Room ID</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Status</th>
						</tr>

						<%
						BookingMessage bookingsArray[] = (BookingMessage[])request.getAttribute("bookingsArray");
						// These string arrays hold the data from the array of RoomMessages
						int arraySize = bookingsArray.length;
						int owner[] = new int[arraySize];
						int room[] = new int[arraySize];
						Date start[] = new Date[arraySize];
						Date end[] = new Date[arraySize];
						String status[] = new String[arraySize];

						// Populate the string arrays
						for (int i=0; i<bookingsArray.length; i++){
							owner[i] 	= bookingsArray[i].ownerID;
							room[i]		= bookingsArray[i].roomID;
							start[i] 	= bookingsArray[i].startDate;
							end[i]		= bookingsArray[i].endDate;
							switch (bookingsArray[i].status){
								case 0:	status[i] = "Not checked in"; break;
								case 1: status[i] = "Checked in"; break;
								case 2: status[i] = "Checked out"; break;
								default: status[i] = "ERROR: Status int has unsupported value!";
							}
						}

						// Print the values into the table
						for(int i=0; i < bookingsArray.length; i++) {
							%>
							<tr>
								<td><span class='index'><%=i+1 %></span></td>
								<td><%=owner[i]%></td>
								<td><%=room[i]%></td>
								<td><%=start[i]%></td>
								<td><%=end[i] %></td>
								<td><%=status[i] %></td>
							</tr>
							<%
						}
						%>
					</table>
				</div>
<%@ include file="footer.jsp" %>
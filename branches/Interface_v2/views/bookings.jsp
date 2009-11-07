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
							<div class='success'>Booking successfully made: <%=request.getAttribute("message") %></div>
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
								<td><input type='text' name='startDate' /></td>
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
							<th>Room</th>
							<th>Start date</th>
							<th>Duration</th>
						</tr>
						<%
							for(int i=0; i<5; i++) {
								%>
								<tr>
									<td><%=i %></td>
									<td><%=i %></td>
									<td><%=i %></td>
								</tr>
								<%
							}
						%>
					</table>
				</div>
<%@ include file="footer.jsp" %>
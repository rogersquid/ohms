<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Available rooms</div>
					Available between <%=request.getParameter("startDate") %> and <%=request.getParameter("endDate") %>
					<%
						if(request.getAttribute("status")=="booking_failed") {
							%>
							<div class='error'><strong>Booking failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}
					%>
					<table class='rooms'>
						<tr>
							<th>Room #</th>
							<th>Floor</th>
							<th>Type</th>
							<th>Price</th>
							<th>Single<br />beds</th>
							<th>Queen<br />beds</th>
							<th>King<br />beds</th>
							<th>TV</th>
							<th>Internet</th>
							<th>Phone</th>
							<th>Kitchen</th>
							<th>Elevator</th>
							<th>Disability<br />access</th>
							<th></th>
						</tr>

						<%
						Message data = (Message)request.getAttribute("data");

						// Print the values into the table
						for(int i=0; i < data.rooms.length; i++) {
							%>
							<tr>
								<!--<td><a href='rooms.html?action=view&amp;id=<%=data.rooms[i].roomID %>'><%=data.rooms[i].roomNumber %></a></span></td>-->
								<td>
									<form action='' method='post'>
										<input type='hidden' name='startDate' value='<%=request.getParameter("startDate") %>' />
										<input type='hidden' name='endDate' value='<%=request.getParameter("endDate") %>' />
										<input type='hidden' name='roomID' value='<%=data.rooms[i].roomID %>' />
										<input type='hidden' name='roomNumber' value='<%=data.rooms[i].roomNumber %>' />
										<input type='submit' value='<%=data.rooms[i].roomNumber %>'>
									</form>
								</td>
								<td><%=data.rooms[i].floor %></td>
								<td><%=data.rooms[i].roomType %></td>
								<td><%=data.rooms[i].price %></td>
								<td><%=data.rooms[i].singleBeds %></td>
								<td><%=data.rooms[i].queenBeds %></td>
								<td><%=data.rooms[i].kingBeds %></td>
								<td><%=data.rooms[i].tv %></td>
								<td><%=data.rooms[i].internet %></td>
								<td><%=data.rooms[i].phone %></td>
								<td><%=data.rooms[i].kitchen %></td>
								<td><%=data.rooms[i].elevator %></td>
								<td><%=data.rooms[i].disabilityAccess %></td>
							</tr>
							<%
						}
						%>
					</table>
					</form>
				</div>
<%@ include file="footer.jsp" %>
<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Rooms &bull; Search results</div>
					<%
						if(request.getAttribute("status")=="booking_failed") {
							%>
							<div class='error'><strong>Room search failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}

						Message data = (Message)request.getAttribute("data");
						if(data.rooms.length>0) {
							%>
							<table class='horizontal'>
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


								// Print the values into the table
								for(int i=0; i < data.rooms.length; i++) {
									%>
									<tr>
										<!--<td><a href='rooms.html?action=view&amp;id=<%=data.rooms[i].roomID %>'><%=data.rooms[i].roomNumber %></a></span></td>-->
										<td>
											<a href='room.html?action=view&amp;id=<%=data.rooms[i].roomID %>'><%=data.rooms[i].roomNumber %></a>
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
							<%
						} else { %>
							<div class='info'>There are no rooms found for your request.</div>
							<%
						} %>
					</form>
				</div>
<%@ include file="../footer.jsp" %>
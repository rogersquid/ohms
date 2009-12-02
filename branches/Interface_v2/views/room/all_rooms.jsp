<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>All Rooms</div>
					<a href='room.html?action=add'>Add new room</a>
					<%
						if(request.getAttribute("status")=="room_failed") {
							%>
							<div class='error'><strong>Booking failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}

						if(request.getAttribute("status")=="room_success") {
							%>
							<div class='success'><%=request.getAttribute("message") %></div>
							<%
						}
					%>

						<%
						Message data = (Message)request.getAttribute("data");
						if(data.rooms.length>0) {

							%>
							<table class='horizontal'>
								<tr>
									<th></th>
									<th>Room #</th>
									<th>Type</th>
									<th>Price</th>
									<th>Available</th>
									<th>Cleaned</th>
									<%-- <th></th> --%>
								</tr>

								<%
								// Print the values into the table
								for(int i=0; i < data.rooms.length; i++) {
								%>
								<tr>
									<td><span class='index'><%=i+1 %></span></td>
									<td><a href='room.html?action=view&amp;id=<%=data.rooms[i].roomID %>'><%=data.rooms[i].roomNumber %></a></td>
									<td><%=data.rooms[i].roomType %></td>
									<td><%=data.rooms[i].price %></td>
									<td><%=data.rooms[i].available %></td>
									<td><%=data.rooms[i].cleaned %></td>
									<%-- <td><a href='room.html?action=delete&amp;id=<%=data.rooms[i].roomID %>' title='Delete room' class='delete'>[ X ]</a></td> --%>
								</tr>
								<%
							} %>
							</table>
							<%
						} else { %>
							<div class='info'>There are no rooms.</div>
							<%
						} %>
				</div>
<%@ include file="../footer.jsp" %>
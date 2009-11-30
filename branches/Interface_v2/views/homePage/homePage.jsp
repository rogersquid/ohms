<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>

					<h2> WELCOME TO OHMS DEMONSTRATION WEB PAGE </h2>
					<h2> OHMS is an online hotel management system </h2>
					<div class="room_image">
						<img src="images/uploads/<%=room.roomType %>.png" />
					</div>
					<table class='vertical'>
						<tr>
							<th>Room #</th>
							<td><%=room.roomNumber %></td>
						</tr>
						<tr>
							<th>Floor</th>
							<td><%=room.floor %></td>
						</tr>
						<tr>
							<th>Type</th>
							<td><%=room.roomType %></td>
						</tr>
						<tr>
							<th>Price</th>
							<td><%=room.price %></td>
						</tr>
						<tr>
							<th>Single beds</th>
							<td><%=room.singleBeds %></td>
						</tr>
						<tr>
							<th>Queen beds</th>
							<td><%=room.queenBeds %></td>
						</tr>
						<tr>
							<th>King beds</th>
							<td><%=room.kingBeds %></td>
						</tr>
						<tr>
							<th>TV</th>
							<td><%=room.tv %></td>
						</tr>
						<tr>
							<th>Internet</th>
							<td><%=room.internet %></td>
						</tr>
						<tr>
							<th>Phone</th>
							<td><%=room.phone %></td>
						</tr>
						<tr>
							<th>Kitchen</th>
							<td><%=room.kitchen  %></td>
						</tr>
						<tr>
							<th>Elevator</th>
							<td><%=room.elevator %></td>
						</tr>
						<tr>
							<th>Disability access</th>
							<td><%=room.disabilityAccess %></td>
						</tr>
					</table>
				</div>
<%@ include file="../footer.jsp" %>
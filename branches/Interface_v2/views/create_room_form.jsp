<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">New Room</DIV> 
					<%
						RoomMessage room = (RoomMessage)request.getAttribute("room");
						if(room==null) {
							room = new AccountMessage();
							room.roomID = 0;
							room.roomType = "";
							room.roomNumber = 0;
							room.price = 0;
							room.available = false;
							room.cleaned = false;
							room.floor = 0;
							room.tv = false;
							room.singleBeds = 0;
							room.queenBeds = 0;
							room.kingBeds = 0;
							room.disabilityAccess = false;
							room.phone = false;
							room.internet = false;
							room.kitchen = false;
							room.onsuite = false;
							room.elevator = false;
						}
						if(request.getAttribute("status")=="register_failed") {
							%>
							<div class='error'><strong>Register failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}
					%>
					<form method='post' action=''>
						<%
						
						if(request.getAttribute("status")=="room_failed") {
							%>
							<div class='error'><strong>Add room failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}
						if(request.getAttribute("status")=="add_success") {
							%>
							<div class='success'><strong>Room successfully added.</strong> </div>
							<%
						}
						%>
						<h3>Room information</h3>
						<table>
							<tr>
								<th>Room number:</th>
								<td><input type='text' name='roomNumber' value='<%=room.roomNumber %>' /></td>
							</tr>
							<tr>
								<th>Floor:</th>
								<td><input type='text' name='floor' value='<%=room.floor %>' /></td>
							</tr>
							<tr>
								<th>Room type:</th>
								<td>
									<select name='roomType'>
										<option value='standard'
										<%
										if(room.roomType.compareToIgnoreCase("standard") == 0) {
											%>
											selected="selected"
											<%
										}
										%>
									>Standard</option>
										<option value='suite'
										<%
										if(room.roomType.compareToIgnoreCase("suite") == 0) {
											%>
											selected="selected"
											<%
										}
										%>
										>Suite</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Price:</th>
								<td>$<input type='text' name='price' value='<%=room.price %>' />
							</tr>
						</table>
						<h3>Select beds</h3>
						<table>
							<tr>
								<th>Number of single beds:</th>
								<td>
									<select name='singleBeds'>
										<option
										<%
										if(room.singleBeds == 0) {
											%>
											selected="selected"
											<%
										}
										%>
										>0</option>
										<option
										<%
										if(room.singleBeds == 1) {
											%>
											selected="selected"
											<%
										}
										%>
										>1</option>
										<option
										<%
										if(room.singleBeds == 2) {
											%>
											selected="selected"
											<%
										}
										%>
										>2</option>
										<option
										<%
										if(room.singleBeds == 3) {
											%>
											selected="selected"
											<%
										}
										%>
										>3</option>
										<option
										<%
										if(room.singleBeds == 4) {
											%>
											selected="selected"
											<%
										}
										%>
										>4</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Number of queen size beds:</th>
								<td>
									<select name='queenBeds'>
										<option
										<%
										if(room.queenBeds == 0) {
											%>
											selected="selected"
											<%
										}
										%>
										>0</option>
										<option
										<%
										if(room.queenBeds == 1) {
											%>
											selected="selected"
											<%
										}
										%>
										>1</option>
										<option
										<%
										if(room.queenBeds == 2) {
											%>
											selected="selected"
											<%
										}
										%>
										>2</option>
										<option
										<%
										if(room.queenBeds == 3) {
											%>
											selected="selected"
											<%
										}
										%>
										>3</option>
										<option
										<%
										if(room.queenBeds == 4) {
											%>
											selected="selected"
											<%
										}
										%>
										>4</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Number of king size beds:</th>
								<td>
									<select name='kingBeds'>
										<option
										<%
										if(room.kingBeds == 0) {
											%>
											selected="selected"
											<%
										}
										%>
										>0</option>
										<option
										<%
										if(room.kingBeds == 1) {
											%>
											selected="selected"
											<%
										}
										%>
										>1</option>
										<option
										<%
										if(room.kingBeds == 2) {
											%>
											selected="selected"
											<%
										}
										%>
										>2</option>
										<option
										<%
										if(room.kingBeds == 3) {
											%>
											selected="selected"
											<%
										}
										%>
										>3</option>
										<option
										<%
										if(room.kingBeds == 4) {
											%>
											selected="selected"
											<%
										}
										%>
										>4</option>
									</select>
								</td>
							</tr>
						</table>
						<h3>Customize room</h3>
						<table>
							<tr>
								<th>TV:</th>
								<td><input type='checkbox' name='tv' value='1' 
										<%
										if(room.tv == 1) {
											%>
											checked="checked"
											<%
										}
										%>
										/></td>
							</tr>
							<tr>
								<th>Internet:</th>
								<td><input type='checkbox' name='internet' value='1'
										<%
										if(room.internet == 1) {
											%>
											checked="checked"
											<%
										}
										%>								
										/></td>
							</tr>
							<tr>
								<th>Restroom on suite:</th>
								<td><input type='checkbox' name='onsuite' value='1' 
										<%
										if(room.onsuite == 1) {
											%>
											checked="checked"
											<%
										}
										%>
										/></td>
							</tr>
							<tr>
								<th>Phone:</th>
								<td><input type='checkbox' name='phone' value='1' 
										<%
										if(room.phone == 1) {
											%>
											checked="checked"
											<%
										}
										%>
										/></td>
							</tr>
							<tr>
								<th>Kitchen:</th>
								<td><input type='checkbox' name='kitchen' value='1' 
										<%
										if(room.kitchen == 1) {
											%>
											checked="checked"
											<%
										}
										%>
										/></td>
							</tr>
							<tr>
								<th>Available:</th>
								<td><input type='checkbox' name='available' value='1' 
										<%
										if(room.available == 1) {
											%>
											checked="checked"
											<%
										}
										%>
										/></td>
							</tr>
							<tr>
								<th>Cleaned:</th>
								<td><input type='checkbox' name='cleaned' value='1'
										<%
										if(room.cleaned == 1) {
											%>
											checked="checked"
											<%
										}
										%>								
										/></td>
							</tr>
						</table>
						
						<h3>Accessibility</h3>
						<table>
							<tr>
								<th>Disability access:</th>
								<td><input type='checkbox' name='disabilityAccess' value='1'
										<%
										if(room.disabilityAccess == 1) {
											%>
											checked="checked"
											<%
										}
										%>		
										/></td>
							</tr>
							<tr>
								<th>Elevator access:</th>
								<td><input type='checkbox' name='elevator' value='1' 
										<%
										if(room.elevator == 1) {
											%>
											checked="checked"
											<%
										}
										%>	
										/></td>
							</tr>
						</table>
						<br />
						<input type="submit" value="Add room" />
					</form>
				</div>
<%@ include file="footer.jsp" %>
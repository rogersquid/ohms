<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">New Room</DIV> 
					<form method='post' action=''>
						<%
						
						if(request.getAttribute("status")=="room_failed") {
							%>
							<div class='error'><strong>Add room failed:</strong> <%=request.getAttribute("message") %></div>
							<%
						}
						%>
						<h3>Room information</h3>
						<table>
							<tr>
								<th>Room number:</th>
								<td><input type='text' name='roomNumber' value='' /></td>
							</tr>
							<tr>
								<th>Floor:</th>
								<td><input type='text' name='floor' value='' /></td>
							</tr>
							<tr>
								<th>Room type:</th>
								<td>
									<select name='roomType'>
										<option value='standard'>Standard</option>
										<option value='suite'>Suite</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Price:</th>
								<td>$<input type='text' name='price' />
							</tr>
						</table>
						<h3>Select beds</h3>
						<table>
							<tr>
								<th>Number of single beds:</th>
								<td>
									<select name='singleBeds'>
										<option>0</option>
										<option>1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Number of queen size beds:</th>
								<td>
									<select name='queenBeds'>
										<option>0</option>
										<option>1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>Number of king size beds:</th>
								<td>
									<select name='kingBeds'>
										<option>0</option>
										<option>1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
									</select>
								</td>
							</tr>
						</table>
						<h3>Customize room</h3>
						<table>
							<tr>
								<th>TV:</th>
								<td><input type='checkbox' name='tv' value='1' /></td>
							</tr>
							<tr>
								<th>Internet:</th>
								<td><input type='checkbox' name='internet' value='1' /></td>
							</tr>
							<tr>
								<th>Restroom on suite:</th>
								<td><input type='checkbox' name='onsuite' value='1' /></td>
							</tr>
							<tr>
								<th>Phone:</th>
								<td><input type='checkbox' name='phone' value='1' /></td>
							</tr>
							<tr>
								<th>Kitchen:</th>
								<td><input type='checkbox' name='kitchen' value='1' /></td>
							</tr>
						</table>
						
						<h3>Accessibility</h3>
						<table>
							<tr>
								<th>Disability access:</th>
								<td><input type='checkbox' name='disabilityAccess' value='1' /></td>
							</tr>
							<tr>
								<th>Elevator access:</th>
								<td><input type='checkbox' name='elevator' value='1' /></td>
							</tr>
						</table>
						<br />
						<input type="submit" value="Add room" />
					</form>
				</div>
<%@ include file="footer.jsp" %>
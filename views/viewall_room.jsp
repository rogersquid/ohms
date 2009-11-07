<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/2000/REC-xhtml1-20000126/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs"> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
		<title>OHMS &raquo; All Rooms</title>
		<link rel='stylesheet' type='text/css' href='/ohms/ohms.css' />
	</head> 
	<body>
		<div id='wrapper'>
			<div id='header'>
				<!-- <a href='' id='header-image'></a> -->
			</div>
			<div id='main-nav'>
				<ul>
					<li><a href=''>Home</a></li>
					<li><a href=''>Login</a></li>
					<li><a href=''>Room availability</a></li>
					<li><a href=''>Etc</a></li>
				</ul>
			</div>
			<div id='content-wrapper'>
				<div id='left-nav'>
					<ul>
						<li><a href='login.html'>Login</a></li>
						<li><a href='register.html'>Register</a></li>
						<li><a href='editaccount.html'>Edit Account</a></li>
						<li><a href='password.html'>Forgot password</a></li>
						<li><a href='createroom.html'>Create Room</a></li>
						<li class='current'>Edit Rooms</li>
						<li><a href='deleteroom.html'>Delete Rooms</a></li>
					</ul>
				</div>
				<div id='content'>
					<div id='title'>All Rooms</div>
					<%
						if(request.getAttribute("status")=="viewall_rooms_failed") {
							%>
							<div class='error'>Failed to obtain the list of all rooms.</div>
							<%
						}
					%>
					<table>
						<tr>
							<th>Floor</th>
							<th>Room Number</th>
							<th>Nightly Price</th>
							<th>Room Type</th>
							<th>Number of beds</th>
							<th>Available</th>
						</tr>
						
						<%
						RoomMessage[] roomsArray = request.getAttribute("roomsArray");
						// These string arrays hold the data from the array of RoomMessages
						String floor	[roomsArray.length];
						String number	[roomsArray.length];
						String price	[roomsArray.length];
						String type		[roomsArray.length];
						String bedcount	[roomsArray.length];
						String available[roomsArray.length];
						
						// Populate the string arrays
						for (int i=0; i<roomsArray.length; i++){
							floor[i] 	= (String)	reply[i].room_floor;
							number[i]	= (String)	reply[i].room_number;
							type[i] 	= (String)	reply[i].room_type;
							bedcount[i] = (String)	reply[i].RoomInventory.numBeds;
							price[i] 	= (String)	reply[i].price;
							available[i] = (String)	reply[i].available;
						}
						
						// Print the values into the table
						for(int i=0; i < roomsArray.length; i++) { 
							%>
							<tr>
								<td></td>
								<td><%=floor[i]%></td>
								<td><%=number[i]%></td>
								<td><%=price[i]%></td>
								<td><%=type[i] %></td>
								<td><%=bedcount[i] %></td>
								<td><%=available[i] %></td>
							</tr>
							<%
						}
						%>
					</table>
				</div>
				<div class='clear'><!-- --></div>
			</div>
			<div id='footer'>
				Online Hotel Management Service &copy; 2009<br /> by 419 Software
			</div>
		</div>
	</body>
</html>
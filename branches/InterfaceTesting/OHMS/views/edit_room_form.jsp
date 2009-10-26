<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/2000/REC-xhtml1-20000126/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs"> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
		<title>OHMS &raquo; Edit Rooms</title>
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
					<div id='title'>Edit Rooms</div>
					<%
						if(request.getAttribute("status")=="register_failed") {
							%>
							<div class='error'>Register failed.</div>
							<%
						}
					%>
					<form action='' method='post'>
						<table>
							<tr>
								<td>Floor: </td>
								<td><input type='text' name='room_floor' /></td>
							</tr>
							<tr>
								<td>Room Number: </td>
								<td><input type='text' name='room_number' /></td>
							</tr>
							<tr>
								<td>Nightly Price: </td>
								<td><input type='text' name='price' /></td>
							</tr>
							<tr>
								<td>Room Type: </td>
								<td><input type='text' name='room_type' /></td>
							</tr>
							<tr>
								<td>Number of beds: </td>
								<td><input type='text' name='numBeds' /></td>
							</tr>
							<tr>
								<td>Room is currently available: </td>
								<td><input type='radio' name='available' value='true'/></td>
							</tr>
							<tr>
								<td>Room is currently cleaned: </td>
								<td><input type='radio' name='cleaned' value='true'/></td>
							</tr>
							<tr>
								<td>Special room features:</td>
							</tr>
							<tr>
								<td>En-suite Bathroom: </td>
								<td><input type='radio' name='onsuite' value='false'/></td>
							</tr>
							<tr>
								<td>Room has a television: </td>
								<td><input type='radio' name='tv' value='true'/></td>
							</tr>
							<tr>
								<td>Disability access: </td>
								<td><input type='radio' name='disability' value='false'/></td>
							</tr>
							<tr>
								<td>Elevator access: </td>
								<td><input type='radio' name='elevator' value='true'/></td>
							</tr>
							<tr>
								<td>Early-Bird wake-up call requested: </td>
								<td><input type='radio' name='ebirdcall' value='false'/></td>
							</tr>
							<tr>
								<td>Morning newspaper requested: </td>
								<td><input type='radio' name='emornpaper' value='false'/></td>
							</tr>
							
							
							<tr>
								<td></td>
								<td><input type='submit' value='Continue' /></td>
							</tr>
						</table>
					</form>
				</div>
				<div class='clear'><!-- --></div>
			</div>
			<div id='footer'>
				Online Hotel Management Service &copy; 2009<br /> by 419 Software
			</div>
		</div>
	</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/2000/REC-xhtml1-20000126/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs"> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
		<title>OHMS &raquo; Edit Account</title>
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
						<li class='current'>Edit Account</li>
						<li><a href='password.html'>Forgot password</a></li>
						<li><a href='createroom.html'>Create Room</a></li>
						<li><a href='editroom.html'>Edit Rooms</a></li>
						<li><a href='deleteroom.html'>Delete Rooms</a></li>
					</ul>
				</div>
				<div id='content'>
					<div id='title'>Edit Account</div>
					<%
						if(request.getAttribute("status")=="edit_account_failed") {
							%>
							<div class='error'>Attempt to edit the account failed.</div>
							<%
						}
					%>
					<form action='' method='post'>
						<table>
							<tr>
								<td>Username: </td>
								<td><input type='text' name='username' /></td>
							</tr>
							<tr>
								<td>Password: </td>
								<td><input type='password' name='password' /></td>
							</tr>
							<tr>
								<td>First name: </td>
								<td><input type='text' name='firstname' /></td>
							</tr>
							<tr>
								<td>Last name: </td>
								<td><input type='text' name='lastname' /></td>
							</tr>
							<tr>
								<td>E-mail address: </td>
								<td><input type='text' name='email' /></td>
							</tr>
							<tr>
								<td>Phone number: </td>
								<td><input type='text' name='phone' /></td>
							</tr>
							<tr>
								<td>Address: </td>
								<td><textarea name='address'></textarea></td>
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
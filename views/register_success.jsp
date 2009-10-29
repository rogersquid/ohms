<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/2000/REC-xhtml1-20000126/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs"> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
		<title>OHMS &raquo; Register</title>
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
						<li class='current'>Register</li>
						<li><a href='password.html'>Forgot password</a></li>
						<li><a href='createroom.html'>Create Room</a></li>
						<li><a href='editroom.html'>Edit Rooms</a></li>
						<li><a href='deleteroom.html'>Delete Rooms</a></li>
					</ul>
				</div>
				<div id='content'>
					<div id='title'>Register successful</div>
					Welcome, <%=request.getAttribute("firstname") %> <%=request.getAttribute("lastname") %>.
					<br />
					You may <a href='login.html'>login</a> with your e-mail address and password.
				</div>
				<div class='clear'><!-- --></div>
			</div>
			<div id='footer'>
				Online Hotel Management Service &copy; 2009<br /> by 419 Software
			</div>
		</div>
	</body>
</html>
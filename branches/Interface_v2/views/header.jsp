<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/2000/REC-xhtml1-20000126/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OHMS &raquo; Hotel Management</title>
		<link rel='stylesheet' type='text/css' href='/ohms/ohms.css' />
		<script type="text/javascript" src="/ohms/js/jquery.js"></script>
		<script type="text/javascript" src="/ohms/js/datepicker-jquery.js"></script>
		<script type="text/javascript" src="/ohms/js/date.js"></script>
		<script type="text/javascript" charset="utf-8"> 
            $(function() {
				$('.date-pick').datePicker();
            });
		</script> 
	</head>
	<body>
		<div id='wrapper'>
			<div id='header'>
				<!-- <a href='' id='header-image'></a> -->
				<%
					if(((Boolean)request.getAttribute("logged_in")).booleanValue()==true) {
						AccountMessage account = (AccountMessage)request.getAttribute("userInfo");
						%>
						User: <strong><%=account.firstName %> <%=account.lastName %></strong>
						<%
					} else {
						%>
						Not logged in.
						<%
					}
				%>
			</div>
			<div id='main-nav'>
				<ul>
					<li><a href='#'>Home</a></li>
					<li><a href='login.html'>Login</a></li>
					<li><a href='bookings.html?action=search'>Rooms</a></li>
				</ul>
			</div>
			<div id='content-wrapper'>
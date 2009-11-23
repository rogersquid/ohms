				<div id='left-nav'>
					<ul>
					<%
						if(((Boolean)request.getAttribute("logged_in")).booleanValue()==false) {
							%>
							<li><a href='register.html'>Register</a></li>
							<li><a href='login.html'>Login</a></li>
							<%
						} else {
							%>
							<li><a href='bookings.html?action=my_bookings'>My bookings</a></li>
							<li><a href='bookings.html?action=all_bookings'>All bookings</a></li>
							<li><a href='bookings.html?action=search'>Search available rooms</a></li>
							<li><a href='login.html?action=logout'>Logout</a></li>
							<%
						}
					%>
					</ul>
				</div>
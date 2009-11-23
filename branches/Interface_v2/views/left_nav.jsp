				<div id='left-nav'>
					<%
						if((boolean)request.getAttribute("logged_in")==false) {
							%>
							<li><a href='register.html'>Register</a></li>
							<li><a href='login.html'>Login</a></li>
							<%
						} else {
							%>
							<li><a href='bookings.html?action=my_bookings'>My bookings</a></li>
							<li><a href='bookings.html?action=all_bookings'>All bookings</a></li>
							<li><a href='bookings.html?action=search'>Search available rooms</a></li>
							<%
						}
					%>
				</div>
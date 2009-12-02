				<div id='left-nav'>
					<ul>
						<li><a href='room.html?action=search'>Search rooms</a></li>
						<li><a href='bookings.html?action=search'>Search available rooms</a></li>
					<%
						if(((Boolean)request.getAttribute("logged_in")).booleanValue()==false) {
							%>
							<li><a href='register.html'>Register</a></li>
							<li><a href='login.html'>Login</a></li>
							<%
						} else {
							%>
							<li><a href='account.html'>My account</a></li>
							<li><a href='bookings.html?action=my_bookings'>My bookings</a></li>
							<li><a href='extras.html?action=account_extras&amp;id=<%=(Integer)request.getAttribute("userID") %>'>My extras</a></li>
							<li><a href='bill.html?action=my_bills'>My bills</a></li>
							<li><a href='login.html?action=logout'>Logout</a></li>
							<%
						}
					%>
					</ul>
					<%
						if(((Integer)request.getAttribute("authLevel"))>=3) {
							%>
					<hr />
					<ul>

							<li><a href='bookings.html?action=all_bookings'>All bookings</a></li>
							<li><a href='bill.html?action=all_bills'>All bills</a></li>
							<li><a href='extras.html?action=all_extras'>All extras</a></li>
							<li><a href='account.html?action=all_accounts'>All accounts</a></li>
							<li><a href='room.html?action=all_rooms'>All rooms</a></li>
					</ul>
							<%
						}
					%>
					</ul>
					<%
						if(((Integer)request.getAttribute("authLevel"))>=2) {
							%>
					<hr />
					<ul>
							<li><a href='report.html'>Reports</a></li>
					</ul>
							<%
						}
					%>
				</div>
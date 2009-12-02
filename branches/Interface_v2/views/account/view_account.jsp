<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<% AccountMessage account = (AccountMessage)request.getAttribute("account"); %>
					<div id='title'>Account &bull <a href='account.html?action=edit&amp;id=<%=account.accountID %>'><small>Edit</small></a></div>
					<%
						String status = "";
						if(request.getParameter("status")!=null) { status = request.getParameter("status"); }

						if(status.equals("login")) {
							%>
							<div class='success'><strong>Login successful.</strong></div>
							<%
						}
						if(status.equals("edit_success")) {
							%>
							<div class='success'><strong>Account updated.</strong></div>
							<%
						}
					%>
					<table class='vertical'>
						<tr>
							<th>E-mail</th>
							<td><%=account.email %></td>
						</tr>
						<tr>
							<th>First name</th>
							<td><%=account.firstName %></td>
						</tr>
						<tr>
							<th>Last name</th>
							<td><%=account.lastName %></td>
						</tr>
						<tr>
							<th>Account type</th>
							<td><%=account.accountType %></td>
						</tr>
						<tr>
							<th>Phone</th>
							<td><%=account.phone %></td>
						</tr>
						<tr>
							<th>Address</th>
							<td><%=account.address %></td>
						</tr>
						<tr>
							<th>Gender</th>
							<td><% if(account.gender){ %>Male<% } else { %> Female <% } %></td>
						</tr>
					</table>

					<a href='bookings.html?action=account_bookings&amp;id=<%=account.accountID %>'>View bookings for this account</a>
					<br />
					<a href='extras.html?action=account_extras&amp;id=<%=account.accountID %>'>View extras for this account</a>
				</div>
<%@ include file="../footer.jsp" %>

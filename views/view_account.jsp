<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Account</div>
					<%
						AccountMessage account = (AccountMessage)request.getAttribute("account");
						if((String)request.getAttribute("login_successful")) {
							%>
							<div class='success'>Login successful.</div>
							<%
						}
					%>
					<table class='rooms'>
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
							<td><%=account.gender %></td>
						</tr>
					</table>
				</div>
<%@ include file="footer.jsp" %>
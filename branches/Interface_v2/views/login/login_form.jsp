<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Login</div>
					<%
						if(request.getAttribute("status")=="login_failed") {
							%>
							<div class='error'><strong>Login failed.</strong></div>
							<%
						}
						if(request.getParameter("status")=="logout") {
							%>
							<div class='success'><strong>You have logged out.</strong></div>
							<%
						}
					%>
					<form action='' method='post'>
						<table>
							<tr>
								<td>E-mail: </td>
								<td><input type='text' name='email' /></td>
							</tr>
							<tr>
								<td>Password: </td>
								<td><input type='password' name='password' /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type='submit' value='Continue' /></td>
							</tr>
						</table>
					</form>
				</div>
<%@ include file="../footer.jsp" %>
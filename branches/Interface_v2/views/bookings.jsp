<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Register</div>
					<%
						if(request.getAttribute("status")=="register_failed") {
							%>
							<div class='error'>Register failed: <%=request.getAttribute("message") %></div>
							<%
						}
					%>
					<form action='' method='post'>
						<table>
							<tr>
								<td>E-mail address: </td>
								<td><input type='text' name='email' /></td>
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
								<td>Gender: </td>
								<td><input type='radio' name='gender' value='1' />Male &nbsp; <input type='radio' name='gender' value='0' />Female</td>
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
<%@ include file="footer.jsp" %>
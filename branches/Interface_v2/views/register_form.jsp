<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Register</div>
					<%
						AccountMessage account = (AccountMessage)request.getAttribute("account");
						if(account==null) {
							account = new AccountMessage();
							account.email = "";
							account.password = "";
							account.re_password = "";
							account.firstName = "";
							account.lastName = "";
							account.phone = "";
							account.address = "";

						}
						if(request.getAttribute("status")=="register_failed") {
							%>
<<<<<<< .mine
							<div class='error'>Register failed. <%=request.getAttribute("message") %></div>
=======
							<div class='error'><strong>Register failed:</strong> <%=request.getAttribute("message") %></div>
>>>>>>> .r432
							<%
						}
					%>
					<form action='' method='post'>
						<table>
							<tr>
								<td>E-mail address: </td>
								<td><input type='text' name='email' value='<%=account.email %>' /></td>
							</tr>
							<tr>
								<td>Password: </td>
								<td><input type='password' name='password' /></td>
							</tr>
							<tr>
								<td>Re-type password: </td>
								<td><input type='password' name='re_password' /></td>
							</tr>
							<tr>
								<td>First name: </td>
								<td><input type='text' name='firstname' value='<%=account.firstName %>' /></td>
							</tr>
							<tr>
								<td>Last name: </td>
								<td><input type='text' name='lastname' value='<%=account.lastName %>' /></td>
							</tr>
							<tr>
								<td>Gender: </td>
								<td><input type='radio' name='gender' value='1' checked='checked' />Male &nbsp; <input type='radio' name='gender' value='0' />Female</td>
							</tr>
							<tr>
								<td>Phone number: </td>
								<td><input type='text' name='phone' value='<%=account.phone %>' /></td>
							</tr>
							<tr>
								<td>Address: </td>
								<td><textarea name='address'><%=account.address %></textarea></td>
							</tr>

							<tr>
								<td></td>
								<td><input type='submit' value='Continue' /></td>
							</tr>
						</table>
					</form>
				</div>
<%@ include file="footer.jsp" %>
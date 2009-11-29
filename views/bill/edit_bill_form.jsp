<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Edit Account</div>
					<%
						AccountMessage account = (AccountMessage)request.getAttribute("account");
						if(account==null) {
							%>
							<div class='error'><strong>Edit Account failed:</strong> No account data</div>
							<%
						}
						if(request.getAttribute("status")=="edit_account_failed") {
							%>
							<div class='error'><strong>Edit Account failed. Please fix the errors listed below.</strong><br /> <%=request.getAttribute("message") %></div>
							<%
						}
						if(request.getAttribute("status")=="edit_account_success") {
							%>
							<div class='success'><strong>Account updated.</strong></div>
							<%
						}
					%>
					<form action='' method='post'>
						<input type='hidden' name='action' value='edit' />
						<input type='hidden' name='id' value='<%=account.accountID %>' />
						<table>
							<tr>
								<td>E-mail address: </td>
								<td><%=account.email %></td>
							</tr>
							<tr>
								<td>Password: </td>
								<td><input type='password' name='password' /></td>
							</tr>
							<tr>
								<td>Re-type password: </td>
								<td><input type='password' name='re_password' /></td>
							</tr>
						<%
							if((Integer)(request.getAttribute("authLevel")) > 3)
							{
							%>
							<tr>
								<td>Account Type: </td>
								<td><select name='accountType' >
									<option value='customer'
									<%
									if(account.accountType.compareToIgnoreCase("customer") == 0) {
										%>
										selected="selected"
										<%
									}
									%>
									> Customer </option>
									<option value='maid'
									<%
									if(account.accountType.compareToIgnoreCase("maid") == 0) {
										%>
										selected="selected"
										<%
									}
									%>
									> Maid </option>
									<option value='staff'
									<%
									if(account.accountType.compareToIgnoreCase("staff") == 0) {
										%>
										selected="selected"
										<%
									}
									%>> Staff </option>
									<option value='admin'
									<%
									if(account.accountType.compareToIgnoreCase("admin") == 0) {
										%>
										selected="selected"
										<%
									}
									%>
									> Admin </option>
								</select></td>
							</tr>
						<% } %>

							<tr>
								<td>First name: </td>
								<td><input type='text' name='firstName' value='<%=account.firstName %>' /></td>
							</tr>
							<tr>
								<td>Last name: </td>
								<td><input type='text' name='lastName' value='<%=account.lastName %>' /></td>
							</tr>
							<tr>
								<td>Gender: </td>
								<td><input type='radio' name='gender' value='1'
								<%
									if(account.gender == true) {
										%>
										checked='checked'
										<%
									}
									%>
									/>Male &nbsp;
									<input type='radio' name='gender' value='0'
									<%
									if(account.gender == false) {
										%>
										checked='checked'
										<%
									}
									%>
									/>Female</td>
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
<%@ include file="../footer.jsp" %>
<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Bills Accounts</div>
					<%
						if(request.getAttribute("status")=="delete_success") {
							%>
							<div class='success'><strong>Success:</strong> <%=request.getAttribute("message") %></div>
							<%
						}

					%>

					<table class='accounts'>
						<tr>
							<th></th>
							<th>Name</th>
							<th>E-mail</th>
							<th>Type</th>
							<th>Phone</th>
							<th></th>
						</tr>

						<%
						Message data = (Message)request.getAttribute("data");

						// Print the values into the table
						for(int i=0; i < data.accounts.length; i++) {
							%>
							<tr>
								<td><span class='index'><%=i+1 %></span></td>
								<td><a href='account.html?action=view&amp;id=<%=data.accounts[i].accountID %>'><%=data.accounts[i].firstName %> <%=data.accounts[i].lastName %></a></td>
								<td><%=data.accounts[i].email %></td>
								<td><%=data.accounts[i].accountType %></td>
								<td><%=data.accounts[i].phone %></td>
								<td><a href='account.html?action=delete&amp;id=<%=data.accounts[i].accountID %>' title='Delete account' class='delete'>[ X ]</a></td>
							</tr>
							<%
						}
						%>
					</table>
				</div>
<%@ include file="../footer.jsp" %>
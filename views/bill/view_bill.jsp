<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<% BillMessage bill = (BillMessage)request.getAttribute("bill"); %>
					<div id='title'>Bill &bull <a href='bill.html?action=edit&amp;id=<%=account.accountID %>'><small>Edit</small></a></div>
					<%
						String status = "";
						if(request.getParameter("status")!=null) { status = request.getParameter("status"); }

						if(status.equals("edit_success")) {
							%>
							<div class='success'><strong>Account updated.</strong></div>
							<%
						}
					%>
					<table class='bills'>
						<tr>
							<th></th>
							<th>Customer</th>
							<th></th>
							<th>paymentType</th>
							<th>status</th>
							<th>price</th>
							<th></th>
						</tr>

						<%
						Message data = (Message)request.getAttribute("data");

						// Print the values into the table
						for(int i=0; i < data.bills.length; i++) {
							%>
							<tr>
								<td><span class='index'><%=i+1 %></span></td>
								<td><a href='account.html?action=view&amp;id=<%=data.accounts[i].accountID %>'><%=data.accounts[i].firstName %> <%=data.accounts[i].lastName %></a></td>
								<td><a href='account.html?action=view&amp;id=<%=data.bills[i].bookingID %>'>
								<td><%=data.bills[i].paymentType %></td>
								<td><% if(data.bills[i].status){ %>Paid<% } else { %> Not Paid <% } %></td>
								<td><%=data.bills[i].price %></td>
							</tr>
							<%
						}
						%>
					</table>
				</div>
<%@ include file="../footer.jsp" %>
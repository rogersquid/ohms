<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>My Bills</div>
					<%
						if(request.getAttribute("status")=="delete_success") {
							%>
							<div class='success'><strong>Success:</strong> <%=request.getAttribute("message") %></div>
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
								<td><%=data.bills[i].totalPrice %></td>
							</tr>
							<%
						}
						%>
					</table>
				</div>
<%@ include file="../footer.jsp" %>
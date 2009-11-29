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

					<%
					Message data = (Message)request.getAttribute("data");
					if(data.bills.length > 0) {
						%>
						<table class='horizontal'>
							<tr>
								<th></th>
								<th>Bill</th>
								<th>Booking</th>
								<th>Payment type</th>
								<th>Status</th>
								<th>Amount due</th>
								<th></th>
							</tr>

							<%

							// Print the values into the table
							for(int i=0; i < data.bills.length; i++) {
								%>
								<tr>
									<td><span class='index'><%=i+1 %></span></td>
									<td><a href="bill.html?action=view&amp;id=<%=data.bills[i].billID %>"><%=data.bills[i].billID %></td>
									<td><a href='bookings.html?action=view&amp;id=<%=data.bills[i].bookingID %>'><%=data.bills[i].bookingID %></a></td>
									<td><%=data.bills[i].paymentType %></td>
									<td><% if(data.bills[i].status){ %>Paid<% } else { %> Not Paid <% } %></td>
									<td>$<%=data.bills[i].totalPrice %></td>
								</tr>
								<%
							}
							%>
						</table>
						<%
					} else {
						%>
						<div class='info'>You have no bills.</div>
						<%
					} %>
				</div>
<%@ include file="../footer.jsp" %>
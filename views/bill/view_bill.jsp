<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<% BillMessage bill = (BillMessage)request.getAttribute("bill"); %>
					<div id='title'>Bill &bull <a href='bill.html?action=edit&amp;id=<%=bill.billID %>'><small>Edit</small></a></div>
					<%
						String status = "";
						if(request.getParameter("status")!=null) { status = request.getParameter("status"); }

						if(status.equals("edit_success")) {
							%>
							<div class='success'><strong>Account updated.</strong></div>
							<%
						}
					%>
					<table class='bill'>
						<tr>
							<th></th>
							<th>paymentType</th>
							<th>status</th>
							<th>price</th>
							<th></th>
						</tr>
						<tr>
							<td><a href='bookings.html?action=view&amp;id=<%=bill.bookingID %>'><%=bill.bookingID %></a></td>
							<td><%=bill.paymentType %></td>
							<td><% if(bill.status){ %>Paid<% } else { %> Not Paid <% } %></td>
							<td><%=bill.totalPrice %></td>
						</tr>
					</table>
				</div>
<%@ include file="../footer.jsp" %>
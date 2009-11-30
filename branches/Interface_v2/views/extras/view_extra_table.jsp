<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<% Message data = (Message)request.getAttribute("data");
					%>
					<div id='title'>Extras of  
						<%
							if (request.getParameter("action")!=null)
							{
								if(request.getParameter("action").equals("booking")
								{
									%> Booking ID: <a href='bookings.html?action=view&amp;id=<%=request.getParameter("bookingID") %>'><%=request.getParameter("bookingID") %></a>
									<% 
								}else if(request.getParameter("action").equals("all_extras")
								{
									%> All Customers
									<%
								}
								else if(request.getParameter("action").equals("account")
								{
									%> Account ID: <a href='account.html?action=view&amp;id=<%=request.getParameter("accountID") %>'><%=request.getParameter("accountID") %></a>
								}
							}
						%>
					</div>
					<%
						String status = "";
						if(request.getParameter("status")!=null) { status = request.getParameter("status"); }
					%>
					<%
						
						if(data.bookings.length>0) {

							%>
							<table class='horizontal'>
								<tr>
									<th></th>
									<th>Extra ID</th>
									<th>Extra Name</th>
									<th>Price</th>
									<th>Date</th>
									<th></th>
								</tr>

								<%
								// Print the values into the table
								for(int i=0; i < data.extras.length; i++) {
								%>
								<tr>
									<td><span class='index'><%=i+1 %></span></td>
									<td><a href='extras.html?action=view&amp;id=<%=data.extras[i].extraID %>'><%=data.extras[i].extraID %></a></td>
									<td><%=data.extras[i].extraName %> </td>
									<td>$<%=data.extras[i].price %></td>
									<td><%=data.extras[i].date %></td>
									<td><%
									if(request.getParameter("authLevel") > 3 || data.extras[i].price == 0)
									{
									%>
										<a href='extras.html?action=delete&amp;id=<%=data.extras[i].extraID %>' title='Delete extra' class='delete'>[ X ]</a>
									<%
									}
									%> </td>
								</tr>
								<%
							} %>
							</table>
							<%
						} else { %>
							<div class='info'>There are no extras found.</div>
							<%
						} %>
				</div>
<%@ include file="footer.jsp" %>

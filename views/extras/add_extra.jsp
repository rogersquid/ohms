<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Add Extra to Booking <%=request.getParameter("bookingID") %>
					</div>
					<%
						ExtraMessage extra = (ExtraMessage)request.getAttribute("extra");
						if(extra==null) {
							extra = new ExtraMessage();
							extra.bookingID = Integer.parseInt(request.getParameter("bookingID"));
							extra.price = 0;
							extra.extraName = "";
							extra.date = new java.sql.Date(System.currentTimeMillis());
						}
						if(request.getAttribute("status")=="add_failed") {
							%>
							<div class='error'><strong>Adding Extra failed.</strong> <br /> <%=request.getAttribute("message") %></div>
							<%
						}
					%>
					<form action='extras.html' method='post'>
						<input type='hidden' name='action' value='add' />
						<input type='hidden' name='bookingID' value='<%=request.getParameter("bookingID") %>' />
						<table>
							<tr>
								<td>Extra Name: </td>
								<td><input type='text' name='extraName' value='<%=extra.extraName %>' /></td>
							</tr>
							<tr>
								<td>Price: </td>
								<td><input type='text' name='price' value='<%=extra.price %>'/></td>
							</tr>
							<tr>
								<td>Date: </td>
								<%
									DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
								%>
								<td><input type='text' name='date' value='<%=df.format(extra.date) %>' class='date-pick'/></td>
							</tr>
							<tr>
								<td></td>
								<td><input type='submit' value='Continue' /></td>
							</tr>
						</table>
					</form>
				</div>
<%@ include file="../footer.jsp" %>
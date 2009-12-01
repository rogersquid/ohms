<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Add Extra to Booking <%
					=request.getParameter("bookingID")
					%>
					</div>
					<%
						ExtraMessage extra = (ExtraMessage)request.getAttribute("extra");
						if(extra==null) {
							extra = new ExtraMessage();
							extra.bookingID = request.getParameter("bookingID");
							extra.price = 0;
							extra.extraName = "";
							extra.date = "";
						}
						if(request.getAttribute("status")=="extra_failed") {
							%>
							<div class='error'><strong>Adding Extra failed.</strong> <br /> <%=request.getAttribute("message") %></div>
							<%
						}
					%>
					<form action='add' method='post'>
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
								<td><input type='text' name='date' class='date-pick'/></td>
							</tr>
							<tr>
								<td></td>
								<td><input type='submit' value='Continue' /></td>
							</tr>
						</table>
					</form>
				</div>
<%@ include file="../footer.jsp" %>
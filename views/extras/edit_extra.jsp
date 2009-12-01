<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Edit Extra <% =request.getParameter("id") %>
					</div>
					
					<%
					ExtraMessage extra = request.getAttribute("extra");
					if(request.getAttribute("status")=="edit_extras_failed") {
							%>
							<div class='error'><strong>Edit Extra failed.</strong> <br /> <%=request.getAttribute("message") %></div>
							<%
					}
					if(request.getAttribute("status")=="edit_extras_success") {
						%>
							<div class='success'><strong>Account updated.</strong></div>
						<%
					}
						%>
					<form action='edit' method='post'>
						<table>
							<tr>
								<td>Extra Name: </td>
								<td><input type='text' name='extraName' value='<%=extra.extraName %>' /></td>
							</tr>
							<tr>
								<td>Price: </td>
								<td><input type='text' name='price' value='<%=extra.price %>/></td>
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
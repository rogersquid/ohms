<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">Confirm Booking</DIV>
					<form method='post' action='bookings.html'>
						<input type='hidden' name='action' value='add_booking' />
						<table>
							<tr>
								<th>Room number: </th>
								<td>
									<a href='room.html?action=view&amp;id=<%=request.getParameter("roomID") %>'><%=request.getParameter("roomNumber") %></a>
									<input type="hidden" name="roomID" value="<%=request.getParameter("roomID") %>" >
								</td>
							</tr>
							<tr>
								<th>Start date: </th>
								<td><%=request.getParameter("startDate") %> <input type="hidden" name="startDate" value="<%=request.getParameter("startDate") %>"></td>
							</tr>
							<TR>
								<th>End date: </th>
								<TD><%=request.getParameter("endDate") %> <INPUT type="hidden" name="endDate" value="<%=request.getParameter("endDate") %>"></TD>
							</TR>
						</table>
						<INPUT type="submit" value="Submit booking">
					</form>
				</div>
<%@ include file="../footer.jsp" %>
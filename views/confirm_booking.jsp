<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">Confirm Booking</DIV> 
					<form method='post' action='bookings.html'>
						<input type='hidden' name='action' value='add_booking' />
						<table> 
							<tr> 
								<th>Room number: </th> 
								<td><%=request.getParameter("roomNumber") %><input type="hidden" name="roomID" value="<%=request.getParameter("roomID") %>" ></td> 
							</tr> 
							<tr> 
								<th>Start date: </th> 
								<td><input type="text" name="startDate" value="<%=request.getParameter("startDate") %>" class="date-pick"></td> 
							</tr> 
							<TR> 
								<th>End date: </th> 
								<TD><INPUT type="text" name="endDate" value="<%=request.getParameter("endDate") %>" class="date-pick"></TD> 
							</TR>
						</table>
						<INPUT type="submit" name="Submit booking" style='margin-left: 50px;'>
					</form>
				</div>
<%@ include file="footer.jsp" %>
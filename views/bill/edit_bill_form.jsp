<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Edit Bill</div>
					<%
						BillMessage bill = (BillMessage)request.getAttribute("bill");
						if(bill==null) {
							%>
							<div class='error'><strong>Edit Bill failed:</strong> No bill data</div>
							<%
						}
						if(request.getAttribute("status")=="edit_bill_failed") {
							%>
							<div class='error'><strong>Edit Bill failed. Please fix the errors listed below.</strong><br /> <%=request.getAttribute("message") %></div>
							<%
						}
						if(request.getAttribute("status")=="edit_bill_success") {
							%>
							<div class='success'><strong>Bill updated.</strong></div>
							<%
						}
					%>
					<form action='' method='post'>
						<input type='hidden' name='action' value='edit' />
						<input type='hidden' name='id' value='<%=bill.billID %>' />
						<table>
							<tr>
								<td>Booking: </td>
								<td><a href='bookings.html?action=view&amp;id=<%=bill.bookingID %>'><%=bill.bookingID %></a></td>
							</tr>
							<tr>
								<td>Payment type: </td>
								<td><%=bill.paymentType %></td>
							</tr>
							<tr>
								<td>Status: </td>
								<td>
									<input type='radio' name='status' value='1' <%
									if(bill.status == true) {
										%>
										checked='checked'
										<%
									}
									%>/>Paid &nbsp; <input type='radio' name='status' value='0' <%
									if(bill.status == false) {
										%>
										checked='checked'
										<%
									}
									%>/>Not paid
								</td>
							</tr>
							<tr>
								<td></td>
								<td><input type='submit' value='Submit' /></td>
							</tr>
						</table>
					</form>
				</div>
<%@ include file="../footer.jsp" %>
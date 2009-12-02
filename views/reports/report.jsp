<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<DIV id="title">Reports</DIV>
					<%

						ReportMessage cleannessReport = (ReportMessage)request.getAttribute("cleannessReport");
						if(cleannessReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No cleanness report data</div>
							<%
						}
						ReportMessage extraReport = (ReportMessage)request.getAttribute("extraReport");
						if(extraReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No extra report data</div>
							<%
						}
						ReportMessage roomStatusReport = (ReportMessage)request.getAttribute("roomStatusReport");
						if(roomStatusReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No Room Status report data</div>
							<%
						}
						ReportMessage roomAnalysisReport = (ReportMessage)request.getAttribute("roomAnalysisReport");
						if(roomAnalysisReport==null) {
							%>
							<div class='error'><strong>Report failed:</strong> No room analysis report data</div>
							<%
						}

					%>

					<h2>Cleanness Report</h2>
					<div class='report'>
						<table class='vertical'>
							<tr>
								<th>Rooms that are clean: </th>
								<td><%=cleannessReport.stats[1]*100 %>%</td>
							</tr>
							<tr>
								<th>Rooms that are unclean: </th>
								<td><%=cleannessReport.stats[0]*100 %>%</td>
							</tr>
						</table>

						<h3>Unclean rooms</h3>
						<%
							if(cleannessReport.tables[0]!=null) {
								%>
								<table class='horizontal'>
									<tr>
										<th></th>
										<th>Room #</th>
										<th>Type</th>
										<th>Price</th>
										<th>Available</th>
										<th>Cleaned</th>
										<th></th>
									</tr>
									<%
										// Print the values into the table
										for(int i=0; i < cleannessReport.tables[0].rooms.length; i++) {
										%>
										<tr>
											<td><span class='index'><%=i+1 %></span></td>
											<td><a href='room.html?action=view&amp;id=<%=cleannessReport.tables[0].rooms[i].roomID %>'><%=cleannessReport.tables[0].rooms[i].roomNumber %></a></td>
											<td><%=cleannessReport.tables[0].rooms[i].roomType %></td>
											<td><%=cleannessReport.tables[0].rooms[i].price %></td>
											<td><%=cleannessReport.tables[0].rooms[i].available %></td>
											<td><%=cleannessReport.tables[0].rooms[i].cleaned %></td>
											<td><a href='room.html?action=clean&amp;id=<%=cleannessReport.tables[0].rooms[i].roomID %>' >Set to clean</a></td>
										</tr>
										<%
									} %>
									</table>
								<%
							} else {
								%>
								<div class='info'>There are no unclean rooms.</div>
								<%
							}
						%>
					</div>
					<%
						if((Integer)request.getAttribute("authLevel")>2) {
							%>
						<br /><br />
						<h2>Room Type Occupancy Report</h2>
						<div class='report'>
							<!--
							// tables[0].rooms[i]	- roomNumber holds all roomNumber that has been occupied
							//		stats[i]		- holds how may days each roomNumber has been occupied (link to roomNumber above)
							// tables[1].rooms[i]	- roomType holds all roomTypes that has been occupied
							//		graphs[i]		- holds how many days each roomType has been occupied (link to roomType above)
							// tables[2].rooms[i]	- roomNumber holds which rooms has been occupied for the most days
							//		values[0]		- holds how many days the most occupied room has been occupied
							// tables[3].rooms[i]	- roomNumber holds which rooms has been booked the most times
							//		values[1]		- holds how many times the most booked room has been booked
							-->
							<div id="room-type-graph" style="width:600px;height:200px"></div>
							<script id="source" language="javascript" type="text/javascript">
								$(function () {

									var d1 = [
										<%
											for(int i=0; i<roomAnalysisReport.graphs.length; i++) {
												%>
												[<%=i %>, <%=roomAnalysisReport.graphs[i] %>],
												<%
											}
										%>
									];

									$.plot($("#room-type-graph"), [
										{
											data: d1,
											bars: { show: true }
										}
									],
										{
											xaxis: {
												ticks: [
													<%
														for(int i=0; i<roomAnalysisReport.graphs.length; i++) {
															%>
															[<%=(i+0.5) %>, "<%=roomAnalysisReport.tables[1].rooms[i].roomType %>"],
															<%
														}
													%>
												]
											},
											yaxis: { min: 0 },
										}
									);
								});
							</script>
							<table class='vertical'>
								<tr>
									<th>Most occupied room: </th>
									<td><a href='room.html?action=view&amp;id=<%=roomAnalysisReport.tables[2].rooms[0].roomID %>'><%=roomAnalysisReport.tables[2].rooms[0].roomNumber %></a>, <%=roomAnalysisReport.values[0] %> days</td>
								</tr>
								<tr>
									<th>Most booked room: </th>
									<td><a href='room.html?action=view&amp;id=<%=roomAnalysisReport.tables[3].rooms[0].roomID %>'><%=roomAnalysisReport.tables[3].rooms[0].roomNumber %></a>, <%=roomAnalysisReport.values[1] %> days</td>
								</tr>
							</table>


						</div>


						<br /><br />

						<h2>Room Status Report</h2>
						<div class='report'>
							<!--
							// tables[0].rooms[i]	- holds today's available rooms
							// tables[1].rooms[i]	- holds today's unavailable rooms
							// tables[2].rooms[i]	- holds today's occupied available rooms
							// tables[3].rooms[i]	- holds today's unoccupied available rooms
							-->
							<h3>Today's unoccupied and available rooms</h3>
							<%
								if(roomStatusReport.tables != null && roomStatusReport.tables[3].rooms.length>0) {
									%>
									<table class='horizontal'>
										<tr>
											<th></th>
											<th>Room #</th>
											<th>Type</th>
											<th>Price</th>
											<th>Available</th>
											<th>Cleaned</th>
										</tr>
										<%
											// Print the values into the table
											for(int i=0; i < roomStatusReport.tables[3].rooms.length; i++) {
											%>
											<tr>
												<td><span class='index'><%=i+1 %></span></td>
												<td><a href='room.html?action=view&amp;id=<%=roomStatusReport.tables[3].rooms[i].roomID %>'><%=roomStatusReport.tables[3].rooms[i].roomNumber %></a></td>
												<td><%=roomStatusReport.tables[3].rooms[i].roomType %></td>
												<td><%=roomStatusReport.tables[3].rooms[i].price %></td>
												<td><%=roomStatusReport.tables[3].rooms[i].available %></td>
												<td><%=roomStatusReport.tables[3].rooms[i].cleaned %></td>
											</tr>
											<%
										} %>
										</table>
									<%
								} else {
									%>
									<div class='info'>There are no unclean rooms.</div>
									<%
								}
							%>
							<h3>Today's occupied rooms</h3>
							<%
								if(roomStatusReport.tables != null && roomStatusReport.tables[2].rooms.length>0) {
									%>
									<table class='horizontal'>
										<tr>
											<th></th>
											<th>Room #</th>
											<th>Type</th>
											<th>Price</th>
											<th>Available</th>
											<th>Cleaned</th>
										</tr>
										<%
											// Print the values into the table
											for(int i=0; i < roomStatusReport.tables[2].rooms.length; i++) {
											%>
											<tr>
												<td><span class='index'><%=i+1 %></span></td>
												<td><a href='room.html?action=view&amp;id=<%=roomStatusReport.tables[2].rooms[i].roomID %>'><%=roomStatusReport.tables[2].rooms[i].roomNumber %></a></td>
												<td><%=roomStatusReport.tables[2].rooms[i].roomType %></td>
												<td><%=roomStatusReport.tables[2].rooms[i].price %></td>
												<td><%=roomStatusReport.tables[2].rooms[i].available %></td>
												<td><%=roomStatusReport.tables[2].rooms[i].cleaned %></td>
											</tr>
											<%
										} %>
										</table>
									<%
								} else {
									%>
									<div class='info'>There are no occupied rooms.</div>
									<%
								}
							%>
							<h3>Today's unavailable rooms</h3>
							<%
								if(roomStatusReport.tables != null && roomStatusReport.tables[1].rooms.length>0) {
									%>
									<table class='horizontal'>
										<tr>
											<th></th>
											<th>Room #</th>
											<th>Type</th>
											<th>Price</th>
											<th>Available</th>
											<th>Cleaned</th>
										</tr>
										<%
											// Print the values into the table
											for(int i=0; i < roomStatusReport.tables[1].rooms.length; i++) {
											%>
											<tr>
												<td><span class='index'><%=i+1 %></span></td>
												<td><a href='room.html?action=view&amp;id=<%=roomStatusReport.tables[1].rooms[i].roomID %>'><%=roomStatusReport.tables[1].rooms[i].roomNumber %></a></td>
												<td><%=roomStatusReport.tables[1].rooms[i].roomType %></td>
												<td><%=roomStatusReport.tables[1].rooms[i].price %></td>
												<td><%=roomStatusReport.tables[1].rooms[i].available %></td>
												<td><%=roomStatusReport.tables[1].rooms[i].cleaned %></td>
											</tr>
											<%
										} %>
										</table>
									<%
								} else {
									%>
									<div class='info'>There are no occupied rooms.</div>
									<%
								}
							%>
						</div>

						<br /><br />

						<h2>Extras Report</h2>
						<div class='report'>
							<!--
							// tables[0].extras[i] 	- holds bookingID
							// 		stats[i] 		- holds number of Extras order (linked to tables[0].extras
							// tables[1].extras[i] 	- Today's Extra
							// tables[2].extras[i]	- Tomorrow's Extra
							// tables[3].extras[i]	- extraName holds extra type, price holds amount earned for that type of extra today
							// tables[4].extras[i]	- date holds each unique date, price holds armount earned from extra that day
							// tables[5].extras[i]	- extraName holds most extra type that earned the most, price holds how much earned
							-->
							<h3>Today's extras</h3>
							<%
								if(extraReport.tables != null && extraReport.tables[1].extras.length>0) {
									%>
									<table class='horizontal'>
										<tr>
											<th></th>
											<th>Name</th>
											<th>Booking</th>
											<th>Price</th>
											<th>Date</th>
										</tr>
										<%
											// Print the values into the table
											for(int i=0; i < extraReport.tables[1].extras.length; i++) {
											%>
											<tr>
												<td><span class='index'><%=i+1 %></span></td>
												<td><a href='extras.html?action=view&amp;id=<%=extraReport.tables[1].extras[i].extraID %>'><%=extraReport.tables[1].extras[i].extraName %></a></td>
												<td><a href='bookings.html?action=view&amp;id=<%=extraReport.tables[1].extras[i].bookingID %>'><%=extraReport.tables[1].extras[i].bookingID %></a></td>
												<td>$<%=extraReport.tables[1].extras[i].price %></td>
												<td><%=extraReport.tables[1].extras[i].date %></td>
											</tr>
											<%
										} %>
										</table>
										<br /><br />
										<div id="extras-graph" style="width:600px;height:200px"></div>
										<script id="source" language="javascript" type="text/javascript">
											$(function () {

												var d1 = [
													<%
														for(int i=0; i<extraReport.tables[3].extras.length; i++) {
															%>
															[<%=i %>, <%=extraReport.tables[3].extras[i].price %>],
															<%
														}
													%>
												];

												$.plot($("#extras-graph"), [
													{
														data: d1,
														bars: { show: true }
													}
												],
													{
														xaxis: {
															ticks: [
																<%
																	for(int i=0; i<extraReport.tables[3].extras.length; i++) {
																		%>
																		[<%=(i+0.5) %>, "<%=extraReport.tables[3].extras[i].extraName %>"],
																		<%
																	}
																%>
															]
														},
														yaxis: { min: 0 },
													}
												);
											});
										</script>

									<%
								} else {
									%>
									<div class='info'>There are no extras.</div>
									<%
								}
							%>


							<h3>Tomorrow's extras</h3>
							<%
								if(extraReport.tables != null && extraReport.tables[2].extras.length>0) {
									%>
									<table class='horizontal'>
										<tr>
											<th></th>
											<th>Name</th>
											<th>Booking</th>
											<th>Price</th>
											<th>Date</th>
										</tr>
										<%
											// Print the values into the table
											for(int i=0; i < extraReport.tables[2].extras.length; i++) {
											%>
											<tr>
												<td><span class='index'><%=i+1 %></span></td>
												<td><a href='extras.html?action=view&amp;id=<%=extraReport.tables[2].extras[i].extraID %>'><%=extraReport.tables[2].extras[i].extraName %></a></td>
												<td><a href='bookings.html?action=view&amp;id=<%=extraReport.tables[2].extras[i].bookingID %>'><%=extraReport.tables[2].extras[i].bookingID %></a></td>
												<td>$<%=extraReport.tables[2].extras[i].price %></td>
												<td><%=extraReport.tables[2].extras[i].date %></td>
											</tr>
											<%
										} %>
										</table>
									<%
								} else {
									%>
									<div class='info'>There are no extras.</div>
									<%
								}
							%>
						</div>
						<%
						}
						%>
				</div>
<%@ include file="../footer.jsp" %>

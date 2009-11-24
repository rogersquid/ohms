				<div id='content'>
					<div id='title'>Edit Rooms</div>
					<%
						if(request.getAttribute("status")=="edit_room_failed") {
							%>
							<div class='error'>Attempt to edit the room failed.</div>
							<%
						}
					%>
					<form action='' method='post'>
						<table>
							<tr>
								<td>Floor: </td>
								<td><input type='text' name='room_floor' /></td>
							</tr>
							<tr>
								<td>Room Number: </td>
								<td><input type='text' name='room_number' /></td>
							</tr>
							<tr>
								<td>Nightly Price: </td>
								<td><input type='text' name='price' /></td>
							</tr>
							<tr>
								<td>Room Type: </td>
								<td><input type='text' name='room_type' /></td>
							</tr>
							<tr>
								<td>Number of beds: </td>
								<td><input type='text' name='numBeds' /></td>
							</tr>
							<tr>
								<td>Room is currently available: </td>
								<td><input type='radio' name='available' value='true'/></td>
							</tr>
							<tr>
								<td>Room is currently cleaned: </td>
								<td><input type='radio' name='cleaned' value='true'/></td>
							</tr>
							<tr>
								<td>Special room features:</td>
							</tr>
							<tr>
								<td>En-suite Bathroom: </td>
								<td><input type='radio' name='onsuite' value='false'/></td>
							</tr>
							<tr>
								<td>Room has a television: </td>
								<td><input type='radio' name='tv' value='true'/></td>
							</tr>
							<tr>
								<td>Disability access: </td>
								<td><input type='radio' name='disability' value='false'/></td>
							</tr>
							<tr>
								<td>Elevator access: </td>
								<td><input type='radio' name='elevator' value='true'/></td>
							</tr>
							<tr>
								<td>Early-Bird wake-up call requested: </td>
								<td><input type='radio' name='ebirdcall' value='false'/></td>
							</tr>
							<tr>
								<td>Morning newspaper requested: </td>
								<td><input type='radio' name='emornpaper' value='false'/></td>
							</tr>


							<tr>
								<td></td>
								<td><input type='submit' value='Continue' /></td>
							</tr>
						</table>
					</form>
				</div>
<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<h2> WELCOME TO OHMS DEMONSTRATION WEB PAGE </h2>
					<h2> OHMS is an online hotel management system </h2>
					<div class="hotel_image">
						<img src="images/uploads/<%=room.roomType %>.png" />
					</div>
				</div>
<%@ include file="../footer.jsp" %>
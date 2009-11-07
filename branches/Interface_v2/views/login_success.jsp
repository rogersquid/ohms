<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Login successful</div>
					Welcome, <%= request.getAttribute("email") %>
				</div>
<%@ include file="footer.jsp" %>
<%@ include file="header.jsp" %>
<%@ include file="left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Register successful</div>
					Welcome, <%=request.getAttribute("firstname") %> <%=request.getAttribute("lastname") %>.
					<br />
					You may <a href='login.html'>login</a> with your e-mail address and password.
				</div>
				<div class='clear'><!-- --></div>
			</div>
<%@ include file="footer.jsp" %>
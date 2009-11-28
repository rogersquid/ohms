<%@ page import="models.database.*" %>
<%@ page import="models.messages.*" %>
<%@ include file="../header.jsp" %>
<%@ include file="../left_nav.jsp" %>
				<div id='content'>
					<div id='title'>Register successful</div>
					<% AccountMessage account = (AccountMessage)request.getAttribute("account"); %>
					Welcome, <%=account.firstName %> <%=account.lastName %>.
					<br />
					You may <a href='login.html'>login</a> with your e-mail address and password.
				</div>
<%@ include file="../footer.jsp" %>
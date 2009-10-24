<%@ page import="java.util.*" %>
<html>
  <head>
    <title>JSP 2.0 Expression Language - Basic Arithmetic</title>
  </head>
  <body>
    <h1>Test form</h1>
    <hr>
    This example shows how to use GET and POST using Servlets and JSP.
	<hr>
    <br>
    <table>
		<tr>
			<th>GET</th>
			<td>
					<form method='get' action=''>
						<input type='text' name='test_get' />
						<input type='submit' value='Submit' />
					</form>
			</td>
			<td>The text you enter will be passed in the URL</td>
		</tr>
		<tr>
			<th>POST</th>
			<td>
					<form method='post' action=''>
						<input type='text' name='test_post' />
						<input type='submit' value='Submit' />
					</form>
			</td>
			<td>The text you enter will be passed in the headers</td>
		</tr>
	</table>
	<% if(request.getAttribute("test_get") != null) { %>
		<hr>
		GET: <%= request.getAttribute("test_get") %><br>
	<% } %>
	<% if(request.getAttribute("test_post") != null) { %>
		<hr>
		POST: <%= request.getAttribute("test_post") %><br>
	<% } %>
  </body>
</html>
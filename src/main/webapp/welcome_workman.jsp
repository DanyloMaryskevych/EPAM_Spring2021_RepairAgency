<%--
  Created by IntelliJ IDEA.
  User: danyl
  Date: 12/05/2021
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<p>Hello, ${username}</p>
Your tasks for today:

</body>
</html>

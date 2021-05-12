<%--
  Created by IntelliJ IDEA.
  User: danyl
  Date: 12/05/2021
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<p>Hello, ${username}</p>
<p>Would you like to make some order?</p><br>

<form action="logout">
    <input class="m-4 btn btn-warning" type="submit" value="Logout">
</form>

</body>
</html>

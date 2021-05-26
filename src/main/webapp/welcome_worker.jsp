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

    if (session.getAttribute("session_id") != session.getId() || !session.getAttribute("role").equals("Worker")) {
        response.sendRedirect("index.jsp");
    }
%>

<%@ include file="header.jsp" %>

<p>Hello, ${username}</p>
<p>Your tasks for today:</p><br>

</body>
</html>

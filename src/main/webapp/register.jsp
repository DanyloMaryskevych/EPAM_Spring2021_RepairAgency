<%--
  Created by IntelliJ IDEA.
  User: danyl
  Date: 12/05/2021
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

<h2 class="m-3">Form</h2>

<form class="m-2" action="register" method="post">
    Enter login: <input type="text" name="login"><br>
    Enter password: <input type="password" name="password1"><br>
    Confirm password: <input type="password" name="password2"><br>
    <p>Would you like to register as:</p>
    <div>
        <input type="radio" name="role" value="Customer" id="customer">
        <label for="customer">Customer</label>

        <input type="radio" name="role" value="Workman" id="workman">
        <label for="workman">Workman</label>
    </div>
    <input type="submit" value="Sign up">
</form>

</body>
</html>

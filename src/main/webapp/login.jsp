<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<html lang="${cookie.get('lang')}">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
</head>
<body>

<%@ include file="header.jsp" %>

<div style="width: 100%" class="row justify-content-md-center mt-5">
    <div class="col col-lg-3">
        <h1 class="text-center mb-4"><fmt:message key="header.login"/></h1>
        <form action="login" method="post">

            <c:choose>
                <c:when test="${invalidUserError == null}">
                    <c:set value="none" var="display_login"/>
                </c:when>
                <c:otherwise>
                    <c:set value="block" var="display_login"/>
                </c:otherwise>
            </c:choose>
            <div style="display: ${display_login}">
                <h5 style="color: red"><fmt:message key="login.login_error"/></h5>
            </div>

            <label class="form-text text-muted" for="login"><fmt:message key="register.login"/></label>
            <input placeholder = "Enter Login" class="form-control" type="text" name="login" Id="login">

            <label class="mt-3 form-text text-muted" for="password"><fmt:message key="register.password"/></label>
            <input class="form-control" type="password" name="password"
                   id="password" placeholder="Enter Password">
            <hr>
            <div class="row">
                <input class="mt-3 ml-3 mr-3 col btn btn-success"
                       type="submit" value="<fmt:message key="header.login"/>">
            </div>

        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

</body>
</html>

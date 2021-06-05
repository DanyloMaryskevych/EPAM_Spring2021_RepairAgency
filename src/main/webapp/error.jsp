<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container w-100">
    <div class="row mt-5">
        <div class="col d-flex justify-content-center">
            <img src="https://www.airtract.com/Content/images/something-wrong.png" alt="error">
        </div>
    </div>

    <div class="row mt-3">
        <div class="col d-flex justify-content-center">
            <div style="font-weight: bold; font-size: xx-large">Oops! Something went wrong!</div>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col d-flex justify-content-center">
            <div style="font-size: large; text-align: center">Access denied. You do not have permission to perform <br> this action or access this resource.</div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

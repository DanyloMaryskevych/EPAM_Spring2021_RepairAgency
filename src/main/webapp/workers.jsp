<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <title>Workers</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
%>

<%@ include file="header.jsp" %>

<div class="container">

    <div class="row w-100 m-4">
        <div class="col-3">
            <fmt:message key="workers.sort"/> :
        </div>

        <div class="col-3">
            <a href="workers?sort_by=orders_amount">
                <button class="btn btn-secondary"><fmt:message key="orders"/></button>
            </a>
        </div>

        <div class="col-2">
            <a href="workers?sort_by=rating">
                <button class="btn btn-secondary"><fmt:message key="workers.rating"/></button>
            </a>
        </div>

    </div>
    <%--@elvariable id="bh_workers_list" type="com.example.Broken_Hammer.entity.Worker"--%>
    <c:forEach var="temp_worker" items="${bh_workers_list}">

        <div class="row w-75 m-4">
            <h4 class="col mb-0 d-flex align-items-center">
                    ${temp_worker.login}
            </h4>

            <h5 class="col mb-0 d-flex align-items-center">
                <fmt:message key="orders"/>: ${temp_worker.ordersAmount}
            </h5>

            <div class="col">
                <span style="font-size: 2em; color: gold"><i class="fas fa-star"></i></span>
                <c:choose>
                    <c:when test="${temp_worker.average == 0}">
                        <span style="font-size: 1.5em"><fmt:message key="workers.none"/></span>
                    </c:when>
                    <c:otherwise>
                        <span style="font-size: 2em">${temp_worker.average}</span>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>

    </c:forEach>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

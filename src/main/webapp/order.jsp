<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("id") != session.getId() || !session.getAttribute("role").equals("Customer")) {
        response.sendRedirect("index.jsp");
    }
%>
<%@ include file="header.jsp" %>

<%--@elvariable id="temp_order" type="com.example.Broken_Hammer.entity.Order"--%>

<div class="container">
    <div class="row mt-5 w-50">
        <div class="col d-flex align-items-center">
            <h4>Order # ${temp_order.id}</h4>
        </div>
        <div class="col">
            <h3>&quot;${temp_order.title}&quot;</h3>
        </div>
    </div>
    <hr class="w-50 m-0 mt-3">

    <div class="row w-50 mt-4">

        <div class="col">
            <div class="row">
                <div class="col">Performance status</div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_order.performanceStatus == 'Not started'}">
                            <c:set value="badge-secondary" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.performanceStatus == 'In work'}">
                            <c:set value="badge-warning" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.performanceStatus == 'Done'}">
                            <c:set value="badge-success" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.performanceStatus == 'Rejected'}">
                            <c:set value="badge-danger" var="badge_class"/>
                        </c:when>
                    </c:choose>
                    <span class="badge badge-pill ${badge_class}">${temp_order.performanceStatus}</span>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col">Payment status</div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_order.paymentStatus == 'Waiting for price'}">
                            <c:set value="badge-secondary" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.paymentStatus == 'Waiting for payment'}">
                            <c:set value="badge-warning" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.paymentStatus == 'Paid'}">
                            <c:set value="badge-success" var="badge_class"/>
                        </c:when>
                    </c:choose>
                    <span class="badge badge-pill ${badge_class}">${temp_order.paymentStatus}</span>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col">Worker</div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_worker == null}">
                            <span>Not specified</span>
                        </c:when>
                        <c:otherwise>${temp_worker}</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col">Price</div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_order.price == 0}">
                            <span class="badge badge-pill badge-secondary">Not specified</span>
                        </c:when>
                        <c:otherwise>${temp_order.price}$</c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>

    </div>
    <hr class="w-50 m-0 mt-3">

    <div class="row w-50 mt-4">
        <div class="col">
            <p class="font-italic">Description: </p>
            ${temp_order.description}
        </div>
    </div>

</div>



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

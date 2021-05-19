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

    <c:choose>
        <c:when test="${payment != null && payment == 'false'}">
            <h4 style="color:red;" class="mt-3">
                Your payment was rejected! Not enough money!
            </h4>
        </c:when>
    </c:choose>

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
                            <c:set value="visible" var="button_v"/>
                            <c:set value="invisible" var="image_v"/>
                        </c:when>
                        <c:when test="${temp_order.paymentStatus == 'Paid'}">
                            <c:set value="badge-success" var="badge_class"/>
                            <c:set value="d-none" var="button_v"/>
                            <c:set value="visible" var="image_v"/>
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
                            <span class="badge badge-pill badge-secondary">Not specified</span>
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
                        <c:otherwise>
                            ${temp_order.price}$
                            <button class="btn btn-success btn-sm ml-3 ${button_v}"
                                    data-toggle="modal" data-target="#payment">
                                Pay
                            </button>
                            <img width="22" height="22" class="ml-3 ${image_v}"
                                 src="data:image/png;base64,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"
                            >

                            <div class="modal fade" id="payment" tabindex="-1" role="dialog"
                                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered bd-example-modal-sm" role="document">
                                    <div class="modal-content">

                                        <form action="order" method="post">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">
                                                    Confirm payment: ${temp_order.price}$ ?
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>

                                            <div class="modal-footer">
                                                <input class="invisible" name="status" value="paid">
                                                <input class="invisible" name="price" value="${temp_order.price}">
                                                <input class="invisible" name="orderID" value="${temp_order.id}">
                                                <button class="btn btn-danger" data-dismiss="modal">No</button>
                                                <input type="submit" value="Yes" class="btn btn-success"/>
                                            </div>

                                        </form>


                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
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

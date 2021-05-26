<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link rel="stylesheet" href="CSS/rating.css">
    <link rel="stylesheet" href="CSS/order_prop.css">
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("session_id") != session.getId()) {
        response.sendRedirect("index.jsp");
    }
%>
<%@ include file="header.jsp" %>

<%--CONSTANTS--%>
<%--Roles--%>
<c:set value="1" var="admin"/>
<c:set value="2" var="customer"/>
<c:set value="3" var="worker"/>

<%--Statuses--%>
<c:set value="1" var="not_started_perf_stat"/>
<c:set value="2" var="in_work_perf_stat"/>
<c:set value="3" var="done_perf_stat"/>
<c:set value="4" var="rejected_perf_stat"/>

<c:set value="1" var="price_pay_stat"/>
<c:set value="2" var="payment_pay_stat"/>
<c:set value="3" var="paid_pay_stat"/>

<%--Other--%>
<c:set value="badge_class" var="badge_class_var"/>
<c:if test="${temp_order.performanceStatusId == rejected_perf_stat}">
    <c:set value="disabled" var="disable_button"/>
</c:if>
<%--@elvariable id="temp_order" type="com.example.Broken_Hammer.entity.OrderDTO"--%>
<%--@elvariable id="role_id" type="java.lang.Integer"--%>
<%--@elvariable id="expected_worker" type="java.lang.String"--%>
<%--@elvariable id="payment" type="java.lang.String"--%>

<div class="container">

    <%--Error payment message--%>
    <c:choose>
        <c:when test="${payment != null && payment == 'false'}">
            <h4 style="color:red;" class="mt-3">
                Your payment was rejected! Not enough money!
            </h4>
        </c:when>
    </c:choose>

    <%--Orders number and title--%>
    <div class="row mt-5 w-50">
        <div class="col d-flex align-items-center">
            <h4>Order # ${temp_order.id}</h4>
        </div>
        <div class="col">
            <h3>&quot;${temp_order.title}&quot;</h3>
        </div>
    </div>

    <hr class="w-50 m-0 mt-3">

    <%--Order Main Info--%>
    <div class="row w-50 mt-4">
        <div class="col">

            <%--Performance status--%>
            <div class="row">
                <div class="col">Performance status</div>
                <div class="col d-flex align-items-center justify-content-between">
                    <c:choose>
                        <c:when test="${temp_order.performanceStatusId == not_started_perf_stat}">
                            <c:set value="badge-secondary" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.performanceStatusId == in_work_perf_stat}">
                            <c:set value="badge-warning" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.performanceStatusId == done_perf_stat}">
                            <c:set value="badge-success" var="badge_class"/>
                            <c:if test="${role_id == customer}">
                                <c:set value="visible" var="feedback_button"/>
                            </c:if>
                        </c:when>
                        <c:when test="${temp_order.performanceStatusId == rejected_perf_stat}">
                            <c:set value="badge-danger" var="badge_class"/>
                        </c:when>
                    </c:choose>
                    <span class="badge badge-pill ${badge_class}">${temp_order.performanceStatus}</span>

                    <%--Start performing button--%>
                    <c:if test="${temp_order.performanceStatusId == not_started_perf_stat && role_id == worker}">
                        <jsp:include page="fragments/performance_button.jsp">
                            <jsp:param name="btn_class" value="btn-warning"/>
                            <jsp:param name="btn_text" value="Start"/>
                            <jsp:param name="title_text" value="Start working?"/>
                            <jsp:param name="orderID" value="${temp_order.id}"/>
                            <jsp:param name="status" value="performing"/>
                            <jsp:param name="perform_status" value="${in_work_perf_stat}"/>
                        </jsp:include>
                    </c:if>

                    <%--Done performing button--%>
                    <c:if test="${temp_order.performanceStatusId == in_work_perf_stat && role_id == worker}">
                        <jsp:include page="fragments/performance_button.jsp">
                            <jsp:param name="btn_class" value="btn-success"/>
                            <jsp:param name="btn_text" value="Done"/>
                            <jsp:param name="title_text" value="Finish working?"/>
                            <jsp:param name="orderID" value="${temp_order.id}"/>
                            <jsp:param name="status" value="performing"/>
                            <jsp:param name="perform_status" value="${done_perf_stat}"/>
                        </jsp:include>
                    </c:if>

                    <%--Reject performing button--%>
                    <c:if test="${role_id == admin && temp_order.paymentStatusId != paid_pay_stat
                                    && temp_order.performanceStatusId != rejected_perf_stat}">
                        <jsp:include page="fragments/performance_button.jsp">
                            <jsp:param name="btn_class" value="btn-danger"/>
                            <jsp:param name="btn_text" value="Reject"/>
                            <jsp:param name="title_text" value="Reject order?"/>
                            <jsp:param name="orderID" value="${temp_order.id}"/>
                            <jsp:param name="status" value="performing"/>
                            <jsp:param name="perform_status" value="${rejected_perf_stat}"/>
                        </jsp:include>
                    </c:if>
                </div>

            </div>

            <%--Payment status--%>
            <div class="row mt-3">
                <div class="col">Payment status</div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_order.paymentStatusId == price_pay_stat}">
                            <c:set value="badge-secondary" var="badge_class"/>
                        </c:when>
                        <c:when test="${temp_order.paymentStatusId == payment_pay_stat}">
                            <c:set value="badge-warning" var="badge_class"/>
                            <c:set value="visible" var="button_v"/>
                            <c:set value="invisible" var="image_v"/>
                        </c:when>
                        <c:when test="${temp_order.paymentStatusId == paid_pay_stat}">
                            <c:set value="badge-success" var="badge_class"/>
                            <c:set value="d-none" var="button_v"/>
                            <c:set value="visible" var="image_v"/>
                        </c:when>
                    </c:choose>
                    <span class="badge badge-pill ${badge_class}">${temp_order.paymentStatus}</span>
                </div>
            </div>

            <%--Worker--%>
            <div class="row mt-3">
                <div class="col">Worker</div>
                <div class="col d-flex align-items-center justify-content-between">
                    <c:choose>
                        <c:when test="${temp_order.workerName == null}">
                            <c:if test="${role_id == customer}">
                                <span class="badge badge-pill badge-secondary">Not specified</span>
                            </c:if>

                            <c:if test="${role_id == admin}">

                                <button class="btn btn-sm btn-info"
                                        data-toggle="modal" data-target="#set_worker" ${disable_button}>
                                    Set Worker
                                </button>

                                <div class="modal fade" id="set_worker" aria-labelledby="set_worker_aria" tabindex="-1"
                                     role="dialog">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="set_worker_aria">Set worker</h5>
                                            </div>

                                            <div class="modal-body">

                                                <c:if test="${temp_order.expectedWorkerID != 0}">
                                                    <div class="row mb-4">
                                                        <div class="col-4 d-flex align-items-center">
                                                            Expected worker:
                                                        </div>
                                                        <div class="col-3 d-flex align-items-center">
                                                                ${expected_worker}
                                                        </div>
                                                        <div class="col-5">
                                                            <form class="mb-0" method="post" action="order">
                                                                <label>
                                                                    <input class="invisible ghost" name="status"
                                                                           value="worker">
                                                                </label>
                                                                <label>
                                                                    <input class="invisible ghost" name="workerID"
                                                                           value="${temp_order.expectedWorkerID}">
                                                                </label>
                                                                <label>
                                                                    <input class="invisible ghost" name="orderID"
                                                                           value="${temp_order.id}">
                                                                </label>
                                                                <input class="btn btn-success" type="submit"
                                                                       value="Confirm">
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <form class="mb-0" action="order" method="post">
                                                    <div class="row">
                                                        <div class="col-4">
                                                            <div class="mb-3">
                                                                Choose worker
                                                            </div>
                                                        </div>

                                                        <div class="col-5">
                                                            <div class="input-group mb-3">
                                                                <label for="workers"></label>
                                                                <select class="custom-select" id="workers"
                                                                        name="workerID">
                                                                    <option selected value="0"></option>
                                                                        <%--@elvariable id="workers_list" type="java.util.List"--%>
                                                                    <c:forEach var="temp_worker"
                                                                               items="${workers_list}">
                                                                        <option value="${temp_worker.id}"> ${temp_worker.login} </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>

                                                        <div class="col-3">
                                                            <label>
                                                                <input class="invisible ghost" name="status" value="worker">
                                                            </label>
                                                            <label>
                                                                <input class="invisible ghost" name="orderID"
                                                                       value="${temp_order.id}">
                                                            </label>
                                                            <input type="submit" class="btn btn-success" value="Save">
                                                        </div>
                                                    </div>

                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>${temp_order.workerName}</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <%--Price--%>
            <div class="row mt-3">
                <div class="col">Price</div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_order.price == 0}">

                            <c:if test="${role_id == customer}">
                                <span class="badge badge-pill badge-secondary">Not specified</span>
                            </c:if>

                            <c:if test="${role_id == admin}">
                                <button class="btn btn-sm btn-success" data-toggle="modal"
                                        data-target="#price" ${disable_button}>
                                    Set Price
                                </button>

                                <div class="modal fade" id="price" tabindex="-1" role="dialog"
                                 aria-labelledby="price_aria" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="price_aria">Price</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body pb-0">
                                            <form method="post" action="order">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-4 d-flex align-items-center">
                                                            <label class="pl-3 m-0" for="price_set">Enter the price: </label>
                                                        </div>

                                                        <div class="col-3">
                                                            <input name="price" class="form-control" type="text" id="price_set">
                                                        </div>

                                                        <div class="col-2 d-flex align-items-center">
                                                            <span style="font-size: 2em; color: green">
                                                                <i class="fas fa-dollar-sign"></i>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="modal-footer pb-0">
                                                    <label>
                                                        <input class="invisible ghost" name="status" value="price">
                                                    </label>
                                                    <label>
                                                        <input class="invisible ghost" name="orderID"
                                                               value="${temp_order.id}">
                                                    </label>
                                                    <input type="submit" class="btn btn-primary" value="Save">
                                                </div>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            </c:if>

                        </c:when>
                        <c:otherwise>
                            ${temp_order.price}$

                            <%--Pay button--%>
                            <c:if test="${role_id == customer}">
                                <jsp:include page="fragments/performance_button.jsp">
                                    <jsp:param name="btn_class" value="btn-success ml-3 ${button_v}"/>
                                    <jsp:param name="btn_text" value="Pay"/>
                                    <jsp:param name="title_text" value="Confirm payment ${temp_order.price}$ ?"/>
                                    <jsp:param name="orderID" value="${temp_order.id}"/>
                                    <jsp:param name="status" value="paid"/>
                                    <jsp:param name="price" value="${temp_order.price}"/>
                                </jsp:include>
                            </c:if>

                            <%--Image--%>
                            <img style="width: 22px; height: 22px" class="ml-3 ${image_v}"
                                 src="data:image/png;base64,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" alt="hammer">

                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>
    </div>

    <hr class="w-50 m-0 mt-3">

    <%--Description--%>
    <div class="row w-50 mt-4">
        <div class="col">
            <p class="font-italic">Description: </p>
            ${temp_order.description}
        </div>
    </div>

    <%--Feedback--%>
    <div class="w-50">
        <c:if test="${feedback_button == 'visible' && temp_order.rating == 0}">
            <button class="btn btn-info mt-3" data-toggle="modal" data-target="#feedback">Send feedback</button>
        </c:if>
        <c:if test="${temp_order.rating > 0}">
            <hr class="mt-3">
            <div class="row">
                <div class="col">
                    <p class="font-italic mb-0">Feedback: </p>

                    <div class="row">
                        <div class="mt-2 mb-2 col-1 d-flex align-items-center">
                            <c:forEach begin="1" end="${temp_order.rating}" varStatus="loop">
                                <img width="35" height="35"
                                     src="https://truebluetour.com/wp-content/uploads/2017/10/PNGPIX-COM-Star-Vector-PNG-Transparent-Image.png"
                                     alt="star">
                            </c:forEach>
                        </div>
                    </div>
                    <blockquote class="blockquote">
                        <p class="mb-0">${temp_order.comment}</p>
                    </blockquote>

                </div>
            </div>

        </c:if>
    </div>

    <!--Feedback Modal Window-->
    <div class="modal fade" id="feedback" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="exampleModalLongTitle">Feedback</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="order" method="post">
                        <h5>Please, evaluate your order quality</h5>
                        <div class="rating">
                            <input type="radio" name="rating" value="5" id="5">
                            <label for="5">☆</label>

                            <input type="radio" name="rating" value="4" id="4">
                            <label for="4">☆</label>

                            <input type="radio" name="rating" value="3" id="3">
                            <label for="3">☆</label>

                            <input type="radio" name="rating" value="2" id="2">
                            <label for="2">☆</label>

                            <input type="radio" name="rating" value="1" id="1">
                            <label for="1">☆</label>
                        </div>

                        <label for="comment">Comment:</label>
                        <textarea class="form-control" name="comment" id="comment"></textarea>

                        <hr>
                        <label>
                            <input class="invisible" name="status" value="feedback">
                        </label>
                        <label>
                            <input class="invisible" name="orderID" value="${temp_order.id}">
                        </label>
                        <label>
                            <input class="invisible" name="workerID" value="${temp_order.workerID}">
                        </label>

                        <div class="row">
                            <input type="submit" class="mt-3 ml-3 mr-3 col btn btn-success" value="Send">
                        </div>
                    </form>

                </div>

            </div>
        </div>
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

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
<c:set value="${param.get('payment_confirm')}" var="payment_confirm_param"/>

<%--Other--%>
<c:set value="badge_class" var="badge_class_var"/>
<c:if test="${temp_order.performanceStatusId == rejected_perf_stat}">
    <c:set value="disabled" var="disable_button"/>
</c:if>
<%--@elvariable id="temp_order" type="com.example.Broken_Hammer.entity.OrderDTO"--%>
<%--@elvariable id="role_id" type="java.lang.Integer"--%>
<%--@elvariable id="expected_worker" type="java.lang.String"--%>
<%--@elvariable id="payment_confirm" type="java.lang.String"--%>

<div class="container">

    <%--Error payment message--%>
    <c:choose>
        <c:when test="${payment_confirm_param != null && payment_confirm_param == 'false'}">
            <h4 style="color:red;" class="mt-3">
                <fmt:message key="order.payment_error"/>
            </h4>
        </c:when>
    </c:choose>

    <%--Orders number and title--%>
    <div class="row mt-5 w-50">
        <div class="col d-flex align-items-center">
            <h4><fmt:message key="order.temp_order"/> # ${temp_order.id}</h4>
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
                <div class="col"><fmt:message key="orders.performance_status"/></div>
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
                            <jsp:param name="btn_text" value="order.start_button"/>
                            <jsp:param name="title_text" value="order.start_button_title"/>
                            <jsp:param name="orderID" value="${temp_order.id}"/>
                            <jsp:param name="status" value="performing"/>
                            <jsp:param name="perform_status" value="${in_work_perf_stat}"/>
                        </jsp:include>
                    </c:if>

                    <%--Done performing button--%>
                    <c:if test="${temp_order.performanceStatusId == in_work_perf_stat && role_id == worker}">
                        <jsp:include page="fragments/performance_button.jsp">
                            <jsp:param name="btn_class" value="btn-success"/>
                            <jsp:param name="btn_text" value="order.done_button"/>
                            <jsp:param name="title_text" value="order.done_button_title"/>
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
                            <jsp:param name="btn_text" value="order.reject_button"/>
                            <jsp:param name="title_text" value="order.reject_button_title"/>
                            <jsp:param name="orderID" value="${temp_order.id}"/>
                            <jsp:param name="status" value="performing"/>
                            <jsp:param name="perform_status" value="${rejected_perf_stat}"/>
                        </jsp:include>
                    </c:if>
                </div>

            </div>

            <%--Payment status--%>
            <div class="row mt-3">
                <div class="col"><fmt:message key="orders.payment_status"/></div>
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
                <div class="col"><fmt:message key="orders.worker"/></div>
                <div class="col d-flex align-items-center justify-content-between">
                    <c:choose>
                        <c:when test="${temp_order.workerName == null}">
                            <c:if test="${role_id == customer}">
                                <span class="badge badge-pill badge-secondary"><fmt:message
                                        key="order.not_specified"/></span>
                            </c:if>

                            <c:if test="${role_id == admin}">

                                <button class="btn btn-sm btn-info"
                                        data-toggle="modal" data-target="#set_worker" ${disable_button}>
                                    <fmt:message key="order.set_worker"/>
                                </button>

                                <div class="modal fade" id="set_worker" aria-labelledby="set_worker_aria" tabindex="-1"
                                     role="dialog">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="set_worker_aria"><fmt:message
                                                        key="orders.worker"/></h5>
                                            </div>

                                            <div class="modal-body">

                                                <form class="needs-validation mb-0" novalidate
                                                      action="order" method="post">

                                                    <label for="workers">
                                                        <small>
                                                            <fmt:message key="order.choose_worker_label"/>
                                                        </small>
                                                    </label>

                                                    <div class="row mt-3">
                                                        <div class="col-6">
                                                            <select class="custom-select mb-3" id="workers"
                                                            name="workerID" required>
                                                        <c:choose>
                                                            <c:when test="${temp_order.expectedWorkerID == 0}">
                                                                <c:set value="" var="value"/>
                                                                <c:set value="&mdash;" var="text"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set value="${temp_order.expectedWorkerID}" var="value"/>
                                                                <c:set value="${expected_worker}" var="text"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <option value="${value}">
                                                                ${text}
                                                        </option>
                                                            <%--@elvariable id="workers_list" type="java.util.List"--%>
                                                        <c:forEach var="temp_worker"
                                                                   items="${workers_list}">
                                                            <c:if test="${temp_order.expectedWorkerID != temp_worker.id}">
                                                                <option value="${temp_worker.id}">${temp_worker.login}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                            <div class="invalid-feedback mb-2">
                                                                <fmt:message key="order.choose_worker_error"/>
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div class="modal-footer pb-0">
                                                        <div class="row">
                                                            <label>
                                                                <input class="invisible ghost" name="status"
                                                                       value="worker">
                                                            </label>
                                                            <label>
                                                                <input class="invisible ghost" name="orderID"
                                                                       value="${temp_order.id}">
                                                            </label>
                                                            <input type="submit" class="btn btn-success"
                                                                   value="<fmt:message key="orders.save"/>">
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
                <div class="col"><fmt:message key="orders.price"/></div>
                <div class="col d-flex align-items-center">
                    <c:choose>
                        <c:when test="${temp_order.price == 0}">

                            <c:if test="${role_id == customer}">
                                <span class="badge badge-pill badge-secondary"><fmt:message
                                        key="order.not_specified"/></span>
                            </c:if>

                            <c:if test="${role_id == admin}">
                                <button class="btn btn-sm btn-success" data-toggle="modal"
                                        data-target="#price" ${disable_button}>
                                    <fmt:message key="order.set_price"/>
                                </button>

                                <div class="modal fade" id="price" tabindex="-1" role="dialog"
                                     aria-labelledby="price_aria" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="price_aria"><fmt:message
                                                        key="orders.price"/></h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body pb-0">
                                                <form class="needs-validation" novalidate method="post" action="order">

                                                    <label for="price_set">
                                                        <fmt:message key="order.enter_price"/>
                                                    </label>

                                                    <input id="price_set" class="form-control mb-3" type="text"
                                                           name="price"
                                                           placeholder="<fmt:message key="orders.price"/>" required
                                                           pattern="^[1-9][0-9]*$">

                                                    <div style="text-align: left" class="invalid-feedback mb-2">
                                                        <fmt:message key="orders.sum_error"/>
                                                    </div>

                                                    <div class="modal-footer pb-0">
                                                        <div class="row w-100">
                                                            <label>
                                                                <input class="invisible ghost" name="status"
                                                                       value="price">
                                                            </label>
                                                            <label>
                                                                <input class="invisible ghost" name="orderID"
                                                                       value="${temp_order.id}">
                                                            </label>
                                                            <input type="submit" class="col btn btn-success"
                                                                   value="<fmt:message key="orders.save"/>">
                                                        </div>
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
                            <c:if test="${role_id == customer && temp_order.performanceStatusId != rejected_perf_stat}">
                                <jsp:include page="fragments/performance_button.jsp">
                                    <jsp:param name="btn_class" value="btn-success ml-3 ${button_v}"/>
                                    <jsp:param name="btn_text" value="order.pay_button"/>
                                    <jsp:param name="title_text" value="order.pay_button_title"/>
                                    <jsp:param name="price_title" value=" ${temp_order.price}$ ?"/>
                                    <jsp:param name="orderID" value="${temp_order.id}"/>
                                    <jsp:param name="status" value="paid"/>
                                    <jsp:param name="price" value="${temp_order.price}"/>
                                </jsp:include>
                            </c:if>

                            <%--Image--%>
                            <img style="width: 22px; height: 22px" class="ml-3 ${image_v}"
                                 src="data:image/png;base64,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"
                                 alt="hammer">

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
            <p class="font-italic"><fmt:message key="orders.description"/>: </p>
            ${temp_order.description}
        </div>
    </div>

    <%--Feedback--%>
    <div class="w-50">
        <c:if test="${feedback_button == 'visible' && temp_order.rating == 0}">
            <button class="btn btn-info mt-3" data-toggle="modal" data-target="#feedback"><fmt:message
                    key="order.send_feedback"/></button>
        </c:if>
        <c:if test="${temp_order.rating > 0}">
            <hr class="mt-3">
            <div class="row">
                <div class="col">
                    <p class="font-italic mb-0"><fmt:message key="order.feedback"/>: </p>

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
                    <h4 class="modal-title" id="exampleModalLongTitle"><fmt:message key="order.feedback"/></h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form class="needs-validation" novalidate action="order" method="post">
                        <h5><fmt:message key="order.quality_title"/></h5>
                        <div class="rating">
                            <input type="radio" name="rating" value="5" id="5" required>
                            <label for="5">☆</label>

                            <input type="radio" name="rating" value="4" id="4" required>
                            <label for="4">☆</label>

                            <input type="radio" name="rating" value="3" id="3" required>
                            <label for="3">☆</label>

                            <input type="radio" name="rating" value="2" id="2" required>
                            <label for="2">☆</label>

                            <input type="radio" name="rating" value="1" id="1" required>
                            <label for="1">☆</label>
                        </div>


                        <label for="comment"><fmt:message key="order.comment"/>:</label>
                        <textarea class="form-control" name="comment" id="comment"></textarea>

                        <hr>
                        <label>
                            <input class="invisible ghost" name="status" value="feedback">
                        </label>
                        <label>
                            <input class="invisible ghost" name="orderID" value="${temp_order.id}">
                        </label>
                        <label>
                            <input class="invisible ghost" name="workerID" value="${temp_order.workerID}">
                        </label>

                        <div class="row">
                            <input type="submit" class="mt-3 ml-3 mr-3 col btn btn-success"
                                   value="<fmt:message key="order.send"/>">
                        </div>
                    </form>

                </div>

            </div>
        </div>
    </div>

</div>

<%@ include file="footer.jsp" %>

<script rel="script" src="JS/fieldsValidation.js"></script>

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

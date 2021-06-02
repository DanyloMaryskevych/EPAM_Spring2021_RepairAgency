<%--@elvariable id="sort_param" type="java.lang.String"--%>
<%--@elvariable id="order_param" type="java.lang.String"--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link rel="stylesheet" href="CSS/table.css">
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("session_id") != session.getId()) {
        response.sendRedirect("index.jsp");
    }
%>

<%@ include file="header.jsp" %>
<%--@elvariable id="orders_list" type="com.example.Broken_Hammer.entity.OrderDTO"--%>

<c:set value="1" var="admin"/>
<c:set value="2" var="customer"/>
<c:set value="3" var="worker"/>

<%--Parameters--%>
<c:set value="page" var="page_param"/>
<c:set value="sort" var="sort_param"/>
<c:set value="order" var="order_param"/>
<c:set value="performance" var="performance_param"/>
<c:set value="payment" var="payment_param"/>
<c:set value="worker" var="worker_param"/>

<%--@elvariable id="role_id" type="java.lang.Integer"--%>
<div style="min-height: 60%; min-width: 85%" class="container">

    <c:if test="${role_id == admin}">
        <c:url value="admin" var="filter_link">
            <c:param name="${page_param}" value="${param.get(page_param)}"/>
            <c:param name="${sort_param}" value="${param.get(sort_param)}"/>
            <c:param name="${order_param}" value="${param.get(order_param)}"/>
            <c:if test="${param.get(performance_param) != null}">
                <c:set value="disabled" var="dis_perform"/>
                <c:param name="${performance_param}" value="${param.get(performance_param)}"/>
            </c:if>
            <c:if test="${param.get(payment_param) != null}">
                <c:set value="disabled" var="dis_payment"/>
                <c:param name="${payment_param}" value="${param.get(payment_param)}"/>
            </c:if>
            <c:if test="${param.get(worker_param) != null}">
                <c:set value="disabled" var="dis_worker"/>
                <c:param name="${worker_param}" value="${param.get(worker_param)}"/>
            </c:if>
        </c:url>
    </c:if>

    <c:if test="${role_id == customer}">
        <div class="m-2">
            <button class="btn btn-success" data-toggle="modal" data-target="#new_order"><fmt:message
                    key="orders.new_order"/></button>
        </div>

        <%--New order modal window--%>
        <div class="modal fade" id="new_order" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">

                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="orders.new_order"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body pb-0">
                        <form action="order" method="post" class="needs-validation" novalidate>
                            <div class="form-group">
                                <label class="form-text text-muted" for="title"><fmt:message
                                        key="orders.title"/></label>
                                <input type="text" class="form-control" id="title" name="title" required>
                                <div class="invalid-feedback">
                                    <fmt:message key="orders.title_error"/>
                                </div>

                                <label class="mt-2 form-text text-muted" for="description"><fmt:message
                                        key="orders.description"/></label>
                                <textarea class="form-control" id="description" name="description" required></textarea>
                                <div class="invalid-feedback">
                                    <fmt:message key="orders.description_error"/>
                                </div>

                                <div class="mt-5">
                                    <label for="workers"><fmt:message key="orders.expected_worker"/></label>
                                    <small class="form-text text-muted mb-1"><fmt:message
                                            key="orders.expected_worker_message"/></small>

                                    <div class="row w-50">
                                        <div class="col">
                                            <div class="input-group mb-3">
                                                <select class="custom-select" id="workers" name="expected_worker_id">
                                                    <option selected value="0"><fmt:message
                                                            key="orders.choose"/></option>
                                                        <%--@elvariable id="workers_list" type="java.util.List"--%>
                                                    <c:forEach var="temp_worker" items="${workers_list}">
                                                        <option value="${temp_worker.id}"> ${temp_worker.login} </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>

                            <div class="modal-footer pb-0">
                                <label>
                                    <input class="invisible" name="status" value="new_order">
                                </label>
                                <input class="btn btn-success" type="submit" value="<fmt:message key="orders.save"/>">
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </c:if>

    <div class="row">

        <%--Filters--%>
        <div class="col-2 mt-4">
            <c:if test="${role_id == admin}">
                <h3 class="mb-2"><fmt:message key="orders.filters"/></h3>
                <c:url value="admin" var="clear_all">
                    <c:param name="${page_param}" value="${param.get(page_param)}"/>
                    <c:param name="${sort_param}" value="${param.get(sort_param)}"/>
                    <c:param name="${order_param}" value="${param.get(order_param)}"/>
                </c:url>
                <a class="mb-4 p-0 btn" href="${clear_all}">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-info"><fmt:message key="orders.clear_all"/></button>
                </a>

                <%--Performance status--%>
                <h4 class="mb-4"><fmt:message key="orders.performance_status"/></h4>
                <a class="p-0 btn ${dis_perform}" href="${filter_link}&performance=1">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.not_started"/></button>
                </a>
                <a class="p-0 btn ${dis_perform}" href="${filter_link}&performance=2">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.in_work"/></button>
                </a>
                <a class="p-0 btn ${dis_perform}" href="${filter_link}&performance=3">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.done"/></button>
                </a>
                <a class="p-0 btn ${dis_perform}" href="${filter_link}&performance=4">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.rejected"/></button>
                </a>

                <%--Payment status--%>
                <h4 class="mt-4 mb-4"><fmt:message key="orders.payment_status"/></h4>
                <a class="p-0 btn ${dis_payment}" href="${filter_link}&payment=1">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.waiting_for_price"/></button>
                </a>
                <a class="p-0 btn ${dis_payment}" href="${filter_link}&payment=2">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.waiting_for_payment"/></button>
                </a>
                <a class="p-0 btn ${dis_payment}" href="${filter_link}&payment=3">
                    <button type="button" class="mt-1 btn btn-sm btn-outline-secondary"><fmt:message key="orders.paid"/></button>
                </a>

                <%--Workers--%>
                <h4 class="mt-4 mb-4"><fmt:message key="orders.workers"/></h4>
                <%--@elvariable id="workers_list" type="java.util.List"--%>
                <c:forEach var="worker_for_filter" items="${workers_list}">
                    <a class="p-0 btn ${dis_worker}" href="${filter_link}&worker=${worker_for_filter.id}">
                        <button type="button" class="mt-1 btn btn-sm btn-outline-secondary">${worker_for_filter.login}</button>
                    </a>
                </c:forEach>
            </c:if>
        </div>

        <div class="col-10">
            <div class="m-4 w-75">
                <div class="row">
                    <div class="col">
                        <h2 class="mb-3"><fmt:message key="orders"/></h2>
                    </div>
                </div>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th><fmt:message key="orders.title"/></th>
                        <c:if test="${role_id == admin}">
                            <th>
                                <fmt:message key="orders.date"/>
                                <jsp:include page="fragments/sorting_arrows.jsp">
                                    <jsp:param name="sort" value="date"/>
                                </jsp:include>
                            </th>
                        </c:if>
                        <th>
                            <fmt:message key="orders.performance_status"/>
                            <c:if test="${role_id == admin}">
                                <jsp:include page="fragments/sorting_arrows.jsp">
                                    <jsp:param name="sort" value="performance_status"/>
                                </jsp:include>
                            </c:if>
                        </th>
                        <th>
                            <fmt:message key="orders.payment_status"/>
                            <c:if test="${role_id == admin}">
                                <jsp:include page="fragments/sorting_arrows.jsp">
                                    <jsp:param name="sort" value="payment_status"/>
                                </jsp:include>
                            </c:if>
                        </th>
                        <th>
                            <fmt:message key="orders.price"/>
                            <c:if test="${role_id == admin}">
                                <jsp:include page="fragments/sorting_arrows.jsp">
                                    <jsp:param name="sort" value="price"/>
                                </jsp:include>
                            </c:if>
                        </th>
                        <c:if test="${role_id == admin}">
                            <th><fmt:message key="orders.worker"/></th>
                        </c:if>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="order" items="${orders_list}">
                        <tr>

                            <c:url var="orderLink" value="order">
                                <c:param name="orderID" value="${order.id}"/>
                            </c:url>

                            <td>
                                <a style="text-decoration: none" href="${orderLink}">
                                    <button class="btn btn-light">
                                            ${order.title}
                                    </button>
                                </a>
                            </td>
                            <c:if test="${role_id == admin}">
                                <td>${order.date}</td>
                            </c:if>
                            <td>${order.performanceStatus}</td>
                            <td>${order.paymentStatus}</td>
                            <c:choose>
                                <c:when test="${order.price == 0}">
                                    <td>&mdash;</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${order.price} $</td>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${role_id == admin}">
                                <c:choose>
                                    <c:when test="${order.workerName == null}">
                                        <td>&mdash;</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${order.workerName}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<nav aria-label="Page navigation example">
    <c:choose>
        <c:when test="${role_id == customer || role_id == worker}">
            <c:set value="profile" var="servlet"/>
        </c:when>

        <c:when test="${role_id == admin}">
            <c:set value="admin" var="servlet"/>
        </c:when>
    </c:choose>
    <ul class="mt-2 pagination justify-content-center">
        <%--@elvariable id="pages" type="java.lang.Integer"--%>

        <%--Control current page--%>
        <c:set value="${param.get('page')}" var="current_page"/>

        <%--First page--%>
        <jsp:include page="fragments/pagination.jsp">
            <jsp:param name="servlet" value="${servlet}"/>
            <jsp:param name="page" value="1"/>
            <jsp:param name="name" value="orders.first"/>
        </jsp:include>

        <%--Disable 'Previous' page--%>
        <c:if test="${current_page == 1}">
            <c:set value="disabled" var="disabled_previous"/>
        </c:if>

        <%--Previous page--%>
        <jsp:include page="fragments/pagination.jsp">
            <jsp:param name="disable_pr" value="${disabled_previous}"/>
            <jsp:param name="servlet" value="${servlet}"/>
            <jsp:param name="page" value="${current_page - 1}"/>
            <jsp:param name="name" value="orders.previous"/>
        </jsp:include>

        <%--All pages--%>
        <c:forEach begin="1" end="${pages}" varStatus="loop">
            <jsp:include page="fragments/pagination.jsp">
                <jsp:param name="servlet" value="${servlet}"/>
                <jsp:param name="page" value="${loop.index}"/>
                <jsp:param name="name" value="${loop.index}"/>
            </jsp:include>
        </c:forEach>

        <%--Disable 'Next' page--%>
        <c:if test="${current_page == pages}">
            <c:set value="disabled" var="disabled_next"/>
        </c:if>

        <%--Next page--%>
        <jsp:include page="fragments/pagination.jsp">
            <jsp:param name="disable_next" value="${disabled_next}"/>
            <jsp:param name="servlet" value="${servlet}"/>
            <jsp:param name="page" value="${current_page + 1}"/>
            <jsp:param name="name" value="orders.next"/>
        </jsp:include>

        <%--Last page--%>
        <jsp:include page="fragments/pagination.jsp">
            <jsp:param name="servlet" value="${servlet}"/>
            <jsp:param name="page" value="${pages}"/>
            <jsp:param name="name" value="orders.last"/>
        </jsp:include>
    </ul>
</nav>

<%@ include file="footer.jsp" %>

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            const forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            const validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("id") != session.getId()) {
        response.sendRedirect("index.jsp");
    }
%>
<%@ include file="header.jsp" %>
<%--@elvariable id="orders_list" type="com.example.Broken_Hammer.entity.Order"--%>

<div style="min-height: 60%; min-width: 75%" class="container">

    <%--@elvariable id="role" type="java.lang.String"--%>
    <c:if test="${role == 'Customer'}">
        <div class="m-2">
            <button class="btn btn-success" data-toggle="modal" data-target="#new_order">New order</button>
        </div>

        <%--New order modal window--%>
        <div class="modal fade" id="new_order" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">

                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title">New Order</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body pb-0">
                        <form action="order" method="post" class="needs-validation" novalidate>
                            <div class="form-group">
                                <label class="form-text text-muted" for="title">Title</label>
                                <input type="text" class="form-control" id="title" name="title" required>
                                <div class="invalid-feedback">
                                    Please provide some title
                                </div>

                                <label class="mt-2 form-text text-muted" for="description">Description</label>
                                <textarea class="form-control" id="description" name="description" required></textarea>
                                <div class="invalid-feedback">
                                    Please provide some description
                                </div>

                                <div class="mt-5">
                                    <label for="workers">Expected worker (optional)</label>
                                    <small class="form-text text-muted mb-1">You can choose the worker you
                                        recommend to do your task</small>

                                    <div class="row w-50">
                                        <div class="col">
                                            <div class="input-group mb-3">
                                                <select class="custom-select" id="workers" name="expected_worker">
                                                    <option selected value="0">Choose...</option>
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
                                <input class="invisible" name="status" value="new_order">
                                <input class="btn btn-success" type="submit" value="Save">
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </c:if>

    <div class="m-4 w-75">
        <h5>My Orders</h5>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Performance status</th>
                <th>Payment status</th>
                <th>Price</th>
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
                    <td>${order.date}</td>
                    <td>${order.performanceStatus}</td>
                    <td>${order.paymentStatus}</td>
                    <c:choose>
                        <c:when test="${order.price == 0}">
                            <td>Not specified</td>
                        </c:when>
                        <c:otherwise>
                            <td>${order.price} $</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<nav aria-label="Page navigation example">
    <ul class="mt-2 pagination justify-content-center">

        <%--Control current page--%>
        <c:set value="${param.get('page')}" var="current_page"/>

        <%--First page--%>
        <li class="page-item"><a class="page-link" href="profile?page=1">First</a></li>

        <%--Disable 'Previous' page--%>
        <c:if test="${current_page == 1}">
            <c:set value="disabled" var="disabled_previous"/>
        </c:if>

        <%--Previous page--%>
        <li class="page-item ${disabled_previous}">
            <a class="page-link" href="profile?page=${current_page - 1}">Previous</a>
        </li>

        <%--All pages--%>
        <c:forEach begin="1" end="${pages}" varStatus="loop">
            <li class="page-item"><a class="page-link" href="profile?page=${loop.index}">${loop.index}</a></li>
        </c:forEach>

        <%--Disable 'Next' page--%>
        <c:if test="${current_page == pages}">
            <c:set value="disabled" var="disabled_next"/>
        </c:if>

        <%--Next page--%>
        <li class="page-item ${disabled_next}">
            <a class="page-link" href="profile?page=${current_page + 1}">Next</a>
        </li>

        <%--Last page--%>
        <li class="page-item"><a class="page-link" href="profile?page=${pages}">Last</a></li>
    </ul>
</nav>

<%@ include file="footer.jsp" %>

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function (form) {
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

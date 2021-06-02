<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<button class="btn btn-sm ${param.btn_class}"
        data-toggle="modal" data-target=".bd-example-modal-sm">
    <fmt:message key="${param.btn_text}"/>
</button>

<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog"
     aria-labelledby="status_aria" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
        <div class="modal-content">

            <div class="modal-header">
                <h4 class="modal-title" id="status_aria"><fmt:message key="${param.title_text}"/>${param.price_title}</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-footer">
                <form action="order" method="post">
                    <label><input class="invisible ghost" name="orderID" value="${param.orderID}"></label>
                    <label><input class="invisible ghost" name="status" value="${param.status}"></label>

                    <%--Parameners, depends on status--%>
                    <label><input class="invisible ghost" name="perform_status" value="${param.perform_status}"></label>
                    <label><input class="invisible ghost" name="price" value="${param.price}"></label>

                    <input type="submit" class="btn btn-success" value="<fmt:message key="order.yes"/>">
                </form>
            </div>
        </div>
    </div>
</div>
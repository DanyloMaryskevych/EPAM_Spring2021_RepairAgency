<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<c:url value="${param.servlet}" var="link">
    <c:param name="page" value="${param.page}"/>

    <c:if test="${param.servlet == 'admin'}">
        <c:param name="sort" value="${param.get('sort')}"/>
        <c:param name="order" value="${param.get('order')}"/>
        <c:if test="${param.get('performance') != null}">
            <c:param name="performance" value="${param.get('performance')}"/>
        </c:if>
        <c:if test="${param.get('payment') != null}">
            <c:param name="payment" value="${param.get('payment')}"/>
        </c:if>
        <c:if test="${param.get('worker') != null}">
            <c:param name="worker" value="${param.get('worker')}"/>
        </c:if>

    </c:if>
</c:url>

<c:catch var="exception">
    <c:set value="${param.name * 1}" var="numberChecker"/>
</c:catch>

<li class="page-item ${param.disable_pr} ${param.disable_next}">
    <a class="page-link" href="${link}">
        <c:choose>
            <c:when test="${numberChecker > 0}">
                ${param.name}
            </c:when>
            <c:otherwise>
                <fmt:message key="${param.name}"/>
            </c:otherwise>
        </c:choose>
    </a>
</li>
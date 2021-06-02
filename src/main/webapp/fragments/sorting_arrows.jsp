<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="text-decoration: none" var="none_line"/>
<c:set value="color: yellowgreen" var="color"/>

<c:url value="admin" var="big_url">
    <c:param name="page" value="1"/>
    <c:param name="sort" value="${param.sort}"/>
    <c:if test="${param.get('performance') != null}">
        <c:param name="performance" value="${param.get('performance')}"/>
    </c:if>
    <c:if test="${param.get('payment') != null}">
        <c:param name="payment" value="${param.get('payment')}"/>
    </c:if>
    <c:if test="${param.get('worker') != null}">
        <c:param name="worker" value="${param.get('worker')}"/>
    </c:if>
</c:url>

<a style="${none_line}; ${color}" href="${big_url}&order=asc">
    <span class="span_tag">&uarr;</span>
</a>
<a style="${none_line}; ${color}" href="${big_url}&order=desc">
    <span class="span_tag">&darr;</span>
</a>
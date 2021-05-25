<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="${param.servlet}" var="link">
  <c:param name="page" value="${param.page}"/>

  <c:if test="${param.servlet == 'admin'}">
    <c:param name="sort" value="${param.get('sort')}"/>
    <c:param name="order" value="${param.get('order')}"/>
  </c:if>
</c:url>

<li class="page-item ${param.disable_pr} ${param.disable_next}">
  <a class="page-link" href="${link}">${param.name}</a>
</li>
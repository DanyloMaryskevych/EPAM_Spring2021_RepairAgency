<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="text-decoration: none" var="none_line"/>
<c:set value="color: yellowgreen" var="color"/>

<a style="${none_line}; ${color}" href="admin?page=1&sort=${param.sort}&order=asc">
    <span class="span_tag">&uarr;</span>
</a>
<a style="${none_line}; ${color}" href="admin?page=1&sort=${param.sort}&order=desc">
    <span class="span_tag">&darr;</span>
</a>
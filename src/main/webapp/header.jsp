<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<div class="w-100">
    <!--Nav bar-->
    <nav style="margin: 0" class="navbar navbar-expand navbar-light bg-light"> <!--'fixed-top'-->
        <div style="width: 100%" class="row justify-content-between">

            <!--Left elements-->
            <div class="col">
                <div class="d-flex justify-content-start">

                    <!--Links-->
                    <div class="w-auto my-auto d-none d-sm-flex">
                        <ul class="navbar-nav mr-auto">
                            <li style="margin-left: 5px" class="nav-item active">
                                <a class="nav-link btn btn-light" href="home"><fmt:message key="header.home"/></a>
                            </li>
                            <li style="margin-left: 5px" class="nav-item active">
                                <a class="nav-link btn btn-light" href="workers?sort_by=orders_amount">BH <fmt:message
                                        key="header.workers"/></a>
                            </li>
                            <%--@elvariable id="session_id" type="java.lang.String"--%>
                            <c:if test="${session_id != null}">
                                <c:choose>
                                    <c:when test="${role_id == 1}">
                                        <c:set value="admin?page=1&sort=date&order=desc" var="link"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set value="profile?page=1" var="link"/>
                                    </c:otherwise>
                                </c:choose>
                                <li style="margin-left: 5px" class="nav-item active">
                                    <a class="nav-link btn btn-light" href="${link}">
                                        <fmt:message key="orders"/>
                                    </a>
                                </li>
                            </c:if>

                        </ul>
                    </div>

                </div>
            </div>

            <!-- Center elements -->
            <div class="col">
                <div class="d-flex justify-content-center">
                    <h1 class="mt-2" style="font-family: 'Akaya Telivigala', cursive; font-size: 36px">Broken
                        Hammer</h1>
                </div>
            </div>

            <!-- Right elements -->
            <div class="col">

                <div class="d-flex justify-content-between">
                    <span>
                        <span style="cursor: pointer" onclick="setCookie('en')">
                            <img width="25" height="20"
                                 src="https://findicons.com/files/icons/281/flag_3/256/united_kingdom_flag.png">
                        </span>
                        <span style="cursor: pointer" onclick="setCookie('ua')">
                            <img width="25" height="20"
                                 src="https://iconarchive.com/download/i109325/wikipedia/flags/UA-Ukraine-Flag.ico">
                        </span>
                    </span>
                    <c:choose>
                        <%--@elvariable id="login" type="java.lang.String"--%>
                        <c:when test="${login != null}">

                            <div class="row align-items-center w-75">
                                <div class="col-8">
                                    <p class="m-0"><fmt:message key="header.hello"/>, ${login}!</p>
                                    <c:if test="${role_id == 2}"><%--@elvariable id="balance" type="java.lang.Integer"--%>
                                        <p class="m-0"><fmt:message key="header.balance"/> ${balance}$</p>
                                    </c:if>
                                </div>

                                <div class="col-2">
                                    <div class="d-flex">


                                        <button class="btn btn-secondary" data-toggle="modal"
                                                data-target="#logout">
                                            <fmt:message key="header.logout"/>
                                        </button>

                                        <div class="modal fade" id="logout" tabindex="-1" role="dialog">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title"><fmt:message key="header.confirm_logout"/></h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <form class="m-0" action="logout">
                                                            <div class="form-group">
                                                                <input class="btn btn-success" type="submit" value="<fmt:message key="order.yes"/>">
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </c:when>
                        <c:otherwise>
                            <div class="w-auto my-auto d-none d-sm-flex">
                                <ul class="navbar-nav mr-auto">

                                    <li style="margin-left: 5px" class="nav-item active">
                                        <a style="text-decoration:none; font-size: 1rem"
                                           class="nav-link mr-4 btn btn-sm" href="login">
                                            <fmt:message key="header.login"/>
                                        </a>
                                    </li>

                                    <li style="margin-left: 5px" class="nav-item active">
                                        <a style="text-decoration: none" href="register">
                                            <button style="color: aliceblue; font-weight: bold; font-size: 1rem; border: none"
                                                    type="button" class="nav-link btn btn-secondary btn-sm">
                                                <fmt:message key="header.register"/>
                                            </button>
                                        </a>

                                    </li>

                                </ul>
                            </div>

                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>
    </nav>
</div>

<script src="JS/langCookie.js"></script>
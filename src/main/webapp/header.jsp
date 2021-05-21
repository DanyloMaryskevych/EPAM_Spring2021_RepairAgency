<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                                <a class="nav-link btn btn-light" href="index.jsp">Home</a>
                            </li>
                            <li style="margin-left: 5px" class="nav-item active">
                                <a class="nav-link btn btn-light" href="workers?sort_by=id">BH Workers</a>
                            </li>
                            <c:choose>
                                <c:when test="${role == 'Customer'}">
                                    <li style="margin-left: 5px" class="nav-item active">
                                        <a class="nav-link btn btn-light" href="customer?page=1">My Orders</a>
                                    </li>
                                </c:when>
                            </c:choose>
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
                <div class="d-flex justify-content-end">
                    <c:choose>
                        <c:when test="${username != null}">

                            <div class="row align-items-center w-75">
                                <div class="col-8">
                                    <p class="m-0">Hello, ${username}!</p>
                                    <p class="m-0">Your balance ${balance}$</p>
                                </div>

                                <div class="col-2">
                                    <div class="d-flex">
                                        <form class="m-0" action="logout">
                                            <div class="form-group">
                                                <input class="btn btn-warning" type="submit" value="Logout">
                                            </div>
                                        </form>
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
                                            Sign in
                                        </a>
                                    </li>

                                    <li style="margin-left: 5px" class="nav-item active">
                                        <a style="text-decoration: none" href="registration.jsp">
                                            <button style="background-color: #33cabb; color: aliceblue; font-weight: bold; font-size: 1rem; border: none"
                                                    type="button" class="nav-link btn btn-primary btn-sm">
                                                Sign up
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
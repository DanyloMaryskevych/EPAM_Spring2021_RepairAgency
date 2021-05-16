<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <!--Nav bar-->
    <nav style="margin: 0" class="navbar navbar-expand navbar-light bg-light"> <!--'fixed-top'-->
        <div style="width: 105%" class="row justify-content-between">

            <!--Left elements-->
            <div class="col">
                <div class="d-flex justify-content-start">

                    <!--Links-->
                    <div class="w-auto my-auto d-none d-sm-flex">
                        <ul class="navbar-nav mr-auto">
                            <li style="margin-left: 5px" class="nav-item active">
                                <a class="nav-link btn btn-light" href="index.jsp">Home</a>
                            </li>
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


            <div class="col">
                <div class="d-flex justify-content-end">
                    <c:choose>
                        <c:when test="${username != null}">

                            <div class="row align-items-center w-50">
                                <div class="col-8">
                                    <p class="m-0">Hello, ${username}!</p>
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
                                               class="nav-link mr-4 btn btn-sm" href="login.jsp">
<%--                                                <button style="font-size: 1rem; border: none" type="button"--%>
<%--                                                        class="nav-link mr-4 btn btn-sm"></button>--%>
                                                Sign in
                                            </a>


                                        <!-- Sign in Modal -->
                                        <div class="modal fade" id="SignInModal" tabindex="-1" role="dialog"
                                             aria-labelledby="SignInModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">

                                                        <%--Header--%>
                                                    <div class="modal-header">
                                                        <h5 class="text-center" id="SignInModalLabel">Login</h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                        <%--Header end--%>

                                                        <%--Body--%>
                                                    <div class="modal-body">
                                                        <form action="login" method="post">
                                                            <label class="form-text text-muted"
                                                                   for="login">Login</label>
                                                            <input class="form-control" type="text" name="login"
                                                                   id="login">

                                                            <label class="mt-3 form-text text-muted"
                                                                   for="password">Password</label>
                                                            <input class="form-control" type="password" name="password"
                                                                   id="password">

                                                            <hr>
                                                            <div class="row">
                                                                <input class="mt-3 ml-3 mr-3 col btn btn-success"
                                                                       type="submit" value="Login">
                                                            </div>

                                                        </form>
                                                    </div>
                                                        <%--Body end--%>

                                                </div>
                                            </div>
                                        </div>
                                        <!-- Sign in Modal end-->
                                    </li>

                                    <li style="margin-left: 5px" class="nav-item active">
                                        <a style="text-decoration: none" href="registration.jsp">
                                            <button style="background-color: #33cabb; color: aliceblue; font-weight: bold; font-size: 1rem; border: none"
                                                type="button" class="nav-link btn btn-primary btn-sm">
                                            Sign up</button>
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
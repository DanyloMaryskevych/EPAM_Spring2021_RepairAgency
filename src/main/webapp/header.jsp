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

                                        <!-- Sign in trigger modal -->
                                        <button style="font-size: 1rem; border: none" type="button"
                                                class="nav-link mr-4 btn btn-sm" data-toggle="modal"
                                                data-target="#SignInModal">
                                            Sign in
                                        </button>

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

                                        <!-- Sign up trigger modal -->
                                        <button style="background-color: #33cabb; color: aliceblue; font-weight: bold; font-size: 1rem; border: none"
                                                type="button" class="nav-link btn btn-primary btn-sm"
<%--                                                data-toggle="modal"--%>
<%--                                                data-target="#SignUpModal"--%>
                                        >
                                            <a style="color: aliceblue" href="registration.jsp">Sign up</a>
                                        </button>

                                        <!-- Sign up Modal -->
                                        <div class="modal fade" id="SignUpModal" tabindex="-1" role="dialog"
                                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">

                                                        <%--Header--%>
                                                    <div class="modal-header">
                                                        <h5 class="text-center" id="exampleModalLabel">Create
                                                            Account</h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                        <%--Header end--%>

                                                        <%--Body--%>
                                                    <div class="modal-body">
                                                        <form class="needs-validation m-2" novalidate action="register"
                                                              method="post">

                                                            <div>
                                                                <label class="form-text text-muted"
                                                                       for="new_login">Login</label>
                                                                <input class="form-control" type="text" name="login"
                                                                       id="new_login" required>
                                                                <div class="invalid-feedback">
                                                                    Please choose a login.
                                                                </div>
                                                            </div>

                                                            <div>
                                                                <label class="form-text text-muted"
                                                                       for="new_password">Password</label>
                                                                <input class="form-control" type="password"
                                                                       name="password"
                                                                       id="new_password" required>
                                                                <div class="invalid-feedback">
                                                                    Please provide a valid password
                                                                </div>
                                                            </div>

                                                            <div>
                                                                <label class="form-text text-muted"
                                                                       for="new_password1">Confirm Password</label>
                                                                <input class="form-control" type="password"
                                                                       name="password1"
                                                                       id="new_password1" required>
                                                                <div class="invalid-feedback">
                                                                    Please provide a valid password
                                                                </div>
                                                            </div>

                                                            <p class="mt-3">Continue as:</p>

                                                            <div class="custom-control custom-radio">
                                                                <input type="radio" class="custom-control-input"
                                                                       id="customer" name="role" value="Customer"
                                                                       required>
                                                                <label class="custom-control-label"
                                                                       for="customer">Customer</label>
                                                            </div>
                                                            <div class="custom-control custom-radio mb-3">
                                                                <input type="radio" class="custom-control-input"
                                                                       id="worker" name="role" value="Worker" required>
                                                                <label class="custom-control-label"
                                                                       for="worker">Worker</label>
                                                                <div class="invalid-feedback">
                                                                    Please choose your role
                                                                </div>
                                                            </div>

                                                            <hr>
                                                            <div class="row">
                                                                <input class="mt-3 ml-3 mr-3 col btn btn-success"
                                                                       type="submit" value="Register">
                                                            </div>
                                                        </form>

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


                                                    </div>
                                                        <%--Body end--%>

                                                </div>
                                            </div>
                                        </div>
                                        <!-- Sign up Modal end-->
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
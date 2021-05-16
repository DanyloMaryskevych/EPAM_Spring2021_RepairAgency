<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: danyl
  Date: 15/05/2021
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

<%@ include file="header.jsp" %>

<div style="width: 100%" class="row justify-content-md-center mt-5">
    <div class="col col-lg-3">
        <h1 class="text-center mb-4">Registration</h1>
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
                <p style="color: red"><c:out value="${loginError}"/></p>
            </div>

            <div>
                <label class="form-text text-muted"
                       for="new_password">Password</label>
                <input class="form-control" type="password"
                       name="password"
                       id="new_password" required>
                <small class="text-muted">Please, provide a password minimum 8 characters, at least one letter and one number</small>
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
            <div>
                <p style="color: red"><c:out value="${passwordEqualityError}"/></p>
                <p style="color: red"><c:out value="${passwordValidationError}"/></p>
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
    </div>
</div>

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

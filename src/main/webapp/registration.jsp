<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link rel="stylesheet" href="CSS/password.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div style="width: 100%; padding-bottom: 130px" class="row justify-content-md-center mt-2">
    <div class="col col-lg-3">
        <h1 class="text-center mb-4"><fmt:message key="register.title"/></h1>
        <form class="needs-validation m-2" novalidate action="register"
              method="post">

            <%--Login--%>
            <div>
                <label class="form-text text-muted"
                       for="new_login"><fmt:message key="register.login"/></label>
                <input class="form-control" type="text" name="login"
                       id="new_login" required
                       pattern="\S{3, 20}">
                <small class="text-muted"><fmt:message key="register.login_desc"/></small>
                <div class="invalid-feedback">
                    <fmt:message key="register.login_error"/>
                </div>
            </div>

            <c:choose>
                <c:when test="${loginError == null}">
                    <c:set value="none" var="display_login"/>
                </c:when>
                <c:otherwise>
                    <c:set value="block" var="display_login"/>
                </c:otherwise>
            </c:choose>
            <%--                aS2#fd35--%>
            <div style="display: ${display_login}">
                <p style="color: red"><fmt:message key="register.login_server_error"/></p>
            </div>

            <%--Password--%>
            <div>
                <label class="form-text text-muted"
                       for="new_password"><fmt:message key="register.password"/></label>
                <input class="form-control pass" type="password"
                       name="password"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$"
                       id="new_password" required>
                <div id="message">
                    <h5>Password must contain the following:</h5>
                    <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
                    <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
                    <p id="number" class="invalid">A <b>number</b></p>
                    <p id="symbol" class="invalid">A <b>symbol (!@#$%^&*_=+-)</b></p>
                    <p id="length" class="invalid"><b>8-12 characters</b></p>
                </div>
                <div class="invalid-feedback">
                    <fmt:message key="register.password_error"/>
                </div>
            </div>

            <%--Confirm Password--%>
            <div>
                <label class="form-text text-muted"
                       for="new_password1"><fmt:message key="register.confirm_password"/></label>
                <input class="form-control conf_pass" type="password"
                       name="password1"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$"
                       id="new_password1" required>
                <div class="form-text text-muted">
                    <p id="confirm"></p>
                </div>
            </div>
            <c:choose>
                <c:when test="${passwordEqualityError == null}">
                    <c:set value="none" var="display_password"/>
                </c:when>
                <c:otherwise>
                    <c:set value="block" var="display_password"/>
                </c:otherwise>
            </c:choose>
            <div style="display: ${display_password}">
                <p style="color: red"><fmt:message key="register.password_server_error"/></p>
            </div>


            <p class="mt-3"><fmt:message key="register.continue_as"/>:</p>

            <div class="custom-control custom-radio">
                <input type="radio" class="custom-control-input"
                       id="customer" name="role_id" value="2"
                       required>
                <label class="custom-control-label"
                       for="customer"><fmt:message key="register.customer"/></label>
            </div>
            <div class="custom-control custom-radio mb-3">
                <input type="radio" class="custom-control-input"
                       id="worker" name="role_id" value="3" required>
                <label class="custom-control-label"
                       for="worker"><fmt:message key="orders.worker"/></label>
                <div class="invalid-feedback">
                    <fmt:message key="register.continue_as_error"/>
                </div>
            </div>

            <hr>
            <div class="row">
                <input class="mt-3 ml-3 mr-3 col btn btn-success"
                       type="submit" value="<fmt:message key="register.register"/>">
            </div>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            const forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            const validation = Array.prototype.filter.call(forms, function (form) {
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

<script>
    const password = document.getElementsByClassName("pass")
        , confirm_password = document.getElementById("new_password1");

    function validatePassword() {
        if (password.value !== confirm_password.value) {
            document.getElementById("confirm").style.setProperty("color", "red");
            document.getElementById("confirm").innerHTML = <fmt:message key="register.confirm_password_error"/>;
        } else {
            document.getElementById("confirm").innerHTML = "";
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>

<script>
    const myInput = document.getElementById("new_password");
    const letter = document.getElementById("letter");
    const capital = document.getElementById("capital");
    const number = document.getElementById("number");
    const symbol = document.getElementById("symbol");
    const length = document.getElementById("length");

    // When the user clicks on the password field, show the message box
    myInput.onfocus = function () {
        document.getElementById("message").style.display = "block";
    }

    // When the user clicks outside of the password field, hide the message box
    myInput.onblur = function () {
        document.getElementById("message").style.display = "none";
    }

    // When the user starts to type something inside the password field
    myInput.onkeyup = function () {
        // Validate lowercase letters
        const lowerCaseLetters = /[a-z]/g;
        if (myInput.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid");
            letter.classList.add("valid");
        } else {
            letter.classList.remove("valid");
            letter.classList.add("invalid");
        }

        // Validate capital letters
        const upperCaseLetters = /[A-Z]/g;
        if (myInput.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid");
            capital.classList.add("valid");
        } else {
            capital.classList.remove("valid");
            capital.classList.add("invalid");
        }

        // Validate numbers
        const numbers = /[0-9]/g;
        if (myInput.value.match(numbers)) {
            number.classList.remove("invalid");
            number.classList.add("valid");
        } else {
            number.classList.remove("valid");
            number.classList.add("invalid");
        }

        // Validate symbols
        const symbols = /[!@#$%^&*_=+-]/g;
        if (myInput.value.match(symbols)) {
            symbol.classList.remove("invalid");
            symbol.classList.add("valid");
        } else {
            symbol.classList.remove("valid");
            symbol.classList.add("invalid");
        }

        // Validate length
        if (myInput.value.length >= 8 && myInput.value.length <= 12) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
        }
    }
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

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
                            <li style="margin-left: 5px"  class="nav-item active">
                                <a class="nav-link btn btn-light" href="index.jsp" >Home</a>
                            </li>
                        </ul>
                    </div>

                </div>
            </div>

            <!-- Center elements -->
            <div class="col">
                <div class="d-flex justify-content-center">
                    <h1 style="font-family: 'Akaya Telivigala', cursive; font-size: 48px">Broken Hammer</h1>
                </div>
            </div>


            <div class="col">
                <div class="d-flex justify-content-end">
                    <c:choose>
                        <c:when test="${username != null}">
                            Hello, ${username}
                            <form action="logout">
                                <input class="m-4 btn btn-warning" type="submit" value="Logout">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button class="m-2"><a href="login">Sign in</a></button>
                            <button class="m-2"><a href="register">Sign up</a></button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>
    </nav>
</div>
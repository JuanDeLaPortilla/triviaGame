<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio</title>
    <%@include file="/WEB-INF/user/commons/head-imports.jsp" %>
</head>
<body>

<div class="container-fluid h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">

        <!-- Center -->
        <div class="col-md-6 col-lg-6 col-xl-4 p-4">

            <!-- Title -->
            <div>
                <h1 class="pb-5 text-center text-primary">Juego de Trivia</h1>
            </div>
            <!-- Title -->

            <!-- Buttons -->
            <div class="row d-flex justify-content-center align-items-center gy-4">

                <!-- Play button -->
                <a class="btn btn-primary btn-lg"
                   href="#">
                    <i class="fa-solid fa-gamepad"></i> Jugar</a>
                <!-- Play button -->

                <!-- Ranking button -->
                <a class="btn btn-primary btn-lg"
                   href="${pageContext.request.contextPath}/ranking">
                    <i class="fa-solid fa-crown"></i> Ranking</a>
                <!-- Ranking button -->

                <c:if test="${(sessionScope.usuario1 != null && sessionScope.usuario2 != null)
                    && (sessionScope.usuario1.esAdmin == 1 || sessionScope.usuario2.esAdmin == 1)}">
                    <!-- Dashboard button -->
                    <a class="btn btn-primary btn-lg"
                       href="${pageContext.request.contextPath}/dashboard">
                        <i class="fa-solid fa-chart-pie"></i> Dashboard</a>
                    <!-- Dashboard button -->
                </c:if>

                <!-- Logout button -->
                <a class="btn btn-primary btn-lg"
                   href="${pageContext.request.contextPath}/logout">
                    <i class="fa-solid fa-right-from-bracket"></i> Salir</a>
                <!-- Logout button -->

            </div>
            <!-- Buttons -->

        </div>
        <!-- Center -->

    </div>
</div>

<%@include file="/WEB-INF/user/commons/footer-imports.jsp" %>

</body>
</html>
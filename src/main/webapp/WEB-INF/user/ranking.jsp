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

<div class="container-fluid h-100 spacer index-background">
    <div class="row d-flex justify-content-center align-items-center h-100">

        <!-- Center -->
        <div class="col-md-10 col-lg-8 col-xl-6 p-4">

            <!-- Title -->
            <div>
                <h1 class="pb-5 text-center text-light">Ranking <i class="fa-solid fa-crown"></i></h1>
            </div>
            <!-- Title -->

            <!-- Table -->
            <div class="card mb-5">
                <div class="card-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Partidas Jugadas</th>
                            <th scope="col">Puntaje Total</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${requestScope.ranking}" var="usuario" varStatus="loop">
                            <tr>
                                <th scope="row">${loop.count}</th>
                                <td>${usuario.nombre}</td>
                                <td>${usuario.partidasJugadas}</td>
                                <td>${usuario.puntajeTotal}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- Table -->

            <!-- Buttons -->
            <div class="row d-flex justify-content-center align-items-center gy-4">

                <!-- Back button -->
                <a class="btn btn-primary btn-lg"
                   href="${pageContext.request.contextPath}/index">
                    <i class="fa-solid fa-gamepad"></i> Volver al Inicio</a>
                <!-- Back button -->

            </div>
            <!-- Buttons -->

        </div>
        <!-- Center -->

    </div>
</div>

<%@include file="/WEB-INF/user/commons/footer-imports.jsp" %>

</body>
</html>
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
                <h1 class="pb-5 text-center text-light">${sessionScope.partida.nombre}</h1>
            </div>
            <!-- Title -->

            <!-- Question Card -->
            <div class="card quiz_box d-none">
                <div class="card-header d-flex justify-content-between align-items-end">
                    <h4 class="titulo">Pregunta 1</h4>

                    <!-- Timer -->
                    <div class="timer">
                        <div class="time_left_txt">Tiempo Restante</div>
                        <div class="timer_sec">15</div>
                    </div>
                    <div class="time_line"></div>
                    <!-- Timer -->

                </div>

                <div class="card-body">
                    <div class="text-center">
                        <h3 class="pregunta">Pregunta?</h3>
                    </div>
                    <div class="opciones row d-flex justify-content-center align-items-center gy-3 pt-3">
                        <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 1</button>
                        <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 2</button>
                        <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 3</button>
                        <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 4</button>
                    </div>
                </div>

                <div class="card-footer d-flex justify-content-between align-items-center">
                    <div class="total_preg"></div>
                    <div class="btn btn-primary btn_siguiente d-none">Siguiente</div>
                </div>
            </div>
            <!-- Question Card -->

            <!-- Result Box -->
            <div class="card result_box d-none">
                <div class="card-header">
                    <h1 class="text-center winner"></h1>
                </div>

                <div class="card-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Puntaje</th>
                        </tr>
                        </thead>

                        <tbody class="data">
                        </tbody>
                    </table>
                </div>

                <div class="card-footer d-flex justify-content-center align-items-center">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/play?action=goToIndex">Volver al
                        Inicio</a>
                </div>
            </div>
            <!-- Result Box -->

        </div>
        <!-- Center -->
    </div>
</div>

<%@include file="/WEB-INF/user/commons/footer-imports.jsp" %>
<script src="${pageContext.request.contextPath}/assets/js/game.js"></script>

</body>
</html>
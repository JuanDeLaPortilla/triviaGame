<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Listado de Respuestas de Usuarios</title>
    <%@include file="/WEB-INF/dashboard/commons/dashboard-head-imports.jsp" %>
</head>
<body class="spacer">
<!-- header -->
<jsp:include page="/WEB-INF/dashboard/commons/dashboard-header.jsp"/>
<!-- header -->

<!-- aside -->
<jsp:include page="/WEB-INF/dashboard/commons/dashboard-aside.jsp"/>
<!-- aside -->
<c:if test="${requestScope.msg != null || !requestScope.msg eq ''}">
    <script>alert(${msg})</script>
</c:if>
<main id="main" class="main">
    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card overflow-auto">
                    <div class="card-body">
                        <h5 class="card-title" style="padding-left: 6px !important; font-size: x-large !important;">
                            Listado de Respuestas de Usuarios</h5>
                        <table class="table table-borderless datatable">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Partida</th>
                                <th scope="col">Pregunta</th>
                                <th scope="col">Respuesta</th>
                                <th scope="col">Es correcta</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.usuarios}" var="u">
                                <tr>
                                    <th scope="row">#${u.usuario.id}</th>
                                    <td>${u.usuario.nombre}</td>
                                    <td>${u.partida.id} - ${u.partida.nombre}</td>
                                    <td>${u.pregunta.id} - ${u.pregunta.contenido}</td>
                                    <td>${u.respuesta.id} - ${u.respuesta.contenido}</td>
                                    <td>${u.respuesta.esCorrecta == 0 ? "No" : "Si"}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<%@include file="/WEB-INF/dashboard/commons/dashboard-scripts-imports.jsp" %>

</body>
</html>
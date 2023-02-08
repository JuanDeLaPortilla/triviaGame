<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Editar Respuesta</title>
    <%@include file="/WEB-INF/dashboard/commons/dashboard-head-imports.jsp" %>
</head>
<body class="spacer">
<!-- header -->
<jsp:include page="/WEB-INF/dashboard/commons/dashboard-header.jsp"/>
<!-- header -->

<!-- aside -->
<jsp:include page="/WEB-INF/dashboard/commons/dashboard-aside.jsp"/>
<!-- aside -->

<main id="main" class="main">
    <section class="section">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Editar una Respuesta</h5>
                        <form class="row g-3" method="post"
                              action="${pageContext.request.contextPath}/respuestas?action=update">
                            <div class="col-12"><label for="input3" class="form-label">Pregunta</label>
                                <select name="tagId" class="form-select" id="input3" required>
                                    <c:forEach items="${requestScope.preguntas}" var="p">
                                        <option value="${p.id}" ${p.id == requestScope.respuesta.pregunta.id ? Selected : ""}>${p.contenido}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-12"><label for="input1" class="form-label">Respuesta</label>
                                <input type="text"
                                       class="form-control"
                                       id="input1"
                                       name="contenido"
                                       value="${requestScope.respuesta.contenido}"
                                       required>
                            </div>
                            <div class="col-12"><label for="input2" class="form-label">Es Correcta</label>
                                <select name="esCorrecta" class="form-select" id="input2" required>
                                    <option value="0" ${requestScope.respuesta.esCorrecta == 0 ? "selected" : ""}>No</option>
                                    <option value="1" ${requestScope.respuesta.esCorrecta == 1 ? "selected" : ""}>Si</option>
                                </select>
                            </div>
                            <input type="hidden" name="id" value="${requestScope.respuesta.id}">
                            <div class="text-center"><input type="submit" class="btn btn-primary" value="Editar">
                            </div>
                        </form>
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
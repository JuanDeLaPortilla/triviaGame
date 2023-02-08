<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Editar Usuario | ALONE </title>
    <%@include file="/WEB-INF/commons/dashboard-head-imports.jsp" %>
</head>
<body>
<!-- header -->
<jsp:include page="/WEB-INF/commons/dashboard-header.jsp"/>
<!-- header -->

<!-- aside -->
<jsp:include page="/WEB-INF/commons/dashboard-aside.jsp"/>
<!-- aside -->

<main id="main" class="main">
    <section class="section">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Editar un Usuario</h5>
                        <form class="row g-3" method="post"
                              action="${pageContext.request.contextPath}/usuarios?action=update">
                            <div class="col-12"><label for="input1" class="form-label">Nombre</label>
                                <input type="text"
                                       class="form-control"
                                       id="input1"
                                       name="nombre"
                                       value="${requestScope.usuario.nombre}"
                                       required>
                            </div>
                            <div class="col-12"><label for="input2" class="form-label">Tel&eacute;fono</label>
                                <input type="text" class="form-control" id="input2" name="telefono" required
                                       value="${requestScope.usuario.telefono}"
                                       maxlength="9">
                            </div>
                            <div class="col-12"><label for="input3" class="form-label">Nivel</label>
                                <select name="nivel" class="form-select" id="input3" required>
                                    <c:choose>
                                        <c:when test="${requestScope.usuario.nivel == 0}">
                                            <option value="0" selected>Cliente</option>
                                            <option value="1">Administrador</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="0">Cliente</option>
                                            <option value="1" selected>Administrador</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                            <div class="col-12"><label for="input4" class="form-label">Email</label>
                                <input type="email"
                                       class="form-control"
                                       id="input4"
                                       name="email"
                                       value="${requestScope.usuario.email}"
                                       required>
                            </div>
                            <div class="col-12"><label for="input5" class="form-label">Password</label>
                                <input type="password" class="form-control" id="input5" name="password"
                                       value="${requestScope.usuario.password}"
                                       required></div>
                            <input type="hidden" name="id" value="${requestScope.usuario.id}">
                            <div class="text-center"><input type="submit" class="btn btn-primary" value="Editar">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<jsp:include page="/WEB-INF/commons/dashboard-footer.jsp"/>
<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<%@include file="/WEB-INF/commons/dashboard-scripts-imports.jsp" %>

</body>
</html>
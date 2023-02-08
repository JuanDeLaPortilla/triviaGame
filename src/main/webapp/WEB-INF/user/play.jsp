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

      <div class="card">
        <div class="card-header d-flex align-items-center p-3">
          <h4 class="titulo">Pregunta 1</h4>
        </div>

        <div class="card-body">
          <div class="pregunta text-center">
            <h3>Pregunta?</h3>
          </div>
          <div class="opciones row d-flex justify-content-center align-items-center gy-3 pt-3">
            <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 1</button>
            <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 2</button>
            <button class="btn btn-custom col-xl-10 col-lg-10">Opcion 3</button>
            <button class="btn btn-custom ccol-xl-10 col-lg-10">Opcion 4</button>
          </div>
        </div>

        <div class="card-footer p-3 text-end">
          <a href="#" class="btn btn-primary btn_siguiente">Siguiente</a>
        </div>
      </div>

    </div>
    <!-- Center -->

  </div>
</div>

<%@include file="/WEB-INF/user/commons/footer-imports.jsp" %>

</body>
</html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrarse</title>
    <%@include file="/WEB-INF/commons/head-imports.jsp" %>
</head>
<body>

<div class="container-fluid h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
                 class="img-fluid" alt="Sample image">
        </div>
        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1 p-4 bg-primary text-light custom-border">
            <div>
                <h2 class="pb-3">Preparate para el juego, &iexcl;Reg&iacute;strate!</h2>
            </div>
            <form>

                <!-- Name input -->
                <div class="form-outline mb-4 pt-2">
                    <input type="text" id="form3Example1" class="form-control form-control-lg"
                           placeholder="John69" name="name" required/>
                    <label class="form-label pt-2" for="form3Example1">Nombre de Usuario</label>
                </div>

                <!-- Email input -->
                <div class="form-outline mb-4 pt-2">
                    <input type="email" id="form3Example3" class="form-control form-control-lg"
                           placeholder="ejemplo@correo.com" name="email" required/>
                    <label class="form-label pt-2" for="form3Example3">Correo Electr&oacute;nico</label>
                </div>

                <!-- Password input -->
                <div class="form-outline mb-2">
                    <input type="password" id="form3Example4" class="form-control form-control-lg"
                           placeholder="*****" name="pass" required/>
                    <label class="form-label pt-2" for="form3Example4">Contrase&ntilde;a</label>
                </div>

                <div class="text-center text-lg-start pt-2">
                    <button type="button" class="btn btn-custom btn-lg"
                            style="padding-left: 2.5rem; padding-right: 2.5rem;">Registrarse
                    </button>
                    <p class="small mt-2 pt-2 mb-0">&iquest;Ya tienes una cuenta? <a
                            href="${pageContext.request.contextPath}/login.jsp"
                            class="link-info">Inicia Sesi&oacute;n</a>
                    </p>
                </div>

            </form>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/commons/head-imports.jsp" %>

</body>
</html>
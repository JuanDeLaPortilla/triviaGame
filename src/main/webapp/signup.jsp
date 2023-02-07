<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrarse</title>
    <%@include file="/WEB-INF/user/commons/head-imports.jsp" %>
</head>
<body>

<div class="container-fluid h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">

        <!-- Left -->
        <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
                 class="img-fluid" alt="Sample image">
        </div>
        <!-- Left -->

        <!-- Right -->
        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1 p-4 bg-primary text-light custom-border">

            <!-- Title -->
            <div class="pb-3 d-flex flex-column">
                <span class="fs-4 m-0">Preparate para el juego,</span>
                <h2 class="fs-1 m-0">&iexcl;Reg&iacute;strate!</h2>
            </div>
            <!-- Title -->

            <!-- Form -->
            <form class="needs-validation" method="post" action="${pageContext.request.contextPath}/signup">

                <!-- Name input -->
                <div class="form-outline mb-2 was-validated">
                    <label class="form-label pt-2" for="form3Example1">Nombre de Usuario</label>
                    <input type="text" id="form3Example1" class="form-control"
                           placeholder="John69" name="nombre" required/>
                    <div class="invalid-feedback text-light">Por favor, ingresa un nombre de usuario</div>
                </div>
                <!-- Name input -->


                <!-- Email input -->
                <div class="form-outline mb-2 was-validated">
                    <label class="form-label pt-2" for="form3Example3">Correo Electr&oacute;nico</label>
                    <input type="email" id="form3Example3" class="form-control"
                           placeholder="ejemplo@correo.com" name="correo" required/>
                    <div class="invalid-feedback text-light">Por favor, ingresa un correo elect&oacute;nico</div>
                </div>
                <!-- Email input -->


                <!-- Password input -->
                <div class="form-outline mb-2 was-validated">
                    <label class="form-label pt-2" for="form3Example4">Contrase&ntilde;a</label>
                    <input type="password" id="form3Example4" class="form-control"
                           placeholder="*****" name="pass" required/>
                    <div class="invalid-feedback text-light">Por favor, ingresa una contrase&ntilde;a</div>
                </div>
                <!-- Password input -->

                <div class="text-center text-lg-start pt-2">

                    <!-- Submit button -->
                    <input type="submit" class="btn btn-custom btn-lg"
                           style="padding-left: 2.5rem; padding-right: 2.5rem;" value="Registrarse">
                    <div class="small text-danger"><p>${error}</p></div>
                    <!-- Submit button -->

                    <!-- Bottom text -->
                    <p class="small mt-2 mb-0">&iquest;Ya tienes una cuenta? <a
                            href="${pageContext.request.contextPath}/login"
                            class="link-info">Inicia Sesi&oacute;n</a>
                    </p>
                    <!-- Bottom text -->

                </div>

            </form>
        </div>
        <!-- Right -->

    </div>
</div>

<%@include file="/WEB-INF/user/commons/footer-imports.jsp" %>

</body>
</html>

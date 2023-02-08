<aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/dashboard"> <i
                class="bi bi-grid"></i> <span>Dashboard</span> </a></li>
        <li class="nav-item">
            <!-- Usuarios -->
            <a class="nav-link collapsed" data-bs-target="#users-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-people"></i><span>Usuarios</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="users-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/usuarios?view=list">
                    <i class="bi bi-circle"></i><span>Listado</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/usuarios?view=byGame">
                    <i class="bi bi-circle"></i><span>Por Partida</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/usuarios?view=add-form">
                    <i class="bi bi-circle"></i><span>A&ntilde;adir</span>
                </a></li>
            </ul>

            <!-- Partidas -->
            <a class="nav-link collapsed" data-bs-target="#games-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-controller"></i><span>Partidas</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="games-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/partidas?view=list">
                    <i class="bi bi-circle"></i><span>Listado</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/partidas?view=add-form">
                    <i class="bi bi-circle"></i><span>Crear</span>
                </a></li>
            </ul>

            <!-- Categorias de Preguntas -->
            <a class="nav-link collapsed" data-bs-target="#tags-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-tag"></i><span>Categor&iacute;as</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="tags-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/categorias?view=list">
                    <i class="bi bi-circle"></i><span>Listado</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/categorias?view=add-form">
                    <i class="bi bi-circle"></i><span>A&ntilde;adir</span>
                </a></li>
            </ul>

            <!-- Preguntas -->
            <a class="nav-link collapsed" data-bs-target="#questions-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-question"></i><span>Preguntas</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="questions-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/preguntas?view=list">
                    <i class="bi bi-circle"></i><span>Listado</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/preguntas?view=add-form">
                    <i class="bi bi-circle"></i><span>A&ntilde;adir</span>
                </a></li>
            </ul>

            <!-- Respuestas -->
            <a class="nav-link collapsed" data-bs-target="#answers-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-check"></i><span>Respuestas</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="answers-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/respuestas?view=list">
                    <i class="bi bi-circle"></i><span>Listado</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/respuestas?view=userAnswers">
                    <i class="bi bi-circle"></i><span>Respuestas de Usuarios</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/respuestas?view=add-form">
                    <i class="bi bi-circle"></i><span>A&ntilde;adir</span>
                </a></li>
            </ul>

        </li>
        <li class="nav-item"><a class="nav-link collapsed" href="${pageContext.request.contextPath}/index"> <i
                class="bi bi-house"></i> <span>Volver al Inicio</span> </a></li>
        <li class="nav-item"><a class="nav-link collapsed" href="${pageContext.request.contextPath}/logout"> <i
                class="bi bi-box-arrow-in-right"></i> <span>Cerrar Sesi&oacute;n</span> </a></li>
    </ul>
</aside>
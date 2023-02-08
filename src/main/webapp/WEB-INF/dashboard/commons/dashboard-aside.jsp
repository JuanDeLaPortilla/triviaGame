<aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/dashboard"> <i
                class="bi bi-grid"></i> <span>Dashboard</span> </a></li>
        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#orders-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-cart"></i><span>&Oacute;rdenes</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="orders-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/ordenes?view=list"> <i class="bi bi-circle"></i><span>Listado</span> </a></li>
                <li><a href="${pageContext.request.contextPath}/ordenes?view=add-form"> <i class="bi bi-circle"></i><span>A&ntilde;adir</span> </a></li>
            </ul>
            <a class="nav-link collapsed" data-bs-target="#users-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-people"></i><span>Usuarios</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="users-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/usuarios?view=list"> <i class="bi bi-circle"></i><span>Listado</span> </a></li>
                <li><a href="${pageContext.request.contextPath}/usuarios?view=add-form"> <i class="bi bi-circle"></i><span>A&ntilde;adir</span> </a></li>
            </ul>
            <a class="nav-link collapsed" data-bs-target="#products-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-bag"></i><span>Productos</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="products-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/productos?view=list"> <i class="bi bi-circle"></i><span>Listado</span> </a></li>
                <li><a href="${pageContext.request.contextPath}/productos?view=add-form"> <i class="bi bi-circle"></i><span>A&ntilde;adir</span> </a></li>
            </ul>
            <a class="nav-link collapsed" data-bs-target="#tags-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-tag"></i><span>Categor&iacute;as</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="tags-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li><a href="${pageContext.request.contextPath}/categorias?view=list"> <i class="bi bi-circle"></i><span>Listado</span> </a></li>
                <li><a href="${pageContext.request.contextPath}/categorias?view=add-form"> <i class="bi bi-circle"></i><span>A&ntilde;adir</span> </a></li>
            </ul>

        </li>
        <li class="nav-item"><a class="nav-link collapsed" href="${pageContext.request.contextPath}/index"> <i
                class="bi bi-house"></i> <span>Volver al Inicio</span> </a></li>
        <li class="nav-item"><a class="nav-link collapsed" href="${pageContext.request.contextPath}/logout"> <i
                class="bi bi-box-arrow-in-right"></i> <span>Cerrar Sesi&oacute;n</span> </a></li>
    </ul>
</aside>
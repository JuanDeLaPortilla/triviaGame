package trivia.game.controladores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import trivia.game.DAO.UsuarioDAO;
import trivia.game.DAO.UsuarioPartidaDAO;
import trivia.game.modelos.Usuario;
import trivia.game.modelos.UsuarioPartida;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "usuarios", value = "/usuarios")
public class UsuariosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO((Connection) request.getAttribute("conn"));
        UsuarioPartidaDAO usuarioPartidaDAO = new UsuarioPartidaDAO((Connection) request.getAttribute("conn"));

        String view = request.getParameter("view");

        if (view != null) {
            switch (view) {
                case "list":
                    viewList(request, response, usuarioDAO);
                    break;
                case "byGame":
                    viewByGame(request, response, usuarioPartidaDAO);
                    break;
                case "add-form":
                    viewAddForm(request, response);
                    break;
                case "update-form":
                    viewUpdateForm(request, response, usuarioDAO);
                    break;
                case "delete":
                    delete(request, usuarioDAO);
                    viewList(request, response, usuarioDAO);
                    break;
                default:
                    ServletUtil.sendError(response);
                    break;
            }
        } else {
            ServletUtil.sendError(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO((Connection) request.getAttribute("conn"));

        String msg = "";
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addUsuario(request, usuarioDAO);
                    msg = "'\u00a1Usuario a\u00f1adido con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewAddForm(request, response);
                    break;

                case "update":
                    addUsuario(request, usuarioDAO);
                    msg = "'\u00a1Usuario actualizado con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewList(request, response, usuarioDAO);
                    break;

                default:
                    ServletUtil.sendError(response);
                    break;
            }
        } else {
            ServletUtil.sendError(response);
        }
    }

    //Métodos para redireccionar a una página
    private void viewList(HttpServletRequest request, HttpServletResponse response, UsuarioDAO usuarioDAO) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.buscar();

        request.setAttribute("usuarios", usuarios);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/usuarios/listado.jsp").forward(request, response);
    }

    private void viewByGame(HttpServletRequest request, HttpServletResponse response, UsuarioPartidaDAO usuarioPartidaDAO) throws ServletException, IOException {
        List<UsuarioPartida> usuarios = usuarioPartidaDAO.buscar();

        request.setAttribute("usuarios", usuarios);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/usuarios/por-partida.jsp").forward(request, response);
    }

    private void viewAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/usuarios/anadir-formulario.jsp").forward(request, response);
    }

    private void viewUpdateForm(HttpServletRequest request, HttpServletResponse response, UsuarioDAO usuarioDAO) throws ServletException, IOException {
        Long id = ServletUtil.getId(request);
        Usuario usuario = usuarioDAO.buscarPorId(id);

        request.setAttribute("usuario", usuario);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/usuarios/editar-formulario.jsp").forward(request, response);
    }

    //Métodos CRUD
    private void addUsuario(HttpServletRequest request, UsuarioDAO usuarioDAO) {
        //Se rescatan los datos
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String pass = request.getParameter("pass");
        int esAdmin = Integer.parseInt(request.getParameter("esAdmin"));
        Long id = ServletUtil.getId(request);

        Usuario usuario = new Usuario(id, nombre, correo, pass, esAdmin);//Se crea el modelo

        usuarioDAO.modificar(usuario);//se llama al DAO
    }

    private void delete(HttpServletRequest request, UsuarioDAO usuarioDAO) {
        Long id = ServletUtil.getId(request);
        usuarioDAO.eliminar(id);
    }
}

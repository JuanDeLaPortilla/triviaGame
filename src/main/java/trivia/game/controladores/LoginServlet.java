package trivia.game.controladores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import trivia.game.DAO.UsuarioDAO;
import trivia.game.modelos.Usuario;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");

        if (usuario1 != null && usuario2 == null) {//si solo inicio sesion usuario 1
            this.defaultAction(request, response, "2");//se lleva al login para jugador 2
        }

        if (usuario1 != null && usuario2 != null) {//si ya se ha iniciado sesion
            sendToIndex(request, response);//se lleva al inicio
            return;
        }

        this.defaultAction(request, response, "1");//sino se lleva al login
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroJugador = request.getParameter("player");
        if (numeroJugador != null) {
            switch (numeroJugador) {
                case "1":
                    if (this.login(request)) {//si logra iniciar sesion jugador 1
                        defaultAction(request, response, "2");//se lleva al login para el jugador 2
                    } else {//si falla
                        sendLoginError(request);// manda un error
                        defaultAction(request, response, "1");//se lleva otra vez al login
                    }
                case "2":
                    if (this.login(request)) {//si logra iniciar sesion jugador 2
                        sendToIndex(request, response);//se lleva al inicio
                    } else {//si falla
                        sendLoginError(request);
                        defaultAction(request, response, "2");//se lleva otra vez al login
                    }
                default:
                    defaultAction(request, response, "1");
            }
        } else {
            defaultAction(request, response, "1");
        }
    }

    private void defaultAction(HttpServletRequest req, HttpServletResponse resp, String numeroJugador) throws ServletException, IOException {
        req.setAttribute("numeroJugador", numeroJugador);
        getServletContext().getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
    }

    private void sendLoginError(HttpServletRequest request) {
        request.setAttribute("error", "Correo o contrase&ntilde;a incorrecta");
    }

    private void sendToIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/index").forward(request, response);
    }

    private boolean login(HttpServletRequest request) {
        //Se rescatan los datos del formulario
        String numeroJugador = request.getParameter("player");
        String correo = request.getParameter("correo");
        String pass = request.getParameter("pass");

        UsuarioDAO usuarioDAO = new UsuarioDAO((Connection) request.getAttribute("conn"));//se instancia el DAO
        Usuario usuario = usuarioDAO.login(correo, pass);//se llama al metodo login del DAO

        if (usuario != null) {//si el usuario existe en la db
            HttpSession session = request.getSession();
            session.setAttribute("usuario" + numeroJugador, usuario);//se guarda en la sesion
            return true;
        }

        return false;
    }
}

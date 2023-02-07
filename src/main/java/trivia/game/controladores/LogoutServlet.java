package trivia.game.controladores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import trivia.game.modelos.Usuario;

import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Se obtiene la sesion
        HttpSession session = request.getSession();

        //Se obtienen los datos de los usuarios
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");

        if (usuario1 != null && usuario2 != null) {
            //Se invalida la sesion
            session.invalidate();
        }

        //Se redirecciona al inicio
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

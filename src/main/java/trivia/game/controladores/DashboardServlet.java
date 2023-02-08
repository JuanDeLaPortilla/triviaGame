package trivia.game.controladores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import trivia.game.DAO.PartidaDAO;
import trivia.game.DAO.UsuarioDAO;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "dashboard", value = "/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        defaultAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void defaultAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO((Connection) request.getAttribute("conn"));
        PartidaDAO partidaDAO = new PartidaDAO((Connection) request.getAttribute("conn"));

        request.setAttribute("usuarios", usuarioDAO.contar());
        request.setAttribute("partidas", partidaDAO.contarPorMes());
        request.setAttribute("ranking", usuarioDAO.obtenerRanking());

        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp").forward(request, response);
    }
}

package trivia.game.controladores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import trivia.game.DAO.PartidaDAO;
import trivia.game.DAO.UsuarioDAO;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "ranking", value = "/ranking")
public class RankingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO((Connection) request.getAttribute("conn"));
        defaultAction(request,response,usuarioDAO);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void defaultAction(HttpServletRequest request, HttpServletResponse response, UsuarioDAO usuarioDAO) throws ServletException, IOException {
        request.setAttribute("ranking", usuarioDAO.obtenerRanking());

        getServletContext().getRequestDispatcher("/WEB-INF/user/ranking.jsp").forward(request, response);
    }
}

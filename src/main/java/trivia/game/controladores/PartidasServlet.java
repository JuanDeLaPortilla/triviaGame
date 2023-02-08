package trivia.game.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trivia.game.DAO.PartidaDAO;
import trivia.game.modelos.Partida;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "partidas", value = "/partidas")
public class PartidasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PartidaDAO partidaDAO = new PartidaDAO((Connection) request.getAttribute("conn"));

        String view = request.getParameter("view");

        if (view != null) {
            switch (view) {
                case "list":
                    viewList(request, response, partidaDAO);
                    break;
                case "add-form":
                    viewAddForm(request, response);
                    break;
                case "update-form":
                    viewUpdateForm(request, response, partidaDAO);
                    break;
                case "delete":
                    delete(request, partidaDAO);
                    viewList(request, response, partidaDAO);
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
        PartidaDAO partidaDAO = new PartidaDAO((Connection) request.getAttribute("conn"));

        String msg = "";
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addPartida(request, partidaDAO);
                    msg = "'\u00a1Partida creada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewAddForm(request, response);
                    break;

                case "update":
                    addPartida(request, partidaDAO);
                    msg = "'\u00a1Partida actualizada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewList(request, response, partidaDAO);
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
    private void viewList(HttpServletRequest request, HttpServletResponse response, PartidaDAO partidaDAO) throws ServletException, IOException {
        List<Partida> partidas = partidaDAO.buscar();

        request.setAttribute("partidas", partidas);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/partidas/listado.jsp").forward(request, response);
    }

    private void viewAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/partidas/anadir-formulario.jsp").forward(request, response);
    }

    private void viewUpdateForm(HttpServletRequest request, HttpServletResponse response, PartidaDAO partidaDAO) throws ServletException, IOException {
        Long id = ServletUtil.getId(request);
        Partida partida = partidaDAO.buscarPorId(id);

        request.setAttribute("partida", partida);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/partidas/editar-formulario.jsp").forward(request, response);
    }

    //Métodos CRUD
    private void addPartida(HttpServletRequest request, PartidaDAO partidaDAO) {
        //Se rescatan los datos
        String nombre = request.getParameter("nombre");
        String fechaStr = request.getParameter("fechaCreacion");
        LocalDate fechaCreacion = LocalDate.parse(fechaStr);
        Long id = ServletUtil.getId(request);

        Partida partida = new Partida(id, nombre, fechaCreacion);//Se crea el modelo

        partidaDAO.modificar(partida);//se llama al DAO
    }

    private void delete(HttpServletRequest request, PartidaDAO partidaDAO) {
        Long id = ServletUtil.getId(request);
        partidaDAO.eliminar(id);
    }
}

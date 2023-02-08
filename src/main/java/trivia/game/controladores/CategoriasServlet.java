package trivia.game.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trivia.game.DAO.CategoriaPreguntaDAO;
import trivia.game.modelos.CategoriaPregunta;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "categorias", value = "/categorias")
public class CategoriasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaPreguntaDAO categoriaPreguntaDAO = new CategoriaPreguntaDAO((Connection) request.getAttribute("conn"));

        String view = request.getParameter("view");

        if (view != null) {
            switch (view) {
                case "list":
                    viewList(request, response, categoriaPreguntaDAO);
                    break;
                case "add-form":
                    viewAddForm(request, response);
                    break;
                case "update-form":
                    viewUpdateForm(request, response, categoriaPreguntaDAO);
                    break;
                case "delete":
                    delete(request, categoriaPreguntaDAO);
                    viewList(request, response, categoriaPreguntaDAO);
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
        CategoriaPreguntaDAO categoriaPreguntaDAO = new CategoriaPreguntaDAO((Connection) request.getAttribute("conn"));

        String msg = "";
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addCategoriaPregunta(request, categoriaPreguntaDAO);
                    msg = "'\u00a1Categor\u00eda creada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewAddForm(request, response);
                    break;

                case "update":
                    addCategoriaPregunta(request, categoriaPreguntaDAO);
                    msg = "'\u00a1Categor\u00eda actualizada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewList(request, response, categoriaPreguntaDAO);
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
    private void viewList(HttpServletRequest request, HttpServletResponse response, CategoriaPreguntaDAO categoriaPreguntaDAO) throws ServletException, IOException {
        List<CategoriaPregunta> categorias = categoriaPreguntaDAO.buscar();

        request.setAttribute("categorias", categorias);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/categorias/listado.jsp").forward(request, response);
    }

    private void viewAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/categorias/anadir-formulario.jsp").forward(request, response);
    }

    private void viewUpdateForm(HttpServletRequest request, HttpServletResponse response, CategoriaPreguntaDAO categoriaPreguntaDAO) throws ServletException, IOException {
        Long id = ServletUtil.getId(request);
        CategoriaPregunta categoria = categoriaPreguntaDAO.buscarPorId(id);

        request.setAttribute("categoria", categoria);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/categorias/editar-formulario.jsp").forward(request, response);
    }

    //Métodos CRUD
    private void addCategoriaPregunta(HttpServletRequest request, CategoriaPreguntaDAO categoriaPreguntaDAO) {
        //Se rescatan los datos
        String contenido = request.getParameter("contenido");
        Long id = ServletUtil.getId(request);

        CategoriaPregunta categoriaPregunta = new CategoriaPregunta(id, contenido);//Se crea el modelo

        categoriaPreguntaDAO.modificar(categoriaPregunta);//se llama al DAO
    }

    private void delete(HttpServletRequest request, CategoriaPreguntaDAO categoriaPreguntaDAO) {
        Long id = ServletUtil.getId(request);
        categoriaPreguntaDAO.eliminar(id);
    }
}

package trivia.game.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trivia.game.DAO.CategoriaPreguntaDAO;
import trivia.game.DAO.PreguntaDAO;
import trivia.game.modelos.CategoriaPregunta;
import trivia.game.modelos.Pregunta;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "preguntas", value = "/preguntas")
public class PreguntasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreguntaDAO preguntaDAO = new PreguntaDAO((Connection) request.getAttribute("conn"));
        CategoriaPreguntaDAO categoriaPreguntaDAO = new CategoriaPreguntaDAO((Connection) request.getAttribute("conn"));

        String view = request.getParameter("view");

        if (view != null) {
            switch (view) {
                case "list":
                    viewList(request, response, preguntaDAO);
                    break;
                case "add-form":
                    viewAddForm(request, response, categoriaPreguntaDAO);
                    break;
                case "update-form":
                    viewUpdateForm(request, response, preguntaDAO, categoriaPreguntaDAO);
                    break;
                case "delete":
                    delete(request, preguntaDAO);
                    viewList(request, response, preguntaDAO);
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
        PreguntaDAO preguntaDAO = new PreguntaDAO((Connection) request.getAttribute("conn"));
        CategoriaPreguntaDAO categoriaPreguntaDAO = new CategoriaPreguntaDAO((Connection) request.getAttribute("conn"));

        String msg = "";
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addPregunta(request, preguntaDAO, categoriaPreguntaDAO);
                    msg = "'\u00a1Pregunta creada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewAddForm(request, response, categoriaPreguntaDAO);
                    break;

                case "update":
                    addPregunta(request, preguntaDAO, categoriaPreguntaDAO);
                    msg = "'\u00a1Pregunta actualizada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewList(request, response, preguntaDAO);
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
    private void viewList(HttpServletRequest request, HttpServletResponse response, PreguntaDAO preguntaDAO) throws ServletException, IOException {
        List<Pregunta> preguntas = preguntaDAO.buscar();

        request.setAttribute("preguntas", preguntas);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/preguntas/listado.jsp").forward(request, response);
    }

    private void viewAddForm(HttpServletRequest request, HttpServletResponse response, CategoriaPreguntaDAO categoriaPreguntaDAO) throws ServletException, IOException {
        List<CategoriaPregunta> categorias = categoriaPreguntaDAO.buscar();

        request.setAttribute("categorias", categorias);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/preguntas/anadir-formulario.jsp").forward(request, response);
    }

    private void viewUpdateForm(HttpServletRequest request, HttpServletResponse response, PreguntaDAO preguntaDAO, CategoriaPreguntaDAO categoriaPreguntaDAO) throws ServletException, IOException {
        Long id = ServletUtil.getId(request);
        Pregunta pregunta = preguntaDAO.buscarPorId(id);

        List<CategoriaPregunta> categorias = categoriaPreguntaDAO.buscar();

        request.setAttribute("categorias", categorias);
        request.setAttribute("pregunta", pregunta);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/preguntas/editar-formulario.jsp").forward(request, response);
    }

    //Métodos CRUD
    private void addPregunta(HttpServletRequest request, PreguntaDAO preguntaDAO, CategoriaPreguntaDAO categoriaPreguntaDAO) {
        //Se rescatan los datos
        Long categoriaId = Long.parseLong(request.getParameter("tagId"));
        String contenido = request.getParameter("contenido");
        Long id = ServletUtil.getId(request);

        //Se busca la categoria
        CategoriaPregunta categoria = categoriaPreguntaDAO.buscarPorId(categoriaId);

        Pregunta pregunta = new Pregunta(id, categoria, contenido);//Se crea el modelo

        preguntaDAO.modificar(pregunta);//se llama al DAO
    }

    private void delete(HttpServletRequest request, PreguntaDAO preguntaDAO) {
        Long id = ServletUtil.getId(request);
        preguntaDAO.eliminar(id);
    }
}

package trivia.game.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trivia.game.DAO.PreguntaDAO;
import trivia.game.DAO.RespuestaDAO;
import trivia.game.DAO.UsuarioRespuestaDAO;
import trivia.game.modelos.Pregunta;
import trivia.game.modelos.Respuesta;
import trivia.game.modelos.UsuarioRespuesta;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "respuestas", value = "/respuestas")
public class RespuestasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespuestaDAO respuestaDAO = new RespuestaDAO((Connection) request.getAttribute("conn"));
        PreguntaDAO preguntaDAO = new PreguntaDAO((Connection) request.getAttribute("conn"));
        UsuarioRespuestaDAO usuarioRespuestaDAO = new UsuarioRespuestaDAO((Connection) request.getAttribute("conn"));

        String view = request.getParameter("view");

        if (view != null) {
            switch (view) {
                case "list":
                    viewList(request, response, respuestaDAO);
                    break;
                case "userAnswers":
                    viewUserAnswers(request, response, usuarioRespuestaDAO);
                    break;
                case "add-form":
                    viewAddForm(request, response, preguntaDAO);
                    break;
                case "update-form":
                    viewUpdateForm(request, response, respuestaDAO, preguntaDAO);
                    break;
                case "delete":
                    delete(request, respuestaDAO);
                    viewList(request, response, respuestaDAO);
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
        RespuestaDAO respuestaDAO = new RespuestaDAO((Connection) request.getAttribute("conn"));
        PreguntaDAO preguntaDAO = new PreguntaDAO((Connection) request.getAttribute("conn"));

        String msg = "";
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addRespuesta(request, respuestaDAO, preguntaDAO);
                    msg = "'\u00a1Respuesta creada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewAddForm(request, response, preguntaDAO);
                    break;

                case "update":
                    addRespuesta(request, respuestaDAO, preguntaDAO);
                    msg = "'\u00a1Respuesta actualizada con \u00e9xito!'";
                    request.setAttribute("msg", msg);

                    viewList(request, response, respuestaDAO);
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
    private void viewList(HttpServletRequest request, HttpServletResponse response, RespuestaDAO respuestaDAO) throws ServletException, IOException {
        List<Respuesta> respuestas = respuestaDAO.buscar();

        request.setAttribute("respuestas", respuestas);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/respuestas/listado.jsp").forward(request, response);
    }

    private void viewUserAnswers(HttpServletRequest request, HttpServletResponse response, UsuarioRespuestaDAO usuarioRespuestaDAO) throws ServletException, IOException {
        List<UsuarioRespuesta> usuarios = usuarioRespuestaDAO.buscar();

        request.setAttribute("usuarios", usuarios);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/respuestas/por-usuarios.jsp").forward(request, response);
    }

    private void viewAddForm(HttpServletRequest request, HttpServletResponse response, PreguntaDAO preguntaDAO) throws ServletException, IOException {
        List<Pregunta> preguntas = preguntaDAO.buscar();

        request.setAttribute("preguntas", preguntas);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/respuestas/anadir-formulario.jsp").forward(request, response);
    }

    private void viewUpdateForm(HttpServletRequest request, HttpServletResponse response, RespuestaDAO respuestaDAO, PreguntaDAO preguntaDAO) throws ServletException, IOException {
        Long id = ServletUtil.getId(request);
        Respuesta respuesta = respuestaDAO.buscarPorId(id);

        List<Pregunta> preguntas = preguntaDAO.buscar();

        request.setAttribute("preguntas", preguntas);
        request.setAttribute("respuesta", respuesta);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard/respuestas/editar-formulario.jsp").forward(request, response);
    }

    //Métodos CRUD
    private void addRespuesta(HttpServletRequest request, RespuestaDAO respuestaDAO, PreguntaDAO preguntaDAO) {
        //Se rescatan los datos
        Long preguntaId = Long.parseLong(request.getParameter("preguntaId"));
        String contenido = request.getParameter("contenido");
        int esCorrecta = Integer.parseInt(request.getParameter("esCorrecta"));
        Long id = ServletUtil.getId(request);

        //Se busca la pregunta
        Pregunta pregunta = preguntaDAO.buscarPorId(preguntaId);

        Respuesta respuesta = new Respuesta(id, pregunta, contenido, esCorrecta);//Se crea el modelo

        respuestaDAO.modificar(respuesta);//se llama al DAO
    }

    private void delete(HttpServletRequest request, RespuestaDAO respuestaDAO) {
        Long id = ServletUtil.getId(request);
        respuestaDAO.eliminar(id);
    }
}

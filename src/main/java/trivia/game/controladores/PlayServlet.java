package trivia.game.controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trivia.game.DAO.PartidaDAO;
import trivia.game.DAO.PreguntaDAO;
import trivia.game.modelos.Partida;
import trivia.game.modelos.PreguntasJuego;
import trivia.game.modelos.Usuario;
import trivia.game.util.LocalDateAdapter;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "play", value = "/play")
public class PlayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreguntaDAO preguntaDAO = new PreguntaDAO((Connection) request.getAttribute("conn"));
        PartidaDAO partidaDAO = new PartidaDAO((Connection) request.getAttribute("conn"));

        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "start":
                    startGame(request, partidaDAO);
                    redirectToJsp(request, response);
                    break;
                case "sendPlayers":
                    out.print(sendPlayers(request));
                    break;
                case "sendQuestions":
                    out.print(sendQuestions(request, preguntaDAO));
                    break;
                case "sendGame":
                    out.print(sendGame(request));
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
        ServletUtil.sendError(response);
    }

    private void redirectToJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/user/play.jsp").forward(req, resp);
    }

    private void startGame(HttpServletRequest request, PartidaDAO partidaDAO) {
        Partida partida = new Partida(setGameTitle(request), LocalDate.now());//Se crea la partida con nombre y fecha

        partidaDAO.modificar(partida);//Se guarda en la base de datos

        partida = partidaDAO
                .buscarPorNombreYFecha
                        (partida.getNombre(), partida.getFechaCreacion());//Se busca la partida para obtener el id asignado

        HttpSession session = request.getSession();//Se obtiene la sesion

        session.setAttribute("partida", partida);//Se guarda la partida en la sesion
    }

    private String sendPlayers(HttpServletRequest req) {
        HttpSession session = req.getSession();//Se obtiene la sesion

        //Se obtienen los datos de los usuarios
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");

        ArrayList<Usuario> usuarios = new ArrayList<>();//Se añaden a una lista

        usuarios.add(usuario1);
        usuarios.add(usuario2);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create();//Se instancia el objeto gson

        return gson.toJson(usuarios);//Se regresa el json
    }

    private String sendQuestions(HttpServletRequest req, PreguntaDAO preguntaDAO) {
        ArrayList<PreguntasJuego> preguntas = preguntaDAO
                .seleccionarPreguntas((Connection) req.getAttribute("conn"));//Se buscan las 10 preguntas para el juego

        Gson gson = new Gson();//Se instancia el objeto gson

        return gson.toJson(preguntas);//Se regresa el json
    }

    private String sendGame(HttpServletRequest request) {
        HttpSession session = request.getSession();//Se obtiene la sesion

        Partida partida = (Partida) session.getAttribute("partida");//Se guardan estos datos en un objeto

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        new LocalDateAdapter().nullSafe()).create();//Se instancia el objeto gson

        return gson.toJson(partida);//Se regresa el json
    }

    private String setGameTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();//Se obtiene la sesion

        //Se obtienen los datos de los usuarios
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");

        return usuario1.getNombre() + " vs " + usuario2.getNombre();//El nombre de la partida será "usuario1 vs usuario2"
    }
}

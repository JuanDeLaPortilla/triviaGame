package trivia.game.controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trivia.game.DAO.*;
import trivia.game.modelos.Partida;
import trivia.game.modelos.PreguntasJuego;
import trivia.game.modelos.Usuario;
import trivia.game.modelos.UsuarioPartida;
import trivia.game.util.LocalDateAdapter;
import trivia.game.util.ServletUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "play", value = "/play")
public class PlayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreguntaDAO preguntaDAO = new PreguntaDAO((Connection) request.getAttribute("conn"));
        PartidaDAO partidaDAO = new PartidaDAO((Connection) request.getAttribute("conn"));
        UsuarioPartidaDAO usuarioPartidaDAO = new UsuarioPartidaDAO((Connection) request.getAttribute("conn"));

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
                case "sendUsersScore":
                    out.print(sendUsersScore(request, usuarioPartidaDAO));
                    break;
                case "goToIndex":
                    destroyGame(request);
                    goToIndex(request, response);
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
        UsuarioPartidaDAO usuarioPartidaDAO = new UsuarioPartidaDAO((Connection) request.getAttribute("conn"));
        UsuarioRespuestaDAO usuarioRespuestaDAO = new UsuarioRespuestaDAO((Connection) request.getAttribute("conn"));
        UsuarioPreguntaDAO usuarioPreguntaDAO = new UsuarioPreguntaDAO((Connection) request.getAttribute("conn"));

        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "getUserAns":
                    out.print(getUserAns(request, usuarioRespuestaDAO, usuarioPreguntaDAO));
                    break;
                case "setUsersScore":
                    out.print(setUsersScore(request, usuarioPartidaDAO, usuarioPreguntaDAO));
                    break;
                default:
                    ServletUtil.sendError(response);
                    break;
            }
        } else {
            ServletUtil.sendError(response);
        }
    }

    private void redirectToJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/user/play.jsp").forward(req, resp);
    }

    private void startGame(HttpServletRequest request, PartidaDAO partidaDAO) {
        Partida partida = new Partida(setGameTitle(request), LocalDate.now());//Se crea la partida con nombre y fecha

        long id = partidaDAO.modificar(partida);//Se guarda en la base de datos

        if (id != 0) {
            partida.setId(id);//se guarda el id creado en el objeto
        }

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

    private String sendUsersScore(HttpServletRequest request, UsuarioPartidaDAO usuarioPartidaDAO) {
        long partidaId = Long.parseLong(request.getParameter("partidaId"));

        List<UsuarioPartida> infoPartida = usuarioPartidaDAO.buscarPorPartida(partidaId);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        new LocalDateAdapter().nullSafe()).create();//Se instancia el objeto gson

        return gson.toJson(infoPartida);//Se regresa el json
    }

    private String getUserAns(HttpServletRequest request, UsuarioRespuestaDAO usuarioRes, UsuarioPreguntaDAO usuarioPr) throws IOException {
        HttpSession session = request.getSession();//Se obtiene la sesion

        //Se obtienen los datos de los usuarios y la partida
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");

        //Se obtiene el json que envio el front
        String json = request.getReader().readLine();

        //Se decodifica el json
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        //Se obtienen los atributos
        long playerId = jsonObject.get("player").getAsInt() == 1 ? usuario1.getId() : usuario2.getId();
        long questionId = jsonObject.get("question").getAsLong();
        long answerId = jsonObject.get("answer").getAsLong();
        long partidaId = jsonObject.get("game").getAsLong();
        int esCorrecta = jsonObject.get("isTrue").getAsInt();

        //Se guarda en la base de datos
        usuarioRes.insertar(playerId, partidaId, questionId, answerId);
        usuarioPr.insertar(playerId, partidaId, questionId, esCorrecta == 0 ? 0 : 100);

        return "SUCCESS";
    }

    private void destroyGame(HttpServletRequest request) {
        HttpSession session = request.getSession();//Se obtiene la sesion
        session.removeAttribute("partida");//Se elimina la partida de la sesion
    }

    private void goToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index").forward(request, response);
    }

    private String setUsersScore(HttpServletRequest request, UsuarioPartidaDAO usuarioPartidaDAO, UsuarioPreguntaDAO usuarioPreguntaDAO) {
        HttpSession session = request.getSession();//Se obtiene la sesion

        //Se obtienen los datos de los usuarios y la partida
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");
        Partida partida = (Partida) session.getAttribute("partida");

        //Se calcula el puntaje de cada jugador
        int puntaje1 = usuarioPreguntaDAO.calcularPuntaje(usuario1.getId(), partida.getId());
        int puntaje2 = usuarioPreguntaDAO.calcularPuntaje(usuario2.getId(), partida.getId());

        //Se guarda en la base de datos
        usuarioPartidaDAO.insertar(usuario1.getId(), partida.getId(), puntaje1);
        usuarioPartidaDAO.insertar(usuario2.getId(), partida.getId(), puntaje2);

        return "SUCCESS";
    }

    private String setGameTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();//Se obtiene la sesion

        //Se obtienen los datos de los usuarios
        Usuario usuario1 = (Usuario) session.getAttribute("usuario1");
        Usuario usuario2 = (Usuario) session.getAttribute("usuario2");

        return usuario1.getNombre() + " vs " + usuario2.getNombre();//El nombre de la partida será "usuario1 vs usuario2"
    }
}

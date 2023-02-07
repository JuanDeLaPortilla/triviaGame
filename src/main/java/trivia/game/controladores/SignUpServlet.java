package trivia.game.controladores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import trivia.game.DAO.UsuarioDAO;
import trivia.game.modelos.Usuario;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "signup", value = "/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (this.signUp(request, response)) {//si se logro registrar el usuario
            request.setAttribute("success", "&iexcl;Usuario registrado con &eacute;xito!");//manda mensaje
            getServletContext().getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);//redirecciona al login
        } else {
            request.setAttribute("error", "&iexcl;Hubo un error al registrar el usuario o el correo ya esta en uso!");//manda mensaje
            getServletContext().getRequestDispatcher("/signup.jsp").forward(request, response);//devuelve a la pagina
        }
    }

    private boolean signUp(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("correo");//Se rescata el correo

        UsuarioDAO usuarioDAO = new UsuarioDAO((Connection) request.getAttribute("conn"));//Se instancia el DAO

        if (usuarioDAO.buscarPorCorreo(correo) != null) {//si el usuario ya esta registrado
            return false;//sale del metodo y da false
        }

        //Sino se rescatan los demas datos del formulario
        String nombre = request.getParameter("nombre");
        String pass = request.getParameter("pass");

        Usuario usuario = new Usuario(nombre, correo, pass, 0);//se crea el usuario

        usuarioDAO.modificar(usuario);//se pasa el usuario al metodo para añadirlo a la db

        return true;
    }
}

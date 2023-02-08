package trivia.game.filtros;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trivia.game.modelos.Usuario;
import trivia.game.util.ServletUtil;

import java.io.IOException;

@WebFilter({"/index", "/ranking"})
public class LoginFiltro implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Usuario usuario1 = ServletUtil.getSessionUser((HttpServletRequest) request, "1");
        Usuario usuario2 = ServletUtil.getSessionUser((HttpServletRequest) request, "2");

        try {
            if ((usuario1 != null && usuario2 != null)) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, ¡necesitas iniciar sesión para ver esta página!");
            }
        } catch (NullPointerException e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, ¡necesitas iniciar sesión para ver esta página!");
        }
    }
}

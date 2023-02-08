package trivia.game.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletUtil {
    public static void sendError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Lo sentimos, el recurso solicitado no está disponible :(");
    }

    public static Long getId(HttpServletRequest request) {
        String idStr;
        try{
            idStr = request.getParameter("id");
        } catch (NullPointerException e){
            idStr = "";
        }

        Long id;
        try{
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e){
            id = 0L;
        }
        return id;
    }
}

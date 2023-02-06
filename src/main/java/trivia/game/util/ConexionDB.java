package trivia.game.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbcpostgresql://localhost:5432/trivia_db?user=postgres&password=admin&ssl=true";
        return DriverManager.getConnection(url);
    }
}

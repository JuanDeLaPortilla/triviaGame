package trivia.game.DAO;

public class ExcepcionSQL extends RuntimeException {
    public ExcepcionSQL(String message) {
        super(message);
    }

    public ExcepcionSQL(String message, Throwable cause) {
        super(message, cause);
    }
}


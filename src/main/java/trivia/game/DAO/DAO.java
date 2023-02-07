package trivia.game.DAO;

import java.util.List;

public interface DAO<T> {
    List<T> buscar();

    T buscarPorId(Long id);

    void modificar(T t);

    void eliminar(Long id);
}
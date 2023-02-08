package trivia.game.DAO;

import trivia.game.modelos.Pregunta;
import trivia.game.util.ExcepcionSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO implements DAO<Pregunta> {
    private Connection conn;

    public PreguntaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Pregunta> buscar() {
        List<Pregunta> preguntas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, cp.categoria_contenido from public.pregunta p inner join categoria_pregunta cp on p.categoria_id = cp.categoria_id")) {
            while (rs.next()) {
                Pregunta pregunta = getPregunta(rs);
                preguntas.add(pregunta);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return preguntas;
    }

    @Override
    public Pregunta buscarPorId(Long id) {
        Pregunta pregunta = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.*, cp.categoria_contenido from public.pregunta p inner join categoria_pregunta cp on p.categoria_id = cp.categoria_id where pregunta_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pregunta = getPregunta(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return pregunta;
    }

    @Override
    public void modificar(Pregunta pregunta) {
        String sql;
        if (pregunta.getId() != null && pregunta.getId() > 0) {
            sql = "update public.pregunta set categoria_id=?, pregunta_contenido=? where pregunta_id=?";
        } else {
            sql = "insert into public.pregunta (categoria_id, pregunta_contenido) values (?,?)";
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, pregunta.getCategoria().getId());
            pst.setString(2, pregunta.getContenido());
            if (pregunta.getId() != null && pregunta.getId() > 0) {
                pst.setLong(3, pregunta.getId());
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.pregunta where pregunta_id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static Pregunta getPregunta(ResultSet rs) throws SQLException {
        Pregunta p = new Pregunta();
        p.setId(rs.getLong("pregunta_id"));
        p.setCategoria(CategoriaPreguntaDAO.getCategoriaPregunta(rs));
        p.setContenido(rs.getString("pregunta_contenido"));
        return p;
    }
}

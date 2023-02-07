package trivia.game.DAO;

import trivia.game.modelos.Respuesta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespuestaDAO implements DAO<Respuesta> {
    private Connection conn;

    public RespuestaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Respuesta> buscar() {
        List<Respuesta> respuestas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.respuesta")) {
            while (rs.next()) {
                Respuesta respuesta = getRespuesta(rs);
                respuestas.add(respuesta);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return respuestas;
    }

    @Override
    public Respuesta buscarPorId(Long id) {
        Respuesta respuesta = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.respuesta where respuesta_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    respuesta = getRespuesta(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return respuesta;
    }

    @Override
    public void modificar(Respuesta respuesta) {
        String sql;
        if (respuesta.getId() != null && respuesta.getId() > 0) {
            sql = "update public.respuesta set pregunta_id=?, respuesta_contenido=?, es_correcta=? where respuesta_id=?";
        } else {
            sql = "insert into public.respuesta (pregunta_id, respuesta_contenido, es_correcta) values (?,?,?)";
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setLong(1, respuesta.getPregunta().getId());
            pst.setString(2, respuesta.getContenido());
            pst.setInt(3,respuesta.getEsCorrecta());
            if (respuesta.getId() != null && respuesta.getId() > 0) {
                pst.setLong(4, respuesta.getId());
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.respuesta where respuesta_id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static Respuesta getRespuesta(ResultSet rs) throws SQLException {
        Respuesta r = new Respuesta();
        r.setId(rs.getLong("respuesta_id"));
        r.setPregunta(PreguntaDAO.getPregunta(rs));
        r.setContenido(rs.getString("respuesta_contenido"));
        r.setEsCorrecta(rs.getInt("es_correcta"));
        return r;
    }
}

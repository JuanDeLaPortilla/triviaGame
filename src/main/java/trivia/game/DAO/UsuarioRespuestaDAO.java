package trivia.game.DAO;

import trivia.game.modelos.UsuarioRespuesta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRespuestaDAO {
    private Connection conn;

    public UsuarioRespuestaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<UsuarioRespuesta> buscar() {
        List<UsuarioRespuesta> usuarioRespuestas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.usuario_respuesta")) {
            while (rs.next()) {
                UsuarioRespuesta usuarioRespuesta = getUsuarioRespuesta(rs);
                usuarioRespuestas.add(usuarioRespuesta);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioRespuestas;
    }

    public List<UsuarioRespuesta> buscarPorId(Long usuarioId, Long partidaId) {
        List<UsuarioRespuesta> usuarioRespuestas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario_respuesta where usuario_id=? and partida_id=?")) {
            stmt.setLong(1, usuarioId);
            stmt.setLong(2, partidaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioRespuesta usuarioRespuesta = getUsuarioRespuesta(rs);
                    usuarioRespuestas.add(usuarioRespuesta);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioRespuestas;
    }

    public List<UsuarioRespuesta> buscarPorUsuario(Long id) {
        List<UsuarioRespuesta> usuarioRespuestas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario_respuesta where usuario_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioRespuesta usuarioRespuesta = getUsuarioRespuesta(rs);
                    usuarioRespuestas.add(usuarioRespuesta);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioRespuestas;
    }

    public void insertar(UsuarioRespuesta usuarioRespuesta) {
        try (PreparedStatement pst = conn.prepareStatement("insert into public.usuario_respuesta (usuario_id, partida_id, pregunta_id, respuesta_id) values (?,?,?,?)")) {
            pst.setLong(1, usuarioRespuesta.getUsuario().getId());
            pst.setLong(2, usuarioRespuesta.getPartida().getId());
            pst.setLong(3, usuarioRespuesta.getPregunta().getId());
            pst.setLong(4, usuarioRespuesta.getRespuesta().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public void actualizar(UsuarioRespuesta usuarioRespuesta) {
        try (PreparedStatement pst = conn.prepareStatement("update public.usuario_respuesta set respuesta_id=? where usuario_id=? and partida_id=? and pregunta_id=?")) {
            pst.setLong(1, usuarioRespuesta.getRespuesta().getId());
            pst.setLong(2, usuarioRespuesta.getPregunta().getId());
            pst.setLong(3, usuarioRespuesta.getUsuario().getId());
            pst.setLong(4, usuarioRespuesta.getPartida().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public void eliminar(UsuarioRespuesta usuarioRespuesta) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.usuario_respuesta where usuario_id=? and partida_id=? and pregunta_id=? and respuesta_id=?")) {
            stmt.setLong(1, usuarioRespuesta.getUsuario().getId());
            stmt.setLong(2, usuarioRespuesta.getPartida().getId());
            stmt.setLong(3, usuarioRespuesta.getPregunta().getId());
            stmt.setLong(4, usuarioRespuesta.getRespuesta().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static UsuarioRespuesta getUsuarioRespuesta(ResultSet rs) throws SQLException {
        UsuarioRespuesta up = new UsuarioRespuesta();
        up.setUsuario(UsuarioDAO.getUsuario(rs));
        up.setPartida(PartidaDAO.getPartida(rs));
        up.setPregunta(PreguntaDAO.getPregunta(rs));
        up.setRespuesta(RespuestaDAO.getRespuesta(rs));
        return up;
    }
}

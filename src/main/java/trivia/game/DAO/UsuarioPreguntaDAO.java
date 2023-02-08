package trivia.game.DAO;

import trivia.game.modelos.UsuarioPregunta;
import trivia.game.util.ExcepcionSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPreguntaDAO {
    private Connection conn;

    public UsuarioPreguntaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<UsuarioPregunta> buscar() {
        List<UsuarioPregunta> usuarioPreguntas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.usuario_pregunta")) {
            while (rs.next()) {
                UsuarioPregunta usuarioPregunta = getUsuarioPregunta(rs);
                usuarioPreguntas.add(usuarioPregunta);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioPreguntas;
    }

    public List<UsuarioPregunta> buscarPorId(Long usuarioId, Long partidaId) {
        List<UsuarioPregunta> usuarioPreguntas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario_pregunta where usuario_id=? and partida_id=?")) {
            stmt.setLong(1, usuarioId);
            stmt.setLong(2, partidaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioPregunta usuarioPregunta = getUsuarioPregunta(rs);
                    usuarioPreguntas.add(usuarioPregunta);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioPreguntas;
    }

    public List<UsuarioPregunta> buscarPorUsuario(Long id) {
        List<UsuarioPregunta> usuarioPreguntas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario_pregunta where usuario_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioPregunta usuarioPregunta = getUsuarioPregunta(rs);
                    usuarioPreguntas.add(usuarioPregunta);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioPreguntas;
    }

    public void insertar(UsuarioPregunta usuarioPregunta) {
        try (PreparedStatement pst = conn.prepareStatement("insert into public.usuario_pregunta (usuario_id, partida_id, pregunta_id, puntaje) values (?,?,?,?)")) {
            pst.setLong(1, usuarioPregunta.getUsuario().getId());
            pst.setLong(2, usuarioPregunta.getPartida().getId());
            pst.setLong(3, usuarioPregunta.getPregunta().getId());
            pst.setInt(4, usuarioPregunta.getPuntaje());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public void actualizar(UsuarioPregunta usuarioPregunta) {
        try (PreparedStatement pst = conn.prepareStatement("update public.usuario_pregunta set puntaje=? where usuario_id=? and partida_id=? and pregunta_id=?")) {
            pst.setInt(1, usuarioPregunta.getPuntaje());
            pst.setLong(2, usuarioPregunta.getUsuario().getId());
            pst.setLong(3, usuarioPregunta.getPartida().getId());
            pst.setLong(4, usuarioPregunta.getPregunta().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public void eliminar(UsuarioPregunta usuarioPregunta) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.usuario_pregunta where usuario_id=? and partida_id=? and pregunta_id=?")) {
            stmt.setLong(1, usuarioPregunta.getUsuario().getId());
            stmt.setLong(2, usuarioPregunta.getPartida().getId());
            stmt.setLong(3, usuarioPregunta.getPregunta().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static UsuarioPregunta getUsuarioPregunta(ResultSet rs) throws SQLException {
        UsuarioPregunta up = new UsuarioPregunta();
        up.setUsuario(UsuarioDAO.getUsuario(rs));
        up.setPartida(PartidaDAO.getPartida(rs));
        up.setPregunta(PreguntaDAO.getPregunta(rs));
        up.setPuntaje(rs.getInt("puntaje"));
        return up;
    }
}

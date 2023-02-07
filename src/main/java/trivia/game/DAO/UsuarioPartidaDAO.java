package trivia.game.DAO;

import trivia.game.modelos.UsuarioPartida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPartidaDAO {
    private Connection conn;

    public UsuarioPartidaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<UsuarioPartida> buscar() {
        List<UsuarioPartida> usuarioPartidas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.usuario_partida")) {
            while (rs.next()) {
                UsuarioPartida usuarioPartida = getUsuarioPartida(rs);
                usuarioPartidas.add(usuarioPartida);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioPartidas;
    }

    public UsuarioPartida buscarPorId(Long usuarioId, Long partidaId) {
        UsuarioPartida usuarioPartida = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario_partida where usuario_id=? and partida_id=?")) {
            stmt.setLong(1, usuarioId);
            stmt.setLong(2, partidaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuarioPartida = getUsuarioPartida(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioPartida;
    }

    public List<UsuarioPartida> buscarPorUsuario(Long id) {
        List<UsuarioPartida> usuarioPartidas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario_partida where usuario_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsuarioPartida usuarioPartida = getUsuarioPartida(rs);
                    usuarioPartidas.add(usuarioPartida);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarioPartidas;
    }

    public void insertar(UsuarioPartida usuarioPartida) {
        try (PreparedStatement pst = conn.prepareStatement("insert into public.usuario_partida (usuario_id, partida_id, puntaje) values (?,?,?)")) {
            pst.setLong(1, usuarioPartida.getUsuario().getId());
            pst.setLong(2, usuarioPartida.getPartida().getId());
            pst.setInt(3, usuarioPartida.getPuntaje());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public void actualizar(UsuarioPartida usuarioPartida) {
        try (PreparedStatement pst = conn.prepareStatement("update public.usuario_partida set puntaje=? where usuario_id=? and partida_id=?")) {
            pst.setInt(1, usuarioPartida.getPuntaje());
            pst.setLong(2, usuarioPartida.getUsuario().getId());
            pst.setLong(3, usuarioPartida.getPartida().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public void eliminar(Long usuarioId, Long partidaId) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.usuario_partida where usuario_id=? and partida_id=?")) {
            stmt.setLong(1, usuarioId);
            stmt.setLong(2, partidaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static UsuarioPartida getUsuarioPartida(ResultSet rs) throws SQLException {
        UsuarioPartida up = new UsuarioPartida();
        up.setUsuario(UsuarioDAO.getUsuario(rs));
        up.setPartida(PartidaDAO.getPartida(rs));
        up.setPuntaje(rs.getInt("puntaje"));
        return up;
    }
}

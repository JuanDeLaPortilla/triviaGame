package trivia.game.DAO;

import trivia.game.modelos.Partida;
import trivia.game.modelos.Usuario;
import trivia.game.modelos.UsuarioPartida;
import trivia.game.util.ExcepcionSQL;

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
             ResultSet rs = stmt.executeQuery("SELECT up.*, u.usuario_nombre, p.partida_nombre from public.usuario_partida up inner join usuario u on up.usuario_id = u.usuario_id inner join partida p on up.partida_id = p.partida_id")) {
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
        try (PreparedStatement stmt = conn.prepareStatement("SELECT up.*, u.usuario_nombre, p.partida_nombre from public.usuario_partida up inner join usuario u on up.usuario_id = u.usuario_id inner join partida p on up.partida_id = p.partida_id where usuario_id=? and partida_id=?")) {
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
        Usuario u = new Usuario();
        Partida p = new Partida();

        u.setId(rs.getLong("usuario_id"));
        u.setNombre(rs.getString("usuario_nombre"));

        p.setId(rs.getLong("partida_id"));
        p.setNombre(rs.getString("partida_nombre"));

        up.setUsuario(u);
        up.setPartida(p);
        up.setPuntaje(rs.getInt("puntaje"));
        return up;
    }
}

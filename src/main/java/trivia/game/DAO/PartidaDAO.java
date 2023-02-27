package trivia.game.DAO;

import trivia.game.modelos.Partida;
import trivia.game.util.ExcepcionSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {
    private Connection conn;

    public PartidaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Partida> buscar() {
        List<Partida> partidas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.partida")) {
            while (rs.next()) {
                Partida partida = getPartida(rs);
                partidas.add(partida);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return partidas;
    }

    public Partida buscarPorId(Long id) {
        Partida partida = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.partida where partida_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    partida = getPartida(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return partida;
    }

    public long modificar(Partida partida) {
        long id = 0L;
        String sql;
        if (partida.getId() != null && partida.getId() > 0) {
            sql = "update public.partida set partida_nombre=?, partida_fecha=? where partida_id=?";
        } else {
            sql = "insert into public.partida (partida_nombre, partida_fecha) values (?,?) returning partida_id";
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, partida.getNombre());
            pst.setDate(2, Date.valueOf(partida.getFechaCreacion()));
            if (partida.getId() != null && partida.getId() > 0) {
                pst.setLong(3, partida.getId());
            }

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    id = rs.getLong("partida_id");
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return id;
    }

    public void eliminar(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.partida where partida_id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public long contarPorMes() {
        long partidas = 0;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery
                     ("SELECT count(partida_id) as partidas from public.partida where extract(month from partida_fecha) " +
                             "= extract(month from current_date)")) {
            if (rs.next()) {
                partidas = rs.getLong("partidas");
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }

        return partidas;
    }

    public static Partida getPartida(ResultSet rs) throws SQLException {
        Partida p = new Partida();
        p.setId(rs.getLong("partida_id"));
        p.setNombre(rs.getString("partida_nombre"));
        p.setFechaCreacion(rs.getDate("partida_fecha").toLocalDate());
        return p;
    }
}

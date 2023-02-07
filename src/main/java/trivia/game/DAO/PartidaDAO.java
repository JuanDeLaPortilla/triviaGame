package trivia.game.DAO;

import trivia.game.modelos.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO implements DAO<Partida> {
    private Connection conn;

    public PartidaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
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

    @Override
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

    @Override
    public void modificar(Partida partida) {
        String sql;
        if (partida.getId() != null && partida.getId() > 0) {
            sql = "update public.partida set partida_nombre=?, partida_fecha=? where partida_id=?";
        } else {
            sql = "insert into public.partida (partida_nombre, partida_fecha) values (?,?)";
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, partida.getNombre());
            pst.setDate(2, Date.valueOf(partida.getFechaCreacion()));
            if (partida.getId() != null && partida.getId() > 0) {
                pst.setLong(3, partida.getId());
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.partida where partida_id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static Partida getPartida(ResultSet rs) throws SQLException {
        Partida p = new Partida();
        p.setId(rs.getLong("partida_id"));
        p.setNombre(rs.getString("partida_nombre"));
        p.setFechaCreacion(rs.getDate("partida_fecha").toLocalDate());
        return p;
    }
}

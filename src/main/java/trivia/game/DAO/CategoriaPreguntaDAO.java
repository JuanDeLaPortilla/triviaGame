package trivia.game.DAO;

import trivia.game.modelos.CategoriaPregunta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaPreguntaDAO implements DAO<CategoriaPregunta> {
    private Connection conn;

    public CategoriaPreguntaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<CategoriaPregunta> buscar() {
        List<CategoriaPregunta> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.categoria_pregunta")) {
            while (rs.next()) {
                CategoriaPregunta categoria = getCategoriaPregunta(rs);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return categorias;
    }

    @Override
    public CategoriaPregunta buscarPorId(Long id) {
        CategoriaPregunta categoria = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.categoria_pregunta where categoria_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = getCategoriaPregunta(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return categoria;
    }

    @Override
    public void modificar(CategoriaPregunta categoria) {
        String sql;
        if (categoria.getId() != null && categoria.getId() > 0) {
            sql = "update public.categoria_pregunta set categoria_contenido where categoria_id=?";
        } else {
            sql = "insert into public.categoria_pregunta (categoria_contenido) values (?)";
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, categoria.getContenido());
            if (categoria.getId() != null && categoria.getId() > 0) {
                pst.setLong(2, categoria.getId());
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.categoria_pregunta where categoria_id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public static CategoriaPregunta getCategoriaPregunta(ResultSet rs) throws SQLException {
        CategoriaPregunta c = new CategoriaPregunta();
        c.setId(rs.getLong("categoria_id"));
        c.setContenido(rs.getString("categoria_contenido"));
        return c;
    }
}

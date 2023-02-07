package trivia.game.DAO;

import trivia.game.modelos.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Usuario> buscar() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from public.usuario")) {
            while (rs.next()) {
                Usuario usuario = getUsuario(rs);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuarios;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario where usuario_id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuario;
    }

    public Usuario buscarPorCorreo(String correo) {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from public.usuario where usuario_correo=?")) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
        return usuario;
    }

    @Override
    public void modificar(Usuario usuario) {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0) {
            sql = "update public.usuario set usuario_nombre=?, usuario_correo=?, usuario_pass=?, es_admin=? where usuario_id=?";
        } else {
            sql = "insert into public.usuario (usuario_nombre, usuario_correo, usuario_pass, es_admin) values (?,?,?,?)";
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getCorreo());
            pst.setString(3, usuario.getPass());
            pst.setInt(4, usuario.getEsAdmin());
            if(usuario.getId() != null && usuario.getId() > 0){
                pst.setLong(5,usuario.getId());
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("delete from public.usuario where usuario_id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }
    }

    public Usuario login(String correo, String pass) {
        Usuario usuario = null;
        PreparedStatement pst;

        try {
            String stmt = "select * from public.usuario where usuario_correo=? and usuario_pass=?";
            pst = conn.prepareStatement(stmt);
            pst.setString(1, correo);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                usuario = getUsuario(rs);
            }
        } catch (SQLException e) {
            throw new ExcepcionSQL(e.getMessage(), e.getCause());
        }

        return usuario;
    }

    public static Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("usuario_id"));
        u.setNombre(rs.getString("usuario_nombre"));
        u.setCorreo(rs.getString("usuario_correo"));
        u.setPass(rs.getString("usuario_pass"));
        u.setEsAdmin(rs.getInt("es_admin"));
        return u;
    }
}

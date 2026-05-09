package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Usuario;

public class UsuarioDAO {

    public Usuario validarLogin(String usuario, String contrasena) {

        Usuario usuarioEncontrado = null;

        String sql = """
                     SELECT 
                        u.id_usuario,
                        u.nombres,
                        u.apellidos,
                        u.usuario,
                        u.contrasena,
                        u.id_rol,
                        r.nombre_rol,
                        u.area_seccion,
                        u.estado
                     FROM usuarios u
                     INNER JOIN roles r ON u.id_rol = r.id_rol
                     WHERE u.usuario = ?
                     AND u.contrasena = ?
                     AND u.estado = 'Activo'
                     """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuarioEncontrado = new Usuario();

                usuarioEncontrado.setIdUsuario(rs.getInt("id_usuario"));
                usuarioEncontrado.setNombres(rs.getString("nombres"));
                usuarioEncontrado.setApellidos(rs.getString("apellidos"));
                usuarioEncontrado.setUsuario(rs.getString("usuario"));
                usuarioEncontrado.setContrasena(rs.getString("contrasena"));
                usuarioEncontrado.setIdRol(rs.getInt("id_rol"));
                usuarioEncontrado.setNombreRol(rs.getString("nombre_rol"));
                usuarioEncontrado.setAreaSeccion(rs.getString("area_seccion"));
                usuarioEncontrado.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("Error al validar login: " + e.getMessage());
        }

        return usuarioEncontrado;
    }

    public boolean guardar(Usuario usuario) {

        String sql = "INSERT INTO usuarios "
                + "(nombres, apellidos, usuario, contrasena, id_rol, area_seccion, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getUsuario());
            ps.setString(4, usuario.getContrasena());
            ps.setInt(5, usuario.getIdRol());
            ps.setString(6, usuario.getAreaSeccion());
            ps.setString(7, usuario.getEstado());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> listar() {

        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = """
                     SELECT 
                        u.id_usuario,
                        u.nombres,
                        u.apellidos,
                        u.usuario,
                        u.contrasena,
                        u.id_rol,
                        r.nombre_rol,
                        u.area_seccion,
                        u.estado
                     FROM usuarios u
                     INNER JOIN roles r ON u.id_rol = r.id_rol
                     ORDER BY u.id_usuario DESC
                     """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                usuario.setAreaSeccion(rs.getString("area_seccion"));
                usuario.setEstado(rs.getString("estado"));

                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    public boolean existeUsuario(String usuario) {

        String sql = "SELECT COUNT(*) AS total FROM usuarios WHERE usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
        }

        return false;
    }

    public boolean existeUsuarioEnOtroRegistro(String usuario, int idUsuario) {

        String sql = "SELECT COUNT(*) AS total FROM usuarios WHERE usuario = ? AND id_usuario <> ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario);
            ps.setInt(2, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar usuario en otro registro: " + e.getMessage());
        }

        return false;
    }

    public boolean actualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET "
                + "nombres = ?, "
                + "apellidos = ?, "
                + "usuario = ?, "
                + "id_rol = ?, "
                + "area_seccion = ?, "
                + "estado = ? "
                + "WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getUsuario());
            ps.setInt(4, usuario.getIdRol());
            ps.setString(5, usuario.getAreaSeccion());
            ps.setString(6, usuario.getEstado());
            ps.setInt(7, usuario.getIdUsuario());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean restablecerContrasena(int idUsuario, String nuevaContrasena) {

        String sql = "UPDATE usuarios SET contrasena = ? WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, nuevaContrasena);
            ps.setInt(2, idUsuario);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al restablecer contraseña: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarEstado(int idUsuario, String nuevoEstado) {

        String sql = "UPDATE usuarios SET estado = ? WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idUsuario);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado del usuario: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> listarUsuariosPrestamo() {

        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = """
                     SELECT 
                        u.id_usuario,
                        u.nombres,
                        u.apellidos,
                        u.usuario,
                        u.contrasena,
                        u.id_rol,
                        r.nombre_rol,
                        u.area_seccion,
                        u.estado
                     FROM usuarios u
                     INNER JOIN roles r ON u.id_rol = r.id_rol
                     WHERE u.estado = 'Activo'
                     AND r.nombre_rol IN ('Profesor', 'Alumno')
                     ORDER BY u.nombres ASC
                     """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                usuario.setAreaSeccion(rs.getString("area_seccion"));
                usuario.setEstado(rs.getString("estado"));

                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios para préstamo: " + e.getMessage());
        }

        return lista;
    }
}
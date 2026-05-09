package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Ejemplar;

public class EjemplarDAO {

    public boolean guardar(Ejemplar ejemplar) {

        String sql = "INSERT INTO ejemplares "
                + "(id_documento, codigo_ejemplar, estado, fecha_registro, observaciones) "
                + "VALUES (?, ?, ?, CURDATE(), ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, ejemplar.getIdDocumento());
            ps.setString(2, ejemplar.getCodigoEjemplar());
            ps.setString(3, ejemplar.getEstado());
            ps.setString(4, ejemplar.getObservaciones());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar ejemplar: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Ejemplar ejemplar) {

        String sql = "UPDATE ejemplares SET "
                + "id_documento = ?, "
                + "codigo_ejemplar = ?, "
                + "estado = ?, "
                + "observaciones = ? "
                + "WHERE id_ejemplar = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, ejemplar.getIdDocumento());
            ps.setString(2, ejemplar.getCodigoEjemplar());
            ps.setString(3, ejemplar.getEstado());
            ps.setString(4, ejemplar.getObservaciones());
            ps.setInt(5, ejemplar.getIdEjemplar());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar ejemplar: " + e.getMessage());
            return false;
        }
    }

    public boolean tienePrestamos(int idEjemplar) {

        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE id_ejemplar = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idEjemplar);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar préstamos del ejemplar: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int idEjemplar) {

        if (tienePrestamos(idEjemplar)) {
            System.out.println("No se puede eliminar el ejemplar porque tiene préstamos registrados.");
            return false;
        }

        String sql = "DELETE FROM ejemplares WHERE id_ejemplar = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idEjemplar);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar ejemplar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Ejemplar> listar() {

        ArrayList<Ejemplar> lista = new ArrayList<>();

        String sql = "SELECT "
                + "IFNULL(e.id_ejemplar, 0) AS id_ejemplar, "
                + "d.id_documento, "
                + "d.titulo, "
                + "td.nombre_tipo, "
                + "d.categoria, "
                + "IFNULL(e.codigo_ejemplar, '') AS codigo_ejemplar, "
                + "IFNULL(e.estado, 'Sin ejemplar') AS estado, "
                + "IFNULL(e.fecha_registro, '') AS fecha_registro, "
                + "IFNULL(e.observaciones, '') AS observaciones "
                + "FROM documentos d "
                + "INNER JOIN tipos_documento td ON d.id_tipo_documento = td.id_tipo_documento "
                + "LEFT JOIN ejemplares e ON d.id_documento = e.id_documento "
                + "ORDER BY d.titulo ASC, e.codigo_ejemplar ASC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Ejemplar ejemplar = new Ejemplar();

                ejemplar.setIdEjemplar(rs.getInt("id_ejemplar"));
                ejemplar.setIdDocumento(rs.getInt("id_documento"));
                ejemplar.setTituloDocumento(rs.getString("titulo"));
                ejemplar.setTipoDocumento(rs.getString("nombre_tipo"));
                ejemplar.setCategoriaDocumento(rs.getString("categoria"));
                ejemplar.setCodigoEjemplar(rs.getString("codigo_ejemplar"));
                ejemplar.setEstado(rs.getString("estado"));
                ejemplar.setFechaRegistro(rs.getString("fecha_registro"));
                ejemplar.setObservaciones(rs.getString("observaciones"));

                lista.add(ejemplar);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ejemplares: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Ejemplar> listarDisponibles() {

        ArrayList<Ejemplar> lista = new ArrayList<>();

        String sql = "SELECT "
                + "e.id_ejemplar, "
                + "e.id_documento, "
                + "d.titulo, "
                + "td.nombre_tipo, "
                + "d.categoria, "
                + "e.codigo_ejemplar, "
                + "e.estado, "
                + "e.fecha_registro, "
                + "e.observaciones "
                + "FROM ejemplares e "
                + "INNER JOIN documentos d ON e.id_documento = d.id_documento "
                + "INNER JOIN tipos_documento td ON d.id_tipo_documento = td.id_tipo_documento "
                + "WHERE e.estado = 'Disponible' "
                + "ORDER BY d.titulo ASC, e.codigo_ejemplar ASC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Ejemplar ejemplar = new Ejemplar();

                ejemplar.setIdEjemplar(rs.getInt("id_ejemplar"));
                ejemplar.setIdDocumento(rs.getInt("id_documento"));
                ejemplar.setTituloDocumento(rs.getString("titulo"));
                ejemplar.setTipoDocumento(rs.getString("nombre_tipo"));
                ejemplar.setCategoriaDocumento(rs.getString("categoria"));
                ejemplar.setCodigoEjemplar(rs.getString("codigo_ejemplar"));
                ejemplar.setEstado(rs.getString("estado"));
                ejemplar.setFechaRegistro(rs.getString("fecha_registro"));
                ejemplar.setObservaciones(rs.getString("observaciones"));

                lista.add(ejemplar);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ejemplares disponibles: " + e.getMessage());
        }

        return lista;
    }
}
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConfiguracionDAO {

    public ArrayList<Object[]> listarConfiguracionPrestamo() {
        ArrayList<Object[]> lista = new ArrayList<>();

        String sql = """
                     SELECT 
                        r.id_rol,
                        r.nombre_rol,
                        IFNULL(cp.max_ejemplares, 0) AS max_ejemplares,
                        IFNULL(cp.dias_prestamo, 0) AS dias_prestamo
                     FROM roles r
                     LEFT JOIN configuracion_prestamo cp ON r.id_rol = cp.id_rol
                     WHERE r.nombre_rol IN ('Profesor', 'Alumno')
                     ORDER BY r.id_rol ASC
                     """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Object[] fila = new Object[4];

                fila[0] = rs.getInt("id_rol");
                fila[1] = rs.getString("nombre_rol");
                fila[2] = rs.getInt("max_ejemplares");
                fila[3] = rs.getInt("dias_prestamo");

                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar configuración de préstamo: " + e.getMessage());
        }

        return lista;
    }

    public boolean guardarConfiguracionPrestamo(int idRol, int maxEjemplares, int diasPrestamo) {
        String sqlExiste = "SELECT COUNT(*) AS total FROM configuracion_prestamo WHERE id_rol = ?";
        String sqlActualizar = "UPDATE configuracion_prestamo SET max_ejemplares = ?, dias_prestamo = ? WHERE id_rol = ?";
        String sqlInsertar = "INSERT INTO configuracion_prestamo (id_rol, max_ejemplares, dias_prestamo) VALUES (?, ?, ?)";

        try (Connection conexion = Conexion.conectar()) {

            boolean existe = false;

            try (PreparedStatement ps = conexion.prepareStatement(sqlExiste)) {
                ps.setInt(1, idRol);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        existe = rs.getInt("total") > 0;
                    }
                }
            }

            if (existe) {
                try (PreparedStatement ps = conexion.prepareStatement(sqlActualizar)) {
                    ps.setInt(1, maxEjemplares);
                    ps.setInt(2, diasPrestamo);
                    ps.setInt(3, idRol);
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conexion.prepareStatement(sqlInsertar)) {
                    ps.setInt(1, idRol);
                    ps.setInt(2, maxEjemplares);
                    ps.setInt(3, diasPrestamo);
                    ps.executeUpdate();
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar configuración de préstamo: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Object[]> listarMora() {
        ArrayList<Object[]> lista = new ArrayList<>();

        String sql = "SELECT id_configuracion_mora, anio, mora_diaria FROM configuracion_mora ORDER BY anio DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Object[] fila = new Object[3];

                fila[0] = rs.getInt("id_configuracion_mora");
                fila[1] = rs.getInt("anio");
                fila[2] = rs.getDouble("mora_diaria");

                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar mora: " + e.getMessage());
        }

        return lista;
    }

    public boolean guardarMora(int anio, double moraDiaria) {
        String sqlExiste = "SELECT COUNT(*) AS total FROM configuracion_mora WHERE anio = ?";
        String sqlActualizar = "UPDATE configuracion_mora SET mora_diaria = ? WHERE anio = ?";
        String sqlInsertar = "INSERT INTO configuracion_mora (anio, mora_diaria) VALUES (?, ?)";

        try (Connection conexion = Conexion.conectar()) {

            boolean existe = false;

            try (PreparedStatement ps = conexion.prepareStatement(sqlExiste)) {
                ps.setInt(1, anio);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        existe = rs.getInt("total") > 0;
                    }
                }
            }

            if (existe) {
                try (PreparedStatement ps = conexion.prepareStatement(sqlActualizar)) {
                    ps.setDouble(1, moraDiaria);
                    ps.setInt(2, anio);
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conexion.prepareStatement(sqlInsertar)) {
                    ps.setInt(1, anio);
                    ps.setDouble(2, moraDiaria);
                    ps.executeUpdate();
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar mora: " + e.getMessage());
            return false;
        }
    }
}
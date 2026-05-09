package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Prestamo;

public class PrestamoDAO {

    public boolean registrarPrestamo(int idUsuario, int idEjemplar) {

        Connection conexion = null;

        try {
            conexion = Conexion.conectar();
            conexion.setAutoCommit(false);

            if (!ejemplarDisponible(conexion, idEjemplar)) {
                conexion.rollback();
                return false;
            }

            int idRol = obtenerRolUsuario(conexion, idUsuario);

            if (idRol == 0) {
                conexion.rollback();
                return false;
            }

            int maxEjemplares = obtenerMaxEjemplares(conexion, idRol);
            int diasPrestamo = obtenerDiasPrestamo(conexion, idRol);

            int prestamosActivos = contarPrestamosActivos(conexion, idUsuario);

            if (prestamosActivos >= maxEjemplares) {
                conexion.rollback();
                return false;
            }

            String sqlPrestamo = "INSERT INTO prestamos "
                    + "(id_usuario, id_ejemplar, fecha_prestamo, fecha_devolucion_estimada, estado, mora_calculada) "
                    + "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL ? DAY), 'Activo', 0)";

            try (PreparedStatement ps = conexion.prepareStatement(sqlPrestamo)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idEjemplar);
                ps.setInt(3, diasPrestamo);
                ps.executeUpdate();
            }

            String sqlEjemplar = "UPDATE ejemplares SET estado = 'Prestado' WHERE id_ejemplar = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sqlEjemplar)) {
                ps.setInt(1, idEjemplar);
                ps.executeUpdate();
            }

            conexion.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());

            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }

            return false;

        } finally {
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    public boolean registrarDevolucion(int idPrestamo) {

        Connection conexion = null;

        try {
            conexion = Conexion.conectar();
            conexion.setAutoCommit(false);

            int idEjemplar = 0;
            int diasAtraso = 0;
            double moraDiaria = 0;
            double moraCalculada;

            String sqlDatos = "SELECT "
                    + "id_ejemplar, "
                    + "GREATEST(DATEDIFF(CURDATE(), fecha_devolucion_estimada), 0) AS dias_atraso "
                    + "FROM prestamos "
                    + "WHERE id_prestamo = ? "
                    + "AND estado = 'Activo'";

            try (PreparedStatement ps = conexion.prepareStatement(sqlDatos)) {
                ps.setInt(1, idPrestamo);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idEjemplar = rs.getInt("id_ejemplar");
                        diasAtraso = rs.getInt("dias_atraso");
                    } else {
                        conexion.rollback();
                        return false;
                    }
                }
            }

            String sqlMora = "SELECT mora_diaria "
                    + "FROM configuracion_mora "
                    + "WHERE anio = YEAR(CURDATE())";

            try (PreparedStatement ps = conexion.prepareStatement(sqlMora);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    moraDiaria = rs.getDouble("mora_diaria");
                }
            }

            moraCalculada = diasAtraso * moraDiaria;

            String sqlDevolucion = "UPDATE prestamos SET "
                    + "fecha_devolucion_real = CURDATE(), "
                    + "estado = 'Devuelto', "
                    + "mora_calculada = ? "
                    + "WHERE id_prestamo = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sqlDevolucion)) {
                ps.setDouble(1, moraCalculada);
                ps.setInt(2, idPrestamo);
                ps.executeUpdate();
            }

            String sqlEjemplar = "UPDATE ejemplares SET estado = 'Disponible' WHERE id_ejemplar = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sqlEjemplar)) {
                ps.setInt(1, idEjemplar);
                ps.executeUpdate();
            }

            conexion.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar devolución: " + e.getMessage());

            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }

            return false;

        } finally {
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    public ArrayList<Prestamo> listarActivos() {

        ArrayList<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT "
                + "p.id_prestamo, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_usuario, "
                + "e.codigo_ejemplar, "
                + "d.titulo, "
                + "p.fecha_prestamo, "
                + "p.fecha_devolucion_estimada, "
                + "IFNULL(p.fecha_devolucion_real, '') AS fecha_devolucion_real, "
                + "p.estado, "
                + "IFNULL(p.mora_calculada, 0) AS mora_calculada "
                + "FROM prestamos p "
                + "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario "
                + "INNER JOIN ejemplares e ON p.id_ejemplar = e.id_ejemplar "
                + "INNER JOIN documentos d ON e.id_documento = d.id_documento "
                + "WHERE p.estado = 'Activo' "
                + "ORDER BY p.fecha_prestamo DESC, p.id_prestamo DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Prestamo prestamo = new Prestamo();

                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setNombreUsuario(rs.getString("nombre_usuario"));
                prestamo.setCodigoEjemplar(rs.getString("codigo_ejemplar"));
                prestamo.setTituloDocumento(rs.getString("titulo"));
                prestamo.setFechaPrestamo(rs.getString("fecha_prestamo"));
                prestamo.setFechaDevolucionEstimada(rs.getString("fecha_devolucion_estimada"));
                prestamo.setFechaDevolucionReal(rs.getString("fecha_devolucion_real"));
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setMoraCalculada(rs.getDouble("mora_calculada"));

                lista.add(prestamo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar préstamos activos: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Prestamo> listarHistorial() {

        ArrayList<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT "
                + "p.id_prestamo, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_usuario, "
                + "e.codigo_ejemplar, "
                + "d.titulo, "
                + "p.fecha_prestamo, "
                + "p.fecha_devolucion_estimada, "
                + "IFNULL(p.fecha_devolucion_real, '') AS fecha_devolucion_real, "
                + "p.estado, "
                + "IFNULL(p.mora_calculada, 0) AS mora_calculada "
                + "FROM prestamos p "
                + "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario "
                + "INNER JOIN ejemplares e ON p.id_ejemplar = e.id_ejemplar "
                + "INNER JOIN documentos d ON e.id_documento = d.id_documento "
                + "ORDER BY p.fecha_prestamo DESC, p.id_prestamo DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Prestamo prestamo = new Prestamo();

                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setNombreUsuario(rs.getString("nombre_usuario"));
                prestamo.setCodigoEjemplar(rs.getString("codigo_ejemplar"));
                prestamo.setTituloDocumento(rs.getString("titulo"));
                prestamo.setFechaPrestamo(rs.getString("fecha_prestamo"));
                prestamo.setFechaDevolucionEstimada(rs.getString("fecha_devolucion_estimada"));
                prestamo.setFechaDevolucionReal(rs.getString("fecha_devolucion_real"));
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setMoraCalculada(rs.getDouble("mora_calculada"));

                lista.add(prestamo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar historial de préstamos: " + e.getMessage());
        }

        return lista;
    }

    private boolean ejemplarDisponible(Connection conexion, int idEjemplar) throws SQLException {

        String sql = "SELECT estado FROM ejemplares WHERE id_ejemplar = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idEjemplar);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("estado").equalsIgnoreCase("Disponible");
                }
            }
        }

        return false;
    }

    private int obtenerRolUsuario(Connection conexion, int idUsuario) throws SQLException {

        String sql = "SELECT id_rol FROM usuarios WHERE id_usuario = ? AND estado = 'Activo'";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_rol");
                }
            }
        }

        return 0;
    }

    private int obtenerMaxEjemplares(Connection conexion, int idRol) throws SQLException {

        String sql = "SELECT max_ejemplares FROM configuracion_prestamo WHERE id_rol = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("max_ejemplares");
                }
            }
        }

        if (idRol == 2) {
            return 5;
        } else if (idRol == 3) {
            return 3;
        }

        return 1;
    }

    private int obtenerDiasPrestamo(Connection conexion, int idRol) throws SQLException {

        String sql = "SELECT dias_prestamo FROM configuracion_prestamo WHERE id_rol = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("dias_prestamo");
                }
            }
        }

        if (idRol == 2) {
            return 15;
        } else if (idRol == 3) {
            return 8;
        }

        return 7;
    }

    private int contarPrestamosActivos(Connection conexion, int idUsuario) throws SQLException {

        String sql = "SELECT COUNT(*) AS total "
                + "FROM prestamos "
                + "WHERE id_usuario = ? "
                + "AND estado = 'Activo'";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }

        return 0;
    }
    
    public Object[] obtenerCalculoMora(int idPrestamo) {

    Object[] datos = new Object[5];

    datos[0] = "";
    datos[1] = "";
    datos[2] = 0;
    datos[3] = 0.0;
    datos[4] = 0.0;

    String sql = "SELECT "
            + "p.fecha_devolucion_estimada, "
            + "CURDATE() AS fecha_actual, "
            + "GREATEST(DATEDIFF(CURDATE(), p.fecha_devolucion_estimada), 0) AS dias_atraso, "
            + "IFNULL(cm.mora_diaria, 0) AS mora_diaria, "
            + "(GREATEST(DATEDIFF(CURDATE(), p.fecha_devolucion_estimada), 0) * IFNULL(cm.mora_diaria, 0)) AS mora_total "
            + "FROM prestamos p "
            + "LEFT JOIN configuracion_mora cm ON cm.anio = YEAR(CURDATE()) "
            + "WHERE p.id_prestamo = ?";

    try (
        Connection conexion = Conexion.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql)
    ) {

        ps.setInt(1, idPrestamo);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                datos[0] = rs.getString("fecha_devolucion_estimada");
                datos[1] = rs.getString("fecha_actual");
                datos[2] = rs.getInt("dias_atraso");
                datos[3] = rs.getDouble("mora_diaria");
                datos[4] = rs.getDouble("mora_total");
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener cálculo de mora: " + e.getMessage());
    }

    return datos;
}
}
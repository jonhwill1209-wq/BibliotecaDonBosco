package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadisticaDAO {

    public int contarUsuarios() {
        return contar("SELECT COUNT(*) AS total FROM usuarios");
    }

    public int contarDocumentos() {
        return contar("SELECT COUNT(*) AS total FROM documentos");
    }

    public int contarEjemplaresDisponibles() {
        return contar("SELECT COUNT(*) AS total FROM ejemplares WHERE estado = 'Disponible'");
    }

    public int contarEjemplaresPrestados() {
        return contar("SELECT COUNT(*) AS total FROM ejemplares WHERE estado = 'Prestado'");
    }

    public int contarPrestamosActivos() {
        return contar("SELECT COUNT(*) AS total FROM prestamos WHERE estado = 'Activo'");
    }

    public int contarPrestamosDevueltos() {
        return contar("SELECT COUNT(*) AS total FROM prestamos WHERE estado = 'Devuelto'");
    }

    private int contar(String sql) {
        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estadística: " + e.getMessage());
        }

        return 0;
    }
}
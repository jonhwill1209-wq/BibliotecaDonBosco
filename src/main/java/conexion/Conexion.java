package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_donbosco?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexión exitosa a MySQL.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con MySQL: " + e.getMessage());
        }

        return conexion;
    }
}
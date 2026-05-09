package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDAO {

    public ArrayList<String> listarCategorias() {
        ArrayList<String> lista = new ArrayList<>();

        String sql = """
                     SELECT nombre_categoria AS categoria
                     FROM categorias_documento

                     UNION

                     SELECT categoria
                     FROM documentos
                     WHERE categoria IS NOT NULL
                     AND TRIM(categoria) <> ''

                     ORDER BY categoria ASC
                     """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("categoria"));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }

        return lista;
    }

    public boolean existeCategoria(String categoria) {
        String sql = "SELECT COUNT(*) AS total FROM categorias_documento WHERE nombre_categoria = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, categoria);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar categoría: " + e.getMessage());
        }

        return false;
    }

    public boolean guardarCategoria(String categoria) {
        String sql = "INSERT INTO categorias_documento (nombre_categoria) VALUES (?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, categoria);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar categoría: " + e.getMessage());
            return false;
        }
    }
}
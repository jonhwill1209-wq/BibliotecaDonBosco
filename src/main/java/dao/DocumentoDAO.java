package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Documento;

public class DocumentoDAO {

    public boolean guardar(Documento documento) {

        String sql = "INSERT INTO documentos "
                + "(titulo, autor, editorial, anio_publicacion, id_tipo_documento, categoria, ubicacion, descripcion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, documento.getTitulo());
            ps.setString(2, documento.getAutor());
            ps.setString(3, documento.getEditorial());
            ps.setInt(4, documento.getAnioPublicacion());
            ps.setInt(5, documento.getIdTipoDocumento());
            ps.setString(6, documento.getCategoria());
            ps.setString(7, documento.getUbicacion());
            ps.setString(8, documento.getDescripcion());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar documento: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Documento documento) {

        String sql = "UPDATE documentos SET "
                + "titulo = ?, "
                + "autor = ?, "
                + "editorial = ?, "
                + "anio_publicacion = ?, "
                + "id_tipo_documento = ?, "
                + "categoria = ?, "
                + "ubicacion = ?, "
                + "descripcion = ? "
                + "WHERE id_documento = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, documento.getTitulo());
            ps.setString(2, documento.getAutor());
            ps.setString(3, documento.getEditorial());
            ps.setInt(4, documento.getAnioPublicacion());
            ps.setInt(5, documento.getIdTipoDocumento());
            ps.setString(6, documento.getCategoria());
            ps.setString(7, documento.getUbicacion());
            ps.setString(8, documento.getDescripcion());
            ps.setInt(9, documento.getIdDocumento());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar documento: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneEjemplares(int idDocumento) {

        String sql = "SELECT COUNT(*) AS total FROM ejemplares WHERE id_documento = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idDocumento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar ejemplares del documento: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int idDocumento) {

        if (tieneEjemplares(idDocumento)) {
            System.out.println("No se puede eliminar el documento porque tiene ejemplares registrados.");
            return false;
        }

        String sql = "DELETE FROM documentos WHERE id_documento = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idDocumento);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar documento: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Documento> listar() {

        ArrayList<Documento> lista = new ArrayList<>();

        String sql = "SELECT "
                + "d.id_documento, "
                + "d.titulo, "
                + "d.autor, "
                + "d.editorial, "
                + "d.anio_publicacion, "
                + "d.id_tipo_documento, "
                + "td.nombre_tipo, "
                + "d.categoria, "
                + "d.ubicacion, "
                + "d.descripcion "
                + "FROM documentos d "
                + "INNER JOIN tipos_documento td ON d.id_tipo_documento = td.id_tipo_documento "
                + "ORDER BY d.titulo ASC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Documento documento = new Documento();

                documento.setIdDocumento(rs.getInt("id_documento"));
                documento.setTitulo(rs.getString("titulo"));
                documento.setAutor(rs.getString("autor"));
                documento.setEditorial(rs.getString("editorial"));
                documento.setAnioPublicacion(rs.getInt("anio_publicacion"));
                documento.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                documento.setNombreTipo(rs.getString("nombre_tipo"));
                documento.setCategoria(rs.getString("categoria"));
                documento.setUbicacion(rs.getString("ubicacion"));
                documento.setDescripcion(rs.getString("descripcion"));

                lista.add(documento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar documentos: " + e.getMessage());
        }

        return lista;
    }
}
package modelo;

public class Documento {

    private int idDocumento;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private int idTipoDocumento;
    private String nombreTipo;
    private String categoria;
    private String ubicacion;
    private String descripcion;

    public Documento() {
    }

    public Documento(int idDocumento, String titulo, String autor, String editorial,
                     int anioPublicacion, int idTipoDocumento, String nombreTipo,
                     String categoria, String ubicacion, String descripcion) {
        this.idDocumento = idDocumento;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.idTipoDocumento = idTipoDocumento;
        this.nombreTipo = nombreTipo;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
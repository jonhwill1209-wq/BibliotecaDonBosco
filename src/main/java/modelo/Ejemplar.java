package modelo;

public class Ejemplar {

    private int idEjemplar;
    private int idDocumento;
    private String tituloDocumento;
    private String tipoDocumento;
    private String categoriaDocumento;
    private String codigoEjemplar;
    private String estado;
    private String fechaRegistro;
    private String observaciones;

    public Ejemplar() {
    }

    public Ejemplar(int idEjemplar, int idDocumento, String tituloDocumento,
                    String tipoDocumento, String categoriaDocumento,
                    String codigoEjemplar, String estado, String fechaRegistro,
                    String observaciones) {
        this.idEjemplar = idEjemplar;
        this.idDocumento = idDocumento;
        this.tituloDocumento = tituloDocumento;
        this.tipoDocumento = tipoDocumento;
        this.categoriaDocumento = categoriaDocumento;
        this.codigoEjemplar = codigoEjemplar;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.observaciones = observaciones;
    }

    public int getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(int idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getTituloDocumento() {
        return tituloDocumento;
    }

    public void setTituloDocumento(String tituloDocumento) {
        this.tituloDocumento = tituloDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCategoriaDocumento() {
        return categoriaDocumento;
    }

    public void setCategoriaDocumento(String categoriaDocumento) {
        this.categoriaDocumento = categoriaDocumento;
    }

    public String getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public void setCodigoEjemplar(String codigoEjemplar) {
        this.codigoEjemplar = codigoEjemplar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
package modelo;

public class Usuario {

    private int idUsuario;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String contrasena;
    private int idRol;
    private String nombreRol;
    private String areaSeccion;
    private String estado;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombres, String apellidos, String usuario,
                   String contrasena, int idRol, String nombreRol,
                   String areaSeccion, String estado) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.areaSeccion = areaSeccion;
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getAreaSeccion() {
        return areaSeccion;
    }

    public void setAreaSeccion(String areaSeccion) {
        this.areaSeccion = areaSeccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
package vista;

import dao.EstadisticaDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.Usuario;

public class MenuPrincipal extends JFrame {

    private Usuario usuarioActual;

    private JButton btnUsuarios;
    private JButton btnDocumentos;
    private JButton btnEjemplares;
    private JButton btnPrestamos;
    private JButton btnDevoluciones;
    private JButton btnMora;
    private JButton btnHistorial;
    private JButton btnCerrarSesion;
    private JButton btnSalir;

    private JLabel lblTotalUsuarios;
    private JLabel lblTotalDocumentos;
    private JLabel lblEjemplaresDisponibles;
    private JLabel lblEjemplaresPrestados;
    private JLabel lblPrestamosActivos;
    private JLabel lblPrestamosDevueltos;

    private JPanel panelTarjetas;

    private final Color COLOR_FONDO = new Color(245, 247, 250);
    private final Color COLOR_PRIMARIO = new Color(35, 47, 62);
    private final Color COLOR_AZUL = new Color(41, 128, 185);
    private final Color COLOR_VERDE = new Color(39, 174, 96);
    private final Color COLOR_NARANJA = new Color(230, 126, 34);
    private final Color COLOR_ROJO = new Color(192, 57, 43);
    private final Color COLOR_MORADO = new Color(142, 68, 173);
    private final Color COLOR_GRIS = new Color(127, 140, 141);
    private final Color COLOR_AZUL_OSCURO = new Color(52, 73, 94);

    public MenuPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        iniciarComponentes();
        configurarPermisos();
        cargarEstadisticas();
    }

    private void iniciarComponentes() {
        setTitle("Sistema de Biblioteca Don Bosco");
        setSize(1200, 800);
        setMinimumSize(new Dimension(980, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_PRIMARIO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Sistema de Biblioteca Don Bosco");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblUsuario = new JLabel(
                "Usuario: " + usuarioActual.getNombres() + " " + usuarioActual.getApellidos()
                + "   |   Rol: " + usuarioActual.getNombreRol(),
                SwingConstants.RIGHT
        );
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblUsuario.setForeground(new Color(220, 220, 220));

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblUsuario, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(0, 20));
        panelCentro.setBackground(COLOR_FONDO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(30, 45, 25, 45));

        JLabel lblBienvenida = new JLabel("Panel Principal", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBienvenida.setForeground(COLOR_PRIMARIO);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panelCentro.add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelDashboard = new JPanel(new BorderLayout(0, 25));
        panelDashboard.setBackground(COLOR_FONDO);

        panelTarjetas = new JPanel(new GridLayout(0, 3, 25, 25));
        panelTarjetas.setBackground(COLOR_FONDO);

        btnUsuarios = crearBotonModulo("Usuarios", "Registrar usuarios, roles y contraseñas", COLOR_AZUL);
        btnDocumentos = crearBotonModulo("Documentos", "Registrar libros, revistas, tesis y CD", COLOR_VERDE);
        btnEjemplares = crearBotonModulo("Ejemplares", "Controlar copias físicas y estados", COLOR_NARANJA);
        btnPrestamos = crearBotonModulo("Préstamos", "Registrar préstamos activos", COLOR_MORADO);
        btnDevoluciones = crearBotonModulo("Devoluciones", "Registrar entregas y mora", COLOR_ROJO);
        btnMora = crearBotonModulo("Configuración", "Mora diaria y límites de préstamo", COLOR_GRIS);
        btnHistorial = crearBotonModulo("Historial", "Consultar préstamos y mora", COLOR_AZUL_OSCURO);

        panelTarjetas.add(btnUsuarios);
        panelTarjetas.add(btnDocumentos);
        panelTarjetas.add(btnEjemplares);
        panelTarjetas.add(btnPrestamos);
        panelTarjetas.add(btnDevoluciones);
        panelTarjetas.add(btnMora);
        panelTarjetas.add(btnHistorial);

        panelDashboard.add(panelTarjetas, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 6, 12, 0));
        panelEstadisticas.setBackground(COLOR_FONDO);
        panelEstadisticas.setPreferredSize(new Dimension(1000, 75));

        lblTotalUsuarios = crearValorEstadistica();
        lblTotalDocumentos = crearValorEstadistica();
        lblEjemplaresDisponibles = crearValorEstadistica();
        lblEjemplaresPrestados = crearValorEstadistica();
        lblPrestamosActivos = crearValorEstadistica();
        lblPrestamosDevueltos = crearValorEstadistica();

        panelEstadisticas.add(crearTarjetaEstadistica("Usuarios", lblTotalUsuarios, COLOR_AZUL));
        panelEstadisticas.add(crearTarjetaEstadistica("Documentos", lblTotalDocumentos, COLOR_VERDE));
        panelEstadisticas.add(crearTarjetaEstadistica("Disponibles", lblEjemplaresDisponibles, COLOR_NARANJA));
        panelEstadisticas.add(crearTarjetaEstadistica("Prestados", lblEjemplaresPrestados, COLOR_ROJO));
        panelEstadisticas.add(crearTarjetaEstadistica("Activos", lblPrestamosActivos, COLOR_MORADO));
        panelEstadisticas.add(crearTarjetaEstadistica("Devueltos", lblPrestamosDevueltos, COLOR_GRIS));

        panelDashboard.add(panelEstadisticas, BorderLayout.SOUTH);

        panelCentro.add(panelDashboard, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblEstado = new JLabel("Sistema activo - Colegio Amigos de Don Bosco");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEstado.setForeground(new Color(90, 90, 90));

        JPanel panelBotonesSalida = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotonesSalida.setBackground(Color.WHITE);

        btnCerrarSesion = crearBotonInferior("Cerrar sesión", new Color(52, 152, 219));
        btnSalir = crearBotonInferior("Salir", new Color(231, 76, 60));

        panelBotonesSalida.add(btnCerrarSesion);
        panelBotonesSalida.add(btnSalir);

        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(panelBotonesSalida, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        btnUsuarios.addActionListener(e -> {
            FrmUsuarios frm = new FrmUsuarios();
            frm.setVisible(true);
        });

        btnDocumentos.addActionListener(e -> {
            FrmDocumentos frm = new FrmDocumentos();
            frm.setVisible(true);
        });

        btnEjemplares.addActionListener(e -> {
            FrmEjemplares frm = new FrmEjemplares();
            frm.setVisible(true);
        });

        btnPrestamos.addActionListener(e -> {
            FrmPrestamos frm = new FrmPrestamos();
            frm.setVisible(true);
        });

        btnDevoluciones.addActionListener(e -> {
            FrmDevoluciones frm = new FrmDevoluciones();
            frm.setVisible(true);
        });

        btnMora.addActionListener(e -> {
            FrmConfiguracion frm = new FrmConfiguracion();
            frm.setVisible(true);
        });

        btnHistorial.addActionListener(e -> {
            FrmHistorialPrestamos frm = new FrmHistorialPrestamos();
            frm.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
            this.dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                cargarEstadisticas();
            }
        });
    }

    private JPanel crearTarjetaEstadistica(String titulo, JLabel lblValor, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 225)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitulo.setForeground(new Color(75, 75, 75));

        lblValor.setForeground(color);

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);

        return tarjeta;
    }

    private JLabel crearValorEstadistica() {
        JLabel label = new JLabel("0", SwingConstants.RIGHT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        return label;
    }

    private void cargarEstadisticas() {
        EstadisticaDAO dao = new EstadisticaDAO();

        lblTotalUsuarios.setText(String.valueOf(dao.contarUsuarios()));
        lblTotalDocumentos.setText(String.valueOf(dao.contarDocumentos()));
        lblEjemplaresDisponibles.setText(String.valueOf(dao.contarEjemplaresDisponibles()));
        lblEjemplaresPrestados.setText(String.valueOf(dao.contarEjemplaresPrestados()));
        lblPrestamosActivos.setText(String.valueOf(dao.contarPrestamosActivos()));
        lblPrestamosDevueltos.setText(String.valueOf(dao.contarPrestamosDevueltos()));
    }

    private JButton crearBotonModulo(String titulo, String descripcion, Color color) {
        JButton boton = new JButton(
                "<html><center>"
                + "<div style='font-size:20px; font-weight:bold;'>" + titulo + "</div>"
                + "<br>"
                + "<div style='font-size:12px;'>" + descripcion + "</div>"
                + "</center></html>"
        );

        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));

        return boton;
    }

    private JButton crearBotonInferior(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 35));

        return boton;
    }

    private void configurarPermisos() {
        String rol = usuarioActual.getNombreRol();

        if (rol.equalsIgnoreCase("Administrador")) {
            btnUsuarios.setEnabled(true);
            btnDocumentos.setEnabled(true);
            btnEjemplares.setEnabled(true);
            btnPrestamos.setEnabled(true);
            btnDevoluciones.setEnabled(true);
            btnMora.setEnabled(true);
            btnHistorial.setEnabled(true);
        } else if (rol.equalsIgnoreCase("Profesor") || rol.equalsIgnoreCase("Alumno")) {
            btnUsuarios.setEnabled(false);
            btnDocumentos.setEnabled(false);
            btnEjemplares.setEnabled(false);
            btnMora.setEnabled(false);
            btnHistorial.setEnabled(false);

            btnPrestamos.setEnabled(true);
            btnDevoluciones.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        Usuario usuarioPrueba = new Usuario();

        usuarioPrueba.setIdUsuario(1);
        usuarioPrueba.setNombres("Administrador");
        usuarioPrueba.setApellidos("Principal");
        usuarioPrueba.setUsuario("admin");
        usuarioPrueba.setIdRol(1);
        usuarioPrueba.setNombreRol("Administrador");
        usuarioPrueba.setEstado("Activo");

        MenuPrincipal menu = new MenuPrincipal(usuarioPrueba);
        menu.setVisible(true);
    }
}
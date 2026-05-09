package vista;

import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import modelo.Usuario;

public class FrmLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnSalir;

    public FrmLogin() {
        Tema.aplicarNimbus();
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setTitle("Login - Sistema Biblioteca");
        setSize(520, 430);
        setMinimumSize(new Dimension(470, 390));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(35, 47, 62));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Sistema de Biblioteca");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSubtitulo = new JLabel("Colegio Amigos de Don Bosco");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(230, 230, 230));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelTitulos = new JPanel(new BorderLayout());
        panelTitulos.setBackground(new Color(35, 47, 62));
        panelTitulos.add(lblTitulo, BorderLayout.CENTER);
        panelTitulos.add(lblSubtitulo, BorderLayout.SOUTH);

        panelSuperior.add(panelTitulos, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Tema.FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 45, 30, 45));
        add(panelContenido, BorderLayout.CENTER);

        JPanel panelLogin = new JPanel(new GridBagLayout());
        panelLogin.setBackground(Tema.PANEL);
        panelLogin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 9, 9, 9);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInicio = new JLabel("Inicio de sesión");
        lblInicio.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblInicio.setForeground(new Color(35, 47, 62));
        lblInicio.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelLogin.add(lblInicio, gbc);

        gbc.gridwidth = 1;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelLogin.add(lblUsuario, gbc);

        txtUsuario = new JTextField();
        Tema.estilizarCampo(txtUsuario);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelLogin.add(txtUsuario, gbc);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelLogin.add(lblContrasena, gbc);

        txtContrasena = new JPasswordField();
        Tema.estilizarCampo(txtContrasena);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelLogin.add(txtContrasena, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnIngresar = new JButton("Ingresar");
        btnSalir = new JButton("Salir");

        Tema.estilizarBoton(btnIngresar, new Color(41, 128, 185));
        Tema.estilizarBoton(btnSalir, new Color(192, 57, 43));

        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelLogin.add(panelBotones, gbc);

        JLabel lblAyuda = new JLabel("Ingrese sus credenciales para acceder al sistema.");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));
        lblAyuda.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelLogin.add(lblAyuda, gbc);

        panelContenido.add(panelLogin);

        btnIngresar.addActionListener(e -> validarLogin());
        btnSalir.addActionListener(e -> System.exit(0));

        getRootPane().setDefaultButton(btnIngresar);
    }

    private void validarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = String.valueOf(txtContrasena.getPassword()).trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el usuario.");
            txtUsuario.requestFocus();
            return;
        }

        if (contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la contraseña.");
            txtContrasena.requestFocus();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioValidado = usuarioDAO.validarLogin(usuario, contrasena);

        if (usuarioValidado != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuarioValidado.getNombres());

            MenuPrincipal menu = new MenuPrincipal(usuarioValidado);
            menu.setVisible(true);

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }
}
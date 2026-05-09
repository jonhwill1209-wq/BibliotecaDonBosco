package vista;

import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Usuario;

public class FrmUsuarios extends JFrame {

    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JTextField txtAreaSeccion;
    private JComboBox<String> cmbRol;
    private JComboBox<String> cmbEstado;
    private JTextField txtBuscar;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JButton btnRestablecer;
    private JButton btnCambiarEstado;
    private JButton btnCerrar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorterTabla;

    private int idUsuarioSeleccionado = 0;

    public FrmUsuarios() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        listarUsuarios();
    }

    private void iniciarComponentes() {
        setTitle("Gestión de Usuarios");
        setSize(1050, 720);
        setMinimumSize(new java.awt.Dimension(900, 620));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.USUARIOS);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Registrar, actualizar, activar/inactivar y restablecer usuarios");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(235, 235, 235));

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblSubtitulo, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBackground(Tema.FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        add(panelContenido, BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Tema.PANEL);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblNombres, gbc);

        txtNombres = new JTextField();
        Tema.estilizarCampo(txtNombres);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(txtNombres, gbc);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblApellidos, gbc);

        txtApellidos = new JTextField();
        Tema.estilizarCampo(txtApellidos);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(txtApellidos, gbc);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblUsuario, gbc);

        txtUsuario = new JTextField();
        Tema.estilizarCampo(txtUsuario);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtUsuario, gbc);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblContrasena, gbc);

        txtContrasena = new JPasswordField();
        Tema.estilizarCampo(txtContrasena);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtContrasena, gbc);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblRol, gbc);

        cmbRol = new JComboBox<>();
        cmbRol.addItem("Administrador");
        cmbRol.addItem("Profesor");
        cmbRol.addItem("Alumno");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(cmbRol, gbc);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblEstado, gbc);

        cmbEstado = new JComboBox<>();
        cmbEstado.addItem("Activo");
        cmbEstado.addItem("Inactivo");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(cmbEstado, gbc);

        JLabel lblArea = new JLabel("Área / Sección:");
        lblArea.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panelFormulario.add(lblArea, gbc);

        txtAreaSeccion = new JTextField();
        Tema.estilizarCampo(txtAreaSeccion);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        panelFormulario.add(txtAreaSeccion, gbc);

        gbc.gridwidth = 1;

        JLabel lblEjemplo = new JLabel("Ejemplo: Profesor de Biología, Alumno de Ingeniería en Sistemas, Administración general.");
        lblEjemplo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEjemplo.setForeground(new Color(90, 90, 90));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panelFormulario.add(lblEjemplo, gbc);

        gbc.gridwidth = 1;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnLimpiar = new JButton("Limpiar");
        btnRestablecer = new JButton("Restablecer contraseña");
        btnCambiarEstado = new JButton("Activar/Inactivar");
        btnCerrar = new JButton("Cerrar");

        Tema.estilizarBoton(btnGuardar, Tema.USUARIOS);
        Tema.estilizarBoton(btnActualizar, new Color(52, 152, 219));
        Tema.estilizarBoton(btnLimpiar, new Color(127, 140, 141));
        Tema.estilizarBoton(btnRestablecer, new Color(155, 89, 182));
        Tema.estilizarBoton(btnCambiarEstado, new Color(230, 126, 34));
        Tema.estilizarBoton(btnCerrar, new Color(192, 57, 43));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRestablecer);
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnCerrar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        panelFormulario.add(panelBotones, gbc);

        panelContenido.add(panelFormulario, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setBackground(Tema.PANEL);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
        panelBusqueda.setBackground(Tema.PANEL);

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(Tema.NORMAL);

        txtBuscar = new JTextField();
        Tema.estilizarCampo(txtBuscar);

        JLabel lblAyuda = new JLabel("Buscar por nombre, usuario, rol, área o estado");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));

        panelBusqueda.add(lblBuscar, BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(lblAyuda, BorderLayout.EAST);

        panelTabla.add(panelBusqueda, BorderLayout.NORTH);

        tablaUsuarios = new JTable();
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaUsuarios, Tema.USUARIOS);

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelContenido.add(panelTabla, BorderLayout.CENTER);

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRestablecer.addActionListener(e -> restablecerContrasena());
        btnCambiarEstado.addActionListener(e -> cambiarEstadoUsuario());
        btnCerrar.addActionListener(e -> dispose());

        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }
        });
    }

    private void guardarUsuario() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String usuarioTexto = txtUsuario.getText().trim();
        String contrasena = String.valueOf(txtContrasena.getPassword()).trim();
        String areaSeccion = txtAreaSeccion.getText().trim();
        String estado = cmbEstado.getSelectedItem().toString();

        if (nombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar los nombres.");
            txtNombres.requestFocus();
            return;
        }

        if (apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar los apellidos.");
            txtApellidos.requestFocus();
            return;
        }

        if (usuarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el usuario.");
            txtUsuario.requestFocus();
            return;
        }

        if (usuarioTexto.contains(" ")) {
            JOptionPane.showMessageDialog(this, "El usuario no debe contener espacios.");
            txtUsuario.requestFocus();
            return;
        }

        if (contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la contraseña.");
            txtContrasena.requestFocus();
            return;
        }

        if (contrasena.length() < 4) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.");
            txtContrasena.requestFocus();
            return;
        }

        if (areaSeccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el área o sección.");
            txtAreaSeccion.requestFocus();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.existeUsuario(usuarioTexto)) {
            JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya existe.");
            txtUsuario.requestFocus();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setUsuario(usuarioTexto);
        usuario.setContrasena(contrasena);
        usuario.setIdRol(cmbRol.getSelectedIndex() + 1);
        usuario.setAreaSeccion(areaSeccion);
        usuario.setEstado(estado);

        if (usuarioDAO.guardar(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente.");
            limpiarCampos();
            listarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el usuario.");
        }
    }

    private void actualizarUsuario() {
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario de la tabla.");
            return;
        }

        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String usuarioTexto = txtUsuario.getText().trim();
        String areaSeccion = txtAreaSeccion.getText().trim();
        String estado = cmbEstado.getSelectedItem().toString();

        if (nombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar los nombres.");
            txtNombres.requestFocus();
            return;
        }

        if (apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar los apellidos.");
            txtApellidos.requestFocus();
            return;
        }

        if (usuarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el usuario.");
            txtUsuario.requestFocus();
            return;
        }

        if (usuarioTexto.contains(" ")) {
            JOptionPane.showMessageDialog(this, "El usuario no debe contener espacios.");
            txtUsuario.requestFocus();
            return;
        }

        if (areaSeccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el área o sección.");
            txtAreaSeccion.requestFocus();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.existeUsuarioEnOtroRegistro(usuarioTexto, idUsuarioSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya pertenece a otro registro.");
            txtUsuario.requestFocus();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuarioSeleccionado);
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setUsuario(usuarioTexto);
        usuario.setIdRol(cmbRol.getSelectedIndex() + 1);
        usuario.setAreaSeccion(areaSeccion);
        usuario.setEstado(estado);

        if (usuarioDAO.actualizar(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            limpiarCampos();
            listarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario.");
        }
    }

    private void listarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList<Usuario> lista = usuarioDAO.listar();

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombres");
        modeloTabla.addColumn("Apellidos");
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Área / Sección");
        modeloTabla.addColumn("Estado");

        for (Usuario u : lista) {
            Object[] fila = new Object[7];

            fila[0] = u.getIdUsuario();
            fila[1] = u.getNombres();
            fila[2] = u.getApellidos();
            fila[3] = u.getUsuario();
            fila[4] = u.getNombreRol();
            fila[5] = u.getAreaSeccion();
            fila[6] = u.getEstado();

            modeloTabla.addRow(fila);
        }

        tablaUsuarios.setModel(modeloTabla);

        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaUsuarios.setRowSorter(sorterTabla);

        sorterTabla.setSortKeys(
                java.util.List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING))
        );

        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(140);
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(140);
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(110);
        tablaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(110);
        tablaUsuarios.getColumnModel().getColumn(5).setPreferredWidth(280);
        tablaUsuarios.getColumnModel().getColumn(6).setPreferredWidth(90);

        filtrarTabla();
    }

    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1 || modeloTabla == null) {
            return;
        }

        int filaModelo = tablaUsuarios.convertRowIndexToModel(filaSeleccionada);

        idUsuarioSeleccionado = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());

        txtNombres.setText(modeloTabla.getValueAt(filaModelo, 1).toString());
        txtApellidos.setText(modeloTabla.getValueAt(filaModelo, 2).toString());
        txtUsuario.setText(modeloTabla.getValueAt(filaModelo, 3).toString());

        String rol = modeloTabla.getValueAt(filaModelo, 4).toString();
        cmbRol.setSelectedItem(rol);

        Object area = modeloTabla.getValueAt(filaModelo, 5);
        txtAreaSeccion.setText(area == null ? "" : area.toString());

        String estado = modeloTabla.getValueAt(filaModelo, 6).toString();
        cmbEstado.setSelectedItem(estado);

        txtContrasena.setText("");
    }

    private void restablecerContrasena() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario.");
            return;
        }

        int filaModelo = tablaUsuarios.convertRowIndexToModel(filaSeleccionada);

        int idUsuario = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
        String nombreUsuario = modeloTabla.getValueAt(filaModelo, 3).toString();

        String nuevaContrasena = JOptionPane.showInputDialog(
                this,
                "Ingrese la nueva contraseña para el usuario: " + nombreUsuario
        );

        if (nuevaContrasena == null) {
            return;
        }

        nuevaContrasena = nuevaContrasena.trim();

        if (nuevaContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía.");
            return;
        }

        if (nuevaContrasena.length() < 4) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.restablecerContrasena(idUsuario, nuevaContrasena)) {
            JOptionPane.showMessageDialog(this, "Contraseña restablecida correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo restablecer la contraseña.");
        }
    }

    private void cambiarEstadoUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario.");
            return;
        }

        int filaModelo = tablaUsuarios.convertRowIndexToModel(filaSeleccionada);

        int idUsuario = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
        String usuario = modeloTabla.getValueAt(filaModelo, 3).toString();
        String estadoActual = modeloTabla.getValueAt(filaModelo, 6).toString();

        String nuevoEstado;

        if (estadoActual.equalsIgnoreCase("Activo")) {
            nuevoEstado = "Inactivo";
        } else {
            nuevoEstado = "Activo";
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cambiar el estado del usuario " + usuario + " a " + nuevoEstado + "?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.cambiarEstado(idUsuario, nuevoEstado)) {
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
            limpiarCampos();
            listarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cambiar el estado.");
        }
    }

    private void filtrarTabla() {
        if (sorterTabla == null) {
            return;
        }

        String textoBusqueda = txtBuscar.getText().trim();

        if (textoBusqueda.isEmpty()) {
            sorterTabla.setRowFilter(null);
        } else {
            sorterTabla.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda)));
        }
    }

    private void limpiarCampos() {
        idUsuarioSeleccionado = 0;

        txtNombres.setText("");
        txtApellidos.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtAreaSeccion.setText("");
        cmbRol.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);

        if (tablaUsuarios != null) {
            tablaUsuarios.clearSelection();
        }

        txtNombres.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmUsuarios frm = new FrmUsuarios();
            frm.setVisible(true);
        });
    }
}
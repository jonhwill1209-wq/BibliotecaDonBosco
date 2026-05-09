package vista;

import dao.EjemplarDAO;
import dao.PrestamoDAO;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Ejemplar;
import modelo.Prestamo;
import modelo.Usuario;

public class FrmPrestamos extends JFrame {

    private JTextField txtUsuarioSeleccionado;
    private JTextField txtEjemplarSeleccionado;

    private JTextField txtBuscarUsuario;
    private JTextField txtBuscarEjemplar;

    private JTable tablaUsuarios;
    private JTable tablaEjemplares;
    private JTable tablaPrestamos;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloEjemplares;
    private DefaultTableModel modeloPrestamos;

    private TableRowSorter<DefaultTableModel> sorterUsuarios;
    private TableRowSorter<DefaultTableModel> sorterEjemplares;

    private int idUsuarioSeleccionado = 0;
    private int idEjemplarSeleccionado = 0;

    public FrmPrestamos() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        cargarUsuarios();
        cargarEjemplaresDisponibles();
        listarPrestamosActivos();
    }

    private void iniciarComponentes() {
        setTitle("Gestión de Préstamos");
        setSize(1150, 760);
        setMinimumSize(new java.awt.Dimension(980, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.PRESTAMOS);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTitulo = new JLabel("Gestión de Préstamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Buscar usuarios, seleccionar ejemplares disponibles y registrar préstamos");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(235, 235, 235));

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblSubtitulo, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBackground(Tema.FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        add(panelContenido, BorderLayout.CENTER);

        JPanel panelOperacion = new JPanel(new BorderLayout(10, 10));
        panelOperacion.setBackground(Tema.PANEL);
        panelOperacion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JPanel panelSeleccion = new JPanel(new GridBagLayout());
        panelSeleccion.setBackground(Tema.PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuarioSeleccionado = new JLabel("Usuario seleccionado:");
        lblUsuarioSeleccionado.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelSeleccion.add(lblUsuarioSeleccionado, gbc);

        txtUsuarioSeleccionado = new JTextField();
        Tema.estilizarCampo(txtUsuarioSeleccionado);
        txtUsuarioSeleccionado.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelSeleccion.add(txtUsuarioSeleccionado, gbc);

        JLabel lblEjemplarSeleccionado = new JLabel("Ejemplar seleccionado:");
        lblEjemplarSeleccionado.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelSeleccion.add(lblEjemplarSeleccionado, gbc);

        txtEjemplarSeleccionado = new JTextField();
        Tema.estilizarCampo(txtEjemplarSeleccionado);
        txtEjemplarSeleccionado.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelSeleccion.add(txtEjemplarSeleccionado, gbc);

        panelOperacion.add(panelSeleccion, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnRegistrar = new JButton("Registrar préstamo");
        btnActualizar = new JButton("Actualizar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        Tema.estilizarBoton(btnRegistrar, Tema.PRESTAMOS);
        Tema.estilizarBoton(btnActualizar, new Color(52, 152, 219));
        Tema.estilizarBoton(btnLimpiar, new Color(127, 140, 141));
        Tema.estilizarBoton(btnCerrar, new Color(192, 57, 43));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        panelOperacion.add(panelBotones, BorderLayout.SOUTH);

        panelContenido.add(panelOperacion, BorderLayout.NORTH);

        JPanel panelUsuarios = crearPanelUsuarios();
        JPanel panelEjemplares = crearPanelEjemplares();

        JSplitPane splitBusqueda = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelUsuarios, panelEjemplares);
        splitBusqueda.setResizeWeight(0.50);
        splitBusqueda.setDividerSize(8);
        splitBusqueda.setBorder(null);

        JPanel panelCentro = new JPanel(new BorderLayout(15, 15));
        panelCentro.setBackground(Tema.FONDO);
        panelCentro.add(splitBusqueda, BorderLayout.CENTER);

        JPanel panelPrestamos = crearPanelPrestamosActivos();
        panelCentro.add(panelPrestamos, BorderLayout.SOUTH);

        panelContenido.add(panelCentro, BorderLayout.CENTER);

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarUsuarioSeleccionado();
            }
        });

        tablaEjemplares.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEjemplarSeleccionado();
            }
        });

        btnRegistrar.addActionListener(e -> registrarPrestamo());
        btnActualizar.addActionListener(e -> actualizarPantalla());
        btnLimpiar.addActionListener(e -> limpiarSeleccion());
        btnCerrar.addActionListener(e -> dispose());

        txtBuscarUsuario.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarUsuarios();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarUsuarios();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarUsuarios();
            }
        });

        txtBuscarEjemplar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarEjemplares();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarEjemplares();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarEjemplares();
            }
        });
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Tema.PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitulo = new JLabel("Buscar usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Tema.PRESTAMOS);

        txtBuscarUsuario = new JTextField();
        Tema.estilizarCampo(txtBuscarUsuario);

        JLabel lblAyuda = new JLabel("Nombre, usuario, rol o área/sección");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));

        JPanel panelBusqueda = new JPanel(new BorderLayout(8, 5));
        panelBusqueda.setBackground(Tema.PANEL);
        panelBusqueda.add(lblTitulo, BorderLayout.NORTH);
        panelBusqueda.add(txtBuscarUsuario, BorderLayout.CENTER);
        panelBusqueda.add(lblAyuda, BorderLayout.SOUTH);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        tablaUsuarios = new JTable();
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaUsuarios, Tema.PRESTAMOS);

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEjemplares() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Tema.PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitulo = new JLabel("Buscar ejemplar disponible");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Tema.PRESTAMOS);

        txtBuscarEjemplar = new JTextField();
        Tema.estilizarCampo(txtBuscarEjemplar);

        JLabel lblAyuda = new JLabel("Código, título, tipo o categoría");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));

        JPanel panelBusqueda = new JPanel(new BorderLayout(8, 5));
        panelBusqueda.setBackground(Tema.PANEL);
        panelBusqueda.add(lblTitulo, BorderLayout.NORTH);
        panelBusqueda.add(txtBuscarEjemplar, BorderLayout.CENTER);
        panelBusqueda.add(lblAyuda, BorderLayout.SOUTH);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        tablaEjemplares = new JTable();
        tablaEjemplares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEjemplares.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaEjemplares, Tema.PRESTAMOS);

        JScrollPane scroll = new JScrollPane(tablaEjemplares);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelPrestamosActivos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setPreferredSize(new java.awt.Dimension(1000, 170));
        panel.setBackground(Tema.PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitulo = new JLabel("Préstamos activos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Tema.PRESTAMOS);

        panel.add(lblTitulo, BorderLayout.NORTH);

        tablaPrestamos = new JTable();
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaPrestamos, Tema.PRESTAMOS);

        JScrollPane scroll = new JScrollPane(tablaPrestamos);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void cargarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList<Usuario> lista = usuarioDAO.listarUsuariosPrestamo();

        modeloUsuarios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloUsuarios.addColumn("ID");
        modeloUsuarios.addColumn("Nombre completo");
        modeloUsuarios.addColumn("Usuario");
        modeloUsuarios.addColumn("Rol");
        modeloUsuarios.addColumn("Área / Sección");

        for (Usuario usuario : lista) {
            Object[] fila = new Object[5];

            fila[0] = usuario.getIdUsuario();
            fila[1] = usuario.getNombres() + " " + usuario.getApellidos();
            fila[2] = usuario.getUsuario();
            fila[3] = usuario.getNombreRol();
            fila[4] = usuario.getAreaSeccion();

            modeloUsuarios.addRow(fila);
        }

        tablaUsuarios.setModel(modeloUsuarios);

        sorterUsuarios = new TableRowSorter<>(modeloUsuarios);
        tablaUsuarios.setRowSorter(sorterUsuarios);

        sorterUsuarios.setSortKeys(
                java.util.List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING))
        );

        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(90);
        tablaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(250);

        filtrarUsuarios();
    }

    private void cargarEjemplaresDisponibles() {
        EjemplarDAO ejemplarDAO = new EjemplarDAO();
        ArrayList<Ejemplar> lista = ejemplarDAO.listarDisponibles();

        modeloEjemplares = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloEjemplares.addColumn("ID");
        modeloEjemplares.addColumn("Código");
        modeloEjemplares.addColumn("Documento");
        modeloEjemplares.addColumn("Tipo");
        modeloEjemplares.addColumn("Categoría");
        modeloEjemplares.addColumn("Estado");

        for (Ejemplar ejemplar : lista) {
            Object[] fila = new Object[6];

            fila[0] = ejemplar.getIdEjemplar();
            fila[1] = ejemplar.getCodigoEjemplar();
            fila[2] = ejemplar.getTituloDocumento();
            fila[3] = ejemplar.getTipoDocumento();
            fila[4] = ejemplar.getCategoriaDocumento();
            fila[5] = ejemplar.getEstado();

            modeloEjemplares.addRow(fila);
        }

        tablaEjemplares.setModel(modeloEjemplares);

        sorterEjemplares = new TableRowSorter<>(modeloEjemplares);
        tablaEjemplares.setRowSorter(sorterEjemplares);

        sorterEjemplares.setSortKeys(
                java.util.List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING))
        );

        tablaEjemplares.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaEjemplares.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaEjemplares.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablaEjemplares.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaEjemplares.getColumnModel().getColumn(4).setPreferredWidth(150);
        tablaEjemplares.getColumnModel().getColumn(5).setPreferredWidth(90);

        filtrarEjemplares();
    }

    private void listarPrestamosActivos() {
        PrestamoDAO prestamoDAO = new PrestamoDAO();
        ArrayList<Prestamo> lista = prestamoDAO.listarActivos();

        modeloPrestamos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloPrestamos.addColumn("ID");
        modeloPrestamos.addColumn("Usuario");
        modeloPrestamos.addColumn("Código");
        modeloPrestamos.addColumn("Documento");
        modeloPrestamos.addColumn("Fecha préstamo");
        modeloPrestamos.addColumn("Fecha devolución");
        modeloPrestamos.addColumn("Estado");

        for (Prestamo p : lista) {
            Object[] fila = new Object[7];

            fila[0] = p.getIdPrestamo();
            fila[1] = p.getNombreUsuario();
            fila[2] = p.getCodigoEjemplar();
            fila[3] = p.getTituloDocumento();
            fila[4] = p.getFechaPrestamo();
            fila[5] = p.getFechaDevolucionEstimada();
            fila[6] = p.getEstado();

            modeloPrestamos.addRow(fila);
        }

        tablaPrestamos.setModel(modeloPrestamos);

        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(280);
        tablaPrestamos.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablaPrestamos.getColumnModel().getColumn(5).setPreferredWidth(130);
        tablaPrestamos.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    private void cargarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1 || modeloUsuarios == null) {
            return;
        }

        int filaModelo = tablaUsuarios.convertRowIndexToModel(filaSeleccionada);

        idUsuarioSeleccionado = Integer.parseInt(modeloUsuarios.getValueAt(filaModelo, 0).toString());

        String nombre = modeloUsuarios.getValueAt(filaModelo, 1).toString();
        String usuario = modeloUsuarios.getValueAt(filaModelo, 2).toString();
        String rol = modeloUsuarios.getValueAt(filaModelo, 3).toString();

        txtUsuarioSeleccionado.setText(idUsuarioSeleccionado + " - " + nombre + " - " + usuario + " - " + rol);
    }

    private void cargarEjemplarSeleccionado() {
        int filaSeleccionada = tablaEjemplares.getSelectedRow();

        if (filaSeleccionada == -1 || modeloEjemplares == null) {
            return;
        }

        int filaModelo = tablaEjemplares.convertRowIndexToModel(filaSeleccionada);

        idEjemplarSeleccionado = Integer.parseInt(modeloEjemplares.getValueAt(filaModelo, 0).toString());

        String codigo = modeloEjemplares.getValueAt(filaModelo, 1).toString();
        String documento = modeloEjemplares.getValueAt(filaModelo, 2).toString();
        String tipo = modeloEjemplares.getValueAt(filaModelo, 3).toString();
        String categoria = modeloEjemplares.getValueAt(filaModelo, 4).toString();

        txtEjemplarSeleccionado.setText(idEjemplarSeleccionado + " - " + codigo + " - " + documento + " - " + tipo + " - " + categoria);
    }

    private void registrarPrestamo() {
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario de la tabla.");
            txtBuscarUsuario.requestFocus();
            return;
        }

        if (idEjemplarSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ejemplar disponible de la tabla.");
            txtBuscarEjemplar.requestFocus();
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea registrar este préstamo?\n\n"
                + "Usuario: " + txtUsuarioSeleccionado.getText() + "\n"
                + "Ejemplar: " + txtEjemplarSeleccionado.getText(),
                "Confirmar préstamo",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        PrestamoDAO prestamoDAO = new PrestamoDAO();

        boolean registrado = prestamoDAO.registrarPrestamo(
                idUsuarioSeleccionado,
                idEjemplarSeleccionado
        );

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Préstamo registrado correctamente.");
            actualizarPantalla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el préstamo. Verifique disponibilidad o límite de préstamos.");
        }
    }

    private void filtrarUsuarios() {
        if (sorterUsuarios == null) {
            return;
        }

        String textoBusqueda = txtBuscarUsuario.getText().trim();

        if (textoBusqueda.isEmpty()) {
            sorterUsuarios.setRowFilter(null);
        } else {
            sorterUsuarios.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda)));
        }
    }

    private void filtrarEjemplares() {
        if (sorterEjemplares == null) {
            return;
        }

        String textoBusqueda = txtBuscarEjemplar.getText().trim();

        if (textoBusqueda.isEmpty()) {
            sorterEjemplares.setRowFilter(null);
        } else {
            sorterEjemplares.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda)));
        }
    }

    private void limpiarSeleccion() {
        idUsuarioSeleccionado = 0;
        idEjemplarSeleccionado = 0;

        txtUsuarioSeleccionado.setText("");
        txtEjemplarSeleccionado.setText("");
        txtBuscarUsuario.setText("");
        txtBuscarEjemplar.setText("");

        if (tablaUsuarios != null) {
            tablaUsuarios.clearSelection();
        }

        if (tablaEjemplares != null) {
            tablaEjemplares.clearSelection();
        }

        txtBuscarUsuario.requestFocus();
    }

    private void actualizarPantalla() {
        limpiarSeleccion();
        cargarUsuarios();
        cargarEjemplaresDisponibles();
        listarPrestamosActivos();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmPrestamos frm = new FrmPrestamos();
            frm.setVisible(true);
        });
    }
}
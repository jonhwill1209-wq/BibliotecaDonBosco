package vista;

import dao.EjemplarDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Ejemplar;

public class FrmEjemplares extends JFrame {

    private JTextField txtDocumentoSeleccionado;
    private JTextField txtTipoDocumento;
    private JTextField txtCategoriaDocumento;
    private JTextField txtCodigoEjemplar;
    private JComboBox<String> cmbEstado;
    private JTextArea txtObservaciones;

    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltroTipo;
    private JComboBox<String> cmbFiltroCategoria;
    private JComboBox<String> cmbOrden;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnActualizarLista;
    private JButton btnCerrar;

    private JTable tablaEjemplares;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorterTabla;

    private ArrayList<Ejemplar> listaEjemplaresActual;

    private int idDocumentoSeleccionado = 0;
    private int idEjemplarSeleccionado = 0;

    public FrmEjemplares() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        listarEjemplares();
    }

    private void iniciarComponentes() {
        setTitle("Gestión de Ejemplares");
        setSize(1120, 760);
        setMinimumSize(new java.awt.Dimension(980, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.EJEMPLARES);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTitulo = new JLabel("Gestión de Ejemplares");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Registrar, consultar, actualizar y eliminar copias físicas");
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

        JLabel lblDocumento = new JLabel("Documento seleccionado:");
        lblDocumento.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblDocumento, gbc);

        txtDocumentoSeleccionado = new JTextField();
        Tema.estilizarCampo(txtDocumentoSeleccionado);
        txtDocumentoSeleccionado.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        panelFormulario.add(txtDocumentoSeleccionado, gbc);

        gbc.gridwidth = 1;

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblTipo, gbc);

        txtTipoDocumento = new JTextField();
        Tema.estilizarCampo(txtTipoDocumento);
        txtTipoDocumento.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtTipoDocumento, gbc);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblCategoria, gbc);

        txtCategoriaDocumento = new JTextField();
        Tema.estilizarCampo(txtCategoriaDocumento);
        txtCategoriaDocumento.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtCategoriaDocumento, gbc);

        JLabel lblCodigo = new JLabel("Código ejemplar:");
        lblCodigo.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblCodigo, gbc);

        txtCodigoEjemplar = new JTextField();
        Tema.estilizarCampo(txtCodigoEjemplar);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(txtCodigoEjemplar, gbc);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblEstado, gbc);

        cmbEstado = new JComboBox<>();
        cmbEstado.addItem("Disponible");
        cmbEstado.addItem("Prestado");
        cmbEstado.addItem("Dañado");
        cmbEstado.addItem("Extraviado");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(cmbEstado, gbc);

        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panelFormulario.add(lblObservaciones, gbc);

        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setFont(Tema.NORMAL);
        txtObservaciones.setBackground(Tema.CAMPO);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelFormulario.add(scrollObservaciones, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblIndicacion = new JLabel("Guardar crea un nuevo ejemplar. Actualizar o eliminar actúa sobre el ejemplar seleccionado.");
        lblIndicacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblIndicacion.setForeground(new Color(90, 90, 90));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panelFormulario.add(lblIndicacion, gbc);

        gbc.gridwidth = 1;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnGuardar = new JButton("Guardar ejemplar");
        btnActualizar = new JButton("Actualizar ejemplar");
        btnEliminar = new JButton("Eliminar ejemplar");
        btnLimpiar = new JButton("Limpiar");
        btnActualizarLista = new JButton("Actualizar lista");
        btnCerrar = new JButton("Cerrar");

        Tema.estilizarBoton(btnGuardar, Tema.EJEMPLARES);
        Tema.estilizarBoton(btnActualizar, new Color(52, 152, 219));
        Tema.estilizarBoton(btnEliminar, new Color(192, 57, 43));
        Tema.estilizarBoton(btnLimpiar, new Color(127, 140, 141));
        Tema.estilizarBoton(btnActualizarLista, new Color(155, 89, 182));
        Tema.estilizarBoton(btnCerrar, new Color(100, 100, 100));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnActualizarLista);
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

        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBackground(Tema.PANEL);

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(4, 6, 4, 6);
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(Tema.NORMAL);
        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.weightx = 0;
        panelFiltros.add(lblBuscar, fgbc);

        txtBuscar = new JTextField();
        Tema.estilizarCampo(txtBuscar);
        fgbc.gridx = 1;
        fgbc.gridy = 0;
        fgbc.weightx = 1;
        panelFiltros.add(txtBuscar, fgbc);

        JLabel lblTipoFiltro = new JLabel("Tipo:");
        lblTipoFiltro.setFont(Tema.NORMAL);
        fgbc.gridx = 2;
        fgbc.gridy = 0;
        fgbc.weightx = 0;
        panelFiltros.add(lblTipoFiltro, fgbc);

        cmbFiltroTipo = new JComboBox<>();
        cmbFiltroTipo.addItem("Todos");
        cmbFiltroTipo.addItem("Libro");
        cmbFiltroTipo.addItem("Revista");
        cmbFiltroTipo.addItem("Tesis");
        cmbFiltroTipo.addItem("CD");
        fgbc.gridx = 3;
        fgbc.gridy = 0;
        fgbc.weightx = 0.3;
        panelFiltros.add(cmbFiltroTipo, fgbc);

        JLabel lblCategoriaFiltro = new JLabel("Categoría:");
        lblCategoriaFiltro.setFont(Tema.NORMAL);
        fgbc.gridx = 4;
        fgbc.gridy = 0;
        fgbc.weightx = 0;
        panelFiltros.add(lblCategoriaFiltro, fgbc);

        cmbFiltroCategoria = new JComboBox<>();
        fgbc.gridx = 5;
        fgbc.gridy = 0;
        fgbc.weightx = 0.4;
        panelFiltros.add(cmbFiltroCategoria, fgbc);

        JLabel lblOrden = new JLabel("Orden:");
        lblOrden.setFont(Tema.NORMAL);
        fgbc.gridx = 0;
        fgbc.gridy = 1;
        fgbc.weightx = 0;
        panelFiltros.add(lblOrden, fgbc);

        cmbOrden = new JComboBox<>();
        cmbOrden.addItem("Documento A-Z");
        cmbOrden.addItem("Documento Z-A");
        cmbOrden.addItem("Código A-Z");
        cmbOrden.addItem("Código Z-A");
        cmbOrden.addItem("Estado A-Z");
        fgbc.gridx = 1;
        fgbc.gridy = 1;
        fgbc.weightx = 0.5;
        panelFiltros.add(cmbOrden, fgbc);

        JLabel lblAyuda = new JLabel("Solo se puede eliminar un ejemplar si no tiene préstamos registrados.");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));
        fgbc.gridx = 2;
        fgbc.gridy = 1;
        fgbc.gridwidth = 4;
        fgbc.weightx = 1;
        panelFiltros.add(lblAyuda, fgbc);

        panelTabla.add(panelFiltros, BorderLayout.NORTH);

        tablaEjemplares = new JTable();
        tablaEjemplares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEjemplares.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaEjemplares, Tema.EJEMPLARES);

        JScrollPane scrollTabla = new JScrollPane(tablaEjemplares);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelContenido.add(panelTabla, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarEjemplar());
        btnActualizar.addActionListener(e -> actualizarEjemplar());
        btnEliminar.addActionListener(e -> eliminarEjemplar());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnActualizarLista.addActionListener(e -> {
            txtBuscar.setText("");

            if (cmbFiltroTipo.getItemCount() > 0) {
                cmbFiltroTipo.setSelectedIndex(0);
            }

            if (cmbFiltroCategoria.getItemCount() > 0) {
                cmbFiltroCategoria.setSelectedIndex(0);
            }

            listarEjemplares();
        });

        btnCerrar.addActionListener(e -> dispose());

        cmbFiltroTipo.addActionListener(e -> filtrarTabla());
        cmbFiltroCategoria.addActionListener(e -> filtrarTabla());
        cmbOrden.addActionListener(e -> ordenarTabla());

        tablaEjemplares.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDocumentoDesdeTabla();
            }
        });

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

    private void listarEjemplares() {
        EjemplarDAO dao = new EjemplarDAO();
        listaEjemplaresActual = dao.listar();

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.addColumn("ID Ejemplar");
        modeloTabla.addColumn("ID Documento");
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Fecha registro");
        modeloTabla.addColumn("Observaciones");

        for (Ejemplar e : listaEjemplaresActual) {
            Object[] fila = new Object[9];

            fila[0] = e.getIdEjemplar();
            fila[1] = e.getIdDocumento();
            fila[2] = e.getCodigoEjemplar();
            fila[3] = e.getTituloDocumento();
            fila[4] = e.getTipoDocumento();
            fila[5] = e.getCategoriaDocumento();
            fila[6] = e.getEstado();
            fila[7] = e.getFechaRegistro();
            fila[8] = e.getObservaciones();

            modeloTabla.addRow(fila);
        }

        tablaEjemplares.setModel(modeloTabla);

        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaEjemplares.setRowSorter(sorterTabla);

        ocultarColumnaIdDocumento();

        tablaEjemplares.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaEjemplares.getColumnModel().getColumn(2).setPreferredWidth(130);
        tablaEjemplares.getColumnModel().getColumn(3).setPreferredWidth(260);
        tablaEjemplares.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaEjemplares.getColumnModel().getColumn(5).setPreferredWidth(150);
        tablaEjemplares.getColumnModel().getColumn(6).setPreferredWidth(100);
        tablaEjemplares.getColumnModel().getColumn(7).setPreferredWidth(120);
        tablaEjemplares.getColumnModel().getColumn(8).setPreferredWidth(230);

        cargarCategoriasFiltro();
        ordenarTabla();
        filtrarTabla();
    }

    private void ocultarColumnaIdDocumento() {
        tablaEjemplares.getColumnModel().getColumn(1).setMinWidth(0);
        tablaEjemplares.getColumnModel().getColumn(1).setMaxWidth(0);
        tablaEjemplares.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    private void cargarDocumentoDesdeTabla() {
        int filaSeleccionada = tablaEjemplares.getSelectedRow();

        if (filaSeleccionada == -1 || modeloTabla == null) {
            return;
        }

        int filaModelo = tablaEjemplares.convertRowIndexToModel(filaSeleccionada);

        idEjemplarSeleccionado = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
        idDocumentoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 1).toString());

        String codigoEjemplar = valorTabla(filaModelo, 2);
        String tituloDocumento = valorTabla(filaModelo, 3);
        String tipoDocumento = valorTabla(filaModelo, 4);
        String categoriaDocumento = valorTabla(filaModelo, 5);
        String estado = valorTabla(filaModelo, 6);
        String observaciones = valorTabla(filaModelo, 8);

        txtDocumentoSeleccionado.setText(idDocumentoSeleccionado + " - " + tituloDocumento);
        txtTipoDocumento.setText(tipoDocumento);
        txtCategoriaDocumento.setText(categoriaDocumento);

        if (idEjemplarSeleccionado == 0) {
            txtCodigoEjemplar.setText("");
            cmbEstado.setSelectedIndex(0);
            txtObservaciones.setText("");
        } else {
            txtCodigoEjemplar.setText(codigoEjemplar);
            cmbEstado.setSelectedItem(estado);
            txtObservaciones.setText(observaciones);
        }

        txtCodigoEjemplar.requestFocus();
    }

    private String valorTabla(int filaModelo, int columna) {
        Object valor = modeloTabla.getValueAt(filaModelo, columna);
        return valor == null ? "" : valor.toString();
    }

    private void guardarEjemplar() {
        if (idDocumentoSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar primero un documento de la tabla inferior.");
            txtBuscar.requestFocus();
            return;
        }

        String codigo = txtCodigoEjemplar.getText().trim();
        String estado = cmbEstado.getSelectedItem().toString();
        String observaciones = txtObservaciones.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código del ejemplar.");
            txtCodigoEjemplar.requestFocus();
            return;
        }

        if (codigo.contains(" ")) {
            JOptionPane.showMessageDialog(this, "El código del ejemplar no debe contener espacios.");
            txtCodigoEjemplar.requestFocus();
            return;
        }

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIdDocumento(idDocumentoSeleccionado);
        ejemplar.setCodigoEjemplar(codigo);
        ejemplar.setEstado(estado);
        ejemplar.setObservaciones(observaciones);

        EjemplarDAO dao = new EjemplarDAO();

        if (dao.guardar(ejemplar)) {
            JOptionPane.showMessageDialog(this, "Ejemplar guardado correctamente.");
            limpiarCamposDespuesDeGuardar();
            listarEjemplares();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el ejemplar. Verifique que el código no esté repetido.");
        }
    }

    private void actualizarEjemplar() {
        if (idEjemplarSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ejemplar existente para actualizar.");
            return;
        }

        if (idDocumentoSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un documento válido.");
            return;
        }

        String codigo = txtCodigoEjemplar.getText().trim();
        String estado = cmbEstado.getSelectedItem().toString();
        String observaciones = txtObservaciones.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código del ejemplar.");
            txtCodigoEjemplar.requestFocus();
            return;
        }

        if (codigo.contains(" ")) {
            JOptionPane.showMessageDialog(this, "El código del ejemplar no debe contener espacios.");
            txtCodigoEjemplar.requestFocus();
            return;
        }

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIdEjemplar(idEjemplarSeleccionado);
        ejemplar.setIdDocumento(idDocumentoSeleccionado);
        ejemplar.setCodigoEjemplar(codigo);
        ejemplar.setEstado(estado);
        ejemplar.setObservaciones(observaciones);

        EjemplarDAO dao = new EjemplarDAO();

        if (dao.actualizar(ejemplar)) {
            JOptionPane.showMessageDialog(this, "Ejemplar actualizado correctamente.");
            limpiarCampos();
            listarEjemplares();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el ejemplar. Verifique que el código no esté repetido.");
        }
    }

    private void eliminarEjemplar() {
        if (idEjemplarSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ejemplar existente para eliminar.");
            return;
        }

        String codigo = txtCodigoEjemplar.getText().trim();
        String documento = txtDocumentoSeleccionado.getText().trim();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este ejemplar?\n\n"
                + "Documento: " + documento + "\n"
                + "Código: " + codigo + "\n\n"
                + "Nota: solo se eliminará si no tiene préstamos registrados.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        EjemplarDAO dao = new EjemplarDAO();

        if (dao.eliminar(idEjemplarSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Ejemplar eliminado correctamente.");
            limpiarCampos();
            listarEjemplares();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el ejemplar.\n"
                    + "Es posible que tenga préstamos registrados.\n"
                    + "En ese caso, cambie su estado a Dañado o Extraviado."
            );
        }
    }

    private void cargarCategoriasFiltro() {
        String categoriaSeleccionada = "Todas";

        if (cmbFiltroCategoria.getSelectedItem() != null) {
            categoriaSeleccionada = cmbFiltroCategoria.getSelectedItem().toString();
        }

        cmbFiltroCategoria.removeAllItems();
        cmbFiltroCategoria.addItem("Todas");

        Set<String> categorias = new HashSet<>();

        for (Ejemplar e : listaEjemplaresActual) {
            if (e.getCategoriaDocumento() != null && !e.getCategoriaDocumento().trim().isEmpty()) {
                categorias.add(e.getCategoriaDocumento().trim());
            }
        }

        ArrayList<String> listaCategorias = new ArrayList<>(categorias);
        listaCategorias.sort(String::compareToIgnoreCase);

        for (String categoria : listaCategorias) {
            cmbFiltroCategoria.addItem(categoria);
        }

        cmbFiltroCategoria.setSelectedItem(categoriaSeleccionada);
    }

    private void filtrarTabla() {
        if (sorterTabla == null) {
            return;
        }

        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        String textoBusqueda = txtBuscar.getText().trim();

        if (!textoBusqueda.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda)));
        }

        if (cmbFiltroTipo.getSelectedItem() != null) {
            String tipo = cmbFiltroTipo.getSelectedItem().toString();

            if (!tipo.equalsIgnoreCase("Todos")) {
                filtros.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(tipo) + "$", 4));
            }
        }

        if (cmbFiltroCategoria.getSelectedItem() != null) {
            String categoria = cmbFiltroCategoria.getSelectedItem().toString();

            if (!categoria.equalsIgnoreCase("Todas")) {
                filtros.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(categoria) + "$", 5));
            }
        }

        if (filtros.isEmpty()) {
            sorterTabla.setRowFilter(null);
        } else {
            sorterTabla.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    private void ordenarTabla() {
        if (sorterTabla == null || cmbOrden.getSelectedItem() == null) {
            return;
        }

        String orden = cmbOrden.getSelectedItem().toString();
        ArrayList<RowSorter.SortKey> claves = new ArrayList<>();

        if (orden.equalsIgnoreCase("Documento A-Z")) {
            claves.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        } else if (orden.equalsIgnoreCase("Documento Z-A")) {
            claves.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
        } else if (orden.equalsIgnoreCase("Código A-Z")) {
            claves.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        } else if (orden.equalsIgnoreCase("Código Z-A")) {
            claves.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        } else if (orden.equalsIgnoreCase("Estado A-Z")) {
            claves.add(new RowSorter.SortKey(6, SortOrder.ASCENDING));
        }

        sorterTabla.setSortKeys(claves);
        sorterTabla.sort();
    }

    private void limpiarCampos() {
        idDocumentoSeleccionado = 0;
        idEjemplarSeleccionado = 0;

        txtDocumentoSeleccionado.setText("");
        txtTipoDocumento.setText("");
        txtCategoriaDocumento.setText("");
        txtCodigoEjemplar.setText("");
        cmbEstado.setSelectedIndex(0);
        txtObservaciones.setText("");
        txtBuscar.setText("");

        if (cmbFiltroTipo.getItemCount() > 0) {
            cmbFiltroTipo.setSelectedIndex(0);
        }

        if (cmbFiltroCategoria.getItemCount() > 0) {
            cmbFiltroCategoria.setSelectedIndex(0);
        }

        if (tablaEjemplares != null) {
            tablaEjemplares.clearSelection();
        }

        txtBuscar.requestFocus();
    }

    private void limpiarCamposDespuesDeGuardar() {
        idEjemplarSeleccionado = 0;

        txtCodigoEjemplar.setText("");
        cmbEstado.setSelectedIndex(0);
        txtObservaciones.setText("");
        txtCodigoEjemplar.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmEjemplares frm = new FrmEjemplares();
            frm.setVisible(true);
        });
    }
}
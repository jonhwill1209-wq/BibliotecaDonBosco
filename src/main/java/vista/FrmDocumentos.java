package vista;

import dao.CategoriaDAO;
import dao.DocumentoDAO;
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
import modelo.Documento;

public class FrmDocumentos extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtAnio;
    private JTextField txtUbicacion;
    private JTextArea txtDescripcion;

    private JComboBox<String> cmbTipoDocumento;
    private JComboBox<String> cmbCategoria;

    private JTextField txtNuevaCategoria;
    private JTextField txtBuscar;
    private JTextField txtFiltroAnio;

    private JComboBox<String> cmbFiltroTipo;
    private JComboBox<String> cmbFiltroCategoria;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnAgregarCategoria;
    private JButton btnLimpiar;
    private JButton btnLimpiarFiltros;
    private JButton btnCerrar;

    private JTable tablaDocumentos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorterTabla;

    private int idDocumentoSeleccionado = 0;

    public FrmDocumentos() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        cargarCategorias();
        listarDocumentos();
    }

    private void iniciarComponentes() {
        setTitle("Gestión de Documentos");
        setSize(1150, 760);
        setMinimumSize(new java.awt.Dimension(980, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.DOCUMENTOS);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTituloVentana = new JLabel("Gestión de Documentos");
        lblTituloVentana.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTituloVentana.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Registrar, consultar, actualizar, eliminar y filtrar documentos");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(235, 235, 235));

        panelSuperior.add(lblTituloVentana, BorderLayout.WEST);
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

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblTitulo, gbc);

        txtTitulo = new JTextField();
        Tema.estilizarCampo(txtTitulo);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(txtTitulo, gbc);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblAutor, gbc);

        txtAutor = new JTextField();
        Tema.estilizarCampo(txtAutor);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(txtAutor, gbc);

        JLabel lblEditorial = new JLabel("Editorial:");
        lblEditorial.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblEditorial, gbc);

        txtEditorial = new JTextField();
        Tema.estilizarCampo(txtEditorial);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtEditorial, gbc);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblAnio, gbc);

        txtAnio = new JTextField();
        Tema.estilizarCampo(txtAnio);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtAnio, gbc);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblTipo, gbc);

        cmbTipoDocumento = new JComboBox<>();
        cmbTipoDocumento.addItem("Libro");
        cmbTipoDocumento.addItem("Revista");
        cmbTipoDocumento.addItem("Tesis");
        cmbTipoDocumento.addItem("CD");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(cmbTipoDocumento, gbc);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblCategoria, gbc);

        cmbCategoria = new JComboBox<>();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(cmbCategoria, gbc);

        JLabel lblNuevaCategoria = new JLabel("Nueva categoría:");
        lblNuevaCategoria.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panelFormulario.add(lblNuevaCategoria, gbc);

        txtNuevaCategoria = new JTextField();
        Tema.estilizarCampo(txtNuevaCategoria);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1;
        panelFormulario.add(txtNuevaCategoria, gbc);

        btnAgregarCategoria = new JButton("Agregar categoría");
        Tema.estilizarBoton(btnAgregarCategoria, new Color(46, 204, 113));
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        panelFormulario.add(btnAgregarCategoria, gbc);

        gbc.gridwidth = 1;

        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panelFormulario.add(lblUbicacion, gbc);

        txtUbicacion = new JTextField();
        Tema.estilizarCampo(txtUbicacion);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        panelFormulario.add(txtUbicacion, gbc);

        gbc.gridwidth = 1;

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panelFormulario.add(lblDescripcion, gbc);

        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setFont(Tema.NORMAL);
        txtDescripcion.setBackground(Tema.CAMPO);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelFormulario.add(scrollDescripcion, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        Tema.estilizarBoton(btnGuardar, Tema.DOCUMENTOS);
        Tema.estilizarBoton(btnActualizar, new Color(52, 152, 219));
        Tema.estilizarBoton(btnEliminar, new Color(192, 57, 43));
        Tema.estilizarBoton(btnLimpiar, new Color(127, 140, 141));
        Tema.estilizarBoton(btnCerrar, new Color(100, 100, 100));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        gbc.gridx = 0;
        gbc.gridy = 6;
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
        fgbc.insets = new Insets(5, 6, 5, 6);
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

        JLabel lblFiltroTipo = new JLabel("Tipo:");
        lblFiltroTipo.setFont(Tema.NORMAL);
        fgbc.gridx = 2;
        fgbc.gridy = 0;
        fgbc.weightx = 0;
        panelFiltros.add(lblFiltroTipo, fgbc);

        cmbFiltroTipo = new JComboBox<>();
        cmbFiltroTipo.addItem("Todos");
        cmbFiltroTipo.addItem("Libro");
        cmbFiltroTipo.addItem("Revista");
        cmbFiltroTipo.addItem("Tesis");
        cmbFiltroTipo.addItem("CD");
        fgbc.gridx = 3;
        fgbc.gridy = 0;
        fgbc.weightx = 0.25;
        panelFiltros.add(cmbFiltroTipo, fgbc);

        JLabel lblFiltroCategoria = new JLabel("Categoría:");
        lblFiltroCategoria.setFont(Tema.NORMAL);
        fgbc.gridx = 4;
        fgbc.gridy = 0;
        fgbc.weightx = 0;
        panelFiltros.add(lblFiltroCategoria, fgbc);

        cmbFiltroCategoria = new JComboBox<>();
        fgbc.gridx = 5;
        fgbc.gridy = 0;
        fgbc.weightx = 0.35;
        panelFiltros.add(cmbFiltroCategoria, fgbc);

        JLabel lblFiltroAnio = new JLabel("Año:");
        lblFiltroAnio.setFont(Tema.NORMAL);
        fgbc.gridx = 0;
        fgbc.gridy = 1;
        fgbc.weightx = 0;
        panelFiltros.add(lblFiltroAnio, fgbc);

        txtFiltroAnio = new JTextField();
        Tema.estilizarCampo(txtFiltroAnio);
        fgbc.gridx = 1;
        fgbc.gridy = 1;
        fgbc.weightx = 0.25;
        panelFiltros.add(txtFiltroAnio, fgbc);

        btnLimpiarFiltros = new JButton("Limpiar filtros");
        Tema.estilizarBoton(btnLimpiarFiltros, new Color(127, 140, 141));
        fgbc.gridx = 2;
        fgbc.gridy = 1;
        fgbc.gridwidth = 2;
        fgbc.weightx = 0;
        panelFiltros.add(btnLimpiarFiltros, fgbc);

        JLabel lblAyuda = new JLabel("Filtra por título, autor, editorial, tipo, categoría, ubicación o año.");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));
        fgbc.gridx = 4;
        fgbc.gridy = 1;
        fgbc.gridwidth = 2;
        fgbc.weightx = 1;
        panelFiltros.add(lblAyuda, fgbc);

        panelTabla.add(panelFiltros, BorderLayout.NORTH);

        tablaDocumentos = new JTable();
        tablaDocumentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDocumentos.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaDocumentos, Tema.DOCUMENTOS);

        JScrollPane scrollTabla = new JScrollPane(tablaDocumentos);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelContenido.add(panelTabla, BorderLayout.CENTER);

        tablaDocumentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        btnGuardar.addActionListener(e -> guardarDocumento());
        btnActualizar.addActionListener(e -> actualizarDocumento());
        btnEliminar.addActionListener(e -> eliminarDocumento());
        btnAgregarCategoria.addActionListener(e -> agregarCategoria());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnLimpiarFiltros.addActionListener(e -> limpiarFiltros());
        btnCerrar.addActionListener(e -> dispose());

        cmbFiltroTipo.addActionListener(e -> filtrarTabla());
        cmbFiltroCategoria.addActionListener(e -> filtrarTabla());

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

        txtFiltroAnio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ArrayList<String> lista = categoriaDAO.listarCategorias();

        cmbCategoria.removeAllItems();

        for (String categoria : lista) {
            cmbCategoria.addItem(categoria);
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

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valor = modeloTabla.getValueAt(i, 6);

            if (valor != null && !valor.toString().trim().isEmpty()) {
                categorias.add(valor.toString().trim());
            }
        }

        ArrayList<String> listaCategorias = new ArrayList<>(categorias);
        listaCategorias.sort(String::compareToIgnoreCase);

        for (String categoria : listaCategorias) {
            cmbFiltroCategoria.addItem(categoria);
        }

        cmbFiltroCategoria.setSelectedItem(categoriaSeleccionada);
    }

    private void agregarCategoria() {
        String nuevaCategoria = txtNuevaCategoria.getText().trim();

        if (nuevaCategoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre de la nueva categoría.");
            txtNuevaCategoria.requestFocus();
            return;
        }

        CategoriaDAO categoriaDAO = new CategoriaDAO();

        if (categoriaDAO.existeCategoria(nuevaCategoria)) {
            JOptionPane.showMessageDialog(this, "La categoría ya existe.");
            txtNuevaCategoria.requestFocus();
            return;
        }

        if (categoriaDAO.guardarCategoria(nuevaCategoria)) {
            JOptionPane.showMessageDialog(this, "Categoría agregada correctamente.");
            txtNuevaCategoria.setText("");
            cargarCategorias();
            cmbCategoria.setSelectedItem(nuevaCategoria);
            listarDocumentos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo agregar la categoría.");
        }
    }

    private void guardarDocumento() {
        Documento documento = obtenerDocumentoDesdeFormulario(false);

        if (documento == null) {
            return;
        }

        DocumentoDAO dao = new DocumentoDAO();

        if (dao.guardar(documento)) {
            JOptionPane.showMessageDialog(this, "Documento guardado correctamente.");
            limpiarCampos();
            cargarCategorias();
            listarDocumentos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el documento.");
        }
    }

    private void actualizarDocumento() {
        if (idDocumentoSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un documento de la tabla para actualizar.");
            return;
        }

        Documento documento = obtenerDocumentoDesdeFormulario(true);

        if (documento == null) {
            return;
        }

        DocumentoDAO dao = new DocumentoDAO();

        if (dao.actualizar(documento)) {
            JOptionPane.showMessageDialog(this, "Documento actualizado correctamente.");
            limpiarCampos();
            cargarCategorias();
            listarDocumentos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el documento.");
        }
    }

    private void eliminarDocumento() {
        if (idDocumentoSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un documento de la tabla para eliminar.");
            return;
        }

        String titulo = txtTitulo.getText().trim();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este documento?\n\n"
                + "Documento: " + titulo + "\n\n"
                + "Nota: solo se eliminará si no tiene ejemplares registrados.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        DocumentoDAO dao = new DocumentoDAO();

        if (dao.eliminar(idDocumentoSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Documento eliminado correctamente.");
            limpiarCampos();
            cargarCategorias();
            listarDocumentos();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el documento.\n"
                    + "Es posible que tenga ejemplares registrados.\n"
                    + "Primero debe eliminar sus ejemplares desde Gestión de Ejemplares."
            );
        }
    }

    private Documento obtenerDocumentoDesdeFormulario(boolean incluirId) {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String anioTexto = txtAnio.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el título del documento.");
            txtTitulo.requestFocus();
            return null;
        }

        if (anioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el año de publicación.");
            txtAnio.requestFocus();
            return null;
        }

        if (cmbCategoria.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una categoría. Si no existe, agréguela primero.");
            return null;
        }

        int anioPublicacion;

        try {
            anioPublicacion = Integer.parseInt(anioTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año de publicación debe ser numérico.");
            txtAnio.requestFocus();
            return null;
        }

        if (anioPublicacion < 1900 || anioPublicacion > 2026) {
            JOptionPane.showMessageDialog(this, "El año de publicación debe estar entre 1900 y 2026.");
            txtAnio.requestFocus();
            return null;
        }

        String categoriaSeleccionada = cmbCategoria.getSelectedItem().toString();

        Documento documento = new Documento();

        if (incluirId) {
            documento.setIdDocumento(idDocumentoSeleccionado);
        }

        documento.setTitulo(titulo);
        documento.setAutor(autor);
        documento.setEditorial(editorial);
        documento.setAnioPublicacion(anioPublicacion);
        documento.setIdTipoDocumento(cmbTipoDocumento.getSelectedIndex() + 1);
        documento.setCategoria(categoriaSeleccionada);
        documento.setUbicacion(ubicacion);
        documento.setDescripcion(descripcion);

        return documento;
    }

    private void listarDocumentos() {
        DocumentoDAO dao = new DocumentoDAO();
        ArrayList<Documento> lista = dao.listar();

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Editorial");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Ubicación");
        modeloTabla.addColumn("Descripción");

        for (Documento d : lista) {
            Object[] fila = new Object[9];

            fila[0] = d.getIdDocumento();
            fila[1] = d.getTitulo();
            fila[2] = d.getAutor();
            fila[3] = d.getEditorial();
            fila[4] = d.getAnioPublicacion();
            fila[5] = d.getNombreTipo();
            fila[6] = d.getCategoria();
            fila[7] = d.getUbicacion();
            fila[8] = d.getDescripcion();

            modeloTabla.addRow(fila);
        }

        tablaDocumentos.setModel(modeloTabla);

        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaDocumentos.setRowSorter(sorterTabla);

        sorterTabla.setSortKeys(
                java.util.List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING))
        );

        tablaDocumentos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaDocumentos.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaDocumentos.getColumnModel().getColumn(2).setPreferredWidth(160);
        tablaDocumentos.getColumnModel().getColumn(3).setPreferredWidth(130);
        tablaDocumentos.getColumnModel().getColumn(4).setPreferredWidth(60);
        tablaDocumentos.getColumnModel().getColumn(5).setPreferredWidth(90);
        tablaDocumentos.getColumnModel().getColumn(6).setPreferredWidth(150);
        tablaDocumentos.getColumnModel().getColumn(7).setPreferredWidth(120);

        ocultarColumnaDescripcion();
        cargarCategoriasFiltro();
        filtrarTabla();
    }

    private void ocultarColumnaDescripcion() {
        tablaDocumentos.getColumnModel().getColumn(8).setMinWidth(0);
        tablaDocumentos.getColumnModel().getColumn(8).setMaxWidth(0);
        tablaDocumentos.getColumnModel().getColumn(8).setPreferredWidth(0);
    }

    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaDocumentos.getSelectedRow();

        if (filaSeleccionada == -1 || modeloTabla == null) {
            return;
        }

        int filaModelo = tablaDocumentos.convertRowIndexToModel(filaSeleccionada);

        idDocumentoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());

        txtTitulo.setText(valorTabla(filaModelo, 1));
        txtAutor.setText(valorTabla(filaModelo, 2));
        txtEditorial.setText(valorTabla(filaModelo, 3));
        txtAnio.setText(valorTabla(filaModelo, 4));

        String tipo = valorTabla(filaModelo, 5);
        cmbTipoDocumento.setSelectedItem(tipo);

        String categoria = valorTabla(filaModelo, 6);

        if (categoria != null && !categoria.isEmpty()) {
            cmbCategoria.setSelectedItem(categoria);
        }

        txtUbicacion.setText(valorTabla(filaModelo, 7));
        txtDescripcion.setText(valorTabla(filaModelo, 8));
    }

    private String valorTabla(int filaModelo, int columna) {
        Object valor = modeloTabla.getValueAt(filaModelo, columna);
        return valor == null ? "" : valor.toString();
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
                filtros.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(tipo) + "$", 5));
            }
        }

        if (cmbFiltroCategoria.getSelectedItem() != null) {
            String categoria = cmbFiltroCategoria.getSelectedItem().toString();

            if (!categoria.equalsIgnoreCase("Todas")) {
                filtros.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(categoria) + "$", 6));
            }
        }

        String anio = txtFiltroAnio.getText().trim();

        if (!anio.isEmpty()) {
            filtros.add(RowFilter.regexFilter("^" + Pattern.quote(anio), 4));
        }

        if (filtros.isEmpty()) {
            sorterTabla.setRowFilter(null);
        } else {
            sorterTabla.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    private void limpiarFiltros() {
        txtBuscar.setText("");
        txtFiltroAnio.setText("");

        if (cmbFiltroTipo.getItemCount() > 0) {
            cmbFiltroTipo.setSelectedIndex(0);
        }

        if (cmbFiltroCategoria.getItemCount() > 0) {
            cmbFiltroCategoria.setSelectedIndex(0);
        }

        filtrarTabla();
    }

    private void limpiarCampos() {
        idDocumentoSeleccionado = 0;

        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtAnio.setText("");
        txtUbicacion.setText("");
        txtDescripcion.setText("");
        txtNuevaCategoria.setText("");

        cmbTipoDocumento.setSelectedIndex(0);

        if (cmbCategoria.getItemCount() > 0) {
            cmbCategoria.setSelectedIndex(0);
        }

        if (tablaDocumentos != null) {
            tablaDocumentos.clearSelection();
        }

        txtTitulo.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmDocumentos frm = new FrmDocumentos();
            frm.setVisible(true);
        });
    }
}
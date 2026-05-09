package vista;

import dao.PrestamoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import modelo.Prestamo;

public class FrmHistorialPrestamos extends JFrame {

    private JTextField txtBuscar;
    private JComboBox<String> cmbEstado;

    private JButton btnActualizar;
    private JButton btnExportar;
    private JButton btnCerrar;

    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorterTabla;

    public FrmHistorialPrestamos() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        listarHistorial();
    }

    private void iniciarComponentes() {
        setTitle("Historial de Préstamos");
        setSize(1150, 700);
        setMinimumSize(new java.awt.Dimension(950, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.PRESTAMOS);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTitulo = new JLabel("Historial de Préstamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Consulta general de préstamos activos, devueltos y mora calculada");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(235, 235, 235));

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblSubtitulo, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBackground(Tema.FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        add(panelContenido, BorderLayout.CENTER);

        JPanel panelFiltros = new JPanel(new BorderLayout(10, 10));
        panelFiltros.setBackground(Tema.PANEL);
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
        panelBusqueda.setBackground(Tema.PANEL);

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(Tema.NORMAL);

        txtBuscar = new JTextField();
        Tema.estilizarCampo(txtBuscar);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(Tema.NORMAL);

        cmbEstado = new JComboBox<>();
        cmbEstado.addItem("Todos");
        cmbEstado.addItem("Activo");
        cmbEstado.addItem("Devuelto");

        panelBusqueda.add(lblBuscar, BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelEstado.setBackground(Tema.PANEL);
        panelEstado.add(lblEstado);
        panelEstado.add(cmbEstado);

        panelBusqueda.add(panelEstado, BorderLayout.EAST);

        panelFiltros.add(panelBusqueda, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnActualizar = new JButton("Actualizar");
        btnExportar = new JButton("Exportar reporte");
        btnCerrar = new JButton("Cerrar");

        Tema.estilizarBoton(btnActualizar, new Color(52, 152, 219));
        Tema.estilizarBoton(btnExportar, new Color(39, 174, 96));
        Tema.estilizarBoton(btnCerrar, new Color(192, 57, 43));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);
        panelBotones.add(btnCerrar);

        panelFiltros.add(panelBotones, BorderLayout.SOUTH);

        panelContenido.add(panelFiltros, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setBackground(Tema.PANEL);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTabla = new JLabel("Registros de préstamos");
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTabla.setForeground(Tema.PRESTAMOS);

        panelTabla.add(lblTabla, BorderLayout.NORTH);

        tablaHistorial = new JTable();
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaHistorial.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaHistorial, Tema.PRESTAMOS);

        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelContenido.add(panelTabla, BorderLayout.CENTER);

        btnActualizar.addActionListener(e -> listarHistorial());
        btnExportar.addActionListener(e -> exportarReporteCSV());
        btnCerrar.addActionListener(e -> dispose());

        cmbEstado.addActionListener(e -> filtrarTabla());

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

    private void listarHistorial() {
        PrestamoDAO dao = new PrestamoDAO();
        ArrayList<Prestamo> lista = dao.listarHistorial();

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Código ejemplar");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Fecha préstamo");
        modeloTabla.addColumn("Fecha estimada");
        modeloTabla.addColumn("Fecha devolución");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Mora");

        for (Prestamo p : lista) {
            Object[] fila = new Object[9];

            fila[0] = p.getIdPrestamo();
            fila[1] = p.getNombreUsuario();
            fila[2] = p.getCodigoEjemplar();
            fila[3] = p.getTituloDocumento();
            fila[4] = p.getFechaPrestamo();
            fila[5] = p.getFechaDevolucionEstimada();
            fila[6] = p.getFechaDevolucionReal().isEmpty() ? "Pendiente" : p.getFechaDevolucionReal();
            fila[7] = p.getEstado();
            fila[8] = "$ " + String.format(java.util.Locale.US, "%.2f", p.getMoraCalculada());

            modeloTabla.addRow(fila);
        }

        tablaHistorial.setModel(modeloTabla);

        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaHistorial.setRowSorter(sorterTabla);

        sorterTabla.setSortKeys(
                java.util.List.of(new RowSorter.SortKey(4, SortOrder.DESCENDING))
        );

        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(130);
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(280);
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(120);
        tablaHistorial.getColumnModel().getColumn(6).setPreferredWidth(120);
        tablaHistorial.getColumnModel().getColumn(7).setPreferredWidth(90);
        tablaHistorial.getColumnModel().getColumn(8).setPreferredWidth(90);

        filtrarTabla();
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

        if (cmbEstado.getSelectedItem() != null) {
            String estado = cmbEstado.getSelectedItem().toString();

            if (!estado.equalsIgnoreCase("Todos")) {
                filtros.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(estado) + "$", 7));
            }
        }

        if (filtros.isEmpty()) {
            sorterTabla.setRowFilter(null);
        } else {
            sorterTabla.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    private void exportarReporteCSV() {
        if (modeloTabla == null || tablaHistorial.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para exportar.");
            return;
        }

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte");

        File archivoSugerido = new File("reporte_historial_prestamos.csv");
        selector.setSelectedFile(archivoSugerido);

        int opcion = selector.showSaveDialog(this);

        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();

        if (!archivo.getName().toLowerCase().endsWith(".csv")) {
            archivo = new File(archivo.getAbsolutePath() + ".csv");
        }

        try (
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)
            )
        ) {

            writer.write("\uFEFF");

            for (int col = 0; col < modeloTabla.getColumnCount(); col++) {
                writer.write(formatoCSV(modeloTabla.getColumnName(col)));

                if (col < modeloTabla.getColumnCount() - 1) {
                    writer.write(",");
                }
            }

            writer.newLine();

            for (int filaVista = 0; filaVista < tablaHistorial.getRowCount(); filaVista++) {
                int filaModelo = tablaHistorial.convertRowIndexToModel(filaVista);

                for (int col = 0; col < modeloTabla.getColumnCount(); col++) {
                    Object valor = modeloTabla.getValueAt(filaModelo, col);
                    writer.write(formatoCSV(valor == null ? "" : valor.toString()));

                    if (col < modeloTabla.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }

                writer.newLine();
            }

            JOptionPane.showMessageDialog(this, "Reporte exportado correctamente:\n" + archivo.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar el reporte:\n" + e.getMessage());
        }
    }

    private String formatoCSV(String valor) {
        String texto = valor.replace("\"", "\"\"");

        if (texto.contains(",") || texto.contains("\"") || texto.contains("\n")) {
            texto = "\"" + texto + "\"";
        }

        return texto;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmHistorialPrestamos frm = new FrmHistorialPrestamos();
            frm.setVisible(true);
        });
    }
}
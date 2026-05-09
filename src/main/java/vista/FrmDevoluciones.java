package vista;

import dao.PrestamoDAO;
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

public class FrmDevoluciones extends JFrame {

    private JTextField txtPrestamoSeleccionado;
    private JTextField txtFechaEstimada;
    private JTextField txtFechaActual;
    private JTextField txtDiasAtraso;
    private JTextField txtMoraDiaria;
    private JTextField txtMoraTotal;

    private JTextField txtBuscar;

    private JTable tablaPrestamos;

    private JButton btnDevolver;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorterTabla;

    private int idPrestamoSeleccionado = 0;

    public FrmDevoluciones() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        listarPrestamosActivos();
    }

    private void iniciarComponentes() {
        setTitle("Gestión de Devoluciones");
        setSize(1120, 730);
        setMinimumSize(new java.awt.Dimension(970, 620));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.DEVOLUCIONES);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTitulo = new JLabel("Gestión de Devoluciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Registrar entregas y visualizar cálculo de mora");
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

        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Tema.PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPrestamoSeleccionado = new JLabel("Préstamo seleccionado:");
        lblPrestamoSeleccionado.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelDatos.add(lblPrestamoSeleccionado, gbc);

        txtPrestamoSeleccionado = new JTextField();
        Tema.estilizarCampo(txtPrestamoSeleccionado);
        txtPrestamoSeleccionado.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        panelDatos.add(txtPrestamoSeleccionado, gbc);

        gbc.gridwidth = 1;

        JLabel lblFechaEstimada = new JLabel("Fecha estimada:");
        lblFechaEstimada.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(lblFechaEstimada, gbc);

        txtFechaEstimada = new JTextField();
        Tema.estilizarCampo(txtFechaEstimada);
        txtFechaEstimada.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelDatos.add(txtFechaEstimada, gbc);

        JLabel lblFechaActual = new JLabel("Fecha actual:");
        lblFechaActual.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(lblFechaActual, gbc);

        txtFechaActual = new JTextField();
        Tema.estilizarCampo(txtFechaActual);
        txtFechaActual.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelDatos.add(txtFechaActual, gbc);

        JLabel lblDiasAtraso = new JLabel("Días atraso:");
        lblDiasAtraso.setFont(Tema.NORMAL);
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(lblDiasAtraso, gbc);

        txtDiasAtraso = new JTextField();
        Tema.estilizarCampo(txtDiasAtraso);
        txtDiasAtraso.setEditable(false);
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelDatos.add(txtDiasAtraso, gbc);

        JLabel lblMoraDiaria = new JLabel("Mora diaria:");
        lblMoraDiaria.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelDatos.add(lblMoraDiaria, gbc);

        txtMoraDiaria = new JTextField();
        Tema.estilizarCampo(txtMoraDiaria);
        txtMoraDiaria.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelDatos.add(txtMoraDiaria, gbc);

        JLabel lblMoraTotal = new JLabel("Mora total:");
        lblMoraTotal.setFont(Tema.NORMAL);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelDatos.add(lblMoraTotal, gbc);

        txtMoraTotal = new JTextField();
        Tema.estilizarCampo(txtMoraTotal);
        txtMoraTotal.setEditable(false);
        txtMoraTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelDatos.add(txtMoraTotal, gbc);

        JLabel lblAyuda = new JLabel("La mora se calcula según los días de atraso y la mora diaria configurada para el año actual.");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelDatos.add(lblAyuda, gbc);

        panelOperacion.add(panelDatos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBotones.setBackground(Tema.PANEL);

        btnDevolver = new JButton("Registrar devolución");
        btnActualizar = new JButton("Actualizar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        Tema.estilizarBoton(btnDevolver, Tema.DEVOLUCIONES);
        Tema.estilizarBoton(btnActualizar, new Color(52, 152, 219));
        Tema.estilizarBoton(btnLimpiar, new Color(127, 140, 141));
        Tema.estilizarBoton(btnCerrar, new Color(192, 57, 43));

        panelBotones.add(btnDevolver);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        panelOperacion.add(panelBotones, BorderLayout.SOUTH);

        panelContenido.add(panelOperacion, BorderLayout.NORTH);

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

        JLabel lblAyudaBusqueda = new JLabel("Buscar por usuario, código, documento, fecha o estado");
        lblAyudaBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyudaBusqueda.setForeground(new Color(90, 90, 90));

        panelBusqueda.add(lblBuscar, BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(lblAyudaBusqueda, BorderLayout.EAST);

        panelTabla.add(panelBusqueda, BorderLayout.NORTH);

        tablaPrestamos = new JTable();
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaPrestamos, Tema.DEVOLUCIONES);

        JScrollPane scrollTabla = new JScrollPane(tablaPrestamos);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelContenido.add(panelTabla, BorderLayout.CENTER);

        tablaPrestamos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarPrestamoSeleccionado();
            }
        });

        btnDevolver.addActionListener(e -> registrarDevolucion());
        btnActualizar.addActionListener(e -> actualizarPantalla());
        btnLimpiar.addActionListener(e -> limpiarSeleccion());
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

    private void listarPrestamosActivos() {
        PrestamoDAO prestamoDAO = new PrestamoDAO();
        ArrayList<Prestamo> lista = prestamoDAO.listarActivos();

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
        modeloTabla.addColumn("Estado");

        for (Prestamo p : lista) {
            Object[] fila = new Object[7];

            fila[0] = p.getIdPrestamo();
            fila[1] = p.getNombreUsuario();
            fila[2] = p.getCodigoEjemplar();
            fila[3] = p.getTituloDocumento();
            fila[4] = p.getFechaPrestamo();
            fila[5] = p.getFechaDevolucionEstimada();
            fila[6] = p.getEstado();

            modeloTabla.addRow(fila);
        }

        tablaPrestamos.setModel(modeloTabla);

        sorterTabla = new TableRowSorter<>(modeloTabla);
        tablaPrestamos.setRowSorter(sorterTabla);

        sorterTabla.setSortKeys(
                java.util.List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING))
        );

        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(130);
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(320);
        tablaPrestamos.getColumnModel().getColumn(4).setPreferredWidth(130);
        tablaPrestamos.getColumnModel().getColumn(5).setPreferredWidth(130);
        tablaPrestamos.getColumnModel().getColumn(6).setPreferredWidth(90);

        filtrarTabla();
    }

    private void cargarPrestamoSeleccionado() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();

        if (filaSeleccionada == -1 || modeloTabla == null) {
            return;
        }

        int filaModelo = tablaPrestamos.convertRowIndexToModel(filaSeleccionada);

        idPrestamoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());

        String usuario = valorTabla(filaModelo, 1);
        String codigo = valorTabla(filaModelo, 2);
        String documento = valorTabla(filaModelo, 3);
        String fechaEstimada = valorTabla(filaModelo, 5);

        txtPrestamoSeleccionado.setText(
                idPrestamoSeleccionado + " - "
                + usuario + " - "
                + codigo + " - "
                + documento
        );

        txtFechaEstimada.setText(fechaEstimada);

        cargarCalculoMora();
    }

    private void cargarCalculoMora() {
        if (idPrestamoSeleccionado == 0) {
            limpiarCalculoMora();
            return;
        }

        PrestamoDAO dao = new PrestamoDAO();
        Object[] datos = dao.obtenerCalculoMora(idPrestamoSeleccionado);

        txtFechaEstimada.setText(texto(datos[0]));
        txtFechaActual.setText(texto(datos[1]));
        txtDiasAtraso.setText(texto(datos[2]));
        txtMoraDiaria.setText(moneda(datos[3]));
        txtMoraTotal.setText(moneda(datos[4]));
    }

    private void registrarDevolucion() {
        if (idPrestamoSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un préstamo activo de la tabla.");
            return;
        }

        int filaSeleccionada = tablaPrestamos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un préstamo activo de la tabla.");
            return;
        }

        int filaModelo = tablaPrestamos.convertRowIndexToModel(filaSeleccionada);

        String usuario = valorTabla(filaModelo, 1);
        String codigo = valorTabla(filaModelo, 2);
        String documento = valorTabla(filaModelo, 3);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea registrar la devolución?\n\n"
                + "Usuario: " + usuario + "\n"
                + "Ejemplar: " + codigo + "\n"
                + "Documento: " + documento + "\n\n"
                + "Días de atraso: " + txtDiasAtraso.getText() + "\n"
                + "Mora diaria: " + txtMoraDiaria.getText() + "\n"
                + "Mora total: " + txtMoraTotal.getText(),
                "Confirmar devolución",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        PrestamoDAO prestamoDAO = new PrestamoDAO();

        boolean devuelto = prestamoDAO.registrarDevolucion(idPrestamoSeleccionado);

        if (devuelto) {
            JOptionPane.showMessageDialog(this, "Devolución registrada correctamente.");
            actualizarPantalla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la devolución.");
        }
    }

    private String valorTabla(int filaModelo, int columna) {
        Object valor = modeloTabla.getValueAt(filaModelo, columna);
        return valor == null ? "" : valor.toString();
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }

    private String moneda(Object valor) {
        double monto = 0;

        if (valor instanceof Number) {
            monto = ((Number) valor).doubleValue();
        } else {
            try {
                monto = Double.parseDouble(valor.toString());
            } catch (Exception e) {
                monto = 0;
            }
        }

        return "$ " + String.format(java.util.Locale.US, "%.2f", monto);
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

    private void limpiarSeleccion() {
        idPrestamoSeleccionado = 0;

        txtPrestamoSeleccionado.setText("");
        txtBuscar.setText("");
        limpiarCalculoMora();

        if (tablaPrestamos != null) {
            tablaPrestamos.clearSelection();
        }

        txtBuscar.requestFocus();
    }

    private void limpiarCalculoMora() {
        txtFechaEstimada.setText("");
        txtFechaActual.setText("");
        txtDiasAtraso.setText("");
        txtMoraDiaria.setText("");
        txtMoraTotal.setText("");
    }

    private void actualizarPantalla() {
        limpiarSeleccion();
        listarPrestamosActivos();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmDevoluciones frm = new FrmDevoluciones();
            frm.setVisible(true);
        });
    }
}
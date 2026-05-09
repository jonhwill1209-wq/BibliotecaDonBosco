package vista;

import dao.ConfiguracionDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class FrmConfiguracion extends JFrame {

    private JComboBox<String> cmbRol;
    private JTextField txtMaxEjemplares;
    private JTextField txtDiasPrestamo;

    private JTextField txtAnio;
    private JTextField txtMoraDiaria;

    private JButton btnGuardarPrestamo;
    private JButton btnGuardarMora;
    private JButton btnCerrar;

    private JTable tablaPrestamos;
    private JTable tablaMora;

    public FrmConfiguracion() {
        Tema.aplicarNimbus();
        iniciarComponentes();
        listarConfiguracionPrestamo();
        listarMora();
    }

    private void iniciarComponentes() {
        setTitle("Configuración de Préstamos y Mora");
        setSize(1050, 700);
        setMinimumSize(new java.awt.Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FONDO);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Tema.CONFIGURACION);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel lblTitulo = new JLabel("Configuración");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Límites de préstamo, días permitidos y mora diaria");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(235, 235, 235));

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(lblSubtitulo, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBackground(Tema.FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        add(panelContenido, BorderLayout.CENTER);

        JPanel panelPrestamo = crearPanelPrestamos();
        JPanel panelMora = crearPanelMora();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelPrestamo, panelMora);
        splitPane.setResizeWeight(0.50);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        panelContenido.add(splitPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(Tema.FONDO);

        btnCerrar = new JButton("Cerrar");
        Tema.estilizarBoton(btnCerrar, new Color(192, 57, 43));
        panelInferior.add(btnCerrar);

        panelContenido.add(panelInferior, BorderLayout.SOUTH);

        btnGuardarPrestamo.addActionListener(e -> guardarConfiguracionPrestamo());
        btnGuardarMora.addActionListener(e -> guardarMora());
        btnCerrar.addActionListener(e -> dispose());

        cmbRol.addActionListener(e -> cargarConfiguracionSeleccionada());
    }

    private JPanel crearPanelPrestamos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Tema.PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel lblTitulo = new JLabel("Configuración de préstamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Tema.CONFIGURACION);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Tema.PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblRol, gbc);

        cmbRol = new JComboBox<>();
        cmbRol.addItem("Profesor");
        cmbRol.addItem("Alumno");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(cmbRol, gbc);

        JLabel lblMax = new JLabel("Máx. ejemplares:");
        lblMax.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblMax, gbc);

        txtMaxEjemplares = new JTextField();
        Tema.estilizarCampo(txtMaxEjemplares);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtMaxEjemplares, gbc);

        JLabel lblDias = new JLabel("Días préstamo:");
        lblDias.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(lblDias, gbc);

        txtDiasPrestamo = new JTextField();
        Tema.estilizarCampo(txtDiasPrestamo);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        panelFormulario.add(txtDiasPrestamo, gbc);

        JLabel lblAyuda = new JLabel("Define cuántos ejemplares puede prestar cada rol y por cuántos días.");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelFormulario.add(lblAyuda, gbc);

        btnGuardarPrestamo = new JButton("Guardar configuración");
        Tema.estilizarBoton(btnGuardarPrestamo, Tema.CONFIGURACION);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelFormulario.add(btnGuardarPrestamo, gbc);

        panel.add(panelFormulario, BorderLayout.NORTH);

        tablaPrestamos = new JTable();
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaPrestamos, Tema.CONFIGURACION);

        JScrollPane scrollTabla = new JScrollPane(tablaPrestamos);
        panel.add(scrollTabla, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelMora() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Tema.PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel lblTitulo = new JLabel("Configuración de mora");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Tema.CONFIGURACION);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Tema.PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(lblAnio, gbc);

        txtAnio = new JTextField();
        Tema.estilizarCampo(txtAnio);
        txtAnio.setText(String.valueOf(LocalDate.now().getYear()));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelFormulario.add(txtAnio, gbc);

        JLabel lblMora = new JLabel("Mora diaria $:");
        lblMora.setFont(Tema.NORMAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(lblMora, gbc);

        txtMoraDiaria = new JTextField();
        Tema.estilizarCampo(txtMoraDiaria);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panelFormulario.add(txtMoraDiaria, gbc);

        JLabel lblAyuda = new JLabel("Define cuánto se cobrará por cada día de atraso según el año.");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(90, 90, 90));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelFormulario.add(lblAyuda, gbc);

        btnGuardarMora = new JButton("Guardar mora");
        Tema.estilizarBoton(btnGuardarMora, Tema.CONFIGURACION);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panelFormulario.add(btnGuardarMora, gbc);

        panel.add(panelFormulario, BorderLayout.NORTH);

        tablaMora = new JTable();
        tablaMora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMora.setRowSelectionAllowed(true);
        Tema.estilizarTabla(tablaMora, Tema.CONFIGURACION);

        JScrollPane scrollTabla = new JScrollPane(tablaMora);
        panel.add(scrollTabla, BorderLayout.CENTER);

        return panel;
    }

    private void guardarConfiguracionPrestamo() {
        String maxTexto = txtMaxEjemplares.getText().trim();
        String diasTexto = txtDiasPrestamo.getText().trim();

        if (maxTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cantidad máxima de ejemplares.");
            txtMaxEjemplares.requestFocus();
            return;
        }

        if (diasTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar los días de préstamo.");
            txtDiasPrestamo.requestFocus();
            return;
        }

        int maxEjemplares;
        int diasPrestamo;

        try {
            maxEjemplares = Integer.parseInt(maxTexto);
            diasPrestamo = Integer.parseInt(diasTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los valores deben ser numéricos.");
            return;
        }

        if (maxEjemplares <= 0) {
            JOptionPane.showMessageDialog(this, "El máximo de ejemplares debe ser mayor que 0.");
            txtMaxEjemplares.requestFocus();
            return;
        }

        if (diasPrestamo <= 0) {
            JOptionPane.showMessageDialog(this, "Los días de préstamo deben ser mayores que 0.");
            txtDiasPrestamo.requestFocus();
            return;
        }

        int idRol;

        if (cmbRol.getSelectedItem().toString().equalsIgnoreCase("Profesor")) {
            idRol = 2;
        } else {
            idRol = 3;
        }

        ConfiguracionDAO dao = new ConfiguracionDAO();

        if (dao.guardarConfiguracionPrestamo(idRol, maxEjemplares, diasPrestamo)) {
            JOptionPane.showMessageDialog(this, "Configuración de préstamo guardada correctamente.");
            listarConfiguracionPrestamo();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la configuración.");
        }
    }

    private void guardarMora() {
        String anioTexto = txtAnio.getText().trim();
        String moraTexto = txtMoraDiaria.getText().trim();

        if (anioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el año.");
            txtAnio.requestFocus();
            return;
        }

        if (moraTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la mora diaria.");
            txtMoraDiaria.requestFocus();
            return;
        }

        int anio;
        double moraDiaria;

        try {
            anio = Integer.parseInt(anioTexto);
            moraDiaria = Double.parseDouble(moraTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año y la mora deben ser valores numéricos.");
            return;
        }

        if (anio < 2000 || anio > 2100) {
            JOptionPane.showMessageDialog(this, "El año debe estar entre 2000 y 2100.");
            txtAnio.requestFocus();
            return;
        }

        if (moraDiaria < 0) {
            JOptionPane.showMessageDialog(this, "La mora diaria no puede ser negativa.");
            txtMoraDiaria.requestFocus();
            return;
        }

        ConfiguracionDAO dao = new ConfiguracionDAO();

        if (dao.guardarMora(anio, moraDiaria)) {
            JOptionPane.showMessageDialog(this, "Mora guardada correctamente.");
            txtMoraDiaria.setText("");
            listarMora();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la mora.");
        }
    }

    private void listarConfiguracionPrestamo() {
        ConfiguracionDAO dao = new ConfiguracionDAO();
        ArrayList<Object[]> lista = dao.listarConfiguracionPrestamo();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID Rol");
        modelo.addColumn("Rol");
        modelo.addColumn("Máx. ejemplares");
        modelo.addColumn("Días préstamo");

        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }

        tablaPrestamos.setModel(modelo);

        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(140);
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(140);

        cargarConfiguracionSeleccionada();
    }

    private void listarMora() {
        ConfiguracionDAO dao = new ConfiguracionDAO();
        ArrayList<Object[]> lista = dao.listarMora();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Año");
        modelo.addColumn("Mora diaria");

        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }

        tablaMora.setModel(modelo);

        tablaMora.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaMora.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaMora.getColumnModel().getColumn(2).setPreferredWidth(140);
    }

    private void cargarConfiguracionSeleccionada() {
        if (tablaPrestamos == null || tablaPrestamos.getRowCount() == 0 || cmbRol == null || cmbRol.getSelectedItem() == null) {
            return;
        }

        String rolSeleccionado = cmbRol.getSelectedItem().toString();

        for (int i = 0; i < tablaPrestamos.getRowCount(); i++) {
            String rolTabla = tablaPrestamos.getValueAt(i, 1).toString();

            if (rolTabla.equalsIgnoreCase(rolSeleccionado)) {
                txtMaxEjemplares.setText(tablaPrestamos.getValueAt(i, 2).toString());
                txtDiasPrestamo.setText(tablaPrestamos.getValueAt(i, 3).toString());
                return;
            }
        }

        txtMaxEjemplares.setText("");
        txtDiasPrestamo.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmConfiguracion frm = new FrmConfiguracion();
            frm.setVisible(true);
        });
    }
}
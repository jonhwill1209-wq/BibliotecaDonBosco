package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

public class Tema {

    public static final Color FONDO = new Color(232, 236, 240);
    public static final Color PANEL = new Color(241, 243, 245);
    public static final Color CAMPO = new Color(250, 250, 250);
    public static final Color TEXTO_OSCURO = new Color(35, 47, 62);
    public static final Color BLANCO = Color.WHITE;

    public static final Color USUARIOS = new Color(41, 128, 185);
    public static final Color DOCUMENTOS = new Color(39, 174, 96);
    public static final Color EJEMPLARES = new Color(230, 126, 34);
    public static final Color PRESTAMOS = new Color(52, 73, 94);
    public static final Color DEVOLUCIONES = new Color(192, 57, 43);
    public static final Color CONFIGURACION = new Color(127, 140, 141);

    public static final Font TITULO = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOTON = new Font("Segoe UI", Font.BOLD, 14);

    public static void aplicarNimbus() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus: " + e.getMessage());
        }
    }

    public static void estilizarBoton(JButton boton, Color color) {
        boton.setFont(BOTON);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(NORMAL);
        campo.setBackground(CAMPO);
    }

    public static void estilizarTabla(JTable tabla, Color color) {
    tabla.setFont(NORMAL);
    tabla.setRowHeight(28);
    tabla.setShowGrid(true);
    tabla.setGridColor(new Color(210, 210, 210));
    tabla.setBackground(new Color(250, 250, 250));
    tabla.setForeground(Color.BLACK);
    tabla.setSelectionBackground(new Color(210, 225, 245));
    tabla.setSelectionForeground(Color.BLACK);

    JTableHeader header = tabla.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    header.setBackground(new Color(220, 224, 228));
    header.setForeground(Color.BLACK);
    header.setOpaque(true);
}
}
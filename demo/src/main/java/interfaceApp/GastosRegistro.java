package interfaceApp;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GastosRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombreApartamento;
    private JTextField txtIVA;
    private JTextField txtTotalConIVA;
    private JTextField txtTotalGasto;
    private JTextField txtNIFProveedor;
    private JTextField txtNombreProveedor;
    private JTextField txtGastoConcepto;
    private JCheckBox chckbxPagoCompletado;
    private funcionesDB dbFun;
    private JComboBox<String> ubicacionComboBox; // Agregar la caja desplegable

    public GastosRegistro() {
    	
        dbFun = new funcionesDB();

        setTitle("Registro de Gastos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 482, 421);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblUbicacion = new JLabel("Ubicación");
        lblUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
        lblUbicacion.setBounds(5, 19, 211, 33);
        contentPane.add(lblUbicacion);
        
        ubicacionComboBox = new DesplegableApartamentoGasto(); // Crear la caja desplegable
        ubicacionComboBox.setBounds(220, 20, 207, 33);
        contentPane.add(ubicacionComboBox);

        JLabel lblIVA = new JLabel("IVA");
        lblIVA.setHorizontalAlignment(SwingConstants.CENTER);
        lblIVA.setBounds(5, 62, 207, 33);
        contentPane.add(lblIVA);

        txtIVA = new JTextField();
        txtIVA.setToolTipText("sólo el número de %");
        txtIVA.setBounds(220, 63, 207, 33);
        contentPane.add(txtIVA);
        txtIVA.setColumns(10);

        JLabel lblTotalConIVA = new JLabel("Total con IVA");
        lblTotalConIVA.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalConIVA.setBounds(5, 105, 207, 33);
        contentPane.add(lblTotalConIVA);

        txtTotalConIVA = new JTextField();
        txtTotalConIVA.setBounds(220, 106, 207, 33);
        contentPane.add(txtTotalConIVA);
        txtTotalConIVA.setColumns(10);

        JLabel lblTotalGasto = new JLabel("Total Gasto");
        lblTotalGasto.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalGasto.setBounds(5, 148, 207, 33);
        contentPane.add(lblTotalGasto);

        txtTotalGasto = new JTextField();
        txtTotalGasto.setBounds(220, 149, 207, 33);
        contentPane.add(txtTotalGasto);
        txtTotalGasto.setColumns(10);

        JLabel lblNIFProveedor = new JLabel("NIF Proveedor");
        lblNIFProveedor.setHorizontalAlignment(SwingConstants.CENTER);
        lblNIFProveedor.setBounds(5, 191, 207, 33);
        contentPane.add(lblNIFProveedor);

        txtNIFProveedor = new JTextField();
        txtNIFProveedor.setBounds(220, 192, 207, 33);
        contentPane.add(txtNIFProveedor);
        txtNIFProveedor.setColumns(10);

        JLabel lblNombreProveedor = new JLabel("Nombre Proveedor");
        lblNombreProveedor.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreProveedor.setBounds(5, 234, 207, 33);
        contentPane.add(lblNombreProveedor);

        txtNombreProveedor = new JTextField();
        txtNombreProveedor.setBounds(220, 235, 207, 33);
        contentPane.add(txtNombreProveedor);
        txtNombreProveedor.setColumns(10);

        JLabel lblGastoConcepto = new JLabel("Gasto Concepto");
        lblGastoConcepto.setHorizontalAlignment(SwingConstants.CENTER);
        lblGastoConcepto.setBounds(5, 277, 207, 33);
        contentPane.add(lblGastoConcepto);

        txtGastoConcepto = new JTextField();
        txtGastoConcepto.setBounds(220, 278, 207, 33);
        contentPane.add(txtGastoConcepto);
        txtGastoConcepto.setColumns(10);

        chckbxPagoCompletado = new JCheckBox("Pago Completado");
        chckbxPagoCompletado.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxPagoCompletado.setBounds(220, 320, 207, 23);
        contentPane.add(chckbxPagoCompletado);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(220, 349, 207, 22);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ubicacion = (String) ubicacionComboBox.getSelectedItem(); // Obtener la ubicación seleccionada
                String ivaStr = txtIVA.getText();
                String totalConIVAStr = txtTotalConIVA.getText();
                String totalGastoStr = txtTotalGasto.getText();
                String nifProveedor = txtNIFProveedor.getText();
                String nombreProveedor = txtNombreProveedor.getText();
                String gastoConcepto = txtGastoConcepto.getText();
                boolean pagoCompletado = chckbxPagoCompletado.isSelected();
                
                try {
                    double iva = Double.parseDouble(ivaStr);
                    double totalConIVA = Double.parseDouble(totalConIVAStr);
                    double totalGasto = Double.parseDouble(totalGastoStr);
                    
                    if((	!ubicacion.isEmpty() && !ivaStr.isEmpty() &&
                    		!totalConIVAStr.isEmpty() && !totalGastoStr.isEmpty() &&
                    		!nifProveedor.isEmpty() && !nombreProveedor.isEmpty() &&
                    		!gastoConcepto.isEmpty()))
                    {
                        dbFun.insertarGasto(ubicacion, iva, totalConIVA, totalGasto, nifProveedor, nombreProveedor, gastoConcepto, pagoCompletado);
                        dispose(); 
                        ListarGastosPanel.listarGastos();
                    }
                    else	JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error en ingreso de datos.", JOptionPane.ERROR_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(btnRegistrar);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GastosRegistro frame = new GastosRegistro();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


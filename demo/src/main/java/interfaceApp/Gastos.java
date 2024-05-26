package interfaceApp;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gastos extends JFrame {

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

    public Gastos() {
    	
        dbFun = new funcionesDB();

        setTitle("Registro de Gastos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setBounds(5, 49, 207, 33);
        contentPane.add(lblUbicacion);
        
        ubicacionComboBox = new DesplegableApartamentoGasto(); // Crear la caja desplegable
        ubicacionComboBox.setBounds(222, 49, 207, 33);
        contentPane.add(ubicacionComboBox);

        JLabel lblIVA = new JLabel("IVA:");
        lblIVA.setBounds(5, 92, 207, 33);
        contentPane.add(lblIVA);

        txtIVA = new JTextField();
        txtIVA.setBounds(222, 92, 207, 33);
        contentPane.add(txtIVA);
        txtIVA.setColumns(10);

        JLabel lblTotalConIVA = new JLabel("Total con IVA:");
        lblTotalConIVA.setBounds(5, 135, 207, 33);
        contentPane.add(lblTotalConIVA);

        txtTotalConIVA = new JTextField();
        txtTotalConIVA.setBounds(222, 135, 207, 33);
        contentPane.add(txtTotalConIVA);
        txtTotalConIVA.setColumns(10);

        JLabel lblTotalGasto = new JLabel("Total Gasto:");
        lblTotalGasto.setBounds(5, 178, 207, 33);
        contentPane.add(lblTotalGasto);

        txtTotalGasto = new JTextField();
        txtTotalGasto.setBounds(222, 178, 207, 33);
        contentPane.add(txtTotalGasto);
        txtTotalGasto.setColumns(10);

        JLabel lblNIFProveedor = new JLabel("NIF Proveedor:");
        lblNIFProveedor.setBounds(5, 221, 207, 33);
        contentPane.add(lblNIFProveedor);

        txtNIFProveedor = new JTextField();
        txtNIFProveedor.setBounds(222, 221, 207, 33);
        contentPane.add(txtNIFProveedor);
        txtNIFProveedor.setColumns(10);

        JLabel lblNombreProveedor = new JLabel("Nombre Proveedor:");
        lblNombreProveedor.setBounds(5, 264, 207, 33);
        contentPane.add(lblNombreProveedor);

        txtNombreProveedor = new JTextField();
        txtNombreProveedor.setBounds(222, 264, 207, 33);
        contentPane.add(txtNombreProveedor);
        txtNombreProveedor.setColumns(10);

        JLabel lblGastoConcepto = new JLabel("Gasto Concepto:");
        lblGastoConcepto.setBounds(5, 307, 207, 33);
        contentPane.add(lblGastoConcepto);

        txtGastoConcepto = new JTextField();
        txtGastoConcepto.setBounds(222, 307, 207, 33);
        contentPane.add(txtGastoConcepto);
        txtGastoConcepto.setColumns(10);

        chckbxPagoCompletado = new JCheckBox("Pago Completado");
        chckbxPagoCompletado.setBounds(222, 350, 207, 23);
        contentPane.add(chckbxPagoCompletado);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(222, 378, 207, 22);
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
                    Gastos frame = new Gastos();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


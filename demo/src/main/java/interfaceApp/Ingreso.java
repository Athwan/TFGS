package interfaceApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Ingreso extends JFrame {

    private JPanel contentPane;
    private JComboBox<String> cmbIntermediario;
    private JComboBox<String> tarifaComboBox;
    private JComboBox<String> personaDNIComboBox;
    private JComboBox<String> ubicacionComboBox;
    private JTextField txtNumCoches;
    private JTextField txtNumPersonas;
    private JTextField txtDescuento;
    private JTextField txtTotalIva;
    private JTextField txtTotalFactura;
    private JTextField txtObservaciones;


    private funcionesDB dbFun;

    public Ingreso() {
        dbFun = new funcionesDB();

        setTitle("Registro de Ingresos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPersonaDNI = new JLabel("Persona DNI:");
        lblPersonaDNI.setBounds(10, 10, 100, 20);
        contentPane.add(lblPersonaDNI);
        
        personaDNIComboBox = new DesplegablePersona();
        personaDNIComboBox.setBounds(120, 10, 150, 20);
        contentPane.add(personaDNIComboBox);

        JLabel lblIntermediario = new JLabel("Intermediario:");
        lblIntermediario.setBounds(10, 40, 100, 20);
        contentPane.add(lblIntermediario);

        cmbIntermediario = new DesplegableIntermediario();
        cmbIntermediario.setBounds(120, 40, 150, 20);
        contentPane.add(cmbIntermediario);

        JLabel lblUbicacion = new JLabel("Apartamento:");
        lblUbicacion.setBounds(10, 66, 207, 33);
        contentPane.add(lblUbicacion);
        
        ubicacionComboBox = new DesplegableApartamentoGasto(); // Crear la caja desplegable
        ubicacionComboBox.setBounds(120, 71, 150, 20);
        contentPane.add(ubicacionComboBox);

        JLabel lblTarifa = new JLabel("Tarifa:");
        lblTarifa.setBounds(10, 100, 100, 20);
        contentPane.add(lblTarifa);

        tarifaComboBox = new DesplegableTarifa();
        tarifaComboBox.setBounds(120, 100, 150, 20);
        contentPane.add(tarifaComboBox);


        JLabel lblNumCoches = new JLabel("Número de Coches:");
        lblNumCoches.setBounds(10, 130, 120, 20);
        contentPane.add(lblNumCoches);

        txtNumCoches = new JTextField();
        txtNumCoches.setBounds(140, 130, 50, 20);
        contentPane.add(txtNumCoches);

        JLabel lblNumPersonas = new JLabel("Número de Personas:");
        lblNumPersonas.setBounds(10, 160, 120, 20);
        contentPane.add(lblNumPersonas);

        txtNumPersonas = new JTextField();
        txtNumPersonas.setBounds(140, 160, 50, 20);
        contentPane.add(txtNumPersonas);

        JLabel lblDescuento = new JLabel("Descuento:");
        lblDescuento.setBounds(10, 190, 100, 20);
        contentPane.add(lblDescuento);

        txtDescuento = new JTextField();
        txtDescuento.setBounds(120, 190, 150, 20);
        contentPane.add(txtDescuento);

        JLabel lblTotalIva = new JLabel("Total IVA:");
        lblTotalIva.setBounds(10, 220, 100, 20);
        contentPane.add(lblTotalIva);

        txtTotalIva = new JTextField();
        txtTotalIva.setBounds(120, 220, 150, 20);
        contentPane.add(txtTotalIva);

        JLabel lblTotalFactura = new JLabel("Total Factura:");
        lblTotalFactura.setBounds(10, 250, 100, 20);
        contentPane.add(lblTotalFactura);

        txtTotalFactura = new JTextField();
        txtTotalFactura.setBounds(120, 250, 150, 20);
        contentPane.add(txtTotalFactura);

        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setBounds(10, 280, 100, 20);
        contentPane.add(lblObservaciones);

        txtObservaciones = new JTextField();
        txtObservaciones.setBounds(120, 280, 150, 20);
        contentPane.add(txtObservaciones);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(10, 310, 100, 30);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarIngreso();
            }
        });
        contentPane.add(btnRegistrar);
    }

    private void registrarIngreso() {
        // Obtener los valores de los campos seleccionados y de texto
        String personaDNI = (String) personaDNIComboBox.getSelectedItem();
        String intermediarioNombre = (String) cmbIntermediario.getSelectedItem();
        String apartamentoUbicacion = (String) ubicacionComboBox.getSelectedItem();
        String tarifaTipo = (String) tarifaComboBox.getSelectedItem();
        int numCoches, numPersonas;
        double descuento, totalIva, totalFactura;
        String observaciones;

        // Validar que los campos de texto no estén vacíos
        try {
            numCoches = Integer.parseInt(txtNumCoches.getText());
            numPersonas = Integer.parseInt(txtNumPersonas.getText());
            descuento = Double.parseDouble(txtDescuento.getText());
            totalIva = Double.parseDouble(txtTotalIva.getText());
            totalFactura = Double.parseDouble(txtTotalFactura.getText());
            observaciones = txtObservaciones.getText();

            // Verificar si algún campo está vacío
            if (personaDNI == null || intermediarioNombre == null || apartamentoUbicacion == null || tarifaTipo == null ||
                    observaciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Te falta algún dato por completar.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Si todos los campos están completos, registrar el ingreso
                dbFun.insertarIngreso(personaDNI, intermediarioNombre, apartamentoUbicacion, tarifaTipo,
                        numCoches, numPersonas, descuento, totalIva, totalFactura, observaciones);
                ListarIngresosPanel.listarIngresos();
                dispose(); 
            }
        } catch (NumberFormatException e) {
            // Manejar el caso en que los campos de texto no sean números válidos
            JOptionPane.showMessageDialog(this, "Revisa que esté toda la información correcta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Ingreso frame = new Ingreso();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

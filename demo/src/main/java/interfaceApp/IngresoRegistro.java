package interfaceApp;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class IngresoRegistro extends JFrame {

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
    private JDatePickerImpl fechaEntradaPicker;
    private JDatePickerImpl fechaSalidaPicker;


    private funcionesDB dbFun;

    public IngresoRegistro() {
        dbFun = new funcionesDB();
        UtilDateModel fechaEntradaModel = new UtilDateModel();
        UtilDateModel fechaSalidaModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl fechaEntradaPanel = new JDatePanelImpl(fechaEntradaModel, properties);
        fechaEntradaPicker = new JDatePickerImpl(fechaEntradaPanel, new DateLabelFormatter());

        JDatePanelImpl fechaSalidaPanel = new JDatePanelImpl(fechaSalidaModel, properties);
        fechaSalidaPicker = new JDatePickerImpl(fechaSalidaPanel, new DateLabelFormatter());


        setTitle("Registro de Ingresos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 319, 484);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPersonaDNI = new JLabel("Persona DNI");
        lblPersonaDNI.setHorizontalAlignment(SwingConstants.CENTER);
        lblPersonaDNI.setBounds(10, 10, 104, 20);
        contentPane.add(lblPersonaDNI);
        
        personaDNIComboBox = new DesplegablePersona();
        personaDNIComboBox.setBounds(120, 10, 150, 20);
        contentPane.add(personaDNIComboBox);

        JLabel lblIntermediario = new JLabel("Intermediario");
        lblIntermediario.setHorizontalAlignment(SwingConstants.CENTER);
        lblIntermediario.setBounds(10, 40, 104, 20);
        contentPane.add(lblIntermediario);

        cmbIntermediario = new DesplegableIntermediario();
        cmbIntermediario.setBounds(120, 40, 150, 20);
        contentPane.add(cmbIntermediario);

        JLabel lblUbicacion = new JLabel("Apartamento");
        lblUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
        lblUbicacion.setBounds(10, 66, 104, 33);
        contentPane.add(lblUbicacion);
        
        ubicacionComboBox = new DesplegableApartamentoGasto(); // Crear la caja desplegable
        ubicacionComboBox.setBounds(120, 71, 150, 20);
        contentPane.add(ubicacionComboBox);

        JLabel lblTarifa = new JLabel("Tarifa");
        lblTarifa.setHorizontalAlignment(SwingConstants.CENTER);
        lblTarifa.setBounds(10, 100, 104, 20);
        contentPane.add(lblTarifa);

        tarifaComboBox = new DesplegableTarifa();
        tarifaComboBox.setBounds(120, 100, 150, 20);
        contentPane.add(tarifaComboBox);


        JLabel lblNumCoches = new JLabel("Número de Coches");
        lblNumCoches.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumCoches.setBounds(0, 131, 138, 20);
        contentPane.add(lblNumCoches);

        txtNumCoches = new JTextField();
        txtNumCoches.setBounds(179, 130, 50, 20);
        contentPane.add(txtNumCoches);

        JLabel lblNumPersonas = new JLabel("Número de Personas");
        lblNumPersonas.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumPersonas.setBounds(0, 161, 142, 20);
        contentPane.add(lblNumPersonas);

        txtNumPersonas = new JTextField();
        txtNumPersonas.setBounds(179, 161, 50, 20);
        contentPane.add(txtNumPersonas);

        JLabel lblDescuento = new JLabel("Descuento");
        lblDescuento.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescuento.setBounds(10, 190, 104, 20);
        contentPane.add(lblDescuento);

        txtDescuento = new JTextField();
        txtDescuento.setToolTipText("número de % de descuento");
        txtDescuento.setBounds(120, 190, 150, 20);
        contentPane.add(txtDescuento);

        JLabel lblTotalIva = new JLabel("Total IVA");
        lblTotalIva.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalIva.setBounds(10, 220, 104, 20);
        contentPane.add(lblTotalIva);

        txtTotalIva = new JTextField();
        txtTotalIva.setBounds(120, 220, 150, 20);
        contentPane.add(txtTotalIva);

        JLabel lblTotalFactura = new JLabel("Total Factura");
        lblTotalFactura.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFactura.setBounds(10, 250, 104, 20);
        contentPane.add(lblTotalFactura);

        txtTotalFactura = new JTextField();
        txtTotalFactura.setBounds(120, 250, 150, 20);
        contentPane.add(txtTotalFactura);

        JLabel lblObservaciones = new JLabel("Observaciones");
        lblObservaciones.setHorizontalAlignment(SwingConstants.CENTER);
        lblObservaciones.setBounds(10, 280, 104, 20);
        contentPane.add(lblObservaciones);

        txtObservaciones = new JTextField();
        txtObservaciones.setBounds(120, 280, 150, 20);
        contentPane.add(txtObservaciones);
        
        JLabel lblFechaEntrada = new JLabel("Fecha de Entrada");
        lblFechaEntrada.setHorizontalAlignment(SwingConstants.CENTER);
        lblFechaEntrada.setBounds(10, 310, 104, 20);
        contentPane.add(lblFechaEntrada);
        fechaEntradaPicker.setBounds(120, 310, 150, 20);
        contentPane.add(fechaEntradaPicker);

        JLabel lblFechaSalida = new JLabel("Fecha de Salida");
        lblFechaSalida.setHorizontalAlignment(SwingConstants.CENTER);
        lblFechaSalida.setBounds(10, 340, 104, 20);
        contentPane.add(lblFechaSalida);
        fechaSalidaPicker.setBounds(120, 340, 150, 20);
        contentPane.add(fechaSalidaPicker);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(170, 393, 100, 30);
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
            Date fechaEntrada = (Date) fechaEntradaPicker.getModel().getValue();
            Date fechaSalida = (Date) fechaSalidaPicker.getModel().getValue();


            // Verificar si algún campo está vacío
            if (personaDNI == null || intermediarioNombre == null || apartamentoUbicacion == null || tarifaTipo == null ||
                    observaciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Te falta algún dato por completar.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	
                // Si todos los campos están completos, registrar el ingreso
                dbFun.insertarIngreso(personaDNI, intermediarioNombre, apartamentoUbicacion, tarifaTipo,
                        numCoches, numPersonas, descuento, totalIva, totalFactura, observaciones, fechaEntrada, fechaSalida);
                dispose(); 
                ListarIngresosPanel.listarIngresos();
            }
        } catch (NumberFormatException e) {
            // Manejar el caso en que los campos de texto no sean números válidos
            JOptionPane.showMessageDialog(this, "Revisa que esté toda la información correcta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof Date) {
                    Date date = (Date) value;
                    return dateFormatter.format(date);
                } else if (value instanceof Calendar) {
                    Calendar calendar = (Calendar) value;
                    return dateFormatter.format(calendar.getTime());
                }
            }
            return "";
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	IngresoRegistro frame = new IngresoRegistro();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

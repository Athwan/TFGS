package interfaceApp;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class CalculoIVATrimestralPanel extends JPanel {
    private EntityManagerFactory emf;
    private EntityManager em;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private JTextField totalIVAIngresosField;
    private JTextField totalIVAGastosField;
    private JTextField netoIVAField;

    public CalculoIVATrimestralPanel() {
        setLayout(new BorderLayout());

        emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        em = emf.createEntityManager();

        // Configurar los modelos de fecha
        UtilDateModel startDateModel = new UtilDateModel();
        UtilDateModel endDateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, p);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, p);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel lblFechaInicio = new JLabel("Fecha Inicio");
        lblFechaInicio.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(lblFechaInicio);
        inputPanel.add(startDatePicker);
        JLabel label_2 = new JLabel("Fecha Fin");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(label_2);
        inputPanel.add(endDatePicker);

        JButton calculateButton = new JButton("Calcular IVA");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularIVA();
            }
        });

        JPanel resultPanel = new JPanel(new GridLayout(3, 2));
        JLabel label = new JLabel("Total IVA Ingresos");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        resultPanel.add(label);
        totalIVAIngresosField = new JTextField();
        totalIVAIngresosField.setEditable(false);
        resultPanel.add(totalIVAIngresosField);
        JLabel lblTotalIvaGastos = new JLabel("Total IVA Gastos");
        lblTotalIvaGastos.setHorizontalAlignment(SwingConstants.CENTER);
        resultPanel.add(lblTotalIvaGastos);
        totalIVAGastosField = new JTextField();
        totalIVAGastosField.setEditable(false);
        resultPanel.add(totalIVAGastosField);
        JLabel label_1 = new JLabel("Neto IVA");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        resultPanel.add(label_1);
        netoIVAField = new JTextField();
        netoIVAField.setEditable(false);
        resultPanel.add(netoIVAField);

        JPanel buttonPanel = new JPanel();

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        buttonPanel.add(calculateButton);

        // Botón para volver
        JButton backButton = new JButton("Volver");
        buttonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la ventana actual y cerrarla
                showMainFrame();
            }
        });
        add(resultPanel, BorderLayout.SOUTH);
    }

    private void showMainFrame() {
        SwingUtilities.getWindowAncestor(this).dispose();
        JFrame mainFrame = new VentanaPrincipal("Usuario"); // Crear una nueva instancia de la ventana principal
        mainFrame.setVisible(true); // Mostrar la ventana principal
    }

    private void calcularIVA() {
        Date startDate = (Date) startDatePicker.getModel().getValue();
        Date endDate = (Date) endDatePicker.getModel().getValue();

        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            TypedQuery<Double> ingresosQuery = em.createQuery(
                "SELECT SUM(i.totalIva) FROM Ingresos i WHERE i.fechaEntrada BETWEEN :startDate AND :endDate", Double.class);
            ingresosQuery.setParameter("startDate", startDate);
            ingresosQuery.setParameter("endDate", endDate);
            Double totalIVAIngresos = ingresosQuery.getSingleResult();

            TypedQuery<Double> gastosQuery = em.createQuery(
                "SELECT SUM(g.iva) FROM Gastos g WHERE g.fecha BETWEEN :startDate AND :endDate", Double.class);
            gastosQuery.setParameter("startDate", startDate);
            gastosQuery.setParameter("endDate", endDate);
            Double totalIVAGastos = gastosQuery.getSingleResult();

            totalIVAIngresosField.setText(totalIVAIngresos != null ? totalIVAIngresos.toString() : "0.0");
            totalIVAGastosField.setText(totalIVAGastos != null ? totalIVAGastos.toString() : "0.0");
            netoIVAField.setText(Double.toString((totalIVAIngresos != null ? totalIVAIngresos : 0.0) - (totalIVAGastos != null ? totalIVAGastos : 0.0)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al calcular el IVA.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Formateador para el JDatePicker
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                if (value instanceof GregorianCalendar) {
                    GregorianCalendar calendar = (GregorianCalendar) value;
                    Date date = calendar.getTime();
                    return dateFormatter.format(date);
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    return dateFormatter.format(date);
                }
            }
            return "";
        }
    }
}

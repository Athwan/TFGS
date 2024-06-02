package interfaceApp;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;

public class ListarIngresosPanel extends JPanel {
    private EntityManagerFactory emf;
    private static EntityManager em;
    private JTable table;
    private static DefaultTableModel tableModel;
    private JTextField totalIVAField;
    private JTextField totalFacturaField;
    private JTextField searchField = new JTextField();


    public ListarIngresosPanel() {
        setLayout(new BorderLayout());

        // Crear EntityManager
        emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        em = emf.createEntityManager();

        // Configurar el modelo de la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Ingresos");
        tableModel.addColumn("Persona DNI");
        tableModel.addColumn("Intermediario");
        tableModel.addColumn("Apartamento");
        tableModel.addColumn("Tarifa");
        tableModel.addColumn("Fecha Entrada");
        tableModel.addColumn("Fecha Salida");
        tableModel.addColumn("Num Coches");
        tableModel.addColumn("Num Personas");
        tableModel.addColumn("Descuento");
        tableModel.addColumn("Total IVA");
        tableModel.addColumn("Total Factura");
        tableModel.addColumn("Observaciones");

        // Configurar la tabla
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        table.setDefaultEditor(Object.class, null);

     // Panel superior con el campo de búsqueda
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarIngreso();
            }
        });

        JLabel label = new JLabel("Buscar por ID de Ingreso:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH); // Agregar el panel de búsqueda al panel principal
        
        listarIngresos();
        
        // Agregar un panel para las sumas
        JPanel sumPanel = new JPanel();

        // Etiqueta y campo de texto para la suma del IVA
        JLabel totalIVALabel = new JLabel("Total IVA:");
        totalIVALabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalIVAField = new JTextField();
        totalIVAField.setHorizontalAlignment(SwingConstants.CENTER);
        totalIVAField.setEditable(false);

        // Etiqueta y campo de texto para la suma del Total de Factura
        JLabel totalFacturaLabel = new JLabel("Total Factura:");
        totalFacturaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalFacturaField = new JTextField();
        totalFacturaField.setHorizontalAlignment(SwingConstants.CENTER);
        totalFacturaField.setEditable(false);

        // Agregar el panel de sumas al sur
        add(sumPanel, BorderLayout.SOUTH);
        sumPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton addIncomeButton = new JButton("Añadir Ingreso");
        sumPanel.add(addIncomeButton);
        addIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaAñadirIngreso();
            }
        });
        
        // Botón para volver
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la ventana actual y cerrarla
                showMainFrame();
            }
        });
        sumPanel.add(backButton);
        sumPanel.add(totalIVALabel);
        sumPanel.add(totalIVAField);
        sumPanel.add(totalFacturaLabel);
        sumPanel.add(totalFacturaField);

        // Calcular y mostrar las sumas
        calcularSumas();
    }
    
    

    // Método para calcular y mostrar las sumas de los ingresos
    private void calcularSumas() {
        double sumaTotalIVA = 0.0;
        double sumaTotalFactura = 0.0;

        // Calcular la suma del IVA y del Total de Factura recorriendo los datos de la tabla
        for (int i = 0; i < table.getRowCount(); i++) {
            sumaTotalIVA += Double.parseDouble(table.getValueAt(i, 10).toString());
            sumaTotalFactura += Double.parseDouble(table.getValueAt(i, 11).toString());
        }

        // Mostrar las sumas en los campos correspondientes
        totalIVAField.setText(String.valueOf(sumaTotalIVA));
        totalFacturaField.setText(String.valueOf(sumaTotalFactura));
    }

    // Método para abrir la ventana para añadir un nuevo ingreso
    private void abrirVentanaAñadirIngreso() {
        IngresoRegistro ingreso = new IngresoRegistro();
        ingreso.setVisible(true);
    }
    
    private void showMainFrame() {
        SwingUtilities.getWindowAncestor(this).dispose();
        JFrame mainFrame = new VentanaPrincipal("Usuario"); // Crear una nueva instancia de la ventana principal
        mainFrame.setVisible(true); // Mostrar la ventana principal
    }
    
    private void buscarIngresoPorID(int idIngreso) {
        // Limpiar la tabla
        tableModel.setRowCount(0);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        // Consulta para buscar el ingreso por su ID
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT i.idIngresos, i.persona.DNI, i.intermediario.nombre, i.apartamento.ubicacion, i.tarifa.tipo, i.fechaEntrada, i.fechaSalida, i.numCoches, i.numPersonas, i.descuento, i.totalIva, i.totalFactura, i.observaciones " +
                        "FROM Ingresos i " +
                        "WHERE i.idIngresos = :id", 
                Object[].class
            );
        query.setParameter("id", idIngreso);
        List<Object[]> results = query.getResultList();

        for (Object[] row : results) {
            tableModel.addRow(row);
        }

        em.close();
        emf.close();
    }

    

	private void buscarIngreso() {
		
        String searchText = searchField.getText();
        if (!searchText.isEmpty()) {
            try {
                int idIngreso = Integer.parseInt(searchText);
                buscarIngresoPorID(idIngreso);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ListarIngresosPanel.this, "Por favor, introduce un ID de ingreso válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Si el campo de búsqueda está vacío, listar todos los ingresos
            listarIngresos();
        }				
	}

    // Método para listar todos los ingresos en la tabla
    public static void listarIngresos() {
        tableModel.setRowCount(0); // Limpiar la tabla

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Object[]> query = em.createQuery(
                "SELECT i.idIngresos, i.persona.DNI, i.intermediario.nombre, i.apartamento.ubicacion, i.tarifa.tipo, i.fechaEntrada, i.fechaSalida, i.numCoches, i.numPersonas, i.descuento, i.totalIva, i.totalFactura, i.observaciones " +
                        "FROM Ingresos i", 
                Object[].class
            );
        List<Object[]> results = query.getResultList();

        for (Object[] row : results) {
            tableModel.addRow(row);
        }

        em.close();
        emf.close();
    }
}

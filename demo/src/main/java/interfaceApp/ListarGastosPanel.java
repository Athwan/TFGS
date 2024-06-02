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

public class ListarGastosPanel extends JPanel {
    private EntityManagerFactory emf;
    private static EntityManager em;
    private JTable table;
    private static DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField totalIVAField;
    private JTextField totalGastoField;


    public ListarGastosPanel() {
        setLayout(new BorderLayout());

        // Crear EntityManager
        emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        em = emf.createEntityManager();

        // Configurar el modelo de la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Gastos");
        tableModel.addColumn("Apartamento ID");
        tableModel.addColumn("IVA");
        tableModel.addColumn("Total con IVA");
        tableModel.addColumn("Total Gasto");
        tableModel.addColumn("NIF Proveedor");
        tableModel.addColumn("Nombre Proveedor");
        tableModel.addColumn("Concepto Gasto");
        tableModel.addColumn("Pago Completado");
        tableModel.addColumn("Fecha");

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
                buscarGasto();
            }
        });

        JLabel label = new JLabel("Buscar por ID de Gasto:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Cargar todos los gastos inicialmente
        listarGastos();
        // Agregar un panel para las sumas
        JPanel sumPanel = new JPanel();

        // Etiqueta y campo de texto para la suma del IVA
        JLabel totalIVALabel = new JLabel("Total IVA:");
        totalIVALabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalIVAField = new JTextField();
        totalIVAField.setHorizontalAlignment(SwingConstants.CENTER);
        totalIVAField.setEditable(false);

        // Etiqueta y campo de texto para la suma del Total de Gastos
        JLabel totalGastoLabel = new JLabel("Total Gasto:");
        totalGastoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalGastoField = new JTextField();
        totalGastoField.setHorizontalAlignment(SwingConstants.CENTER);
        totalGastoField.setEditable(false);

        // Agregar el panel de sumas al sur
        add(sumPanel, BorderLayout.SOUTH);
        sumPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JPanel buttonPanel = new JPanel();
        sumPanel.add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton addExpenseButton = new JButton("Añadir Gasto");
        buttonPanel.add(addExpenseButton);
        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaAñadirGasto();
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
        buttonPanel.add(backButton);
        sumPanel.add(totalIVALabel);
        sumPanel.add(totalIVAField);
        sumPanel.add(totalGastoLabel);
        sumPanel.add(totalGastoField);
        
        // Calcular y mostrar las sumas
        calcularSumas();
    }
    
    //Calcular la casilla
    private void calcularSumas() {
        double sumaTotalIVA = 0.0;
        double sumaTotalGasto = 0.0;

        // Calcular la suma del IVA y del Total de Gastos recorriendo los datos de la tabla
        for (int i = 0; i < table.getRowCount(); i++) {
            String pagoCompletado = table.getValueAt(i, 8).toString(); // Obtener el valor de la columna "Pago Completado"
            if (!pagoCompletado.equals("NO PAGADO")) { // Verificar si el pago está completado
                sumaTotalIVA += Double.parseDouble(table.getValueAt(i, 3).toString());
                sumaTotalGasto += Double.parseDouble(table.getValueAt(i, 4).toString());
            }
        }

        // Mostrar las sumas en los campos correspondientes
        totalIVAField.setText(String.valueOf(sumaTotalIVA));
        totalGastoField.setText(String.valueOf(sumaTotalGasto));
    }

    
    private void abrirVentanaAñadirGasto() {
        // Abrir la ventana para añadir un nuevo gasto
        GastosRegistro gastos = new GastosRegistro();
        gastos.setVisible(true);
    }
    
    
    private void showMainFrame() {
        SwingUtilities.getWindowAncestor(this).dispose();
        JFrame mainFrame = new VentanaPrincipal("Usuario"); // Crear una nueva instancia de la ventana principal
        mainFrame.setVisible(true); // Mostrar la ventana principal
    }
    
    /*
     * 
        // Botón para volver
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la ventana actual y cerrarla
                showMainFrame();
            }
        });
     */
    
    public static void listarGastos() {
        tableModel.setRowCount(0); // Limpiar la tabla

        TypedQuery<Object[]> query = em.createQuery(
                "SELECT g.idGastos, a.ID_Apartamento, g.iva, g.totalConIVA, g.totalGasto, g.nifProveedor, g.nombreProveedor, g.gastoConcepto, CASE WHEN g.pagoCompletado = true THEN 'PAGADO' ELSE 'NO PAGADO' END, g.fecha " +
                "FROM Gastos g JOIN g.apartamento a", 
                Object[].class
            );
        List<Object[]> results = query.getResultList();

        for (Object[] row : results) {
            tableModel.addRow(row);
        }
    }

    private void buscarGasto() {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            listarGastos(); // Si el campo de búsqueda está vacío, listar todos los gastos
            return;
        }

        try {
            int idGasto = Integer.parseInt(searchText);
            tableModel.setRowCount(0); // Limpiar la tabla

            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT g.idGastos, a.ID_Apartamento, g.iva, g.totalConIVA, g.totalGasto, g.nifProveedor, g.nombreProveedor, g.gastoConcepto, CASE WHEN g.pagoCompletado = true THEN 'PAGADO' ELSE 'NO PAGADO' END, g.fecha " +
                    "FROM Gastos g JOIN g.apartamento a " +
                    "WHERE g.idGastos = :idGasto", 
                    Object[].class
                );
            
            query.setParameter("idGasto", idGasto);
            List<Object[]> results = query.getResultList();

            for (Object[] row : results) {
                tableModel.addRow(row);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID de Gasto debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    public static void main(String[] args) {
        JFrame frame = new JFrame("Listar Gastos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().add(new ListarGastosPanel());
        frame.setVisible(true);
    }
}


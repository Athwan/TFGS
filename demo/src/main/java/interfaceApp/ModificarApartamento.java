package interfaceApp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import classes.*;

public class ModificarApartamento extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ModificarApartamento window = new ModificarApartamento();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ModificarApartamento() {
        initialize();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initialize() {
        setTitle("Modificar Apartamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        // Configurar el modelo de la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Ubicación");

        // Configurar la tabla
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botón para modificar apartamento
        JButton btnModificar = new JButton("Modificar Nombre");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarNombreApartamento();
            }
        });
        panel.add(btnModificar, BorderLayout.SOUTH);

        // Cargar la lista de apartamentos disponibles en la tabla
        cargarApartamentosDisponibles();
    }

    private void cargarApartamentosDisponibles() {
        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Consulta para obtener la lista de todos los apartamentos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Apartamento> query = em.createQuery(
            "SELECT a FROM Apartamento a", Apartamento.class);
        List<Apartamento> resultados = query.getResultList();

        // Llenar la tabla con los resultados de la consulta
        for (Apartamento apartamento : resultados) {
            tableModel.addRow(new Object[]{apartamento.getID_Apartamento(), apartamento.getUbicacion()});
        }

        em.close();
        emf.close();
    }

    private void modificarNombreApartamento() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idApartamento = (int) table.getValueAt(filaSeleccionada, 0);
            String nuevoNombre = JOptionPane.showInputDialog(this, "Introduce el nuevo nombre del apartamento:");
            if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                Apartamento apartamento = em.find(Apartamento.class, idApartamento);
                apartamento.setUbicacion(nuevoNombre);
                em.getTransaction().commit();
                em.close();
                emf.close();
                cargarApartamentosDisponibles(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un nombre válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un apartamento para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

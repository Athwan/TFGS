package interfaceApp;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import classes.*;


public class CancelarReserva extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CancelarReserva window = new CancelarReserva();
                    window.setVisible(true); // Hacer visible la ventana CancelarReserva
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CancelarReserva() {
        initialize();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initialize() {
        setTitle("Cancelar Reserva"); // Establecer el título de la ventana
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

        // Botón para eliminar apartamento
        JButton btnEliminar = new JButton("Eliminar Apartamento");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarApartamentoSeleccionado();
            }
        });
        panel.add(btnEliminar, BorderLayout.SOUTH);

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


    private void eliminarApartamentoSeleccionado() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idApartamento = (int) table.getValueAt(filaSeleccionada, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que quieres eliminar este apartamento?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Realizar operación para eliminar el apartamento de la base de datos
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                Apartamento apartamento = em.find(Apartamento.class, idApartamento);
                em.remove(apartamento);
                em.getTransaction().commit();
                em.close();
                emf.close();

                // Actualizar la tabla después de eliminar el apartamento
                cargarApartamentosDisponibles();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un apartamento para eliminar.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

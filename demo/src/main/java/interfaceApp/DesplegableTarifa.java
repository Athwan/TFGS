package interfaceApp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JComboBox;

public class DesplegableTarifa extends JComboBox<String> {

    public DesplegableTarifa() {
        super();
        // Crear una fábrica de EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        // Crear un EntityManager
        EntityManager em = emf.createEntityManager();

        try {
            // Iniciar la transacción
            em.getTransaction().begin();
            // Crear la consulta para obtener los tipos de tarifa
            TypedQuery<String> query = em.createQuery("SELECT t.tipo FROM Tarifa t", String.class);
            // Obtener el resultado de la consulta
            java.util.List<String> tipos = query.getResultList();
            // Agregar los tipos al desplegable
            for (String tipo : tipos) {
                addItem(tipo);
            }
            // Finalizar la transacción
            em.getTransaction().commit();
        } catch (Exception e) {
            // Manejar cualquier excepción
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar el EntityManager
            em.close();
            // Cerrar la fábrica de EntityManager
            emf.close();
        }
    }
}

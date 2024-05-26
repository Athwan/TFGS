package interfaceApp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JComboBox;

public class DesplegableApartamentoGasto extends JComboBox<String> {

    public DesplegableApartamentoGasto() {
        super();
        // Crear una fábrica de EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        // Crear un EntityManager
        EntityManager em = emf.createEntityManager();
        
        try {
            // Iniciar la transacción
            em.getTransaction().begin();
            // Crear la consulta para obtener las ubicaciones de los apartamentos
            TypedQuery<String> query = em.createQuery("SELECT a.ubicacion FROM Apartamento a", String.class);
            // Obtener el resultado de la consulta
            java.util.List<String> ubicaciones = query.getResultList();
            // Agregar las ubicaciones al desplegable
            for (String ubicacion : ubicaciones) {
                addItem(ubicacion);
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

package interfaceApp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JComboBox;

public class DesplegablePersona extends JComboBox<String> {

    public DesplegablePersona() {
        super();
        // Crear una fábrica de EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        // Crear un EntityManager
        EntityManager em = emf.createEntityManager();
        
        try {
            // Iniciar la transacción
            em.getTransaction().begin();
            // Crear la consulta para obtener los DNIs de las personas
            TypedQuery<String> query = em.createQuery("SELECT p.DNI FROM Persona p", String.class);
            // Obtener el resultado de la consulta
            java.util.List<String> DNIs = query.getResultList();
            // Agregar los DNIs al desplegable
            for (String DNI : DNIs) {
                addItem(DNI);
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

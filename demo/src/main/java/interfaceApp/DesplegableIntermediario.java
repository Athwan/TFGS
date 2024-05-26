package interfaceApp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JComboBox;

public class DesplegableIntermediario extends JComboBox<String> {

    public DesplegableIntermediario() {
        super();
        // Crear una fábrica de EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        // Crear un EntityManager
        EntityManager em = emf.createEntityManager();

        try {
            // Iniciar la transacción
            em.getTransaction().begin();
            // Crear la consulta para obtener los nombres de los intermediarios
            TypedQuery<String> query = em.createQuery("SELECT i.nombre FROM Intermediarios i", String.class);
            // Obtener el resultado de la consulta
            java.util.List<String> nombres = query.getResultList();
            // Agregar los nombres al desplegable
            for (String nombre : nombres) {
                addItem(nombre);
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

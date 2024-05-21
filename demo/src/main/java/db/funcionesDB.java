package db;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import classes.*;

public class funcionesDB {

	
	public void insertarApartamento(String ubicacion)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Crear un nuevo apartamento
        Apartamento apartamento = new Apartamento(ubicacion);
        em.persist(apartamento);

        // Obtener el apartamento de la base de datos
        Apartamento a = em.find(Apartamento.class, apartamento.getID_Apartamento());
        System.out.println("Apartamento número:"+ a.getID_Apartamento()+" ubicado en: "+a.getUbicacion());

        em.getTransaction().commit();
        em.close();
        emf.close();
	}//insertarApartamento
	
	
    public void insertarPersona(String dni, String nombre, String apellidos, int telefono, String password) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Crear una nueva persona
        Persona persona = new Persona(dni, nombre, apellidos, telefono, password);
        
        // Verificar si la persona ya existe
        Persona existingPersona = em.find(Persona.class, dni);
        if (existingPersona == null) {
            em.persist(persona);
            System.out.println("Persona insertada con DNI: " + persona.getDNI());
        } else {
            System.out.println("La persona con DNI " + dni + " ya existe.");
        }

        // Obtener la persona de la base de datos
        Persona p = em.find(Persona.class, persona.getDNI());
        System.out.println("Persona DNI:" + p.getDNI() + ", Nombre: " + p.getNombre() + ", Apellidos: " + p.getApellidos());

        em.getTransaction().commit();
        em.close();
        emf.close();
    }//insertarPersona
    
    
    public void insertarIntermediario(String nombre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Crear un nuevo intermediario
        Intermediarios intermediario = new Intermediarios();
        intermediario.setNombre(nombre);
        
        // Verificar si el intermediario ya existe
        Intermediarios existingIntermediario = em.createQuery("SELECT i FROM Intermediarios i WHERE i.nombre = :nombre", Intermediarios.class)
                                                 .setParameter("nombre", nombre)
                                                 .getResultStream()
                                                 .findFirst()
                                                 .orElse(null);
        if (existingIntermediario == null) {
            em.persist(intermediario);
            System.out.println("Intermediario insertado con ID: " + intermediario.getID_Intermediario() + " y nombre: " + intermediario.getNombre());
        } else {
            System.out.println("El intermediario con nombre " + nombre + " ya existe.");
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }//insertarIntermediario

    
    public void insertarTarifa(String tipo, Double precio_base) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Crear una nueva tarifa
        Tarifa tarifa = new Tarifa();
        tarifa.setTipo(tipo);
        tarifa.setPrecio_base(precio_base);
        
        // Verificar si la tarifa ya existe
        // Asumiendo que el tipo es único, puedes ajustar el criterio de búsqueda si es necesario.
        Tarifa existingTarifa = em.createQuery("SELECT t FROM Tarifa t WHERE t.tipo = :tipo", Tarifa.class)
                                  .setParameter("tipo", tipo)
                                  .getResultStream()
                                  .findFirst()
                                  .orElse(null);
        if (existingTarifa == null) {
            em.persist(tarifa);
            em.getTransaction().commit();
            System.out.println("Tarifa insertada con ID: " + tarifa.getID_Tarifa() + ", Tipo: " + tarifa.getTipo() + ", Precio base: " + tarifa.getPrecio_base());
        } else {
            System.out.println("La tarifa del tipo " + tipo + " ya existe.");
            em.getTransaction().rollback();
        }

        em.close();
        emf.close();
    }//insertarTarifa
    
    public void insertarGasto(String nombreApartamento, double iva, double totalConIVA, double totalGasto,
            String nifProveedor, String nombreProveedor, String gastoConcepto, boolean pagoCompletado) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// Buscar el apartamento por nombre
		TypedQuery<Apartamento> query = em.createQuery("SELECT a FROM Apartamento a WHERE a.ubicacion = :ubicacion", Apartamento.class);
		query.setParameter("ubicacion", nombreApartamento);
		List<Apartamento> apartamentos = query.getResultList();
		
		if (apartamentos.isEmpty()) {
		System.out.println("No se encontró el apartamento con la ubicación: " + nombreApartamento);
		em.getTransaction().rollback();
		em.close();
		emf.close();
		return;
		}
		
		// Usar el primer apartamento encontrado
		Apartamento apartamento = apartamentos.get(0);
		
		// Crear el objeto Gastos y asignarle la fecha actual
		Gastos gastos = new Gastos();
		gastos.setApartamento(apartamento);
		gastos.setIva(iva);
		gastos.setTotalConIVA(totalConIVA);
		gastos.setTotalGasto(totalGasto);
		gastos.setNifProveedor(nifProveedor);
		gastos.setNombreProveedor(nombreProveedor);
		gastos.setGastoConcepto(gastoConcepto);
		gastos.setPagoCompletado(pagoCompletado);
		gastos.setFecha(new Date()); // Asignar la fecha actual
		
		// Persistir el objeto Gastos
		em.persist(gastos);
		System.out.println("Gasto insertado con ID: " + gastos.getIdGastos());
		
		em.getTransaction().commit();
		em.close();
		emf.close();
    }//insertarGasto
    
    public void insertarIngreso(String personaDNI, String intermediarioNombre, String apartamentoUbicacion, String tarifaTipo, 
            int numCoches, int numPersonas, double descuento, double totalIva, double totalFactura, String observaciones) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// Buscar Persona por DNI
		Persona persona = em.find(Persona.class, personaDNI);
		if (persona == null) {
		System.out.println("Persona no encontrada");
		em.getTransaction().rollback();
		em.close();
		emf.close();
		return;
		}
		
		// Buscar Intermediario por nombre
		TypedQuery<Intermediarios> queryIntermediario = em.createQuery("SELECT i FROM Intermediarios i WHERE i.nombre = :nombre", Intermediarios.class);
		queryIntermediario.setParameter("nombre", intermediarioNombre);
		List<Intermediarios> intermediariosList = queryIntermediario.getResultList();
		Intermediarios intermediario = intermediariosList.isEmpty() ? null : intermediariosList.get(0);
		
		if (intermediario == null) {
		System.out.println("Intermediario no encontrado");
		em.getTransaction().rollback();
		em.close();
		emf.close();
		return;
		}
		
		// Buscar Apartamento por ubicación
		TypedQuery<Apartamento> queryApartamento = em.createQuery("SELECT a FROM Apartamento a WHERE a.ubicacion = :ubicacion", Apartamento.class);
		queryApartamento.setParameter("ubicacion", apartamentoUbicacion);
		List<Apartamento> apartamentoList = queryApartamento.getResultList();
		Apartamento apartamento = apartamentoList.isEmpty() ? null : apartamentoList.get(0);
		if (apartamento == null) {
		    System.out.println("Apartamento no encontrado");
		    em.getTransaction().rollback();
		    em.close();
		    emf.close();
		    return;
		}

		// Buscar Tarifa por tipo
		TypedQuery<Tarifa> queryTarifa = em.createQuery("SELECT t FROM Tarifa t WHERE t.tipo = :tipo", Tarifa.class);
		queryTarifa.setParameter("tipo", tarifaTipo);
		List<Tarifa> tarifaList = queryTarifa.getResultList();
		Tarifa tarifa = tarifaList.isEmpty() ? null : tarifaList.get(0);

		if (tarifa == null) {
		    System.out.println("Tarifa no encontrada");
		    em.getTransaction().rollback();
		    em.close();
		    emf.close();
		    return;
		}

		// Crear el objeto Ingresos y asignarle la fecha actual
		Ingresos ingresos = new Ingresos();
		ingresos.setPersona(persona);
		ingresos.setIntermediario(intermediario);
		ingresos.setApartamento(apartamento);
		ingresos.setTarifa(tarifa);
		ingresos.setNumCoches(numCoches);
		ingresos.setNumPersonas(numPersonas);
		ingresos.setDescuento(descuento);
		ingresos.setTotalIva(totalIva);
		ingresos.setTotalFactura(totalFactura);
		ingresos.setObservaciones(observaciones);
		ingresos.setFechaEntrada(new Date());
		ingresos.setFechaSalida(new Date());

		// Persistir el objeto Ingresos
		em.persist(ingresos);
		System.out.println("Ingreso insertado con ID: " + ingresos.getIdIngresos());

		em.getTransaction().commit();
		em.close();
		emf.close();
    }

    
    
    
    
    /*
    public void insertarIngreso(String dniPersona, String nombreIntermediario, String ubicacionApartamento,
            String tipoTarifa, int numCoches, int numPersonas, double descuento,
            double totalIva, double totalFactura, String observaciones) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// Buscar la persona por DNI
		TypedQuery<Persona> personaQuery = em.createQuery("SELECT p FROM Persona p WHERE p.DNI = :dni", Persona.class);
		personaQuery.setParameter("dni", dniPersona);
		Persona persona;
		
		try {
			persona = personaQuery.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No se encontró ninguna persona con el DNI: " + dniPersona);
			em.getTransaction().rollback();
			em.close();
			emf.close();
			return;
		}
		
		// Buscar el intermediario por nombre
		TypedQuery<Intermediarios> intermediarioQuery = em.createQuery("SELECT i FROM Intermediarios i WHERE i.nombre = :nombre", Intermediarios.class);
		intermediarioQuery.setParameter("nombre", nombreIntermediario);
		Intermediarios intermediario;
		try {
			intermediario = intermediarioQuery.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No se encontró ningún intermediario con el nombre: " + nombreIntermediario);
			em.getTransaction().rollback();
			em.close();
			emf.close();
			return;
		}
		
		// Buscar el apartamento por ubicación
		TypedQuery<Apartamento> apartamentoQuery = em.createQuery("SELECT a FROM Apartamento a WHERE a.ubicacion = :ubicacion", Apartamento.class);
		apartamentoQuery.setParameter("ubicacion", ubicacionApartamento);
		Apartamento apartamento;
		try {
			apartamento = apartamentoQuery.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No se encontró ningún apartamento con la ubicación: " + ubicacionApartamento);
			em.getTransaction().rollback();
			em.close();
			emf.close();
			return;
		}
		
		// Buscar la tarifa por tipo
		TypedQuery<Tarifa> tarifaQuery = em.createQuery("SELECT t FROM Tarifa t WHERE t.tipo = :tipo", Tarifa.class);
		tarifaQuery.setParameter("tipo", tipoTarifa);
		Tarifa tarifa;
		try {
			tarifa = tarifaQuery.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No se encontró ninguna tarifa con el tipo: " + tipoTarifa);
			em.getTransaction().rollback();
			em.close();
			emf.close();
		return;
		}
		
		// Crear el objeto Ingresos
		Ingresos ingreso = new Ingresos();
		ingreso.setPersona(persona);
		ingreso.setIntermediario(intermediario);
		ingreso.setApartamento(apartamento);
		ingreso.setTarifa(tarifa);
		ingreso.setFechaEntrada(new Date()); // Establecer la fecha de entrada como la fecha actual
		ingreso.setNumCoches(numCoches);
		ingreso.setNumPersonas(numPersonas);
		ingreso.setDescuento(descuento);
		ingreso.setTotalIva(totalIva);
		ingreso.setTotalFactura(totalFactura);
		ingreso.setObservaciones(observaciones);
		
		// Persistir el objeto Ingresos
		em.persist(ingreso);
		System.out.println("Ingreso insertado con ID: " + ingreso.getIdIngresos());
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		}*/
}//funciones DB

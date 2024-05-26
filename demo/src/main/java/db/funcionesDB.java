package db;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import classes.*;
import interfaceApp.Intermediario;

public class funcionesDB {

    private static SessionFactory sessionFactory;

    // función para buscar por DNI
    public boolean existePersonaPorDNI(String dni) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();
        
        boolean existe = false;
        
        try {
            em.getTransaction().begin();
            // Buscar Persona por DNI
            Persona persona = em.find(Persona.class, dni);
            if (persona != null) {
                existe = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        
        return existe;
    }//existePersonaPorDNI

    //	función para buscar intermediario
    
    public boolean existeIntermediario(String nombre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();
        
        boolean existe = false;
        
        try {
            em.getTransaction().begin();
            // Buscar Intermediario por nombre
            TypedQuery<Intermediario> query = em.createQuery("SELECT i FROM Intermediario i WHERE i.nombre = :nombre", Intermediario.class);
            query.setParameter("nombre", nombre);
            Intermediario intermediario = null;
            try {
                intermediario = query.getSingleResult();
            } catch (NoResultException e) {
                // No se encontró ningún intermediario con el nombre dado
            }

            if (intermediario != null) {
                existe = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        
        return existe;
    }//existeIntermediario
    
    
    public boolean existeTarifa(String tipo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();
        
        boolean existe = false;
        
        try {
            em.getTransaction().begin();
            // Buscar Tarifa por tipo
            TypedQuery<Tarifa> query = em.createQuery("SELECT t FROM Tarifa t WHERE t.tipo = :tipo", Tarifa.class);
            query.setParameter("tipo", tipo);
            Tarifa tarifa = null;
            try {
                tarifa = query.getSingleResult();
            } catch (NoResultException e) {
                // No se encontró ninguna tarifa con el tipo dado
            }

            if (tarifa != null) {
                existe = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        
        return existe;
    }
    
    
    // Función para encriptar una contraseña utilizando SHA-256
    public static String encryptPassword(String password) {
        try {
            // Crear una instancia de MessageDigest para el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Aplicar el algoritmo de hash a la contraseña
            byte[] hashedBytes = md.digest(password.getBytes());
            
            // Convertir los bytes hash a una representación hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }//
	
	
    public boolean esDNICorrecto(String dni) {
        // Comprueba si el DNI tiene 9 caracteres y el formato es correcto
        if (dni == null || dni.length() != 9) {
            return false;
        }
        
        // Extrae la parte numérica y la letra
        String numeroParte = dni.substring(0, 8);
        char letraParte = dni.charAt(dni.length() - 1);
        
        // Verifica si la parte numérica es realmente un número
        if (!numeroParte.matches("\\d+")) {
            return false;
        }
        
        // Cálculo de la letra correspondiente
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(numeroParte);
        char letraCalculada = letras.charAt(numero % 23);
        
        // Compara la letra calculada con la letra proporcionada
        return letraCalculada == letraParte;
    }
    
    
    public boolean existeApartamento(String ubicacion) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionalquileresPU");
        EntityManager em = emf.createEntityManager();
        
        boolean existe = false;
        
        try {
            em.getTransaction().begin();
            // Buscar Tarifa por ubicación
            TypedQuery<Apartamento> query = em.createQuery("SELECT a FROM Apartamento a WHERE a.ubicacion = :ubicacion", Apartamento.class);
            query.setParameter("ubicacion", ubicacion);
            Apartamento apartamento = null;
            try {
                apartamento = query.getSingleResult();
            } catch (NoResultException e) {
                // No se encontró ningún apartamento con la ubicación dada
            }

            if (apartamento != null) {
                existe = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        
        return existe;
    }
	
	
							// INSERTS A DB
	
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
    }//insertarIngreso
}//funciones DB

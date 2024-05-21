package classes;
import javax.persistence.*;

@Entity
@Table(name = "persona")

public class Persona {


	@Id
	private String DNI;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellidos", nullable = false)
	private String apellidos;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "telefono", nullable = false)
	private int numeroTelefono;
	
	//	Constructor
	public Persona() {};
	
	// GETTERS AND SETTERS
	
    public Persona(String dni, String nombre, String apellidos, int telefono, String password) {
        this.DNI = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = telefono;
        this.password = password;
    }

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(int numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	
	

}

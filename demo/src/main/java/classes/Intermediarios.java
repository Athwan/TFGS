package classes;

import javax.persistence.*;

@Entity
@Table(name = "intermediarios")
public class Intermediarios {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID_Intermediario;
	
	@Column(name="nombre_intermediario", nullable = false)
	private String nombre;
	
	//	Constructor
	public Intermediarios() {}
	
	
    // GETTERS AND SETTERS

	public int getID_Intermediario() {
		return ID_Intermediario;
	}

	public void setID_Intermediario(int iD_Intermediario) {
		ID_Intermediario = iD_Intermediario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

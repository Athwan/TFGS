package classes;

import javax.persistence.*;

@Entity
@Table(name = "tarifa")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID_Tarifa;

	@Column(name = "tipo", nullable = false)
    private String tipo;
    
    @Column(name = "precio_base", nullable = false)
    private Double precio_base;
    
    // Constructor vacío
    public Tarifa() {};
    
    // GETTERS AND SETTERS
    
    public int getID_Tarifa() {
 		return ID_Tarifa;
 	}

 	public void setID_Tarifa(int iD_Tarifa) {
 		ID_Tarifa = iD_Tarifa;
 	}

 	public String getTipo() {
 		return tipo;
 	}

 	public void setTipo(String tipo) {
 		this.tipo = tipo;
 	}

 	public Double getPrecio_base() {
 		return precio_base;
 	}

 	public void setPrecio_base(Double precio_base) {
 		this.precio_base = precio_base;
 	}

}

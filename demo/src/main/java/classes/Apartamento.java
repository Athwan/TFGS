package classes;

import javax.persistence.*;

@Entity
@Table(name = "apartamento")
public class Apartamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID_Apartamento;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    // Constructor (ubi)
    public Apartamento(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    // Constructor vacío
    public Apartamento(){}; 


    // GETTERS AND SETTERS
    
    public int getID_Apartamento() {
        return ID_Apartamento;
    }
    
    public void setID_Apartamento(int ID_Apartamento) {
        this.ID_Apartamento = ID_Apartamento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}

package classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ingresos")
public class Ingresos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Ingresos")
    private int idIngresos;

    @ManyToOne
    @JoinColumn(name = "Persona_DNI", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "intermediario", nullable = false)
    private Intermediarios intermediario;

    @ManyToOne
    @JoinColumn(name = "apartamento", nullable = false)
    private Apartamento apartamento;

    @ManyToOne
    @JoinColumn(name = "tarifa", nullable = false)
    private Tarifa tarifa;

    @Column(name = "fecha_entrada")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrada;

    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;

    @Column(name = "num_coches")
    private int numCoches;

    @Column(name = "num_personas")
    private int numPersonas;

    @Column(name = "descuento")
    private double descuento;

    @Column(name = "total_iva")
    private double totalIva;

    @Column(name = "total_factura")
    private double totalFactura;

    @Column(name = "observaciones")
    private String observaciones;
    
    
    // Getters y setters
    public int getIdIngresos() {
        return idIngresos;
    }

    public void setIdIngresos(int idIngresos) {
        this.idIngresos = idIngresos;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Intermediarios getIntermediario() {
        return intermediario;
    }

    public void setIntermediario(Intermediarios intermediario) {
        this.intermediario = intermediario;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getNumCoches() {
        return numCoches;
    }

    public void setNumCoches(int numCoches) {
        this.numCoches = numCoches;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(double totalIva) {
        this.totalIva = totalIva;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

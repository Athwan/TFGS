package classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gastos")
public class Gastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Gastos")
    private int idGastos;

    @ManyToOne
    @JoinColumn(name = "apartamentoID", nullable = false)
    private Apartamento apartamento;

    @Column(name = "iva")
    private double iva;

    @Column(name = "total_con_IVA")
    private double totalConIVA;

    @Column(name = "total_gasto")
    private double totalGasto;

    @Column(name = "NIF_proveedor")
    private String nifProveedor;

    @Column(name = "nombre_proveedor")
    private String nombreProveedor;

    @Column(name = "gasto_concepto")
    private String gastoConcepto;

    @Column(name = "pagoCompletado")
    private boolean pagoCompletado;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    // Constructor vacío
    public Gastos() {}

    public Gastos(double iva, double totalConIVA, double totalGasto, String nifProveedor,
            String nombreProveedor, String gastoConcepto, boolean pagoCompletado, Apartamento apartamento) {
	  this.iva = iva;
	  this.totalConIVA = totalConIVA;
	  this.totalGasto = totalGasto;
	  this.nifProveedor = nifProveedor;
	  this.nombreProveedor = nombreProveedor;
	  this.gastoConcepto = gastoConcepto;
	  this.pagoCompletado = pagoCompletado;
	  this.apartamento = apartamento;
    }

    // GETTERS AND SETTERS
    
    public int getIdGastos() {
        return idGastos;
    }

    public void setIdGastos(int idGastos) {
        this.idGastos = idGastos;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotalConIVA() {
        return totalConIVA;
    }

    public void setTotalConIVA(double totalConIVA) {
        this.totalConIVA = totalConIVA;
    }

    public double getTotalGasto() {
        return totalGasto;
    }

    public void setTotalGasto(double totalGasto) {
        this.totalGasto = totalGasto;
    }

    public String getNifProveedor() {
        return nifProveedor;
    }

    public void setNifProveedor(String nifProveedor) {
        this.nifProveedor = nifProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getGastoConcepto() {
        return gastoConcepto;
    }

    public void setGastoConcepto(String gastoConcepto) {
        this.gastoConcepto = gastoConcepto;
    }

    public boolean isPagoCompletado() {
        return pagoCompletado;
    }

    public void setPagoCompletado(boolean pagoCompletado) {
        this.pagoCompletado = pagoCompletado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

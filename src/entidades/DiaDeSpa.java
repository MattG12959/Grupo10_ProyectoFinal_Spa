/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class DiaDeSpa {
    private int codPack;
    private LocalDateTime fechayhora;
    private String preferencias;
    private Cliente cliente;
    private ArrayList<Sesion> sesiones;
    private double monto;
    private boolean estado;

    public DiaDeSpa() {
    }

    public DiaDeSpa(LocalDateTime fechayhora, String preferencias, Cliente cliente, ArrayList<Sesion> sesiones, double monto, boolean estado) {
        this.fechayhora = fechayhora;
        this.preferencias = preferencias;
        this.cliente = cliente;
        this.sesiones = new ArrayList<>();
        this.monto = monto;
        this.estado = true;
    }

    public DiaDeSpa(int codPack, LocalDateTime fechayhora, String preferencias, Cliente cliente, ArrayList<Sesion> sesiones, double monto, boolean estado) {
        this.codPack = codPack;
        this.fechayhora = fechayhora;
        this.preferencias = preferencias;
        this.cliente = cliente;
        this.sesiones = new ArrayList<>();
        this.monto = monto;
        this.estado = true;
    }

    public int getCodPack() {
        return codPack;
    }

    public void setCodPack(int codPack) {
        this.codPack = codPack;
    }

    public LocalDateTime getFechayhora() {
        return fechayhora;
    }

    public void setFechayhora(LocalDateTime fechayhora) {
        this.fechayhora = fechayhora;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(ArrayList<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    @Override
    public String toString() {
        return "Dia_de_Spa{" +
                "codPack=" + codPack +
                ", fecha_y_hora=" + fechayhora +
                ", preferencias='" + preferencias + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "null") +
                ", sesiones=" + sesiones.size() +
                ", monto=" + monto +
                ", estado=" + estado +
                '}';
    }
}




package entidades;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author Grupo10 
 * 
 * Altamirano Karina 
 * Gianfranco Antonacci Matías 
 * Bequis Marcos Ezequiel 
 * Dave Natalia 
 * Quiroga Dorzan Alejo
 */
public class Tratamiento {
    
    // Atributos
    private int codTratam = -1;
    private String nombre;
    private String tipoTratamiento; // enum: constantes para especialidades (facial, corporal, relajación, o estético
    private String detalle; // Información del tipo de tratamiento
    private ArrayList<Producto> producto = new ArrayList<>(); 
    private LocalTime duracion; // Hora
    private double costo;
    private boolean estado;
    
    // Constructores
    public Tratamiento() {
    }

    public Tratamiento(String nombre, String tipoTratamiento, String detalle, ArrayList<Producto> producto, LocalTime duracion, double costo, boolean estado) {
        this.nombre = nombre;
        this.tipoTratamiento = tipoTratamiento;
        this.detalle = detalle;
        this.producto = producto;
        this.duracion = duracion;
        this.costo = costo;
        this.estado = estado;
    }
    
    public Tratamiento(int codTratam, String nombre, String tipoTratamiento, String detalle, ArrayList<Producto> producto, LocalTime duracion, double costo, boolean estado) {
        this.codTratam = codTratam;
        this.nombre = nombre;
        this.tipoTratamiento = tipoTratamiento;
        this.detalle = detalle;
        this.producto = producto;
        this.duracion = duracion;
        this.costo = costo;
        this.estado = estado;
    }

    public Tratamiento(String nombre, String tipoTratamiento, String detalle, LocalTime duracion, double costo, boolean estado) {
        this.nombre = nombre;
        this.tipoTratamiento = tipoTratamiento;
        this.detalle = detalle;
        this.duracion = duracion;
        this.costo = costo;
        this.estado = estado;
    }
    
    
    
    // Getters y Setters
    public int getCodTratam() {
        return codTratam;
    }

    public void setCodTratam(int codTratam) {
        this.codTratam = codTratam;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String gettipoTratamiento() {
        return tipoTratamiento;
    }

    public void settipoTratamiento(String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public ArrayList<Producto> getProducto() {
        return producto;
    }

    public void setProducto(ArrayList<Producto> producto) {
        this.producto = producto;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

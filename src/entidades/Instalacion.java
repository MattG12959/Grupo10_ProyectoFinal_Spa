
package entidades;


public class Instalacion {

   private int codInstal = -1;
   private String nombre;
   private String detalleDeUso;
   private int usos;
   private double precio;
   private boolean estado;

    public Instalacion() {
    }

    public Instalacion(String nombre, String detalleDeUso, int usos, double precio30m, boolean estado) {
        this.nombre = nombre;
        this.detalleDeUso = detalleDeUso;
        this.usos = usos;
        this.precio = precio30m;
        this.estado = estado;
    }

    public Instalacion(int codInstal, String nombre, String detalleDeUso, int usos, double precio30m, boolean estado) {
        this.codInstal = codInstal;
        this.nombre = nombre;
        this.detalleDeUso = detalleDeUso;
        this.usos = usos;
        this.precio = precio30m;
        this.estado = estado;
    }

    public int getCodInstal() {
        return codInstal;
    }

    public void setCodInstal(int codInstal) {
        this.codInstal = codInstal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalleDeUso() {
        return detalleDeUso;
    }

    public void setDetalleDeUso(String detalleDeUso) {
        this.detalleDeUso = detalleDeUso;
    }

    public int getUsos() {
        return usos;
    }

    public void setUsos(int usos) {
        this.usos = usos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Instalacion{" + "codInstal=" + codInstal + ", nombre=" + nombre + ", precio30m=" + precio + '}';
    }

}


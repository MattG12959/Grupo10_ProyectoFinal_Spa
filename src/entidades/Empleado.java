
package entidades;

/**
 * @author Grupo10 
 * 
 * Altamirano Karina 
 * Gianfranco Antonacci Matías 
 * Bequis Marcos Ezequiel 
 * Dave Natalia 
  */

public class Empleado {
    // Atributos
    private int idEmpleado = -1;
    private int dni;
    private String puesto;
    
    // Constructores
    public Empleado() {
    }

    public Empleado(int dni, String puesto) {
        this.dni = dni;
        this.puesto = puesto;
    }

    public Empleado(int idEmpleado, int dni, String puesto) {
        this.idEmpleado = idEmpleado;
        this.dni = dni;
        this.puesto = puesto;
    }
    
    //  Getters y Setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}

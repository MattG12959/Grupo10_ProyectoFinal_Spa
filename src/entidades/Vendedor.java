
package entidades;

/**
 * @author Grupo10 Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos
 * Ezequiel Dave Natalia Quiroga Dorzan Alejo
 */
public class Vendedor {
    int idEmpleado;
    String nombre;
    String apellido;
    String teléfono;
    int dni; 
    String especialidad; // (enum: constantes para especialidad (facial, corporal, relajación, o estético))
    boolean estado; // (Inactivo o Activo)

    public Vendedor() {
    }

    
    public Vendedor(int idEmpleado, String nombre, String apellido, String teléfono, int dni, String especialidad, boolean estado) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.teléfono = teléfono;
        this.dni = dni;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Vendedor(String nombre, String apellido, String teléfono, int dni, String especialidad, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.teléfono = teléfono;
        this.dni = dni;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTeléfono() {
        return teléfono;
    }

    public void setTeléfono(String teléfono) {
        this.teléfono = teléfono;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
}

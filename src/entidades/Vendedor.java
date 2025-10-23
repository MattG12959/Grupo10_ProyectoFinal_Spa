
package entidades;

/**
 * @author Grupo10 Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos
 * Ezequiel Dave Natalia Quiroga Dorzan Alejo
 */
public class Vendedor {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String telefono;
    private int dni; 
    private String puesto; // (enum: constantes para puesto (facial, corporal, relajación, o estético))
    private boolean estado; // (Inactivo o Activo)

    public Vendedor() {
    }

    public Vendedor(int idEmpleado, String nombre, String apellido, String teléfono, int dni, String especialidad, boolean estado) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = teléfono;
        this.dni = dni;
        this.puesto = especialidad;
        this.estado = estado;
    }

    public Vendedor(String nombre, String apellido, String teléfono, int dni, String especialidad, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = teléfono;
        this.dni = dni;
        this.puesto = especialidad;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
     
    
}

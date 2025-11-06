package entidades;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
public class Masajista {

    private int idEmpleado;
    private int matricula;
    private String nombre;
    private String apellido;
    private String telefono;
    private int dni;
    private String puesto; // (enum: Masajista o Vendedor)
    private String especialidad; //(enum: constantes para especialidad (facial, corporal, relajación, o estético))
    private boolean estado; // (Inactivo o Activo)

    public Masajista() {
    }

    public Masajista(int idEmpleado, int matricula, String nombre, String apellido, String telefono, int dni, String puesto,  String especialidad, boolean estado) {
        this.idEmpleado = idEmpleado;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.puesto = puesto;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Masajista(int matricula, String nombre, String apellido, String telefono, int dni,  String puesto, String especialidad, boolean estado) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.puesto = puesto;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
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

    @Override
    public String toString() {
        return "Masajista{" + "idEmpleado=" + idEmpleado + ", matricula=" + matricula + ", nombre=" + nombre + ", apellido=" + apellido + ", teléfono=" + telefono + ", dni=" + dni + ", especialidad=" + especialidad + ", estado=" + estado + '}';
    }

}

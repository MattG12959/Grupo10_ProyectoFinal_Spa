
package constantes;

/**
 *
 * @author Ezequiel
 */
public enum Especialidades {
    FACIAL("Facial"),
    CORPORAL("Corporal"),
    RELAJACION("Relajacion"),
    ESTETICO("Estetico");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String especialidad;
    
    // Constructor que se llama para inicializar cada constant
    Especialidades(String especialidad) {
        this.especialidad = especialidad;
    }
    
    // Metodos getters para acceder a los campos
    public String getEspecialidad() {
        return especialidad;
    }
    
    
}

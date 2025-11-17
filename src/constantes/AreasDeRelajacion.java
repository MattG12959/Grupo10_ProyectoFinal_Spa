
package constantes;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public enum AreasDeRelajacion {
    ZONA_DE_RELAX("Zona de relax", "Espacio tranquilo con tumbonas o sillones para descansar antes y después de los tratamientos."),
    SALA_DE_TE("Sala de te", "Un área donde se ofrecen bebidas relajantes para complementar la experiencia."),
    SALA_DE_INFUCIONES("Sala de infuciones", "Un área donde se ofrecen bebidas relajantes para complementar la experiencia.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;
    
    // Constructor que se llama para inicializar cada constant
    AreasDeRelajacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Metodos getters para acceder a los campos
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

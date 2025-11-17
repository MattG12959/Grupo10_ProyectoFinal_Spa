
package constantes;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public enum Masajes {
    MASAJE_SUECO("Masaje sueco", "Terapia de relajación de cuerpo completo con presión ligera a media."),
    MASAJE_DE_TEJIDO_PROFUNDO("Masaje de tejido profundo", "Para aliviar tensión muscular crónica y tratar contracturas, con presión más intensa."),
    MASAJE_CON_PIEDRAS_CALIENTES("Masaje con piedras calientes", "Utiliza piedras calientes para calmar los músculos y el sistema nervioso."),
    MASAJES_ESPECIALIZADOS("Masajes especializados", "Incluyen técnicas como la aromaterapia, masajes para parejas o masajes prenatales.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;

    // Constructor que se llama para inicializar cada constante
    Masajes(String nombre, String descripcion) {
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

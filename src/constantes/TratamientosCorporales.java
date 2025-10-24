
package constantes;

/**
 *
 * @author Ezequiel
 */
public enum TratamientosCorporales {
    EXFOLIACION_CORPORAL("Exfoliación corporal", "Elimina las células muertas de la piel con el uso de sales o azúcares."),
    ENVOLTURAS_CORPORALES("Envolturas corporales", "Hidratan y desintoxican la piel con ingredientes como algas, barro o chocolate."),
    HIDROTERAPIA("Hidroterapia", "Sesiones en saunas, jacuzzis o baños de vapor."),
    VACUMTERAPIA("Vacumterapia", "Es una succión corporal que mejora la circulación sanguínea y el drenaje linfático.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;

    // Constructor que se llama para inicializar cada constante
    TratamientosCorporales(String nombre, String descripcion) {
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

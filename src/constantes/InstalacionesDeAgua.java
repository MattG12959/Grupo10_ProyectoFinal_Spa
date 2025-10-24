
package constantes;

/**
 *
 * @author Ezequiel
 */
public enum InstalacionesDeAgua {
    JACUZZI("Jacuzzi", "Bañeras con chorros de agua a presión para relajar los músculos y aliviar la tensión."),
    PISCINA_CLIMATIZADA("Piscina climatizada", "Piscinas con agua a temperatura agradable, a veces equipadas con camas de burbujas."),
    SAUNA("Sauna", "Habitaciones con vapor de agua caliente que ayudan a purificar y limpiar la piel en profundidad."),
    DUCHAS_DE_SENSACIONES("Duchas de sensaciones", "Combina diferentes temperaturas, presiones y aromas para estimular los sentidos."),
    PEDILUVIO("Pediluvio", "Baño de pies que generalmente combina agua fría y caliente para mejorar la circulación de las piernas."),
    CIRCUITO_DE_HIDROTERAPIA("Circuito de hidroterapia", "Secuencia de 3 bañeras de agua con diferentes temperaturas para estimular la circulación.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;

    // Constructor que se llama para inicializar cada constante
    InstalacionesDeAgua(String nombre, String descripcion) {
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

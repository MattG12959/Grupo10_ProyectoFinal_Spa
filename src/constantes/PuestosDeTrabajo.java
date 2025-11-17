package constantes;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public enum PuestosDeTrabajo {
    MASAJISTA("Masajista"),
    VENDEDOR("Vendedor");

    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String puesto;
    
    // Constructor que se llama para inicializar cada constante
    PuestosDeTrabajo(String puesto) {
        this.puesto = puesto;
    }
    
    // Metodos getters para acceder a los campos
    public String getPuesto() {
        return puesto;
    }
}

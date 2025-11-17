/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constantes;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public enum TratamientosRelajacion {
    MASAJE_RELAJANTE("Masaje relajante", "Masaje suave que reduce el estrés y la tensión muscular, promoviendo la relajación general."),
    AROMATERAPIA("Aromaterapia", "Uso de aceites esenciales para mejorar el bienestar físico y emocional."),
    REFLEXOLOGIA("Reflexología", "Técnica de presión en puntos específicos de pies y manos para aliviar tensiones."),
    REIKI("Reiki", "Técnica de sanación energética que promueve la relajación y el equilibrio."),
    MEDITACION_GUIADA("Meditación guiada", "Sesiones de relajación mental y meditación para reducir el estrés."),
    TERAPIA_CON_PIEDRAS_CALIENTES("Terapia con piedras calientes", "Masaje con piedras volcánicas calientes que relajan los músculos y mejoran la circulación."),
    BAÑO_DE_VAPOR("Baño de vapor", "Sesión en baño turco o sauna para relajar y desintoxicar el cuerpo.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;

    // Constructor que se llama para inicializar cada constante
    TratamientosRelajacion(String nombre, String descripcion) {
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

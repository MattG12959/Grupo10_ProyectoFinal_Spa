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
public enum TratamientosEsteticos {
    DEPILACION_LASER("Depilación láser", "Eliminación permanente del vello mediante tecnología láser."),
    DEPILACION_CERA("Depilación con cera", "Eliminación del vello mediante aplicación de cera caliente o fría."),
    MANICURE("Manicure", "Cuidado y embellecimiento de las uñas de las manos."),
    PEDICURE("Pedicure", "Cuidado y embellecimiento de las uñas de los pies."),
    TRATAMIENTO_ANTIENVEJECIMIENTO("Tratamiento antienvejecimiento", "Tratamientos para reducir arrugas y mejorar la elasticidad de la piel."),
    TRATAMIENTO_ANTICELULITICO("Tratamiento anticelulítico", "Tratamientos para reducir la apariencia de la celulitis."),
    TRATAMIENTO_REDUCTOR("Tratamiento reductor", "Tratamientos para reducir medidas y tonificar el cuerpo."),
    LIMPIEZA_PROFUNDA("Limpieza profunda", "Limpieza exhaustiva de la piel para eliminar impurezas y puntos negros.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;

    // Constructor que se llama para inicializar cada constante
    TratamientosEsteticos(String nombre, String descripcion) {
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

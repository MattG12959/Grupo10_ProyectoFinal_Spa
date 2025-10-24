/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package constantes;

/**
 *
 * @author Ezequiel
 */
public enum TratamientosFaciales {
    FACIAL_BASICO("Facial básico", "Limpieza profunda, exfoliación y mascarilla para refrescar el cutis."),
    FACIALES_AVANZADOS("Faciales avanzados", "Pueden incluir tratamientos antienvejecimiento, microdermoabrasión o hidrafaciales."),
    MICRODERMOABRASION("Microdermoabrasión", "Exfolia la capa superficial de la piel usando puntas de diamante o microcristales."),
    RADIOFRECUENCIA("Radiofrecuencia", "Usa ondas de radio para generar calor en la piel, estimulando la producción de colágeno y elastina."),
    ULTRASONIDO("Ultrasonido", "Usa ondas sonoras de alta frecuencia para limpiar la piel, penetrar activos o hacer masajes profundos."),
    LUZ_PULSADA_INTENSA("Luz Pulsada Intensa (IPL)", "Utilizada para fotodepilación, rejuvenecimiento de la piel y tratamiento de manchas."),
    VAPORIZADOR_FACIAL("Vaporizador facial", "Produce vapor para abrir los poros y limpiar la piel profundamente.");
    
    // Campos de la clase, se ponen como privados y finales para que sean inmutables
    private final String nombre;
    private final String descripcion;

    // Constructor que se llama para inicializar cada constante
    TratamientosFaciales(String nombre, String descripcion) {
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

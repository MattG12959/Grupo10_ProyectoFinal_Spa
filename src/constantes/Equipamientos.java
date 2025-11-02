
package constantes;

/**
 *
 * @author GP10
 */

public enum Equipamientos {

    CAMILLA_MASAJES("Cama de Masajes", 
        "Superficie acolchada ajustable utilizada para realizar diferentes tipos de masajes."),

    PIEDRAS_CALIENTES("Piedras Calientes", 
        "Rocas lisas de basalto que se calientan y se colocan sobre el cuerpo para relajar los músculos."),

    VAPORIZADOR_FACIAL("Vaporizador Facial", 
        "Dispositivo que emite vapor caliente para abrir los poros y facilitar la limpieza facial."),

    EQUIPO_RADIOFRECUENCIA("Equipo de Radiofrecuencia", 
        "Aparato que emite ondas de radio para estimular la producción de colágeno y tonificar la piel."),

    MAQUINA_ULTRASONIDO("Máquina de Ultrasonido", 
        "Dispositivo que utiliza ondas ultrasónicas para penetrar principios activos en la piel."),

    CAMILLA_HIDROMASAJE("Cama de Hidromasaje", 
        "Cama o bañera con chorros de agua a presión que proporcionan un masaje relajante."),

    CABINA_SAUNA("Cabina de Sauna", 
        "Habitáculo cerrado que genera vapor o calor seco para relajar los músculos y purificar la piel."),

    DUCHA_SENSORIAL("Ducha Sensorial", 
        "Sistema de ducha con distintos tipos de presión, temperatura y aromaterapia para estimular los sentidos."),

    EQUIPO_MICRODERMOABRASION("Equipo de Microdermoabrasión", 
        "Aparato con puntas de diamante o microcristales que exfolia la piel en profundidad."),

    LAMPARA_LUZ_PULSADA("Lámpara de Luz Pulsada (IPL)", 
        "Equipo que emite pulsos de luz intensa para tratamientos de rejuvenecimiento o depilación."),

    CAMILLA_PRENATAL("Cama de Masaje Prenatal", 
        "Cama ergonómica adaptada para embarazadas, con espacio para el abdomen y soporte adecuado."),

    SILLON_RELAX("Sillón de Relajación", 
        "Asiento reclinable o masajeador donde los clientes descansan antes o después de los tratamientos.");

    // Atributos
    private final String nombre;
    private final String descripcion;

    // Constructor
    Equipamientos(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}


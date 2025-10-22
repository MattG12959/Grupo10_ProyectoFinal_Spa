package entidades;

import java.util.ArrayList;

/**
 * @author Grupo10 
 * 
 * Altamirano Karina 
 * Gianfranco Antonacci Matías 
 * Bequis Marcos Ezequiel 
 * Dave Natalia 
 * Quiroga Dorzan Alejo
 */

// Hace referencia a una sala común de masajes 
public class Consultorio {

    // Atributos
    private int nroConsultorio = -1;
    private int usos; // Cantidad de veces que fue utilizado el consultorio para mantenimiento, estadísticas, etc
    private ArrayList<String> equipamiento; // enum: constantes para equipamientos
    private ArrayList<String> apto; // enum: constantes para especialidades

    // Constructores
    public Consultorio() {
    }

    public Consultorio(int usos, ArrayList<String> equipamiento, ArrayList<String> apto) {
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }

    public Consultorio(int nroConsultorio, int usos, ArrayList<String> equipamiento, ArrayList<String> apto) {
        this.nroConsultorio = nroConsultorio;
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }

    // Getters y Setters
    public int getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(int nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public int getUsos() {
        return usos;
    }

    public void setUsos(int usos) {
        this.usos = usos;
    }

    public ArrayList<String> getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(ArrayList<String> equipamiento) {
        this.equipamiento = equipamiento;
    }

    public ArrayList<String> getApto() {
        return apto;
    }

    public void setApto(ArrayList<String> apto) {
        this.apto = apto;
    }
}

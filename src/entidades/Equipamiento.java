/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

public class Equipamiento {
    private int idEquipamiento = -1;
    private int nroConsultorio;
    private String nombre_equipamiento;
    private String descripcion_equipamiento;

    public Equipamiento() {
    }

    public Equipamiento(int nroConsultorio, String nombre_equipamiento, String descripcion_equipamiento) {
        this.nroConsultorio = nroConsultorio;
        this.nombre_equipamiento = nombre_equipamiento;
        this.descripcion_equipamiento = descripcion_equipamiento;
    }
    
    public Equipamiento(int idEquipamiento, int nroConsultorio, String nombre_equipamiento, String descripcion_equipamiento) {
        this.idEquipamiento = idEquipamiento;
        this.nroConsultorio = nroConsultorio;
        this.nombre_equipamiento = nombre_equipamiento;
        this.descripcion_equipamiento = descripcion_equipamiento;
    }

    public int getIdEquipamiento() {
        return idEquipamiento;
    }

    public void setIdEquipamiento(int idEquipamiento) {
        this.idEquipamiento = idEquipamiento;
    }

    public int getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(int nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getNombre_equipamiento() {
        return nombre_equipamiento;
    }

    public void setNombre_equipamiento(String nombre_equipamiento) {
        this.nombre_equipamiento = nombre_equipamiento;
    }

    public String getDescripcion_equipamiento() {
        return descripcion_equipamiento;
    }

    public void setDescripcion_equipamiento(String descripcion_equipamiento) {
        this.descripcion_equipamiento = descripcion_equipamiento;
    }
    
    
}

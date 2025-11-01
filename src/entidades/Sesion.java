package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
public class Sesion {
    int codSesion = -1;
    LocalDateTime fechaHoraInicio;
    LocalDateTime fechaHoraFinal;
    Tratamiento tratamiento;
    Consultorio consultorio;
    Masajista masajista;
    Dia_De_Spa diaDeSpa;
    ArrayList<Instalacion> insalaciones;
    boolean estado;

    public Sesion() {
    }

    public Sesion(int codSesion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFinal, Tratamiento tratamiento, Consultorio consultorio, Masajista masajista, Dia_De_Spa diaDeSpa, ArrayList<Instalacion> insalaciones, boolean estado) {
        this.codSesion = codSesion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFinal = fechaHoraFinal;
        this.tratamiento = tratamiento;
        this.consultorio = consultorio;
        this.masajista = masajista;
        this.diaDeSpa = diaDeSpa;
        this.insalaciones = insalaciones;
        this.estado = estado;
    }

    
    
    public Sesion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFinal, Tratamiento tratamiento, Consultorio consultorio, Masajista masajista, Dia_De_Spa diaDeSpa, ArrayList<Instalacion> insalaciones, boolean estado) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFinal = fechaHoraFinal;
        this.tratamiento = tratamiento;
        this.consultorio = consultorio;
        this.masajista = masajista;
        this.diaDeSpa = diaDeSpa;
        this.insalaciones = insalaciones;
        this.estado = estado;
    }

    public int getCodSesion() {
        return codSesion;
    }

    public void setCodSesion(int codSesion) {
        this.codSesion = codSesion;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFinal() {
        return fechaHoraFinal;
    }

    public void setFechaHoraFinal(LocalDateTime fechaHoraFinal) {
        this.fechaHoraFinal = fechaHoraFinal;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Masajista getMasajista() {
        return masajista;
    }

    public void setMasajista(Masajista masajista) {
        this.masajista = masajista;
    }

    public Dia_De_Spa getDiaDeSpa() {
        return diaDeSpa;
    }

    public void setDiaDeSpa(Dia_De_Spa diaDeSpa) {
        this.diaDeSpa = diaDeSpa;
    }

    public ArrayList<Instalacion> getInsalaciones() {
        return insalaciones;
    }

    public void setInsalaciones(ArrayList<Instalacion> insalaciones) {
        this.insalaciones = insalaciones;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Sesion{" + "codSesion=" + codSesion + ", fechaHoraInicio=" + fechaHoraInicio + ", fechaHoraFinal=" + fechaHoraFinal + ", tratamiento=" + tratamiento + ", consultorio=" + consultorio + ", masajista=" + masajista + ", diaDeSpa=" + diaDeSpa + ", insalaciones=" + insalaciones + ", estado=" + estado + '}';
    }
}

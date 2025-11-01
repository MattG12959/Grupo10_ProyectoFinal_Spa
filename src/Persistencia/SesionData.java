/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Sesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
public class SesionData {
    private Connection con = null;

    public SesionData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }
    
    //------------ Buscar en la base de datos la Sesion por ID ------------
    public Sesion buscarSesion(int codSesion){
        String query = "SELECT * FROM `sesion` WHERE `codSesion` = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, codSesion);
            ResultSet resultado = ps.executeQuery();
            Sesion sesion = null;
            LocalDateTime fechaHoraInicio = null;
            LocalDateTime fechaHoraFinal = null;
            
            while(resultado.next()){
                fechaHoraInicio = LocalDateTime.parse(resultado.getString("fecha_hora_inicio"));
                fechaHoraFinal = LocalDateTime.parse(resultado.getString("fecha_hora_final"));
                sesion = new Sesion(
                    /*
                    resultado.getInt("codSesion"),
                    fechaHoraInicio,
                    fechaHoraFinal,
                    resultado.getInt("idTratamiento"),
                    resultado.getInt("idConsultorio"),
                    resultado.getInt("idMasajista"),
                    resultado.getString("instalaciones"),
                    resultado.getInt("codPack"),
                    resultado.getBoolean("estado")
                    */
                );
            }
            return sesion;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la sesion en la base de datos. " + ex.getMessage());
        }
        return null;
    }
    
    
}

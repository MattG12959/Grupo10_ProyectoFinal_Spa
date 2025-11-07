/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Equipamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EquipamientoData {

    private Connection con;

    public EquipamientoData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    public void cargaEquipamiento(ArrayList<Equipamiento> equipamientos) {
        try {
            String sql = "INSERT INTO `equipamiento`(`idConsultorio`, `nombre`, `descripcion`) VALUES (?, ?, ?)";
            
            if (!equipamientos.isEmpty()) {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                for (Equipamiento aux : equipamientos) {
                    ps.setInt(1, aux.getNroConsultorio());
                    ps.setString(2, aux.getNombre_equipamiento());
                    ps.setString(3, aux.getDescripcion_equipamiento());

                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    
                    if (rs.next()) {
                        aux.setIdEquipamiento(rs.getInt(1));
                    }
                    
                    JOptionPane.showMessageDialog(null, "Equipamiento dado de alta con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
                }
                ps.close();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido guardar el equipamiento a la base de datos.", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipamientos", "", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(VendedorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

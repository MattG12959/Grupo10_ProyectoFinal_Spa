/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Consultorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author GP10
 */
public class ConsultorioData {

    private Connection con;

    public ConsultorioData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    public void cargaConsultorio(Consultorio consultorio) {
        String sql = "INSERT INTO `consultorio`(`usos`, `apto`) VALUES (?, ?)";
        String existe = "SELECT * FROM `consultorio` WHERE `idConsultorio`= ?";

        // Creo una nueva conexion, ya que siempre va a tirar error por una imcompatiblidad entre miConexion y Connection
        miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");

        EquipamientoData ed = new EquipamientoData(conexion);

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, consultorio.getUsos());
            ps.setString(2, consultorio.getApto());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                consultorio.setNroConsultorio(rs.getInt(1));
            }
            
                /*  CARGAMOS LOS EQUIPAMIENTOS LUEGO :P
            if (!consultorio.getEquipamiento().isEmpty()) {
                ed.cargaEquipamiento(consultorio.getEquipamiento());
            }*/

            ps.close();

            JOptionPane.showMessageDialog(null, "Consultorio dado de alta", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la tabla de tratamientos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

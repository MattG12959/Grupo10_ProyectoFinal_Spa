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
import java.util.ArrayList;
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
    
    public Consultorio buscarConsultorio(int idConsultorio) {
        String sql = "SELECT * FROM consultorio WHERE idConsultorio = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idConsultorio);
            ResultSet rs = ps.executeQuery();
            Consultorio consultorio = null;

            while (rs.next()) {
                consultorio = new Consultorio(
                        rs.getInt("idConsultorio"),
                        rs.getInt("usos"),
                        rs.getString("apto")
                );
            }

            ps.close();
            return consultorio;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el consultorio. " + ex.getMessage());
            return null;
        }
    }
    
    public void eliminarConsultorio(int idConsultorio) {
        String sql = "DELETE FROM consultorio WHERE idConsultorio = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idConsultorio);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el consultorio con ese código.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar consultorio. " + ex.getMessage());
        }
    }
    
    public ArrayList<Consultorio> listarConsultorios() {
        ArrayList<Consultorio> consultorios = new ArrayList<>();
        String sql = "SELECT * FROM consultorio";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio c = new Consultorio(
                        rs.getInt("idConsultorio"),
                        rs.getInt("usos"),
                        rs.getString("apto")
                );
                consultorios.add(c);
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar consultorios. " + ex.getMessage());
        }

        return consultorios;
    }
}

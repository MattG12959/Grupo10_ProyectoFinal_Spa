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

    public ArrayList<Equipamiento> listarEquipamientosPorConsultorio(int nroConsultorio) {
        ArrayList<Equipamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipamiento WHERE idConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Equipamiento e = new Equipamiento();
                e.setIdEquipamiento(rs.getInt("idEquipamiento"));
                e.setNroConsultorio(rs.getInt("idConsultorio"));
                e.setNombre_equipamiento(rs.getString("nombre"));
                e.setDescripcion_equipamiento(rs.getString("descripcion"));
                lista.add(e);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar equipamientos: " + ex.getMessage());
        }
        return lista;
    }
    
    public void eliminarEquipamiento(int idEquipamiento) {
    String sql = "DELETE FROM equipamiento WHERE idEquipamiento = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idEquipamiento);
        int filas = ps.executeUpdate();
        if (filas > 0) {
            JOptionPane.showMessageDialog(null, "Equipamiento eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el equipamiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el equipamiento. " + ex.getMessage());
    }
}
}

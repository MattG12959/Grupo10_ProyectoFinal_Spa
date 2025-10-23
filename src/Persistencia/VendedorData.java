/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.sql.Connection;
import entidades.Vendedor;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class VendedorData {

    private Connection con = null;

    public VendedorData(miConexion conexion) {
        this.con = conexion.buscarConexion();
        //con = miConexion.buscarConexion();
    }

    

    public void darAltaVendedor(int idEmpleado) {
        String query = "UPDATE vendedor SET estado=1 WHERE idEmpleado = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, idEmpleado);
            int i = ps.executeUpdate();

            if (i == 0) {
                JOptionPane.showMessageDialog(null, "Vendedor dado de alta con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro al vendedor", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VendedorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarVendedor(Vendedor a) {        
        String query = "UPDATE Vendedor SET nombre = ?, apellido = ?, telefono=?, dni=? WHERE idEmpleado = ?";
        //System.out.println("["+a.getNombre()+"]"+a.getApellido()+"]"a.getTelefono()+"] "+a.getDni()+"]"+a.getEspecialidad()+"]"+a.getEstado()+"]"+a.getIdEmpleado()+"] ");
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getTeléfono());
            ps.setInt(4, a.getDni());
            ps.setInt(5, a.getIdEmpleado());
            int aux = ps.executeUpdate();
            if (aux == 0) {
                JOptionPane.showMessageDialog(null, "El Vendedor no se ha modificado.");
            }
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(VendedorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

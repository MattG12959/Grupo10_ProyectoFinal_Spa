/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;


import java.sql.Connection;
import entidades.Cliente;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class ClienteData {
    private Connection con = null;

    public ClienteData(miConexion conexion) {
        this.con = conexion.buscarConexion();
        //con = miConexion.buscarConexion();
    }

    public void actualizarCliente(Cliente a) {        
        String query = "UPDATE Cliente SET dni=?, nombre=?, apellido=?, telefono=?, edad=?, afecciones=?, estado=? WHERE codCli=?";
        //System.out.println("[" + a.getDni() + "] [" + a.getNombre() + "] [" + a.getApellido() + "] [" + a.getTelefono() + "] [" + a.getEdad() + "] [" + a.getAfecciones() + "] [" + a.isEstado() + "] [" + a.getCodCli() + "]");

            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, a.getDni());
                ps.setString(2, a.getNombre());
                ps.setString(3, a.getApellido());
                ps.setString(4, a.getTelefono());
                ps.setInt(5, a.getEdad());
                ps.setString(6, a.getAfecciones());
                ps.setBoolean(7, a.isEstado());
                ps.setInt(8, a.getCodCli());

                int aux = ps.executeUpdate();
                if (aux == 0) {
                    JOptionPane.showMessageDialog(null, "El Cliente no se ha modificado.");
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente.");
                }
                ps.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + ex.getMessage());
                Logger.getLogger(ClienteData.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Empleado;
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

    

    public void altaVendedor(Vendedor vendedor) {
        try {
            // 1) Creo un empleado
            Empleado empleado = new Empleado();

            // 2) Le paso el dni del vendedor y el puesto
            empleado.setDni(vendedor.getDni());
            empleado.setPuesto(vendedor.getPuesto());

            // 3) Creo una nueva conexion, ya que siempre va a tirar error por una imcompatiblidad entre miConexion y Connection
            miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
            conexion.buscarConexion();

            // 4) Llamo al metodo "AltaEmpleado"
            EmpleadoData empleadoData = new EmpleadoData(conexion);
            empleadoData.altaEmpleado(empleado);
            
            // 5) Inserto en la tabla VENDEDOR con el idEmpleado ya generado, el DNI y el puesto
            String sql = "INSERT INTO `vendedor` (`idEmpleado`, `nombre`, `apellido`, `telefono`, `dni`, `puesto`, `estado`) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, empleado.getIdEmpleado());
            ps.setString(2, vendedor.getNombre());
            ps.setString(3, vendedor.getApellido());
            ps.setString(4, vendedor.getTelefono());
            ps.setInt(5, empleado.getDni());
            ps.setString(6, empleado.getPuesto());
            ps.setBoolean(7, vendedor.getEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Vendedor dado de alta con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo dar de alta al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
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
            ps.setString(3, a.getTelefono());
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
    
    public void bajaLogica(int idEmpleado) {
        String query = "UPDATE vendedor SET estado=0 WHERE idEmpleado = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, idEmpleado);
            int i = ps.executeUpdate();

            if (i == 1) {
                JOptionPane.showMessageDialog(null, "Empleado dado de baja con exito.", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VendedorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

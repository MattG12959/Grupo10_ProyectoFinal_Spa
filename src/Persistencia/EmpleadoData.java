
package Persistencia;

import entidades.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author Grupo10 
 * 
 * Altamirano Karina 
 * Gianfranco Antonacci Matías 
 * Bequis Marcos Ezequiel 
 * Dave Natalia 
 * Quiroga Dorzan Alejo
 */

public class EmpleadoData {
    private Connection con = null;

    public EmpleadoData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }
    
    public void altaEmpleado(Empleado empleado) {
        String sql = "INSERT INTO `empleado`(`dni`, `puesto`) VALUES (?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, empleado.getDni());
            ps.setString(2, empleado.getPuesto());
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                empleado.setIdEmpleado(rs.getInt(1));
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la tabla de empleados", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package Persistencia;
/*
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
*/
import entidades.Instalacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class InstalacionData {

    private Connection con = null;
    
    public InstalacionData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }
    
    // ----------------- GUARDAR INSTALACIÓN -----------------
    public void guardarInstalacion(Instalacion i) {
        String sql = "INSERT INTO instalacion (nombre, detalle_de_uso, usos, precio, estado) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDetalleDeUso());
            ps.setInt(3, i.getUsos());
            ps.setDouble(4, i.getPrecio());
            ps.setBoolean(5, i.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                i.setCodInstal(rs.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "Instalación guardada correctamente.");
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la instalación. " + ex.getMessage());
        }
    }
    
    // ----------------- MODIFICAR INSTALACIÓN -----------------
    public void modificarInstalacion(Instalacion i){
        String query = "UPDATE instalacion SET nombre =? ,detalle_de_uso = ?, usos = ?, precio = ?, estado = ? WHERE idInstalacion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDetalleDeUso());
            ps.setInt(3,i.getUsos());
            ps.setDouble(4,i.getPrecio());
            ps.setBoolean(5, i.isEstado());
            ps.setInt(6, i.getCodInstal());
            
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Instalación modificada correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al modificar la instalación: " + ex.getMessage());
        }
    }

    // ----------------- BUSCAR INSTALACIÓN POR ID -----------------
    public Instalacion buscarInstalacion(int codInstal) {
        String sql = "SELECT * FROM instalacion WHERE idInstalacion = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInstal);
            ResultSet rs = ps.executeQuery();
            Instalacion instalacion = null;

            while (rs.next()) {
                instalacion = new Instalacion(
                        rs.getInt("idInstalacion"),
                        rs.getString("nombre"),
                        rs.getString("detalle_de_uso"),
                        rs.getInt("usos"),
                        rs.getDouble("precio"),
                        rs.getBoolean("estado")
                );
            }

            ps.close();
            return instalacion;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la instalación. " + ex.getMessage());
            return null;
        }
    }

    // ----------------- ELIMINAR INSTALACIÓN -----------------
    public void eliminarInstalacion(int codInstal) {
        String sql = "DELETE FROM instalacion WHERE codInstal = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInstal);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Instalación eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la instalación con ese código.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar instalación. " + ex.getMessage());
        }
    }

    // ----------------- LISTAR TODAS LAS INSTALACIONES -----------------
    public ArrayList<Instalacion> listarInstalaciones() {
        ArrayList<Instalacion> instalaciones = new ArrayList<>();
        String sql = "SELECT * FROM instalacion";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Instalacion i = new Instalacion(
                        rs.getInt("idInstalacion"),
                        rs.getString("nombre"),
                        rs.getString("detalle_de_uso"),
                        rs.getInt("usos"),
                        rs.getDouble("precio"),
                        rs.getBoolean("estado")
                );
                instalaciones.add(i);
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones. " + ex.getMessage());
        }

        return instalaciones;
    }
}

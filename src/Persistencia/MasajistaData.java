/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Empleado;
import entidades.Masajista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
public class MasajistaData {

     private Connection con = null;

    public MasajistaData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    public void altaMasajista(Masajista masajista) {
        try {
            // 1) Creo un empleado
            Empleado empleado = new Empleado();

            // 2) Le paso el dni de la masajista y el puesto
            empleado.setDni(masajista.getDni());
            empleado.setPuesto(masajista.getPuesto());

            // 3) Creo una nueva conexion, ya que siempre va a tirar error por una imcompatiblidad entre miConexion y Connection
            miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
            conexion.buscarConexion();

            // 4) Llamo al metodo "AltaEmpleado"
            EmpleadoData empleadoData = new EmpleadoData(conexion);
            empleadoData.altaEmpleado(empleado);

            // 5) Inserto en la tabla MASAJISTAcon el idEmpleado ya generado, el DNI y el puesto
            String sql = "INSERT INTO masajista (idEmpleado, matricula, nombre, apellido, telefono, dni, puesto, especialidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, empleado.getIdEmpleado());
            ps.setInt(2, masajista.getMatricula());
            ps.setString(3, masajista.getNombre());
            ps.setString(4, masajista.getApellido());
            ps.setString(5, masajista.getTelefono());
            ps.setInt(6, masajista.getDni());
            ps.setString(7, masajista.getPuesto());
            ps.setString(8, masajista.getEspecialidad());
            ps.setBoolean(9, masajista.isEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista dado de alta con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo dar de alta al masajista.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MasajistaData.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo dar de alta al masajista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
//--------------------ACTUALIZAR MASAJISTA-----------------------------

    public void actualizarMasajista(Masajista a) {
        String query = "UPDATE Masajista SET matricula=?,  nombre=?, apellido=?, telefono=?, dni=?, puesto=?, especialidad=?, estado=? WHERE idEmpleado=?";
        //System.out.println("["  + a.getMatricula() + " ] [" + a.getNombre() + "] [" + a.getApellido() + "] [" + a.getTelefono() + "]["a.getDni() + "] [" + a.getPuesto() + "] [" + a.getEspecialidad() + "] [" + a.isEstado() + "] [" + a.getIdEmpleado() + "]");

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, a.getMatricula());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellido());
            ps.setString(4, a.getTelefono());
            ps.setInt(5, a.getDni());
            ps.setString(5, a.getPuesto());
            ps.setString(6, a.getEspecialidad());
            ps.setBoolean(7, a.isEstado());
            ps.setInt(8, a.getIdEmpleado());

            int aux = ps.executeUpdate();
            if (aux == 0) {
                JOptionPane.showMessageDialog(null, "El Masajista no se ha modificado.");
            } else {
                JOptionPane.showMessageDialog(null, "Masajista modificado exitosamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar al Masajista: " + ex.getMessage());
            Logger.getLogger(MasajistaData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ----------------- ELIMINAR MASAJISTA -----------------
     public void eliminarMasajista(int idEmpleado) {
        String sql = "DELETE FROM masajista WHERE idEmpleado = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró al masajista con ese código.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al masajista. " + ex.getMessage());
        }
    }
    // ----------------- LISTAR TODAS LAS MASAJISTAS -----------------
    public List<Masajista> listarMasajistas() {
        List<Masajista> masajista = new ArrayList<>();
        String sql = "SELECT * FROM masajista";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Masajista m = new Masajista(
                        rs.getInt("idEmpledo"),
                        rs.getInt("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getInt("dni"),
                        rs.getString("puesto"),
                        rs.getString("especialidad"),
                        rs.getBoolean("estado")
                );
                masajista.add(m);
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas. " + ex.getMessage());
        }

        return masajista;
    }

    //------------------BAJA LOGICA-----------------------
    public void bajaLogica(int idEmpleado) {
        String query = "UPDATE masajista SET estado=0 WHERE idEmpleado = ?";
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
            Logger.getLogger(MasajistaData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //-------------------------BUSCAR MASAJISTA POR DNI

    public Masajista buscarMasajistaPorDNI(int dni) {
        String sql = "SELECT * FROM masajista WHERE dni=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            Masajista masajista = null;

            while (rs.next()) {
                masajista = new Masajista(
                        rs.getInt("idEmpledo"),
                        rs.getInt("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getInt("dni"),
                        rs.getString("puesto"),
                        rs.getString("especialidad"),
                        rs.getBoolean("estado")
                );
            }

            ps.close();
            return masajista;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar al masajista. " + ex.getMessage());
            return null;
        }
    }
    
    //--------------BUSCAR MASAJISTA POR MATRICULA-----------
       public Masajista buscarMasajistaPorMatricula(int matricula) {
        String sql = "SELECT * FROM masajista WHERE matricula=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);
            ResultSet rs = ps.executeQuery();
            Masajista masajista = null;

            while (rs.next()) {
                masajista = new Masajista(
                        rs.getInt("idEmpledo"),
                        rs.getInt("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getInt("dni"),
                        rs.getString("puesto"),
                        rs.getString("especialidad"),
                        rs.getBoolean("estado")
                );
            }

            ps.close();
            return masajista;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar al masajista. " + ex.getMessage());
            return null;
        }
    }
}

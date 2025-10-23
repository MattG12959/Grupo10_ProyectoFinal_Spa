/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Empleado;
import entidades.Masajista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        // 1) Creo un empleado
        Empleado empleado = new Empleado();

        // 2) Le paso el dni de la masajista y el puesto
        empleado.setDni(masajista.getDni());
       // empleado.setPuesto(masajista.getPuesto());

        // 3) Creo una nueva conexion, ya que siempre va a tirar error por una imcompatiblidad entre miConexion y Connection
        miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
        conexion.buscarConexion();

        // 4) Llamo al metodo "AltaEmpleado"
        EmpleadoData empleadoData = new EmpleadoData(conexion);
        empleadoData.altaEmpleado(empleado);

        // 5) Inserto en la tabla MASAJISTAcon el idEmpleado ya generado, el DNI y el puesto
        String sql = "INSERT INTO `masajista` (´matricula', `nombre`, `apellido`, `telefono`, 'especialidad`, 'estado', `idEmpleado`, dni` ) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, masajista.getMatricula());
            ps.setString(2, masajista.getNombre());
            ps.setString(3, masajista.getApellido());
            ps.setString(4, masajista.getTelefono());
            ps.setString(5, masajista.getEspecialidad());
             ps.setInt(6, empleado.getIdEmpleado());
            ps.setInt(7, masajista.getDni());
          //  ps.setString(6, empleado.getPuesto());
            ps.setBoolean(8, masajista.isEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista dado de alta con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo dar de alta al masajista.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MasajistaData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

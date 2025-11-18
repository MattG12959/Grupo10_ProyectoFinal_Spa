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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia 
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
            // Primero, obtener el DNI actual del vendedor para comparar
            Vendedor vendedorActual = buscarVendedorPorId(a.getIdEmpleado());
            boolean dniCambio = vendedorActual != null && vendedorActual.getDni() != a.getDni();
            
            if (dniCambio) {
                // Si el DNI cambió, usar un valor temporal para evitar el conflicto de FK
                // 1. Deshabilitar temporalmente las verificaciones de FK
                PreparedStatement psDisableFK = con.prepareStatement("SET FOREIGN_KEY_CHECKS = 0");
                psDisableFK.execute();
                psDisableFK.close();
                
                try {
                    // 2. Actualizar primero el DNI en empleado
                    miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                    conexion.buscarConexion();
                    EmpleadoData empleadoData = new EmpleadoData(conexion);
                    empleadoData.actualizarDNIEmpleado(a.getIdEmpleado(), a.getDni());
                    
                    // 3. Ahora actualizar el vendedor con el nuevo DNI
                    PreparedStatement psVendedor = con.prepareStatement(query);
                    psVendedor.setString(1, a.getNombre());
                    psVendedor.setString(2, a.getApellido());
                    psVendedor.setString(3, a.getTelefono());
                    psVendedor.setInt(4, a.getDni());
                    psVendedor.setInt(5, a.getIdEmpleado());
                    int auxVendedor = psVendedor.executeUpdate();
                    psVendedor.close();
                    
                    if (auxVendedor == 0) {
                        throw new SQLException("El Vendedor no se ha modificado.");
                    }
                } finally {
                    // 4. Rehabilitar las verificaciones de FK
                    PreparedStatement psEnableFK = con.prepareStatement("SET FOREIGN_KEY_CHECKS = 1");
                    psEnableFK.execute();
                    psEnableFK.close();
                }
            } else {
                // Si el DNI no cambió, solo actualizar los demás campos
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getApellido());
                ps.setString(3, a.getTelefono());
                ps.setInt(4, a.getDni());
                ps.setInt(5, a.getIdEmpleado());
                int aux = ps.executeUpdate();
                ps.close();
                
                if (aux == 0) {
                    throw new SQLException("El Vendedor no se ha modificado.");
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar al Vendedor: " + ex.getMessage());
            Logger.getLogger(VendedorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ----------------- ELIMINAR VENDEDOR -----------------
    public void eliminarVendedor(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM vendedor WHERE idEmpleado = ?";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idEmpleado);
        int filas = ps.executeUpdate();
        ps.close();
        
        if (filas == 0) {
            throw new SQLException("No se encontró el vendedor con ese idEmpleado");
        }
    }
    
    // ----------------- INSERTAR VENDEDOR CON ID EMPLEADO EXISTENTE -----------------
    // Usado cuando se cambia de puesto de masajista a vendedor
    public void insertarVendedorConIdExistente(Vendedor vendedor) throws SQLException {
        String sql = "INSERT INTO vendedor (idEmpleado, nombre, apellido, telefono, dni, puesto, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, vendedor.getIdEmpleado());
        ps.setString(2, vendedor.getNombre());
        ps.setString(3, vendedor.getApellido());
        ps.setString(4, vendedor.getTelefono());
        ps.setInt(5, vendedor.getDni());
        ps.setString(6, vendedor.getPuesto());
        ps.setBoolean(7, vendedor.getEstado());
        
        int filas = ps.executeUpdate();
        ps.close();
        
        if (filas == 0) {
            throw new SQLException("No se pudo insertar el vendedor");
        }
    }
    
    //-------------BAJA LOGICA
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

    // ---------------------ALTA LOGICA
    public void altaLogica(int idEmpleado) {
        String query = "UPDATE vendedor SET estado=1 WHERE idEmpleado = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idEmpleado);
            int i = ps.executeUpdate();

            if (i == 1) {
                JOptionPane.showMessageDialog(null, "Empleado activado con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendedorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ----------------- LISTAR TODAS LAS VENDEDORAS -----------------
    public ArrayList<Vendedor> listarVendedores() {
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        String sql = "SELECT * FROM vendedor";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vendedor m = new Vendedor(
                        rs.getInt("idEmpleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getInt("dni"),
                        rs.getString("puesto"),
                        rs.getBoolean("estado")
                );
                vendedores.add(m);
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas. " + ex.getMessage());
        }

        return vendedores;
    }

    // Buscar vendedor por DNI
    public Vendedor buscarVendedorPorDNI(int dni) {
        String sql = "SELECT * FROM vendedor WHERE dni=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            Vendedor vendedor = null;
            while (rs.next()) {
                vendedor = new Vendedor(
                        rs.getInt("idEmpleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getInt("dni"),
                        rs.getString("puesto"),
                        rs.getBoolean("estado")
                );
            }
            ps.close();
            return vendedor;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar al vendedor. " + ex.getMessage());
            return null;
        }
    }

    // Buscar vendedor por ID
    public Vendedor buscarVendedorPorId(int idEmpleado) {
        String sql = "SELECT * FROM vendedor WHERE idEmpleado=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            Vendedor vendedor = null;
            while (rs.next()) {
                vendedor = new Vendedor(
                        rs.getInt("idEmpleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getInt("dni"),
                        rs.getString("puesto"),
                        rs.getBoolean("estado")
                );
            }
            ps.close();
            return vendedor;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar al vendedor. " + ex.getMessage());
            return null;
        }
    }

    // Buscar vendedor por matrícula (no tiene matrícula, así que devuelve null)
    public Vendedor buscarVendedorPorMatricula(int matricula) {
        // Los vendedores no tienen matrícula, así que siempre retorna null
        return null;
    }
}

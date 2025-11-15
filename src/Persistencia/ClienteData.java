/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import entidades.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteData {

    private Connection con = null;

    public ClienteData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    // Guardar cliente
    public void guardarCliente(Cliente c) {
        String sql = "INSERT INTO cliente (dni, nombre, apellido, teléfono, edad, afecciones, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setString(4, c.getTelefono());
            ps.setInt(5, c.getEdad());
            ps.setString(6, c.getAfecciones());
            ps.setBoolean(7, c.isEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setCodCli(rs.getInt(1));
            }

            ps.close();
            JOptionPane.showMessageDialog(null, "Cliente guardado correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar cliente. " + ex.getMessage());
        }
    }

    // Buscar cliente por ID
    public Cliente buscarCliente(int codCli) {
        String sql = "SELECT * FROM cliente WHERE codCli = ?";
        Cliente c = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codCli);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente();
                c.setCodCli(rs.getInt("codCli"));
                c.setDni(rs.getInt("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setTelefono(rs.getString("teléfono"));
                c.setEdad(rs.getInt("edad"));
                c.setAfecciones(rs.getString("afecciones"));
                c.setEstado(rs.getBoolean("estado"));
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente. " + ex.getMessage());
        }
        return c;
    }

    // Modificar cliente
    public void modificarCliente(Cliente c) {
        String sql = "UPDATE cliente SET dni = ?, nombre = ?, apellido = ?, teléfono = ?, edad = ?, afecciones = ?, estado = ? WHERE codCli = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setString(4, c.getTelefono());
            ps.setInt(5, c.getEdad());
            ps.setString(6, c.getAfecciones());
            ps.setBoolean(7, c.isEstado());
            ps.setInt(8, c.getCodCli());

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cliente modificado correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente. " + ex.getMessage());
        }
    }

    // Eliminar cliente
    public void eliminarCliente(int codCli) {
        String sql = "DELETE FROM cliente WHERE codCli = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codCli);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente. " + ex.getMessage());
        }
    }

    // Listar clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE 1=1";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCodCli(rs.getInt("codCli"));
                c.setDni(rs.getInt("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setTelefono(rs.getString("teléfono"));
                c.setEdad(rs.getInt("edad"));
                c.setAfecciones(rs.getString("afecciones"));
                c.setEstado(rs.getBoolean("estado"));
                clientes.add(c);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes. " + ex.getMessage());
        }
        return clientes;
    }
    
    // Listar Clientes Activos
    public List<Cliente> listarClientesActivos(boolean activo, boolean inactivo){
        List<Cliente> clientesActivos = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        if(activo){
            sql += " AND estado = true";
        }
        if(inactivo){
            sql += " AND estado = false";
        }
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCodCli(rs.getInt("codCli"));
                c.setDni(rs.getInt("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setTelefono(rs.getString("teléfono"));
                c.setEdad(rs.getInt("edad"));
                c.setAfecciones(rs.getString("afecciones"));
                c.setEstado(rs.getBoolean("estado"));
                clientesActivos.add(c);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes. " + ex.getMessage());
        }
        return clientesActivos;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import entidades.DiaDeSpa;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
import Persistencia.miConexion;

/**
 *
 * @author usuario
 */
public class DiaDeSpaData {
    private Connection con = null;

    public DiaDeSpaData (miConexion conexion) {
        this.con = conexion.buscarConexion();
    }
    
    public void agregarDiadespa(DiaDeSpa diaSpa) {
        String sql = "INSERT INTO dia_de_spa (fecha_y_hora, preferencias, " +
                     "idCliente, monto, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(diaSpa.getFechayhora()));
            ps.setString(2, diaSpa.getPreferencias());
            ps.setInt(3, diaSpa.getCliente().getCodCli());
            ps.setDouble(4, diaSpa.getMonto());
            ps.setBoolean(5, diaSpa.isEstado());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                diaSpa.setCodPack(rs.getInt(1));
            }
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Día de spa registrado con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la tabla dia_de_spa", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
     public void actualizarDiadespa(DiaDeSpa diaSpa) {
        String sql = "UPDATE dia_de_spa SET fecha_y_hora = ?, preferencias = ?, " +
                     "idCliente = ?, monto = ?, estado = ? WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(diaSpa.getFechayhora()));
            ps.setString(2, diaSpa.getPreferencias());
            ps.setInt(3, diaSpa.getCliente().getCodCli());
            ps.setDouble(4, diaSpa.getMonto());
            ps.setBoolean(5, diaSpa.isEstado());
            ps.setInt(6, diaSpa.getCodPack());
            
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Día de spa actualizado con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el día de spa", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
    public void eliminarDiadespa(int codPack) {
        String sql = "UPDATE dia_de_spa SET estado = 0 WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Día de spa eliminado con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el día de spa", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    public DiaDeSpa buscarPorCodigo(int codPack) {
        String sql = "SELECT * FROM dia_de_spa WHERE codPack = ?";
        DiaDeSpa diaSpa = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                diaSpa = new DiaDeSpa();
                diaSpa.setCodPack(rs.getInt("codPack"));
                diaSpa.setFechayhora(rs.getTimestamp("fecha_y_hora").toLocalDateTime());
                diaSpa.setPreferencias(rs.getString("preferencias"));
                diaSpa.setMonto(rs.getDouble("monto"));
                diaSpa.setEstado(rs.getBoolean("estado"));
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el día de spa", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return diaSpa;
    }

    
    
    public ArrayList<DiaDeSpa> listarPorCliente(int idCliente) {
        ArrayList<DiaDeSpa> lista = new ArrayList<>();
        String sql = "SELECT * FROM dia_de_spa WHERE idCliente = ? AND estado = 1";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                DiaDeSpa diaSpa = new DiaDeSpa();
                diaSpa.setCodPack(rs.getInt("codPack"));
                diaSpa.setFechayhora(rs.getTimestamp("fecha_y_hora").toLocalDateTime());
                diaSpa.setPreferencias(rs.getString("preferencias"));
                diaSpa.setMonto(rs.getDouble("monto"));
                diaSpa.setEstado(rs.getBoolean("estado"));
                
                lista.add(diaSpa);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo listar los días de spa del cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    
    public ArrayList<DiaDeSpa> listarActivos() {
       ArrayList<DiaDeSpa> lista = new ArrayList<>();
        String sql = "SELECT * FROM dia_de_spa WHERE estado = 1";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                DiaDeSpa diaSpa = new DiaDeSpa();
                diaSpa.setCodPack(rs.getInt("codPack"));
                diaSpa.setFechayhora(rs.getTimestamp("fecha_y_hora").toLocalDateTime());
                diaSpa.setPreferencias(rs.getString("preferencias"));
                diaSpa.setMonto(rs.getDouble("monto"));
                diaSpa.setEstado(rs.getBoolean("estado"));
                
                lista.add(diaSpa);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la tabla dia_de_spa", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    public void activar(int codPack) {
        String sql = "UPDATE dia_de_spa SET estado = 1 WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Día de spa activado con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo activar el día de spa", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
      
    public void desactivar(int codPack) {
        eliminarDiadespa(codPack);
    }
   
    
    
       
    
}

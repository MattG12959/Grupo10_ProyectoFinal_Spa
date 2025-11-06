/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
public class ProductoData {
    
    private Connection con = null;

    public ProductoData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }
    
    //------------ Buscar en la base de datos el Producto por ID ------------
    public Producto buscarProducto(int idProducto){
        String query = "SELECT * FROM `producto` WHERE `idProdcuto` = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,idProducto);
            ResultSet resultado = ps.executeQuery();
            Producto producto = null;
            
            while(resultado.next()){
                producto = new Producto (
                resultado.getInt("idProducto"),
                resultado.getString("nombre"),
                resultado.getString("fabricante"),
                resultado.getString("detalle"),
                resultado.getDouble("precio"),
                resultado.getInt("stock"),
                resultado.getBoolean("vegano"),
                resultado.getBoolean("sinTacc"));
            }
            return producto;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Producto en la base de datos." + ex.getMessage());
        }
        return null;
    }
    
    //------------ Guardar Producto ------------    
    public void guardarProducto(Producto producto){
        
        String query = "INSERT INTO producto(nombre, fabricante, detalle, precio, "
                + "stock, vegano, sinTacc) VALUES (?,?,?,?,?,?,?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,producto.getNombre());
            ps.setString(2,producto.getFabricante());
            ps.setString(3, producto.getDetalle());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5,producto.getStock());
            ps.setBoolean(6, producto.isVegano());
            ps.setBoolean(7, producto.isSinTacc());
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){
                producto.setIdProducto(1);                
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de Producto");
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar el Producto en la base de datos." + ex.getMessage());
        }
    }
    
}

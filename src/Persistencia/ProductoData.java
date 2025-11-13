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
import java.util.ArrayList;
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
        String query = "SELECT * FROM `producto` WHERE `idProducto` = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(query);
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
            ps.close();
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
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){
                producto.setIdProducto(rs.getInt(1));                
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de Producto");
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar el Producto en la base de datos." + ex.getMessage());
        }
    }
        //------------ Actualizar Producto ------------
    public void actualizarProducto(Producto producto){
        String query = "UPDATE producto SET nombre = ?, fabricante = ?, detalle = ?, "
                + "precio = ?, stock = ?, vegano = ?, sinTacc = ? WHERE idProducto = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getFabricante());
            ps.setString(3, producto.getDetalle());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setBoolean(6, producto.isVegano());
            ps.setBoolean(7, producto.isSinTacc());
            ps.setInt(8, producto.getIdProducto());
            
            int filas = ps.executeUpdate();
            if(filas > 0){
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto con ese ID.");
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al actualizar el Producto en la base de datos." + ex.getMessage());
        }
    }
    
    //------------ Eliminar Producto ------------
    public void eliminarProducto(int idProducto){
        String query = "DELETE FROM producto WHERE idProducto = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            
            int filas = ps.executeUpdate();
            if(filas > 0){
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto con ese ID.");
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al eliminar el Producto de la base de datos." + ex.getMessage());
        }
    }
    
    //------------ Listar todos los Productos ------------
    public ArrayList<Producto> listarTodosProductos(){
        ArrayList<Producto> lista = new ArrayList<>();
        String query = "SELECT * FROM producto";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultado = ps.executeQuery();
            
            while(resultado.next()){
                Producto producto = new Producto(
                    resultado.getInt("idProducto"),
                    resultado.getString("nombre"),
                    resultado.getString("fabricante"),
                    resultado.getString("detalle"),
                    resultado.getDouble("precio"),
                    resultado.getInt("stock"),
                    resultado.getBoolean("vegano"),
                    resultado.getBoolean("sinTacc")
                );
                lista.add(producto);
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al listar los Productos." + ex.getMessage());
        }
        return lista;
    }
    
    //------------ Listar Productos por Filtro ------------
    public ArrayList<Producto> listarProductosPorFiltro(boolean vegano, boolean sinTacc){
        ArrayList<Producto> lista = new ArrayList<>();
        String query = "SELECT * FROM producto WHERE 1=1";
        
        // Construir la consulta según los filtros
        if(vegano){
            query += " AND vegano = true";
        }
        if(sinTacc){
            query += " AND sinTacc = true";
        }
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultado = ps.executeQuery();
            
            while(resultado.next()){
                Producto producto = new Producto(
                    resultado.getInt("idProducto"),
                    resultado.getString("nombre"),
                    resultado.getString("fabricante"),
                    resultado.getString("detalle"),
                    resultado.getDouble("precio"),
                    resultado.getInt("stock"),
                    resultado.getBoolean("vegano"),
                    resultado.getBoolean("sinTacc")
                );
                lista.add(producto);
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al filtrar los Productos." + ex.getMessage());
        }
        return lista;
    }
    
    //------------ Buscar Productos por ID o Nombre ------------
    public ArrayList<Producto> buscarProductosPorIdONombre(String busqueda, boolean filtroVegano, boolean filtroSinTacc){
        ArrayList<Producto> lista = new ArrayList<>();
        String query = "SELECT * FROM producto WHERE 1=1";
        
        // Determinar si la búsqueda es por ID o nombre
        boolean esNumero = false;
        int idBusqueda = -1;
        try {
            idBusqueda = Integer.parseInt(busqueda.trim());
            esNumero = true;
        } catch (NumberFormatException e) {
            esNumero = false;
        }
        
        // Agregar condición de búsqueda
        if (esNumero) {
            query += " AND idProducto = ?";
        } else {
            query += " AND nombre LIKE ?";
        }
        
        // Agregar filtros
        if(filtroVegano){
            query += " AND vegano = true";
        }
        if(filtroSinTacc){
            query += " AND sinTacc = true";
        }
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            if (esNumero) {
                ps.setInt(1, idBusqueda);
            } else {
                ps.setString(1, "%" + busqueda.trim() + "%");
            }
            
            ResultSet resultado = ps.executeQuery();
            
            while(resultado.next()){
                Producto producto = new Producto(
                    resultado.getInt("idProducto"),
                    resultado.getString("nombre"),
                    resultado.getString("fabricante"),
                    resultado.getString("detalle"),
                    resultado.getDouble("precio"),
                    resultado.getInt("stock"),
                    resultado.getBoolean("vegano"),
                    resultado.getBoolean("sinTacc")
                );
                lista.add(producto);
            }
            ps.close();
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al buscar los Productos." + ex.getMessage());
        }
        return lista;
    }
    
    //------------ Actualizar Stock del Producto ------------
    public void actualizarStock(int idProducto, int cantidadVendida) throws SQLException{
        String query = "UPDATE producto SET stock = stock - ? WHERE idProducto = ? AND stock >= ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidadVendida);
            
            int filas = ps.executeUpdate();
            if(filas == 0){
                throw new SQLException("No se pudo actualizar el stock. Verifique que haya suficiente stock disponible.");
            }
            ps.close();
            
        }catch (SQLException ex){
            throw new SQLException("Error al actualizar el stock del producto: " + ex.getMessage());
        }
    }
    
}

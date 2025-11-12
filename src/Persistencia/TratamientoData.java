/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.sql.Connection;
import entidades.Tratamiento;
import constantes.TratamientosCorporales;
import constantes.TratamientosFaciales;
import constantes.Especialidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.*;
import entidades.Producto;

/**
 *
 * @author thyetix
 */
public class TratamientoData {

    private Connection con = null;

    public TratamientoData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    public void cargaTratamiento(Tratamiento tratamiento) {

        String sql = "INSERT INTO tratamiento (nombre, tipoTratamiento, detalle, duracion, costo, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String tipoTratamientoClave = tratamiento.gettipoTratamiento();
            String nombreDeEnum = obtenerNombreTratamiento(tipoTratamientoClave);

            if (nombreDeEnum == null) {
                nombreDeEnum = tratamiento.getNombre();
            }

            String especialidad = obtenerEspecialidadPorNombre(nombreDeEnum);

            if (especialidad == null) {
                especialidad = "Desconocida";
            }

            ps.setString(1, nombreDeEnum);
            ps.setString(2, especialidad);

            String detalle = tratamiento.getDetalle();

            if (detalle == null || detalle.isEmpty()) {
                detalle = obtenerDescripcionTratamiento(tipoTratamientoClave);
            }

            ps.setString(3, detalle);
            ps.setTime(4, Time.valueOf(tratamiento.getDuracion()));
            ps.setDouble(5, tratamiento.getCosto());
            ps.setBoolean(6, tratamiento.isEstado());

            ps.executeUpdate();

            // --- Recuperar ID autogenerado y asignarlo al objeto tratamiento ---
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tratamiento.setCodTratam(rs.getInt(1));
            }
            rs.close();

            // --- Ahora que ya tenemos el id, insertar productos relacionados (si los hay) ---
            if (tratamiento.getProducto() != null && !tratamiento.getProducto().isEmpty()) {
                insertarProductosTratamiento(tratamiento.getCodTratam(), tratamiento.getProducto());
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar tratamiento: " + ex.getMessage());
        }
    }

    //trae los mobres de los tratamientos 
    public String obtenerNombreTratamiento(String tipoTratamiento) {
        for (TratamientosFaciales tf : TratamientosFaciales.values()) {
            if (tf.getNombre().equalsIgnoreCase(tipoTratamiento)) {
                return tf.getNombre();
            }
        }
        for (TratamientosCorporales tc : TratamientosCorporales.values()) {
            if (tc.getNombre().equalsIgnoreCase(tipoTratamiento)) {
                return tc.getNombre();
            }
        }
        return null;
    }

    //trae las especialidades
    public String obtenerEspecialidadPorNombre(String nombreTratamiento) {
        for (TratamientosFaciales tf : TratamientosFaciales.values()) {
            if (tf.getNombre().equalsIgnoreCase(nombreTratamiento)) {
                // Asume que si está en TratamientosFaciales, la especialidad es FACIAL
                return Especialidades.FACIAL.getEspecialidad();
            }
        }

        for (TratamientosCorporales tc : TratamientosCorporales.values()) {
            if (tc.getNombre().equalsIgnoreCase(nombreTratamiento)) {
                // Asume que si está en TratamientosCorporales, la especialidad es CORPORAL
                return Especialidades.CORPORAL.getEspecialidad();
            }
        }

        return null; // Si el nombre no coincide con ningún tratamiento definido
    }

    //---------Métodos aux para cargar y eliminar productos de los tratamientos--------
    private void insertarProductosTratamiento(int codTratam, ArrayList<Producto> productos) {
        String sql = "INSERT INTO tratamiento_producto (codTratamiento, idProducto, cantidad) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            for (Producto prod : productos) {
                ps.setInt(1, codTratam);
                ps.setInt(2, prod.getIdProducto());
                ps.setInt(3, 1); // Cantidad por defecto = 1
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar productos del tratamiento: " + ex.getMessage());
        }
    }

    private void eliminarProductosTratamiento(int codTratam) {
        String sql = "DELETE FROM tratamiento_producto WHERE codTratamiento = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codTratam);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar productos del tratamiento: " + ex.getMessage());
        }
    }

    public void actualizarTratamiento(Tratamiento tratamiento) {
        String sql = "UPDATE tratamiento SET nombre = ?, tipoTratamiento = ?, detalle = ?, duracion = ?, costo = ?, estado = ? WHERE codTratamiento = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            // Obtener nombre del tratamiento desde el enum
            String tipoTratamientoClave = tratamiento.gettipoTratamiento();
            String nombreDeEnum = obtenerNombreTratamiento(tipoTratamientoClave);
            if (nombreDeEnum == null) {
                nombreDeEnum = tratamiento.getNombre();
            }

            // Obtener especialidad (Facial/Corporal) desde el enum
            String especialidad = obtenerEspecialidadPorNombre(nombreDeEnum);
            if (especialidad == null) {
                especialidad = "Desconocida";
            }

            ps.setString(1, nombreDeEnum);
            ps.setString(2, especialidad);

            // Obtener descripción automática del enum si el detalle está vacío
            String detalle = tratamiento.getDetalle();
            if (detalle == null || detalle.isEmpty()) {
                detalle = obtenerDescripcionTratamiento(tipoTratamientoClave);
            }
            ps.setString(3, detalle);
            ps.setTime(4, Time.valueOf(tratamiento.getDuracion()));
            ps.setDouble(5, tratamiento.getCosto());
            ps.setBoolean(6, tratamiento.isEstado());
            ps.setInt(7, tratamiento.getCodTratam());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                // Actualizar productos asociados
                eliminarProductosTratamiento(tratamiento.getCodTratam());
                if (tratamiento.getProducto() != null && !tratamiento.getProducto().isEmpty()) {
                    insertarProductosTratamiento(tratamiento.getCodTratam(), tratamiento.getProducto());
                }
                JOptionPane.showMessageDialog(null, "Tratamiento actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el tratamiento a actualizar");
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tratamiento: " + ex.getMessage());
        }
    }

    // Método para listar todos los tratamientos 
    public ArrayList<Tratamiento> listarTodosTratamientos() {
        ArrayList<Tratamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setCodTratam(rs.getInt("codTratamiento"));
                tratamiento.setNombre(rs.getString("nombre"));
                tratamiento.settipoTratamiento(rs.getString("tipoTratamiento")); // CORREGIDO
                tratamiento.setDetalle(rs.getString("detalle"));
                tratamiento.setDuracion(rs.getTime("duracion").toLocalTime());
                tratamiento.setCosto(rs.getDouble("costo"));
                tratamiento.setEstado(rs.getBoolean("estado"));

                lista.add(tratamiento);
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar tratamientos: " + ex.getMessage());
        }

        return lista;
    }

    // Obtener descripción de cualquier tipo de tratamiento
    public String obtenerDescripcionTratamiento(String nombre) {
        // Buscar en tratamientos faciales
        for (TratamientosFaciales tf : TratamientosFaciales.values()) {
            if (tf.getNombre().equals(nombre)) {
                return tf.getDescripcion();
            }
        }
        // Buscar en tratamientos corporales
        for (TratamientosCorporales tc : TratamientosCorporales.values()) {
            if (tc.getNombre().equals(nombre)) {
                return tc.getDescripcion();
            }
        }
        return null;
    }

    public Tratamiento buscarTratamiento(int idTratamiento) {
        String sql = "SELECT * FROM tratamiento WHERE codTratamiento = ?";
        Tratamiento t = null;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idTratamiento);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                t = new Tratamiento();
                t.setCodTratam(rs.getInt("codTratamiento"));
                t.setNombre(rs.getString("nombre"));
                t.settipoTratamiento(rs.getString("tipoTratamiento"));
                t.setDetalle(rs.getString("detalle"));
                t.setDuracion(rs.getTime("duracion").toLocalTime());
                t.setCosto(rs.getDouble("costo"));
                t.setEstado(rs.getBoolean("estado"));
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar tratamiento. " + ex.getMessage());
        }

        return t;
    }
}

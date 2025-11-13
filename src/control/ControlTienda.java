/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.ProductoData;
import entidades.Producto;
import vistas.vistaTienda;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Clase control para administrar las operaciones CRUD de vistaTienda
 * @author Grupo10
 */
public class ControlTienda {
    
    private vistaTienda vista;
    private ProductoData productoData;
    private Producto productoSeleccionado;
    private boolean cargandoDesdeTabla = false;
    
    public ControlTienda(vistaTienda vista, ProductoData productoData) {
        this.vista = vista;
        this.productoData = productoData;
        this.productoSeleccionado = null;
        inicializarVista();
    }
    
    /**
     * Inicializa la vista con los datos necesarios
     */
    private void inicializarVista() {
        cargarTabla();
        // Configurar listener para la tabla (este se configura aquí porque es específico del control)
        configurarListenerTabla();
    }
    
    /**
     * Configura el listener de selección en la tabla
     */
    private void configurarListenerTabla() {
        // Listener para selección en la tabla
        vista.getJtTienda().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !cargandoDesdeTabla) {
                cargarProductoDesdeTabla();
            }
        });
    }
    
    /**
     * Guarda o actualiza un producto según si tiene ID o no
     */
    public void guardarOActualizarProducto() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            String nombre = vista.getJtNombre().getText().trim();
            String fabricante = vista.getJtFabricante().getText().trim();
            String detalle = vista.getJtDetalle().getText().trim();
            double precio = Double.parseDouble(vista.getJtPrecio().getText().trim());
            int stock = Integer.parseInt(vista.getStock().getText().trim());
            boolean vegano = vista.getJcbProdVegano().isSelected();
            boolean sinTacc = vista.getJcbProdSinTacc().isSelected();
            
            if (productoSeleccionado != null && productoSeleccionado.getIdProducto() != -1) {
                // Actualizar producto existente
                productoSeleccionado.setNombre(nombre);
                productoSeleccionado.setFabricante(fabricante);
                productoSeleccionado.setDetalle(detalle);
                productoSeleccionado.setPrecio(precio);
                productoSeleccionado.setStock(stock);
                productoSeleccionado.setVegano(vegano);
                productoSeleccionado.setSinTacc(sinTacc);
                
                productoData.actualizarProducto(productoSeleccionado);
            } else {
                // Crear nuevo producto
                Producto nuevoProducto = new Producto(nombre, fabricante, detalle, precio, stock, vegano, sinTacc);
                productoData.guardarProducto(nuevoProducto);
                JOptionPane.showMessageDialog(null, "Producto guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            limpiarCampos();
            cargarTabla();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El precio y el stock deben ser valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Busca un producto por ID
     */
    public void buscarProductoPorId() {
        try {
            String idStr = vista.getJTextField1().getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID de producto para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idProducto = Integer.parseInt(idStr);
            Producto producto = productoData.buscarProducto(idProducto);
            
            if (producto != null) {
                cargandoDesdeTabla = true;
                vista.getJtNombre().setText(producto.getNombre());
                vista.getJtFabricante().setText(producto.getFabricante());
                vista.getJtDetalle().setText(producto.getDetalle());
                vista.getJtPrecio().setText(String.valueOf(producto.getPrecio()));
                vista.getStock().setText(String.valueOf(producto.getStock()));
                vista.getJcbProdVegano().setSelected(producto.isVegano());
                vista.getJcbProdSinTacc().setSelected(producto.isSinTacc());
                productoSeleccionado = producto;
                cargandoDesdeTabla = false;
                
                // Seleccionar en la tabla si existe
                seleccionarEnTabla(idProducto);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID: " + idProducto, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carga los datos del producto seleccionado en la tabla a los campos del formulario
     */
    private void cargarProductoDesdeTabla() {
        int filaSeleccionada = vista.getJtTienda().getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }
        cargandoDesdeTabla = true;
        
        try {
            DefaultTableModel modelo = vista.getModelo();
            int idProducto = (Integer) modelo.getValueAt(filaSeleccionada, 0);
            
            productoSeleccionado = productoData.buscarProducto(idProducto);
            if (productoSeleccionado != null) {
                vista.getJtNombre().setText(productoSeleccionado.getNombre());
                vista.getJtFabricante().setText(productoSeleccionado.getFabricante());
                vista.getJtDetalle().setText(productoSeleccionado.getDetalle());
                vista.getJtPrecio().setText(String.valueOf(productoSeleccionado.getPrecio()));
                vista.getStock().setText(String.valueOf(productoSeleccionado.getStock()));
                vista.getJcbProdVegano().setSelected(productoSeleccionado.isVegano());
                vista.getJcbProdSinTacc().setSelected(productoSeleccionado.isSinTacc());
                vista.getJTextField1().setText(String.valueOf(productoSeleccionado.getIdProducto()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cargandoDesdeTabla = false;
        }
    }
    
    /**
     * Carga todos los productos en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = vista.getModelo();
        modelo.setRowCount(0); // Limpiar la tabla
        
        ArrayList<Producto> productos = productoData.listarTodosProductos();
        
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getFabricante(),
                p.getDetalle(),
                p.getPrecio(),
                p.getStock(),
                p.isVegano() ? "Sí" : "No",
                p.isSinTacc() ? "Sí" : "No"
            });
        }
    }
    
    /**
     * Aplica los filtros seleccionados a la tabla
     */
    public void aplicarFiltros() {
        DefaultTableModel modelo = vista.getModelo();
        modelo.setRowCount(0); // Limpiar la tabla
        
        boolean filtroVegano = vista.getJcbVegano1().isSelected();
        boolean filtroSinTacc = vista.getJcbSinT1().isSelected();
        
        ArrayList<Producto> productos;
        
        if (filtroVegano || filtroSinTacc) {
            productos = productoData.listarProductosPorFiltro(filtroVegano, filtroSinTacc);
        } else {
            productos = productoData.listarTodosProductos();
        }
        
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getFabricante(),
                p.getDetalle(),
                p.getPrecio(),
                p.getStock(),
                p.isVegano() ? "Sí" : "No",
                p.isSinTacc() ? "Sí" : "No"
            });
        }
    }
    
    /**
     * Elimina un producto de la base de datos
     */
    public void eliminarProducto() {
        // Si no hay producto seleccionado, intentar obtenerlo de la tabla seleccionada
        if (productoSeleccionado == null || productoSeleccionado.getIdProducto() == -1) {
            int filaSeleccionada = vista.getJtTienda().getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            DefaultTableModel modelo = vista.getModelo();
            int idProducto = (Integer) modelo.getValueAt(filaSeleccionada, 0);
            productoSeleccionado = productoData.buscarProducto(idProducto);
            
            if (productoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el producto seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Está seguro de que desea eliminar el producto: " + productoSeleccionado.getNombre() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            productoData.eliminarProducto(productoSeleccionado.getIdProducto());
            limpiarCampos();
            cargarTabla();
        }
    }
    
    /**
     * Limpia completamente la tabla (solo visualmente, sin eliminar datos de la BD)
     */
    public void limpiarTabla() {
        DefaultTableModel modelo = vista.getModelo();
        modelo.setRowCount(0);
        limpiarCampos();
    }
    
    /**
     * Selecciona una fila en la tabla según el ID del producto
     */
    private void seleccionarEnTabla(int idProducto) {
        DefaultTableModel modelo = vista.getModelo();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(idProducto)) {
                vista.getJtTienda().setRowSelectionInterval(i, i);
                vista.getJtTienda().scrollRectToVisible(vista.getJtTienda().getCellRect(i, 0, true));
                return;
            }
        }
    }
    
    /**
     * Limpia los campos del formulario
     */
    public void limpiarCampos() {
        vista.getJtNombre().setText("");
        vista.getJtFabricante().setText("");
        vista.getJtDetalle().setText("");
        vista.getJtPrecio().setText("");
        vista.getStock().setText("");
        vista.getJcbProdVegano().setSelected(false);
        vista.getJcbProdSinTacc().setSelected(false);
        vista.getJTextField1().setText("");
        productoSeleccionado = null;
        vista.getJtTienda().clearSelection();
    }
    
    /**
     * Valida que los campos requeridos estén completos
     */
    private boolean validarCampos() {
        String nombre = vista.getJtNombre().getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String fabricante = vista.getJtFabricante().getText().trim();
        if (fabricante.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El fabricante no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String detalle = vista.getJtDetalle().getText().trim();
        if (detalle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El detalle no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String precioStr = vista.getJtPrecio().getText().trim();
        if (precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El precio no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                JOptionPane.showMessageDialog(null, "El precio debe ser un valor positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String stockStr = vista.getStock().getText().trim();
        if (stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El stock no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(null, "El stock debe ser un valor positivo o cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El stock debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
}

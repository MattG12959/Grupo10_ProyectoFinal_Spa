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
 *
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

                // Verificar si hubo cambios en alguno de los 7 campos
                boolean hayCambios = false;

                if (!productoSeleccionado.getNombre().equals(nombre)
                        || !productoSeleccionado.getFabricante().equals(fabricante)
                        || !productoSeleccionado.getDetalle().equals(detalle)
                        || productoSeleccionado.getPrecio() != precio
                        || productoSeleccionado.getStock() != stock
                        || productoSeleccionado.isVegano() != vegano
                        || productoSeleccionado.isSinTacc() != sinTacc) {
                    hayCambios = true;
                }

                if (!hayCambios) {
                    JOptionPane.showMessageDialog(null,
                            "Debe modificar algún valor para actualizar el producto.\n"
                            + "Los campos a modificar son: Nombre, Fabricante, Detalle, Precio, Stock, Producto Vegano, Sin TACC.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

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
     * Busca productos por ID o nombre y los muestra en la tabla
     */
    public void buscarProductoPorId() {
        try {
            String busqueda = vista.getJTextField1().getText().trim();
            if (busqueda.isEmpty()) {
                // Si está vacío, cargar todos los productos
                aplicarFiltros();
                return;
            }

            boolean filtroVegano = vista.getJcbVegano1().isSelected();
            boolean filtroSinTacc = vista.getJcbSinT1().isSelected();

            ArrayList<Producto> productos = productoData.buscarProductosPorIdONombre(busqueda, filtroVegano, filtroSinTacc);

            DefaultTableModel modelo = vista.getModelo();
            modelo.setRowCount(0); // Limpiar la tabla

            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron productos con la búsqueda: " + busqueda, "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga los datos del producto seleccionado en la tabla a los campos del
     * formulario
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
     * Limpia completamente la tabla (solo visualmente, sin eliminar datos de la
     * BD)
     */
    public void limpiarTabla() {
        DefaultTableModel modelo = vista.getModelo();
        modelo.setRowCount(0);
        limpiarCampos();
    }

    /**
     * Agrega el producto seleccionado en jtTienda a la tabla de ventas
     */
    public void agregarProductoAVentas() {
        int filaSeleccionada = vista.getJtTienda().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla para agregar a ventas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DefaultTableModel modelo = vista.getModelo();
            int idProducto = (Integer) modelo.getValueAt(filaSeleccionada, 0);
            int stockDisponible = (Integer) modelo.getValueAt(filaSeleccionada, 5);

            // Verificar si el producto ya está en la tabla de ventas
            DefaultTableModel modeloVentas = vista.getModeloVentas();
            for (int i = 0; i < modeloVentas.getRowCount(); i++) {
                if (modeloVentas.getValueAt(i, 0).equals(idProducto)) {
                    // Si ya existe, incrementar la cantidad
                    int cantidadActual = (Integer) modeloVentas.getValueAt(i, 5);
                    if (cantidadActual + 1 > stockDisponible) {
                        JOptionPane.showMessageDialog(null, "No hay suficiente stock disponible. Stock disponible: " + stockDisponible, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    modeloVentas.setValueAt(cantidadActual + 1, i, 5);
                    return;
                }
            }

            // Si no existe, agregarlo con cantidad 1
            if (stockDisponible <= 0) {
                JOptionPane.showMessageDialog(null, "No hay stock disponible para este producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            modeloVentas.addRow(new Object[]{
                modelo.getValueAt(filaSeleccionada, 0), // ID
                modelo.getValueAt(filaSeleccionada, 1), // Nombre
                modelo.getValueAt(filaSeleccionada, 2), // Fabricante
                modelo.getValueAt(filaSeleccionada, 3), // Detalle
                modelo.getValueAt(filaSeleccionada, 4), // Precio
                1, // Cantidad inicial
                modelo.getValueAt(filaSeleccionada, 6), // Vegano
                modelo.getValueAt(filaSeleccionada, 7) // Sin TACC
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto a ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el artículo seleccionado de la tabla de ventas
     */
    public void eliminarArticuloDeVentas() {
        int filaSeleccionada = vista.getJtVentas().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un artículo de la tabla de ventas para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modeloVentas = vista.getModeloVentas();
        modeloVentas.removeRow(filaSeleccionada);
    }

    /**
     * Limpia la tabla de ventas
     */
    public void limpiarTablaVentas() {
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de que desea limpiar la tabla de ventas?",
                "Confirmar limpieza",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultTableModel modeloVentas = vista.getModeloVentas();
            modeloVentas.setRowCount(0);
        }
    }

    /**
     * Confirma la venta, actualiza el stock en la base de datos y muestra
     * mensaje de éxito
     */
    public void confirmarVenta() {
        DefaultTableModel modeloVentas = vista.getModeloVentas();

        if (modeloVentas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la lista de ventas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Confirma la venta de " + modeloVentas.getRowCount() + " producto(s)?",
                "Confirmar Venta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Actualizar el stock de cada producto vendido
            for (int i = 0; i < modeloVentas.getRowCount(); i++) {
                int idProducto = (Integer) modeloVentas.getValueAt(i, 0);
                int cantidad = (Integer) modeloVentas.getValueAt(i, 5);

                productoData.actualizarStock(idProducto, cantidad);
            }

            // Limpiar la tabla de ventas
            modeloVentas.setRowCount(0);

            // Recargar la tabla de productos para reflejar los cambios de stock
            cargarTabla();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Venta realizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al confirmar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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

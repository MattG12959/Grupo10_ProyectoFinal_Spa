/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.InstalacionData;
import entidades.Instalacion;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.vistaInstalaciones;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public class ControlInstalaciones {

    private vistaInstalaciones vistaInstalacion;
    private InstalacionData instalacionData;

    public ControlInstalaciones(vistaInstalaciones vistaInstalacion, InstalacionData instalacionData) {
        this.vistaInstalacion = vistaInstalacion;
        this.instalacionData = instalacionData;
    }

    //--------- Llenar la tabla con las instalaciones que estan en la base de datos ---------
    public void listarInstalaciones() {
        ArrayList<Instalacion> listaInstalaciones = instalacionData.listarInstalaciones();
        ArrayList<Instalacion> filtrados = new ArrayList<>();

        //--------- Listar Clientes Activos ---------
        if (vistaInstalacion.getJrbActivo() == true && vistaInstalacion.getJrbInactivo() == false) {
            for (Instalacion insta : listaInstalaciones) {
                if (insta.isEstado() == true) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes Inactivos ---------
        if (vistaInstalacion.getJrbActivo() == false && vistaInstalacion.getJrbInactivo() == true) {
            for (Instalacion insta : listaInstalaciones) {
                if (insta.isEstado() == false) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes sin filtros ---------
        if (vistaInstalacion.getJrbActivo() == false && vistaInstalacion.getJrbInactivo() == false) {
            crearTablaFiltrada(listaInstalaciones);
        }
        
    }

    //--------- Cargar Instalaciones  ---------
    public void cargarInstalaciones() {
        double precios = 0;
        int usos = 0;
        ArrayList<String> errores = new ArrayList<>();
        
        if(vistaInstalacion.getJtInstalaciones().getSelectedRow() >= 0){
            JOptionPane.showMessageDialog(vistaInstalacion, "No puedes Cargar una Instalacion seleccionada. Solo modificarlo.");
            limpiarCasilleros();
            return;
        }
        
        //-------- Validaciones --------
        // Validar Nombre
        String nombre = "";
        if (vistaInstalacion.getJtfNombre().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        } else {
            nombre = vistaInstalacion.getJtfNombre().trim();
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                errores.add("El nombre solo puede contener letras y espacios.");
            } else if (nombre.length() > 20) {
                errores.add("El nombre no puede tener más de 20 caracteres.");
            }
        }
        
        // Validar Precio
        if (vistaInstalacion.getJtfPrecio().isEmpty()) {
            errores.add("El precio no puede estar vacío");
        } else {
            try {
                precios = Double.parseDouble(vistaInstalacion.getJtfPrecio().trim());
            } catch (NumberFormatException e) {
                errores.add("El precio debe ser un número válido.");
            }
        }
        
        // Validar Detalle
        String detalle = "";
        if (vistaInstalacion.getJtaDetalle().isEmpty()) {
            errores.add("El detalle no puede estar vacío");
        } else {
            detalle = vistaInstalacion.getJtaDetalle().trim();
        }
        
        // Validar Usos
        if(vistaInstalacion.getJtfUsos().isEmpty()){
            errores.add("Los usos no pueden estar vacíos");
        } else {
            try {
                usos = Integer.parseInt(vistaInstalacion.getJtfUsos().trim());
            } catch (NumberFormatException e) {
                errores.add("Los usos deben ser un número válido.");
            }
        }
        
        // Si hay errores, mostrarlos todos juntos
        if (!errores.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");
            for (String error : errores) {
                mensaje.append("• ").append(error).append("\n");
            }
            JOptionPane.showMessageDialog(vistaInstalacion, mensaje.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean estado = vistaInstalacion.getJcbEstado();
        
        Instalacion instalacionC = new Instalacion();
        
        instalacionC.setNombre(nombre);
        instalacionC.setPrecio(precios);
        instalacionC.setDetalleDeUso(detalle);
        instalacionC.setUsos(usos);
        instalacionC.setEstado(estado);
        
        instalacionData.guardarInstalacion(instalacionC);
        listarInstalaciones();
        limpiarCasilleros();
        
    }
    
    //--------- Buscador de Instalacioens ---------
    public void buscarInstalaciones(){
        String buscarInsta = vistaInstalacion.getJtfBuscarInstalacion().trim();
        
        if(buscarInsta.isEmpty()){
            listarInstalaciones();
            return;
        }
        
        ArrayList<Instalacion> listaInstalaciones = instalacionData.listarInstalaciones();
        ArrayList<Instalacion> filtrados = new ArrayList<>();
        
        //--------- Listar Clientes Activos ---------
        if (vistaInstalacion.getJrbActivo() == true && vistaInstalacion.getJrbInactivo() == false) {
            for (Instalacion insta : listaInstalaciones) {
                String nombreInsta = String.valueOf(insta.getNombre());
                if (nombreInsta.toLowerCase().startsWith(buscarInsta) && insta.isEstado() == true) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes Inactivos ---------
        if (vistaInstalacion.getJrbActivo() == false && vistaInstalacion.getJrbInactivo() == true) {
            for (Instalacion insta : listaInstalaciones) {
                String nombreInsta = String.valueOf(insta.getNombre());
                if (nombreInsta.toLowerCase().startsWith(buscarInsta) && insta.isEstado() == false) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes sin filtros ---------
        if (vistaInstalacion.getJrbActivo() == false && vistaInstalacion.getJrbInactivo() == false) {
            for (Instalacion insta : listaInstalaciones) {
                String nombreInsta = String.valueOf(insta.getNombre());
                if (nombreInsta.toLowerCase().startsWith(buscarInsta)) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }
        
    }
    
    //--------- Modificar Instalaciones  ---------
    public void modificarInstalaciones(){
        int fila = vistaInstalacion.getJtInstalaciones().getSelectedRow();
        Instalacion instalacionM = null;
        if(fila < 0){
            JOptionPane.showMessageDialog(vistaInstalacion, "Debe seleccionar una fila de la tabla para modificar una Instalación.");
            return;
        }
        
        double precio = 0;
        int usos = 0;
        ArrayList<String> errores = new ArrayList<>();
        
        //-------- Validaciones --------
        // Validar Nombre
        String nombre = "";
        if (vistaInstalacion.getJtfNombre().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        } else {
            nombre = vistaInstalacion.getJtfNombre().trim();
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                errores.add("El nombre solo puede contener letras y espacios.");
            } else if (nombre.length() > 20) {
                errores.add("El nombre no puede tener más de 20 caracteres.");
            }
        }
        
        // Validar Precio
        if (vistaInstalacion.getJtfPrecio().isEmpty()) {
            errores.add("El precio no puede estar vacío");
        } else {
            try {
                precio = Double.parseDouble(vistaInstalacion.getJtfPrecio().trim());
            } catch (NumberFormatException e) {
                errores.add("El precio debe ser un número válido.");
            }
        }
        
        // Validar Detalle
        String detalle = "";
        if (vistaInstalacion.getJtaDetalle().isEmpty()) {
            errores.add("El detalle no puede estar vacío");
        } else {
            detalle = vistaInstalacion.getJtaDetalle().trim();
        }
        
        // Validar Usos
        if(vistaInstalacion.getJtfUsos().isEmpty()){
            errores.add("Los usos no pueden estar vacíos");
        } else {
            try {
                usos = Integer.parseInt(vistaInstalacion.getJtfUsos().trim());
            } catch (NumberFormatException e) {
                errores.add("Los usos deben ser un número válido.");
            }
        }
        
        // Si hay errores, mostrarlos todos juntos
        if (!errores.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");
            for (String error : errores) {
                mensaje.append("• ").append(error).append("\n");
            }
            JOptionPane.showMessageDialog(vistaInstalacion, mensaje.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = (int) vistaInstalacion.getJtInstalaciones().getValueAt(fila, 0);
        boolean estado = vistaInstalacion.getJcbEstado();
        
        //--- Buscamos la instalación en la base de datos ---
        instalacionM = instalacionData.buscarInstalacion(id);
        
        //--- Asignamos los nuevos datos al objeto instalacion ---
        instalacionM.setNombre(nombre);
        instalacionM.setPrecio(precio);
        instalacionM.setDetalleDeUso(detalle);
        instalacionM.setUsos(usos);
        instalacionM.setEstado(estado);        
        
        //--- Modificamos la instalación ---
        instalacionData.modificarInstalacion(instalacionM);
        limpiarCasilleros();
        listarInstalaciones();
    }
    
    //--------- Seleccionar Instalación en la tabla  ---------
    public void seleccionarInstalacion(){
        int filas = vistaInstalacion.getJtInstalaciones().getSelectedRow();
        
        String nombre = (String) vistaInstalacion.getJtInstalaciones().getValueAt(filas, 1);
        String detalle = (String) vistaInstalacion.getJtInstalaciones().getValueAt(filas, 2);
        int usos = (int) vistaInstalacion.getJtInstalaciones().getValueAt(filas, 3);
        double precio = (double) vistaInstalacion.getJtInstalaciones().getValueAt(filas, 4);
        boolean estado = true;
        if(vistaInstalacion.getJtInstalaciones().getValueAt(filas, 5).equals("Activo")){
            estado = true;
        }else if( vistaInstalacion.getJtInstalaciones().getValueAt(filas, 5).equals("Inactivo")){
            estado = false;
        }
        
        vistaInstalacion.setJtfNombre(nombre);
        vistaInstalacion.setJtfPrecio(Double.toString(precio));
        vistaInstalacion.setJtaDetalle(detalle);
        vistaInstalacion.setJtfUsos(Integer.toString(usos));
        vistaInstalacion.setJtcbEstado(estado);
    }
    
    //--------- Limpiar casilleros  ---------
    public void limpiarCasilleros(){
        vistaInstalacion.setJtfNombre("");
        vistaInstalacion.setJtfPrecio("");
        vistaInstalacion.setJtaDetalle("");
        vistaInstalacion.setJtfUsos("");
        vistaInstalacion.setJtcbEstado(false);
        vistaInstalacion.resetearFiltrosEstado();
        listarInstalaciones();
    }

    //--------- Código repetido para filtrar tablas ---------
    public void crearTablaFiltrada(ArrayList<Instalacion> instalacion) {
        
        DefaultTableModel modelo = vistaInstalacion.getModel();
        modelo.setRowCount(0);
        
        for (Instalacion insta : instalacion) {
            vistaInstalacion.getModel().addRow(new Object[]{
                insta.getCodInstal(),
                insta.getNombre(),
                insta.getDetalleDeUso(),
                insta.getUsos(),
                insta.getPrecio(),
                insta.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }
}


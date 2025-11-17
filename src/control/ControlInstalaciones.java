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
        double precios;
        int usos;

        //-------- Validaciones --------
        if (vistaInstalacion.getJtfNombre().isEmpty()) {
            JOptionPane.showMessageDialog(vistaInstalacion, "El casillero Nombre debe estar completo.");
            return;
        }
        if (vistaInstalacion.getJtfPrecio().isEmpty()) {
            JOptionPane.showMessageDialog(vistaInstalacion, "El casillero Precio debe estar completo.");
            return;
        } else {
            try {
                precios = Double.parseDouble(vistaInstalacion.getJtfPrecio());
            } catch (NumberFormatException e) {
                vistaInstalacion.setJtfPrecio("");
                JOptionPane.showMessageDialog(vistaInstalacion, "Debe ingresar un valor numérico en Precio.");
                return;
            }
        }
        if (vistaInstalacion.getJtaDetalle().isEmpty()) {
            JOptionPane.showMessageDialog(vistaInstalacion, "Es necesario el detalle de la Instalación, complete el casillero.");
            return;
        }
        if(vistaInstalacion.getJtfUsos().isEmpty()){
            JOptionPane.showMessageDialog(vistaInstalacion, "El casillero Usos debe estar completo.");
            return;
        }else{
            try {
                usos = Integer.parseInt(vistaInstalacion.getJtfUsos());
            } catch (NumberFormatException e) {
                vistaInstalacion.setJtfUsos("");
                JOptionPane.showMessageDialog(vistaInstalacion, "Debe ingresar un valor numérico en Usos.");
                return;
            }
        }
        if(vistaInstalacion.getJtInstalaciones().getSelectedRow() >= 0){
            JOptionPane.showMessageDialog(vistaInstalacion, "No puedes Cargar una Instalacion seleccionada. Solo modificarlo.");
            limpiarCasilleros();
            return;
        }
        
        String nombre = vistaInstalacion.getJtfNombre();
        String detalle = vistaInstalacion.getJtaDetalle();
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
        
        int id = (int) vistaInstalacion.getJtInstalaciones().getValueAt(fila, 0);
        String nombre = vistaInstalacion.getJtfNombre();
        double precio = Double.parseDouble(vistaInstalacion.getJtfPrecio());
        String detalle = vistaInstalacion.getJtaDetalle();
        int usos = Integer.parseInt(vistaInstalacion.getJtfUsos());
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

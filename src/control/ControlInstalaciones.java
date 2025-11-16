/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.InstalacionData;
import entidades.Cliente;
import entidades.Instalacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import vistas.vistaInstalaciones;

/**
 *
 * @author matia
 */
public class ControlInstalaciones {

    private vistaInstalaciones vistaInstalacion;
    private InstalacionData instalacionData;

    public ControlInstalaciones(vistaInstalaciones vistaInstalacion, InstalacionData instalacionData) {
        this.vistaInstalacion = vistaInstalacion;
        this.instalacionData = instalacionData;
    }

    public void listarInstaciones() {
        List<Instalacion> listaInstalciones = instalacionData.listarInstalaciones();
        List<Instalacion> filtrados = new ArrayList<>();

        //--------- Listar Clientes Activos ---------
        if (vistaInstalacion.getJrbActivo() == true && vistaInstalacion.getJrbInactivo() == false) {
            for (Instalacion insta : listaInstalciones) {
                if (insta.isEstado() == true) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes Inactivos ---------
        if (vistaInstalacion.getJrbActivo() == false && vistaInstalacion.getJrbInactivo() == true) {
            for (Instalacion insta : listaInstalciones) {
                if (insta.isEstado() == false) {
                    filtrados.add(insta);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes sin filtros ---------
        if (vistaInstalacion.getJrbActivo() == false && vistaInstalacion.getJrbInactivo() == false) {
            String[] columnas = {"ID", "Nombre", "Detalle", "Usos", "Precio", "Estado"};
            Object[][] datos = new Object[listaInstalciones.size()][6];
            
            for (int i = 0; i < listaInstalciones.size(); i++) {
                Instalacion insta = listaInstalciones.get(i);
                datos[i][0] = insta.getCodInstal();
                datos[i][1] = insta.getNombre();
                datos[i][2] = insta.getDetalleDeUso();
                datos[i][3] = insta.getUsos();
                datos[i][4] = insta.getPrecio();
                datos[i][5] = insta.isEstado() ? "Activo" : "Inactivo";
            }

            DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
            vistaInstalacion.getJtInstalaciones().setModel(modelo);
        }

    }

    //--------- Codigo repedito para filtrar tablas ---------
    public void crearTablaFiltrada(List<Instalacion> filtrados) {
        String[] columnas = {"ID", "Nombre", "Detalle", "Usos", "Precio", "Estado"};
        Object[][] datos = new Object[filtrados.size()][5];
        for (int i = 0; i < filtrados.size(); i++) {
            Instalacion insta = filtrados.get(i);
            datos[i][0] = insta.getCodInstal();
            datos[i][1] = insta.getNombre();
            datos[i][2] = insta.getDetalleDeUso();
            datos[i][3] = insta.getUsos();
            datos[i][4] = insta.getPrecio();
            datos[i][5] = insta.isEstado() ? "Activo" : "Inactivo";
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        vistaInstalacion.getJtInstalaciones().setModel(modelo);
    }
}

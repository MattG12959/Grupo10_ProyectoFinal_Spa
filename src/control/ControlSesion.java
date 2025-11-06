/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.ClienteData;
import Persistencia.ConsultorioData;
import Persistencia.TratamientoData;
import Persistencia.InstalacionData;
import Persistencia.MasajistaData;
import javax.swing.JOptionPane;
import vistas.vistaCargarSesion;

/**
 *
 * @author matia
 */
public class ControlSesion {
    private vistaCargarSesion vistaCargarSesion;
    private ClienteData clienteData;
    private TratamientoData tratamientoData;
    private ConsultorioData consultorioData;
    private InstalacionData instalacionesData;
    private MasajistaData masajistaData;

    public ControlSesion(vistaCargarSesion vistaCargarSesion, ClienteData clienteData, TratamientoData tratamientoData, ConsultorioData consultorioData, InstalacionData instalacionesData, MasajistaData masajistaData) {
        this.vistaCargarSesion = vistaCargarSesion;
        this.clienteData = clienteData;
        this.tratamientoData = tratamientoData;
        this.consultorioData = consultorioData;
        this.instalacionesData = instalacionesData;
        this.masajistaData = masajistaData;
    }

    public void cargarCliente(){
        try {
            vistaCargarSesion.getJtClienteSesion(); //Informacion del jTextField Cliente
            
        } catch (Exception e) {
        }
    }
    
    public void buscarCliente(){
        try {
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar Cliente.");
        }
    }
    
    
}

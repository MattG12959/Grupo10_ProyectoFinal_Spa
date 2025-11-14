/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.ClienteData;
import entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import vistas.vistaCliente;

/**
 *
 * @author matia
 */
public class ControlCliente {
    private vistaCliente vistaCliente;
    private ClienteData clienteData;
    private Cliente cliente;

    public ControlCliente(vistaCliente vistaCliente, ClienteData clienteData) {
        this.vistaCliente = vistaCliente;
        this.clienteData = clienteData;
    }
    
    public void listarClientes(){
        DefaultTableModel modelo = new DefaultTableModel();
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = clienteData.listarClientes();
        
        for (Cliente lista : listaClientes) {
            modelo.addRow(new Object[]{
                lista.getCodCli(),
                lista.getNombre(),
                lista.getApellido(),
                lista.getDni(),
                lista.getTelefono(),
                lista.getAfecciones(),
                lista.getEdad(),
                lista.isEstado()
            });
        }
    }
    
    public void limpiarCasilleros(){
        vistaCliente.setJtfNombre("");
        vistaCliente.setJtfApellido("");
        vistaCliente.setJtfDNI("");
        vistaCliente.setJtfTelefono("");
        vistaCliente.setJtfEdad("");
        vistaCliente.setJtaAfecciones("");
        vistaCliente.setJcbActivo(false);
    }
}

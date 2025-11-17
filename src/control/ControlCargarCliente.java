/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.ClienteData;
import entidades.Cliente;
import vistas.vistaCargarCliente;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public class ControlCargarCliente {
    private vistaCargarCliente vistaCargarCliente;
    private ClienteData clienteData;
    private Cliente cliente = null;

    public ControlCargarCliente(vistaCargarCliente vistaCargarCliente, ClienteData clienteData) {
        this.vistaCargarCliente = vistaCargarCliente;
        this.clienteData = clienteData;
    }

    public void cargarCliente(){
        
    }
    
    
}

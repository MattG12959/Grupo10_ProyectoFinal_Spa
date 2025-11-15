/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.ClienteData;
import entidades.Cliente;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
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
    
    //--------- Llenar la tabla con los clientes que estan en la base de datos ---------
    public void listarClientes(){
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = clienteData.listarClientes();
        
        String[] columnas = {"ID", "Nombre", "Apellido", "DNI", "Telefono", "Edad", "Afecciones", "Estado"};
        Object[][] datos = new Object[listaClientes.size()][8];
        
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            datos[i][0] = cliente.getCodCli();
            datos[i][1] = cliente.getNombre();
            datos[i][2] = cliente.getApellido();
            datos[i][3] = cliente.getDni();
            datos[i][4] = cliente.getTelefono();
            datos[i][5] = cliente.getEdad();
            datos[i][6] = cliente.getAfecciones();
            datos[i][7] = cliente.isEstado() ? "Activo" : "Inactivo";                    
        }
        
        DefaultTableModel modelo = new DefaultTableModel(datos,columnas);
        
        vistaCliente.getjTablaClientes().setModel(modelo);
    }
    
    //--------- Carga Cliente Base de Datos ---------
    public void cargarCliente(){
        int dni;
        long telefono;
        int edad;
        
        //----- Validaciones ------
        if(vistaCliente.getJtfNombre().isEmpty()){
            JOptionPane.showMessageDialog(null, "El Casillero Nombre debe estar completo");
            return;
        }        
        if(vistaCliente.getJtfApellido().isEmpty()){
            JOptionPane.showMessageDialog(null, "El Casillero Apellido debe estar completo");
            return;
        }
        if(vistaCliente.getJtfDNI().isEmpty()){
            JOptionPane.showMessageDialog(null, "El Casillero DNI debe estar completo");
            return;
        }else{
            try {
                dni = Integer.parseInt(vistaCliente.getJtfDNI());
            } catch (NumberFormatException e) {                
                vistaCliente.setJtfDNI("");
                JOptionPane.showMessageDialog(vistaCliente, "Debe ingresar un valor númerico en DNI.");
                return;
            }
        }
        if(vistaCliente.getJtfTelefono().isEmpty()){
            JOptionPane.showMessageDialog(null, "El Casillero Telefono debe estar completo");
            return;
        }else{
            try {
                telefono = Integer.parseInt(vistaCliente.getJtfTelefono());
            } catch (NumberFormatException e) {                
                vistaCliente.setJtfTelefono("");
                JOptionPane.showMessageDialog(vistaCliente, "Debe ingresar un valor númerico en Teléfono.");
                return;
            }
        }
        if(vistaCliente.getJtfEdad().isEmpty()){
            JOptionPane.showMessageDialog(null, "El Casillero Edad debe estar completo");
            return;
        }else{
            try {
                edad = Integer.parseInt(vistaCliente.getJtfEdad());
            } catch (NumberFormatException e) {                
                vistaCliente.setJtfEdad("");
                JOptionPane.showMessageDialog(vistaCliente, "Debe ingresar un valor númerico en Edad.");
                return;
            }
        }
        
        //------ Cargar ojeto Cliente ------
        String nombre = vistaCliente.getJtfNombre();
        String apellido = vistaCliente.getJtfApellido();
        String afecciones = vistaCliente.getJtaAfecciones();
        String telefonoS = vistaCliente.getJtfTelefono();
        
        Cliente cargaCliente = new Cliente();
        
        cargaCliente.setNombre(nombre);
        cargaCliente.setApellido(apellido);
        cargaCliente.setDni(dni);
        cargaCliente.setTelefono(telefonoS);
        cargaCliente.setEdad(edad);
        cargaCliente.setAfecciones(afecciones);
        cargaCliente.setEstado(vistaCliente.getJcnActivo());
        
        clienteData.guardarCliente(cargaCliente);
        listarClientes();
        limpiarCasilleros();
    }
    
    //--------- Limpiar Casilleros ---------
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

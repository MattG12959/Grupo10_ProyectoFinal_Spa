/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.ClienteData;
import entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.vistaCliente;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public class ControlCliente {

    private vistaCliente vistaCliente;
    private ClienteData clienteData;

    public ControlCliente(vistaCliente vistaCliente, ClienteData clienteData) {
        this.vistaCliente = vistaCliente;
        this.clienteData = clienteData;
    }

    //--------- Llenar la tabla con los clientes que estan en la base de datos ---------
    public void listarClientes() {
        ArrayList<Cliente> listaClientes = clienteData.listarClientes();
        ArrayList<Cliente> filtrados = new ArrayList<>();

        //--------- Listar Clientes Activos ---------
        if (vistaCliente.getJrbActivo() == true && vistaCliente.getJrbInactivo() == false) {
            for (Cliente c : listaClientes) {
                if (c.isEstado() == true) {
                    filtrados.add(c);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //--------- Listar Clientes Inactivos ---------
        if (vistaCliente.getJrbActivo() == false && vistaCliente.getJrbInactivo() == true) {
            for (Cliente c : listaClientes) {
                if (c.isEstado() == false) {
                    filtrados.add(c);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        if (vistaCliente.getJrbActivo() == false && vistaCliente.getJrbInactivo() == false) {
            crearTablaFiltrada(listaClientes);
        }
    }

    //--------- Carga Cliente Base de Datos ---------
    public void cargarCliente() {
        long dni = 0;
        int edad = 0;
        String telefonoS = "";
        ArrayList<String> errores = new ArrayList<>();
        
        if(vistaCliente.getjTablaClientes().getSelectedRow() >= 0){
            JOptionPane.showMessageDialog(vistaCliente, "No puedes Cargar un cliente seleccionado. Solo modificarlo.");
            limpiarCasilleros();
            return;
        }
        
        //----- Validaciones ------
        // Validar Nombre
        String nombre = "";
        if (vistaCliente.getJtfNombre().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        } else {
            nombre = vistaCliente.getJtfNombre().trim();
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                errores.add("El nombre solo puede contener letras y espacios.");
            } else if (nombre.length() > 20) {
                errores.add("El nombre no puede tener más de 20 caracteres.");
            }
        }
        
        // Validar Apellido
        String apellido = "";
        if (vistaCliente.getJtfApellido().isEmpty()) {
            errores.add("El apellido no puede estar vacío");
        } else {
            apellido = vistaCliente.getJtfApellido().trim();
            if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                errores.add("El apellido solo puede contener letras y espacios.");
            } else if (apellido.length() > 20) {
                errores.add("El apellido no puede tener más de 20 caracteres.");
            }
        }
        
        // Validar DNI (8 números)
        if (vistaCliente.getJtfDNI().isEmpty()) {
            errores.add("El DNI no puede estar vacío");
        } else {
            String dniTexto = vistaCliente.getJtfDNI().trim();
            if (!dniTexto.matches("\\d+")) {
                errores.add("El DNI solo puede contener números.");
            } else if (dniTexto.length() != 8) {
                errores.add("El DNI debe tener exactamente 8 números.");
            } else {
                try {
                    dni = Long.parseLong(dniTexto);
                } catch (NumberFormatException e) {
                    errores.add("El DNI debe ser un número válido.");
                }
            }
        }
        
        // Validar Teléfono (8 a 20 números)
        if (vistaCliente.getJtfTelefono().isEmpty()) {
            errores.add("El teléfono no puede estar vacío");
        } else {
            telefonoS = vistaCliente.getJtfTelefono().trim();
            if (!telefonoS.matches("\\d+")) {
                errores.add("El teléfono solo puede contener números.");
            } else if (telefonoS.length() < 8 || telefonoS.length() > 20) {
                errores.add("El teléfono debe tener entre 8 y 20 números.");
            }
        }
        
        // Validar Edad (número)
        if (vistaCliente.getJtfEdad().isEmpty()) {
            errores.add("La edad no puede estar vacía");
        } else {
            try {
                edad = Integer.parseInt(vistaCliente.getJtfEdad().trim());
            } catch (NumberFormatException e) {
                errores.add("La edad debe ser un número válido.");
            }
        }
        
        // Si hay errores, mostrarlos todos juntos
        if (!errores.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");
            for (String error : errores) {
                mensaje.append("• ").append(error).append("\n");
            }
            JOptionPane.showMessageDialog(vistaCliente, mensaje.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //------ Cargar objeto Cliente ------
        String afecciones = vistaCliente.getJtaAfecciones();

        Cliente cargaCliente = new Cliente();

        cargaCliente.setNombre(nombre);
        cargaCliente.setApellido(apellido);
        cargaCliente.setDni((int)dni);
        cargaCliente.setTelefono(telefonoS);
        cargaCliente.setEdad(edad);
        cargaCliente.setAfecciones(afecciones);
        cargaCliente.setEstado(vistaCliente.getJcnActivo());

        clienteData.guardarCliente(cargaCliente);
        listarClientes();
        limpiarCasilleros();
    }

    //--------- Limpiar Casilleros ---------
    public void limpiarCasilleros() {
        vistaCliente.setJtfNombre("");
        vistaCliente.setJtfApellido("");
        vistaCliente.setJtfDNI("");
        vistaCliente.setJtfTelefono("");
        vistaCliente.setJtfEdad("");
        vistaCliente.setJtaAfecciones("");
        vistaCliente.setJcbActivo(false);
        vistaCliente.resetearFiltrosEstado();
        
        listarClientes();
    }

    //--------- Buscar por DNI ---------
    public void buscarClienteDni() {
        String dniTexto = vistaCliente.getJtfDniCliente().trim();

        if (dniTexto.isEmpty()) {
            listarClientes();
            return;
        }

        try {
            Integer.parseInt(vistaCliente.getJtfDniCliente());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaCliente, "Debe ingresar un valor númerico en DNI.");
            return;
        }

        ArrayList<Cliente> listaClientes = clienteData.listarClientes();
        ArrayList<Cliente> filtrados = new ArrayList<>();

        //------ Filtrado marcando radio buttons activo ------
        if (vistaCliente.getJrbActivo() == true && vistaCliente.getJrbInactivo() == false) {
            for (Cliente c : listaClientes) {
                String dniCliente = String.valueOf(c.getDni());
                if (dniCliente.startsWith(dniTexto) && c.isEstado() == true) {
                    filtrados.add(c);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //------ Filtrado marcando radio buttons inactivo ------
        if (vistaCliente.getJrbActivo() == false && vistaCliente.getJrbInactivo() == true) {
            for (Cliente c : listaClientes) {
                String dniCliente = String.valueOf(c.getDni());
                if (dniCliente.startsWith(dniTexto) && c.isEstado() == false) {
                    filtrados.add(c);
                }
            }
            crearTablaFiltrada(filtrados);
        }

        //------ Filtrado sin marcar radio buttons activo e inactivo ------
        if (vistaCliente.getJrbActivo() == false && vistaCliente.getJrbInactivo() == false) {
            for (Cliente c : listaClientes) {
                String dniCliente = String.valueOf(c.getDni());
                if (dniCliente.startsWith(dniTexto)) {
                    filtrados.add(c);
                }
            }
            crearTablaFiltrada(filtrados);
        }
    }

    //--------- Código repetido para filtrar tablas ---------
    public void crearTablaFiltrada(ArrayList<Cliente> clientes) {
        DefaultTableModel modelo = vistaCliente.getModel();
        modelo.setRowCount(0);
        
        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                c.getCodCli(),
                c.getNombre(),
                c.getApellido(),
                c.getDni(),
                c.getTelefono(),
                c.getEdad(),
                c.getAfecciones(),
                c.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }
    
    //--------- Modificar Cliente al seleccionar desde la tabla ---------
    public void modificarCliente(){
        //-- Construir el objeto
        int fila = vistaCliente.getjTablaClientes().getSelectedRow();
        Cliente clienteM = null;
        
        if(fila < 0){
            JOptionPane.showMessageDialog(vistaCliente, "Debe seleccionar una fila de la tabla para modificar un cliente.");
            return;
        }
        
        long dniLong = 0;
        int edad = 0;
        String telefonoS = "";
        ArrayList<String> errores = new ArrayList<>();
        
        //----- Validaciones ------
        // Validar Nombre
        String nombre = "";
        if (vistaCliente.getJtfNombre().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        } else {
            nombre = vistaCliente.getJtfNombre().trim();
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                errores.add("El nombre solo puede contener letras y espacios.");
            } else if (nombre.length() > 20) {
                errores.add("El nombre no puede tener más de 20 caracteres.");
            }
        }
        
        // Validar Apellido
        String apellido = "";
        if (vistaCliente.getJtfApellido().isEmpty()) {
            errores.add("El apellido no puede estar vacío");
        } else {
            apellido = vistaCliente.getJtfApellido().trim();
            if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                errores.add("El apellido solo puede contener letras y espacios.");
            } else if (apellido.length() > 20) {
                errores.add("El apellido no puede tener más de 20 caracteres.");
            }
        }
        
        // Validar DNI (8 números)
        if (vistaCliente.getJtfDNI().isEmpty()) {
            errores.add("El DNI no puede estar vacío");
        } else {
            String dniTexto = vistaCliente.getJtfDNI().trim();
            if (!dniTexto.matches("\\d+")) {
                errores.add("El DNI solo puede contener números.");
            } else if (dniTexto.length() != 8) {
                errores.add("El DNI debe tener exactamente 8 números.");
            } else {
                try {
                    dniLong = Long.parseLong(dniTexto);
                } catch (NumberFormatException e) {
                    errores.add("El DNI debe ser un número válido.");
                }
            }
        }
        
        // Validar Teléfono (8 a 20 números)
        if (vistaCliente.getJtfTelefono().isEmpty()) {
            errores.add("El teléfono no puede estar vacío");
        } else {
            telefonoS = vistaCliente.getJtfTelefono().trim();
            if (!telefonoS.matches("\\d+")) {
                errores.add("El teléfono solo puede contener números.");
            } else if (telefonoS.length() < 8 || telefonoS.length() > 20) {
                errores.add("El teléfono debe tener entre 8 y 20 números.");
            }
        }
        
        // Validar Edad (número)
        if (vistaCliente.getJtfEdad().isEmpty()) {
            errores.add("La edad no puede estar vacía");
        } else {
            try {
                edad = Integer.parseInt(vistaCliente.getJtfEdad().trim());
            } catch (NumberFormatException e) {
                errores.add("La edad debe ser un número válido.");
            }
        }
        
        // Si hay errores, mostrarlos todos juntos
        if (!errores.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");
            for (String error : errores) {
                mensaje.append("• ").append(error).append("\n");
            }
            JOptionPane.showMessageDialog(vistaCliente, mensaje.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int ID = (int) vistaCliente.getjTablaClientes().getValueAt(fila, 0);
        String afecciones = vistaCliente.getJtaAfecciones();
        int DNI = (int)dniLong;
        boolean estado = vistaCliente.getJcnActivo();
        
        //----- Buscamos el cliente en la base de datos y lo asignamos a un objeto cliente -----
        clienteM = clienteData.buscarCliente(ID);
        
        //----- Modificar cliente seleccionado -----
        clienteM.setCodCli(ID);
        clienteM.setNombre(nombre);
        clienteM.setApellido(apellido);
        clienteM.setDni(DNI);
        clienteM.setTelefono(telefonoS);
        clienteM.setEdad(edad);
        clienteM.setAfecciones(afecciones);
        clienteM.setEstado(estado);
        
        clienteData.modificarCliente(clienteM);        
        limpiarCasilleros();
        listarClientes();
    }
    
    //--------- Seleccionar Cliente en la tabla  ---------
    public void seleccionarCliente(){
        int fila = vistaCliente.getjTablaClientes().getSelectedRow();
        
        //String ID = (String) vistaCliente.getjTablaClientes().getValueAt(fila, 0);
        String nombre = (String) vistaCliente.getjTablaClientes().getValueAt(fila, 1);
        String apellido = (String) vistaCliente.getjTablaClientes().getValueAt(fila, 2);
        int DNI = (int) vistaCliente.getjTablaClientes().getValueAt(fila, 3);
        String telefono = (String) vistaCliente.getjTablaClientes().getValueAt(fila, 4);
        int edad = (int) vistaCliente.getjTablaClientes().getValueAt(fila, 5);
        String afecciones = (String) vistaCliente.getjTablaClientes().getValueAt(fila, 6);
        boolean estado = true;
        if(vistaCliente.getjTablaClientes().getValueAt(fila, 7).equals("Activo")){
            estado = true;
        }else if(vistaCliente.getjTablaClientes().getValueAt(fila, 7).equals("Inactivo")){
            estado = false;
        }
        
        vistaCliente.setJtfNombre(nombre);
        vistaCliente.setJtfApellido(apellido);
        vistaCliente.setJtfDNI(Integer.toString(DNI));
        vistaCliente.setJtfTelefono(telefono);
        vistaCliente.setJtfEdad(Integer.toString(edad));
        vistaCliente.setJtaAfecciones(afecciones);
        vistaCliente.setJcbActivo(estado);
    }
}
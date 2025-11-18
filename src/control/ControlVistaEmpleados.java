
package control;
/*
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
*/
import Persistencia.*;
import constantes.*;
import entidades.Masajista;
import entidades.Vendedor;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.vistaEmpleados;
import vistas.vistaModificarEmpleado;

public class ControlVistaEmpleados {

    private vistaEmpleados vista;
    private VendedorData vendedorData;
    private MasajistaData masajistaData;
    private JDesktopPane escritorio;

    public ControlVistaEmpleados(vistaEmpleados vista, MasajistaData masajistaData, VendedorData vendedorData, JDesktopPane escritorio) {
        this.vista = vista;
        this.vendedorData = vendedorData;
        this.masajistaData = masajistaData;
        this.escritorio = escritorio;
    }

    public void preCargarCbTipoDeEmpleado() {
        String[] tipoDeEmpleado = {"Todos", PuestosDeTrabajo.MASAJISTA.getPuesto(), PuestosDeTrabajo.VENDEDOR.getPuesto()};

        JComboBox<String> combo = vista.getCbTipoDeEmpleado();
        combo.removeAllItems(); // Limpia el combo por si ya tenía algo

        for (String aux : tipoDeEmpleado) {
            combo.addItem(aux);
        }

        vista.setCbTipoDeEmpleado(combo);
    }

    public void agregarListenerTipoEmpleado() {
        vista.getCbTipoDeEmpleado().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarEspecialidades();
            }
        });
        // Llamar una vez para dejar el estado inicial correcto
        actualizarEspecialidades();
    }

    private void actualizarEspecialidades() {
        String seleccionado = (String) vista.getCbTipoDeEmpleado().getSelectedItem();
        JComboBox<String> cbEspecialidades = vista.getCbEspecialidades();
        cbEspecialidades.removeAllItems();

        if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(seleccionado)) {
            cbEspecialidades.addItem("Todos");
            cbEspecialidades.addItem(Especialidades.CORPORAL.getEspecialidad());
            cbEspecialidades.addItem(Especialidades.ESTETICO.getEspecialidad());
            cbEspecialidades.addItem(Especialidades.FACIAL.getEspecialidad());
            cbEspecialidades.addItem(Especialidades.RELAJACION.getEspecialidad());
            cbEspecialidades.setEnabled(true);
        } else {
            cbEspecialidades.setEnabled(false); // deshabilita si no es masajista
        }
    }

    public void actualizarTabla() {
        String tipoEmpleado = (String) vista.getCbTipoDeEmpleado().getSelectedItem();
        //DefaultTableModel modelo = (DefaultTableModel) vista.getJtEmpleados().getModel();
        //modelo.setRowCount(0); // Limpia la tabla

        if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(tipoEmpleado)) {
            // Columnas para masajista
            String[] columnas = {"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"};

            DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // ID Empleado = 0, Matricula = 1, Estado = 8, Especialidad = 7, Puesto = 6
                    return column != 0 && column != 1 && column != 8 && column != 7 && column != 6;
                }
            };
            vista.getJtEmpleados().setModel(modelo);

            String especialidad = (String) vista.getCbEspecialidades().getSelectedItem();
            ArrayList<Masajista> lista;
            if (especialidad != null && !"Todos".equals(especialidad)) {
                lista = masajistaData.listarMasajistasPorEspecialidad(especialidad);
            } else {
                lista = masajistaData.listarMasajistas();
            }

            for (Masajista m : lista) {
                modelo.addRow(new Object[]{
                    m.getIdEmpleado(),
                    m.getMatricula(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getTelefono(),
                    m.getDni(),
                    m.getPuesto(),
                    m.getEspecialidad(),
                    m.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(tipoEmpleado)) {
            // Columnas para vendedor
            String[] columnas = {"ID Empleado", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Estado"};

            DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // ID Empleado = 0, Estado = 6, Puesto = 5
                    return column != 0 && column != 6 && column != 5;
                }
            };
            vista.getJtEmpleados().setModel(modelo);

            ArrayList<Vendedor> lista = vendedorData.listarVendedores();
            for (Vendedor v : lista) {
                modelo.addRow(new Object[]{
                    v.getIdEmpleado(),
                    v.getNombre(),
                    v.getApellido(),
                    v.getTelefono(),
                    v.getDni(),
                    v.getPuesto(),
                    v.getEstado() ? "Activo" : "Inactivo"
                });
            }
        } else {
            String[] columnas = {"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"};

            DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // ID Empleado = 0, Matricula = 1, Estado = 8, Especialidad = 7, Puesto = 6
                    return column != 0 && column != 1 && column != 8 && column != 7 && column != 6;
                }
            };
            vista.getJtEmpleados().setModel(modelo);

            // Cargar todos los masajistas
            ArrayList<Masajista> listaMasajistas = masajistaData.listarMasajistas();
            for (Masajista m : listaMasajistas) {
                modelo.addRow(new Object[]{
                    m.getIdEmpleado(),
                    m.getMatricula(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getTelefono(),
                    m.getDni(),
                    m.getPuesto(),
                    m.getEspecialidad(),
                    m.isEstado() ? "Activo" : "Inactivo"
                });
            }
            // Cargar todos los vendedores
            ArrayList<Vendedor> listaVendedores = vendedorData.listarVendedores();
            for (Vendedor v : listaVendedores) {
                modelo.addRow(new Object[]{
                    v.getIdEmpleado(),
                    "", // Matrícula vacío
                    v.getNombre(),
                    v.getApellido(),
                    v.getTelefono(),
                    v.getDni(),
                    v.getPuesto(),
                    "", // Especialidad vacío
                    v.getEstado() ? "Activo" : "Inactivo"
                });
            }
        }
    }

    public void cambiarEstadoSeleccionado() {
        int fila = vista.getJtEmpleados().getSelectedRow();

        // Comprueba de que no haya ninguna fila seleccionada
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.getJtEmpleados().getModel();

        int idEmpleado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

        String puesto = modelo.getValueAt(fila, modelo.findColumn("Puesto")).toString();

        String estadoActual = modelo.getValueAt(fila, modelo.getColumnCount() - 1).toString();

        if ("Activo".equals(estadoActual)) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea dar de baja a este empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puesto)) {
                masajistaData.bajaLogica(idEmpleado);
            } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puesto)) {
                vendedorData.bajaLogica(idEmpleado);
            } else {
                JOptionPane.showMessageDialog(null, "No se reconoce el tipo de empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else if ("Inactivo".equals(estadoActual)) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea activar a este empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puesto)) {
                masajistaData.altaLogica(idEmpleado);
            } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puesto)) {
                vendedorData.altaLogica(idEmpleado);
            } else {
                JOptionPane.showMessageDialog(null, "No se reconoce el tipo de empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Refresca la tabla
        actualizarTabla();
    }

    public void modificarEmpleadoSeleccionado() {
        int fila = vista.getJtEmpleados().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel modelo = (DefaultTableModel) vista.getJtEmpleados().getModel();
        int idEmpleado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        String puesto = "";
        
        try {
            puesto = modelo.getValueAt(fila, modelo.findColumn("Puesto")).toString();
        } catch (IllegalArgumentException e) {
            // Si no encuentra la columna "Puesto", buscar en otra posición
            if (modelo.getColumnCount() > 5) {
                puesto = modelo.getValueAt(fila, 5).toString();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo determinar el puesto del empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        // Verificar si ya existe una ventana de modificación abierta
        JInternalFrame abierto = buscarFrame(vistaModificarEmpleado.class);
        if (abierto != null) {
            try { 
                abierto.setSelected(true); 
            } catch (java.beans.PropertyVetoException e) {}
            abierto.toFront();
            centrarFrame(abierto);
            return;
        }
        
         // Crear nueva ventana de modificación
        vistaModificarEmpleado frm = new vistaModificarEmpleado(idEmpleado, puesto, masajistaData, vendedorData, this);
        escritorio.add(frm);
        frm.pack(); 
        centrarFrame(frm);
        frm.setVisible(true);
        try { 
            frm.setSelected(true); 
        } catch (java.beans.PropertyVetoException e) {}
    }
    
    private JInternalFrame buscarFrame(Class<? extends JInternalFrame> clase) {
        for (JInternalFrame f : escritorio.getAllFrames()) {
            if (clase.isInstance(f)) return f;
        }
        return null;
    }
    
    private void centrarFrame(JInternalFrame f) {
        int x = (escritorio.getWidth() - f.getWidth()) / 2;
        int y = (escritorio.getHeight() - f.getHeight()) / 2;
        f.setLocation(Math.max(x, 0), Math.max(y, 0));

        
    }

    public void buscarEmpleado() {
        String tipoEmpleado = (String) vista.getCbTipoDeEmpleado().getSelectedItem();
        String dniTxt = vista.getTxtBusquedaPorDni().getText().trim();
        String idTxt = vista.getTxtBusquedaPorId().getText().trim();
        String matriculaTxt = vista.getTxtBusquedaPorMatricula().getText().trim();

        // Validar que solo un campo tenga texto
        int camposLlenos = 0;
        if (!dniTxt.isEmpty()) {
            camposLlenos++;
        }
        if (!idTxt.isEmpty()) {
            camposLlenos++;
        }
        if (!matriculaTxt.isEmpty()) {
            camposLlenos++;
        }

        if (camposLlenos == 0) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un valor en uno de los campos de búsqueda.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (camposLlenos > 1) {
            JOptionPane.showMessageDialog(null, "Solo puede buscar por un campo a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        

        DefaultTableModel modelo = (DefaultTableModel) vista.getJtEmpleados().getModel();
        modelo.setRowCount(0); // Limpia la tabla

        try {
            if (!dniTxt.isEmpty()) {
                int dni = Integer.parseInt(dniTxt);
                if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(tipoEmpleado)) {
                    Masajista m = masajistaData.buscarMasajistaPorDNI(dni);
                    if (m != null) {
                        modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"});
                        modelo.addRow(new Object[]{
                            m.getIdEmpleado(), m.getMatricula(), m.getNombre(), m.getApellido(), m.getTelefono(), m.getDni(), m.getPuesto(), m.getEspecialidad(), m.isEstado() ? "Activo" : "Inactivo"
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró masajista con ese DNI.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(tipoEmpleado)) {
                    Vendedor v = vendedorData.buscarVendedorPorDNI(dni);
                    if (v != null) {
                        modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Estado"});
                        modelo.addRow(new Object[]{
                            v.getIdEmpleado(), v.getNombre(), v.getApellido(), v.getTelefono(), v.getDni(), v.getPuesto(), v.getEstado() ? "Activo" : "Inactivo"
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró vendedor con ese DNI.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // Si es "Todos", buscar en ambos
                    Masajista m = masajistaData.buscarMasajistaPorDNI(dni);
                    Vendedor v = vendedorData.buscarVendedorPorDNI(dni);
                    modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"});
                    if (m != null) {
                        modelo.addRow(new Object[]{
                            m.getIdEmpleado(), m.getMatricula(), m.getNombre(), m.getApellido(), m.getTelefono(), m.getDni(), m.getPuesto(), m.getEspecialidad(), m.isEstado() ? "Activo" : "Inactivo"
                        });
                    }
                    if (v != null) {
                        modelo.addRow(new Object[]{
                            v.getIdEmpleado(), "", v.getNombre(), v.getApellido(), v.getTelefono(), v.getDni(), v.getPuesto(), "", v.getEstado() ? "Activo" : "Inactivo"
                        });
                    }
                    if (m == null && v == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con ese DNI.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else if (!idTxt.isEmpty()) {
                int id = Integer.parseInt(idTxt);
                if ("Masajista".equals(tipoEmpleado)) {
                    Masajista m = masajistaData.buscarMasajistaPorId(id);
                    if (m != null) {
                        modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"});
                        modelo.addRow(new Object[]{
                            m.getIdEmpleado(), m.getMatricula(), m.getNombre(), m.getApellido(), m.getTelefono(), m.getDni(), m.getPuesto(), m.getEspecialidad(), m.isEstado() ? "Activo" : "Inactivo"
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró masajista con ese ID.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if ("Vendedor".equals(tipoEmpleado)) {
                    Vendedor v = vendedorData.buscarVendedorPorId(id);
                    if (v != null) {
                        modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Estado"});
                        modelo.addRow(new Object[]{
                            v.getIdEmpleado(), v.getNombre(), v.getApellido(), v.getTelefono(), v.getDni(), v.getPuesto(), v.getEstado() ? "Activo" : "Inactivo"
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró vendedor con ese ID.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // Si es "Todos", buscar en ambos
                    Masajista m = masajistaData.buscarMasajistaPorId(id);
                    Vendedor v = vendedorData.buscarVendedorPorId(id);
                    modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"});
                    if (m != null) {
                        modelo.addRow(new Object[]{
                            m.getIdEmpleado(), m.getMatricula(), m.getNombre(), m.getApellido(), m.getTelefono(), m.getDni(), m.getPuesto(), m.getEspecialidad(), m.isEstado() ? "Activo" : "Inactivo"
                        });
                    }
                    if (v != null) {
                        modelo.addRow(new Object[]{
                            v.getIdEmpleado(), "", v.getNombre(), v.getApellido(), v.getTelefono(), v.getDni(), v.getPuesto(), "", v.getEstado() ? "Activo" : "Inactivo"
                        });
                    }
                    if (m == null && v == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con ese ID.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else if (!matriculaTxt.isEmpty()) {
                int matricula = Integer.parseInt(matriculaTxt);
                
                // La búsqueda por matrícula siempre busca masajistas, independientemente del tipo seleccionado
                Masajista m = masajistaData.buscarMasajistaPorMatricula(matricula);
                if (m != null) {
                    modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"});
                    modelo.addRow(new Object[]{
                        m.getIdEmpleado(), m.getMatricula(), m.getNombre(), m.getApellido(), m.getTelefono(), m.getDni(), m.getPuesto(), m.getEspecialidad(), m.isEstado() ? "Activo" : "Inactivo"
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró masajista con esa matrícula.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese solo números en los campos de búsqueda.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

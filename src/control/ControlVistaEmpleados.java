/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.*;
import constantes.*;
import entidades.Masajista;
import entidades.Vendedor;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.vistaEmpleados;

public class ControlVistaEmpleados {

    private vistaEmpleados vista;
    private VendedorData vendedorData;
    private MasajistaData masajistaData;

    public ControlVistaEmpleados(vistaEmpleados vista, MasajistaData masajistaData, VendedorData vendedorData) {
        this.vista = vista;
        this.vendedorData = vendedorData;
        this.masajistaData = masajistaData;
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

        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea modificar los datos de este empleado?", "Confirmar modificación", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.getJtEmpleados().getModel();
        String puesto = modelo.getValueAt(fila, modelo.findColumn("Puesto")).toString();

        try {
            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puesto)) {
                // Tomar todos los datos del masajista
                int idEmpleado = Integer.parseInt(modelo.getValueAt(fila, modelo.findColumn("ID Empleado")).toString());
                int matricula = Integer.parseInt(modelo.getValueAt(fila, modelo.findColumn("Matrícula")).toString());
                String nombre = modelo.getValueAt(fila, modelo.findColumn("Nombre")).toString();
                String apellido = modelo.getValueAt(fila, modelo.findColumn("Apellido")).toString();
                String telefono = modelo.getValueAt(fila, modelo.findColumn("Teléfono")).toString();
                int dni = Integer.parseInt(modelo.getValueAt(fila, modelo.findColumn("DNI")).toString());
                String especialidad = modelo.getValueAt(fila, modelo.findColumn("Especialidad")).toString();
                String estadoStr = modelo.getValueAt(fila, modelo.findColumn("Estado")).toString();
                boolean estado = "Activo".equals(estadoStr);

                Masajista m = new Masajista(idEmpleado, matricula, nombre, apellido, telefono, dni, puesto, especialidad, estado);

                masajistaData.actualizarMasajista(m);

            } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puesto)) {
                // Tomar todos los datos del vendedor
                int idEmpleado = Integer.parseInt(modelo.getValueAt(fila, modelo.findColumn("ID Empleado")).toString());
                String nombre = modelo.getValueAt(fila, modelo.findColumn("Nombre")).toString();
                String apellido = modelo.getValueAt(fila, modelo.findColumn("Apellido")).toString();
                String telefono = modelo.getValueAt(fila, modelo.findColumn("Teléfono")).toString();
                int dni = Integer.parseInt(modelo.getValueAt(fila, modelo.findColumn("DNI")).toString());
                String estadoStr = modelo.getValueAt(fila, modelo.findColumn("Estado")).toString();
                boolean estado = "Activo".equals(estadoStr);

                Vendedor v = new Vendedor(idEmpleado, nombre, apellido, telefono, dni, puesto, estado);
                vendedorData.actualizarVendedor(v);

            } else {
                JOptionPane.showMessageDialog(null, "No se reconoce el tipo de empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Refresca la tabla
        actualizarTabla();
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

        // Si el campo de matrícula tiene texto pero el tipo de empleado no es masajista, error
        if (!matriculaTxt.isEmpty() && ! PuestosDeTrabajo.MASAJISTA.getPuesto().equals(tipoEmpleado)) {
            JOptionPane.showMessageDialog(null, "La búsqueda por matrícula solo está habilitada para masajistas.", "Error", JOptionPane.ERROR_MESSAGE);
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
                if ("Masajista".equals(tipoEmpleado)) {
                    Masajista m = masajistaData.buscarMasajistaPorMatricula(matricula);
                    if (m != null) {
                        modelo.setColumnIdentifiers(new String[]{"ID Empleado", "Matrícula", "Nombre", "Apellido", "Teléfono", "DNI", "Puesto", "Especialidad", "Estado"});
                        modelo.addRow(new Object[]{
                            m.getIdEmpleado(), m.getMatricula(), m.getNombre(), m.getApellido(), m.getTelefono(), m.getDni(), m.getPuesto(), m.getEspecialidad(), m.isEstado() ? "Activo" : "Inactivo"
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró masajista con esa matrícula.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La búsqueda por matrícula solo está habilitada para masajistas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese solo números en los campos de búsqueda.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Persistencia.*;
import entidades.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vistas.vistaCliente;
import vistas.vistaCargarSesion;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public class ControlSesion {

    private vistaCargarSesion vista;
    private ClienteData clienteData;
    private TratamientoData tratamientoData;
    private ConsultorioData consultorioData;
    private InstalacionData instalacionesData;
    private MasajistaData masajistaData;
    private ArrayList<Instalacion> instalacionesSesion = new ArrayList<>();
    private ArrayList<Sesion> sesionesDia = new ArrayList<>();
    private DiaDeSpaData diaDeSpaData;
    private Cliente clienteSeleccionado = null;

    public ControlSesion(vistaCargarSesion vistaCargarSesion, ClienteData clienteData, TratamientoData tratamientoData, ConsultorioData consultorioData, InstalacionData instalacionesData, MasajistaData masajistaData, DiaDeSpaData diaDeSpaData) {
        this.vista = vistaCargarSesion;
        this.clienteData = clienteData;
        this.tratamientoData = tratamientoData;
        this.consultorioData = consultorioData;
        this.instalacionesData = instalacionesData;
        this.masajistaData = masajistaData;
        this.diaDeSpaData = diaDeSpaData;
    }

    public void inicializar() {

        try {
            // combobox principales
            cargarConsultorios();
            cargarInstalaciones();

            // Dejar el placeholder seleccionado (índice 0)
            vista.getCbConsultorio().setSelectedIndex(0);
            // Cargar masajistas y tratamientos con placeholder
            cargarMasajistasPorEspecialidad("");
            cargarTratamientosPorEspecialidad("");

            // Dejar el placeholder de instalación seleccionado
            vista.getCbInstalacion().setSelectedIndex(0);
            vista.getLabelInformacionDeLaInstalacion().setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al inicializar pantalla de sesión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarDatosCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }

        vista.getLabelNombreCliente().setText("Nombre: " + cliente.getNombre() + " " + cliente.getApellido());
        vista.getLabelDniCliente().setText("DNI: " + String.valueOf(cliente.getDni()));
        vista.getLabelTelefonoCliente().setText("Telefono: " + cliente.getTelefono());
        vista.getLabelEdadCliente().setText("Edad: " + String.valueOf(cliente.getEdad()));
        vista.getTxtAfecciones().setText(cliente.getAfecciones());

        // Hacer que el textarea no sea editable ni cambie de tamaño, le seteo tamaño default
        vista.getTxtAfecciones().setEditable(false);
        vista.getTxtAfecciones().setLineWrap(true);
        vista.getTxtAfecciones().setWrapStyleWord(true);
        vista.getTxtAfecciones().setPreferredSize(new java.awt.Dimension(232, 84));
        vista.getTxtAfecciones().setMaximumSize(new java.awt.Dimension(232, 84));
        vista.getTxtAfecciones().setMinimumSize(new java.awt.Dimension(232, 84));

        clienteSeleccionado = cliente;
    }

    private void cargarConsultorios() {
        try {
            vista.getCbConsultorio().removeAllItems();
            
            // Agregar placeholder
            vista.getCbConsultorio().addItem("Seleccione un consultorio...");

            ArrayList<Consultorio> lista = consultorioData.listarConsultorios();
            for (Consultorio c : lista) {
                vista.getCbConsultorio().addItem(c);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar consultorios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void consultorioSeleccionado() {
        try {
            Object itemSeleccionado = vista.getCbConsultorio().getSelectedItem();
            
            // Verificar si es el placeholder
            if (itemSeleccionado == null || itemSeleccionado instanceof String) {
                vista.getCbMasajista().removeAllItems();
                vista.getCbMasajista().addItem("Seleccione un masajista...");
                vista.getCbTratamiento().removeAllItems();
                vista.getCbTratamiento().addItem("Seleccione un tratamiento...");
                return;
            }

            Consultorio cons = (Consultorio) itemSeleccionado;
            String especialidad = cons.getApto(); // ESPECIALIDAD
            cargarMasajistasPorEspecialidad(especialidad);
            cargarTratamientosPorEspecialidad(especialidad);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al procesar consultorio seleccionado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMasajistasPorEspecialidad(String especialidad) {
        try {
            vista.getCbMasajista().removeAllItems();
            
            // Agregar placeholder
            vista.getCbMasajista().addItem("Seleccione un masajista...");

            // Solo cargar masajistas si hay una especialidad válida
            if (especialidad != null && !especialidad.isEmpty()) {
                ArrayList<Masajista> lista = masajistaData.listarMasajistasAptosPorEspecialidad(especialidad);

                for (Masajista m : lista) {
                    vista.getCbMasajista().addItem(m);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar masajistas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTratamientosPorEspecialidad(String especialidad) {
        try {
            vista.getCbTratamiento().removeAllItems();
            
            // Agregar placeholder
            vista.getCbTratamiento().addItem("Seleccione un tratamiento...");

            // Solo cargar tratamientos si hay una especialidad válida
            if (especialidad != null && !especialidad.isEmpty()) {
                ArrayList<Tratamiento> lista = tratamientoData.listarTodosTratamientos();

                for (Tratamiento t : lista) {
                    // especialidad
                    if (t.isEstado() 
                            && t.gettipoTratamiento() != null
                            && t.gettipoTratamiento().equalsIgnoreCase(especialidad)) {
                                               
                        vista.getCbTratamiento().addItem(t);
                    }
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tratamientos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarInstalaciones() {
        try {
            vista.getCbInstalacion().removeAllItems();
            
            // Agregar placeholder
            vista.getCbInstalacion().addItem("Seleccione una instalación...");

            ArrayList<Instalacion> lista = instalacionesData.listarInstalaciones();

            for (Instalacion i : lista) {
                if (i.isEstado()) { // solo activas
                    vista.getCbInstalacion().addItem(i);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar instalaciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void instalacionSeleccionada() {
        try {
            Object itemSeleccionado = vista.getCbInstalacion().getSelectedItem();
            
            // Verificar si es el placeholder
            if (itemSeleccionado == null || itemSeleccionado instanceof String) {
                vista.getLabelInformacionDeLaInstalacion().setText("");
                return;
            }
            
            Instalacion inst = (Instalacion) itemSeleccionado;
            vista.getLabelInformacionDeLaInstalacion()
                    .setText(inst.getDetalleDeUso());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar info de instalación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarInstalacionASesion() {
        try {
            Object itemSeleccionado = vista.getCbInstalacion().getSelectedItem();
            
            // Verificar si es el placeholder
            if (itemSeleccionado == null || itemSeleccionado instanceof String) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una instalación.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Instalacion inst = (Instalacion) itemSeleccionado;

            if (!instalacionesSesion.contains(inst)) {
                instalacionesSesion.add(inst);
                actualizarTablaInstalaciones();
                actualizarFechaHoraSalidaYCosto();
            } else {
                JOptionPane.showMessageDialog(null, "Esa instalación ya fue agregada a la sesión.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar instalación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarInstalacionDeSesion() {
        try {
            int fila = vista.getTableInstalaciones().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una instalación en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            instalacionesSesion.remove(fila);
            actualizarTablaInstalaciones();
            actualizarFechaHoraSalidaYCosto();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar instalación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //COMPRAR GASEOSA
    private void actualizarTablaInstalaciones() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) vista.getTableInstalaciones().getModel();
            modelo.setRowCount(0);

            for (Instalacion i : instalacionesSesion) {
                modelo.addRow(new Object[]{
                    i.getCodInstal(), // IDinstalacion
                    i.getNombre(), // nombre po Instalacion
                    i.getDetalleDeUso() // Descripcion
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tabla de instalaciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private LocalDateTime obtenerFechaHoraIngreso() {
        return obtenerFechaHoraIngreso(true);
    }
    
    private LocalDateTime obtenerFechaHoraIngreso(boolean mostrarErrores) {
        try {
            Date fecha = vista.getGetDateChooserFechaYHoraIngreso().getDate();
            int hora = (Integer) vista.getSpinnerHora().getValue();
            int minutos = (Integer) vista.getSpinnerMinutos().getValue();

            if (fecha == null) {
                if (mostrarErrores) {
                    JOptionPane.showMessageDialog(null,"Debe seleccionar una fecha de ingreso.","Error", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }

            if (hora < 7 || hora > 20) {
                if (mostrarErrores) {
                    JOptionPane.showMessageDialog(null, "El horario debe ser entre 7 y 20.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }

            if (minutos < 0 || minutos > 59) {
                if (mostrarErrores) {
                    JOptionPane.showMessageDialog(null, "Los minutos deben estar entre 0 y 59.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }

            LocalDate localDate = fecha.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            return LocalDateTime.of(localDate, LocalTime.of(hora, minutos));

        } catch (Exception ex) {
            if (mostrarErrores) {
                JOptionPane.showMessageDialog(null, "Error al obtener fecha/hora: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
    }

    public void actualizarFechaHoraSalidaYCosto() {
        try {
            Object itemSeleccionado = vista.getCbTratamiento().getSelectedItem();
            
            // Verificar si es el placeholder
            if (itemSeleccionado == null || itemSeleccionado instanceof String) {
                return;
            }
            
            Tratamiento t = (Tratamiento) itemSeleccionado;

            LocalDateTime ingreso = obtenerFechaHoraIngreso(false);
            if (ingreso == null) {
                return;
            }

            //  tratamiento en minutos
            int minutosTratamiento = t.getDuracion().toSecondOfDay() / 60;

            // cada instalación = 30 minutos
            int minutosInstalaciones = instalacionesSesion.size() * 30;

            LocalDateTime salida = ingreso.plusMinutes(minutosTratamiento + minutosInstalaciones);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            vista.getLabelFechaHoraFinal()
                    .setText("Fecha y hora de salida: " + salida.format(fmt));

            // costo: tratamiento + instalaciones
            double costo = t.getCosto();
            for (Instalacion i : instalacionesSesion) {
                costo += i.getPrecio();
            }

            vista.getLabelCosto().setText(String.format("Costo Spa Total: $ %.2f", costo));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al calcular salida/costo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void guardarSesion() {
        try {
            if (clienteSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Object itemConsultorio = vista.getCbConsultorio().getSelectedItem();
            Object itemMasajista = vista.getCbMasajista().getSelectedItem();
            Object itemTratamiento = vista.getCbTratamiento().getSelectedItem();

            // Verificar que no sean placeholders
            if (itemConsultorio == null || itemConsultorio instanceof String ||
                itemMasajista == null || itemMasajista instanceof String ||
                itemTratamiento == null || itemTratamiento instanceof String) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar consultorio, masajista y tratamiento.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Consultorio consultorio = (Consultorio) itemConsultorio;
            Masajista masajista = (Masajista) itemMasajista;
            Tratamiento tratamiento = (Tratamiento) itemTratamiento;

            LocalDateTime ingreso = obtenerFechaHoraIngreso();
            if (ingreso == null) {
                return;
            }

            int minutosTratamiento = tratamiento.getDuracion().toSecondOfDay() / 60;
            int minutosInstalaciones = instalacionesSesion.size() * 30;
            LocalDateTime salida = ingreso.plusMinutes(minutosTratamiento + minutosInstalaciones);

            ArrayList<Instalacion> instalacionesParaSesion = new ArrayList<Instalacion>();
            for (Instalacion ins : instalacionesSesion) {
                instalacionesParaSesion.add(ins);
            }

            Sesion s = new Sesion(
                    ingreso,
                    salida,
                    tratamiento,
                    consultorio,
                    masajista,
                    null, // DiaDeSpa se setea cuando guardamos el dia, un alma por un alma
                    instalacionesParaSesion,
                    true
            );

            sesionesDia.add(s);
            actualizarTablaSesiones();

            // Limpio instalaciones para la proxima sesión
            instalacionesSesion.clear();
            actualizarTablaInstalaciones();
            actualizarFechaHoraSalidaYCosto();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la sesión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaSesiones() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) vista.getTableSesiones().getModel();
            modelo.setRowCount(0);

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // ------------------------ ACA ---------------------------------
            for (Sesion s : sesionesDia) {
                modelo.addRow(new Object[]{
                    s.getFechaHoraInicio().format(fmt),
                    s.getFechaHoraFinal().format(fmt),
                    s.getTratamiento().getNombre(),
                    s.getConsultorio().getApto(),
                    s.getMasajista().getNombre() + " " + s.getMasajista().getApellido()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tabla de sesiones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void guardarDiaDeSpa() {
        try {
            if (clienteSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (sesionesDia.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe cargar al menos una sesión.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String preferencias = vista.getTxtPreferencias().getText();

            // Tomo la fecha/hora de la primera sesipn como fecha del pack, el id del dia de spa
            LocalDateTime fechaPack = sesionesDia.get(0).getFechaHoraInicio();

            double montoTotal = calcularMontoTotalDia();

            DiaDeSpa dia = new DiaDeSpa();

            dia.setFechayhora(fechaPack);
            dia.setPreferencias(preferencias);
            dia.setCliente(clienteSeleccionado);
            dia.setSesiones(new ArrayList<>(sesionesDia));
            dia.setMonto(montoTotal);
            dia.setEstado(true);

            diaDeSpaData.guardarDiaDeSpa(dia);

            //JOptionPane.showMessageDialog(null, "Día de Spa guardado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);

            limpiarPantalla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el Día de Spa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calcularMontoTotalDia() {
        double total = 0;

        for (Sesion s : sesionesDia) {
            total += s.getTratamiento().getCosto();
            if (s.getInsalaciones() != null) {
                for (Instalacion i : s.getInsalaciones()) {
                    total += i.getPrecio();
                }
            }
        }

        return total;
    }

    private void limpiarPantalla() {
        clienteSeleccionado = null;
        sesionesDia.clear();
        instalacionesSesion.clear();

        vista.getLabelNombreCliente().setText("Nombre:");
        vista.getLabelDniCliente().setText("DNI:");
        vista.getLabelTelefonoCliente().setText("Telefono:");
        vista.getLabelEdadCliente().setText("Edad:");
        vista.getTxtAfecciones().setText("");
        vista.getTxtPreferencias().setText("");
        vista.getLabelInformacionDeLaInstalacion().setText("");
        vista.getLabelFechaHoraFinal().setText("Fecha y hora de salida");
        vista.getLabelCosto().setText("Costo Spa Total: $");

        actualizarTablaInstalaciones();
        actualizarTablaSesiones();
    }
    
    public void abrirVistaCargarCliente(JDesktopPane escritorio) {
        try {
            // Verificar si la ventana ya está abierta
            JInternalFrame abierto = buscarFrame(escritorio, vistaCliente.class);
            if (abierto != null) {
                try {
                    abierto.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                }
                abierto.toFront();
                centrarFrame(escritorio, abierto);
                return;
            }

            // Crear y mostrar la nueva ventana
            vistaCliente frm = new vistaCliente();
            escritorio.add(frm);
            frm.pack();
            centrarFrame(escritorio, frm);
            frm.setVisible(true);
            try {
                frm.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al abrir la vista de cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JInternalFrame buscarFrame(JDesktopPane escritorio, Class<?> clase) {
        for (JInternalFrame frame : escritorio.getAllFrames()) {
            if (clase.isInstance(frame)) {
                return frame;
            }
        }
        return null;
    }

    private void centrarFrame(JDesktopPane escritorio, JInternalFrame frame) {
        java.awt.Dimension desktopSize = escritorio.getSize();
        java.awt.Dimension frameSize = frame.getSize();
        frame.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
        );
    }
}

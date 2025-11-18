/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/*
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
*/
import Persistencia.MasajistaData;
import Persistencia.VendedorData;
import constantes.*;
import entidades.Masajista;
import entidades.Vendedor;
import javax.swing.JOptionPane;
import vistas.vistaModificarEmpleado;
import control.ControlVistaEmpleados;

public class ControlModificarEmpleado {

    private vistaModificarEmpleado vista;
    private VendedorData vendedorData;
    private MasajistaData masajistaData;
    private ControlVistaEmpleados controlVistaEmpleados;
    private int idEmpleado;
    private String puestoOriginal;
    private Masajista masajistaOriginal;
    private Vendedor vendedorOriginal;

    public ControlModificarEmpleado(vistaModificarEmpleado vista, MasajistaData masajistaData, VendedorData vendedorData, ControlVistaEmpleados controlVistaEmpleados, int idEmpleado, String puestoOriginal) {
        this.vista = vista;
        this.masajistaData = masajistaData;
        this.vendedorData = vendedorData;
        this.controlVistaEmpleados = controlVistaEmpleados;
        this.idEmpleado = idEmpleado;
        this.puestoOriginal = puestoOriginal;
        cargarDatosEmpleado();
    }

    private void cargarDatosEmpleado() {
        try {
            
            // El combobox está deshabilitado, no se puede cambiar el puesto
            
            
            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puestoOriginal)) {
                masajistaOriginal = masajistaData.buscarMasajistaPorId(idEmpleado);
                if (masajistaOriginal != null) {
                    vista.getJtNombre().setText(masajistaOriginal.getNombre());
                    vista.getJtApellido().setText(masajistaOriginal.getApellido());
                    vista.getJtTelefono().setText(masajistaOriginal.getTelefono());
                    vista.getJtDni().setText(String.valueOf(masajistaOriginal.getDni()));
                    vista.getJtMatricula().setText(String.valueOf(masajistaOriginal.getMatricula()));
                    vista.getJcbEspecialidad().setSelectedItem(masajistaOriginal.getEspecialidad());
                    // Habilitar campos de masajista
                    vista.getJtMatricula().setEnabled(true);
                    vista.getJcbEspecialidad().setEnabled(true);
                }
            } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puestoOriginal)) {
                vendedorOriginal = vendedorData.buscarVendedorPorId(idEmpleado);
                if (vendedorOriginal != null) {
                    vista.getJtNombre().setText(vendedorOriginal.getNombre());
                    vista.getJtApellido().setText(vendedorOriginal.getApellido());
                    vista.getJtTelefono().setText(vendedorOriginal.getTelefono());
                    vista.getJtDni().setText(String.valueOf(vendedorOriginal.getDni()));
                    vista.getJtMatricula().setText("");
                    vista.getJcbEspecialidad().setSelectedIndex(-1);
                    // Deshabilitar campos de masajista para vendedor
                    vista.getJtMatricula().setEnabled(false);
                    vista.getJcbEspecialidad().setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void preCargarCbPuesto() {
        vista.getJcbPuesto().removeAllItems();
        vista.getJcbPuesto().addItem(PuestosDeTrabajo.MASAJISTA.getPuesto());
        vista.getJcbPuesto().addItem(PuestosDeTrabajo.VENDEDOR.getPuesto());
    }

    public void preCargarCbEspecialidad() {
        vista.getJcbEspecialidad().removeAllItems();
        vista.getJcbEspecialidad().addItem(Especialidades.CORPORAL.getEspecialidad());
        vista.getJcbEspecialidad().addItem(Especialidades.ESTETICO.getEspecialidad());
        vista.getJcbEspecialidad().addItem(Especialidades.FACIAL.getEspecialidad());
        vista.getJcbEspecialidad().addItem(Especialidades.RELAJACION.getEspecialidad());
    }

    public void actualizarCamposSegunPuesto() {
        // Obtener el puesto seleccionado del combobox
        String puestoSeleccionado = (String) vista.getJcbPuesto().getSelectedItem();
        
        if (puestoSeleccionado == null) {
            return;
        }
        
        if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puestoSeleccionado)) {
            vista.getJtMatricula().setEnabled(false);
            vista.getJcbEspecialidad().setEnabled(false);
            vista.getJtMatricula().setText("");
            vista.getJcbEspecialidad().setSelectedIndex(-1);
        } else if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puestoSeleccionado)) {
            vista.getJtMatricula().setEnabled(true);
            vista.getJcbEspecialidad().setEnabled(true);
            // Si había datos originales de masajista, restaurarlos
            if (masajistaOriginal != null) {
                vista.getJtMatricula().setText(String.valueOf(masajistaOriginal.getMatricula()));
                vista.getJcbEspecialidad().setSelectedItem(masajistaOriginal.getEspecialidad());
            }
        }
    }

    public void modificarEmpleado() {
        try {
            java.util.ArrayList<String> errores = new java.util.ArrayList<>();
            
            // Obtener valores de los campos
            String nombre = vista.getJtNombre().getText().trim();
            String apellido = vista.getJtApellido().getText().trim();
            String telefono = vista.getJtTelefono().getText().trim();
            String dniStr = vista.getJtDni().getText().trim();
            
            // Obtener el puesto seleccionado del combobox
            String puesto = (String) vista.getJcbPuesto().getSelectedItem();
            
            String matriculaStr = vista.getJtMatricula().getText().trim();
            String especialidad = (String) vista.getJcbEspecialidad().getSelectedItem();
            
            // Validar puesto
            if (puesto == null || puesto.isEmpty()) {
                errores.add("• Debe seleccionar un puesto.");
            }
            
            // Validar nombre (máximo 60 caracteres, solo letras y espacios)
            if (nombre.isEmpty()) {
                errores.add("• El nombre no puede estar vacío.");
            } else {
                if (nombre.length() > 60) {
                    errores.add("• El nombre no puede tener más de 60 caracteres.");
                }
                if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) {
                    errores.add("• El nombre solo puede contener letras y espacios.");
                }
            }

            // Validar apellido (máximo 60 caracteres, solo letras y espacios)
            if (apellido.isEmpty()) {
                errores.add("• El apellido no puede estar vacío.");
            } else {
                if (apellido.length() > 60) {
                    errores.add("• El apellido no puede tener más de 60 caracteres.");
                }
                if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) {
                    errores.add("• El apellido solo puede contener letras y espacios.");
                }
            }

            // Validar teléfono (8 a 20 números)
            if (telefono.isEmpty()) {
                errores.add("• El teléfono no puede estar vacío.");
            } else if (!telefono.matches("\\d{8,20}")) {
                errores.add("• El teléfono debe tener entre 8 y 20 números.");
            }

            // Validar DNI (exactamente 8 números)
            if (dniStr.isEmpty()) {
                errores.add("• El DNI no puede estar vacío.");
            } else if (!dniStr.matches("\\d{8}")) {
                errores.add("• El DNI debe tener exactamente 8 números.");
            } else {
                try {
                    long dniLong = Long.parseLong(dniStr);
                    if (dniLong > Integer.MAX_VALUE) {
                        errores.add("• El DNI es demasiado grande (máximo: " + Integer.MAX_VALUE + ").");
                    } else {
                        // Validar que el DNI no esté en uso por otro empleado (excepto el actual)
                        // Cuando se cambia de puesto, permitir que el mismo empleado tenga el DNI en ambas tablas
                        int dni = (int) dniLong;
                        if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puesto)) {
                            Masajista masajistaConDNI = masajistaData.buscarMasajistaPorDNI(dni);
                            if (masajistaConDNI != null && masajistaConDNI.getIdEmpleado() != idEmpleado) {
                                errores.add("• El DNI ya está registrado para otro masajista.");
                            }
                            // Si el vendedor con este DNI es el mismo empleado que se está modificando, permitirlo (cambio de puesto)
                            Vendedor vendedorConDNI = vendedorData.buscarVendedorPorDNI(dni);
                            if (vendedorConDNI != null && vendedorConDNI.getIdEmpleado() != idEmpleado) {
                                errores.add("• El DNI ya está registrado para otro vendedor.");
                            }
                        } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puesto)) {
                            Vendedor vendedorConDNI = vendedorData.buscarVendedorPorDNI(dni);
                            if (vendedorConDNI != null && vendedorConDNI.getIdEmpleado() != idEmpleado) {
                                errores.add("• El DNI ya está registrado para otro vendedor.");
                            }
                            // Si el masajista con este DNI es el mismo empleado que se está modificando, permitirlo (cambio de puesto)
                            Masajista masajistaConDNI = masajistaData.buscarMasajistaPorDNI(dni);
                            if (masajistaConDNI != null && masajistaConDNI.getIdEmpleado() != idEmpleado) {
                                errores.add("• El DNI ya está registrado para otro masajista.");
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    errores.add("• El DNI debe ser un número válido.");
                }
            }

            // Validar matrícula y especialidad solo si es masajista
            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puesto)) {
                if (matriculaStr.isEmpty()) {
                    errores.add("• La matrícula no puede estar vacía.");
                } else if (!matriculaStr.matches("\\d{4,11}")) {
                    errores.add("• La matrícula debe tener entre 4 y 11 números.");
                } else {
                    try {
                        long matriculaLong = Long.parseLong(matriculaStr);
                        if (matriculaLong > Integer.MAX_VALUE) {
                            errores.add("• La matrícula es demasiado grande (máximo: " + Integer.MAX_VALUE + ").");
                        } else {
                            // Validar que la matrícula no esté en uso por otro masajista
                            int matricula = (int) matriculaLong;
                            Masajista masajistaConMatricula = masajistaData.buscarMasajistaPorMatricula(matricula);
                            if (masajistaConMatricula != null && masajistaConMatricula.getIdEmpleado() != idEmpleado) {
                                errores.add("• La matrícula ya está registrada para otro masajista.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        errores.add("• La matrícula debe ser un número válido.");
                    }
                }

                if (especialidad == null || especialidad.isEmpty()) {
                    errores.add("• Debe seleccionar una especialidad.");
                }
            }

            // Si hay errores, mostrarlos todos de una vez
            if (!errores.isEmpty()) {
                StringBuilder mensaje = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");
                for (String error : errores) {
                    mensaje.append(error).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensaje.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si hubo cambios (el puesto NO puede cambiar, está deshabilitado)
            boolean hayCambios = false;
            
            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puestoOriginal)) {
                if (masajistaOriginal == null) {
                    hayCambios = true;
                } else {
                    hayCambios = !nombre.equals(masajistaOriginal.getNombre()) ||
                                  !apellido.equals(masajistaOriginal.getApellido()) ||
                                  !telefono.equals(masajistaOriginal.getTelefono()) ||
                                  Integer.parseInt(dniStr) != masajistaOriginal.getDni() ||
                                  Integer.parseInt(matriculaStr) != masajistaOriginal.getMatricula() ||
                                  !especialidad.equals(masajistaOriginal.getEspecialidad());
                }
            } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puestoOriginal)) {
                if (vendedorOriginal == null) {
                    hayCambios = true;
                } else {
                    hayCambios = !nombre.equals(vendedorOriginal.getNombre()) ||
                                  !apellido.equals(vendedorOriginal.getApellido()) ||
                                  !telefono.equals(vendedorOriginal.getTelefono()) ||
                                  Integer.parseInt(dniStr) != vendedorOriginal.getDni();
                }
            }

            if (!hayCambios) {
                JOptionPane.showMessageDialog(null, "No se modificaron ningún campo.\nEl empleado no fue modificado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Si hay cambios y los valores son correctos, guardar
            if (PuestosDeTrabajo.MASAJISTA.getPuesto().equals(puestoOriginal)) {
                int matricula = (int) Long.parseLong(matriculaStr);
                int dni = (int) Long.parseLong(dniStr);
                Masajista m = new Masajista(idEmpleado, matricula, nombre, apellido, telefono, dni, puestoOriginal, especialidad, masajistaOriginal != null ? masajistaOriginal.isEstado() : true);
                masajistaData.actualizarMasajista(m);
                JOptionPane.showMessageDialog(null, "Masajista modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else if (PuestosDeTrabajo.VENDEDOR.getPuesto().equals(puestoOriginal)) {
                int dni = (int) Long.parseLong(dniStr);
                Vendedor v = new Vendedor(idEmpleado, nombre, apellido, telefono, dni, puestoOriginal, vendedorOriginal != null ? vendedorOriginal.getEstado() : true);
                vendedorData.actualizarVendedor(v);
                JOptionPane.showMessageDialog(null, "Vendedor modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            // Actualizar la tabla en vistaEmpleados
            if (controlVistaEmpleados != null) {
                controlVistaEmpleados.actualizarTabla();
            }
            
            // Cerrar la ventana de modificación
            vista.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar los datos numéricos. Verifique que la matrícula y el DNI sean números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        vista.getJtNombre().setText("");
        vista.getJtApellido().setText("");
        vista.getJtTelefono().setText("");
        vista.getJtDni().setText("");
        vista.getJtMatricula().setText("");
        vista.getJcbPuesto().setSelectedIndex(-1);
        vista.getJcbEspecialidad().setSelectedIndex(-1);
    }
}


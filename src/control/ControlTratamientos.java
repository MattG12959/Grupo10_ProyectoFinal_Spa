/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import Persistencia.TratamientoData;
import constantes.TratamientosCorporales;
import constantes.TratamientosFaciales;
import constantes.Especialidades;
import entidades.Tratamiento;
import vistas.vistaTratamientos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Grupo10
 */
public class ControlTratamientos {
    
    private vistaTratamientos vista;
    private TratamientoData tratamientoData;
    private Tratamiento tratamientoSeleccionado;
    private boolean cargandoDesdeTabla = false;
    
    public ControlTratamientos(vistaTratamientos vista, TratamientoData tratamientoData) {
        this.vista = vista;
        this.tratamientoData = tratamientoData;
        this.tratamientoSeleccionado = null;
        inicializarVista();
    }
    
    /**
     * Inicializa la vista con los datos necesarios
     */
    private void inicializarVista() {
        cargarComboTipo();
        cargarTabla();
        configurarListeners();
    }
    


    /**
     * Carga el combo box de tipo con "Corporal" y "Facial"
     */
    public void cargarComboTipo() {
        vista.getJcbTipo().removeAllItems();
        vista.getJcbTipo().addItem("Seleccione un tipo...");
        vista.getJcbTipo().addItem(Especialidades.CORPORAL.getEspecialidad());
        vista.getJcbTipo().addItem(Especialidades.FACIAL.getEspecialidad());
    }
    
    /**
     * Carga el combo box de nombre según el tipo seleccionado
     */
    public void cargarComboNombre() {
        String tipoSeleccionado = (String) vista.getJcbTipo().getSelectedItem();
        vista.getJcbNombre().removeAllItems();
        
        if (tipoSeleccionado == null || "Seleccione un tipo...".equals(tipoSeleccionado)) {
            return;
        }
        
        if (Especialidades.CORPORAL.getEspecialidad().equals(tipoSeleccionado)) {
            // Cargar tratamientos corporales
            for (TratamientosCorporales tc : TratamientosCorporales.values()) {
                vista.getJcbNombre().addItem(tc.getNombre());
            }
        } else if (Especialidades.FACIAL.getEspecialidad().equals(tipoSeleccionado)) {
            // Cargar tratamientos faciales
            for (TratamientosFaciales tf : TratamientosFaciales.values()) {
                vista.getJcbNombre().addItem(tf.getNombre());
            }
        }
    }
    
    /**
     * Carga el detalle cuando se selecciona un nombre
     */
    public void cargarDetalle() {
        String nombreSeleccionado = (String) vista.getJcbNombre().getSelectedItem();
        if (nombreSeleccionado == null) {
            return;
        }
        
        String descripcion = null;
        String tipoSeleccionado = (String) vista.getJcbTipo().getSelectedItem();
        
        if (Especialidades.CORPORAL.getEspecialidad().equals(tipoSeleccionado)) {
            for (TratamientosCorporales tc : TratamientosCorporales.values()) {
                if (tc.getNombre().equals(nombreSeleccionado)) {
                    descripcion = tc.getDescripcion();
                    break;
                }
            }
        } else if (Especialidades.FACIAL.getEspecialidad().equals(tipoSeleccionado)) {
            for (TratamientosFaciales tf : TratamientosFaciales.values()) {
                if (tf.getNombre().equals(nombreSeleccionado)) {
                    descripcion = tf.getDescripcion();
                    break;
                }
            }
        }
        
        if (descripcion != null) {
            vista.getJtfDetalle().setText(descripcion);
        }
    }
    
    /**
     * Configura los listeners de los componentes
     */
    private void configurarListeners() {
        // Listener para el combo de tipo
        vista.getJcbTipo().addActionListener(e -> {
            // No procesar si estamos cargando desde la tabla
            if (cargandoDesdeTabla) {
                return;
            }
            
            String tipoSeleccionado = (String) vista.getJcbTipo().getSelectedItem();
            // Solo procesar si no es el item por defecto
            if (tipoSeleccionado != null && !"Seleccione un tipo...".equals(tipoSeleccionado)) {
                // Limpiar solo los campos que no sean el tipo
                vista.getJcbNombre().removeAllItems();
                vista.getJtfDetalle().setText("");
                vista.getJftfDuracion().setText("");
                vista.getJtfCosto().setText("");
                vista.getChboxActivo().setSelected(false);
                tratamientoSeleccionado = null;
                vista.getJtTratamiento().clearSelection();
                // Cargar los nombres según el tipo seleccionado
                cargarComboNombre();
            }
        });
        
        // Listener para el combo de nombre
        vista.getJcbNombre().addActionListener(e -> {
            cargarDetalle();
        });
        
        // Listener para selección de fila en la tabla
        vista.getJtTratamiento().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarTratamientoDesdeTabla();
            }
        });
    }
    
    /**
     * Carga los datos del tratamiento seleccionado en la tabla a los campos del formulario
     */
    private void cargarTratamientoDesdeTabla() {
        int filaSeleccionada = vista.getJtTratamiento().getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }
        cargandoDesdeTabla = true;
        
        try {
            DefaultTableModel modelo = (DefaultTableModel) vista.getJtTratamiento().getModel();
            int codigo = (Integer) modelo.getValueAt(filaSeleccionada, 0);
            
            tratamientoSeleccionado = tratamientoData.buscarTratamiento(codigo);
            if (tratamientoSeleccionado != null) {
                // Cargar código
                vista.getJtfCodigo().setText(String.valueOf(tratamientoSeleccionado.getCodTratam()));
                
                // Cargar tipo - primero obtener el tipo desde la BD
                String tipo = tratamientoSeleccionado.gettipoTratamiento();
                if (tipo != null) {
                    // Seleccionar el tipo en el combobox (esto disparará el listener, pero lo ignoraremos)
                    vista.getJcbTipo().setSelectedItem(tipo);
                    // Cargar los nombres correspondientes al tipo seleccionado
                    cargarComboNombre();
                    
                    // Cargar nombre después de que se haya poblado el combobox
                    String nombre = tratamientoSeleccionado.getNombre();
                    if (nombre != null) {
                        // Intentar seleccionar el nombre
                        vista.getJcbNombre().setSelectedItem(nombre);
                        // Cargar el detalle automáticamente
                        cargarDetalle();
                    }
                }
                
                // Cargar detalle (por si acaso no se cargó automáticamente)
                String detalle = tratamientoSeleccionado.getDetalle();
                if (detalle != null && !detalle.isEmpty()) {
                    vista.getJtfDetalle().setText(detalle);
                }
                
                // Cargar duración
                LocalTime duracion = tratamientoSeleccionado.getDuracion();
                if (duracion != null) {
                    vista.getJftfDuracion().setText(duracion.format(DateTimeFormatter.ofPattern("HH:mm")));
                } else {
                    vista.getJftfDuracion().setText("");
                }
                
                // Cargar costo
                vista.getJtfCosto().setText(String.valueOf(tratamientoSeleccionado.getCosto()));
                
                // Cargar estado
                vista.getChboxActivo().setSelected(tratamientoSeleccionado.isEstado());
            }
        } finally {
            // Restaurar el flag
            cargandoDesdeTabla = false;
        
        }
    }
    
    /**
     * Agrega un nuevo tratamiento a la base de datos
     */
    public void agregarTratamiento() {
        try {
            // Validaciones
            if (!validarCampos()) {
                return;
            }
            
            // Obtener valores del formulario
            String nombre = (String) vista.getJcbNombre().getSelectedItem();
            String detalle = vista.getJtfDetalle().getText().trim();
            String duracionStr = vista.getJftfDuracion().getText().trim();
            String costoStr = vista.getJtfCosto().getText().trim();
            boolean estado = vista.getChboxActivo().isSelected();
            
            // Parsear duración
            LocalTime duracion = parsearDuracion(duracionStr);
            if (duracion == null) {
                JOptionPane.showMessageDialog(null, "Formato de duración inválido. Use HH:mm (ej: 01:30)", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parsear costo
            double costo;
            try {
                costo = Double.parseDouble(costoStr);
                if (costo < 0) {
                    JOptionPane.showMessageDialog(null, "El costo no puede ser negativo.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El costo debe ser un número válido.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el tratamiento
            Tratamiento tratamiento = new Tratamiento();
            tratamiento.setNombre(nombre);
            tratamiento.settipoTratamiento(nombre); // Se usa el nombre para buscar en los enums
            tratamiento.setDetalle(detalle);
            tratamiento.setDuracion(duracion);
            tratamiento.setCosto(costo);
            tratamiento.setEstado(estado);
            
            // Guardar en la base de datos
            tratamientoData.cargaTratamiento(tratamiento);
            
            JOptionPane.showMessageDialog(null, "Tratamiento agregado correctamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos y actualizar tabla
            limpiarCampos();
            cargarTabla();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar tratamiento: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Edita un tratamiento existente
     */
    public void editarTratamiento() {
        try {
            if (tratamientoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un tratamiento de la tabla para editar.", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validaciones
            if (!validarCampos()) {
                return;
            }
            
            // Obtener valores del formulario
            String detalle = vista.getJtfDetalle().getText().trim();
            String duracionStr = vista.getJftfDuracion().getText().trim();
            String costoStr = vista.getJtfCosto().getText().trim();
            boolean estado = vista.getChboxActivo().isSelected();
            
            // Parsear duración
            LocalTime duracion = parsearDuracion(duracionStr);
            if (duracion == null) {
                JOptionPane.showMessageDialog(null, "Formato de duración inválido. Use HH:mm (ej: 01:30)", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parsear costo
            double costo;
            try {
                costo = Double.parseDouble(costoStr);
                if (costo < 0) {
                    JOptionPane.showMessageDialog(null, "El costo no puede ser negativo.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El costo debe ser un número válido.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Actualizar el tratamiento
            tratamientoSeleccionado.setDetalle(detalle);
            tratamientoSeleccionado.setDuracion(duracion);
            tratamientoSeleccionado.setCosto(costo);
            tratamientoSeleccionado.setEstado(estado);
            
            // Actualizar en la base de datos
            tratamientoData.actualizarTratamiento(tratamientoSeleccionado);
            
            JOptionPane.showMessageDialog(null, "Tratamiento actualizado correctamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar tabla
            cargarTabla();
            tratamientoSeleccionado = null;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar tratamiento: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carga todos los tratamientos en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getJtTratamiento().getModel();
        modelo.setRowCount(0); // Limpiar la tabla
        
        ArrayList<Tratamiento> tratamientos = tratamientoData.listarTodosTratamientos();
        
        for (Tratamiento t : tratamientos) {
            String duracionStr = "";
            if (t.getDuracion() != null) {
                duracionStr = t.getDuracion().format(DateTimeFormatter.ofPattern("HH:mm"));
            }
            
            String estadoStr = t.isEstado() ? "Activo" : "Inactivo";
            
            modelo.addRow(new Object[]{
                t.getCodTratam(),
                t.getNombre(),
                t.gettipoTratamiento(),
                t.getDetalle(),
                duracionStr,
                t.getCosto(),
                estadoStr
            });
        }
    }
    
    /**
     * Limpia los campos del formulario
     */
    public void limpiarCampos() {
        vista.getJtfCodigo().setText("");
        vista.getJcbTipo().setSelectedIndex(0);
        vista.getJcbNombre().removeAllItems();
        vista.getJtfDetalle().setText("");
        vista.getJftfDuracion().setText("");
        vista.getJtfCosto().setText("");
        vista.getChboxActivo().setSelected(false);
        tratamientoSeleccionado = null;
        vista.getJtTratamiento().clearSelection();
    }
    
    /**
     * Valida que los campos requeridos estén completos
     */
    private boolean validarCampos() {
        String tipo = (String) vista.getJcbTipo().getSelectedItem();
        String nombre = (String) vista.getJcbNombre().getSelectedItem();
        String duracion = vista.getJftfDuracion().getText().trim();
        String costo = vista.getJtfCosto().getText().trim();
        
        if (tipo == null || "Seleccione un tipo...".equals(tipo)) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de tratamiento.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (nombre == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un nombre de tratamiento.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (duracion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la duración del tratamiento.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (costo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el costo del tratamiento.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Parsea una cadena de duración en formato HH:mm a LocalTime
     */
    private LocalTime parsearDuracion(String duracionStr) {
        try {
            if (duracionStr.matches("\\d{1,2}:\\d{2}")) {
                String[] partes = duracionStr.split(":");
                int horas = Integer.parseInt(partes[0]);
                int minutos = Integer.parseInt(partes[1]);
                
                if (horas >= 0 && horas < 24 && minutos >= 0 && minutos < 60) {
                    return LocalTime.of(horas, minutos);
                }
            }
        } catch (Exception e) {
            // Retornar null si hay error
        }
        return null;
    }
}


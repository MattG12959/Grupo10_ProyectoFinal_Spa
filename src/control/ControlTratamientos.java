/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import Persistencia.TratamientoData;
import constantes.TratamientosCorporales;
import constantes.TratamientosFaciales;
import constantes.TratamientosRelajacion;
import constantes.TratamientosEsteticos;
import constantes.Especialidades;
import entidades.Tratamiento;
import vistas.vistaTratamientos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public class ControlTratamientos {
    
    private vistaTratamientos vista;
    private TratamientoData tratamientoData;
    private Tratamiento tratamientoSeleccionado;
    private boolean cargandoDesdeTabla = false;
    
    private String detalleOriginal;
    private LocalTime duracionOriginal;
    private double costoOriginal;
    private boolean estadoOriginal;
    
    
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
        vista.getJcbNombre().setEnabled(false);
        deshabilitarCamposDependientes();
        cargarTabla();
        configurarListeners();
    }
    /**
     * Habilita los campos que requieren tipo y nombre seleccionados
     */
    private void habilitarCamposDependientes() {
        vista.getJtfDetalle().setEnabled(true);
        vista.getJftfDuracion().setEnabled(true);
        vista.getJtfCosto().setEnabled(true);
        vista.getChboxActivo().setEnabled(true);
    }
    
    /**
     * Deshabilita los campos que requieren tipo y nombre seleccionados
     */
    private void deshabilitarCamposDependientes() {
        vista.getJtfDetalle().setEnabled(false);
        vista.getJftfDuracion().setEnabled(false);
        vista.getJtfCosto().setEnabled(false);
        vista.getChboxActivo().setEnabled(false);
    }
    


    /**
     * Carga el combo box de tipo 
     */
    public void cargarComboTipo() {
        vista.getJcbTipo().removeAllItems();
        vista.getJcbTipo().addItem("Seleccione un tipo...");
        vista.getJcbTipo().addItem(Especialidades.CORPORAL.getEspecialidad());
        vista.getJcbTipo().addItem(Especialidades.FACIAL.getEspecialidad());
        vista.getJcbTipo().addItem(Especialidades.RELAJACION.getEspecialidad());
        vista.getJcbTipo().addItem(Especialidades.ESTETICO.getEspecialidad());
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
        // Agregar el placeholder como primer elemento
        vista.getJcbNombre().addItem("Seleccione un tratamiento");
        
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
        } else if (Especialidades.RELAJACION.getEspecialidad().equals(tipoSeleccionado)) {
            // Cargar tratamientos de relajación
            for (TratamientosRelajacion tr : TratamientosRelajacion.values()) {
                vista.getJcbNombre().addItem(tr.getNombre());
            }
        } else if (Especialidades.ESTETICO.getEspecialidad().equals(tipoSeleccionado)) {
            // Cargar tratamientos estéticos
            for (TratamientosEsteticos te : TratamientosEsteticos.values()) {
                vista.getJcbNombre().addItem(te.getNombre());
            }
        }
    }
    
    /**
     * Carga el detalle cuando se selecciona un nombre
     */
    public void cargarDetalle() {
        String nombreSeleccionado = (String) vista.getJcbNombre().getSelectedItem();
        
        if (nombreSeleccionado == null || "Seleccione un tratamiento".equals(nombreSeleccionado)) {
            // Limpiar el detalle si se selecciona el placeholder
            vista.getJtfDetalle().setText("");
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
        } else if (Especialidades.RELAJACION.getEspecialidad().equals(tipoSeleccionado)) {
            for (TratamientosRelajacion tr : TratamientosRelajacion.values()) {
                if (tr.getNombre().equals(nombreSeleccionado)) {
                    descripcion = tr.getDescripcion();
                    break;
                }
            }
        } else if (Especialidades.ESTETICO.getEspecialidad().equals(tipoSeleccionado)) {
            for (TratamientosEsteticos te : TratamientosEsteticos.values()) {
                if (te.getNombre().equals(nombreSeleccionado)) {
                    descripcion = te.getDescripcion();
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
                // Habilitar el combobox de nombre cuando se selecciona un tipo válido
                vista.getJcbNombre().setEnabled(true);
                // Limpiar solo los campos que no sean el tipo
                vista.getJcbNombre().removeAllItems();
                vista.getJtfDetalle().setText("");
                vista.getJftfDuracion().setText("");
                vista.getJtfCosto().setText("");
                vista.getChboxActivo().setSelected(false);
                tratamientoSeleccionado = null;
                vista.getJtTratamiento().clearSelection();
                deshabilitarCamposDependientes();
                cargarComboNombre();
            } else {
                // Deshabilitar el combobox de nombre si no hay tipo seleccionado
                vista.getJcbNombre().setEnabled(false);
                vista.getJcbNombre().removeAllItems();
                vista.getJcbNombre().setSelectedIndex(-1);
                deshabilitarCamposDependientes();
            }
        });
        
         // Listener para el combo de nombre
        vista.getJcbNombre().addActionListener(e -> {
            // No procesar si estamos cargando desde la tabla
            if (cargandoDesdeTabla) {
                return;
            }
            
            String nombreSeleccionado = (String) vista.getJcbNombre().getSelectedItem();
            String tipoSeleccionado = (String) vista.getJcbTipo().getSelectedItem();
            
            // Verificar si tipo y nombre están seleccionados (no son placeholders)
            if (tipoSeleccionado != null && !"Seleccione un tipo...".equals(tipoSeleccionado) 
                && nombreSeleccionado != null && !"Seleccione un tratamiento".equals(nombreSeleccionado)) {
                // Habilitar campos dependientes cuando ambos están seleccionados
                habilitarCamposDependientes();
                cargarDetalle();
            } else {
                // Deshabilitar campos dependientes si no hay nombre válido seleccionado
                deshabilitarCamposDependientes();
                if (nombreSeleccionado == null || "Seleccione un tratamiento".equals(nombreSeleccionado)) {
                    vista.getJtfDetalle().setText("");
                }
            }
        });
        
        // Listener para selección de fila en la tabla
        vista.getJtTratamiento().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarTratamientoDesdeTabla();
            }
        });
        
        // Listener para el botón de búsqueda por código
        javax.swing.JButton btnBusquedaCodigo = vista.getJbBusquedaTratamCodigo();
        if (btnBusquedaCodigo != null) {
            // Limpiar listeners previos si los hay
            for (java.awt.event.ActionListener al : btnBusquedaCodigo.getActionListeners()) {
                btnBusquedaCodigo.removeActionListener(al);
            }
            btnBusquedaCodigo.addActionListener(e -> {
                buscarPorCodigos();
            });
        }
        
        // Listener para el botón de búsqueda por nombre
        javax.swing.JButton btnBusquedaNombre = vista.getJbBusquedaTratamNombre();
        if (btnBusquedaNombre != null) {
            // Limpiar listeners previos si los hay
            for (java.awt.event.ActionListener al : btnBusquedaNombre.getActionListeners()) {
                btnBusquedaNombre.removeActionListener(al);
            }
            btnBusquedaNombre.addActionListener(e -> {
                buscarPorNombre();
            });
        }
        
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
            
                
                // Cargar tipo - primero obtener el tipo desde la BD
                String tipo = tratamientoSeleccionado.gettipoTratamiento();
                if (tipo != null) {
                    // Seleccionar el tipo en el combobox (esto disparará el listener, pero lo ignoraremos)
                    vista.getJcbTipo().setSelectedItem(tipo);
                    // Habilitar el combobox de nombre ya que hay un tipo seleccionado
                    vista.getJcbNombre().setEnabled(true);
                    // Cargar los nombres correspondientes al tipo seleccionado
                    cargarComboNombre();
                    
                    // Cargar nombre después de que se haya poblado el combobox
                    String nombre = tratamientoSeleccionado.getNombre();
                    if (nombre != null) {
                        // Intentar seleccionar el nombre
                        vista.getJcbNombre().setSelectedItem(nombre);
                        // Habilitar campos dependientes ya que tipo y nombre están seleccionados
                        habilitarCamposDependientes();
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
                
                // Guardar valores originales para comparar al editar
                detalleOriginal = tratamientoSeleccionado.getDetalle();
                duracionOriginal = tratamientoSeleccionado.getDuracion();
                costoOriginal = tratamientoSeleccionado.getCosto();
                estadoOriginal = tratamientoSeleccionado.isEstado();
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
            String tipo = (String) vista.getJcbTipo().getSelectedItem();
            String nombre = (String) vista.getJcbNombre().getSelectedItem();
            String detalle = vista.getJtfDetalle().getText().trim();
            String duracionStr = vista.getJftfDuracion().getText().trim();
            String costoStr = vista.getJtfCosto().getText().trim();
            boolean estado = vista.getChboxActivo().isSelected();
            
             // Validar que no exista ya un tratamiento con la misma combinación de tipo + nombre
            Tratamiento tratamientoExistente = tratamientoData.buscarTratamientoPorNombreYTipo(nombre, tipo);
            if (tratamientoExistente != null) {
                JOptionPane.showMessageDialog(null, 
                    "El Tratamiento ya existe con código " + tratamientoExistente.getCodTratam() + ".\n" +
                    "Si desea editarlo, selecciónelo de la tabla, cambie los datos y haga clic en Editar.", 
                    "Error - Tratamiento Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
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
            String detalleFinal = detalle.trim();
            tratamiento.setDetalle(detalleFinal);
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
            
             // Comparar con valores originales
            boolean hayCambios = false;
            
            // Comparar detalle
            String detalleOriginalStr = (detalleOriginal != null) ? detalleOriginal.trim() : "";
            String detalleActual = detalle.trim();
            if (!detalleActual.equals(detalleOriginalStr)) {
                hayCambios = true;
            }
            
            // Comparar duración
            if (duracionOriginal == null || !duracionOriginal.equals(duracion)) {
                hayCambios = true;
            }
            
            // Comparar costo (con tolerancia para decimales)
            if (Math.abs(costo - costoOriginal) > 0.001) {
                hayCambios = true;
            }
            
            // Comparar estado
            if (estado != estadoOriginal) {
                hayCambios = true;
            }
            
            // Si no hay cambios, mostrar mensaje y retornar
            if (!hayCambios) {
                JOptionPane.showMessageDialog(null, 
                    "No se han realizado cambios en los campos detalle, duración, costo y activo.\n" +
                    "El tratamiento no ha sido editado.", 
                    "Sin cambios", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
                      
            
            // Actualizar el tratamiento
            String detalleFinal = detalle.trim();
            tratamientoSeleccionado.setDetalle(detalleFinal);
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
            
            // Limpiar valores originales
            detalleOriginal = null;
            duracionOriginal = null;
            costoOriginal = 0.0;
            estadoOriginal = false;
            
            
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
        actualizarTablaConTratamientos(tratamientos);
    }
    
    /**
     * Actualiza la tabla con una lista de tratamientos
     */
    private void actualizarTablaConTratamientos(ArrayList<Tratamiento> tratamientos) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getJtTratamiento().getModel();
        modelo.setRowCount(0); // Limpiar la tabla
        
        
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
     * Busca tratamientos por códigos múltiples (solo con botón)
     */
    private void buscarPorCodigos() {
        String codigosStr = vista.getJtfBusquedaCodigo().getText().trim();
        
        if (codigosStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Debe ingresar al menos un código para buscar.\nFormato: 1,2,5,7", 
                "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar formato: solo números y comas
        if (!codigosStr.matches("^[0-9,]+$")) {
            JOptionPane.showMessageDialog(null, 
                "El formato de códigos es inválido.\nSolo se permiten números y comas (ej: 1,2,5,7).", 
                "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar que no haya comas consecutivas o al inicio/final
        if (codigosStr.startsWith(",") || codigosStr.endsWith(",") || codigosStr.contains(",,")) {
            JOptionPane.showMessageDialog(null, 
                "El formato de códigos es inválido.\nNo se permiten comas al inicio, final o consecutivas.\nFormato correcto: 1,2,5,7", 
                "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            ArrayList<Tratamiento> tratamientos = tratamientoData.buscarTratamientosPorCodigos(codigosStr);
            
            if (tratamientos.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron tratamientos con los códigos especificados.", 
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar la tabla
                actualizarTablaConTratamientos(tratamientos);
            } else {
                actualizarTablaConTratamientos(tratamientos);
                JOptionPane.showMessageDialog(null, 
                    "Se encontraron " + tratamientos.size() + " tratamiento(s).", 
                    "Búsqueda exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al buscar tratamientos por códigos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Busca tratamientos por nombre
     */
    private void buscarPorNombre() {
        String nombrePatron = vista.getJtfBusquedaNombre().getText().trim();
        
        // Si el campo está vacío, cargar todos los tratamientos
        if (nombrePatron.isEmpty()) {
            cargarTabla();
            return;
        }
        
        // Validar caracteres permitidos: letras, números, espacios y los caracteres especiales: . , ; -
        if (!nombrePatron.matches("^[a-zA-Z0-9\\s.,;-]+$")) {
            // Encontrar caracteres no válidos para mostrarlos en el mensaje
            StringBuilder caracteresInvalidos = new StringBuilder();
            for (char c : nombrePatron.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) 
                    && c != '.' && c != ',' && c != ';' && c != '-') {
                    if (caracteresInvalidos.indexOf(String.valueOf(c)) == -1) {
                        if (caracteresInvalidos.length() > 0) {
                            caracteresInvalidos.append(", ");
                        }
                        caracteresInvalidos.append("'").append(c).append("'");
                    }
                }
            }
            
            JOptionPane.showMessageDialog(null, 
                "El campo de búsqueda por nombre contiene caracteres no válidos: " + caracteresInvalidos.toString() + 
                "\n\nSolo se permiten letras, números, espacios y los siguientes caracteres especiales: . , ; -", 
                "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            ArrayList<Tratamiento> tratamientos = tratamientoData.buscarTratamientosPorNombre(nombrePatron);
            
            if (tratamientos.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron tratamientos con el nombre: \"" + nombrePatron + "\"", 
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar la tabla
                actualizarTablaConTratamientos(tratamientos);
            } else {
                actualizarTablaConTratamientos(tratamientos);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al buscar tratamientos por nombre: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    
    }
    /**
     * Limpia los campos del formulario
     */
    public void limpiarCampos() {
        vista.getJtfCodigo().setText("");
        vista.getJcbTipo().setSelectedIndex(0);
        vista.getJcbNombre().removeAllItems();
        vista.getJcbNombre().setEnabled(false);
        vista.getJtfDetalle().setText("");
        vista.getJftfDuracion().setText("");
        vista.getJtfCosto().setText("");
        vista.getChboxActivo().setSelected(false);
        tratamientoSeleccionado = null;
        vista.getJtTratamiento().clearSelection();
        deshabilitarCamposDependientes();
        detalleOriginal = null;
        duracionOriginal = null;
        costoOriginal = 0.0;
        estadoOriginal = false; 
        vista.getJtfBusquedaCodigo().setText("");
        vista.getJtfBusquedaNombre().setText("");
        cargarTabla();
    }
    
    
    /**
     * Valida que los campos requeridos estén completos
     */
    private boolean validarCampos() {
        String tipo = (String) vista.getJcbTipo().getSelectedItem();
        String nombre = (String) vista.getJcbNombre().getSelectedItem();
        String duracion = vista.getJftfDuracion().getText().trim();
        String costo = vista.getJtfCosto().getText().trim();
        String detalle = vista.getJtfDetalle().getText().trim();
        
        if (tipo == null || "Seleccione un tipo...".equals(tipo)) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de tratamiento.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (nombre == null || "Seleccione un tratamiento".equals(nombre)) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un nombre de tratamiento.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
         // Verificar campos faltantes y mostrar un solo mensaje
        java.util.ArrayList<String> camposFaltantes = new java.util.ArrayList<>();
        
        if (detalle == null || detalle.isEmpty()) {
            camposFaltantes.add("Detalle");
        }
        
        if (duracion.isEmpty()) {
            camposFaltantes.add("Duración");
        }
        
        if (costo.isEmpty()) {
            camposFaltantes.add("Costo");
        }
        
         if (!camposFaltantes.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Los siguientes campos son obligatorios y están vacíos:\n\n");
            for (int i = 0; i < camposFaltantes.size(); i++) {
                mensaje.append("• ").append(camposFaltantes.get(i));
                if (i < camposFaltantes.size() - 1) {
                    mensaje.append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, mensaje.toString(), 
                "Error - Campos Faltantes", JOptionPane.ERROR_MESSAGE);
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


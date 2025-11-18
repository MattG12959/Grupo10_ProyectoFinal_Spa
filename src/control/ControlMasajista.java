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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import vistas.vistaCargarMasajista;

public class ControlMasajista {

    private vistaCargarMasajista vista;
    private MasajistaData masajistaData;
    private VendedorData vendedorData;
    private Masajista masajista;

    public ControlMasajista(vistaCargarMasajista vista, MasajistaData masajistaData, VendedorData vendedorData) {
        this.vista = vista;
        this.masajistaData = masajistaData;
        this.vendedorData = vendedorData;
    }

    public void cargarMasajista() {
        try {
            java.util.ArrayList<String> errores = new java.util.ArrayList<>();
            
            // Obtener valores de los campos
            String nombre = vista.getTxtNombre().getText().trim();
            String apellido = vista.getTxtApellido().getText().trim();
            String telefono = vista.getTxtTelefono().getText().trim();
            String dniStr = vista.getTxtDni().getText().trim();
            String matriculaStr = vista.getTxtMatricula().getText().trim();
            String especialidad = (String) vista.getCbEspecialidades().getSelectedItem();
            
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
                errores.add("• El DNI debe tener exactamente 11 números.");
            } else {
                try {
                    long dniLong = Long.parseLong(dniStr);
                    if (dniLong > Integer.MAX_VALUE) {
                        errores.add("• El DNI es demasiado grande (máximo: " + Integer.MAX_VALUE + ").");
                    }
                } catch (NumberFormatException e) {
                    errores.add("• El DNI debe ser un número válido.");
                }
            }

            // Validar matrícula (4 a 11 números)
            if (matriculaStr.isEmpty()) {
                errores.add("• La matrícula no puede estar vacía.");
            } else if (!matriculaStr.matches("\\d{4,11}")) {
                errores.add("• La matrícula debe tener entre 4 y 11 números.");
            } else {
                try {
                    long matriculaLong = Long.parseLong(matriculaStr);
                    if (matriculaLong > Integer.MAX_VALUE) {
                        errores.add("• La matrícula es demasiado grande (máximo: " + Integer.MAX_VALUE + ").");
                    }
                } catch (NumberFormatException e) {
                    errores.add("• La matrícula debe ser un número válido.");
                }
            }

            // Validar especialidad
            if (especialidad == null || especialidad.isEmpty()) {
                errores.add("• Debe seleccionar una especialidad.");
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

            // Si no hay errores, proceder a crear el masajista
            int matricula = (int) Long.parseLong(matriculaStr);
            int dni = (int) Long.parseLong(dniStr);

            // Crear el masajista
            masajista = new Masajista(matricula, nombre, apellido, telefono, dni, PuestosDeTrabajo.MASAJISTA.getPuesto(), especialidad, true);
            
            masajistaData.altaMasajista(masajista);
            //JOptionPane.showMessageDialog(null, "Masajista cargado correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar los datos numéricos. Verifique que la matrícula y el DNI sean números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el masajista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void preCargarCb() {
        String[] especialidades = {Especialidades.CORPORAL.getEspecialidad(), Especialidades.ESTETICO.getEspecialidad(), Especialidades.FACIAL.getEspecialidad(), Especialidades.RELAJACION.getEspecialidad()};

        JComboBox<String> combo = vista.getCbEspecialidades();
        combo.removeAllItems(); // Limpia el combo por si ya tenía algo

        for (String esp : especialidades) {
            combo.addItem(esp);
        }

        vista.setCbEspecialidades(combo);
    }
}

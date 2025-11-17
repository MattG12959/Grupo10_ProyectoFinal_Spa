package control;
/*
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
*/
import Persistencia.MasajistaData;
import constantes.*;
import entidades.Masajista;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import vistas.vistaCargarMasajista;

public class ControlMasajista {

    private vistaCargarMasajista vista;
    private MasajistaData masajistaData;
    private Masajista masajista;

    public ControlMasajista(vistaCargarMasajista vista, MasajistaData masajistaData) {
        this.vista = vista;
        this.masajistaData = masajistaData;
    }

    public void cargarMasajista() {
        try {
            // Validar matrícula
            String matriculaStr = vista.getTxtMatricula().getText().trim();
            if (matriculaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "La matrícula no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int matricula = Integer.parseInt(matriculaStr);

            // Validar nombre
            String nombre = vista.getTxtNombre().getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar apellido
            String apellido = vista.getTxtApellido().getText().trim();
            if (apellido.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El apellido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar teléfono
            String telefono = vista.getTxtTelefono().getText().trim();
            if (telefono.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El teléfono no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar DNI
            String dniStr = vista.getTxtDni().getText().trim();
            if (dniStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El DNI no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int dni = Integer.parseInt(dniStr);

            // Validar especialidad
            String especialidad = (String) vista.getCbEspecialidades().getSelectedItem();
            if (especialidad == null || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una especialidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear el masajista
            masajista = new Masajista(matricula, nombre, apellido, telefono, dni,PuestosDeTrabajo.MASAJISTA.getPuesto(), especialidad, true);
            
            masajistaData.altaMasajista(masajista);
            //JOptionPane.showMessageDialog(null, "Masajista cargado correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La matrícula y el DNI deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
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

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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import vistas.vistaCargarMasajista;
import vistas.vistaCargarVendedor;

public class ControlVendedor {

    private vistaCargarVendedor vista;
    private VendedorData vendedorData;
    private Vendedor vendedor;

    public ControlVendedor(vistaCargarVendedor vista, VendedorData vendedorData) {
        this.vista = vista;
        this.vendedorData = vendedorData;
    }

    public void cargarVendedor() {
        try {
            // String nombre, String apellido, String telefono, int dni, String puesto, boolean estado

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

            // Validar telefono
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

            // Crear el vendedor
            vendedor = new Vendedor(nombre, apellido, telefono, dni, PuestosDeTrabajo.VENDEDOR.getPuesto(), true);
            
            vendedorData.altaVendedor(vendedor);
            //JOptionPane.showMessageDialog(null, "Vendedor cargado correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El DNI debe contener números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

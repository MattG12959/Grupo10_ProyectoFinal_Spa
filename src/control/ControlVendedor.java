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
import entidades.Vendedor;
import javax.swing.JOptionPane;
import vistas.vistaCargarVendedor;

public class ControlVendedor {

    private vistaCargarVendedor vista;
    private VendedorData vendedorData;
    private MasajistaData masajistaData;
    private Vendedor vendedor;

    public ControlVendedor(vistaCargarVendedor vista, VendedorData vendedorData, MasajistaData masajistaData) {
        this.vista = vista;
        this.vendedorData = vendedorData;
        this.masajistaData = masajistaData;
    }

    public void cargarVendedor() {
        try {
            java.util.ArrayList<String> errores = new java.util.ArrayList<>();
            
            // Obtener valores de los campos
            String nombre = vista.getTxtNombre().getText().trim();
            String apellido = vista.getTxtApellido().getText().trim();
            String telefono = vista.getTxtTelefono().getText().trim();
            String dniStr = vista.getTxtDni().getText().trim();
            
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
                        // Validar que el DNI sea único (no exista en vendedores ni masajistas)
                        int dni = (int) dniLong;
                        Vendedor vendedorExistente = vendedorData.buscarVendedorPorDNI(dni);
                        if (vendedorExistente != null) {
                            errores.add("• El DNI ya está registrado para un vendedor.");
                        }
                        if (masajistaData != null) {
                            entidades.Masajista masajistaExistente = masajistaData.buscarMasajistaPorDNI(dni);
                            if (masajistaExistente != null) {
                                errores.add("• El DNI ya está registrado para un masajista.");
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    errores.add("• El DNI debe ser un número válido.");
                }
            }

            // Si hay errores, mostrarlos todos de una vez
            if (!errores.isEmpty()) {
                StringBuilder mensaje = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");
                for (String error : errores) {
                    mensaje.append(error).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensaje.toString(), "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si no hay errores, crear el vendedor
            int dni = (int) Long.parseLong(dniStr);
            vendedor = new Vendedor(nombre, apellido, telefono, dni, PuestosDeTrabajo.VENDEDOR.getPuesto(), true);
            
            vendedorData.altaVendedor(vendedor);
            //JOptionPane.showMessageDialog(null, "Vendedor cargado correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar los datos. Por favor, verifique que todos los campos sean válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

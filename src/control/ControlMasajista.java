
package control;

import Persistencia.MasajistaData;
import constantes.*;
import entidades.Masajista;
import vistas.vistaCargarMasajista;

public class ControlMasajista {
    private vistaCargarMasajista vista;
    private MasajistaData masajistaData;
    private Masajista masajista;

    public ControlMasajista(vistaCargarMasajista vista, MasajistaData masajistaData) {
        this.vista = vista;
        this.masajistaData = masajistaData;
    }
    
    public cargarMasajista() {
        try {
            // int matricula, String nombre, String apellido, String telefono, int dni,  String puesto, String especialidad, boolean estado
            int dni = Integer.parseInt(vista.getTxtDni().getText());
            
            masajista = new Masajista(vista.getTxtMatricula(), vista.getTxtNombre(), vista.getTxtApellido(), vista.getTxtTelefono(), dni, PuestosDeTrabajo.MASAJISTA.getPuesto(), vista.getCbEspecialidades(), true);
        }
    }
}

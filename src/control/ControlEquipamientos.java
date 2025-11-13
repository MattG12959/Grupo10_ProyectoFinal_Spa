package control;

import Persistencia.ConsultorioData;
import Persistencia.EquipamientoData;
import Persistencia.MasajistaData;
import entidades.Consultorio;
import entidades.Equipamiento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import vistas.vistaCargarEquipamientos;

/**
 *
 * @author ezequiel
 */
public class ControlEquipamientos {

    private vistaCargarEquipamientos vista1;
    private ConsultorioData consultorioData;
    private EquipamientoData equipamientoData;
    private MasajistaData masajistaData;
    private ArrayList<Consultorio> listaConsultorios;

    public ControlEquipamientos(vistaCargarEquipamientos vista1, ConsultorioData consultorioData, EquipamientoData equipamientoData, MasajistaData masajistaData) {
        this.vista1 = vista1;
        this.consultorioData = consultorioData;
        this.equipamientoData = equipamientoData;
        this.masajistaData = masajistaData;
        this.listaConsultorios = new ArrayList<>();
    }

    // Cargar consultorios en el combobox
    public void cargarComboConsultorios() {
        listaConsultorios = consultorioData.listarConsultorios();
        JComboBox<String> combo = vista1.getCbConsultorio();
        combo.removeAllItems();
        for (Consultorio c : listaConsultorios) {
            combo.addItem(c.getApto());
        }
    }

    // Cargar equipamientos (enum) en el combobox
    public void cargarComboEquipamientos() {
        JComboBox<String> combo = vista1.getCbEquipamiento();
        combo.removeAllItems();
        for (constantes.Equipamientos e : constantes.Equipamientos.values()) {
            combo.addItem(e.getNombre());
        }
    }

    // Listener para mostrar la descripción del equipamiento seleccionado
    public void agregarListenerComboEquipamiento() {
        vista1.getCbEquipamiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreSeleccionado = (String) vista1.getCbEquipamiento().getSelectedItem();
                String descripcion = "";
                for (constantes.Equipamientos eq : constantes.Equipamientos.values()) {
                    if (eq.getNombre().equals(nombreSeleccionado)) {
                        descripcion = eq.getDescripcion();
                        break;
                    }
                }
                vista1.getTxtInfo().setText(descripcion);
            }
        });
    }

    // Listener para boton de agregars
    public void agregarListenerBtnAgregar() {
        vista1.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEquipamientoAlConsultorio();
            }
        });
    }

    // Agregar el equipamiento al consultorio
    private void agregarEquipamientoAlConsultorio() {
        String aptoSeleccionado = (String) vista1.getCbConsultorio().getSelectedItem();
        String nombreEquipamiento = (String) vista1.getCbEquipamiento().getSelectedItem();
        String descripcion = vista1.getTxtInfo().getText();

        // Buscar el consultorio por apto
        Consultorio consultorioSeleccionado = null;
        for (Consultorio c : listaConsultorios) {
            if (c.getApto().equals(aptoSeleccionado)) {
                consultorioSeleccionado = c;
                break;
            }
        }
        if (consultorioSeleccionado == null) {
            JOptionPane.showMessageDialog(vista1, "Debe seleccionar un consultorio válido.");
            return;
        }

        // Crear el equipamiento
        Equipamiento nuevo = new Equipamiento();
        nuevo.setNroConsultorio(consultorioSeleccionado.getNroConsultorio());
        nuevo.setNombre_equipamiento(nombreEquipamiento);
        nuevo.setDescripcion_equipamiento(descripcion);

        // Guardar en la base de datoss
        ArrayList<Equipamiento> lista = new ArrayList<>();
        lista.add(nuevo);
        equipamientoData.cargaEquipamiento(lista);

    }

    public void actualizarDescripcionEquipamiento() {
        String nombreSeleccionado = (String) vista1.getCbEquipamiento().getSelectedItem();
        String descripcion = "";
        for (constantes.Equipamientos eq : constantes.Equipamientos.values()) {
            if (eq.getNombre().equals(nombreSeleccionado)) {
                descripcion = eq.getDescripcion();
                break;
            }
        }
        vista1.getTxtInfo().setText(descripcion);
    }
}

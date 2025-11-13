package control;

import Persistencia.ConsultorioData;
import Persistencia.EquipamientoData;
import Persistencia.MasajistaData;
import entidades.Consultorio;
import entidades.Equipamiento;
import entidades.Masajista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vistas.vistaConsultorios;

/**
 *
 * @author ezequiel
 */
public class ControlConsultorio {

    private vistaConsultorios vista;
    private ConsultorioData consultorioData;
    private EquipamientoData equipamientoData;
    private MasajistaData masajistaData;
    private ArrayList<Consultorio> listaConsultorios;
    private ArrayList<Equipamiento> listaEquipamientosActual; // lista los equipamientos del consultorio actual

    public ControlConsultorio(vistaConsultorios vista, ConsultorioData consultorioData, EquipamientoData equipamientoData, MasajistaData masajistaData) {
        this.vista = vista;
        this.consultorioData = consultorioData;
        this.equipamientoData = equipamientoData;
        this.masajistaData = masajistaData;
    }

    public void cargarComboConsultoriosPorApto() {
        listaConsultorios = consultorioData.listarConsultorios();
        JComboBox<String> combo = vista.getCbConsultorio();
        combo.removeAllItems();

        for (Consultorio c : listaConsultorios) {
            combo.addItem(c.getApto());
        }
    }

    public void cargarEquipamientosDeConsultorio() {

        JComboBox<String> combo = vista.getCbConsultorio();
        String aptoSeleccionado = (String) combo.getSelectedItem();
        if (aptoSeleccionado == null) {
            return;
        }

        // Buscar el consultorio por especialidad
        Consultorio consultorioSeleccionado = null;
        for (Consultorio c : listaConsultorios) {
            if (c.getApto().equals(aptoSeleccionado)) {
                consultorioSeleccionado = c;
                break;
            }
        }

        if (consultorioSeleccionado == null) {
            return;
        }

        int nroConsultorio = consultorioSeleccionado.getNroConsultorio();

        listaEquipamientosActual = equipamientoData.listarEquipamientosPorConsultorio(nroConsultorio);

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaEquipamientos().getModel();
        modelo.setRowCount(0);

        // Trar todos los equipamientos de ese consultorio
        ArrayList<Equipamiento> lista = equipamientoData.listarEquipamientosPorConsultorio(nroConsultorio);

        // Calcula stock por especialidad, va sumando mediante consigue nombres iguales
        ArrayList<String> nombresAgregados = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            Equipamiento eq = lista.get(i);
            String nombre = eq.getNombre_equipamiento();

            // Si ya agregamos este nombre, seguimos
            boolean yaAgregado = false;
            for (String n : nombresAgregados) {
                if (n.equals(nombre)) {
                    yaAgregado = true;
                    break;
                }
            }
            if (yaAgregado) {
                continue;
            }

            // Calcular stock
            int stock = 0;

            String descripcion = "";

            for (int j = 0; j < lista.size(); j++) {
                Equipamiento eq2 = lista.get(j);

                if (eq2.getNombre_equipamiento().equals(nombre)) {
                    stock++;
                    descripcion = eq2.getDescripcion_equipamiento();
                }
            }

            // Agregar fila a la tabla
            modelo.addRow(new Object[]{
                nombre,
                descripcion,
                stock
            });

            // Marcar este nombre como agregado
            nombresAgregados.add(nombre);
        }
    }

    public void cargarUsosPorConsultorio() {
        ArrayList<Integer> usos = consultorioData.obtenerUsos();
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaUsos().getModel();
        modelo.setRowCount(0);

        Object[] fila = new Object[modelo.getColumnCount()];
        for (int i = 0; i < fila.length && i < usos.size(); i++) {
            fila[i] = usos.get(i);
        }
        modelo.addRow(fila);
    }

    public void cargarMasajistasAptosPorEspecialidad() {
        String especialidad = (String) vista.getCbConsultorio().getSelectedItem();
        ArrayList<Masajista> masajistas = masajistaData.listarMasajistasAptosPorEspecialidad(especialidad);

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaMasajistas().getModel();
        modelo.setRowCount(0);

        for (Masajista m : masajistas) {
            modelo.addRow(new Object[]{
                m.getMatricula(),
                m.getNombre(),
                m.getApellido()
            });
        }
    }

    public void agregarListenerComboConsultorio() {
        vista.getCbConsultorio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEquipamientosDeConsultorio();
                cargarMasajistasAptosPorEspecialidad();
            }
        });
    }

    public void eliminarEquipamientoSeleccionado() {
        JTable tabla = vista.getTablaEquipamientos();
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un equipamiento para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el equipamiento correspondiente a la fila seleccionada
        Equipamiento equipamiento = listaEquipamientosActual.get(fila);
        int idEquipamiento = equipamiento.getIdEquipamiento();

        int confirm = JOptionPane.showConfirmDialog(vista, "¿Está seguro que desea eliminar este equipamiento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        equipamientoData.eliminarEquipamiento(idEquipamiento);

        // Refrescar la tabla
        cargarEquipamientosDeConsultorio();
    }
}

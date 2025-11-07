package vistas;

import Persistencia.ClienteData;
import Persistencia.ConsultorioData;
import Persistencia.DiaDeSpaData;
import Persistencia.miConexion;
import Persistencia.EmpleadoData;
import Persistencia.InstalacionData;
import Persistencia.MasajistaData;
import Persistencia.ProductoData;
import Persistencia.SesionData;
import Persistencia.TratamientoData;
import Persistencia.VendedorData;
//import constantes.ConstantesPuestos; ->Mati: Lo comente para que aparezca con error.
import entidades.Empleado;
import entidades.Vendedor;
import constantes.*;
import entidades.Cliente;
import entidades.Consultorio;
import entidades.DiaDeSpa;
import entidades.Equipamiento;
import entidades.Instalacion;
//import static constantes.ConstantesPuestos.MASAJISTA; ->Mati: Lo comente para que aparezca con error.
import entidades.Masajista;
import entidades.Producto;
import entidades.Sesion;
import entidades.Tratamiento;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Scanner;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
// Clase Testing
public class Gp10_Spa {

    private static Scanner scanner;

    private static miConexion conexion;

    public static void main(String[] args) {
        // 1) Crear la conexión principal que pasarás a las Data
        miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");

        // 2) Crear objetos Data
        DiaDeSpaData diaData = new DiaDeSpaData(conexion);
        SesionData sesionData = new SesionData(conexion);
        InstalacionData instalData = new InstalacionData(conexion);
        ConsultorioData consultData = new ConsultorioData(conexion);
        TratamientoData tratData = new TratamientoData(conexion);
        MasajistaData masData = new MasajistaData(conexion);
        ClienteData clienteData = new ClienteData(conexion);

        try {
            // -------------- CARGO INSTALACION ---------------------- 

            Instalacion ins1 = new Instalacion();

            ins1.setNombre(AreasDeRelajacion.SALA_DE_INFUCIONES.getNombre());
            ins1.setDetalleDeUso(AreasDeRelajacion.SALA_DE_INFUCIONES.getDescripcion());
            ins1.setUsos(10);
            ins1.setPrecio(500.0);
            ins1.setEstado(true);
            instalData.guardarInstalacion(ins1);
            System.out.println("Instalacion 1 guardada con id: " + ins1.getCodInstal());
            JOptionPane.showMessageDialog(null, "Instalacion 1 guardada con id: " + ins1.getCodInstal());

            Instalacion ins2 = new Instalacion();

            ins2.setNombre(AreasDeRelajacion.ZONA_DE_RELAX.getNombre());
            ins2.setDetalleDeUso(AreasDeRelajacion.ZONA_DE_RELAX.getDescripcion());
            ins2.setUsos(5);
            ins2.setPrecio(1500.0);
            ins2.setEstado(true);
            instalData.guardarInstalacion(ins2);
            System.out.println("Instalacion 2 guardada con id: " + ins2.getCodInstal());
            JOptionPane.showMessageDialog(null, "Instalacion 2 guardada con id: " + ins2.getCodInstal());

            // -------------- CARGO TRATAMIENTO -------------
            Tratamiento tr = new Tratamiento();

            tr.setNombre(TratamientosCorporales.ENVOLTURAS_CORPORALES.getNombre());
            tr.settipoTratamiento(Especialidades.CORPORAL.getEspecialidad());
            tr.setDetalle(TratamientosCorporales.ENVOLTURAS_CORPORALES.getDescripcion());
            tr.setDuracion(LocalTime.of(2, 30));
            tr.setCosto(1200.0);
            tr.setEstado(true);
            tratData.cargaTratamiento(tr);
            System.out.println("Tratamiento guardado con id: " + tr.getCodTratam());
            JOptionPane.showMessageDialog(null, "Tratamiento guardado con id: " + tr.getCodTratam());

            // ----------------- CARGO MASAJISTA -------------
            Masajista m = new Masajista();

            m.setMatricula(368);
            m.setNombre("Juan");
            m.setApellido("Perez");
            m.setTelefono("12345678");
            m.setDni(62626262);
            m.setPuesto(PuestosDeTrabajo.MASAJISTA.getPuesto());
            m.setEspecialidad(Especialidades.CORPORAL.getEspecialidad());
            masData.altaMasajista(m);
            System.out.println("Masajista guardado con matricula: " + m.getMatricula());
            JOptionPane.showMessageDialog(null, "Masajista guardado con matricula: " + m.getMatricula());

            // --------------- CARGO CONSULTORIO -------------
            Consultorio c = new Consultorio();

            c.setUsos(5);
            c.setApto(Especialidades.CORPORAL.getEspecialidad());

            // cargo tres equipamientos para probar todo
            ArrayList<Equipamiento> equipamientos = new ArrayList<>();

            Equipamiento e1 = new Equipamiento();
            e1.setNombre_equipamiento(Equipamientos.CABINA_SAUNA.getNombre());
            e1.setDescripcion_equipamiento(Equipamientos.CABINA_SAUNA.getDescripcion());

            Equipamiento e2 = new Equipamiento();
            e2.setNombre_equipamiento(Equipamientos.CAMILLA_HIDROMASAJE.getNombre());
            e2.setDescripcion_equipamiento(Equipamientos.CAMILLA_HIDROMASAJE.getDescripcion());

            Equipamiento e3 = new Equipamiento();
            e3.setNombre_equipamiento(Equipamientos.CAMILLA_MASAJES.getNombre());
            e3.setDescripcion_equipamiento(Equipamientos.CAMILLA_MASAJES.getDescripcion());

            equipamientos.add(e1);
            equipamientos.add(e2);
            equipamientos.add(e3);

            // asignar la lista al consultorio antes de persistirlo
            c.setEquipamiento(equipamientos);

            consultData.cargaConsultorio(c);

            System.out.println("Consultorio guardado con nro: " + c.getNroConsultorio());
            JOptionPane.showMessageDialog(null, "Consultorio guardado con nro: " + c.getNroConsultorio());

            // ------------------- CARGA CLIENTE ------------------
            Cliente cliente = new Cliente();
            // Ajustá el setter/getter de id si tu clase usa otro nombre
            cliente.setDni(44437768);
            cliente.setNombre("Marcos");
            cliente.setApellido("Bequis");
            cliente.setTelefono("2664880438");
            cliente.setEdad(22);
            cliente.setAfecciones("Ninguna");
            cliente.setEstado(true);
            clienteData.guardarCliente(cliente);

            System.out.println("Cliente guardado con id: " + cliente.getCodCli());
            JOptionPane.showMessageDialog(null, "Cliente guardado con id: " + cliente.getCodCli());

            // --------------- CARGA SESION - CARGA DIA DE SPA-------------
            Sesion s = new Sesion();
            s.setFechaHoraInicio(LocalDateTime.now());
            s.setFechaHoraFinal(LocalDateTime.now().plusHours(2));
            s.setTratamiento(tr);
            s.setConsultorio(c);
            s.setMasajista(m);
            s.setEstado(true);
            ArrayList<Instalacion> listaIns = new ArrayList<>();
            listaIns.add(ins1);
            listaIns.add(ins2);
            s.setInsalaciones(listaIns);

            // --- crear DiaDeSpa y asociar la sesión, fijarse que S tenga referencia al Dia
            DiaDeSpa dia = new DiaDeSpa();
            dia.setFechayhora(LocalDateTime.now());
            dia.setPreferencias("Sin fragancias");
            dia.setCliente(cliente);
            dia.setMonto(3000.0);
            dia.setEstado(true);

            ArrayList<Sesion> sesiones = new ArrayList<>();

            s.setDiaDeSpa(dia);   // <- crear la referencia hacia el Dia
            sesiones.add(s);
            dia.setSesiones(sesiones);

            // Ahora guardar SOLO el DiaDeSpa; él guardará las sesiones internamente
            diaData.guardarDiaDeSpa(dia);

            // 5) Recuperar y mostrar lo guardado como prueba
            DiaDeSpa buscado = diaData.buscarDiaDeSpa(dia.getCodPack());

            JOptionPane.showMessageDialog(null, "DiaDeSpa: \n\ncodPack: " + buscado.getCodPack() + "\nFecha y hora: " + buscado.getFechayhora()
                    + "\nPreferencias: " + buscado.getPreferencias() + "Cliente: " + buscado.getCliente().getNombre() + " " + buscado.getCliente().getApellido()
                    + "\n\nCantidad de sesiones: " + buscado.getSesiones().size() + "\nMonto: $" + buscado.getMonto() + "\nEstado: " + buscado.isEstado()
                    + "\n\nAcontinuacion las sesiones asociadas a dia de spa numero " + buscado.getCodPack() + "...");

            System.out.println("Buscado codPack: " + buscado.getCodPack());
            System.out.println("Fecha: " + buscado.getFechayhora());
            System.out.println("Cliente: " + buscado.getCliente().getNombre() + " " + buscado.getCliente().getApellido());
            System.out.println("Cantidad sesiones: " + buscado.getSesiones().size());
            
            String instalaciones = "";

            for (Sesion aux : buscado.getSesiones()) {
                
                for (Instalacion ii : aux.getInsalaciones()) {
                    instalaciones += "\nInstalacion id: " + ii.getCodInstal() + "\nNombre: " + ii.getNombre() + "\nDetalle de uso: " + ii.getDetalleDeUso() + "\nUsos: " + ii.getUsos() + "\nPrecio: $" + ii.getPrecio() + "\nEstado: " + ii.isEstado() + "\n\n";
                }
                JOptionPane.showMessageDialog(null,
                        "Sesion ID: " + aux.getCodSesion() + "\nFecha hora inicio: " + aux.getFechaHoraInicio() + "\nFecha hora final: " + aux.getFechaHoraFinal() 
                        + "\n\n" + "------------------------------------------------------------------------" + "\nTratamiento id: " + aux.getTratamiento().getCodTratam() + "\nNombre tratamiento: " + aux.getTratamiento().getNombre() + "\nTipo de tratamiento: " + aux.getTratamiento().gettipoTratamiento() + "\nDetalle tratamiento: " + aux.getTratamiento().getDetalle() + "\nDuracion tratamiento: " + aux.getTratamiento().getDuracion() + "hs" + "\nCosto tratamiento: " + aux.getTratamiento().getCosto() + "\nEstado tratamiento: " + aux.getTratamiento().isEstado()
                        + "\n\n" + "------------------------------------------------------------------------" + "\nConsultorio id: " + aux.getConsultorio().getNroConsultorio() + "\nUsos consultorio: " + aux.getConsultorio().getUsos() + "\nApto consultorio: " + aux.getConsultorio().getApto()
                        + "\n\n" + "------------------------------------------------------------------------" + "\nMasajista matricula: " + aux.getMasajista().getMatricula() + "\nIdEmpleado: " + aux.getMasajista().getIdEmpleado() + "\nApellido y Nombre: " + aux.getMasajista().getApellido() + " " + aux.getMasajista().getNombre() + "\nTelefono: " + aux.getMasajista().getTelefono() + "\nDNI: " + aux.getMasajista().getDni() + "\nPuesto: " + aux.getMasajista().getPuesto() + "\nEspecialidad: " + aux.getMasajista().getEspecialidad() + "\nEstado: " + aux.getMasajista().isEstado()
                        + "\n\n" + "------------------------------------------------------------------------" + instalaciones + "\n\n\"------------------------------------------------------------------------\"\nEstado de la sesion: " + aux.isEstado() 
                );
            }

            for (Sesion s2 : buscado.getSesiones()) {
                System.out.println("  Sesion id: " + s2.getCodSesion() + " Tratamiento: " + s2.getTratamiento().getNombre());
                System.out.println("    Instalaciones usadas: ");
                for (Instalacion ii : s2.getInsalaciones()) {
                    System.out.println("      - " + ii.getNombre());
                }
            }

            System.out.println("--------------- FINAL DE EJECUCION-----------------");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

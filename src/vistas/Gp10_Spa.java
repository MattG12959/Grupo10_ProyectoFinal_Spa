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
            ins1.setPrecio30m(500.0);
            ins1.setEstado(true);
            instalData.guardarInstalacion(ins1);
            System.out.println("Instalacion guardada con id: " + ins1.getCodInstal());

            // -------------- CARGO TRATAMIENTO -------------
            Tratamiento tr = new Tratamiento();

            tr.setNombre(TratamientosCorporales.ENVOLTURAS_CORPORALES.getNombre());
            tr.settipoTratamiento(Especialidades.CORPORAL.getEspecialidad());
            tr.setDetalle(TratamientosCorporales.ENVOLTURAS_CORPORALES.getDescripcion());
            tr.setDuracion(LocalTime.of(60, 0));
            tr.setCosto(1200.0);
            tr.setEstado(true);
            tratData.cargaTratamiento(tr);
            System.out.println("Tratamiento guardado con id: " + tr.getCodTratam());

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

            // --------------- CARGO CONSULTORIO -------------
            Consultorio c = new Consultorio();
            
            c.setUsos(5);
            c.setApto("General");
            consultData.cargaConsultorio(c);
            
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
            
            
            System.out.println("Consultorio guardado con nro: " + c.getNroConsultorio());

            
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

            // 4) Crear una sesión que use las entidades anteriores
            Sesion s = new Sesion();
            s.setFechaHoraInicio(LocalDateTime.now());
            s.setFechaHoraFinal(LocalDateTime.now().plusHours(1));
            s.setTratamiento(tr);
            s.setConsultorio(c);
            s.setMasajista(m);
            s.getDiaDeSpa().setCodPack(0); // por ahora 0, luego lo seteamos con DiaDeSpa
            s.setEstado(true);

            // Asociar instalaciones a la sesión
            ArrayList<Instalacion> listaIns = new ArrayList<>();
            listaIns.add(ins1);
            s.setInsalaciones(listaIns);

            // No guardamos la sesión suelta: la guardamos cuando guardemos DiaDeSpa (pero podés guardarla suelta también)
            // sesionData.guardarSesion(s);

            // 5) Crear DiaDeSpa con la sesión
            DiaDeSpa dia = new DiaDeSpa();
            dia.setFechayhora(LocalDateTime.now());
            dia.setPreferencias("Sin fragancias");
            dia.setCliente(cliente);
            dia.setMonto(3000.0);
            dia.setEstado(true);

            ArrayList<Sesion> sesiones = new ArrayList<>();
            sesiones.add(s);
            dia.setSesiones(sesiones);

            // 6) Guardar DiaDeSpa (esto guardará las sesiones y las relaciones)
            diaData.guardarDiaDeSpa(dia);
            System.out.println("DiaDeSpa guardado con codPack: " + dia.getCodPack());

            // 7) Recuperar y mostrar lo guardado
            DiaDeSpa buscado = diaData.buscarDiaDeSpa(dia.getCodPack());
            System.out.println("Buscado codPack: " + buscado.getCodPack());
            System.out.println("Fecha: " + buscado.getFechayhora());
            System.out.println("Cliente: " + buscado.getCliente().getNombre() + " " + buscado.getCliente().getApellido());
            System.out.println("Cantidad sesiones: " + buscado.getSesiones().size());
            for (Sesion s2 : buscado.getSesiones()) {
                System.out.println("  Sesion id: " + s2.getCodSesion() + " Tratamiento: " + s2.getTratamiento().getNombre());
                System.out.println("    Instalaciones usadas: ");
                for (Instalacion ii : s2.getInsalaciones()) {
                    System.out.println("      - " + ii.getNombre());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
        
        
        
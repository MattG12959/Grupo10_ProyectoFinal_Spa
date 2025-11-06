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
import entidades.Instalacion;
//import static constantes.ConstantesPuestos.MASAJISTA; ->Mati: Lo comente para que aparezca con error.
import entidades.Masajista;
import entidades.Producto;
import entidades.Sesion;
import entidades.Tratamiento;
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
            // 3) Crear/guardar entidades necesarias (instalaciones, tratamiento, masajista, consultorio, cliente)
            Instalacion ins1 = new Instalacion();
            ins1.setNombre("Sauna");
            ins1.setDetalleDeUso("Uso para relajacion");
            ins1.setUsos(10);
            ins1.setPrecio30m(500.0);
            ins1.setEstado(true);
            instalData.guardarInstalacion(ins1);
            System.out.println("Instalacion guardada con id: " + ins1.getCodInstal());

            Tratamiento tr = new Tratamiento();
            // Ajustá nombres/constructores según tu clase Tratamiento
            tr.setCodTratam(0); // si tiene autoincrement, no importa
            tr.setNombre("Masaje relajante");
            tr.setDuracionMinutos(60);
            tr.setPrecio(1200.0);
            tratData.guardarTratamiento(tr);
            System.out.println("Tratamiento guardado con id: " + tr.getCodTratam());

            Masajista m = new Masajista();
            m.setMatricula(0);
            m.setNombre("Juan");
            m.setApellido("Perez");
            m.setTelefono("12345678");
            masData.guardarMasajista(m);
            System.out.println("Masajista guardado con matricula: " + m.getMatricula());

            Consultorio c = new Consultorio();
            c.setUsos(5);
            c.setApto("General");
            consultData.guardarConsultorio(c);
            System.out.println("Consultorio guardado con nro: " + c.getNroConsultorio());

            Cliente cliente = new Cliente();
            // Ajustá el setter/getter de id si tu clase usa otro nombre
            cliente.setCodCliente(0);
            cliente.setNombre("Marcos");
            cliente.setApellido("Bequis");
            clienteData.guardarCliente(cliente);
            System.out.println("Cliente guardado con id: " + cliente.getCodCliente());

            // 4) Crear una sesión que use las entidades anteriores
            Sesion s = new Sesion();
            s.setFechaHoraInicio(LocalDateTime.now());
            s.setFechaHoraFinal(LocalDateTime.now().plusHours(1));
            s.setTratamiento(tr);
            s.setConsultorio(c);
            s.setMasajista(m);
            s.setCodPack(0); // por ahora 0, luego lo seteamos con DiaDeSpa
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
        
        
        
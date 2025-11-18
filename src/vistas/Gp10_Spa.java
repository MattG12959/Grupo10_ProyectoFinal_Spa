package vistas;
/*
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
*/
import Persistencia.ClienteData;
import Persistencia.ConsultorioData;
import Persistencia.DiaDeSpaData;
import Persistencia.miConexion;
import Persistencia.InstalacionData;
import Persistencia.MasajistaData;
import Persistencia.SesionData;
import Persistencia.TratamientoData;
import Persistencia.ProductoData;
import Persistencia.VendedorData;
import entidades.Empleado;
import entidades.Vendedor;
import constantes.*;
import entidades.Cliente;
import entidades.Consultorio;
import entidades.DiaDeSpa;
import entidades.Equipamiento;
import entidades.Instalacion;
import entidades.Masajista;
import entidades.Producto;
import entidades.Sesion;
import entidades.Tratamiento;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase de testing ampliada: crea varias entidades y persiste un DiaDeSpa con 5
 * sesiones. Adaptá ids/valores si tu modelo de datos difiere.
 */
public class Gp10_Spa {

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
            /*
            Instalacion ins1 = new Instalacion();
            ins1.setNombre(AreasDeRelajacion.SALA_DE_INFUCIONES.getNombre());
            ins1.setDetalleDeUso(AreasDeRelajacion.SALA_DE_INFUCIONES.getDescripcion());
            ins1.setUsos(10);
            ins1.setPrecio(500.0);
            ins1.setEstado(true);
            instalData.guardarInstalacion(ins1);
            System.out.println("Instalacion 1 guardada con id: " + ins1.getCodInstal());

            Instalacion ins2 = new Instalacion();
            ins2.setNombre(AreasDeRelajacion.ZONA_DE_RELAX.getNombre());
            ins2.setDetalleDeUso(AreasDeRelajacion.ZONA_DE_RELAX.getDescripcion());
            ins2.setUsos(5);
            ins2.setPrecio(1500.0);
            ins2.setEstado(true);
            instalData.guardarInstalacion(ins2);
            System.out.println("Instalacion 2 guardada con id: " + ins2.getCodInstal());

            // -------------- CARGO TRATAMIENTOS -------------
            Tratamiento tr1 = new Tratamiento();
            tr1.setNombre(TratamientosCorporales.ENVOLTURAS_CORPORALES.getNombre());
            tr1.settipoTratamiento(Especialidades.CORPORAL.getEspecialidad());
            tr1.setDetalle(TratamientosCorporales.ENVOLTURAS_CORPORALES.getDescripcion());
            tr1.setDuracion(LocalTime.of(2, 0));
            tr1.setCosto(1200.0);
            tr1.setEstado(true);
            tratData.cargaTratamiento(tr1);
            System.out.println("Tratamiento 1 guardado con id: " + tr1.getCodTratam());

            Tratamiento tr2 = new Tratamiento();
            tr2.setNombre("Masaje relajante profundo");
            tr2.settipoTratamiento(Especialidades.CORPORAL.getEspecialidad());
            tr2.setDetalle("Masaje relajante de 90 minutos");
            tr2.setDuracion(LocalTime.of(1, 30));
            tr2.setCosto(900.0);
            tr2.setEstado(true);
            tratData.cargaTratamiento(tr2);
            System.out.println("Tratamiento 2 guardado con id: " + tr2.getCodTratam());

            Tratamiento tr3 = new Tratamiento();
            tr3.setNombre("Masaje terapéutico");
            tr3.settipoTratamiento(Especialidades.CORPORAL.getEspecialidad());
            tr3.setDetalle("Masaje focalizado para alivio de tensiones");
            tr3.setDuracion(LocalTime.of(1, 0));
            tr3.setCosto(700.0);
            tr3.setEstado(true);
            tratData.cargaTratamiento(tr3);
            System.out.println("Tratamiento 3 guardado con id: " + tr3.getCodTratam());

            // ----------------- CARGO VARIOS MASAJISTAS -------------
            Masajista m1 = new Masajista();
            m1.setMatricula(1001);
            m1.setNombre("Lucas");
            m1.setApellido("Gonzalez");
            m1.setTelefono("111111111");
            m1.setDni(30111222);
            m1.setPuesto(PuestosDeTrabajo.MASAJISTA.getPuesto());
            m1.setEspecialidad(Especialidades.CORPORAL.getEspecialidad());
            m1.setEstado(true);
            masData.altaMasajista(m1);

            Masajista m2 = new Masajista();
            m2.setMatricula(1002);
            m2.setNombre("Martina");
            m2.setApellido("Lopez");
            m2.setTelefono("222222222");
            m2.setDni(30222333);
            m2.setPuesto(PuestosDeTrabajo.MASAJISTA.getPuesto());
            m2.setEspecialidad(Especialidades.CORPORAL.getEspecialidad());
            m2.setEstado(true);
            masData.altaMasajista(m2);

            Masajista m3 = new Masajista();
            m3.setMatricula(1003);
            m3.setNombre("Diego");
            m3.setApellido("Ferreira");
            m3.setTelefono("333333333");
            m3.setDni(30333444);
            m3.setPuesto(PuestosDeTrabajo.MASAJISTA.getPuesto());
            m3.setEspecialidad(Especialidades.CORPORAL.getEspecialidad());
            m3.setEstado(true);
            masData.altaMasajista(m3);

            Masajista m4 = new Masajista();
            m4.setMatricula(1004);
            m4.setNombre("Sofia");
            m4.setApellido("Martinez");
            m4.setTelefono("444444444");
            m4.setDni(30444555);
            m4.setPuesto(PuestosDeTrabajo.MASAJISTA.getPuesto());
            m4.setEspecialidad(Especialidades.CORPORAL.getEspecialidad());
            m4.setEstado(true);
            masData.altaMasajista(m4);

            Masajista m5 = new Masajista();
            m5.setMatricula(1005);
            m5.setNombre("Federico");
            m5.setApellido("Ramos");
            m5.setTelefono("555555555");
            m5.setDni(30555666);
            m5.setPuesto(PuestosDeTrabajo.MASAJISTA.getPuesto());
            m5.setEspecialidad(Especialidades.CORPORAL.getEspecialidad());
            m5.setEstado(true);
            masData.altaMasajista(m5);

            System.out.println("Masajistas guardados: " + m1.getMatricula() + ", " + m2.getMatricula() + ", " + m3.getMatricula() + ", " + m4.getMatricula() + ", " + m5.getMatricula());
            
            // --------------- CARGO CONSULTORIO -------------
            Consultorio c = new Consultorio();
            c.setUsos(5);
            c.setApto(Especialidades.CORPORAL.getEspecialidad());
            
            // equipamientos (3) y asignación al consultorio
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
            c.setEquipamiento(equipamientos);
            consultData.cargaConsultorio(c);
            System.out.println("Consultorio guardado con nro: " + c.getNroConsultorio());

            // ------------------- CARGA CLIENTE ------------------
            Cliente cliente = new Cliente();
            cliente.setDni(44437768);
            cliente.setNombre("Marcos");
            cliente.setApellido("Bequis");
            cliente.setTelefono("2664880438");
            cliente.setEdad(22);
            cliente.setAfecciones("Ninguna");
            cliente.setEstado(true);
            clienteData.guardarCliente(cliente);
            System.out.println("Cliente guardado con id: " + cliente.getCodCli());

            // --------------- CARGA SESIONES - CARGA DIA DE SPA -------------
            DiaDeSpa dia = new DiaDeSpa();
            dia.setFechayhora(LocalDateTime.now());
            dia.setPreferencias("Sin fragancias");
            dia.setCliente(cliente);
            dia.setMonto(7500.0);
            dia.setEstado(true);

            ArrayList<Sesion> sesiones = new ArrayList<>();

            // Sesion 1
            Sesion s1 = new Sesion();
            s1.setFechaHoraInicio(LocalDateTime.now().plusHours(1));
            s1.setFechaHoraFinal(LocalDateTime.now().plusHours(2).plusMinutes(30));
            s1.setTratamiento(tr1);
            s1.setConsultorio(c);
            s1.setMasajista(m1);
            s1.setEstado(true);
            ArrayList<Instalacion> listaIns1 = new ArrayList<>();
            listaIns1.add(ins1); listaIns1.add(ins2);
            s1.setInsalaciones(listaIns1);
            s1.setDiaDeSpa(dia);
            sesiones.add(s1);

            // Sesion 2
            Sesion s2 = new Sesion();
            s2.setFechaHoraInicio(LocalDateTime.now().plusHours(3));
            s2.setFechaHoraFinal(LocalDateTime.now().plusHours(4).plusMinutes(30));
            s2.setTratamiento(tr2);
            s2.setConsultorio(c);
            s2.setMasajista(m2);
            s2.setEstado(true);
            ArrayList<Instalacion> listaIns2 = new ArrayList<>();
            listaIns2.add(ins1);
            s2.setInsalaciones(listaIns2);
            s2.setDiaDeSpa(dia);
            sesiones.add(s2);

            // Sesion 3
            Sesion s3 = new Sesion();
            s3.setFechaHoraInicio(LocalDateTime.now().plusHours(5));
            s3.setFechaHoraFinal(LocalDateTime.now().plusHours(6));
            s3.setTratamiento(tr3);
            s3.setConsultorio(c);
            s3.setMasajista(m3);
            s3.setEstado(true);
            ArrayList<Instalacion> listaIns3 = new ArrayList<>();
            listaIns3.add(ins2);
            s3.setInsalaciones(listaIns3);
            s3.setDiaDeSpa(dia);
            sesiones.add(s3);

            // Sesion 4
            Sesion s4 = new Sesion();
            s4.setFechaHoraInicio(LocalDateTime.now().plusHours(7));
            s4.setFechaHoraFinal(LocalDateTime.now().plusHours(8).plusMinutes(30));
            s4.setTratamiento(tr1);
            s4.setConsultorio(c);
            s4.setMasajista(m4);
            s4.setEstado(true);
            ArrayList<Instalacion> listaIns4 = new ArrayList<>();
            listaIns4.add(ins1); listaIns4.add(ins2);
            s4.setInsalaciones(listaIns4);
            s4.setDiaDeSpa(dia);
            sesiones.add(s4);

            // Sesion 5 (sin instalaciones)
            Sesion s5 = new Sesion();
            s5.setFechaHoraInicio(LocalDateTime.now().plusHours(9));
            s5.setFechaHoraFinal(LocalDateTime.now().plusHours(10));
            s5.setTratamiento(tr3);
            s5.setConsultorio(c);
            s5.setMasajista(m5);
            s5.setEstado(true);
            ArrayList<Instalacion> listaIns5 = new ArrayList<>();
            s5.setInsalaciones(listaIns5);
            s5.setDiaDeSpa(dia);
            sesiones.add(s5);

            // Asociar sesiones al DiaDeSpa
            dia.setSesiones(sesiones);

            // Guardar DiaDeSpa (internamente guardará las sesiones y los enlaces)
            diaData.guardarDiaDeSpa(dia);

            // Recuperar y mostrar lo guardado como prueba
            DiaDeSpa buscado = diaData.buscarDiaDeSpa(dia.getCodPack());

            JOptionPane.showMessageDialog(null, "DiaDeSpa: \n\ncodPack: " + buscado.getCodPack() + "\nFecha y hora: " + buscado.getFechayhora()
                    + "\nPreferencias: " + buscado.getPreferencias() + "\nCliente: " + buscado.getCliente().getNombre() + " " + buscado.getCliente().getApellido()
                    + "\n\nCantidad de sesiones: " + buscado.getSesiones().size() + "\nMonto: $" + buscado.getMonto() + "\nEstado: " + buscado.isEstado()
                    + "\n\nA continuación las sesiones asociadas a dia de spa numero " + buscado.getCodPack() + "...");

            System.out.println("Buscado codPack: " + buscado.getCodPack());
            System.out.println("Fecha: " + buscado.getFechayhora());
            System.out.println("Cliente: " + buscado.getCliente().getNombre() + " " + buscado.getCliente().getApellido());
            System.out.println("Cantidad sesiones: " + buscado.getSesiones().size());

            for (Sesion aux : buscado.getSesiones()) {
                String instalacionesStr = "";
                for (Instalacion ii : aux.getInsalaciones()) {
                    instalacionesStr += "\nInstalacion id: " + ii.getCodInstal() + "\nNombre: " + ii.getNombre() + "\nDetalle de uso: " + ii.getDetalleDeUso() + "\nUsos: " + ii.getUsos() + "\nPrecio: $" + ii.getPrecio() + "\nEstado: " + ii.isEstado() + "\n\n";
                }
                JOptionPane.showMessageDialog(null,
                        "Sesion ID: " + aux.getCodSesion() + "\nFecha hora inicio: " + aux.getFechaHoraInicio() + "\nFecha hora final: " + aux.getFechaHoraFinal()
                        + "\n\n" + "------------------------------------------------------------------------" + "\nTratamiento id: " + aux.getTratamiento().getCodTratam() + "\nNombre tratamiento: " + aux.getTratamiento().getNombre() + "\nTipo de tratamiento: " + aux.getTratamiento().gettipoTratamiento() + "\nDetalle tratamiento: " + aux.getTratamiento().getDetalle() + "\nDuracion tratamiento: " + aux.getTratamiento().getDuracion() + "hs" + "\nCosto tratamiento: " + aux.getTratamiento().getCosto() + "\nEstado tratamiento: " + aux.getTratamiento().isEstado()
                        + "\n\n" + "------------------------------------------------------------------------" + "\nConsultorio id: " + aux.getConsultorio().getNroConsultorio() + "\nUsos consultorio: " + aux.getConsultorio().getUsos() + "\nApto consultorio: " + aux.getConsultorio().getApto()
                        + "\n\n" + "------------------------------------------------------------------------" + "\nMasajista matricula: " + aux.getMasajista().getMatricula() + "\nApellido y Nombre: " + aux.getMasajista().getApellido() + " " + aux.getMasajista().getNombre() + "\nTelefono: " + aux.getMasajista().getTelefono() + "\nDNI: " + aux.getMasajista().getDni() + "\nPuesto: " + aux.getMasajista().getPuesto() + "\nEspecialidad: " + aux.getMasajista().getEspecialidad() + "\nEstado: " + aux.getMasajista().isEstado()
                        + "\n\n" + "------------------------------------------------------------------------" + instalacionesStr + "\n\n\"------------------------------------------------------------------------\"\nEstado de la sesion: " + aux.isEstado()
                );
            }

            // Mostrar resumen en consola
            for (Sesion aux : buscado.getSesiones()) {
                System.out.println("  Sesion id: " + aux.getCodSesion() + " Tratamiento: " + aux.getTratamiento().getNombre() + " Masajista: " + aux.getMasajista().getApellido());
                System.out.println("    Instalaciones usadas: ");
                for (Instalacion ii : aux.getInsalaciones()) {
                    System.out.println("      - " + ii.getNombre());
                }
            }

            System.out.println("--------------- FINAL DE EJECUCION-----------------");
             */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void cargarDatosDePrueba() {
        miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");

        DiaDeSpaData diaData = new DiaDeSpaData(conexion);
        SesionData sesionData = new SesionData(conexion);
        InstalacionData instalData = new InstalacionData(conexion);
        ProductoData productoData = new ProductoData(conexion);
        ConsultorioData consultData = new ConsultorioData(conexion);
        TratamientoData tratData = new TratamientoData(conexion);
        MasajistaData masData = new MasajistaData(conexion);
        ClienteData clienteData = new ClienteData(conexion);
        VendedorData vendedorData = new VendedorData(conexion);
        

        try {
            // ----------- INSTALACIONES -----------
            Instalacion ins1 = new Instalacion();
            ins1.setNombre(AreasDeRelajacion.ZONA_DE_RELAX.getNombre());
            ins1.setDetalleDeUso(AreasDeRelajacion.ZONA_DE_RELAX.getDescripcion());
            ins1.setUsos(12);
            ins1.setPrecio(800.0);
            ins1.setEstado(true);
            instalData.guardarInstalacion(ins1);

            Instalacion ins2 = new Instalacion();
            ins2.setNombre(AreasDeRelajacion.SALA_DE_TE.getNombre());
            ins2.setDetalleDeUso(AreasDeRelajacion.SALA_DE_TE.getDescripcion());
            ins2.setUsos(8);
            ins2.setPrecio(400.0);
            ins2.setEstado(true);
            instalData.guardarInstalacion(ins2);

            Instalacion ins3 = new Instalacion();
            ins3.setNombre(AreasDeRelajacion.SALA_DE_INFUCIONES.getNombre());
            ins3.setDetalleDeUso(AreasDeRelajacion.SALA_DE_INFUCIONES.getDescripcion());
            ins3.setUsos(10);
            ins3.setPrecio(500.0);
            ins3.setEstado(true);
            instalData.guardarInstalacion(ins3);

            // ----------- TRATAMIENTOS CORPORALES -----------
            Tratamiento tr1 = new Tratamiento();
            tr1.setNombre(TratamientosCorporales.EXFOLIACION_CORPORAL.getNombre());
            tr1.settipoTratamiento("Corporal");
            tr1.setDetalle(TratamientosCorporales.EXFOLIACION_CORPORAL.getDescripcion());
            tr1.setDuracion(java.time.LocalTime.of(1, 0));
            tr1.setCosto(1000.0);
            tr1.setEstado(true);
            tratData.cargaTratamiento(tr1);

            Tratamiento tr2 = new Tratamiento();
            tr2.setNombre(TratamientosCorporales.ENVOLTURAS_CORPORALES.getNombre());
            tr2.settipoTratamiento("Corporal");
            tr2.setDetalle(TratamientosCorporales.ENVOLTURAS_CORPORALES.getDescripcion());
            tr2.setDuracion(java.time.LocalTime.of(1, 30));
            tr2.setCosto(1200.0);
            tr2.setEstado(true);
            tratData.cargaTratamiento(tr2);

            Tratamiento tr3 = new Tratamiento();
            tr3.setNombre(TratamientosCorporales.HIDROTERAPIA.getNombre());
            tr3.settipoTratamiento("Corporal");
            tr3.setDetalle(TratamientosCorporales.HIDROTERAPIA.getDescripcion());
            tr3.setDuracion(java.time.LocalTime.of(1, 0));
            tr3.setCosto(900.0);
            tr3.setEstado(true);
            tratData.cargaTratamiento(tr3);

            // ----------- TRATAMIENTOS FACIALES -----------
            Tratamiento tr4 = new Tratamiento();
            tr4.setNombre(TratamientosFaciales.FACIAL_BASICO.getNombre());
            tr4.settipoTratamiento("Facial");
            tr4.setDetalle(TratamientosFaciales.FACIAL_BASICO.getDescripcion());
            tr4.setDuracion(java.time.LocalTime.of(0, 45));
            tr4.setCosto(800.0);
            tr4.setEstado(true);
            tratData.cargaTratamiento(tr4);

            Tratamiento tr5 = new Tratamiento();
            tr5.setNombre(TratamientosFaciales.MICRODERMOABRASION.getNombre());
            tr5.settipoTratamiento("Facial");
            tr5.setDetalle(TratamientosFaciales.MICRODERMOABRASION.getDescripcion());
            tr5.setDuracion(java.time.LocalTime.of(1, 0));
            tr5.setCosto(1100.0);
            tr5.setEstado(true);
            tratData.cargaTratamiento(tr5);

            Tratamiento tr6 = new Tratamiento();
            tr6.setNombre(TratamientosFaciales.RADIOFRECUENCIA.getNombre());
            tr6.settipoTratamiento("Facial");
            tr6.setDetalle(TratamientosFaciales.RADIOFRECUENCIA.getDescripcion());
            tr6.setDuracion(java.time.LocalTime.of(1, 0));
            tr6.setCosto(1300.0);
            tr6.setEstado(true);
            tratData.cargaTratamiento(tr6);

            // ----------- MASAJISTAS (3 de cada tipo) -----------
            // FACIAL
            masData.altaMasajista(new Masajista(2001, "Ana", "Pérez", "111111111", 40111222, "Masajista", "Facial", true));
            masData.altaMasajista(new Masajista(2002, "Bruno", "Sosa", "111111112", 40111223, "Masajista", "Facial", true));
            masData.altaMasajista(new Masajista(2003, "Carla", "Mendez", "111111113", 40111224, "Masajista", "Facial", true));
            // CORPORAL
            masData.altaMasajista(new Masajista(2101, "Lucas", "Gonzalez", "222222221", 40222331, "Masajista", "Corporal", true));
            masData.altaMasajista(new Masajista(2102, "Martina", "Lopez", "222222222", 40222332, "Masajista", "Corporal", true));
            masData.altaMasajista(new Masajista(2103, "Diego", "Ferreira", "222222223", 40222333, "Masajista", "Corporal", true));
            // ESTETICO
            masData.altaMasajista(new Masajista(2201, "Sofia", "Martinez", "333333331", 40333441, "Masajista", "Estetico", true));
            masData.altaMasajista(new Masajista(2202, "Federico", "Ramos", "333333332", 40333442, "Masajista", "Estetico", true));
            masData.altaMasajista(new Masajista(2203, "Valentina", "Suarez", "333333333", 40333443, "Masajista", "Estetico", true));
            // RELAJACION
            masData.altaMasajista(new Masajista(2301, "Pedro", "Alvarez", "444444441", 40444551, "Masajista", "Relajacion", true));
            masData.altaMasajista(new Masajista(2302, "Luciana", "Vega", "444444442", 40444552, "Masajista", "Relajacion", true));
            masData.altaMasajista(new Masajista(2303, "Emilia", "Castro", "444444443", 40444553, "Masajista", "Relajacion", true));
            
            // ----------- VENDEDORES -----------
            vendedorData.altaVendedor(new Vendedor("Ezequiel", "Bequis", "2664880438", 44437768, PuestosDeTrabajo.VENDEDOR.getPuesto(), true));
            
            
            // ----------- CONSULTORIOS (Facial, Corporal, Estetico, Relajacion) -----------
            // FACIAL
            Consultorio c1 = new Consultorio();
            c1.setUsos(7);
            c1.setApto(Especialidades.FACIAL.getEspecialidad());
            ArrayList<Equipamiento> eq1 = new ArrayList<>();
            eq1.add(new Equipamiento(0, 0, Equipamientos.VAPORIZADOR_FACIAL.getNombre(), Equipamientos.VAPORIZADOR_FACIAL.getDescripcion()));
            eq1.add(new Equipamiento(0, 0, Equipamientos.LAMPARA_LUZ_PULSADA.getNombre(), Equipamientos.LAMPARA_LUZ_PULSADA.getDescripcion()));
            eq1.add(new Equipamiento(0, 0, Equipamientos.EQUIPO_MICRODERMOABRASION.getNombre(), Equipamientos.EQUIPO_MICRODERMOABRASION.getDescripcion()));
            c1.setEquipamiento(eq1);
            consultData.cargaConsultorio(c1);

            // CORPORAL
            Consultorio c2 = new Consultorio();
            c2.setUsos(10);
            c2.setApto(Especialidades.CORPORAL.getEspecialidad());
            ArrayList<Equipamiento> eq2 = new ArrayList<>();
            eq2.add(new Equipamiento(0, 0, Equipamientos.CAMILLA_MASAJES.getNombre(), Equipamientos.CAMILLA_MASAJES.getDescripcion()));
            eq2.add(new Equipamiento(0, 0, Equipamientos.CAMILLA_HIDROMASAJE.getNombre(), Equipamientos.CAMILLA_HIDROMASAJE.getDescripcion()));
            eq2.add(new Equipamiento(0, 0, Equipamientos.PIEDRAS_CALIENTES.getNombre(), Equipamientos.PIEDRAS_CALIENTES.getDescripcion()));
            c2.setEquipamiento(eq2);
            consultData.cargaConsultorio(c2);

            // ESTETICO
            Consultorio c3 = new Consultorio();
            c3.setUsos(8);
            c3.setApto(Especialidades.ESTETICO.getEspecialidad());
            ArrayList<Equipamiento> eq3 = new ArrayList<>();
            eq3.add(new Equipamiento(0, 0, Equipamientos.EQUIPO_RADIOFRECUENCIA.getNombre(), Equipamientos.EQUIPO_RADIOFRECUENCIA.getDescripcion()));
            eq3.add(new Equipamiento(0, 0, Equipamientos.MAQUINA_ULTRASONIDO.getNombre(), Equipamientos.MAQUINA_ULTRASONIDO.getDescripcion()));
            eq3.add(new Equipamiento(0, 0, Equipamientos.EQUIPO_MICRODERMOABRASION.getNombre(), Equipamientos.EQUIPO_MICRODERMOABRASION.getDescripcion()));
            c3.setEquipamiento(eq3);
            consultData.cargaConsultorio(c3);

            // RELAJACION
            Consultorio c4 = new Consultorio();
            c4.setUsos(12);
            c4.setApto(Especialidades.RELAJACION.getEspecialidad());
            ArrayList<Equipamiento> eq4 = new ArrayList<>();
            eq4.add(new Equipamiento(0, 0, Equipamientos.CABINA_SAUNA.getNombre(), Equipamientos.CABINA_SAUNA.getDescripcion()));
            eq4.add(new Equipamiento(0, 0, Equipamientos.DUCHA_SENSORIAL.getNombre(), Equipamientos.DUCHA_SENSORIAL.getDescripcion()));
            eq4.add(new Equipamiento(0, 0, Equipamientos.SILLON_RELAX.getNombre(), Equipamientos.SILLON_RELAX.getDescripcion()));
            c4.setEquipamiento(eq4);
            consultData.cargaConsultorio(c4);

            // ----------- CLIENTES -----------
            clienteData.guardarCliente(new Cliente(50111223, "Julieta", "Molina", "2664880439", 28, "Alergia a cremas", true));
            clienteData.guardarCliente(new Cliente(50111224, "Santiago", "Paz", "2664880440", 35, "Hipertensión", true));
            clienteData.guardarCliente(new Cliente(50111225, "Camila", "Rossi", "2664880441", 30, "Ninguna", true));
            clienteData.guardarCliente(new Cliente(50111226, "Valeria", "Gomez", "2664880442", 40, "Diabetes", true));

     
            // ----------- PRODUCTOS -----------
            productoData.guardarProducto(new Producto("Crema Hidratante de Aloe Vera", "BioSkin",
                    "Crema corporal ultra hidratante con extracto natural de aloe.", 4500.0, 25, true, true));

            productoData.guardarProducto(new Producto("Aceite Esencial de Lavanda", "AromaZen",
                    "Aceite esencial puro para relajación y aromaterapia.", 3200.0, 15, true, true));

            productoData.guardarProducto(new Producto("Sales de Baño Minerales", "SpaMineral",
                    "Sales con magnesio y aroma a rosas para baños descontracturantes.", 2800.0, 18, false, true));

            productoData.guardarProducto(new Producto("Jabón Artesanal de Caléndula", "NaturalCare",
                    "Jabón ideal para piel sensible con extracto de caléndula.", 1500.0, 40, true, true));

            productoData.guardarProducto(new Producto("Exfoliante Corporal de Café", "TerraBella",
                    "Exfoliante natural a base de café orgánico.", 3900.0, 20, true, false));

            productoData.guardarProducto(new Producto("Mascarilla Facial de Arcilla Verde", "PureSkin",
                    "Máscara detox para absorber impurezas y regular la oleosidad.", 3500.0, 12, true, true));

            productoData.guardarProducto(new Producto("Aceite para Masajes Relajante", "AromaZen",
                    "Mezcla de aceites naturales para masajes profundos.", 4200.0, 14, false, true));

            productoData.guardarProducto(new Producto("Toalla Facial de Microfibra", "SoftTouch",
                    "Toalla ultra suave apta para spa y tratamientos faciales.", 2100.0, 30, false, true));

            productoData.guardarProducto(new Producto("Crema para Masajes Neutra", "ProSpa",
                    "Crema hipoalergénica ideal para masajes prolongados.", 4800.0, 10, false, false));

            productoData.guardarProducto(new Producto("Velas Aromáticas de Vainilla", "EssenceHome",
                    "Vela artesanal para aromatizar ambientes y sesiones de spa.", 2600.0, 22, true, true));

            productoData.guardarProducto(new Producto("Tónico Facial de Manzanilla", "BioSkin",
                    "Tónico suave calmante y refrescante.", 3300.0, 16, true, true));

            productoData.guardarProducto(new Producto("Espuma de Limpieza Facial", "PureSkin",
                    "Limpieza profunda para todo tipo de piel.", 3600.0, 19, false, true));

            productoData.guardarProducto(new Producto("Crema Antiestrés de Lavanda", "NaturalCare",
                    "Crema corporal relajante con aroma suave.", 4100.0, 13, true, false));

            productoData.guardarProducto(new Producto("Shampoo Sólido Nutritivo", "EcoBeauty",
                    "Shampoo sólido con aceite de argán, sin plástico.", 2900.0, 25, true, true));

            productoData.guardarProducto(new Producto("Acondicionador Herbal", "EcoBeauty",
                    "Acondicionador con extractos naturales de romero y salvia.", 3400.0, 17, true, false));

            productoData.guardarProducto(new Producto("Mascarilla Capilar Reparadora", "HairSpa",
                    "Tratamiento para reparar puntas y suavizar el cabello.", 5200.0, 11, false, true));

            productoData.guardarProducto(new Producto("Gel de Ducha Energizante", "TerraBella",
                    "Gel con aroma cítrico revitalizante.", 2700.0, 28, false, false));

            productoData.guardarProducto(new Producto("Loción Corporal de Coco", "SoftTouch",
                    "Loción nutritiva con aroma a coco tropical.", 3900.0, 14, true, true));

            productoData.guardarProducto(new Producto("Piedras Calientes para Masajes", "ProSpa",
                    "Juego de piedras volcánicas para terapias de calor.", 6500.0, 6, false, true));

            productoData.guardarProducto(new Producto("Spray Ambiental Relax", "EssenceHome",
                    "Spray aromático con lavanda y bergamota para ambientes.", 3100.0, 20, true, true));

            JOptionPane.showMessageDialog(null, "¡Datos de prueba cargados exitosamente!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos de prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



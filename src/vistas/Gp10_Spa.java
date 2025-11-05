package vistas;

import Persistencia.miConexion;
import Persistencia.EmpleadoData;
import Persistencia.MasajistaData;
import Persistencia.ProductoData;
import Persistencia.TratamientoData;
import Persistencia.VendedorData;
//import constantes.ConstantesPuestos; ->Mati: Lo comente para que aparezca con error.
import entidades.Empleado;
import entidades.Vendedor;
import constantes.*;
//import static constantes.ConstantesPuestos.MASAJISTA; ->Mati: Lo comente para que aparezca con error.
import entidades.Masajista;
import entidades.Producto;
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
        scanner = new Scanner(System.in);
      

        conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
        
        MasajistaData masajistaData = new MasajistaData(conexion);
        
        //EmpleadoData empleadodata = new EmpleadoData(conexion);
        //VendedorData vendedorData = new VendedorData(conexion);

        /*
        vendedorData.bajaLogica(1);
        vendedorData.bajaLogica(2);
        */
        
        /*
        Vendedor vend = new Vendedor();
        vend.setNombre("Sol");
        vend.setApellido("DASDA");
        vend.setTelefono("1111111111");
        vend.setDni(564465564);
        vend.setPuesto(PuestosDeTrabajo.VENDEDOR.getPuesto());
        vend.setEstado(true);
        
        vendedorData.altaVendedor(vend);
         */
        //Empleado emp =  new Empleado(44437768, ConstantesPuestos.MASAJISTA);
        //empleadodata.altaEmpleado(emp);
        /*   ----Prueba método actualizarVendedor (tiene que tener el mismo ID y DNI)----
    
    
    // Crear vendedor con datos actualizados
    Vendedor vend = new Vendedor();
    vend.setIdEmpleado(3); // ID existente en la BD
    vend.setNombre("Juan");
    vend.setApellido("Pérez");
    vend.setTeléfono("1111111111");
    vend.setDni(30456789);
    
    // Mostrar datos antes de actualizar
    System.out.println("Actualizando vendedor con ID: " + vend.getIdEmpleado());
    System.out.println("Nombre: " + vend.getNombre());
    System.out.println("Apellido: " + vend.getApellido());
    System.out.println("Teléfono: " + vend.getTeléfono());
    System.out.println("DNI: " + vend.getDni());
    
    
    // Ejecutar actualización
    vendedorData.actualizarVendedor(vend);
    System.out.println("Prueba de actualización completada");
    
         */
        // crear masajista con datos actualizados
        
      /*  EmpleadoData empleadoData1 = new EmpleadoData(conexion);
       

        Masajista masajista = new Masajista();
        masajista.setMatricula(569);
        masajista.setIdEmpleado(3);
        masajista.setNombre("Lopsf");
        masajista.setApellido("adsdsa");
        masajista.setTelefono("2664345678");
        masajista.setDni(485141);
        masajista.setPuesto("masajista");  ->Mati: Lo comente para que aparezca con error.
        masajista.setEspecialidad("estético");
        masajista.setEstado(true);

        masajistaData.altaMasajista(masajista);*/

      EmpleadoData empleadoData1 = new EmpleadoData(conexion);

        Masajista masajista = new Masajista();
    /*    masajista.setMatricula(124);
        // NO setear idEmpleado manualmente - lo genera automáticamente altaMasajista()
        // masajista.setIdEmpleado(3);  // COMENTAR ESTA LÍNEA
        masajista.setNombre("Lois");
        masajista.setApellido("Lane");
        masajista.setTelefono("2664345678");
        masajista.setDni(4651141);
        masajista.setPuesto("Masajista");  // String con el nombre del puesto
        masajista.setEspecialidad("FACIAL");      // Usar constantes del enum
        masajista.setEstado(true);

        masajistaData.altaMasajista(masajista);  */
        
        Masajista act = new Masajista();
                    System.out.print("ID Empleado: ");
                    act.setIdEmpleado(scanner.nextInt());
                    System.out.print("Matricula: ");
                    act.setMatricula(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Nombre: ");
                    act.setNombre(scanner.nextLine());
                    System.out.print("Apellido: ");
                    act.setApellido(scanner.nextLine());
                    System.out.print("Telefono: ");
                    act.setTelefono(scanner.nextLine());
                    System.out.print("DNI: ");
                    act.setDni(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Puesto: ");
                    act.setPuesto(scanner.nextLine());
                    System.out.print("Especialidad: ");
                    act.setEspecialidad(scanner.nextLine());
                    act.setEstado(true);
                    masajistaData.actualizarMasajista(act);
                    
                    // Ejecutar actualización
                    masajistaData.actualizarMasajista(masajista);
                    System.out.println("Prueba de actualización completada");
      
      
      
        /*
        System.out.println(TratamientosFaciales.LUZ_PULSADA_INTENSA.getNombre() + ": " 
                + TratamientosFaciales.LUZ_PULSADA_INTENSA.getDescripcion());

        */
        
        
        
        /*
        //-----------TESTS CON SCANNER PARA TRATAMIENTO--------------
        TratamientoData tratamientoData = new TratamientoData(conexion);
        ProductoData productoData = new ProductoData(conexion);
                    
        int opcion;
        do {
            System.out.println("\n=== TESTS CRUD TRATAMIENTO ===");
            System.out.println("1. Test CREATE - Cargar nuevo tratamiento");
            System.out.println("2. Test READ - Listar todos los tratamientos");
            System.out.println("3. Test UPDATE - Actualizar tratamiento");
            System.out.println("4. Test BAJA - Dar de baja tratamiento");
            System.out.println("5. Test ALTA - Dar de alta tratamiento");
            System.out.println("6. Test DELETE - Borrar tratamiento (físico)");
            System.out.println("7. Test LISTAR POR TIPO");
            System.out.println("8. Ejecutar TODOS los tests automáticamente");
            System.out.println("0. Salir");
            System.out.print("\nIngrese opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    testCreate(tratamientoData, productoData);
                    break;
                case 2:
                    testRead(tratamientoData);
                    break;
                case 3:
                    testUpdate(tratamientoData, productoData);
                    break;
                case 4:
                    testBaja(tratamientoData);
                    break;
                case 5:
                    testAlta(tratamientoData);
                    break;
                case 6:
                    testDelete(tratamientoData);
                    break;
                case 7:
                    testListarPorTipo(tratamientoData);
                    break;
                case 8:
                    ejecutarTodosLosTests(tratamientoData, productoData);
                    break;
                case 0:
                    System.out.println("\nSaliendo...");
                    break;
                default:
                    System.out.println("\nOpción inválida");
                    break;
            }
            
        } while (opcion != 0);
        
        scanner.close();
    }
    
    // ==================== TEST 1: CREATE ====================
    private static void testCreate(TratamientoData tratamientoData, ProductoData productoData) {
        System.out.println("\n=== TEST CREATE ===");
        
        try {
            // Seleccionar tipo de tratamiento
            System.out.println("\nTipo de tratamiento:");
            System.out.println("1. Facial");
            System.out.println("2. Corporal");
            System.out.print("Opción: ");
            int tipoSelec = scanner.nextInt();
            scanner.nextLine();
            
            String tipoTratamiento = "";
            String nombre = "";
            String detalle = "";
            
            if (tipoSelec == 1) {
                System.out.println("\n=== TRATAMIENTOS FACIALES ===");
                TratamientosFaciales[] faciales = TratamientosFaciales.values();
                for (int i = 0; i < faciales.length; i++) {
                    System.out.println((i + 1) + ". " + faciales[i].getNombre() + " - " + faciales[i].getDescripcion());
                }
                System.out.print("Seleccione: ");
                int seleccion = scanner.nextInt();
                scanner.nextLine();
                tipoTratamiento = faciales[seleccion - 1].getNombre();
                nombre = faciales[seleccion - 1].getNombre();
                detalle = faciales[seleccion - 1].getDescripcion();
            } else {
                System.out.println("\n=== TRATAMIENTOS CORPORALES ===");
                TratamientosCorporales[] corporales = TratamientosCorporales.values();
                for (int i = 0; i < corporales.length; i++) {
                    System.out.println((i + 1) + ". " + corporales[i].getNombre() + " - " + corporales[i].getDescripcion());
                }
                System.out.print("Seleccione: ");
                int seleccion = scanner.nextInt();
                scanner.nextLine();
                tipoTratamiento = corporales[seleccion - 1].getNombre();
                nombre = corporales[seleccion - 1].getNombre();
                detalle = corporales[seleccion - 1].getDescripcion();
            }
            
            System.out.println("\nTratamiento seleccionado:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Detalle: " + detalle);
            
            LocalTime duracion = null;
            while (duracion == null) {
                System.out.print("Duración (ej: 1:30): ");
                String duracionStr = scanner.nextLine();
                duracion = parsearDuracion(duracionStr);
                if (duracion == null) {
                    System.out.println("✗ Formato inválido");
                }
            }
            
            System.out.print("Costo: ");
            double costo = scanner.nextDouble();
            scanner.nextLine();
            
            // Productos opcionales
            ArrayList<Producto> productos = new ArrayList<>();
            System.out.print("¿Agregar productos? (S/N): ");
            String resp = scanner.nextLine().toUpperCase();
            
            while (resp.equals("S")) {
                System.out.print("ID del producto: ");
                int idProd = scanner.nextInt();
                scanner.nextLine();
                
                Producto prod = productoData.buscarProducto(idProd);
                if (prod != null) {
                    productos.add(prod);
                    System.out.println("✓ Agregado: " + prod.getNombre());
                } else {
                    System.out.println("✗ Producto no encontrado");
                }
                
                System.out.print("¿Agregar otro? (S/N): ");
                resp = scanner.nextLine().toUpperCase();
            }
            
            Tratamiento tratamiento = new Tratamiento(nombre, tipoTratamiento, detalle, productos, duracion, costo, true);
            tratamientoData.cargaTratamiento(tratamiento);
            
            System.out.println("\n✓ TEST PASÓ - Tratamiento creado");
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
    // ==================== TEST 2: READ ====================
    private static void testRead(TratamientoData tratamientoData) {
        System.out.println("\n=== TEST READ ===");
        
        try {
            ArrayList<Tratamiento> tratamientos = tratamientoData.listarTodosTratamientos();
            
            if (tratamientos.isEmpty()) {
                System.out.println("No hay tratamientos");
            } else {
                System.out.println("\nTotal: " + tratamientos.size() + " tratamiento(s)\n");
                for (Tratamiento t : tratamientos) {
                    System.out.println("ID: " + t.getCodTratam() + 
                                     " | " + t.getNombre() + 
                                     " | $" + t.getCosto() + 
                                     " | " + (t.isEstado() ? "Activo" : "Inactivo"));
                }
            }
            System.out.println("\n✓ TEST PASÓ");
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
    // ==================== TEST 3: UPDATE ====================
    private static void testUpdate(TratamientoData tratamientoData, ProductoData productoData) {
        System.out.println("\n=== TEST UPDATE ===");
        
        try {
            System.out.print("Código del tratamiento a actualizar: ");
            int codTratam = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nNuevo Tipo:");
            System.out.println("1. Facial");
            System.out.println("2. Corporal");
            System.out.print("Opción: ");
            int tipoSelec = scanner.nextInt();
            scanner.nextLine();
            
            String tipoTratamiento = "";
            String nombre = "";
            String detalle = "";
            
            if (tipoSelec == 1) {
                System.out.println("\n=== TRATAMIENTOS FACIALES ===");
                TratamientosFaciales[] faciales = TratamientosFaciales.values();
                for (int i = 0; i < faciales.length; i++) {
                    System.out.println((i + 1) + ". " + faciales[i].getNombre() + " - " + faciales[i].getDescripcion());
                }
                System.out.print("Seleccione: ");
                int sel = scanner.nextInt();
                scanner.nextLine();
                tipoTratamiento = faciales[sel - 1].getNombre();
                nombre = faciales[sel - 1].getNombre();
                detalle = faciales[sel - 1].getDescripcion();
            } else {
                System.out.println("\n=== TRATAMIENTOS CORPORALES ===");
                TratamientosCorporales[] corporales = TratamientosCorporales.values();
                for (int i = 0; i < corporales.length; i++) {
                    System.out.println((i + 1) + ". " + corporales[i].getNombre() + " - " + corporales[i].getDescripcion());
                }
                System.out.print("Seleccione: ");
                int sel = scanner.nextInt();
                scanner.nextLine();
                tipoTratamiento = corporales[sel - 1].getNombre();
                nombre = corporales[sel - 1].getNombre();
                detalle = corporales[sel - 1].getDescripcion();
            }
            
            System.out.println("\nTratamiento seleccionado:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Detalle: " + detalle);
            
            LocalTime duracion = null;
            while (duracion == null) {
                System.out.print("Nueva duración (ej: 1:30): ");
                duracion = parsearDuracion(scanner.nextLine());
            }
            
            System.out.print("Nuevo costo: ");
            double costo = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("¿Está activo? (S/N): ");
            boolean estado = scanner.nextLine().toUpperCase().equals("S");
            
            ArrayList<Producto> productos = new ArrayList<>();
            System.out.print("¿Actualizar productos? (S/N): ");
            String resp = scanner.nextLine().toUpperCase();
            
            if (resp.equals("S")) {
                while (resp.equals("S")) {
                    System.out.print("ID producto: ");
                    int idProd = scanner.nextInt();
                    scanner.nextLine();
                    
                    Producto prod = productoData.buscarProducto(idProd);
                    if (prod != null) {
                        productos.add(prod);
                        System.out.println("✓ Agregado");
                    }
                    
                    System.out.print("¿Otro? (S/N): ");
                    resp = scanner.nextLine().toUpperCase();
                }
            }
            
            Tratamiento tratamiento = new Tratamiento(codTratam, nombre, tipoTratamiento, detalle, productos, duracion, costo, estado);
            tratamientoData.actualizarTratamiento(tratamiento);
            
            System.out.println("\n✓ TEST PASÓ");
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
    // ==================== TEST 4: BAJA ====================
    private static void testBaja(TratamientoData tratamientoData) {
        System.out.println("\n=== TEST BAJA ===");
        
        try {
            System.out.print("Código del tratamiento: ");
            int codTratam = scanner.nextInt();
            scanner.nextLine();
            
            tratamientoData.bajaTratamiento(codTratam);
            System.out.println("\n✓ TEST PASÓ");
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
    // ==================== TEST 5: ALTA ====================
    private static void testAlta(TratamientoData tratamientoData) {
        System.out.println("\n=== TEST ALTA ===");
        
        try {
            System.out.print("Código del tratamiento: ");
            int codTratam = scanner.nextInt();
            scanner.nextLine();
            
            tratamientoData.altaTratamiento(codTratam);
            System.out.println("\n✓ TEST PASÓ");
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
    // ==================== TEST 6: DELETE ====================
    private static void testDelete(TratamientoData tratamientoData) {
        System.out.println("\n=== TEST DELETE ===");
        
        try {
            System.out.print("Código del tratamiento: ");
            int codTratam = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("¿Seguro? Esta acción no se puede deshacer (S/N): ");
            String confirm = scanner.nextLine().toUpperCase();
            
            if (confirm.equals("S")) {
                tratamientoData.borrarTratamiento(codTratam);
                System.out.println("\n✓ TEST PASÓ");
            } else {
                System.out.println("Operación cancelada");
            }
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
    // ==================== TEST 7: LISTAR POR TIPO ====================
    private static void testListarPorTipo(TratamientoData tratamientoData) {
        System.out.println("\n=== TEST LISTAR POR TIPO ===");
        
        try {
            System.out.print("Tipo a buscar: ");
            String tipo = scanner.nextLine();
            
            ArrayList<Tratamiento> tratamientos = tratamientoData.listarTratamientosPorTipo(tipo);
            
            if (tratamientos.isEmpty()) {
                System.out.println("No hay tratamientos de ese tipo");
            } else {
                System.out.println("\n=== TIPO: " + tipo + " ===\n");
                for (Tratamiento t : tratamientos) {
                    System.out.println("ID: " + t.getCodTratam() + " - " + t.getNombre() + " - $" + t.getCosto());
                }
            }
            System.out.println("\n✓ TEST PASÓ");
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST FALLÓ: " + e.getMessage());
        }
    }
    
        
        
    // ==================== EJECUTAR TODOS ====================
    private static void ejecutarTodosLosTests(TratamientoData tratamientoData, ProductoData productoData) {
        System.out.println("\n=== EJECUTANDO TODOS LOS TESTS ===\n");
        
        int exitosos = 0;
        int total = 6;
        
        // CREATE
        System.out.println("1/6 CREATE...");
        try {
            Tratamiento t = new Tratamiento("Test Auto", "Limpieza facial profunda", "Test", new ArrayList<>(), LocalTime.of(1, 0), 5000.0, true);
            tratamientoData.cargaTratamiento(t);
            System.out.println("✓ PASÓ");
            exitosos++;
        } catch (Exception e) {
            System.out.println("✗ FALLÓ");
        }
        
        // READ
        System.out.println("\n2/6 READ...");
        try {
            ArrayList<Tratamiento> lista = tratamientoData.listarTodosTratamientos();
            System.out.println("✓ PASÓ - " + lista.size() + " registros");
            exitosos++;
        } catch (Exception e) {
            System.out.println("✗ FALLÓ");
        }
        
        // UPDATE
        System.out.println("\n3/6 UPDATE...");
        try {
            ArrayList<Tratamiento> lista = tratamientoData.listarTodosTratamientos();
            if (!lista.isEmpty()) {
                Tratamiento t = lista.get(0);
                t.setCosto(t.getCosto() + 100);
                tratamientoData.actualizarTratamiento(t);
                System.out.println("✓ PASÓ");
                exitosos++;
            }
        } catch (Exception e) {
            System.out.println("✗ FALLÓ");
        }
        
        // BAJA
        System.out.println("\n4/6 BAJA...");
        try {
            ArrayList<Tratamiento> lista = tratamientoData.listarTodosTratamientos();
            if (!lista.isEmpty()) {
                tratamientoData.bajaTratamiento(lista.get(0).getCodTratam());
                System.out.println("✓ PASÓ");
                exitosos++;
            }
        } catch (Exception e) {
            System.out.println("✗ FALLÓ");
        }
        
        // ALTA
        System.out.println("\n5/6 ALTA...");
        try {
            ArrayList<Tratamiento> lista = tratamientoData.listarTodosTratamientos();
            if (!lista.isEmpty()) {
                tratamientoData.altaTratamiento(lista.get(0).getCodTratam());
                System.out.println("✓ PASÓ");
                exitosos++;
            }
        } catch (Exception e) {
            System.out.println("✗ FALLÓ");
        }
        
        // DELETE
        System.out.println("\n6/6 DELETE...");
        try {
            Tratamiento t = new Tratamiento("TEMP", "Hidratación facial", "Temp", new ArrayList<>(), LocalTime.of(0, 30), 1000.0, true);
            tratamientoData.cargaTratamiento(t);
            
            ArrayList<Tratamiento> lista = tratamientoData.listarTodosTratamientos();
            for (Tratamiento tr : lista) {
                if (tr.getNombre().equals("TEMP")) {
                    tratamientoData.borrarTratamiento(tr.getCodTratam());
                    break;
                }
            }
            System.out.println("✓ PASÓ");
            exitosos++;
        } catch (Exception e) {
            System.out.println("✗ FALLÓ");
        }
        
        System.out.println("\n==================");
        System.out.println("RESUMEN: " + exitosos + "/" + total);
        System.out.println("==================");
    }
    
    // ==================== MÉTODO AUXILIAR ====================
    private static LocalTime parsearDuracion(String duracionStr) {
        try {
            return LocalTime.parse(duracionStr);
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
                return LocalTime.parse(duracionStr, formatter);
            } catch (DateTimeParseException e2) {
                return null;
            }
        }
    }
*/
        
        
        /*//--------MASAJISTA-------------
         int opcion;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Alta");
            System.out.println("2. Actualizar");
            System.out.println("3. Eliminar");
            System.out.println("4. Listar");
            System.out.println("5. Buscar por DNI");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1: // ALTA
                    Masajista nuevo = new Masajista();
                    nuevo.setMatricula(12345);
                    nuevo.setNombre("Juan");
                    nuevo.setApellido("Perez");
                    nuevo.setTelefono("2664123456");
                    nuevo.setDni(35123456);
                    nuevo.setPuesto("Masajista");
                    nuevo.setEspecialidad("Estetico");
                    nuevo.setEstado(true);
                    System.out.println("Creando masajista: " + nuevo.getNombre() + " " + nuevo.getApellido());
                    masajistaData.altaMasajista(nuevo);
                    break;
                    
                case 2: // ACTUALIZAR
                    Masajista act = new Masajista();
                    System.out.print("ID Empleado: ");
                    act.setIdEmpleado(scanner.nextInt());
                    System.out.print("Matricula: ");
                    act.setMatricula(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Nombre: ");
                    act.setNombre(scanner.nextLine());
                    System.out.print("Apellido: ");
                    act.setApellido(scanner.nextLine());
                    System.out.print("Telefono: ");
                    act.setTelefono(scanner.nextLine());
                    System.out.print("DNI: ");
                    act.setDni(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Puesto: ");
                    act.setPuesto(scanner.nextLine());
                    System.out.print("Especialidad: ");
                    act.setEspecialidad(scanner.nextLine());
                    act.setEstado(true);
                    masajistaData.actualizarMasajista(act);
                    break;
                    
                case 3: // ELIMINAR
                    System.out.print("ID Empleado a eliminar: ");
                    int id = scanner.nextInt();
                    masajistaData.eliminarMasajista(id);
                    break;
                    
                case 4: // LISTAR
                    List<Masajista> lista = masajistaData.listarMasajistas();
                    System.out.println("\n--- MASAJISTAS ---");
                    for (Masajista m : lista) {
                        System.out.println("ID: " + m.getIdEmpleado() + 
                            " | " + m.getNombre() + " " + m.getApellido() + 
                            " | DNI: " + m.getDni() + 
                            " | Mat: " + m.getMatricula());
                    }
                    break;
                    
                case 5: // BUSCAR POR DNI
                    System.out.print("DNI: ");
                    int dni = scanner.nextInt();
                    Masajista m = masajistaData.buscarMasajistaPorDNI(dni);
                    if (m != null) {
                        System.out.println("Encontrado: " + m.getNombre() + " " + m.getApellido());
                    }
                    break;
            }
        } while (opcion != 0);
        
        scanner.close();*/
    }
}
        
        
        
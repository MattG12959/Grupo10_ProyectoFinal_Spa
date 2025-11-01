package vistas;

import Persistencia.miConexion;
import Persistencia.EmpleadoData;
import Persistencia.MasajistaData;
import Persistencia.VendedorData;
//import constantes.ConstantesPuestos; ->Mati: Lo comente para que aparezca con error.
import entidades.Empleado;
import entidades.Vendedor;
import constantes.*;
//import static constantes.ConstantesPuestos.MASAJISTA; ->Mati: Lo comente para que aparezca con error.
import entidades.Masajista;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia Quiroga Dorzan Alejo
 */
// Clase Testing
public class Gp10_Spa {

    private static miConexion conexion;

    public static void main(String[] args) {
        conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");

        //EmpleadoData empleadodata = new EmpleadoData(conexion);
        VendedorData vendedorData = new VendedorData(conexion);

        /*
        vendedorData.bajaLogica(1);
        vendedorData.bajaLogica(2);
        */
        
        Vendedor vend = new Vendedor();
        vend.setNombre("Sol");
        vend.setApellido("DASDA");
        vend.setTelefono("1111111111");
        vend.setDni(564465564);
        vend.setPuesto(PuestosDeTrabajo.VENDEDOR.getPuesto());
        vend.setEstado(true);
        
        vendedorData.altaVendedor(vend);
         
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
        /*
        EmpleadoData empleadoData1 = new EmpleadoData(conexion);
        MasajistaData masajistaData = new MasajistaData(conexion);

        Masajista masajista = new Masajista();
        masajista.setMatricula(569);
        masajista.setIdEmpleado(3);
        masajista.setNombre("Lopsf");
        masajista.setApellido("adsdsa");
        masajista.setTelefono("2664345678");
        masajista.setDni(485141);
        //masajista.setPuesto(ConstantesPuestos.MASAJISTA);  ->Mati: Lo comente para que aparezca con error.
        masajista.setEspecialidad("estético");
        masajista.setEstado(true);

        masajistaData.altaMasajista(masajista);*/

        System.out.println(TratamientosFaciales.LUZ_PULSADA_INTENSA.getNombre() + ": " 
                + TratamientosFaciales.LUZ_PULSADA_INTENSA.getDescripcion());
        
    }
}

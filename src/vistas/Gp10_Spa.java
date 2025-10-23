
package vistas;

import Persistencia.miConexion;
import Persistencia.EmpleadoData;
import Persistencia.VendedorData;
import constantes.ConstantesPuestos;
import entidades.Empleado;
import entidades.Vendedor;
/**
 * @author Grupo10 
 * 
 * Altamirano Karina 
 * Gianfranco Antonacci Matías 
 * Bequis Marcos Ezequiel 
 * Dave Natalia 
 * Quiroga Dorzan Alejo
 */

// Clase Testing
public class Gp10_Spa {
    private static miConexion conexion;
    
    public static void main(String[] args) {
       conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
       
       EmpleadoData empleadodata = new EmpleadoData(conexion);
       
       //Empleado emp =  new Empleado(44437768, ConstantesPuestos.MASAJISTA);
       //empleadodata.altaEmpleado(emp);
    
    
    /*   ----Prueba método actualizarVendedor (tiene que tener el mismo ID y DNI)----
    VendedorData vendedorData = new VendedorData(conexion);
    
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
    }
 }


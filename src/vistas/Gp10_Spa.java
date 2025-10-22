
package vistas;

import Persistencia.miConexion;
import Persistencia.EmpleadoData;
import constantes.ConstantesPuestos;
import entidades.Empleado;
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
    }
}

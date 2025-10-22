/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vistas;

import Persistencia.miConexion;
import Persistencia.EmpleadoData;
/**
 * @author Grupo10 
 * 
 * Altamirano Karina 
 * Gianfranco Antonacci Matías 
 * Bequis Marcos Ezequiel 
 * Dave Natalia 
 * Quiroga Dorzan Alejo
 */

public class Gp10_Spa {
    private static miConexion conexion;
    
    public static void main(String[] args) {
       conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
       
       EmpleadoData empleadodata = EmpleadoData(conexion);
    }
}


package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author Grupo10 
 * Altamirano Karina
 * Gianfranco Antonacci Matías
 * Bequis Marcos Ezequiel
 * Dave  Natalia
  */
public class miConexion {
    private String url;
    private String user;
    private String password;
    
    private static Connection conexion = null; 

    //Constructor para la conexion a la base con la url, su usuaio y su contraseña.
    public miConexion(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    
    //Establecer y guardar la conexión.
    public Connection buscarConexion(){
        if(conexion == null){
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                conexion = DriverManager.getConnection(url,user,password);
                
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo cargar el driver: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return conexion;
    }
}



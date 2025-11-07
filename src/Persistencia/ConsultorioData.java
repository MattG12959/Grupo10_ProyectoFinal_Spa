/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Consultorio;
import entidades.Equipamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author GP10
 */
public class ConsultorioData {

    private Connection con;

    public ConsultorioData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    public void cargaConsultorio(Consultorio consultorio) {
        String sql = "INSERT INTO `consultorio`(`usos`, `apto`) VALUES (?, ?)";

        // Creo una nueva conexion (como en tu patrón)
        miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
        EquipamientoData ed = new EquipamientoData(conexion);

        try {
            PreparedStatement ps = conexion.buscarConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, consultorio.getUsos());
            ps.setString(2, consultorio.getApto());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            consultorio.setNroConsultorio(rs.getInt(1));
            rs.close();
            ps.close();

            for (Equipamiento e : consultorio.getEquipamiento()) {
                e.setNroConsultorio(consultorio.getNroConsultorio());
            }

            // delego la inserción a EquipamientoData 
            ed.cargaEquipamiento(consultorio.getEquipamiento());

            JOptionPane.showMessageDialog(null, "Consultorio dado de alta", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la tabla de consultorios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Consultorio buscarConsultorio(int idConsultorio) {
        String sql = "SELECT * FROM consultorio WHERE idConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idConsultorio);
            ResultSet rs = ps.executeQuery();

            Consultorio consultorio = null;
            if (rs.next()) {
                consultorio = new Consultorio();
                consultorio.setNroConsultorio(rs.getInt("idConsultorio"));
                consultorio.setUsos(rs.getInt("usos"));
                consultorio.setApto(rs.getString("apto"));

                // cargar equipamiento
                String sqlEq = "SELECT * FROM equipamiento WHERE idConsultorio = ?";

                PreparedStatement psEq = con.prepareStatement(sqlEq);

                psEq.setInt(1, idConsultorio);
                ResultSet rsEq = psEq.executeQuery();

                ArrayList<Equipamiento> listaEq = new ArrayList<>();

                while (rsEq.next()) {
                    Equipamiento e = new Equipamiento();
                    e.setIdEquipamiento(rsEq.getInt("idEquipamiento"));
                    e.setNroConsultorio(rsEq.getInt("idConsultorio"));
                    e.setNombre_equipamiento(rsEq.getString("nombre"));
                    e.setDescripcion_equipamiento(rsEq.getString("descripcion"));
                    listaEq.add(e);
                }

                consultorio.setEquipamiento(new ArrayList<>(listaEq));
                rsEq.close();
                psEq.close();
            }

            ps.close();
            return consultorio;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el consultorio. " + ex.getMessage());
            return null;
        }
    }

    public void eliminarConsultorio(int idConsultorio) {
        String sql = "DELETE FROM consultorio WHERE idConsultorio = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idConsultorio);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el consultorio con ese código.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar consultorio. " + ex.getMessage());
        }
    }

    public ArrayList<Consultorio> listarConsultorios() {
        ArrayList<Consultorio> consultorios = new ArrayList<>();
        String sql = "SELECT * FROM consultorio";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio c = new Consultorio();
                c.setNroConsultorio(rs.getInt("idConsultorio"));
                c.setUsos(rs.getInt("usos"));
                c.setApto(rs.getString("apto"));

                // cargar equipamientp
                String sqlEq = "SELECT * FROM equipamiento WHERE idConsultorio = ?";
                PreparedStatement psEq = con.prepareStatement(sqlEq);
                psEq.setInt(1, c.getNroConsultorio());
                ResultSet rsEq = psEq.executeQuery();
                ArrayList<Equipamiento> listaEq = new ArrayList<>();
                while (rsEq.next()) {
                    Equipamiento e = new Equipamiento();
                    e.setIdEquipamiento(rsEq.getInt("idEquipamiento"));
                    e.setNroConsultorio(rsEq.getInt("idConsultorio"));
                    e.setNombre_equipamiento(rsEq.getString("nombre"));
                    e.setDescripcion_equipamiento(rsEq.getString("descripcion"));
                    listaEq.add(e);
                }
                c.setEquipamiento(new ArrayList<>(listaEq));
                rsEq.close();
                psEq.close();

                consultorios.add(c);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar consultorios. " + ex.getMessage());
        }
        return consultorios;
    }
}

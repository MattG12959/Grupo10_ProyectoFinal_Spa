package Persistencia;

import entidades.DiaDeSpa;
import entidades.Sesion;
import entidades.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DiaDeSpaData {

    private Connection con = null;

    public DiaDeSpaData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    // Guardar DiaDeSpa
    public void guardarDiaDeSpa(DiaDeSpa d) {
        String sql = "INSERT INTO dia_de_spa (fecha_y_hora, preferencias, idCliente, monto, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(d.getFechayhora()));
            ps.setString(2, d.getPreferencias());
            ps.setInt(3, d.getCliente().getCodCli()); 
            ps.setDouble(4, d.getMonto());
            ps.setBoolean(5, d.isEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                d.setCodPack(rs.getInt(1));
            }

            // guardar sesiones y tabla intermedia dia_de_spa_sesion
            miConexion conexion = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
            SesionData sd = new SesionData(conexion);
            String sqlLink = "INSERT INTO dia_de_spa_sesion (codPack, codSesion) VALUES (?, ?)";

            for (Sesion s : d.getSesiones()) {
                s.getDiaDeSpa().setCodPack(d.getCodPack());
                s.setDiaDeSpa(d);
                sd.guardarSesion(s);

                PreparedStatement psLink = con.prepareStatement(sqlLink);
                psLink.setInt(1, d.getCodPack());
                psLink.setInt(2, s.getCodSesion());
                psLink.executeUpdate();
                psLink.close();
            }

            ps.close();
            JOptionPane.showMessageDialog(null, "Día de spa guardado correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el día de spa. " + ex.getMessage());
        }
    }

    //Buscar DiaDeSpa por ID
    public DiaDeSpa buscarDiaDeSpa(int codPack) {
        String sql = "SELECT * FROM dia_de_spa WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();

            DiaDeSpa d = null;
            if (rs.next()) {
                d = new DiaDeSpa();
                d.setCodPack(rs.getInt("codPack"));
                d.setFechayhora(rs.getTimestamp("fecha_y_hora").toLocalDateTime());
                d.setPreferencias(rs.getString("preferencias"));

                miConexion conexionCliente = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                ClienteData cd = new ClienteData(conexionCliente);
                
                d.setCliente(cd.buscarCliente(rs.getInt("idCliente")));

                d.setMonto(rs.getDouble("monto"));
                d.setEstado(rs.getBoolean("estado"));

                // recuperar sesiones asociadas
                miConexion conexionSes = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                SesionData sd = new SesionData(conexionSes);
                ArrayList<Sesion> sesiones = sd.listarSesionesPorPack(codPack);
                d.setSesiones(new ArrayList<>(sesiones));
            }

            ps.close();
            return d;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el día de spa. " + ex.getMessage());
            return null;
        }
    }

    // Listar todos los DiaDeSpa 
    public ArrayList<DiaDeSpa> listarDiaDeSpas() {
        ArrayList<DiaDeSpa> dias = new ArrayList<>();
        String sql = "SELECT * FROM dia_de_spa";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DiaDeSpa d = new DiaDeSpa();
                d.setCodPack(rs.getInt("codPack"));
                d.setFechayhora(rs.getTimestamp("fecha_y_hora").toLocalDateTime());
                d.setPreferencias(rs.getString("preferencias"));

                miConexion conexionCliente = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                ClienteData cd = new ClienteData(conexionCliente);
                d.setCliente(cd.buscarCliente(rs.getInt("idCliente")));

                d.setMonto(rs.getDouble("monto"));
                d.setEstado(rs.getBoolean("estado"));

                miConexion conexionSes = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                SesionData sd = new SesionData(conexionSes);
                
                ArrayList liSesion = new ArrayList<>(sd.listarSesionesPorPack(d.getCodPack()));
                
                d.setSesiones(liSesion);

                dias.add(d);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar días de spa. " + ex.getMessage());
        }
        return dias;
    }

    // Eliminar DiaDeSpa
    public void eliminarDiaDeSpa(int codPack) {
        String sql = "DELETE FROM dia_de_spa WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Día de spa eliminado correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar día de spa. " + ex.getMessage());
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import entidades.Instalacion;
import entidades.Sesion;
import entidades.Tratamiento;
import entidades.Consultorio;
import entidades.Masajista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SesionData {

    private Connection con = null;

    public SesionData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    // Guardar sesion
    public void guardarSesion(Sesion s) {
        String sql = "INSERT INTO sesion (fecha_hora_inicio, fecha_hora_fin, idTratamiento, idConsultorio, idMasajista, codPack, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(s.getFechaHoraInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(s.getFechaHoraFinal()));
            ps.setInt(3, s.getTratamiento().getCodTratam());
            ps.setInt(4, s.getConsultorio().getNroConsultorio());
            ps.setInt(5, s.getMasajista().getMatricula());
            ps.setInt(6, s.getDiaDeSpa().getCodPack());
            ps.setBoolean(7, s.isEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                s.setCodSesion(rs.getInt(1));
            }

            // inserto instalaciones asociadas en tabla intermedia sesion_instalacion
            String sqlIns = "INSERT INTO sesion_instalacion (codSesion, idInstalacion) VALUES (?, ?)";
            PreparedStatement psIns = con.prepareStatement(sqlIns);
            for (Instalacion ins : s.getInsalaciones()) {
                psIns.setInt(1, s.getCodSesion());
                psIns.setInt(2, ins.getCodInstal());
                psIns.executeUpdate();
            }
            psIns.close();

            ps.close();
            JOptionPane.showMessageDialog(null, "Sesión guardada correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la sesión. " + ex.getMessage());
        }
    }

    // Buscar sesion por id
    public Sesion buscarSesion(int codSesion) {
        String sql = "SELECT * FROM sesion WHERE codSesion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ResultSet rs = ps.executeQuery();

            Sesion s = null;
            if (rs.next()) {
                s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                s.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
                s.setFechaHoraFinal(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());

                // reconstruir Tratamiento, Consultorio y Masajista usando sus Data
                miConexion conexion1 = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                TratamientoData td = new TratamientoData(conexion1);
                s.setTratamiento(td.buscarTratamiento(rs.getInt("idTratamiento")));

                miConexion conexion2 = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                ConsultorioData cd = new ConsultorioData(conexion2);
                s.setConsultorio(cd.buscarConsultorio(rs.getInt("idConsultorio")));

                miConexion conexion3 = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                MasajistaData md = new MasajistaData(conexion3);
                s.setMasajista(md.buscarMasajistaPorMatricula(rs.getInt("idMasajista")));

                s.getDiaDeSpa().setCodPack(rs.getInt("codPack"));
                s.setEstado(rs.getBoolean("estado"));

                // recuperar instalaciones asociadas en la tabla intermedia sesion_instalacion
                String sqlIns = "SELECT idInstalacion FROM sesion_instalacion WHERE codSesion = ?";
                PreparedStatement psIns = con.prepareStatement(sqlIns);
                psIns.setInt(1, codSesion);
                ResultSet rsIns = psIns.executeQuery();
                List<Instalacion> instalaciones = new ArrayList<>();
                miConexion conexionInst = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                InstalacionData idata = new InstalacionData(conexionInst);
                while (rsIns.next()) {
                    Instalacion insObj = idata.buscarInstalacion(rsIns.getInt("idInstalacion"));
                    instalaciones.add(insObj);
                }
                s.setInsalaciones(new ArrayList<>(instalaciones));
                rsIns.close();
                psIns.close();
            }

            ps.close();
            return s;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la sesión. " + ex.getMessage());
            return null;
        }
    }

    // Listar sesionrs por pack
    public ArrayList<Sesion> listarSesionesPorPack(int codPack) {
        ArrayList<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT * FROM sesion WHERE codPack = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sesion s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                s.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
                s.setFechaHoraFinal(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());

                miConexion conexion1 = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                TratamientoData td = new TratamientoData(conexion1);
                s.setTratamiento(td.buscarTratamiento(rs.getInt("idTratamiento")));

                miConexion conexion2 = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                ConsultorioData cd = new ConsultorioData(conexion2);
                s.setConsultorio(cd.buscarConsultorio(rs.getInt("idConsultorio")));

                miConexion conexion3 = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                MasajistaData md = new MasajistaData(conexion3);
                s.setMasajista(md.buscarMasajistaPorMatricula(rs.getInt("idMasajista")));

                s.getDiaDeSpa().setCodPack(rs.getInt("codPack"));
                s.setEstado(rs.getBoolean("estado"));

                // instalaciones asociadas
                String sqlIns = "SELECT idInstalacion FROM sesion_instalacion WHERE codSesion = ?";
                PreparedStatement psIns = con.prepareStatement(sqlIns);
                psIns.setInt(1, s.getCodSesion());
                ResultSet rsIns = psIns.executeQuery();
                List<Instalacion> instalaciones = new ArrayList<>();
                miConexion conexionInst = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                InstalacionData idata = new InstalacionData(conexionInst);
                while (rsIns.next()) {
                    Instalacion insObj = idata.buscarInstalacion(rsIns.getInt("idInstalacion"));
                    instalaciones.add(insObj);
                }
                s.setInsalaciones(new ArrayList<>(instalaciones));
                rsIns.close();
                psIns.close();

                sesiones.add(s);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar sesiones por pack. " + ex.getMessage());
        }
        return sesiones;
    }

    // Eliminar Sesion fisico
    public void eliminarSesion(int codSesion) {
        String sql = "DELETE FROM sesion WHERE codSesion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Sesión eliminada correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar sesión. " + ex.getMessage());
        }
    }
}

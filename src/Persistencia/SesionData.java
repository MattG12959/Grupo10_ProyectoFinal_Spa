package Persistencia;
/*
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
*/
import entidades.Instalacion;
import entidades.Sesion;
import entidades.Tratamiento;
import entidades.Consultorio;
import entidades.DiaDeSpa;
import entidades.Masajista;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SesionData {

    private Connection con = null;

    public SesionData(miConexion conexion) {
        this.con = conexion.buscarConexion();
    }

    // --------------------------------------------------
// Método: guardarSesion    ACLARACIÓN IMPORTANTE: SOLO FUNCIONA DENTRO DE GUARDAR DIA DE SPA
// Inserta una nueva sesión y sus instalaciones asociadas en la BD
// --------------------------------------------------
    public void guardarSesion(Sesion s) {
        // SQL para insertar una fila en la tabla 'sesion' (se usan placeholders ? para PreparedStatement)
        String sql = "INSERT INTO sesion (fecha_hora_inicio, fecha_hora_fin, idTratamiento, idConsultorio, idMasajista, codPack, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            // Preparamos la sentencia para poder setear los parámetros de forma segura
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Convertimos y seteamos la fecha/hora de inicio (LocalDateTime -> Timestamp)
            ps.setTimestamp(1, Timestamp.valueOf(s.getFechaHoraInicio()));

            // Convertimos y seteamos la fecha/hora de fin (LocalDateTime -> Timestamp)
            ps.setTimestamp(2, Timestamp.valueOf(s.getFechaHoraFinal()));

            // Seteamos el id del tratamiento asociado
            ps.setInt(3, s.getTratamiento().getCodTratam());

            // Seteamos el número de consultorio utilizado
            ps.setInt(4, s.getConsultorio().getNroConsultorio());

            // Seteamos la matrícula del masajista
            ps.setInt(5, s.getMasajista().getMatricula());

            // Seteamos el código de pack (dia de spa) asociado
            ps.setInt(6, s.getDiaDeSpa().getCodPack());

            // Seteamos el estado (boolean) de la sesión
            ps.setBoolean(7, s.isEstado());

            // Ejecutamos la inserción en la tabla sesion
            ps.executeUpdate();

            // Recuperamos la clave generada automáticamente (codSesion)
            ResultSet rs = ps.getGeneratedKeys(); // obtiene ResultSet con las keys generadas
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
            psIns.close(); // cerramos el PreparedStatement de SESION_instalacion

            ps.close(); // cerramos el PreparedStatement principal
            JOptionPane.showMessageDialog(null, "Sesión guardada correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la sesión. " + ex.getMessage());
        }
    }

    // --------------------------------------------------
// Método: buscarSesion
// Recupera una Sesion completa por su codSesion (incluye relaciones)
// --------------------------------------------------
    public Sesion buscarSesion(int codSesion) {
        String sql = "SELECT * FROM sesion WHERE codSesion = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ResultSet rs = ps.executeQuery();

            // variable que contendrá la sesión encontrada o null
            Sesion s = null;

            if (rs.next()) { // si el ResultSet tiene una fila (existe la sesión)
                s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                // Obtener fecha/hora de inicio desde la columna tipo TIMESTAMP y convertir a LocalDateTime
                s.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
                // Obtener fecha/hora de fin desde la columna tipo TIMESTAMP y convertir a LocalDateTime
                s.setFechaHoraFinal(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());

                // reconstruir Tratamiento, Consultorio y Masajista usando sus Data
                // Crea una nueva conexión temporal
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

    // --------------------------------------------------
// Método: listarSesionesPorPack
// Lista todas las sesiones que tengan el codPack indicado
// --------------------------------------------------
    public ArrayList<Sesion> listarSesionesPorPack(int codPack) {
        String sql = "SELECT * FROM sesion WHERE codPack = ?";
        ArrayList<Sesion> lista = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codPack);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sesion s = new Sesion();
                    s.setCodSesion(rs.getInt("codSesion"));

                    Timestamp tsInicio = rs.getTimestamp("fecha_hora_inicio");
                    Timestamp tsFin = rs.getTimestamp("fecha_hora_fin");
                    if (tsInicio != null) {
                        s.setFechaHoraInicio(tsInicio.toLocalDateTime());
                    }
                    if (tsFin != null) {
                        s.setFechaHoraFinal(tsFin.toLocalDateTime());
                    }

                    // --- crear un DiaDeSpa mínimo para evitar NullPointerException ---
                    DiaDeSpa dd = new DiaDeSpa();
                    int codPackFromRs = rs.getInt("codPack");
                    if (!rs.wasNull()) {
                        dd.setCodPack(codPackFromRs);
                    }
                    s.setDiaDeSpa(dd);
                    // -------------------------------------------------------------------

                    // Reconstruir tratamiento, consultorio, masajista (usar tus Data classes)
                    int idTrat = rs.getInt("idTratamiento");
                    if (!rs.wasNull()) {
                        TratamientoData td = new TratamientoData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                        s.setTratamiento(td.buscarTratamiento(idTrat));
                    }

                    int idCons = rs.getInt("idConsultorio");
                    if (!rs.wasNull()) {
                        ConsultorioData cd = new ConsultorioData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                        s.setConsultorio(cd.buscarConsultorio(idCons));
                    }

                    int idMas = rs.getInt("idMasajista");
                    if (!rs.wasNull()) {
                        MasajistaData md = new MasajistaData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                        s.setMasajista(md.buscarMasajistaPorMatricula(idMas));
                    }

                    s.setEstado(rs.getBoolean("estado"));

                    // recuperar instalaciones asociadas (si aplica)
                    String sqlIns = "SELECT idInstalacion FROM sesion_instalacion WHERE codSesion = ?";
                    try (PreparedStatement psIns = con.prepareStatement(sqlIns)) {
                        psIns.setInt(1, s.getCodSesion());
                        try (ResultSet rsIns = psIns.executeQuery()) {
                            List<Instalacion> instalaciones = new ArrayList<>();
                            miConexion conexionInst = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                            InstalacionData idata = new InstalacionData(conexionInst);
                            while (rsIns.next()) {
                                Instalacion insObj = idata.buscarInstalacion(rsIns.getInt("idInstalacion"));
                                instalaciones.add(insObj);
                            }
                            s.setInsalaciones(new ArrayList<>(instalaciones));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    lista.add(s);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar sesiones por pack. " + ex.getMessage());
        }

         return lista;
    }

    // --------------------------------------------------
    // Método: listarSesionesPorFecha
    // Lista todas las sesiones de una fecha específica
    // --------------------------------------------------
    public ArrayList<Sesion> listarSesionesPorFecha(LocalDateTime fecha) {
        ArrayList<Sesion> lista = new ArrayList<>();
        String sql = "SELECT * FROM sesion WHERE DATE(fecha_hora_inicio) = DATE(?) AND estado = 1 ORDER BY fecha_hora_inicio";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Sesion s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                
                Timestamp tsInicio = rs.getTimestamp("fecha_hora_inicio");
                Timestamp tsFin = rs.getTimestamp("fecha_hora_fin");
                if (tsInicio != null) {
                    s.setFechaHoraInicio(tsInicio.toLocalDateTime());
                }
                if (tsFin != null) {
                    s.setFechaHoraFinal(tsFin.toLocalDateTime());
                }
                
                DiaDeSpa dd = null;
                int codPackFromRs = rs.getInt("codPack");
                if (!rs.wasNull() && codPackFromRs > 0) {
                    // Cargar el DiaDeSpa completo con el cliente
                    DiaDeSpaData ddd = new DiaDeSpaData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    dd = ddd.buscarDiaDeSpa(codPackFromRs);
                }
                if (dd == null) {
                    dd = new DiaDeSpa();
                    if (!rs.wasNull()) {
                        dd.setCodPack(codPackFromRs);
                    }
                }
                s.setDiaDeSpa(dd);
                
                int idTrat = rs.getInt("idTratamiento");
                if (!rs.wasNull()) {
                    TratamientoData td = new TratamientoData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    s.setTratamiento(td.buscarTratamiento(idTrat));
                }
                
                int idCons = rs.getInt("idConsultorio");
                if (!rs.wasNull()) {
                    ConsultorioData cd = new ConsultorioData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    s.setConsultorio(cd.buscarConsultorio(idCons));
                }
                
                int idMas = rs.getInt("idMasajista");
                if (!rs.wasNull()) {
                    MasajistaData md = new MasajistaData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    s.setMasajista(md.buscarMasajistaPorMatricula(idMas));
                }
                
                s.setEstado(rs.getBoolean("estado"));
                
                String sqlIns = "SELECT idInstalacion FROM sesion_instalacion WHERE codSesion = ?";
                try (PreparedStatement psIns = con.prepareStatement(sqlIns)) {
                    psIns.setInt(1, s.getCodSesion());
                    try (ResultSet rsIns = psIns.executeQuery()) {
                        List<Instalacion> instalaciones = new ArrayList<>();
                        miConexion conexionInst = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                        InstalacionData idata = new InstalacionData(conexionInst);
                        while (rsIns.next()) {
                            Instalacion insObj = idata.buscarInstalacion(rsIns.getInt("idInstalacion"));
                            instalaciones.add(insObj);
                        }
                        s.setInsalaciones(new ArrayList<>(instalaciones));
                    }
                }
                
                lista.add(s);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar sesiones por fecha: " + ex.getMessage());
        }
        return lista;
    }

    // --------------------------------------------------
    // Método: listarTodasLasSesiones
    // Lista todas las sesiones realizadas hasta la fecha de consulta
    // --------------------------------------------------
    public ArrayList<Sesion> listarTodasLasSesiones() {
        ArrayList<Sesion> lista = new ArrayList<>();
        String sql = "SELECT * FROM sesion WHERE estado = 1 ORDER BY fecha_hora_inicio DESC";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Sesion s = new Sesion();
                s.setCodSesion(rs.getInt("codSesion"));
                
                Timestamp tsInicio = rs.getTimestamp("fecha_hora_inicio");
                Timestamp tsFin = rs.getTimestamp("fecha_hora_fin");
                if (tsInicio != null) {
                    s.setFechaHoraInicio(tsInicio.toLocalDateTime());
                }
                if (tsFin != null) {
                    s.setFechaHoraFinal(tsFin.toLocalDateTime());
                }
                
                DiaDeSpa dd = null;
                int codPackFromRs = rs.getInt("codPack");
                if (!rs.wasNull() && codPackFromRs > 0) {
                    // Cargar el DiaDeSpa completo con el cliente
                    DiaDeSpaData ddd = new DiaDeSpaData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    dd = ddd.buscarDiaDeSpa(codPackFromRs);
                }
                if (dd == null) {
                    dd = new DiaDeSpa();
                    if (!rs.wasNull()) {
                        dd.setCodPack(codPackFromRs);
                    }
                }
                s.setDiaDeSpa(dd);
                
                int idTrat = rs.getInt("idTratamiento");
                if (!rs.wasNull()) {
                    TratamientoData td = new TratamientoData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    s.setTratamiento(td.buscarTratamiento(idTrat));
                }
                
                int idCons = rs.getInt("idConsultorio");
                if (!rs.wasNull()) {
                    ConsultorioData cd = new ConsultorioData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    s.setConsultorio(cd.buscarConsultorio(idCons));
                }
                
                int idMas = rs.getInt("idMasajista");
                if (!rs.wasNull()) {
                    MasajistaData md = new MasajistaData(new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", ""));
                    s.setMasajista(md.buscarMasajistaPorMatricula(idMas));
                }
                
                s.setEstado(rs.getBoolean("estado"));
                
                String sqlIns = "SELECT idInstalacion FROM sesion_instalacion WHERE codSesion = ?";
                try (PreparedStatement psIns = con.prepareStatement(sqlIns)) {
                    psIns.setInt(1, s.getCodSesion());
                    try (ResultSet rsIns = psIns.executeQuery()) {
                        List<Instalacion> instalaciones = new ArrayList<>();
                        miConexion conexionInst = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
                        InstalacionData idata = new InstalacionData(conexionInst);
                        while (rsIns.next()) {
                            Instalacion insObj = idata.buscarInstalacion(rsIns.getInt("idInstalacion"));
                            instalaciones.add(insObj);
                        }
                        s.setInsalaciones(new ArrayList<>(instalaciones));
                    }
                }
                
                lista.add(s);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar todas las sesiones: " + ex.getMessage());
        }
        return lista;
    }
}


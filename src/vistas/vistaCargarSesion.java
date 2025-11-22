/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas;


import Persistencia.ClienteData;
import Persistencia.ConsultorioData;
import Persistencia.DiaDeSpaData;
import Persistencia.MasajistaData;
import Persistencia.TratamientoData;
import Persistencia.miConexion;
import control.ControlSesion;
import Persistencia.InstalacionData;
import com.toedter.calendar.JDateChooser;
import entidades.Cliente;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * @author Grupo10
 *
 * Altamirano Karina Gianfranco Antonacci Matías Bequis Marcos Ezequiel Dave
 * Natalia
 */
public class vistaCargarSesion extends javax.swing.JInternalFrame {

    /**
     * Creates new form vistaCargarSesion
     */
    private final JDesktopPane Escritorio;
    private miConexion connection;
    private ControlSesion controlSesion;
    private vistaCargarSesion vistaCargarSesion;
    private ClienteData clienteData;
    private TratamientoData tratamientoData;
    private ConsultorioData consultorioData;
    private InstalacionData instalacionesData;
    private MasajistaData masajistaData;
    private DiaDeSpaData diaDeSpaData;
    Cliente clienteSeleccionado = null;

    public vistaCargarSesion(JDesktopPane Escritorio) {
        initComponents();
        calendario();
        
        java.awt.Dimension tam = new java.awt.Dimension(81, 52); 
        labelInformacionDeLaInstalacion.setPreferredSize(tam);
        labelInformacionDeLaInstalacion.setMinimumSize(tam);
        labelInformacionDeLaInstalacion.setMaximumSize(tam);

        this.Escritorio = Escritorio;
        try {
            connection = new miConexion("jdbc:mariadb://localhost:3306/gp10_entre_dedos", "root", "");
            clienteData = new ClienteData(connection);
            tratamientoData = new TratamientoData(connection);
            consultorioData = new ConsultorioData(connection);
            instalacionesData = new InstalacionData(connection);
            masajistaData = new MasajistaData(connection);
            diaDeSpaData = new DiaDeSpaData(connection);

            //Este constructor se utiliza para conectar con la clase Control
            this.controlSesion = new ControlSesion(this,
                    clienteData,
                    tratamientoData,
                    consultorioData,
                    instalacionesData,
                    masajistaData,
                    diaDeSpaData
            );

            controlSesion.inicializar();

            // CONSULTORIOS
            cbConsultorio.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.consultorioSeleccionado();
                }
            });

            // INSTALACIN
            cbInstalacion.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.instalacionSeleccionada();
                }
            });

            btnAgregarInstalacion.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.agregarInstalacionASesion();
                }
            });

            btnEliminarInstalacion.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.eliminarInstalacionDeSesion();
                }
            });

            // CAMBIO DE HORA / MINUTOS
            spinnerHora.addChangeListener(new javax.swing.event.ChangeListener() {
                @Override
                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    controlSesion.actualizarFechaHoraSalidaYCosto();
                }
            });

            spinnerMinutos.addChangeListener(new javax.swing.event.ChangeListener() {
                @Override
                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    controlSesion.actualizarFechaHoraSalidaYCosto();
                }
            });

            // CAMBIO DE TRATAMIENTO
            cbTratamiento.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.actualizarFechaHoraSalidaYCosto();
                }
            });

            // GUARDAR SESION
            btnGuardarSesion.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.guardarSesion();
                }
            });

            // GUARDAR DIA DE SPA
            btnGuardarDiaDeSpa.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.guardarDiaDeSpa();
                }
            });
            
            // CARGAR CLIENTE
            btnCargarCliente.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controlSesion.abrirVistaCargarCliente(Escritorio);
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos (vistaCargarSesion): " + e.getMessage());
        }
        
        
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpFechas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableSesiones = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        labelNombreCliente = new javax.swing.JLabel();
        labelDniCliente = new javax.swing.JLabel();
        labelTelefonoCliente = new javax.swing.JLabel();
        labelEdadCliente = new javax.swing.JLabel();
        labelAfeccionesCliente = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAfecciones = new javax.swing.JTextArea();
        btnSeleccionarCliente = new javax.swing.JButton();
        btnCargarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelConsultorio = new javax.swing.JLabel();
        cbConsultorio = new javax.swing.JComboBox();
        labelMsajg = new javax.swing.JLabel();
        cbMasajista = new javax.swing.JComboBox();
        labelTratamiento = new javax.swing.JLabel();
        cbTratamiento = new javax.swing.JComboBox();
        labelInstalacion = new javax.swing.JLabel();
        cbInstalacion = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPreferencias = new javax.swing.JTextArea();
        btnAgregarInstalacion = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        labelInformacionDeLaInstalacion = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableInstalaciones = new javax.swing.JTable();
        labelInstalacion2 = new javax.swing.JLabel();
        labelInstalacion3 = new javax.swing.JLabel();
        labelInstalacion4 = new javax.swing.JLabel();
        getDateChooserFechaYHoraIngreso = new com.toedter.calendar.JDateChooser();
        spinnerHora = new javax.swing.JSpinner();
        spinnerMinutos = new javax.swing.JSpinner();
        labelFechaHoraFinal = new javax.swing.JLabel();
        btnEliminarInstalacion = new javax.swing.JButton();
        btnGuardarSesion = new javax.swing.JButton();
        btnGuardarDiaDeSpa = new javax.swing.JButton();
        labelInstalacion1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        labelCosto = new javax.swing.JLabel();

        setBackground(new java.awt.Color(115, 179, 147));
        setClosable(true);
        setTitle("Cargar Sesion");
        setPreferredSize(new java.awt.Dimension(1310, 610));

        jpFechas.setBackground(new java.awt.Color(115, 179, 147));
        jpFechas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpFechas.setPreferredSize(new java.awt.Dimension(1300, 564));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sesiones del dia de Spa");

        tableSesiones.setBackground(new java.awt.Color(155, 216, 185));
        tableSesiones.setForeground(new java.awt.Color(0, 0, 0));
        tableSesiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha hora inicio", "Fecha hora final", "Tratamiento", "Consultorio", "Masajista"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSesiones.setGridColor(new java.awt.Color(155, 216, 185));
        tableSesiones.setSelectionBackground(new java.awt.Color(0, 51, 51));
        tableSesiones.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(tableSesiones);

        jPanel1.setBackground(new java.awt.Color(115, 179, 147));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelNombreCliente.setBackground(new java.awt.Color(0, 0, 0));
        labelNombreCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNombreCliente.setText("Nombre: ");

        labelDniCliente.setBackground(new java.awt.Color(0, 0, 0));
        labelDniCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelDniCliente.setText("DNI:");

        labelTelefonoCliente.setBackground(new java.awt.Color(0, 0, 0));
        labelTelefonoCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelTelefonoCliente.setText("Telefono:");

        labelEdadCliente.setBackground(new java.awt.Color(0, 0, 0));
        labelEdadCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelEdadCliente.setText("Edad: ");

        labelAfeccionesCliente.setBackground(new java.awt.Color(0, 0, 0));
        labelAfeccionesCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelAfeccionesCliente.setText("Afecciones del cliente:");

        txtAfecciones.setBackground(new java.awt.Color(155, 216, 185));
        txtAfecciones.setColumns(20);
        txtAfecciones.setRows(5);
        jScrollPane1.setViewportView(txtAfecciones);

        btnSeleccionarCliente.setBackground(new java.awt.Color(155, 216, 185));
        btnSeleccionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icono-lupa.png"))); // NOI18N
        btnSeleccionarCliente.setText(" Seleccionar cliente");
        btnSeleccionarCliente.setPreferredSize(new java.awt.Dimension(155, 30));
        btnSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarClienteActionPerformed(evt);
            }
        });

        btnCargarCliente.setBackground(new java.awt.Color(155, 216, 185));
        btnCargarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icono-registrar-empleado.png"))); // NOI18N
        btnCargarCliente.setText("Cargar cliente");
        btnCargarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarClienteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Datos del Cliente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSeleccionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCargarCliente))
                    .addComponent(labelAfeccionesCliente)
                    .addComponent(labelEdadCliente)
                    .addComponent(labelTelefonoCliente)
                    .addComponent(labelDniCliente)
                    .addComponent(labelNombreCliente)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSeleccionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelNombreCliente)
                .addGap(18, 18, 18)
                .addComponent(labelDniCliente)
                .addGap(18, 18, 18)
                .addComponent(labelTelefonoCliente)
                .addGap(18, 18, 18)
                .addComponent(labelEdadCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelAfeccionesCliente)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(115, 179, 147));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelConsultorio.setBackground(new java.awt.Color(0, 0, 0));
        labelConsultorio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelConsultorio.setText("Consultorio:");

        cbConsultorio.setBackground(new java.awt.Color(155, 216, 185));
        cbConsultorio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelMsajg.setBackground(new java.awt.Color(0, 0, 0));
        labelMsajg.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelMsajg.setText("Masajista:");

        cbMasajista.setBackground(new java.awt.Color(155, 216, 185));
        cbMasajista.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelTratamiento.setBackground(new java.awt.Color(0, 0, 0));
        labelTratamiento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelTratamiento.setText("Tratamiento:");

        cbTratamiento.setBackground(new java.awt.Color(155, 216, 185));
        cbTratamiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelInstalacion.setBackground(new java.awt.Color(0, 0, 0));
        labelInstalacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelInstalacion.setText("Instalacion:");

        cbInstalacion.setBackground(new java.awt.Color(155, 216, 185));
        cbInstalacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbInstalacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInstalacionActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Ingrese las preferencias del cliente para el dia de spa:");

        txtPreferencias.setBackground(new java.awt.Color(155, 216, 185));
        txtPreferencias.setColumns(20);
        txtPreferencias.setRows(5);
        jScrollPane2.setViewportView(txtPreferencias);

        btnAgregarInstalacion.setBackground(new java.awt.Color(155, 216, 185));
        btnAgregarInstalacion.setForeground(new java.awt.Color(155, 216, 185));
        btnAgregarInstalacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icono-mas.png"))); // NOI18N
        btnAgregarInstalacion.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Detalle de la Sesión");

        labelInformacionDeLaInstalacion.setText("Info de la Instalacion:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelInformacionDeLaInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(labelInstalacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(labelTratamiento)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(labelMsajg)
                                        .addGap(24, 24, 24))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(labelConsultorio)
                                        .addGap(24, 24, 24)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbConsultorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbTratamiento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbMasajista, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelConsultorio)
                    .addComponent(cbConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMsajg)
                    .addComponent(cbMasajista, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTratamiento)
                    .addComponent(cbTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelInstalacion)
                        .addComponent(cbInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelInformacionDeLaInstalacion)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel3.setBackground(new java.awt.Color(115, 179, 147));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableInstalaciones.setBackground(new java.awt.Color(155, 216, 185));
        tableInstalaciones.setForeground(new java.awt.Color(0, 0, 0));
        tableInstalaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Instalacion", "Descripcion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableInstalaciones.setGridColor(new java.awt.Color(155, 216, 185));
        tableInstalaciones.setSelectionBackground(new java.awt.Color(0, 51, 51));
        tableInstalaciones.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(tableInstalaciones);

        labelInstalacion2.setBackground(new java.awt.Color(0, 0, 0));
        labelInstalacion2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelInstalacion2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstalacion2.setText("Fecha de ingreso al dia de spa:");
        labelInstalacion2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        labelInstalacion3.setBackground(new java.awt.Color(0, 0, 0));
        labelInstalacion3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelInstalacion3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstalacion3.setText("Hora:");
        labelInstalacion3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        labelInstalacion4.setBackground(new java.awt.Color(0, 0, 0));
        labelInstalacion4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelInstalacion4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstalacion4.setText("Minutos:");
        labelInstalacion4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        labelFechaHoraFinal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelFechaHoraFinal.setText("Fecha y hora de salida");

        btnEliminarInstalacion.setText("Eliminar instalacion de la sesion");

        btnGuardarSesion.setBackground(new java.awt.Color(155, 216, 185));
        btnGuardarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icono-nuevo-registro.png"))); // NOI18N
        btnGuardarSesion.setText(" Guardar Sesion");

        btnGuardarDiaDeSpa.setBackground(new java.awt.Color(155, 216, 185));
        btnGuardarDiaDeSpa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icono-nuevo-registro.png"))); // NOI18N
        btnGuardarDiaDeSpa.setText(" Guardar Dia de Spa");

        labelInstalacion1.setBackground(new java.awt.Color(0, 0, 0));
        labelInstalacion1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelInstalacion1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstalacion1.setText("Instalaciones cargadas:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Resumen");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFechaHoraFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(labelInstalacion2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(getDateChooserFechaYHoraIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(labelInstalacion3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(spinnerHora, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(labelInstalacion4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(spinnerMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEliminarInstalacion, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(btnGuardarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnGuardarDiaDeSpa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(labelInstalacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(labelInstalacion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarInstalacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelInstalacion3)
                                .addComponent(labelInstalacion2))
                            .addComponent(labelInstalacion4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(getDateChooserFechaYHoraIngreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelFechaHoraFinal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarDiaDeSpa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel4.setBackground(new java.awt.Color(115, 179, 147));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelCosto.setBackground(new java.awt.Color(0, 0, 0));
        labelCosto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelCosto.setText("Costo Spa Total: $");
        labelCosto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(labelCosto)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpFechasLayout = new javax.swing.GroupLayout(jpFechas);
        jpFechas.setLayout(jpFechasLayout);
        jpFechasLayout.setHorizontalGroup(
            jpFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFechasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jpFechasLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpFechasLayout.setVerticalGroup(
            jpFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFechasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpFechasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpFechasLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpFechas, javax.swing.GroupLayout.DEFAULT_SIZE, 1296, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpFechas, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarClienteActionPerformed
        // TODO add your handling code here:

        JInternalFrame abierto = buscarFrame(vistaSeleccionarCliente.class);
        if (abierto != null) {
            try {
                abierto.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
            }
            abierto.toFront();
            centrarFrame(abierto);
            return;
        }

        vistaSeleccionarCliente frm = new vistaSeleccionarCliente(connection, controlSesion);
        Escritorio.add(frm);
        frm.pack();
        centrarFrame(frm);
        frm.setVisible(true);
        try {
            frm.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
        }

        if (clienteSeleccionado != null) {
            controlSesion.mostrarDatosCliente(clienteSeleccionado);
        }
    }//GEN-LAST:event_btnSeleccionarClienteActionPerformed

    private void cbInstalacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInstalacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbInstalacionActionPerformed

    private void btnCargarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCargarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarInstalacion;
    private javax.swing.JButton btnCargarCliente;
    private javax.swing.JButton btnEliminarInstalacion;
    private javax.swing.JButton btnGuardarDiaDeSpa;
    private javax.swing.JButton btnGuardarSesion;
    private javax.swing.JButton btnSeleccionarCliente;
    private javax.swing.JComboBox cbConsultorio;
    private javax.swing.JComboBox cbInstalacion;
    private javax.swing.JComboBox cbMasajista;
    private javax.swing.JComboBox cbTratamiento;
    private com.toedter.calendar.JDateChooser getDateChooserFechaYHoraIngreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel jpFechas;
    private javax.swing.JLabel labelAfeccionesCliente;
    private javax.swing.JLabel labelConsultorio;
    private javax.swing.JLabel labelCosto;
    private javax.swing.JLabel labelDniCliente;
    private javax.swing.JLabel labelEdadCliente;
    private javax.swing.JLabel labelFechaHoraFinal;
    private javax.swing.JLabel labelInformacionDeLaInstalacion;
    private javax.swing.JLabel labelInstalacion;
    private javax.swing.JLabel labelInstalacion1;
    private javax.swing.JLabel labelInstalacion2;
    private javax.swing.JLabel labelInstalacion3;
    private javax.swing.JLabel labelInstalacion4;
    private javax.swing.JLabel labelMsajg;
    private javax.swing.JLabel labelNombreCliente;
    private javax.swing.JLabel labelTelefonoCliente;
    private javax.swing.JLabel labelTratamiento;
    private javax.swing.JSpinner spinnerHora;
    private javax.swing.JSpinner spinnerMinutos;
    private javax.swing.JTable tableInstalaciones;
    private javax.swing.JTable tableSesiones;
    private javax.swing.JTextArea txtAfecciones;
    private javax.swing.JTextArea txtPreferencias;
    // End of variables declaration//GEN-END:variables

    // --- Métodos ---
    
    // --- Setear el día al entrar a sesión y la fecha minima para la sesión --- 
    public void calendario() {
        getDateChooserFechaYHoraIngreso.setDate(new Date());
        
        Calendar calMin = Calendar.getInstance();
        int ano = LocalDate.now().getYear();
        int mes = (LocalDate.now().getMonthValue() - 1);
        int dia = LocalDate.now().getDayOfMonth();
        calMin.set(ano, mes, dia);
        Date fechaMin = calMin.getTime();
        
        getDateChooserFechaYHoraIngreso.setMinSelectableDate(fechaMin);
    }
    
    // Getters y Setters
    public miConexion getConnection() {
        return connection;
    }

    public void setConnection(miConexion connection) {
        this.connection = connection;
    }

    public ControlSesion getControlSesion() {
        return controlSesion;
    }

    public void setControlSesion(ControlSesion controlSesion) {
        this.controlSesion = controlSesion;
    }

    public vistaCargarSesion getVistaCargarSesion() {
        return vistaCargarSesion;
    }

    public void setVistaCargarSesion(vistaCargarSesion vistaCargarSesion) {
        this.vistaCargarSesion = vistaCargarSesion;
    }

    public ClienteData getClienteData() {
        return clienteData;
    }

    public void setClienteData(ClienteData clienteData) {
        this.clienteData = clienteData;
    }

    public TratamientoData getTratamientoData() {
        return tratamientoData;
    }

    public void setTratamientoData(TratamientoData tratamientoData) {
        this.tratamientoData = tratamientoData;
    }

    public ConsultorioData getConsultorioData() {
        return consultorioData;
    }

    public void setConsultorioData(ConsultorioData consultorioData) {
        this.consultorioData = consultorioData;
    }

    public InstalacionData getInstalacionesData() {
        return instalacionesData;
    }

    public void setInstalacionesData(InstalacionData instalacionesData) {
        this.instalacionesData = instalacionesData;
    }

    public MasajistaData getMasajistaData() {
        return masajistaData;
    }

    public void setMasajistaData(MasajistaData masajistaData) {
        this.masajistaData = masajistaData;
    }

    public JButton getBtnAgregarInstalacion() {
        return btnAgregarInstalacion;
    }

    public void setBtnAgregarInstalacion(JButton btnAgregarInstalacion) {
        this.btnAgregarInstalacion = btnAgregarInstalacion;
    }

    public JButton getBtnCargarCliente() {
        return btnCargarCliente;
    }

    public void setBtnCargarCliente(JButton btnCargarCliente) {
        this.btnCargarCliente = btnCargarCliente;
    }

    public JButton getBtnEliminarInstalacion() {
        return btnEliminarInstalacion;
    }

    public void setBtnEliminarInstalacion(JButton btnEliminarInstalacion) {
        this.btnEliminarInstalacion = btnEliminarInstalacion;
    }

    public JButton getBtnGuardarSesion() {
        return btnGuardarSesion;
    }

    public void setBtnGuardarSesion(JButton btnGuardarSesion) {
        this.btnGuardarSesion = btnGuardarSesion;
    }

    public JButton getBtnGuardarSesion1() {
        return btnGuardarDiaDeSpa;
    }

    public void setBtnGuardarSesion1(JButton btnGuardarSesion1) {
        this.btnGuardarDiaDeSpa = btnGuardarSesion1;
    }

    public JButton getBtnSeleccionarCliente() {
        return btnSeleccionarCliente;
    }

    public void setBtnSeleccionarCliente(JButton btnSeleccionarCliente) {
        this.btnSeleccionarCliente = btnSeleccionarCliente;
    }

    public JComboBox getCbConsultorio() {
        return cbConsultorio;
    }

    public void setCbConsultorio(JComboBox cbConsultorio) {
        this.cbConsultorio = cbConsultorio;
    }

    public JComboBox getCbInstalacion() {
        return cbInstalacion;
    }

    public void setCbInstalacion(JComboBox cbInstalacion) {
        this.cbInstalacion = cbInstalacion;
    }

    public JComboBox getCbMasajista() {
        return cbMasajista;
    }

    public void setCbMasajista(JComboBox cbMasajista) {
        this.cbMasajista = cbMasajista;
    }

    public JComboBox getCbTratamiento() {
        return cbTratamiento;
    }

    public void setCbTratamiento(JComboBox cbTratamiento) {
        this.cbTratamiento = cbTratamiento;
    }

    public JDateChooser getGetDateChooserFechaYHoraIngreso() {
        return getDateChooserFechaYHoraIngreso;
    }

    public void setGetDateChooserFechaYHoraIngreso(JDateChooser getDateChooserFechaYHoraIngreso) {
        this.getDateChooserFechaYHoraIngreso = getDateChooserFechaYHoraIngreso;
    }

    

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    public void setjScrollPane4(JScrollPane jScrollPane4) {
        this.jScrollPane4 = jScrollPane4;
    }

    public JPanel getJpFechas() {
        return jpFechas;
    }

    public void setJpFechas(JPanel jpFechas) {
        this.jpFechas = jpFechas;
    }

    public JLabel getLabelAfeccionesCliente() {
        return labelAfeccionesCliente;
    }

    public void setLabelAfeccionesCliente(JLabel labelAfeccionesCliente) {
        this.labelAfeccionesCliente = labelAfeccionesCliente;
    }

    public JLabel getLabelConsultorio() {
        return labelConsultorio;
    }

    public void setLabelConsultorio(JLabel labelConsultorio) {
        this.labelConsultorio = labelConsultorio;
    }

    public JLabel getLabelDniCliente() {
        return labelDniCliente;
    }

    public void setLabelDniCliente(JLabel labelDniCliente) {
        this.labelDniCliente = labelDniCliente;
    }

    public JLabel getLabelEdadCliente() {
        return labelEdadCliente;
    }

    public void setLabelEdadCliente(JLabel labelEdadCliente) {
        this.labelEdadCliente = labelEdadCliente;
    }

    public JLabel getLabelFechaHoraFinal() {
        return labelFechaHoraFinal;
    }

    public void setLabelFechaHoraFinal(JLabel labelFechaHoraFinal) {
        this.labelFechaHoraFinal = labelFechaHoraFinal;
    }

    public JLabel getLabelInformacionDeLaInstalacion() {
        return labelInformacionDeLaInstalacion;
    }

    public void setLabelInformacionDeLaInstalacion(JLabel labelInformacionDeLaInstalacion) {
        this.labelInformacionDeLaInstalacion = labelInformacionDeLaInstalacion;
    }

    public JLabel getLabelInstalacion() {
        return labelInstalacion;
    }

    public void setLabelInstalacion(JLabel labelInstalacion) {
        this.labelInstalacion = labelInstalacion;
    }

    public JLabel getLabelInstalacion1() {
        return labelInstalacion1;
    }

    public void setLabelInstalacion1(JLabel labelInstalacion1) {
        this.labelInstalacion1 = labelInstalacion1;
    }

    public JLabel getLabelInstalacion2() {
        return labelInstalacion2;
    }

    public void setLabelInstalacion2(JLabel labelInstalacion2) {
        this.labelInstalacion2 = labelInstalacion2;
    }

    public JLabel getLabelInstalacion3() {
        return labelInstalacion3;
    }

    public void setLabelInstalacion3(JLabel labelInstalacion3) {
        this.labelInstalacion3 = labelInstalacion3;
    }

    public JLabel getLabelInstalacion4() {
        return labelInstalacion4;
    }

    public void setLabelInstalacion4(JLabel labelInstalacion4) {
        this.labelInstalacion4 = labelInstalacion4;
    }

    public JLabel getLabelInstalacion5() {
        return labelCosto;
    }

    public void setLabelInstalacion5(JLabel labelInstalacion5) {
        this.labelCosto = labelInstalacion5;
    }

    public JLabel getLabelMsajg() {
        return labelMsajg;
    }

    public void setLabelMsajg(JLabel labelMsajg) {
        this.labelMsajg = labelMsajg;
    }

    public JLabel getLabelNombreCliente() {
        return labelNombreCliente;
    }

    public void setLabelNombreCliente(JLabel labelNombreCliente) {
        this.labelNombreCliente = labelNombreCliente;
    }

    public JLabel getLabelTelefonoCliente() {
        return labelTelefonoCliente;
    }

    public void setLabelTelefonoCliente(JLabel labelTelefonoCliente) {
        this.labelTelefonoCliente = labelTelefonoCliente;
    }

    public JLabel getLabelTratamiento() {
        return labelTratamiento;
    }

    public void setLabelTratamiento(JLabel labelTratamiento) {
        this.labelTratamiento = labelTratamiento;
    }

    public JSpinner getSpinnerHora() {
        return spinnerHora;
    }

    public void setSpinnerHora(JSpinner spinnerHora) {
        this.spinnerHora = spinnerHora;
    }

    public JSpinner getSpinnerMinutos() {
        return spinnerMinutos;
    }

    public void setSpinnerMinutos(JSpinner spinnerMinutos) {
        this.spinnerMinutos = spinnerMinutos;
    }

    public JTable getTableInstalaciones() {
        return tableInstalaciones;
    }

    public void setTableInstalaciones(JTable tableInstalaciones) {
        this.tableInstalaciones = tableInstalaciones;
    }

    public JTable getTableSesiones() {
        return tableSesiones;
    }

    public void setTableSesiones(JTable tableSesiones) {
        this.tableSesiones = tableSesiones;
    }

    public JTextArea getTxtAfecciones() {
        return txtAfecciones;
    }

    public void setTxtAfecciones(JTextArea txtAfecciones) {
        this.txtAfecciones = txtAfecciones;
    }

    public JTextArea getTxtAfecciones1() {
        return txtPreferencias;
    }

    public void setTxtAfecciones1(JTextArea txtAfecciones1) {
        this.txtPreferencias = txtAfecciones1;
    }

    public JInternalFrame buscarFrame(Class clase) {
        for (JInternalFrame frame : Escritorio.getAllFrames()) {
            if (clase.isInstance(frame)) {
                return frame;
            }
        }
        return null;
    }

    public void centrarFrame(JInternalFrame frame) {
        Dimension desktopSize = Escritorio.getSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
        );
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public DiaDeSpaData getDiaDeSpaData() {
        return diaDeSpaData;
    }

    public void setDiaDeSpaData(DiaDeSpaData diaDeSpaData) {
        this.diaDeSpaData = diaDeSpaData;
    }

    public JButton getBtnGuardarDiaDeSpa() {
        return btnGuardarDiaDeSpa;
    }

    public void setBtnGuardarDiaDeSpa(JButton btnGuardarDiaDeSpa) {
        this.btnGuardarDiaDeSpa = btnGuardarDiaDeSpa;
    }

    public JLabel getLabelCosto() {
        return labelCosto;
    }

    public void setLabelCosto(JLabel labelCosto) {
        this.labelCosto = labelCosto;
    }

    public JTextArea getTxtPreferencias() {
        return txtPreferencias;
    }

    public void setTxtPreferencias(JTextArea txtPreferencias) {
        this.txtPreferencias = txtPreferencias;
    }

}

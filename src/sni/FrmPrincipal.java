/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sni;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author vladi
 */
public class FrmPrincipal extends javax.swing.JFrame {
    Tablas tabla = new Tablas();
    QPersonas qPersonas = new QPersonas();
    QMiembroElectoral qMiembroElectoral = new QMiembroElectoral();
    QAfiliado qAfiliados = new QAfiliado();
    QCapacitaciones qCapacitaciones = new QCapacitaciones();
    URL pathReporteBuscar=this.getClass().getResource("/reportes/general.jasper");
    Map parametroReporteBuscar = null;
    String paramReporteBuscar="";

    /**
     * Creates new form Principal
     */
    public FrmPrincipal() {
        initComponents();
        iniciarBuscar();
        if(!Usuario.isSupersu()){
            initUsuario();
        }else{
            initSuperUsuario();
        }
    }
    
    public void initUsuario(){
        for(int i=1;i<10;i++){
            tbpPrincipal.setEnabledAt(i, false);
        }
        tbpPrincipal.setSelectedIndex(0);
    }
    
    public void initSuperUsuario(){
        setDescripcionGnral();
        iniciarEM();
        iniciarAfiliados();
        iniciarCapacitaciones();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Panel Buscar">
    public void iniciarBuscar(){
        actualizarTblBuscar();
    }
    
    public void actualizarTblBuscar(){
        tblDatosBuscar.setModel(llenarTblBuscar());
        //tblDatosBuscar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //tabla.resizeColumnWidth(tblDatosBuscar);
    }
    
    public DefaultTableModel llenarTblBuscar(){
        List<Personas> personas = new ArrayList<Personas>();
        DefaultTableModel dtmModelo = new DefaultTableModel();
        
        dtmModelo.addColumn("DUI");
        dtmModelo.addColumn("Nombres");
        dtmModelo.addColumn("Apellidos");
        dtmModelo.addColumn("Teléfono");
        dtmModelo.addColumn("Dirección");
        dtmModelo.addColumn("Correo");
        dtmModelo.addColumn("Foto");
        dtmModelo.addColumn("Nivel Académico");
        dtmModelo.addColumn("Facebook");
        
        personas = qPersonas.mostrarPersonas();
        
        int i=0;
        for(Personas per:personas){
            dtmModelo.addRow(new Object[]{});
            dtmModelo.setValueAt(per.getDui(),i,0);
            dtmModelo.setValueAt(per.getNombre(),i,1);
            dtmModelo.setValueAt(per.getApellido(),i,2);
            dtmModelo.setValueAt(per.getTelefono(),i,3);
            dtmModelo.setValueAt(per.getDireccion(),i,4);
            dtmModelo.setValueAt(per.getCorreo(),i,5);
            dtmModelo.setValueAt(per.getFoto(),i,6);
            dtmModelo.setValueAt(per.getNivelAcademico(),i,7);
            dtmModelo.setValueAt(per.getFacebook(),i,8);
            i++;
        }
        return dtmModelo;
    }
    
    public DefaultTableModel llenarTblBuscar(String tipoFiltro, String filtro){
        List<Personas> personas = new ArrayList<Personas>();
        DefaultTableModel dtmModelo = new DefaultTableModel();
        
        dtmModelo.addColumn("DUI");
        dtmModelo.addColumn("Nombres");
        dtmModelo.addColumn("Apellidos");
        dtmModelo.addColumn("Teléfono");
        dtmModelo.addColumn("Dirección");
        dtmModelo.addColumn("Correo");
        dtmModelo.addColumn("Foto");
        dtmModelo.addColumn("Nivel Académico");
        dtmModelo.addColumn("Facebook");
        
        personas = qPersonas.mostrarPersonasPor(tipoFiltro, filtro);
        int i=0;
        for(Personas per:personas){
            dtmModelo.addRow(new Object[]{});
            dtmModelo.setValueAt(per.getDui(),i,0);
            dtmModelo.setValueAt(per.getNombre(),i,1);
            dtmModelo.setValueAt(per.getApellido(),i,2);
            dtmModelo.setValueAt(per.getTelefono(),i,3);
            dtmModelo.setValueAt(per.getDireccion(),i,4);
            dtmModelo.setValueAt(per.getCorreo(),i,5);
            dtmModelo.setValueAt(per.getFoto(),i,6);
            dtmModelo.setValueAt(per.getNivelAcademico(),i,7);
            dtmModelo.setValueAt(per.getFacebook(),i,8);
            i++;
        }
        return dtmModelo;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Miembro Electoral">
    
    public void iniciarEM(){
        actualizarTblEM();
        lblFotoEM.setIcon(new ImageIcon(SNI.class.getResource("/images/user.png")));
    }
    
    public void actualizarTblEM(){
        tblEM.setModel(llenarTblEM());
        tabla.resizeColumnWidth(tblEM);
    }
    
    public DefaultTableModel llenarTblEM(){
        List<MiembroElectoral> miembros = new ArrayList<MiembroElectoral>();
        DefaultTableModel dtmModelo = new DefaultTableModel();
        
        dtmModelo.addColumn("DUI");
        dtmModelo.addColumn("Nombres");
        dtmModelo.addColumn("Apellidos");
        dtmModelo.addColumn("JRV");
        dtmModelo.addColumn("Cargo");
        dtmModelo.addColumn("Centro de votación");
        dtmModelo.addColumn("Lugar de Capacitacion");
        dtmModelo.addColumn("Fecha de capacitación");
        
        miembros = qMiembroElectoral.mostrarMiembros();
        
        int i=0;
        for(MiembroElectoral me:miembros){
            dtmModelo.addRow(new Object[]{});
            dtmModelo.setValueAt(me.getDui(),i,0);
            dtmModelo.setValueAt(me.getNombre(),i,1);
            dtmModelo.setValueAt(me.getApellido(),i,2);
            dtmModelo.setValueAt(me.getJrv(),i,3);
            dtmModelo.setValueAt(me.getCargo(),i,4);
            dtmModelo.setValueAt(me.getCentroVotacion(),i,5);
            dtmModelo.setValueAt(me.getLugarCapacitacion(),i,6);
            dtmModelo.setValueAt(me.getFechaCapacitacion(),i,7);
            i++;
        }
        
        return dtmModelo;
    }
    
    public void getMiembroById(String dui){
        MiembroElectoral miembro = new MiembroElectoral();
                
        miembro = qMiembroElectoral.mostrarMiembro(dui);
        
        txfDUIEM.setText(dui);
        txfNombreEM.setText(miembro.getNombre() + " "+ miembro.getApellido());
        txfTelefonoEM.setText(miembro.getTelefono());
        txfDirEM.setText(miembro.getDireccion());
        txfCorreoEM.setText(miembro.getTelefono());
        txfJVEM.setText(String.valueOf(miembro.getJrv()));
        txfCargoEM.setText(miembro.getCargo());
        txfCVEM.setText(miembro.getCentroVotacion());
    }
    
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Afiliados">
    public void iniciarAfiliados(){
        actualizarTblAfiliados();
        lblFotoAfiliados.setIcon(new ImageIcon(SNI.class.getResource("/images/user.png")));
    }
    
    public void actualizarTblAfiliados(){
        tblAfiliados.setModel(llenarTblAfiliados());
        tabla.resizeColumnWidth(tblAfiliados);
    }
    
    public DefaultTableModel llenarTblAfiliados(){
        List<Afiliado> afiliados = new ArrayList<Afiliado>();
        DefaultTableModel dtmModelo = new DefaultTableModel();
        
        dtmModelo.addColumn("DUI");
        dtmModelo.addColumn("Nombre");
        dtmModelo.addColumn("Dirección");
        dtmModelo.addColumn("Teléfono");
        dtmModelo.addColumn("Total donado");
        
        afiliados = qAfiliados.mostrarAfiliados();
        
        int i=0;
        for(Afiliado af:afiliados){
            dtmModelo.addRow(new Object[]{});
            dtmModelo.setValueAt(af.getDui(),i,0);
            dtmModelo.setValueAt(af.getNombre() + " " + af.getApellido() ,i,1);
            dtmModelo.setValueAt(af.getDireccion(),i,2);
            dtmModelo.setValueAt(af.getTelefono(),i,3);
            dtmModelo.setValueAt(af.getTotalDonaciones().toString(),i,4);
            i++;
        }
        
        return dtmModelo;
    }
    
    public void getAfiliadoById(int dui){
        Afiliado afiliado = new Afiliado();
                
        afiliado = qAfiliados.mostrarAfiliados(dui);
        
        txfDUIAfiliados.setText(String.valueOf(dui));
        txfNombreAfiliados.setText(afiliado.getNombre() + " "+ afiliado.getApellido());
        txfTelefonoAfiliados.setText(afiliado.getTelefono());
        txfDirAfiliados.setText(afiliado.getDireccion());
        txfCorreoAfiliados.setText(afiliado.getTelefono());
        txfFacebookAfiliados.setText(afiliado.getFacebook());
        txfDonacionesAfiliados.setText(afiliado.getTotalDonaciones().toString());
        
    }
    
    
    //</editor-fold>
    
    // <editor-fold desc="Capacitaciones">
    public void iniciarCapacitaciones(){
        actualizarTblCapacitaciones();
        lblFotoCapacitaciones.setIcon(new ImageIcon(SNI.class.getResource("/images/user.png")));
    }
    
    public void actualizarTblCapacitaciones(){
        tblCapacitaciones.setModel(llenarTblCapacitaciones());
        tabla.resizeColumnWidth(tblCapacitaciones);
    }
    
    public DefaultTableModel llenarTblCapacitaciones(){
        List<Capacitaciones> capacitaciones = new ArrayList<Capacitaciones>();
        DefaultTableModel dtmModelo = new DefaultTableModel();
        
        dtmModelo.addColumn("DUI");
        dtmModelo.addColumn("Nombre");
        dtmModelo.addColumn("Telefono");
        dtmModelo.addColumn("Dirección");
        dtmModelo.addColumn("Correo");
        dtmModelo.addColumn("Asistencias");
        
        capacitaciones = qCapacitaciones.mostrarCapacitaciones();
        
        int i=0;
        for(Capacitaciones cap:capacitaciones){
            dtmModelo.addRow(new Object[]{});
            dtmModelo.setValueAt(cap.getDui(),i,0);
            dtmModelo.setValueAt(cap.getNombre()+" "+cap.getApellido(),i,1);
            dtmModelo.setValueAt(cap.getTelefono(),i,2);
            dtmModelo.setValueAt(cap.getDireccion(),i,3);
            dtmModelo.setValueAt(cap.getCorreo(),i,4);
            dtmModelo.setValueAt(cap.getAsistencias().toString(),i,5);
            i++;
        }
        
        return dtmModelo;
    }
    /*
    public void getCapacitacionesById(String dui){
        MiembroElectoral miembro = new MiembroElectoral();
                
        miembro = qMiembroElectoral.mostrarMiembro(dui);
        
        txfDUIEM.setText(dui);
        txfNombreEM.setText(miembro.getNombre() + " "+ miembro.getApellido());
        txfTelefonoEM.setText(miembro.getTelefono());
        txfDirEM.setText(miembro.getDireccion());
        txfCorreoEM.setText(miembro.getTelefono());
        txfJVEM.setText(String.valueOf(miembro.getJrv()));
        txfCargoEM.setText(miembro.getCargo());
        txfCVEM.setText(miembro.getCentroVotacion());
    }
    */
    //</editor-fold>
    
    public String evaluarNulo(String dato){
        if(dato==null){
            return "n/a";
        }else{
            return dato;
        }
    }
    
    public void setDescripcionGnral(){
        lblDescripción.setText("Administración general de datos, permite crear o modificar registros base.");
    }
    
    public void relog(){
        Usuario.setLoggeado(false);
        Usuario.setSupersu(false);
        Usuario.setUsuario("");
        FrmLogin lg = new FrmLogin();
        lg.setVisible(true);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbpPrincipal = new javax.swing.JTabbedPane();
        pnlBuscar = new javax.swing.JPanel();
        pnlFiltrarDatosBuscar = new javax.swing.JPanel();
        txfFiltrarBuscar = new javax.swing.JTextField();
        cmbFiltrarDatosBuscar = new javax.swing.JComboBox<>();
        btnAplicarFiltroBuscar = new javax.swing.JButton();
        scpnlDatosBuscar = new javax.swing.JScrollPane();
        tblDatosBuscar = new javax.swing.JTable();
        pnlControlBuscar = new javax.swing.JPanel();
        btnGuardarBuscar = new javax.swing.JButton();
        btnImprimirBuscar = new javax.swing.JButton();
        btnSalirBuscar = new javax.swing.JButton();
        lblFiltrosAplicadosBuscar = new javax.swing.JLabel();
        lblFiltrosBuscar = new javax.swing.JLabel();
        pnlEquipoTrabajo = new javax.swing.JPanel();
        pnlBuscarTW = new javax.swing.JPanel();
        lblFiltrarEM = new javax.swing.JLabel();
        txfFiltrarEM = new javax.swing.JTextField();
        btnBuscarEM = new javax.swing.JButton();
        cmbFiltrarEM = new javax.swing.JComboBox<>();
        pnlFotoEM = new javax.swing.JPanel();
        lblFotoEM = new javax.swing.JLabel();
        pnlDatosEM = new javax.swing.JPanel();
        lblDUIEM = new javax.swing.JLabel();
        txfDUIEM = new javax.swing.JTextField();
        lblNombreEM = new javax.swing.JLabel();
        txfNombreEM = new javax.swing.JTextField();
        lblTelefonoEM = new javax.swing.JLabel();
        txfTelefonoEM = new javax.swing.JTextField();
        lblDirEM = new javax.swing.JLabel();
        txfDirEM = new javax.swing.JTextField();
        lblCorreoEM = new javax.swing.JLabel();
        txfCorreoEM = new javax.swing.JTextField();
        lblJRVEM = new javax.swing.JLabel();
        txfJVEM = new javax.swing.JTextField();
        lblCVEM = new javax.swing.JLabel();
        txfCVEM = new javax.swing.JTextField();
        btnInfoEM = new javax.swing.JButton();
        txfCargoEM = new javax.swing.JTextField();
        lblCargoEM = new javax.swing.JLabel();
        scpnlEM = new javax.swing.JScrollPane();
        tblEM = new javax.swing.JTable();
        btnAgregarEM = new javax.swing.JButton();
        btnEliminarEM = new javax.swing.JButton();
        btnGuardarEM = new javax.swing.JButton();
        btnImprimirEM = new javax.swing.JButton();
        pnlAfiliados = new javax.swing.JPanel();
        pnlBuscarAfiliado = new javax.swing.JPanel();
        lblFiltrarAfiliados = new javax.swing.JLabel();
        txfFiltrarAfiliados = new javax.swing.JTextField();
        cmbFiltrarAfiliados = new javax.swing.JComboBox<>();
        btnBuscarAfiliados = new javax.swing.JButton();
        pnlFotoAfiliados = new javax.swing.JPanel();
        lblFotoAfiliados = new javax.swing.JLabel();
        pnlDatosAfiliados = new javax.swing.JPanel();
        lblDUIAfiliados = new javax.swing.JLabel();
        txfDUIAfiliados = new javax.swing.JTextField();
        lblNombreAfiliados = new javax.swing.JLabel();
        txfNombreAfiliados = new javax.swing.JTextField();
        lblTelefonoAfiliados = new javax.swing.JLabel();
        txfTelefonoAfiliados = new javax.swing.JTextField();
        lblDirAfiliados = new javax.swing.JLabel();
        txfDirAfiliados = new javax.swing.JTextField();
        lblCorreoAfiliados = new javax.swing.JLabel();
        txfCorreoAfiliados = new javax.swing.JTextField();
        lblFacebookAfiliados = new javax.swing.JLabel();
        txfFacebookAfiliados = new javax.swing.JTextField();
        lblDonacionesAfiliados = new javax.swing.JLabel();
        txfDonacionesAfiliados = new javax.swing.JTextField();
        btnHistorialAfiliados = new javax.swing.JButton();
        btnFacturaAfiliados = new javax.swing.JButton();
        scpnlAfiliados = new javax.swing.JScrollPane();
        tblAfiliados = new javax.swing.JTable();
        btnAgregarAfiliados = new javax.swing.JButton();
        btnEliminarAfiliados = new javax.swing.JButton();
        btnGuardarAfiliados = new javax.swing.JButton();
        btnImprimirAfiliados = new javax.swing.JButton();
        pnlCapacitaciones = new javax.swing.JPanel();
        pnlBuscarCapacitaciones = new javax.swing.JPanel();
        lblFiltrarCapacitaciones = new javax.swing.JLabel();
        txfFiltrarCapacitaciones = new javax.swing.JTextField();
        btnBuscarCapacitaciones = new javax.swing.JButton();
        cmbFiltrarCapacitaciones = new javax.swing.JComboBox<>();
        pnlFotoCapacitaciones = new javax.swing.JPanel();
        lblFotoCapacitaciones = new javax.swing.JLabel();
        pnlDatosCapacitaciones = new javax.swing.JPanel();
        lblDUIEM1 = new javax.swing.JLabel();
        txfDUIEM1 = new javax.swing.JTextField();
        lblNombreEM1 = new javax.swing.JLabel();
        txfNombreEM1 = new javax.swing.JTextField();
        lblTelefonoEM1 = new javax.swing.JLabel();
        txfTelefonoEM1 = new javax.swing.JTextField();
        lblDirEM1 = new javax.swing.JLabel();
        txfDirEM1 = new javax.swing.JTextField();
        lblCorreoEM1 = new javax.swing.JLabel();
        txfCorreoEM1 = new javax.swing.JTextField();
        lblJRVEM1 = new javax.swing.JLabel();
        txfJVEM1 = new javax.swing.JTextField();
        lblCVEM1 = new javax.swing.JLabel();
        txfCVEM2 = new javax.swing.JTextField();
        btnInfoEM1 = new javax.swing.JButton();
        txfCargoEM1 = new javax.swing.JTextField();
        lblCargoEM1 = new javax.swing.JLabel();
        scpnlCapacitaciones = new javax.swing.JScrollPane();
        tblCapacitaciones = new javax.swing.JTable();
        btnAgregarEM1 = new javax.swing.JButton();
        btnEliminarEM2 = new javax.swing.JButton();
        btnGuardarEM1 = new javax.swing.JButton();
        btnImprimirEM1 = new javax.swing.JButton();
        pnlComisiones = new javax.swing.JPanel();
        pnlBuscarTW2 = new javax.swing.JPanel();
        lblFiltrarEM2 = new javax.swing.JLabel();
        txfFiltrarEM2 = new javax.swing.JTextField();
        btnBuscarEM2 = new javax.swing.JButton();
        cmbFiltrarEM2 = new javax.swing.JComboBox<>();
        pnlFotoEM2 = new javax.swing.JPanel();
        lblFotoEM3 = new javax.swing.JLabel();
        pnlDatosEM2 = new javax.swing.JPanel();
        lblDUIEM2 = new javax.swing.JLabel();
        txfDUIEM2 = new javax.swing.JTextField();
        lblNombreEM2 = new javax.swing.JLabel();
        txfNombreEM2 = new javax.swing.JTextField();
        lblTelefonoEM2 = new javax.swing.JLabel();
        txfTelefonoEM2 = new javax.swing.JTextField();
        lblDirEM2 = new javax.swing.JLabel();
        txfDirEM2 = new javax.swing.JTextField();
        lblCorreoEM2 = new javax.swing.JLabel();
        txfCorreoEM2 = new javax.swing.JTextField();
        lblJRVEM2 = new javax.swing.JLabel();
        txfJVEM2 = new javax.swing.JTextField();
        lblCVEM2 = new javax.swing.JLabel();
        txfCVEM3 = new javax.swing.JTextField();
        btnInfoEM2 = new javax.swing.JButton();
        txfCargoEM2 = new javax.swing.JTextField();
        lblCargoEM2 = new javax.swing.JLabel();
        scpnlEM2 = new javax.swing.JScrollPane();
        tblEM2 = new javax.swing.JTable();
        btnAgregarEM2 = new javax.swing.JButton();
        btnEliminarEM3 = new javax.swing.JButton();
        btnGuardarEM2 = new javax.swing.JButton();
        btnImprimirEM2 = new javax.swing.JButton();
        pnlCuadroElectoral = new javax.swing.JPanel();
        pnlVotaciones = new javax.swing.JPanel();
        pnlVisitas = new javax.swing.JPanel();
        pnlBuscarTW3 = new javax.swing.JPanel();
        lblFiltrarEM3 = new javax.swing.JLabel();
        txfFiltrarEM3 = new javax.swing.JTextField();
        btnBuscarEM3 = new javax.swing.JButton();
        cmbFiltrarEM3 = new javax.swing.JComboBox<>();
        pnlFotoEM3 = new javax.swing.JPanel();
        lblFotoEM4 = new javax.swing.JLabel();
        pnlDatosEM3 = new javax.swing.JPanel();
        lblDUIEM3 = new javax.swing.JLabel();
        txfDUIEM3 = new javax.swing.JTextField();
        lblNombreEM3 = new javax.swing.JLabel();
        txfNombreEM3 = new javax.swing.JTextField();
        lblTelefonoEM3 = new javax.swing.JLabel();
        txfTelefonoEM3 = new javax.swing.JTextField();
        lblDirEM3 = new javax.swing.JLabel();
        txfDirEM3 = new javax.swing.JTextField();
        lblCorreoEM3 = new javax.swing.JLabel();
        txfCorreoEM3 = new javax.swing.JTextField();
        lblJRVEM3 = new javax.swing.JLabel();
        txfJVEM3 = new javax.swing.JTextField();
        lblCVEM3 = new javax.swing.JLabel();
        txfCVEM4 = new javax.swing.JTextField();
        btnInfoEM3 = new javax.swing.JButton();
        txfCargoEM3 = new javax.swing.JTextField();
        lblCargoEM3 = new javax.swing.JLabel();
        scpnlEM3 = new javax.swing.JScrollPane();
        tblEM3 = new javax.swing.JTable();
        btnAgregarEM3 = new javax.swing.JButton();
        btnEliminarEM4 = new javax.swing.JButton();
        btnGuardarEM3 = new javax.swing.JButton();
        btnImprimirEM3 = new javax.swing.JButton();
        pnlFacturacion = new javax.swing.JPanel();
        pnlFiltrarDatosFacturacion = new javax.swing.JPanel();
        txfFiltrarFacturacion = new javax.swing.JTextField();
        cmbFiltrarFacturacion = new javax.swing.JComboBox<>();
        btnAplicarFiltroFacturacion = new javax.swing.JButton();
        btnLimpiarFiltroFacturacion = new javax.swing.JButton();
        scpnlDatosFacturacion = new javax.swing.JScrollPane();
        tblDatosFacturacion = new javax.swing.JTable();
        pnlControlFacturacion = new javax.swing.JPanel();
        btnGuardarFacturacion = new javax.swing.JButton();
        btnImprimirFacturacion = new javax.swing.JButton();
        btnSalirFacturacion = new javax.swing.JButton();
        lblFiltrosAplicadosFacturacion = new javax.swing.JLabel();
        lblFiltrosFacturacion = new javax.swing.JLabel();
        btnNuevaFactura = new javax.swing.JButton();
        pnlControl = new javax.swing.JPanel();
        pnlAdmin = new javax.swing.JPanel();
        btnCargosME = new javax.swing.JButton();
        btnCargoCom = new javax.swing.JButton();
        btnCentroVotacion = new javax.swing.JButton();
        btnComisiones = new javax.swing.JButton();
        btnDepartamentos = new javax.swing.JButton();
        btnJRV = new javax.swing.JButton();
        btnMunicipios = new javax.swing.JButton();
        btnNivelesAcad = new javax.swing.JButton();
        btnPartidos = new javax.swing.JButton();
        pnlDescripcion = new javax.swing.JPanel();
        lblDescripción = new javax.swing.JLabel();
        jmbMenuBar = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        mnuArchivoSalir = new javax.swing.JMenuItem();
        mnuEditar = new javax.swing.JMenu();
        mnuFacturar = new javax.swing.JMenu();
        mnuUsuario = new javax.swing.JMenu();
        mnuVentana = new javax.swing.JMenu();
        mnuAyuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbpPrincipal.setPreferredSize(new java.awt.Dimension(720, 480));

        pnlFiltrarDatosBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar datos"));

        cmbFiltrarDatosBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DUI", "Nombre", "Apellido", "Teléfono", "Dirección", "Correo", "Nivel Académico" }));

        btnAplicarFiltroBuscar.setText("Aplicar filtro");
        btnAplicarFiltroBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarFiltroBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFiltrarDatosBuscarLayout = new javax.swing.GroupLayout(pnlFiltrarDatosBuscar);
        pnlFiltrarDatosBuscar.setLayout(pnlFiltrarDatosBuscarLayout);
        pnlFiltrarDatosBuscarLayout.setHorizontalGroup(
            pnlFiltrarDatosBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarDatosBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfFiltrarBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbFiltrarDatosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAplicarFiltroBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFiltrarDatosBuscarLayout.setVerticalGroup(
            pnlFiltrarDatosBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiltrarDatosBuscarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFiltrarDatosBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfFiltrarBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltrarDatosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAplicarFiltroBuscar))
                .addContainerGap())
        );

        scpnlDatosBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        tblDatosBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scpnlDatosBuscar.setViewportView(tblDatosBuscar);

        pnlControlBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));

        btnGuardarBuscar.setText("Guardar");

        btnImprimirBuscar.setText("Imprimir");
        btnImprimirBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirBuscarActionPerformed(evt);
            }
        });

        btnSalirBuscar.setText("Salir");
        btnSalirBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirBuscarActionPerformed(evt);
            }
        });

        lblFiltrosAplicadosBuscar.setText("Filtros aplicados:");

        lblFiltrosBuscar.setText("Ninguno");

        javax.swing.GroupLayout pnlControlBuscarLayout = new javax.swing.GroupLayout(pnlControlBuscar);
        pnlControlBuscar.setLayout(pnlControlBuscarLayout);
        pnlControlBuscarLayout.setHorizontalGroup(
            pnlControlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrosAplicadosBuscar)
                .addGap(18, 18, 18)
                .addComponent(lblFiltrosBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimirBuscar)
                .addGap(18, 18, 18)
                .addComponent(btnGuardarBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlControlBuscarLayout.setVerticalGroup(
            pnlControlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlBuscarLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(pnlControlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarBuscar)
                    .addComponent(btnImprimirBuscar)
                    .addComponent(btnSalirBuscar)
                    .addComponent(lblFiltrosBuscar))
                .addContainerGap())
            .addGroup(pnlControlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrosAplicadosBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBuscarLayout = new javax.swing.GroupLayout(pnlBuscar);
        pnlBuscar.setLayout(pnlBuscarLayout);
        pnlBuscarLayout.setHorizontalGroup(
            pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlDatosBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlFiltrarDatosBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlControlBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBuscarLayout.setVerticalGroup(
            pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFiltrarDatosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnlDatosBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tbpPrincipal.addTab("Buscar", pnlBuscar);

        pnlBuscarTW.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        lblFiltrarEM.setText("Filtrar:");

        btnBuscarEM.setText("Buscar");

        cmbFiltrarEM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione filtro--", "DUI", "Nombre", "Apellido", "JRV", "Cargo", "Centro de votación", "Lugar de Capacitación" }));

        javax.swing.GroupLayout pnlBuscarTWLayout = new javax.swing.GroupLayout(pnlBuscarTW);
        pnlBuscarTW.setLayout(pnlBuscarTWLayout);
        pnlBuscarTWLayout.setHorizontalGroup(
            pnlBuscarTWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarTWLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrarEM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txfFiltrarEM, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarEM, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarEM)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBuscarTWLayout.setVerticalGroup(
            pnlBuscarTWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarTWLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarTWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltrarEM)
                    .addComponent(txfFiltrarEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarEM)
                    .addComponent(cmbFiltrarEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFotoEM.setBackground(new java.awt.Color(51, 51, 255));
        pnlFotoEM.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pnlFotoEM.setOpaque(false);

        javax.swing.GroupLayout pnlFotoEMLayout = new javax.swing.GroupLayout(pnlFotoEM);
        pnlFotoEM.setLayout(pnlFotoEMLayout);
        pnlFotoEMLayout.setHorizontalGroup(
            pnlFotoEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoEM, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        pnlFotoEMLayout.setVerticalGroup(
            pnlFotoEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoEM, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        pnlDatosEM.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        lblDUIEM.setText("DUI:");

        txfDUIEM.setEditable(false);

        lblNombreEM.setText("Nombre:");

        txfNombreEM.setEditable(false);

        lblTelefonoEM.setText("Teléfono:");

        txfTelefonoEM.setEditable(false);

        lblDirEM.setText("Dir:");

        txfDirEM.setEditable(false);

        lblCorreoEM.setText("Correo: ");

        txfCorreoEM.setEditable(false);

        lblJRVEM.setText("JRV:");

        txfJVEM.setEditable(false);

        lblCVEM.setText("Centro de votación:");

        txfCVEM.setEditable(false);

        btnInfoEM.setText("Info");

        txfCargoEM.setEditable(false);

        lblCargoEM.setText("Cargo:");

        javax.swing.GroupLayout pnlDatosEMLayout = new javax.swing.GroupLayout(pnlDatosEM);
        pnlDatosEM.setLayout(pnlDatosEMLayout);
        pnlDatosEMLayout.setHorizontalGroup(
            pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosEMLayout.createSequentialGroup()
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDUIEM)
                            .addComponent(lblCargoEM)
                            .addComponent(lblDirEM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfDUIEM, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txfDirEM)
                            .addComponent(txfCargoEM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreEM, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCorreoEM, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfCorreoEM, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfNombreEM))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonoEM)
                            .addComponent(lblJRVEM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfTelefonoEM, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfJVEM)))
                    .addGroup(pnlDatosEMLayout.createSequentialGroup()
                        .addComponent(lblCVEM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfCVEM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInfoEM)))
                .addContainerGap())
        );
        pnlDatosEMLayout.setVerticalGroup(
            pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDUIEM)
                    .addComponent(txfDUIEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreEM)
                    .addComponent(txfNombreEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoEM)
                    .addComponent(txfTelefonoEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirEM)
                    .addComponent(txfDirEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCorreoEM)
                    .addComponent(txfCorreoEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJRVEM)
                    .addComponent(txfJVEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosEMLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCVEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInfoEM))
                        .addGap(16, 16, 16))
                    .addGroup(pnlDatosEMLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlDatosEMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCargoEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCargoEM))
                        .addGap(18, 18, 18)
                        .addComponent(lblCVEM)
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        tblEM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEMMouseClicked(evt);
            }
        });
        scpnlEM.setViewportView(tblEM);

        btnAgregarEM.setText("Agregar");

        btnEliminarEM.setText("Eliminar");

        btnGuardarEM.setText("Guardar");

        btnImprimirEM.setText("Imprimir");

        javax.swing.GroupLayout pnlEquipoTrabajoLayout = new javax.swing.GroupLayout(pnlEquipoTrabajo);
        pnlEquipoTrabajo.setLayout(pnlEquipoTrabajoLayout);
        pnlEquipoTrabajoLayout.setHorizontalGroup(
            pnlEquipoTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEquipoTrabajoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEquipoTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlEM, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlBuscarTW, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEquipoTrabajoLayout.createSequentialGroup()
                        .addComponent(pnlFotoEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlEquipoTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEquipoTrabajoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAgregarEM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarEM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimirEM))
                            .addComponent(pnlDatosEM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlEquipoTrabajoLayout.setVerticalGroup(
            pnlEquipoTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEquipoTrabajoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBuscarTW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEquipoTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFotoEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlEM, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEquipoTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarEM)
                    .addComponent(btnEliminarEM)
                    .addComponent(btnAgregarEM)
                    .addComponent(btnImprimirEM))
                .addContainerGap())
        );

        tbpPrincipal.addTab("Miembros Electorales", pnlEquipoTrabajo);

        pnlBuscarAfiliado.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        lblFiltrarAfiliados.setText("Filtrar:");

        cmbFiltrarAfiliados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione filtro--", "DUI", "Nombre", "Apellido", "JRV", "Cargo", "Centro de votación", "Lugar de Capacitación" }));

        btnBuscarAfiliados.setText("Buscar");

        javax.swing.GroupLayout pnlBuscarAfiliadoLayout = new javax.swing.GroupLayout(pnlBuscarAfiliado);
        pnlBuscarAfiliado.setLayout(pnlBuscarAfiliadoLayout);
        pnlBuscarAfiliadoLayout.setHorizontalGroup(
            pnlBuscarAfiliadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarAfiliadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrarAfiliados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txfFiltrarAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarAfiliados)
                .addContainerGap(183, Short.MAX_VALUE))
        );
        pnlBuscarAfiliadoLayout.setVerticalGroup(
            pnlBuscarAfiliadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarAfiliadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarAfiliadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltrarAfiliados)
                    .addComponent(txfFiltrarAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarAfiliados)
                    .addComponent(cmbFiltrarAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFotoAfiliados.setBackground(new java.awt.Color(51, 51, 255));
        pnlFotoAfiliados.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pnlFotoAfiliados.setOpaque(false);

        javax.swing.GroupLayout pnlFotoAfiliadosLayout = new javax.swing.GroupLayout(pnlFotoAfiliados);
        pnlFotoAfiliados.setLayout(pnlFotoAfiliadosLayout);
        pnlFotoAfiliadosLayout.setHorizontalGroup(
            pnlFotoAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        pnlFotoAfiliadosLayout.setVerticalGroup(
            pnlFotoAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        pnlDatosAfiliados.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        lblDUIAfiliados.setText("DUI:");

        txfDUIAfiliados.setEditable(false);

        lblNombreAfiliados.setText("Nombre:");

        txfNombreAfiliados.setEditable(false);

        lblTelefonoAfiliados.setText("Teléfono:");

        txfTelefonoAfiliados.setEditable(false);

        lblDirAfiliados.setText("Dir:");

        txfDirAfiliados.setEditable(false);

        lblCorreoAfiliados.setText("Correo: ");

        txfCorreoAfiliados.setEditable(false);

        lblFacebookAfiliados.setText("Facebook:");

        txfFacebookAfiliados.setEditable(false);

        lblDonacionesAfiliados.setText("Total donaciones:");

        txfDonacionesAfiliados.setEditable(false);

        btnHistorialAfiliados.setText("Historal de donaciones");

        btnFacturaAfiliados.setText("Nueva Factura");

        javax.swing.GroupLayout pnlDatosAfiliadosLayout = new javax.swing.GroupLayout(pnlDatosAfiliados);
        pnlDatosAfiliados.setLayout(pnlDatosAfiliadosLayout);
        pnlDatosAfiliadosLayout.setHorizontalGroup(
            pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosAfiliadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addComponent(lblDonacionesAfiliados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfDonacionesAfiliados))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCorreoAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDUIAfiliados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTelefonoAfiliados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfTelefonoAfiliados, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfCorreoAfiliados)
                            .addComponent(txfDUIAfiliados))))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addComponent(lblFacebookAfiliados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfFacebookAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNombreAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(lblDirAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfNombreAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txfDirAfiliados)))
                    .addGroup(pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addComponent(btnHistorialAfiliados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFacturaAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        pnlDatosAfiliadosLayout.setVerticalGroup(
            pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosAfiliadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDUIAfiliados)
                    .addComponent(txfDUIAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreAfiliados)
                    .addComponent(txfNombreAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefonoAfiliados)
                    .addComponent(txfTelefonoAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDirAfiliados)
                    .addComponent(txfDirAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfDonacionesAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHistorialAfiliados)
                            .addComponent(btnFacturaAfiliados))
                        .addGap(16, 16, 16))
                    .addGroup(pnlDatosAfiliadosLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlDatosAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCorreoAfiliados)
                            .addComponent(txfCorreoAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfFacebookAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFacebookAfiliados))
                        .addGap(18, 18, 18)
                        .addComponent(lblDonacionesAfiliados)
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        tblAfiliados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAfiliados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAfiliadosMouseClicked(evt);
            }
        });
        scpnlAfiliados.setViewportView(tblAfiliados);

        btnAgregarAfiliados.setText("Agregar");

        btnEliminarAfiliados.setText("Eliminar");

        btnGuardarAfiliados.setText("Guardar");

        btnImprimirAfiliados.setText("Imprimir");

        javax.swing.GroupLayout pnlAfiliadosLayout = new javax.swing.GroupLayout(pnlAfiliados);
        pnlAfiliados.setLayout(pnlAfiliadosLayout);
        pnlAfiliadosLayout.setHorizontalGroup(
            pnlAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAfiliadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlAfiliados)
                    .addComponent(pnlBuscarAfiliado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAfiliadosLayout.createSequentialGroup()
                        .addComponent(pnlFotoAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAfiliadosLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAgregarAfiliados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarAfiliados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarAfiliados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimirAfiliados))
                            .addComponent(pnlDatosAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlAfiliadosLayout.setVerticalGroup(
            pnlAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAfiliadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBuscarAfiliado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFotoAfiliados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAfiliadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarAfiliados)
                    .addComponent(btnEliminarAfiliados)
                    .addComponent(btnAgregarAfiliados)
                    .addComponent(btnImprimirAfiliados))
                .addContainerGap())
        );

        tbpPrincipal.addTab("Afiliados", pnlAfiliados);

        pnlBuscarCapacitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        lblFiltrarCapacitaciones.setText("Filtrar:");

        btnBuscarCapacitaciones.setText("Buscar");

        cmbFiltrarCapacitaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione filtro--", "DUI", "Nombre", "Apellido", "JRV", "Cargo", "Centro de votación", "Lugar de Capacitación" }));

        javax.swing.GroupLayout pnlBuscarCapacitacionesLayout = new javax.swing.GroupLayout(pnlBuscarCapacitaciones);
        pnlBuscarCapacitaciones.setLayout(pnlBuscarCapacitacionesLayout);
        pnlBuscarCapacitacionesLayout.setHorizontalGroup(
            pnlBuscarCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrarCapacitaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txfFiltrarCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarCapacitaciones)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBuscarCapacitacionesLayout.setVerticalGroup(
            pnlBuscarCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltrarCapacitaciones)
                    .addComponent(txfFiltrarCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCapacitaciones)
                    .addComponent(cmbFiltrarCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFotoCapacitaciones.setBackground(new java.awt.Color(51, 51, 255));
        pnlFotoCapacitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pnlFotoCapacitaciones.setOpaque(false);

        javax.swing.GroupLayout pnlFotoCapacitacionesLayout = new javax.swing.GroupLayout(pnlFotoCapacitaciones);
        pnlFotoCapacitaciones.setLayout(pnlFotoCapacitacionesLayout);
        pnlFotoCapacitacionesLayout.setHorizontalGroup(
            pnlFotoCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoCapacitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        pnlFotoCapacitacionesLayout.setVerticalGroup(
            pnlFotoCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoCapacitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        pnlDatosCapacitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        lblDUIEM1.setText("DUI:");

        txfDUIEM1.setEditable(false);

        lblNombreEM1.setText("Nombre:");

        txfNombreEM1.setEditable(false);

        lblTelefonoEM1.setText("Teléfono:");

        txfTelefonoEM1.setEditable(false);

        lblDirEM1.setText("Dir:");

        txfDirEM1.setEditable(false);

        lblCorreoEM1.setText("Correo: ");

        txfCorreoEM1.setEditable(false);

        lblJRVEM1.setText("JRV:");

        txfJVEM1.setEditable(false);

        lblCVEM1.setText("Centro de votación:");

        txfCVEM2.setEditable(false);

        btnInfoEM1.setText("Info");

        txfCargoEM1.setEditable(false);

        lblCargoEM1.setText("Cargo:");

        javax.swing.GroupLayout pnlDatosCapacitacionesLayout = new javax.swing.GroupLayout(pnlDatosCapacitaciones);
        pnlDatosCapacitaciones.setLayout(pnlDatosCapacitacionesLayout);
        pnlDatosCapacitacionesLayout.setHorizontalGroup(
            pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosCapacitacionesLayout.createSequentialGroup()
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDUIEM1)
                            .addComponent(lblCargoEM1)
                            .addComponent(lblDirEM1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfDUIEM1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txfDirEM1)
                            .addComponent(txfCargoEM1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreEM1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCorreoEM1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfCorreoEM1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfNombreEM1))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonoEM1)
                            .addComponent(lblJRVEM1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfTelefonoEM1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfJVEM1)))
                    .addGroup(pnlDatosCapacitacionesLayout.createSequentialGroup()
                        .addComponent(lblCVEM1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfCVEM2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInfoEM1)))
                .addContainerGap())
        );
        pnlDatosCapacitacionesLayout.setVerticalGroup(
            pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDUIEM1)
                    .addComponent(txfDUIEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreEM1)
                    .addComponent(txfNombreEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoEM1)
                    .addComponent(txfTelefonoEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirEM1)
                    .addComponent(txfDirEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCorreoEM1)
                    .addComponent(txfCorreoEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJRVEM1)
                    .addComponent(txfJVEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosCapacitacionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCVEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInfoEM1))
                        .addGap(16, 16, 16))
                    .addGroup(pnlDatosCapacitacionesLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlDatosCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCargoEM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCargoEM1))
                        .addGap(18, 18, 18)
                        .addComponent(lblCVEM1)
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        tblCapacitaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCapacitaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCapacitacionesMouseClicked(evt);
            }
        });
        scpnlCapacitaciones.setViewportView(tblCapacitaciones);

        btnAgregarEM1.setText("Agregar");

        btnEliminarEM2.setText("Eliminar");

        btnGuardarEM1.setText("Guardar");

        btnImprimirEM1.setText("Imprimir");

        javax.swing.GroupLayout pnlCapacitacionesLayout = new javax.swing.GroupLayout(pnlCapacitaciones);
        pnlCapacitaciones.setLayout(pnlCapacitacionesLayout);
        pnlCapacitacionesLayout.setHorizontalGroup(
            pnlCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlCapacitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlBuscarCapacitaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapacitacionesLayout.createSequentialGroup()
                        .addComponent(pnlFotoCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCapacitacionesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAgregarEM1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEM2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarEM1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimirEM1))
                            .addComponent(pnlDatosCapacitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlCapacitacionesLayout.setVerticalGroup(
            pnlCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapacitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBuscarCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFotoCapacitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlCapacitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCapacitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarEM1)
                    .addComponent(btnEliminarEM2)
                    .addComponent(btnAgregarEM1)
                    .addComponent(btnImprimirEM1))
                .addContainerGap())
        );

        tbpPrincipal.addTab("Capacitaciones", pnlCapacitaciones);

        pnlBuscarTW2.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        lblFiltrarEM2.setText("Filtrar:");

        btnBuscarEM2.setText("Buscar");

        cmbFiltrarEM2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione filtro--", "DUI", "Nombre", "Apellido", "JRV", "Cargo", "Centro de votación", "Lugar de Capacitación" }));

        javax.swing.GroupLayout pnlBuscarTW2Layout = new javax.swing.GroupLayout(pnlBuscarTW2);
        pnlBuscarTW2.setLayout(pnlBuscarTW2Layout);
        pnlBuscarTW2Layout.setHorizontalGroup(
            pnlBuscarTW2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarTW2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrarEM2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txfFiltrarEM2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarEM2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarEM2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBuscarTW2Layout.setVerticalGroup(
            pnlBuscarTW2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarTW2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarTW2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltrarEM2)
                    .addComponent(txfFiltrarEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarEM2)
                    .addComponent(cmbFiltrarEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFotoEM2.setBackground(new java.awt.Color(51, 51, 255));
        pnlFotoEM2.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pnlFotoEM2.setOpaque(false);

        javax.swing.GroupLayout pnlFotoEM2Layout = new javax.swing.GroupLayout(pnlFotoEM2);
        pnlFotoEM2.setLayout(pnlFotoEM2Layout);
        pnlFotoEM2Layout.setHorizontalGroup(
            pnlFotoEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        pnlFotoEM2Layout.setVerticalGroup(
            pnlFotoEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        pnlDatosEM2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        lblDUIEM2.setText("DUI:");

        txfDUIEM2.setEditable(false);

        lblNombreEM2.setText("Nombre:");

        txfNombreEM2.setEditable(false);

        lblTelefonoEM2.setText("Teléfono:");

        txfTelefonoEM2.setEditable(false);

        lblDirEM2.setText("Dir:");

        txfDirEM2.setEditable(false);

        lblCorreoEM2.setText("Correo: ");

        txfCorreoEM2.setEditable(false);

        lblJRVEM2.setText("JRV:");

        txfJVEM2.setEditable(false);

        lblCVEM2.setText("Centro de votación:");

        txfCVEM3.setEditable(false);

        btnInfoEM2.setText("Info");

        txfCargoEM2.setEditable(false);

        lblCargoEM2.setText("Cargo:");

        javax.swing.GroupLayout pnlDatosEM2Layout = new javax.swing.GroupLayout(pnlDatosEM2);
        pnlDatosEM2.setLayout(pnlDatosEM2Layout);
        pnlDatosEM2Layout.setHorizontalGroup(
            pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEM2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosEM2Layout.createSequentialGroup()
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDUIEM2)
                            .addComponent(lblCargoEM2)
                            .addComponent(lblDirEM2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfDUIEM2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txfDirEM2)
                            .addComponent(txfCargoEM2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreEM2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCorreoEM2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfCorreoEM2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfNombreEM2))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonoEM2)
                            .addComponent(lblJRVEM2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfTelefonoEM2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfJVEM2)))
                    .addGroup(pnlDatosEM2Layout.createSequentialGroup()
                        .addComponent(lblCVEM2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfCVEM3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInfoEM2)))
                .addContainerGap())
        );
        pnlDatosEM2Layout.setVerticalGroup(
            pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEM2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDUIEM2)
                    .addComponent(txfDUIEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreEM2)
                    .addComponent(txfNombreEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoEM2)
                    .addComponent(txfTelefonoEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirEM2)
                    .addComponent(txfDirEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCorreoEM2)
                    .addComponent(txfCorreoEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJRVEM2)
                    .addComponent(txfJVEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosEM2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCVEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInfoEM2))
                        .addGap(16, 16, 16))
                    .addGroup(pnlDatosEM2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlDatosEM2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCargoEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCargoEM2))
                        .addGap(18, 18, 18)
                        .addComponent(lblCVEM2)
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        tblEM2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEM2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEM2MouseClicked(evt);
            }
        });
        scpnlEM2.setViewportView(tblEM2);

        btnAgregarEM2.setText("Agregar");

        btnEliminarEM3.setText("Eliminar");

        btnGuardarEM2.setText("Guardar");

        btnImprimirEM2.setText("Imprimir");

        javax.swing.GroupLayout pnlComisionesLayout = new javax.swing.GroupLayout(pnlComisiones);
        pnlComisiones.setLayout(pnlComisionesLayout);
        pnlComisionesLayout.setHorizontalGroup(
            pnlComisionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComisionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlComisionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlEM2, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlBuscarTW2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComisionesLayout.createSequentialGroup()
                        .addComponent(pnlFotoEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlComisionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlComisionesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAgregarEM2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEM3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarEM2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimirEM2))
                            .addComponent(pnlDatosEM2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlComisionesLayout.setVerticalGroup(
            pnlComisionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComisionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBuscarTW2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlComisionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFotoEM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlEM2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlComisionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarEM2)
                    .addComponent(btnEliminarEM3)
                    .addComponent(btnAgregarEM2)
                    .addComponent(btnImprimirEM2))
                .addContainerGap())
        );

        tbpPrincipal.addTab("Comisiones", pnlComisiones);

        javax.swing.GroupLayout pnlCuadroElectoralLayout = new javax.swing.GroupLayout(pnlCuadroElectoral);
        pnlCuadroElectoral.setLayout(pnlCuadroElectoralLayout);
        pnlCuadroElectoralLayout.setHorizontalGroup(
            pnlCuadroElectoralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );
        pnlCuadroElectoralLayout.setVerticalGroup(
            pnlCuadroElectoralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        tbpPrincipal.addTab("Cuadro electoral", pnlCuadroElectoral);

        javax.swing.GroupLayout pnlVotacionesLayout = new javax.swing.GroupLayout(pnlVotaciones);
        pnlVotaciones.setLayout(pnlVotacionesLayout);
        pnlVotacionesLayout.setHorizontalGroup(
            pnlVotacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );
        pnlVotacionesLayout.setVerticalGroup(
            pnlVotacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        tbpPrincipal.addTab("Votaciones", pnlVotaciones);

        pnlBuscarTW3.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        lblFiltrarEM3.setText("Filtrar:");

        btnBuscarEM3.setText("Buscar");

        cmbFiltrarEM3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione filtro--", "DUI", "Nombre", "Apellido", "JRV", "Cargo", "Centro de votación", "Lugar de Capacitación" }));

        javax.swing.GroupLayout pnlBuscarTW3Layout = new javax.swing.GroupLayout(pnlBuscarTW3);
        pnlBuscarTW3.setLayout(pnlBuscarTW3Layout);
        pnlBuscarTW3Layout.setHorizontalGroup(
            pnlBuscarTW3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarTW3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrarEM3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txfFiltrarEM3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarEM3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarEM3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBuscarTW3Layout.setVerticalGroup(
            pnlBuscarTW3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarTW3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarTW3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltrarEM3)
                    .addComponent(txfFiltrarEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarEM3)
                    .addComponent(cmbFiltrarEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFotoEM3.setBackground(new java.awt.Color(51, 51, 255));
        pnlFotoEM3.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pnlFotoEM3.setOpaque(false);

        javax.swing.GroupLayout pnlFotoEM3Layout = new javax.swing.GroupLayout(pnlFotoEM3);
        pnlFotoEM3.setLayout(pnlFotoEM3Layout);
        pnlFotoEM3Layout.setHorizontalGroup(
            pnlFotoEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoEM4, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        pnlFotoEM3Layout.setVerticalGroup(
            pnlFotoEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFotoEM4, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        pnlDatosEM3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        lblDUIEM3.setText("DUI:");

        txfDUIEM3.setEditable(false);

        lblNombreEM3.setText("Nombre:");

        txfNombreEM3.setEditable(false);

        lblTelefonoEM3.setText("Teléfono:");

        txfTelefonoEM3.setEditable(false);

        lblDirEM3.setText("Dir:");

        txfDirEM3.setEditable(false);

        lblCorreoEM3.setText("Correo: ");

        txfCorreoEM3.setEditable(false);

        lblJRVEM3.setText("JRV:");

        txfJVEM3.setEditable(false);

        lblCVEM3.setText("Centro de votación:");

        txfCVEM4.setEditable(false);

        btnInfoEM3.setText("Info");

        txfCargoEM3.setEditable(false);

        lblCargoEM3.setText("Cargo:");

        javax.swing.GroupLayout pnlDatosEM3Layout = new javax.swing.GroupLayout(pnlDatosEM3);
        pnlDatosEM3.setLayout(pnlDatosEM3Layout);
        pnlDatosEM3Layout.setHorizontalGroup(
            pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEM3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosEM3Layout.createSequentialGroup()
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDUIEM3)
                            .addComponent(lblCargoEM3)
                            .addComponent(lblDirEM3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfDUIEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txfDirEM3)
                            .addComponent(txfCargoEM3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreEM3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCorreoEM3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfCorreoEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfNombreEM3))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonoEM3)
                            .addComponent(lblJRVEM3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfTelefonoEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txfJVEM3)))
                    .addGroup(pnlDatosEM3Layout.createSequentialGroup()
                        .addComponent(lblCVEM3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfCVEM4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInfoEM3)))
                .addContainerGap())
        );
        pnlDatosEM3Layout.setVerticalGroup(
            pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosEM3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDUIEM3)
                    .addComponent(txfDUIEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreEM3)
                    .addComponent(txfNombreEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoEM3)
                    .addComponent(txfTelefonoEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirEM3)
                    .addComponent(txfDirEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCorreoEM3)
                    .addComponent(txfCorreoEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJRVEM3)
                    .addComponent(txfJVEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosEM3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCVEM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInfoEM3))
                        .addGap(16, 16, 16))
                    .addGroup(pnlDatosEM3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlDatosEM3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfCargoEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCargoEM3))
                        .addGap(18, 18, 18)
                        .addComponent(lblCVEM3)
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        tblEM3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEM3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEM3MouseClicked(evt);
            }
        });
        scpnlEM3.setViewportView(tblEM3);

        btnAgregarEM3.setText("Agregar");

        btnEliminarEM4.setText("Eliminar");

        btnGuardarEM3.setText("Guardar");

        btnImprimirEM3.setText("Imprimir");

        javax.swing.GroupLayout pnlVisitasLayout = new javax.swing.GroupLayout(pnlVisitas);
        pnlVisitas.setLayout(pnlVisitasLayout);
        pnlVisitasLayout.setHorizontalGroup(
            pnlVisitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVisitasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlVisitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlBuscarTW3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVisitasLayout.createSequentialGroup()
                        .addComponent(pnlFotoEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlVisitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlVisitasLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAgregarEM3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarEM4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarEM3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimirEM3))
                            .addComponent(pnlDatosEM3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlVisitasLayout.setVerticalGroup(
            pnlVisitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVisitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBuscarTW3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlVisitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDatosEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFotoEM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlEM3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlVisitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarEM3)
                    .addComponent(btnEliminarEM4)
                    .addComponent(btnAgregarEM3)
                    .addComponent(btnImprimirEM3))
                .addContainerGap())
        );

        tbpPrincipal.addTab("Visitas", pnlVisitas);

        pnlFiltrarDatosFacturacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar datos"));

        cmbFiltrarFacturacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAplicarFiltroFacturacion.setText("Aplicar filtro");

        btnLimpiarFiltroFacturacion.setText("Limpiar filtros");

        javax.swing.GroupLayout pnlFiltrarDatosFacturacionLayout = new javax.swing.GroupLayout(pnlFiltrarDatosFacturacion);
        pnlFiltrarDatosFacturacion.setLayout(pnlFiltrarDatosFacturacionLayout);
        pnlFiltrarDatosFacturacionLayout.setHorizontalGroup(
            pnlFiltrarDatosFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarDatosFacturacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfFiltrarFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAplicarFiltroFacturacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpiarFiltroFacturacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFiltrarDatosFacturacionLayout.setVerticalGroup(
            pnlFiltrarDatosFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiltrarDatosFacturacionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFiltrarDatosFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfFiltrarFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltrarFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAplicarFiltroFacturacion)
                    .addComponent(btnLimpiarFiltroFacturacion))
                .addContainerGap())
        );

        scpnlDatosFacturacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        tblDatosFacturacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scpnlDatosFacturacion.setViewportView(tblDatosFacturacion);

        pnlControlFacturacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));

        btnGuardarFacturacion.setText("Guardar");

        btnImprimirFacturacion.setText("Imprimir");

        btnSalirFacturacion.setText("Salir");
        btnSalirFacturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirFacturacionActionPerformed(evt);
            }
        });

        lblFiltrosAplicadosFacturacion.setText("Filtros aplicados:");

        lblFiltrosFacturacion.setText("Ninguno");

        btnNuevaFactura.setText("Nueva Factura");

        javax.swing.GroupLayout pnlControlFacturacionLayout = new javax.swing.GroupLayout(pnlControlFacturacion);
        pnlControlFacturacion.setLayout(pnlControlFacturacionLayout);
        pnlControlFacturacionLayout.setHorizontalGroup(
            pnlControlFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlFacturacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrosAplicadosFacturacion)
                .addGap(18, 18, 18)
                .addComponent(lblFiltrosFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaFactura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimirFacturacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarFacturacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlControlFacturacionLayout.setVerticalGroup(
            pnlControlFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlFacturacionLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(pnlControlFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarFacturacion)
                    .addComponent(btnImprimirFacturacion)
                    .addComponent(btnSalirFacturacion)
                    .addComponent(lblFiltrosFacturacion)
                    .addComponent(btnNuevaFactura))
                .addContainerGap())
            .addGroup(pnlControlFacturacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFiltrosAplicadosFacturacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlFacturacionLayout = new javax.swing.GroupLayout(pnlFacturacion);
        pnlFacturacion.setLayout(pnlFacturacionLayout);
        pnlFacturacionLayout.setHorizontalGroup(
            pnlFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacturacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlDatosFacturacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlFiltrarDatosFacturacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlControlFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlFacturacionLayout.setVerticalGroup(
            pnlFacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacturacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFiltrarDatosFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnlDatosFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControlFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tbpPrincipal.addTab("Facturación", pnlFacturacion);

        pnlAdmin.setBorder(javax.swing.BorderFactory.createTitledBorder("Administrar"));
        pnlAdmin.setPreferredSize(new java.awt.Dimension(695, 210));

        btnCargosME.setText("Cargos de Miembros Electorales");

        btnCargoCom.setText("Cargos de comisionados");

        btnCentroVotacion.setText("Centros de votación");
        btnCentroVotacion.setPreferredSize(new java.awt.Dimension(200, 45));

        btnComisiones.setText("Comisiones");
        btnComisiones.setPreferredSize(new java.awt.Dimension(200, 45));

        btnDepartamentos.setText("Departamentos");
        btnDepartamentos.setPreferredSize(new java.awt.Dimension(200, 45));
        btnDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartamentosActionPerformed(evt);
            }
        });

        btnJRV.setText("Juntas receptoras de votos");
        btnJRV.setPreferredSize(new java.awt.Dimension(200, 45));

        btnMunicipios.setText("Municipios");
        btnMunicipios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMunicipiosActionPerformed(evt);
            }
        });

        btnNivelesAcad.setText("Niveles académicos");

        btnPartidos.setText("Partidos políticos");
        btnPartidos.setPreferredSize(new java.awt.Dimension(200, 45));

        javax.swing.GroupLayout pnlAdminLayout = new javax.swing.GroupLayout(pnlAdmin);
        pnlAdmin.setLayout(pnlAdminLayout);
        pnlAdminLayout.setHorizontalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCargoCom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCargosME, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCentroVotacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnComisiones, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJRV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMunicipios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNivelesAcad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPartidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        pnlAdminLayout.setVerticalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCargosME, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnComisiones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMunicipios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCargoCom, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNivelesAcad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnCentroVotacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnJRV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPartidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pnlDescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));
        pnlDescripcion.setPreferredSize(new java.awt.Dimension(695, 210));

        lblDescripción.setText("Texto descriptivo");

        javax.swing.GroupLayout pnlDescripcionLayout = new javax.swing.GroupLayout(pnlDescripcion);
        pnlDescripcion.setLayout(pnlDescripcionLayout);
        pnlDescripcionLayout.setHorizontalGroup(
            pnlDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDescripcionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDescripción)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDescripcionLayout.setVerticalGroup(
            pnlDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDescripcionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDescripción)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(pnlDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbpPrincipal.addTab("Panel de Control", pnlControl);

        mnuArchivo.setText("Archivo");

        mnuArchivoSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnuArchivoSalir.setText("Salir");
        mnuArchivoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuArchivoSalirActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuArchivoSalir);

        jmbMenuBar.add(mnuArchivo);

        mnuEditar.setText("Editar");
        jmbMenuBar.add(mnuEditar);

        mnuFacturar.setText("Facturar");
        jmbMenuBar.add(mnuFacturar);

        mnuUsuario.setText("Usuario");
        jmbMenuBar.add(mnuUsuario);

        mnuVentana.setText("Ventana");
        jmbMenuBar.add(mnuVentana);

        mnuAyuda.setText("Ayuda");
        jmbMenuBar.add(mnuAyuda);

        setJMenuBar(jmbMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuArchivoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuArchivoSalirActionPerformed
        salir();
    }//GEN-LAST:event_mnuArchivoSalirActionPerformed

    private void btnMunicipiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMunicipiosActionPerformed
        FrmMunicipios mun=new FrmMunicipios(this,true);
        mun.main();
    }//GEN-LAST:event_btnMunicipiosActionPerformed

    private void btnDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartamentosActionPerformed
        FrmDepartamentos dp=new FrmDepartamentos(this,true);
        dp.main();
    }//GEN-LAST:event_btnDepartamentosActionPerformed

    private void btnSalirFacturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirFacturacionActionPerformed
        salir();
    }//GEN-LAST:event_btnSalirFacturacionActionPerformed

    private void btnSalirBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirBuscarActionPerformed
        salir();
    }//GEN-LAST:event_btnSalirBuscarActionPerformed

    private void tblEMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEMMouseClicked
        getMiembroById(tblEM.getValueAt(tblEM.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tblEMMouseClicked

    private void tblAfiliadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAfiliadosMouseClicked
        getAfiliadoById(Integer.parseInt(tblAfiliados.getValueAt(tblAfiliados.getSelectedRow(),0).toString()));
    }//GEN-LAST:event_tblAfiliadosMouseClicked

    private void tblCapacitacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCapacitacionesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblCapacitacionesMouseClicked

    private void tblEM2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEM2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEM2MouseClicked

    private void tblEM3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEM3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEM3MouseClicked

    private void btnAplicarFiltroBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarFiltroBuscarActionPerformed
        switch (cmbFiltrarDatosBuscar.getSelectedIndex()) {
            case 0:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("dui", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/dui.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("dui",paramReporteBuscar);
                break;
            case 1:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("nombre", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/nombre.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("nombre",paramReporteBuscar);
                break;
            case 2:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("apellido", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/apellido.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("apellido",paramReporteBuscar);
                break;
            case 3:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("telefono", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/telefono.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("telefono",paramReporteBuscar);
                break;
            case 4:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("direccion", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/direccion.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("direccion",paramReporteBuscar);
                break;
            case 5:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("correo", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/correo.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("correo",paramReporteBuscar);
                break;
            default:
                paramReporteBuscar = txfFiltrarBuscar.getText()+"%";
                tblDatosBuscar.setModel(llenarTblBuscar("nivelAcademico.nivelAcademico", paramReporteBuscar));
                pathReporteBuscar = this.getClass().getResource("/reportes/general/nivelAcademico.jasper");
                parametroReporteBuscar = new HashMap();
                parametroReporteBuscar.put("nivelAcademico",paramReporteBuscar);
                break;
        }
    }//GEN-LAST:event_btnAplicarFiltroBuscarActionPerformed

    private void btnImprimirBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirBuscarActionPerformed
        Conexion con = new Conexion();
        Connection conn;
        try{
            conn = con.conectar();
            
            JasperReport reporte = null;
            
            reporte = (JasperReport) JRLoader.loadObject(pathReporteBuscar);
            
            JasperPrint jprint=JasperFillManager.fillReport(reporte, parametroReporteBuscar, conn);
            
            JasperViewer view = new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            
        } catch (JRException e){
            e.getMessage();
        } finally{
            con.desconectar();
        }
    }//GEN-LAST:event_btnImprimirBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarAfiliados;
    private javax.swing.JButton btnAgregarEM;
    private javax.swing.JButton btnAgregarEM1;
    private javax.swing.JButton btnAgregarEM2;
    private javax.swing.JButton btnAgregarEM3;
    private javax.swing.JButton btnAplicarFiltroBuscar;
    private javax.swing.JButton btnAplicarFiltroFacturacion;
    private javax.swing.JButton btnBuscarAfiliados;
    private javax.swing.JButton btnBuscarCapacitaciones;
    private javax.swing.JButton btnBuscarEM;
    private javax.swing.JButton btnBuscarEM2;
    private javax.swing.JButton btnBuscarEM3;
    private javax.swing.JButton btnCargoCom;
    private javax.swing.JButton btnCargosME;
    private javax.swing.JButton btnCentroVotacion;
    private javax.swing.JButton btnComisiones;
    private javax.swing.JButton btnDepartamentos;
    private javax.swing.JButton btnEliminarAfiliados;
    private javax.swing.JButton btnEliminarEM;
    private javax.swing.JButton btnEliminarEM2;
    private javax.swing.JButton btnEliminarEM3;
    private javax.swing.JButton btnEliminarEM4;
    private javax.swing.JButton btnFacturaAfiliados;
    private javax.swing.JButton btnGuardarAfiliados;
    private javax.swing.JButton btnGuardarBuscar;
    private javax.swing.JButton btnGuardarEM;
    private javax.swing.JButton btnGuardarEM1;
    private javax.swing.JButton btnGuardarEM2;
    private javax.swing.JButton btnGuardarEM3;
    private javax.swing.JButton btnGuardarFacturacion;
    private javax.swing.JButton btnHistorialAfiliados;
    private javax.swing.JButton btnImprimirAfiliados;
    private javax.swing.JButton btnImprimirBuscar;
    private javax.swing.JButton btnImprimirEM;
    private javax.swing.JButton btnImprimirEM1;
    private javax.swing.JButton btnImprimirEM2;
    private javax.swing.JButton btnImprimirEM3;
    private javax.swing.JButton btnImprimirFacturacion;
    private javax.swing.JButton btnInfoEM;
    private javax.swing.JButton btnInfoEM1;
    private javax.swing.JButton btnInfoEM2;
    private javax.swing.JButton btnInfoEM3;
    private javax.swing.JButton btnJRV;
    private javax.swing.JButton btnLimpiarFiltroFacturacion;
    private javax.swing.JButton btnMunicipios;
    private javax.swing.JButton btnNivelesAcad;
    private javax.swing.JButton btnNuevaFactura;
    private javax.swing.JButton btnPartidos;
    private javax.swing.JButton btnSalirBuscar;
    private javax.swing.JButton btnSalirFacturacion;
    private javax.swing.JComboBox<String> cmbFiltrarAfiliados;
    private javax.swing.JComboBox<String> cmbFiltrarCapacitaciones;
    private javax.swing.JComboBox<String> cmbFiltrarDatosBuscar;
    private javax.swing.JComboBox<String> cmbFiltrarEM;
    private javax.swing.JComboBox<String> cmbFiltrarEM2;
    private javax.swing.JComboBox<String> cmbFiltrarEM3;
    private javax.swing.JComboBox<String> cmbFiltrarFacturacion;
    private javax.swing.JMenuBar jmbMenuBar;
    private javax.swing.JLabel lblCVEM;
    private javax.swing.JLabel lblCVEM1;
    private javax.swing.JLabel lblCVEM2;
    private javax.swing.JLabel lblCVEM3;
    private javax.swing.JLabel lblCargoEM;
    private javax.swing.JLabel lblCargoEM1;
    private javax.swing.JLabel lblCargoEM2;
    private javax.swing.JLabel lblCargoEM3;
    private javax.swing.JLabel lblCorreoAfiliados;
    private javax.swing.JLabel lblCorreoEM;
    private javax.swing.JLabel lblCorreoEM1;
    private javax.swing.JLabel lblCorreoEM2;
    private javax.swing.JLabel lblCorreoEM3;
    private javax.swing.JLabel lblDUIAfiliados;
    private javax.swing.JLabel lblDUIEM;
    private javax.swing.JLabel lblDUIEM1;
    private javax.swing.JLabel lblDUIEM2;
    private javax.swing.JLabel lblDUIEM3;
    private javax.swing.JLabel lblDescripción;
    private javax.swing.JLabel lblDirAfiliados;
    private javax.swing.JLabel lblDirEM;
    private javax.swing.JLabel lblDirEM1;
    private javax.swing.JLabel lblDirEM2;
    private javax.swing.JLabel lblDirEM3;
    private javax.swing.JLabel lblDonacionesAfiliados;
    private javax.swing.JLabel lblFacebookAfiliados;
    private javax.swing.JLabel lblFiltrarAfiliados;
    private javax.swing.JLabel lblFiltrarCapacitaciones;
    private javax.swing.JLabel lblFiltrarEM;
    private javax.swing.JLabel lblFiltrarEM2;
    private javax.swing.JLabel lblFiltrarEM3;
    private javax.swing.JLabel lblFiltrosAplicadosBuscar;
    private javax.swing.JLabel lblFiltrosAplicadosFacturacion;
    private javax.swing.JLabel lblFiltrosBuscar;
    private javax.swing.JLabel lblFiltrosFacturacion;
    private javax.swing.JLabel lblFotoAfiliados;
    private javax.swing.JLabel lblFotoCapacitaciones;
    private javax.swing.JLabel lblFotoEM;
    private javax.swing.JLabel lblFotoEM3;
    private javax.swing.JLabel lblFotoEM4;
    private javax.swing.JLabel lblJRVEM;
    private javax.swing.JLabel lblJRVEM1;
    private javax.swing.JLabel lblJRVEM2;
    private javax.swing.JLabel lblJRVEM3;
    private javax.swing.JLabel lblNombreAfiliados;
    private javax.swing.JLabel lblNombreEM;
    private javax.swing.JLabel lblNombreEM1;
    private javax.swing.JLabel lblNombreEM2;
    private javax.swing.JLabel lblNombreEM3;
    private javax.swing.JLabel lblTelefonoAfiliados;
    private javax.swing.JLabel lblTelefonoEM;
    private javax.swing.JLabel lblTelefonoEM1;
    private javax.swing.JLabel lblTelefonoEM2;
    private javax.swing.JLabel lblTelefonoEM3;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenuItem mnuArchivoSalir;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenu mnuEditar;
    private javax.swing.JMenu mnuFacturar;
    private javax.swing.JMenu mnuUsuario;
    private javax.swing.JMenu mnuVentana;
    private javax.swing.JPanel pnlAdmin;
    private javax.swing.JPanel pnlAfiliados;
    private javax.swing.JPanel pnlBuscar;
    private javax.swing.JPanel pnlBuscarAfiliado;
    private javax.swing.JPanel pnlBuscarCapacitaciones;
    private javax.swing.JPanel pnlBuscarTW;
    private javax.swing.JPanel pnlBuscarTW2;
    private javax.swing.JPanel pnlBuscarTW3;
    private javax.swing.JPanel pnlCapacitaciones;
    private javax.swing.JPanel pnlComisiones;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlControlBuscar;
    private javax.swing.JPanel pnlControlFacturacion;
    private javax.swing.JPanel pnlCuadroElectoral;
    private javax.swing.JPanel pnlDatosAfiliados;
    private javax.swing.JPanel pnlDatosCapacitaciones;
    private javax.swing.JPanel pnlDatosEM;
    private javax.swing.JPanel pnlDatosEM2;
    private javax.swing.JPanel pnlDatosEM3;
    private javax.swing.JPanel pnlDescripcion;
    private javax.swing.JPanel pnlEquipoTrabajo;
    private javax.swing.JPanel pnlFacturacion;
    private javax.swing.JPanel pnlFiltrarDatosBuscar;
    private javax.swing.JPanel pnlFiltrarDatosFacturacion;
    private javax.swing.JPanel pnlFotoAfiliados;
    private javax.swing.JPanel pnlFotoCapacitaciones;
    private javax.swing.JPanel pnlFotoEM;
    private javax.swing.JPanel pnlFotoEM2;
    private javax.swing.JPanel pnlFotoEM3;
    private javax.swing.JPanel pnlVisitas;
    private javax.swing.JPanel pnlVotaciones;
    private javax.swing.JScrollPane scpnlAfiliados;
    private javax.swing.JScrollPane scpnlCapacitaciones;
    private javax.swing.JScrollPane scpnlDatosBuscar;
    private javax.swing.JScrollPane scpnlDatosFacturacion;
    private javax.swing.JScrollPane scpnlEM;
    private javax.swing.JScrollPane scpnlEM2;
    private javax.swing.JScrollPane scpnlEM3;
    private javax.swing.JTable tblAfiliados;
    private javax.swing.JTable tblCapacitaciones;
    private javax.swing.JTable tblDatosBuscar;
    private javax.swing.JTable tblDatosFacturacion;
    private javax.swing.JTable tblEM;
    private javax.swing.JTable tblEM2;
    private javax.swing.JTable tblEM3;
    private javax.swing.JTabbedPane tbpPrincipal;
    private javax.swing.JTextField txfCVEM;
    private javax.swing.JTextField txfCVEM2;
    private javax.swing.JTextField txfCVEM3;
    private javax.swing.JTextField txfCVEM4;
    private javax.swing.JTextField txfCargoEM;
    private javax.swing.JTextField txfCargoEM1;
    private javax.swing.JTextField txfCargoEM2;
    private javax.swing.JTextField txfCargoEM3;
    private javax.swing.JTextField txfCorreoAfiliados;
    private javax.swing.JTextField txfCorreoEM;
    private javax.swing.JTextField txfCorreoEM1;
    private javax.swing.JTextField txfCorreoEM2;
    private javax.swing.JTextField txfCorreoEM3;
    private javax.swing.JTextField txfDUIAfiliados;
    private javax.swing.JTextField txfDUIEM;
    private javax.swing.JTextField txfDUIEM1;
    private javax.swing.JTextField txfDUIEM2;
    private javax.swing.JTextField txfDUIEM3;
    private javax.swing.JTextField txfDirAfiliados;
    private javax.swing.JTextField txfDirEM;
    private javax.swing.JTextField txfDirEM1;
    private javax.swing.JTextField txfDirEM2;
    private javax.swing.JTextField txfDirEM3;
    private javax.swing.JTextField txfDonacionesAfiliados;
    private javax.swing.JTextField txfFacebookAfiliados;
    private javax.swing.JTextField txfFiltrarAfiliados;
    private javax.swing.JTextField txfFiltrarBuscar;
    private javax.swing.JTextField txfFiltrarCapacitaciones;
    private javax.swing.JTextField txfFiltrarEM;
    private javax.swing.JTextField txfFiltrarEM2;
    private javax.swing.JTextField txfFiltrarEM3;
    private javax.swing.JTextField txfFiltrarFacturacion;
    private javax.swing.JTextField txfJVEM;
    private javax.swing.JTextField txfJVEM1;
    private javax.swing.JTextField txfJVEM2;
    private javax.swing.JTextField txfJVEM3;
    private javax.swing.JTextField txfNombreAfiliados;
    private javax.swing.JTextField txfNombreEM;
    private javax.swing.JTextField txfNombreEM1;
    private javax.swing.JTextField txfNombreEM2;
    private javax.swing.JTextField txfNombreEM3;
    private javax.swing.JTextField txfTelefonoAfiliados;
    private javax.swing.JTextField txfTelefonoEM;
    private javax.swing.JTextField txfTelefonoEM1;
    private javax.swing.JTextField txfTelefonoEM2;
    private javax.swing.JTextField txfTelefonoEM3;
    // End of variables declaration//GEN-END:variables

    public void salir(){
        System.exit(0);
    }

}
package edu.nur.edd.dao;

import edu.nur.edd.listas.ListaDoble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class VentanaF2 extends JFrame {
    private static Logger logger = LogManager.getRootLogger();
    private EstudianteDto estudiante;

    private JTextField codigoIDTextField = new JTextField();
    private JTextField nombreTextField = new JTextField();
    private JTextField apPaternoTextField = new JTextField();
    private JTextField apMaternoTextField = new JTextField();
    private JTextField dtNacimientoTextField = new JTextField();
    private JTextField materiaIDTextField = new JTextField();
    private JTextField carreraTextField = new JTextField();


    private JPanel panel_informacion = new JPanel();
    private JPanel panel_principal = new JPanel();
    private JPanel panel_principal2 = new JPanel();

    private JTable tabla = new JTable();
    private Object[][] data = new Object[0][0];
    private String[] columnNames = {"Codigo_ID", "sNombre", "apPaterno", "apMaterno", "dtNacimiento", "Materia_ID", "Carrera"};;
    private JScrollPane scrollPane = new JScrollPane();
    private DefaultTableModel model;

    private JTable tabla2 = new JTable();
    private JScrollPane scrollPane2 = new JScrollPane();
    private DefaultTableModel model2;
    private JTable tabla3 = new JTable();
    private JScrollPane scrollPane3 = new JScrollPane();
    private DefaultTableModel model3;

    public VentanaF2(EstudianteDto estudiante) {
        this.estudiante = estudiante;
        setTitle("Formulario Materia");
        setSize(294,382);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pack();
        setLocationRelativeTo(null);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Option");
        JMenuItem menuItem = new JMenuItem("Cerrar Sesion");
        menuItem.addActionListener(e ->{
            new VentanaF();
            this.dispose();
//            cerrarV();
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Salir");
        menuItem.addActionListener(e -> {
            logger.info("El usuario sale del programa");
            System.exit(0);
        });
        menu.add(menuItem);
        bar.add(menu);
        this.setJMenuBar(bar);

        initInformation();
        this.setVisible(true);
//        addComponentListener( new MyComponentAdapter());
        repaint();
    }
    private void cerrarV() {
        // Show the Ventana window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaF();
            }
        });

        // Close the current Materia window
        dispose();
    }

    private class MyComponentAdapter extends ComponentAdapter {
        @Override
        public void componentShown(ComponentEvent e) {
            if (e.getComponent() == panel_principal) {
                setSize(500, 500);
            } else if (e.getComponent() == panel_informacion) {
                setSize(260, 220);
            }
        }
    }
    public void initInformation(){
        panel_informacion.setLayout(null);
        panel_informacion.setBackground(Color.gray);
//        codigoIDTextField = new JTextField(10);
//        nombreTextField = new JTextField(10);
//        apPaternoTextField = new JTextField(10);
        JLabel tituloLabel = new JLabel("Informacion Estudiantil");
        JLabel codigoIDLabel = new JLabel("CodigoID:");
        JLabel nombreLabel = new JLabel("Nombre:");
        JLabel apPaternoLabel = new JLabel("ApPaterno:");
        JLabel apMaternoLabel = new JLabel("ApMaterno:");
        JLabel dtNacimientoLabel = new JLabel("dtNacimiento:");
        JLabel materiaIDLabel = new JLabel("MateriaID:");
        JLabel carreraLabel = new JLabel("Carrera:");

        JButton btnInicio1 = new JButton("Inicio");
        JButton btnPerfil1 = new JButton("Perfil");

//        PersonaDao dao = new PersonaDao();
//        PersonaDto estudiante = dao.getById(Integer.parseInt(codigoIDTextField.getText()));

        tituloLabel.setBounds(70,18,150,30);
        codigoIDLabel.setBounds(50,60,80,25);
        codigoIDTextField.setBounds(135,60,80,25);
        nombreLabel.setBounds(50,90,80,25);
        nombreTextField.setBounds(135,90,80,25);
        apPaternoLabel.setBounds(50,120,80,25);
        apPaternoTextField.setBounds(135,120,80,25);
        apMaternoLabel.setBounds(50,152,80,25);
        apMaternoTextField.setBounds(135,151,80,25);
        dtNacimientoLabel.setBounds(50,181,80,25);
        dtNacimientoTextField.setBounds(135,181,80,25);
        materiaIDLabel.setBounds(50,211,80,25);
        materiaIDTextField.setBounds(135,211,80,25);
        carreraLabel.setBounds(50,241,80,25);
        carreraTextField.setBounds(135,241,80,25);

        btnInicio1.setBounds(73,274,60,25);
        btnPerfil1.setBounds(143,274,60,25);

        codigoIDTextField.setOpaque(false);
        nombreTextField.setOpaque(false);
        apPaternoTextField.setOpaque(false);
        apMaternoTextField.setOpaque(false);
        dtNacimientoTextField.setOpaque(false);
        materiaIDTextField.setOpaque(false);
        carreraTextField.setOpaque(false);
//        nombreTextField.setForeground();
        btnPerfil1.setBorder(new LineBorder(new Color(213, 201, 4)));
        btnInicio1.setMargin(new Insets(0,0,0,0));
        btnPerfil1.setMargin(new Insets(0,0,0,0));


        codigoIDTextField.setText(String.valueOf(estudiante.getCodigoId()));
        nombreTextField.setText(estudiante.getNombre());
        apPaternoTextField.setText(String.valueOf(estudiante.getApellidoPaterno()));
        apMaternoTextField.setText(String.valueOf(estudiante.getApellidoMaterno()));
        dtNacimientoTextField.setText(estudiante.getFechaNacimiento());
        materiaIDTextField.setText(String.valueOf(estudiante.getMateriaId()));
        carreraTextField.setText(String.valueOf(estudiante.getCarrera()));


        codigoIDTextField.setEnabled(false);
        nombreTextField.setEnabled(false);
        apPaternoTextField.setEnabled(false);
        apMaternoTextField.setEnabled(false);
        dtNacimientoTextField.setEnabled(false);
        materiaIDTextField.setEnabled(false);
        carreraTextField.setEnabled(false);

        panel_informacion.add(tituloLabel);
        panel_informacion.add(codigoIDLabel);
        panel_informacion.add(codigoIDTextField);
        panel_informacion.add(nombreLabel);
        panel_informacion.add(nombreTextField);
        panel_informacion.add(apPaternoLabel);
        panel_informacion.add(apPaternoTextField);
        panel_informacion.add(apMaternoLabel);
        panel_informacion.add(apMaternoTextField);
        panel_informacion.add(dtNacimientoLabel);
        panel_informacion.add(dtNacimientoTextField);
        panel_informacion.add(materiaIDLabel);
        panel_informacion.add(materiaIDTextField);
        panel_informacion.add(carreraLabel);
        panel_informacion.add(carreraTextField);

        panel_informacion.add(btnInicio1);
        panel_informacion.add(btnPerfil1);

        this.add(panel_informacion);

        btnInicio1.addActionListener(e ->{
            JOptionPane.showMessageDialog(rootPane, "ventana de inicio");
            panel_informacion.setVisible(false);
            panel_principal.setVisible(true);
            initTable();
            // Cambiar el tamaño del frame
            setSize(940, 520);
            this.repaint();
        });
        btnPerfil1.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "ventana perfil");

        });

//        panel_informacion.addComponentListener(new MyComponentAdapter());
    }

    public void initTable(){
        panel_principal.setLayout(null);
        panel_principal.setBackground(Color.gray);

        JLabel lbTitulo = new JLabel("Tabla General");

        JButton btnInicio2 = new JButton("Inicio");
        JButton btnPerfil2 = new JButton("Perfil");


        lbTitulo.setBounds(315,1,80,25);


        btnInicio2.setBounds(285,420,60,25);
        btnPerfil2.setBounds(360,420,60,25);

        btnInicio2.setOpaque(false);
        btnInicio2.setBorder(new LineBorder(new Color(213, 201, 4)));
        btnPerfil2.setOpaque(true);

        btnInicio2.setMargin(new Insets(0,0,0,0));
        btnPerfil2.setMargin(new Insets(0,0,0,0));


        panel_principal.add(lbTitulo);
        panel_principal.add(btnInicio2);
        panel_principal.add(btnPerfil2);
        this.add(panel_principal);

        btnInicio2.addActionListener(e ->{
            JOptionPane.showMessageDialog(rootPane, "ventana de inicio");
        });
        btnPerfil2.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "ventana perfil");
            panel_principal.setVisible(false);
            panel_informacion.setVisible(true);
            initInformation();
        });


        JButton btnVerTabla = new JButton("Ver Tabla");
        btnVerTabla.setBounds(315, 385, 75, 28);
        btnVerTabla.setMargin(new Insets(0,0,0,0));
        btnVerTabla.setToolTipText("(2) ver la lista de estudiantes registrados");


        JButton btnVerTabla2 = new JButton("Opcion 1");
        btnVerTabla2.setBounds(610, 385, 70, 28);
        btnVerTabla2.setMargin(new Insets(0,0,0,0));
        btnVerTabla2.setToolTipText("(1) Muestra tabla Estudiante y registro de Estudiante,Materia,Notas");
        JButton btnVerTabla3 = new JButton("Opcion 2");
        btnVerTabla3.setBounds(685, 385, 70, 28);
        btnVerTabla3.setMargin(new Insets(0,0,0,0));
        btnVerTabla3.setToolTipText("(3) ver las materias en las que esta inscrito un estudiante especifico");


        JLabel codigoIDLabel = new JLabel("CodigoID:");
        JLabel nombreLabel = new JLabel("Nombre:");
        JLabel apPaternoLabel = new JLabel("ApPaterno:");
        JLabel apMaternoLabel = new JLabel("ApMaterno:");
        JLabel dtNacimientoLabel = new JLabel("dtNacimiento:");
        JLabel materiaIDLabel = new JLabel("MateriaID:");
        JLabel carreraLabel = new JLabel("Carrera:");

        JLabel notaIDLabel = new JLabel("NotaID:");
        JLabel tNotaLabel = new JLabel("tNota:");
        JButton btnNuevo2 = new JButton("Registrar");

        JTextField codigoIDTextField = new JTextField();
        JTextField nombreTextField = new JTextField();
        JTextField apPaternoTextField = new JTextField();
        JTextField apMaternoTextField = new JTextField();
        JTextField dtNacimientoTextField = new JTextField();
        JTextField materiaIDTextField = new JTextField();
        JTextField carreraTextField = new JTextField();

        JTextField notaIDTextField = new JTextField();
        JTextField tNotaTextField = new JTextField();

        JButton btnEliminar = new JButton("eliminar");
        JButton btnModifcar = new JButton("guardar Mod.");
        JButton btnNuevo = new JButton("Guardar");
        JButton btnReset = new JButton("//");
        JButton btnReset2 = new JButton("Reset");

        btnReset2.setBounds(390,24,28,18);
        btnReset2.setContentAreaFilled(false);
        btnReset2.setMargin(new Insets(0,0,0,0));
        panel_principal.add(btnReset2);

        panel_principal.add(btnVerTabla);
        panel_principal.add(btnVerTabla2);
        panel_principal.add(btnVerTabla3);

        btnReset2.addActionListener(e -> {
            if (scrollPane != null && tabla != null && scrollPane2 != null && tabla2 != null && scrollPane3 != null && tabla3 != null) {
                scrollPane.setVisible(false);
                tabla.setVisible(false);
                this.panel_principal.remove(scrollPane);
                this.panel_principal.remove(tabla);
                scrollPane2.setVisible(false);
                tabla2.setVisible(false);
                this.panel_principal.remove(scrollPane2);
                this.panel_principal.remove(tabla2);
                scrollPane3.setVisible(false);
                tabla3.setVisible(false);
                this.panel_principal.remove(scrollPane3);
                this.panel_principal.remove(tabla3);
            }
            this.panel_principal.revalidate();
            this.panel_principal.repaint();
        });
        btnVerTabla.addActionListener(e -> {
            notaIDLabel.setVisible(false);
            notaIDTextField.setVisible(false);
            tNotaLabel.setVisible(false);
            tNotaTextField.setVisible(false);
            btnNuevo2.setVisible(false);

            this.scrollPane.setVisible(true);
            this.tabla.setVisible(true);

            this.scrollPane2.setVisible(false);
            this.tabla2.setVisible(false);
            this.scrollPane3.setVisible(false);
            this.tabla3.setVisible(false);
            this.panel_principal.remove(scrollPane2);
            this.panel_principal.remove(tabla2);
            this.panel_principal.remove(scrollPane3);
            this.panel_principal.remove(tabla3);
            this.panel_principal.repaint();

            ListaDoble<EstudianteDto> listaEstudiantes = new ConsultaDao().getAllEstudiantes();
            if (listaEstudiantes != null) {
                data = new Object[listaEstudiantes.tamano()][7];
                columnNames = new String[]{"Codigo_ID", "sNombre", "apPaterno", "apMaterno", "dtNacimiento", "Materia_ID", "Carrera"};
                Iterator<EstudianteDto> iterator = listaEstudiantes.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    EstudianteDto estudiante = iterator.next();
                    data[i][0] = estudiante.getCodigoId();
                    data[i][1] = estudiante.getNombre();
                    data[i][2] = estudiante.getApellidoPaterno();
                    data[i][3] = estudiante.getApellidoMaterno();
                    data[i][4] = estudiante.getFechaNacimiento();
                    data[i][5] = estudiante.getMateriaId();
                    data[i][6] = estudiante.getCarrera();
                    i++;
                }
                tabla = new JTable(data, columnNames);
                scrollPane = new JScrollPane(tabla);
                scrollPane.setBounds(20, 145, 672, 230);

                codigoIDTextField.setOpaque(false);
                nombreTextField.setOpaque(false);
                apPaternoTextField.setOpaque(false);
                apMaternoTextField.setOpaque(false);
                dtNacimientoTextField.setOpaque(false);
                materiaIDTextField.setOpaque(false);
                carreraTextField.setOpaque(false);


                codigoIDLabel.setBounds(10,25,80,25);
                codigoIDTextField.setBounds(78,24,90,25);
                nombreLabel.setBounds(10,52,90,25);
                nombreTextField.setBounds(78,51,90,25);
                apPaternoLabel.setBounds(10,78,90,25);
                apPaternoTextField.setBounds(78,78,90,25);
                apMaternoLabel.setBounds(10,104,90,25);
                apMaternoTextField.setBounds(78,106,90,25);

                dtNacimientoLabel.setBounds(180,25,90,25);
                dtNacimientoTextField.setBounds(262,24,90,25);
                materiaIDLabel.setBounds(180,52,90,25);
                materiaIDTextField.setBounds(262,51,90,25);
                carreraLabel.setBounds(180,78,90,25);
                carreraTextField.setBounds(262,78,90,25);

                btnNuevo.setBounds(630, 30, 75, 25);
                btnEliminar.setBounds(630, 58, 75, 25);
                btnModifcar.setBounds(630, 86, 75, 25);
                btnReset.setBounds(360,24,18,18);

                codigoIDTextField.setOpaque(false);
                nombreTextField.setOpaque(false);
                apPaternoTextField.setOpaque(false);

                btnNuevo.setContentAreaFilled(false);
                btnNuevo.setMargin(new Insets(0,0,0,0));
                btnEliminar.setContentAreaFilled(false);
                btnEliminar.setForeground(new Color(211, 30, 30, 255));
                btnEliminar.setBorder(new LineBorder(new Color(211, 30, 30, 255)));
                btnEliminar.setMargin(new Insets(0,0,0,0));
                btnModifcar.setContentAreaFilled(false);
                btnModifcar.setForeground(new Color(71, 175, 3, 255));
                btnModifcar.setBorder(new LineBorder(new Color(71, 175, 3, 255)));
                btnModifcar.setMargin(new Insets(0,0,0,0));
                btnReset.setContentAreaFilled(false);
                btnReset.setMargin(new Insets(0,0,0,0));

                panel_principal.add(codigoIDLabel);
                panel_principal.add(codigoIDTextField);
                panel_principal.add(nombreLabel);
                panel_principal.add(nombreTextField);
                panel_principal.add(apPaternoLabel);
                panel_principal.add(apPaternoTextField);
                panel_principal.add(apMaternoLabel);
                panel_principal.add(apMaternoTextField);
                panel_principal.add(dtNacimientoLabel);
                panel_principal.add(dtNacimientoTextField);
                panel_principal.add(materiaIDLabel);
                panel_principal.add(materiaIDTextField);
                panel_principal.add(carreraLabel);
                panel_principal.add(carreraTextField);

                panel_principal.add(btnNuevo);
                panel_principal.add(btnEliminar);
                panel_principal.add(btnModifcar);
                panel_principal.add(btnReset);


                model = new DefaultTableModel(data, columnNames);
                tabla.setModel(model);
                tabla.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int row = tabla.getSelectedRow();
                        if (row >= 0) {
                            codigoIDTextField.setText(String.valueOf(tabla.getValueAt(row, 0)));
                            nombreTextField.setText(String.valueOf(tabla.getValueAt(row, 1)));
                            apPaternoTextField.setText(String.valueOf(tabla.getValueAt(row, 2)));

                            apMaternoTextField.setText(String.valueOf(tabla.getValueAt(row, 3)));
                            dtNacimientoTextField.setText(String.valueOf(tabla.getValueAt(row, 4)));
                            materiaIDTextField.setText(String.valueOf(tabla.getValueAt(row, 5)));
                            carreraTextField.setText(String.valueOf(tabla.getValueAt(row, 6)));
                        }
                    }
                });
                btnNuevo.addActionListener(ee -> {
                    String idText = codigoIDTextField.getText();
                    String nombre = nombreTextField.getText();
                    String appaterno = apPaternoTextField.getText();
                    String apmaterno = apMaternoTextField.getText();
                    String dtNacimiento = dtNacimientoTextField.getText();
                    String materiaId = materiaIDTextField.getText();
                    String carrera = carreraTextField.getText();

                    if (idText.isEmpty() || nombre.isEmpty() || appaterno.isEmpty() || dtNacimiento.isEmpty() || materiaId.isEmpty() || carrera.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos para guardar el registro.");
                        return;
                    }

                        EstudianteDto nuevo = new EstudianteDto(idText, nombre, appaterno, apmaterno, dtNacimiento, materiaId, carrera);
                        new ConsultaDao().insert(nuevo);

                        codigoIDTextField.setText("");
                        nombreTextField.setText("");
                        apPaternoTextField.setText("");

                });

                btnEliminar.addActionListener(ee -> {
                    int row = tabla.getSelectedRow();
                    if (row >= 0) {
                        String id = codigoIDTextField.getText();
                        new ConsultaDao().eliminarEstudiante(id);

                        // Success: Remove the row from the table
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(rootPane, "Registro eliminado exitosamente.");
                        codigoIDTextField.setText("");
                        nombreTextField.setText("");
                        apPaternoTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Seleccione un registro para eliminar.");
                    }
                });

                btnModifcar.addActionListener(ee -> {

                });
                btnReset.addActionListener(e1 -> {
                    codigoIDTextField.setText("");
                    nombreTextField.setText("");
                    apPaternoTextField.setText("");
                    apMaternoTextField.setText("");
                    dtNacimientoTextField.setText("");
                    materiaIDTextField.setText("");
                    carreraTextField.setText("");

                    notaIDTextField.setText("");
                    tNotaTextField.setText("");
                });

            } else {
                JOptionPane.showMessageDialog(rootPane, "No hay registros en la tabla estudiante.");
            }

            panel_principal.add(scrollPane);
            this.panel_principal.revalidate();

        });
        btnVerTabla2.addActionListener(e -> {
            notaIDLabel.setVisible(true);
            notaIDTextField.setVisible(true);
            tNotaLabel.setVisible(true);
            tNotaTextField.setVisible(true);
            btnNuevo2.setVisible(true);

            this.scrollPane.setVisible(false);
            this.tabla.setVisible(false);
            this.panel_principal.remove(scrollPane);
            this.panel_principal.remove(tabla);

            this.scrollPane2.setVisible(true);
            this.tabla2.setVisible(true);
            this.scrollPane3.setVisible(true);
            this.tabla3.setVisible(true);
            this.panel_principal.repaint();

            ListaDoble<EstudianteDto> listaestudianteDto = new ConsultaDao().getAllEstudiantes();
            ListaDoble<EstudianteNotaDto> listaestudianteNotaDto = new ConsultaDao().getAllEstudiantesNotas();
            if (listaestudianteDto != null && listaestudianteNotaDto != null){
                Object[][] data = new Object[listaestudianteDto.tamano()][6];
                String[] columnNames = {"Codigo_ID", "sNombre", "apPaterno", "dtNacimiento", "Materia_ID", "Carrera"};
                Iterator<EstudianteDto> iterator = listaestudianteDto.iterator();
                Object[][] data2 = new Object[listaestudianteNotaDto.tamano()][4];
                String[] columnNames2 = {"estudianteID", "notaID", "tnotas", "materiaID"};
                Iterator<EstudianteNotaDto> iterator2 = listaestudianteNotaDto.iterator();
                int i2 = 0;
                int i = 0;
                while (iterator.hasNext()) {
                    EstudianteDto estudiante = iterator.next();
                    data[i][0] = estudiante.getCodigoId();
                    data[i][1] = estudiante.getNombre();
                    data[i][2] = estudiante.getApellidoPaterno();
                    data[i][3] = estudiante.getFechaNacimiento();
                    data[i][4] = estudiante.getMateriaId();
                    data[i][5] = estudiante.getCarrera();
                    i++;
                }
                while (iterator2.hasNext()) {
                    EstudianteNotaDto estudianteNotas = iterator2.next();
                    data2[i2][0] = estudianteNotas.getEstudianteId();
                    data2[i2][1] = estudianteNotas.getNotaId();
                    data2[i2][2] = estudianteNotas.getTotalNotas();
                    data2[i2][3] = estudianteNotas.getMateriaId();
                    i2++;
                }
                tabla2 = new JTable(data, columnNames);
                scrollPane2 = new JScrollPane(tabla2);
                scrollPane2.setBounds(15, 150, 535, 230);

                tabla3 = new JTable(data2, columnNames2);
                scrollPane3 = new JScrollPane(tabla3);
                scrollPane3.setBounds(560, 150, 350, 200);

                panel_principal.add(scrollPane2);
                this.panel_principal.revalidate();

                panel_principal.add(scrollPane3);
                this.panel_principal.revalidate();

                notaIDLabel.setBounds(182,105,90,25);
                notaIDTextField.setBounds(262,105,90,25);
                tNotaLabel.setBounds(360,105,90,25);
                tNotaTextField.setBounds(395,105,90,25);
                notaIDTextField.setOpaque(false);
                tNotaTextField.setOpaque(false);

                panel_principal.add(notaIDLabel);
                panel_principal.add(notaIDTextField);
                panel_principal.add(tNotaLabel);
                panel_principal.add(tNotaTextField);

                model2 = new DefaultTableModel(data, columnNames);
                tabla2.setModel(model2);
                tabla2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int row = tabla2.getSelectedRow();
                        if (row >= 0) {
                            codigoIDTextField.setText(String.valueOf(tabla2.getValueAt(row, 0)));
                            nombreTextField.setText(String.valueOf(tabla2.getValueAt(row, 1)));
                            apPaternoTextField.setText(String.valueOf(tabla2.getValueAt(row, 2)));

                            //apMaternoTextField.setText(String.valueOf(tabla2.getValueAt(row, 3)));
                            dtNacimientoTextField.setText(String.valueOf(tabla2.getValueAt(row, 3)));
                            materiaIDTextField.setText(String.valueOf(tabla2.getValueAt(row, 4)));
                            carreraTextField.setText(String.valueOf(tabla2.getValueAt(row, 5)));
                        }
                    }
                });
                model3 = new DefaultTableModel(data2, columnNames2);
                tabla3.setModel(model3);
                tabla3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int row = tabla3.getSelectedRow();
                        if (row >= 0) {
                            codigoIDTextField.setText(String.valueOf(tabla3.getValueAt(row, 0)));
                            notaIDTextField.setText(String.valueOf(tabla3.getValueAt(row, 1)));
                            tNotaTextField.setText(String.valueOf(tabla3.getValueAt(row, 2)));
                            materiaIDTextField.setText(String.valueOf(tabla3.getValueAt(row, 3)));
                        }
                    }
                });


                btnNuevo2.setBounds(630, 115, 100, 25);
                btnNuevo2.setContentAreaFilled(false);
                btnNuevo2.setMargin(new Insets(0,0,0,0));
                btnNuevo2.setToolTipText("(1) Registrar un nuevo estudiante junto con sus materias inscritas y notas");
                panel_principal.add(btnNuevo2);

                btnNuevo2.addActionListener(ee -> {
                    String idText = codigoIDTextField.getText();
                    String nombre = nombreTextField.getText();
                    String appaterno = apPaternoTextField.getText();
                    String apmaterno = apMaternoTextField.getText();
                    String dtNacimiento = dtNacimientoTextField.getText();
                    String materiaId = materiaIDTextField.getText();
                    String carrera = carreraTextField.getText();

                    String notaId = notaIDTextField.getText();
                    String tnota = tNotaTextField.getText();

                    if (idText.isEmpty() || nombre.isEmpty() || appaterno.isEmpty() || dtNacimiento.isEmpty() || materiaId.isEmpty() || carrera.isEmpty()
                            || notaId.isEmpty() || tnota.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos para guardar el registro.");
                        return;
                    }

                    ConsultaDao consultaDao = new ConsultaDao();
                    consultaDao.registrarNuevoEstudiante2(idText, nombre, appaterno, dtNacimiento, carrera,
                            new String[]{notaId}, new int[]{Integer.parseInt(tnota)}, new String[]{materiaId});

                    codigoIDTextField.setText("");
                    nombreTextField.setText("");
                    apPaternoTextField.setText("");
                    codigoIDTextField.setText("");
                    nombreTextField.setText("");
                    apPaternoTextField.setText("");
                    apMaternoTextField.setText("");
                    dtNacimientoTextField.setText("");
                    materiaIDTextField.setText("");
                    carreraTextField.setText("");
                    notaIDTextField.setText("");
                    tNotaTextField.setText("");
                });
            }
            panel_principal.add(scrollPane2);
            this.panel_principal.revalidate();
            panel_principal.add(scrollPane3);
            this.panel_principal.revalidate();

            this.panel_principal.repaint();
            /**panel_principal.setVisible(false);
            panel_principal2.setVisible(true);
            initTable2();
            this.repaint();*/

        });
        btnVerTabla3.addActionListener(e -> {
            // Obtener el código ID del estudiante desde algún componente, por ejemplo, un campo de texto
            String codigoIdEstudiante = codigoIDTextField.getText();

            // Llamar al método getMateriasPorEstudiante() de ConsultaDao y obtener la lista de materias
            ListaDoble<MateriaDto> materias = new ConsultaDao().getMateriasPorEstudiante(codigoIdEstudiante);

            // Mostrar las materias en algún componente, como una tabla
            // Por ejemplo, puedes iterar sobre la lista de materias y mostrarlas en un JOptionPane
            StringBuilder mensaje = new StringBuilder("Materias del estudiante:\n");
            for (MateriaDto materia : materias) {
                mensaje.append(materia.getCodigoId()).append(" - ").append(materia.getNombre()).append("\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString());


        });

    }

    public void initTable2(){
        panel_principal2.setLayout(null);
        panel_principal2.setBackground(Color.gray);
        ListaDoble<EstudianteDto> listaestudianteDto = new ConsultaDao().getAllEstudiantes();
        ListaDoble<EstudianteNotaDto> listaestudianteNotaDto = new ConsultaDao().getAllEstudiantesNotas();
        if (listaestudianteDto != null && listaestudianteNotaDto != null){
            Object[][] data = new Object[listaestudianteDto.tamano()][6];
            String[] columnNames = {"Codigo_ID", "sNombre", "apPaterno", "dtNacimiento", "Materia_ID", "Carrera"};
            Iterator<EstudianteDto> iterator = listaestudianteDto.iterator();
            Object[][] data2 = new Object[listaestudianteNotaDto.tamano()][4];
            String[] columnNames2 = {"estudianteID", "notaID", "tnotas", "materiaID"};
            Iterator<EstudianteNotaDto> iterator2 = listaestudianteNotaDto.iterator();
            int i = 0;
            int i2 = 0;
            while (iterator.hasNext()) {
                EstudianteDto estudiante = iterator.next();
                data[i][0] = estudiante.getCodigoId();
                data[i][1] = estudiante.getNombre();
                data[i][2] = estudiante.getApellidoPaterno();
                data[i][3] = estudiante.getFechaNacimiento();
                data[i][4] = estudiante.getMateriaId();
                data[i][5] = estudiante.getCarrera();
                i++;
            }
            tabla2 = new JTable(data, columnNames);
            scrollPane2 = new JScrollPane(tabla2);
            scrollPane2.setBounds(15, 150, 530, 230);

            while (iterator2.hasNext()) {
                EstudianteNotaDto estudianteNotas = iterator2.next();
                data2[i2][0] = estudianteNotas.getEstudianteId();
                data2[i2][1] = estudianteNotas.getNotaId();
                data2[i2][2] = estudianteNotas.getTotalNotas();
                data2[i2][3] = estudianteNotas.getMateriaId();
                i2++;
            }
            tabla3 = new JTable(data2, columnNames2);
            scrollPane3 = new JScrollPane(tabla3);
            scrollPane3.setBounds(565, 150, 350, 200);

            panel_principal2.add(scrollPane2);
            this.panel_principal2.revalidate();

            panel_principal2.add(scrollPane3);
            this.panel_principal2.revalidate();
        }

        this.panel_principal2.repaint();
    }

}


package edu.nur.edd.dao;


import edu.nur.edd.listas.ListaDoble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class VentanaF extends JFrame {

    private JTextField idTextField;
    private JTextField nombreTextField;
    private JLabel noRegistLabel = new JLabel("No tienes cuenta? ");
    private JLabel registroLabel = new JLabel("<html><u>Registrate</u></html>"+"!");

    private JTextField idCodigoTextFieldRegistro = new JTextField();
    private JTextField snombreTextFieldRegistro = new JTextField();
    private JTextField appaternoTextFieldRegistro = new JTextField();
    private JTextField apmaternoTextFieldRegistro = new JTextField();
    private JTextField dtNacimientoTextFieldRegistro = new JTextField();
    private JTextField materiaIdTextFieldRegistro = new JTextField();
    private JTextField carreraTextFieldRegistro = new JTextField();

    private JPanel panel_principal = new JPanel();
    private JPanel panel_registro = new JPanel();
    public VentanaF() {
        setTitle("Ventana de ingreso");
        setSize(245,298);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponent();
        repaint();
        setVisible(true);
    }

    public void initComponent(){
        panel_principal.setLayout(null);
        panel_principal.setBackground(Color.gray);

        panel_principal.setLayout(null);

        idTextField = new JTextField();
        nombreTextField = new JTextField();
        JLabel titulolabel = new JLabel("INICIAR SESION");
        JLabel idLabel = new JLabel("ID:");
        JLabel nombreLabel = new JLabel("Nombre:");

        JButton ingresarButton = new JButton("Ingresar");

        titulolabel.setBounds(47,10,100,25);
        idLabel.setBounds(20,50,80,25);
        idTextField.setBounds(71,50,90,25);
        nombreLabel.setBounds(20,80,80,25);
        nombreTextField.setBounds(71,80,90,25);
        ingresarButton.setBounds(70,115,80,25);
        noRegistLabel.setBounds(5,210,110,20);
        registroLabel.setBounds(112,210,100,20);

        ingresarButton.setMargin(new Insets(0,0,0,0));
        ingresarButton.setOpaque(false);

        panel_principal.add(titulolabel);
        panel_principal.add(idLabel);
        panel_principal.add(idTextField);
        panel_principal.add(nombreLabel);
        panel_principal.add(nombreTextField);
        panel_principal.add(ingresarButton);
        panel_principal.add(noRegistLabel);
        panel_principal.add(registroLabel);
        this.add(panel_principal);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idTextField.getText();
                String nombre = nombreTextField.getText();

                if (idText.isEmpty() || nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "Debes ingresar el ID y el Nombre.", "Error en el inicio de sesión", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(rootPane, "El ID debe ser un número válido.", "Error en el inicio de sesión", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (verificarInicioSesion()) {
                    JOptionPane.showMessageDialog(rootPane, "Ingreso Exitoso!!", "Válido", JOptionPane.INFORMATION_MESSAGE);
                    mostrarFormularioMateria();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Datos incorrectos", "Error en el inicio de sesión", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel_principal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(e.getX() > registroLabel.getX() && e.getX() < registroLabel.getX() + registroLabel.getWidth()
                        && e.getY() > registroLabel.getY() && e.getY() < registroLabel.getY() + registroLabel.getHeight()){
                    JOptionPane.showMessageDialog(rootPane, "click al label");
                    panel_principal.setVisible(false);
                    panel_registro.setVisible(true);
                    idCodigoTextFieldRegistro.setText("");
                    snombreTextFieldRegistro.setText("");
                    appaternoTextFieldRegistro.setText("");
                    apmaternoTextFieldRegistro.setText("");
                    dtNacimientoTextFieldRegistro.setText("");
                    materiaIdTextFieldRegistro.setText("");
                    carreraTextFieldRegistro.setText("");
                    initRegistr();
                    repaint();
                }
            }
        });
        repaint();
    }

    private void initRegistr() {
        panel_registro.setLayout(null);
        panel_registro.setBackground(Color.gray);

        JPanel camposRegistroPanel = new JPanel();
        camposRegistroPanel.setLayout(null);

        JLabel tituloLabel = new JLabel("Registro N-Estudiante");
        JLabel codigoIdLabel = new JLabel("CodigoID:");
        JLabel sNombreLabel = new JLabel("sNombre:");
        JLabel appaternoLabel = new JLabel("apPaterno:");
        JLabel apmaternoLabel = new JLabel("apMaterno:");
        JLabel dtNacimientoLabel = new JLabel("dtNacimiento:");
        JLabel materiaIdLabel = new JLabel("MateriaID:");
        JLabel carreraLabel = new JLabel("Carrera:");


        JButton atrasButton = new JButton("Atrás");
        JButton guardarButton = new JButton("Guardar");

        tituloLabel.setBounds(84, 4, 80, 25);
        codigoIdLabel.setBounds(20, 30, 80, 25);
        idCodigoTextFieldRegistro.setBounds(92, 30, 90, 25);
        sNombreLabel.setBounds(20, 60, 80, 25);
        snombreTextFieldRegistro.setBounds(92, 60, 90, 25);
        appaternoLabel.setBounds(20, 90, 80, 25);
        appaternoTextFieldRegistro.setBounds(92, 90, 90, 25);
        apmaternoLabel.setBounds(20, 120, 80, 25);
        apmaternoTextFieldRegistro.setBounds(92, 120, 90, 25);
        dtNacimientoLabel.setBounds(20, 150, 80, 25);
        dtNacimientoTextFieldRegistro.setBounds(92, 150, 90, 25);
        materiaIdLabel.setBounds(20, 180, 80, 25);
        materiaIdTextFieldRegistro.setBounds(92, 180, 90, 25);
        carreraLabel.setBounds(20, 210, 80, 25);
        carreraTextFieldRegistro.setBounds(92, 210, 90, 24);

        atrasButton.setBounds(20, 240, 75, 25);
        guardarButton.setBounds(140, 240, 75, 25);

        panel_registro.add(codigoIdLabel);
        panel_registro.add(idCodigoTextFieldRegistro);
        panel_registro.add(sNombreLabel);
        panel_registro.add(snombreTextFieldRegistro);
        panel_registro.add(appaternoLabel);
        panel_registro.add(appaternoTextFieldRegistro);
        panel_registro.add(apmaternoLabel);
        panel_registro.add(apmaternoTextFieldRegistro);
        panel_registro.add(dtNacimientoLabel);
        panel_registro.add(dtNacimientoTextFieldRegistro);
        panel_registro.add(materiaIdLabel);
        panel_registro.add(materiaIdTextFieldRegistro);
        panel_registro.add(carreraLabel);
        panel_registro.add(carreraTextFieldRegistro);
        panel_registro.add(atrasButton);
        panel_registro.add(guardarButton);

        this.add(panel_registro);

        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_principal.setVisible(true);
                panel_registro.setVisible(false);
                repaint();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idCodigoTextFieldRegistro.getText();
                String nombre = snombreTextFieldRegistro.getText();
                String appaternoText = appaternoTextFieldRegistro.getText();
                String apmaterno = apmaternoTextFieldRegistro.getText();
                String dtNacimiento = dtNacimientoTextFieldRegistro.getText();
                String materiaId = materiaIdTextFieldRegistro.getText();
                String carrera = carreraTextFieldRegistro.getText();

                if (idText.isEmpty() || nombre.isEmpty() || appaternoText.isEmpty() || dtNacimiento.isEmpty() || carrera.isEmpty() ) {
                    JOptionPane.showMessageDialog(panel_registro, "Necesita ingresar todos los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {


                ConsultaDao dao = new ConsultaDao();
                if (!dao.idYaExiste(idText)){
                    //JOptionPane.showMessageDialog(panel_registro, "El ID ingresado ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("El ID ingresado ya está registrado.");
                    idCodigoTextFieldRegistro.setText("");
                } else {
                    EstudianteDto persona = new EstudianteDto(idText, nombre, appaternoText, apmaterno, dtNacimiento, materiaId, carrera);
                    dao.insert(persona);
                    System.out.println("Estudiante registrado exitosamente.");
                }

//                    atrasButton.doClick(); // Go back to the previous panel after saving the data

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Error en el formato de los campos ID o Altura.");
                }
            }
        });

        repaint();
    }

    public boolean verificarInicioSesion() {
        String id = idTextField.getText();
        String nombre = nombreTextField.getText().toLowerCase();

        ConsultaDao dao = new ConsultaDao();
        return dao.verificarIniciarSesion(id, nombre);
    }
    public void mostrarFormularioMateria() {
        String id = idTextField.getText();
        String nombre = nombreTextField.getText();

        ConsultaDao dao = new ConsultaDao();
        EstudianteDto persona = dao.getById(id);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JTextField alturaTextField = new JTextField(10);
                VentanaF2 ventana2 = new VentanaF2(persona);
                ventana2.setVisible(true);
                dispose();
            }
        });
    }
    public static void main(String[] args) {
        new VentanaF();
    }
}




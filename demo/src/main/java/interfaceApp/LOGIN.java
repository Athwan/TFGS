package interfaceApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import classes.*;
import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LOGIN extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JTextField txtRegUsuario;
    private JPasswordField txtRegContrasena;
    private JTextField txtEmail;
    private JButton btnRegistrar;
    private funcionesDB dbFun = new funcionesDB();
    
    public LOGIN() {
        setTitle("Gestión de Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout());

        // Panel de Login
        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createTitledBorder("Inicio de Sesión"));
        contentPane.add(loginPanel, "LoginPanel");
        loginPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	}
        });
        lblUsuario.setBackground(new Color(255, 255, 255));
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(lblUsuario);

        txtUsuario = new JTextField();
        loginPanel.add(txtUsuario);
        txtUsuario.setColumns(10);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(lblContrasena);

        txtContrasena = new JPasswordField();
        loginPanel.add(txtContrasena);
        txtContrasena.setColumns(10);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(lblRol);

        String[] roles = {"Usuario", "Administrador"};
        JComboBox<String> comboRol = new JComboBox<>(roles);
        comboRol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboRol.getSelectedItem().equals("Administrador")) {
                    btnRegistrar.setEnabled(false);
                } else {
                    btnRegistrar.setEnabled(true);
                }
            }
        });
        loginPanel.add(comboRol);

        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(new Color(70, 130, 180));
        btnIniciarSesion.setForeground(Color.WHITE);
        loginPanel.add(btnIniciarSesion);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(60, 179, 113));
        btnRegistrar.setForeground(Color.WHITE);
        loginPanel.add(btnRegistrar);

        // Panel de Registro
        JPanel registerPanel = new JPanel();
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registro de Usuario"));
        contentPane.add(registerPanel, "RegisterPanel");
        registerPanel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblRegUsuario = new JLabel("Usuario:");
        registerPanel.add(lblRegUsuario);

        txtRegUsuario = new JTextField();
        registerPanel.add(txtRegUsuario);
        txtRegUsuario.setColumns(10);

        JLabel lblRegContrasena = new JLabel("Contraseña:");
        registerPanel.add(lblRegContrasena);

        txtRegContrasena = new JPasswordField();
        registerPanel.add(txtRegContrasena);
        txtRegContrasena.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        registerPanel.add(lblEmail);

        txtEmail = new JTextField();
        registerPanel.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblRegRol = new JLabel("Rol:");
        registerPanel.add(lblRegRol);

        JComboBox<String> comboRegRol = new JComboBox<>(roles);
        registerPanel.add(comboRegRol);


        JButton btnCambiarALogin = new JButton("Iniciar Sesión");
        btnCambiarALogin.setBackground(new Color(34, 139, 34));
        btnCambiarALogin.setForeground(Color.WHITE);
        registerPanel.add(btnCambiarALogin);

        // Action Listeners
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro(); // Crear una instancia de la ventana de Registro
                registro.setVisible(true); // Mostrar la ventana de Registro
            }
        });

        btnCambiarALogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) contentPane.getLayout();
                cl.show(contentPane, "LoginPanel");
            }
        });

        btnIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());
                String rol = (String) comboRol.getSelectedItem();
                
                // Lógica de autenticación     
                if (rol.equals("Administrador") && usuario.equals("superAdmin")) {
                    VentanaPrincipal mainFrame = new VentanaPrincipal("Administrador");
                    mainFrame.setVisible(true); // Mostrar la ventana
                    dispose();
                } 
            	
                else if (rol.equals("Administrador")){
                // Mostrar mensaje de error si el usuario no es "superAdmin"
                JOptionPane.showMessageDialog(null, "Nombre de usuario incorrecto para administrador", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                }
                
                else {
                    // aquí va la lógica para comprobar la autenticación de usuario normal
                	if(dbFun.existePersonaPorDNI(usuario))
                	{
                        VentanaPrincipal mainFrame = new VentanaPrincipal("Usuario");
                        mainFrame.setVisible(true); // Mostrar la ventana
                        dispose();
                	}
                	else JOptionPane.showMessageDialog(null, "Nombre de usuario incorrecto", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LOGIN frame = new LOGIN();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    //funciones
    
}

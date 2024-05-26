package interfaceApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro extends JFrame {

    private JPanel contentPane;
    private JTextField txtDNI;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtTelefono;
    private JPasswordField txtContrasena;
    private funcionesDB dbFun = new funcionesDB();

    public Registro() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setBounds(5, 6, 207, 33);
        contentPane.add(lblDNI);

        txtDNI = new JTextField();
        txtDNI.setBounds(222, 6, 207, 33);
        contentPane.add(txtDNI);
        txtDNI.setColumns(10);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(5, 49, 207, 33);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(222, 49, 207, 33);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(5, 92, 207, 33);
        contentPane.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(222, 92, 207, 33);
        contentPane.add(txtApellidos);
        txtApellidos.setColumns(10);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(5, 135, 207, 33);
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(222, 135, 207, 33);
        contentPane.add(txtTelefono);
        txtTelefono.setColumns(10);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(5, 178, 207, 33);
        contentPane.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(222, 178, 207, 33);
        contentPane.add(txtContrasena);
        txtContrasena.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(222, 228, 207, 22);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes acceder a los valores ingresados por el usuario
                String dni = txtDNI.getText();
                String nombre = txtNombre.getText();
                String apellidos = txtApellidos.getText();
                int telefono = Integer.parseInt(txtTelefono.getText());
                String contrasena = new String(txtContrasena.getPassword());
                
            	if(dbFun.esDNICorrecto(dni) && !dbFun.existePersonaPorDNI(dni)) {
                    dbFun.insertarPersona(dni, nombre, apellidos, telefono, dbFun.encryptPassword(contrasena));
                    JOptionPane.showMessageDialog(null, "Registro exitoso.");
                    dispose(); 
            	}
                else JOptionPane.showMessageDialog(null, "DNI incorrecto o ya existente.", "Error a la hora de registrar DNI", JOptionPane.ERROR_MESSAGE);
            }//actionEvent	
        });
        contentPane.add(btnRegistrar);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Registro frame = new Registro();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

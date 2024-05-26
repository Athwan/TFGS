package interfaceApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Intermediario extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombre;
	private funcionesDB dbFun = new funcionesDB();

    public Intermediario() {
        setTitle("Registro de Intermediario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre del Intermediario:");
        lblNombre.setBounds(5, 49, 207, 33);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(222, 49, 207, 33);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(222, 100, 207, 22);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                
                if (!nombre.isEmpty() && !dbFun.existeIntermediario(nombre)) {
                    dbFun.insertarIntermediario(nombre);
                    // Aquí puedes agregar la lógica para manejar el registro del intermediario
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Comprueba de nuevo el nombre.", "Error insertando un intermediario", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(btnRegistrar);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Intermediario frame = new Intermediario();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


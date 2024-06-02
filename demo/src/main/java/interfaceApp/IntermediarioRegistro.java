package interfaceApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntermediarioRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombre;
	private funcionesDB dbFun = new funcionesDB();

    public IntermediarioRegistro() {
        setTitle("Registro de Intermediario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 418, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre del Intermediario");
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombre.setBounds(12, 56, 160, 33);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(179, 57, 207, 33);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(93, 111, 207, 22);
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
                    IntermediarioRegistro frame = new IntermediarioRegistro();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


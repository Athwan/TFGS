package interfaceApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Apartamento extends JFrame {

    private JPanel contentPane;
    private JTextField txtUbicacion;
    private funcionesDB dbFun;

    public Apartamento() {
    	
        dbFun = new funcionesDB();

        setTitle("Registro de Apartamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setBounds(5, 49, 207, 33);
        contentPane.add(lblUbicacion);

        txtUbicacion = new JTextField();
        txtUbicacion.setBounds(222, 49, 207, 33);
        contentPane.add(txtUbicacion);
        txtUbicacion.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(222, 92, 207, 22);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ubicacion = txtUbicacion.getText();
                
                if(!ubicacion.isEmpty() && !dbFun.existeApartamento(ubicacion)) {
                    dbFun.insertarApartamento(ubicacion);
                    dispose(); 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el apartamento.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(btnRegistrar);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Apartamento frame = new Apartamento();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

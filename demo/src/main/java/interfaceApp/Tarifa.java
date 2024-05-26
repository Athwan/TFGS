package interfaceApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tarifa extends JFrame {

    private JPanel contentPane;
    private JTextField txtTipo;
    private JTextField txtPrecioBase;
    private funcionesDB dbFun;

    public Tarifa() {
    	
        dbFun = new funcionesDB();

        setTitle("Registro de Propiedad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(5, 49, 207, 33);
        contentPane.add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(222, 49, 207, 33);
        contentPane.add(txtTipo);
        txtTipo.setColumns(10);

        JLabel lblPrecioBase = new JLabel("Precio Base:");
        lblPrecioBase.setBounds(5, 92, 207, 33);
        contentPane.add(lblPrecioBase);

        txtPrecioBase = new JTextField();
        txtPrecioBase.setBounds(222, 92, 207, 33);
        contentPane.add(txtPrecioBase);
        txtPrecioBase.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(222, 135, 207, 22);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipo = txtTipo.getText();
                String precioBaseStr = txtPrecioBase.getText();
                try {
                    double precioBase = Double.parseDouble(precioBaseStr);
                    
                    if((!tipo.isEmpty() && !precioBaseStr.isEmpty()) && !dbFun.existeTarifa(precioBaseStr))
                    {
                        dbFun.insertarTarifa(tipo, precioBase);
                        dispose(); 
                    }
                    else	JOptionPane.showMessageDialog(null, "Ha habido un error insertando un Intermediario.", "Error en ingreso de datos.", JOptionPane.ERROR_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El precio base debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
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

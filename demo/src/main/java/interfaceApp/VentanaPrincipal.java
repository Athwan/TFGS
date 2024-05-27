package interfaceApp;
import javax.swing.border.EmptyBorder;

import db.funcionesDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    
    private JPanel contentPane;
    
    
    public VentanaPrincipal(String rol) {
        setTitle("Panel Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Crear el panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(rol.equals("Administrador") ? 7 : 4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear un panel superior con un título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout());
        JLabel titulo = new JLabel("Panel Principal - " + rol);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        panelTitulo.setBackground(new Color(51, 153, 255));
        panelTitulo.add(titulo);

        // Agregar el panel de título a la parte superior
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // Agregar botones al panel de botones
        if (rol.equals("Administrador")) {
            addButton(panelBotones, "Insertar Apartamento", e -> insertarApartamento());
            addButton(panelBotones, "Insertar Intermediario", e -> insertarIntermediario());
            addButton(panelBotones, "Insertar Tarifa", e -> insertarTarifa());
            addButton(panelBotones, "Insertar Gasto", e -> insertarGasto());
            addButton(panelBotones, "Insertar Ingreso", e -> insertarIngreso());
        }
        else {
        	
            addButton(panelBotones, "Listar Ingresos y Total", e -> listarIngresos());
            addButton(panelBotones, "Listar Gastos y Total", e -> listarGastos());
            addButton(panelBotones, "Liquidación Trimestral de IVA", e -> liquidacionIVA());
        }


        // Agregar el panel de botones al centro
        contentPane.add(panelBotones, BorderLayout.CENTER);

        // Botón de logout
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));
        btnLogout.setBackground(new Color(255, 51, 51));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Mostrar la ventana de login nuevamente
                new LOGIN().setVisible(true);
            }
        });
        
        // Panel para el botón de logout
        JPanel panelBotonLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonLogout.add(btnLogout);

        // Agregar el panel del botón de logout en la parte inferior
        contentPane.add(panelBotonLogout, BorderLayout.SOUTH);

        // Establecer colores y fuentes
        contentPane.setBackground(new Color(245, 245, 245));
        panelBotones.setBackground(new Color(255, 255, 255));
    }

    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        panel.add(button);
    }

    
    // Métodos para cada botón
    private void insertarApartamento() {
    	Apartamento apartamento = new Apartamento();
    	apartamento.setVisible(true);
    	//JOptionPane.showMessageDialog(this, "Insertar Apartamento - Función no implementada aún.");
    }

    private void insertarIntermediario() {
    	
    	Intermediario intermediario = new Intermediario(); // Crear una instancia de la ventana de Registro
    	intermediario.setVisible(true); // Mostrar la ventana de Registro
        //JOptionPane.showMessageDialog(this, "Insertar Intermediario - Función no implementada aún.");
    }

    private void insertarTarifa() {
    	Tarifa tarifa = new Tarifa();
    	tarifa.setVisible(true);
        //JOptionPane.showMessageDialog(this, "Insertar Tarifa - Función no implementada aún.");
    }

    private void insertarGasto() {
    	Gastos gastos = new Gastos();
    	gastos.setVisible(true);
        //JOptionPane.showMessageDialog(this, "Insertar Gasto - Función no implementada aún.");
    }

    private void insertarIngreso() {
    	Ingreso ingreso = new Ingreso();
    	ingreso.setVisible(true);
        //JOptionPane.showMessageDialog(this, "Insertar Ingreso - Función no implementada aún.");
    }

    
    
    ///////////////////////////////////////	USUARIO	/////////////////////////////////////////
    
    
    private void listarIngresos() {
        JOptionPane.showMessageDialog(this, "Listar Ingresos y Total - Función no implementada aún.");
    }

    private void listarGastos() {
    	   // Crear una instancia de ListarGastosPanel
        ListarGastosPanel gastos = new ListarGastosPanel();

        // Agregar ListarGastosPanel al contenido de JFrame
        getContentPane().removeAll(); // Limpiar contenido anterior
        getContentPane().add(gastos, BorderLayout.CENTER);

        // Validar y volver a dibujar JFrame
        revalidate();
        repaint();

        // Hacer visible el JFrame
        setVisible(true);
        //JOptionPane.showMessageDialog(this, "Listar Gastos y Total - Función no implementada aún.");
    }

    private void liquidacionIVA() {
        JOptionPane.showMessageDialog(this, "Liquidación Trimestral de IVA - Función no implementada aún.");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Cambiar "Usuario" por "Administrador" para probar la vista del administrador
                    VentanaPrincipal frame = new VentanaPrincipal("Usuario");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import classes.*;
import db.*;

public class App {
    public static Session theSession = HibernateUtil.getSessionFactory().getCurrentSession();

    public static void main(String[] args)
    {
        System.out.println("Hello, World!");
        
        funcionesDB dbFun = new funcionesDB();
        
        // PARA PONER A 0 EL AUTOINCREMENT
        // ALTER TABLE `apartamento` AUTO_INCREMENT = 1; 
        String apartamento = "1ap2";
        dbFun.insertarApartamento(apartamento);
        dbFun.insertarPersona("12345678S", "Pancracio", "de los Palotes", 123123321, "1234");
        dbFun.insertarIntermediario("Mano Manitas a domicilio");
        dbFun.insertarTarifa("tarifa Verano Mix 2000", 600.0);
        // sustituir el nombre del apartamento (al final) con el APARTAMENTEO existente
        dbFun.insertarGasto(apartamento, 0.21, 180.0, 150.0, "12345678S", "Mi Proveedor", "Compra de materiales", true);
        dbFun.insertarIngreso("12345678S", "Mano Manitas a domicilio", apartamento,
        		"tarifa Verano Mix 2000", 2, 4, 0.1, 1500.0, 1800.0,
        		"Observaciones de ingreso");

    }//main
    
}//mainClass

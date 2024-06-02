
package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import classes.*;
import db.*;
import interfaceApp.*;

public class App {
    public static Session theSession = HibernateUtil.getSessionFactory().getCurrentSession();

    public static void main(String[] args)
    {        
    	LOGIN login = new LOGIN();
    	login.setVisible(true);
    	
    }//main
    
}//mainClass

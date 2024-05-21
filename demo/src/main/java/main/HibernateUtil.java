package main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.File;

public class HibernateUtil {
    
        private static final SessionFactory sessionFactory;
        // Static code. Only executed once, like a Singleton
        static {
            try {
                // We create the SessionFactory from the hibernate.cfg.xml file
                sessionFactory = new Configuration().configure(new File("hibernate.cfg.xml")).buildSessionFactory();
                
            } catch (Throwable ex) {
                System.err.println("Initialization failed. " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }
    }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.query;

import Entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author URRUTIA
 */
public class DataQuery {
      EntityManagerFactory emf;
    EntityManager em;

    public DataQuery() {
        emf = Persistence.createEntityManagerFactory("ProyectoVentasPU");
        em = emf.createEntityManager();
    }

    public boolean logincontrol(String username, String password) {
        try {
            Usuario l=em.createNamedQuery("Usuario.control", Usuario.class).setParameter("login", username).setParameter("contrasena", password).getSingleResult();
            if (l !=null) {
                return true;
            }
            return false;
        } catch (Exception e)  {
            return false;
        }
    }
}

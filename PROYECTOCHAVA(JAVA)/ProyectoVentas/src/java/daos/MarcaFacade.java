/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidades.Marca;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author URRUTIA
 */
@Stateless
public class MarcaFacade extends AbstractFacade<Marca> {
    @PersistenceContext(unitName = "ProyectoVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MarcaFacade() {
        super(Marca.class);
    }
    
       public List<Marca> marca_nombre (String nombre){
        EntityManager em2= getEntityManager();
        Query q= em.createNamedQuery("Marca.buscarmarca").setParameter("nombre", nombre);
        return q.getResultList();
    }
    
}

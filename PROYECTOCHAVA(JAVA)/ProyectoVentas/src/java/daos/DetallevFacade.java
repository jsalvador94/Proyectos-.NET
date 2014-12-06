/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidades.Detallev;
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
public class DetallevFacade extends AbstractFacade<Detallev> {
    @PersistenceContext(unitName = "ProyectoVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetallevFacade() {
        super(Detallev.class);
    }
    
    public List<Detallev> nventa (Integer nventa){
        EntityManager em2= getEntityManager();
        Query q= em.createNamedQuery("Detallev.findByNdetalle").setParameter("ndetalle", nventa);
        return q.getResultList();
    }
    
}

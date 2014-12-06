/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidades.Datosp;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author URRUTIA
 */
@Stateless
public class DatospFacade extends AbstractFacade<Datosp> {
    @PersistenceContext(unitName = "ProyectoVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatospFacade() {
        super(Datosp.class);
    }
    
}

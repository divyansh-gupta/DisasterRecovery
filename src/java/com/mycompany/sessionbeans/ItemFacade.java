/*
 * Created by Divyansh Gupta on 2017.04.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.DisasterRecovery.Location;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author divyansh
 */
@Stateless
public class ItemFacade extends AbstractFacade<Item> {

    @PersistenceContext(unitName = "DisasterRecoveryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemFacade() {
        super(Item.class);
    }
    
    public Collection<Item> findByLocationId(Location id) {
       return em.createQuery("SELECT l FROM Item l WHERE l.locationId = :id").setParameter("id", id).getResultList();
    }
    
//    public Item findById(Integer id) {
//        if (em.createQuery("SELECT l FROM Item l WHERE l.id = :id")
//                .setParameter("id", id)
//                .getResultList().isEmpty()) {
//            return null;
//        } else {
//            return (Item) (em.createQuery("SELECT l FROM Item l WHERE l.id = :id")
//                    .setParameter("id", id)
//                    .getSingleResult());
//        }
//    }
}

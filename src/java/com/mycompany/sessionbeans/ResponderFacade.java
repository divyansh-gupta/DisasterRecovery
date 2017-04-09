/*
 * Created by Divyansh Gupta on 2017.04.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Responder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author divyansh
 */
@Stateless
public class ResponderFacade extends AbstractFacade<Responder> {

    @PersistenceContext(unitName = "DisasterRecoveryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResponderFacade() {
        super(Responder.class);
    }
    
        
    public Responder findByUsername(String username) {
        if (em.createQuery("SELECT c FROM Responder c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Responder) (em.createQuery("SELECT c FROM Responder c WHERE c.username = :username")
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }
    
    public String findPhotosByResponderID(Integer id) {
        if (em.createQuery("SELECT p.image FROM Responder p WHERE p.id = :id")
                .setParameter("id", id)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (String) (em.createQuery("SELECT p.image FROM Responder p WHERE p.id = :id")
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }
    
}

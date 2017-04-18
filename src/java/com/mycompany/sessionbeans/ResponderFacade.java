/*
 * Created by Divyansh Gupta on 2017.04.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Responder;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
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
        } else {
            return (String) (em.createQuery("SELECT p.image FROM Responder p WHERE p.id = :id")
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }

    public Responder findById(Integer id) {
        if (em.createQuery("SELECT c FROM Responder c WHERE c.id = :id")
                .setParameter("id", id)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Responder) (em.createQuery("SELECT c FROM Responder c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }

    public void deleteResponder(Integer id) {
        Responder responder = em.find(Responder.class, id);
        em.remove(responder);
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username") != null;
    }

    public Responder getLoggedIn() {
        /*  user_id (database primary key) was put into the SessionMap
            in the initializeSessionMap() method below or in LoginManager. */
        int userPrimaryKey = (int) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user_id");
        /*
            Given the primary key, obtain the object reference of the Responder
            object and store it into the instance variable selected.
         */
        return this.find(userPrimaryKey);
    }
}

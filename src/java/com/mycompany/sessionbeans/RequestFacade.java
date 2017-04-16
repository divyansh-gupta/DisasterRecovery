/*
 * Created by Divyansh Gupta on 2017.04.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Request;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jinwoo
 */
@Stateless
public class RequestFacade extends AbstractFacade<Request> {
    @PersistenceContext(unitName = "DisasterRecoveryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RequestFacade() {
        super(Request.class);
    }

    /*
    ---------------------------
    Search Category: Title
    ---------------------------
    */
    public List<Request> findByLocation(String locationName) {
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Requests c WHERE c.title LIKE :locationName").setParameter("locationName", locationName).getResultList();
    }
}

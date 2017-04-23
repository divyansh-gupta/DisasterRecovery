/*
 * Created by Divyansh Gupta on 2017.04.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Need;
import com.mycompany.DisasterRecovery.Request;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author divyansh
 */
@Stateless
public class NeedFacade extends AbstractFacade<Need> {

    @PersistenceContext(unitName = "DisasterRecoveryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NeedFacade() {
        super(Need.class);
    }
    
    public List<Need> findByLocation(Request request) {
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Need c "
                + "WHERE c.requestId=:request").setParameter("request", request).getResultList();
    }
}   

/*
 * Created by Divyansh Gupta on 2017.04.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Location;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author divyansh
 */
@Stateless
public class LocationFacade extends AbstractFacade<Location> {

    @PersistenceContext(unitName = "DisasterRecoveryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocationFacade() {
        super(Location.class);
    }

    public Location findByLatLong(BigDecimal lat, BigDecimal lng) {
        if (em.createQuery("SELECT l FROM Location l WHERE l.latitude = :latitude AND l.longitude = :longitude")
                .setParameter("latitude", lat)
                .setParameter("longitude", lng)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Location) (em.createQuery("SELECT l FROM Location l WHERE l.latitude = :latitude AND l.longitude = :longitude")
                    .setParameter("latitude", lat)
                    .setParameter("longitude", lng)
                    .getSingleResult());
        }
    }

    public Location findById(Integer id) {
        if (em.createQuery("SELECT l FROM Location l WHERE l.id = :id")
                .setParameter("id", id)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Location) (em.createQuery("SELECT l FROM Location l WHERE l.id = :id")
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }
}

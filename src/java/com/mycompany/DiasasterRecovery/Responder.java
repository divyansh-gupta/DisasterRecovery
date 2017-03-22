/*
 * Created by Divyansh Gupta on 2017.03.22  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DiasasterRecovery;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author divyansh
 */
@Entity
@Table(name = "Responder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responder.findAll", query = "SELECT r FROM Responder r")
    , @NamedQuery(name = "Responder.findById", query = "SELECT r FROM Responder r WHERE r.id = :id")
    , @NamedQuery(name = "Responder.findByUsername", query = "SELECT r FROM Responder r WHERE r.username = :username")
    , @NamedQuery(name = "Responder.findByResponderName", query = "SELECT r FROM Responder r WHERE r.responderName = :responderName")
    , @NamedQuery(name = "Responder.findByImage", query = "SELECT r FROM Responder r WHERE r.image = :image")})
public class Responder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "responder_name")
    private String responderName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "image")
    private String image;
    @JoinColumn(name = "location_id", referencedColumnName = "ID")
    @ManyToOne
    private Location locationId;

    public Responder() {
    }

    public Responder(Integer id) {
        this.id = id;
    }

    public Responder(Integer id, String username, String responderName, String image) {
        this.id = id;
        this.username = username;
        this.responderName = responderName;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResponderName() {
        return responderName;
    }

    public void setResponderName(String responderName) {
        this.responderName = responderName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Responder)) {
            return false;
        }
        Responder other = (Responder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.DiasasterRecovery.Responder[ id=" + id + " ]";
    }
    
}

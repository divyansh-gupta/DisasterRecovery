/*
 * Created by Divyansh Gupta on 2017.03.22  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
/**
 *
 * @author divyansh
 */
@Entity
@Table(name = "Request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Request.findAll", query = "SELECT r FROM Request r")
    , @NamedQuery(name = "Request.findById", query = "SELECT r FROM Request r WHERE r.id = :id")
    , @NamedQuery(name = "Request.findByStatus", query = "SELECT r FROM Request r WHERE r.status = :status")})
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "status")
    private String status;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestId")
    private Collection<Need> needCollection;
    
    @JoinColumn(name = "from_location_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Location fromLocationId;
    
    @JoinColumn(name = "to_location_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Location toLocationId;
    
    /**
     * Empty Constructor
     */
    public Request() {
    }

    /**
     * Request Constructor ID
     * @param id id
     */
    public Request(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded Request constructor
     * @param id id
     * @param status status
     */
    public Request(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Get id
     * @return Request ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get id
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get status
     * @return Request Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get need collection
     * @return Request need collection
     */
    @XmlTransient
    public Collection<Need> getNeedCollection() {
        return needCollection;
    }

    /**
     * Set need collection
     * @param needCollection need collection
     */
    public void setNeedCollection(Collection<Need> needCollection) {
        this.needCollection = needCollection;
    }
    
    /**
     * Get from location id
     * @return Request's From location ID
     */
    public Location getFromLocationId() {
        return fromLocationId;
    }

    /**
     * Set from location id
     * @param fromLocationId from location id
     */
    public void setFromLocationId(Location fromLocationId) {
        this.fromLocationId = fromLocationId;
    }

    /**
     * Get to location id
     * @return Request's to location ID
     */
    public Location getToLocationId() {
        return toLocationId;
    }

    /**
     * Set to location id
     * @param toLocationId to location id
     */
    public void setToLocationId(Location toLocationId) {
        this.toLocationId = toLocationId;
    }
    
    // Overloaded functions
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.DiasasterRecovery.Request[ id=" + id + " ]";
    }
    
}

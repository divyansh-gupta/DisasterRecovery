/*
 * Created by Divyansh Gupta on 2017.03.22  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import com.mycompany.sessionbeans.ItemFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author divyansh
 */
@Entity
@Table(name = "Location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
    , @NamedQuery(name = "Location.findById", query = "SELECT l FROM Location l WHERE l.id = :id")
    , @NamedQuery(name = "Location.findByLocationName", query = "SELECT l FROM Location l WHERE l.locationName = :locationName")
    , @NamedQuery(name = "Location.findByLatitude", query = "SELECT l FROM Location l WHERE l.latitude = :latitude")
    , @NamedQuery(name = "Location.findByLatLong", query = "SELECT l FROM Location l WHERE l.latitude = :latitude AND l.longitude = :longitude")
    , @NamedQuery(name = "Location.findByLongitude", query = "SELECT l FROM Location l WHERE l.longitude = :longitude")})

public class Location implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "triggered")
    private boolean triggered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderLocation")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiverLocation")
    private Collection<Message> messageCollection1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "location_name")
    private String locationName;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private BigDecimal latitude;

    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private BigDecimal longitude;


    @Basic(optional = false)
    @Column(name = "emergency_description")
    private String emergencyDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private Collection<Item> itemCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromLocationId")
    private Collection<Request> requestCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toLocationId")
    private Collection<Request> requestCollection1;

    @OneToMany(mappedBy = "locationId")
    private Collection<Responder> responderCollection;

    /**
     * Empty Constructor
     */
    public Location() {
    }

    /**
     * Constructor with id
     * @param id
     */
    public Location(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded Constructor
     * @param id
     * @param locationName
     * @param latitude
     * @param longitude
     * @param triggered
     * @param emergencyDescription
     */
    public Location(Integer id, String locationName, BigDecimal latitude, BigDecimal longitude, Boolean triggered, String emergencyDescription) {
        this.id = id;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.triggered = triggered;
        this.emergencyDescription = emergencyDescription;
    }

    /**
     * Get emergency description
     * @return Emergency Description
     */
    public String getEmergencyDescription() {
        return emergencyDescription;
    }

    /**
     * Set emergency description
     * @param emergencyDescription description
     */
    public void setEmergencyDescription(String emergencyDescription) {
        this.emergencyDescription = emergencyDescription;
    }

    /**
     * Get id
     * @return Location ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set id
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get location name
     * @return Location Name 
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Set location name
     * @param locationName location name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Get latitude
     * @return Location Latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Set latitude
     * @param latitude latitude
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * Get longitude
     * @return Location Longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Set longitude
     * @param longitude longitude
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * Get item collection
     * @return Location Item Collection
     */
    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    /**
     * Set item collection
     * @param itemCollection item collection
     */
    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    /**
     * Get request collection
     * @return Location Request Collection
     */
    @XmlTransient
    public Collection<Request> getRequestCollection() {
        return requestCollection;
    }

    /**
     * Set request collection
     * @param requestCollection request collection
     */
    public void setRequestCollection(Collection<Request> requestCollection) {
        this.requestCollection = requestCollection;
    }

    /**
     * Get request collection 1
     * @return Location Request Collection 1
     */
    @XmlTransient
    public Collection<Request> getRequestCollection1() {
        return requestCollection1;
    }

    /**
     * Set request collection 1
     * @param requestCollection1 request collection 1
     */
    public void setRequestCollection1(Collection<Request> requestCollection1) {
        this.requestCollection1 = requestCollection1;
    }

    /**
     * Get responder collection
     * @return Location Responder Collection
     */
    @XmlTransient
    public Collection<Responder> getResponderCollection() {
        return responderCollection;
    }

    /**
     * Set responder collection
     * @param responderCollection responder collection
     */
    public void setResponderCollection(Collection<Responder> responderCollection) {
        this.responderCollection = responderCollection;
    }
    
    /**
     * Get triggered
     * @return Location Triggered
     */
    public boolean getTriggered() {
        return triggered;
    }

    /**
     * Set triggered
     * @param triggered triggered
     */
    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    /**
     * Get message collection
     * @return Location Message Collection
     */
    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    /**
     * Set message collection
     * @param messageCollection message collection
     */
    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    /**
     * Get message collection 1
     * @return Location Message Collection1
     */
    @XmlTransient
    public Collection<Message> getMessageCollection1() {
        return messageCollection1;
    }

    /**
     * Set message collection 1
     * @param messageCollection1 message collection 1
     */
    public void setMessageCollection1(Collection<Message> messageCollection1) {
        this.messageCollection1 = messageCollection1;
    }
    
    /**
     * Get alternate name
     * @return Location Alternate Name
     */
    public String getAlternateName() {
        return this.locationName.split(",")[0].replace("\\s+", "_");
    }

    // Override functions
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.DiasasterRecovery.Location[ id=" + id + " ]";
    }
}

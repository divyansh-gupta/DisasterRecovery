/*
 * Created by Divyansh Gupta on 2017.03.22  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import java.io.Serializable;
import java.util.Collection;
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

/**
 *
 * @author divyansh
 */
// Item class
@Entity
@Table(name = "Item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
    , @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id")
    , @NamedQuery(name = "Item.findByItemType", query = "SELECT i FROM Item i WHERE i.itemType = :itemType")
    , @NamedQuery(name = "Item.findByQuantity", query = "SELECT i FROM Item i WHERE i.quantity = :quantity")})
public class Item implements Serializable {// Item class

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;         // Item ID

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "item_type")
    private String itemType;    // Item type

    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;       // Item Quantity

    @JoinColumn(name = "location_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Location locationId;    // Item Location ID

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Collection<Need> needCollection;    // Collection of Needs


    /**
     * Empty Constructor
     */
    public Item() {
    }

    /**
     * Item Constructor with id
     * @param id
     */
    public Item(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded Item constructor
     * @param id
     * @param itemType
     * @param quantity
     */
    public Item(Integer id, String itemType, int quantity) {
        this.id = id;
        this.itemType = itemType;
        this.quantity = quantity;
    }
    
    // Getters and setters

    /**
     * Get ID
     * @return Item ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set ID
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get Item Type
     * @return Item Type
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Set Item Type
     * @param itemType item type
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * Get Quantity
     * @return Item Quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set quantity
     * @param quantity quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get location id
     * @return Item Location
     */
    public Location getLocationId() {
        return locationId;
    }

    /**
     * Set location id
     * @param locationId location id
     */
    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    /**
     * Get need collection
     * @return Need Collection
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.DiasasterRecovery.Item[ id=" + id + " ]";
    }

}

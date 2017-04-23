/*
 * Created by Divyansh Gupta on 2017.03.22  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author divyansh
 */
@Entity
@Table(name = "Need")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Need.findAll", query = "SELECT n FROM Need n")
    , @NamedQuery(name = "Need.findById", query = "SELECT n FROM Need n WHERE n.id = :id")
    , @NamedQuery(name = "Need.findByQuantity", query = "SELECT n FROM Need n WHERE n.quantity = :quantity")})
public class Need implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "item_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "request_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Request requestId;
    

    public Need() {
    }

    public Need(Integer id) {
        this.id = id;
    }

    public Need(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItemId() {
        return itemId;
    }
    

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Request getRequestId() {
        return requestId;
    }

    public void setRequestId(Request requestId) {
        this.requestId = requestId;
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
        if (!(object instanceof Need)) {
            return false;
        }
        Need other = (Need) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.DiasasterRecovery.Need[ id=" + id + " ]";
    }
    
}

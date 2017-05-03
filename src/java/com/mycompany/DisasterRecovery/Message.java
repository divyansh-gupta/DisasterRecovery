/*
 * Created by Divyansh Gupta on 2017.05.02  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author divyansh
 */
@Entity
@Table(name = "Message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id")
    , @NamedQuery(name = "Message.findByTimeStamp", query = "SELECT m FROM Message m WHERE m.timeStamp = :timeStamp")
    , @NamedQuery(name = "Message.findByDescription", query = "SELECT m FROM Message m WHERE m.description = :description")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "description")
    private String description;
    
    @JoinColumn(name = "sender_location", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Location senderLocation;
    
    @JoinColumn(name = "reciever_location", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Location recieverLocation;

    /**
     * Empty Constructor
     */
    public Message() {
    }

    /**
     * Message Constructor with id
     * @param id
     */
    public Message(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded Message Constructor
     * @param id
     * @param timeStamp
     * @param description
     */
    public Message(Integer id, Date timeStamp, String description) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.description = description;
    }

    /**
     *
     * @return Message ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return Message Time stamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @param timeStamp
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     *
     * @return Message Description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return Message Sender Location
     */
    public Location getSenderLocation() {
        return senderLocation;
    }

    /**
     *
     * @param senderLocation
     */
    public void setSenderLocation(Location senderLocation) {
        this.senderLocation = senderLocation;
    }

    /**
     *
     * @return Message Receiver Location
     */
    public Location getRecieverLocation() {
        return recieverLocation;
    }

    /**
     *
     * @param recieverLocation
     */
    public void setRecieverLocation(Location recieverLocation) {
        this.recieverLocation = recieverLocation;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.DisasterRecovery.Message[ id=" + id + " ]";
    }
    
}

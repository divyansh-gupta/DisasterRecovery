/*
 * Created by Divyansh Gupta on 2017.04.23  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.LocationFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author divyansh
 */
@Named(value = "inventoryManager")
@SessionScoped
public class InventoryManager implements Serializable {
    
    @EJB
    LocationFacade locationFacade;
    
    private Integer waterCount;
    private Integer cannedGoodsCount;
    private Integer blanketsCount;
    private Integer shelterCount;
    private Integer emergencyKitCount;
    private Double dollarCount;
    
    public String updateInventory(Responder user) {
        
        return "/Inventory.xhtml";
    }

    public LocationFacade getLocationFacade() {
        return locationFacade;
    }

    public void setLocationFacade(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

    public Integer getWaterCount() {
        return waterCount;
    }

    public void setWaterCount(Integer waterCount) {
        this.waterCount = waterCount;
    }

    public Integer getCannedGoodsCount() {
        return cannedGoodsCount;
    }

    public void setCannedGoodsCount(Integer cannedGoodsCount) {
        this.cannedGoodsCount = cannedGoodsCount;
    }

    public Integer getBlanketsCount() {
        return blanketsCount;
    }

    public void setBlanketsCount(Integer blanketsCount) {
        this.blanketsCount = blanketsCount;
    }

    public Integer getShelterCount() {
        return shelterCount;
    }

    public void setShelterCount(Integer shelterCount) {
        this.shelterCount = shelterCount;
    }

    public Integer getEmergencyKitCount() {
        return emergencyKitCount;
    }

    public void setEmergencyKitCount(Integer emergencyKitCount) {
        this.emergencyKitCount = emergencyKitCount;
    }

    public Double getDollarCount() {
        return dollarCount;
    }

    public void setDollarCount(Double dollarCount) {
        this.dollarCount = dollarCount;
    }
    
}

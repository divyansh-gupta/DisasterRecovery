/*
 * Created by Divyansh Gupta on 2017.04.23  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.ItemFacade;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.ResponderFacade;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
    
    @EJB
    ResponderFacade responderFacade;
    
    @EJB
    ItemFacade itemFacade;
    
    private Integer waterCount;
    private Integer cannedGoodsCount;
    private Integer blanketsCount;
    private Integer shelterCount;
    private Integer emergencyKitCount;
    private Integer dollarCount;
    
    public String updateInventory(Responder user) {
        Location userLocation = user.getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
                .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        typeItemMap.get("WATER").setQuantity(waterCount);
        typeItemMap.get("CANNED_GOODS").setQuantity(this.cannedGoodsCount);
        typeItemMap.get("BLANKETS").setQuantity(this.blanketsCount);
        typeItemMap.get("SHELTER").setQuantity(this.shelterCount);
        typeItemMap.get("EMERGENCY_KITS").setQuantity(this.emergencyKitCount);
        typeItemMap.get("USD").setQuantity(this.dollarCount.intValue());
        
        itemFacade.edit(typeItemMap.get("WATER"));
        itemFacade.edit(typeItemMap.get("CANNED_GOODS"));
        itemFacade.edit(typeItemMap.get("BLANKETS"));
        itemFacade.edit(typeItemMap.get("SHELTER"));
        itemFacade.edit(typeItemMap.get("EMERGENCY_KITS"));
        itemFacade.edit(typeItemMap.get("USD"));

        return "/Inventory.xhtml";
    }
    
    public Responder getLoggedInUser() {
        Responder selected = null;
        int userPrimaryKey = (int) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user_id");
        /*
            Given the primary key, obtain the object reference of the Responder
            object and store it into the instance variable selected.
        */
        selected = responderFacade.find(userPrimaryKey);
        return selected;
    }

    public LocationFacade getLocationFacade() {
        return locationFacade;
    }

    public void setLocationFacade(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

    public Integer getWaterCount() {
        Location userLocation = getLoggedInUser().getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        return typeItemMap.get("WATER").getQuantity();
    }

    public void setWaterCount(Integer waterCount) {
        this.waterCount = waterCount;
    }
    
    public Integer getCannedGoodsCount() {
        Location userLocation = getLoggedInUser().getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        return typeItemMap.get("CANNED_GOODS").getQuantity();
    }

    public void setCannedGoodsCount(Integer cannedGoodsCount) {
        this.cannedGoodsCount = cannedGoodsCount;
    }

    public Integer getBlanketsCount() {
        Location userLocation = getLoggedInUser().getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        return typeItemMap.get("BLANKETS").getQuantity();
    }

    public void setBlanketsCount(Integer blanketsCount) {
        this.blanketsCount = blanketsCount;
    }

    public Integer getShelterCount() {
        Location userLocation = getLoggedInUser().getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        return typeItemMap.get("SHELTER").getQuantity();
    }

    public void setShelterCount(Integer shelterCount) {
        this.shelterCount = shelterCount;
    }

    public Integer getEmergencyKitCount() {
        Location userLocation = getLoggedInUser().getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        return typeItemMap.get("EMERGENCY_KITS").getQuantity();
    }

    public void setEmergencyKitCount(Integer emergencyKitCount) {
        this.emergencyKitCount = emergencyKitCount;
    }

    public Integer getDollarCount() {
        Location userLocation = getLoggedInUser().getLocationId();
        Map<String, Item> typeItemMap = itemFacade.findByLocationId(userLocation).stream()
            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
        return typeItemMap.get("USD").getQuantity();
    }

    public void setDollarCount(Integer dollarCount) {
        this.dollarCount = dollarCount;
    }
    
}

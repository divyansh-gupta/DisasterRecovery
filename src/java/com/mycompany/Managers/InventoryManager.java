/*
 * Created by Divyansh Gupta on 2017.04.23  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Responder;
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
    
    private Integer waterCount;
    private Integer cannedGoodsCount;
    private Integer blanketsCount;
    private Integer shelterCount;
    private Integer emergencyKitCount;
    private Double dollarCount;
    
    public String updateInventory(Responder user) {
//        Location userLocation = user.getLocationId();
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//                .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
//        typeItemMap.get("WATER").setQuantity(waterCount);
//        System.out.println(typeItemMap);
//        locationFacade.edit(userLocation);
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
//        Location userLocation = getLoggedInUser().getLocationId();
//        System.out.println(userLocation.getItemCollection());
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
////        System.out.println(typeItemMap);
//        return typeItemMap.get("WATER").getQuantity();
        return waterCount;
    }

    public void setWaterCount(Integer waterCount) {
        this.waterCount = waterCount;
    }
//'WATER', 'CANNED_GOODS', 'BLANKETS', 'SHELTER', 'EMERGENCY_KITS', 'USD'
    public Integer getCannedGoodsCount() {
//        Location userLocation = getLoggedInUser().getLocationId();
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
//        return typeItemMap.get("CANNNED_GOODS").getQuantity();
        return cannedGoodsCount;
    }

    public void setCannedGoodsCount(Integer cannedGoodsCount) {
        this.cannedGoodsCount = cannedGoodsCount;
    }

    public Integer getBlanketsCount() {
//        Location userLocation = getLoggedInUser().getLocationId();
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
//        return typeItemMap.get("BLANKETS").getQuantity();
        return this.blanketsCount;
    }

    public void setBlanketsCount(Integer blanketsCount) {
        this.blanketsCount = blanketsCount;
    }

    public Integer getShelterCount() {
//        Location userLocation = getLoggedInUser().getLocationId();
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
//        return typeItemMap.get("SHELTER").getQuantity();
        return this.shelterCount;
    }

    public void setShelterCount(Integer shelterCount) {
        this.shelterCount = shelterCount;
    }

    public Integer getEmergencyKitCount() {
//        Location userLocation = getLoggedInUser().getLocationId();
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
//        return typeItemMap.get("EMERGENCY_KITS").getQuantity();
        return this.emergencyKitCount;
    }

    public void setEmergencyKitCount(Integer emergencyKitCount) {
        this.emergencyKitCount = emergencyKitCount;
    }

    public Double getDollarCount() {
//        Location userLocation = getLoggedInUser().getLocationId();
//        Map<String, Item> typeItemMap = userLocation.getItemCollection().stream()
//            .collect(Collectors.toMap(item -> item.getItemType(), item -> item));
//        return typeItemMap.get("USD").getQuantity();
        return this.dollarCount;
    }

    public void setDollarCount(Double dollarCount) {
        this.dollarCount = dollarCount;
    }
    
}

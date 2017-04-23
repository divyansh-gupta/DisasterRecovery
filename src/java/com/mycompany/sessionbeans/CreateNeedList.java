/*
 * Created by Bhaanu Kaul on 2017.04.11  * 
 * Copyright © 2017 Bhaanu Kaul. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Need;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Bhaanu Kaul
 */
@ManagedBean(name = "CreateNeedList")
@ViewScoped
public class CreateNeedList {    
        private List<Need> needs;
        private List<Item> items;
        private List<Integer> quantities;

    public List<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    @PostConstruct
    public void init() {
        items = new ArrayList();
        quantities = new ArrayList();
//        Need need = new Need();
        items.add(new Item());
        quantities.add(0);
    }

    public void submit() {
            
    }
    public void changed(ValueChangeEvent event) {
        System.out.println(event.getNewValue());
//         this.changed = true;
}
    

    public void extend() {
//        Need newNeed = new Need();
           Item newItem = new Item();
           int qty = 0;
//        needs.add(newNeed);
            items.add(newItem);
            quantities.add(qty);
    }

    public void setValues(List<Need> values) {
        this.needs = values;
    }

    public List<Need> getValues() {
        return needs;
    }
}

package com.mycompany.jsfclasses;


import com.mycompany.DisasterRecovery.Item;

import com.mycompany.DisasterRecovery.Need;
import com.mycompany.DisasterRecovery.Request;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.NeedFacade;
import com.mycompany.sessionbeans.RequestFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("requestController")
@SessionScoped
public class RequestController implements Serializable {

    @EJB
    private com.mycompany.sessionbeans.RequestFacade ejbFacade;
    
    @EJB
    NeedFacade needFacade;
    
    private List<Request> items = null;
    private List<Need> needs = null;

    public NeedFacade getNeedFacade() {
        return needFacade;
    }

    public void setNeedFacade(NeedFacade needFacade) {
        this.needFacade = needFacade;
    }
    private Request selected;
    private NeedController needCtrl = new NeedController();

    Need waterNeed = new Need();
    Need cannedNeed = new Need();
    Need blanketNeed = new Need();
    Need shelterNeed = new Need();
    Need usdNeed = new Need();
    Need emergencyNeed = new Need();

    public RequestFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(RequestFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Need getWaterNeed() {
        return waterNeed;
    }

    public void setWaterNeed(Need waterNeed) {
        this.waterNeed = waterNeed;
    }

    public Need getCannedNeed() {
        return cannedNeed;
    }

    public void setCannedNeed(Need cannedNeed) {
        this.cannedNeed = cannedNeed;
    }

    public Need getBlanketNeed() {
        return blanketNeed;
    }

    public void setBlanketNeed(Need blanketNeed) {
        this.blanketNeed = blanketNeed;
    }

    public Need getShelterNeed() {
        return shelterNeed;
    }

    public void setShelterNeed(Need shelterNeed) {
        this.shelterNeed = shelterNeed;
    }

    public Need getUsdNeed() {
        return usdNeed;
    }

    public void setUsdNeed(Need usdNeed) {
        this.usdNeed = usdNeed;
    }

    public Need getEmergencyNeed() {
        return emergencyNeed;
    }

    public void setEmergencyNeed(Need emergencyNeed) {
        this.emergencyNeed = emergencyNeed;
    }

//    public List<Need> getNeedsToAdd() {
//        return needsToAdd;
//    }
//
//    public void setNeedsToAdd(List<Need> needsToAdd) {
//        this.needsToAdd = needsToAdd;
//    }
    public RequestController() {

    }

    public Request getSelected() {
        return selected;
    }

    public void setSelected(Request selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RequestFacade getFacade() {
        return ejbFacade;
    }

    public Request prepareCreate() {
        selected = new Request();
        initializeEmbeddableKey();
        return selected;
    }

    public void createRequest() {
        Collection<Need> needList = new ArrayList();
        Map<String, Item> fromLocationMap = selected.getFromLocationId().getItemCollection()
                .stream().collect(Collectors.toMap(i -> i.getItemType(), i -> i));

        waterNeed.setItemId(fromLocationMap.get("WATER"));
        cannedNeed.setItemId(fromLocationMap.get("CANNED_GOODS"));
        blanketNeed.setItemId(fromLocationMap.get("SHELTER"));
        shelterNeed.setItemId(fromLocationMap.get("BLANKETS"));
        usdNeed.setItemId(fromLocationMap.get("EMERGENCY_KITS"));
        emergencyNeed.setItemId(fromLocationMap.get("USD"));

        waterNeed.setQuantity(0);
        blanketNeed.setQuantity(0);
        shelterNeed.setQuantity(0);
        usdNeed.setQuantity(0);
        cannedNeed.setQuantity(0);
        emergencyNeed.setQuantity(0);
        

        if (waterNeed.getQuantity() > 0) {
            needList.add(waterNeed);
        }
        if (blanketNeed.getQuantity() > 0) {
            needList.add(blanketNeed);
        }
        if (shelterNeed.getQuantity() > 0) {
            needList.add(shelterNeed);
        }
        if (cannedNeed.getQuantity() > 0) {
            needList.add(cannedNeed);
        }
        if (emergencyNeed.getQuantity() > 0) {
            needList.add(emergencyNeed);
        }
        if (usdNeed.getQuantity() > 0) {

            needList.add(usdNeed);
        }

        selected.setNeedCollection(needList);

    }

    public void create() {
        createRequest();

        this.selected.setStatus("REQUESTED");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RequestCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RequestUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RequestDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Request> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }
    
    public List<Need> getNeedList(Request req) {
        needs = needFacade.findByLocation(req);
        return needs;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Request getRequest(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Request> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Request> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Request.class)
    public static class RequestControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RequestController controller = (RequestController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "requestController");
            return controller.getRequest(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Request) {
                Request o = (Request) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Request.class.getName()});
                return null;
            }
        }

    }

}

package com.mycompany.jsfclasses;

import com.mycompany.DisasterRecovery.Item;

import com.mycompany.DisasterRecovery.Need;
import com.mycompany.DisasterRecovery.Request;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.ItemFacade;
import com.mycompany.sessionbeans.NeedFacade;
import com.mycompany.sessionbeans.RequestFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import java.util.Random;

/**
 * Request controller
 * @author cheng
 */
@Named("requestController")
@SessionScoped
public class RequestController implements Serializable {

    @EJB
    private com.mycompany.sessionbeans.RequestFacade ejbFacade;

    @EJB
    NeedFacade needFacade;

    @EJB
    private ItemFacade itemFacade;
    
    @EJB
    private RequestFacade requestFacade;

  
    private List<Request> items = null;
    private List<Need> needs = null;

  

    private Request selected;
    private NeedController needCtrl = new NeedController();

    Need waterNeed = new Need();
    Need cannedNeed = new Need();
    Need blanketNeed = new Need();
    Need shelterNeed = new Need();
    Need usdNeed = new Need();
    Need emergencyNeed = new Need();

    /**
     *
     * @return
     */
    public RequestFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * Get item facade
     */
    public ItemFacade getItemFacade() {
        return itemFacade;
    }
    
    /**
     * Get need facade
     */
    public NeedFacade getNeedFacade() {
        return needFacade;
    }
    
    /**
     * Get request facade
     */
    public RequestFacade getRequestFacade() {
        return requestFacade;
    }

    /**
     * Get water need
     */
    public Need getWaterNeed() {
        return waterNeed;
    }

    /**
     * Set water need
     */
    public void setWaterNeed(Need waterNeed) {
        this.waterNeed = waterNeed;
    }

    /**
     * Get canned need
     */
    public Need getCannedNeed() {
        return cannedNeed;
    }

    /**
     * Set canned need
     */
    public void setCannedNeed(Need cannedNeed) {
        this.cannedNeed = cannedNeed;
    }

    /**
     * Get blanket need
     */
    public Need getBlanketNeed() {
        return blanketNeed;
    }

    /**
     * Set blanket need
     */
    public void setBlanketNeed(Need blanketNeed) {
        this.blanketNeed = blanketNeed;
    }

    /**
     * Get shelter need
     */
    public Need getShelterNeed() {
        return shelterNeed;
    }

    /**
     * Set shelter need
     */
    public void setShelterNeed(Need shelterNeed) {
        this.shelterNeed = shelterNeed;
    }

    /**
     * Get usd need
     */
    public Need getUsdNeed() {
        return usdNeed;
    }

    /**
     * Set usd need
     */
    public void setUsdNeed(Need usdNeed) {
        this.usdNeed = usdNeed;
    }

    /**
     * Get emergency need
     */
    public Need getEmergencyNeed() {
        return emergencyNeed;
    }

    /**
     * Set emergency need
     */
    public void setEmergencyNeed(Need emergencyNeed) {
        this.emergencyNeed = emergencyNeed;
    }

    /**
     * Default constructor
     */
    public RequestController() {

    }

    /**
     * Get selected need
     */
    public Request getSelected() {
        return selected;
    }

    /**
     * Set selected need
     */
    public void setSelected(Request selected) {
        this.selected = selected;
    }

    /**
     * Get facade
     */
    private RequestFacade getFacade() {
        return ejbFacade;
    }

    /**
     * Prepare create
     */
    public Request prepareCreate() {
        selected = new Request();
        return selected;
    }

    /**
     * Create need
     */
    public void createRequest() {
        Collection<Need> needList = new ArrayList();
        Map<String, Item> fromLocationMap = getItemFacade().findByLocationId(selected.getFromLocationId())
                .stream().collect(Collectors.toMap(i -> i.getItemType(), i -> i));

        System.out.println(selected.getFromLocationId());

        waterNeed.setItemId(fromLocationMap.get("WATER"));
        cannedNeed.setItemId(fromLocationMap.get("CANNED_GOODS"));
        blanketNeed.setItemId(fromLocationMap.get("SHELTER"));
        shelterNeed.setItemId(fromLocationMap.get("BLANKETS"));
        usdNeed.setItemId(fromLocationMap.get("USD"));
        emergencyNeed.setItemId(fromLocationMap.get("EMERGENCY_KITS"));

        waterNeed.setRequestId(selected);
        cannedNeed.setRequestId(selected);
        blanketNeed.setRequestId(selected);
        shelterNeed.setRequestId(selected);
        usdNeed.setRequestId(selected);
        emergencyNeed.setRequestId(selected);
        

        if (waterNeed.getQuantity() > 0) {
            getNeedFacade().create(waterNeed);
            needList.add(waterNeed);
        }
        if (blanketNeed.getQuantity() > 0) {
            getNeedFacade().create(blanketNeed);

            needList.add(blanketNeed);
        }
        if (shelterNeed.getQuantity() > 0) {
            getNeedFacade().create(shelterNeed);

            needList.add(shelterNeed);
        }
        if (cannedNeed.getQuantity() > 0) {
            getNeedFacade().create(cannedNeed);

            needList.add(cannedNeed);
        }
        if (emergencyNeed.getQuantity() > 0) {
            getNeedFacade().create(emergencyNeed);

            needList.add(emergencyNeed);
        }
        if (usdNeed.getQuantity() > 0) {
            getNeedFacade().create(usdNeed);

            needList.add(usdNeed);
        }

        selected.setNeedCollection(needList);

    }

    /**
     * create
     */
    public void create() {
        Date date = new Date();
        Random rand = new Random(date.getTime());
        int temp = rand.nextInt();
        selected.setId(Math.abs(temp));
        this.selected.setStatus("REQUESTED");
        selected = persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RequestCreated"));
        
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        createRequest();
    }

    /**
     * update
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RequestUpdated"));
    }

    /**
     * destroy
     */
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RequestDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Get items
     */
    public List<Request> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /**
     * Get needs
     */
    public List<Need> getNeeds() {
        return needs;
    }

    /**
     * Set needs
     */
    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }

    /**
     * Get need list
     */
    public List<Need> getNeedList(Request req) {
        needs = needFacade.findByLocation(req);
        System.out.println(needs.toString());
        return needs;
    }

    /**
     * Persist
     */
    private Request persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    return (Request) getFacade().edit(selected);
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
        return null;
    }

    /**
     * Get request
     * @param id
     * @return
     */
    public Request getRequest(java.lang.Integer id) {
        return getFacade().find(id);
    }

    /**
     * Get item available
     */
    public List<Request> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    /**
     * Get item available
     */
    public List<Request> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * Converter
     */
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

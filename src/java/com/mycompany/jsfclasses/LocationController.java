package com.mycompany.jsfclasses;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.LocationFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Location controller
 * @author cheng
 */
@Named("locationController")
@SessionScoped
public class LocationController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    
    @EJB
    private com.mycompany.sessionbeans.LocationFacade ejbFacade;
    private List<Location> items = null;
    private Location selected;

    /**
     * Default constructor
     */
    public LocationController() {
    }

    /**
     * Get selected
     * @return selected location
     */
    public Location getSelected() {
        return selected;
    }

    /**
     * Set selected location
     * @param selected location
     */
    public void setSelected(Location selected) {
        this.selected = selected;
    }

    private LocationFacade getFacade() {
        return ejbFacade;
    }

    /**
     * Prepare create
     * @return
     */
    public Location prepareCreate() {
        selected = new Location();
        return selected;
    }

    /**
     * Create
     */
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LocationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Update
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LocationUpdated"));
    }

    /**
     * Destroy
     */
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LocationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Get location
     * @return list of location
     */
    public List<Location> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /*
     * Persist
     */
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
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

    /**
     * Get location
     * @param id id
     * @return location
     */
    public Location getLocation(java.lang.Integer id) {
        return getFacade().find(id);
    }

    /**
     * Get available location
     * @return list of location
     */
    public List<Location> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    /**
     *Get available location
     * @return list of location
     */
    public List<Location> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * Converter
     */ 
    @FacesConverter(forClass = Location.class)
    public static class LocationControllerConverter implements Converter {

        /**
         * Get as object
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocationController controller = (LocationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "locationController");
            return controller.getLocation(getKey(value));
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

        /** 
         * Get as string
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Location) {
                Location o = (Location) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Location.class.getName()});
                return null;
            }
        }

    }

}

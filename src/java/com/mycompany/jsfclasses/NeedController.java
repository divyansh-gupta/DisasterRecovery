package com.mycompany.jsfclasses;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.DisasterRecovery.Need;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.NeedFacade;

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
 * Need controller
 * @author cheng
 */
@Named("needController")
@SessionScoped
public class NeedController implements Serializable {
    
    @EJB
    private com.mycompany.sessionbeans.NeedFacade ejbFacade;
    
    private List<Need> items = null;
    private Need selected;
    
    /**
     * Default constructor
     */
    public NeedController() {
    }

    /**
     * Get selected need
     * @return need
     */
    public Need getSelected() {
        return selected;
    }

    /**
     * Set selected need
     * @param selected need
     */
    public void setSelected(Need selected) {
        this.selected = selected;
    }

    private NeedFacade getFacade() {
        return ejbFacade;
    }

    /** 
     * Prepare create
     */
    public Need prepareCreate() {
        selected = new Need();
        return selected;
    }

    /**
     * Create
     */
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("NeedCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Update
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("NeedUpdated"));
    }

    /**
     * Destroy
     */
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("NeedDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Get needs
     * @return list of needs
     */
    public List<Need> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /**
     * Persist
     * @param persistAction
     * @param successMessage 
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
     * Get need
     * @param id id
     * @return need
     */
    public Need getNeed(java.lang.Integer id) {
        return getFacade().find(id);
    }

    /**
     * Get needs available
     */
    public List<Need> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    /**
     * Get needs available
     */
    public List<Need> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * Converter
     */
    @FacesConverter(forClass = Need.class)
    public static class NeedControllerConverter implements Converter {

        /**
         * Get as object
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NeedController controller = (NeedController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "needController");
            return controller.getNeed(getKey(value));
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
         */
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Need) {
                Need o = (Need) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Need.class.getName()});
                return null;
            }
        }

    }
    
    /**
     * Need to string
     */
    public String needToString(Need n) {
        return n.getQuantity()+ " of " + n.getItemId().getItemType().replace('_', ' ');
    }
}

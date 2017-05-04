package com.mycompany.jsfclasses;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.ItemFacade;

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
 * Item controller
 * @author cheng
 */
@Named("itemController")
@SessionScoped
public class ItemController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    
    @EJB
    private com.mycompany.sessionbeans.ItemFacade ejbFacade;
    
    private List<Item> items = null;
    private Item selected;

    /**
     * Default constructor
     */
    public ItemController() {
    }

    /**
     * Get selected item
     * @return item
     */
    public Item getSelected() {
        return selected;
    }

    /**
     * Set selected item
     * @param selected item
     */
    public void setSelected(Item selected) {
        this.selected = selected;
    }

    private ItemFacade getFacade() {
        return ejbFacade;
    }

    /**
     * Prepare create
     * @return item
     */
    public Item prepareCreate() {
        selected = new Item();
        return selected;
    }

    /**
     * Create item
     */
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Update item
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ItemUpdated"));
    }

    /**
     * Destroy item
     */
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Get items
     * @return list of item
     */
    public List<Item> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /*
     * persist
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
     * Get item
     * @param id id
     * @return item
     */
    public Item getItem(java.lang.Integer id) {
        return getFacade().find(id);
    }

    /**
     * Get item available
     * @return list of item
     */
    public List<Item> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    /**
     * Get item available
     * @return list of item
     */ 
    public List<Item> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * Item controller converter
     */
    @FacesConverter(forClass = Item.class)
    public static class ItemControllerConverter implements Converter {

        /**
         * Get as object
         * @param facesContext context
         * @param component component
         * @param value value
         * @return object
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemController controller = (ItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemController");
            return controller.getItem(getKey(value));
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
         * @param facesContext context 
         * @param component component
         * @param object object
         * @return string
         */
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Item) {
                Item o = (Item) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Item.class.getName()});
                return null;
            }
        }

    }

}

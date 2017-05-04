package com.mycompany.jsfclasses;

import com.mycompany.DisasterRecovery.Message;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.MessageFacade;

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
 *
 * @author cheng
 */
@Named("messageController")
@SessionScoped
public class MessageController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    
    @EJB
    private com.mycompany.sessionbeans.MessageFacade ejbFacade;
    private List<Message> items = null;
    private Message selected;

    /**
     * Default constructor
     */
    public MessageController() {
    }

    /**
     * Get selected message
     */
    public Message getSelected() {
        return selected;
    }

    /**
     * Set selected message
     */
    public void setSelected(Message selected) {
        this.selected = selected;
    }

    /*
     * Get facade
     */
    private MessageFacade getFacade() {
        return ejbFacade;
    }

    /**
     * prepare create
     */
    public Message prepareCreate() {
        selected = new Message();
        return selected;
    }

    /**
     * Create
     */
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundles").getString("MessageCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Update
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundles").getString("MessageUpdated"));
    }

    /**
     * Destroy
     */
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundles").getString("MessageDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Get items
     */
    public List<Message> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundles").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundles").getString("PersistenceErrorOccured"));
            }
        }
    }

    /**
     * Get message by id
     */
    public Message getMessage(java.lang.Integer id) {
        return getFacade().find(id);
    }

    /**
     * Get available items
     */
    public List<Message> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    /**
     * Get available items
     */
    public List<Message> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * Converter
     */
    @FacesConverter(forClass = Message.class)
    public static class MessageControllerConverter implements Converter {

        /**
         * Get as object
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MessageController controller = (MessageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "messageController");
            return controller.getMessage(getKey(value));
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
            if (object instanceof Message) {
                Message o = (Message) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Message.class.getName()});
                return null;
            }
        }

    }

}

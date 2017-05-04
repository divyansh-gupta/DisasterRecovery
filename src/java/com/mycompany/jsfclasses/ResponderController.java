package com.mycompany.jsfclasses;

import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.ResponderFacade;

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
@Named("responderController")
@SessionScoped
public class ResponderController implements Serializable {

    @EJB
    private com.mycompany.sessionbeans.ResponderFacade ejbFacade;
    private List<Responder> items = null;
    private Responder selected;

    /**
     *
     */
    public ResponderController() {
    }

    /**
     *
     * @return
     */
    public Responder getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(Responder selected) {
        this.selected = selected;
    }

    /**
     *
     */
    protected void setEmbeddableKeys() {
    }

    /**
     *
     */
    protected void initializeEmbeddableKey() {
    }

    private ResponderFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @return
     */
    public Responder prepareCreate() {
        selected = new Responder();
        initializeEmbeddableKey();
        return selected;
    }

    /**
     *
     */
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ResponderCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     *
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ResponderUpdated"));
    }

    /**
     *
     */
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ResponderDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     *
     * @return
     */
    public List<Responder> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    /**
     *
     * @param id
     * @return
     */
    public Responder getResponder(java.lang.Integer id) {
        return getFacade().find(id);
    }

    /**
     *
     * @return
     */
    public List<Responder> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    /**
     *
     * @return
     */
    public List<Responder> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     *
     */
    @FacesConverter(forClass = Responder.class)
    public static class ResponderControllerConverter implements Converter {

        /**
         *
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
            ResponderController controller = (ResponderController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "responderController");
            return controller.getResponder(getKey(value));
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
         *
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
            if (object instanceof Responder) {
                Responder o = (Responder) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Responder.class.getName()});
                return null;
            }
        }

    }

}

/*
 * Created by Divyansh Gupta on 2017.02.21  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.ResponderFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author cheng
 */
@Named(value = "loginManager")
@SessionScoped
/**
 *
 * @author divyansh
 */
public class LoginManager implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private String username;
    private String password;
    private String errorMessage;

    /*
    The instance variable 'userFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the UserFacade object, after it is instantiated at runtime, into the instance variable 'userFacade'.
     */
    @EJB
    private ResponderFacade responderFacade;
    

    // Constructor method instantiating an instance of LoginManager
    public LoginManager() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get error message
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set error message
     * @param errorMessage error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Get responder facade
     * @return responder facade
     */
    public ResponderFacade getResponderFacade() {
        return responderFacade;
    }

    /*
    ================
    Instance Methods
    ================
     */

    /**
     * Redirect to create account page
     */
    public String createUser() {
        // Redirect to show the CreateAccount page
        return "/CreateAccount.xhtml?faces-redirect=true";
    }

    /**
     * Redirect to reset password page
     */
    public String resetPassword() {
        // Redirect to show the EnterUsername page
        return "/EnterUsername.xhtml?faces-redirect=true";
    }

    /*
    Sign in the user if the entered username and password are valid
    @return "" if an error occurs; otherwise, redirect to show the Profile page
     */
    public String loginUser(boolean triggered) {
        // Obtain the object reference of the User object from the entered username
        Responder user = getResponderFacade().findByUsername(getUsername());

        if (user == null) {
            errorMessage = "Invalid login credential!";
            return "";
        } else {
            String actualUsername = user.getUsername();
            String enteredUsername = getUsername();

            String actualPassword = user.getPassword();
            String enteredPassword = getPassword();

            if (!actualUsername.equals(enteredUsername) || !actualPassword.equals(enteredPassword)) {
                errorMessage = "Invalid login credential!";
                return "";
            }

            errorMessage = "";

            // Initialize the session map with user properties of interest
            initializeSessionMap(user);
            
            if (triggered) {
                return "/Trigger.xhtml?faces-redirect=true";
            }
            // Redirect to show the Profile
            return redirectIfSignedIn(user);
        }
    }
    
    /**
     * If triggered, redirect to map, else redirect to profile
     * @param user responder
     * @return destination page
     */
    public String redirectIfSignedIn(Responder user) {
        if (user.getLocationId().getTriggered()) {
            return "/Map.xhtml?faces-redirect=true";
        }
        return "/Profile.xhtml?faces-redirect=true";
    }

    /*
    Initialize the session map with the user properties of interest,
    namely, first_name, last_name, username, and user_id.
    user_id = primary key of the user entity in the database
     */
    public void initializeSessionMap(Responder user) {
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("name", user.getResponderName());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", username);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }
}

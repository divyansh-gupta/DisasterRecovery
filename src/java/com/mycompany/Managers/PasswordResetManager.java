/*
 * Created by Divyansh Gupta on 2017.02.21  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.ResponderFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;


@Named(value = "passwordResetManager")
@SessionScoped

/**
 * Password reset manager
 * @author cheng
 */
public class PasswordResetManager implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private String username;
    private String message = "";
    private String answer;
    private String password;

    /*
    The instance variable 'userFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the UserFacade object, after it is instantiated at runtime, into the instance variable 'userFacade'.
     */
    @EJB
    private ResponderFacade userFacade;

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
     * Get message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get answer
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set answer
     * @param answer answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get responder facade
     * @return responder facade
     */
    public ResponderFacade getUserFacade() {
        return userFacade;
    }

    /*
    ================
    Instance Methods
    ================
     */
    
    // Process the submitted username
    public String usernameSubmit() {

        // Obtain the object reference of the User object with username
        Responder user = getUserFacade().findByUsername(username);

        if (user == null) {
            message = "Entered username does not exist!";

            // Redirect to show the EnterUsername page
            return "EnterUsername?faces-redirect=true";
        } else {
            // Entered username exists
            message = "";

            // Redirect to show the SecurityQuestion page
            return "SecurityQuestion?faces-redirect=true";
        }
    }

    // Process the submitted answer to the security question
    public String securityAnswerSubmit() {

        // Obtain the object reference of the User object with username
//        Responder user = getUserFacade().findByUsername(username);
//
//        String actualSecurityAnswer = user.getSecurityAnswer();
//        String enteredSecurityAnswer = getAnswer();
//
//        if (actualSecurityAnswer.equals(enteredSecurityAnswer)) {
//            // Answer to the security question is correct
//            message = "";
//
//            // Redirect to show the ResetPassword page
//            return "ResetPassword?faces-redirect=true";
//        } else {
//            // Answer to the security question is wrong
//            message = "Security question answer is incorrect!";
//
//            // Redirect to show the SecurityQuestion page
//            return "SecurityQuestion?faces-redirect=true";
//        }
        return "SecurityQuestion?faces-redirect=true";
    }

    // Return the security question selected by the User object with username
    public String getSecurityQuestion() {

//        // Obtain the object reference of the User object with username
//        Responder user = getUserFacade().findByUsername(username);
//
//        // Obtain the number of the security question selected by the user
//        int questionNumber = user.getSecurityQuestion();
//
//        // Return the security question corresponding to the question number
//        return Constants.QUESTIONS[questionNumber];
        return null;
    }

    // Validate if the entered password matches the entered confirm password
    public void validateInformation(ComponentSystemEvent event) {

        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="password" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("password");

        // Obtain the password entered in the UIInput field with id="password" on the UI
        String entered_password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="confirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");

        // Obtain the confirm password entered in the UIInput field with id="confirmPassword" on the UI
        String entered_confirm_password = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        if (entered_password.isEmpty() || entered_confirm_password.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!entered_password.equals(entered_confirm_password)) {
            message = "Password and Confirm Password must match!";
        } else {
            message = "";
        }
    }

    /**
     * Reset password
     * @return redirect page
     */
    public String resetPassword() {

        if (message == null || message.isEmpty()) {

            // Obtain the object reference of the User object with username
            Responder user = getUserFacade().findByUsername(username);

            try {
                // Reset User object's password
                user.setPassword(password);

                // Update the database
                getUserFacade().edit(user);

                // Initialize the instance variables
                username = message = answer = password = "";

            } catch (EJBException e) {
                message = "Something went wrong while resetting your password, please try again!";

                // Redirect to show the ResetPassword page
                return "ResetPassword?faces-redirect=true";
            }

            // Redirect to show the index (Home) page
            return "index?faces-redirect=true";

        } else {
            // Redirect to show the ResetPassword page
            return "ResetPassword?faces-redirect=true";
        }
    }

}

/*
 * Created by Osman Balci on 2017.01.28  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Item;
import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Responder;
import static com.mycompany.Managers.Constants.DEFAULT_PHOTO_RELATIVE_PATH;
import com.mycompany.sessionbeans.ItemFacade;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.ResponderFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/*
---------------------------------------------------------------------------
JSF Managed Beans annotated with @ManagedBean from javax.faces.bean
is in the process of being deprecated in favor of CDI Beans. Therefore, 
you should use @Named from javax.inject package to designate a managed
bean as a JSF controller class. Contexts and Dependency Injection (CDI) 
beans annotated with @Named is the preferred approach, because CDI 
enables Java EE-wide dependency injection. CDI bean is a bean managed
by the CDI container. 

Within JSF XHTML pages, this bean will be referenced by using the name
'accountManager'. Actually, the default name is the class name starting
with a lower case letter and value = 'accountManager' is optional;
However, we spell it out to make our code more readable and understandable.
---------------------------------------------------------------------------
 */
@Named(value = "accountManager")

/*
The @SessionScoped annotation preserves the values of the AccountManager
object's instance variables across multiple HTTP request-response cycles
as long as the user's established HTTP session is alive.
 */
@SessionScoped

/**
 *
 * @author Balci
 */

/*
--------------------------------------------------------------------------
Marking the AccountManager class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized. 

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer, 
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
--------------------------------------------------------------------------
 */
public class AccountManager implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private String username;
    private String password;
    private String newPassword;

    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;

    private int securityQuestion;
    private String securityAnswer;

    private String email;

    private final String[] listOfStates = Constants.STATES;
    private Map<String, Object> security_questions;
    private String statusMessage;

    private Responder selected;

    /*
    The instance variable 'responderFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the ResponderFacade object, after it is instantiated at runtime, into the instance variable 'responderFacade'.
     */
    @EJB
    private ResponderFacade responderFacade;
    @EJB
    private LocationFacade locationFacade;

    @EJB
    private ItemFacade itemFacade;

    public ItemFacade getItemFacade() {
        return itemFacade;
    }

    public void setItemFacade(ItemFacade itemFacade) {
        this.itemFacade = itemFacade;
    }

    // Constructor method instantiating an instance of AccountManager
    public AccountManager() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String[] getListOfStates() {
        return listOfStates;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zip_code) {
        this.zipcode = zip_code;
    }

    public int getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResponderFacade getResponderFacade() {
        return responderFacade;
    }

    public LocationFacade getLocationFacade() {
        return locationFacade;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
    private Map<String, Object> security_questions;
        String      int
        ---------   ---
        question1,  0
        question2,  1
        question3,  2
            :
    When the user selects a security question, its number (int) is stored; not its String.
    Later, given the number (int), the security question String is retrieved.
     */
    public Map<String, Object> getSecurity_questions() {

        if (security_questions == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. 
                Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings
                in the order they were put into the map.
             */
            security_questions = new LinkedHashMap<>();

            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                security_questions.put(Constants.QUESTIONS[i], i);
            }
        }
        return security_questions;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Responder getSelected() {

        if (selected == null) {
            /*
            user_id (database primary key) was put into the SessionMap
            in the initializeSessionMap() method below or in LoginManager.
             */
            int userPrimaryKey = (int) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("user_id");
            /*
            Given the primary key, obtain the object reference of the Responder
            object and store it into the instance variable selected.
             */
            selected = getResponderFacade().find(userPrimaryKey);
        }
        // Return the object reference of the selected Responder object
        return selected;
    }

    public void setSelected(Responder selectedResponder) {
        this.selected = selectedResponder;
    }

    /*
    ================
    Instance Methods
    ================
     */
    // Return True if a user is logged in; otherwise, return False
    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username") != null;
    }
    
    /**
     *  This function utilizes the google maps to get Latitude and Longitude values
     *  based on a City, State and Zipcode. These values are used to create a Location
     *  Object which is stored in the database. 
     * @param city
     * @param State
     * @param zipcode
     * @return
     * @throws Exception 
     */
    public Location getLatLongFromAddress(String city, String State, String zipcode) throws Exception {
        String api_call_init = Constants.GMAPS_GEOCODE_BASE_URL + city + ", "
                + State + ", " + zipcode + "&"
                + Constants.GMAPS_GEOCODE_API;
        String api_call_final = api_call_init.replaceAll(" ", "+");
        String results = readUrlContent(api_call_final);
        results = "[" + results + "]";
        JSONArray jsonResults = new JSONArray(results);
        JSONArray jsonArrayResults = jsonResults.getJSONObject(0).getJSONArray("results");
        JSONObject resultsObject = jsonArrayResults.getJSONObject(0);

        JSONObject geometry = resultsObject.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");
        System.out.println(jsonArrayResults.toString());
        BigDecimal latitude = location.optBigDecimal("lat", BigDecimal.ZERO);
        BigDecimal longitude = location.optBigDecimal("lng", BigDecimal.ZERO);

        Location checkLocation = getLocationFacade().findByLatLong(latitude, longitude);
        if (checkLocation != null) {
            System.out.println("checkLocation from DB: " + checkLocation);
            return checkLocation;
        } else {
            String locationName = city + ", " + state + " " + zipcode;
            Location newEntry = new Location();
            newEntry.setLocationName(locationName);
            newEntry.setLatitude(latitude);
            newEntry.setLongitude(longitude);
            newEntry.setTriggered(Boolean.FALSE);
            getLocationFacade().create(newEntry);
            System.out.println("newEntry from DB: " + newEntry);
            newEntry.setItemCollection(createInitItemList(newEntry));
            return newEntry;
        }
    }
    
    /**
     * Creates and stores an initial item list for a location. All
     * items start out with a value with 0. A user will have to manually updated
     * the values from another page.
     * @param location
     * @return 
     */
    public Collection<Item> createInitItemList(Location location) {
        Item water = new Item();
        water.setItemType("WATER");
        water.setQuantity(0);
        water.setLocationId(location);

        Item blanket = new Item();
        blanket.setItemType("BLANKETS");
        blanket.setQuantity(0);
        blanket.setLocationId(location);

        Item usd = new Item();
        usd.setItemType("USD");
        usd.setQuantity(0);
        usd.setLocationId(location);

        Item emergencyKits = new Item();
        emergencyKits.setItemType("EMERGENCY_KITS");
        emergencyKits.setQuantity(0);
        emergencyKits.setLocationId(location);

        Item cannedGoods = new Item();
        cannedGoods.setItemType("CANNED_GOODS");
        cannedGoods.setQuantity(0);
        cannedGoods.setLocationId(location);

        Item shelter = new Item();
        shelter.setItemType("SHELTER");
        shelter.setQuantity(0);
        shelter.setLocationId(location);

        getItemFacade().create(shelter);
        getItemFacade().create(usd);
        getItemFacade().create(blanket);
        getItemFacade().create(emergencyKits);
        getItemFacade().create(cannedGoods);
        getItemFacade().create(water);

        Collection<Item> returnList = new ArrayList();
        returnList.add(shelter);
        returnList.add(usd);
        returnList.add(blanket);
        returnList.add(cannedGoods);
        returnList.add(water);
        returnList.add(emergencyKits);

        return returnList;
    }

    /*
    Create a new new user account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String createAccount() throws Exception {

        // First, check if the entered username is already being used
        // Obtain the object reference of a Responder object with username
        Responder aResponder = getResponderFacade().findByUsername(username);

        if (aResponder != null) {
            // A user already exists with the username entered
            username = "";
            statusMessage = "Respondername already exists! Please select a different one!";
            return "";
        }

        // The entered username is available
        if (statusMessage == null || statusMessage.isEmpty()) {
            try {
                // Instantiate a new Responder object
                Responder newResponder = new Responder();

                /*
                Set the properties of the newly created newResponder object with the values
                entered by the user in the AccountCreationForm in CreateAccount.xhtml
                 */
                newResponder.setResponderName(name);
                newResponder.setEmail(email);
                newResponder.setPassword(password);
                Location userLocation = getLatLongFromAddress(city, state, zipcode);
                newResponder.setLocationId(userLocation);
                newResponder.setUsername(username);

                newResponder.setImage(DEFAULT_PHOTO_RELATIVE_PATH);

                getResponderFacade().create(newResponder);
            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while creating user's account! See: " + e.getMessage();
                return "";
            }
            // Initialize the session map for the newly created Responder object
            initializeSessionMap();

            /*
            The Profile page cannot be shown since the new Responder has not signed in yet.
            Therefore, we show the Sign In page for the new Responder to sign in first.
             */
            return "/SignIn.xhtml?faces-redirect=true";
        }
        return "";
    }
/*
     Update the signed-in user's account profile. Return "" if an error occurs;w
     otherwise, upon successful account update, redirect to show the Profile page.
     */
 
    public String updateAccount() throws Exception {
 
        if (statusMessage == null || statusMessage.isEmpty()) {
 
            // Obtain the signed-in user's username
            String user_name = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("username");
 
            // Obtain the object reference of the signed-in user
            Responder editResponder = getResponderFacade().findByUsername(user_name);
 
            try {
                /*
                Set the signed-in user's properties to the values entered by
                the user in the EditAccountProfileForm in EditAccount.xhtml.
                 */
                editResponder.setResponderName(this.selected.getResponderName());
                editResponder.setEmail(this.selected.getEmail());
                editResponder.setLocationName(this.selected.getLocationId().getLocationName());
                editResponder.setUsername(this.selected.getUsername());
//                Location userLocation = getLatLongFromAddress(city, state, zipcode);
//                editResponder.setLocationId(userLocation);
 
                // It is optional for the user to change his/her password
                String new_Password = getNewPassword();
 
                if (new_Password == null || new_Password.isEmpty()) {
                    // Do nothing. The user does not want to change the password.
                } else {
                    editResponder.setPassword(new_Password);
                    // Password changed successfully!
                    // Password was first validated by invoking the validatePasswordChange method below.
                }
 
                // Store the changes in the CloudDriveDB database
                getResponderFacade().edit(editResponder);
 
            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while editing user's profile! See: " + e.getMessage();
                return "";
            }
            // Account update is completed, redirect to show the Profile page.
            return "Profile.xhtml?faces-redirect=true";
        }
        return "";
    }
    
/*
    
    the signed-in user's account. Return "" if an error occurs; otherwise,
    upon successful account deletion, redirect to show the index (home) page.
     */
    public String deleteAccount() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            // Obtain the database primary key of the signed-in Responder object
            int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");

            try {
                // Delete the Responder entity, whose primary key is user_id, from the CloudDriveDB database
                getResponderFacade().deleteResponder(user_id);

                statusMessage = "Your account is successfully deleted!";

            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while deleting user's account! See: " + e.getMessage();
                return "";
            }

            logout();
            return "/SignIn.xhtml?faces-redirect=true";
        }
        return "";
    }
    
    public String getCityName() {
        return this.selected.getLocationId().getLocationName().split(",| ")[0];
    }
    
    public String getStateName() {
        return this.selected.getLocationId().getLocationName().split(",| ")[2];
    }
    
    public String getZipCodeName() {
        return this.selected.getLocationId().getLocationName().split(",| ")[3];
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
            statusMessage = "Password and Confirm Password must match!";
        } else {
            statusMessage = "";
        }
    }

    // Validate the new password and new confirm password
    public void validatePasswordChange(ComponentSystemEvent event) {
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
        // Obtain the object reference of the UIInput field with id="newPassword" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("newPassword");

        // Obtain the new password entered in the UIInput field with id="newPassword" on the UI
        String new_Password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="newConfirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("newConfirmPassword");

        // Obtain the new confirm password entered in the UIInput field with id="newConfirmPassword" on the UI
        String new_ConfirmPassword = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        // It is optional for the user to change his/her password
        if (new_Password.isEmpty() || new_ConfirmPassword.isEmpty()) {
            // Do nothing. The user does not want to change the password.
            return;
        }

        if (!new_Password.equals(new_ConfirmPassword)) {
            statusMessage = "New Password and New Confirm Password must match!";
        } else {
            /*
            REGular EXpression (regex) for validating password strength:
            (?=.{8,31})        ==> Validate the password to be minimum 8 and maximum 31 characters long. 
            (?=.*[!@#$%^&*()]) ==> Validate the password to contain at least one special character. 
                                   (all special characters of the number keys from 1 to 0 on the keyboard)
            (?=.*[A-Z])        ==> Validate the password to contain at least one uppercase letter. 
            (?=.*[a-z])        ==> Validate the password to contain at least one lowercase letter. 
            (?=.*[0-9])        ==> Validate the password to contain at least one number from 0 to 9.
             */
            String regex = "^(?=.{8,31})(?=.*[!@#$%^&*()])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";

            if (!new_Password.matches(regex)) {
                statusMessage = "The password must be minimum 8 "
                        + "characters long, contain at least one special character, "
                        + "contain at least one uppercase letter, "
                        + "contain at least one lowercase letter, "
                        + "and contain at least one number 0 to 9.";
            } else {
                statusMessage = "";
            }
        }
    }

    // Validate if the entered password and confirm password are correct
    public void validateResponderPassword(ComponentSystemEvent event) {
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
            statusMessage = "Password and Confirm Password must match!";
        } else {
            // Obtain the logged-in Responder's username
            String user_name = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("username");

            // Obtain the object reference of the signed-in Responder object
            Responder user = getResponderFacade().findByUsername(user_name);

            if (entered_password.equals(user.getPassword())) {
                // entered password = signed-in user's password
                statusMessage = "";
            } else {
                statusMessage = "Incorrect Password!";
            }
        }
    }

    // Initialize the session map for the Responder object
    public void initializeSessionMap() {

        // Obtain the object reference of the Responder object
        Responder user = getResponderFacade().findByUsername(getUsername());

        // Put the Responder's object reference into session map variable user
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user", user);

        // Put the Responder's database primary key into session map variable user_id
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }

    /*
    UIComponent is the base class for all user interface components in JavaServer Faces. 
    The set of UIComponent instances associated with a particular request and response are organized into
    a component tree under a UIViewRoot that represents the entire content of the request or response.
     
    @param components: UIComponent instances associated with the current request and response
    @return True if entered password is correct; otherwise, return False
     */
    private boolean correctPasswordEntered(UIComponent components) {

        // Obtain the object reference of the UIInput field with id="verifyPassword" on the UI
        UIInput uiInputVerifyPassword = (UIInput) components.findComponent("verifyPassword");

        // Obtain the verify password entered in the UIInput field with id="verifyPassword" on the UI
        String verifyPassword = uiInputVerifyPassword.getLocalValue()
                == null ? "" : uiInputVerifyPassword.getLocalValue().toString();

        if (verifyPassword.isEmpty()) {
            statusMessage = "Please enter a password!";
            return false;

        } else if (verifyPassword.equals(password)) {
            // Correct password is entered
            return true;

        } else {
            statusMessage = "Invalid password entered!";
            return false;
        }
    }

    // Show the Home page
    public String showHomePage() {
        return "/SignIn?faces-redirect=true";
    }

    // Show the Profile page
    public String showProfile() {
        return "/Profile?faces-redirect=true";
    }

    public String logout() {

        // Clear the logged-in Responder's session map
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

        // Reset the logged-in Responder's properties
        username = password = "";
        name = "";
        address1 = address2 = city = state = zipcode = "";
        securityQuestion = 0;
        securityAnswer = "";
        email = statusMessage = "";

        // Invalidate the logged-in Responder's session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Redirect to show the index (Home) page
        return "/SignIn.xhtml?faces-redirect=true";
    }

    public String responderPhoto() {

        // Obtain the signed-in user's username
        String usernameOfSignedInResponder = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("username");

        // Obtain the object reference of the signed-in user
        Responder signedInResponder = getResponderFacade().findByUsername(usernameOfSignedInResponder);

        // Obtain the id (primary key in the database) of the signedInResponder object
        Integer userId = signedInResponder.getId();

        String photo = getResponderFacade().findPhotosByResponderID(userId);

        /*
        In glassfish-web.xml file, we designated the '/CloudStorage/' directory as the
        Alternate Document Root with the following statement:
        
        <property name="alternatedocroot_1" value="from=/CloudStorage/* dir=/Responders/Balci" />
        
        in Constants.java file, we defined the relative photo file path as
        
        public static final String PHOTOS_RELATIVE_PATH = "CloudStorage/PhotoStorage/";
        
        Thus, JSF knows that 'CloudStorage/' is the document root directory.
         */
        if (photo.equals(Constants.DEFAULT_PHOTO_RELATIVE_PATH)) {
            return photo;
        }

        String relativePhotoFilePath = Constants.PHOTOS_RELATIVE_PATH + photo;

        System.out.println(relativePhotoFilePath);
        System.out.println(Constants.PHOTOS_RELATIVE_PATH);
        return relativePhotoFilePath;
    }

    /*
    Delete all of the files that belong to the Responder
    whose object reference is userId
     */
    public String readUrlContent(String webServiceURL) throws Exception {
        /*
        reader is an object reference pointing to an object instantiated from the BufferedReader class.
        Currently, it is "null" pointing to nothing.
         */
        BufferedReader reader = null;

        try {
            // Create a URL object from the webServiceURL given
            URL url = new URL(webServiceURL);
            /*
            The BufferedReader class reads text from a character-input stream, buffering characters
            so as to provide for the efficient reading of characters, arrays, and lines.
             */
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Create a mutable sequence of characters and store its object reference into buffer
            StringBuilder buffer = new StringBuilder();

            // Create an array of characters of size 10240
            char[] chars = new char[10240];

            int numberOfCharactersRead;
            /*
            The read(chars) method of the reader object instantiated from the BufferedReader class
            reads 10240 characters as defined by "chars" into a portion of a buffered array.

            The read(chars) method attempts to read as many characters as possible by repeatedly
            invoking the read method of the underlying stream. This iterated read continues until
            one of the following conditions becomes true:

                (1) The specified number of characters have been read, thus returning the number of characters read.
                (2) The read method of the underlying stream returns -1, indicating end-of-file, or
                (3) The ready method of the underlying stream returns false, indicating that further input requests would block.

            If the first read on the underlying stream returns -1 to indicate end-of-file then the read(chars) method returns -1.
            Otherwise the read(chars) method returns the number of characters actually read.
             */
            while ((numberOfCharactersRead = reader.read(chars)) != -1) {
                buffer.append(chars, 0, numberOfCharactersRead);
            }

            // Return the String representation of the created buffer
            return buffer.toString();

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}

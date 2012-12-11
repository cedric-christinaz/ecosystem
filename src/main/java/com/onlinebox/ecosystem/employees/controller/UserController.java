package com.onlinebox.ecosystem.employees.controller;

import com.onlinebox.ecosystem.employees.bean.AccessLevelManagerBean;
import com.onlinebox.ecosystem.employees.bean.UserJobManagerBean;
import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author cedric
 */
@ManagedBean
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private UserManagerBean userBean;
    @EJB
    private AccessLevelManagerBean accessLevelBean;
    @EJB
    private UserJobManagerBean jobBean;
    private List<User> users; //contain the list of users to be displayed (active users or disabled users)
    private List<User> activeUsers; //contain the list of all active users
    private List<User> disabledUsers; //contain the list of all active users
    private User user; //contain the new user to add or the current user to edit
    private String confirmPassword;
    private boolean isActiveUsers; //true is view shows the active users, false if view shows the disabled users
      
    private StreamedContent userPictureTemp;//contain the new uploaded picture (but not saved at the moment because the save is done when closing the dialog.
    private boolean displayTempPicture;//TRUE if the new uploaded file must be displayed, FALSE if the current picuture must be displayed.
    /**
     * Creates a new instance of UserController.
     */
    public UserController() {
        //Important to create a new empty user. Otherwise, the JSF page
        //links a null object.
        user = new User();
    }

    /**
     * Initialization method that is called at the creation of the managed bean. This method loads all the users.
     */
    @PostConstruct
    void init() {
        displayTempPicture = false;
        activeUsers = userBean.getAllActiveUsers();
        disabledUsers = userBean.getAllDisabledUsers();
        users = activeUsers;
        isActiveUsers = true;
    }

    /**
     * This method returns all the users of the systems..
     *
     * @return
     */
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * This method returns the currently selected user if we are editing an existing user or the new user if we are adding an new user.
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * This method is called by the JSF Page users_view.xhtml to delete an existing user. The user must be disabled.
     */
    public void deleteUser() {
        System.out.println("deleteUser");
        if (user != null && user.getId() > 0) {
            userBean.delete(user);
            users.remove(user);
            disabledUsers.remove(user);
        }
    }

    /**
     * This method is called by the JSF Page users_view.xhtml to disable an existing user.
     */
    public void disableUser() {
        System.out.println("disableUser");
        if (user != null && user.getId() > 0) {
            try {
                userBean.archive(user);
                disabledUsers.add(user);
                activeUsers.remove(user);
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This method is called by the JSF Page users_view.xhtml to enable an existing disabled user.
     */
    public void enableUser() {
        System.out.println("enableUser");
        if (user != null && user.getId() > 0) {
            try {
                userBean.unArchive(user);
                activeUsers.add(user);
                disabledUsers.remove(user);
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This method is called by the JSF Page users_view.xhtml to create a new user or to edit an exising user.
     */
    public void saveUser() {
        System.out.println("saveUser()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (user != null) {
            try {
                if (user.getId() > 0) {
                    //Modify an existing user because id already contains a valid value (>0)
                    updateUser();
                } else {
                    //New user because id does not contain a valid value (<=0=
                    if (user.getPassword().equals(this.confirmPassword)) {
                        createUser();
                    } else {
                        message = "The passwords don't match.";
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the user.";
            }
        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetUser();
            this.resetUserDetailsDialog();
        }
        context.addCallbackParam("isOk", isOk);
    }

    /**
     * Reset the current selected user, so that when a popup is opened, there is now old data displayed.
     */
    public void resetUser() {
        System.out.println("resetUser()");
        user = new User();
    }

    /**
     * Switch to disabled/active users view
     */
    public void showActiveDisabledUsers() {
        if (isActiveUsers) {
            //Current view displayed : active users -> must switch to inactive users
            users = this.disabledUsers;
            isActiveUsers = false;
        } else {
            //Current view displayed : disabled users -> must switch to active users
            users = this.activeUsers;
            isActiveUsers = true;
        }

        resetUser(); //in every case, reset the current selected user
    }

    /*
     * Private method that creates a new user. It is called by the method saveUser().
     */
    private void createUser() throws Exception {
        user.setAccessLevel(accessLevelBean.get(user.getAccessLevel().getId()));
        user.setJob(jobBean.get(user.getJob().getId()));

        user = this.userBean.create(user);
        activeUsers.add(user);
    }

    /*
     * Private method that modifies an existing user. It is called by the method saveUser().
     */
    private void updateUser() throws Exception {
        user = this.userBean.update(user);
    }

    /**
     * This method allows to change dynamically the label of the link to switch from the active users view to the inactive users view.
     *
     * @return
     */
    public String getButtonSwitchViewLabel() {
        String label;
        if (this.isActiveUsers) {
            label = "Show disabled users (" + disabledUsers.size() + ")";
        } else {
            label = "Show active users (" + activeUsers.size() + ")";
        }
        return label;
    }

    /**
     * Getter that indicates if the user view shows the active users or the disabled users.
     *
     * @return true if view shows active users, false if view shows disabled users.
     */
    public boolean isIsActiveUsers() {
        return isActiveUsers;
    }

    /**
     * Setter that allows to set which view is shown (active users or disabled users)
     *
     * @param isActiveUsers true for active users, false for disabled users.
     */
    public void setIsActiveUsers(boolean isActiveUsers) {
        this.isActiveUsers = isActiveUsers;
    }

    /**
     * Handle the upload of the picture of the user.
     *
     * @param event
     */
    public void handlePictureUpload(FileUploadEvent event) {
        System.out.println("handlePictureUpload");

        UploadedFile file = event.getFile();
        if (file != null) {
            try {
                user.setImage(IOUtils.toByteArray(file.getInputstream()));

                InputStream is = new ByteArrayInputStream(user.getImage());
                this.userPictureTemp = new DefaultStreamedContent(is, "image/png");

                this.displayTempPicture = true;

            } catch (IOException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This method returns the picture of the user specified in the parameter idEmployee.
     *
     * @return the picture of the user.
     */
    public StreamedContent getUserPicture() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (this.displayTempPicture) {
            
            return userPictureTemp;
        } 
        else {
            //Get the parameter idEmployee
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String sIdEmployee = params.get("idEmployee");

            if (sIdEmployee != null) {
                int idEmployee = Integer.parseInt(sIdEmployee);
                if (idEmployee > 0) {
                    User user = userBean.get(idEmployee);

                    if (user.getImage() != null && user.getImage().length > 0) {
                        //Ok, image exists, return it
                        InputStream is = new ByteArrayInputStream(user.getImage());
                        return new DefaultStreamedContent(is, "image/png");
                    }
                }
            }
        }
        //Other case (user has no picture), return default image
        return new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/defaultuser.png"), "image/png");

    }

    /**
     * Getter that return the new picture to display. For the moment, this picture
     * is not saved in the database.
     * @return 
     */
    public StreamedContent getUserPictureTemp() {
        return userPictureTemp;
    }

    /**
     * Setter that allows to set the new picture to display. For the moment, this picture
     * is not saved in the database.
     * @param userPictureTemp 
     */
    public void setUserPictureTemp(StreamedContent userPictureTemp) {
        this.userPictureTemp = userPictureTemp;
    }

    /**
     * This method return TRUE if the new picture should be display or FALSE if the existing picture must be displayed.
     * @return 
     */
    public boolean isDisplayTempPicture() {
        return displayTempPicture;
    }

   
    public void setDisplayTempPicture(boolean displayTempPicture) {
        this.displayTempPicture = displayTempPicture;
    }
    
    /**
     * This method is called when the dialog box to edit a user is closed. We reset
     * some values for the next time the dialog will be opened.
     */
    public void resetUserDetailsDialog(){
        this.displayTempPicture = false;
    }
}

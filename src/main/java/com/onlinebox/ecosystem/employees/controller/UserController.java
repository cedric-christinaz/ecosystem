/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.employees.controller;

import com.onlinebox.ecosystem.employees.bean.AccessLevelManagerBean;
import com.onlinebox.ecosystem.employees.bean.UserJobManagerBean;
import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author cedric
 */
@ManagedBean
@ViewScoped
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

    /**
     * Creates a new instance of UserController.
     */
    public UserController() {
        //Important to create a new empty user. Otherwise, the JSF page
        //links a null object.
        user = new User();
    }

    /**
     * Initialization method that is called at the creation of the managed bean.
     * This method loads all the users.
     */
    @PostConstruct
    void init() {
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
     * This method returns the currently selected user if we are editing an
     * existing user or the new user if we are adding an new user.
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
     * This method is called by the JSF Page users_view.xhtml to delete an
     * existing user. The user must be disabled.
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
     * This method is called by the JSF Page users_view.xhtml to disable an
     * existing user.
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
     * This method is called by the JSF Page users_view.xhtml to enable an
     * existing disabled user.
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
     * This method is called by the JSF Page users_view.xhtml to create a new
     * user or to edit an exising user.
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
        }
        context.addCallbackParam("isOk", isOk);
    }

    /**
     * Reset the current selected user, so that when a popup is opened, there is
     * now old data displayed.
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
     * This method allows to change dynamically the label of the link to switch
     * from the active users view to the inactive users view.
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
     * Getter that indicates if the user view shows the active users or the
     * disabled users.
     *
     * @return true if view shows active users, false if view shows disabled
     * users.
     */
    public boolean isIsActiveUsers() {
        return isActiveUsers;
    }

    /**
     * Setter that allows to set which view is shown (active users or disabled
     * users)
     *
     * @param isActiveUsers true for active users, false for disabled users.
     */
    public void setIsActiveUsers(boolean isActiveUsers) {
        this.isActiveUsers = isActiveUsers;
    }
}

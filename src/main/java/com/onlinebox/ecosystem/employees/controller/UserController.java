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
    private List<User> users; //contain the list of all users
    private User user; //contain the new user to add or the current user to edit
    private String confirmPassword;

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
        users = userBean.getAll();

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
     * existing user.
     */
    public void deleteUser() {
        System.out.println("deleteUser");
        if (user != null) {
            userBean.delete(user);
            users.remove(user);
        }
    }

    /**
     * This method is called by the JSF Page users_view.xhtml to create a new 
     * user or to edit an exising user.
     */
    public void saveUser() {
        System.out.println("saveUser()");
        String message = "";
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
        }
    }
    
    /*
     * Private method that creates a new user. It is called by the method saveUser().
     */
    private void createUser() throws Exception {
        user.setAccessLevel(accessLevelBean.get(user.getAccessLevel().getId()));
        user.setJob(jobBean.get(user.getJob().getId()));

        user = this.userBean.create(user);
        users.add(user);
        user = new User();
    }

     /*
     * Private method that modifies an existing user. It is called by the method saveUser().
     */
    private void updateUser() throws Exception {
        user = this.userBean.update(user);
        user = new User();
    }
}

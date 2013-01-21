package com.onlinebox.ecosystem.controller;

import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * This managed bean is responsible to logout correctly users. The session is destroyed
 *
 * @author cedric
 */
@ManagedBean
@SessionScoped
public class UserSessionController implements Serializable {

    @EJB
    private UserManagerBean userBean;
    private User user;

    /**
     * Creates a new instance of LogoutController
     */
    public UserSessionController() {
    }

    @PostConstruct
    void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String username = context.getUserPrincipal().getName();
        user = userBean.getByUsername(username);

        user.setLastLogin(new Date());
        try {
            user = userBean.update(user);
        } catch (Exception ex) {
            //Should never have problem here.
            Logger.getLogger(UserSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * This method is responsible to logout correctly the user. The session is destroyed and the user redirect to the login page.
     *
     * @return
     */
    public String logout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        user = null;
        return "dashboard_view.xhtml?faces-redirect=true";

    }
}

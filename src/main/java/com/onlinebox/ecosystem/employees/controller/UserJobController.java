package com.onlinebox.ecosystem.employees.controller;

import com.onlinebox.ecosystem.employees.bean.UserJobManagerBean;
import com.onlinebox.ecosystem.employees.entity.UserJob;
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
public class UserJobController {

    private List<UserJob> userJobs;
    private UserJob userJob;
    @EJB
    private UserJobManagerBean userJobBean;

    /**
     * Creates a new instance of UserJobController
     */
    public UserJobController() {
        userJob = new UserJob();
    }

    @PostConstruct
    public void init() {
        userJobs = userJobBean.getAll();
    }

    public List<UserJob> getUserJobs() {
        return userJobs;
    }

    /**
     * This method is called by the JSF Page parameters_view.xhtml to create a new user job or to edit an exising user job.
     */
    public void saveUserJob() {
        System.out.println("saveUserJob()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (userJob != null) {
            try {
                if (userJob.getId() > 0) {
                    //Modify an existing user job because id already contains a valid value (>0)
                    updateUserJob();
                } else {
                    //New user job because id does not contain a valid value (<=0)
                    createUserJob();
                }
            } catch (Exception ex) {
                Logger.getLogger(UserJobController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the job.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetUserJob();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new user job. It is called by the method saveUserJob().
     */
    private void createUserJob() throws Exception {
        userJob = this.userJobBean.create(userJob);
        userJobs.add(userJob);
    }

    /*
     * Private method that modifies an existing user job. It is called by the method saveUserJob().
     */
    private void updateUserJob() throws Exception {
        userJob = this.userJobBean.update(userJob);
    }

    /**
     * Reset the current selected user job, so that when a popup is opened, there is now old data displayed.
     */
    public void resetUserJob() {
        System.out.println("resetUserJob()");
        userJob = new UserJob();
    }

    public void deleteUserJob() {
        if (userJob != null && userJob.getId() > 0) {
            userJobBean.delete(userJob);
            userJobs.remove(userJob);
        }
    }

    public UserJob getUserJob() {
        return userJob;
    }

    public void setUserJob(UserJob userJob) {
        this.userJob = userJob;
    }
}

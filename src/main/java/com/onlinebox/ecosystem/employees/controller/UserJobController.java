/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.employees.controller;

import com.onlinebox.ecosystem.employees.bean.UserJobManagerBean;
import com.onlinebox.ecosystem.employees.entity.UserJob;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cedric
 */
@ManagedBean
@RequestScoped
public class UserJobController {
    
    
    @EJB
    private UserJobManagerBean userJobBean;
    
    /**
     * Creates a new instance of UserJobController
     */
    public UserJobController() {
    }
    
    
    public List<UserJob> getUserJobs() {
        return userJobBean.getAll();
    }
    
}

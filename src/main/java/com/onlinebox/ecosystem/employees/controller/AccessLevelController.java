/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.employees.controller;

import com.onlinebox.ecosystem.employees.bean.AccessLevelManagerBean;
import com.onlinebox.ecosystem.employees.entity.AccessLevel;
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
public class AccessLevelController {

    @EJB
    private AccessLevelManagerBean accessLevelBean;
      
    /**
     * Creates a new instance of AccessLevelController
     */
    public AccessLevelController() {
    }

    
    public List<AccessLevel> getAccessLevels() {
        return accessLevelBean.getAll();
    }

   
    
    
    
}

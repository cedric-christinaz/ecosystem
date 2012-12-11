/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.util.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cedric
 */
@ManagedBean
@RequestScoped
public class UtilController {

    /**
     * Creates a new instance of UtilController
     */
    public UtilController() {
    }
    
    public String getTodayDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(new Date());
    }
    
    public String getCurrentYear(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(new Date());
    }
    
    
}

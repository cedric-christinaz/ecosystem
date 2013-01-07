
package com.onlinebox.ecosystem.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 *
 * @author cedric
 */
@ManagedBean
@RequestScoped
public class DashboardController {

    private DashboardModel model;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {

        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
     
   

        column1.addWidget("timeReporting");
    


        model.addColumn(column1);


    }

    public DashboardModel getModel() {
        return model;
    }
}

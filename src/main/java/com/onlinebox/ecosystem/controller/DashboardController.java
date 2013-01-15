
package com.onlinebox.ecosystem.controller;

import com.onlinebox.ecosystem.projects.bean.ProjectManagerBean;
import com.onlinebox.ecosystem.projects.bean.TaskManagerBean;
import com.onlinebox.ecosystem.projects.entity.Project;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author cedric
 */
@ManagedBean
@RequestScoped
public class DashboardController implements Serializable{

//    private DashboardModel model;
    
    @EJB
    private ProjectManagerBean projectBean;
    @EJB
    private TaskManagerBean taskBean;
    
    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {

//        model = new DefaultDashboardModel();
//        DashboardColumn column1 = new DefaultDashboardColumn();
//     
//   
//
//        column1.addWidget("timeReporting");
//    
//
//
//        model.addColumn(column1);


    }

//    public DashboardModel getModel() {
//        return model;
//    }
    
    public List<Project> getAllLateProjects(){
        return projectBean.getAllLate();
    }

    public PieChartModel getPieModelTeamPerformanceCurrentMonth() {
        List taskTypes = taskBean.getWorkedHoursByTaskTypeCurrentMonth();
        PieChartModel pieModelTeamPerformance = new PieChartModel();  
        
        for(int i=0; i<taskTypes.size(); i++){
            Object[] o = (Object[]) taskTypes.get(i);
            pieModelTeamPerformance.set((String)o[0], (Long)o[1]);
        }
        
        return pieModelTeamPerformance;
    }
    
    public PieChartModel getPieModelTeamPerformanceLastMonth() {
        List taskTypes = taskBean.getWorkedHoursByTaskTypeLastMonth();
        PieChartModel pieModelTeamPerformance = new PieChartModel();  
        
        for(int i=0; i<taskTypes.size(); i++){
            Object[] o = (Object[]) taskTypes.get(i);
            pieModelTeamPerformance.set((String)o[0], (Long)o[1]);
        }
        
        return pieModelTeamPerformance;
    }
    
    
}

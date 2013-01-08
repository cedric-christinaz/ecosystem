
package com.onlinebox.ecosystem.projects.controller;

import com.onlinebox.ecosystem.projects.bean.TaskTypeManagerBean;
import com.onlinebox.ecosystem.projects.entity.TaskType;
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
public class TaskTypeController {

    @EJB
    private TaskTypeManagerBean taskTypeBean;
    
    private List<TaskType> taskTypes;
    private TaskType taskType;
    
    /**
     * Creates a new instance of TaskTypeController
     */
    public TaskTypeController() {
        taskType = new TaskType();
    }
    
    @PostConstruct
    public void init(){
        taskTypes = taskTypeBean.getAll();
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    
     /**
     * This method is called by the JSF Page parameters_view.xhtml to create a new task type or to edit an exising task tye.
     */
    public void saveTaskType() {
        System.out.println("saveTaskType()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (taskType != null) {
            try {
                if (taskType.getId() > 0) {
                    //Modify an existing task type because id already contains a valid value (>0)
                    updateTaskType();
                } else {
                    //New task type because id does not contain a valid value (<=0)
                    createTaskType();
                }
            } catch (Exception ex) {
                Logger.getLogger(TaskTypeController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the task type.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetTaskType();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new task type. It is called by the method saveTaskType().
     */
    private void createTaskType() throws Exception {
        taskType = this.taskTypeBean.create(taskType);
        taskTypes.add(taskType);
    }

    /*
     * Private method that modifies an existing task type. It is called by the method saveTaskType().
     */
    private void updateTaskType() throws Exception {
        taskType = this.taskTypeBean.update(taskType);
    }

    /**
     * Reset the current selected task type, so that when a popup is opened, there is now old data displayed.
     */
    public void resetTaskType() {
        System.out.println("resetTaskType()");
        taskType = new TaskType();
    }

    public void deleteTaskType() {
        if (taskType != null && taskType.getId() > 0) {
            taskTypeBean.delete(taskType);
            taskTypes.remove(taskType);
        }
    }
 
    
}

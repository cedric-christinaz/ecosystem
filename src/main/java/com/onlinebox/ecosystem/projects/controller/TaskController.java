package com.onlinebox.ecosystem.projects.controller;

import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.bean.TaskManagerBean;
import com.onlinebox.ecosystem.projects.bean.TaskTypeManagerBean;
import com.onlinebox.ecosystem.projects.entity.Task;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author cedric
 */
@ManagedBean
@ViewScoped
public class TaskController {

    @ManagedProperty(value = "#{userSessionController.user}")
    private User user;
    @EJB
    private TaskManagerBean taskBean;
    @EJB
    private TaskTypeManagerBean taskTypeBean;
    @EJB
    private UserManagerBean userBean;
    private List<Task> tasks;
    private Task task;
    private User selectedUser;

    /**
     * Creates a new instance of TaskController
     */
    public TaskController() {
        task = new Task();
        task.setTaskDate(new Date());
    }

    /**
     * Initialization method that is called at the creation of the managed bean. This method loads all the tasks of the users.
     */
    @PostConstruct
    void init() {
        tasks = taskBean.getByUser(user);
        selectedUser = user;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * This method is called by the JSF Page dashboard_view.xhtml to create a new task or to edit an exising task.
     */
    public void saveTask() {
        System.out.println("saveTask()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (task != null) {
            try {
                if (task.getId() > 0) {
                    //Modify an existing task because id already contains a valid value (>0)
                    updateTask();
                } else {
                    //New task because id does not contain a valid value (<=0)
                    createTask();
                }
            } catch (Exception ex) {
                Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the task.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetTask();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new task. It is called by the method saveTask().
     */
    private void createTask() throws Exception {
        task.setUser(selectedUser);
        task.setTaskType(taskTypeBean.get(task.getTaskType().getId()));
        task = this.taskBean.create(task);
        tasks.add(task);
    }

    /*
     * Private method that modifies an existing task. It is called by the method saveTask().
     */
    private void updateTask() throws Exception {
        task.setTaskType(taskTypeBean.get(task.getTaskType().getId()));
        task = this.taskBean.update(task);
    }

    /**
     * Reset the current selected task, so that when a popup is opened, there is now old data displayed.
     */
    public void resetTask() {
        System.out.println("resetTask()");
        task = new Task();
        task.setTaskDate(new Date());
    }

    /**
     * This method is called by the JSF Page timetracking_view.xhtml to delete an existing task.
     */
    public void deleteTask() {
        System.out.println("deleteTask");
        if (task != null && task.getId() > 0) {
            taskBean.delete(task);
            tasks.remove(task);
        }
    }

    public List<User> getAllUsers() {
        return userBean.getAll();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void handleUserSelection() {
        tasks = taskBean.getByUser(selectedUser);
    }
}

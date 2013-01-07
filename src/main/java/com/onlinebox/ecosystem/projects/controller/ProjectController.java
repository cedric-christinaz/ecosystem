
package com.onlinebox.ecosystem.projects.controller;

import com.onlinebox.ecosystem.clients.entity.Company;
import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.bean.ProjectManagerBean;
import com.onlinebox.ecosystem.projects.bean.TaskTypeManagerBean;
import com.onlinebox.ecosystem.projects.entity.Project;
import com.onlinebox.ecosystem.projects.entity.Task;
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
public class ProjectController {

    @ManagedProperty(value="#{userSessionController.user}")
    private User user;
    @EJB
    private ProjectManagerBean projectBean;
    @EJB
    private TaskTypeManagerBean taskTypeBean;
    private List<Project> projects; //contain the list of projects to be displayed 
    private Project project; //contain the new project to add or the current project to edit
    private Task task;

    /**
     * Creates a new instance of ProjectController
     */
    public ProjectController() {
        //Important to create a new empty project and task. Otherwise, the JSF page
        //links a null object.
        project = new Project();
        task = new Task();
        project.setCompany(new Company());
    }

    /**
     * Initialization method that is called at the creation of the managed bean. This method loads all the projects.
     */
    @PostConstruct
    void init() {
        projects = projectBean.getAll();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * This method is called by the JSF Page projects_view.xhtml to create a new project or to edit an exising project.
     */
    public void saveProject() {
        System.out.println("saveProject()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (project != null) {
            try {
                if (project.getId() > 0) {
                    //Modify an existing project because id already contains a valid value (>0)
                    updateProject();
                } else {
                    //New project because id does not contain a valid value (<=0)
                    createProject();
                }
            } catch (Exception ex) {
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the project.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetProject();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new company. It is called by the method saveCompany().
     */
    private void createProject() throws Exception {
        project = this.projectBean.create(project);
        projects.add(project);
    }

    /*
     * Private method that modifies an existing company. It is called by the method saveCompany().
     */
    private void updateProject() throws Exception {
        project = this.projectBean.update(project);
    }

    /**
     * Reset the current selected project, so that when a popup is opened, there is now old data displayed.
     */
    public void resetProject() {
        System.out.println("resetProject()");
        project = new Project();
        project.setCompany(new Company());
    }

    /**
     * This method is called when the dialog box to edit a project is closed. We reset some values for the next time the dialog will be opened.
     */
    public void resetProjectDetailsDialog() {
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * This method is called by the JSF Page projects_view.xhtml to add a new task from the edit project popup.
     */
    public void addNewTask() {
        System.out.println("addNewProject");
        if (task != null) {
            task.setTaskType(taskTypeBean.get(task.getTaskType().getId()));
            task.setProject(project);
            task.setUser(user);
            user.getTasks().add(task);
            project.getTasks().add(task);
        }
    }

    /**
     * This method is called by the JSF Page projects_view.xhtml to delete an existing project.
     */
    public void deleteProject() {
        System.out.println("deleteProject");
        if (project != null && project.getId() > 0) {
            projectBean.delete(project);
            projects.remove(project);
        }
    }

    /**
     * This method computes the total of worked hours for the project specified in parameter.
     * @param pProject
     * @return number of worked hours for the project
     */
    public int getDoneHours(Project pProject) {
        int done = 0;
        if (pProject != null && pProject.getTasks() != null) {
            for (Task task : pProject.getTasks()) {
                done += task.getDuration();
            }
        }
        return done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * This methods returns a list of Project that match the specified parameter.
     * @param query  part of the name of the projects to return
     * @return list of project that match the search   
     */
    public List<Project> searchProject(String query){
         return projectBean.findAllByName(query);
    }
    
}

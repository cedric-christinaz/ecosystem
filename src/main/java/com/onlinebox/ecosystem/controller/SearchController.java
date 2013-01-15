package com.onlinebox.ecosystem.controller;

import com.onlinebox.ecosystem.clients.bean.CompanyManagerBean;
import com.onlinebox.ecosystem.clients.entity.Company;
import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.bean.ProjectManagerBean;
import com.onlinebox.ecosystem.projects.entity.Project;
import com.onlinebox.ecosystem.util.entity.IEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 * This managed bean manages the search. The web application has just one simple input text to search over all searchable information. Searchable information
 * are Projects, Clients and Employees
 *
 * @author cedric
 */
@ManagedBean
@RequestScoped
public class SearchController implements Serializable {

    @EJB
    private ProjectManagerBean projectBean;
    @EJB
    private CompanyManagerBean clientBean;
    @EJB
    private UserManagerBean userBean;

    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
    }

    /**
     * This method searchs in the Projects, the Clients and the Employees.
     *
     * @param query - the search criteria
     * @return
     */
    public List<IEntity> search(String query) {
        List<IEntity> res = projectBean.findAllByName(query);
        res.addAll(clientBean.findAllByName(query));
        res.addAll(userBean.findAllByName(query));
        return res;

    }

    /**
     * This method is called when the user chooses a result. It will be redirected
     * to the correct page basing on the result. For example, if the result is a project,
     * then the page projects_view.xhtml will be displayed.
     * @param event 
     */
    public void handleSelect(SelectEvent event) {
        IEntity entity = (IEntity) event.getObject();

        //Get the application name
        String applicationName = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String pageName = "";
        try {
            //Redirect to the correct page (Projects page if the search result is a project, ...) with the ID of the element found.

            if (entity instanceof Project) {
                pageName = "projects";
            } else {
                if (entity instanceof Company) {
                    pageName = "clients";
                } else {
                    if (entity instanceof User) {
                        pageName = "users";
                    }
                }
            }

            //Redirect to the correct page only if a valid result has been found.
            if (!pageName.equals("")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(applicationName + "/faces/protected/" + pageName + "_view.xhtml?id=" + entity.getId());
            }

        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}

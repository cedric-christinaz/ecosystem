package com.onlinebox.ecosystem.util.controller;

import com.onlinebox.ecosystem.clients.bean.CompanyManagerBean;
import com.onlinebox.ecosystem.clients.entity.Company;
import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.bean.ProjectManagerBean;
import com.onlinebox.ecosystem.projects.entity.Project;
import com.onlinebox.ecosystem.util.entity.IEntity;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * This converter converts a IEntity to its ID
 *
 * @author cedric
 */
@ManagedBean
public class SearchConverter implements Converter {

    @EJB
    ProjectManagerBean projectBean;
    @EJB
    CompanyManagerBean clientBean;
    @EJB
    UserManagerBean userBean;

    /**
     * Creates a new instance of ProjectConverter
     */
    public SearchConverter() {
    }

    /**
     * This method takes the specfied id and return its corresponding entity. The id is prefix with
     * - p if the entity is a Project 
     * - c if the entity is a Client
     * - u if the entity is an Employee
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        IEntity entity = null;
        String prefix = "";
        try {
            if (value != null && !value.equals("")) {
                prefix = value.substring(0, 1);
                long id = Integer.parseInt(value.substring(1));

                if (prefix.equals("p")) {
                    entity = projectBean.get(id);
                } else {
                    if (prefix.equals("c")) {
                        entity = clientBean.get(id);
                    }
                    else{
                        if(prefix.equals("u")){
                            entity = userBean.get(id);
                        }
                    }
                }


            }
        } catch (Exception e) {
            throw new ConverterException("Entity does not exist.");
        }
        return entity;
    }

    /**
     * Takes a IEntity and returns its id. The id is prefix with: - p if the entity is a Project - c if the entity is a Client - u if the entity is an Employee
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String id = "";
        if (value != null) {
            id = String.valueOf(((IEntity) value).getId());
            if (value instanceof Project) {
                id = "p" + id;
            } else {
                if (value instanceof Company) {
                    id = "c" + id;
                } else {
                    if (value instanceof User) {
                        id = "u" + id;
                    }
                }
            }
        }
        return id;
    }
}

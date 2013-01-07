package com.onlinebox.ecosystem.util.controller;

import com.onlinebox.ecosystem.projects.bean.ProjectManagerBean;
import com.onlinebox.ecosystem.projects.entity.Project;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * This converter converts a Project object to string (its id).
 *
 * @author cedric
 */
@ManagedBean
public class ProjectConverter implements Converter {

    @EJB
    ProjectManagerBean projectBean;

    /**
     * Creates a new instance of ProjectConverter
     */
    public ProjectConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Project project = null;
        try {
            if (value != null && !value.equals("")) {
                long idProject = Integer.parseInt(value);
                project = projectBean.get(idProject);
            }
        } catch (Exception e) {
            throw new ConverterException("The Project does not exist.");
        }
        return project;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String idProject = "";
        if (value != null) {
            idProject = String.valueOf(((Project) value).getId());
        }
        return idProject;
    }
}

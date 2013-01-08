package com.onlinebox.ecosystem.util.controller;

import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * This converter converts a User object to string (its id).
 *
 * @author cedric
 */
@ManagedBean
public class UserConverter implements Converter {

    @EJB
    UserManagerBean userBean;

    /**
     * Creates a new instance of CompanyConverter
     */
    public UserConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        User user = null;
        try {
            if (value != null && !value.equals("")) {
                long idUser = Integer.parseInt(value);
                user = userBean.get(idUser);
            }
        } catch (Exception e) {
            throw new ConverterException("The User does not exist.");
        }
        return user;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String idUser = "";
        if (value != null) {
            idUser = String.valueOf(((User) value).getId());
        }
        return idUser;
    }
}

package com.onlinebox.ecosystem.util.controller;

import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author cedric
 */
@ManagedBean
public class UsernameValidator implements Validator {
    
    @EJB
    UserManagerBean userBean;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String message ="";
        String username = String.valueOf(value);
        
        User user = userBean.getByUsername(username);
        
        if(user.getId() > 0){
            message = "Username already exists.";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
            throw new ValidatorException(facesMessage);
        }
    }
    
}

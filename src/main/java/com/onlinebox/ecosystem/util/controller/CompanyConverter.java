package com.onlinebox.ecosystem.util.controller;

import com.onlinebox.ecosystem.clients.bean.CompanyManagerBean;
import com.onlinebox.ecosystem.clients.entity.Company;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * This converter converts a Company object to string (its id).
 *
 * @author cedric
 */
@ManagedBean
public class CompanyConverter implements Converter {

    @EJB
    CompanyManagerBean clientBean;

    /**
     * Creates a new instance of CompanyConverter
     */
    public CompanyConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Company company = null;
        try {
            if (value != null && !value.equals("")) {
                long idCompany = Integer.parseInt(value);
                company = clientBean.get(idCompany);
            }
        } catch (Exception e) {
            throw new ConverterException("The Client does not exist.");
        }
        return company;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String idCompany = "";
        if (value != null) {
            idCompany = String.valueOf(((Company) value).getId());
        }
        return idCompany;
    }
}

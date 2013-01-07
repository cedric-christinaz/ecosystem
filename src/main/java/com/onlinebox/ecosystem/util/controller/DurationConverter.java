package com.onlinebox.ecosystem.util.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author cedric
 */
@ManagedBean
public class DurationConverter implements Converter {

    /**
     * Converts a string with the form hh:mm in minutes
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.equals("")) {
            return "";
        }
        
        int min = Integer.parseInt(value.substring(3));
        if(min > 59) {
            throw new ConverterException("Duration format not correct.");
        }
        
        return Integer.parseInt(value.substring(0, 2)) * 60 + Integer.parseInt(value.substring(3));
    }

    /**
     * Converts minutes as string with format hh:mm
     * @param context
     * @param component
     * @param value
     * @return 
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        int minutes = (Integer)value;
        
        int hour = minutes/60;
        int min = minutes%60;
        
        String sHour = String.valueOf(hour);
        if(hour < 10)
            sHour = "0"+sHour;
        
        String sMin = String.valueOf(min);
        if(min < 10)
            sMin = "0"+sMin;
        
        return sHour+":"+sMin;

    }

}

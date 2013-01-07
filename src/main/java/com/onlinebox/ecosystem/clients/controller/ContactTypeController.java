package com.onlinebox.ecosystem.clients.controller;

import com.onlinebox.ecosystem.clients.bean.ContactTypeManagerBean;
import com.onlinebox.ecosystem.clients.entity.ContactType;
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
public class ContactTypeController {

    @EJB
    private ContactTypeManagerBean contactTypeBean;
    
    private List<ContactType> contactTypes;
    private ContactType contactType;
    
    /**
     * Creates a new instance of ContactTypeController
     */
    public ContactTypeController() {
        contactType = new ContactType();
    }
    
    @PostConstruct
    public void init(){
        contactTypes = contactTypeBean.getAll();
    }
    
    public List<ContactType> getContactTypes() {
        return contactTypes;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }
    
    /**
     * This method is called by the JSF Page parameters_view.xhtml to create a new contact type or to edit an exising contact tye.
     */
    public void saveContactType() {
        System.out.println("saveContactType()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (contactType != null) {
            try {
                if (contactType.getId() > 0) {
                    //Modify an existing contact type because id already contains a valid value (>0)
                    updateContactType();
                } else {
                    //New contact type because id does not contain a valid value (<=0)
                    createContactType();
                }
            } catch (Exception ex) {
                Logger.getLogger(ContactTypeController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the contact type.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetContactType();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new contact type. It is called by the method saveContactType().
     */
    private void createContactType() throws Exception {
        contactType = this.contactTypeBean.create(contactType);
        contactTypes.add(contactType);
    }

    /*
     * Private method that modifies an existing contact type. It is called by the method saveContactType().
     */
    private void updateContactType() throws Exception {
        contactType = this.contactTypeBean.update(contactType);
    }

    /**
     * Reset the current selected contact type, so that when a popup is opened, there is now old data displayed.
     */
    public void resetContactType() {
        System.out.println("resetContactType()");
        contactType = new ContactType();
    }

    public void deleteContactType() {
        if (contactType != null && contactType.getId() > 0) {
            contactTypeBean.delete(contactType);
            contactTypes.remove(contactType);
        }
    }
    
    
}

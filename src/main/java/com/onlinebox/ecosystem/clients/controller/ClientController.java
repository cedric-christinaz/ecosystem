package com.onlinebox.ecosystem.clients.controller;

import com.onlinebox.ecosystem.clients.bean.CompanyManagerBean;
import com.onlinebox.ecosystem.clients.entity.Company;
import com.onlinebox.ecosystem.employees.controller.UserController;
import com.onlinebox.ecosystem.util.FileHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author cedric
 */
@ManagedBean
@SessionScoped
public class ClientController {

    @EJB
    private CompanyManagerBean clientBean;
    private List<Company> companies; //contain the list of clients to be displayed
    private Company company; //contain the new company to add or the current company to edit
    private String companyLogoTemp;//contain the new uploaded logo (but not saved at the moment because the save is done when closing the dialog.
    private boolean displayTempLogo;//TRUE if the new uploaded logo must be displayed, FALSE if the current logo must be displayed.

    /**
     * Creates a new instance of ClientController
     */
    public ClientController() {
        //Important to create a new empty company. Otherwise, the JSF page
        //links a null object.
        company = new Company();
    }

    /**
     * Initialization method that is called at the creation of the managed bean. This method loads all the companies.
     */
    @PostConstruct
    void init() {
        companies = clientBean.getAll();
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * This method is called by the JSF Page clients_view.xhtml to create a new company or to edit an exising company.
     */
    public void saveCompany() {
        System.out.println("saveCompany()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (company != null) {
            try {
                if (company.getId() > 0) {
                    //Modify an existing company because id already contains a valid value (>0)
                    updateCompany();
                } else {
                    //New company because id does not contain a valid value (<=0)
                    createCompany();
                }
            } catch (Exception ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the company.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetCompany();
            this.resetCompanyDetailsDialog();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new company. It is called by the method saveCompany().
     */
    private void createCompany() throws Exception {
        company = this.clientBean.create(company);
        companies.add(company);
    }

    /*
     * Private method that modifies an existing company. It is called by the method saveCompany().
     */
    private void updateCompany() throws Exception {
        company = this.clientBean.update(company);
    }

    /**
     * This method returns the picture of the specified company (call when filling the companies table).
     *
     * @param logoName Name of the picture to display
     * @return
     */
    public String getLogo(String logoName) {

        if (logoName != null && !logoName.equals("")) {
            return "/pubimg/" + logoName;
        }

        return "";
    }

    public boolean hasLogo(String logoName) {
        boolean bRes = false;
        if (logoName != null && !logoName.equals("")) {
            bRes = true;
        }
        return bRes;
    }

    /**
     * This method returns the picture of the specified company (call when editing a user).
     *
     * @param logoName Name of the picture to display
     * @return
     */
    public String getLogo() {

        if (this.displayTempLogo) {
            return "/pubimg/" + companyLogoTemp;
        }
        if (company.getLogo() != null && !company.getLogo().equals("")) {
            return "/pubimg/" + company.getLogo();
        }

        return "";
    }

    public boolean hasLogo() {
        boolean bRes = false;
        if (company.getLogo() != null && !company.getLogo().equals("")) {
            bRes = true;
        }
        return bRes;
    }

    /**
     * Reset the current selected company, so that when a popup is opened, there is now old data displayed.
     */
    public void resetCompany() {
        System.out.println("resetCompany()");
        company = new Company();
    }

    /**
     * This method is called by the JSF Page clients_view.xhtml to delete an existing company.
     */
    public void deleteCompany() {
        System.out.println("deleteCompany");
        if (company != null && company.getId() > 0) {
            clientBean.delete(company);
            companies.remove(company);

            try {
                Path file = Paths.get("/var/www/ecosystem/pubimg/" + company.getLogo());
                if (Files.exists(file)) {
                    Files.delete(file);
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Getter that return the new logo to display. For the moment, this logo is not saved in the database.
     *
     * @return
     */
    public String getCompanyLogoTemp() {
        return companyLogoTemp;
    }

    /**
     * Setter that allows to set the new logo to display. For the moment, this logo is not saved in the database.
     *
     * @param userPictureTemp
     */
    public void setCompanyLogoTemp(String companyLogoTemp) {
        this.companyLogoTemp = companyLogoTemp;
    }

    /**
     * This method return TRUE if the new logo should be display or FALSE if the existing logo must be displayed.
     *
     * @return
     */
    public boolean isDisplayTempLogo() {
        return displayTempLogo;
    }

    public void setDisplayTempLogo(boolean displayTempLogo) {
        this.displayTempLogo = displayTempLogo;
    }

    /**
     * Handle the upload of the picture of the user.
     *
     * @param event
     */
    public void handlePictureUpload(FileUploadEvent event) {
        System.out.println("handlePictureUpload");

        UploadedFile file = event.getFile();
        if (file != null) {
            try {
                company.setLogo("logocompany_" + company.getId() + "." + FileHelper.getExtensionFromMimeType(file.getContentType()));

                Path target = Paths.get("/var/www/ecosystem/pubimg/" + company.getLogo());
                Files.copy(file.getInputstream(), target, StandardCopyOption.REPLACE_EXISTING);

                this.companyLogoTemp = company.getLogo();

                this.displayTempLogo = true;

            } catch (IOException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This method is called when the dialog box to edit a company is closed. We reset some values for the next time the dialog will be opened.
     */
    public void resetCompanyDetailsDialog() {
        this.displayTempLogo = false;
    }
}

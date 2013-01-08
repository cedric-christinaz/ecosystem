package com.onlinebox.ecosystem.clients.bean;

import com.onlinebox.ecosystem.clients.entity.*;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@javax.ejb.Stateless(name = "CompanyManager")
public class CompanyManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param company
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public Company create(Company company) throws Exception {
        em.persist(company);
        return company;
    }

    /**
     *
     * @param company
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public Company update(Company company) throws Exception {
        return em.merge(company);
    }

    /**
     *
     * @param company
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public void delete(Company company) {
        Company toDelete = em.merge(company);
        em.remove(toDelete);
    }

    public java.util.List<Company> getAll() {
        Query query = em.createNamedQuery("Company.findAll");
        return query.getResultList();
    }

    /**
     *
     * @param id
     */
    public Company get(long id) {
        return em.find(Company.class, id);
    }

    public java.util.List<Company> findAllByName(String name) {
        Query query = em.createNamedQuery("Company.findAllByName");
        query.setParameter("clientName", "%" + name + "%");
        return query.getResultList();
    }

    /**
     *
     * @param contact
     */
    public void addContact(Contact contact) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param contact
     */
    public void updateContact(Contact contact) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param contact
     */
    public boolean removeContact(Contact contact) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param note
     */
    public void addNote(ClientNote note) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method modifies an existing note.
     *
     * @param note the modified note
     */
    public void updateNote(ClientNote note) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param note
     */
    public boolean removeNote(ClientNote note) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param document
     */
    public void addDocument(Document document) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param document
     */
    public void updateDocument(Document document) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param document
     */
    public void removeDocument(Document document) {
        throw new UnsupportedOperationException();
    }
}
package com.onlinebox.ecosystem.clients.bean;

import com.onlinebox.ecosystem.clients.entity.*;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@javax.ejb.Stateless(name = "ContactTypeManager")
public class ContactTypeManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param contactType
     */
    @RolesAllowed({"ADMIN"})
    public ContactType create(ContactType contactType) {
        em.persist(contactType);
        return contactType;
    }

    /**
     *
     * @param contactType
     */
    @RolesAllowed({"ADMIN"})
    public ContactType update(ContactType contactType) {
        return em.merge(contactType);
    }

    /**
     *
     * @param contactType
     */
    @RolesAllowed({"ADMIN"})
    public void delete(ContactType contactType) {
        ContactType toDelete = em.merge(contactType);
        em.remove(toDelete);
    }

    public java.util.List<ContactType> getAll() {
        Query query = em.createNamedQuery("ContactType.findAll");
        return query.getResultList();
    }

    /**
     *
     * @param id
     */
    public ContactType get(long id) {
        return em.find(ContactType.class, id);
    }
}
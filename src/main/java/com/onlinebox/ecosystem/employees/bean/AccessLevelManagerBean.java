package com.onlinebox.ecosystem.employees.bean;

import com.onlinebox.ecosystem.employees.entity.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * This bean manages the access level of the employees.
 * @author cedric
 */
@javax.ejb.Stateless(name = "AccessLevelManager")
public class AccessLevelManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param accessLevel
     */
    public AccessLevel create(AccessLevel accessLevel) throws Exception {
        if (accessLevel.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        em.persist(accessLevel);
        return accessLevel;
    }

    /**
     *
     * @param accessLevel
     */
    public AccessLevel update(AccessLevel accessLevel) throws Exception {
        if (accessLevel.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        return em.merge(accessLevel);
    }

    /**
	 * 
	 * @param accessLevel
	 */
    public void delete(AccessLevel accessLevel) {
        AccessLevel toDelete = em.merge(accessLevel);
        em.remove(toDelete);
    }

    public java.util.List<AccessLevel> getAll() {
        Query query = em.createNamedQuery("AccessLevel.findAll");
        return query.getResultList();
    }

    /**
     *
     * @param id
     */
    public AccessLevel get(long id) {
        return em.find(AccessLevel.class, id);
    }
}
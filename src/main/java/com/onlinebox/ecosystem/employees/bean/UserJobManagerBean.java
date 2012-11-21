package com.onlinebox.ecosystem.employees.bean;

import com.onlinebox.ecosystem.employees.entity.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * This bean manages the user job (job title of the employees)
 * @author cedric
 */
@javax.ejb.Stateless(name = "UserJobManager")
public class UserJobManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param userJob
     */
    public UserJob create(UserJob userJob) throws Exception {
        if (userJob.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        em.persist(userJob);
        return userJob;
    }

    /**
     *
     * @param userJob
     */
    public UserJob update(UserJob userJob) throws Exception {
        if (userJob.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        return em.merge(userJob);
    }

    /**
	 * 
	 * @param userJob
	 */
    public void delete(UserJob userJob) {
        UserJob toDelete = em.merge(userJob);
        em.remove(toDelete);
    }

    public java.util.List<UserJob> getAll() {
        Query query = em.createNamedQuery("UserJob.findAll");
        return query.getResultList();
    }

    /**
     *
     * @param id
     */
    public UserJob get(long id) {
        return em.find(UserJob.class, id);
    }
}
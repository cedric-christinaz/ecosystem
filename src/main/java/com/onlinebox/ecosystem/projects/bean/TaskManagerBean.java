package com.onlinebox.ecosystem.projects.bean;

import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * This bean manages the tasks.
 * @author cedric
 */
@javax.ejb.Stateless(name = "TaskManager")
public class TaskManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param task
     */
    public Task create(Task task) {
        em.persist(task);
        return task;
    }

    /**
     *
     * @param task
     */
    public Task update(Task task) {
        return em.merge(task);
    }

    /**
     *
     * @param task
     */
    public void delete(Task task) {
        Task toDelete = em.merge(task);
        em.remove(toDelete);
    }

    /**
     *
     * @param id
     */
    public Task get(long id) {
        return em.find(Task.class, id);
    }
    
    public List<Task> getByUser(User user){
        Query query = em.createNamedQuery("Task.findAllByUser");
        query.setParameter("idUser", user.getId());
        return query.getResultList();
    }
}
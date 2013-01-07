package com.onlinebox.ecosystem.projects.bean;

import com.onlinebox.ecosystem.projects.entity.*;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This bean manages the type of tasks (for example, specification, implementation, test, ...)
 *
 * @author cedric
 */
@javax.ejb.Stateless(name = "TaskTypeManager")
public class TaskTypeManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param taskType
     */
    @RolesAllowed({"ADMIN"})
    public TaskType create(TaskType taskType) throws Exception {
        if (taskType.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        em.persist(taskType);
        return taskType;
    }

    /**
     *
     * @param taskType
     */
    @RolesAllowed({"ADMIN"})
    public TaskType update(TaskType taskType) throws Exception {
        if (taskType.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        return em.merge(taskType);
    }

    /**
     *
     * @param taskType
     */
    @RolesAllowed({"ADMIN"})
    public void delete(TaskType taskType) {
        TaskType toDelete = em.merge(taskType);
        em.remove(toDelete);
    }

    public java.util.List<TaskType> getAll() {
        Query query = em.createNamedQuery("TaskType.findAll");
        return query.getResultList();
    }

    /**
     *
     * @param id
     */
    public TaskType get(long id) {
        return em.find(TaskType.class, id);
    }
}
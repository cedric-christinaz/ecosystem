package com.onlinebox.ecosystem.projects.bean;

import com.onlinebox.ecosystem.projects.entity.*;
import com.onlinebox.ecosystem.util.entity.IEntity;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This bean manages the projects.
 *
 * @author cedric
 */
@javax.ejb.Stateless(name = "ProjectManager")
public class ProjectManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param project
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public Project create(Project project) throws Exception {
        if (project.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        em.persist(project);
        return project;
    }

    /**
     *
     * @param project
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public Project update(Project project) throws Exception {
        if (project.getName().equals("")) {
            throw new Exception("The attribute 'name' cannot be empty");
        }
        return em.merge(project);
    }

    /**
     *
     * @param project
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public void delete(Project project) {
        Project toDelete = em.merge(project);
        em.remove(toDelete);
    }

    /**
     * This method returns all the projects.
     *
     * @return
     */
    public java.util.List<Project> getAll() {
        Query query = em.createNamedQuery("Project.findAll");
        return query.getResultList();
    }

    /**
     * This method returns a list of project basing on the specified status
     *
     * @param projectStatus 0 : In Progress, 1 : Closed, Other value: All project
     * @return
     */
    public java.util.List<Project> getAllByStatus(int projectStatus) {
        Query query = null;
        if (projectStatus == 0 || projectStatus == 1) {
            query = em.createNamedQuery("Project.findAllByStatus");
            query.setParameter("projectStatus", projectStatus);
        } else {
            query = em.createNamedQuery("Project.findAll");
        }

        return query.getResultList();
    }

    /**
     * This method returns a list of project that should be closed basing on the End Date of the project.
     *
     * @return
     */
    public java.util.List<Project> getAllLate() {
        Query query = em.createNamedQuery("Project.findAllLate");
        return query.getResultList();
    }

    /**
     * This method return the Project that corresponds to the specified id
     *
     * @param id
     */
    public Project get(long id) {
        return em.find(Project.class, id);
    }

    public java.util.List<IEntity> findAllByName(String name) {
        Query query = em.createNamedQuery("Project.findAllByName");
        query.setParameter("projectName", "%" + name + "%");
        return query.getResultList();
    }

    public java.util.List<IEntity> findAllInProgressByName(String name) {
        Query query = em.createNamedQuery("Project.findAllInProgressByName");
        query.setParameter("projectName", "%" + name + "%");
        return query.getResultList();
    }

    public java.util.List getWorkedHoursByTaskType(long idProject) {
        Query query = em.createQuery("SELECT e.name, SUM(t.duration) FROM Task t INNER JOIN t.taskType e WHERE t.project.id = :idProject GROUP BY e.name");
        query.setParameter("idProject", idProject);
        return query.getResultList();
    }
}
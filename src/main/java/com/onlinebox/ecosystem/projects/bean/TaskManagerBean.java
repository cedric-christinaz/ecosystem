package com.onlinebox.ecosystem.projects.bean;

import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.entity.*;
import java.util.Calendar;
import java.util.Date;
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
             
        Project project = em.find(Project.class, task.getProject().getId());
        project.getTasks().add(task);
        em.merge(project);
        
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
    
    public List<Task> getByUserAndByPeriod(User user, Date startDate, Date endDate){

        Query query = em.createNamedQuery("Task.findAllByUserAndByPeriod");
        query.setParameter("idUser", user.getId());
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    
    public List getWorkedHoursByTaskTypeCurrentMonth(){
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        Date firstDayOfMonth = calendar.getTime();  
        
        Query query = em.createQuery("SELECT e.name, SUM(t.duration) FROM Task t INNER JOIN t.taskType e WHERE t.taskDate >= :startDate GROUP BY e.name");
        query.setParameter("startDate", firstDayOfMonth);
        return query.getResultList();
    }
    
    public List getWorkedHoursByTaskTypeLastMonth(){
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        
        
        calendar.set(Calendar.DAY_OF_MONTH, 1);  //first day of current month
        calendar.add(Calendar.DATE, -1);    //last day of last month
        Date lastDayOfLastMonth = calendar.getTime(); 
         
        calendar.set(Calendar.DAY_OF_MONTH, 1);  //first day of last month
        Date firstDayOfLastMonth = calendar.getTime();  
        
        Query query = em.createQuery("SELECT e.name, SUM(t.duration) FROM Task t INNER JOIN t.taskType e WHERE t.taskDate >= :startDate AND t.taskDate <= :endDate GROUP BY e.name");
        query.setParameter("startDate", firstDayOfLastMonth);
        query.setParameter("endDate", lastDayOfLastMonth);
        return query.getResultList();
    }
    
}
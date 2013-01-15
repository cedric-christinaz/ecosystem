package com.onlinebox.ecosystem.projects.entity;

import com.onlinebox.ecosystem.clients.entity.*;
import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import java.util.*;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "select p from Project p ORDER BY p.name"),
    @NamedQuery(name = "Project.findAllByName", query = "select p from Project p WHERE p.name LIKE :projectName ORDER BY p.name"),
    @NamedQuery(name = "Project.findAllByStatus", query = "select p from Project p WHERE p.status = :projectStatus ORDER BY p.name"),
    @NamedQuery(name = "Project.findAllInProgressByName", query = "select p from Project p WHERE p.status = 0 AND p.name LIKE :projectName ORDER BY p.name"),
    @NamedQuery(name = "Project.findAllLate", query = "select p from Project p WHERE p.endDate < CURRENT_DATE AND p.status = 0 ORDER BY p.endDate DESC")
})
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name = "t_project")
public class Project implements IEntity, Serializable {

    @javax.persistence.ManyToOne(optional = false)
    @javax.persistence.JoinColumn(name = "t_companyId", referencedColumnName = "Id", nullable = false)
    private Company company;
    @javax.persistence.OneToMany(mappedBy = "project")
    @javax.persistence.JoinColumn(name = "t_projectId", referencedColumnName = "Id", nullable = false)
    private List<Task> tasks;
    @javax.persistence.Id
    @javax.persistence.Column(name = "Id", nullable = false, length = 20)
    private long id;
    @javax.persistence.Column(name = "Name", nullable = false, length = 150)
    private String name;
    @javax.persistence.Column(name = "EstimatedHour", length = 11)
    private int estimatedHour;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "CreateDate", nullable = false)
    private java.util.Date createDate;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "LastUpdateDate", nullable = false)
    private java.util.Date lastUpdateDate;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "EndDate")
    private java.util.Date endDate;
    @javax.persistence.Column(name = "UseEstimatedHour", nullable = false, length = 1)
    private boolean useEstimatedHour;
    @javax.persistence.Column(name = "UseEndDate", nullable = false, length = 1)
    private boolean useEndDate;
    @javax.persistence.Column(name = "Status", nullable = false, length = 2)
    private int status;

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimatedHour() {
        return this.estimatedHour;
    }

    public void setEstimatedHour(int estimatedHour) {
        this.estimatedHour = estimatedHour;
    }

    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.util.Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(java.util.Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String toString() {
        return this.getName();
    }

    public java.util.Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public boolean isUseEstimatedHour() {
        return this.useEstimatedHour;
    }

    public void setUseEstimatedHour(boolean useEstimatedHour) {
        this.useEstimatedHour = useEstimatedHour;
    }

    public boolean isUseEndDate() {
        return this.useEndDate;
    }

    public void setUseEndDate(boolean useEndDate) {
        this.useEndDate = useEndDate;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
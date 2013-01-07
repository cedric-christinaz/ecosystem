package com.onlinebox.ecosystem.projects.entity;

import com.onlinebox.ecosystem.clients.entity.*;
import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import java.util.*;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "select p from Project p ORDER BY p.name"),
    @NamedQuery(name = "Project.findAllByName", query = "select p from Project p WHERE p.name LIKE :projectName ORDER BY p.name")})
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
    @javax.persistence.Column(name = "EstimatedHour", nullable = false, length = 11)
    private int estimatedHour;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "CreateDate", nullable = false)
    private java.util.Date createDate;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "LastUpdateDate", nullable = false)
    private java.util.Date lastUpdateDate;

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
}
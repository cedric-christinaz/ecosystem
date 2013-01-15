package com.onlinebox.ecosystem.projects.entity;

import com.onlinebox.ecosystem.employees.entity.*;
import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    @NamedQuery(name = "Task.findAllByUser", query = "select t from Task t INNER JOIN t.user u WHERE u.id=:idUser ORDER BY t.taskDate DESC"),
})
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_task")
public class Task implements IEntity, Serializable {

    @javax.persistence.ManyToOne(optional = false)
    @javax.persistence.JoinColumn(name = "t_projectId", referencedColumnName = "Id", nullable = false)
    private Project project;
    @javax.persistence.OneToOne(optional = false)
    @javax.persistence.JoinColumn(name = "t_task_typeId", referencedColumnName = "Id", nullable = false)
    private TaskType taskType;
    @javax.persistence.Id
    @javax.persistence.Column(name = "Id", nullable = false, length = 20)
    private long id;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
    private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
    private java.util.Date lastUpdateDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="TaskDate", nullable=false)
    private java.util.Date taskDate;
    @javax.persistence.Column(name = "Duration", nullable = false, length = 11)
    private int duration;
    @javax.persistence.Column(name = "Remark")
    private String remark;
    @javax.persistence.ManyToOne(optional = false)
    @javax.persistence.JoinColumn(name = "t_userId", referencedColumnName = "Id", nullable = false)
    private User user;

    public Task() {
        this.setTaskType(new TaskType());
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public java.util.Date getTaskDate() {
        return this.taskDate;
    }

    public void setTaskDate(java.util.Date taskDate) {
        this.taskDate = taskDate;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
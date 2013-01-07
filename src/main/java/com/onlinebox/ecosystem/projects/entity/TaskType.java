package com.onlinebox.ecosystem.projects.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.NamedQuery;

@NamedQuery(name = "TaskType.findAll", query = "select t from TaskType t ORDER BY t.name")
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_task_type")
public class TaskType implements IEntity, Serializable {

	@javax.persistence.Id
	@javax.persistence.Column(name="Id", nullable=false, length=20)
	private long id;
	@javax.persistence.Column(name="Name", nullable=false, length=150)
	private String name;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
	private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
	private java.util.Date lastUpdateDate;
	@javax.persistence.Column(name="HourlyPrice", nullable=false, length=11)
	private int hourlyPrice;

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

	public int getHourlyPrice() {
		return this.hourlyPrice;
	}

	public void setHourlyPrice(int hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}

}
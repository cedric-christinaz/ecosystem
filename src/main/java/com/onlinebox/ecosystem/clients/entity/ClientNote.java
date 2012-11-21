package com.onlinebox.ecosystem.clients.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.*;
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_client_note")
public class ClientNote implements IEntity, Serializable {

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
	@javax.persistence.Column(name="Id", nullable=false, length=19)
	private long id;
	@javax.persistence.Column(name="Detail", nullable=false)
	private String detail;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
	private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
	private java.util.Date lastUpdateDate;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
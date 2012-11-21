package com.onlinebox.ecosystem.clients.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.*;
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_document")
public class Document implements IEntity, Serializable {

	@javax.persistence.OneToOne(optional=false)
	@javax.persistence.JoinColumn(name="t_document_typeId", referencedColumnName="Id", nullable=false)
	private DocumentType type;
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
	@javax.persistence.Column(name="Id", nullable=false, length=19)
	private long id;
	@javax.persistence.Column(name="Name", nullable=false, length=150)
	private String name;
	@javax.persistence.Column(name="Detail")
	private String detail;
	@javax.persistence.Column(name="Document", nullable=false, length=10)
	private Integer document;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
	private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
	private java.util.Date lastUpdateDate;

	public DocumentType getType() {
		return this.type;
	}

	public void setType(DocumentType type) {
		this.type = type;
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

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getDocument() {
		return this.document;
	}

	public void setDocument(Integer document) {
		this.document = document;
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
package com.onlinebox.ecosystem.clients.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_company")
public class Company implements IEntity, Serializable {

	@javax.persistence.OneToMany(mappedBy="company")
	@javax.persistence.JoinColumn(name="t_companyId", referencedColumnName="Id", nullable=false)
	private List<Contact> contacts;
	@javax.persistence.OneToMany
	@javax.persistence.JoinColumn(name="t_companyId", referencedColumnName="Id", nullable=false)
	private List<Document> documents;
	@javax.persistence.OneToMany
	@javax.persistence.JoinColumn(name="t_companyId", referencedColumnName="Id", nullable=false)
	private List<ClientNote> notes;
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
	@javax.persistence.Column(name="Id", nullable=false, length=19)
	private long id;
	@javax.persistence.Column(name="Name", nullable=false, length=100)
	private String name;
	@javax.persistence.Column(name="Street", length=100)
	private String street;
	@javax.persistence.Column(name="ZipCode", length=10)
	private String zipCode;
	@javax.persistence.Column(name="City", length=60)
	private String city;
	@javax.persistence.Column(name="Email", length=100)
	private String email;
	@javax.persistence.Column(name="Phone", length=10)
	private int phone;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
	private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
	private java.util.Date lastUpdateDate;
	@javax.persistence.Column(name="Logo", length=10)
	private Integer logo;

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<ClientNote> getNotes() {
		return this.notes;
	}

	public void setNotes(List<ClientNote> notes) {
		this.notes = notes;
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

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return this.phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
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

	public Integer getLogo() {
		return this.logo;
	}

	public void setLogo(Integer logo) {
		this.logo = logo;
	}

}
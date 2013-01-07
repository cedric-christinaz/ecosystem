package com.onlinebox.ecosystem.clients.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.*;
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_contact")
public class Contact implements IEntity, Serializable {

	@javax.persistence.ManyToOne(optional=false)
	@javax.persistence.JoinColumn(name="t_companyId", referencedColumnName="Id", nullable=false)
	private Company company;
	@javax.persistence.OneToMany
	@javax.persistence.ManyToOne(optional=false)
	@javax.persistence.JoinColumn(name="t_contact_typeId", referencedColumnName="Id", nullable=false)
	private ContactType role;
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
	@javax.persistence.Column(name="Id", nullable=false, length=19)
	private long id;
	@javax.persistence.Column(name="Firstname", nullable=false, length=60)
	private String firstname;
	@javax.persistence.Column(name="Lastname", nullable=false, length=60)
	private String lastname;
	@javax.persistence.Column(name="Email", length=100)
	private String email;
	@javax.persistence.Column(name="PhoneOffice", length=20)
	private String phoneOffice;
	@javax.persistence.Column(name="PhoneMobile", length=20)
	private String phoneMobile;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
	private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
	private java.util.Date lastUpdateDate;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ContactType getRole() {
		return this.role;
	}

	public void setRole(ContactType role) {
		this.role = role;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneOffice() {
		return this.phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getPhoneMobile() {
		return this.phoneMobile;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
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
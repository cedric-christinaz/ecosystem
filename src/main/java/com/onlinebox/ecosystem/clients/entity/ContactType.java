package com.onlinebox.ecosystem.clients.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(name = "ContactType.findAll", query = "select ct from ContactType ct order by ct.name"),})
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name = "t_contact_type")
public class ContactType implements IEntity, Serializable, Comparable<ContactType> {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "Id", nullable = false, length = 19)
    private long id;
    @javax.persistence.Column(name = "Name", nullable = false, length = 60)
    private String name;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "CreateDate", nullable = false)
    private java.util.Date createDate;
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @javax.persistence.Column(name = "LastUpdateDate", nullable = false)
    private java.util.Date lastUpdateDate;

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

    @Override
    public int compareTo(ContactType o) {
        if (this.getName() == null) {
            return -1;
        }
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
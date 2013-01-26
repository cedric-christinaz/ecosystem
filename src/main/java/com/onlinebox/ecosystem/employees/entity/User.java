package com.onlinebox.ecosystem.employees.entity;

import com.onlinebox.ecosystem.projects.entity.Task;
import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User u order by u.lastname, u.firstname"),
    @NamedQuery(name = "User.findActive", query = "select u from User u WHERE u.isActive = true order by u.lastname, u.firstname"),
    @NamedQuery(name = "User.findDisabled", query = "select u from User u WHERE u.isActive = false order by u.lastname, u.firstname"),
    @NamedQuery(name = "User.findByUsername", query = "select u from User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findAllByName", query = "select u from User u WHERE u.firstname LIKE :firstname OR u.lastname LIKE :lastname ORDER BY u.lastname, u.firstname")
})
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_user")
public class User implements IEntity, Serializable, Comparable<User> {

    @javax.persistence.OneToOne(optional = false)
    @javax.persistence.JoinColumn(name = "t_user_jobId", referencedColumnName = "Id", nullable = false)
    private UserJob job;
    @javax.persistence.OneToOne(optional = false)
    @javax.persistence.JoinColumn(name = "t_access_levelId", referencedColumnName = "Id", nullable = false)
    private AccessLevel accessLevel;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "Id", nullable = false, length = 19)
    private long id;
    @javax.persistence.Column(name = "Firstname", nullable = false, length = 60)
    private String firstname;
    @javax.persistence.Column(name = "Lastname", nullable = false, length = 60)
    private String lastname;
    @javax.persistence.Column(name = "Email", length = 100)
    private String email;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="Birthday")
    private java.util.Date birthday;
    @javax.persistence.Column(name = "Steet", length = 100)
    private String steet;
    @javax.persistence.Column(name = "ZipCode", length = 10)
    private String zipCode;
    @javax.persistence.Column(name = "City", length = 60)
    private String city;
    @javax.persistence.Column(name = "IsActive", nullable = false)
    private boolean isActive;
    @javax.persistence.Column(name = "Image", length = 100)
    private String image;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastLogin")
    private java.util.Date lastLogin;
    @javax.persistence.Column(name = "PhoneHome", length = 20)
    private String phoneHome;
    @javax.persistence.Column(name = "PhoneOffice", length = 20)
    private String phoneOffice;
    @javax.persistence.Column(name = "PhoneMobile", length = 20)
    private String phoneMobile;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
    private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
    private java.util.Date lastUpdateDate;
    @javax.persistence.Column(name = "Username", unique = true, nullable = false, length = 120)
    private String username;
    @javax.persistence.Column(name = "Password", nullable = false, length = 128)
    private String password;
    @javax.persistence.OneToMany(mappedBy = "user")
    @javax.persistence.JoinColumn(name = "t_userId", referencedColumnName = "Id", nullable = false)
    private List<Task> tasks;

    public User() {
        this.setAccessLevel(new AccessLevel());
        this.setJob(new UserJob());
    }

    public UserJob getJob() {
        return this.job;
    }

    public void setJob(UserJob job) {
        this.job = job;
    }

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
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

    public java.util.Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public String getSteet() {
        return this.steet;
    }

    public void setSteet(String steet) {
        this.steet = steet;
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

    public boolean isIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.util.Date getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(java.util.Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPhoneHome() {
        return this.phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
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

    @Override
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    @Override
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public java.util.Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(java.util.Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String toString() {
        return this.getFirstname() + " " + this.getLastname();
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(User o) {
        return (this.getLastname()+this.getFirstname()).compareToIgnoreCase((o.getLastname()+o.getFirstname()));
    }
    
    
}
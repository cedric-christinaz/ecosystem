package com.onlinebox.ecosystem.employees.bean;

import com.onlinebox.ecosystem.employees.entity.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@javax.ejb.Stateless(name = "UserManager")
public class UserManagerBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param user
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public User create(User user) throws Exception {

        //Check if the firstname and lastname are not empty
        if (user.getFirstname().equals("")) {
            throw new Exception("The attribute 'firstname' cannot be empty");
        }
        if (user.getLastname().equals("")) {
            throw new Exception("The attribute 'lastname' cannot be empty");
        }
        if (user.getPassword().equals("")) {
            throw new Exception("The attribute 'password' cannot be empty");
        }

        //Activate the user
        user.setIsActive(true);

        //Crypt the password entered for this user
        user.setPassword(this.cryptPassword(user.getPassword()));

        em.persist(user);
        return user;
    }

    /**
     *
     * @param user
     */
    public User update(User user) throws Exception {
        if (user.getFirstname().equals("")) {
            throw new Exception("The attribute 'firstname' cannot be empty");
        }
        if (user.getLastname().equals("")) {
            throw new Exception("The attribute 'lastname' cannot be empty");
        }
        return em.merge(user);
    }

    /**
     *
     * @param user
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public void delete(User user) {
        User toDelete = em.merge(user);
        em.remove(toDelete);
    }

    /**
     * This method returns all users (both enabled and disabled).
     * @return List of all users (both enabled and disabled)
     */
    public java.util.List<User> getAll() {
        Query query = em.createNamedQuery("User.findAll");
        return query.getResultList();
    }

    /**
     * This method returns all users that are enabled.
     * @return List of all enabled users
     */
    public java.util.List<User> getAllActiveUsers() {
        Query query = em.createNamedQuery("User.findActive");
        return query.getResultList();
    }
    
     /**
     * This method returns all users that are enabled.
     * @return List of all enabled users
     */
    public java.util.List<User> getAllDisabledUsers() {
        Query query = em.createNamedQuery("User.findDisabled");
        return query.getResultList();
    }

    /**
     *
     * @param id
     */
    public User get(long id) {
        return em.find(User.class, id);
    }

    public User getByUsername(String username) throws NoResultException  {
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        try{
            return (User) query.getSingleResult();
        }
        catch(NoResultException nre){
            return new User();
        }     
    }

    /**
     * This method inactivate the user.
     *
     * @param user
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public User archive(User user) throws Exception {
        user.setIsActive(false);
        return this.update(user);
    }

    /**
     * This method inactivate the user.
     *
     * @param user
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public User unArchive(User user) throws Exception {
        user.setIsActive(true);
        return this.update(user);
    }

    /**
     * This method sets the default password to the user.
     *
     * @param user
     */
    @RolesAllowed({"ADMIN", "POWER_USER"})
    public void resetPassword(User user) throws Exception {
        user.setPassword(cryptPassword(user.getPassword()));
        this.update(user);

    }

    /*
     * Hash with SHA-512 the string specified in parameter and returns it in hexadecimal.
     */
    private String cryptPassword(String clearPassword) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashPwd = md.digest(clearPassword.getBytes());

        //convert the byte to hex
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hashPwd.length; i++) {
            hexString.append(Integer.toString((hashPwd[i] & 0xff) + 0x100, 16).substring(1));
        }

        return hexString.toString();
    }
}
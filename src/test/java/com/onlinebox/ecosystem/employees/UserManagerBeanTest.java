/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.employees;

import com.onlinebox.ecosystem.employees.bean.AccessLevelManagerBean;
import com.onlinebox.ecosystem.employees.bean.UserJobManagerBean;
import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.AccessLevel;
import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.employees.entity.UserJob;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cedric
 */
public class UserManagerBeanTest {

    private static EJBContainer ec;
    private static Context ctx;

    public UserManagerBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        ec = EJBContainer.createEJBContainer();
        ctx = ec.getContext();
    }

    @AfterClass
    public static void tearDownClass() {
        ec.close();
    }

    @Test
    public void create() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user with minimal information (firstname, lastname, password, access level, job title)
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutFirstname1() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without firstname
            User user = new User();
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because firstname is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because firstname is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutFirstname2() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without firstname
            User user = new User();
            user.setFirstname("");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because firstname is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because firstname is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutLastname1() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without lastname
            User user = new User();
            user.setFirstname("James");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because lastname is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because lastname is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutLastname2() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without lastname
            User user = new User();
            user.setFirstname("James");
            user.setLastname("");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because lastname is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because lastname is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutPassword1() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without password
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because password is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because password is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutPassword2() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without password
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because password is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because password is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutJobTitle() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user without job
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(null);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because job is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because job is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void createWithoutAccessLevel() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new user without access level
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(null);
            user.setJob(job);

            try {
                user = userBean.create(user);
                Assert.fail("user should not be added in the db because access level is null");
            } catch (Exception e) {
            }

            if (user.getId() > 0) {
                Assert.fail("user should not be added in the db because access level is null");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void resetPassword() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user with minimal information (firstname, lastname, password, access level, job title)
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());

            //Reset Password
            user.setPassword("welcome");
            userBean.resetPassword(user);
            user = userBean.get(user.getId());

            Assert.assertEquals("Password Hash does not match", "728db48989c9878bdb727058ae0d0968c5902f488dd9e3d4a4aa3f90410da5566fd0ca5f59c6a58154cce2e5c8e7a2586a79d88397d12c46b830ee50890971eb", user.getPassword());


        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void archiveUser() throws Exception {
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user with minimal information (firstname, lastname, password, access level, job title)
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());

            //Inactivate User
            userBean.archive(user);
            user = userBean.get(user.getId());

            Assert.assertEquals("User should be inactive", false, user.isIsActive());
            
            //Activate User
            userBean.unArchive(user);
            user = userBean.get(user.getId());

            Assert.assertEquals("User should be active", true, user.isIsActive());


        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }
    

    @Test
    public void getAll() throws Exception{
        
        try {
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Get initial users numbers
            List<User> users = userBean.getAll();
            int initialSize = users.size();
            
            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user with minimal information (firstname, lastname, password, access level, job title)
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());

            user = new User();
            user.setFirstname("John");
            user.setLastname("Travolta");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());
            
            user = new User();
            user.setFirstname("John");
            user.setLastname("rambo");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());

            users = userBean.getAll();
            Assert.assertNotNull("Users list should not be empty", users);
            Assert.assertEquals("Users List should contains 3 elements", 3, (users.size() - initialSize));
            
            //Test if order by is correct (a - z) ignore case
            String prevUser = "";
            for(User myuser : users){                
                if(prevUser.compareToIgnoreCase(myuser.getLastname()) > 0){
                    Assert.fail("Order of users list is wrong.");
                }
                prevUser = myuser.getLastname();
            }
            
        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }
        
    }
    
    @Test
    public void delete() throws Exception {
        try {
            
            
            UserManagerBean userBean = (UserManagerBean) ctx.lookup("java:global/classes/UserManager");
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Create a new Job Title
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            //Create a new Access Level
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            //Create a new user with minimal information (firstname, lastname, password, access level, job title)
            User user = new User();
            user.setFirstname("James");
            user.setLastname("Bond");
            user.setPassword("test");
            user.setAccessLevel(accessLevel);
            user.setJob(job);

            user = userBean.create(user);

            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);

            if (user.getId() <= 0) {
                Assert.fail("Object user must have an id greater than 0");
            }

            Assert.assertEquals("User should be active", true, user.isIsActive());

            Assert.assertEquals("Password Hash does not match", "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff", user.getPassword());

            long idUser = user.getId();
            user=null;
            
            user = userBean.get(idUser);
            
            Assert.assertNotNull("Object user cannot be null because it is just added in DB", user);
            
            userBean.delete(user);
            
            user=null;
            
            user = userBean.get(idUser);
             Assert.assertNull("Object user should be null because it has been deleted.", user);
            
        } catch (NamingException ex) {
            Logger.getLogger(UserManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }
    
}

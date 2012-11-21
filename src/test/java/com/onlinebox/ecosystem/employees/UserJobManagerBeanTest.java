/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.employees;

import com.onlinebox.ecosystem.employees.bean.UserJobManagerBean;
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
public class UserJobManagerBeanTest {

    private static EJBContainer ec;
    private static Context ctx;

    public UserJobManagerBeanTest() {
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
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");
            UserJob job = new UserJob();
            job.setName("Developer");
            job = userJobBean.create(job);

            Assert.assertNotNull("Object job cannot be null because it is just added in DB", job);

            if (job.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }

            //Insert job with name = null --> not authorized
            job = new UserJob();
            try {
                job = userJobBean.create(job);
            } catch (Exception e) {
            }
            if (job.getId() > 0) {
                Assert.fail("Object should not be created because name is empty");
            }

            //Insert job with name = "" --> not authorized
            job = new UserJob();
            job.setName("");
            try {
                job = userJobBean.create(job);
            } catch (Exception e) {
            }

            if (job.getId() > 0) {
                Assert.fail("Object should not be created because name is empty");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void update() throws InterruptedException {
        try {
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            //Add a new user job in the database
            UserJob job = new UserJob();
            job.setName("Developer");
            try {
                job = userJobBean.create(job);
            } catch (Exception ex) {
                Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object job cannot be null because it is just added in DB", job);
            if (job.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }
            
            Thread.sleep(2000); //Pause 2 seconds
            
            //Update the user job --> ok
            job.setName("Developer Modified");
            try {
                job = userJobBean.update(job);
            } catch (Exception e) {
                Assert.fail("Object not modified");
            }
            System.out.println("*******************************************************");
            System.out.println(job.getCreateDate());
            System.out.println(job.getLastUpdateDate());
            if(job.getCreateDate().compareTo(job.getLastUpdateDate()) >= 0){
                Assert.fail("Last Modified date should be greater than create date");
            }

            //Update the user job without giving a name (name = NULL) --> not authorized
            boolean isException = false;
            job.setName(null);
            try {
                job = userJobBean.update(job);
                Assert.fail("Object dd should not be modified because name is empty");
            } catch (Exception e) {
                isException = true;
            }
            if (!isException) {
                Assert.fail("Object should not be modified because name is empty");
            }

            //Update the user job without giving a name (name = "") --> not authorized
            isException = false;
            job.setName("");
            try {
                job = userJobBean.update(job);
                Assert.fail("Object dd should not be modified because name is empty");
            } catch (Exception e) {
                isException = true;
            }
            if (!isException) {
                Assert.fail("Object should not be modified because name is empty");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }

    }

    @Test
    public void delete() {
        try {
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");
            UserJob job = new UserJob();
            job.setName("To be deleted");
            try {
                job = userJobBean.create(job);
            } catch (Exception ex) {
                Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object job cannot be null because it is just added in DB", job);
            if (job.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }

            try {
                userJobBean.delete(job);
            } catch (Exception e) {
                Assert.fail("Object not deleted");
            }

        } catch (NamingException ex) {
            Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }
    }

    @Test
    public void getAll() {
        try {
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");

            List<UserJob> jobs = userJobBean.getAll();
            int initialSize = jobs.size();

            UserJob job = new UserJob();
            job.setName("Developer");
            try {
                job = userJobBean.create(job);
            } catch (Exception ex) {
                Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object job cannot be null because it is just added in DB", job);
            if (job.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }

            job = new UserJob();
            job.setName("Manager");
            try {
                job = userJobBean.create(job);
            } catch (Exception ex) {
                Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object job cannot be null because it is just added in DB", job);
            if (job.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }

            jobs = userJobBean.getAll();
            Assert.assertNotNull("Jobs list should not be empty", jobs);
            Assert.assertEquals("Jobs List should contains 2 elements", 2, (jobs.size() - initialSize));


        } catch (NamingException ex) {
            Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }
    }

    @Test
    public void get() throws Exception {
        try {

            long idJob = -1;
            UserJobManagerBean userJobBean = (UserJobManagerBean) ctx.lookup("java:global/classes/UserJobManager");
            UserJob job = new UserJob();
            job.setName("CTO");
            job = userJobBean.create(job);

            Assert.assertNotNull("Object job cannot be null because it is just added in DB", job);

            if (job.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }
            idJob = job.getId();

            job = null;
            job = userJobBean.get(idJob);
            Assert.assertEquals("CTO", job.getName());


        } catch (NamingException ex) {
            Logger.getLogger(UserJobManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }
    }
}

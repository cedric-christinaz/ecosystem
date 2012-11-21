/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.employees;


import com.onlinebox.ecosystem.employees.bean.AccessLevelManagerBean;
import com.onlinebox.ecosystem.employees.entity.AccessLevel;
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
public class AccessLevelManagerBeanTest {

    private static EJBContainer ec;
    private static Context ctx;

    public AccessLevelManagerBeanTest() {
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
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("Admin");
            accessLevel = accessLevelBean.create(accessLevel);

            Assert.assertNotNull("Object accessLevel cannot be null because it is just added in DB", accessLevel);

            if (accessLevel.getId() <= 0) {
                Assert.fail("Object accessLevel must have an id greater than 0");
            }

            //Insert access level with name = null --> not authorized
            accessLevel = new AccessLevel();
            try {
                accessLevel = accessLevelBean.create(accessLevel);
            } catch (Exception e) {
            }
            if (accessLevel.getId() > 0) {
                Assert.fail("Object should not be created because name is empty");
            }

            //Insert access level with name = "" --> not authorized
            accessLevel = new AccessLevel();
            accessLevel.setName("");
            try {
                accessLevel = accessLevelBean.create(accessLevel);
            } catch (Exception e) {
            }

            if (accessLevel.getId() > 0) {
                Assert.fail("Object should not be created because name is empty");
            }

        } catch (NamingException ex) {
            Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void update() throws InterruptedException {
        try {
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");

            //Add a new access level in the database
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("Admin");
            try {
                accessLevel = accessLevelBean.create(accessLevel);
            } catch (Exception ex) {
                Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object accessLevel cannot be null because it is just added in DB", accessLevel);
            if (accessLevel.getId() <= 0) {
                Assert.fail("Object accessLevel must have an id greater than 0");
            }
            
            Thread.sleep(2000); //Pause 2 seconds
            
            //Update the user job --> ok
            accessLevel.setName("Admin Modified");
            try {
                accessLevel = accessLevelBean.update(accessLevel);
            } catch (Exception e) {
                Assert.fail("Object not modified");
            }
            System.out.println("*******************************************************");
            System.out.println(accessLevel.getCreateDate());
            System.out.println(accessLevel.getLastUpdateDate());
            if(accessLevel.getCreateDate().compareTo(accessLevel.getLastUpdateDate()) >= 0){
                Assert.fail("Last Modified date should be greater than create date");
            }

            //Update the access level without giving a name (name = NULL) --> not authorized
            boolean isException = false;
            accessLevel.setName(null);
            try {
                accessLevel = accessLevelBean.update(accessLevel);
                Assert.fail("Object dd should not be modified because name is empty");
            } catch (Exception e) {
                isException = true;
            }
            if (!isException) {
                Assert.fail("Object should not be modified because name is empty");
            }

            //Update the access level without giving a name (name = "") --> not authorized
            isException = false;
            accessLevel.setName("");
            try {
                accessLevel = accessLevelBean.update(accessLevel);
                Assert.fail("Object dd should not be modified because name is empty");
            } catch (Exception e) {
                isException = true;
            }
            if (!isException) {
                Assert.fail("Object should not be modified because name is empty");
            }

        } catch (NamingException ex) {
            Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void delete() {
        try {
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("To be deleted");
            try {
                accessLevel = accessLevelBean.create(accessLevel);
            } catch (Exception ex) {
                Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object accessLevel cannot be null because it is just added in DB", accessLevel);
            if (accessLevel.getId() <= 0) {
                Assert.fail("Object accessLevel must have an id greater than 0");
            }

            try {
                accessLevelBean.delete(accessLevel);
            } catch (Exception e) {
                Assert.fail("Object not deleted");
            }

        } catch (NamingException ex) {
            Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
             Assert.fail("Unable to create bean");
        }
    }

    @Test
    public void getAll() {
        try {
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");

            List<AccessLevel> accessLevels = accessLevelBean.getAll();
            int initialSize = accessLevels.size();

            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            try {
                accessLevel = accessLevelBean.create(accessLevel);
            } catch (Exception ex) {
                Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object accessLevel cannot be null because it is just added in DB", accessLevel);
            if (accessLevel.getId() <= 0) {
                Assert.fail("Object accessLevel must have an id greater than 0");
            }

            accessLevel = new AccessLevel();
            accessLevel.setName("Admin");
            try {
                accessLevel = accessLevelBean.create(accessLevel);
            } catch (Exception ex) {
                Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Assert.assertNotNull("Object job cannot be null because it is just added in DB", accessLevel);
            if (accessLevel.getId() <= 0) {
                Assert.fail("Object job must have an id greater than 0");
            }

            accessLevels = accessLevelBean.getAll();
            Assert.assertNotNull("Jobs list should not be empty", accessLevel);
            Assert.assertEquals("Jobs List should contains 2 elements", 2, (accessLevels.size() - initialSize));


        } catch (NamingException ex) {
            Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }
    }

    @Test
    public void get() throws Exception {
        try {

            long idAccessLevel = -1;
            AccessLevelManagerBean accessLevelBean = (AccessLevelManagerBean) ctx.lookup("java:global/classes/AccessLevelManager");
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setName("User");
            accessLevel = accessLevelBean.create(accessLevel);

            Assert.assertNotNull("Object accessLevel cannot be null because it is just added in DB", accessLevel);

            if (accessLevel.getId() <= 0) {
                Assert.fail("Object accessLevel must have an id greater than 0");
            }
            idAccessLevel = accessLevel.getId();

            accessLevel = null;
            accessLevel = accessLevelBean.get(idAccessLevel);
            Assert.assertEquals("User", accessLevel.getName());


        } catch (NamingException ex) {
            Logger.getLogger(AccessLevelManagerBeanTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Unable to create bean");
        }
    }
}

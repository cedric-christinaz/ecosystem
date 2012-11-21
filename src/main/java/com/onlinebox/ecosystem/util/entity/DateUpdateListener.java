/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebox.ecosystem.util.entity;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author cedric
 */
public class DateUpdateListener {
    
    @PrePersist
    public void onPrePersist(IEntity entity){
        Date now = new Date();    
        entity.setCreateDate(now);      
        entity.setLastUpdateDate(now);
        
        
    }
    
    @PreUpdate
    public void onPreUpdate(IEntity entity){
        Date now = new Date();
        entity.setLastUpdateDate(now);
    }
    
}

package com.onlinebox.ecosystem.util.entity;

/**
 * This interface represents a basic entity that all entities must implements.
 */
public interface IEntity {

	/**
	 * This method returns the primary key of the entity.
	 */
	long getId();

	/**
	 * This method allows to set the primary key of the entity. This method shouldn't be used because the primary key is managed by the database.
	 * @param id
	 */
	void setId(long id);

	/**
	 * This method returns the creation date of the entity.
	 */
	java.util.Date getCreateDate();

	/**
	 * This method allows to set the creation date of the entity.
	 * @param date
	 */
	void setCreateDate(java.util.Date date);

	/**
	 * This method returns the date of the last modification of the entity.
	 */
	java.util.Date getLastUpdateDate();

	/**
	 * This method allows to set the date of the last modification of the entity.
	 * @param date
	 */
	void setLastUpdateDate(java.util.Date date);

}
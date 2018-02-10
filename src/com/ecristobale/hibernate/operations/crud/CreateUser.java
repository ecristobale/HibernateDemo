package com.ecristobale.hibernate.operations.crud;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecristobale.hibernate.entity.User;
import com.ecristobale.hibernate.utils.DateUtils;

/**
 * @author Eduardo Cristóbal Enríquez
 */

/**
 * SessionFactory: read hibernate.cfg.xml. 
 *				creates Session objects.
 *				heavy-weight object.
 *				SINGLETON: it is reused.
 *
 * Session:		it is a wrapper of a JDBC connection.
 *				Object used to save/retrieve objects.
 *				short-lived object (dropped away after do the DB operation).
 *				Retrieved from SessionFactory.
 */
public class CreateUser {

	public static void main(String[] args) throws ParseException {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(User.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		String theDateOfBirthStr = "03/11/1988";
        Date theDateOfBirth = DateUtils.parseDate(theDateOfBirthStr);
		
		try {
			// create User object
			System.out.println("1. Creating new User object and after that beginning a new transaction");
			User tempUser = new User("Eduardo", "Cristobal", theDateOfBirth, "eduardocristobal@email.email");

			// begin transaction
			session.beginTransaction();
			
			// save the object
			System.out.println("2. Inside Transaction: saving the user.");
			int id = (Integer) session.save(tempUser);
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited. User stored with id = " + id);
			
			System.out.println("User: " + tempUser.toString());
			
		} finally {
			session.close();
			factory.close();
		}
	}

}

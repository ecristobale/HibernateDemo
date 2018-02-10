package com.ecristobale.hibernate.operations.crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecristobale.hibernate.entity.User;

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
public class UpdateUser {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(User.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			int id = 1;
			
			// begin transaction
			session.beginTransaction();
			
			// retrieve the object
			System.out.println("1. Retrieve the user with id: " + id);
			User myUser = session.get(User.class, id);
			
			// update the object. As it is a persisted object there is no need to save it!!
			System.out.println("2. Inside Transaction: updating the user.");
			myUser.setLastName("UPDATED Cristóbal");
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited. User updated in the Database.");
			
			System.out.println("User: " + myUser.toString());
			
			System.out.println("\n\n NEW TRANSACTION");
			
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// update email for all users
			System.out.println("Update email for all users");
			
			int rowsUpdated = session.createQuery
					          ("update User set email='email@email.es'")
					          .executeUpdate();
			
			// commit transaction from session
			session.getTransaction().commit();

			System.out.println("* Transaction commited. Updated " + rowsUpdated 
							  + " rows in the Database.");
			
		} finally {
			session.close();
			factory.close();
		}
	}

}

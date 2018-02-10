package com.ecristobale.hibernate.operations.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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
public class DeleteUser {

	public static void main(String[] args) throws IOException {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(User.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			String input = "";
			
			// Ask for a positive number
			do {
				System.out.print("\n1. Please introduce the ID of the object you want to delete from DB: ");
				input = keyboard.readLine();
			} while (!isPositiveNumber(input));
			
			int id = Integer.valueOf(input);
			
			// begin transaction
			session.beginTransaction();
			
			/*// OPTION 1: retrieve the object
			System.out.println("2. Inside Transaction: deleting the user with id: " + id);
			User myUser = session.get(User.class, id);
			
			// delete the object
			session.delete(myUser);*/
			
			// OPTION 2: 
			// creating the query
			String hqlCode = "delete from User where id = :param1";
			Query query = session.createQuery(hqlCode);
									
			// set parameters to the query
			query.setParameter("param1", id);
						
			int rowsDeleted = query.executeUpdate();
			
			// execute a rollback on the transaction and discard changes instead of commit
			//session.getTransaction().rollback();
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited. User deleted with id = " + id);
						
			
		} finally {
			session.close();
			factory.close();
		}
	}
	
	public static boolean isPositiveNumber(String input) {

        boolean isPositiveNumber;

        try {
            int number = Integer.parseInt(input);
            isPositiveNumber = number > 0 ? true:false;
        } catch (NumberFormatException excepcion) {
        	isPositiveNumber = false;
        }

        return isPositiveNumber;
    }

}

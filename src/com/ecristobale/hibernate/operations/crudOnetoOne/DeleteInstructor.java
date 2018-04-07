package com.ecristobale.hibernate.operations.crudOnetoOne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ecristobale.hibernate.entity.Instructor;
import com.ecristobale.hibernate.entity.InstructorDetail;

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
public class DeleteInstructor {

	public static void main(String[] args) throws IOException {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
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
			
			// Retrieve the object
			System.out.println("2. Inside Transaction: deleting the instructor with id: " + id);
			Instructor myInstructor = session.get(Instructor.class, id);
			
			// delete the Instructor retrieved (if no Instructor retrieved, it is null)
			// delete also its details because of CascadeType.ALL
			if(myInstructor!=null) {
				session.delete(myInstructor);
			}
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited. Instructor deleted with id = " + id);
			
		} finally {
			session.close();
			factory.close();
		}
	}
	
	public static boolean isPositiveNumber(String input) {

        try {
            return Integer.parseInt(input) > 0;
        } catch (NumberFormatException excepcion) {
        	return false;
        }
    }

}

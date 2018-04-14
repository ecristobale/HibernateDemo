package com.ecristobale.hibernate.operations.crudOnetoOneBI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecristobale.hibernate.entity.InstructorBI;
import com.ecristobale.hibernate.entity.InstructorDetailBI;

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
public class UpdateInstructor {

	public static void main(String[] args) throws IOException {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(InstructorBI.class)
								.addAnnotatedClass(InstructorDetailBI.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			String input = "";
			
			// Ask for a positive number
			do {
				System.out.print("\n1. Please introduce the ID of the Instructor Detail you want to update from DB: ");
				input = keyboard.readLine();
			} while (!isPositiveNumber(input));
			
			int id = Integer.valueOf(input);
			
			// begin transaction
			session.beginTransaction();
			
			// Retrieve the object
			System.out.println("2. Inside Transaction: retrieving the InstructorDetail with id: " + id);
			InstructorDetailBI myInstructorDetail = session.get(InstructorDetailBI.class, id);
			
			if(myInstructorDetail != null) {
				myInstructorDetail.getInstructorBI().setEmail("MODIFIED " + myInstructorDetail.getInstructorBI().getEmail());
			}else {
				System.out.println("No InstructorDetail found in database for the id: " + id);
			}
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited.");
			
			if(myInstructorDetail != null) {
				System.out.println("Instructor from InstructorDetail updated with id of the InstructorDetail = " + id);
				System.out.println("InstructorDetailBI: " + myInstructorDetail);
				System.out.println("InstructorBI: " + myInstructorDetail.getInstructorBI());
			}
			
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

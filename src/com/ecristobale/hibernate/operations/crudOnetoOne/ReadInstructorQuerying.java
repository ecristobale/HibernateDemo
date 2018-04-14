package com.ecristobale.hibernate.operations.crudOnetoOne;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ecristobale.hibernate.entity.Instructor;
import com.ecristobale.hibernate.entity.InstructorDetail;
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
public class ReadInstructorQuerying {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {

			// begin transaction
			session.beginTransaction();
			
			/** in HQL refer to table and columns with the name they have IN JAVA!! */
			
			// 1. Query all instructors and its details because of CascadeType.ALL: 
			List<Instructor> theInstructors = session.createQuery("from Instructor").getResultList();
			
			// display users
			System.out.println(" 1. All instructors:");
			displayInstructors(theInstructors);
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("\n* Transaction commited. Instructors retrieved.");
			
		} finally {
			session.close();
			factory.close();
		}
	}

	private static void displayInstructors(List<Instructor> theInstructors) {
		for(Instructor tempInstructor:theInstructors) {
			System.out.println(tempInstructor);
		}
	}

}

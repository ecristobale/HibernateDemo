package com.ecristobale.hibernate.operations.crudOnetoOneBI;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecristobale.hibernate.entity.Instructor;
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
public class ReadInstructorDetailQuerying {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(InstructorBI.class)
								.addAnnotatedClass(InstructorDetailBI.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {

			// begin transaction
			session.beginTransaction();
			
			/** in HQL refer to table and columns with the name they have IN JAVA!! */
			
			// 1. Query all instructors and its details because of CascadeType.ALL: 
			List<InstructorDetailBI> theInstructorsDetails = session.createQuery("from InstructorDetailBI").getResultList();
			
			// display users
			System.out.println(" 1. All instructorsDetails:");
			displayInstructorsDetails(theInstructorsDetails);
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("\n* Transaction commited. Instructors Details retrieved.");
			
		} finally {
			session.close();
			factory.close();
		}
	}

	private static void displayInstructorsDetails(List<InstructorDetailBI> theInstructorsDetails) {
		for(InstructorDetailBI tempInstructorDetail:theInstructorsDetails) {
			System.out.println(tempInstructorDetail);
			System.out.println(tempInstructorDetail.getInstructorBI());
		}
	}

}

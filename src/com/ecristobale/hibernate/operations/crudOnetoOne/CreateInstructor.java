package com.ecristobale.hibernate.operations.crudOnetoOne;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecristobale.hibernate.entity.Instructor;
import com.ecristobale.hibernate.entity.InstructorDetail;
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
public class CreateInstructor {

	public static void main(String[] args) throws ParseException {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		String theDateOfBirthStr = "03/11/1988";
        Date theDateOfBirth = DateUtils.parseDate(theDateOfBirthStr);
		
		try {
			// create Instructor and InstructorDetail objects
			System.out.println("1. Creating a new Instructor and InstructorDetail objects");
			Instructor tempInst = new Instructor("Eduardo", "Cristobal", theDateOfBirth, "eduardocristobal@email.email");
			InstructorDetail tempInstDet = new InstructorDetail("Edu Channel","Football");

			// Associate the objects
			tempInst.setInstructorDetail(tempInstDet);
			
			// begin transaction
			session.beginTransaction();
			
			// Save the instructor and its details because cascade is set to CascadeType.ALL:
			System.out.println("2. Inside Transaction: saving the instructor and its details.");
			int id = (Integer) session.save(tempInst);
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited. Instructor stored with id = " + id);
			
			System.out.println("Instructor: " + tempInst.toString());
			
		} finally {
			session.close();
			factory.close();
		}
	}

}

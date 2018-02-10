package com.ecristobale.hibernate.operations.crud;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecristobale.hibernate.entity.User;
import com.ecristobale.hibernate.utils.DateUtils;

public class PrimaryKeyGeneration {

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
			// create 3 User objects
			System.out.println("1. Creating 3 new User objects and after that beginning a new transaction");
			User tempUser1 = new User("User1", "Same", theDateOfBirth, "eduardocristobal@email.email");
			User tempUser2 = new User("User2", "Different", theDateOfBirth, "eduardocristobal@email.email");
			User tempUser3 = new User("User3", "Same", theDateOfBirth, "eduardocristobal@email.email");

			// begin transaction
			session.beginTransaction();
			
			// save the object
			System.out.println("2. Inside Transaction: saving the users.");
			int id1 = (Integer) session.save(tempUser1);
			int id2 = (Integer) session.save(tempUser2);
			int id3 = (Integer) session.save(tempUser3);
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("3. Transaction commited. Users stored with ids = " 
								+ id1 + ", " + id2 + ", " + id3);

			System.out.println("User1: " + tempUser1.toString());
			System.out.println("User2: " + tempUser2.toString());
			System.out.println("User3: " + tempUser3.toString());
			
		} finally {
			session.close();
			factory.close();
		}
	}

}

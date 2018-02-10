package com.ecristobale.hibernate.operations.crud;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class ReadUserQuerying {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(User.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {

			// begin transaction
			session.beginTransaction();
			
			/** in HQL refer to table and columns with the name they have IN JAVA!! */
			
			// 1. Query all users: 
			List<User> theUsers = session.createQuery("from User").getResultList();
			
			// display users
			System.out.println(" 1. All users:");
			displayUsers(theUsers);
			
			// 2. Query users: lastName='same'
			theUsers = session.createQuery("from User s where s.lastName='Same'")
							  .getResultList();
			
			// display users
			System.out.println("\n 2. Users who have lastName='Same'");
			displayUsers(theUsers);
			
			// 3. Query users: lastName='Different' OR firstName='Eduardo'
			theUsers = session.createQuery("from User s where "
					+ "s.lastName='Different' OR s.firstName='Eduardo'")
							  .getResultList();
			
			// display users
			System.out.println("\n 3. Users who have lastName='Different' OR firstName='Eduardo'");
			displayUsers(theUsers);
			
			// 3. Query users: email LIKE '%@%'
			theUsers = session.createQuery("from User s where s.email LIKE '%@%'")
							  .getResultList();
			
			// display users
			System.out.println("\n 4. Users who have email LIKE '%@%'");
			displayUsers(theUsers);
			
			// 4. Query users using parameters: where firstName LIKE :param1 (Edu%) 
			String param1 = "%3";
			String param2 = "Same";
					
			// creating the query
			String hqlCode = "from User where firstName LIKE :param1 AND lastName = :param2";
			Query query = session.createQuery(hqlCode);
						
			// set parameters to the query
			query.setParameter("param1", param1);
			query.setParameter("param2", param2);
			
			theUsers = query.getResultList();
			
			// display users
			System.out.println("\n 5. Users who have firstname LIKE :param1 ('%3') AND lastName = :param2 ('Same')");
			displayUsers(theUsers);
			
			// commit transaction from session
			session.getTransaction().commit();
			
			System.out.println("\n* Transaction commited. Users retrieved.");
			
		} finally {
			session.close();
			factory.close();
		}
	}

	private static void displayUsers(List<User> theUsers) {
		for(User tempUser:theUsers) {
			System.out.println(tempUser);
		}
	}

}

package com.example.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.hibernate.demo.entity.Student;

public class UpdateStudentDemo {

	public static void main(String[] args) {
		// In Hibernate, data manipulation is done by "Session" objects which are generally short lived and one time.
		// In order create sessions, we need a "SessionFactory" object. This is a heavy weight object and will typically be
		// created only once. After that we can get sessions from this factory.
		SessionFactory sessionFactory = new Configuration()
											.configure("hibernate.cfg.xml")
											.addAnnotatedClass(Student.class)
											.buildSessionFactory();
	
		Session mySession = sessionFactory.getCurrentSession();
		
		try {
			mySession.beginTransaction();
			
			int studentID = 1;
			
			// Retrieving a student object form the database		
			System.out.println("Getting student with ID: " + studentID);
			
			Student retrievedStudent = mySession.get(Student.class, studentID);

			System.out.println("Updating Student...");
			
			retrievedStudent.setFirstName("Rahim");
			
			mySession.getTransaction().commit();
			
			// Update using query
			mySession = sessionFactory.getCurrentSession();
			mySession.beginTransaction();
			
			mySession.createQuery("update Student set email='rahim@outlook.com' where id=1").executeUpdate();
			
			// Commiting is essential for any database operation in hibernate
			mySession.getTransaction().commit();
			
			System.out.println("Done!");
		} finally {
			sessionFactory.close();
		}
	}

}

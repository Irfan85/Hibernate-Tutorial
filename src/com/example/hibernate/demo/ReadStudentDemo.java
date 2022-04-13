package com.example.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.hibernate.demo.entity.Student;

public class ReadStudentDemo {

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
			System.out.println("Creating a Student object...");
			Student student = new Student("Abdul", "Kuddus", "abdulkuddus@gmail.com");
			
			mySession.beginTransaction();
			
			System.out.println("Saving the student object");
			System.out.println(student);
			mySession.save(student);
			
			mySession.getTransaction().commit();
			
			System.out.println("Saved student. Generated ID: " + student.getId());
			
			// Retrieving a student object form the database
			// Since it's a new sql operation, we have to create a new session
			mySession = sessionFactory.getCurrentSession();
			mySession.beginTransaction();
			
			System.out.println("Getting student with ID: " + student.getId());
			
			Student retrievedStudent = mySession.get(Student.class, student.getId());
			
			System.out.println("Found Student: " + retrievedStudent);
			
			// Commiting is essential for any database operation in hibernate
			mySession.getTransaction().commit();
			
			System.out.println("Done!");
		} finally {
			sessionFactory.close();
		}
	}

}

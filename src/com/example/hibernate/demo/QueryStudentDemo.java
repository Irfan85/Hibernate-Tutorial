package com.example.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.hibernate.demo.entity.Student;

public class QueryStudentDemo {

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
			
			// Query: all
			List<Student> students = mySession.createQuery("from Student").getResultList();
			
			displayStudents(students);
			System.out.println();
			
			// Query: lastName = Ali. Note that we have to use the field names in our java class instead of the SQL table
			// column names
			students = mySession.createQuery("from Student s where s.lastName='Ali'").getResultList();
			
			System.out.println("Students who have last name of Ali");
			displayStudents(students);
			System.out.println();
			
			// Query: lastName = Ali or firstName = "Abdul"
			students = mySession.createQuery("from Student s where s.lastName='Ali' OR s.firstName='Abdul'").getResultList();
			
			System.out.println("Students who have last name of Ali or first name of Abdul");
			displayStudents(students);
			System.out.println();
			
			// Query: email address ends with gmail.com
			students = mySession.createQuery("from Student s where s.email LIKE '%gmail.com'").getResultList();
			
			System.out.println("Students whos' email ends with gmail.com");
			displayStudents(students);
			System.out.println();
			
			
			mySession.getTransaction().commit();
			
			System.out.println("Done!");
		} finally {
			sessionFactory.close();
		}
	}

	private static void displayStudents(List<Student> students) {
		for(Student student : students) {
			System.out.println(student);
		}
	}

}

package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import roles.PermissionsDeniedException;
import roles.User;
import data.Course;
import data.Data;
import data.DataInterface;
import data.Session;
import data.Student;


public class UserStoriesTests {
		private Course course;
		private Student student;
		private Session session;
		private User user;
		private DataInterface data;
		
		@Before
		public void setUp() throws Exception {
			//Given a
			course = new Course("PSD3");
			student = new Student("1","Mihail","Tachev");
			session = new Session(course, "Lab1");
			data = Data.getSingleton();
		}
		
		@Test
		public void assignSessionToCourseTest() throws PermissionsDeniedException{
			//if lecturer
			user = new User(false,true,false,false);
			//When there is session associated with that course
			//Then assign that session to the course
			if (session == null) {
				System.out.println("session is null");
			}
			if (course == null) {
				System.out.println("course is null");
			}
			user.addSessionToCourse(session.getTitle(), course.getCourseTitle());
			assertTrue(data.hasSession(session.getTitle()));
		}
		
		@Test
		public void bookSlotForCourseTest() throws PermissionsDeniedException{
			//if student
			user = new User(false,false,false,true);
			//When enrolled in that course
			course.addStudent(student);
			user.bookSlotForCourse(student.getId(), session.getTitle(), course.getCourseTitle());
			//Then I have to be in that session 
			assertTrue(data.hasSession(session.getTitle()));
			assertTrue(data.hasStudent(student.getId()));
		}
		
		@Test
		public void assignRoomTest() throws PermissionsDeniedException{
			//if administrator
			user = new User(true,false,false,false);
			session.setRoom("BO507");
			course.addSession(session);
			//When created a timetable slot
			user.createTimeSlotForSession(new Date(2014,2,22,10,00), new Date(2014,2,22,11,00), "PSD3", session.getTitle());
			//Then assign a room to that timetable slot
			user.assignRoomToSession("BO507", session.getTitle());
			assertEquals(data.getSession(session.getTitle()).getRoom(),session.getRoom());
		}
		
}

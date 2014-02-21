package testing;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import roles.PermissionsDeniedException;
import roles.User;
import data.Course;
import data.Data;
import data.Session;
import data.Student;

public class UserStoriesTests {
		private Course course;
		private Student student;
		private Session session;
		private User user;
		private Data data;
		
		@Before
		public void setUp(){
			//Given a
			course = new Course("PSD3");
			student = new Student("1","Mihail","Tachev");
			session = new Session(course, "Lab1");
			data = new Data();
		}
		
		@Test
		public void assignSessionToCourseTest() throws PermissionsDeniedException{
			setUp();
			//if lecturer
			user = new User(false,true,false,false);
			//When there is session associated with that course
			//Then assign that session to the course
			user.addSessionToCourse(session.getTitle(), course.getCourseTitle());
			assertTrue(user.getData().hasSession(session));
		}
		
		@Test
		public void bookSlotForCourseTest() throws PermissionsDeniedException{
			setUp();
			//if student
			user = new User(false,false,false,true);
			//When enrolled in that course
			course.addStudent(student);
			user.bookSlotForCourse(student.getId(), session.getTitle(), course.getCourseTitle());
			//Then I have to be in that session 
			assertTrue(user.getData().hasSession(session));
			assertTrue(user.getData().hasStudents(student));
		}
		
		@Test
		public void assignRoomTest() throws PermissionsDeniedException{
			setUp();
			//if administrator
			user = new User(true,false,false,false);
			session.setRoom("BO507");
			course.addSession(session);
			//When created a timetable slot
			user.createTimeSlotForSession(new Date(2014,2,22,10,00), new Date(2014,2,22,11,00), "PSD3", session.getTitle());
			//Then assign a room to that timetable slot
			user.assignRoomToSession("BO507", session.getTitle());
			assertEquals(user.getData().getSession(session.getTitle()).getRoom(),session.getRoom());
		}
		
}

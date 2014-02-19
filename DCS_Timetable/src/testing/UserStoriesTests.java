package testing;

import static org.junit.Assert.*;

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
			//Given a course
			course = new Course("PSD3");
			student = new Student("1","Mihail","Tachev");
			session = new Session(course, "Lab1");
			user = new User(false,false,false,true);
			data = new Data();
		}
		@Test
		public void bookSlotForCourseTest() throws PermissionsDeniedException{
			setUp();
			//When enrolled in that course
			course.addStudent(student);
			user.bookSlotForCourse(student.getId(), session.getTitle(), course.getCourseTitle());
			//Then I have to be in that session 
			assertTrue(user.getData().hasSession(session));
			assertTrue(user.getData().hasStudents(student));
		}
		
}

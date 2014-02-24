package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import roles.AdministratorRole;
import roles.LecturerRole;
import roles.PermissionsDeniedException;
import roles.StudentRole;
import roles.User;
import data.Course;
import data.Data;
import data.DataInterface;
import data.MyCampusCourseImport;
import data.Session;
import data.Student;


public class UserStoriesTests {
	private Course course;
	private Student student;
	private Session session;
	//private User user;
	private DataInterface data;
	
	@Before
	public void setUp() throws Exception {
		//Given a
		course = new Course("PSD3");
		student = new Student("1","Mihail","Tachev");
		session = new Session(course, "Lab1");
		data = Data.getSingleton();
	}
	
	/**
	 * Testing user story 1:
	 * As a lecturer, 
	 * I want to import a MyCampus course,
	 * So that teaching sessions can be identified.
	 */
	@Test
	public void lecturerImportMyCampusCourseTest() {
		// Mock course details
		ArrayList<String> students = new ArrayList<String>();
		students.add("student1");
		students.add("student2");
		ArrayList<String> sessions = new ArrayList<String>();
		sessions.add("session1");
		final String myCampusCourseToImport = "Course1";
		final Set<String> studentSet = new HashSet<String>(students);
		final Set<String> sessionSet = new HashSet<String>(sessions);
		// Mock MyCampus importer
		final class MyCampusImporterMockImplementation implements MyCampusCourseImport {
			public String getCourseTitle() {
				return myCampusCourseToImport;
			}
			public Set<String> getStudentsEnrolled() {
				return studentSet;
			}
			public Set<String> getAssociatedSessions() {
				return sessionSet;
			}
		}
		LecturerRole user = new User(false,true,false,false);
		try {
			user.importMyCampusTeachingSession(new MyCampusImporterMockImplementation());
			for (String s : sessionSet) {
				assertTrue(data.hasSession(s));
			}
			for (String s : studentSet) {
				assertTrue(data.hasStudent(s));
			}
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Testing user story 2:
	 * As a lecturer, 
	 * I want to add a session to a course,
	 * So that timetable slots can be identified.
	 */
	@Test
	public void lecturerAssignSessionToCourseTest() {
		LecturerRole user = new User(false,true,false,false);
		try {
			user.addSessionToCourse(session.getTitle(), course.getCourseTitle());
			assertTrue(data.hasSession(session.getTitle()));
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Testing user story 4:
	 * As a lecturer, 
	 * I want to specify that a session is a one off, 
	 * or recurs weekly or fortnightly,
	 * So that I don't have to create a large number of sessions.
	 */
	@Test
	public void lecturerSessionRecurenceTest() {
		LecturerRole user = new User(false,true,false,false);
		try {
			int periodicity = 2;
			user.specifyPeriodicityForSession(session.getTitle(), periodicity);
			assertEquals(periodicity, data.getSession(session.getTitle()).getPeriodicity());
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Test for user story 8:
	 * As a administrator,
	 * I want to assign a room to a timetable slot,
	 * So that room bookings can be recorded.
	 */
	@Test
	public void administratorAssignRoomTest() {
		AdministratorRole user = new User(true,false,false,false);
		String room = "BO507";
		course.addSession(session);
		try {
			user.createTimeSlotForSession(new Date(), new Date(), "PSD3", session.getTitle());
			user.assignRoomToSession(room, session.getTitle());
			assertEquals(room, data.getSession(session.getTitle()).getRoom());
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Test for user story 11:
	 * As a student,
	 * I want to book a timetable slot for each session of my course,
	 * So that I can take the course.
	 */
	@Test
	public void studentBookSlotForCourseTest() {
		StudentRole user = new User(false,false,false,true);
		// When enrolled in that course
		course.addStudent(student);
		try {
			user.bookSlotForCourse(student.getId(), session.getTitle(), course.getCourseTitle());
			assertTrue(data.hasSession(session.getTitle()));
			assertTrue(data.hasStudent(student.getId()));
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}	
}

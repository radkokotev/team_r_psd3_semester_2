package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import roles.AdministratorRole;
import roles.LecturerRole;
import roles.PermissionsDeniedException;
import roles.StudentRole;
import roles.TutorRole;
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
	private Date date;
	private DataInterface data;
	private boolean isAlive = true;
	
	@Before
	public void setUp() throws Exception{
		//Given a
		course = new Course("PSD3");
		student = new Student("1","Mihail","Tachev");
		session = new Session(course, "Lab1");
		date = new Date();
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
			user.createTimeSlotForSession(date, date, "PSD3", session.getTitle());
			user.assignRoomToSession(room, session.getTitle());
			assertEquals(room, data.getSession(session.getTitle()).getRoom());
			// revert back to no room
			user.assignRoomToSession("", session.getTitle());
			assertEquals("", data.getSession(session.getTitle()).getRoom());
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
	
	/**
	 * Test for user story 12:
	 * As a student,
	 * I want to check that I have signed up for all compulsory sessions,
	 * So that I don't fail the course.
	 */
	@Test
	public void studentChecksCompulsorySessionsTest() {
		StudentRole user = new User(false,false,false,true);
		// When enrolled in that course
		Student anotherStudent = new Student("anotherStudent");
		course.addStudent(anotherStudent);
		String compulsory;
		String enrolled;
		try {
			// check no compulsory
			compulsory = user.getAllCompulsorySessionsForCourse(anotherStudent.getId(), 
					course.getCourseTitle());
			enrolled = user.getSessionsForWhichEnrolled(anotherStudent.getId(), 
					course.getCourseTitle());
			assertEquals(compulsory, enrolled);
			
			// check not enrolled for all
			User admin = new User(true,false,false,false);
			// set session to be compulsory
			admin.setSessionToBeCompulsory(session.getTitle(), true);
			compulsory = user.getAllCompulsorySessionsForCourse(anotherStudent.getId(), 
					course.getCourseTitle());
			enrolled = user.getSessionsForWhichEnrolled(anotherStudent.getId(), 
					course.getCourseTitle());
			boolean match = compulsory.equals(enrolled);
			assertFalse(match);
			
			// check enrolled for all
			user.bookSlotForCourse(anotherStudent.getId(), session.getTitle(), 
					course.getCourseTitle());  // enrol student for the session
			compulsory = user.getAllCompulsorySessionsForCourse(student.getId(), 
					course.getCourseTitle());
			enrolled = user.getSessionsForWhichEnrolled(anotherStudent.getId(), 
					course.getCourseTitle());
			assertEquals(compulsory, enrolled);
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Testing user story 14:
	 * As a tutor,
	 * I want to see the details (time, location, students, tutor) of 
	 * every timetable slot in a session,
	 * So that I know when sessions happen.
	 */
	@Test
	public void lecturerGetSessionDetailsTest() {
		TutorRole user = new User(false,false,true,false);
		try {
			String result = user.getInformationForSession(session.getTitle());
			assertFalse(result.isEmpty());
		} catch (PermissionsDeniedException e) {
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Testing user story 9(the new one for last sprint):
	 * As a administrator,
	 * I want to check that there are no timetable slot clashes between courses
	 * So that students are able to complete the course.
	 */
	@Test
	public void checkCourseTimeSlotClashesTest() {
		// if administrator
		AdministratorRole user = new User(true,false,false,false);
		try{
		// Given two course with start and end
			Calendar startPSD = new GregorianCalendar();
			Calendar endPSD = new GregorianCalendar();
			startPSD.set(2014, 03, 03, 13, 30);
			endPSD.set(2014, 03, 03, 14, 30);
			Course PSD = new Course("PSD3", startPSD, endPSD );
			Calendar startTP = new GregorianCalendar();
			Calendar endTP = new GregorianCalendar();
			startTP.set(2014, 03, 03, 14, 45);
			endTP.set(2014, 03, 03, 15, 30);
			Course TP = new Course("PSD3", startTP, endTP);
			//check if they overlap
			assertTrue(user.checkCourseTimeSlotClashes(startPSD,endPSD,startTP,endTP));
		} catch (PermissionsDeniedException e) {
			// or throw permission denied exception
			fail("Unexpected permission denial");
		}
	}
	
	/**
	 * Test permissions denied
	 * @throws PermissionsDeniedException will through an exception
	 */
	@Test(expected = PermissionsDeniedException.class) 
	public void permissionsDeniedTest() throws PermissionsDeniedException {
		User user = new User(false,false,false,false);
		user.getSessionsForWhichEnrolled(student.getId(), course.getCourseTitle());
	}
	
	/**
	 * Test security/1 non functional requirement
	 * The system shall distinguish between lecturers, administrators, tutors and student roles.
	 */
	@Test
	public void testUsersPrivileges(){
		User admin = new User(true, false, false, false);
		User lecturer = new User(false, true, false, false);
		User tutor = new User(false, false, true, false);
		User student = new User(false, false, false, true);
		
		assertTrue(admin.isAdmin());
		assertTrue(lecturer.isLecturer());
		assertTrue(tutor.isTutor());
		assertTrue(student.isStudent());
		assertFalse(admin.isStudent());
		assertFalse(admin.isLecturer());
		assertFalse(tutor.isLecturer());
		assertFalse(student.isAdmin());
	}
	
	/**
	 * Testing performance/0 non functional requirement
	 * The system shall support at least 100 courses.
	 * @throws Exception 
	 */
	@Test
	public void testCourses100() {
		int initial = data.getNumberOfCourses();
		for (int i = 0; i < 100; i++){
			data.getCourse((Integer.toString(i)));
		}
		assertEquals(initial + 100, data.getNumberOfCourses());
	}
	
	/**
	 * Testing performance/1 non functional requirement
	 * The system shall support at least 10 different session types per course
	 * @throws Exception 
	 */
	@Test
	public void testSessions10(){
		course = data.getCourse("PSD");
		for(int i = 0; i < 10; i++){
			data.assignSessionToCourse(Integer.toString(i), course.getCourseTitle());
			Session session = data.getSession(Integer.toString(i));
			session.setType(Integer.toString(i));
		}
		assertTrue(data.getSessionsForCourse(course.getCourseTitle()).size() >= 10);
	}
	
	private boolean getRandomBoolean(){
		return  Math.random() < 0.5;
	}
	
	/**
	 * Testing performance/2 non functional requirement
	 * The system shall support at least 1000 different users
	 * @throws Exception 
	 */
	
	@Test
	public void testUsers1000(){
		User[] users = new User[1000];
		for(int i = 0; i < 1000; i++){
			users[i] = new User(getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean());
		}
		
	}
	
	/**
	 * Testing performance/3 non functional requirement
	 * The system shall support at least 20 different timetable slots per session
	 * @throws PermissionsDeniedException 
	 * @throws Exception 
	 */
	
	@Test
	public void testSlots20() throws PermissionsDeniedException {
		data.assignSessionToCourse(session.getTitle(), course.getCourseTitle());
		User admin = new User(true, false, false, false);
		long twentyWeeksLater = 20 * 7 * 24 * 60 * 60 * 1000 + System.currentTimeMillis();
		int oneWeek = 7 * 24 * 60 * 60 * 1000;
		admin.createTimeSlotForSession(new Date(), new Date(twentyWeeksLater), course.getCourseTitle(), session.getTitle());
		session.setPeriodicity(oneWeek);
	}
	
	/**
	 * Testing performance/4 non functional requirement
	 * The system shall support at least 100 different concurrently active users.
	 * @throws InterruptedException 
	 */
	
	@Test
	public void concurrent100Users() throws InterruptedException{
		Thread[] ths = new Thread[100];
		for(int i = 0; i < 100; i++) {
			ths[i] = new Thread(new Worker());
			ths[i].start();
		}
		for(Thread t : ths)
			t.join();
	}
	private synchronized boolean isAlive(){
		return isAlive;
	}
	
	private synchronized void setIsAlive(boolean alive){
		isAlive = alive;
	}
	
	private class Worker implements Runnable{
		@Override
		public void run() {
			int i = 0;
			long id = Thread.currentThread().getId();
			User u = new User(true, true, true, true);
			while(isAlive()) {
				try {
					synchronized(u.getData()) {
						u.addSessionToCourse("lab" + Long.toString(id) + Integer.toString(i), "course" + Long.toString(id) + Integer.toString(i));
					}
					synchronized(u.getData()) {
						u.assignRoomToSession("room" + Long.toString(id) + Integer.toString(i), "lab" + Long.toString(id) + Integer.toString(i));
					}
					synchronized(u.getData()) {
						u.bookSlotForCourse(Long.toString(id), "lab" + Long.toString(id) + Integer.toString(i), "course" + Long.toString(id) + Integer.toString(i));
					}
				} catch (PermissionsDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Thread.activeCount() >= 100) {
					setIsAlive(false);
					assert(true);
				}
				i++;
			}
		}
	}
}

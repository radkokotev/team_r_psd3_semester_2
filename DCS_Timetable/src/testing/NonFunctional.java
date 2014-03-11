package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class NonFunctional {

	private Course course;
	private Session session;
	private DataInterface data;
	private boolean isAlive = true;

	@Before
	public void setUp() throws Exception{
		//Given a
		course = new Course("PSD3");
		session = new Session(course, "Lab1");
		data = Data.getSingleton();
	}
	
	/**
	 * Test security/0 non functional requirement
	 * The system shall authenticate users via the MyCampus single sign-on service.
	 */
	@Test
	public void testMyCampusLogin(){
		User user1 = new User(getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), true);
		User user2 = new User(getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), true);
		User user3 = new User(getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), false);
		User user4 = new User(getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), false);
		
		assertTrue(user1.loginMyCampus());
		assertTrue(user2.loginMyCampus());
		assertFalse(user3.loginMyCampus());
		assertFalse(user4.loginMyCampus());
	}
	
	
	/**
	 * Test security/1 non functional requirement
	 * The system shall distinguish between lecturers, administrators, tutors and student roles.
	 */
	@Test
	public void testUsersPrivileges(){
		User admin = new User(true, false, false, false, true);
		User lecturer = new User(false, true, false, false, true);
		User tutor = new User(false, false, true, false, true);
		User student = new User(false, false, false, true, true);
		
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
			users[i] = new User(getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean(), getRandomBoolean());
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
		User admin = new User(true, false, false, false, true);
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
			User u = new User(true, true, true, true, true);
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

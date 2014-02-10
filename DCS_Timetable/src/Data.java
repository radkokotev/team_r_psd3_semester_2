import java.util.HashMap;
import java.util.Set;


public class Data {
	private static Data instance;
	
	private HashMap<String, Course> titlesToCourses;
	private HashMap<String, Session> titlesToSession;
	private HashMap<String, Student> idsToStudents;
	
	private Data() {
		titlesToCourses = new HashMap<String, Course>();;
		titlesToSession = new HashMap<String, Session>();
		idsToStudents = new HashMap<String,Student>();
	}
	
	public static Data getSingleton() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}
	
	public Student getStudent(String id) {
		Student student = idsToStudents.get(id);
		if (student == null) {
			student = new Student(id);
			idsToStudents.put(id, student);
		}
		return student;
	}
	
	public Course getCourse(String title) {
		Course course = titlesToCourses.get(title);
		if (course == null) {
			course = new Course(title);
			titlesToCourses.put(title, course);
		}
		return course;
	}
	
	public Session getSession(String title) {
		return titlesToSession.get(title);
	}
	
	public void assignSessionToCourse(String sessionTitle, String courseTitle) {
		Session session = getSession(sessionTitle);
		Course course = getCourse(courseTitle);
		if (session == null) {
			session = new Session(course, sessionTitle);
			titlesToSession.put(sessionTitle, session);
		}
		course.addSession(session);
	}
	
	public void assignStudentToCourse(String id, String courseTitle) {
		Course course = getCourse(courseTitle);
		Student student = getStudent(id);
		course.addStudent(student);
	}
	
	public void assignStudenttoSession(String id, String sessionTitle, String courseTitle) {
		Session session = getSession(sessionTitle);
		Course course = getCourse(courseTitle);
		Student student = getStudent(id);
		if (session == null) {
			session = new Session(course, sessionTitle);
			titlesToSession.put(sessionTitle, session);
		}
		course.addSession(session);
		session.addStudent(student);
	}
	
	public Set<Student> getStudentsForCourse(String courseTitle) {
		Course course = getCourse(courseTitle);
		return course.getStudents();
	}
	
	public Set<Session> getSessionsForCourse(String courseTitle) {
		Course course = getCourse(courseTitle);
		return course.getSessions();
	}
	
	public Set<Student> getStudentsForSession(String sessionTitle, String courseTitle) {
		Course course = getCourse(courseTitle);
		Session session = getSession(sessionTitle);
		if (session == null) {
			session = new Session(course, sessionTitle);
			titlesToSession.put(sessionTitle, session);
		}
		return session.getStudents();
	}
	
	public void importCourseFromMyCampus(MyCampusCourseImport myCampus) {
		Course course = getCourse(myCampus.getCourseTitle());
		for (String sessionTitle : myCampus.getAssociatedSessions()) {
			assignSessionToCourse(sessionTitle, course.getCourseTitle());
		}
		for (String studentId : myCampus.getStudentsEnrolled()) {
			assignStudentToCourse(studentId, course.getCourseTitle());
		}
	}
}

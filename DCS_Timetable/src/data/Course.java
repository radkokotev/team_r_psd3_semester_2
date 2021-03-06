package data;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public class Course {
	private String courseTitle;
	private HashSet<Session> sessions;
	private HashSet<Student> students;
	private Calendar start;
	private Calendar end;
	
	public Course (String title) {
		this.courseTitle = title;
		sessions = new HashSet<Session>();
		//has to instantiate this hashset as well
		students = new HashSet<Student>();
	}
	
	// new constructor with new properties instantiated
	public Course (String title,Calendar start, Calendar end) {
		this.courseTitle = title;
		sessions = new HashSet<Session>();
		//has to instantiate this hashset as well
		students = new HashSet<Student>();
		// start and end date provided
		this.start = start;
		this.end = end;
	}
		
	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void addSession (Session s) {
		Data data = Data.getSingleton();
		sessions.add(s);
		data.assignSessionToCourse(s.getTitle(), courseTitle);
		
	}
	
	public boolean hasSession(Session s) {
		return sessions.contains(s);
	}
	
	public Set<Session> getSessions(){
		return sessions;
	}
	
	public void addStudent (Student s) {
		students.add(s);
	}
	
	public boolean hasStudent(Student s) {
		return students.contains(s);
	}
	
	public Set<Student> getStudents(){
		return students;
	}
}

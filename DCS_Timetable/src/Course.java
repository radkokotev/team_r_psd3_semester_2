import java.util.HashSet;
import java.util.Set;


public class Course {
	private String courseTitle;
	private HashSet<Session> sessions;
	private HashSet<Student> students;
	
	
	public Course (String title) {
		this.courseTitle = title;
		sessions = new HashSet<Session>();
	}
		
	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void addSession (Session s) {
		Data data = Data.getSingleton();
		data.assignSessionToCourse(s.getTitle(), courseTitle);
		sessions.add(s);
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

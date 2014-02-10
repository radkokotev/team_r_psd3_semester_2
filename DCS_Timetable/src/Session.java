import java.util.HashSet;
import java.util.Set;


public class Session {
	private String name;
	private Course course;
	private HashSet<Student> students;
	
	public Session (Course course) {
		this.course = course;
		this.name = "";
		this.students = new HashSet<Student>();
	}
	
	public Session (Course course, String name) {
		this.course = course;
		this.name = name;
		this.students = new HashSet<Student>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

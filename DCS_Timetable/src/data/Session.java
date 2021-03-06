package data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author	Kotev, Greblikas, Turner, Vascila, Tachev
 * @version	1.0
 * 
 * Provides functionality for timetable management system.
 * 
 * Method javadocs to be added in future.
 */

public class Session {
	private String title;
	private Course course;
	private HashSet<Student> students;
	private String room;
	private String tutor;
	private Date startTime;
	private Date endTime;
	private int periodicity;
	private boolean isCompulsory;
	private String type;
	
	public Session (Course course) {
		this.course = course;
		this.title = "";
		this.room = "";
		this.students = new HashSet<Student>();
		this.startTime = new Date();
		this.endTime = new Date();
		this.tutor = "";
		this.students = new HashSet<Student>();
		this.type = "Lab";
	}
	
	public Session (Course course, String title) {
		this.title = title;
		this.course = course;
		this.room = "";
		this.students = new HashSet<Student>();
		this.startTime = new Date();
		this.endTime = new Date();
		this.tutor = "";
		this.students = new HashSet<Student>();
		this.type = "Lab";
	}
	
	public Session (Course course, String title, Date startTime, Date endTime) {
		this.course = course;
		this.title = title;
		this.room = "";
		this.students = new HashSet<Student>();
		this.startTime = startTime;
		this.endTime = endTime;
		this.tutor = "";
		this.students = new HashSet<Student>();
		this.type = "Lab";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public String getTutor() {
		return tutor;
	}
	
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	
	public String getRoom() {
		return room;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date time) {
		startTime = time;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date time) {
		endTime = time;
	}
	
	public int getPeriodicity() {
		return periodicity;
	}
	
	public void setPeriodicity(int period) {
		periodicity = period;
	}
	
	public boolean getIsCompulsory() {
		return isCompulsory;
	}
	
	public void setIsCompulsory(boolean isCompulsory) {
		this.isCompulsory = isCompulsory;
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
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	@Override
	public String toString() {
		String result =  "Session title: " + title +
				", type: " + type + 
				", tutor: " + tutor +
				", is compulsory: " + isCompulsory +
				", room: " + room + 
				", startTime: " + startTime.toString() + 
				", endTime: " + endTime.toString() + 
				", periodicity: " + periodicity + 
				"\nStudent list:\n";
		for (Student s : students) {
			result += s.getId() + "\n";
		}
		return result;
	}
}

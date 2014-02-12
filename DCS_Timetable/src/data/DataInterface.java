package data;

import java.util.Set;

public interface DataInterface {
	
	public Student getStudent(String id);
	
	public Course getCourse(String title);
	
	public Session getSession(String title);
	
	public void assignSessionToCourse(String sessionTitle, String courseTitle);
	
	public void assignStudentToCourse(String id, String courseTitle);
	
	public void assignStudenttoSession(String id, String sessionTitle, String courseTitle);
	
	public Set<Student> getStudentsForCourse(String courseTitle);
	
	public Set<Session> getSessionsForCourse(String courseTitle);
	
	public Set<Student> getStudentsForSession(String sessionTitle, String courseTitle);
	
	public void importCourseFromMyCampus(MyCampusCourseImport myCampus);
}

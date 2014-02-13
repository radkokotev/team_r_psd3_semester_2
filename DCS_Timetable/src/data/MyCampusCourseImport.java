package data;
import java.util.Set;

/**
 * Mock external service to import courses from MyCampus.
 */
public interface MyCampusCourseImport {
	/**
	 * A method to return a mock course title
	 * @return the title of the course
	 */
	public String getCourseTitle();
	
	/**
	 * A method to return a mock student set
	 * @return a set of students enrolled into the course
	 */
	public Set<String> getStudentsEnrolled();
	
	/**
	 * A method to return a mock session set
	 * @return a set of sessions associated with the course
	 */
	public Set<String> getAssociatedSessions();
}

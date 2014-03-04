package data;

import java.util.Calendar;
import java.util.Set;

/**
 * An interface to provide access API for a mocked database
 */
public interface DataInterface {

	/**
	 * Gets a student object by the student id
	 * @param id the matriculation number of the student
	 * @return the Student object corresponding to this id
	 */
	public Student getStudent(String id);

	/**
	 * Gets a course object by the course title
	 * @param title the title of the course
	 * @return the Course object corresponding to the title
	 */
	public Course getCourse(String title);
	
	/**
	 * Gets a Session object by the title of the session
	 * @param title the title of the session
	 * @return the Session object corresponding to the title
	 */
	public Session getSession(String title);
	
	/**
	 * Assigns a session to the a course (if either doesn't exist it gets created)
	 * @param sessionTitle the title of the session to be assigned
	 * @param courseTitle the title of the course to be assigned
	 */
	public void assignSessionToCourse(String sessionTitle, String courseTitle);
	
	/**
	 * Assigns a student to a course (if either doesn't exist it gets created)
	 * @param id student matriculation number
	 * @param courseTitle the title of the course
	 */
	public void assignStudentToCourse(String id, String courseTitle);
	
	/**
	 * Assigns a student to a specific session
	 * @param id matriculation number of the student
	 * @param sessionTitle title of the session
	 * @param courseTitle title of the course
	 */
	public void assignStudenttoSession(String id, String sessionTitle, String courseTitle);
	
	/**
	 * Gets the student set for a given course
	 * @param courseTitle title of the course
	 * @return a set of Students enrolled into the course
	 */
	public Set<Student> getStudentsForCourse(String courseTitle);
	
	/**
	 * Gets the session set associated with a given course
	 * @param courseTitle title of the course
	 * @return a set of sessions assigned to the course
	 */
	public Set<Session> getSessionsForCourse(String courseTitle);
	
	/**
	 * Gets the student set for a given session 
	 * @param sessionTitle session title
	 * @param courseTitle course that accommodates the session
	 * @return the set of students associated with the session
	 */
	public Set<Student> getStudentsForSession(String sessionTitle, String courseTitle);
	
	/**
	 * Imports a course from a MyCampus importer
	 * @param myCampus the import delegate for a given course
	 */
	public void importCourseFromMyCampus(MyCampusCourseImport myCampus);
	
	/**
	 * Checking if a session is already registered
	 * @param sessionTitle the title of the session 
	 * @return true if the session already exists and false otherwise
	 */
	public boolean hasSession(String sessionTitle);

	/**
	 * Checking if a student is already registered
	 * @param studentId the matriculation number of the student 
	 * @return true if the student already exists and false otherwise
	 */
	public boolean hasStudent(String studentId);
	
	/**
	 * Checking if there are time slot clashes between courses
	 * @param start of Course1 data object
	 * @param end of Course1 data object
	 * @param start of Course2 data object
	 * @param end of Course2 data object
	  * @return true if time slots do not overlap (no clashes)
	 */
	public boolean checkCourseTimeSlotClashes(Calendar start1, Calendar end1, Calendar start2, Calendar end2);
	
	/**
	 * @return number of all existing courses in database
	 */
	public int getNumberOfCourses();
}

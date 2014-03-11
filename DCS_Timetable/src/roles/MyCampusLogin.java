package roles;

/**
 * Mock external service for logging in from MyCampus.
 */
public interface MyCampusLogin {
	/**
	 * A method to return a mock login attempt
	 * @return boolean showing attempt result
	 */
	public boolean loginMyCampus();
}

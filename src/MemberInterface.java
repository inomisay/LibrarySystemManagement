import java.util.ArrayList;

public interface MemberInterface {

    /**
     * Get the list of loans associated with the member.
     * @return The list of loans.
     */
    ArrayList<Loan> getLoans();

    /**
     * Authenticates the user with the provided username and password.
     *
     * @param username The username of the user trying to authenticate.
     * @param password The password of the user trying to authenticate.
     * @return {@code true} if the authentication is successful, {@code false} otherwise.
     */
     boolean authenticate(String username, String password);
     
     /**
      * Display the menu options available to the member.
     */
     
     //void memberMenu(); Console
}

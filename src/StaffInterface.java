//import java.io.IOException;

public interface StaffInterface {

    /**
     * Get the salary of the staff member.
     * @return The salary of the staff member.
     */
    double getSalary();

    /**
     * Set the salary of the staff member.
     * @param salary The salary to set.
     */
    void setSalary(double salary);
    
    /**
     * Calculate the total salary of the staff member including bonuses and deductions.
     * @param morningHours The number of hours worked in the morning shift.
     * @param noonHours The number of hours worked in the noon shift.
     * @param nightHours The number of hours worked in the night shift.
     * @param bonusAmount The amount of bonus to be added to the salary.
     * @param deductionAmount The amount of deduction to be subtracted from the salary.
     * @return The total salary of the staff member.
     */
    double calculateSalary(int morningHours, int noonHours, int nightHours, double bonusAmount, double deductionAmount);

    /**
     * Authenticates the user with the provided username and password.
     *
     * @param username The username of the staff member trying to authenticate.
     * @param password The password of the staff member trying to authenticate.
     * @return {@code true} if the authentication is successful, {@code false} otherwise.
     */
    boolean authenticate(String username, String password);
    
    /**
     * Display the menu options available to the staff member.
     * @throws IOException If an I/O error occurs.
     */
    //void staffMenu() throws IOException;
}

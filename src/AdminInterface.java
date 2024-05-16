import java.io.IOException;
import java.util.ArrayList;

public interface AdminInterface {

    /**
     * Gets the salary of the admin.
     *
     * @return The salary of the admin.
     */
    double getSalary();

    /**
     * Sets the salary of the admin.
     *
     * @param salary The new salary of the admin.
     */
    void setSalary(double salary);

    /**
     * Retrieves the address.
     * 
     * @return The address.
     */
    String getAddress();
    
    /**
     * Sets the address.
     * 
     * @param address The new address to set.
     */
    void setAddress(String address);
    
    /**
     * Gets the list of admins.
     *
     * @return The list of admins.
     */
    ArrayList<Admin> getAdmins();

    /**
     * Displays information about the admin.
     */
    void displayInfo();

    /**
     * Gets information about all admins.
     *
     * @param admins The list of admins.
     * @return Information about all admins.
     */
    String getAllAdminsInfo(ArrayList<Admin> admins);

    /**
     * Gets GUI-friendly information about all admins.
     *
     * @param admins The list of admins.
     * @return GUI-friendly information about all admins.
     */
    String getAllAdminsInfo_GUI(ArrayList<Admin> admins);

    /**
     * Gets information about all admins.
     *
     * @return Information about all admins.
     */
    String getAllAdminsInformation();

    /**
     * Searches for an admin by username.
     *
     * @param username The username of the admin to search for.
     * @return The admin if found, otherwise null.
     */
    Admin searchAdminByUsername(String username);

    /**
     * Updates information of an admin.
     *
     * @param username      The username of the admin to update.
     * @param newName       The new name of the admin.
     * @param newSurname    The new surname of the admin.
     * @param newPhoneNumber The new phone number of the admin.
     * @param newAddress    The new address of the admin.
     * @param newEmail      The new email of the admin.
     * @param newPassword   The new password of the admin.
     * @return True if the update was successful, otherwise false.
     */
    boolean updateAdminInformation(String username, String newName, String newSurname, String newPhoneNumber, String newAddress, String newEmail, String newPassword);

    /**
     * Updates information of an admin from CSV.
     *
     * @param username      The username of the admin to update.
     * @param newName       The new name of the admin.
     * @param newSurname    The new surname of the admin.
     * @param newPhoneNumber The new phone number of the admin.
     * @param newAddress    The new address of the admin.
     * @param newEmail      The new email of the admin.
     * @param newPassword   The new password of the admin.
     * @return True if the update was successful, otherwise false.
     */
    boolean updateAdminInformation_CSV(String username, String newName, String newSurname, String newPhoneNumber, String newAddress, String newEmail, String newPassword);

    /**
     * Reads admin information from CSV file.
     *
     * @throws IOException If an I/O error occurs.
     */
    void readFromCSV() throws IOException;

    /**
     * Authenticates an admin with given username and password.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return True if authentication is successful, otherwise false.
     */
    boolean authenticate(String username, String password);

    /**
     * Displays the admin menu.
     *
     * @throws IOException If an I/O error occurs.
     */
    //void adminMenu() throws IOException;
}

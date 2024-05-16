public interface PersonInterface {

    /**
     * Get the ID of the person.
     * @return The ID of the person.
     */
    int getID();

    /**
     * Set the ID of the person.
     * @param ID The ID to set.
     */
    void setID(int ID);

    /**
     * Get the name of the person.
     * @return The name of the person.
     */
    String getName();

    /**
     * Set the name of the person.
     * @param name The name to set.
     */
    void setName(String name);

    /**
     * Get the surname of the person.
     * @return The surname of the person.
     */
    String getSurname();

    /**
     * Set the surname of the person.
     * @param surname The surname to set.
     */
    void setSurname(String surname);

    /**
     * Get the phone number of the person.
     * @return The phone number of the person.
     */
    String getPhoneNumber();

    /**
     * Set the phone number of the person.
     * @param phoneNumber The phone number to set.
     */
    void setPhoneNumber(String phoneNumber);

    /**
     * Get the address of the person.
     * @return The address of the person.
     */
    String getAddress();

    /**
     * Set the address of the person.
     * @param address The address to set.
     */
    void setAddress(String address);

    /**
     * Get the email of the person.
     * @return The email of the person.
     */
    String getEmail();

    /**
     * Set the email of the person.
     * @param email The email to set.
     */
    void setEmail(String email);

    /**
     * Get the username of the person.
     * @return The username of the person.
     */
    String getUsername();

    /**
     * Set the username of the person.
     * @param username The username to set.
     */
    void setUsername(String username);

    /**
     * Get the password of the person.
     * @return The password of the person.
     */
    String getPassword();

    /**
     * Set the password of the person.
     * @param password The password to set.
     */
    void setPassword(String password);

    /**
     * Display information about the person.
     */
    void displayInfo();
}

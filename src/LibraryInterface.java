import java.io.IOException;

public interface LibraryInterface {
    
    /**
     * Reads data from CSV files and performs necessary operations.
     * 
     * @throws IOException If an I/O error occurs.
     */
    void readoperations() throws IOException;

    /**
     * Retrieves the current IDs and updates book information if necessary.
     * 
     * @throws IOException If an I/O error occurs.
     */
    void getCurrentID_Updatebook() throws IOException;
    
    /**
     * Adds a new book to the library system.
     */
    void addBook();

    /**
     * Adds a new book to the library system with provided information.
     * 
     * @param title       The title of the book.
     * @param author      The author of the book.
     * @param ISBN        The ISBN of the book.
     * @param publishYear The publishing year of the book.
     * @param img         The image reference of the book.
     * @return True if the book is added successfully, false otherwise.
     */
    boolean addBook_GUI(String title, String author, String ISBN, String publishYear, String img);

    /**
     * Removes a book from the library system.
     * 
     * @throws IOException If an I/O error occurs.
     */
    void removeBook() throws IOException;

    /**
     * Removes a book from the library system by ISBN.
     * 
     * @param ISBN The ISBN of the book to be removed.
     * @return True if the book is removed successfully, false otherwise.
     */
    boolean removeBookByISBN_GUI(String ISBN);

    /**
     * Searches for a book in the library system.
     * 
     * @throws IOException If an I/O error occurs.
     */
    void searchBook() throws IOException;

    /**
     * Searches for a book in the library system by the given search key for GUI purposes.
     * 
     * @param searchKey The title, author, or ISBN of the book to search for.
     * @return True if the book is found, false otherwise.
     */
    boolean searchBook_GUI(String searchKey);

    /**
     * Searches for a book in the library system by the given search key.
     * 
     * @param searchKey The title, author, or ISBN of the book to search for.
     * @return Details of the found book.
     */
    String viewFoundBook(String searchKey);

    /**
     * Prints details of all books in the library system.
     */
    void printBooks();

    /**
     * Prints details of all books in the library system for GUI display.
     * 
     * @return Details of all books.
     */
    String printBooks_GUI();
    
    /**
     * Adds a new staff member to the library system.
     */
    void addStaff();

    /**
     * Adds a new staff member to the library system with provided information.
     * 
     * @param name        The name of the staff member.
     * @param surname     The surname of the staff member.
     * @param phoneNumber The phone number of the staff member.
     * @param address     The address of the staff member.
     * @param email       The email of the staff member.
     * @param username    The username of the staff member.
     * @param password    The password of the staff member.
     * @param salary      The salary of the staff member.
     */
    void addStaff(String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary);

    
    /**
     * Adds a new staff member to the library system with provided information.
     * 
     * @param name        The name of the staff member.
     * @param surname     The surname of the staff member.
     * @param phoneNumber The phone number of the staff member.
     * @param address     The address of the staff member.
     * @param email       The email of the staff member.
     * @param username    The username of the staff member.
     * @param password    The password of the staff member.
     * @param salary      The salary of the staff member.
     * @return True if the staff member is added successfully, false otherwise.
     */
    boolean addStaff_GUI(String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary);

    /**
     * Removes a staff member from the library system.
     */
    void removeStaff();

    /**
     * Removes a staff member from the library system by username.
     * 
     * @param username The username of the staff member to be removed.
     * @return True if the staff member is removed successfully, false otherwise.
     */
    boolean removeStaffByUsername_GUI(String username);

    /**
     * Searches for a staff member in the library system.
     * 
     * @throws IOException If an I/O error occurs.
     */
    void searchStaff() throws IOException;

    /**
     * Searches for a staff member in the library system by username.
     * 
     * @param searchKey The username of the staff member to search for.
     * @return The staff member found, or null if not found.
     * @throws IOException If an I/O error occurs.
     */
    Staff searchStaffByUsername(String searchKey) throws IOException;

    /**
     * Searches for a staff member in the library system by username, name, or email for GUI purposes.
     * 
     * @param searchKey The username, name, or email of the staff member to search for.
     * @return True if the staff member is found, false otherwise.
     */
    boolean searchStaffByUsername_GUI(String searchKey);

    /**
     * Searches for a staff member in the library system by username.
     * 
     * @param searchKey The username of the staff member to search for.
     * @return Details of the found staff member.
     */
    String viewFoundStaff(String searchKey);

    /**
     * Prints details of all staff members in the library system.
     */
    void printStaff();

    /**
     * Prints details of all staff members in the library system for GUI display.
     * 
     * @return Details of all staff members.
     */
    String printStaff_GUI();
    
    /**
     * Updates staff member information and writes changes to CSV.
     * 
     * @param username      The username of the staff member to update.
     * @param newName       The new name of the staff member.
     * @param newSurname    The new surname of the staff member.
     * @param newPhoneNumber The new phone number of the staff member.
     * @param newAddress    The new address of the staff member.
     * @param newEmail      The new email of the staff member.
     * @param newUsername   The new username of the staff member.
     * @param newPassword   The new password of the staff member.
     * @throws IOException If an I/O error occurs.
     */
    void addToArrayStaff(String username, String newName, String newSurname, String newPhoneNumber, String newAddress, String newEmail, String newUsername, String newPassword) throws IOException;

    /**
     * Calculates and updates the salary of a staff member and writes changes to CSV.
     * 
     * @param username        The username of the staff member.
     * @param morningHours    The number of hours worked in the morning shift.
     * @param noonHours       The number of hours worked in the noon shift.
     * @param nightHours      The number of hours worked in the night shift.
     * @param bonusAmount     The bonus amount to be added to the salary.
     * @param deductionAmount The deduction amount to be subtracted from the salary.
     * @return True if the salary is updated successfully, false otherwise.
     */
    boolean StaffCalculateSalary(String username, int morningHours, int noonHours, int nightHours, double bonusAmount, double deductionAmount);

    /**
     * Adds a new member with user input.
     */
    void addMember();

    /**
     * Adds a new member with specified details.
     * 
     * @param name        The name of the member.
     * @param surname     The surname of the member.
     * @param phoneNumber The phone number of the member.
     * @param address     The address of the member.
     * @param email       The email of the member.
     * @param username    The username of the member.
     * @param password    The password of the member.
     */
    void addMember(String name, String surname, String phoneNumber, String address, String email, String username, String password);

    /**
     * Adds a new member with specified details and returns true if added successfully.
     * 
     * @param name        The name of the member.
     * @param surname     The surname of the member.
     * @param phoneNumber The phone number of the member.
     * @param email       The email of the member.
     * @param username    The username of the member.
     * @param password    The password of the member.
     * @return True if the member is added successfully, false otherwise.
     */
    boolean addMember_GUI(String name, String surname, String phoneNumber, String email, String username, String password);

    /**
     * Removes a member by username.
     */
    void removeMember();

    /**
     * Removes a member by username and returns true if removed successfully.
     * 
     * @param username The username of the member to remove.
     * @return True if the member is removed successfully, false otherwise.
     */
    boolean removeMemberByUsername_GUI(String username);

    /**
     * Searches for a member by name or surname.
     * 
     * @throws IOException If an I/O error occurs.
     */
    void searchMember() throws IOException;

    /**
     * Searches for a member by username.
     * 
     * @param searchKey The username to search for.
     * @return The member if found, otherwise null.
     * @throws IOException If an I/O error occurs.
     */
    Member searchMemberByUsername(String searchKey) throws IOException;

    /**
     * Searches for a member by name and surname.
     * 
     * @param name    The name of the member.
     * @param surname The surname of the member.
     * @return The member if found, otherwise null.
     * @throws IOException If an I/O error occurs.
     */
    Member searchMemberBy_NameandSurname(String name, String surname) throws IOException;

    /**
     * Searches for a member by username, name, or email.
     * 
     * @param searchKey The username, name, or email to search for.
     * @return True if the member is found, otherwise false.
     */
    boolean searchMemberByUsername_GUI(String searchKey);

    /**
     * Displays information of the found member.
     * 
     * @param searchKey The username of the member to find.
     * @return A string containing information of the found member.
     */
    String viewFoundMember(String searchKey);

    /**
     * Prints details of all members.
     */
    void printMembers();

    /**
     * Returns a formatted string containing details of all members.
     * 
     * @return A string containing details of all members.
     */
    String printMembers_GUI();

    /**
     * Updates member information with the specified details.
     * 
     * @param username      The username of the member to update.
     * @param newName       The new name of the member.
     * @param newSurname    The new surname of the member.
     * @param newPhoneNumber The new phone number of the member.
     * @param newEmail      The new email of the member.
     * @param newUsername   The new username of the member.
     * @param newPassword   The new password of the member.
     * @throws IOException If an I/O error occurs.
     */
    void addToArrayMember(String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newUsername, String newPassword) throws IOException;

    /**
     * View all loan records and optionally calculate fines for overdue books.
     */
    void viewLoans();

    /**
     * Generates a formatted string containing loan records for display in a GUI.
     * 
     * @return A formatted string containing loan records, or a message indicating no records found.
     */
    public String viewLoans_GUI();
    
    /**
     * View all loans associated with a specific member.
     * 
     * @param username The username of the member.
     */
    void viewMemberLoans(String username);

    /**
     * View all loans associated with a specific member and return a formatted string.
     * 
     * @param username The username of the member.
     * @return A formatted string containing the member's loans.
     */
    String viewMemberLoans_GUI(String username);

    /**
     * Delete loan records associated with a specific member and book.
     * 
     * @param username The username of the member.
     * @param ISBN     The ISBN of the book.
     * @throws IOException If an I/O error occurs.
     */
    void deleteLoans(String username, String ISBN) throws IOException;

    /**
     * Searches for loan records associated with the specified username and calculates fines.
     * 
     * @param username The username of the member whose loan records need to be searched.
     * @return A string containing details of fines associated with the member's loan records.
     */
    public String fineLoanSearch(String username);
    
    /**
     * Check if a member has any overdue books and calculate the total fine.
     * 
     * @param username The username of the member.
     * @param ISBN     The ISBN of the book.
     * @return The total fine amount for overdue books.
     */
    double checkInBook(String username, String ISBN);

    /**
     * Check out a book for a member.
     * 
     * @param username The username of the member.
     * @param ISBN     The ISBN of the book.
     * @return True if the book is checked out successfully, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    boolean CheckOutBook(String username, String ISBN) throws IOException;

    /**
     * Process a book transaction (check-in or check-out) for a member.
     * 
     * @param username The username of the member.
     * @param checkOut True if checking out a book, false if checking in.
     */
    void processBookTransaction(String username, boolean checkOut);

}

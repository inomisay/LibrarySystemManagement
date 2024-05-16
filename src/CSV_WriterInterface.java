import java.util.List;

public interface CSV_WriterInterface {
    /**
     * Writes book information to a CSV file.
     * 
     * @param filename     The name of the file to write to.
     * @param title        The title of the book.
     * @param author       The author of the book.
     * @param ISBN         The ISBN of the book.
     * @param publishYear  The publish year of the book.
     * @param img          The image of the book.
     * @param publisher    The publisher of the book.
     * @param img2         The second image of the book.
     * @param img3         The third image of the book.
     */
    void writeBook(String filename, String title, String author, String ISBN, String publishYear, String img, String publisher, String img2, String img3);

    /**
     * Writes staff information to a CSV file.
     * 
     * @param filename     The name of the file to write to.
     * @param ID           The ID of the staff member.
     * @param name         The name of the staff member.
     * @param surname      The surname of the staff member.
     * @param phoneNumber  The phone number of the staff member.
     * @param address      The address of the staff member.
     * @param email        The email of the staff member.
     * @param username     The username of the staff member.
     * @param password     The password of the staff member.
     * @param salary       The salary of the staff member.
     * @param $            Placeholder parameter.
     */
    void writeStaff(String filename, int ID, String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary, String $);

    /**
     * Writes member information to a CSV file.
     * 
     * @param filename     The name of the file to write to.
     * @param ID           The ID of the member.
     * @param name         The name of the member.
     * @param surname      The surname of the member.
     * @param phoneNumber  The phone number of the member.
     * @param email        The email of the member.
     * @param username     The username of the member.
     * @param password     The password of the member.
     */
    void writeMember(String filename, int ID, String name, String surname, String phoneNumber, String email, String username, String password);

    /**
     * Updates staff information in a CSV file.
     * 
     * @param filename      The name of the file to update.
     * @param username      The username of the staff member to update.
     * @param newName       The new name of the staff member.
     * @param newSurname    The new surname of the staff member.
     * @param newPhoneNumber  The new phone number of the staff member.
     * @param newEmail      The new email of the staff member.
     * @param newAddress    The new address of the staff member.
     * @param newUsername   The new username of the staff member.
     * @param newPassword   The new password of the staff member.
     * @param newSalary     The new salary of the staff member.
     */
    void updateStaff(String filename, String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newAddress, String newUsername, String newPassword, double newSalary);

    /**
     * Updates staff information in a CSV file.
     * 
     * @param filename      The name of the file to update.
     * @param username      The username of the staff member to update.
     * @param newName       The new name of the staff member.
     * @param newSurname    The new surname of the staff member.
     * @param newPhoneNumber  The new phone number of the staff member.
     * @param newEmail      The new email of the staff member.
     * @param newAddress    The new address of the staff member.
     * @param newUsername   The new username of the staff member.
     * @param newPassword   The new password of the staff member.
     */
    void updateStaff(String filename, String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newAddress, String newUsername, String newPassword);

    /**
     * Updates member information in a CSV file.
     * 
     * @param filename      The name of the file to update.
     * @param username      The username of the member to update.
     * @param newName       The new name of the member.
     * @param newSurname    The new surname of the member.
     * @param newPhoneNumber  The new phone number of the member.
     * @param newEmail      The new email of the member.
     * @param newUsername   The new username of the member.
     * @param newPassword   The new password of the member.
     */
    void updateMember(String filename, String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newUsername, String newPassword);

    /**
     * Writes or updates loan records in a CSV file.
     * 
     * @param filename  The name of the file to write to or update.
     * @param loans     The list of loan records to write or update.
     */
    void writeOrUpdateLoanRecords(String filename, List<Loan> loans);

    
    /**
     * Writes or updates loan records in a file.
     * 
     * @param filename The name of the file to write or update loan records.
     * @param newFine The new fine amount associated with the loan record.
     * @param name The name of the borrower.
     * @param surname The surname of the borrower.
     * @param ISBN The ISBN of the book associated with the loan record.
     */
    void writeOrUpdateLoanRecords(String filename, String newFine, String name, String surname, String ISBN);

    /**
     * Writes a new loan record to a CSV file.
     * 
     * @param filename     The name of the file to write to.
     * @param memberName   The name of the member.
     * @param memberSurname  The surname of the member.
     * @param bookName     The name of the book.
     * @param dueDate      The due date of the loan.
     * @param fine         The fine amount for the loan.
     * @param date         The date of the loan.
     */
    void writeLoanRecord(String filename, String memberName, String memberSurname, String bookName, String dueDate, double fine, java.util.Date date);

    /**
     * Removes an entry from a CSV file.
     * 
     * @param filename      The name of the CSV file.
     * @param valueToRemove The value to remove from the CSV file.
     */
    void removeEntryFromCSV(String filename, String valueToRemove);

    /**
     * Removes a loan entry from a CSV file.
     * 
     * @param filename    The name of the CSV file.
     * @param name        The name of the member.
     * @param surname     The surname of the member.
     * @param bookname    The name of the book.
     */
    void remove_CSV_loan(String filename, String username, String phoneNumber, String bookname);
}

import java.util.List;

public interface CSVInterface {
    /**
     * Adds a book to the CSV records.
     * 
     * @param book The book to add.
     */
    void addBook(Book book);
    
    /**
     * Adds a staff member to the CSV records.
     * 
     * @param staff The staff member to add.
     */
    void addStaff(Staff staff);
    
    /**
     * Adds a member to the CSV records.
     * 
     * @param member The member to add.
     */
    void addMember(Member member);
    
    /**
     * Adds a loan record to the CSV records.
     * 
     * @param loan The loan record to add.
     */
    void addLoan(Loan loan);
    
    /**
     * Deletes a loan record from the CSV records.
     * 
     * @param loan The loan record to delete.
     */
    void deleteLoan(Loan loan);

    /**
     * Retrieves the list of books.
     * 
     * @return The list of books.
     */
    List<Book> getBooks();

    /**
     * Retrieves the list of staff members.
     * 
     * @return The list of staff members.
     */
    List<Staff> getStaff();

    /**
     * Retrieves the list of members.
     * 
     * @return The list of members.
     */
    List<Member> getMembers();

    /**
     * Retrieves the list of loan records.
     * 
     * @return The list of loan records.
     */
    List<Loan> getLoanRecords();

}

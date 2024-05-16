import java.util.Date;

public interface LoanInterface {
    /**
     * Gets the book associated with the loan.
     * 
     * @return The book associated with the loan.
     */
    Book getBook();

    /**
     * Sets the book associated with the loan.
     * 
     * @param book The book to set.
     */
    void setBook(Book book);

    /**
     * Gets the member associated with the loan.
     * 
     * @return The member associated with the loan.
     */
    Member getMember();

    /**
     * Sets the member associated with the loan.
     * 
     * @param member The member to set.
     */
    void setMember(Member member);

    /**
     * Gets the due date of the loan.
     * 
     * @return The due date of the loan.
     */
    Date getDueDate();

    /**
     * Sets the due date of the loan.
     * 
     * @param dueDate The due date to set.
     */
    void setDueDate(Date dueDate);

    /**
     * Gets the fine associated with the loan.
     * 
     * @return The fine associated with the loan.
     */
    double getFine();

    /**
     * Sets the fine associated with the loan.
     * 
     * @param fine The fine to set.
     */
    void setFine(double fine);

    /**
     * Calculates the fine for the loan based on the due date and the current date.
     * 
     * @return The calculated fine.
     */
    double calculateFine();

    /**
     * Gets the formatted date of the loan for GUI printing.
     * 
     * @return The formatted date of the loan.
     */
    String getDate();
}

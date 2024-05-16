import java.io.IOException;

public interface CSV_ReaderInterface {
    /**
     * Reads book information from a CSV file and adds them to the records.
     * 
     * @param filepath The path to the book CSV file.
     * @throws IOException If an I/O error occurs.
     */
    void readBook(String filepath) throws IOException;
    
    /**
     * Reads staff information from a CSV file and adds them to the records.
     * 
     * @param filepath The path to the staff CSV file.
     * @throws IOException If an I/O error occurs.
     */
    void readStaff(String filepath) throws IOException;
    
    /**
     * Reads member information from a CSV file and adds them to the records.
     * 
     * @param filepath The path to the member CSV file.
     * @throws IOException If an I/O error occurs.
     */
    void readMember(String filepath) throws IOException;
    
    /**
     * Reads loan records from a CSV file.
     * 
     * @param fileName The name of the loan record CSV file.
     * @return An array containing all lines of the CSV file.
     * @throws IOException If an I/O error occurs.
     */
    String[] readLoanRecords(String fileName) throws IOException;
    
    /**
     * Checks for the last ID found in a CSV file for staff or member.
     * 
     * @param filePath The path to the CSV file.
     * @return The last ID found in the CSV file.
     * @throws IOException If an I/O error occurs.
     */
    int IDFound(String filePath) throws IOException;
}

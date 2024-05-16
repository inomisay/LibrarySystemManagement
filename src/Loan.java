import java.text.SimpleDateFormat;
import java.util.Date;

public class Loan implements LoanInterface {
	
	// Attributes
    private Book book;
	private Member member;
    private Date dueDate;
    private double fine;
    private String dateString;

    // Constructor
    public Loan(String name, String surname, String phoneNumber, String email, String username,String title, String author, String ISBN, int totalQuantity, Date dueDate, double fine) {
        this.member= new Member(0, name, surname, phoneNumber, email, username, "private");
    	//this.member = new Member(name, surname); 
        this.book = new Book(title, author, ISBN, "private", totalQuantity, "private");
        //this.book = new Book(bookName);
        this.dueDate = dueDate;
        this.fine = fine;
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = dateFormat.format(dueDate);
        this.dateString = dateString;
    }

    // Methods (Setters/Getters)
    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public Member getMember() {
        return member;
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    //For GUI Print
    @Override
    public String getDate() {
        return dateString;
    }
    
    @Override
    public double getFine() {
        return fine;
    }
    
    @Override
    public void setFine(double fine) {
        this.fine = fine;
    }

    // Methods
    @Override
    public double calculateFine() {

    	double fineRatePerDay = 2.0; // $2.00 fine per day for late returns

        // Get the current date
        Date currentDate = new Date();

        // Calculate the difference in days between the due date and the current date
        long diffInMillies = Math.abs(currentDate.getTime() - dueDate.getTime());
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24); // Convert milliseconds to days

        // If the book is returned before or on the due date, no fine is incurred
        if (currentDate.before(dueDate) || currentDate.equals(dueDate)) {
            return 0.0;
        } else {
            // Calculate the fine based on the difference in days
            double fine = fineRatePerDay * diffInDays;
            return fine;
        }
    }
}

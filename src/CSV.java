import java.util.*;

public abstract class CSV implements CSVInterface {
	
	// Attributes
    // Book: read/write
 	// Member=user: read/write
 	// Staff: read/write
 	// Loan: read/write
 	
 	protected static List<Book> books = new LinkedList<>();
 	protected static List<Staff> staffs = new LinkedList<>();
 	protected static List<Member> members = new LinkedList<>();
 	protected static List<Loan> loanRecords = new LinkedList<>();
    protected static String loanCSV = "loans.csv";

    // Methods
 	// Adding Book
    @Override
 	public void addBook(Book book) {
 		books.add(book);
 	}
 	
 	// Adding Staff
    @Override
 	public void addStaff(Staff staff) {
 		staffs.add(staff);
 	}
 	
 	// Adding Member
    @Override
 	public void addMember(Member member) {
 		members.add(member);
 	}
    
 	// Adding Loan
    @Override
   	public void addLoan(Loan loan) {
   		loanRecords.add(loan);  		
   	}

   	//Delete loan
    @Override
  	public void deleteLoan(Loan loan) {
  		loanRecords.remove(loan);
  	}
  	
 	// Methods (Setters/Getters)
    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public List<Staff> getStaff() {
        return staffs;
    }
    
    @Override
    public List<Member> getMembers() {
        return members;
    }
    
    @Override
    public List<Loan> getLoanRecords() {
        return loanRecords;
    }
}
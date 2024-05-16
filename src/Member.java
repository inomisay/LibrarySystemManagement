//import java.io.IOException;
//import java.util.Scanner;
import java.util.ArrayList;

public class Member extends Person implements MemberInterface {
	
	// Attributes
    //private static Scanner scanner = new Scanner(System.in);
    //private static Library library = new Library(); // Console
    //private static Admin admin = new Admin();
    private static CSV_Reader csv_read = new CSV_Reader();
    private ArrayList<Loan> loans;
    
    // Constructor
    public Member(int ID, String name, String surname, String phoneNumber, String email, String username, String password) {
        super(ID, name, surname, phoneNumber, email, username, password);
        this.loans = new ArrayList<>();
    }
    
    public Member(String name, String surname) {
        super(name, surname);
        this.loans = new ArrayList<>();
    }
    
    public Member() {
    	super(0, "", "", "", "", "", "");
    }
    
	// Methods (Setters/Getters)
    @Override
    public ArrayList<Loan> getLoans() {
        return loans;
    }
    
    // Implementing abstract method from Person
    public void displayInfo() {
        System.out.println("Member Information:");
        System.out.printf("%-25s %-25s %-20s %-50s %-20s\n", "Name", "Surname", "Phone Number", "Email", "Username");
        System.out.printf("%-25s %-25s %-20s %-50s %-20s\n", getName(), getSurname(), getPhoneNumber(), getEmail(), getUsername());
        
        if (loans.isEmpty()) {
            System.out.println("No books borrowed");
        } else {
            System.out.println("Books Borrowed:");
            for (Loan loan : loans) {
                System.out.println("- " + loan.getBook().getTitle());
            }
        }
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        for (Member member : csv_read.getMembers()) {
            if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                return true; // Authentication successful
            }
        }
        return false; // Authentication failed
    }
    
    // Menu Console
    /*
    @Override
    public void memberMenu() {
    	String username = "";
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\nMember Login");
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            loggedIn = authenticate(username, password);
            if (!loggedIn) {
                System.out.println("Invalid username or password. Please try again.");
            }
        }

        boolean exitMemberMenu = false;
        while (!exitMemberMenu) {
        	System.out.println("\nMember Menu");
            System.out.println("1. Search Book");
            System.out.println("2. View All Books");
            System.out.println("3. View Loans");
            System.out.println("4. Check in/out Book");
            System.out.println("5. Change Information");
            System.out.println("6. Get Admin Information");
            System.out.println("7. Exit to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
            case 1:
                // Search Book
                try {
                	library.searchBook();
                } catch (IOException e) {
                    System.out.println("\nIncorrect input");
                    e.printStackTrace();
                }
                break;
            case 2:
                // View All Books
                library.printBooks();                    
                break;
            case 3:
                // View Loans
                library.viewMemberLoans(username);
                break;
            case 4:
                // Check in/out Book
            	System.out.print("\nCheck out or Check in? (checkout/checkin): ");
                String transactionType = scanner.nextLine().toLowerCase();
                if (transactionType.equals("checkout")) {
                	library.processBookTransaction(username, true); // Check out
                } else if (transactionType.equals("checkin")) {
                	library.processBookTransaction(username, false); // Check in
                } else {
                    System.out.println("Invalid transaction type.");
                }
                break;
            case 5: 
            
                break;
            case 6:
            	 // View All Admins
            	String allAdminsInfo = admin.getAllAdminsInformation();
                System.out.println(allAdminsInfo);
            	break;
            case 7:
                exitMemberMenu = true;
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }
    */
}

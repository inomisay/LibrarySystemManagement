import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.time.ZoneOffset;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Library implements LibraryInterface{
	
	// Attributes
    private final String bookCSV = "Books.csv";
    private final String staffCSV = "Staff.csv";
    private final String memberCSV = "Member.csv";
    private final String loanCSV = "Loans.csv";

    private static int ID_Staff = 1;
    private static int ID_Member = 1;

    private static CSV_Reader csv_read = new CSV_Reader();
    private static CSV_Writer csv_write = new CSV_Writer();
    private Scanner scanner = new Scanner(System.in);

    // Constructor
    public Library(){
    	try {
    		getCurrentID_Updatebook();
			readoperations();
		} catch (IOException e) {
			System.out.println("Couldn't find the file.");
			e.printStackTrace();
		}
    }
    
    // Methods
    @Override
    public void readoperations() throws IOException {
    	csv_read.readBook(bookCSV);
    	csv_read.readMember(memberCSV);
    	csv_read.readStaff(staffCSV);
    	String[] allloan = csv_read.readLoanRecords(loanCSV);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); // Format of the due date
	        
    	for(int i = 0; i < allloan.length; i++) {
    	    String line = allloan[i]; // Added line to retrieve each line from the array
    	    //chek if the line is empty or not
    	    if(line == null) {
    	    	break;
    	    }
    	    String[] fields = line.split(";");
    	    
    	    String name = fields[0];
    	    String surname = fields[1];
    	    String bookName = fields[2];
    	    java.sql.Date dueDate; // Change the type to java.sql.Date
    	    try {
    	        java.util.Date utilDate = dateFormat.parse(fields[3]); // Parse due date string to java.util.Date object
    	        dueDate = new java.sql.Date(utilDate.getTime()); // Convert java.util.Date to java.sql.Date
    	    } catch (ParseException e) {
    	        System.err.println("Error parsing due date: " + e.getMessage());
    	        dueDate = null; // Assign null if parsing fails
    	    }
    	    String a = fields[4];
    	    String[] aa = a.split(",");    	  
    	    double fine = Double.parseDouble(aa[0]);
    	    //search for the person
    	    Member member = searchMemberBy_NameandSurname(name, surname);
    	    Book book = searchBook(bookName);
    	    
    	    
    	    Loan loan = new Loan(member.getName(),member.getSurname(),member.getPhoneNumber(),member.email,member.getUsername(),book.getTitle(),book.getAuthor(),book.getISBN(),book.getTotalQuantity(),dueDate,fine);
    	    csv_write.addLoan(loan);
    	}
    }
    
    // ID management
    @Override
    public void getCurrentID_Updatebook() throws IOException {
        ID_Staff =csv_read.IDFound(staffCSV) + 1;
        ID_Member =csv_read.IDFound(memberCSV) + 1;
        boolean firstLineSkipped = false;
        // read loan 
        // find the ISBN
        // then find the book with that ISBN 
        // then add the overwrite th information
        
        // find the person lists
        for (Loan loan : csv_write.getLoanRecords()) {
            if (!firstLineSkipped) {
                firstLineSkipped = true;
                continue; // Skip the first line
            }
            String iSBN = loan.getBook().getISBN();
            //search in book
            Book book = searchBook(iSBN);
            book.decreaseTotalQuantity(1);
           // book.setTotalQuantity(0);
        }        
    }
    
    //Books
    @Override
    public void addBook() {
        String title, author, ISBN, publishYear, img;

        // Getting the inputValues
        System.out.println("\nPlease fill the information :");
        System.out.print("Title : ");
        title = scanner.nextLine();
        System.out.print("Author : ");
        author = scanner.nextLine();
        System.out.print("ISBN : ");
        ISBN = scanner.nextLine();
        System.out.print("Publish Year : ");
        publishYear = scanner.nextLine();
        System.out.print("Image Reference : ");
        img = scanner.nextLine();

        csv_write.writeBook(bookCSV, title, author, ISBN, publishYear, img, publishYear, img, img);
        Book newBook = new Book(title, author, ISBN, publishYear, ID_Member, img);
        csv_write.addBook(newBook);
        System.out.printf("\nThe Book with this title is %s Successfully added.", title);
    }
    
    @Override
    public boolean addBook_GUI(String title, String author, String ISBN, String publishYear, String img) {
        if (title != null && !title.isEmpty() &&
            author != null && !author.isEmpty() &&
            ISBN != null && !ISBN.isEmpty() &&
            publishYear != null && !publishYear.isEmpty() &&
            img != null && !img.isEmpty()) {

            // Add the book to the CSV
    		csv_write.writeBook(bookCSV, title, author, ISBN, publishYear, img, publishYear, img, img);
            
            // Create a new Book object
            Book newBook = new Book(title, author, ISBN, publishYear, ID_Member, img);
            
            // Add the book to the list of books
            csv_write.addBook(newBook);
            
            return true;
        }
        return false;
    }

    @Override
    public void removeBook() throws IOException {
        System.out.print("\nEnter the ISBN of the book to remove: ");
        String ISBN = scanner.nextLine();
        Book book = null;
		List<Book> bookList = csv_read.getBooks();
		for (Book b : bookList) {
		    if (b.getISBN().equals(ISBN)) {
		        book = b;
		        break;
		    }
		}
		if (book != null) {
            // Remove the book from list of books
		    csv_write.getBooks().remove(book);
            // Remove the book from CSV 
		    csv_write.removeEntryFromCSV(bookCSV, ISBN);
		    System.out.println("\nBook removed successfully.");
		} else {
		    System.out.println("\nBook not found.");
		}
    }
    
    @Override
    public boolean removeBookByISBN_GUI(String ISBN) {
    	Book book = null;
		List<Book> bookList = csv_read.getBooks();
		for (Book b : bookList) {
		    if (b.getISBN().equals(ISBN)) {
		        book = b;
		        break;
		    }
		}
		if (book != null) {
            // Remove the book from list of books
		    csv_write.getBooks().remove(book);
            // Remove the book from CSV 
		    csv_write.removeEntryFromCSV(bookCSV, ISBN);
		    return true;
		} else {
		    return false;
		}
    }
    
    @Override
    public void searchBook() throws IOException {
        System.out.print("\nPlease enter the Title/Author/ISBN of the Book: ");
        String searchKey = scanner.nextLine();
        List<Book> books = csv_read.getBooks();
        List<Book> booksAvailable = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getTitle().equals(searchKey) || 
                book.getAuthor().equals(searchKey) || 
                book.getISBN().equals(searchKey)) {
                booksAvailable.add(book);
            }
        }        
        
        if (!booksAvailable.isEmpty()) {            
            System.out.println("\nBook Found:");
            System.out.println("-----------------------------");
            System.out.println("Title: " + booksAvailable.get(0).getTitle());
            System.out.println("Author: " + booksAvailable.get(0).getAuthor());
            System.out.println("ISBN: " + booksAvailable.get(0).getISBN());
            System.out.println("Publish Year: " + booksAvailable.get(0).getPublishYear());
        } else {            
            System.out.println("\nThe book was not found.");
        }
    }

    Book searchBook(String searchKey) throws IOException {
        List<Book> books = csv_read.getBooks();
        List<Book> booksAvailable = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equals(searchKey) ||
                book.getAuthor().equals(searchKey) ||
                book.getISBN().equals(searchKey)) {
                booksAvailable.add(book);
            }
        }

        if (!booksAvailable.isEmpty()) {
            return booksAvailable.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public boolean searchBook_GUI(String searchKey) {
    	List<Book> books = csv_read.getBooks();
        List<Book> booksAvailable = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equals(searchKey) ||
                book.getAuthor().equals(searchKey) ||
                book.getISBN().equals(searchKey)) {
                booksAvailable.add(book);
            }
        }

        if (!booksAvailable.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String viewFoundBook(String query) {
        List<Book> books = csv_read.getBooks();
        Set<String> foundTitles = new HashSet<>();
        StringBuilder foundBooksInfo = new StringBuilder();
        
        foundBooksInfo.append("\n");
        foundBooksInfo.append(" Found Book(s):\n");
        foundBooksInfo.append("--------------------------------------------------\n");
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(query) ||
                book.getAuthor().equalsIgnoreCase(query) ||
                book.getISBN().equalsIgnoreCase(query)) {
                String title = book.getTitle();
                // Check if the title has already been added
                if (!foundTitles.contains(title)) {
                    foundTitles.add(title); // Add the title to the set
                    foundBooksInfo.append(" Title: ").append(title).append("\n");
                    foundBooksInfo.append(" Author: ").append(book.getAuthor()).append("\n");
                    foundBooksInfo.append(" ISBN: ").append(book.getISBN()).append("\n");
                    foundBooksInfo.append("--------------------------------------------------\n");
                    foundBooksInfo.append("\n");
                }
            }
        }

        return foundBooksInfo.toString();
    }
    
    @Override
    public void printBooks() {
   	 List<Book> bookList = csv_read.getBooks(); 
   	 
   	 // Print details of all books in the list
   	 System.out.println("\nTHE BOOKS LIST");
        for (Book book : bookList) {
       	 System.out.println("--------------------------------------------------------------------\n");
            System.out.println("Title:\t" + book.getTitle());
            System.out.println("Author:\t" + book.getAuthor());
            System.out.println("ISBN:\t" + book.getISBN());
            System.out.println("Publish Year:\t" + book.getPublishYear());
            System.out.println("Total Quantity:\t" + book.getTotalQuantity());
            System.out.println("Image:\t" + book.getIMG());
       	 System.out.println("\n--------------------------------------------------------------------");
        }
    }
    
    @Override
    public String printBooks_GUI() {
        List<Book> bookList = csv_read.getBooks(); 

        // If bookList is empty or null, return an appropriate message
        if (bookList == null || bookList.isEmpty()) {
            return "No Book Records Have Been Found!";
        }

        Set<String> printedISBNs = new HashSet<>(); // To store unique book ISBNs
        StringBuilder bookInfo = new StringBuilder();
        bookInfo.append("\n THE BOOKS LIST\n");
        bookInfo.append("--------------------------------------------------------------------\n");

        for (Book book : bookList) {
            String ISBN = book.getISBN();
            // Check if the book ISBN has already been printed
            if (!printedISBNs.contains(ISBN)) {
                printedISBNs.add(ISBN); // Add the ISBN to the set
                bookInfo.append(" Title:\t").append(book.getTitle()).append("\n");
                bookInfo.append(" Author:").append(book.getAuthor()).append("\n");
                bookInfo.append(" ISBN:\t").append(ISBN).append("\n");
                bookInfo.append(" Publish Year:\t").append(book.getPublishYear()).append("\n");
                bookInfo.append(" Total Quantity:").append(book.getTotalQuantity()).append("\n");
                bookInfo.append(" Image:\t").append(book.getIMG()).append("\n");
                bookInfo.append("--------------------------------------------------------------------\n");
            }
        }

        return bookInfo.toString();
    }

    // Staff
    @Override
    public void addStaff() {    	
        String name, surname, phoneNumber, address, email, username, password;
        double salary;
        
        // Getting the input values 
        System.out.println("\nPlease fill the information:");
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Surname: ");
        surname = scanner.nextLine();
        System.out.print("Phone Number: ");
        phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        email = scanner.nextLine();
        System.out.print("Address: ");
        address = scanner.nextLine();
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        System.out.print("Salary: ");
        salary = scanner.nextDouble();
        
        // Add the staff to CSV 
        csv_write.writeStaff(staffCSV, ID_Staff, name, surname, phoneNumber, address, email, username, password, salary, "$");
        Staff newStaff = new Staff(ID_Staff, name, surname, phoneNumber, address, email, username, password, salary);
        // Add the staff to list of books
        csv_write.addStaff(newStaff);
        ID_Staff++;        
        System.out.printf("\nThe Staff %s Successfully added.", name);
    }
    
    @Override
    public void addStaff(String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary) {
        // Add the staff to CSV 
    	csv_write.writeStaff(staffCSV ,ID_Staff, name, surname, phoneNumber, address, email, username, password, salary, "$");
        Staff newStaff = new Staff(ID_Staff, name, surname, phoneNumber, address, email, username, password, salary);
        // Add the staff to list of books
        csv_write.addStaff(newStaff);
        ID_Staff++;        
    }

    @Override
    public boolean addStaff_GUI(String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary) {
        // Check if the staff with the given username already exists
        List<Staff> existingStaff = csv_read.getStaff();
        for (Staff staff : existingStaff) {
            if (staff.getUsername().equals(username)) {
                // Staff with the same username already exists
                return false;
            }
        }
        // Add the staff to CSV 
        csv_write.writeStaff(staffCSV, ID_Staff, name, surname, phoneNumber, address, email, username, password, salary, "$");
        Staff newStaff = new Staff(ID_Staff, name, surname, phoneNumber, address, email, username, password, salary);
        // Add the staff to list of books
        csv_write.addStaff(newStaff);
        ID_Staff++;

        return true;
    }
    
    @Override
    public void removeStaff() {
        System.out.print("\nEnter the username of the staff to remove: ");
        String username = scanner.nextLine();
        try {
            Staff staff = searchStaffByUsername(username);
            if (staff != null) {
                // Remove the staff from list of books
                csv_write.getStaff().remove(staff);
                // Remove the staff from CSV 
                csv_write.removeEntryFromCSV(staffCSV, String.valueOf(staff.getID()));
                System.out.println("\nStaff removed successfully.");
            } else {
                System.out.println("\nStaff not found.");
            }
        } catch (IOException e) {
            System.out.println("\nError occurred while removing staff: " + e.getMessage());
        }
    }
    
    @Override
    public boolean removeStaffByUsername_GUI(String username) {
    	String USERNAME = username;
        try {
            Staff staff = searchStaffByUsername(USERNAME);
            if (staff != null) {
                // Remove the staff from list of books
                csv_write.getStaff().remove(staff);
                // Remove the staff from CSV 
                csv_write.removeEntryFromCSV(staffCSV, String.valueOf(staff.getID()));
        	    return true;
            } else { // Staff not found
        	    return false;
            }
        } catch (IOException e) {
    	    return false;
        }
    }

    @Override
    public void searchStaff() throws IOException {
        System.out.print("\nPlease enter the name or surname of the staff: ");
        String searchKey = scanner.nextLine();
        
        List<Staff> staffs = csv_read.getStaff();
        Staff foundStaff = null;
        
        for (Staff staff : staffs) {
            if (staff.getName().equals(searchKey) || staff.getSurname().equals(searchKey)) {
                foundStaff = staff;
                break;
            }
        }
        
        if (foundStaff != null) {
            System.out.println("\nStaff Found:");
            System.out.println("-----------------------------");
            System.out.println("Name: " + foundStaff.getName());
            System.out.println("Surname: " + foundStaff.getSurname());
            System.out.println("Phone Number: " + foundStaff.getPhoneNumber());
            System.out.println("Email: " + foundStaff.getEmail());
            System.out.println("Address: " + foundStaff.getAddress());
        } else {
            System.out.println("\nStaff was not found.");
        }
    }
    
    @Override
    public Staff searchStaffByUsername(String searchKey) throws IOException{
    	List<Staff> staffs = csv_read.getStaff();
    	List<Staff> foundStaff = new ArrayList<>();
    
    	for(Staff staff : staffs ) {
    		 if ( staff.getUsername().equals(searchKey))
    		 {
    			 foundStaff.add(staff);
                return  staff;
             }
    	} 
    	return null;
    }
    
    @Override
    public boolean searchStaffByUsername_GUI(String searchKey) {
        List<Staff> staffList = csv_read.getStaff();

		for (Staff staff : staffList) {
		    if (staff.getUsername().equals(searchKey) ||
		        staff.getName().equals(searchKey) ||
		        staff.getEmail().equals(searchKey)) {
		        return true;
		    }
		}

		return false;
    }
        
    @Override
    public String viewFoundStaff(String searchKey) {
        List<Staff> staffList = csv_read.getStaff();
		List<Staff> staffsAvailable = new ArrayList<>();
        Staff foundedStaff = null;
        StringBuilder staffInfo = new StringBuilder();

        for (Staff Staff : staffList) {
            if (Staff.getUsername().equals(searchKey)) {
                staffsAvailable.add(Staff);
                foundedStaff = Staff;
            }
        }
        staffInfo.append("\n");
        staffInfo.append(" Found Staff(s):\n");
        if (!staffsAvailable.isEmpty()) {  
            
        	staffInfo.append("--------------------------------------------------\n");
            staffInfo.append(" ID: ").append(foundedStaff.getID()).append("\n");
            staffInfo.append(" Name: ").append(foundedStaff.getName()).append("\n");
            staffInfo.append(" Surname: ").append(foundedStaff.getSurname()).append("\n");
            staffInfo.append(" Phone Number: ").append(foundedStaff.getPhoneNumber()).append("\n");
            staffInfo.append(" Address: ").append(foundedStaff.getAddress()).append("\n");
            staffInfo.append(" Email: ").append(foundedStaff.getEmail()).append("\n");
            staffInfo.append(" Username: ").append(foundedStaff.getUsername()).append("\n");
            staffInfo.append(" Salary: ").append(foundedStaff.getSalary()).append("$\n");
            staffInfo.append("\n--------------------------------------------------\n");
         
        }else {
        staffInfo.append("Staff Not Found!");          
        }
        return staffInfo.toString();
    } 
    
    @Override
    public void printStaff() {
    	List<Staff> staffList = csv_read.getStaff(); 
    	 
         // Print details of all staffs in the list
    	System.out.println("\n THE STAFFS LIST");
         for (Staff staff : staffList) {
        	 System.out.println("--------------------------------------------------------------------\n");
             System.out.println(" ID :\t"+staff.getID());
        	 System.out.println("Name :\t" + staff.getName());
             System.out.println("Surname:\t" + staff.getSurname());
             System.out.println("Phone Number:\t" + staff.getPhoneNumber());
             System.out.println("Address:\t" + staff.getAddress());
             System.out.println("Email:\t" + staff.getEmail());
             System.out.println("Username:\t" + staff.getUsername());
             System.out.println("Password:\t"+staff.getPassword());
             System.out.println("Salary:\t"+staff.getSalary()+"$");
        	 System.out.println("\n--------------------------------------------------------------------");
         }
    }
    
    @Override
    public String printStaff_GUI() {
        List<Staff> staffList = csv_read.getStaff(); 
        Set<Integer> printedIDs = new HashSet<>(); // To store unique staff IDs
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nTHE STAFF LIST\n");
        
        for (Staff staff : staffList) {
            int ID = staff.getID();
            // Check if the staff ID has already been printed
            if (!printedIDs.contains(ID)) {
                printedIDs.add(ID); // Add the ID to the set
                stringBuilder.append("--------------------------------------------------\n");
                stringBuilder.append(" ID: ").append(ID).append("\n");
                stringBuilder.append(" Name: ").append(staff.getName()).append("\n");
                stringBuilder.append(" Surname: ").append(staff.getSurname()).append("\n");
                stringBuilder.append(" Phone Number t").append(staff.getPhoneNumber()).append("\n");
                stringBuilder.append(" Address: ").append(staff.getAddress()).append("\n");
                stringBuilder.append(" Email: ").append(staff.getEmail()).append("\n");
                stringBuilder.append(" Username: ").append(staff.getUsername()).append("\n");
                stringBuilder.append(" Salary: ").append(staff.getSalary()).append("$\n");
                stringBuilder.append("\n--------------------------------------------------\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void addToArrayStaff(String username, String newName, String newSurname, String newPhoneNumber, String newAddress, String newEmail, String newUsername, String newPassword) throws IOException {
    	Staff staff = searchStaffByUsername(username);
    	staff.setName(newUsername);
    	staff.setSurname(newSurname);
    	staff.setPhoneNumber(newPhoneNumber);
    	staff.setAddress(newAddress);
    	staff.setEmail(newEmail);
    	staff.setUsername(newUsername);
    	staff.setPassword(newPassword);
    	
    }
    
    @Override
    public boolean StaffCalculateSalary(String username,int morningHours, int noonHours, int nightHours, double bonusAmount, double deductionAmount) {
    	// Search that Staff and change the salary of him or her from List and CSV
    	boolean iswrited = false;
    	//Search:
    	Staff staff;
		try {
			staff = searchStaffByUsername(username);
			//change from List
	    	double newSalary = staff.calculateSalary(morningHours, noonHours, nightHours, bonusAmount, deductionAmount);
	    	staff.setSalary(newSalary);    	
	    	//Change in CSV
	    	csv_write.updateStaff(staffCSV, staff.username, staff.name, staff.surname, staff.phoneNumber, staff.email, staff.address, staff.username, staff.password, newSalary);
	        
	    	iswrited = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return iswrited;
    }
    
    // Member
    @Override
    public void addMember() {    	
        String name, surname, phoneNumber, email, username, password;
        
        // Getting the input values
        System.out.println("\nPlease fill the information:");
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Surname: ");
        surname = scanner.nextLine();
        System.out.print("Phone Number: ");
        phoneNumber = scanner.nextLine();    	
        System.out.print("Email: ");
        email = scanner.nextLine();
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        
        // Add the member to CSV 
        csv_write.writeMember(memberCSV, ID_Member, name, surname, phoneNumber, email, username, password);
        Member newMember = new Member(ID_Member, name, surname, phoneNumber, email, username, password);
        // Add the member to list of books
        csv_write.addMember(newMember);  
        ID_Member++;
        System.out.printf("\nThe Member %s Successfully added.", name);
    }
    
    @Override
    public void addMember(String name, String surname, String phoneNumber, String address, String email, String username, String password) {
        // Add the member to CSV 
    	csv_write.writeMember(memberCSV, ID_Member, name, surname, phoneNumber, email, username, password);
        Member newMember = new Member(ID_Member, name, surname, phoneNumber, email, username, password);
        // Add the member to list of books
        csv_write.addMember(newMember);  
        ID_Member++;
    }
    
    @Override
    public boolean addMember_GUI(String name, String surname, String phoneNumber, String email, String username, String password) {
        // Check if the member with the given username already exists
        List<Member> existingMembers = csv_read.getMembers();
        for (Member member : existingMembers) {
            if (member.getUsername().equals(username)) {
                // Member with the same username already exists
                return false;
            }
        }

        // Add the member to CSV 
        csv_write.writeMember(memberCSV, ID_Member, name, surname, phoneNumber, email, username, password);
        Member newMember = new Member(ID_Member, name, surname, phoneNumber, email, username, password);
        // Add the member to list of books
        csv_write.addMember(newMember);
        ID_Member++;

        return true;
    }
    
    @Override
    public void removeMember() {
        System.out.print("\nEnter the username of the member to remove: ");
        String username = scanner.nextLine();
        try {
            Member member = searchMemberByUsername(username);
            if (member != null) {
                // Remove the staff from list of books
                csv_write.getMembers().remove(member);
                // Remove the staff from CSV 
                csv_write.removeEntryFromCSV(memberCSV, String.valueOf(member.getID()));
                System.out.println("\nMember removed successfully.");
            } else {
                System.out.println("\nMember not found.");
            }
        } catch (IOException e) {
            System.out.println("\nError occurred while removing member: " + e.getMessage());
        }
    }

    @Override
    public boolean removeMemberByUsername_GUI(String username) {
    	String USERNAME = username;
        try {
            Member member = searchMemberByUsername(USERNAME);
            if (member != null) {
                // Remove the staff from list of books
                csv_write.getMembers().remove(member);
                // Remove the staff from CSV 
                csv_write.removeEntryFromCSV(memberCSV, String.valueOf(member.getID()));
        	    return true;
            } else { // member not found
        	    return false;
            }
        } catch (IOException e) {
    	    return false;
        }   
    }

    @Override
    public void searchMember() throws IOException {
        System.out.print("\nPlease enter the name or surname of the member: ");
        String searchKey = scanner.nextLine();
        
        List<Member> members = csv_read.getMembers();
        List<Member> membersAvailable = new ArrayList<>();
        
        for (Member member : members) {
            if (member.getName().equals(searchKey) || member.getSurname().equals(searchKey)) {
                membersAvailable.add(member);
            }
        }        
        
        if (!membersAvailable.isEmpty()) {            
            Member foundMember = membersAvailable.get(0);
            System.out.println("\nMember Found:");
            System.out.println("-----------------------------");
            System.out.println("Name: " + foundMember.getName());
            System.out.println("Surname: " + foundMember.getSurname());
            System.out.println("Phone Number: " + foundMember.getPhoneNumber());
            System.out.println("Email: " + foundMember.getEmail());
        } else {            
            System.out.println("\nmmber was not found.");
        }
    }

    @Override
    public Member searchMemberByUsername(String searchKey) throws IOException{
    	List<Member> members = csv_read.getMembers();
    	List<Member> membersAvailable = new ArrayList<>();
    	
    	for(Member member : members) {
    		if(/*member.getName().equals(searchKey) ||*/ member.getUsername().equals(searchKey)){
    			membersAvailable.add(member);
    			return member;
    		}    		
    	}
    	return null;
    }
    
    @Override
    public Member searchMemberBy_NameandSurname(String name,String Surname) throws IOException{
    	List<Member> members = csv_read.getMembers();
    	List<Member> membersAvailable = new ArrayList<>();
    	
    	for(Member member : members) {
    		if(member.getName().equals(name) && member.getSurname().equals(Surname)){
    			membersAvailable.add(member);
    			return member;
    		}    		
    	}
    	return null;
    }
    
    @Override
    public boolean searchMemberByUsername_GUI(String searchKey) {
        List<Member> memberList = csv_read.getMembers();

        for (Member member : memberList) {
            if (member.getUsername().equals(searchKey) ||
                member.getName().equals(searchKey) ||
                member.getEmail().equals(searchKey)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String viewFoundMember(String searchKey) {
        List<Member> memberList = csv_read.getMembers();
		List<Member> membersAvailable = new ArrayList<>();
        Member foundedMember = null;
        StringBuilder memberInfo = new StringBuilder();

        for (Member Member : memberList) {
            if (Member.getUsername().equals(searchKey)) {
            	membersAvailable.add(Member);
                foundedMember = Member;
            }
        }
        memberInfo.append("\n");
        memberInfo.append(" Found Member(s):\n");
        if (!membersAvailable.isEmpty()) {  
            
        	memberInfo.append("--------------------------------------------------\n");
        	memberInfo.append(" ID: ").append(foundedMember.getID()).append("\n");
        	memberInfo.append(" Name: ").append(foundedMember.getName()).append("\n");
        	memberInfo.append(" Surname: ").append(foundedMember.getSurname()).append("\n");
        	memberInfo.append(" Phone Number: ").append(foundedMember.getPhoneNumber()).append("\n");
        	memberInfo.append(" Email: ").append(foundedMember.getEmail()).append("\n");
        	memberInfo.append(" Username: ").append(foundedMember.getUsername()).append("\n");
        	memberInfo.append("\n--------------------------------------------------\n");
         
        }else {
        	memberInfo.append("Member Not Found!");          
        }
        return memberInfo.toString();
    } 

    @Override
    public void printMembers() {    	
    	List<Member> memberlist = csv_read.getMembers();
    	
    	// Print details of all members in the list
    	System.out.println("\nTHE MEMBERS LIST");
        for (Member member : memberlist) {
       	    System.out.println("--------------------------------------------------------------------\n");
            System.out.println("ID :\t"+member.getID());
       	    System.out.println("Name :\t" + member.getName());
            System.out.println("Surname:\t" + member.getSurname());
            System.out.println("Phone Number:\t" + member.getPhoneNumber());
            System.out.println("Email:\t" + member.getEmail());
            System.out.println("Username:\t" + member.getUsername());
            System.out.println("Password:\t"+member.getPassword());           
       	    System.out.println("\n--------------------------------------------------------------------");
        }
    }
    
    @Override
    public String printMembers_GUI() {
        List<Member> memberList = csv_read.getMembers();

        // If memberList is empty or null, return an appropriate message
        if (memberList == null || memberList.isEmpty()) {
            return "No Member Records Have Been Found!";
        }

        Set<Integer> printedIDs = new HashSet<>(); // To store unique member IDs
        StringBuilder memberInfo = new StringBuilder("\n THE MEMBERS LIST\n");

        for (Member member : memberList) {
            int ID = member.getID();
            // Check if the member ID has already been printed
            if (!printedIDs.contains(ID)) {
                printedIDs.add(ID); // Add the ID to the set
                memberInfo.append("--------------------------------------------------\n");
                memberInfo.append(" ID: ").append(ID).append("\n");
                memberInfo.append(" Name: ").append(member.getName()).append("\n");
                memberInfo.append(" Surname: ").append(member.getSurname()).append("\n");
                memberInfo.append(" Phone Number: ").append(member.getPhoneNumber()).append("\n");
                memberInfo.append(" Email: ").append(member.getEmail()).append("\n");
                memberInfo.append(" Username: ").append(member.getUsername()).append("\n");
            }
        }

        // Append closing separator after iterating over all members
        memberInfo.append("--------------------------------------------------\n");

        return memberInfo.toString();
    }
    
    @Override
    public void addToArrayMember(String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newUsername, String newPassword) throws IOException {
    	Member member = searchMemberByUsername(username);
    	member.setName(newUsername);
    	member.setSurname(newSurname);
    	member.setPhoneNumber(newPhoneNumber);
    	member.setEmail(newEmail);
    	member.setUsername(newUsername);
    	member.setPassword(newPassword);
    }
    
    // Loan
    @Override
    public void viewLoans() {
        // Get all the loan records
        List<Loan> loans = csv_read.getLoanRecords();

        if (loans.isEmpty()) {
            System.out.println("\nNo loan records found.");
        } else {
            System.out.println("\nLoan Records:\n");
            for (Loan loan : loans) {
                System.out.println("Member: " + loan.getMember().getName() + " " + loan.getMember().getSurname() + loan.getMember().getUsername());
                System.out.println("Book: " + loan.getBook().getTitle());
                System.out.println("Due Date: " + loan.getDueDate());
                System.out.println("Fine: $" + loan.getFine());
                System.out.println("--------------------------------------------");
            }

            // Ask if the admin wants to calculate fines
            System.out.print("Calculate fines for overdue books? (yes/no): ");
            String calculateFines = scanner.nextLine().toLowerCase();

            if (calculateFines.equals("yes")) {
                // Prompt for the name of the member
                System.out.print("Enter the username of the member to calculate fines for: ");
                String memberName = scanner.nextLine().toLowerCase();

                // Calculate fines for the specified member's overdue books
                for (Loan loan : loans) {
                    if (loan.getMember().getUsername().equalsIgnoreCase(memberName)) {
                        double fine = loan.calculateFine();
                        if (fine > 0) {
                            // Update the fine for the loan
                            loan.setFine(fine);
                        }
                    }
                }
                // Rewrite the CSV file with updated loan records
                csv_write.writeOrUpdateLoanRecords("loans.csv", loans);
                System.out.println("Fines calculated and updated in the system.");
            }
        }
        // Ask if the admin wants to add a new loan
        System.out.print("Add a new loan? (yes/no): ");
        String addLoan = scanner.nextLine().toLowerCase();

        if (addLoan.equals("yes")) {
            // Prompt for loan details
            System.out.print("Enter member's name: ");
            String memberName = scanner.nextLine();
            System.out.print("Enter member's surname: ");
            String memberSurname = scanner.nextLine();
            System.out.print("Enter book name: ");
            @SuppressWarnings("unused")
			String bookName = scanner.nextLine();
            
            // Prompt for due date
            System.out.print("Enter due date (yyyy-MM-dd): ");
            String dueDateString = scanner.nextLine();

            // Parse the input string into a Date object
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressWarnings("unused")
				Date dueDate = new Date(dateFormat.parse(dueDateString).getTime());
                
                // Check if the member exists
                boolean memberExists = false;
                for (Loan loan : loans) {
                    if (loan.getMember().getName().equalsIgnoreCase(memberName) && loan.getMember().getSurname().equalsIgnoreCase(memberSurname)) {
                        memberExists = true;
                        break;
                    }
                }
                if (!memberExists) {
                    // Member does not exist, add new loan with new member
                	// Loan newLoan = new Loan(memberName, memberSurname, bookName, dueDate, 0.0);
                	// loans.add(newLoan);
                    // Rewrite the CSV file with updated loan records
                    csv_write.writeOrUpdateLoanRecords("loans.csv", loans);
                    System.out.println("New loan added successfully.");
                } else {
                    System.out.println("Member already has existing loans. Please add a new loan for another member.");
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
        }
    }
    
    @Override
    public void viewMemberLoans(String username) {
        System.out.println("\nYour Loans:");
        List<Loan> memberLoans = new ArrayList<>();
        for (Loan loan : csv_write.getLoanRecords()) {
            if (loan.getMember().getUsername().equals(username)) {
                memberLoans.add(loan);
            }
        }
        if (memberLoans.isEmpty()) {
            System.out.println("\nYou have no active loans.");
        } else {
            for (Loan loan : memberLoans) {
                System.out.println("\nBook: " + loan.getBook().getTitle());
                System.out.println("Due Date: " + loan.getDueDate());
                System.out.println("Fine: $" + loan.getFine());
                System.out.println("--------------------------------------------");
            }
        }
    }
      
    @Override
    public String viewLoans_GUI() {
    	// Get all the loan records
        List<Loan> loans = csv_read.getLoanRecords();
        StringBuilder loanRecords = new StringBuilder();
        
        if (loans.isEmpty()) {
        	loanRecords.append("\n Loan Records:\n");
        	loanRecords.append("---------------------------------------------------------\n");
            return "\n No loan records found.";
        } else {
        	loanRecords.append("\n Loan Records:\n");
        	loanRecords.append("---------------------------------------------------------\n");
            for (Loan loan : loans) {
            	loanRecords.append(" Member Info: " + loan.getMember().getUsername() + "\n PhoneNumber: +90 " + loan.getMember().getPhoneNumber());
            	loanRecords.append("\n Book: " + loan.getBook().getTitle());
            	loanRecords.append("\n Due Date: " + loan.getDueDate());
            	loanRecords.append("\n Fine: $" + loan.getFine());
            	loanRecords.append("\n---------------------------------------------------------\n");
            }
            return loanRecords.toString();
        }
    }
    
    @Override
    public String viewMemberLoans_GUI(String username) {
        List<Loan> memberLoans = new ArrayList<>();
        for (Loan loan : csv_write.getLoanRecords()) {
            if (loan.getMember().getUsername().equals(username)) {
                memberLoans.add(loan);
            }
        }
        
        if (memberLoans.isEmpty()) {
            return "\n Your Loans: \n---------------------------------------------------------\n You have no active loans.";
        } else {
            StringBuilder loanRecords = new StringBuilder();
            loanRecords.append("\n Your Loans:\n");
        	loanRecords.append("---------------------------------------------------------\n");
            for (Loan loan : memberLoans) {
                loanRecords.append(" Book: ").append(loan.getBook().getTitle()).append("\n");
                loanRecords.append(" ISBN: ").append(loan.getBook().getISBN()).append("\n");
                loanRecords.append(" Due Date: ").append(loan.getDate()).append("\n");
                loanRecords.append(" Fine: $").append(loan.getFine()).append("\n");
            	loanRecords.append("---------------------------------------------------------\n");
            }
            return loanRecords.toString();
        }
    }
    
    @Override
    public void deleteLoans(String username , String ISBN) throws IOException {
    	// delete frome csv 
    	// search from the loans and find that person
    	// find the persons isbn from the loan
    	// add 1 to the book number
    	
    	// find the person lists
    	List<Loan> memberLoans = new ArrayList<>();
        for (Loan loan : csv_write.getLoanRecords()) {
            if (loan.getMember().getUsername().equals(username)) {
                memberLoans.add(loan);
            }
        }
        String name="";
        String surname ="";
        String bookname="";
        //find the searched ISBN  and delete from csv       
        for (Loan loan : memberLoans) {
       	 if (loan.getBook().getISBN().equals(ISBN)) {        	     
       		 // Get the line associated with this loan		
       		 name = loan.getMember().getName();
       		 surname = loan.getMember().getSurname();
       		 bookname = loan.getBook().getTitle();
       		 csv_write.remove_CSV_loan("loans.csv", name, surname, bookname);  
       		 // Remove the loan from the list
             csv_write.deleteLoan(loan);
       		 break;
       		 }
       	 }         
        //now we should add to the book by 1
        //search book , then add to csv and array
        //add ti array
        Book book = searchBook(ISBN);
        //int quantity = book.getTotalQuantity()+1;
   		//book.setTotalQuantity(quantity);
        book.increaseTotalQuantity(1); 
    }
    
    // Find fine of loan
    @Override
   	public String fineLoanSearch(String username) {
   	    // find the person lists 	   
   		List<Loan> memberLoans = new ArrayList<>();
   		
   	    for (Loan loan : csv_write.getLoanRecords()) {
   	        if (loan.getMember().getUsername().equals(username)) {
   	            memberLoans.add(loan);   	            
   	        }
   	    }
        @SuppressWarnings("unused")
		double fine = 0.0;
        StringBuilder loanRecords = new StringBuilder();
        loanRecords.append("\n Your Fines :");
        loanRecords.append("\n-----------------------------------------------------------\n");
        Loan findedLoan= null;
        for (Loan loan : memberLoans){  
        	if(loan.getMember().getUsername().equals(username)) {
        		findedLoan = loan;
        	}
         	fine = loan.calculateFine();
         	loanRecords.append(" Fine : ").append(loan.calculateFine()).append("\n");       	  
        }          
          
        // update the Loan in CSV and List
        try {
			Member findedMember = searchMemberByUsername(username);
			String fineString = String.valueOf(fine);
			// Ehtemale read error hast!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(findedLoan!= null) {
				csv_write.writeOrUpdateLoanRecords(loanCSV,fineString , findedMember.getName(), findedMember.getSurname(),findedLoan.getBook().getTitle());						
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
      	
  		return loanRecords.toString();
   	}
   	
    @Override
    public double checkInBook(String username,String ISBN) {
    	// find the person lists
    	List<Loan> memberLoans = new ArrayList<>();
        for (Loan loan : csv_write.getLoanRecords()) {
            if (loan.getMember().getUsername().equals(username)) {
                memberLoans.add(loan);
            }
        }
        //find the searched ISBN 
        double fine = 0.0;
        for (Loan loan : memberLoans) {
       	 if (loan.getBook().getISBN().equals(ISBN)) {
       	    fine = loan.calculateFine();        	    
         }
       }
		return fine;
	}
    
    @Override
    public boolean CheckOutBook(String username,String ISBN) throws IOException {
 		//Book operations
 		/* search for the book by ISBN
 		 * Decrease quantity
 		 */
 		
 		//Loan operations
 		/* Add Loan the Member
 		 * Add CSV loan
 		 */
 		
 		//Book & loan operations:
 		
 		boolean ischeckedOut = false;
 		Book book = searchBook(ISBN);
 		Member member=searchMemberByUsername(username);
 		if (book.getTotalQuantity() > 0) {
            // Decrease book quantity by 1
            book.decreaseTotalQuantity(1);
            // Write member info and book details to loans.csv
            if (csv_write != null) {
                // Get current date
                LocalDate currentDate = LocalDate.now();
                // Calculate due date (7 days from the current date)
                LocalDate dueDate = currentDate.plusDays(7);
                // Convert due date to string
                String dueDateString = dueDate.toString();
                //change local date to date
                Date date = Date.from(dueDate.atStartOfDay().toInstant(ZoneOffset.UTC));
                // Write loan record to CSV and List
                csv_write.writeLoanRecord("loans.csv", member.getName(),member.getSurname() , book.getTitle(), dueDateString, 0.0,date);
                Loan newLoan = new Loan(member.getName(),member.getSurname(),member.getPhoneNumber(),member.email,member.getUsername(),book.getTitle(),book.getAuthor(),book.getISBN(),book.getTotalQuantity(),date,0.0);
        	    csv_write.addLoan(newLoan);
            }
            ischeckedOut=true;
        } 
 		return ischeckedOut;
 	}
    
    @Override
    public void processBookTransaction(String username, boolean checkOut) {
        System.out.print("Enter the ISBN of the book: ");
        String bookISBN = scanner.nextLine().trim(); // Add trim() to remove leading/trailing whitespace
        
        try {
            Book book = searchBook(bookISBN);
            if (book != null) {
                if (checkOut) {
                    // Checking out a book
                    if (book.getTotalQuantity() > 0) {
                        // Decrease book quantity by 1
                        book.decreaseTotalQuantity(1);
                        // Write member info and book details to loans.csv
                        if (csv_write != null) {
                            // Get current date
                            LocalDate currentDate = LocalDate.now();
                            // Calculate due date (7 days from the current date)
                            LocalDate dueDate = currentDate.plusDays(7);
                            // Convert due date to string
                            String dueDateString = dueDate.toString();
                            // Write loan record to CSV
                            //change local date to date
                            Date date = Date.from(dueDate.atStartOfDay().toInstant(ZoneOffset.UTC));
                            
                            csv_write.writeLoanRecord("loans.csv", username, "", book.getTitle(), dueDateString, 0.0, date);
                        }
                        System.out.println("Book checked out successfully.");
                    } else {
                        System.out.println("Book not available.");
                    }
                } else {
                    // Checking in a book
                    // Increase book quantity by 1
                    book.increaseTotalQuantity(1);
                    System.out.println("Book checked in successfully.");
                    // Find and remove the loan record for the checked-in book
                    if (csv_write != null) {
                        // Iterate over loan records
                        for (Loan loan : csv_read.getLoanRecords()) {
                            // Check if the loan record matches the book and member
                            if (loan.getBook().equals(book) && loan.getMember().getUsername().equals(username)) {
                                // Remove the loan record
                                csv_write.removeEntryFromCSV("loans.csv", loan.getMember().getName());
                                System.out.println("Loan record removed.");
                                // Calculate overdue fines if the due date has passed
                                LocalDate currentDate = LocalDate.now();
                                LocalDate dueDate = toLocalDate(loan.getDueDate());
                                if (dueDate != null && currentDate.isAfter(dueDate)) {
                                    double fine = loan.calculateFine();
                                    if (fine > 0) {
                                        loan.setFine(fine);
                                        System.out.println("Overdue fine calculated and updated.");
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Book not found.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper method to convert due date to LocalDate
    private LocalDate toLocalDate(Object dueDateObj) {
        if (dueDateObj == null) {
            return null;
        }
        if (dueDateObj instanceof LocalDate) {
            return (LocalDate) dueDateObj;
        }
        if (dueDateObj instanceof java.sql.Date) {
            return ((java.sql.Date) dueDateObj).toLocalDate();
        }
        return null; // Handle unsupported type
    }
  
}

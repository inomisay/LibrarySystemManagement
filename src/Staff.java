//import java.io.IOException;
//import java.util.Scanner;

public class Staff extends Person implements StaffInterface {
	
	// Attributes
    //private static Scanner scanner = new Scanner(System.in);
    //private static Library library = new Library();
    //private static Admin admin = new Admin();
    private static CSV_Reader csv_read = new CSV_Reader();
    private double salary;
    
    // Constructor
    public Staff(int ID, String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary) {
        super(ID, name, surname, phoneNumber, address, email, username, password);
        this.salary = salary;
    }
    
    public Staff(int ID, String name, String surname, String phoneNumber, String email, String username,
			String password, double salary) {
    	super(ID, name, surname, phoneNumber, email, username, password);
        this.salary = salary;
	}
    
    public Staff() {
    	super(0, "", "", "", "", "", "");
    }

	// Methods (Setters/Getters)
    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    // Methods
    @Override
    public double calculateSalary(int morningHours, int noonHours, int nightHours, double bonusAmount, double deductionAmount) {
        int workDaysPerMonth = 20; // Assuming 20 working days in a month with holidays excluded

        // Calculate total work hours for each shift
        int totalMorningHours = morningHours * workDaysPerMonth;
        int totalNoonHours = noonHours * workDaysPerMonth;
        int totalNightHours = nightHours * workDaysPerMonth;

        // Calculate total work hours for all shifts combined
        int totalWorkHours = totalMorningHours + totalNoonHours + totalNightHours;

        // Calculate base salary per hour
        double baseHourlyRate = salary / totalWorkHours;

        // Calculate regular salary for each shift
        double morningSalary = morningHours * baseHourlyRate;
        double noonSalary = noonHours * baseHourlyRate;
        double nightSalary = nightHours * baseHourlyRate;

        // Calculate overtime pay rate
        double overtimeRate = baseHourlyRate * 10; // base rate for overtime 

        // Calculate overtime hours for each shift (no more than 12 hours per day)
        int morningOvertimeHours = Math.max(0, morningHours - 12 * workDaysPerMonth);
        int noonOvertimeHours = Math.max(0, noonHours - 12 * workDaysPerMonth);
        int nightOvertimeHours = Math.max(0, nightHours - 12 * workDaysPerMonth);

        // Calculate overtime salary for each shift
        double morningOvertimeSalary = morningOvertimeHours * overtimeRate;
        double noonOvertimeSalary = noonOvertimeHours * overtimeRate;
        double nightOvertimeSalary = nightOvertimeHours * overtimeRate;

        // Calculate total salary including regular and overtime pay for all shifts
        double totalSalary = morningSalary + noonSalary + nightSalary + morningOvertimeSalary + noonOvertimeSalary + nightOvertimeSalary;

        // Add bonus amount
        totalSalary += bonusAmount;

        // Subtract deduction amount
        totalSalary -= deductionAmount;

        return totalSalary;
    }

    // Implementing abstract method from Person
    public void displayInfo() {
        System.out.println("Staff Information:");
        System.out.printf("%-25s %-25s %-20s %-50s %-20s %-10.2f\n", "Name", "Surname", "Phone Number", "Address", "Email", "Username", "Salary");
        System.out.printf("%-25s %-25s %-20s %-50s %-20s %-10.2f\n", getName(), getSurname(), getPhoneNumber(), getAddress(),getEmail(), getUsername(), getSalary());
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        for (Staff staff : csv_read.getStaff()) {
            if (staff.getUsername().equals(username) && staff.getPassword().equals(password)) {
                return true; // Authentication successful
            }
        }
        return false; // Authentication failed
    }
    
    /*
    // Menu Console
    @Override
    public void staffMenu() throws IOException {
	    boolean loggedIn = false;
	    while (!loggedIn) {
	        System.out.println("\nStaff Login");
	        System.out.print("Enter username: ");
	        String username = scanner.nextLine();
	        System.out.print("Enter password: ");
	        String password = scanner.nextLine();
	        loggedIn = authenticate(username, password);
	        if (!loggedIn) {
	            System.out.println("Invalid username or password. Please try again.");
	        }
	    }

	    boolean exitStaffMenu = false;
	    while (!exitStaffMenu) {
	        System.out.println("\nStaff Menu");
	        System.out.println("1. Add Member");
	        System.out.println("2. Remove Member");
	        System.out.println("3. View All Members");
	        System.out.println("4. Search Member");
	        System.out.println("5. Add Book");
	        System.out.println("6. Remove Book");
	        System.out.println("7. View All Books");
	        System.out.println("8. Search Book");
	        System.out.println("9. View Loans");
	        System.out.println("10.Change Information");
	        System.out.println("11.Get Admin Information");
	        System.out.println("12. Exit to Main Menu");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine(); // Consume newline character

	        switch (choice) {
            case 1:
                // Add Member
            	library.addMember();
            	break;
            case 2:
                // Remove Member
            	library.removeMember();
            	break;
            case 3:
                // View All Members
            	library.printMembers();
            	break;
            case 4:
                // Search Member
            	try {
            		library.searchMember();
			    } catch (IOException e) {
				    System.out.println("\nIncorrect input");
				    e.printStackTrace();
			    }
            	break;
            case 5:
                // Add Book
            	library.addBook();
            	break;
            case 6:
                // Remove Book
            	library.removeBook();
            	break;
            case 7:
                // View All Books
            	library.printBooks();
            	break;
            case 8:
                // Search Book
            	try {
            		library.searchBook();
				    } catch (IOException e) {
				    	System.out.println("\nIncorrect input");
					   e.printStackTrace();
				    }
            	break;
            case 9:
                // View Loans
            	library.viewLoans();
            	break;
            case 10:
                break;
            case 11:
            	 // View All Admins
            	String allAdminsInfo = admin.getAllAdminsInformation();
                System.out.println(allAdminsInfo);
            	break;
            case 12:
                exitStaffMenu = true;
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.");
	        }
	    }
    }
    */
}


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Admin extends Staff implements AdminInterface{
    
	// Attributes
    private static ArrayList<Admin> admins = new ArrayList<>();
    //private static Scanner scanner = new Scanner(System.in);
    //private static Library library = new Library();
    private double salary;

    // Constructor
    public Admin(int ID, String name, String surname, String phoneNumber,String address, String email, String username, String password, double salary) {
        super(ID, name, surname, phoneNumber,address, email, username, password, salary);
        this.salary = salary;
    }
    
    public Admin(){
    	super(0, "", "", "", "", "", "", 0);
        admins = new ArrayList<>();
        // read the Admin from csv
        try {
        	readFromCSV();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    
    @Override
    public String getAddress() {
        return address;
    }
    
    @Override
    public void setAddress(String Address) {
    	this.address = Address;
    }
    
    // Getter method for the admins ArrayList
    @Override
    public ArrayList<Admin> getAdmins() {
        return admins;
    }
    
	// Methods
    // Implementing abstract method from Person
    public void displayInfo() {
        System.out.println("Admin Information:");
        System.out.printf("%-25s %-25s %-20s %-50s %-20s %-20s %-10.2f\n", "Name", "Surname", "Phone Number", "Address", "Email", "Username", "Salary");
        System.out.printf("%-25s %-25s %-20s %-50s %-20s %-20s %-10.2f\n", getName(), getSurname(), getPhoneNumber(), getAddress(), getEmail(), getUsername(), getSalary());
    }
    
    // Print admin information
    String getAdminInfo() {
        return String.format("| ID : %-5d | Name : %-15s | Surname : %-20s | Phone : %-15s | Email : %-40s |",
        						ID, name, surname, phoneNumber, email);
    }
    
    String getAdminInfo_GUI() {
        return String.format(" ID: %d\n %s %s\n Phone Number: %s\n Email: %s\n", 
        						ID, name, surname, phoneNumber, email);
    }
    
    @Override
    public String getAllAdminsInfo(ArrayList<Admin> admins) {
        StringBuilder info = new StringBuilder();
        info.append("ADMIN INFORMATION\n");
        info.append("------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (Admin admin : admins) {
            info.append(admin.getAdminInfo()).append("\n");
        }
        info.append("------------------------------------------------------------------------------------------------------------------------------------------------------");
        return info.toString();
    }
    
    @Override
    public String getAllAdminsInfo_GUI(ArrayList<Admin> admins) {
        StringBuilder info = new StringBuilder();
        info.append("\n");
        info.append("                 ADMIN INFORMATION\n");
        info.append("----------------------------------------------------\n");
        for (Admin admin : admins) {
            info.append(admin.getAdminInfo_GUI());
            info.append("----------------------------------------------------\n");
        }
        return info.toString();
    }
    
    @Override
    public String getAllAdminsInformation() {
        StringBuilder info = new StringBuilder();
        info.append("ADMIN INFORMATION\n");
        info.append("------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (Admin admin : admins) {
            info.append(admin.getAdminInfo()).append("\n");
        }
        info.append("------------------------------------------------------------------------------------------------------------------------------------------------------");
        return info.toString();
    }
    
    // Search Admin
    @Override
    public Admin searchAdminByUsername(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }
        return null; // If admin with the given username is not found
    }
    
    // Update info from the  arraylist 
    @Override
    public boolean updateAdminInformation(String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newAddress, String newPassword) {
    	    
    	for (Admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                // Update the admin's information
                admin.setName(newName);
                admin.setSurname(newSurname);
                admin.setPhoneNumber(newPhoneNumber);
                admin.setEmail(newEmail);
                admin.setAddress(newAddress);
                admin.setPassword(newPassword);                
                return true; // Indicate successful update
            }
        }
        return false; // If admin with the given username is not found
    }
    
    @Override
    public boolean updateAdminInformation_CSV(String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newAddress, String newPassword) {
    	boolean isrewrited = false;
    	//Update from CSV
    	try {
             // Read all lines from the file and update the line containing the staff information
            // List<String> lines = Files.readAllLines(Paths.get("Admin.csv"));
    		// Update from CSV
    	    List<String> lines = new ArrayList<>();
    	    try (BufferedReader br = new BufferedReader(new FileReader("Admin.csv"))) {
    	        String line;
    	        while ((line = br.readLine()) != null) {
    	            lines.add(line);
    	        }
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	        return false; // Unable to read the file
    	    }

             for (int i = 0; i < lines.size(); i++) {
                 String line = lines.get(i);
                 String[] parts = line.split(";");
                 if (parts[6].equals(username)) {
                     // Update the staff information
                     parts[1] = newName;
                     parts[2] = newSurname;
                     parts[3] = newPhoneNumber;
                     parts[4] = newEmail;
                     parts[5] = newAddress;
                     //parts[6] = newUsername;
                     parts[7] = newPassword;
                     // parts[8] = String.valueOf(newSalary);                  
                     lines.set(i, String.join(";", parts));
                     isrewrited = true;
                     break;
                 }
             }
             // Write the updated lines back to the file
             Files.write(Paths.get("Admin.csv"), lines);
         } catch (IOException e) {        	 
             e.printStackTrace();
         }
    	return isrewrited; //if the isrewrited is false--> we couldn't rewrite from csv
    }
    
    @Override
    public void readFromCSV() throws IOException {
    	BufferedReader excelAdmin = new BufferedReader(new FileReader("Admin.csv"));
		
		String line;
		excelAdmin.readLine();
		
		while((line = excelAdmin.readLine()) != null) {
			String[] LS = line.split(";");	//LS = line split	
			String id = LS[0];				
			String name = LS[1];
			String surname = LS[2];
			String phonNumber = LS[3];
			String email = LS[4];
			String address = LS[5];
			String username = LS[6]; 
			String password = LS[7]; 	
			String salary = LS[8];
			double Salary = Double.valueOf(salary); 
			int ID = Integer.valueOf(id);

			Admin admin = new Admin(ID, name, surname, phonNumber,address, email, username, password, Salary);
			admins.add(admin);									
		}
		excelAdmin.close();
    }
    
    // Authentication
    @Override
    public boolean authenticate(String username, String password) {
        for (Admin admin : admins) {
            if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                return true; // Authentication successful
            }
        }
        return false; // Authentication failed
    }

    /*
    // Menu
    @Override
    public void adminMenu() throws IOException {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\nAdministrator Login");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            loggedIn = authenticate(username, password);
            if (!loggedIn) {
                System.out.println("Invalid username or password. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
        }

        boolean exitAdminMenu = false;
        while (!exitAdminMenu) {
	        System.out.println("\nAdministrator Menu");
	        System.out.println("1. Add Staff");
	        System.out.println("2. Remove Staff");
	        System.out.println("3. View All Staff");
	        System.out.println("4. Search Staff");
	        System.out.println("5. Add Member");
	        System.out.println("6. Remove Member");
	        System.out.println("7. View All Members");
	        System.out.println("8. Search Member");
	        System.out.println("9. Add Book");
	        System.out.println("10. Remove Book");
	        System.out.println("11. View All Books");
	        System.out.println("12. Search Book");
	        System.out.println("13. View Loans");
	        System.out.println("14. Change personal Information");
	        System.out.println("15. Exit to Main Menu");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine(); // Consume newline character
	        switch (choice) {
            case 1:
                // Add Staff
            	Long t1 = System.currentTimeMillis();
            	library.addStaff();	
            	Long t2 = System.currentTimeMillis()-t1;
            	System.out.println("\nAdd staff time = "+ t2);
                break;
            case 2:
                // Remove Staff
            	Long t3 = System.currentTimeMillis();
            	library.removeStaff();
            	Long t4 = System.currentTimeMillis()-t3;
            	System.out.println("\nRemouve staff time = "+t4);
            	break;
            case 3:
                // View All Staff
            	Long t5 = System.currentTimeMillis();
                library.printStaff();
                Long t6 = System.currentTimeMillis()-t5;
                System.out.println("\nprint staff time = "+ t6);
                break;
            case 4:
                // Search Staff
            	Long t7 = System.currentTimeMillis();
            	try {
            		library.searchStaff();
			    } catch (IOException e) {					
				    e.printStackTrace();
			    }
            	Long t8 = System.currentTimeMillis()-t7;
            	System.out.println("\nsearch staff time = "+ t8);
            	break;
            case 5:
                // Add Member
            	Long t9 = System.currentTimeMillis();
            	library.addMember();
            	Long t10 = System.currentTimeMillis()-t9;
            	System.out.println("\nAdd member time = "+ t10);
            	break;
            case 6:
                // Remove Member
            	Long t11 = System.currentTimeMillis();
            	library.removeMember();
            	Long t12 = System.currentTimeMillis()-t11;
            	System.out.println("\nremove member time = "+ t12);
            	break;
            case 7:
                // View All Members
            	Long t13 = System.currentTimeMillis();
            	library.printMembers();
            	Long t14 = System.currentTimeMillis()-t13;
            	System.out.println("\nprint member time = "+ t14);
            	break;
            case 8:
                // Search Member
            	Long t15 = System.currentTimeMillis();
            	try {
            		library.searchMember();
			    } catch (IOException e) {
				    System.out.println("\nIncorrect input");
				    e.printStackTrace();
			    }
            	Long t16 = System.currentTimeMillis()-t15;
            	System.out.println("\nsearch member time = "+ t16);
            	break;
            case 9:
                // Add Book
            	Long t17 = System.currentTimeMillis();
            	library.addBook();
            	Long t18 = System.currentTimeMillis()-t17;
            	System.out.println("\nAdd book time = "+ t18);
            	break;
            case 10:
                // Remove Book
            	Long t19 = System.currentTimeMillis();
            	library.removeBook();
            	Long t20 = System.currentTimeMillis()-t19;
            	System.out.println("\nremove book time = "+ t20);
            	break;
            case 11:
                // View All Books
            	Long t21 = System.currentTimeMillis();
            	library.printBooks();
            	Long t22 = System.currentTimeMillis()-t21;
            	System.out.println("\nprint book time = "+ t22);
            	break;
            case 12:
                // Search Book  
            	Long t23 = System.currentTimeMillis();
			    try {
			    	library.searchBook();
			    } catch (IOException e) {
			    	System.out.println("\nIncorrect input");
				   e.printStackTrace();
			    }
			    Long t24 = System.currentTimeMillis()-t23;
			    System.out.println("\nsearch book time = "+ t24);

            	break;
            case 13:
                // View Loans
            	Long t25 = System.currentTimeMillis();
            	library.viewLoans();
            	Long t26 = System.currentTimeMillis()-t25;
            	System.out.println("\nview loans time = "+ t26);
            	break;
            case 14:
            	Long t29 = System.currentTimeMillis();
    			Long t30 = System.currentTimeMillis()-t29;
    			System.out.println("\nchange information2 time = "+ t30);
            	break;
            case 15:
                exitAdminMenu = true;
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.");
	        }
        }
    }
*/
}

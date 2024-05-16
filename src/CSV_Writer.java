import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSV_Writer extends CSV implements CSV_WriterInterface {
	
	// Attributes
    private static CSV_Reader csv_read = new CSV_Reader();

    // Write Book CSV
    @Override
    public void writeBook(String filename,String title, String author, String ISBN, String publishYear, String img , String publisher, String img2, String img3) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            String line;
            String delimiter = ";";

            // Read all lines and check if the file is properly ended
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            // Check if the last line ends properly
            String[] rows = content.toString().split(System.lineSeparator());
            String lastRow = rows[rows.length - 1];
            if (!lastRow.endsWith(delimiter)) {
                // If not properly ended, add new parameters (name and surname) as a new row with quotes
            	
                String[] newParams  = {title,author,ISBN,publishYear,img,publisher,img2,img3 };

                StringBuilder newRow = new StringBuilder();
                for (int i = 0; i < newParams.length; i++) {
                    newRow.append(newParams[i]);
                    if (i < newParams.length - 1) {
                        newRow.append(delimiter);
                    }
                }
                newRow.append(System.lineSeparator());

                // Append the new row to the file
                writer.write(newRow.toString());
                System.out.println("File was not properly ended. Added new parameters: John, Doe, tehran");
            } else {
                System.out.println("File is properly ended.");
            }

            // Close the reader and writer
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Write Staff CSV
    @Override
    public void writeStaff(String filename,int ID, String name, String surname, String phoneNumber, String address, String email, String username, String password, double salary, String $) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            String line;
            String delimiter = ";";

            // Read all lines and check if the file is properly ended
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            // Check if the last line ends properly
            String[] rows = content.toString().split(System.lineSeparator());
            String lastRow = rows[rows.length - 1];
            
            
            if (content.length() == 0 || !lastRow.endsWith(delimiter)) {
            	String salaryTemp = Double.toString(salary); 
            	String id = Integer.toString(ID);
                // If not properly ended, add new parameters (name and surname) as a new row with quotes
            	//                      0     1       2       3         4       5        6        7      8    
                String[] newParams  = {id, name, surname , phoneNumber, address, email, username, password, salaryTemp};

                StringBuilder newRow = new StringBuilder();
                for (int i = 0; i < newParams.length; i++) {
                    newRow.append(newParams[i]);
                    if (i < newParams.length - 1) {
                        newRow.append(delimiter);
                    }
                }
                newRow.append(System.lineSeparator());

                // Append the new row to the file
                writer.write(newRow.toString());
               
            } else {
            	writer.newLine();
               // System.out.println("File is properly ended.");
            }

            // Close the reader and writer
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Write Member CSV
    @Override
    public void writeMember(String filename,int ID, String name, String surname, String phoneNumber, String email, String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            String line;
            String delimiter = ";";

            // Read all lines and check if the file is properly ended
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            // Check if the last line ends properly
            String[] rows = content.toString().split(System.lineSeparator());
            String lastRow = rows[rows.length - 1];
            
            if (content.length() == 0 || !lastRow.endsWith(delimiter)) {            	
            	String id = Integer.toString(ID);
                // If not properly ended, add new parameters (name and surname) as a new row with quotes
            	//                      0     1       2       3         4       5        6        7      8    
                String[] newParams  = {id,name, surname ,phoneNumber,email,username,password };

                StringBuilder newRow = new StringBuilder();
                for (int i = 0; i < newParams.length; i++) {
                    newRow.append(newParams[i]);
                    if (i < newParams.length - 1) {
                        newRow.append(delimiter);
                    }
                }
                newRow.append(System.lineSeparator());

                // Append the new row to the file
                writer.write(newRow.toString());
            	writer.newLine();
            } else {
            	writer.newLine();
               // System.out.println("File is properly ended.");
            }

            // Close the reader and writer
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Update Staff
    @Override
    public void updateStaff(String filename, String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newAddress, String newUsername, String newPassword, double newSalary) {
        try {
            // Read all lines from the file and update the line containing the staff information
            // List<String> lines = Files.readAllLines(Paths.get(filename));
        	
        	 //List<String> lines = new ArrayList<>();
        	List<String> lines = new ArrayList<>();
     	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
     	        String line;
     	        while ((line = br.readLine()) != null) {
     	            lines.add(line);
     	        }
     	    } catch (IOException e) {
     	        e.printStackTrace();     	       
     	    }
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(";");
                if (parts[6].equals(username)) {
                    // Update the staff information
                    parts[1] = newName;
                    parts[2] = newSurname;
                    parts[3] = newPhoneNumber;
                    parts[4] = newAddress;
                    parts[5] = newEmail;
                    parts[6] = newUsername;
                    parts[7] = newPassword;
                    parts[8] = String.valueOf(newSalary);                  
                    lines.set(i, String.join(";", parts));
                    break;
                }
            }
            // Write the updated lines back to the file
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // update from List
    	List<Staff> stafflist = getStaff();    	
        for (Staff staff : stafflist) {
            if (staff.getUsername().equals(username)) {
                // Update the admin information
            	staff.setName(newName);
            	staff.setSurname(newSurname);
            	staff.setPhoneNumber(newPhoneNumber);
            	staff.setEmail(newEmail);
            	staff.setAddress(newAddress);
            	staff.setUsername(newUsername);
            	staff.setPassword(newPassword);
            	staff.setSalary(newSalary);
                break;
            }
        }
    }
    
    @Override
    public void updateStaff(String filename, String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newAddress, String newUsername, String newPassword) {
        try {
            // Read all lines from the file and update the line containing the staff information
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(";");
                if (parts[6].equals(username)) {
                    // Update the staff information
                    parts[1] = newName;
                    parts[2] = newSurname;
                    parts[3] = newPhoneNumber;
                    parts[4] = newAddress;
                    parts[5] = newEmail;
                    parts[6] = newUsername;
                    parts[7] = newPassword;
                    lines.set(i, String.join(";", parts));
                    break;
                }
            }
            // Write the updated lines back to the file
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // update from List
    	List<Staff> stafflist = getStaff();    	
        for (Staff staff : stafflist) {
            if (staff.getUsername().equals(username)) {
                // Update the admin information
            	staff.setName(newName);
            	staff.setSurname(newSurname);
            	staff.setPhoneNumber(newPhoneNumber);
            	staff.setEmail(newEmail);
            	staff.setAddress(newAddress);
            	staff.setUsername(newUsername);
            	staff.setPassword(newPassword);            	
                break;
            }
        }
    }
    
    //Update Member
    @Override
    public void updateMember(String filename, String username, String newName, String newSurname, String newPhoneNumber, String newEmail, String newUsername, String newPassword) {
        try {
            // Read all lines from the file and update the line containing the member information
            //List<String> lines = Files.readAllLines(Paths.get(filename));
        	List<String> lines = new ArrayList<>();
     	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
     	        String line;
     	        while ((line = br.readLine()) != null) {
     	            lines.add(line);
     	        }
     	    } catch (IOException e) {
     	        e.printStackTrace();     	       
     	    }
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(";");
                if (parts[5].equals(username)) {
                    // Update the member information
                    parts[1] = newName;
                    parts[2] = newSurname;
                    parts[3] = newPhoneNumber;
                    parts[4] = newEmail;
                    parts[5] = newUsername;
                    parts[6] = newPassword;
                    lines.set(i, String.join(";", parts));
                    break;
                }
            }
            // Write the updated lines back to the file
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update from List
        List<Member> memberlist = getMembers();    	
        for (Member member : memberlist) {
            if (member.getUsername().equals(username)) {
                // Update the member information
            	member.setName(newName);
            	member.setSurname(newSurname);
            	member.setPhoneNumber(newPhoneNumber);
            	member.setEmail(newEmail);            	
            	member.setUsername(newUsername);
            	member.setPassword(newPassword);            	
                break;
            }
        }
    }
    
    // Write & Update Loan Record CSV
    @Override
    public void writeOrUpdateLoanRecords(String filename, List<Loan> loans) {   
    	
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write header if the file is empty
            if (Files.size(Paths.get(filename)) == 0) {
                writer.write("ID,Name,Surname,Book Name,Due Date,Fine\n");
            }

            // Iterate over the list of loans
            for (Loan loan : loans) {
                StringBuilder loanRecord = new StringBuilder();
                loanRecord.append(loan.getMember().getID()).append(";")
                          .append(loan.getMember().getName()).append(";")
                          .append(loan.getMember().getSurname()).append(";")
                          .append(loan.getBook().getTitle()).append(";")
                          .append(loan.getDueDate()).append(";")
                          .append(loan.getFine()).append("\n");

                writer.write(loanRecord.toString());
            }
            System.out.println("Loan records written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing loan records to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void writeOrUpdateLoanRecords(String filename, String newfine, String name, String surname, String Title) {
        
    	try {
            // Read all lines from the file and update the line containing the member information
            //List<String> lines = Files.readAllLines(Paths.get(filename));
        	List<String> lines = new ArrayList<>();
     	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
     	        String line;
     	        while ((line = br.readLine()) != null) {
     	            lines.add(line);
     	        }
     	    } catch (IOException e) {
     	        e.printStackTrace();     	       
     	    }
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(";");
                if (parts[0].equals(name)&&parts[1].equals(surname)&& parts[2].equals(Title)) {
                    // Update the member information
                    parts[4] = newfine;
                    lines.set(i, String.join(";", parts));
                    break;
                }
            }
            // Write the updated lines back to the file
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update from List
    	double fine = Double.parseDouble(newfine);
        List<Loan> Loanlist = getLoanRecords();    	
        for (Loan loans : Loanlist) {
            if (loans.getMember().getName().equals(name)&& loans.getMember().getSurname().equals(surname)&&loans.getBook().getTitle().equals(Title)) {
                // Update the member information
            	loans.setFine(fine);           	
                break;
            }
        }
    }

    @Override
    public void writeLoanRecord(String filename, String memberName, String memberSurname, String bookName, String dueDate, double fine,java.util.Date date) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // Read existing loan records
            List<Loan> existingLoans = csv_read.getLoanRecords();
            // Check if there is an existing loan record for the same member and book
            boolean loanExists = false;
            for (Loan loan : existingLoans) {
                if (loan.getMember().getName().equals(memberName) && loan.getBook().getTitle().equals(bookName)) {
                    // Loan record already exists, do not write a new one
                    loanExists = true;
                    System.out.println("Loan record already exists for this member and book.");
                    break;
                }
            }
            if (!loanExists) {
            	
            	// Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(date);

                // Format the fine
                DecimalFormat df = new DecimalFormat("#.##");
                String fineString = df.format(fine);
            	
                // Construct the loan record entry
            	//String fineString = String.valueOf(fine);
                String loanRecord = memberName + ";" + memberSurname + ";" + bookName + ";" + dateString + ";" + fineString + "\n";
                // Write the loan record to the file
                writer.write(loanRecord);
                // add to the array
                /*Loan newloan = new Loan(memberName, memberSurname, bookName, date, fine);
                addLoan(newloan);*/
                System.out.println("Loan record written to file successfully.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the loan record to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Remove an entry from a CSV file
    @Override
    public void removeEntryFromCSV(String filename, String valueToRemove) {
        // Create a temporary file to write the modified content
        String tempFileName = "temp.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName))) {

            // Read the first line (header) and write it to the temporary file
            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine(); // Write a new line after the header
            }

            // Read and process the remaining lines
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (!parts[0].equals(valueToRemove)) {
                    writer.write(line);
                    writer.newLine(); // Write a new line after each valid entry
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if there's an error during file processing
        }

        // Replace the original file with the temporary file
        try {
            Files.move(Paths.get(tempFileName), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void remove_CSV_loan(String filename, String name,String surname,String bookname) {
    	// Create a temporary file to write the modified content
        String tempFileName = "temp.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName))) {

            // Read the first line (header) and write it to the temporary file
            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine(); // Write a new line after the header
            }

            // Read and process the remaining lines
            String line;
            while ((line = reader.readLine()) != null) {                
            	String[] parts = line.split(";");
            	if (!(parts[0].equals(name)&& parts[1].equals(surname)&& parts[2].equals(bookname))) {
                    writer.write(line);
                    writer.newLine(); // Write a new line after each valid entry
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if there's an error during file processing
        }

        // Replace the original file with the temporary file
        try {
            Files.move(Paths.get(tempFileName), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
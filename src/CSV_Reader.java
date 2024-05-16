import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSV_Reader extends CSV implements CSV_ReaderInterface {

	// Read Book CSV
	@Override
	public void readBook(String filepath) throws IOException {		
		BufferedReader excelbook = new BufferedReader(new FileReader(filepath));
		
		String line ;		
		excelbook.readLine();

		while((line = excelbook.readLine()) != null) {
			String[] LS = line.split(";");     //LS = line split
			String Title = LS[1];
			String ISBN = LS[0];
			String autor = LS[2];
			String publishYear = LS[3];
			int quantity = 1; // Default quantity of book = 1	
			String img = LS[6]; 
			
			Book book = new Book(Title, autor, ISBN, publishYear, quantity, img);
			
			addBook(book);			
		}
		excelbook.close();
	}

	// Read Staff CSV
	@Override
	public void readStaff(String filepath) throws IOException {
        BufferedReader excelStaff = new BufferedReader(new FileReader(filepath));
		
		String line ;
		excelStaff.readLine();
		
		while((line = excelStaff.readLine()) != null) {
			String[] LS = line.split(";");	//LS = line split
			int ID =Integer.parseInt(LS[0]);
			String name = LS[1];
			String surname = LS[2];
			String phonNumber = LS[3];
			String address = LS[4]; 
			String email = LS[5];
			String username = LS[6]; 
			String password = LS[7]; 
			String salary1 = LS[8];
			
			if (salary1.endsWith(",")) {
	            salary1 = salary1.substring(0, salary1.length() - 1); // Remove the last character","
	        }
			Double salary =Double.valueOf(salary1);
			
			Staff staff = new Staff(ID, name, surname, phonNumber, address, email, username, password, salary);

			addStaff(staff);			
		}
		excelStaff.close();
	}

	// Read Member CSV
	@Override
	public void readMember(String filepath) throws IOException {
        BufferedReader excelMember = new BufferedReader(new FileReader(filepath));
		
		String line;
		excelMember.readLine();
		
		while((line = excelMember.readLine()) != null) {
			String[] LS = line.split(";");	//LS = line split	
			String id = LS[0];
			String name = LS[1];
			String surname = LS[2];
			String phonNumber = LS[3];
			String email = LS[4];
			String username = LS[5]; 
			String password = LS[6]; 			
			int ID = Integer.valueOf(id);

			Member member = new Member(ID, name, surname, phonNumber, email, username, password);
			
			addMember(member);
					
		}
		excelMember.close();
	}
	
	// Read Loan Record CSV
	@Override
	public String[] readLoanRecords(String fileName) throws IOException {
	    String[] allLines= new String[1000];
    	try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			
			int i =0;
			reader.readLine(); // Skipping the header line
			while ((line = reader.readLine()) != null) {
				allLines[i]=line;
			    i++; 
			}
		}
        return allLines;
	}
	
	// for Staff/Member ID checks
	@Override
	public int IDFound(String filePath) throws IOException { 
	       BufferedReader excelStaff = new BufferedReader(new FileReader(filePath));
	        
	        String line ;
	        excelStaff.readLine();
	        int id=0;
	        while((line = excelStaff.readLine()) != null) {
	            String[] LS = line.split(";");    //LS = line split
	            int ID =Integer.parseInt(LS[0]);
	            id =ID;
	        }
	        excelStaff.close();
	        return id;
	 }
}

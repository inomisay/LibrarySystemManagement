
public abstract class Person implements PersonInterface {
	
	// Attributes
    protected int ID = 0;
    protected String name;
    protected String surname;
    protected String phoneNumber = "";
    protected String address = "";
    protected String email = null;
    protected String username = null;
    protected String password = null;
    
    // Constructor
    // Staff
    public Person(int ID, String name, String surname, String phoneNumber, String address, String email, String username, String password) {
        this.ID = ID;
    	this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    // Member
    public Person(int ID, String name, String surname, String phoneNumber, String email, String username, String password) {
        this.ID = ID;
    	this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public Person(String name, String surname) {
    	this.name = name;
        this.surname = surname;
    }
    
    // Methods (Setters/Getters)
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
    
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
 	// Abstract method
    public abstract void displayInfo();
}


public class Book implements BookInterface {
	
	// Attributes
    private String title;
    private String author;
    private String ISBN;
    private String publishYear;
    private int totalQuantity = 1; // Default total quantity of book = 1
    private String img;
    
    // Constructor
    public Book(String title, String author, String ISBN, String publishYear, int totalQuantity, String img) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publishYear = publishYear;
        setTotalQuantity(totalQuantity);
        this.img = img;
    }
    
    public Book(String title) {
    	this.title = title;
	}

	// Methods (Setters/Getters)
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getISBN() {
        return ISBN;
    }

    @Override
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String getPublishYear() {
        return publishYear;
    }

    @Override
    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    @Override
    public int getTotalQuantity() {
        return totalQuantity;
    }

    @Override
    public void setTotalQuantity(int totalQuantity) {
    	if (totalQuantity > 0) {
    		this.totalQuantity = getTotalQuantity() ;
        } else {
            System.out.println("Total quantity must be a positive integer.");
        }
    }
    
    @Override
    public void setIMG(String img) {
        this.img = img;
    }

    @Override
    public String getIMG() {
        return img;
    }
    
    // Methods
    @Override
    public void decreaseTotalQuantity(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative.");
        }
        if (totalQuantity >= amount) {
            totalQuantity -= amount;
        } else {
            throw new IllegalArgumentException("Cannot decrease total quantity. Quantity is less than the specified amount.");
        }
    }

    @Override
    public void increaseTotalQuantity(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative.");
        }
        totalQuantity += amount;
    }
}

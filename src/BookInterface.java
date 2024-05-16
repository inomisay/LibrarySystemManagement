
public interface BookInterface {

    /**
     * Get the title of the book.
     * @return The title of the book.
     */
    String getTitle();

    /**
     * Set the title of the book.
     * @param title The title to set.
     */
    void setTitle(String title);

    /**
     * Get the author of the book.
     * @return The author of the book.
     */
    String getAuthor();

    /**
     * Set the author of the book.
     * @param author The author to set.
     */
    void setAuthor(String author);

    /**
     * Get the ISBN of the book.
     * @return The ISBN of the book.
     */
    String getISBN();

    /**
     * Set the ISBN of the book.
     * @param ISBN The ISBN to set.
     */
    void setISBN(String ISBN);

    /**
     * Get the publish year of the book.
     * @return The publish year of the book.
     */
    String getPublishYear();

    /**
     * Set the publish year of the book.
     * @param publishYear The publish year to set.
     */
    void setPublishYear(String publishYear);

    /**
     * Get the total quantity of the book.
     * @return The total quantity of the book.
     */
    int getTotalQuantity();

    /**
     * Set the total quantity of the book.
     * @param totalQuantity The total quantity to set.
     */
    void setTotalQuantity(int totalQuantity);

    /**
     * Set the image of the book.
     * @param img The image URL to set.
     */
    void setIMG(String img);

    /**
     * Get the image URL of the book.
     * @return The image URL of the book.
     */
    String getIMG();

    /**
     * Decrease the total quantity of the book by the specified amount.
     * @param amount The amount by which to decrease the total quantity.
     * @throws IllegalArgumentException If the specified amount is negative or exceeds the current total quantity.
     */
    void decreaseTotalQuantity(int amount) throws IllegalArgumentException;

    /**
     * Increase the total quantity of the book by the specified amount.
     * @param amount The amount by which to increase the total quantity.
     * @throws IllegalArgumentException If the specified amount is negative.
     */
    void increaseTotalQuantity(int amount) throws IllegalArgumentException;
}

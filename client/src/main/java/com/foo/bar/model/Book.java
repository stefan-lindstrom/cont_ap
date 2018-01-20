package com.foo.bar.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Book {

    @NotNull(message = "Titel får ej vara null")
    @Size(min=1, message = "Titel får ej vara tom")
    private String title;

    @NotNull(message = "Författare får ej vara null")
    @Size(min=1, message = "Författare får ej vara tom")
    private String author;

    @NotNull(message = "Pris får ej vara null")
    @DecimalMin(value = "0.0", message = "Pris får ej vara negativt")
    private BigDecimal price;

    public Book() {
    }

    public Book(String theTitle, String theAuthor, BigDecimal thePrice) {
        title = theTitle;
        author = theAuthor;
        price = thePrice;
    }

    public String getTitle() { return title; }
    public void setTitle(String theTitle) { title = theTitle; }

    public String getAuthor() {  return author; }
    public void setAuthor(String theAuthor) { author = theAuthor; }

    public BigDecimal getPrice() {  return price; }
    public void setPrice(BigDecimal thePrice) { price = thePrice; }

    @Override
    public String toString() {
        // For debugging
        return title + " av " + author + " @ " + price + "Kr";
    }

    @Override
    public int hashCode() {
        return (null == title ? 1 : title.hashCode()) + 7 * (null == author ? 1 : author.hashCode()) + 17 * (null == price ? 1 : price.hashCode());
    }

    @Override
    public boolean equals(Object other) {
        Book otherBook = (Book) other;

        return (null == title ? title == otherBook.title : title.equals(otherBook.title)) &&
                (null == author ? author == otherBook.author : author.equals(otherBook.author)) &&
                (null == price ? price == otherBook.price : price.equals(otherBook.price));
    }
}

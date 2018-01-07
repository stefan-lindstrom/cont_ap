package com.foo.bar.model;

import java.math.BigDecimal;

public class Book {

    private String title;
    private String author;
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
        return title.hashCode() + 7 * author.hashCode() + 17 * price.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        Book otherBook = (Book) other;

        return title.equals(otherBook.title) && author.equals(otherBook.author) && price.equals(otherBook.price);
    }
}
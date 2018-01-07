package com.foo.bar.model;

import java.math.BigDecimal;

public class Book {

    private String title;
    private String author;
    private BigDecimal price;

    public Book(){
    }

    public Book(String theTitle, String theAuthor, BigDecimal thePrice) {
        title = theTitle;
        author = theAuthor;
        price = thePrice;
    }
}
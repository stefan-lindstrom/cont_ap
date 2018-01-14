package com.foo.bar.client;


import com.foo.bar.model.Book;

public interface ClientInterface {
    Book[] list(String searchString);
    boolean add(Book book, int quantity);
    int[] buy(Book... books);
}

package com.foo.bar.service;

import com.foo.bar.model.Book;

public interface IBookService {
    Book[] list(String searchString);
    boolean add(Book book, int quantity);
    int[] buy(Book... books);
}

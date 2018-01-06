package com.foo.bar.service;

import com.foo.bar.model.Book;

public class BookService implements IBookService {

    @Override
    public Book[] list(String searchString) {
        return new Book[0];
    }

    @Override
    public boolean add(Book book, int quantity) {
        return false;
    }

    @Override
    public int[] buy(Book... books) {
        return new int[0];
    }
}
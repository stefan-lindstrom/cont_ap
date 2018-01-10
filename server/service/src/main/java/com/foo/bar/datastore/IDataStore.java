package com.foo.bar.datastore;

import com.foo.bar.model.Book;
import com.foo.bar.types.StatusCode;

import java.util.Collection;

public interface IDataStore {
    String URL = "https://raw.githubusercontent.com/contribe/contribe/dev/bookstoredata/bookstoredata.txt";

    Collection<Book> findBooks(String searchTerm);
    StatusCode buyBook(Book theBook);
    boolean addBook(Book theBook, Integer theAntal);
    Collection<Book> getBookList();
}

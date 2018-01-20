package com.foo.bar.service;

import com.foo.bar.datastore.DataStore;
import com.foo.bar.datastore.IDataStore;
import com.foo.bar.datastore.io.DatastoreReadException;
import com.foo.bar.helpers.ValidationHelper;
import com.foo.bar.model.Book;
import com.foo.bar.types.StatusCode;

import java.util.stream.Stream;

public class BookService implements IBookService {
    private IDataStore itsDatastore;
    private ValidationHelper itsValidationHelper;

    public BookService() {
        itsValidationHelper = new ValidationHelper();
    }

    public void init() throws DatastoreReadException {
        itsDatastore = new DataStore();
    }

    @Override
    public Book[] list(String searchString) {
        return null == searchString ? itsDatastore.getBookList().toArray(new Book[0]) : itsDatastore.findBooks(searchString).toArray(new Book[0]);
    }

    @Override
    public boolean add(Book book, int quantity) {
        if (quantity < 0) {
            return false;
        }
        if (!itsValidationHelper.validateSimple(book)) {
            return false;
        }

        return itsDatastore.addBook(book, quantity);
    }

    @Override
    public int[] buy(Book... books) {
        if (!itsValidationHelper.validationCollection(books)) {
            return new int[0];
        }
        return Stream.of(books)
                .map(b -> itsDatastore.buyBook(b))
                .mapToInt(StatusCode::code)
                .toArray();
   }
}
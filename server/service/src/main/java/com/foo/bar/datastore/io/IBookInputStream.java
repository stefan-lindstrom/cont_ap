package com.foo.bar.datastore.io;

import com.foo.bar.model.BookDataStoreEntry;

import java.io.IOException;

public interface IBookInputStream {
    BookDataStoreEntry readBook() throws DatastoreReadException;
}

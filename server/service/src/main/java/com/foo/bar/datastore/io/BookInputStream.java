package com.foo.bar.datastore.io;


import com.foo.bar.model.Book;
import com.foo.bar.model.BookDataStoreEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;

public class BookInputStream extends InputStream implements IBookInputStream {
    private LineNumberReader itsReader;

    public BookInputStream(InputStream theStream) {
        itsReader = new LineNumberReader(new InputStreamReader(theStream));
    }

    @Override
    public BookDataStoreEntry readBook() throws DatastoreReadException {
        try {
            String line = null;
            line = itsReader.readLine();
            if (line == null) {
                return null;
            }
            String[] data = line.split(";");
            if (data.length != 4) {
                throw new DatastoreReadException("Invalid data, expected 4 entries from string: " + line);
            }

            BookDataStoreEntry aStoreEntry = new BookDataStoreEntry(new Book(data[0], data[1], new BigDecimal(data[2])), new Integer(data[3]));

            return aStoreEntry;
        } catch (IOException e) {
            throw new DatastoreReadException("I/O error while reading bookdata: ", e);
        } catch(NumberFormatException e) {
            throw new DatastoreReadException("Number format error (price)", e);
        }
    }

    @Override
    public int read() throws IOException {
        return itsReader.read();
    }
}

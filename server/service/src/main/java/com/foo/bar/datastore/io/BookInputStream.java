package com.foo.bar.datastore.io;


import com.foo.bar.model.Book;
import com.foo.bar.model.BookDataStoreEntry;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BookInputStream extends InputStream implements IBookInputStream {
    private LineNumberReader itsReader;

    public BookInputStream(InputStream theStream) {
        itsReader = new LineNumberReader(new InputStreamReader(theStream));
    }

    public BookInputStream(Reader aReader) {
        itsReader = new LineNumberReader(aReader);
    }


    @Override
    public BookDataStoreEntry readBook() throws DatastoreReadException {
        try {
            String line;

            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            df.setParseBigDecimal(true);

            line = itsReader.readLine();
            if (line == null) {
                return null;
            }
            String[] data = line.split(";");
            if (data.length != 4) {
                throw new DatastoreReadException("Invalid data, expected 4 entries from string: " + line);
            }

            BookDataStoreEntry aStoreEntry = new BookDataStoreEntry(new Book(data[0], data[1],  (BigDecimal)df.parseObject(data[2])), new Integer(data[3]));

            return aStoreEntry;
        } catch (IOException e) {
            throw new DatastoreReadException("I/O error while reading bookdata: ", e);
        } catch(NumberFormatException e) {
            throw new DatastoreReadException("Number format error )", e);
        } catch (ParseException e) {
            throw new DatastoreReadException("Number parse error )", e);
        }
    }

    @Override
    public int read() throws IOException {
        return itsReader.read();
    }
}

package com.foo.bar.datastore;

import com.foo.bar.datastore.io.BookInputStream;
import com.foo.bar.datastore.io.DatastoreReadException;
import com.foo.bar.model.Book;
import com.foo.bar.model.BookDataStoreEntry;
import com.foo.bar.types.StatusCode;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/*
 * Datastore with support functions. Reads data from URL. Normally would not exists (data would be in a DB with a JPA model.
 */
public class DataStore implements IDataStore {
    private Collection<BookDataStoreEntry> itsStore = new HashSet<>();
    private BiPredicate<String, String> searchFun = (x,y) -> x.toUpperCase().contains(y.toUpperCase());

    public DataStore() throws DatastoreReadException {
        URL url;
        try {
            url = new URL(URL);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            getContent(con);

        } catch (MalformedURLException e) {
            throw new DatastoreReadException("Malformed URL " + URL, e);
        } catch (IOException e) {
            throw new DatastoreReadException("I/O error ", e);
        }
    }

    public DataStore(Reader theReader) throws DatastoreReadException {
        BookInputStream theStream = new BookInputStream(theReader);
        BookDataStoreEntry aBook;
        while (null != (aBook = theStream.readBook())) {
            itsStore.add(aBook);
        }
    }

    public void dumpStoreToStream(PrintStream theStream) {
        // for Debugging
        itsStore.forEach(b -> theStream.println(b));
    }

    public Collection<BookDataStoreEntry> getAllStoreItems() {
        return itsStore;
    }

    public Collection<BookDataStoreEntry> getItemsInStore() {
        return itsStore.stream().filter(b -> b.getAntal() > 0).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> getBookList() {
        return itsStore.stream().map(BookDataStoreEntry::getBook).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findBooks(String searchTerm) {
        return getAllStoreItems().stream()
                .filter(b -> searchFun.test(b.getBook().getAuthor(),searchTerm) || searchFun.test(b.getBook().getTitle(),searchTerm))
                .map(BookDataStoreEntry::getBook)
                .collect(Collectors.toSet());
    }

    public Collection<Book> findBooks(String author, String title) {
        return getAllStoreItems().stream()
                .filter(b -> searchFun.test(b.getBook().getAuthor(),author) && searchFun.test(b.getBook().getTitle(), title))
                .map(BookDataStoreEntry::getBook)
                .collect(Collectors.toSet());
    }

    @Override
    public StatusCode buyBook(Book theBook) {
        BookDataStoreEntry entry = findByBook(theBook);
        if (null != entry) {
            if (entry.getAntal() > 0) {
                entry.setAntal(entry.getAntal() - 1);
                // Not strictly needed, but if implementation changes, this should be safe
                addBook(theBook, entry.getAntal());
                return StatusCode.OK;
            }
            return StatusCode.NOT_IN_STOCK;
        }
        return StatusCode.DOES_NOT_EXIST;
    }

    @Override
    public boolean addBook(Book theBook, Integer theAntal) {
        return  (theAntal < 0) ? false : itsStore.add(new BookDataStoreEntry(theBook, theAntal));
    }

    private BookDataStoreEntry findByBook(Book theParam) {
        return getAllStoreItems().stream()
                .filter(be -> be.getBook().equals(theParam))
                .findFirst()
                .orElse(null);
    }

    private void getContent(HttpsURLConnection connection) throws DatastoreReadException {
        try {
            BookInputStream bis = new BookInputStream(connection.getInputStream());
            BookDataStoreEntry entry = null;

            while ((entry = bis.readBook()) != null) {
                itsStore.add(entry);
            }
            bis.close();

        } catch (IOException e) {
            throw new DatastoreReadException("I/O error ", e);
        }
    }
}


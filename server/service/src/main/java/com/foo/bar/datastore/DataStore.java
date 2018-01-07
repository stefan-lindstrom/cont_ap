package com.foo.bar.datastore;

import com.foo.bar.datastore.io.BookInputStream;
import com.foo.bar.datastore.io.DatastoreReadException;
import com.foo.bar.model.Book;
import com.foo.bar.model.BookDataStoreEntry;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

/*
 * Datastore with support functions. Reads data from URL. Normally would not exists (data would be in a DB with a JPA model.
 */
public class DataStore implements IDataStore {
    Collection<BookDataStoreEntry> itsStore = new HashSet<>();

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

    public Collection<Book> getBookList() {
        return itsStore.stream().map(BookDataStoreEntry::getBook).collect(Collectors.toSet());
    }

    public Collection<Book> findBooks(String searchTerm) {
        return getAllStoreItems().stream()
                .filter(b -> b.getBook().getAuthor().contains(searchTerm) || b.getBook().getTitle().contains(searchTerm))
                .map(BookDataStoreEntry::getBook)
                .collect(Collectors.toSet());
    }

    public Collection<Book> findBooks(String author, String title) {
        return getAllStoreItems().stream()
                .filter(b -> b.getBook().getAuthor().contains(author) && b.getBook().getTitle().contains(title))
                .map(BookDataStoreEntry::getBook)
                .collect(Collectors.toSet());
    }

    public boolean buyBook(Book theBook) {
        BookDataStoreEntry entry = findByBook(theBook);
        if (null != entry) {
            entry.setAntal(entry.getAntal() - 1);
            return true;
        }
        return false;
    }

    public boolean addBook(Book theBook, Integer theAntal) {
        if (null == findByBook(theBook)) {
            itsStore.add(new BookDataStoreEntry(theBook, theAntal));
            return true;
        }
        return false;
    }

    private BookDataStoreEntry findByBook(Book theParam) {
        return getItemsInStore().stream()
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

    public static void main(String[] argv) throws Exception {
        DataStore ds = new DataStore();
        ds.dumpStoreToStream(System.out);

        System.out.println("");
        ds.getBookList().forEach(b -> System.out.println(b));

        System.out.println("");
        ds.getItemsInStore().forEach(b -> System.out.println(b));

        System.out.println("");
        System.out.println("Findbook(2 args) = ");
        ds.findBooks("ing Bast", "andom Sa").forEach(b -> System.out.println(b));

        System.out.println("");
        System.out.println("Findbook(1 arg) = ");
        ds.findBooks("Money").forEach(b -> System.out.println(b));

        System.out.println("");
        System.out.println("buyBook() = " + ds.buyBook(ds.findBooks("Money").iterator().next()));
        System.out.println("");
        ds.getItemsInStore().forEach(b -> System.out.println(b));

        ds.addBook(new Book("Demon Haunted World", "Carl Sagan", new BigDecimal("155.50")), 3);
        System.out.println("");
        ds.getItemsInStore().forEach(b -> System.out.println(b));
    }
}


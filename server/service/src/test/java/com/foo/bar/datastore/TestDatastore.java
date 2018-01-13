package com.foo.bar.datastore;

import com.foo.bar.datastore.io.DatastoreReadException;
import com.foo.bar.model.Book;
import com.foo.bar.model.BookDataStoreEntry;
import com.foo.bar.types.StatusCode;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDatastore {
    private DataStore itsTestStore;
    private static final String bookData = "Demon Haunted World;Carl Sagan;155.50;2\n"+
            "Revelation Space;Alastair Reynolds;99.90;1\n" +
            "Guards! Guards!;Terry Pratchett;12.33;12\n"+
            "Leviathan Wakes;James S A Corey;15.37;0";

    @Before
    public void setUp() throws DatastoreReadException {
        StringReader aStringReader = new StringReader(bookData);
        itsTestStore = new DataStore(aStringReader);
    }

    @Test
    public void testBasicData() {
        assertEquals("Expected 4 books in store", 4, itsTestStore.getAllStoreItems().size());
        assertEquals("Expected 4 books from list()", 4, itsTestStore.getBookList().size());
        assertEquals("Expected one less book when listing items in store", 3, itsTestStore.getItemsInStore().size());
    }

    @Test
    public void testFindAuthorExists() {
        assertEquals("Expected to find book",1, itsTestStore.findBooks("lastair Reyn").size());
    }

    @Test
    public void testFindAuthorMixedCaseExists() {
        assertEquals("Expected to find book (case insensitive search)", 1, itsTestStore.findBooks("LasTaIr ReYn").size());
    }

    @Test
    public void testFindAuthorDoesNotExist() {
        assertEquals("Did not expect to find boook", 0, itsTestStore.findBooks("foobar").size());
    }

    @Test
    public void testFindTitleExists() {
        assertEquals("Expected to find book",1, itsTestStore.findBooks("Wakes").size());
    }

    @Test
    public void testFinTitleDoesNotExist() {
        assertEquals("Did not expect to find boook",0, itsTestStore.findBooks("foobar").size());
    }

    @Test
    public void testAddNewBook() {
        boolean rc = itsTestStore.addBook(new Book("Foo", "Bar", new BigDecimal(40.00)), 4);
        assertTrue("Expected add to succeed", rc);

        assertEquals("Expected 5 books in store after add",5, itsTestStore.getAllStoreItems().size());
        assertEquals("Expected 5 books from list() after add", 5, itsTestStore.getBookList().size());
        assertEquals("Expected one less book when listing items in store",4, itsTestStore.getItemsInStore().size());
    }

    @Test
    public void testAddNewBookNegativeQuantity() {
        boolean rc = itsTestStore.addBook(new Book("Foo", "Bar", new BigDecimal(40.00)), -4);
        assertFalse("Expected add to fail due to negatove quantty", rc);
        assertEquals("Expected 4 books in store after failed add",4, itsTestStore.getAllStoreItems().size());
        assertEquals("Expected 4 books from list() after failed add", 4, itsTestStore.getBookList().size());
        assertEquals("Expected one less book when listing items in store",3, itsTestStore.getItemsInStore().size());
    }

    @Test
    public void testUpdateBookQuantity() {
        // add new book with quantity 4
        testAddNewBook();
        BookDataStoreEntry n = itsTestStore.getAllStoreItems().stream().filter(bde -> bde.getBook().equals(itsTestStore.findBooks("Foo").iterator().next())).findFirst().get();
        assertEquals("Expected the book to have quantity 4 after addition",4, n.getAntal().intValue());

        boolean rc = itsTestStore.addBook(new Book("Foo", "Bar", new BigDecimal(40.00)), 8);
        assertTrue("Expected add to success",rc);

        // Not strictly needed, same object reference used, but that is an implementation detail that may not hold true in the future,
        // and thus not a good idea to base parts of a test on
        n = itsTestStore.getAllStoreItems().stream().filter(bde -> bde.getBook().equals(itsTestStore.findBooks("Foo").iterator().next())).findFirst().get();
        assertEquals("Expected quantity to be updated correctly after add",8, n.getAntal().intValue());
    }

    @Test
    public void testBuyNonExistingBook() {
        assertEquals("Store should return DOES_NOT_EXISTS", StatusCode.DOES_NOT_EXIST, itsTestStore.buyBook(new Book("Foo", "Bar", new BigDecimal("123.34"))));
    }

    @Test
    public void testBuyOutOfStockBook() {
        Book newBook = new Book("Foo", "Bar", new BigDecimal("123.34"));
        itsTestStore.addBook(newBook, 0);
        assertEquals("Store should return NOT_IN_STOCK",StatusCode.NOT_IN_STOCK, itsTestStore.buyBook(newBook));
    }

    @Test
    public void testBuyBook() {
        Book newBook = new Book("Foo", "Bar", new BigDecimal("123.34"));
        itsTestStore.addBook(newBook, 2);
        assertEquals("Store should return OK", StatusCode.OK, itsTestStore.buyBook(newBook));
        BookDataStoreEntry n = itsTestStore.getAllStoreItems().stream().filter(bde -> bde.getBook().equals(newBook)).iterator().next();
        assertEquals("Expected quantity to be one less after succeaful purchase", 1, n.getAntal().intValue());
    }
}

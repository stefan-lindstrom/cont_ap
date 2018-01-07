package com.foo.bar.datastore;

import com.foo.bar.datastore.io.DatastoreReadException;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

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
        assertEquals(4, itsTestStore.getAllStoreItems().size());
        assertEquals(4, itsTestStore.getBookList().size());
        assertEquals(3, itsTestStore.getItemsInStore().size());
    }

    @Test
    public void testFindAuthorExists() {
        assertEquals(1, itsTestStore.findBooks("lastair Reyn").size());
    }

    @Test
    public void testFindAuthorDoesNotExist() {
        assertEquals(0, itsTestStore.findBooks("foobar").size());
    }

    @Test
    public void testFindTitleExists() {
        assertEquals(1, itsTestStore.findBooks("Wakes").size());
    }

    @Test
    public void testFinTitleDoesNotExist() {
        assertEquals(0, itsTestStore.findBooks("foobar").size());
    }

}

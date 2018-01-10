package com.foo.bar.service;

import com.foo.bar.datastore.DataStore;
import com.foo.bar.datastore.io.DatastoreReadException;
import com.foo.bar.model.Book;
import com.foo.bar.types.StatusCode;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.StringReader;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestBookService {
    private DataStore itsDataStore;
    private IBookService itsService;

    private static final String bookData = "Demon Haunted World;Carl Sagan;155.50;2\n"+
            "Revelation Space;Alastair Reynolds;99.90;1\n" +
            "Guards! Guards!;Terry Pratchett;12.33;12\n"+
            "Leviathan Wakes;James S A Corey;15.37;0";

    @Before
    public void setup() throws DatastoreReadException {
        StringReader aStringReader = new StringReader(bookData);
        itsDataStore = new DataStore(aStringReader);
        itsService = new BookService();
        Whitebox.setInternalState(itsService, itsDataStore);
    }

    @Test
    public void testGetStock() {
        Book[] theList = itsService.list(null);
        assertEquals(4, theList.length);
    }

    @Test
    public void testListWithParamsMatch() {
        Book[] theList = itsService.list("re");
        assertEquals(2, theList.length);

    }

    @Test
    public void testListWithParamsNoMatch() {
        Book[] theList = itsService.list("herpderp");
        assertEquals(0, theList.length);
    }

    @Test
    public void buyBooks() {
        int[] result = itsService.buy(
                new Book("Guards! Guards!", "Terry Pratchett", new BigDecimal("12.33")),
                new Book("Leviathan Wakes","James S A Corey", new BigDecimal("15.37")),
                new Book("Herp", "Derp", new BigDecimal(42.20))
        );
       assertNotNull(result);
       assertEquals(3, result.length);
       assertEquals(StatusCode.OK.code().intValue(), result[0]);
       assertEquals(StatusCode.NOT_IN_STOCK.code().intValue(), result[1]);
       assertEquals(StatusCode.DOES_NOT_EXIST.code().intValue(), result[2]);
    }
}
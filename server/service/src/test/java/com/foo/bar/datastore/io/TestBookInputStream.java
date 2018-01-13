package com.foo.bar.datastore.io;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestBookInputStream {

    @Test(expected = NullPointerException.class)
    public void testNullStream() throws Exception {
        BookInputStream bis = new BookInputStream((Reader)null);
        bis.readBook();
    }

    @Test
    public void testEmptyStream() throws Exception {
        BookInputStream bis = new BookInputStream(new StringReader(""));
        assertNull("Expected null from empty stream, got book", bis.readBook());
    }

    @Test
    public void testNonEmptyStream() throws Exception {
        BookInputStream bis = new BookInputStream(new StringReader("Demon Haunted World;Carl Sagan;155.00;2"));
        assertNotNull("Expected to read a book, got null", bis.readBook());
        assertNull("Second read expected to be null, got book", bis.readBook());
    }

    @Test(expected = DatastoreReadException.class)
    public void testBadData() throws Exception {
        BookInputStream bis = new BookInputStream(new StringReader("Foo&Bar/xyzzy"));
        bis.readBook();
    }

    @Test(expected = DatastoreReadException.class)
    public void testBadPrice() throws Exception {
        BookInputStream bis = new BookInputStream(new StringReader("Foo;Bar;x1--11x;0"));
        bis.readBook();
    }

    @Test(expected = DatastoreReadException.class)
    public void testBadAntal() throws Exception {
        BookInputStream bis = new BookInputStream(new StringReader("Foo;Bar;1,100.33;0p?"));
        bis.readBook();
    }
}

package com.foo.bar.model;

import com.foo.bar.helpers.ValidationHelper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TestBook {
    ValidationHelper itsvalidationHelper;

    @Before
    public void setUp() {
        itsvalidationHelper = new ValidationHelper();
    }

    @Test
    public void testNullTitle() {
        Book b = new Book(null, "A", new BigDecimal("123.2"));
        assertFalse("Expected SimpleValidation to return false", itsvalidationHelper.validateSimple(b));
        assertTrue("Kunde inte hitta väntat valideringsdel i resultatet", itsvalidationHelper.validationWithMessage(b).contains("Titel får ej vara null"));
    }

    @Test
    public void testEmptyTitle() {
        Book b = new Book("", "A", new BigDecimal("123.2"));
        assertFalse("Expected SimpleValidation to return false", itsvalidationHelper.validateSimple(b));
        assertTrue("Kunde inte hitta väntat valideringsdel i resultatet", itsvalidationHelper.validationWithMessage(b).contains("Titel får ej vara tom"));
    }

    @Test
    public void testNullAuthor() {
        Book b = new Book("A", null, new BigDecimal("123.2"));
        assertFalse("Expected SimpleValidation to return false", itsvalidationHelper.validateSimple(b));
        assertTrue("Kunde inte hitta väntat valideringsdel i resultatet", itsvalidationHelper.validationWithMessage(b).contains("Författare får ej vara null"));
    }

    @Test
    public void testEmptyAuthor() {
        Book b = new Book("A", "", new BigDecimal("123.2"));
        assertFalse("Expected SimpleValidation to return false", itsvalidationHelper.validateSimple(b));
        assertTrue("Kunde inte hitta väntat valideringsdel i resultatet", itsvalidationHelper.validationWithMessage(b).contains("Författare får ej vara tom"));
    }

    @Test
    public void testNullPrice() {
        Book b = new Book("A", "B", null);
        assertFalse("Expected SimpleValidation to return false", itsvalidationHelper.validateSimple(b));
        assertTrue("Kunde inte hitta väntat valideringsdel i resultatet", itsvalidationHelper.validationWithMessage(b).contains("Pris får ej vara null"));
    }

    @Test
    public void testNegativePrice() {
        Book b = new Book("A", "B", new BigDecimal("-123.2"));
        assertFalse("Expected SimpleValidation to return false", itsvalidationHelper.validateSimple(b));
        assertTrue("Kunde inte hitta väntat valideringsdel i resultatet", itsvalidationHelper.validationWithMessage(b).contains("Pris får ej vara negativt"));
    }

    @Test
    public void testAllOk() {
        Book b = new Book("A", "B", new BigDecimal("123.2"));
        assertTrue("Expected SimpleValidation to return true", itsvalidationHelper.validateSimple(b));
        assertTrue("Vaiderings-resultat skall vara tomt", itsvalidationHelper.validationWithMessage(b).isEmpty());
    }

    @Test
    public void testEquality() {
        Book nullParts1 = new Book(null, "A", new BigDecimal("123"));
        Book nullParts2 = new Book(null, "A", new BigDecimal("123"));
        Book nullParts3 = new Book("B", "A", null);

        Book full1 = new Book("A", "B", new BigDecimal("12.2"));
        Book full2 = new Book("A", "C", new BigDecimal("12.2"));
        Book full3 = new Book("A", "B", new BigDecimal("12.22"));

        assertTrue("Expected book1 to equal book 2", nullParts1.equals(nullParts2));
        assertTrue("Expected book2 to equal book 1", nullParts2.equals(nullParts1));
        assertTrue("Expected book3 to equal itself", nullParts3.equals(nullParts3));
        assertFalse("Did not expect book1 to equal book 3", nullParts1.equals(nullParts3));
        assertFalse("Did not expect book3 to equal book 1", nullParts3.equals(nullParts1));

        assertFalse("Did not expect full book1 to equal full book 2", full1.equals(full2));
        assertFalse("Did not expect full book1 to equal full book 3", full1.equals(full3));
        assertFalse("Did not expect full book2 to equal full book 3", full2.equals(full3));

        assertTrue("Expected full book1 to equal itself", full1.equals(full1));
        assertTrue("Expected full book1 to equal itself", full2.equals(full2));
        assertTrue("Expected full book1 to equal itself", full3.equals(full3));
    }

    @Test
    public void testHashcode() {
        Book nullParts1 = new Book(null, "A", new BigDecimal("123"));
        Book nullParts2 = new Book(null, "A", new BigDecimal("123"));
        Book nullParts3 = new Book("B", "A", null);

        Book full1 = new Book("A", "B", new BigDecimal("12.2"));
        Book full2 = new Book("A", "C", new BigDecimal("12.2"));
        Book full3 = new Book("A", "B", new BigDecimal("12.22"));
        Book full4 = new Book("A", "B", new BigDecimal("12.22"));

        assertEquals("Expected book1 and book 2 to have same hashcode", nullParts1.hashCode(), nullParts2.hashCode());
        assertNotEquals("Did not expect book1 and book 3 to have same hashcode", nullParts1.hashCode(), nullParts3.hashCode());

        assertNotEquals("Did not expect full book1 and full book 2 to have same hashcode", full1.hashCode(), full2.hashCode());
        assertNotEquals("Did not expect full book1 and full book 3 to have same hashcode", full1.hashCode(), full3.hashCode());
        assertNotEquals("Did not expect full book2 and full book 3 to have same hashcode", full2.hashCode(), full3.hashCode());

        assertEquals("Expected full book3 and full book 4 to have same hashcode", full3.hashCode(), full4.hashCode());
    }
}

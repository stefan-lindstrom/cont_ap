package com.foo.bar.model;

public class BookDataStoreEntry {
    private Book itsBook;
    private Integer itsAntal;

    public BookDataStoreEntry() {
    }

    public BookDataStoreEntry(Book theBook, Integer theAntal) {
        itsBook = theBook;
        itsAntal = theAntal;
    }

    public Book getBook() { return itsBook;  }
    public void setBook(Book theBook) { itsBook = theBook;  }

    public Integer getAntal() {  return itsAntal; }
    public void setAntal(Integer theAntal) {  itsAntal = theAntal; }

    @Override
    public String toString() {
        // Mostly for debugging. If complex object, a StringBuilder might be good.
        return itsBook.toString() + ", antal i lager: " + itsAntal;
    }

    @Override
    public int hashCode() {
        return itsBook.hashCode() + 11*itsAntal.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        // don't check null or non-book instances. If we try to do aBook.compareTo(null) or aBook.equals(aBeerKeg)
        // something is *seriously* wrong, better to get error direct rather than hide the problem.
        BookDataStoreEntry otherEntry = (BookDataStoreEntry)other;

        // No field should be null, use Bean Validation (JSR 380) to ensure this (TODO)
        return itsBook.equals(otherEntry.itsBook) && itsAntal.equals(otherEntry.itsAntal);
    }
}

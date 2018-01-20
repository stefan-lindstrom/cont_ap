package com.foo.bar.store;

import com.foo.bar.model.Book;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ShoppingBasket {
    private List<Book> theBasket = new ArrayList<>();

    public ShoppingBasket() {
    }

    public void add(Book aBook) {
        theBasket.add(aBook);
    }

    public void remove(Book aBook) {
        theBasket.remove(aBook);
    }

    public void remove(int bookIdx) {
        theBasket.remove(bookIdx);
    }

    public void empty() {
        theBasket.clear();
    }

    public void display(PrintStream out, Function<Book, String> displayFunction) {
        theBasket.forEach(b -> out.println(displayFunction.apply(b)));
    }

    public void displayNumbered(PrintStream out, Function<Book, String> displayFunction) {
        theBasket.forEach(b -> out.println((theBasket.indexOf(b) + 1) +") " + displayFunction.apply(b)));
    }

    public int numItemsInBasket() {
        return theBasket.size();
    }

    public Book[] getContent() {
        return theBasket.toArray(new Book[0]);
    }
}

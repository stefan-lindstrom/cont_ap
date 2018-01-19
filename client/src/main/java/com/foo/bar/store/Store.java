package com.foo.bar.store;

import com.foo.bar.client.ClientInterface;
import com.foo.bar.client.StoreClient;
import com.foo.bar.model.Book;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.function.Function;

public class Store {
    private ClientInterface itsInterface;
    private ShoppingBasket itsBasket;
    private LineNumberReader itsInput;
    private Function<Book, String> itsListBookFunction = b -> b.getTitle() + " av " + b.getAuthor() + " @ " + b.getPrice() + ":-";

    public Store() {
        itsInterface = new StoreClient();
        itsBasket = new ShoppingBasket();
        itsInput = new LineNumberReader(new InputStreamReader(System.in));
    }

    public void displayMeny() {
        System.out.println("1) Lista alla böcker");
        System.out.println("2) Sök författare/böcker");
        System.out.println("3) Lägg till bok i kundvagn");
        System.out.println("4) Ta bort bok från kundvagn");
        System.out.println("5) Visa kundvagn");
        System.out.println("6) Töm kundvagn");
        System.out.println("7) Gå till kassan");
        System.out.println("8) Avsluta");
        System.out.println("\nVal: ");
    }


    public void run() throws IOException {
        int choice = 0;
        do {
            try {
                displayMeny();
                choice = new Integer(itsInput.readLine());
                switch (choice) {
                    case 1:
                        listBtoreContents();
                        break;
                    case 2:
                        searchForBook();
                        break;
                    case 3:
                        addBook();
                        break;
                    case 4:
                        removeBook();
                        break;
                    case 5:
                        System.out.println("Din kundkorg innehåller: ");
                        itsBasket.display(System.out, itsListBookFunction);
                        System.out.println();
                        break;
                    case 6:
                        itsBasket.empty();
                        break;
                    case 7:
                        checkout();
                        break;
                    case 8:
                        break;
                    default:
                        System.out.println("Falaktigt val!");
                }
            } catch (IOException ie) {
                System.out.println("Fel vid läsning av val, försök igen");
            } catch (NumberFormatException ne) {
                System.out.println("Inte en giltig siffra, försök igen");
            }
        } while(choice != 8);
    }

    private void checkout() {
    }

    private void removeBook() {
        try {
            itsBasket.displayNumbered(System.out, itsListBookFunction);
            System.out.println("\nVälj book (1 - " + itsBasket.numItemsInBasket() + "): ");
            int book = new Integer(itsInput.readLine());
            if (book < 1 || book > itsBasket.numItemsInBasket()) {
                System.out.println("Book nummer " + book + " existerar ej...");
                return;
            }
            itsBasket.remove(book - 1);
        } catch (IOException e) {
            System.out.println("Kunde inte läsa inmatning...");
        } catch (NumberFormatException nfe) {
            System.out.println("Ej giltigt nummer inmatat");
        }
    }

    private void addBook() {
        try {
            Book[] books = itsInterface.list("");
            for (int i = 0; i < books.length; ++i ) {
                System.out.println((i+1) + ") " + itsListBookFunction.apply(books[i]));
            }
            System.out.println("\nVälj book (1 - " + books.length + "): ");
            int book = new Integer(itsInput.readLine());
            if (book < 1 || book > books.length) {
                System.out.println("Book nummer " + book + " existerar ej...");
                return;
            }
            itsBasket.add(books[book-1]);
        } catch (IOException e) {
            System.out.println("Kunde inte läsa inmatning...");
        } catch (NumberFormatException nfe) {
            System.out.println("Ej giltigt nummer inmatat");
        }
    }

    private void searchForBook() {
        try {
            System.out.println("Mata in författare eller titel. Del av titel/namn går bra, och gemenr/versaler spelar ingen roll: ");
            System.out.println("Följande böcker matchar din sökning: ");
            for (Book aBook : itsInterface.list(itsInput.readLine())) {
                System.out.println(itsListBookFunction.apply(aBook));
            }
        } catch (IOException e) {
            System.out.println("Kunde inte läsa söksträng");
        }
        System.out.println();
    }

    private void listBtoreContents() {
        System.out.println("Följande böcker finns i affären:");
        for (Book aBook : itsInterface.list("")) {
            System.out.println(itsListBookFunction.apply(aBook));
        }
        System.out.println();
    }

    public static void main(String [] argv) throws IOException {
        Store sm = new Store();
        sm.run();
    }
}

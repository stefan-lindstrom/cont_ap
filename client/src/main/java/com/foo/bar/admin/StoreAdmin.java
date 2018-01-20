package com.foo.bar.admin;

import com.foo.bar.client.AdminClient;
import com.foo.bar.client.ClientInterface;
import com.foo.bar.model.Book;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.util.function.Function;

public class StoreAdmin {
    private ClientInterface itsInterface;
    private LineNumberReader itsInput;
    private Function<Book, String> itsListBookFunction = b -> b.getTitle() + " av " + b.getAuthor() + " @ " + b.getPrice() + ":-";

    public StoreAdmin() {
        itsInterface = new AdminClient();
        itsInput = new LineNumberReader(new InputStreamReader(System.in));

    }

    public void run() {
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
                        updateBook();
                        break;
                    case 3:
                        addNewBook();
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Falaktigt val!");
                }
            } catch (IOException ie) {
                System.out.println("Fel vid läsning av val, försök igen");
            } catch (NumberFormatException ne) {
                System.out.println("Inte en giltig siffra, försök igen");
            }
        } while(choice != 4);

    }

    private void addNewBook() {
        try {
            System.out.println("Mata in författare: ");
            String author = itsInput.readLine();

            System.out.println("Mata in titel: ");
            String title = itsInput.readLine();

            System.out.println("Mata in pris: ");
            BigDecimal price = new BigDecimal(itsInput.readLine());

            System.out.println("Mata in antal i lager: ");
            Integer amount = new Integer(itsInput.readLine());

            if (itsInterface.add(new Book(title, author, price), amount)) {
                System.out.println("Bok tillagd till lager.");
            } else {
                System.out.println("Kunde ej lägga till bok.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Kunde ej lägga till bok, fel vid inmatning av data.");
        }
    }

    private void updateBook() {
        try {
            Book[] books = itsInterface.list("");
            for (int i = 0; i < books.length; ++i ) {
                System.out.println((i+1) + ") " + itsListBookFunction.apply(books[i]));
            }
            System.out.println("\nVälj bok (1 - " + books.length + "): ");
            int book = new Integer(itsInput.readLine());
            if (book < 1 || book > books.length) {
                System.out.println("Bok nummer " + book + " existerar ej...");
                return;
            }
            System.out.println("Mata in antal exemplar som finns av boken: ");
            int amount = new Integer(itsInput.readLine());
            if (itsInterface.add(books[book-1], amount)) {
                System.out.println("Boken är uppdaterad...");
            } else {
                System.out.println("Kunde inte uppdatera boken i lagret.");
            }
        } catch (IOException e) {
            System.out.println("Kunde inte läsa inmatning...");
        } catch (NumberFormatException nfe) {
            System.out.println("Ej giltigt nummer inmatat");
        }
    }

    private void displayMeny() {
        System.out.println("1) Lista alla böcker");
        System.out.println("2) Uppdatera existerande bok");
        System.out.println("3) Lägg till ny bok");
        System.out.println("4) Avsluta");
    }

    private void listBtoreContents() {
        System.out.println("Följande böcker finns i affären:");
        for (Book aBook : itsInterface.list("")) {
            System.out.println(itsListBookFunction.apply(aBook));
        }
        System.out.println();
    }


    public static void main(String [] argv) {
        StoreAdmin sm = new StoreAdmin();
        sm.run();
    }
}

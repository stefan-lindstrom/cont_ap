package com.foo.bar.service;

import com.foo.bar.datastore.io.DatastoreReadException;
import com.foo.bar.helpers.BookHelper;
import com.foo.bar.model.Book;
import com.foo.bar.model.BookDTO;
import com.foo.bar.rest.IBookRest;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.Path;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Stream.of;
import static com.foo.bar.helpers.BookHelper.mapToBook;

@Path("bookstore")
public class BookStore implements IBookRest {
    // This should really be solved by injects, but this will do as a workaround for a test project.
    private static BookService itsService;

    public BookStore() {
    }

    @Override
    public Collection<BookDTO> list(String searchString) {
        String realSearchParam;
        if ("".equals(searchString)) {
            realSearchParam = null;
        } else {
            realSearchParam = searchString;
        }
        return of(itsService.list(realSearchParam))
                .map(BookHelper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean add(BookDTO book, Integer quantity) {
        return itsService.add(mapToBook(book), quantity);
    }

    @Override
    public Collection<Integer> buy(List<BookDTO> books) {
        if (!books.isEmpty()) {
            int[] replyArr = itsService.buy(books.stream()
                    .map(BookHelper::mapToBook)
                    .toArray(Book[]::new));
            List<Integer> rc =  Arrays.stream(replyArr).boxed().collect(Collectors.toList());
            return rc;
        }
        return Collections.EMPTY_LIST;
    }

    private static void setupConsoleLogging() {
        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
    }

    public HttpServer startServer() throws DatastoreReadException {
        final ResourceConfig rc = new ResourceConfig().packages("com.foo.bar.service");
        HttpServer server =  GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        return server;
    }

    public static void main(String[] args) throws IOException, DatastoreReadException {
        BookStore theStore = new BookStore();

        itsService = new BookService();
        itsService.init();

        final HttpServer server = theStore.startServer();

        System.out.println(String.format("Jersey app started with WADL available at %sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();

        server.stop();
    }
}

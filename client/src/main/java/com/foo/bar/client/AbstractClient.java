package com.foo.bar.client;

import com.foo.bar.helpers.BookHelper;
import com.foo.bar.model.Book;
import com.foo.bar.model.BookDTO;
import com.foo.bar.rest.IBookRest;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class AbstractClient implements ClientInterface {
    private Client itsClient;

    public AbstractClient() {
        super();
         itsClient = ClientBuilder.newClient(new ClientConfig());
    }

    @Override
    public Book[] list(String searchString) {
        WebTarget target = buildBasicTarget(IBookRest.class);
        target = target.path("list").queryParam("search", (null == searchString ? "" : searchString));
        Response response = target.request(MediaType.APPLICATION_JSON).get(Response.class);

        List<BookDTO> listOfBooks = getTypeFromResponse(new GenericType<List<BookDTO>>() {}, response);

        return listOfBooks.stream()
                .map(BookHelper::mapToBook)
                .toArray(Book[]::new);
    }

    protected WebTarget buildBasicTarget(Class ifClass) {
        WebTarget webTarget = itsClient.target(IBookRest.BASE_URI);
        Path topPath = (Path)ifClass.getAnnotation(Path.class);
        if (topPath == null) {
            // error
        }
        return webTarget.path(topPath.value());
    }

    protected <T> T getTypeFromResponse(GenericType<T> x, Response aResponse) {
        return aResponse.readEntity(x);
    }
}

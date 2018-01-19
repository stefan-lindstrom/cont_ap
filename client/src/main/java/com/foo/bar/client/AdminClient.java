package com.foo.bar.client;

import com.foo.bar.exceptions.NotImplementedException;
import com.foo.bar.helpers.BookHelper;
import com.foo.bar.model.Book;
import com.foo.bar.rest.IBookRest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AdminClient extends AbstractClient {
    public AdminClient() {
        super();
    }

    @Override
    public boolean add(Book book, int quantity) {
        WebTarget target = buildBasicTarget(IBookRest.class);
        target = target.path("add").queryParam("quantity", quantity);
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(BookHelper.mapToDTO(book)));

        return Boolean.parseBoolean(response.readEntity(String.class));
    }

    @Override
    public int[] buy(Book... books) {
        throw new NotImplementedException("buy() is not implemented for store admins");
    }
}

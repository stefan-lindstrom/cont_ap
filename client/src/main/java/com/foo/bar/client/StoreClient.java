package com.foo.bar.client;

import com.foo.bar.exceptions.NotImplementedException;
import com.foo.bar.helpers.BookHelper;
import com.foo.bar.model.Book;
import com.foo.bar.rest.IBookRest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoreClient extends AbstractClient {
    public StoreClient() {
        super();
    }

    @Override
    public boolean add(Book book, int quantity) {
        throw new NotImplementedException("add() is not implemented for store customers");
    }

    @Override
    public int[] buy(Book... books) {
        WebTarget target = buildBasicTarget(IBookRest.class);
        target = target.path("buy");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(Arrays.stream(books).map(BookHelper::mapToDTO).collect(Collectors.toList())));

        List<Integer> reply = getTypeFromResponse(new GenericType<List<Integer>>() {}, response);
        return reply.stream().mapToInt(i->i).toArray();
    }
}

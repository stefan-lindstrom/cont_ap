package com.foo.bar.rest;

import com.foo.bar.model.BookDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

// Ideal would be if jersey supported class level path annotation on i/f
// however, it does not, which sort of defeats the purpose of having this shared i/f if both
// client and sevter have to individually annotate this.
//
// Could be solved by setting this as a property in maven, and user filtered resource to generate
// client/server implementation classes with the path set before compilation stage in a "real"
// system but will solve it like this for now.
//
// One workaround to avoid discrepancy between shared/client shared/server is to make a simple
// unit test on both sides (client/server) that compares class level path annotation content, thus
// failing if a discrepancy is detected.
@Path("bookstore")
public interface IBookRest {
    String BASE_URI = "http://localhost:8080/ap/";

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    Collection<BookDTO> list(@QueryParam("search") String searchString);

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Boolean add(BookDTO book, @QueryParam("quantity") Integer quantity);

    @POST
    @Path("buy")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Collection<Integer> buy(List<BookDTO> books);
}

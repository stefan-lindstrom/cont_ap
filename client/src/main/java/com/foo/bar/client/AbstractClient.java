package com.foo.bar.client;

import com.foo.bar.model.BookDTO;
import com.foo.bar.rest.IBookRest;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class AbstractClient implements ClientInterface {
    private Client itsClient;

    public AbstractClient() {
        super();
         itsClient = ClientBuilder.newClient(new ClientConfig());
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

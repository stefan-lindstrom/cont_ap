package com.foo.bar.web;

import com.foo.bar.rest.IBookRest;
import com.foo.bar.service.BookStore;
import org.junit.Test;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;

import static org.junit.Assert.assertEquals;

public class TestIf {
    @Test
    public void testClassLevelPath() {
        Class<IBookRest> shared = IBookRest.class;
        Class<BookStore> server = BookStore.class;

        Annotation sharedAnnotation = shared.getAnnotation(Path.class);
        Annotation serverAnnotation = server.getAnnotation(Path.class);

        assertEquals("Class level Path annotation differs between shared I/F and server.", sharedAnnotation, serverAnnotation);
    }
}

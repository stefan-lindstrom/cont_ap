package com.foo.bar.datastore.io;

public class DatastoreReadException extends Exception {
    public DatastoreReadException() {
        super();
    }

    public DatastoreReadException(String message) {
        super(message);
    }

    public DatastoreReadException(String message, Throwable cause) {
        super(message, cause);
    }

}

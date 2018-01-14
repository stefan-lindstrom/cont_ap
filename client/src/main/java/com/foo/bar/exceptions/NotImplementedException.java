package com.foo.bar.exceptions;

// would be better with either user based server side rights (e.g, admin can use some methods, normal users others,
// or split admin/client interface (or both, even)...
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("NotImplementedException");
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super("NotImplementedException", cause);
    }
}

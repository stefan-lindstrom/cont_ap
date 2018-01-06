package com.foo.bar.types;

public enum StatusCode {
    OK(0), NOT_IN_STOCK(1), DOES_NOT_EXIST(2);

    private Integer itsCode;

    StatusCode(Integer theCode) {
        itsCode = theCode;
    }

    Integer code() { return itsCode; }
}
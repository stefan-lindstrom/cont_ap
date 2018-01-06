package com.foo.bar.model;

import java.math.BigDecimal;

public class BookDTO {
    private String itsTitle;
    private String itsAuthor;
    private BigDecimal itsPrice;

    public BookDTO() {
    }

    public BookDTO(String theTitle, String theAuthor, BigDecimal thePrice) {
        itsTitle = theTitle;
        itsAuthor = theAuthor;
        itsPrice = thePrice;
    }
}

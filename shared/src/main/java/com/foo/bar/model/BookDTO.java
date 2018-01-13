package com.foo.bar.model;

import java.math.BigDecimal;

// Slightly overkill to use separate data model classes for server/client with DTO(s) in between,
// but it is (IMO) good practice.
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

    public String getTitle() {  return itsTitle;  }
    public void setTitle(String itsTitle) {  this.itsTitle = itsTitle;  }

    public String getAuthor() {  return itsAuthor;  }
    public void setAuthor(String itsAuthor) { this.itsAuthor = itsAuthor; }

    public BigDecimal getPrice() {  return itsPrice; }
    public void setPrice(BigDecimal itsPrice) { this.itsPrice = itsPrice; }
}

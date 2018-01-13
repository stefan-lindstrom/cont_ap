package com.foo.bar.helpers;

import com.foo.bar.model.Book;
import com.foo.bar.model.BookDTO;

;

public class BookHelper {
    public static BookDTO mapToDTO(Book theBook) {
        return new BookDTO(theBook.getTitle(), theBook.getAuthor(), theBook.getPrice());
    }

    public static Book mapToBook(BookDTO theDTO) {
        return new Book(theDTO.getTitle(), theDTO.getAuthor(), theDTO.getPrice());
    }
}

package com.thomasalves.library.service;

import com.thomasalves.library.dto.BookInsertDTO;
import com.thomasalves.library.entities.Author;
import com.thomasalves.library.entities.Book;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.repository.BookRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;


    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {

        return bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book not found! Id: " + id ));
    }

    public boolean checkAvailability(Book book )  {

        return book.getCopies().stream().anyMatch(copy -> copy.getAvailability() == Availability.AVAILABLE);

    }

    public Book insert(BookInsertDTO book) {

        Book newBook = new Book();
        newBook.setTitle(book.getTitle());

        List<Author> authors = new ArrayList<>();

        for( Long id : book.getAuthorIds()){
          authors.add(authorService.findById(id));
        }

        newBook.setAuthors(authors);

        newBook.setCategory(book.getCategory());
        newBook.setLanguage(book.getLanguage());
        newBook.setPages(book.getPages());
        newBook.setEdition(book.getEdition());

        List<BookCopy> copies = new ArrayList<>();

        for (int i = 0; i < book.getNumberOfCopies(); i++) {
            BookCopy copy = new BookCopy();
            copy.setBook(newBook);
            copy.setAvailability(Availability.AVAILABLE);
            copy.setShelfLocation(null);

            copies.add(copy);

        }
        newBook.setCopies(copies);

        return bookRepository.save(newBook);

    }


}

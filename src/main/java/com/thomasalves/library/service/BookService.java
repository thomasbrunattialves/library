package com.thomasalves.library.service;

import com.thomasalves.library.dto.BookInsertDTO;
import com.thomasalves.library.entities.Author;
import com.thomasalves.library.entities.Book;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.BookStatusHistory;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.entities.enums.BookStatus;
import com.thomasalves.library.repository.BookRepository;
import com.thomasalves.library.repository.BookStatusHistoryRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import com.thomasalves.library.service.exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookStatusHistoryRepository historyRepository;


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

    @Transactional
    public void delete(Long id) {
        Book book = findBookById(id);
        BookStatus previousStatus = book.getStatus();
        if(previousStatus == BookStatus.WITHDRAWING || previousStatus == BookStatus.REMOVED){
            throw new ResourceConflictException("Book is already being withdrawn or removed.");
        }
        List <Availability> availabilities = new ArrayList<>();

        for(int i = 0; i < book.getCopies().size(); i++){
            BookCopy copy = book.getCopies().get(i);
            availabilities.add(copy.getAvailability());
        }
        if(availabilities.stream().allMatch(availability -> availability == Availability.AVAILABLE)){
            book.setStatus(BookStatus.REMOVED);
        }
        else{
            book.setStatus(BookStatus.WITHDRAWING);
        }
        bookRepository.save(book);
        BookStatus newStatus = book.getStatus();
        BookStatusHistory history = new BookStatusHistory(book, previousStatus, newStatus, "Book withdrawn from circulation");
        historyRepository.save(history);

    }




    public void update(Long id, BookInsertDTO bookInsertDTO) {
        Book book = findBookById(id);
        updateData(book, bookInsertDTO);
        bookRepository.save(book);
    }

    private void updateData(Book book, BookInsertDTO bIDto){

        book.setTitle(bIDto.getTitle());

        List<Author> authors = new ArrayList<>();

        for( Long id : bIDto.getAuthorIds()){
            authors.add(authorService.findById(id));
        }

        book.setAuthors(authors);

        book.setCategory(bIDto.getCategory());
        book.setLanguage(bIDto.getLanguage());
        book.setPages(bIDto.getPages());
        book.setEdition(bIDto.getEdition());

    }


}

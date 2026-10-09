package com.thomasalves.library.service;


import com.thomasalves.library.dto.BookCopyInsertDTO;
import com.thomasalves.library.dto.BookCopyUpdateDTO;
import com.thomasalves.library.entities.Book;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.repository.BookCopyRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookService bookService;

    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    public BookCopy findById(Long id) {
        return bookCopyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book copy not found! Id: " + id));
    }

    public BookCopy insert(BookCopyInsertDTO bookCopyInsertDTO){

        BookCopy bookCopy = new BookCopy();

        bookCopy.setAvailability(Availability.AVAILABLE);

        bookCopy.setShelfLocation(bookCopyInsertDTO.getShelfLocation());
        Book book = bookService.findBookById(bookCopyInsertDTO.getBookId());
        bookCopy.setBook(book);

        return bookCopyRepository.save(bookCopy);

    }

    public void delete(Long id) {
        BookCopy bk = findById(id);
        bookCopyRepository.delete(bk);
    }

    public void update(Long id,  BookCopyUpdateDTO bookCopyUpdateDTO) {
        BookCopy bookCopy = findById(id);
        bookCopy.setShelfLocation(bookCopyUpdateDTO.getShelfLocation());
        bookCopy.setAvailability(bookCopyUpdateDTO.getAvailability());

       bookCopyRepository.save(bookCopy);

    }


}


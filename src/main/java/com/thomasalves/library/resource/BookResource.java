package com.thomasalves.library.resource;

import com.thomasalves.library.dto.BookDTO;
import com.thomasalves.library.entities.Book;
import com.thomasalves.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity <List<BookDTO>> findAll(){
        List<Book> list = bookService.findAll();
        List<BookDTO> listDto = list.stream().map(book -> new BookDTO(book, bookService.checkAvailability(book))).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id){
        Book book = bookService.findBookById(id);
        boolean available = bookService.checkAvailability(book);
        BookDTO bookDto = new BookDTO(book, available);
        return ResponseEntity.ok().body(bookDto);

    }



}

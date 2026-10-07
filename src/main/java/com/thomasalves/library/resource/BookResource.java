package com.thomasalves.library.resource;

import com.thomasalves.library.dto.BookDTO;
import com.thomasalves.library.dto.BookInsertDTO;
import com.thomasalves.library.entities.Book;
import com.thomasalves.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<BookDTO> insert(@RequestBody BookInsertDTO bIDto){
        Book book = bookService.insert(bIDto);
        boolean available = bookService.checkAvailability(book);
        BookDTO bookDto = new BookDTO(book, available);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();
        return ResponseEntity.created(uri).body(bookDto);
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BookInsertDTO bookInsertDTO) {
        bookService.update(id, bookInsertDTO);
        return ResponseEntity.ok().build();
    }

}

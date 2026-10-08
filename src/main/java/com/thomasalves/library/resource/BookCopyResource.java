package com.thomasalves.library.resource;


import com.thomasalves.library.dto.BookCopyDTO;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.service.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book-copies")
public class BookCopyResource {

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping
    public ResponseEntity<List<BookCopyDTO>> findAll() {
        List<BookCopy> list = bookCopyService.findAll();
        List<BookCopyDTO> dtoList = list.stream().map(bookCopy -> new BookCopyDTO(bookCopy) ).toList();
        return ResponseEntity.ok(dtoList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDTO> findById(@PathVariable Long id) {
        BookCopy bookcopy = bookCopyService.findById(id);
        BookCopyDTO bookCopyDTO = new BookCopyDTO(bookcopy);
        return ResponseEntity.ok(bookCopyDTO);

    }

}

package com.thomasalves.library.resource;


import com.thomasalves.library.dto.BookCopyDTO;
import com.thomasalves.library.dto.BookCopyInsertDTO;
import com.thomasalves.library.dto.BookCopyUpdateDTO;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.service.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/book-copies")
public class BookCopyResource {

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping
    public ResponseEntity<List<BookCopyDTO>> findAll() {
        List<BookCopy> list = bookCopyService.findAll();
        List<BookCopyDTO> dtoList = list.stream().map(BookCopyDTO::new).toList();
        return ResponseEntity.ok(dtoList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDTO> findById(@PathVariable Long id) {
        BookCopy bookcopy = bookCopyService.findById(id);
        BookCopyDTO bookCopyDTO = new BookCopyDTO(bookcopy);
        return ResponseEntity.ok(bookCopyDTO);

    }


    @PostMapping
    public ResponseEntity<BookCopyDTO> insert(@RequestBody BookCopyInsertDTO bookCopyInsertDTO) {

        BookCopy bk = bookCopyService.insert(bookCopyInsertDTO);

        BookCopyDTO bookCopyDTO = new BookCopyDTO(bk);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bk.getId())
                .toUri();

        return ResponseEntity.created(uri).body(bookCopyDTO);


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookCopyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BookCopyUpdateDTO dto){
        bookCopyService.update(id, dto);
        return ResponseEntity.ok().build();
    }







}

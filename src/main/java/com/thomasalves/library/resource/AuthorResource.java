package com.thomasalves.library.resource;


import com.thomasalves.library.dto.AuthorDTO;
import com.thomasalves.library.dto.AuthorInsertDTO;
import com.thomasalves.library.entities.Author;
import com.thomasalves.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorResource {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll(){
        List<Author> list = authorService.findAll();
        List<AuthorDTO> authors = list.stream().map(author -> new AuthorDTO(author)).toList();
        return ResponseEntity.ok().body(authors);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable Long id){
        Author author = authorService.findById(id);
        AuthorDTO authorDto = new AuthorDTO(author);
        return ResponseEntity.ok().body(authorDto);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> insert(@RequestBody AuthorInsertDTO authorInsertDTODTO) {
        Author author = authorService.insert(authorInsertDTODTO);
        AuthorDTO authorDTO = new AuthorDTO(author);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId()).toUri();


        return ResponseEntity.created(uri).body(authorDTO);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AuthorInsertDTO authorInsertDTO){
        authorService.update(id, authorInsertDTO);
        return ResponseEntity.ok().build();
    }
}

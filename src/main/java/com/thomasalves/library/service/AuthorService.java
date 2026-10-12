package com.thomasalves.library.service;

import com.thomasalves.library.dto.AuthorInsertDTO;
import com.thomasalves.library.entities.Author;
import com.thomasalves.library.repository.AuthorRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;

import com.thomasalves.library.service.exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Author not found! Id: " + id));
    }

    public Author insert(AuthorInsertDTO authorInsertDTO){
        Author author = new Author();
        author.setName(authorInsertDTO.getName());
        author.setBirthYear(authorInsertDTO.getBirthYear());
        author.setNationality(authorInsertDTO.getNationality());
        return authorRepository.save(author);
    }

    @Transactional
    public void delete(Long id) {
        Author author = findById(id);
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new ResourceConflictException("Cannot delete author with associated books.");
        }
        authorRepository.delete(author);
    }

    public Author update(Long id, AuthorInsertDTO authorInsertDTO) {
        Author author = findById(id);
        author.setName(authorInsertDTO.getName());
        author.setBirthYear(authorInsertDTO.getBirthYear());
        author.setNationality(authorInsertDTO.getNationality());
        return authorRepository.save(author);
    }

}

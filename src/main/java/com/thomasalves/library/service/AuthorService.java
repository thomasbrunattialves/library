package com.thomasalves.library.service;

import com.thomasalves.library.entities.Author;
import com.thomasalves.library.repository.AuthorRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Author not found! Id: " + id));
    }



}

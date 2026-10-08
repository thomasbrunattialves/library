package com.thomasalves.library.service;


import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.repository.BookCopyRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {

    @Autowired
    BookCopyRepository bookCopyRepository;

    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    public BookCopy findById(Long id) {
        return bookCopyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book copy not found! Id: " + id));
    }






}


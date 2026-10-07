package com.thomasalves.library.service;


import com.thomasalves.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {

    @Autowired
    BookCopyRepository bookCopyRepository;




}


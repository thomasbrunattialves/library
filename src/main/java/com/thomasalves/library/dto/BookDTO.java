package com.thomasalves.library.dto;

import com.thomasalves.library.entities.Book;
import com.thomasalves.library.entities.enums.Category;

import java.io.Serializable;
import java.util.List;

public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private List<AuthorDTO> authors;
    private Category category;
    private String language;
    private int pages;
    private Integer edition;
    private boolean availability;

    public BookDTO(){

    }

    public BookDTO(Book book, boolean availability) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authors = book.getAuthors().stream().map(AuthorDTO::new).toList();
        this.category = book.getCategory();
        this.language = book.getLanguage();
        this.pages = book.getPages();
        this.edition = book.getEdition();
        this.availability = availability;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

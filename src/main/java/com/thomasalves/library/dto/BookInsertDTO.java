package com.thomasalves.library.dto;

import com.thomasalves.library.entities.enums.Category;

import java.io.Serializable;
import java.util.List;

public class BookInsertDTO implements Serializable {

    private static final long serialVersiounUID = 1L;

    private String title;
    private List<Long> authorIds;
    private Category category;
    private String language;
    private Integer pages;
    private Integer edition;
    private Integer numberOfCopies;

    public BookInsertDTO() {

    }

    public BookInsertDTO(String title, List<Long> authorIds, Category category, String language, Integer pages, Integer edition, Integer numberOfCopies) {
        this.title = title;
        this.authorIds = authorIds;
        this.category = category;
        this.language = language;
        this.pages = pages;
        this.edition = edition;
        this.numberOfCopies = numberOfCopies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
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

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}

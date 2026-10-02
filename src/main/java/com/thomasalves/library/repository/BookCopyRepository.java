package com.thomasalves.library.repository;

import com.thomasalves.library.entities.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

}

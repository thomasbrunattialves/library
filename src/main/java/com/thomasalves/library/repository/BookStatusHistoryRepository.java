package com.thomasalves.library.repository;

import com.thomasalves.library.entities.BookStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStatusHistoryRepository extends JpaRepository<BookStatusHistory, Long> {
}

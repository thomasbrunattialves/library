package com.thomasalves.library.repository;

import com.thomasalves.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

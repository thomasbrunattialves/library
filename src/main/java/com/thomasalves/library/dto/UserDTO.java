package com.thomasalves.library.dto;

import com.thomasalves.library.entities.User;

import java.io.Serializable;
import java.time.LocalDate;


public class UserDTO implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;
        private String name;
        private LocalDate birthDate;
        private String email;


    public UserDTO() {

    }

    public UserDTO (User user){
        this.id = user.getId();
        this.name = user.getName();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
    }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

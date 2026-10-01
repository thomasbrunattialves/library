package com.thomasalves.library.service;

import com.thomasalves.library.dto.UserDTO;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.repository.UserRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found! Id: " + id ));

    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public User fromDTO(UserDTO us){
        User user = new User();
        user.setName(us.getName());
        user.setBirthDate(us.getBirthDate());
        user.setEmail(us.getEmail());
        return user;
    }

    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }


    public void update(Long id , UserDTO userDto) {
        User user = findById(id);
        updateData(user, userDto);
        userRepository.save(user);
    }

    private void updateData(User user, UserDTO userDto) {
        user.setName(userDto.getName());
        user.setBirthDate(userDto.getBirthDate());
        user.setEmail(userDto.getEmail());

    }






}
package ru.jm.spring.dao;

import ru.jm.spring.model.User;

import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User getUserByUsername(String username);

    public void save(User user);

    public void updateUser(Long id, User updatedUser);

    public void deleteUser(Long id);


    //void updateUser(Long id);
}

// test branch

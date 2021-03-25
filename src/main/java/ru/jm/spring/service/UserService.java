package ru.jm.spring.service;

import ru.jm.spring.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User getUserById(int id);

    public User getUserByUsername(String  username);

    public void save(User user);

    public void updateUser(int id, User updatedUser);

    public void deleteUser(int id);
}

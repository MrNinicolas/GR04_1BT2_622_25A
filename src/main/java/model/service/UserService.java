package model.service;

import model.entities.User;
import model.jpa.UserJPA;

public class UserService {
    private final UserJPA userJPA;

    public UserService() {
        userJPA = new UserJPA();
    }

    public User authenticate(String dni, String password) {
        User user = userJPA.findUserByDni(dni);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean createUser(User user) {
        return userJPA.create(user);
    }
}
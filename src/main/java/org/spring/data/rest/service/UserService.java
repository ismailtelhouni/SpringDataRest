package org.spring.data.rest.service;

import org.spring.data.rest.modele.User;

public interface UserService {
    public User registerUser(String username, String email , String password);
}

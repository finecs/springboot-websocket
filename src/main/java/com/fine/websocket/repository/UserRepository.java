package com.fine.websocket.repository;

import com.fine.websocket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}

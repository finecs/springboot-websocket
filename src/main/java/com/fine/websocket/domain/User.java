package com.fine.websocket.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */

@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private LocalDateTime create_data;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreate_data() {
        return create_data;
    }

    public void setCreate_data(LocalDateTime create_data) {
        this.create_data = create_data;
    }
}



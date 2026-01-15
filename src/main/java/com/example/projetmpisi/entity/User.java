package com.example.projetmpisi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;      // <- pour UserControllerTest
    private String username;  // <- pour les steps/tests Cucumber
    private String email;

    public User() {
    }

    // Constructeur 4 arguments (déjà utilisé par certains tests)
    public User(int id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    // **NOUVEAU** constructeur 3 arguments pour faire passer tous les tests existants
    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // GET/SET pour 'name' (tests UserControllerTest)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // GET/SET pour 'username' (tests Cucumber)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}




